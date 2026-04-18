package service;

import util.Option;

import java.sql.SQLException;

public class MainMenuService extends MenuService {
    private final MagicService magicService;
    private final PlayerService playerService;
    private final RpgClassService rpgClassService;
    private final SpeciesService speciesService;

    public MainMenuService() throws SQLException {
        this.magicService = new MagicService();
        this.playerService = new PlayerService();
        this.rpgClassService = new RpgClassService();
        this.speciesService = new SpeciesService();
        this.menuTitle = "MENU PRINCIPAL";
        this.menuOptions.add(new Option(1, "GERENCIAR MAGIAS", this::executarGerenciamentoMagias));
        this.menuOptions.add(new Option(2, "GERENCIAR CLASSES", this::executarGerenciamentoClasses));
        this.menuOptions.add(new Option(3, "GERENCIAR JOGADORES", this::executarGerenciamentoJogadores));
        this.menuOptions.add(new Option(4, "GERENCIAR ESPECIES", this::executarGerenciamentoEspecies));
    }

    private Boolean executarGerenciamentoMagias() {
        try {
            return magicService.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar magias", e);
        }
    }

    private Boolean executarGerenciamentoClasses() {
        try {
            return rpgClassService.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar classes", e);
        }
    }

    private Boolean executarGerenciamentoJogadores() {
        try {
            return playerService.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar jogadores", e);
        }
    }

    private Boolean executarGerenciamentoEspecies() {
        try {
            return speciesService.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar especies", e);
        }
    }
}
