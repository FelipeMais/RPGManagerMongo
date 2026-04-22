package dao;

import contracts.CharacterSheetSkillDAO;
import model.Skill;
import model.relationship.CharacterSheetSkill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacterSheetSkillSqlDAO implements CharacterSheetSkillDAO {
    private final Connection connection;

    public CharacterSheetSkillSqlDAO(Connection connection) {this.connection = connection;}

    @Override
    public void insert(CharacterSheetSkill characterSheetSkill) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO fichahabilidades(id_habilidade, id_ficha, valor) VALUES (?, ?, ?)"
        );
        st.setInt(1, characterSheetSkill.getSheetId());
        st.setInt(2, characterSheetSkill.getSkillId());
        st.setInt(3, characterSheetSkill.getValue());
        st.execute();
        st.close();
    }

    @Override
    public void removeBySheetId(Integer sheetId) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "DELETE FROM fichahabilidades WHERE id_ficha = ?"
        );
        st.setInt(1, sheetId);
        st.execute();
        st.close();
    }

    @Override
    public List<CharacterSheetSkill> findBySheetId(Integer sheetId) throws SQLException {
        List<CharacterSheetSkill> skills = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement(
                "SELECT fh.id_ficha, fh.id_habilidade, fh.valor, h.nome_habilidade " +
                        "h.descr_habilidade, h.atributo_base" +
                        "FROM fichahabilidades fh " +
                        "INNER JOIN habilidades h ON h.id_habilidade = fh.id_habilidade " +
                        "WHERE fh.id_ficha = ? ORDER BY fh.id_habilidade"
        );
        st.setInt(1, sheetId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            Skill skill = new Skill(
                    result.getInt(2),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6)
            );
            skills.add(new CharacterSheetSkill(
                    result.getInt(1),
                    result.getInt(2),
                    result.getInt(3),
                    skill
            ));
        }
        st.close();
        return skills;
    }
}
