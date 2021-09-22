/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.service;

import info.drj.model.Usuario;
import info.drj.repository.Usuarios;
import info.drj.util.jpa.Transactional;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author Dirceu Junior
 */
public class CadastroUsuarioService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Usuarios usuarios;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        Usuario usuarioExistente = usuarios.porEmail(usuario.getEmail());
        if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
            throw new NegocioException("JÁ EXISTE UM USUARIO COM EMAIL INFORMADO");
        }
        usuarioExistente = usuarios.porCpf(usuario.getCpf());
        if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
            throw new NegocioException("JÁ EXISTE UM USUARIO COM CPF INFORMADO");
        }
        if (usuario.getPerfis().isEmpty()) {
            throw new NegocioException("USUÁRIO DEVE POSSUIR PELO MENOS 1 PERFIL!");
        }
        return usuarios.guardar(usuario);
        //return usuarios.guardar(usuario);
    }

}
