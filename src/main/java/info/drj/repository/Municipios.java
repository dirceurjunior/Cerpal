/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.repository;

import info.drj.model.Municipio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Dirceu Junior
 */
public class Municipios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Municipio municipioPorId(Long id) {
        return this.manager.find(Municipio.class, id);
    }

    public List<Municipio> porNome(String municipio) {
        try {
            return manager.createQuery("from Municipio where upper(municipioNome) like :municipioNome", Municipio.class).setParameter("municipioNome", municipio.toUpperCase() + "%").getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
