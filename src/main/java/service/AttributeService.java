package service;

import contracts.AttributeDAO;
import factory.DaoFactory;
import model.Attribute;
import util.Option;
import util.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AttributeService extends MenuService {
    private final AttributeDAO attributeDAO;

    public AttributeService() throws SQLException {
        this.attributeDAO = DaoFactory.getAttributeDAO();
        this.menuTitle = "GERENCIAR ATRIBUTOS";
        this.menuOptions.add(new Option(1, "INCLUIR ATRIBUTO", this::create));
        this.menuOptions.add(new Option(2, "ATUALIZAR ATRIBUTO", this::update));
        this.menuOptions.add(new Option(3, "REMOVER ATRIBUTO", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR ATRIBUTO", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR ATRIBUTOS", this::listAll));
    }

    private Boolean create() {
        try {
            Attribute newAttribute = instantiateAttribute(false);
            attributeDAO.insert(newAttribute);
        } catch (Exception err) {
            System.out.println("Erro ao criar novo atributo!");
        }
        return true;
    }

    private Boolean update() {
        try {
            Attribute updatedAttribute = instantiateAttribute(true);
            attributeDAO.update(updatedAttribute);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar atributo!");
        }
        return true;
    }

    private Attribute instantiateAttribute(Boolean askId) {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        if (askId) {
            System.out.print("Digite o ID do atributo: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print("Digite o nome do atributo: ");
        String name = scanner.nextLine();

        if (askId) {
            return new Attribute(id, name);
        }
        return new Attribute(name);
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do atributo: ");
            Integer attributeId = scanner.nextInt();
            attributeDAO.remove(attributeId);
        } catch (Exception err) {
            System.out.println("Erro ao remover atributo!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do atributo: ");
            Integer attributeId = scanner.nextInt();
            Attribute attribute = findAttributeById(attributeId);
            if (attribute == null) {
                System.out.println("Atributo nao encontrado");
                return false;
            }
            print(Collections.singletonList(attribute));
        } catch (Exception err) {
            System.out.println("Erro ao buscar atributo!");
        }
        return true;
    }

    public Attribute findAttributeById(Integer attributeId) throws SQLException {
        return attributeDAO.findById(attributeId);
    }


    private Boolean listAll() {
        try {
            List<Attribute> attributes = attributeDAO.listAll();
            print(attributes);
        } catch (Exception err) {
            System.out.println("Erro ao buscar atributos!");
        }
        return true;
    }

    private void print(List<Attribute> attributes) {
        String[] headers = {"ID", "NOME"};
        int[] widths = {4, 30};
        List<String[]> rows = new ArrayList<>();

        for (Attribute attribute : attributes) {
            rows.add(new String[]{
                    String.valueOf(attribute.getId()),
                    attribute.getName()
            });
        }

        UI.printTable(headers, widths, rows);
    }
}
