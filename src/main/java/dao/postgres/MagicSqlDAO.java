package dao.postgres;

import contracts.MagicDAO;
import model.Magic;
import model.relationship.MagicAttribute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MagicSqlDAO implements MagicDAO {

    private final Connection connection;
    private final MagicAttributeSqlDAO magicAttributeDAO;

    public MagicSqlDAO(Connection connection) {
        this.connection = connection;
        this.magicAttributeDAO = new MagicAttributeSqlDAO(connection);
    }

    @Override
    public void insert(Magic newMagic) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO magias(nome_magia, descricao, custo_mana, nivel_minimo, dados) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, newMagic.getName());
            st.setString(2, newMagic.getDescription());
            st.setInt(3, newMagic.getManaCost());
            st.setInt(4, newMagic.getMinLevel());
            st.setString(5, newMagic.getDices());
            st.executeUpdate();

            ResultSet generatedKeys = st.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new SQLException("Nao foi possivel obter o ID da magia criada.");
            }

            Integer magicId = generatedKeys.getInt(1);
            persistAttributes(magicId, newMagic.getAttributes());
            st.close();
            connection.commit();
        } catch (SQLException err) {
            connection.rollback();
            throw err;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void update(Magic magic) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            PreparedStatement st = connection.prepareStatement(
                    "UPDATE magias SET nome_magia = ?, descricao = ?, custo_mana = ?, nivel_minimo = ?, dados = ? WHERE id_magia = ?"
            );
            st.setString(1, magic.getName());
            st.setString(2, magic.getDescription());
            st.setInt(3, magic.getManaCost());
            st.setInt(4, magic.getMinLevel());
            st.setString(5, magic.getDices());
            st.setInt(6, magic.getId());
            st.executeUpdate();
            st.close();

            magicAttributeDAO.removeByMagicId(magic.getId());
            persistAttributes(magic.getId(), magic.getAttributes());
            connection.commit();
        } catch (SQLException err) {
            connection.rollback();
            throw err;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void remove(Integer magicId) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            magicAttributeDAO.removeByMagicId(magicId);
            PreparedStatement st = connection.prepareStatement("DELETE FROM magias WHERE id_magia = ?");
            st.setInt(1, magicId);
            st.execute();
            st.close();
            connection.commit();
        } catch (SQLException err) {
            connection.rollback();
            throw err;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public Magic findById(Integer magicId) throws SQLException {
        List<Magic> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("SELECT * FROM magias WHERE id_magia = ?");
        st.setInt(1, magicId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            Magic magic = Magic.fromResultSet(result);
            magic.setAttributes(magicAttributeDAO.findByMagicId(magic.getId()));
            list.add(magic);
        }
        st.close();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Magic> listAll() throws SQLException {
        List<Magic> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM magias");
        while (result.next()) {
            Magic magic = Magic.fromResultSet(result);
            magic.setAttributes(magicAttributeDAO.findByMagicId(magic.getId()));
            list.add(magic);
        }
        st.close();
        return list;
    }

    private void persistAttributes(Integer magicId, List<MagicAttribute> attributes) throws SQLException {
        if (attributes == null) {
            return;
        }
        for (MagicAttribute attribute : attributes) {
            magicAttributeDAO.insert(new MagicAttribute(
                    magicId,
                    attribute.getAttributeId(),
                    attribute.getValue(),
                    attribute.getAttribute()
            ));
        }
    }
}
