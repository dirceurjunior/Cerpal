/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.repository;

import info.drj.model.Conta;
import info.drj.repository.filter.ContaFilter;
import info.drj.service.NegocioException;
import info.drj.util.jpa.Transactional;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.swing.text.MaskFormatter;
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
public class Contas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Conta guardar(Conta conta) {
        return manager.merge(conta);
    }

    @Transactional
    public void remover(Conta conta) {
        try {
            conta = porId(conta.getId());
            manager.remove(conta);
            manager.flush();
        } catch (PersistenceException e) {
            throw new NegocioException("USUARIO NÃO PODE SER EXCLUIDO");
        }
    }

    public List<Conta> all() {
        return manager.createQuery("from Conta where order by id", Conta.class).setMaxResults(20).getResultList();
    }

    public Conta porId(Long id) {
        return manager.find(Conta.class, id);
    }

    public List<Conta> porCpf(String cpf) {
        cpf = format("###.###.###-##", cpf);
        try {
            return manager.createQuery("from Conta where cpf = :cpf order by nota_fiscal desc", Conta.class).setParameter("cpf", cpf).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Conta> porCnpj(String cnpj) {
        cnpj = format("##.###.###/####-##", cnpj);
        try {
            return manager.createQuery("from Conta where cnpj = :cnpj order by nota_fiscal desc", Conta.class).setParameter("cnpj", cnpj).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Conta porCadastroDataReferencia(String cadastro, String dataReferencia) {
        try {
            return manager.createQuery("from Conta where cadastro = :cadastro and dataReferencia = :dataReferencia", Conta.class)
                    .setParameter("cadastro", cadastro)
                    .setParameter("dataReferencia", dataReferencia)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Conta porNotaFiscal(String notaFiscal) {
        try {
            return manager.createQuery("from Conta where notaFiscal = :notaFiscal", Conta.class).setParameter("notaFiscal", notaFiscal).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Boolean verificarNotaFiscal(String notaFiscal) {
        try {
            List<Conta> resultado = manager.createQuery("from Conta where notaFiscal = :notaFiscal", Conta.class).setParameter("notaFiscal", notaFiscal).getResultList();
            if (resultado.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Conta> porDataReferencia(String dataReferencia) {
        try {
            return manager.createQuery("from Conta where dataReferencia = :dataReferencia order by nota_fiscal desc", Conta.class).setParameter("dataReferencia", dataReferencia).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Conta> filtrados(ContaFilter filtro) {
        Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Conta.class);
        if (StringUtils.isNotBlank(filtro.getNome())) {
            criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
        }
        String documento = removerCaracteres(filtro.getDocumento());
        if (StringUtils.isNotBlank(documento)) {
            if (documento.length() == 11) {
                criteria.add(Restrictions.ilike("cpf", format("###.###.###-##", documento), MatchMode.ANYWHERE));
            }
            if (documento.length() == 14) {
                criteria.add(Restrictions.ilike("cnpj", format("##.###.###/####-##", documento), MatchMode.ANYWHERE));
            }
        }
        if (StringUtils.isNotBlank(filtro.getNc())) {
            criteria.add(Restrictions.eq("cadastro", filtro.getNc()));
        }
        return criteria.addOrder(Order.desc("notaFiscal")).list();
    }

    public String removerCaracteres(String value) {
        value = value.replace('.', ' ');
        value = value.replace('-', ' ');
        value = value.replace('/', ' ');
        value = value.replaceAll(" ", "");
        return value;
    }

    private static String format(String pattern, Object value) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(pattern);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
