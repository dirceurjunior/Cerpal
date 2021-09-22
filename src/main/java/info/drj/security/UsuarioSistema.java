/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.security;

import info.drj.model.Perfil;
import info.drj.model.Usuario;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Dirceu Junior
 */
public class UsuarioSistema extends User {

    private static final long serialVersionUID = 1L;

    private Usuario usuario;

    public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isAdministrador() {
        List<Perfil> lista = usuario.getPerfis();
        for (Perfil p : lista) {
            if (p.getNome().equals("ADMINISTRADOR")) {
                return true;
            }
        }
        return false;
    }

    public boolean isGerente() {
        List<Perfil> lista = usuario.getPerfis();
        for (Perfil p : lista) {
            if (p.getNome().equals("GERENTE")) {
                return true;
            }
        }
        return false;
    }

    public boolean isFinanceiro() {
        List<Perfil> lista = usuario.getPerfis();
        for (Perfil p : lista) {
            if (p.getNome().equals("FINANCEIRO")) {
                return true;
            }
        }
        return false;
    }

    public boolean isVendedor() {
        List<Perfil> lista = usuario.getPerfis();
        for (Perfil p : lista) {
            if (p.getNome().equals("VENDEDOR")) {
                return true;
            }
        }
        return false;
    }

}
