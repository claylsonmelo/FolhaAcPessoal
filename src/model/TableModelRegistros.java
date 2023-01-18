/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author claylson
 */
public class TableModelRegistros extends AbstractTableModel {

    private List<RegistrosTable> registros;
 private static final NumberFormat brazilianFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private final int COLUNA_EMPRESA = 0;
    private final int COLUNA_CODIGO = 1;
     private final int COLUNA_TIPO = 2;
    private final int COLUNA_HISTORICO = 3;
    private final int COLUNA_VALOR = 4;
    private final int COLUNA_CTDEBITO = 5;
    private final int COLUNA_CTCREDITO = 6;

    private final String[] colunas = {"Empresa", "Código","Tipo", "Histórico", "Valor", "Débito", "Crédito"};

    public TableModelRegistros(List<RegistrosTable> registros) {
        this.registros = registros;
    }

    @Override
    public int getRowCount() {
        return registros.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RegistrosTable registroTable = registros.get(rowIndex);
        Object row = null;
        switch (columnIndex) {
            case COLUNA_EMPRESA:
                row = registroTable.getEmpresa();
                break;
            case COLUNA_CODIGO:
                row = registroTable.getCodigo();
                break;
            case COLUNA_TIPO:
                row = registroTable.getTipo();
                break;
            case COLUNA_HISTORICO:
                row = registroTable.getHistorico();
                break;
            case COLUNA_VALOR:
                row = brazilianFormat.format(registroTable.getValor());
                break;
            case COLUNA_CTDEBITO:
                row = registroTable.getContaDebito();
                break;
            case COLUNA_CTCREDITO:
                row = registroTable.getContaCredito();
                break;
        };
        return row;
    }

    public List<RegistrosTable> getDataSet() {
        return registros;
    }

    @Override
    public String getColumnName(int col) {
        return colunas[col];
    }
    
    public void clearRow() {
        registros.clear();
        fireTableDataChanged();
    }
    
    
}
