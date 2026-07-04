package dao.postgres;

import contracts.RpgClassDAO;
import model.RpgClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RpgClassSqlDAO implements RpgClassDAO {

    private final Connection connection;

    public RpgClassSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(RpgClass newRpgClass) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO classe(nome_classe, descricao) VALUES (?, ?)"
        );
        st.setString(1, newRpgClass.getClassName());
        st.setString(2, newRpgClass.getDescription());
        st.execute();
        st.close();
    }

    @Override
    public void update(RpgClass rpgClass) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE classe SET nome_classe = ?, descricao = ? WHERE id_classe = ?"
        );
        st.setString(1, rpgClass.getClassName());
        st.setString(2, rpgClass.getDescription());
        st.setInt(3, rpgClass.getIdClass());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer rpgClassId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM classe WHERE id_classe = ?");
        st.setInt(1, rpgClassId);
        st.execute();
        st.close();
    }

    @Override
    public RpgClass findById(Integer rpgClassId) throws SQLException {
        List<RpgClass> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("SELECT * FROM classe WHERE id_classe = ?");
        st.setInt(1, rpgClassId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            list.add(RpgClass.fromResultSet(result));
        }
        st.close();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<RpgClass> listAll() throws SQLException {
        List<RpgClass> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM classe");
        while (result.next()) {
            list.add(RpgClass.fromResultSet(result));
        }
        st.close();
        return list;
    }
}
