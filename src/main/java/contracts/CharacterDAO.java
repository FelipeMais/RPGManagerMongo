package contracts;

import model.Character;
import model.dto.CharacterWeight;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CharacterDAO {
    Integer insert(Character newCharacter) throws SQLException;
    void update(Character character) throws SQLException;
    void remove(Integer characterId) throws SQLException;
    Character findById(Integer characterId) throws SQLException;
    List<Character> listAll() throws SQLException;
    List<Character> findByClassAndSpecies(Integer classId, Integer speciesId) throws SQLException;
    List<CharacterWeight> findPersonagensComSobrecarga(BigDecimal limitePeso) throws SQLException;
}
