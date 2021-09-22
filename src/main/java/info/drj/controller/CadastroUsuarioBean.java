/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.controller;

import info.drj.model.Contato;
import info.drj.model.Endereco;
import info.drj.model.Perfil;
import info.drj.model.Usuario;
import info.drj.repository.Perfis;
import info.drj.repository.Usuarios;
import info.drj.security.UsuarioLogado;
import info.drj.security.UsuarioSistema;
import info.drj.service.CadastroUsuarioService;
import info.drj.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Dirceu Junior
 */
@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Usuarios usuarios;

    @Inject
    private Perfis perfis;

    @Inject
    private CadastroUsuarioService cadastroUsuarioService;

    @Inject
    @UsuarioLogado
    private UsuarioSistema usuarioLogado;

    private Usuario usuario;
    private Perfil perfil;

    private List<Perfil> listaPerfis = new ArrayList<>();
    private List<Perfil> listaPerfisSelecionados = new ArrayList<>();

    private Endereco endereco;
    private Endereco enderecoSelecionado;

    private Contato contato;
    private Contato contatoSelecionado;

    public CadastroUsuarioBean() {
        limpar();
    }

    public void salvar() {
        this.usuario.setPerfis(listaPerfisSelecionados);
        this.usuario = cadastroUsuarioService.salvar(this.usuario);
        limpar();
        FacesUtil.addInfoMessage("Usuário salvo com sucesso!");
    }

    public void inicializar() {
        List<Perfil> lista = null;
        lista = usuarioLogado.getUsuario().getPerfis();
        for (Perfil p : lista) {
            if (!p.getNome().equals("ADMINISTRADOR") && !p.getNome().equals("MASTER")) {
                this.listaPerfis = perfis.menosAdm();
            } else {
                this.listaPerfis = perfis.todos();
                break;
            }
        }
    }

    private void limpar() {
        usuario = new Usuario();
        listaPerfisSelecionados.clear();
        novoEndereco();
        novoContato();
    }

    public void adicionarPerfil() {
        boolean flag = true;
        if (perfil != null) {
            if (listaPerfisSelecionados.isEmpty()) {
                this.listaPerfisSelecionados.add(perfil);
            } else {
                for (Iterator iterator = listaPerfisSelecionados.iterator(); iterator.hasNext();) {
                    Perfil c = (Perfil) iterator.next();
                    if (c.getId().equals(perfil.getId())) {
                        flag = false;
                    }
                }
                if (flag) {
                    this.listaPerfisSelecionados.add(perfil);
                    perfil = new Perfil();
                } else {
                    FacesUtil.addErrorMessage("Perfil já informado para Usuário!");
                }
            }
        }
    }

    public void removerPerfil() {
        this.listaPerfisSelecionados.remove(perfil);
        perfil = new Perfil();
    }

    public boolean isEditando() {
        if (this.usuario != null) {
            return usuario.getId() != null;
        }
        return false;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (usuario != null) {
            this.usuario = usuario;
            listaPerfisSelecionados = usuario.getPerfis();
        }
    }

    /////////////////////////////////////////////////////////////////// --------------------------------- endereços
    public void novoEndereco() {
        this.endereco = new Endereco();
        enderecoSelecionado = null;
    }

    public void adicionarEndereco() {
        if (enderecoSelecionado == null) {
            endereco.setUsuario(usuario);
            usuario.getEnderecos().add(endereco);
        }
        novoEndereco();
    }

    public void removerEndereco() {
        if (enderecoSelecionado != null) {
            usuario.getEnderecos().remove(enderecoSelecionado);
        }
        novoEndereco();
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Endereco getEnderecoSelecionado() {
        return enderecoSelecionado;
    }

    public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
        this.enderecoSelecionado = enderecoSelecionado;
        this.endereco = enderecoSelecionado;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////// --------------------------------- contatos
    public void novoContato() {
        this.contato = new Contato();
        contatoSelecionado = null;
    }

    public void adicionarContato() {
        if (contatoSelecionado == null) {
            contato.setUsuario(usuario);
            usuario.getContatos().add(contato);
        }
        novoContato();
    }

    public void removerContato() {
        if (contatoSelecionado != null) {
            usuario.getContatos().remove(contatoSelecionado);
        }
        novoContato();
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Contato getContatoSelecionado() {
        return contatoSelecionado;
    }

    public void setContatoSelecionado(Contato contatoSelecionado) {
        this.contatoSelecionado = contatoSelecionado;
        this.contato = contatoSelecionado;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public List<Perfil> getListaPerfis() {
        return listaPerfis;
    }

    public void setListaPerfis(List<Perfil> listaPerfis) {
        this.listaPerfis = listaPerfis;
    }

    public List<Perfil> getListaPerfisSelecionados() {
        return listaPerfisSelecionados;
    }

    public void setListaPerfisSelecionados(List<Perfil> listaPerfisSelecionados) {
        this.listaPerfisSelecionados = listaPerfisSelecionados;
    }

}
