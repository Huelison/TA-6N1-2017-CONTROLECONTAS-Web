/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.MovimentoDAO;
import br.edu.ifsul.dao.PessoaDAO;
import br.edu.ifsul.modelo.Movimento;
import br.edu.ifsul.modelo.Pagamento;
import br.edu.ifsul.modelo.Pessoa;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Huelison
 */
@Named(value = "controleMovimento")
@SessionScoped
public class ControleMovimento implements Serializable {

    @EJB
    private MovimentoDAO<Movimento> dao;
    private Movimento objeto;
    private Boolean editando;

    @EJB
    private PessoaDAO<Pessoa> daoPessoa;

    private Boolean editandoPagamento;
    private Pagamento pagamento;
    private Boolean novoPagamento;

    public ControleMovimento() {
        editando = false;

    }

    public String listar() {
        editando = false;
        return "/privado/movimento/listar?faces-redirect=true";
    }

    public void novo() {

        objeto = new Movimento();
        editando = true;
        editandoPagamento = false;
    }

    public void alterar(Object id) {
        try {
            objeto = dao.getObjectByID(id);
            editando = true;
            editandoPagamento = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao carregar objeto: "
                    + Util.getMensagemErro(e));
        }
    }

    public void excluir(Object id) {
        try {
            objeto = dao.getObjectByID(id);
            dao.remove(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e) {
            Util.mensagemErro("Erro ao remover objeto: "
                    + Util.getMensagemErro(e));
        }
    }

    public void salvar() {
        System.out.println("aqui");
        try {
            if (objeto.getId() == null) {
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            editando = false;
            editandoPagamento = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir objeto: "
                    + Util.getMensagemErro(e));
        }
    }


    public void novoPagamento() {
        pagamento = new Pagamento();
        editandoPagamento = true;
        novoPagamento = true;
    }

    public void salvarPagamento() { 
        if (pagamento.getId() == null) {
            if (novoPagamento) {
                objeto.adicionarPagamento(pagamento);
            }
        }
        editandoPagamento = false;
        Util.mensagemInformacao("Pagamento persistido com sucesso!");
    }

    public void alterarPagamento(int index) {
        pagamento = objeto.getPagamentos().get(index);
        editandoPagamento = true;
        novoPagamento = false;
    }

    public void excluirPagamento(int index) {
        objeto.removerPagamentos(index);
        Util.mensagemInformacao("Pagamento removido com sucesso!");
    }    
    
    public MovimentoDAO<Movimento> getDao() {
        return dao;
    }

    public void setDao(MovimentoDAO<Movimento> dao) {
        this.dao = dao;
    }

    public Movimento getObjeto() {
        return objeto;
    }

    public void setObjeto(Movimento objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public PessoaDAO<Pessoa> getDaoPessoa() {
        return daoPessoa;
    }

    public void setDaoPessoa(PessoaDAO<Pessoa> daoPessoa) {
        this.daoPessoa = daoPessoa;
    }

    public Boolean getEditandoPagamento() {
        return editandoPagamento;
    }

    public void setEditandoPagamento(Boolean editandoPagamento) {
        this.editandoPagamento = editandoPagamento;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Boolean getNovoPagamento() {
        return novoPagamento;
    }

    public void setNovoPagamento(Boolean novoPagamento) {
        this.novoPagamento = novoPagamento;
    }

}
