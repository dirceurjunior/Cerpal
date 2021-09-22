/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.controller;

import info.drj.model.en.TipoAtivoInativo;
import info.drj.model.en.TipoSituacaoCliente;
import info.drj.model.en.TipoCRT;
import info.drj.model.en.TipoContato;
import info.drj.model.en.TipoEmail;
import info.drj.model.en.TipoEndereco;
import info.drj.model.en.TipoEstadoCivil;
import info.drj.model.en.TipoFormaPagamento;
import info.drj.model.en.TipoModalidade;
import info.drj.model.en.TipoPessoa;
import info.drj.model.en.TipoSexo;
import info.drj.model.en.TipoStatusNF;
import info.drj.model.en.TipoStatusPedido;
import info.drj.model.en.TipoUnidadeProduto;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Dirceu Junior
 */
@Named
@ViewScoped
public class EnumBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public EnumBean() {
    }

    public TipoPessoa[] getTiposPessoas() {
        return TipoPessoa.values();
    }

    public TipoSexo[] getTiposSexos() {
        return TipoSexo.values();
    }

    public TipoEstadoCivil[] getTiposEstadoCivis() {
        return TipoEstadoCivil.values();
    }

    public TipoEndereco[] getTiposEnderecos() {
        return TipoEndereco.values();
    }

    public TipoContato[] getTiposContatos() {
        return TipoContato.values();
    }

    public TipoEmail[] getTiposEmails() {
        return TipoEmail.values();
    }

    public TipoCRT[] getTiposCRTs() {
        return TipoCRT.values();
    }

    public TipoUnidadeProduto[] getTiposUnidadeProdutos() {
        return TipoUnidadeProduto.values();
    }

    public TipoStatusPedido[] getTipoStatusPedidos() {
        return TipoStatusPedido.values();
    }

    public TipoSituacaoCliente[] getTipoAtivoInativoBloqueados() {
        return TipoSituacaoCliente.values();
    }

    public TipoAtivoInativo[] getTipoAtivoInativos() {
        return TipoAtivoInativo.values();
    }

    public TipoStatusNF[] getTipoStatusNFs() {
        return TipoStatusNF.values();
    }

    public TipoFormaPagamento[] getTipoFormaPagamentos() {
        return TipoFormaPagamento.values();
    }

     public TipoModalidade[] getTipoModalidades() {
        return TipoModalidade.values();
    }

}
