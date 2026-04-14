package service;

import dao.contracts.MagicDAO;
import factory.DaoFactory;
import model.Magic;
import util.Option;

import java.sql.SQLException;
import java.util.Scanner;

public class MagicService extends MenuService {
    private final MagicDAO magicDAO;

    public MagicService() throws SQLException {
        this.magicDAO = DaoFactory.getMagicDAO();
        this.menuTitle = "GERENCIAR MAGIAS";
        this.menuOptions.add(new Option(1, "INCLUIR MAGIA", this::create));
        this.menuOptions.add(new Option(2, "REMOVER MAGIA", this::remove));
    }

    private Boolean create() {
        try {
            Magic newMagic = instantiateMagic();
            magicDAO.insert(newMagic);
        } catch (Exception err) {
            System.out.println("Erro ao criar nova magia!");
        }
        return true;
    }

    private Magic instantiateMagic() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID da magia: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o nome da magia: ");
        String name = scanner.nextLine();

        System.out.print("Digite a descrição: ");
        String description = scanner.nextLine();

        System.out.print("Custo de mana: ");
        Integer manaCost = scanner.nextInt();

        System.out.print("Nível mínimo necessário: ");
        Integer minLevel = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite os dados (ex: 2d6, 1d20): ");
        String dices = scanner.nextLine();

        return new Magic(id, name, description, manaCost, minLevel, dices);
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id da magia: ");
            Integer magicId = scanner.nextInt();
            magicDAO.remove(magicId);
        } catch (Exception err) {
            System.out.println("Erro ao remover magia!");
        }
        return true;
    }
}
