/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.model.en;

/**
 *
 * @author Dirceu Junior
 */
public enum TipoCRT {

    LUCRO_PRESUMIDO("Lucro Presumido"),
    LUCRO_REAL("Lucro Real"),
    SIMPLES_NACIONAL("Simples Nacional"),
    MICROEMPREENDEDOR_INDIVIDUAL("Microempreendedor Individual (MEI)"),
    LUCRO_ARBITRADO("Lucro Arbitrado");

    private String descricao;

    TipoCRT(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
