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
public enum TipoFormaPagamento {

    BOLETO_BANCARIO("BOLETO BANCÁRIO"),
    DEPOSITO_BANCARIO("DEPOSITO BANCÁRIO"),
    DINHEIRO("DINHEIRO"),
    CARTAO_CREDITO("CARTÃO CRÉDITO"),
    CARTAO_DEBITO("CARTÃO DÉBITO"),
    CHEQUE("CHEQUE");

    private String descricao;

    TipoFormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
