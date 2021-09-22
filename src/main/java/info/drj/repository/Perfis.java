/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.repository;

import info.drj.model.Perfil;
import info.drj.service.NegocioException;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Dirceu Junior
 */
public class Perfis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Perfil porId(Long id) {
        return manager.find(Perfil.class, id);
    }

    public List<Perfil> todos() {
        List<Perfil> perfis = null;
        try {
            perfis = this.manager.createQuery("from Perfil where id < 99 order by nome", Perfil.class).getResultList();
        } catch (NegocioException e) {
            throw new NegocioException("Não encontrou nenhum perfil cadastrado no sistema");
        }
        return perfis;
    }
    
     public List<Perfil> menosAdm() {
        List<Perfil> perfis = null;
        try {
            perfis = this.manager.createQuery("from Perfil where id > 1 and id < 99 order by nome", Perfil.class).getResultList();
        } catch (NegocioException e) {
            throw new NegocioException("Não encontrou nenhum perfil cadastrado no sistema");
        }
        return perfis;
    }

}
