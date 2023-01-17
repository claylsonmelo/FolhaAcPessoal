/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package folhaacpessoal;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.TableModel;

/**
 *
 * @author claylson
 */
public class Principal extends JFrame {

    private JPanel painelFundo;
    private JPanel painelBotoes;
    private JTable tabela;
    private JScrollPane barraRolagem;
    private JButton btCarregar;
    private JButton btExportSelec;
    private JButton btSelecTodos;
    private TableModel modelo;
    private static final NumberFormat brazilianFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public Principal() {
        super("teste");
        criaJTable();
        criaJanela();

    }

    public void criaJanela() {
        btCarregar = new JButton("Carregar");
        btExportSelec = new JButton("Exportar Selecionados");
        btSelecTodos = new JButton("Seleciona Todos");
        painelBotoes = new JPanel();
        barraRolagem = new JScrollPane(tabela);
        painelFundo = new JPanel();
        painelFundo.setLayout(new BorderLayout());
        painelFundo.add(BorderLayout.CENTER, barraRolagem);
        painelBotoes.add(btCarregar);
        painelBotoes.add(btSelecTodos);
        painelBotoes.add(btExportSelec);
        painelFundo.add(BorderLayout.SOUTH, painelBotoes);

        getContentPane().add(painelFundo);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setVisible(true);
        btCarregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregar();
            }
        });
        btSelecTodos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabela.selectAll();
            }
        });
        btExportSelec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportar();
            }
        });
    }

    private void criaJTable() {
        tabela = new JTable(modelo);
         

    }

    private void carregar() {
        ExportaLctoFolhaBean eBean = new ExportaLctoFolhaBean();
        modelo = new TableModel(eBean.tableModel());
        tabela.setModel(modelo);    
    }
    
    private void exportar(){
        int[] selectedRows = tabela.getSelectedRows();
        for (int r=0;r<selectedRows.length;r++){
            
               System.out.println("sele: "+selectedRows[r]+" - "+modelo.getValueAt(selectedRows[r], 3));
            }
        
    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.setVisible(true);
    }
}
