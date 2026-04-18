package contracts;

import model.Player;

import java.sql.SQLException;
import java.util.List;

public interface PlayerDAO {
    void insert(Player newPlayer) throws SQLException;
    void update(Player player) throws SQLException;
    void remove(Integer playerId) throws SQLException;
    Player findById(Integer playerId) throws SQLException;
    List<Player> listAll() throws SQLException;
}
