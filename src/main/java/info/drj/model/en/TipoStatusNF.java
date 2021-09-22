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
public enum TipoStatusNF {

    EMITIDA("EMITIDA"),
    PENDENTE("PENDENTE");

    private String descricao;

    TipoStatusNF(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
