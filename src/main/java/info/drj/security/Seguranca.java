/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 *
 * @author Dirceu Junior
 */
@Named
@RequestScoped
public class Seguranca {

    @Inject
    private ExternalContext externalContext;

    public String getNomeUsuario() {
        String nome = null;
        UsuarioSistema usuarioLogado = getUsuarioLogado();
        if (usuarioLogado != null) {
            nome = usuarioLogado.getUsuario().getNome().split(" ")[0];
        }
        return nome;
    }

    @Produces
    @UsuarioLogado
    public UsuarioSistema getUsuarioLogado() {
        UsuarioSistema usuario = null;
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (auth != null && auth.getPrincipal() != null) {
            usuario = (UsuarioSistema) auth.getPrincipal();
        }
        return usuario;
    }

    public boolean isEmitirVendaPermitido() {
        return externalContext.isUserInRole("ADMINISTRADOR") || externalContext.isUserInRole("VENDEDOR");
    }

    public boolean isEmitirPedidoPermitido() {
        return externalContext.isUserInRole("ADMINISTRADOR") || externalContext.isUserInRole("VENDEDOR");
    }

    public boolean isCancelarPedidoPermitido() {
        return externalContext.isUserInRole("ADMINISTRADOR") || externalContext.isUserInRole("VENDEDOR");
    }

    public boolean isCancelarVendaPermitido() {
        return externalContext.isUserInRole("ADMINISTRADOR") || externalContext.isUserInRole("VENDEDOR");
    }

    public boolean isPermitidoEmicaoReducaoZ() {
        return externalContext.isUserInRole("ADMINISTRADOR");
    }

    public boolean isUsuarioMaster() {
        return externalContext.isUserInRole("MASTER");
    }

    public boolean isAdministrador() {
        return externalContext.isUserInRole("ADMINISTRADOR");
    }

    public boolean isFinanceiro() {
        return externalContext.isUserInRole("FINANCEIRO");
    }

    public boolean isGerente() {
        return externalContext.isUserInRole("GERENTE");
    }

    public boolean isAlteraSituacaoCliente() {
        return isAdministrador() || isFinanceiro();
    }

}
