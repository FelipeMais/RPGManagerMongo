package service;

import contracts.LocationTypeDAO;
import factory.DaoFactory;
import model.LocationType;
import util.Colors;
import util.Option;
import util.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LocationTypeService extends MenuService {
    private final LocationTypeDAO locationTypeDAO;

    public LocationTypeService() throws SQLException {
        this.locationTypeDAO = DaoFactory.getLocationTypeDAO();
        this.menuTitle = "GERENCIAR TIPOS DE LOCAL";
        this.menuOptions.add(new Option(1, "INCLUIR TIPO DE LOCAL", this::create));
        this.menuOptions.add(new Option(2, "ATUALIZAR TIPO DE LOCAL", this::update));
        this.menuOptions.add(new Option(3, "REMOVER TIPO DE LOCAL", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR TIPO DE LOCAL", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR TIPOS DE LOCAL", this::listAll));
    }

    private Boolean create() {
        try {
            LocationType newLocationType = instantiateLocationType(false);
            locationTypeDAO.insert(newLocationType);
        } catch (Exception err) {
            System.out.println("Erro ao criar novo tipo de local!");
        }
        return true;
    }

    private Boolean update() {
        try {
            LocationType updatedLocationType = instantiateLocationType(true);
            locationTypeDAO.update(updatedLocationType);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar tipo de local!");
        }
        return true;
    }

    private LocationType instantiateLocationType(Boolean askId) {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        if (askId) {
            System.out.print("Digite o ID do tipo de local: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print("Digite o nome do tipo de local: ");
        String name = scanner.nextLine();

        System.out.print("Digite a descricao: ");
        String description = scanner.nextLine();

        if (askId) {
            return new LocationType(id, name, description);
        }
        return new LocationType(name, description);
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do tipo de local: ");
            Integer locationTypeId = scanner.nextInt();
            locationTypeDAO.remove(locationTypeId);
        } catch (Exception err) {
            System.out.println("Erro ao remover tipo de local!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do tipo de local: ");
            Integer locationTypeId = scanner.nextInt();
            LocationType locationType = locationTypeDAO.findById(locationTypeId);
            if (locationType == null) {
                System.out.println("Tipo de local nao encontrado");
                return true;
            }
            detail(locationType);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar tipo de local!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<LocationType> locationTypes = locationTypeDAO.listAll();
            print(locationTypes);
            UI.enterAnythingToContinue();
        } catch (Exception err) {
            System.out.println("Erro ao buscar tipos de local!");
        }
        return true;
    }

    private void print(List<LocationType> locationTypes) {
        String[] headers = {"ID", "NOME", "DESCRICAO"};
        int[] widths = {4, 24, 70};
        List<String[]> rows = new ArrayList<>();

        for (LocationType locationType : locationTypes) {
            rows.add(new String[]{
                    String.valueOf(locationType.getId()),
                    locationType.getName(),
                    locationType.getDescription()
            });
        }

        UI.printTable(headers, widths, rows);
    }

    private void detail(LocationType locationType) {
        int width = 45;
        System.out.println("\n" + Colors.CYAN + "╔" + "═".repeat(width + 2) + "╗");
        UI.printRow(Colors.BOLD + locationType.getName().toUpperCase(), width);
        if (locationType.getDescription() != null) {
            System.out.println(Colors.CYAN + "╟" + "─".repeat(width + 2) + "╢");
            UI.printRow(Colors.GRAY + locationType.getDescription(), width);
        }
        System.out.println(Colors.CYAN + "╚" + "═".repeat(width + 2) + "╝\n" + Colors.RESET);
    }
}
