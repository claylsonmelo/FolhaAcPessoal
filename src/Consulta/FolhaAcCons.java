/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Consulta;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Empresa;
import model.RegistroFolhaAc;
import util.DbUtils;

/**
 *
 * @author claylson
 */
public class FolhaAcCons implements Serializable {

    private static String SQL = "SELECT * FROM PROC_ITM_LISTALCTOFOLHAC5(?,?) WHERE tipo IS NOT NULL AND codigo IS NOT null ORDER BY empresa, tipo";

    public List<RegistroFolhaAc> registros(Date competencia) {
        List<RegistroFolhaAc> listaReg = new ArrayList<>();
        RegistroContabilCons cons = new RegistroContabilCons();
        List<Empresa> empresas = cons.empresas();
        if (!empresas.isEmpty()) {
            Connection conn = DbUtils.createConnection();
            for (Empresa empresa : empresas) {
                listaReg.addAll(registrosPorEmpresa(conn, dateToCompetencia(competencia), empresa));
            }
            DbUtils.closeConnection(conn);
        }
        return listaReg;
    }

    public List<RegistroFolhaAc> registrosPorEmpresa(Connection conn, String competencia, Empresa empresa) {
        List<RegistroFolhaAc> listaReg = new ArrayList<>();
        PreparedStatement ps = DbUtils.createStatement(conn, SQL);
        DbUtils.setStrParam(ps, 1, empresa.getNroFortes());
        DbUtils.setStrParam(ps, 2, competencia);
        ResultSet rs = DbUtils.executeQuery(ps);
        while (DbUtils.next(rs)) {
            listaReg.add(registroFolhaFromRs(rs, empresa));
        }
        DbUtils.closeResultSet(rs);
        DbUtils.closeStatement(ps);
        return listaReg;
    }

    private RegistroFolhaAc registroFolhaFromRs(ResultSet rs, Empresa emp) {
        RegistroFolhaAc rf = new RegistroFolhaAc();
        rf.setEmpresa(emp);
        rf.setTipo(DbUtils.getStrValue(rs, "tipo"));
        rf.setCodigo(DbUtils.getStrValue(rs, "codigo"));
        rf.setNome(DbUtils.getStrValue(rs, "nome"));
        rf.setIndProvDesc(DbUtils.getStrValue(rs, "infprovdesc"));
        rf.setValor(DbUtils.getDoubleValue(rs, "valortotal"));
        return rf;
    }

     private String dateToCompetencia(Date datLancto) {
        SimpleDateFormat formatToStr = new SimpleDateFormat("yyyyMM");
        return formatToStr.format(datLancto);
    }
    
}
