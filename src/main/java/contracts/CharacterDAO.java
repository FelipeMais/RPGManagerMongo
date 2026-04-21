package contracts;

import model.Attribute;
import model.Character;

import java.sql.SQLException;
import java.util.List;

public interface CharacterDAO {
    void insert(Character newCharacter) throws SQLException;
    void update(Character character) throws SQLException;
    void remove(Integer characterId) throws SQLException;
    Character findById(Integer characterId) throws SQLException;
    List<Character> listAll() throws SQLException;
}
