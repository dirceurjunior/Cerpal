/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.repository;

import info.drj.model.Endereco;
import info.drj.model.Municipio;
import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Dirceu Junior
 */
public class Enderecos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Endereco enderecoPorId(Long id) {
        return this.manager.find(Endereco.class, id);
    }

}
