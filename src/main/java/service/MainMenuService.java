package service;

import util.Option;

import java.sql.SQLException;

public class MainMenuService extends MenuService {
    private final MagicService magicService;

    public MainMenuService() throws SQLException {
        this.magicService = new MagicService();
        this.menuTitle = "MENU PRINCIPAL";
        this.menuOptions.add(new Option(1, "GERENCIAR MAGIAS", this::executarGerenciamentoMagias));
    }

    private Boolean executarGerenciamentoMagias() {
        try {
            return magicService.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar magias", e);
        }
    }

}


