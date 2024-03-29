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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
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
    private JButton btExcluirSelec;
    private JButton btSelecTodos;
    private JButton btLocalizar;
    private JButton btLimpar;
    private JTextField textPathExportacao;
    private JFormattedTextField textDtaLancto;
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
            btExcluirSelec = new JButton("Excluir Selecionados");
            btSelecTodos = new JButton("Seleciona Todos");
            btLocalizar = new JButton("Localizar");
            btLimpar = new JButton("Limpar");
            textPathExportacao = new JTextField(20);
            textDtaLancto = new JFormattedTextField(new MaskFormatter("##/##/##"));
            textDtaLancto.setColumns(7);
            textDtaLancto.setFont(textDtaLancto.getFont().deriveFont(16F));
            textDtaLancto.setEnabled(false);
            textPathExportacao.setFont(textPathExportacao.getFont().deriveFont(16F));
            painelBotoes = new JPanel();
            painelFundo = new JPanel();
            barraRolagem = new JScrollPane(tabela);
            painelFundo.setLayout(new BorderLayout());
            painelCarregar = new JPanel(new FlowLayout(FlowLayout.LEFT));
            painelCarregar.add(textPathExportacao);
            painelCarregar.add(btLocalizar);
            painelCarregar.add(textDtaLancto);
            painelCarregar.add(btCarregar);
            painelCarregar.add(btLimpar);
            painelFundo.add(BorderLayout.NORTH, painelCarregar);
            painelFundo.add(BorderLayout.CENTER, barraRolagem);
            painelBotoes.add(btSelecTodos);
            painelBotoes.add(btExportSelec);
            painelBotoes.add(btExcluirSelec);
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
                            textDtaLancto.setEnabled(true);
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
                    gerirTable("G");
                }
            });
            btExcluirSelec.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gerirTable("E");
                }
            });

            btLimpar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textPathExportacao.setText("");
                    textDtaLancto.setText("");
                    textDtaLancto.setEnabled(false);
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
        Date datComp = dtaLanctoToDate();
        if (null != datComp && datComp.before(new Date())) {
            //Popula tabela
            modelo = new TableModelRegistros(exportaBean.tableModelRegistro(datComp));
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
            btCarregar.setEnabled(false);
            textDtaLancto.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Campo Data Lancto inválido!", "Atenção!", JOptionPane.WARNING_MESSAGE);
        }
    }

    private Date dtaLanctoToDate() {
        if (!"".equals(textDtaLancto.getText().trim().replace("/", ""))) {
            try {
                if (isDateValid(textDtaLancto.getText())) {
                    SimpleDateFormat formatToDate = new SimpleDateFormat("dd/MM/yy");
                    return formatToDate.parse(textDtaLancto.getText());
                }
            } catch (ParseException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static boolean isDateValid(String strDate) {
        String dateFormat = "dd/MM/uu";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(dateFormat)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate date = LocalDate.parse(strDate, dateTimeFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void gerirTable(String tipo) {
        int[] selectedRows = tabela.getSelectedRows();
        if (selectedRows.length > 0) {
            boolean success = false;
            if ("E".equals(tipo)) {
                for (int r = 0; r < selectedRows.length; r++) {
                    modelo.removeRow(selectedRows[r]);
                    System.out.println("row: "+selectedRows[r]);
                    //Verificar Deleção não está sendo feito toda seleção
                }
                JOptionPane.showMessageDialog(null, "Registros Excluidos!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
            } else {
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
                success = exportaBean.exportaLayoutC5(regSelecionados, textPathExportacao.getText(), dtaLanctoToDate());
                if (success) {
                    JOptionPane.showMessageDialog(null, "Arquivo Gerado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possível gerar o arquivo.", "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione pelo menos um registro!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
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
