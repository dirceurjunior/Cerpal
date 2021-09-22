/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.service;

import info.drj.model.Conta;
import info.drj.repository.Contas;
import info.drj.util.jpa.Transactional;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author Dirceu Junior
 */
public class CadastroContaService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Contas contas;

    @Transactional
    public Conta salvar(Conta conta) {
        return contas.guardar(conta);
    }

}
