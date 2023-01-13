/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package folhaacpessoal;

import Consulta.FolhaAcCons;
import Consulta.RegistroContabilCons;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.C5Registro1;
import model.C5Registro2;
import model.C5Registro3;
import model.RegistroContabil;
import model.RegistroFolhaAc;
import sun.misc.FileURLMapper;
import util.FileUtils;

/**
 *
 * @author claylson
 */
public class FolhaACPessoal {

    private static List<RegistroFolhaAc> registrosAc;
    private static List<RegistroContabil> registrosContabeis;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FolhaAcCons cons = new FolhaAcCons();
        registrosAc = cons.registros("202210");
        RegistroContabilCons regCons = new RegistroContabilCons();
        registrosContabeis = regCons.registrosContabeis();
        List<C5Registro1> lancamentos = new ArrayList<>();

        for (RegistroFolhaAc regAc : registrosAc) {
            C5Registro1 lancamento = c5Registro1Completo(regAc);
            if (null != lancamento) {
                lancamentos.add(lancamento);
            }
        }
        FileWriter fw = FileUtils.abreArquivoGerar("", "teste.txt", false);
        FileUtils.inserirConteudoLancto(fw, lancamentos);
        FileUtils.fechaArquivoGerado(fw);

    }

    private static C5Registro1 c5Registro1Completo(RegistroFolhaAc regAc) {
        C5Registro1 reg1 = new C5Registro1();
        reg1.setNroEmpresaMatriz(2);
        reg1.setDataLancamento(dateToStr(new Date()));
        reg1.setNroLoteContabil(910);
        reg1.setNroLancamento(null);
        reg1.setIndExtemporaneo("N");
        for (RegistroContabil regCont : registrosContabeis) {
            if (regCont.getCodigo().equals(regAc.getCodigo())) {
                List<C5Registro2> lsReg2 = new ArrayList<>();
                reg1.setHistorico(regCont.getHistorico());
                C5Registro2 reg2D = new C5Registro2();
                reg2D.setConta(regCont.getContaDebito());
                reg2D.setIndDebitoCredito("D");
                reg2D.setValor(DoubleToMoeda(regAc.getValor()));
                reg2D.setHistorico(regCont.getHistorico());
                C5Registro3 reg3 = new C5Registro3();
                reg3.setParametro("E");
                reg3.setValorParametro(regAc.getEmpresa().toString());
                reg2D.setRegistro3(reg3);
                lsReg2.add(reg2D);
                C5Registro2 reg2C = new C5Registro2();
                reg2C.setConta(regCont.getContaCredito());
                reg2C.setIndDebitoCredito("C");
                reg2C.setValor(DoubleToMoeda(regAc.getValor()));
                reg2C.setHistorico(regCont.getHistorico());
                C5Registro3 reg3C = new C5Registro3();
                reg3C.setParametro("E");
                reg3C.setValorParametro(regAc.getEmpresa().toString());
                reg2C.setRegistro3(reg3C);
                lsReg2.add(reg2C);
                reg1.setRegistro2(lsReg2);
                return reg1;//Se encontrar conta contabil no arquivo retonar o registro.
            }
        }
        return null;
    }

    private static String dateToStr(Date d) {
        SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");
        String data = (sdff.format(d));
        return data;
    }

    private static String DoubleToMoeda(Double d) {
        String formatado = new DecimalFormat("#.00").format(d);
        return formatado;

    }

}
