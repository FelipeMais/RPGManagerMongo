package service;

import contracts.CharacterSheetDAO;
import contracts.RpgClassDAO;
import contracts.SpeciesDAO;
import factory.DaoFactory;
import model.CharacterSheet;
import model.RpgClass;
import model.Species;
import util.Option;
import util.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CharacterSheetService extends MenuService {
    private final CharacterSheetDAO characterSheetDAO;
    private final RpgClassDAO rpgClassDAO;
    private final SpeciesDAO speciesDAO;

    public CharacterSheetService() throws SQLException {
        this.characterSheetDAO = DaoFactory.getCharacterSheetDAO();
        this.rpgClassDAO = DaoFactory.getRpgClassDAO();
        this.speciesDAO = DaoFactory.getSpeciesDAO();
        this.menuTitle = "GERENCIAR FICHAS";
        this.menuOptions.add(new Option(1, "INCLUIR FICHA", this::create));
        this.menuOptions.add(new Option(2, "ATUALIZAR FICHA", this::update));
        this.menuOptions.add(new Option(3, "REMOVER FICHA", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR FICHA", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR FICHAS", this::listAll));
    }

    public Integer createCharacterSheetInteractively() throws SQLException {
        CharacterSheet newCharacterSheet = instantiateCharacterSheet(false);
        Integer generatedId = characterSheetDAO.insert(newCharacterSheet);
        System.out.println("Ficha criada com ID: " + generatedId);
        return generatedId;
    }

    public CharacterSheet findCharacterSheetById(Integer characterSheetId) throws SQLException {
        return characterSheetDAO.findById(characterSheetId);
    }

    private Boolean create() {
        try {
            createCharacterSheetInteractively();
        } catch (Exception err) {
            System.out.println("Erro ao criar nova ficha!");
        }
        return true;
    }

    private Boolean update() {
        try {
            CharacterSheet updatedCharacterSheet = instantiateCharacterSheet(true);
            characterSheetDAO.update(updatedCharacterSheet);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar ficha!");
        }
        return true;
    }

    private CharacterSheet instantiateCharacterSheet(Boolean askId) {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        if (askId) {
            System.out.print("Digite o ID da ficha: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }

        Integer classId = readExistingClassId(scanner);
        Integer speciesId = readExistingSpeciesId(scanner);

        System.out.print("Pontos de vida maximos: ");
        Integer maxHitPoints = scanner.nextInt();

        System.out.print("Pontos de mana maximos: ");
        Integer maxManaPoints = scanner.nextInt();

        System.out.print("Forca: ");
        Integer strength = scanner.nextInt();

        System.out.print("Destreza: ");
        Integer dexterity = scanner.nextInt();

        System.out.print("Constituicao: ");
        Integer constitution = scanner.nextInt();

        System.out.print("Inteligencia: ");
        Integer intelligence = scanner.nextInt();

        System.out.print("Sabedoria: ");
        Integer wisdom = scanner.nextInt();

        System.out.print("Carisma: ");
        Integer charisma = scanner.nextInt();

        System.out.print("Nivel: ");
        Integer level = scanner.nextInt();

        if (askId) {
            return new CharacterSheet(id, classId, speciesId, maxHitPoints, maxManaPoints, strength, dexterity, constitution, intelligence, wisdom, charisma, level);
        }
        //TO DO: arrumar skill e magia conhecida
        return new CharacterSheet(classId, speciesId, maxHitPoints, maxManaPoints, strength, dexterity, constitution, intelligence, wisdom, charisma, level, null);
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id da ficha: ");
            Integer characterSheetId = scanner.nextInt();
            characterSheetDAO.remove(characterSheetId);
        } catch (Exception err) {
            System.out.println("Erro ao remover ficha!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id da ficha: ");
            Integer characterSheetId = scanner.nextInt();
            CharacterSheet characterSheet = characterSheetDAO.findById(characterSheetId);
            if (characterSheet == null) {
                System.out.println("Ficha nao encontrada");
                return false;
            }
            print(Collections.singletonList(characterSheet));
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar ficha!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<CharacterSheet> characterSheets = characterSheetDAO.listAll();
            print(characterSheets);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar fichas!");
        }
        return true;
    }

    private void print(List<CharacterSheet> characterSheets) {
        String[] headers = {"ID", "CLASSE", "ESPECIE", "NIVEL", "PV MAX", "PM MAX", "FOR", "DES", "CON", "INT", "SAB", "CAR"};
        int[] widths = {4, 6, 7, 5, 6, 6, 3, 3, 3, 3, 3, 3};
        List<String[]> rows = new ArrayList<>();

        for (CharacterSheet characterSheet : characterSheets) {
            rows.add(new String[]{
                    String.valueOf(characterSheet.getId()),
                    printableNullable(characterSheet.getClassId()),
                    printableNullable(characterSheet.getSpeciesId()),
                    String.valueOf(characterSheet.getLevel()),
                    String.valueOf(characterSheet.getMaxHitPoints()),
                    String.valueOf(characterSheet.getMaxManaPoints()),
                    String.valueOf(characterSheet.getStrength()),
                    String.valueOf(characterSheet.getDexterity()),
                    String.valueOf(characterSheet.getConstitution()),
                    String.valueOf(characterSheet.getIntelligence()),
                    String.valueOf(characterSheet.getWisdom()),
                    String.valueOf(characterSheet.getCharisma())
            });
        }

        UI.printTable(headers, widths, rows);
    }

    private Integer normalizeNullableId(Integer value) {
        if (value != null && value == 0) {
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

    private Integer readExistingClassId(Scanner scanner) {
        while (true) {
            try {
                showAvailableClasses();
                System.out.print("Digite o ID da classe (0 para nenhuma): ");
                Integer classId = normalizeNullableId(scanner.nextInt());
                scanner.nextLine();

                if (classId == null) {
                    return null;
                }

                if (rpgClassDAO.findById(classId) != null) {
                    return classId;
                }

                System.out.println("Classe informada nao existe.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar classes.", err);
            }
        }
    }

    private Integer readExistingSpeciesId(Scanner scanner) {
        while (true) {
            try {
                showAvailableSpecies();
                System.out.print("Digite o ID da especie (0 para nenhuma): ");
                Integer speciesId = normalizeNullableId(scanner.nextInt());
                scanner.nextLine();

                if (speciesId == null) {
                    return null;
                }

                if (speciesDAO.findById(speciesId) != null) {
                    return speciesId;
                }

                System.out.println("Especie informada nao existe.");
            } catch (SQLException err) {
                throw new RuntimeException("Erro ao listar especies.", err);
            }
        }
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
}
