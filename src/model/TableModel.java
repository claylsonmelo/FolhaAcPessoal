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
public class TableModel extends AbstractTableModel {

    private List<RegistrosTable> registros;
 private static final NumberFormat brazilianFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private final int COLUNA_EMPRESA = 0;
    private final int COLUNA_CODIGO = 1;
    private final int COLUNA_NOME = 2;
    private final int COLUNA_VALOR = 3;
    private final int COLUNA_CTDEBITO = 4;
    private final int COLUNA_CTCREDITO = 5;

    private final String[] colunas = {"Empresa", "Código", "Nome", "Valor", "Débito", "Crédito"};

    public TableModel(List<RegistrosTable> registros) {
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
            case COLUNA_NOME:
                row = registroTable.getNome();
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
