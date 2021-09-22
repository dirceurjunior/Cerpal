/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.repository;

import info.drj.model.Config;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Dirceu Junior
 */
public class Configs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Config guardar(Config config) {
        return manager.merge(config);
    }

    public Config porId(Long id) {
        return manager.find(Config.class, id);
    }

    public List<Config> todos() {
        return manager.createQuery("from Config order by id", Config.class).getResultList();
    }

}
