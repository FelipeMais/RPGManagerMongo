package dao;

import contracts.MagicDAO;
import model.Magic;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MagicSqlDAO implements MagicDAO {

    private Connection connection;
    public MagicSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Magic newMagic) throws SQLException {
        PreparedStatement st;
        st = connection.prepareStatement("INSERT INTO magias(id_magia, nome_magia, descricao, custo_mana, nivel_minimo, dados) VALUES (?, ?, ?, ?, ?, ?)");
        st.setInt(1, newMagic.getId());
        st.setString(2, newMagic.getName());
        st.setString(3, newMagic.getDescription());
        st.setInt(4, newMagic.getManaCost());
        st.setInt(5, newMagic.getMinLevel());
        st.setString(6, newMagic.getDices());
        st.execute();
        st.close();
    }

    @Override
    public void remove(Integer magicId) throws SQLException {
        PreparedStatement st;
        st = connection.prepareStatement("DELETE FROM magias WHERE id_magia = ?");
        st.setInt(1, magicId);
        st.execute();
        st.close();
    }

    @Override
    public Magic findById(Integer magicId) throws SQLException {
        List<Magic> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("SELECT * FROM magias WHERE id_magia = ?");
        st.setInt(1, magicId);
        ResultSet result = st.executeQuery();
        while(result.next()) {
            list.add(Magic.fromResultSet(result));
        }
        st.close();
        if(!list.isEmpty()){
            return list.getFirst();
        }
        return null;

    }

    @Override
    public List<Magic> listAll() throws SQLException {
        List<Magic> list = new ArrayList<>();
        Statement st = connection.createStatement();
        String sql = "SELECT * FROM magias";
        ResultSet result = st.executeQuery(sql);
        while(result.next()) {
            list.add(Magic.fromResultSet(result));
        }
        return list;
    }
}
