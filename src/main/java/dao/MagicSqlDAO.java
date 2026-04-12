package dao;

import dao.contracts.MagicDAO;
import model.Magic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public void remove() {

    }

    @Override
    public Magic findById() {
        return null;
    }

    @Override
    public List<Magic> listAll() {
        return List.of();
    }
}
