package service;

import contracts.SkillDAO;
import factory.DaoFactory;
import model.Skill;
import util.Option;
import util.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SkillService extends MenuService{
    private final SkillDAO skillDAO;

    public SkillService() throws SQLException {
        this.skillDAO = DaoFactory.getSkillDAO();
        this.menuTitle = "GERENCIAR HABILIDADES";
        this.menuOptions.add(new Option(1, "INCLUIR HABILIDADE", this::create));
        this.menuOptions.add(new Option(2, "ATUALIZAR HABILIDADE", this::update));
        this.menuOptions.add(new Option(3, "REMOVER HABILIDADE", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR HABILIDADE", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR HABILIDADES", this::listAll));
    }

    private Boolean create() {
        try {
            Skill newSkill = instantiateSkill(false);
            skillDAO.insert(newSkill);
        } catch (Exception err) {
            System.out.println("Erro ao criar nova habilidade!");
        }
        return true;
    }

    private Boolean update() {
        try {
            Skill updatedSkill = instantiateSkill(true);
            skillDAO.update(updatedSkill);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar habilidade!");
        }
        return true;
    }

    private Skill instantiateSkill(Boolean askId) {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        if (askId) {
            System.out.print("Digite o ID da habilidade: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print("Digite o nome da habilidade: ");
        String name = scanner.nextLine();

        System.out.print("Digite a descrição da habilidade: ");
        String descr = scanner.nextLine();

        System.out.print("Digite o atributo base da habilidade: ");
        String baseAttribute = scanner.nextLine();

        if (askId) {
            return new Skill(id, name, descr, baseAttribute);
        }
        return new Skill(name, descr, baseAttribute);
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id da habilidade: ");
            Integer skillId = scanner.nextInt();
            skillDAO.remove(skillId);
        } catch (Exception err) {
            System.out.println("Erro ao remover habilidade!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id da habilidade: ");
            Integer skillId = scanner.nextInt();
            Skill skill = findSkillById(skillId);
            if (skill == null) {
                System.out.println("Habilidade nâo encontrada");
                return false;
            }
            print(Collections.singletonList(skill));
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar habilidade!");
        }
        return true;
    }

    public Skill findSkillById(Integer skillId) throws SQLException {
        return skillDAO.findById(skillId);
    }


    private Boolean listAll() {
        try {
            List<Skill> skills = skillDAO.listAll();
            print(skills);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar habilidades!");
        }
        return true;
    }

    private void print(List<Skill> skills) {
        String[] headers = {"ID", "NOME", "DESCRIÇÃO", "ATRIBUTO BASE"};
        int[] widths = {4, 30, 120, 30};
        List<String[]> rows = new ArrayList<>();

        for (Skill skill : skills) {
            rows.add(new String[]{
                    String.valueOf(skill.getId()),
                    skill.getName()
            });
        }

        UI.printTable(headers, widths, rows);
    }
}
