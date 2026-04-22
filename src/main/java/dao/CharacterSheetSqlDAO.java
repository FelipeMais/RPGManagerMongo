package dao;

import contracts.CharacterSheetDAO;
import model.Ability;
import model.CharacterSheet;
import model.Magic;

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
    public CharacterSheetSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Integer insert(CharacterSheet newCharacterSheet) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
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
                generatedKeys.close();
                st.close();
                throw new SQLException("Nao foi possivel obter o ID da ficha criada.");
            }

            int id = generatedKeys.getInt(1);
            generatedKeys.close();
            st.close();

            persistKnownMagics(id, newCharacterSheet.getKnownMagics());
            persistKnownAbilities(id, newCharacterSheet.getKnownAbilities());
            connection.commit();
            return id;
        } catch (SQLException err) {
            connection.rollback();
            throw err;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
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

            removeKnownMagicsBySheetId(characterSheet.getId());
            persistKnownMagics(characterSheet.getId(), characterSheet.getKnownMagics());
            removeKnownAbilitiesBySheetId(characterSheet.getId());
            persistKnownAbilities(characterSheet.getId(), characterSheet.getKnownAbilities());
            connection.commit();
        } catch (SQLException err) {
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
        try {
            removeKnownMagicsBySheetId(characterSheetId);
            removeKnownAbilitiesBySheetId(characterSheetId);
            PreparedStatement st = connection.prepareStatement("DELETE FROM ficha WHERE id_ficha = ?");
            st.setInt(1, characterSheetId);
            st.execute();
            st.close();
            connection.commit();
        } catch (SQLException err) {
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
            characterSheet.setKnownMagics(findKnownMagicsBySheetId(characterSheet.getId()));
            characterSheet.setKnownAbilities(findKnownAbilitiesBySheetId(characterSheet.getId()));
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
            characterSheet.setKnownMagics(findKnownMagicsBySheetId(characterSheet.getId()));
            characterSheet.setKnownAbilities(findKnownAbilitiesBySheetId(characterSheet.getId()));
            list.add(characterSheet);
        }
        st.close();
        return list;
    }


    private void setNullableInteger(PreparedStatement st, int parameterIndex, Integer value) throws SQLException {
        if (value == null) {
            st.setNull(parameterIndex, Types.INTEGER);
            return;
        }
        st.setInt(parameterIndex, value);
    }

    private void persistKnownMagics(Integer characterSheetId, List<Magic> knownMagics) throws SQLException {
        if (knownMagics == null) {
            return;
        }

        for (Magic magic : knownMagics) {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO magiasconhecidas(id_magia, id_ficha) VALUES (?, ?)"
            );
            st.setInt(1, magic.getId());
            st.setInt(2, characterSheetId);
            st.executeUpdate();
            st.close();
        }
    }

    private void removeKnownMagicsBySheetId(Integer characterSheetId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM magiasconhecidas WHERE id_ficha = ?");
        st.setInt(1, characterSheetId);
        st.executeUpdate();
        st.close();
    }

    private List<Magic> findKnownMagicsBySheetId(Integer characterSheetId) throws SQLException {
        List<Magic> knownMagics = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT m.* FROM magiasconhecidas mc INNER JOIN magias m ON m.id_magia = mc.id_magia WHERE mc.id_ficha = ? ORDER BY m.nome_magia"
        );
        st.setInt(1, characterSheetId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            knownMagics.add(Magic.fromResultSet(result));
        }
        st.close();
        return knownMagics;
    }

    private void persistKnownAbilities(Integer characterSheetId, List<Ability> knownAbilities) throws SQLException {
        if (knownAbilities == null) {
            return;
        }

        for (Ability ability : knownAbilities) {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO fichahabilidades(id_habilidade, id_ficha) VALUES (?, ?)"
            );
            st.setInt(1, ability.getId());
            st.setInt(2, characterSheetId);
            st.executeUpdate();
            st.close();
        }
    }

    private void removeKnownAbilitiesBySheetId(Integer characterSheetId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM fichahabilidades WHERE id_ficha = ?");
        st.setInt(1, characterSheetId);
        st.executeUpdate();
        st.close();
    }

    private List<Ability> findKnownAbilitiesBySheetId(Integer characterSheetId) throws SQLException {
        List<Ability> knownAbilities = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT h.id_habilidade, h.nome_habilidade, h.descr_habilidade, h.atributo_base FROM fichahabilidades fh INNER JOIN habilidades h ON h.id_habilidade = fh.id_habilidade WHERE fh.id_ficha = ? ORDER BY h.nome_habilidade"
        );
        st.setInt(1, characterSheetId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            knownAbilities.add(Ability.fromResultSet(result));
        }
        st.close();
        return knownAbilities;
    }
}
