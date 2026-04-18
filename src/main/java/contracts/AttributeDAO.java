package contracts;

import model.Attribute;

import java.sql.SQLException;
import java.util.List;

public interface AttributeDAO {
    void insert(Attribute newAttribute) throws SQLException;
    void update(Attribute attribute) throws SQLException;
    void remove(Integer qualityId) throws SQLException;
    Attribute findById(Integer qualityId) throws SQLException;
    List<Attribute> listAll() throws SQLException;
}
