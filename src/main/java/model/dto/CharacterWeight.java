package model.dto;

import java.math.BigDecimal;
import model.Character;

public class CharacterWeight {

    private Character personagem;
    private BigDecimal pesoTotal;

    public CharacterWeight(Character personagem, BigDecimal pesoTotal) {
        this.personagem = personagem;
        this.pesoTotal = pesoTotal;
    }

    public Character getPersonagem() {
        return personagem;
    }

    public BigDecimal getPesoTotal() {
        return pesoTotal;
    }

}
