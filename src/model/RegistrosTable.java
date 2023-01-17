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
public class RegistrosTable implements Serializable {
    private String empresa;
    private String codigo;
    private String nome;
    private Double valor;
    private String contaDebito;
    private String contaCredito;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getContaDebito() {
        return contaDebito;
    }

    public void setContaDebito(String contaDebito) {
        this.contaDebito = contaDebito;
    }

    public String getContaCredito() {
        return contaCredito;
    }

    public void setContaCredito(String contaCredito) {
        this.contaCredito = contaCredito;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    
    
    
}
