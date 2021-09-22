/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.repository;

import info.drj.model.Cooperado;
import info.drj.repository.filter.CooperadoFilter;
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
public class Cooperados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Cooperado guardar(Cooperado cooperado) {
        return manager.merge(cooperado);
    }

    @Transactional
    public void remover(Cooperado cooperado) {
        try {
            cooperado = porId(cooperado.getId());
            manager.remove(cooperado);
            manager.flush();
        } catch (PersistenceException e) {
            throw new NegocioException("USUARIO NÃO PODE SER EXCLUIDO");
        }
    }

    public Cooperado porId(Long id) {
        return manager.find(Cooperado.class, id);
    }

    public List<Cooperado> porCpf(String cpf) {
        cpf = format("###.###.###-##", cpf);
        try {
            return manager.createQuery("from Cooperado where cpf = :cpf order by nota_fiscal desc", Cooperado.class).setParameter("cpf", cpf).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Cooperado> porCnpj(String cnpj) {
        cnpj = format("##.###.###/####-##", cnpj);
        try {
            return manager.createQuery("from Cooperado where cnpj = :cnpj order by nota_fiscal desc", Cooperado.class).setParameter("cnpj", cnpj).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Cooperado porMatricula(String matricula) {
        try {
            return manager.createQuery("from Cooperado where matricula = :matricula", Cooperado.class).setParameter("matricula", matricula)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Cooperado porCadastroDataReferencia(String cadastro, String dataReferencia) {
        try {
            return manager.createQuery("from Cooperado where cadastro = :cadastro and dataReferencia = :dataReferencia", Cooperado.class)
                    .setParameter("cadastro", cadastro)
                    .setParameter("dataReferencia", dataReferencia)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Cooperado> filtrados(CooperadoFilter filtro) {
        Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Cooperado.class);
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
        return criteria.addOrder(Order.asc("notaFiscal")).list();
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
