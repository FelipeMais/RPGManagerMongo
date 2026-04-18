package dao;

import contracts.PlayerDAO;
import model.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlayerSqlDAO implements PlayerDAO {

    private final Connection connection;

    public PlayerSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Player newPlayer) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO jogador(id_jogador, nome_jogador, data_entrada, ativo) VALUES (?, ?, ?, ?)"
        );
        st.setInt(1, newPlayer.getId());
        st.setString(2, newPlayer.getName());
        st.setTimestamp(3, newPlayer.getEntryDate());
        st.setBoolean(4, newPlayer.getActive());
        st.execute();
        st.close();
    }

    @Override
    public void update(Player player) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE jogador SET nome_jogador = ?, data_entrada = ?, ativo = ? WHERE id_jogador = ?"
        );
        st.setString(1, player.getName());
        st.setTimestamp(2, player.getEntryDate());
        st.setBoolean(3, player.getActive());
        st.setInt(4, player.getId());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer playerId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM jogador WHERE id_jogador = ?");
        st.setInt(1, playerId);
        st.execute();
        st.close();
    }

    @Override
    public Player findById(Integer playerId) throws SQLException {
        List<Player> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("SELECT * FROM jogador WHERE id_jogador = ?");
        st.setInt(1, playerId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            list.add(Player.fromResultSet(result));
        }
        st.close();
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    @Override
    public List<Player> listAll() throws SQLException {
        List<Player> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM jogador");
        while (result.next()) {
            list.add(Player.fromResultSet(result));
        }
        st.close();
        return list;
    }
}
