package service;

import contracts.CharacterSheetDAO;
import contracts.CharacterDAO;
import contracts.LocationDAO;
import contracts.PlayerDAO;
import contracts.RpgClassDAO;
import contracts.SpeciesDAO;
import factory.DaoFactory;
import model.Character;
import model.CharacterSheet;
import model.Location;
import model.Player;
import model.RpgClass;
import model.Species;
import util.Option;
import util.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static util.Colors.*;

public class CharacterService extends MenuService {
    private final CharacterDAO characterDAO;
    private final CharacterSheetDAO characterSheetDAO;
    private final PlayerDAO playerDAO;
    private final LocationDAO locationDAO;
    private final RpgClassDAO rpgClassDAO;
    private final SpeciesDAO speciesDAO;
    private final CharacterSheetService characterSheetService;

    public CharacterService() throws SQLException {
        this.characterDAO = DaoFactory.getCharacterDAO();
        this.characterSheetDAO = DaoFactory.getCharacterSheetDAO();
        this.playerDAO = DaoFactory.getPlayerDAO();
        this.locationDAO = DaoFactory.getLocationDAO();
        this.rpgClassDAO = DaoFactory.getRpgClassDAO();
        this.speciesDAO = DaoFactory.getSpeciesDAO();
        this.characterSheetService = new CharacterSheetService();
        this.menuTitle = "GERENCIAR PERSONAGENS";
        this.menuOptions.add(new Option(1, "INCLUIR PERSONAGEM", this::create));
        this.menuOptions.add(new Option(2, "ATUALIZAR PERSONAGEM", this::update));
        this.menuOptions.add(new Option(3, "REMOVER PERSONAGEM", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR PERSONAGEM", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR PERSONAGENS", this::listAll));
        this.menuOptions.add(new Option(6, "GERENCIAR FICHAS", this::manageCharacterSheets));
    }

    private Boolean create() {
        try {
            Character newCharacter = instantiateCharacter(false);
            characterDAO.insert(newCharacter);
        } catch (Exception err) {
            System.out.println("Erro ao criar novo personagem!");
        }
        return true;
    }

    private Boolean update() {
        try {
            Character updatedCharacter = instantiateCharacter(true);
            characterDAO.update(updatedCharacter);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar personagem!");
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

        Integer playerId = readExistingPlayerId(scanner);

        Integer sheetId;
        if (askId) {
            sheetId = readExistingSheetId(scanner);
        } else {
            sheetId = resolveSheetIdForCreation(scanner);
        }

        Integer currentLocationId = readExistingLocationId(scanner);

        System.out.print("Digite o nome do personagem: ");
        String name = scanner.nextLine();

        System.out.print("Pontos de vida atuais: ");
        Integer hitPoints = scanner.nextInt();

        System.out.print("Pontos de mana atuais: ");
        Integer manaPoints = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite a historia do personagem (vazio para nenhuma): ");
        String history = normalizeNullableText(scanner.nextLine());

        if (askId) {
            return new Character(id, playerId, sheetId, currentLocationId, name, hitPoints, manaPoints, history);
        }
        return new Character(playerId, sheetId, currentLocationId, name, hitPoints, manaPoints, history);
    }

    private Integer resolveSheetIdForCreation(Scanner scanner) throws SQLException {
        while (true) {
            System.out.println("[1]: VINCULAR FICHA EXISTENTE");
            System.out.println("[2]: CRIAR NOVA FICHA");
            System.out.print("Escolha uma opcao para a ficha: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                Integer sheetId = readExistingSheetIdRequired(scanner);
                if (sheetId != null) {
                    return sheetId;
                }
            }

            if (option == 2) {
                return characterSheetService.createCharacterSheetInteractively();
            }

            System.out.println("Opcao invalida.");
        }
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do personagem: ");
            Integer characterId = scanner.nextInt();
            characterDAO.remove(characterId);
        } catch (Exception err) {
            System.out.println("Erro ao remover personagem!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do personagem: ");
            Integer characterId = scanner.nextInt();
            Character character = characterDAO.findById(characterId);
            if (character == null) {
                System.out.println("Personagem nao encontrado");
                return false;
            }
            detail(character);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar personagem!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<Character> characterList = characterDAO.listAll();
            print(characterList);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar personagens!");
        }
        return true;
    }

    private Boolean manageCharacterSheets() {
        try {
            while (characterSheetService.execute()) { }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar fichas", e);
        }
        return true;
    }

    private void print(List<Character> characterList) {
        String[] headers = {"ID", "NOME", "JOGADOR", "LOCAL", "CLASSE", "ESPECIE", "NIVEL", "PV", "PM", "PV MAX", "PM MAX", "FOR", "DES", "CON", "INT", "SAB", "CAR", "HISTORIA"};
        int[] widths = {4, 18, 16, 16, 14, 14, 5, 4, 4, 6, 6, 3, 3, 3, 3, 3, 3, 20};
        List<String[]> rows = new ArrayList<>();

        try {
            Map<Integer, Player> playersById = mapPlayersById(playerDAO.listAll());
            Map<Integer, Location> locationsById = mapLocationsById(locationDAO.listAll());
            Map<Integer, CharacterSheet> sheetsById = mapCharacterSheetsById(characterSheetDAO.listAll());
            Map<Integer, RpgClass> classesById = mapClassesById(rpgClassDAO.listAll());
            Map<Integer, Species> speciesById = mapSpeciesById(speciesDAO.listAll());

            for (Character character : characterList) {
                CharacterSheet characterSheet = sheetsById.get(character.getSheetId());

                rows.add(new String[]{
                        String.valueOf(character.getId()),
                        character.getName(),
                        resolvePlayerName(character.getPlayerId(), playersById),
                        resolveLocationName(character.getCurrentLocationId(), locationsById),
                        resolveClassName(characterSheet, classesById),
                        resolveSpeciesName(characterSheet, speciesById),
                        resolveLevel(characterSheet),
                        String.valueOf(character.getHitPoints()),
                        String.valueOf(character.getManaPoints()),
                        resolveMaxHitPoints(characterSheet),
                        resolveMaxManaPoints(characterSheet),
                        resolveAttribute(characterSheet, "FOR"),
                        resolveAttribute(characterSheet, "DES"),
                        resolveAttribute(characterSheet, "CON"),
                        resolveAttribute(characterSheet, "INT"),
                        resolveAttribute(characterSheet, "SAB"),
                        resolveAttribute(characterSheet, "CAR"),
                        character.getHistory()
                });
            }
        } catch (SQLException err) {
            throw new RuntimeException("Erro ao montar listagem de personagens", err);
        }

        UI.printTable(headers, widths, rows);
    }

    private void detail(Character character) throws SQLException {
        int width = 50;
        CharacterSheet sheet = characterSheetDAO.findById(character.getSheetId());
        Player player = character.getPlayerId() != null ? playerDAO.findById(character.getPlayerId()) : null;
        Location loc = character.getCurrentLocationId() != null ? locationDAO.findById(character.getCurrentLocationId()) : null;
        RpgClass rClass = (sheet != null && sheet.getClassId() != null) ? rpgClassDAO.findById(sheet.getClassId()) : null;
        Species spec = (sheet != null && sheet.getSpeciesId() != null) ? speciesDAO.findById(sheet.getSpeciesId()) : null;

        System.out.println("\n" + CYAN + "╔" + "═".repeat(width + 2) + "╗" + RESET);

        UI.printRow(GRAY + (player != null ? player.getName() : "NPC"), width);
        UI.printRow(BOLD.toString() + character.getName().toUpperCase() + GRAY + " (Lv. " + (sheet != null ? sheet.getLevel() : 0) + ")", width);
        UI.printRow(BLUE.toString() + (rClass != null ? rClass.getClassName() : "Sem classe") + " " + GREEN + (spec != null ? spec.getName() : "Desconhecido"), width);

        System.out.println(CYAN + "╠" + "═".repeat(width + 2) + "╣");

        UI.printRow(RED + "HP: " + character.getHitPoints() + "/" + (sheet != null ? sheet.getMaxHitPoints() : 0) + " | " +
                BLUE + "MP: " + character.getManaPoints() + "/" + (sheet != null ? sheet.getMaxManaPoints() : 0), width);
        UI.printRow(BOLD + "LOCALIZAÇÃO ATUAL: " + RESET + (loc != null ? loc.getName() : "Desconhecido"), width);

        System.out.println(CYAN + "╟" + "─".repeat(width + 2) + "╢");

        UI.printRow(BOLD + "[ ATRIBUTOS ]", width);
        if (sheet != null) {
            UI.printRow(YELLOW + " FOR: " + RESET + String.format("%-4d", sheet.getStrength()) + YELLOW + "INT: " + RESET + sheet.getIntelligence(), width);
            UI.printRow(YELLOW + " DEX: " + RESET + String.format("%-4d", sheet.getDexterity()) + YELLOW + "SAB: " + RESET + sheet.getWisdom(), width);
            UI.printRow(YELLOW + " CON: " + RESET + String.format("%-4d", sheet.getConstitution()) + YELLOW + "CAR: " + RESET + sheet.getCharisma(), width);
        }

        System.out.println(CYAN + "╠" + "═".repeat(width + 2) + "╝" + RESET);

        UI.printRow(BOLD + "[ HISTÓRIA ]" + RESET, width);
        String history = (character.getHistory() != null) ? character.getHistory() : "A história deste personagem é desconhecida...";
        while (history.length() > width) {
            int cut = history.lastIndexOf(' ', width);
            if (cut == -1) cut = width;
            UI.printRow(GRAY + history.substring(0, cut).trim() + RESET, width);
            history = history.substring(cut).trim();
        }
        UI.printRow(GRAY + history + RESET, width);

        System.out.println(CYAN + "╚" + "═".repeat(width + 2) + "╝" + RESET + "\n");
    }

    private Map<Integer, Player> mapPlayersById(List<Player> players) {
        Map<Integer, Player> playersById = new HashMap<>();
        for (Player player : players) {
            playersById.put(player.getId(), player);
        }
        return playersById;
    }

    private Map<Integer, Location> mapLocationsById(List<Location> locations) {
        Map<Integer, Location> locationsById = new HashMap<>();
        for (Location location : locations) {
            locationsById.put(location.getId(), location);
        }
        return locationsById;
    }

    private Map<Integer, CharacterSheet> mapCharacterSheetsById(List<CharacterSheet> characterSheets) {
        Map<Integer, CharacterSheet> sheetsById = new HashMap<>();
        for (CharacterSheet characterSheet : characterSheets) {
            sheetsById.put(characterSheet.getId(), characterSheet);
        }
        return sheetsById;
    }

    private Map<Integer, RpgClass> mapClassesById(List<RpgClass> classes) {
        Map<Integer, RpgClass> classesById = new HashMap<>();
        for (RpgClass rpgClass : classes) {
            classesById.put(rpgClass.getIdClass(), rpgClass);
        }
        return classesById;
    }

    private Map<Integer, Species> mapSpeciesById(List<Species> speciesList) {
        Map<Integer, Species> speciesById = new HashMap<>();
        for (Species species : speciesList) {
            speciesById.put(species.getId(), species);
        }
        return speciesById;
    }

    private String resolvePlayerName(Integer playerId, Map<Integer, Player> playersById) {
        if (playerId == null) {
            return "-";
        }

        Player player = playersById.get(playerId);
        if (player == null) {
            return "Jogador #" + playerId;
        }
        return player.getName();
    }

    private String resolveLocationName(Integer locationId, Map<Integer, Location> locationsById) {
        if (locationId == null) {
            return "-";
        }

        Location location = locationsById.get(locationId);
        if (location == null) {
            return "Local #" + locationId;
        }
        return location.getName();
    }

    private String resolveClassName(CharacterSheet characterSheet, Map<Integer, RpgClass> classesById) {
        if (characterSheet == null || characterSheet.getClassId() == null) {
            return "-";
        }

        RpgClass rpgClass = classesById.get(characterSheet.getClassId());
        if (rpgClass == null) {
            return "Classe #" + characterSheet.getClassId();
        }
        return rpgClass.getClassName();
    }

    private String resolveSpeciesName(CharacterSheet characterSheet, Map<Integer, Species> speciesById) {
        if (characterSheet == null || characterSheet.getSpeciesId() == null) {
            return "-";
        }

        Species species = speciesById.get(characterSheet.getSpeciesId());
        if (species == null) {
            return "Especie #" + characterSheet.getSpeciesId();
        }
        return species.getName();
    }

    private String resolveLevel(CharacterSheet characterSheet) {
        if (characterSheet == null) {
            return "-";
        }
        return String.valueOf(characterSheet.getLevel());
    }

    private String resolveMaxHitPoints(CharacterSheet characterSheet) {
        if (characterSheet == null) {
            return "-";
        }
        return String.valueOf(characterSheet.getMaxHitPoints());
    }

    private String resolveMaxManaPoints(CharacterSheet characterSheet) {
        if (characterSheet == null) {
            return "-";
        }
        return String.valueOf(characterSheet.getMaxManaPoints());
    }

    private String resolveAttribute(CharacterSheet characterSheet, String attributeName) {
        if (characterSheet == null) {
            return "-";
        }

        return switch (attributeName) {
            case "FOR" -> String.valueOf(characterSheet.getStrength());
            case "DES" -> String.valueOf(characterSheet.getDexterity());
            case "CON" -> String.valueOf(characterSheet.getConstitution());
            case "INT" -> String.valueOf(characterSheet.getIntelligence());
            case "SAB" -> String.valueOf(characterSheet.getWisdom());
            case "CAR" -> String.valueOf(characterSheet.getCharisma());
            default -> "-";
        };
    }

    private Integer normalizeNullableId(Integer value) {
        if (value != null && value == 0) {
            return null;
        }
        return value;
    }

    private String normalizeNullableText(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value;
    }

    private String printableNullable(Integer value) {
        if (value == null) {
            return "-";
        }
        return value.toString();
    }

    private Integer readExistingPlayerId(Scanner scanner) {
        while (true) {
            try {
                showAvailablePlayers();
                System.out.print("Digite o ID do jogador (0 para nenhum): ");
                Integer playerId = normalizeNullableId(scanner.nextInt());
                scanner.nextLine();

                if (playerId == null) {
                    return null;
                }

                if (playerDAO.findById(playerId) != null) {
                    return playerId;
                }

                System.out.println("Jogador informado nao existe.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar jogadores.", err);
            }
        }
    }

    private Integer readExistingSheetId(Scanner scanner) {
        while (true) {
            try {
                showAvailableSheets();
                System.out.print("Digite o ID da ficha (0 para nenhuma): ");
                Integer sheetId = normalizeNullableId(scanner.nextInt());
                scanner.nextLine();

                if (sheetId == null) {
                    return null;
                }

                if (characterSheetDAO.findById(sheetId) != null) {
                    return sheetId;
                }

                System.out.println("Ficha informada nao existe.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar fichas.", err);
            }
        }
    }

    private Integer readExistingSheetIdRequired(Scanner scanner) {
        while (true) {
            try {
                showAvailableSheets();
                System.out.print("Digite o ID da ficha existente: ");
                Integer sheetId = normalizeNullableId(scanner.nextInt());
                scanner.nextLine();

                if (sheetId == null) {
                    System.out.println("Informe um ID de ficha valido.");
                    continue;
                }

                if (characterSheetDAO.findById(sheetId) != null) {
                    return sheetId;
                }

                System.out.println("Ficha informada nao existe.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar fichas.", err);
            }
        }
    }

    private Integer readExistingLocationId(Scanner scanner) {
        while (true) {
            try {
                showAvailableLocations();
                System.out.print("Digite o ID do local atual (0 para nenhum): ");
                Integer locationId = normalizeNullableId(scanner.nextInt());
                scanner.nextLine();

                if (locationId == null) {
                    return null;
                }

                if (locationDAO.findById(locationId) != null) {
                    return locationId;
                }

                System.out.println("Local informado nao existe.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar locais.", err);
            }
        }
    }

    private void showAvailablePlayers() throws SQLException {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 24};
        List<String[]> rows = new ArrayList<>();

        for (Player player : playerDAO.listAll()) {
            rows.add(new String[]{String.valueOf(player.getId()), player.getName()});
        }

        UI.printTable(headers, widths, rows);
    }

    private void showAvailableLocations() throws SQLException {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 24};
        List<String[]> rows = new ArrayList<>();

        for (Location location : locationDAO.listAll()) {
            rows.add(new String[]{String.valueOf(location.getId()), location.getName()});
        }

        UI.printTable(headers, widths, rows);
    }

    private void showAvailableSheets() throws SQLException {
        String[] headers = {"ID", "CLASSE", "ESPECIE", "NIVEL"};
        int[] widths = {4, 8, 8, 5};
        List<String[]> rows = new ArrayList<>();

        for (CharacterSheet sheet : characterSheetDAO.listAll()) {
            rows.add(new String[]{
                    String.valueOf(sheet.getId()),
                    printableNullable(sheet.getClassId()),
                    printableNullable(sheet.getSpeciesId()),
                    String.valueOf(sheet.getLevel())
            });
        }

        UI.printTable(headers, widths, rows);
    }
}
