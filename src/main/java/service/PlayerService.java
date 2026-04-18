package service;

import contracts.PlayerDAO;
import factory.DaoFactory;
import model.Player;
import util.Option;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PlayerService extends MenuService {
    private final PlayerDAO playerDAO;

    public PlayerService() throws SQLException {
        this.playerDAO = DaoFactory.getPlayerDAO();
        this.menuTitle = "GERENCIAR JOGADORES";
        this.menuOptions.add(new Option(1, "INCLUIR JOGADOR", this::create));
        this.menuOptions.add(new Option(2, "ATUALIZAR JOGADOR", this::update));
        this.menuOptions.add(new Option(3, "REMOVER JOGADOR", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR JOGADOR", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR JOGADORES", this::listAll));
    }

    private Boolean create() {
        try {
            Player newPlayer = instantiatePlayer();
            playerDAO.insert(newPlayer);
        } catch (Exception err) {
            System.out.println("Erro ao criar novo jogador!");
        }
        return true;
    }

    private Boolean update() {
        try {
            Player updatedPlayer = instantiatePlayer();
            playerDAO.update(updatedPlayer);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar jogador!");
        }
        return true;
    }

    private Player instantiatePlayer() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID do jogador: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o nome do jogador: ");
        String name = scanner.nextLine();

        System.out.print("Digite a data de entrada (yyyy-mm-dd hh:mm:ss): ");
        String entryDate = scanner.nextLine();

        System.out.print("Jogador ativo? (true/false): ");
        Boolean active = scanner.nextBoolean();

        return new Player(id, name, Timestamp.valueOf(entryDate), active);
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do jogador: ");
            Integer playerId = scanner.nextInt();
            playerDAO.remove(playerId);
        } catch (Exception err) {
            System.out.println("Erro ao remover jogador!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do jogador: ");
            Integer playerId = scanner.nextInt();
            Player player = playerDAO.findById(playerId);
            if (player == null) {
                System.out.println("Jogador nao encontrado");
                return false;
            }
            print(Collections.singletonList(player));
        } catch (Exception err) {
            System.out.println("Erro ao buscar jogador!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<Player> players = playerDAO.listAll();
            print(players);
        } catch (Exception err) {
            System.out.println("Erro ao buscar jogadores!");
        }
        return true;
    }

    private void print(List<Player> players) {
        System.out.print("ID | ");
        System.out.print("NOME | ");
        System.out.print("DATA ENTRADA | ");
        System.out.print("ATIVO | ");
        System.out.print("\n");
        for (Player player : players) {
            System.out.print(player.getId() + pipe());
            System.out.print(player.getName() + pipe());
            System.out.print(player.getEntryDate() + pipe());
            System.out.print(player.getActive() + pipe());
            System.out.print("\n");
        }
    }

    private String pipe() {
        return " | ";
    }
}
