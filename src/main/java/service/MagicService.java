package service;

import contracts.MagicDAO;
import factory.DaoFactory;
import model.Magic;
import util.Option;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MagicService extends MenuService {
    private final MagicDAO magicDAO;

    public MagicService() throws SQLException {
        this.magicDAO = DaoFactory.getMagicDAO();
        this.menuTitle = "GERENCIAR MAGIAS";
        this.menuOptions.add(new Option(1, "INCLUIR MAGIA", this::create));
        this.menuOptions.add(new Option(2, "REMOVER MAGIA", this::remove));
        this.menuOptions.add(new Option(3, "BUSCAR MAGIA", this::findById));
        this.menuOptions.add(new Option(4, "LISTAR MAGIAS", this::listAll));
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

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id da magia: ");
            Integer magicId = scanner.nextInt();
            Magic magia = magicDAO.findById(magicId);
            if(magia == null) {
                System.out.println("NAO ACHOU NADA");
                return false;
            }
            print(Collections.singletonList(magia));
        } catch (Exception err) {
            System.out.println("Erro ao buscar magia!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<Magic> magicList = magicDAO.listAll();
            print(magicList);
        } catch (Exception err) {
            System.out.println("Erro ao buscar magia!");
        }
        return true;
    }

    private void print(List<Magic> magicList) {
        System.out.print("ID | ");
        System.out.print("NOME | ");
        System.out.print("DESCRICAO | ");
        System.out.print("CUSTO DE MANA | ");
        System.out.print("NIVEL MINIMO | ");
        System.out.print("DADOS | ");
        System.out.print("\n");
        for(Magic magic : magicList) {
            System.out.print(magic.getId()+pipe());
            System.out.print(magic.getName()+pipe());
            System.out.print(magic.getDescription()+pipe());
            System.out.print(magic.getManaCost()+pipe());
            System.out.print(magic.getMinLevel()+pipe());
            System.out.print(magic.getDices()+pipe());
            System.out.print("\n");
        }
    }

    private String pipe(){
        return " | ";
    }
}
