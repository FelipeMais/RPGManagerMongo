package service;

import contracts.AttributeDAO;
import contracts.MagicDAO;
import factory.DaoFactory;
import model.Attribute;
import model.Magic;
import model.relationship.MagicAttribute;
import util.Option;
import util.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static util.UI.printRow;
import static util.Colors.*;

public class MagicService extends MenuService {
    private final AttributeDAO attributeDAO;
    private final MagicDAO magicDAO;

    public MagicService() throws SQLException {
        this.attributeDAO = DaoFactory.getAttributeDAO();
        this.magicDAO = DaoFactory.getMagicDAO();
        this.menuTitle = "GERENCIAR MAGIAS";
        this.menuOptions.add(new Option(1, "INCLUIR MAGIA", this::create));
        this.menuOptions.add(new Option(2, "ALTERAR MAGIA", this::update));
        this.menuOptions.add(new Option(3, "REMOVER MAGIA", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR MAGIA", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR MAGIAS", this::listAll));
    }

    private Boolean create() {
        try {
            Magic newMagic = instantiateMagic(false);
            magicDAO.insert(newMagic);
        } catch (Exception err) {
            System.out.println("Erro ao criar nova magia!");
        }
        return true;
    }

    private Boolean update() {
        try {
            Magic updatedMagic = instantiateMagic(true);
            magicDAO.update(updatedMagic);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar magia!");
        }
        return true;
    }

    private Magic instantiateMagic(Boolean askId) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        if (askId) {
            System.out.print("Digite o ID da magia: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print("Digite o nome da magia: ");
        String name = scanner.nextLine();

        System.out.print("Digite a descricao: ");
        String description = scanner.nextLine();

        System.out.print("Custo de mana: ");
        Integer manaCost = scanner.nextInt();

        System.out.print("Nivel minimo necessario: ");
        Integer minLevel = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite os dados (ex: 2d6, 1d20): ");
        String dices = scanner.nextLine();

        List<MagicAttribute> attributes = getAttributes();
        if (askId) {
            return new Magic(id, name, description, manaCost, minLevel, dices, attributes);
        }
        return new Magic(name, description, manaCost, minLevel, dices, attributes);
    }

    private List<MagicAttribute> getAttributes() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Deseja adicionar atributos a magia? [S]/[N]: ");
        String addAttributes = scanner.nextLine();
        if (!addAttributes.equalsIgnoreCase("S")) {
            return new ArrayList<>();
        }

        List<MagicAttribute> attributes = new ArrayList<>();
        boolean addAttribute = true;
        while (addAttribute) {
            showAvailableAttributes();
            System.out.print("Id do atributo (0 para encerrar): ");
            Integer attributeId = scanner.nextInt();

            if (attributeId == 0) {
                addAttribute = false;
                scanner.nextLine();
                continue;
            }

            if (containsAttribute(attributes, attributeId)) {
                System.out.println("Atributo ja adicionado para esta magia.");
                scanner.nextLine();
                continue;
            }

            Attribute attributeFound = attributeDAO.findById(attributeId);
            if (attributeFound == null) {
                System.out.println("Atributo nao encontrado! Digite outro id.");
                scanner.nextLine();
                continue;
            }

            System.out.print("Valor para o atributo da magia: ");
            Integer attributeValue = scanner.nextInt();
            scanner.nextLine();

            attributes.add(new MagicAttribute(null, attributeId, attributeValue, attributeFound));
        }
        return attributes;
    }

    private boolean containsAttribute(List<MagicAttribute> attributes, Integer attributeId) {
        for (MagicAttribute attribute : attributes) {
            if (attribute.getAttributeId().equals(attributeId)) {
                return true;
            }
        }
        return false;
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
            if (magia == null) {
                System.out.println("Magia nao encontrada");
                return false;
            }
            detail(magia);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar magia!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<Magic> magicList = magicDAO.listAll();
            print(magicList);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar magia!");
        }
        return true;
    }

    private void print(List<Magic> magicList) {
        String[] headers = {"ID", "NOME", "MANA", "NIVEL", "DADOS", "ATRIBUTOS", "DESCRICAO"};
        int[] widths = {4, 18, 4, 5, 8, 50, 50};
        List<String[]> rows = new ArrayList<>();

        for (Magic magic : magicList) {
            rows.add(new String[]{
                    String.valueOf(magic.getId()),
                    magic.getName(),
                    String.valueOf(magic.getManaCost()),
                    String.valueOf(magic.getMinLevel()),
                    magic.getDices(),
                    formatAttributes(magic.getAttributes()),
                    magic.getDescription()
            });
        }

        UI.printTable(headers, widths, rows);
    }

    private void detail(Magic magic) {
        String RESET = "\u001B[0m", BOLD = "\u001B[1m", PURPLE = "\u001B[35m",
                CYAN = "\u001B[36m", GRAY = "\u001B[90m", YEL = "\u001B[33m";
        int width = 50;

        System.out.println("\n" + CYAN + "╔" + "═".repeat(width + 2) + "╗" + RESET);
        printRow(BOLD + PURPLE + magic.getName().toUpperCase() + RESET + GRAY + " [ #" + magic.getId() + "]" + RESET, width);
        System.out.println(CYAN + "╠" + "═".repeat(width + 2) + "╣" + RESET);

        printRow(BOLD + "CUSTO: " + RESET + BLUE + magic.getManaCost() + " MP" + RESET + " | " +
                BOLD + "REQ: " + RESET + "Lv. " + magic.getMinLevel(), width);
        printRow(BOLD + "PODER: " + RESET + YEL + (magic.getDices().isEmpty() ? "-" : magic.getDices()) + RESET, width);

        System.out.println(CYAN + "╟" + "─".repeat(width + 2) + "╢" + RESET);

        printRow(BOLD + "[ ATRIBUTOS ]" + RESET, width);
        if (magic.getAttributes() != null && !magic.getAttributes().isEmpty()) {
            for (var attr : magic.getAttributes()) {
                String name = attr.getAttribute() != null ? attr.getAttribute().getName() : "Attr #" + attr.getAttributeId();
                String bonus = (attr.getValue() != 0) ? ": " + (attr.getValue() > 0 ? "+" : "") + attr.getValue() : " (Intrinsic)";
                printRow(" • " + name + bonus, width);
            }
        } else {
            printRow(GRAY + "  Nenhum atributo adicional." + RESET, width);
        }

        System.out.println(CYAN + "╟" + "─".repeat(width + 2) + "╢" + RESET);

        if (magic.getDescription() != null && !magic.getDescription().isBlank()) {
            String desc = magic.getDescription();
            while (desc.length() > width) {
                int cut = desc.lastIndexOf(' ', width);
                if (cut == -1) cut = width;
                printRow(GRAY + desc.substring(0, cut).trim() + RESET, width);
                desc = desc.substring(cut).trim();
            }
            printRow(GRAY + desc + RESET, width);
        }

        System.out.println(CYAN + "╚" + "═".repeat(width + 2) + "╝" + RESET + "\n");
    }

    private String formatAttributes(List<MagicAttribute> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return "-";
        }

        List<String> values = new ArrayList<>();
        for (MagicAttribute attribute : attributes) {
            String attributeName = attribute.getAttribute() != null
                    ? attribute.getAttribute().getName()
                    : attribute.getAttributeId().toString();
            values.add(attributeName + "(" + attribute.getValue() + ")");
        }
        return String.join(", ", values);
    }

    private void showAvailableAttributes() throws SQLException {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 24};
        List<String[]> rows = new ArrayList<>();

        for (Attribute attribute : attributeDAO.listAll()) {
            rows.add(new String[]{String.valueOf(attribute.getId()), attribute.getName()});
        }

        UI.printTable(headers, widths, rows);
    }
}
