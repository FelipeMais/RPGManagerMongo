package service;

import contracts.*;
import factory.DaoFactory;
import model.*;
import model.Character;
import model.dto.CharacterWeight;
import util.Option;
import util.UI;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

public class ReportService extends MenuService {
    private final CharacterDAO characterDAO;
    private final CharacterSheetDAO characterSheetDAO;
    private final SpeciesDAO speciesDAO;
    private final RpgClassDAO rpgClassDAO;
    private final CombatActionDAO combatActionDAO;
    private final CombatActionTypeDAO combatActionTypeDAO;

    public ReportService() throws SQLException {
        this.characterDAO = DaoFactory.getCharacterDAO();
        this.characterSheetDAO = DaoFactory.getCharacterSheetDAO();
        this.rpgClassDAO = DaoFactory.getRpgClassDAO();
        this.speciesDAO = DaoFactory.getSpeciesDAO();
        this.combatActionDAO = DaoFactory.getCombatActionDAO();
        this.combatActionTypeDAO = DaoFactory.getCombatActionTypeDAO();

        this.menuTitle = "RELATORIOS";
        this.menuOptions.add(new Option(1, "GERAR RELATORIO DE PERSONAGENS", this::generateCharacterReport));
        this.menuOptions.add(new Option(2, "GERAR RELATORIO DE SOBRECARGA DE PESO", this::generateOverweightReport));
        this.menuOptions.add(new Option(3, "GERAR RELATORIO DE ACOES EM COMBATE", this::generateCombatHistoryReport));
    }

    private boolean generateCharacterReport() {
        try {
            Scanner scanner = new Scanner(System.in);

            Integer classId = readExistingClassId(scanner);
            Integer speciesId = readExistingSpeciesId(scanner);

            List<Character> filteredCharacters = characterDAO.findByClassAndSpecies(classId, speciesId);
            printCharacterReport(filteredCharacters, classId, speciesId);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao gerar relatorio de personagens!");
            err.printStackTrace();
        }

        return true;
    }

    private boolean generateOverweightReport() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o limite de peso considerado normal (ex: 50 ou 50.5): ");
            BigDecimal limitePeso = scanner.nextBigDecimal();
            scanner.nextLine();

            List<CharacterWeight> personagens = characterDAO.findPersonagensComSobrecarga(limitePeso);

            System.out.println("\nRELATORIO DE SOBRECARGA DE PESO");
            System.out.println("Limite estabelecido: " + limitePeso + "\n");

            if (personagens.isEmpty()) {
                System.out.println("Nenhum personagem esta com sobrecarga de peso.");
            } else {
                String[] headers = {"ID", "NOME", "PESO ATUAL", "SOBRECARGA (EXCESSO)"};
                int[] widths = {4, 20, 14, 22};
                List<String[]> rows = new ArrayList<>();

                for (CharacterWeight dto : personagens) {
                    BigDecimal pesoAtual = dto.getPesoTotal();
                    BigDecimal excesso = pesoAtual.subtract(limitePeso);

                    rows.add(new String[]{
                            String.valueOf(dto.getPersonagem().getId()),
                            dto.getPersonagem().getName(),
                            pesoAtual.toString(),
                            "+" + excesso.toString()
                    });
                }

                UI.printTable(headers, widths, rows);
            }

            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao gerar relatorio de sobrecarga!");
            err.printStackTrace();
        }

        return true;
    }

    private boolean generateCombatHistoryReport() {
        try {
            Scanner scanner = new Scanner(System.in);

            showAvailableCharacters();
            System.out.print("Digite o ID do personagem para ver seu historico de combate: ");
            Integer characterId = scanner.nextInt();
            scanner.nextLine();

            Character character = characterDAO.findById(characterId);
            if (character == null) {
                System.out.println("Personagem nao encontrado.");
                return true;
            }

            Integer actionTypeId = readCombatActionTypeFilter(scanner);

            List<model.dto.AcaoCombateDTO> actions = combatActionDAO.listReportByActorIdAndType(characterId, actionTypeId);

            System.out.println("\nRELATORIO DE ACOES DE COMBATE");
            System.out.println("Personagem: " + character.getName() + " (ID " + character.getId() + ")");

            if (actionTypeId == 0) {
                System.out.println("Filtro: Todos os tipos de acao\n");
            } else {
                System.out.println("Filtro: " + combatActionTypeDAO.findById(actionTypeId).getName() + "\n");
            }

            if (actions.isEmpty()) {
                System.out.println("Nenhuma acao encontrada para os filtros informados.");
                UI.enterAnythingToContinue();
                return true;
            }

            String[] headers = {"COMBATE", "TURNO", "TIPO DE ACAO", "ALVO", "ITEM USADO", "MAGIA USADA", "RESULTADO"};
            int[] widths = {8, 7, 20, 20, 18, 18, 10};
            List<String[]> rows = new ArrayList<>();

            for (model.dto.AcaoCombateDTO dto : actions) {
                rows.add(new String[]{
                        String.valueOf(dto.getCombatId()),
                        String.valueOf(dto.getTurnOrder()),
                        dto.getActionTypeName(),
                        dto.getTargetName() != null ? dto.getTargetName() : "-",
                        dto.getItemName() != null ? dto.getItemName() : "-",
                        dto.getMagicName() != null ? dto.getMagicName() : "-",
                        String.valueOf(dto.getResultValue())
                });
            }

            UI.printTable(headers, widths, rows);
            UI.enterAnythingToContinue();

        } catch (Exception err) {
            System.out.println("Erro ao gerar relatorio de combate!");
            err.printStackTrace();
        }

        return true;
    }

    private Integer readExistingClassId(Scanner scanner) throws SQLException {
        while (true) {
            showAvailableClasses();
            System.out.println("0 - TODAS AS CLASSES");
            System.out.print("Digite o ID da classe desejada: ");
            Integer classId = scanner.nextInt();
            scanner.nextLine();

            if (classId == 0 || rpgClassDAO.findById(classId) != null) {
                return classId;
            }

            System.out.println("Classe informada nao existe.");
        }
    }

    private Integer readExistingSpeciesId(Scanner scanner) throws SQLException {
        while (true) {
            showAvailableSpecies();
            System.out.println("0 - TODAS AS ESPECIES");
            System.out.print("Digite o ID da especie desejada: ");
            Integer speciesId = scanner.nextInt();
            scanner.nextLine();

            if (speciesId == 0 || speciesDAO.findById(speciesId) != null) {
                return speciesId;
            }

            System.out.println("Especie informada nao existe.");
        }
    }

    private Integer readCombatActionTypeFilter(Scanner scanner) throws SQLException {
        while (true) {
            showAvailableCombatActionTypes();
            System.out.println("0 - TODOS OS TIPOS DE ACAO");
            System.out.print("Digite o ID do tipo de acao desejada (ou 0 para todas): ");
            Integer typeId = scanner.nextInt();
            scanner.nextLine();

            if (typeId == 0 || combatActionTypeDAO.findById(typeId) != null) {
                return typeId;
            }

            System.out.println("Tipo de acao informado nao existe.");
        }
    }

    private void printCharacterReport(List<Character> characterList, Integer classId, Integer speciesId) throws SQLException {
        String filterClassName = (classId == 0) ? "Todas as Classes" : rpgClassDAO.findById(classId).getClassName();
        String filterSpeciesName = (speciesId == 0) ? "Todas as Especies" : speciesDAO.findById(speciesId).getName();

        System.out.println("RELATORIO DE PERSONAGENS");
        System.out.println("Classe: " + filterClassName + " (ID " + classId + ")");
        System.out.println("Especie: " + filterSpeciesName + " (ID " + speciesId + ")");

        if (characterList.isEmpty()) {
            System.out.println("Nenhum personagem encontrado para os filtros informados.");
            return;
        }

        Map<Integer, CharacterSheet> sheetsById = mapSheetsById(characterSheetDAO.listAll());

        Map<Integer, RpgClass> classesById = new HashMap<>();
        for (RpgClass c : rpgClassDAO.listAll()) {
            classesById.put(c.getIdClass(), c);
        }

        Map<Integer, Species> speciesById = new HashMap<>();
        for (Species s : speciesDAO.listAll()) {
            speciesById.put(s.getId(), s);
        }

        String[] headers = {"ID", "NOME", "ID FICHA", "CLASSE", "ESPECIE", "NIVEL", "PV", "PM"};
        int[] widths = {4, 20, 8, 16, 16, 5, 4, 4};
        List<String[]> rows = new ArrayList<>();

        for (Character character : characterList) {
            CharacterSheet characterSheet = sheetsById.get(character.getSheetId());
            RpgClass charClass = null;
            Species charSpecies = null;

            if (characterSheet != null) {
                charClass = classesById.get(characterSheet.getClassId());
                charSpecies = speciesById.get(characterSheet.getSpeciesId());
            }

            rows.add(new String[]{
                    String.valueOf(character.getId()),
                    character.getName(),
                    String.valueOf(character.getSheetId()),
                    resolveClassName(characterSheet, charClass),
                    resolveSpeciesName(characterSheet, charSpecies),
                    resolveLevel(characterSheet),
                    String.valueOf(character.getHitPoints()),
                    String.valueOf(character.getManaPoints())
            });
        }

        UI.printTable(headers, widths, rows);
    }

    private void showAvailableClasses() throws SQLException {
        List<RpgClass> classes = rpgClassDAO.listAll();
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 24};
        List<String[]> rows = new ArrayList<>();

        for (RpgClass rpgClass : classes) {
            rows.add(new String[]{String.valueOf(rpgClass.getIdClass()), rpgClass.getClassName()});
        }

        UI.printTable(headers, widths, rows);
    }

    private void showAvailableSpecies() throws SQLException {
        List<Species> speciesList = speciesDAO.listAll();
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 24};
        List<String[]> rows = new ArrayList<>();

        for (Species species : speciesList) {
            rows.add(new String[]{String.valueOf(species.getId()), species.getName()});
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

    private void showAvailableCombatActionTypes() throws SQLException {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 30};
        List<String[]> rows = new ArrayList<>();

        for (CombatActionType combatActionType : combatActionTypeDAO.listAll()) {
            rows.add(new String[]{String.valueOf(combatActionType.getId()), combatActionType.getName()});
        }

        UI.printTable(headers, widths, rows);
    }

    private Map<Integer, CharacterSheet> mapSheetsById(List<CharacterSheet> characterSheets) {
        Map<Integer, CharacterSheet> sheetsById = new HashMap<>();
        for (CharacterSheet characterSheet : characterSheets) {
            sheetsById.put(characterSheet.getId(), characterSheet);
        }
        return sheetsById;
    }

    private String resolveClassName(CharacterSheet characterSheet, RpgClass rpgClass) {
        if (characterSheet == null || characterSheet.getClassId() == null) {
            return "-";
        }
        if (rpgClass == null) {
            return "Classe #" + characterSheet.getClassId();
        }
        return rpgClass.getClassName();
    }

    private String resolveSpeciesName(CharacterSheet characterSheet, Species species) {
        if (characterSheet == null || characterSheet.getSpeciesId() == null) {
            return "-";
        }
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
}