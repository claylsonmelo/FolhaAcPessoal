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
public class Empresa implements Serializable {

    private Integer nroErp;
    private String nroFortes;
    private String nome;

    public Integer getNroErp() {
        return nroErp;
    }

    public void setNroErp(Integer nroErp) {
        this.nroErp = nroErp;
    }

    public String getNroFortes() {
        return nroFortes;
    }

    public void setNroFortes(String nroFortes) {
        this.nroFortes = nroFortes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
