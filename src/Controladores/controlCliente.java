package Controladores;

import Componentes.Fecha;
import Componentes.Login;
import Componentes.Mensajes;
import Datos.GestionarCliente;
import IU.dlgClientes;
import IU.dlgGestClientes;
import IU.dlgGestClientes1;
import Modelo.ClienteMovil;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class controlCliente {

    static String UsuarioL = "";

    public static void aModificar() {
        int x = dlgClientes.tablaClientes.getSelectedRow();
        String cod = dlgClientes.tablaClientes.getValueAt(x, 0).toString();
        ClienteMovil c = GestionarCliente.busCliente(cod);
        dlgGestClientes.lblCodC.setText(String.valueOf(c.getidCliente()));
        dlgGestClientes.cbCiudad.setSelectedIndex(c.getCodCiudad());
        dlgGestClientes.txtRazonS.setText(c.getRazonSocial());
        dlgGestClientes.txtRuc.setText(c.getRuc());
        dlgGestClientes.txtTelefono.setText(c.getTelefono());
        dlgGestClientes.txtDireccion.setText(c.getDireccion());
    }

    public static ClienteMovil capturarCampos() {
        ClienteMovil c = null;
        String telf;
        int codC = Integer.parseInt(dlgGestClientes.lblCodC.getText());
        int codCi = Integer.parseInt(dlgGestClientes.lbCiudad.getText());
        String razonS = dlgGestClientes.txtRazonS.getText().toUpperCase();
        String ruc = dlgGestClientes.txtRuc.getText().toUpperCase();
        if (dlgGestClientes.txtTelefono.getText().isEmpty()) {
            telf = "' '";
        } else {
            telf = dlgGestClientes.txtTelefono.getText();
        }
        String direc = dlgGestClientes.txtDireccion.getText().toUpperCase();
        c = new ClienteMovil(codC, razonS, ruc, direc, telf, codCi);
        return c;
    }

    public static ClienteMovil capturarCampos1() {
        ClienteMovil c = null;
        String telf;
        int codC = Integer.parseInt(dlgGestClientes1.lblCodC.getText());
        int codCi = Integer.parseInt(dlgGestClientes1.lbCiudad.getText());
        String razonS = dlgGestClientes1.txtRazonS.getText().toUpperCase();
        String ruc = dlgGestClientes1.txtRuc.getText().toUpperCase();
        if (dlgGestClientes1.txtTelefono.getText().isEmpty()) {
            telf = "' '";
        } else {
            telf = dlgGestClientes1.txtTelefono.getText();
        }
        String direc = dlgGestClientes1.txtDireccion.getText().toUpperCase();
        c = new ClienteMovil(codC, razonS, ruc, direc, telf, codCi);
        return c;
    }

    public static String addCliente() {
        String msg;
        ClienteMovil c = capturarCampos();
        msg = GestionarCliente.addCliente(c);
        if (msg == null) {
            Mensajes.informacion("Cliente Registrado");
        } else {
            Mensajes.error(msg);
        }
        return msg;
    }

    public static String addCliente1() {
        String msg;
        ClienteMovil c = capturarCampos1();
        msg = GestionarCliente.addCliente(c);
        if (msg == null) {
            Mensajes.informacion("Cliente Registrado");
        } else {
            Mensajes.error(msg);
        }
        return msg;
    }

    public static String actCliente() {
        String msg;
        ClienteMovil c = capturarCampos();
        msg = GestionarCliente.actCliente(c);
        if (msg == null) {
            Mensajes.informacion("Cliente Actualizado");
        } else {
            Mensajes.error(msg);
        }
        return msg;
    }

    public static String delCliente() {
        int x = dlgClientes.tablaClientes.getSelectedRow();
        String msg;
        String cod = dlgClientes.tablaClientes.getValueAt(x, 0).toString();
        String usuario = UsuarioL = Login.getUsuarioLogueado();
        msg = GestionarCliente.delCliente(cod);
        if (msg == null) {
            Mensajes.informacion("Cliente Eliminado");
        } else {
            Mensajes.error(msg);
        }
        return msg;
    }

    public static void listClientes(JTable tabla, String cod) {
        List lista = null;
        lista = GestionarCliente.listClientes(cod);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            tb.addRow(fila);
        }
    }

    public static void listClientesContaduria(JTable tabla) {
        List lista;
        lista = GestionarCliente.listClientesContaduria();
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0].toString();
            fila[1].toString();
            if (fila[2].toString().contains("-")) {
                String ruc = fila[2].toString();
                Object[] parts = ruc.split("-");
                fila[2] = parts[0].toString();
                fila[3] = parts[1].toString();
            } else {
                fila[2].toString();
                fila[3]=null;
            }

            tb.addRow(fila);
        }
    }
    
    public static void listActosGravados(JTable tabla) {
        List lista;
        lista = GestionarCliente.listActosGravados();
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0].toString();
            fila[1].toString();
            fila[2].toString();
            tb.addRow(fila);
        }
    }
    
    public static void listVCContaduria(JTable tabla, String desde, String hasta) {
        List lista;
        lista = GestionarCliente.listVCContaduria(desde, hasta);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0]= fila[0].toString();
            fila[1]= fila[1].toString()+"-"+fila[2].toString();
            fila[2]= Fecha.formatoFechaVMRInverter(fila[3].toString());
            fila[3]= Integer.parseInt(fila[4].toString());
            if(fila[5].toString().equals("CONTADO")){
                fila[4]= "1";
            }else if(fila[5].toString().equals("CRÉDITO")){
                fila[4]= "2";
            }
            if(fila[6] == null){
               fila[5] =""; 
            }else{
                fila[5] = fila[6].toString();
            }
            fila[6] = "1";

            tb.addRow(fila);
        }
    }
    
    public static void listVDContaduria(JTable tabla, String desde, String hasta) {
        List lista;
        lista = GestionarCliente.listVDContaduria(desde, hasta);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0]= fila[2].toString();
            fila[1]= fila[3].toString()+"-"+fila[4].toString();
            fila[2]= Fecha.formatoFechaVMRInverter(fila[5].toString());
            fila[3]= Integer.parseInt(fila[6].toString());
            if(fila[7].toString().equals("CONTADO")){
                fila[4]= "1";
            }else if(fila[7].toString().equals("CRÉDITO")){
                fila[4]= "2";
            }
            if(fila[8] == null){
                fila[5]="";
            }else{
                fila[5]= fila[8].toString();
            }
            fila[6] = fila[9].toString();
            fila[7] = fila[10].toString();
            fila[8] = fila[11].toString();
            fila[9] = "0";
            fila[10] = "0";
            tb.addRow(fila);
        }
    }

    public static void filtClientes(JTable tabla, String cad) {
        List lista = null;
        lista = GestionarCliente.filRazonS(cad);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            tb.addRow(fila);
        }
    }

    public static void filtRuc(JTable tabla, String cad) {
        List lista = null;
        lista = GestionarCliente.filRuc(cad);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            tb.addRow(fila);
        }
    }
    /*public static void filContacto(JTable tabla, String cad)
    {
        List lista = null;
        lista = GestionarCliente.filContacto(cad);
        for(int i=1;i<lista.size();i++)
        {
            DefaultTableModel tb = (DefaultTableModel)tabla.getModel();
            Object[]fila = (Object[])lista.get(i);
            tb.addRow(fila);
        }
    }
     */
 /*public static void filDireccion(JTable tabla, String cad)
    {
        List lista = null;
        lista = GestionarCliente.filDireccion(cad);
        for(int i=1;i<lista.size();i++)
        {
            DefaultTableModel tb = (DefaultTableModel)tabla.getModel();
            Object[]fila = (Object[])lista.get(i);
            tb.addRow(fila);
        }
    }
     */
}
