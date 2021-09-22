package info.drj.repository;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package info.kabanas.repository;
//
//import info.kabanas.model.en.TipoSituacaoCliente;
//import info.kabanas.repository.filter.ClienteFilter;
//import info.kabanas.security.UsuarioLogado;
//import info.kabanas.security.UsuarioSistema;
//import info.kabanas.service.NegocioException;
//import info.kabanas.util.jpa.Transactional;
//import java.io.Serializable;
//import java.util.List;
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import javax.persistence.PersistenceException;
//import org.apache.commons.lang3.StringUtils;
//import org.hibernate.Criteria;
//import org.hibernate.Session;
//import org.hibernate.criterion.MatchMode;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Restrictions;
//
///**
// *
// * @author Dirceu Junior
// */
//public class Clientes implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Inject
//    private EntityManager manager;
//
//    @Inject
//    @UsuarioLogado
//    private UsuarioSistema usuarioLogado;
//
//    public Cliente guardar(Cliente cliente) {
//        return manager.merge(cliente);
//    }
//
//    @Transactional
//    public void remover(Cliente cliente) {
//        try {
//            cliente = porId(cliente.getId());
//            manager.remove(cliente);
//            manager.flush();
//        } catch (PersistenceException e) {
//            throw new NegocioException("Cliente não pode ser excluído.");
//        }
//    }
//
//    public Cliente porId(Long id) {
//        return manager.find(Cliente.class, id);
//    }
//
//    public Cliente porCpfCnpj(String cpfCnpj) {
//        try {
//            return manager.createQuery("from Cliente where cpfCnpj = :cpfCnpj", Cliente.class).setParameter("cpfCnpj", cpfCnpj).getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        }
//    }
//
//    public List<Cliente> porNome(String nome) {
//
//        try {
//            Session session = manager.unwrap(Session.class);
//            Criteria criteria = session.createCriteria(Cliente.class);
//            criteria.add(Restrictions.ilike("nomeRazaoSocial", nome, MatchMode.ANYWHERE));
//            if (usuarioLogado.isVendedor()) {
//                criteria.add(Restrictions.eq("usuarioCadastro", usuarioLogado.getUsuario()));
//            }
//            return criteria.list();
//        } catch (NoResultException e) {
//            return null;
//        }
//
//    }
//
//    public List<Cliente> aguardandoLiberacao() {
//        try {
//            Session session = manager.unwrap(Session.class);
//            Criteria criteria = session.createCriteria(Cliente.class);
//            criteria.add(Restrictions.eq("tipoSituacao", TipoSituacaoCliente.AGUARDANDO_APROVACAO));
//            return criteria.list();
//        } catch (NoResultException e) {
//            return null;
//        }
//    }
//
//    public List<Cliente> todos() {
//        return manager.createQuery("from Cliente order by id", Cliente.class).getResultList();
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<Cliente> filtrados(ClienteFilter filtro) {
//        Session session = manager.unwrap(Session.class);
//        Criteria criteria = session.createCriteria(Cliente.class);
//
//        if (StringUtils.isNotBlank(filtro.getCpfCnpj())) {
//            criteria.add(Restrictions.ilike("cpfCnpj", filtro.getCpfCnpj(), MatchMode.ANYWHERE));
//        }
//        if (StringUtils.isNotBlank(filtro.getNomeRazaoSocial())) {
//            criteria.add(Restrictions.ilike("nomeRazaoSocial", filtro.getNomeRazaoSocial(), MatchMode.ANYWHERE));
//        }
//        if (StringUtils.isNotBlank(filtro.getNomeFantasia())) {
//            criteria.add(Restrictions.ilike("nomeFantasia", filtro.getNomeFantasia(), MatchMode.ANYWHERE));
//        }
//        if (usuarioLogado.isVendedor()) {
//            criteria.add(Restrictions.eq("usuarioCadastro", usuarioLogado.getUsuario()));
//        }
//
//        return criteria.addOrder(Order.asc("nomeRazaoSocial")).list();
//    }
//
//}
