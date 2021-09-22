/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.service;

import info.drj.repository.Produtos;
import info.drj.model.Produto;
import info.drj.security.UsuarioLogado;
import info.drj.security.UsuarioSistema;
import info.drj.util.jpa.Transactional;
import java.io.Serializable;
import java.util.Date;
import javax.inject.Inject;

/**
 *
 * @author Dirceu Junior
 */
public class CadastroProdutoService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Produtos produtos;

    @Inject
    @UsuarioLogado
    private UsuarioSistema usuarioLogado;

    @Transactional
    public Produto salvar(Produto produto) {

        if (produto.getId() == null) {
            produto.setUsuarioCadastro(usuarioLogado.getUsuario());
        } else {
            produto.setUsuarioUltimaAlteracao(usuarioLogado.getUsuario());
            produto.setDataUltimaAlteracao(new Date());
        }

        return produtos.guardar(produto);
    }

}
