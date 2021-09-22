/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.service;

import info.drj.model.Cooperado;
import info.drj.repository.Cooperados;
import info.drj.util.jpa.Transactional;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author Dirceu Junior
 */
public class CadastroCooperadoService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Cooperados cooperados;

    @Transactional
    public Cooperado salvar(Cooperado cooperado) {
        return cooperados.guardar(cooperado);
    }

}
