package service;

import contracts.CombatActionTypeDAO;
import factory.DaoFactory;
import model.CombatActionType;
import util.Option;
import util.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CombatActionTypeService extends MenuService {
    private final CombatActionTypeDAO combatActionTypeDAO;

    public CombatActionTypeService() throws SQLException {
        this.combatActionTypeDAO = DaoFactory.getCombatActionTypeDAO();
        this.menuTitle = "GERENCIAR TIPOS DE ACAO DE COMBATE";
        this.menuOptions.add(new Option(1, "INCLUIR TIPO DE ACAO", this::create));
        this.menuOptions.add(new Option(2, "ATUALIZAR TIPO DE ACAO", this::update));
        this.menuOptions.add(new Option(3, "REMOVER TIPO DE ACAO", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR TIPO DE ACAO", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR TIPOS DE ACAO", this::listAll));
    }

    private Boolean create() {
        try {
            CombatActionType combatActionType = instantiateCombatActionType(false);
            combatActionTypeDAO.insert(combatActionType);
        } catch (Exception err) {
            System.out.println("Erro ao criar tipo de acao de combate!");
        }
        return true;
    }

    private Boolean update() {
        try {
            CombatActionType combatActionType = instantiateCombatActionType(true);
            combatActionTypeDAO.update(combatActionType);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar tipo de acao de combate!");
        }
        return true;
    }

    private CombatActionType instantiateCombatActionType(Boolean askId) {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        if (askId) {
            System.out.print("Digite o ID do tipo de acao de combate: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print("Digite o nome do tipo de acao de combate: ");
        String name = scanner.nextLine();

        if (askId) {
            return new CombatActionType(id, name);
        }
        return new CombatActionType(name);
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do tipo de acao de combate: ");
            Integer combatActionTypeId = scanner.nextInt();
            combatActionTypeDAO.remove(combatActionTypeId);
        } catch (Exception err) {
            System.out.println("Erro ao remover tipo de acao de combate!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do tipo de acao de combate: ");
            Integer combatActionTypeId = scanner.nextInt();
            CombatActionType combatActionType = combatActionTypeDAO.findById(combatActionTypeId);
            if (combatActionType == null) {
                System.out.println("Tipo de acao de combate nao encontrado");
                return true;
            }
            print(Collections.singletonList(combatActionType));
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar tipo de acao de combate!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<CombatActionType> combatActionTypes = combatActionTypeDAO.listAll();
            print(combatActionTypes);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar tipos de acao de combate!");
        }
        return true;
    }

    private void print(List<CombatActionType> combatActionTypes) {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 32};
        List<String[]> rows = new ArrayList<>();

        for (CombatActionType combatActionType : combatActionTypes) {
            rows.add(new String[]{
                    String.valueOf(combatActionType.getId()),
                    combatActionType.getName()
            });
        }

        UI.printTable(headers, widths, rows);
    }
}
