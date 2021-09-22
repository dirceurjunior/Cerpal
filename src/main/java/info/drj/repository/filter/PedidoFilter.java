/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.repository.filter;

import info.drj.model.en.TipoStatusNF;
import info.drj.model.en.TipoStatusPedido;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Dirceu Junior
 */
public class PedidoFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long numeroDe;
    private Long numeroAte;
    private Date dataCriacaoDe;
    private Date dataCriacaoAte;
    private String nomeVendedor;
    private String nomeCliente;
    private TipoStatusPedido[] statusPedido;
    private TipoStatusNF[] statusNF = TipoStatusNF.values();

    public Long getNumeroDe() {
        return numeroDe;
    }

    public void setNumeroDe(Long numeroDe) {
        this.numeroDe = numeroDe;
    }

    public Long getNumeroAte() {
        return numeroAte;
    }

    public void setNumeroAte(Long numeroAte) {
        this.numeroAte = numeroAte;
    }

    public Date getDataCriacaoDe() {
        return dataCriacaoDe;
    }

    public void setDataCriacaoDe(Date dataCriacaoDe) {
        this.dataCriacaoDe = dataCriacaoDe;
    }

    public Date getDataCriacaoAte() {
        return dataCriacaoAte;
    }

    public void setDataCriacaoAte(Date dataCriacaoAte) {
        this.dataCriacaoAte = dataCriacaoAte;
    }

    public String getNomeVendedor() {
        return nomeVendedor;
    }

    public void setNomeVendedor(String nomeVendedor) {
        this.nomeVendedor = nomeVendedor;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public TipoStatusPedido[] getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(TipoStatusPedido[] statusPedido) {
        this.statusPedido = statusPedido;
    }

    public TipoStatusNF[] getStatusNF() {
        return statusNF;
    }

    public void setStatusNF(TipoStatusNF[] statusNF) {
        this.statusNF = statusNF;
    }

}
