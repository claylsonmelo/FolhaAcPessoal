/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.C5Registro1;
import model.C5Registro2;
import model.C5Registro3;

/**
 *
 * @author claylson
 */
public class FileUtils implements Serializable {

    public static FileWriter abreArquivoGerar(String fileCompleto, boolean adiciona) {

        try {
            File file = new File(fileCompleto);
            return new FileWriter(file, adiciona);

        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, "Falha ao Criar arquivo", ex);
        }
        return null;
    }

    private static String reg1ToLinha(C5Registro1 reg1) {
        StringBuilder sb = new StringBuilder();
        sb.append(reg1.getCodigoRegitro());
        sb.append("|");
        sb.append(reg1.getNroEmpresaMatriz());
        sb.append("|");
        sb.append(reg1.getDataLancamento());
        sb.append("|");
        sb.append(reg1.getNroLoteContabil());
        sb.append("|");
        sb.append(reg1.getNroLancamento());
        sb.append("|");
        sb.append(reg1.getHistorico());
        sb.append("|");
        sb.append(reg1.getIndExtemporaneo());
        sb.append("|");
        sb.append(reg1.getDataExtemporaneo());
        return sb.toString().replace("null", "");
    }

    private static String reg2ToLinha(C5Registro2 reg2) {
        StringBuilder sb = new StringBuilder();
        sb.append(reg2.getCodigoRegistro());
        sb.append("|");
        sb.append(reg2.getConta());
        sb.append("|");
        sb.append(reg2.getIndDebitoCredito());
        sb.append("|");
        sb.append(reg2.getValor());
        sb.append("|");
        sb.append(reg2.getHistorico());
        return sb.toString();
    }

    private static String reg3ToLinha(C5Registro3 reg3) {
        StringBuilder sb = new StringBuilder();
        sb.append(reg3.getCodigoRegistro());
        sb.append("|");
        sb.append(reg3.getParametro());
        sb.append("|");
        sb.append(reg3.getValorParametro());
        return sb.toString();
    }

    public static boolean inserirConteudoLancto(FileWriter fw, List<C5Registro1> lancamentos) {
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            int tamanho = lancamentos.size();
            for (C5Registro1 lcto : lancamentos) {
                bw.write(reg1ToLinha(lcto));
                bw.newLine();
                for (C5Registro2 lctoReg2 : lcto.getRegistro2()) {
                    bw.write(reg2ToLinha(lctoReg2));
                    bw.newLine();
                    bw.write(reg3ToLinha(lctoReg2.getRegistro3()));
                    if(tamanho != lancamentos.indexOf(lcto)){
                    bw.newLine();    
                    }
                }
            }
        
  
            bw.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean inserirConteudo(FileWriter fw, List<String> conteudo) {
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            int tamanho = conteudo.size();
            for (String linha : conteudo) {
                bw.write(linha);
                if (linha.indexOf(linha) != tamanho) {
                    bw.newLine();
                }
            }
            bw.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean inserirConteudo(FileWriter fw, String conteudo) {
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(conteudo);
            bw.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean fechaArquivoGerado(FileWriter fw) {
        try {
            fw.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static File[] listarArquivosDiretorio(String pathDiretorio) {
        File diretorio = diretorioDoArquivo(pathDiretorio);
        File files[] = diretorio.listFiles();
        return files;
    }

    public static FileInputStream fileToFileInputStr(File arquivo) {
        try {
            return new FileInputStream(arquivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static FileOutputStream fileToFileOutputStr(File arquivo) {

        try {
            return new FileOutputStream(arquivo);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static File diretorioDoArquivo(String pathDiretorio) {
        File diretorio = new File(pathDiretorio);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
        return diretorio;
    }

    public static boolean deletarArquivo(File arquivo) {
        return arquivo.delete();
    }

    public static File renomearArquivo(File arquivo, String novonome) {
        String novoarquivostr = arquivo.getAbsolutePath().replace(arquivo.getName(), novonome);
        File novoArquivo = new File(novoarquivostr);
        if (arquivo.renameTo(novoArquivo)) {
            return novoArquivo;
        }
        return arquivo;
    }

    public static FileReader abreArquivoLer(File arquivo) {

        try {
            return new FileReader(arquivo);

        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, "Falha ao Ler arquivo", ex);
        }
        return null;
    }

    public static String fileToString(String path, String nomeArquivo, Charset encoding) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path + File.separator + nomeArquivo));
            return new String(encoded, encoding);
        } catch (IOException ex) {
            System.out.println("Erro Leitura arquivo sql: " + ex.getMessage());
            //Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static List<String> lerConteudo(FileReader fr) {
        BufferedReader br = new BufferedReader(fr);
        List<String> linhas = new ArrayList<>();
        String linha = "";
        try {
            while (br.ready()) {
                linhas.add(br.readLine());
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return linhas;
    }

    public static StringBuilder lerConteudoSb(FileReader fr) {
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();

        try {
            while (br.ready()) {
                sb.append(br.readLine());
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb;
    }

    public static boolean fechaArquivoLido(FileReader fr) {
        try {
            fr.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
