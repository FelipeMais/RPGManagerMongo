package service;

import contracts.AbilityDAO;
import factory.DaoFactory;
import model.Ability;
import util.Colors;
import util.Option;
import util.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AbilityService extends MenuService {
    private final AbilityDAO abilityDAO;

    public AbilityService() throws SQLException {
        this.abilityDAO = DaoFactory.getAbilityDAO();
        this.menuTitle = "GERENCIAR HABILIDADES";
        this.menuOptions.add(new Option(1, "INCLUIR HABILIDADE", this::create));
        this.menuOptions.add(new Option(2, "ATUALIZAR HABILIDADE", this::update));
        this.menuOptions.add(new Option(3, "REMOVER HABILIDADE", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR HABILIDADE", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR HABILIDADES", this::listAll));
    }

    private Boolean create() {
        try {
            Ability newAbility = instantiateAbility(false);
            abilityDAO.insert(newAbility);
        } catch (Exception err) {
            System.out.println("Erro ao criar nova habilidade!");
        }
        return true;
    }

    private Boolean update() {
        try {
            Ability updatedAbility = instantiateAbility(true);
            abilityDAO.update(updatedAbility);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar habilidade!");
        }
        return true;
    }

    private Ability instantiateAbility(Boolean askId) {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        if (askId) {
            System.out.print("Digite o ID da habilidade: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print("Digite o nome da habilidade: ");
        String name = scanner.nextLine();

        System.out.print("Digite a descricao da habilidade: ");
        String description = scanner.nextLine();

        System.out.print("Digite o atributo base da habilidade: ");
        String baseAttribute = scanner.nextLine();

        if (askId) {
            return new Ability(id, name, description, baseAttribute);
        }
        return new Ability(name, description, baseAttribute);
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id da habilidade: ");
            Integer abilityId = scanner.nextInt();
            abilityDAO.remove(abilityId);
        } catch (Exception err) {
            System.out.println("Erro ao remover habilidade!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id da habilidade: ");
            Integer abilityId = scanner.nextInt();
            Ability ability = abilityDAO.findById(abilityId);
            if (ability == null) {
                System.out.println("Habilidade nao encontrada");
                return false;
            }
            detail(ability);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar habilidade!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<Ability> abilities = abilityDAO.listAll();
            print(abilities);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar habilidades!");
        }
        return true;
    }

    private void print(List<Ability> abilities) {
        String[] headers = {"ID", "NOME", "ATRIBUTO BASE", "DESCRICAO"};
        int[] widths = {4, 24, 14, 70};
        List<String[]> rows = new ArrayList<>();

        for (Ability ability : abilities) {
            rows.add(new String[]{
                    String.valueOf(ability.getId()),
                    ability.getName(),
                    ability.getBaseAttribute(),
                    ability.getDescription()
            });
        }

        UI.printTable(headers, widths, rows);
    }

    private void detail(Ability ability) { // Replace T with the specific model
        int width = 45;
        System.out.println("\n" + Colors.CYAN + "╔" + "═".repeat(width + 2) + "╗");
        UI.printRow(Colors.BOLD + ability.getName().toUpperCase(), width);
        if (ability.getDescription() != null) {
            System.out.println(Colors.CYAN + "╟" + "─".repeat(width + 2) + "╢");
            UI.printRow(Colors.GRAY + ability.getDescription(), width);
        }
        System.out.println(Colors.CYAN + "╚" + "═".repeat(width + 2) + "╝\n"+ Colors.RESET);
    }
}
