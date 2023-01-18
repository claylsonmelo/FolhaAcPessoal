/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package folhaacpessoal;

import Consulta.FolhaAcCons;
import Consulta.RegistroContabilCons;
import java.io.FileWriter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.C5Registro1;
import model.C5Registro2;
import model.C5Registro3;
import model.RegistroContabil;
import model.RegistroFolhaAc;
import model.RegistrosTable;
import util.FileUtils;

/**
 *
 * @author claylson
 */
public class ExportaLctoFolhaBean implements Serializable {

    private static List<RegistroFolhaAc> registrosAc;
    private static List<RegistroContabil> registrosContabeis;

    public List<RegistrosTable> tableModel(String competencia) {
        List<RegistrosTable> registros = new ArrayList<>();
        FolhaAcCons cons = new FolhaAcCons();
        registrosAc = cons.registros(competencia);
        RegistroContabilCons regCons = new RegistroContabilCons();
        registrosContabeis = regCons.registrosContabeis();
        for (RegistroFolhaAc regAc : registrosAc) {
            for (RegistroContabil regCont : registrosContabeis) {
                if (regCont.getCodigo().equals(regAc.getCodigo()) && regCont.getTipo().equals(regAc.getTipo())) {
                    RegistrosTable regTable = new RegistrosTable();
                    regTable.setEmpresa(regAc.getEmpresa().toString());
                    regTable.setCodigo(regCont.getCodigo());
                    regTable.setTipo(regCont.getTipo());
                    regTable.setHistorico(regCont.getHistorico());
                    regTable.setValor(regAc.getValor());
                    regTable.setContaDebito(regCont.getContaDebito());
                    regTable.setContaCredito(regCont.getContaCredito());
                    registros.add(regTable);
                }
            }
        }
        return registros;
    }

    public boolean exportaLayoutC5(List<RegistrosTable> registrosExp, String nomeArquivo) {
        List<C5Registro1> lancamentos = new ArrayList<>();
        boolean succes = false;
        for (RegistrosTable regAc : registrosExp) {
            C5Registro1 lancamento = c5Registro1Completo(regAc);
            if (null != lancamento) {
                lancamentos.add(lancamento);
            }
        }
        FileWriter fw = FileUtils.abreArquivoGerar(nomeArquivo, false);
        if (null != fw) {
            FileUtils.inserirConteudoLancto(fw, lancamentos);
            succes = FileUtils.fechaArquivoGerado(fw);
        }

        return succes;
    }

    private static C5Registro1 c5Registro1Completo(RegistrosTable regAc) {
        C5Registro1 reg1 = new C5Registro1();
        reg1.setNroEmpresaMatriz(2);
        reg1.setDataLancamento(dateToStr(new Date()));
        reg1.setNroLoteContabil(910);
        reg1.setNroLancamento(null);
        reg1.setIndExtemporaneo("N");
        reg1.setHistorico(regAc.getHistorico());

        List<C5Registro2> lsReg2 = new ArrayList<>();
        C5Registro2 reg2D = new C5Registro2();
        reg2D.setConta(regAc.getContaDebito());
        reg2D.setIndDebitoCredito("D");
        reg2D.setValor(DoubleToMoeda(regAc.getValor()));
        reg2D.setHistorico(regAc.getHistorico());
        C5Registro3 reg3 = new C5Registro3();
        reg3.setParametro("E");
        reg3.setValorParametro(regAc.getEmpresa());
        reg2D.setRegistro3(reg3);
        lsReg2.add(reg2D);
        C5Registro2 reg2C = new C5Registro2();
        reg2C.setConta(regAc.getContaCredito());
        reg2C.setIndDebitoCredito("C");
        reg2C.setValor(DoubleToMoeda(regAc.getValor()));
        reg2C.setHistorico(regAc.getHistorico());
        C5Registro3 reg3C = new C5Registro3();
        reg3C.setParametro("E");
        reg3C.setValorParametro(regAc.getEmpresa());
        reg2C.setRegistro3(reg3C);
        lsReg2.add(reg2C);
        reg1.setRegistro2(lsReg2);
        return reg1;//Se encontrar conta contabil no arquivo retonar o registro.

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
