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
public enum TipoStatusPedido {

    ORCAMENTO("ORÇAMENTO"),
    EMITIDO("EMITIDO"),
    CANCELADO("CANCELADO");

    private String descricao;

    TipoStatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
