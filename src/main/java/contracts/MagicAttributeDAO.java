package contracts;

import model.relationship.MagicAttribute;

import java.sql.SQLException;
import java.util.List;

public interface MagicAttributeDAO {
    void insert(MagicAttribute magicAttribute) throws SQLException;
    void removeByMagicId(Integer magicId) throws SQLException;
    List<MagicAttribute> findByMagicId(Integer magicId) throws SQLException;
}
