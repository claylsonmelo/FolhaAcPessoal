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

    private RegistroContabil registroToLinhaString(String linha) {
        String[] linhaSplit = linha.split("\\|");
        RegistroContabil registro = new RegistroContabil();
        registro.setCodigo(linhaSplit[0]);
        registro.setContaDebito(linhaSplit[1]);
        registro.setContaCredito(linhaSplit[2]);
        registro.setHistorico(removerAcentos(linhaSplit[3]));
        return registro;

    }

    public String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}
