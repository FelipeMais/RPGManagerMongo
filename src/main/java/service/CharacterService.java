package service;

import contracts.CharacterDAO;
import factory.DaoFactory;
import model.Character;
import model.Magic;
import model.relationship.MagicAttribute;
import util.Option;
import util.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CharacterService extends MenuService{
    private final CharacterDAO characterDAO;

    public CharacterService() throws SQLException {
        this.characterDAO = DaoFactory.getCharacterDAO();
        this.menuTitle = "GERENCIAR PERSONAGENS";
        this.menuOptions.add(new Option(1, "INCLUIR PERSONAGEM", this::create));
        this.menuOptions.add(new Option(2, "ALTERAR PERSONAGEM", this::update));
        this.menuOptions.add(new Option(3, "REMOVER PERSONAGEM", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR PERSONAGEM", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR PERSONAGEM", this::listAll));
        this.menuOptions.add(new Option(5, "GERENCIAR FICHA", this::executarGerenciamentoFicha));
    }

    private Boolean create() {
        try {
            Character newMagic = instantiateCharacter(false);
            characterDAO.insert(newMagic);
        } catch (Exception err) {
            System.out.println("Erro ao criar nova magia!");
        }
        return true;
    }

    private Boolean update() {
        try {
            Character updatedMagic = instantiateCharacter(true);
            characterDAO.update(updatedMagic);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar magia!");
        }
        return true;
    }

    private Character instantiateCharacter(Boolean askId) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        if (askId) {
            System.out.print("Digite o ID do personagem: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }



        if (askId) {
            return null;
        }
        return null;
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do personagem: ");
            Integer magicId = scanner.nextInt();
            characterDAO.remove(magicId);
        } catch (Exception err) {
            System.out.println("Erro ao remover personagem!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do personagem: ");
            Integer magicId = scanner.nextInt();
            Character character = characterDAO.findById(magicId);
            if (character == null) {
                System.out.println("Magia nao encontrada");
                return false;
            }
            print(Collections.singletonList(character));
        } catch (Exception err) {
            System.out.println("Erro ao buscar persongem!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<Character> characterList = characterDAO.listAll();
            print(characterList);
        } catch (Exception err) {
            System.out.println("Erro ao buscar personagem!");
        }
        return true;
    }

    private Boolean executarGerenciamentoFicha() {
        return true;
    }

    private void print(List<Character> magicList) {
        String[] headers = {"ID", "NOME", "MANA", "NIVEL", "DADOS", "ATRIBUTOS", "DESCRICAO"};
        int[] widths = {4, 18, 4, 5, 8, 50, 50};
        List<String[]> rows = new ArrayList<>();

        for (Character magic : magicList) {
            rows.add(new String[]{

            });
        }

        UI.printTable(headers, widths, rows);
    }
}
