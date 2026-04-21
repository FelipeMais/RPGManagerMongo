package service;

import util.Option;

import java.sql.SQLException;

public class MainMenuService extends MenuService {
    private final ItemService itemService;
    private final LocationService locationService;
    private final MagicService magicService;
    private final PlayerService playerService;
    private final AttributeService attributeService;
    private final AbilityService abilityService;
    private final CombatService combatService;
    private final RpgClassService rpgClassService;
    private final SpeciesService speciesService;
    private final CharacterService characterService;

    public MainMenuService() throws SQLException {
        this.itemService = new ItemService();
        this.locationService = new LocationService();
        this.magicService = new MagicService();
        this.playerService = new PlayerService();
        this.attributeService = new AttributeService();
        this.abilityService = new AbilityService();
        this.combatService = new CombatService();
        this.rpgClassService = new RpgClassService();
        this.speciesService = new SpeciesService();
        this.characterService = new CharacterService();
        this.menuTitle = "MENU PRINCIPAL";
        this.menuOptions.add(new Option(1, "GERENCIAR MAGIAS", this::executarGerenciamentoMagias));
        this.menuOptions.add(new Option(2, "GERENCIAR ITENS", this::executarGerenciamentoItens));
        this.menuOptions.add(new Option(3, "GERENCIAR CLASSES", this::executarGerenciamentoClasses));
        this.menuOptions.add(new Option(4, "GERENCIAR JOGADORES", this::executarGerenciamentoJogadores));
        this.menuOptions.add(new Option(5, "GERENCIAR ESPECIES", this::executarGerenciamentoEspecies));
        this.menuOptions.add(new Option(6, "GERENCIAR LOCAIS", this::executarGerenciamentoLocais));
        this.menuOptions.add(new Option(7, "GERENCIAR ATRIBUTOS", this::executarGerenciamentoAtributos));
        this.menuOptions.add(new Option(8, "GERENCIAR PERSONAGENS", this::executarGerenciamentoPersonagens));
        this.menuOptions.add(new Option(9, "GERENCIAR HABILIDADES", this::executarGerenciamentoHabilidades));
        this.menuOptions.add(new Option(10, "GERENCIAR COMBATE", this::executarGerenciamentoCombate));
    }

    private Boolean executarGerenciamentoMagias() {
        try {
            while (magicService.execute()) { }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar magias", e);
        }
    }

    private Boolean executarGerenciamentoItens() {
        try {
            while (itemService.execute()) { }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar itens", e);
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

    private Boolean executarGerenciamentoAtributos() {
        try {
            while (attributeService.execute()) { }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar atributos", e);
        }
    }

    private Boolean executarGerenciamentoPersonagens() {
        try {
            while (characterService.execute()) { }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar personagens", e);
        }
    }

    private Boolean executarGerenciamentoHabilidades() {
        try {
            while (abilityService.execute()) { }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar habilidades", e);
        }
    }

    private Boolean executarGerenciamentoCombate() {
        try {
            while (combatService.execute()) { }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerenciar combate", e);
        }
    }
}
