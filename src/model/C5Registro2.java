/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author claylson
 */
public class C5Registro2 implements Serializable {
    private Integer codigoRegistro = 2;
    private String conta;
    private String indDebitoCredito;
    private String valor;
    private String historico;
    private C5Registro3 registro3;

    public C5Registro3 getRegistro3() {
        return registro3;
    }

    public void setRegistro3(C5Registro3 registro3) {
        this.registro3 = registro3;
    }

    public Integer getCodigoRegistro() {
        return codigoRegistro;
    }
    
    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getIndDebitoCredito() {
        return indDebitoCredito;
    }

    public void setIndDebitoCredito(String indDebitoCredito) {
        this.indDebitoCredito = indDebitoCredito;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }


    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }
    
    
}
