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
public enum TipoSituacaoCliente {

    AGUARDANDO_APROVACAO("AGUARDANDO APROVAÇÃO"),
    ATIVO("ATIVO"), 
    INATIVO("INATIVO"), 
    BLOQUEADO("BLOQUADO FALTA DE PAGAMENTO");

    private String descricao;

    TipoSituacaoCliente(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
