package service;

import contracts.RpgClassDAO;
import factory.DaoFactory;
import model.RpgClass;
import util.Option;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class RpgClassService extends MenuService {
    private final RpgClassDAO rpgClassDAO;

    public RpgClassService() throws SQLException {
        this.rpgClassDAO = DaoFactory.getRpgClassDAO();
        this.menuTitle = "GERENCIAR CLASSES";
        this.menuOptions.add(new Option(1, "INCLUIR CLASSE", this::create));
        this.menuOptions.add(new Option(2, "ATUALIZAR CLASSE", this::update));
        this.menuOptions.add(new Option(3, "REMOVER CLASSE", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR CLASSE", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR CLASSES", this::listAll));
    }

    private Boolean create() {
        try {
            RpgClass newRpgClass = instantiateRpgClass(false);
            rpgClassDAO.insert(newRpgClass);
        } catch (Exception err) {
            System.out.println("Erro ao criar nova classe!");
        }
        return true;
    }

    private Boolean update() {
        try {
            RpgClass updatedRpgClass = instantiateRpgClass(true);
            rpgClassDAO.update(updatedRpgClass);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar classe!");
        }
        return true;
    }

    private RpgClass instantiateRpgClass(Boolean askId) {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        if (askId) {
            System.out.print("Digite o ID da classe: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print("Digite o nome da classe: ");
        String name = scanner.nextLine();

        System.out.print("Digite a descricao: ");
        String description = scanner.nextLine();

        if (askId) {
            return new RpgClass(id, name, description);
        }
        return new RpgClass(name, description);
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id da classe: ");
            Integer rpgClassId = scanner.nextInt();
            rpgClassDAO.remove(rpgClassId);
        } catch (Exception err) {
            System.out.println("Erro ao remover classe!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id da classe: ");
            Integer rpgClassId = scanner.nextInt();
            RpgClass rpgClass = rpgClassDAO.findById(rpgClassId);
            if (rpgClass == null) {
                System.out.println("Classe nao encontrada");
                return false;
            }
            print(Collections.singletonList(rpgClass));
        } catch (Exception err) {
            System.out.println("Erro ao buscar classe!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<RpgClass> rpgClasses = rpgClassDAO.listAll();
            print(rpgClasses);
        } catch (Exception err) {
            System.out.println("Erro ao buscar classes!");
        }
        return true;
    }

    private void print(List<RpgClass> rpgClasses) {
        System.out.print("ID | ");
        System.out.print("NOME | ");
        System.out.print("DESCRICAO | ");
        System.out.print("\n");
        for (RpgClass rpgClass : rpgClasses) {
            System.out.print(rpgClass.getIdClass() + pipe());
            System.out.print(rpgClass.getClassName() + pipe());
            System.out.print(rpgClass.getDescription() + pipe());
            System.out.print("\n");
        }
    }

    private String pipe() {
        return " | ";
    }
}
