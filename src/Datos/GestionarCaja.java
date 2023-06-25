package Datos;

import Componentes.Operacion;
import Componentes.generarCodigos;
import Modelo.Caja;

public class GestionarCaja {

    public static String getCodigo() {
        String cod = generarCodigos.getCodigo("SELECT MAX(ca_id) FROM caja");
        return cod;
    }

    public static String addCaja(Caja caja) {
        String msg;
        StringBuilder sql = new StringBuilder("INSERT INTO caja (ca_id, ca_fechainicio, ca_horainicio, ca_inicial, ca_final, ca_entregado, ca_diferencia, ca_usuarioinicio, ca_indicador) VALUES (");
        sql.append(getCodigo()).append(",'");
        sql.append(caja.getFechaI()).append("','");
        sql.append(caja.getHoraI()).append("',");
        //sql.append(caja.getHoraI()).append("','");
        //sql.append(caja.getFechaF()).append("','");
        //sql.append(caja.getHoraF()).append("',");
        sql.append(caja.getCaInicial()).append(",");
        sql.append(caja.getCaFinal()).append(",");
        sql.append(caja.getCaEntregado()).append(",");
        sql.append(caja.getCaDiferencia()).append(",'");
        sql.append(caja.getUsuarioI()).append("','S')");
        msg = Operacion.exeOperacion(sql.toString());
        return msg;
    }

    public static String actCaja(Caja caja) {
        String msg;
        StringBuilder sql = new StringBuilder("UPDATE caja SET ca_final=");
        sql.append(caja.getCaFinal());
        sql.append(", ca_entregado=");
        sql.append(caja.getCaEntregado());
        sql.append(", ca_diferencia=");
        sql.append(caja.getCaDiferencia());
        sql.append(" WHERE ca_id =");
        sql.append(caja.getCaId());
        sql.append("");
        msg = Operacion.exeOperacion(sql.toString());
        return msg;
    }
    
    public static String CerrarCaja(Caja caja) {
        String msg;
        StringBuilder sql = new StringBuilder("UPDATE caja SET ca_fechafin='");
        sql.append(caja.getFechaF());
        sql.append("', ca_horafin='");
        sql.append(caja.getHoraF());
        sql.append("', ca_final=");
        sql.append(caja.getCaFinal());
        sql.append(", ca_entregado=");
        sql.append(caja.getCaEntregado());
        sql.append(", ca_diferencia=");
        sql.append(caja.getCaDiferencia());
        sql.append(", ca_usuariocierre='");
        sql.append(caja.getUsuarioF());
        sql.append("', ca_indicador='N'");
        sql.append(" WHERE ca_id =");
        sql.append(caja.getCaId());
        sql.append("");
        msg = Operacion.exeOperacion(sql.toString());
        return msg;
    }

    public static Caja busCaja(String cod) {
        Caja caj = null;
        StringBuilder s = new StringBuilder("SELECT * FROM caja WHERE ca_id=");
        s.append(cod);
        s.append("");
        Object[] filaObt = Operacion.getFila(s.toString());
        if (filaObt != null) {
            caj = new Caja();
            caj.setCaId(Integer.valueOf(filaObt[0].toString()));
            caj.setFechaI(String.valueOf(filaObt[1].toString()));
            caj.setHoraI(String.valueOf(filaObt[2].toString()));
            //caj.setFechaF(String.valueOf(filaObt[3].toString()));
            //caj.setHoraF(String.valueOf(filaObt[4].toString()));
            caj.setCaInicial(Integer.parseInt(filaObt[5].toString()));
            caj.setCaFinal(Integer.parseInt(filaObt[6].toString()));
            caj.setCaEntregado(Integer.parseInt(filaObt[7].toString()));
            caj.setCaDiferencia(Integer.parseInt(filaObt[8].toString()));
            caj.setUsuarioI(String.valueOf(filaObt[9].toString()));
            //caj.setUsuarioF(String.valueOf(filaObt[10].toString()));
            caj.setIndicador(String.valueOf(filaObt[11].toString()));
            System.out.println("Encontrado");
        } else {
            System.out.println("No encontrado");
        }
        return caj;
    }
}
