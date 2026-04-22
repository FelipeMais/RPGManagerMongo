package service;

import contracts.PlayerDAO;
import factory.DaoFactory;
import model.Player;
import util.Colors;
import util.Option;
import util.UI;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
            Player newPlayer = instantiatePlayer(false);
            playerDAO.insert(newPlayer);
        } catch (Exception err) {
            System.out.println("Erro ao criar novo jogador!");
        }
        return true;
    }

    private Boolean update() {
        try {
            Player updatedPlayer = instantiatePlayer(true);
            playerDAO.update(updatedPlayer);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar jogador!");
        }
        return true;
    }

    private Player instantiatePlayer(Boolean askId) {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        if (askId) {
            System.out.print("Digite o ID do jogador: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print("Digite o nome do jogador: ");
        String name = scanner.nextLine();

        System.out.print("Digite a data de entrada (yyyy-mm-dd hh:mm:ss): ");
        String entryDate = scanner.nextLine();

        System.out.print("Jogador ativo? (true/false): ");
        Boolean active = scanner.nextBoolean();

        if (askId) {
            return new Player(id, name, Timestamp.valueOf(entryDate), active);
        }
        return new Player(name, Timestamp.valueOf(entryDate), active);
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
            detail(player);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar jogador!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<Player> players = playerDAO.listAll();
            print(players);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar jogadores!");
        }
        return true;
    }

    private void print(List<Player> players) {
        String[] headers = {"ID", "NOME", "ENTRADA", "ATIVO"};
        int[] widths = {4, 24, 30, 5};
        List<String[]> rows = new ArrayList<>();

        for (Player player : players) {
            rows.add(new String[]{
                    String.valueOf(player.getId()),
                    player.getName(),
                    String.valueOf(player.getEntryDate()),
                    String.valueOf(player.getActive())
            });
        }

        UI.printTable(headers, widths, rows);
    }

    private void detail(Player player) {
        int width = 45;
        System.out.println("\n" + Colors.CYAN + "╔" + "═".repeat(width + 2) + "╗");
        UI.printRow(Colors.BOLD + player.getName().toUpperCase() +
        (player.getActive() ? Colors.GREEN + " ATIVO" : Colors.RED + " INATIVO"), width);
        System.out.println(Colors.CYAN + "╟" + "─".repeat(width + 2) + "╢");
        UI.printRow(Colors.GRAY + player.getEntryDate().toString(), width);
        System.out.println(Colors.CYAN + "╚" + "═".repeat(width + 2) + "╝\n"+ Colors.RESET);
    }
}
