package contracts;

import model.Attribute;

import java.sql.SQLException;
import java.util.List;

public interface AttributeDAO {
    void insert(Attribute newAttribute) throws SQLException;
    void update(Attribute attribute) throws SQLException;
    void remove(Integer attributeId) throws SQLException;
    Attribute findById(Integer attributeId) throws SQLException;
    List<Attribute> listAll() throws SQLException;
}
