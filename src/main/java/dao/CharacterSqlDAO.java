package dao;

import contracts.CharacterDAO;
import model.Attribute;
import model.Character;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CharacterSqlDAO implements CharacterDAO {
    Connection connection;

    public CharacterSqlDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void insert(Character newCharacter) throws SQLException {

    }

    @Override
    public void update(Character character) throws SQLException {

    }

    @Override
    public void remove(Integer characterId) throws SQLException {

    }

    @Override
    public Character findById(Integer characterId) throws SQLException {
        return null;
    }

    @Override
    public List<Character> listAll() throws SQLException {
        return List.of();
    }
}
