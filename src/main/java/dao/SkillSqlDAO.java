package dao;

import contracts.SkillDAO;
import model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillSqlDAO implements SkillDAO {
    private final Connection connection;

    public SkillSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Skill newSkill) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO habilidades(nome_habilidade, descr_habilidade, atributo_base) VALUES (?)"
        );
        st.setString(1, newSkill.getName());
        st.execute();
        st.close();
    }

    @Override
    public void update(Skill skill) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "UPDATE habilidades SET nome_habilidade, descr_habilidade, atributo_base = ? WHERE id_ficha = ?"
        );
        st.setString(1, skill.getName());
        st.setInt(2, skill.getId());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void remove(Integer skillId) throws SQLException {
        PreparedStatement st = connection.prepareStatement("DELETE FROM habilidades WHERE id_ficha = ?");
        st.setInt(1, skillId);
        st.execute();
        st.close();
    }

    @Override
    public Skill findById(Integer skillId) throws SQLException {
        List<Skill> list = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("SELECT * FROM habilidades WHERE id_ficha = ?");
        st.setInt(1, skillId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            list.add(Skill.fromResultSet(result));
        }
        st.close();
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    @Override
    public List<Skill> listAll() throws SQLException {
        List<Skill> list = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet result = st.executeQuery("SELECT * FROM habilidades");
        while (result.next()) {
            list.add(Skill.fromResultSet(result));
        }
        st.close();
        return list;
    }
}
