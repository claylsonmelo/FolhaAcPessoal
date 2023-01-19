/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Consulta;

import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import model.Empresa;
import model.RegistroContabil;
import util.FileUtils;

/**
 *
 * @author claylson
 */
public class RegistroContabilCons implements Serializable {

    public List<RegistroContabil> registrosContabeis() {
        List<RegistroContabil> lista = new ArrayList<>();
        File file = new File(System.getProperty("user.dir") + File.separator + "RegistroFolhaContabil.txt");
        FileReader fr = FileUtils.abreArquivoLer(file);
        List<String> conteudoarquivo = FileUtils.lerConteudo(fr);
        FileUtils.fechaArquivoLido(fr);
        for (String linha : conteudoarquivo) {
            lista.add(registroToLinhaString(linha));
        }
        return lista;
    }

    public List<Empresa> empresas() {
        List<Empresa> emp = new ArrayList<>();
        File file = new File(System.getProperty("user.dir") + File.separator + "Empresa.txt");
        FileReader fr = FileUtils.abreArquivoLer(file);
        List<String> conteudoarquivo = FileUtils.lerConteudo(fr);
        FileUtils.fechaArquivoLido(fr);
        for (String linha : conteudoarquivo) {
            emp.add(empresaFromLinhaString(linha));
        }
        return emp;
    }

    private Empresa empresaFromLinhaString(String linha) {
        String[] linhaSplit = linha.split("\\|");
        Empresa emp = new Empresa();
        emp.setNroFortes(linhaSplit[0]);
        emp.setNroErp(Integer.valueOf(linhaSplit[1]));
        emp.setNome(linhaSplit[2]);
        return emp;
    }

    private RegistroContabil registroToLinhaString(String linha) {
        String[] linhaSplit = linha.split("\\|");
        RegistroContabil registro = new RegistroContabil();
        registro.setCodigo(linhaSplit[0]);
        registro.setTipo(linhaSplit[1]);
        registro.setContaDebito(linhaSplit[2]);
        registro.setContaCredito(linhaSplit[3]);
        registro.setHistorico(removerAcentos(linhaSplit[4]));
        return registro;

    }

    public String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
