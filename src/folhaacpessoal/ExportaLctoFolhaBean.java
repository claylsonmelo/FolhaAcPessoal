/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package folhaacpessoal;

import Consulta.FolhaAcCons;
import Consulta.RegistroContabilCons;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.C5Registro1;
import model.RegistroContabil;
import model.RegistroFolhaAc;
import model.RegistrosTable;

/**
 *
 * @author claylson
 */
public class ExportaLctoFolhaBean implements Serializable {

    private static List<RegistroFolhaAc> registrosAc;
    private static List<RegistroContabil> registrosContabeis;

    public List<RegistrosTable> tableModel() {
        List<RegistrosTable> registros = new ArrayList<>();
        FolhaAcCons cons = new FolhaAcCons();
        registrosAc = cons.registros("202212");
        RegistroContabilCons regCons = new RegistroContabilCons();
        registrosContabeis = regCons.registrosContabeis();
        for (RegistroFolhaAc regAc : registrosAc) {
            for (RegistroContabil regCont : registrosContabeis) {
                if (regCont.getCodigo().equals(regAc.getCodigo())) {
                    RegistrosTable regTable = new RegistrosTable();
                    regTable.setEmpresa(regAc.getEmpresa().toString());
                    regTable.setCodigo(regCont.getCodigo());
                    regTable.setNome(regCont.getHistorico());
                    regTable.setValor(regAc.getValor());
                    regTable.setContaDebito(regCont.getContaDebito());
                    regTable.setContaCredito(regCont.getContaCredito());
                    registros.add(regTable);
                }
            }
        }
        return registros;
    }
}
