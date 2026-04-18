package service;

import contracts.AttributeDAO;
import factory.DaoFactory;
import model.Attribute;
import util.Option;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AttributeService extends MenuService {
    private final AttributeDAO attributeDAO;

    public AttributeService() throws SQLException {
        this.attributeDAO = DaoFactory.getQualityDAO();
        this.menuTitle = "GERENCIAR ATRIBUTOS";
        this.menuOptions.add(new Option(1, "INCLUIR ATRIBUTO", this::create));
        this.menuOptions.add(new Option(2, "ATUALIZAR ATRIBUTO", this::update));
        this.menuOptions.add(new Option(3, "REMOVER ATRIBUTO", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR ATRIBUTO", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR ATRIBUTOS", this::listAll));
    }

    private Boolean create() {
        try {
            Attribute newAttribute = instantiateQuality(false);
            attributeDAO.insert(newAttribute);
        } catch (Exception err) {
            System.out.println("Erro ao criar novo atributo!");
        }
        return true;
    }

    private Boolean update() {
        try {
            Attribute updatedAttribute = instantiateQuality(true);
            attributeDAO.update(updatedAttribute);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar atributo!");
        }
        return true;
    }

    private Attribute instantiateQuality(Boolean askId) {
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
            Integer qualityId = scanner.nextInt();
            attributeDAO.remove(qualityId);
        } catch (Exception err) {
            System.out.println("Erro ao remover atributo!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do atributo: ");
            Integer qualityId = scanner.nextInt();
            Attribute attribute = findAttributeById(qualityId);
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
    public Attribute findAttributeById(Integer qualityId) throws SQLException{
        return attributeDAO.findById(qualityId);
    }


    private Boolean listAll() {
        try {
            List<Attribute> qualities = attributeDAO.listAll();
            print(qualities);
        } catch (Exception err) {
            System.out.println("Erro ao buscar atributos!");
        }
        return true;
    }

    private void print(List<Attribute> qualities) {
        System.out.print("ID | ");
        System.out.print("NOME | ");
        System.out.print("\n");
        for (Attribute attribute : qualities) {
            System.out.print(attribute.getId() + pipe());
            System.out.print(attribute.getName() + pipe());
            System.out.print("\n");
        }
    }

    private String pipe() {
        return " | ";
    }
}
