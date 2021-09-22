/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.repository;

import info.drj.repository.filter.ProdutoFilter;
import info.drj.model.Produto;
import info.drj.service.NegocioException;
import info.drj.util.jpa.Transactional;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Dirceu Junior
 */
public class Produtos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Produto guardar(Produto produto) {
        return manager.merge(produto);
    }

    @Transactional
    public void remover(Produto produto) {
        try {
            produto = porId(produto.getId());
            manager.remove(produto);
            manager.flush();
        } catch (PersistenceException e) {
            throw new NegocioException("Produto não pode ser excluído.");
        }
    }

    public List<Produto> todos() {
        return manager.createQuery("from Produto order by nome", Produto.class).getResultList();
    }

    public Produto porId(Long id) {
        return manager.find(Produto.class, id);
    }

    public Produto porSku(String sku) {
        try {
            return manager.createQuery("from Produto where upper(sku) = :sku", Produto.class).setParameter("sku", sku.toUpperCase()).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Produto porCodigoBarras(String codigoBarras) {
        try {
            return manager.createQuery("from Produto where upper(codigoBarras) = :codigoBarras", Produto.class).setParameter("codigoBarras", codigoBarras.toUpperCase()).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

//    public List<Produto> porNome(String nome) {
//        try {
//            return manager.createQuery("from Produto where upper(nome) like :nome", Produto.class).setParameter("nome", nome.toUpperCase() + "%").getResultList();
//        } catch (NoResultException e) {
//            return null;
//        }
//    }
    public List<Produto> porNome(String nome) {
        try {
            Session session = manager.unwrap(Session.class);
            Criteria criteria = session.createCriteria(Produto.class);
            criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
            return criteria.list();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Produto porProduto(String codigo) {
        Long novoCodigo = Long.parseLong(codigo);
        try {
            if (codigo.length() > 8) {
                return manager.createQuery("from Produto where upper(codigoBarras) = :codigoBarras", Produto.class).setParameter("codigoBarras", codigo.toUpperCase()).getSingleResult();
            } else {
                return manager.createQuery("from Produto where codigoInterno = :codigoInterno", Produto.class).setParameter("codigoInterno", codigo.toUpperCase()).getSingleResult();
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Produto> filtrados(ProdutoFilter filtro) {
        Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Produto.class);

        if (StringUtils.isNotBlank(filtro.getNome())) {
            criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
        }
        return criteria.addOrder(Order.asc("nome")).list();
    }
}
