package contracts;

import model.relationship.CharacterSheetSkill;

import java.sql.SQLException;
import java.util.List;

public interface CharacterSheetSkillDAO {
    void insert(CharacterSheetSkill characterSheetSkill) throws SQLException;
    void removeBySheetId(Integer sheetId) throws SQLException;
    List<CharacterSheetSkill> findBySheetId(Integer sheetId) throws SQLException;

}
