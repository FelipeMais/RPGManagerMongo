package service;

import contracts.AttributeDAO;
import contracts.MagicDAO;
import factory.DaoFactory;
import model.Attribute;
import model.Magic;
import model.relationship.MagicAttribute;
import util.Option;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
        System.out.print("ATRIBUTOS | ");
        System.out.print("\n");
        for (Magic magic : magicList) {
            System.out.print(magic.getId() + pipe());
            System.out.print(magic.getName() + pipe());
            System.out.print(magic.getDescription() + pipe());
            System.out.print(magic.getManaCost() + pipe());
            System.out.print(magic.getMinLevel() + pipe());
            System.out.print(magic.getDices() + pipe());
            System.out.print(formatAttributes(magic.getAttributes()) + pipe());
            System.out.print("\n");
        }
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

    private String pipe() {
        return " | ";
    }
}
