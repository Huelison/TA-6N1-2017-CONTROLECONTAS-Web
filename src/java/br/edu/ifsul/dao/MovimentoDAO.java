/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Movimento;
import br.edu.ifsul.modelo.Pagamento;
import br.edu.ifsul.modelo.Pessoa;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateful;

/**
 *
 * @author Huelison
 */
@Stateful
public class MovimentoDAO<TIPO> extends DAOGenerico<Movimento> implements Serializable {

    private String filtroTpPagamento;

    public MovimentoDAO() {
        super();
        ordem = "id";
        filtroTpPagamento = "";
        classePersistente = Movimento.class;
    }

    @Override
    public Movimento getObjectByID(Object id) throws Exception {
        Movimento obj = (Movimento) em.find(classePersistente, id);
        obj.getPagamentos().size();
        return obj;
    }

    @Override
    public List<Movimento> getListaObjetos() {
        String jpql = "from " + classePersistente.getSimpleName();
        filtro = filtro.replaceAll("[';-]", "");
        String where = "";
        if (filtro.length() > 0) {
            if (ordem.equals("id")) {
                try {
                    Integer.parseInt(filtro);
                    where += " where " + ordem + " = '" + filtro + "' ";
                } catch (Exception e) {
                }
            } else {
                where += " where upper(" + ordem + ") like '" + filtro.toUpperCase() + "%' ";
            }
        }

        if (filtroTpPagamento.trim().length() > 0) {
            if (where.length() > 0) {
                where += " and ";
            } else {
                where += " where ";
            }
            where += "  upper(tipo) = '" + filtroTpPagamento.toUpperCase() + "'";
        }

        jpql += where;
        jpql += " order by " + ordem;
        
        System.out.println(jpql);
        totalObjetos = em.createQuery(jpql).getResultList().size();
        return em.createQuery(jpql).setFirstResult(posicaoAtual).
                setMaxResults(maximoObjetos).getResultList();
    }

    public String getFiltroTpPagamento() {
        return filtroTpPagamento;
    }

    public void setFiltroTpPagamento(String filtroTpPagamento) {
        this.filtroTpPagamento = filtroTpPagamento;
    }

}
