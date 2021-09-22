/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.service;

import info.drj.repository.Configs;
import info.drj.model.Config;
import info.drj.security.UsuarioLogado;
import info.drj.security.UsuarioSistema;
import info.drj.util.jpa.Transactional;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.inject.Inject;

/**
 *
 * @author Dirceu Junior
 */
public class CadastroConfigService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Configs configs;

    @Inject
    @UsuarioLogado
    private UsuarioSistema usuarioLogado;

    @Transactional
    public Config salvar(Config config) {

        config.setUsuarioUltimaAlteracao(usuarioLogado.getUsuario());
        config.setDataUltimaAlteracao(LocalDateTime.now());

        return configs.guardar(config);
    }

}
