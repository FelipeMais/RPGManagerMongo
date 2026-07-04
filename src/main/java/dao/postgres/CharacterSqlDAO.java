package dao.postgres;

import contracts.CharacterDAO;
import model.dto.CharacterWeight;
import model.relationship.InventoryItem;
import model.Character;

import java.math.BigDecimal;
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
    private final InventorySqlDAO inventoryDAO;

    public CharacterSqlDAO(Connection connection) {
        this.connection = connection;
        this.inventoryDAO = new InventorySqlDAO(connection);
    }

    @Override
    public Integer insert(Character newCharacter) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO personagem(id_jogador, id_ficha, local_atual, nome_personagem, pontos_vida, pontos_mana, historia) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            setNullableInteger(st, 1, newCharacter.getPlayerId());
            setNullableInteger(st, 2, newCharacter.getSheetId());
            setNullableInteger(st, 3, newCharacter.getCurrentLocationId());
            st.setString(4, newCharacter.getName());
            st.setInt(5, newCharacter.getHitPoints());
            st.setInt(6, newCharacter.getManaPoints());
            st.setString(7, newCharacter.getHistory());
            st.executeUpdate();

            ResultSet generatedKeys = st.getGeneratedKeys();
            if (!generatedKeys.next()) {
                generatedKeys.close();
                st.close();
                throw new SQLException("Nao foi possivel obter o ID do personagem criado.");
            }

            Integer characterId = generatedKeys.getInt(1);
            generatedKeys.close();
            st.close();

            persistInventory(characterId, newCharacter.getInventory());
            connection.commit();
            return characterId;
        } catch (SQLException err) {
            connection.rollback();
            throw err;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
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
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            inventoryDAO.removeByCharacterId(characterId);
            PreparedStatement st = connection.prepareStatement("DELETE FROM personagem WHERE id_personagem = ?");
            st.setInt(1, characterId);
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
    public Character findById(Integer characterId) throws SQLException {
        List<Character> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT id_personagem, id_jogador, id_ficha, local_atual, nome_personagem, pontos_vida, pontos_mana, historia FROM personagem WHERE id_personagem = ?"
        );
        st.setInt(1, characterId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            Character character = Character.fromResultSet(result);
            character.setInventory(inventoryDAO.findByCharacterId(character.getId()));
            list.add(character);
        }
        st.close();
        if (!list.isEmpty()) {
            return list.get(0);
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
            Character character = Character.fromResultSet(result);
            character.setInventory(inventoryDAO.findByCharacterId(character.getId()));
            list.add(character);
        }
        st.close();
        return list;
    }

    private void persistInventory(Integer characterId, List<InventoryItem> inventoryItems) throws SQLException {
        if (inventoryItems == null) {
            return;
        }

        for (InventoryItem inventoryItem : inventoryItems) {
            inventoryDAO.insert(new InventoryItem(
                    inventoryItem.getItemId(),
                    characterId,
                    inventoryItem.getQuantity(),
                    inventoryItem.getItem()
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

    @Override
    public List<Character> findByClassAndSpecies(Integer classId, Integer speciesId) throws SQLException {
        List<Character> list = new ArrayList<>();

        String sql = "SELECT p.id_personagem, p.id_jogador, p.id_ficha, p.local_atual, " +
                "p.nome_personagem, p.pontos_vida, p.pontos_mana, p.historia " +
                "FROM personagem p " +
                "INNER JOIN ficha f ON p.id_ficha = f.id_ficha " +
                "WHERE (? = 0 OR f.id_classe = ?) " +
                "AND (? = 0 OR f.id_especie = ?)";

        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, classId);
        st.setInt(2, classId);
        st.setInt(3, speciesId);
        st.setInt(4, speciesId);

        ResultSet result = st.executeQuery();
        while (result.next()) {
            Character character = Character.fromResultSet(result);
            character.setInventory(inventoryDAO.findByCharacterId(character.getId()));
            list.add(character);
        }
        st.close();

        return list;
    }

    @Override
    public List<CharacterWeight> findPersonagensComSobrecarga(BigDecimal limitePeso) throws SQLException {
        List<CharacterWeight> list = new ArrayList<>();
        String sql = "SELECT p.id_personagem, p.id_jogador, p.id_ficha, p.local_atual, " +
                "p.nome_personagem, p.pontos_vida, p.pontos_mana, p.historia, " +
                "SUM(i.peso * inv.quantidade) AS peso_total " +
                "FROM personagem p " +
                "INNER JOIN inventario inv ON p.id_personagem = inv.id_personagem " +
                "INNER JOIN itens i ON inv.id_item = i.id_item " +
                "GROUP BY p.id_personagem, p.id_jogador, p.id_ficha, p.local_atual, " +
                "p.nome_personagem, p.pontos_vida, p.pontos_mana, p.historia " +
                "HAVING SUM(i.peso * inv.quantidade) > ?";

        PreparedStatement st = connection.prepareStatement(sql);
        st.setBigDecimal(1, limitePeso);

        ResultSet result = st.executeQuery();
        while (result.next()) {
            Character character = Character.fromResultSet(result);
            BigDecimal pesoTotal = result.getBigDecimal("peso_total");

            list.add(new CharacterWeight(character, pesoTotal));
        }
        st.close();

        return list;
    }
}
