/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Consulta;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.RegistroFolhaAc;
import util.DbUtils;

/**
 *
 * @author claylson
 */
public class FolhaAcCons implements Serializable {

    private static String SQL = "SELECT (SELECT CASE EST.SEQCNPJ WHEN '0001' THEN '0002' WHEN '0002' THEN '0001' ELSE EST.SEQCNPJ END AS EMPRE FROM EST WHERE EST.EMP_CODIGO=0311 AND EST.CODIGO = B.EMPRESA) AS EMPRESA,\n"
            + "	   B.TIPO,\n"
            + "	   B.CODIGO,\n"
            + "	   B.NOME,\n"
            + "	   B.INFPROVDESC,\n"
            + "	   B.VALORTOTAL\n"
            + "FROM \n"
            + "(SELECT a.est_codigo AS empresa, 'FOL' AS tipo, EFP.EVE_CODIGO AS CODIGO, EVE.NOME, eve.INFPROVDESC,SUM( EFP.VALOR) valortotal  FROM \n"
            + "(SELECT EFO.EMP_CODIGO, SEP.EST_CODIGO, EFO.FOL_SEQ , EFO.EPG_CODIGO\n"
            + "FROM FOL, EFO, SEP\n"
            + "WHERE FOL.EMP_CODIGO = EFO.EMP_CODIGO \n"
            + "AND   FOL.SEQ = EFO.FOL_SEQ \n"
            + "AND   FOL.FOL_SEQ_PAI IS NULL\n"
            + "AND   EFO.EMP_CODIGO = SEP.EMP_CODIGO \n"
            + "AND   EFO.EPG_CODIGO = SEP.EPG_CODIGO \n"
            + "AND   SEP.EMP_CODIGO = 0311\n"
            + "AND   EFO.SEP_DATA   = SEP.\"DATA\"\n"
            + "AND   FOL.FOLHA = 2\n"
            + "AND   EXTRACT(YEAR FROM FOL.DTCALCULO)||LPAD(EXTRACT(MONTH FROM FOL.DTCALCULO),2,'0') = ?\n"
            + ") A\n"
            + "LEFT JOIN EFP \n"
            + "	ON  EFP.EMP_CODIGO = A.EMP_CODIGO \n"
            + "	AND EFP.EFO_FOL_SEQ = A.FOL_SEQ\n"
            + "	AND EFP.EFO_EPG_CODIGO = A.EPG_CODIGO \n"
            + "LEFT JOIN EVE\n"
            + "	ON EFP.EMP_CODIGO = EVE.EMP_CODIGO \n"
            + "	AND EFP.EVE_CODIGO  = EVE.CODIGO\n"
            + "GROUP BY a.est_codigo, EFP.EVE_CODIGO, EVE.NOME, eve.INFPROVDESC\n"
            + "union\n"
            + "SELECT a.est_codigo AS empresa, 'FER' AS tipo, EFP.EVE_CODIGO AS CODIGO, EVE.NOME, eve.INFPROVDESC,SUM( EFP.VALOR) valorTotal FROM \n"
            + "(SELECT EFO.EMP_CODIGO, SEP.EST_CODIGO, EFO.FOL_SEQ , EFO.EPG_CODIGO\n"
            + "FROM FOL, EFO, SEP, FER\n"
            + "WHERE FOL.EMP_CODIGO = EFO.EMP_CODIGO \n"
            + "AND   FOL.SEQ = EFO.FOL_SEQ \n"
            + "AND   FOL.FOL_SEQ_PAI IS NULL\n"
            + "AND   EFO.EMP_CODIGO = SEP.EMP_CODIGO \n"
            + "AND   EFO.EPG_CODIGO = SEP.EPG_CODIGO \n"
            + "AND   EFO.SEP_DATA   = SEP.\"DATA\"\n"
            + "AND   SEP.EMP_CODIGO = 0311\n"
            + "AND   FER.EMP_CODIGO = EFO.EMP_CODIGO \n"
            + "AND   FER.EFO_FOL_SEQ = EFO.FOL_SEQ \n"
            + "AND   FER.EFO_EPG_CODIGO = EFO.EPG_CODIGO \n"
            + "AND   EXTRACT(YEAR FROM FER.DTGOZOINICIAL)||LPAD(EXTRACT(MONTH FROM FER.DTGOZOINICIAL),2,'0') = ?\n"
            + ") A\n"
            + "LEFT JOIN EFP \n"
            + "	ON  EFP.EMP_CODIGO = A.EMP_CODIGO \n"
            + "	AND EFP.EFO_FOL_SEQ = A.FOL_SEQ\n"
            + "	AND EFP.EFO_EPG_CODIGO = A.EPG_CODIGO \n"
            + "LEFT JOIN EVE\n"
            + "	ON EFP.EMP_CODIGO = EVE.EMP_CODIGO \n"
            + "	AND EFP.EVE_CODIGO  = EVE.CODIGO\n"
            + "GROUP BY a.est_codigo, EFP.EVE_CODIGO, EVE.NOME, eve.INFPROVDESC) B\n"
            + "ORDER BY B.EMPRESA, B.CODIGO";

    public List<RegistroFolhaAc> registros(String competencia) {
        List<RegistroFolhaAc> listaReg = new ArrayList<>();
        PreparedStatement ps = DbUtils.createStatement(SQL);
        DbUtils.setStrParam(ps, 1, competencia);
        DbUtils.setStrParam(ps, 2, competencia);
        ResultSet rs = DbUtils.executeQuery(ps);
        while (DbUtils.next(rs)) {
            listaReg.add(registroFolhaFromRs(rs));
        }
        DbUtils.closeResultSet(rs);
        DbUtils.closeStatement(ps, true);
        return listaReg;
    }

    private RegistroFolhaAc registroFolhaFromRs(ResultSet rs) {
        RegistroFolhaAc rf = new RegistroFolhaAc();
        rf.setEmpresa(DbUtils.getIntValue(rs, "empresa"));
        rf.setTipo(DbUtils.getStrValue(rs, "tipo"));
        rf.setCodigo(DbUtils.getStrValue(rs, "codigo"));
        rf.setNome(DbUtils.getStrValue(rs, "nome"));
        rf.setIndProvDesc(DbUtils.getStrValue(rs, "infprovdesc"));
        rf.setValor(DbUtils.getDoubleValue(rs, "valortotal"));
        return rf;
    }

}
