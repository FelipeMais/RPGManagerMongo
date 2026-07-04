package dao.postgres;

import contracts.MagicAttributeDAO;
import model.Attribute;
import model.relationship.MagicAttribute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MagicAttributeSqlDAO implements MagicAttributeDAO {

    private final Connection connection;

    public MagicAttributeSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(MagicAttribute magicAttribute) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO magiacaracteristicas(id_qualidade, id_magia, valor) VALUES (?, ?, ?)"
        );
        st.setInt(1, magicAttribute.getAttributeId());
        st.setInt(2, magicAttribute.getMagicId());
        st.setInt(3, magicAttribute.getValue());
        st.execute();
        st.close();
    }

    @Override
    public void removeByMagicId(Integer magicId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "DELETE FROM magiacaracteristicas WHERE id_magia = ?"
        );
        st.setInt(1, magicId);
        st.execute();
        st.close();
    }

    @Override
    public List<MagicAttribute> findByMagicId(Integer magicId) throws SQLException {
        List<MagicAttribute> attributes = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT mc.id_magia, mc.id_qualidade, mc.valor, q.nome_qualidade " +
                        "FROM magiacaracteristicas mc " +
                        "INNER JOIN qualidades q ON q.id_qualidade = mc.id_qualidade " +
                        "WHERE mc.id_magia = ? ORDER BY mc.id_qualidade"
        );
        st.setInt(1, magicId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            Attribute attribute = new Attribute(result.getInt(2), result.getString(4));
            attributes.add(new MagicAttribute(
                    result.getInt(1),
                    result.getInt(2),
                    result.getInt(3),
                    attribute
            ));
        }
        st.close();
        return attributes;
    }
}
