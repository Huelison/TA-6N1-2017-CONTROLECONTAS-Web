/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.converter;

import br.edu.ifsul.modelo.Pessoa;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Huelison
 */
@FacesConverter(value = "converterPessoa")
public class ConverterPessoa implements Converter, Serializable{
    @PersistenceContext(unitName = "TA-6N1-2017-CONTROLECONTAS-WebPU")
    protected EntityManager em;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
 
        if(string == null || string.equals("Selecione um registro")){
            return null;
        }
        
        return em.find(Pessoa.class, Integer.parseInt(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
         
        if (o == null){
            return null;
        } 
        Pessoa obj = (Pessoa) o;
        
        return obj.getId().toString();
    }
    
}
