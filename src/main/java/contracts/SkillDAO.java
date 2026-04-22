package contracts;

import model.Skill;

import java.sql.SQLException;
import java.util.List;

public interface SkillDAO {
    void insert(Skill newSkill) throws SQLException;
    void update(Skill skill) throws SQLException;
    void remove(Integer skillId) throws SQLException;
    Skill findById(Integer skillId) throws SQLException;
    List<Skill> listAll() throws SQLException;
}
