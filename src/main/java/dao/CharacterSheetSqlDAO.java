package dao;

import contracts.CharacterSheetDAO;
import contracts.CharacterSheetSkillDAO;
import model.CharacterSheet;
import model.relationship.CharacterSheetSkill;
import model.relationship.ItemAttribute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CharacterSheetSqlDAO implements CharacterSheetDAO {
    private final Connection connection;
    private final CharacterSheetSkillDAO characterSheetSkillDAO;
    public CharacterSheetSqlDAO(Connection connection) {
        this.connection = connection;
        this.characterSheetSkillDAO = new CharacterSheetSkillSqlDAO(connection);
    }

    @Override
    public Integer insert(CharacterSheet newCharacterSheet) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO ficha(id_classe, id_especie, pontos_vida_max, pontos_mana_max, forca, destreza, constituicao, inteligencia, sabedoria, carisma, nivel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        setNullableInteger(st, 1, newCharacterSheet.getClassId());
        setNullableInteger(st, 2, newCharacterSheet.getSpeciesId());
        st.setInt(3, newCharacterSheet.getMaxHitPoints());
        st.setInt(4, newCharacterSheet.getMaxManaPoints());
        st.setInt(5, newCharacterSheet.getStrength());
        st.setInt(6, newCharacterSheet.getDexterity());
        st.setInt(7, newCharacterSheet.getConstitution());
        st.setInt(8, newCharacterSheet.getIntelligence());
        st.setInt(9, newCharacterSheet.getWisdom());
        st.setInt(10, newCharacterSheet.getCharisma());
        st.setInt(11, newCharacterSheet.getLevel());
        st.executeUpdate();

        ResultSet generatedKeys = st.getGeneratedKeys();
        if (!generatedKeys.next()) {
            int id = generatedKeys.getInt(1);
            generatedKeys.close();
            st.close();
            return id;
        }

        generatedKeys.close();
        st.close();
        throw new SQLException("Nao foi possivel obter o ID da ficha criada.");
    }

    @Override
    public void update(CharacterSheet characterSheet) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE ficha SET id_classe = ?, id_especie = ?, pontos_vida_max = ?, pontos_mana_max = ?, forca = ?, destreza = ?, constituicao = ?, inteligencia = ?, sabedoria = ?, carisma = ?, nivel = ? WHERE id_ficha = ?"
            );
            setNullableInteger(st, 1, characterSheet.getClassId());
            setNullableInteger(st, 2, characterSheet.getSpeciesId());
            st.setInt(3, characterSheet.getMaxHitPoints());
            st.setInt(4, characterSheet.getMaxManaPoints());
            st.setInt(5, characterSheet.getStrength());
            st.setInt(6, characterSheet.getDexterity());
            st.setInt(7, characterSheet.getConstitution());
            st.setInt(8, characterSheet.getIntelligence());
            st.setInt(9, characterSheet.getWisdom());
            st.setInt(10, characterSheet.getCharisma());
            st.setInt(11, characterSheet.getLevel());
            st.setInt(12, characterSheet.getId());
            st.executeUpdate();
            st.close();

            characterSheetSkillDAO.removeBySheetId(characterSheet.getId());
            persistSkills(characterSheet.getId(), characterSheet.getSkills());
            connection.commit();
        }catch (SQLException err) {
            connection.rollback();
            throw err;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void remove(Integer characterSheetId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try{
            characterSheetSkillDAO.removeBySheetId(characterSheetId);
            PreparedStatement st = connection.prepareStatement("DELETE FROM ficha WHERE id_ficha = ?");
            st.setInt(1, characterSheetId);
            st.execute();
            st.close();
            connection.commit();
        }catch (SQLException err) {
            connection.rollback();
            throw err;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public CharacterSheet findById(Integer characterSheetId) throws SQLException {
        List<CharacterSheet> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT id_ficha, id_classe, id_especie, pontos_vida_max, pontos_mana_max, forca, destreza, constituicao, inteligencia, sabedoria, carisma, nivel FROM ficha WHERE id_ficha = ?"
        );
        st.setInt(1, characterSheetId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            CharacterSheet characterSheet = CharacterSheet.fromResultSet(result);
            characterSheet.setSkills(characterSheetSkillDAO.findBySheetId(characterSheet.getId()));
            list.add(characterSheet);
        }
        st.close();
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    @Override
    public List<CharacterSheet> listAll() throws SQLException {
        List<CharacterSheet> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery(
                "SELECT id_ficha, id_classe, id_especie, pontos_vida_max, pontos_mana_max, forca, destreza, constituicao, inteligencia, sabedoria, carisma, nivel FROM ficha"
        );
        while (result.next()) {
            CharacterSheet characterSheet = CharacterSheet.fromResultSet(result);
            characterSheet.setSkills(characterSheetSkillDAO.findBySheetId(characterSheet.getId()));
            list.add(characterSheet);
        }
        st.close();
        return list;
    }

    private void persistSkills(Integer sheetId, List<CharacterSheetSkill> skills) throws SQLException {
        if (skills == null) {
            return;
        }
        for (CharacterSheetSkill skill : skills) {
            characterSheetSkillDAO.insert(new CharacterSheetSkill(
                    sheetId,
                    skill.getSkillId(),
                    skill.getValue(),
                    skill.getSkill()
            ));
        }
    }

    private void setNullableInteger(PreparedStatement st, int parameterIndex, Integer value) throws SQLException {
        if (value == null) {
            st.setNull(parameterIndex, Types.INTEGER);
            return;
        }
        st.setInt(parameterIndex, value);
    }
}
