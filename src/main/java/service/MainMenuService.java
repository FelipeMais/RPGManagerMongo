package service;

import util.Option;

import java.sql.SQLException;

public class MainMenuService extends MenuService {
    private final LocationService locationService;
    private final MagicService magicService;
    private final PlayerService playerService;
    private final RpgClassService rpgClassService;
    private final SpeciesService speciesService;

    public MainMenuService() throws SQLException {
        this.locationService = new LocationService();
        this.magicService = new MagicService();
        this.playerService = new PlayerService();
        this.rpgClassService = new RpgClassService();
        this.speciesService = new SpeciesService();
        this.menuTitle = "MENU PRINCIPAL";
        this.menuOptions.add(new Option(1, "GERENCIAR MAGIAS", this::executarGerenciamentoMagias));
        this.menuOptions.add(new Option(2, "GERENCIAR CLASSES", this::executarGerenciamentoClasses));
        this.menuOptions.add(new Option(3, "GERENCIAR JOGADORES", this::executarGerenciamentoJogadores));
        this.menuOptions.add(new Option(4, "GERENCIAR ESPECIES", this::executarGerenciamentoEspecies));
        this.menuOptions.add(new Option(5, "GERENCIAR LOCAIS", this::executarGerenciamentoLocais));
    }

    private Boolean executarGerenciamentoMagias() {
        try {
            while (magicService.execute()) { }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar magias", e);
        }
    }

    private Boolean executarGerenciamentoClasses() {
        try {
            while (rpgClassService.execute()) { }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar classes", e);
        }
    }

    private Boolean executarGerenciamentoJogadores() {
        try {
            while (playerService.execute()) { }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar jogadores", e);
        }
    }

    private Boolean executarGerenciamentoEspecies() {
        try {
            while (speciesService.execute()) { }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar especies", e);
        }
    }

    private Boolean executarGerenciamentoLocais() {
        try {
            while (locationService.execute()) { }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar locais", e);
        }
    }
}
