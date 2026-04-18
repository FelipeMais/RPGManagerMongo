package service;

import contracts.SpeciesDAO;
import factory.DaoFactory;
import model.Species;
import util.Option;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SpeciesService extends MenuService {
    private final SpeciesDAO speciesDAO;

    public SpeciesService() throws SQLException {
        this.speciesDAO = DaoFactory.getSpeciesDAO();
        this.menuTitle = "GERENCIAR ESPECIES";
        this.menuOptions.add(new Option(1, "INCLUIR ESPECIE", this::create));
        this.menuOptions.add(new Option(2, "ATUALIZAR ESPECIE", this::update));
        this.menuOptions.add(new Option(3, "REMOVER ESPECIE", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR ESPECIE", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR ESPECIES", this::listAll));
    }

    private Boolean create() {
        try {
            Species newSpecies = instantiateSpecies();
            speciesDAO.insert(newSpecies);
        } catch (Exception err) {
            System.out.println("Erro ao criar nova especie!");
        }
        return true;
    }

    private Boolean update() {
        try {
            Species updatedSpecies = instantiateSpecies();
            speciesDAO.update(updatedSpecies);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar especie!");
        }
        return true;
    }

    private Species instantiateSpecies() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID da especie: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o nome da especie: ");
        String name = scanner.nextLine();

        return new Species(id, name);
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id da especie: ");
            Integer speciesId = scanner.nextInt();
            speciesDAO.remove(speciesId);
        } catch (Exception err) {
            System.out.println("Erro ao remover especie!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id da especie: ");
            Integer speciesId = scanner.nextInt();
            Species species = speciesDAO.findById(speciesId);
            if (species == null) {
                System.out.println("Especie nao encontrada");
                return false;
            }
            print(Collections.singletonList(species));
        } catch (Exception err) {
            System.out.println("Erro ao buscar especie!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<Species> speciesList = speciesDAO.listAll();
            print(speciesList);
        } catch (Exception err) {
            System.out.println("Erro ao buscar especies!");
        }
        return true;
    }

    private void print(List<Species> speciesList) {
        System.out.print("ID | ");
        System.out.print("NOME | ");
        System.out.print("\n");
        for (Species species : speciesList) {
            System.out.print(species.getId() + pipe());
            System.out.print(species.getName() + pipe());
            System.out.print("\n");
        }
    }

    private String pipe() {
        return " | ";
    }
}
