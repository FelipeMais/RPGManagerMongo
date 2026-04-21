package service;

import contracts.CharacterDAO;
import contracts.CombatActionDAO;
import contracts.CombatActionTypeDAO;
import contracts.CombatDAO;
import contracts.CombatantDAO;
import contracts.ItemDAO;
import contracts.LocationDAO;
import contracts.MagicDAO;
import factory.DaoFactory;
import model.Character;
import model.Combat;
import model.CombatAction;
import model.CombatActionType;
import model.Item;
import model.Location;
import model.Magic;
import util.Option;
import util.UI;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CombatService extends MenuService {
    private final CharacterDAO characterDAO;
    private final CombatDAO combatDAO;
    private final CombatActionDAO combatActionDAO;
    private final CombatActionTypeDAO combatActionTypeDAO;
    private final CombatantDAO combatantDAO;
    private final ItemDAO itemDAO;
    private final LocationDAO locationDAO;
    private final MagicDAO magicDAO;
    private final CombatActionTypeService combatActionTypeService;

    public CombatService() throws SQLException {
        this.characterDAO = DaoFactory.getCharacterDAO();
        this.combatDAO = DaoFactory.getCombatDAO();
        this.combatActionDAO = DaoFactory.getCombatActionDAO();
        this.combatActionTypeDAO = DaoFactory.getCombatActionTypeDAO();
        this.combatantDAO = DaoFactory.getCombatantDAO();
        this.itemDAO = DaoFactory.getItemDAO();
        this.locationDAO = DaoFactory.getLocationDAO();
        this.magicDAO = DaoFactory.getMagicDAO();
        this.combatActionTypeService = new CombatActionTypeService();
        this.menuTitle = "GERENCIAR COMBATE";
        this.menuOptions.add(new Option(1, "GERENCIAR TIPOS DE ACAO DE COMBATE", this::manageCombatActionTypes));
        this.menuOptions.add(new Option(2, "CRIAR NOVO COMBATE", this::createCombat));
        this.menuOptions.add(new Option(3, "REGISTRAR ACAO NO LOG DE COMBATE", this::registerCombatAction));
        this.menuOptions.add(new Option(4, "MOSTRAR COMBATE", this::showCombat));
        this.menuOptions.add(new Option(5, "REMOVER COMBATE", this::removeCombat));
        this.menuOptions.add(new Option(6, "ALTERAR COMBATE", this::updateCombat));
    }

    private Boolean manageCombatActionTypes() {
        try {
            while (combatActionTypeService.execute()) { }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar tipos de acao de combate", e);
        }
        return true;
    }

    private Boolean createCombat() {
        try {
            Combat combat = instantiateCombat();
            Integer combatId = combatDAO.insert(combat);
            System.out.println("Combate criado com ID: " + combatId);
        } catch (Exception err) {
            System.out.println("Erro ao criar combate!");
        }
        return true;
    }

    private Boolean registerCombatAction() {
        try {
            CombatAction combatAction = instantiateCombatAction();
            Integer actionId = combatActionDAO.insert(combatAction);
            System.out.println("Acao registrada com ID: " + actionId);
        } catch (Exception err) {
            System.out.println("Erro ao registrar acao de combate!");
        }
        return true;
    }

    private Boolean showCombat() {
        try {
            Scanner scanner = new Scanner(System.in);
            Combat combat = readExistingCombat(scanner);
            List<CombatAction> combatActions = combatActionDAO.listByCombatId(combat.getId());
            printCombatDetails(combat, combatActions);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao mostrar combate!");
        }
        return true;
    }

    private Boolean removeCombat() {
        try {
            Scanner scanner = new Scanner(System.in);
            Combat combat = readExistingCombat(scanner);
            combatDAO.remove(combat.getId());
            System.out.println("Combate removido com sucesso.");
        } catch (Exception err) {
            System.out.println("Erro ao remover combate!");
        }
        return true;
    }

    private Boolean updateCombat() {
        try {
            Scanner scanner = new Scanner(System.in);
            Combat combat = readExistingCombat(scanner);
            Combat updatedCombat = instantiateUpdatedCombat(combat, scanner);
            combatDAO.update(updatedCombat);
            manageCombatantsUpdate(updatedCombat, scanner);
            System.out.println("Combate alterado com sucesso.");
        } catch (Exception err) {
            System.out.println("Erro ao alterar combate!");
        }
        return true;
    }

    private Combat instantiateCombat() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Integer locationId = readExistingLocationId(scanner);
        Timestamp combatDate = readCombatDate(scanner);

        System.out.print("Digite o sumario do combate: ");
        String summary = normalizeNullableText(scanner.nextLine());

        List<Integer> combatantIds = collectCombatants(scanner);
        return new Combat(locationId, combatDate, summary, combatantIds);
    }

    private CombatAction instantiateCombatAction() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Combat combat = readExistingCombat(scanner);
        Integer combatActionTypeId = readExistingCombatActionTypeId(scanner);
        Integer actorId = readExistingCombatantId(scanner, combat.getCombatantIds(), "agente");
        Integer targetId = readExistingOptionalCombatantId(scanner, combat.getCombatantIds(), "alvo");
        Integer itemId = readExistingItemId(scanner);
        Integer magicId = readExistingMagicId(scanner);

        System.out.print("Ordem do turno: ");
        Integer turnOrder = scanner.nextInt();

        System.out.print("Valor do resultado: ");
        Integer resultValue = scanner.nextInt();
        scanner.nextLine();

        return new CombatAction(
                combat.getId(),
                combatActionTypeId,
                actorId,
                targetId,
                itemId,
                magicId,
                turnOrder,
                resultValue
        );
    }

    private Timestamp readCombatDate(Scanner scanner) {
        while (true) {
            System.out.print("Data do combate (yyyy-MM-dd HH:mm:ss, vazio para agora): ");
            String rawDate = scanner.nextLine();
            if (rawDate.isBlank()) {
                return new Timestamp(System.currentTimeMillis());
            }

            try {
                return Timestamp.valueOf(rawDate);
            } catch (IllegalArgumentException err) {
                System.out.println("Data invalida. Use o formato yyyy-MM-dd HH:mm:ss.");
            }
        }
    }

    private Combat instantiateUpdatedCombat(Combat currentCombat, Scanner scanner) throws SQLException {
        Integer locationId = readUpdatedLocationId(scanner, currentCombat.getLocationId());
        Timestamp combatDate = readUpdatedCombatDate(scanner, currentCombat.getDate());
        String summary = readUpdatedSummary(scanner, currentCombat.getSummary());

        return new Combat(
                currentCombat.getId(),
                locationId,
                combatDate,
                summary,
                currentCombat.getCombatantIds()
        );
    }

    private Timestamp readUpdatedCombatDate(Scanner scanner, Timestamp currentDate) {
        while (true) {
            System.out.print("Data do combate (yyyy-MM-dd HH:mm:ss, vazio para manter " + printableNullable(currentDate) + "): ");
            String rawDate = scanner.nextLine();
            if (rawDate.isBlank()) {
                return currentDate;
            }

            try {
                return Timestamp.valueOf(rawDate);
            } catch (IllegalArgumentException err) {
                System.out.println("Data invalida. Use o formato yyyy-MM-dd HH:mm:ss.");
            }
        }
    }

    private String readUpdatedSummary(Scanner scanner, String currentSummary) {
        System.out.print("Digite o sumario do combate (vazio para manter atual): ");
        String summary = scanner.nextLine();
        if (summary.isBlank()) {
            return currentSummary;
        }
        return summary;
    }

    private List<Integer> collectCombatants(Scanner scanner) throws SQLException {
        List<Integer> combatantIds = new ArrayList<>();

        while (true) {
            showAvailableCharacters();
            System.out.print("Id do combatente (0 para encerrar): ");
            Integer characterId = scanner.nextInt();
            scanner.nextLine();

            if (characterId == 0) {
                if (!combatantIds.isEmpty()) {
                    return combatantIds;
                }
                System.out.println("Adicione ao menos um combatente.");
                continue;
            }

            Character character = characterDAO.findById(characterId);
            if (character == null) {
                System.out.println("Personagem informado nao existe.");
                continue;
            }

            if (combatantIds.contains(characterId)) {
                System.out.println("Personagem ja adicionado neste combate.");
                continue;
            }

            combatantIds.add(characterId);
        }
    }

    private Integer readUpdatedLocationId(Scanner scanner, Integer currentLocationId) {
        while (true) {
            try {
                showAvailableLocations();
                System.out.print("Digite o ID do local do combate (0 para manter " + currentLocationId + "): ");
                Integer locationId = normalizeKeepCurrentId(scanner.nextInt());
                scanner.nextLine();

                if (locationId == null) {
                    return currentLocationId;
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

    private Integer readExistingLocationId(Scanner scanner) {
        while (true) {
            try {
                showAvailableLocations();
                System.out.print("Digite o ID do local do combate: ");
                Integer locationId = scanner.nextInt();
                scanner.nextLine();

                if (locationDAO.findById(locationId) != null) {
                    return locationId;
                }

                System.out.println("Local informado nao existe.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar locais.", err);
            }
        }
    }

    private Combat readExistingCombat(Scanner scanner) {
        while (true) {
            try {
                showAvailableCombats();
                System.out.print("Digite o ID do combate: ");
                Integer combatId = scanner.nextInt();
                scanner.nextLine();

                Combat combat = combatDAO.findById(combatId);
                if (combat != null) {
                    return combat;
                }

                System.out.println("Combate informado nao existe.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar combates.", err);
            }
        }
    }

    private Integer readExistingCombatActionTypeId(Scanner scanner) {
        while (true) {
            try {
                showAvailableCombatActionTypes();
                System.out.print("Digite o ID do tipo de acao de combate: ");
                Integer combatActionTypeId = scanner.nextInt();
                scanner.nextLine();

                if (combatActionTypeDAO.findById(combatActionTypeId) != null) {
                    return combatActionTypeId;
                }

                System.out.println("Tipo de acao de combate informado nao existe.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar tipos de acao de combate.", err);
            }
        }
    }

    private Integer readExistingCombatantId(Scanner scanner, List<Integer> combatantIds, String label) {
        while (true) {
            try {
                showCombatCharacters(combatantIds);
                System.out.print("Digite o ID do " + label + ": ");
                Integer characterId = scanner.nextInt();
                scanner.nextLine();

                if (combatantIds.contains(characterId)) {
                    return characterId;
                }

                System.out.println("Personagem informado nao faz parte do combate.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar combatentes.", err);
            }
        }
    }

    private Integer readExistingOptionalCombatantId(Scanner scanner, List<Integer> combatantIds, String label) {
        while (true) {
            try {
                showCombatCharacters(combatantIds);
                System.out.print("Digite o ID do " + label + " (0 para nenhum): ");
                Integer characterId = normalizeNullableId(scanner.nextInt());
                scanner.nextLine();

                if (characterId == null) {
                    return null;
                }

                if (combatantIds.contains(characterId)) {
                    return characterId;
                }

                System.out.println("Personagem informado nao faz parte do combate.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar combatentes.", err);
            }
        }
    }

    private Integer readExistingItemId(Scanner scanner) {
        while (true) {
            try {
                showAvailableItems();
                System.out.print("Digite o ID do item usado (0 para nenhum): ");
                Integer itemId = normalizeNullableId(scanner.nextInt());
                scanner.nextLine();

                if (itemId == null) {
                    return null;
                }

                if (itemDAO.findById(itemId) != null) {
                    return itemId;
                }

                System.out.println("Item informado nao existe.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar itens.", err);
            }
        }
    }

    private Integer readExistingMagicId(Scanner scanner) {
        while (true) {
            try {
                showAvailableMagics();
                System.out.print("Digite o ID da magia usada (0 para nenhuma): ");
                Integer magicId = normalizeNullableId(scanner.nextInt());
                scanner.nextLine();

                if (magicId == null) {
                    return null;
                }

                if (magicDAO.findById(magicId) != null) {
                    return magicId;
                }

                System.out.println("Magia informada nao existe.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar magias.", err);
            }
        }
    }

    private void showAvailableLocations() throws SQLException {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 30};
        List<String[]> rows = new ArrayList<>();

        for (Location location : locationDAO.listAll()) {
            rows.add(new String[]{String.valueOf(location.getId()), location.getName()});
        }

        UI.printTable(headers, widths, rows);
    }

    private void showAvailableCharacters() throws SQLException {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 30};
        List<String[]> rows = new ArrayList<>();

        for (Character character : characterDAO.listAll()) {
            rows.add(new String[]{String.valueOf(character.getId()), character.getName()});
        }

        UI.printTable(headers, widths, rows);
    }

    private void showAvailableCharactersExcludingCombatants(List<Integer> combatantIds) throws SQLException {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 30};
        List<String[]> rows = new ArrayList<>();

        for (Character character : characterDAO.listAll()) {
            if (!combatantIds.contains(character.getId())) {
                rows.add(new String[]{String.valueOf(character.getId()), character.getName()});
            }
        }

        UI.printTable(headers, widths, rows);
    }

    private void showAvailableCombatActionTypes() throws SQLException {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 30};
        List<String[]> rows = new ArrayList<>();

        for (CombatActionType combatActionType : combatActionTypeDAO.listAll()) {
            rows.add(new String[]{String.valueOf(combatActionType.getId()), combatActionType.getName()});
        }

        UI.printTable(headers, widths, rows);
    }

    private void showAvailableItems() throws SQLException {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 30};
        List<String[]> rows = new ArrayList<>();

        for (Item item : itemDAO.listAll()) {
            rows.add(new String[]{String.valueOf(item.getId()), item.getName()});
        }

        UI.printTable(headers, widths, rows);
    }

    private void showAvailableMagics() throws SQLException {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 30};
        List<String[]> rows = new ArrayList<>();

        for (Magic magic : magicDAO.listAll()) {
            rows.add(new String[]{String.valueOf(magic.getId()), magic.getName()});
        }

        UI.printTable(headers, widths, rows);
    }

    private void showAvailableCombats() throws SQLException {
        String[] headers = {"ID", "LOCAL", "DATA", "COMBATENTES", "SUMARIO"};
        int[] widths = {4, 18, 19, 30, 30};
        List<String[]> rows = new ArrayList<>();

        Map<Integer, String> locationsById = mapLocationsById(locationDAO.listAll());
        Map<Integer, String> characterNamesById = mapCharacterNamesById(characterDAO.listAll());

        for (Combat combat : combatDAO.listAll()) {
            rows.add(new String[]{
                    String.valueOf(combat.getId()),
                    locationsById.getOrDefault(combat.getLocationId(), "Local #" + combat.getLocationId()),
                    combat.getDate() != null ? combat.getDate().toString() : "-",
                    formatCombatants(combat.getCombatantIds(), characterNamesById),
                    combat.getSummary()
            });
        }

        UI.printTable(headers, widths, rows);
    }

    private void printCombatDetails(Combat combat, List<CombatAction> combatActions) throws SQLException {
        Map<Integer, String> locationsById = mapLocationsById(locationDAO.listAll());
        Map<Integer, String> characterNamesById = mapCharacterNamesById(characterDAO.listAll());
        Map<Integer, String> combatActionTypeNamesById = mapCombatActionTypeNamesById(combatActionTypeDAO.listAll());
        Map<Integer, String> itemNamesById = mapItemNamesById(itemDAO.listAll());
        Map<Integer, String> magicNamesById = mapMagicNamesById(magicDAO.listAll());

        UI.printSubTitle("CABECALHO DO COMBATE");
        String[] combatHeaders = {"ID", "LOCAL", "DATA", "COMBATENTES", "SUMARIO"};
        int[] combatWidths = {4, 18, 19, 35, 40};
        List<String[]> combatRows = new ArrayList<>();
        combatRows.add(new String[]{
                String.valueOf(combat.getId()),
                locationsById.getOrDefault(combat.getLocationId(), "Local #" + combat.getLocationId()),
                combat.getDate() != null ? combat.getDate().toString() : "-",
                formatCombatants(combat.getCombatantIds(), characterNamesById),
                combat.getSummary()
        });
        UI.printTable(combatHeaders, combatWidths, combatRows);

        UI.printSubTitle("ACOES DO COMBATE");
        String[] actionHeaders = {"ID", "TURNO", "TIPO", "AGENTE", "ALVO", "ITEM", "MAGIA", "RESULTADO"};
        int[] actionWidths = {4, 5, 18, 18, 18, 18, 18, 9};
        List<String[]> actionRows = new ArrayList<>();

        for (CombatAction combatAction : combatActions) {
            actionRows.add(new String[]{
                    String.valueOf(combatAction.getId()),
                    String.valueOf(combatAction.getTurnOrder()),
                    combatActionTypeNamesById.getOrDefault(
                            combatAction.getCombatActionTypeId(),
                            "Tipo #" + combatAction.getCombatActionTypeId()
                    ),
                    characterNamesById.getOrDefault(combatAction.getActorId(), "Personagem #" + combatAction.getActorId()),
                    combatAction.getTargetId() != null
                            ? characterNamesById.getOrDefault(combatAction.getTargetId(), "Personagem #" + combatAction.getTargetId())
                            : "-",
                    combatAction.getItemId() != null
                            ? itemNamesById.getOrDefault(combatAction.getItemId(), "Item #" + combatAction.getItemId())
                            : "-",
                    combatAction.getMagicId() != null
                            ? magicNamesById.getOrDefault(combatAction.getMagicId(), "Magia #" + combatAction.getMagicId())
                            : "-",
                    String.valueOf(combatAction.getResultValue())
            });
        }

        UI.printTable(actionHeaders, actionWidths, actionRows);
    }

    private void showCombatCharacters(List<Integer> combatantIds) throws SQLException {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 30};
        List<String[]> rows = new ArrayList<>();
        Map<Integer, String> characterNamesById = mapCharacterNamesById(characterDAO.listAll());

        for (Integer combatantId : combatantIds) {
            rows.add(new String[]{
                    String.valueOf(combatantId),
                    characterNamesById.getOrDefault(combatantId, "Personagem #" + combatantId)
            });
        }

        UI.printTable(headers, widths, rows);
    }

    private void addCombatant(Combat combat, Scanner scanner) throws SQLException {
        showCombatCharacters(combat.getCombatantIds());
        System.out.println("PERSONAGENS DISPONIVEIS PARA ADICIONAR");
        showAvailableCharactersExcludingCombatants(combat.getCombatantIds());

        while (true) {
            System.out.print("Digite o ID do combatente para adicionar: ");
            Integer characterId = scanner.nextInt();
            scanner.nextLine();

            Character character = characterDAO.findById(characterId);
            if (character == null) {
                System.out.println("Personagem informado nao existe.");
                continue;
            }

            if (combat.getCombatantIds().contains(characterId)) {
                System.out.println("Personagem ja faz parte do combate.");
                continue;
            }

            combatantDAO.insert(combat.getId(), characterId);
            combat.getCombatantIds().add(characterId);
            System.out.println("Combatente adicionado com sucesso.");
            return;
        }
    }

    private void removeCombatant(Combat combat, Scanner scanner) throws SQLException {
        if (combat.getCombatantIds().size() <= 1) {
            System.out.println("Nao e possivel remover o unico combatente do combate.");
            return;
        }

        while (true) {
            System.out.println("COMBATENTES ATUAIS");
            showCombatCharacters(combat.getCombatantIds());
            System.out.print("Digite o ID do combatente para remover: ");
            Integer characterId = scanner.nextInt();
            scanner.nextLine();

            if (!combat.getCombatantIds().contains(characterId)) {
                System.out.println("Personagem informado nao faz parte do combate.");
                continue;
            }

            combatantDAO.remove(combat.getId(), characterId);
            combat.getCombatantIds().remove(characterId);
            System.out.println("Combatente removido com sucesso.");
            return;
        }
    }

    private void manageCombatantsUpdate(Combat combat, Scanner scanner) throws SQLException {
        while (true) {
            System.out.println("COMBATENTES ATUAIS");
            showCombatCharacters(combat.getCombatantIds());
            System.out.println("[0]: FINALIZAR ALTERACOES DE COMBATENTES");
            System.out.println("[1]: ADICIONAR COMBATENTE");
            System.out.println("[2]: REMOVER COMBATENTE");
            System.out.print("Escolha uma opcao: ");
            Integer option = scanner.nextInt();
            scanner.nextLine();

            if (option == 0) {
                return;
            }

            if (option == 1) {
                addCombatant(combat, scanner);
                continue;
            }

            if (option == 2) {
                removeCombatant(combat, scanner);
                continue;
            }

            System.out.println("Opcao invalida.");
        }
    }

    private Map<Integer, String> mapLocationsById(List<Location> locations) {
        Map<Integer, String> locationsById = new HashMap<>();
        for (Location location : locations) {
            locationsById.put(location.getId(), location.getName());
        }
        return locationsById;
    }

    private Map<Integer, String> mapCharacterNamesById(List<Character> characters) {
        Map<Integer, String> characterNamesById = new HashMap<>();
        for (Character character : characters) {
            characterNamesById.put(character.getId(), character.getName());
        }
        return characterNamesById;
    }

    private Map<Integer, String> mapCombatActionTypeNamesById(List<CombatActionType> combatActionTypes) {
        Map<Integer, String> combatActionTypeNamesById = new HashMap<>();
        for (CombatActionType combatActionType : combatActionTypes) {
            combatActionTypeNamesById.put(combatActionType.getId(), combatActionType.getName());
        }
        return combatActionTypeNamesById;
    }

    private Map<Integer, String> mapItemNamesById(List<Item> items) {
        Map<Integer, String> itemNamesById = new HashMap<>();
        for (Item item : items) {
            itemNamesById.put(item.getId(), item.getName());
        }
        return itemNamesById;
    }

    private Map<Integer, String> mapMagicNamesById(List<Magic> magics) {
        Map<Integer, String> magicNamesById = new HashMap<>();
        for (Magic magic : magics) {
            magicNamesById.put(magic.getId(), magic.getName());
        }
        return magicNamesById;
    }

    private String formatCombatants(List<Integer> combatantIds, Map<Integer, String> characterNamesById) {
        if (combatantIds == null || combatantIds.isEmpty()) {
            return "-";
        }

        List<String> names = new ArrayList<>();
        for (Integer combatantId : combatantIds) {
            names.add(characterNamesById.getOrDefault(combatantId, "Personagem #" + combatantId));
        }
        return String.join(", ", names);
    }

    private Integer normalizeNullableId(Integer value) {
        if (value != null && value == 0) {
            return null;
        }
        return value;
    }

    private Integer normalizeKeepCurrentId(Integer value) {
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

    private String printableNullable(Object value) {
        if (value == null) {
            return "-";
        }
        return value.toString();
    }
}
