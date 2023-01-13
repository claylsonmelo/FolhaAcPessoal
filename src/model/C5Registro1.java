/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author claylson
 */
public class C5Registro1 implements Serializable {
    private Integer codigoRegitro = 1;
    private Integer nroEmpresaMatriz;
    private String dataLancamento;
    private Integer nroLoteContabil;
    private Integer nroLancamento;
    private String historico;
    private String indExtemporaneo;
    private String dataExtemporaneo;
    private List<C5Registro2> registro2;

    public List<C5Registro2> getRegistro2() {
        return registro2;
    }

    public void setRegistro2(List<C5Registro2> registro2) {
        this.registro2 = registro2;
    }

    public Integer getCodigoRegitro() {
        return codigoRegitro;
    }


    public Integer getNroLoteContabil() {
        return nroLoteContabil;
    }

    public void setNroLoteContabil(Integer nroLoteContabil) {
        this.nroLoteContabil = nroLoteContabil;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getIndExtemporaneo() {
        return indExtemporaneo;
    }

    public void setIndExtemporaneo(String indExtemporaneo) {
        this.indExtemporaneo = indExtemporaneo;
    }

    public Integer getNroEmpresaMatriz() {
        return nroEmpresaMatriz;
    }

    public void setNroEmpresaMatriz(Integer nroEmpresaMatriz) {
        this.nroEmpresaMatriz = nroEmpresaMatriz;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getDataExtemporaneo() {
        return dataExtemporaneo;
    }

    public void setDataExtemporaneo(String dataExtemporaneo) {
        this.dataExtemporaneo = dataExtemporaneo;
    }

    public Integer getNroLancamento() {
        return nroLancamento;
    }

    public void setNroLancamento(Integer nroLancamento) {
        this.nroLancamento = nroLancamento;
    }
   
    
    
}
