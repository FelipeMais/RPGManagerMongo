package model;

public class CombatActionType {
    private Integer id;
    private String nome;

    public CombatActionType(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
