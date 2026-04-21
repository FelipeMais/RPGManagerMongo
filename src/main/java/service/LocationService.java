package service;

import contracts.LocationDAO;
import factory.DaoFactory;
import model.Location;
import util.Option;
import util.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LocationService extends MenuService {
    private final LocationDAO locationDAO;
    private final LocationTypeService locationTypeService;

    public LocationService() throws SQLException {
        this.locationDAO = DaoFactory.getLocationDAO();
        this.locationTypeService = new LocationTypeService();
        this.menuTitle = "GERENCIAR LOCAIS";
        this.menuOptions.add(new Option(1, "INCLUIR LOCAL", this::create));
        this.menuOptions.add(new Option(2, "ATUALIZAR LOCAL", this::update));
        this.menuOptions.add(new Option(3, "REMOVER LOCAL", this::remove));
        this.menuOptions.add(new Option(4, "BUSCAR LOCAL", this::findById));
        this.menuOptions.add(new Option(5, "LISTAR LOCAIS", this::listAll));
        this.menuOptions.add(new Option(6, "GERENCIAR TIPOS DE LOCAL", this::manageLocationTypes));
    }

    private Boolean create() {
        try {
            Location newLocation = instantiateLocation(false);
            locationDAO.insert(newLocation);
        } catch (Exception err) {
            System.out.println("Erro ao criar novo local!");
        }
        return true;
    }

    private Boolean update() {
        try {
            Location updatedLocation = instantiateLocation(true);
            locationDAO.update(updatedLocation);
        } catch (Exception err) {
            System.out.println("Erro ao atualizar local!");
        }
        return true;
    }

    private Location instantiateLocation(Boolean askId) {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        if (askId) {
            System.out.print("Digite o ID do local: ");
            id = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print("Digite o ID do local pai (0 para nenhum): ");
        Integer parentId = normalizeNullableId(scanner.nextInt());
        scanner.nextLine();

        System.out.print("Digite o ID do tipo de local (0 para nenhum): ");
        Integer locationTypeId = normalizeNullableId(scanner.nextInt());
        scanner.nextLine();

        System.out.print("Digite o nome do local: ");
        String name = scanner.nextLine();

        System.out.print("Digite a descricao: ");
        String description = scanner.nextLine();

        if (askId) {
            return new Location(parentId, id, locationTypeId, name, description);
        }
        return new Location(parentId, locationTypeId, name, description);
    }

    private Boolean remove() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do local: ");
            Integer locationId = scanner.nextInt();
            locationDAO.remove(locationId);
        } catch (Exception err) {
            System.out.println("Erro ao remover local!");
        }
        return true;
    }

    private Boolean findById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Id do local: ");
            Integer locationId = scanner.nextInt();
            Location location = locationDAO.findById(locationId);
            if (location == null) {
                System.out.println("Local nao encontrado");
                return true;
            }
            print(Collections.singletonList(location));
        } catch (Exception err) {
            System.out.println("Erro ao buscar local!");
        }
        return true;
    }

    private Boolean listAll() {
        try {
            List<Location> locations = locationDAO.listAll();
            print(locations);
        } catch (Exception err) {
            System.out.println("Erro ao buscar locais!");
        }
        return true;
    }

    private Boolean manageLocationTypes() {
        try {
            while (locationTypeService.execute()) { }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar tipos de local", e);
        }
        return true;
    }

    private void print(List<Location> locations) {
        String[] headers = {"ID", "LOCAL PAI", "TIPO", "NOME", "DESCRICAO"};
        int[] widths = {4, 9, 6, 20, 70};
        List<String[]> rows = new ArrayList<>();

        for (Location location : locations) {
            rows.add(new String[]{
                    String.valueOf(location.getId()),
                    printableNullable(location.getParentId()),
                    printableNullable(location.getLocationTypeId()),
                    location.getName(),
                    location.getDescription()
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

}
