/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package folhaacpessoal;

import Consulta.RegistroContabilCons;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;
import model.Empresa;
import model.RegistrosTable;
import model.TableModelRegistros;

/**
 *
 * @author claylson
 */
public class Principal extends JFrame {

    private JPanel painelFundo;
    private JPanel painelBotoes;
    private JPanel painelCarregar;
    private JTable tabela;
    private JScrollPane barraRolagem;
    private JButton btCarregar;
    private JButton btExportSelec;
    private JButton btSelecTodos;
    private JButton btLocalizar;
    private JButton btLimpar;
    private JTextField textPathExportacao;
    private JFormattedTextField textCompetencia;
    private TableModelRegistros modelo;
    private static final NumberFormat brazilianFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    ExportaLctoFolhaBean exportaBean = new ExportaLctoFolhaBean();
   

    public Principal() {
        super("Arquivo Lançamento Folha Fortes X Consinco");
        criaJTable();
        criaJanela();

    }

    public void criaJanela() {

        try {
            
            btCarregar = new JButton("Carregar");
            btCarregar.setEnabled(false);
            btExportSelec = new JButton("Exportar Selecionados");
            btSelecTodos = new JButton("Seleciona Todos");
            btLocalizar = new JButton("Localizar");
            btLimpar = new JButton("Limpar");
            textPathExportacao = new JTextField(20);
            textCompetencia = new JFormattedTextField(new MaskFormatter("####/##"));
            textCompetencia.setColumns(5);
            textCompetencia.setFont(textCompetencia.getFont().deriveFont(16F));
            textCompetencia.setEnabled(false);
            textPathExportacao.setFont(textPathExportacao.getFont().deriveFont(16F));
            painelBotoes = new JPanel();
            painelFundo = new JPanel();
            barraRolagem = new JScrollPane(tabela);
            painelFundo.setLayout(new BorderLayout());
            painelCarregar = new JPanel(new FlowLayout(FlowLayout.LEFT));
            painelCarregar.add(textPathExportacao);
            painelCarregar.add(btLocalizar);
            painelCarregar.add(textCompetencia);
            painelCarregar.add(btCarregar);
            painelCarregar.add(btLimpar);
            painelFundo.add(BorderLayout.NORTH, painelCarregar);
            painelFundo.add(BorderLayout.CENTER, barraRolagem);
            painelBotoes.add(btSelecTodos);
            painelBotoes.add(btExportSelec);
            painelFundo.add(BorderLayout.SOUTH, painelBotoes);

            getContentPane().add(painelFundo);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(1000, 600);
            setVisible(true);
            btLocalizar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser localizador = new JFileChooser();
                    localizador.setDialogTitle("Selecione o Arquivo");
                    FileNameExtensionFilter csv = new FileNameExtensionFilter("CSV (Separado por Virgula)(.csv)", "csv", "CSV");
                    localizador.addChoosableFileFilter(csv);
                    localizador.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int option = localizador.showOpenDialog(null);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File file = localizador.getSelectedFile();
                        try {
                            textPathExportacao.setText(file.getCanonicalPath() + ".CSV");
                            textPathExportacao.setEditable(false);
                            btCarregar.setEnabled(true);
                            textCompetencia.setEnabled(true);
                        } catch (IOException ex) {
                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Nenhum arquivo selecionado!");
                    }
                }
            });
            btCarregar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doCarregarTabela();
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

            btLimpar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textPathExportacao.setText("");
                    textCompetencia.setText("");
                    textCompetencia.setEnabled(false);
                    btCarregar.setEnabled(false);
                    modelo.clearRow();
                }
            });
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void criaJTable() {
        tabela = new JTable(modelo);

    }

    private void doCarregarTabela() {
        if (textCompetencia.getText().replace(" ", "").replace("/", "").length() == 6) {
            //Popula tabela
            modelo = new TableModelRegistros(exportaBean.tableModelRegistro(textCompetencia.getText().replace("/", "")));
            tabela.setModel(modelo);
            //Ajusta largura colunas
            Dimension tableSize = tabela.getPreferredSize();
            tabela.getColumn("Empresa").setPreferredWidth(50);
            tabela.getColumn("Código").setPreferredWidth(50);
            tabela.getColumn("Tipo").setPreferredWidth(50);
            tabela.getColumn("Débito").setPreferredWidth(50);
            tabela.getColumn("Crédito").setPreferredWidth(50);
            tabela.getColumn("Histórico").setPreferredWidth(Math.round((tableSize.width - 250) * 0.80f));
            tabela.getColumn("Valor").setPreferredWidth(Math.round((tableSize.width - 250) * 0.20f));
        } else {
            JOptionPane.showMessageDialog(null, "Campo Competência inválido!", "Atenção!", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void exportar() {

        int[] selectedRows = tabela.getSelectedRows();
        if (selectedRows.length > 0) {
            RegistroContabilCons consCont = new RegistroContabilCons();
            List<Empresa> empresas = consCont.empresas();
            List<RegistrosTable> regSelecionados = new ArrayList<>();
            for (int r = 0; r < selectedRows.length; r++) {
                RegistrosTable regTabSel = new RegistrosTable();
                regTabSel.setEmpresa(getNroEmpresaErp(empresas, modelo.getValueAt(selectedRows[r], 0).toString()));
                regTabSel.setCodigo(modelo.getValueAt(selectedRows[r], 1).toString());
                regTabSel.setTipo(modelo.getValueAt(selectedRows[r], 2).toString());
                regTabSel.setHistorico(modelo.getValueAt(selectedRows[r], 3).toString());
                regTabSel.setValor(Double.valueOf(modelo.getValueAt(selectedRows[r], 4).toString()
                        .replace("R$ ", "")
                        .replace(".", "")
                        .replace(",", ".")));
                regTabSel.setContaDebito(modelo.getValueAt(selectedRows[r], 5).toString());
                regTabSel.setContaCredito(modelo.getValueAt(selectedRows[r], 6).toString());
                regSelecionados.add(regTabSel);
            }
            boolean success = exportaBean.exportaLayoutC5(regSelecionados, textPathExportacao.getText());
            if (success) {
                JOptionPane.showMessageDialog(null, "Arquivo Gerado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível gerar o arquivo.", "Erro!", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private Empresa getNroEmpresaErp(List<Empresa> empresas, String nome) {
        for (Empresa empresa : empresas) {
            if (empresa.getNome().equals(nome)) {
                return empresa;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.setVisible(true);
    }
}
