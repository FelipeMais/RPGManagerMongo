package service;

import contracts.AttributeDAO;
import contracts.ItemDAO;
import factory.DaoFactory;
import model.Attribute;
import model.Item;
import model.relationship.ItemAttribute;
import util.Option;
import util.UI;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static util.Colors.*;

public class ItemService extends MenuService {
    private final AttributeDAO attributeDAO;
    private final ItemDAO itemDAO;

    public ItemService() throws SQLException {
        this.attributeDAO = DaoFactory.getAttributeDAO();
        this.itemDAO = DaoFactory.getItemDAO();
        this.menuTitle = "GERENCIAR ITENS";
        this.menuOptions.add(new Option(1, "INCLUIR ITEM", this::create));
        this.menuOptions.add(new Option(2, "ALTERAR ITEM", this::update));
        this.menuOptions.add(new Option(3, "REMOVER ITEM", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR ITEM", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR ITENS", this::listAll));
    }

    private Boolean create() {
        try {
            Item newItem = instantiateItem(false);
            itemDAO.insert(newItem);
        } catch (Exception err) {
            System.out.println("Erro ao criar novo item!");
        }
        return true;
    }

    private Boolean update() {
        try {
            Item updatedItem = instantiateItem(true);
            itemDAO.insert(updatedItem);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar item!");
        }
        return true;
    }

    private Item instantiateItem(Boolean askId) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;

        if (askId) {
            System.out.print("Digite o ID do item: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print("Digite o nome do item: ");
        String name = scanner.nextLine();

        System.out.print("Digite a descricao: ");
        String description = scanner.nextLine();

        System.out.print("Digite o peso do item: ");
        BigDecimal weight = scanner.nextBigDecimal();

        System.out.print("Digite o valor monetario do item: ");
        BigDecimal monetaryValue = scanner.nextBigDecimal();

        List<ItemAttribute> attributes = getAttributes();
        if (askId) {
            return new Item(id, name, description, weight, monetaryValue, attributes);
        }
        return new Item(name, description, weight, monetaryValue, attributes);
    }

    private List<ItemAttribute> getAttributes() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Deseja adicionar atributos ao item? [S]/[N]: ");
        String addAttributes = scanner.nextLine();
        if (!addAttributes.equalsIgnoreCase("S")) {
            return new ArrayList<>();
        }

        List<ItemAttribute> attributes = new ArrayList<>();
        boolean addAttribute = true;
        while (addAttribute) {
            showAvailableAttributes();
            System.out.print("Id do atributo (0 para encerrar)");
            Integer attributeId = scanner.nextInt();

            if (attributeId == 0) {
                addAttribute = false;
                scanner.nextLine();
                continue;
            }

            if (containsAttribute(attributes, attributeId)) {
                System.out.println("Atributo ja adicionado para este item.");
                scanner.nextLine();
                continue;
            }

            Attribute attributeFound = attributeDAO.findById(attributeId);
            if (attributeFound == null) {
                System.out.println("Atributo nao encontrado! Digite outro id.");
                scanner.nextLine();
                continue;
            }

            System.out.print("Valor para o atributo do item: ");
            Integer attributeValue = scanner.nextInt();
            scanner.nextLine();

            attributes.add(new ItemAttribute(null, attributeId, attributeValue, attributeFound));
        }
        return attributes;
    }

    private boolean containsAttribute(List<ItemAttribute> attributes, Integer attributeId) {
        for (ItemAttribute attribute : attributes) {
            if (attribute.getAttributeId().equals(attributeId)) {
                return true;
            }
        }
        return false;
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do item: ");
            Integer itemId = scanner.nextInt();
            itemDAO.remove(itemId);
        } catch (Exception err) {
            System.out.println("Erro ao remover item!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do item: ");
            Integer itemId = scanner.nextInt();
            Item item = itemDAO.findById(itemId);
            if (item == null) {
                System.out.println("Item nao encontrado");
                return false;
            }
            detail(item);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar item!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<Item> itemList = itemDAO.listAll();
            print(itemList);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar item!");
        }
        return true;
    }

    private void print(List<Item> itemList) {
        String[] headers = {"ID", "NOME", "PESO", "VALOR", "ATRIBUTOS", "DESCRICAO"};
        int[] widths = {4, 18, 8, 10, 50, 50};
        List<String[]> rows = new ArrayList<>();

        for (Item item : itemList) {
            rows.add(new String[]{
                    String.valueOf(item.getId()),
                    item.getName(),
                    String.valueOf(item.getWeight()),
                    String.valueOf(item.getMonetaryValue()),
                    formatAttributes(item.getAttributes()),
                    item.getDescription()
            });
        }

        UI.printTable(headers, widths, rows);
    }

    private void detail(Item item) {

        int width = 50;

        System.out.println("\n" + CYAN + "╔" + "═".repeat(width + 2) + "╗" + RESET);

        UI.printRow(BOLD.toString() + PURPLE + item.getName().toUpperCase() + RESET + " " + GRAY + "#" + item.getId() + RESET, width);

        System.out.println(CYAN + "╠" + "═".repeat(width + 2) + "╣" + RESET);

        String weightValue = "Peso: " + item.getWeight() + "kg | Valor: " + GOLD + item.getMonetaryValue() + " moedas" + RESET;
        UI.printRow(weightValue, width);

        System.out.println(CYAN + "╟" + "─".repeat(width + 2) + "╢" + RESET);

        UI.printRow(BOLD + "[ Atributos ]" + RESET, width);
        if (item.getAttributes() != null && !item.getAttributes().isEmpty()) {
            for (var attr : item.getAttributes()) {
                String name = attr.getAttribute() != null ? attr.getAttribute().getName() : "Attr #" + attr.getAttributeId();

                String display;
                if (attr.getValue() == 0) {

                    display = GREEN + name + RESET;
                } else {

                    String bonus = (attr.getValue() > 0 ? "+" : "") + attr.getValue();
                    display = " • " + name + ": " + BOLD + bonus + RESET;
                }
                UI.printRow(display, width);
            }
        } else {
            UI.printRow(GRAY + "  (Nenhum Atributo Registrado)" + RESET, width);
        }

        System.out.println(CYAN + "╟" + "─".repeat(width + 2) + "╢" + RESET);

        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            String desc = item.getDescription();
            while (desc.length() > width) {
                int cut = desc.lastIndexOf(' ', width);
                if (cut == -1) cut = width;
                UI.printRow(GRAY + desc.substring(0, cut).trim() + RESET, width);
                desc = desc.substring(cut).trim();
            }
            UI.printRow(GRAY + desc + RESET, width);
        }

        System.out.println(CYAN + "╚" + "═".repeat(width + 2) + "╝" + RESET + "\n");
    }


    private String formatAttributes(List<ItemAttribute> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return "-";
        }

        List<String> values = new ArrayList<>();
        for (ItemAttribute attribute : attributes) {
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
