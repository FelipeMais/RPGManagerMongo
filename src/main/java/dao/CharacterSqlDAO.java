package dao;

import contracts.CharacterDAO;
import model.Character;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CharacterSqlDAO implements CharacterDAO {
    private final Connection connection;

    public CharacterSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Character newCharacter) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO personagem(id_jogador, id_ficha, local_atual, nome_personagem, pontos_vida, pontos_mana, historia) VALUES (?, ?, ?, ?, ?, ?, ?)"
        );
        setNullableInteger(st, 1, newCharacter.getPlayerId());
        setNullableInteger(st, 2, newCharacter.getSheetId());
        setNullableInteger(st, 3, newCharacter.getCurrentLocationId());
        st.setString(4, newCharacter.getName());
        st.setInt(5, newCharacter.getHitPoints());
        st.setInt(6, newCharacter.getManaPoints());
        st.setString(7, newCharacter.getHistory());
        st.execute();
        st.close();
    }

    @Override
    public void update(Character character) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE personagem SET id_jogador = ?, id_ficha = ?, local_atual = ?, nome_personagem = ?, pontos_vida = ?, pontos_mana = ?, historia = ? WHERE id_personagem = ?"
        );
        setNullableInteger(st, 1, character.getPlayerId());
        setNullableInteger(st, 2, character.getSheetId());
        setNullableInteger(st, 3, character.getCurrentLocationId());
        st.setString(4, character.getName());
        st.setInt(5, character.getHitPoints());
        st.setInt(6, character.getManaPoints());
        st.setString(7, character.getHistory());
        st.setInt(8, character.getId());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer characterId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM personagem WHERE id_personagem = ?");
        st.setInt(1, characterId);
        st.execute();
        st.close();
    }

    @Override
    public Character findById(Integer characterId) throws SQLException {
        List<Character> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT id_personagem, id_jogador, id_ficha, local_atual, nome_personagem, pontos_vida, pontos_mana, historia FROM personagem WHERE id_personagem = ?"
        );
        st.setInt(1, characterId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            list.add(Character.fromResultSet(result));
        }
        st.close();
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    @Override
    public List<Character> listAll() throws SQLException {
        List<Character> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery(
                "SELECT id_personagem, id_jogador, id_ficha, local_atual, nome_personagem, pontos_vida, pontos_mana, historia FROM personagem"
        );
        while (result.next()) {
            list.add(Character.fromResultSet(result));
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
}
