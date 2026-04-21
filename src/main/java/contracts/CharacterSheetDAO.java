package contracts;

import model.CharacterSheet;

import java.sql.SQLException;
import java.util.List;

public interface CharacterSheetDAO {
    Integer insert(CharacterSheet newCharacterSheet) throws SQLException;
    void update(CharacterSheet characterSheet) throws SQLException;
    void remove(Integer characterSheetId) throws SQLException;
    CharacterSheet findById(Integer characterSheetId) throws SQLException;
    List<CharacterSheet> listAll() throws SQLException;
}
