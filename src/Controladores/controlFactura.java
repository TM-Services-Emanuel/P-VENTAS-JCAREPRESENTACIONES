package Controladores;

import Componentes.ConexionBD;
import Componentes.Fecha;
import Componentes.Login;
import Componentes.Mensajes;
import Componentes.Redondeo;
import Datos.ArregloFactura;
import Datos.ArregloTransferencia;
import Datos.GestionarArticulosMovil;
import Datos.GestionarCliente;
import Datos.GestionarFactura;
import Datos.GestionarVendedor;
import IU.dlgBuscarArticuloTransferencia;
import IU.dlgBuscarArticuloVenta;
import IU.dlgBuscarArticuloVenta1;
import IU.dlgBuscarCliente;
import IU.dlgConsultarFacturas;
import IU.dlgConsultarFacturasMovil;
import IU.dlgGestTransferencias;
import static IU.dlgGestTransferencias.BloquearOrigen;
import IU.dlgVentas;
import Modelo.ArticuloMovil;
import Modelo.ClienteMovil;
import Modelo.DetalleFactura;
import Modelo.DetalleTransferencia;
import Modelo.Vendedor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.mariadb.jdbc.MariaDbConnection;
import org.mariadb.jdbc.MariaDbStatement;

public class controlFactura {

    static ClienteMovil cl;
    static ArticuloMovil art;
    static DetalleFactura dfa;
    static DetalleTransferencia dtran;
    static ArregloFactura array = new ArregloFactura();
    static ArregloTransferencia arrayT = new ArregloTransferencia();
    static String UsuarioL = "";
    public static MariaDbConnection con;
    public static MariaDbStatement st;
    public static ResultSet rss;

    public static void prepararBD() {
        try {
            con = (MariaDbConnection) new ConexionBD().getConexion();
            if (con == null) {
                System.out.println("No hay Conexion con la Base de Datos");
            } else {
                st = (MariaDbStatement) con.createStatement();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selecArticulo()//Seleccionar Artículo
    {
        int x = dlgBuscarArticuloVenta.tbDetalle.getSelectedRow();
        String cod = dlgBuscarArticuloVenta.tbDetalle.getValueAt(x, 0).toString();
        art = GestionarArticulosMovil.busArticulo(cod);
        dlgVentas.txtCodArticulo.setText(String.valueOf(art.getIdproducto()));
        dlgVentas.txtArt.setText(art.getDescripcion());
        dlgVentas.txtCant.setText("1");
        //DecimalFormat df = new DecimalFormat("#,###");
        //String PV = String.valueOf(art.getPrecio_venta());
    }
    
    public static void selecArticuloT()//Seleccionar Artículo
    {
        int x = dlgBuscarArticuloVenta1.tbDetalle.getSelectedRow();
        String cod = dlgBuscarArticuloVenta1.tbDetalle.getValueAt(x, 0).toString();
        art = GestionarArticulosMovil.busArticulo(cod);
        dlgGestTransferencias.txtCodArticulo.setText(String.valueOf(art.getIdproducto()));
        dlgGestTransferencias.txtArt.setText(art.getDescripcion());
        dlgGestTransferencias.txtCant.setText("1");
    }
    
    public static void selecArticuloTR()//Seleccionar Artículo
    {
        int x = dlgBuscarArticuloTransferencia.tbDetalle.getSelectedRow();
        String cod = dlgBuscarArticuloTransferencia.tbDetalle.getValueAt(x, 0).toString();
        art = GestionarArticulosMovil.busArticulo(cod);
        dlgGestTransferencias.txtCodArticulo.setText(String.valueOf(art.getIdproducto()));
        dlgGestTransferencias.txtArt.setText(art.getDescripcion());
        dlgGestTransferencias.txtCant.setText("1");
    }

    public static void selectCliente()//Seleccionar Cliente
    {
        int x = dlgBuscarCliente.tbDetalle.getSelectedRow();
        String cod = dlgBuscarCliente.tbDetalle.getValueAt(x, 0).toString();
        cl = GestionarCliente.busCliente(cod);
        dlgVentas.txtCodCliente.setText(String.valueOf(cl.getidCliente()));
        dlgVentas.txtRuc.setText((cl.getRuc()));
        dlgVentas.txtRazonS.setText(cl.getRazonSocial());
    }

    public static void selectClienteInicio(String cod)//Seleccionar Cliente
    {
        //int x = dlgBuscarCliente.tbDetalle.getSelectedRow();
        //String cod = dlgBuscarCliente.tbDetalle.getValueAt(x, 0).toString();
        cl = GestionarCliente.busCliente(cod);
        dlgVentas.txtCodCliente.setText(String.valueOf(cl.getidCliente()));
        dlgVentas.txtRuc.setText((cl.getRuc()));
        dlgVentas.txtRazonS.setText(cl.getRazonSocial());
    }

    public static int getExcetas() {
        int total = 0;
        DefaultTableModel tb = (DefaultTableModel) dlgVentas.tbDetalle.getModel();
        int fila = tb.getRowCount();
        for (int i = 0; i < fila; i++) {
            total += Integer.valueOf(String.valueOf(dlgVentas.tbDetalle.getModel().getValueAt(i, 5)).replace(".", "").replace(",", ""));
        }
        return Redondeo.redondearI(total);
    }

    public static int get5Libre() {
        int total = 0;
        DefaultTableModel tb = (DefaultTableModel) dlgVentas.tbDetalle.getModel();
        int fila = tb.getRowCount();
        for (int i = 0; i < fila; i++) {
            total += Integer.valueOf(String.valueOf(dlgVentas.tbDetalle.getModel().getValueAt(i, 6)).replace(".", "").replace(",", ""));
        }
        return total;
    }

    public static int get5() {
        int total = 0;
        DefaultTableModel tb = (DefaultTableModel) dlgVentas.tbDetalle.getModel();
        int fila = tb.getRowCount();
        for (int i = 0; i < fila; i++) {
            total += Integer.valueOf(String.valueOf(dlgVentas.tbDetalle.getModel().getValueAt(i, 6)).replace(".", "").replace(",", ""));
        }
        return Redondeo.redondearI(total / 21);
    }

    public static int get10() {
        int total = 0;
        DefaultTableModel tb = (DefaultTableModel) dlgVentas.tbDetalle.getModel();
        int fila = tb.getRowCount();
        for (int i = 0; i < fila; i++) {
            total += Integer.valueOf(String.valueOf(dlgVentas.tbDetalle.getModel().getValueAt(i, 7)).replace(".", "").replace(",", ""));
        }
        return Redondeo.redondearI(total / 11);
    }

    public static int get10Libre() {
        int total = 0;
        DefaultTableModel tb = (DefaultTableModel) dlgVentas.tbDetalle.getModel();
        int fila = tb.getRowCount();
        for (int i = 0; i < fila; i++) {
            total += Integer.valueOf(String.valueOf(dlgVentas.tbDetalle.getModel().getValueAt(i, 7)).replace(".", "").replace(",", ""));
        }
        return total;
    }

    public static int getTotal()//Calcula el total de la venta
    {
        int total = 0;
        DefaultTableModel tb = (DefaultTableModel) dlgVentas.tbDetalle.getModel();
        int fila = tb.getRowCount();
        for (int i = 0; i < fila; i++) {
            total += Integer.parseInt(String.valueOf(dlgVentas.tbDetalle.getModel().getValueAt(i, 8)).replace(".", "").replace(",", ""));
        }
        return /*Redondeo.redondearD*/ (total);
    }
    
    public static int getTotalVentasMoviles() {
        int total = 0;
        DefaultTableModel tb = (DefaultTableModel) dlgConsultarFacturasMovil.tbVentasMoviles.getModel();
        int fila = tb.getRowCount();
        for (int i = 0; i < fila; i++) {
            String activo = dlgConsultarFacturasMovil.tbVentasMoviles.getModel().getValueAt(i, 13).toString();
            if(!activo.equals("ANULADO")){
              total += Integer.valueOf(String.valueOf(dlgConsultarFacturasMovil.tbVentasMoviles.getModel().getValueAt(i, 11)).replace(".", "").replace(",", ""));  
            }            
        }
        return (total);
    }
    
    public static int getTotalVentasMovilesA() {
        int total = 0;
        DefaultTableModel tb = (DefaultTableModel) dlgConsultarFacturasMovil.tbVentasMoviles.getModel();
        int fila = tb.getRowCount();
        for (int i = 0; i < fila; i++) {
            String activo = dlgConsultarFacturasMovil.tbVentasMoviles.getModel().getValueAt(i, 13).toString();
            if(activo.equals("ANULADO")){
              total += Integer.valueOf(String.valueOf(dlgConsultarFacturasMovil.tbVentasMoviles.getModel().getValueAt(i, 11)).replace(".", "").replace(",", ""));  
            }            
        }
        return (total);
    }
    
    public static int getTotaltransferencia()//Calcula el total de la venta
    {
        int total = 0;
        DefaultTableModel tb = (DefaultTableModel) dlgGestTransferencias.tbDetalle.getModel();
        int fila = tb.getRowCount();
        for (int i = 0; i < fila; i++) {
            total += Integer.parseInt(String.valueOf(dlgGestTransferencias.tbDetalle.getModel().getValueAt(i, 5)).replace(".", "").replace(",", ""));
        }
        return /*Redondeo.redondearD*/ (total);
    }
    
    public static void insertarTransferencia(String cod, String codB, String desc, String prec, String cant, String total, JTable tabla) {
        String fila[] = new String[6];
        DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
        fila[0] = cod;
        fila[1] = codB;
        fila[2] = desc;
        fila[3] = prec;
        fila[4] = cant;
        fila[5] = total;
        tb.addRow(fila);
    }

    public static void insertar(String cod, String codB, String desc, String cant, String prec, String total, int iva, JTable tabla) {
        String fila[] = new String[10];
        DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
        fila[0] = cod;
        fila[1] = codB;
        fila[2] = desc;
        fila[3] = cant;
        fila[4] = prec;
        switch (iva) {
            case 3 -> {
                fila[5] = "0";
                fila[6] = "0";
                fila[7] = total;
            }
            case 2 -> {
                fila[5] = "0";
                fila[6] = total;
                fila[7] = "0";
            }
            case 1 -> {
                fila[5] = total;
                fila[6] = "0";
                fila[7] = "0";
            }
        }
        fila[8] = total;
        fila[9] = String.valueOf(iva);
        tb.addRow(fila);
    }

    /*  public static double actStock() {
        int cant = Integer.parseInt(dlgVentas.txtCant.getText());
        return art.getStock() - cant;
    }*/
    public static void addmismoItemFactura(int fila, double cantTabla, int iva, double cantAdd, int precio) {
        try {
            double cantFinal = (cantTabla + cantAdd);
            int montoVenta = (int) (cantFinal * precio);

            dlgVentas.tbDetalle.setValueAt(String.valueOf(cantFinal), fila, 3);
            dlgVentas.tbDetalle.setValueAt(String.valueOf(precio), fila, 4);
            switch (iva) {
                case 3 -> {
                    dlgVentas.tbDetalle.setValueAt(String.valueOf("0"), fila, 5);
                    dlgVentas.tbDetalle.setValueAt(String.valueOf("0"), fila, 6);
                    dlgVentas.tbDetalle.setValueAt(String.valueOf(montoVenta), fila, 7);
                }
                case 2 -> {
                    dlgVentas.tbDetalle.setValueAt(String.valueOf("0"), fila, 5);
                    dlgVentas.tbDetalle.setValueAt(String.valueOf(montoVenta), fila, 6);
                    dlgVentas.tbDetalle.setValueAt(String.valueOf("0"), fila, 7);
                }
                case 1 -> {
                    dlgVentas.tbDetalle.setValueAt(String.valueOf(montoVenta), fila, 5);
                    dlgVentas.tbDetalle.setValueAt(String.valueOf("0"), fila, 6);
                    dlgVentas.tbDetalle.setValueAt(String.valueOf("0"), fila, 7);
                }
            }
            dlgVentas.tbDetalle.setValueAt(String.valueOf(montoVenta), fila, 8);
            dlgVentas.tbDetalle.setValueAt(String.valueOf(iva), fila, 9);
        } catch (Exception e) {
            Mensajes.error("Seleccione una fila de la tabla");
        }
    }
    
    public static void addmismoItemTransferencia(int fila, double cantTabla, double cantAdd, int precio) {
        try {
            double cantFinal = (cantTabla + cantAdd);
            int montoVenta = (int) (cantFinal * precio);
            dlgGestTransferencias.tbDetalle.setValueAt(String.valueOf(cantFinal), fila, 4);
            dlgGestTransferencias.tbDetalle.setValueAt(String.valueOf(precio), fila, 3);
            dlgGestTransferencias.tbDetalle.setValueAt(String.valueOf(montoVenta), fila, 5);
        } catch (Exception e) {
            Mensajes.error("Seleccione una fila de la tabla");
        }
    }

    public static void addTabla(JTable tabla) {
        try {
            //int f = dlgBuscarArticuloVenta.tbDetalle.getSelectedRow();
            int codA = art.getIdproducto();
            String codB = art.getCodBarra();
            String desc = art.getDescripcion();
            double cant = Double.parseDouble(dlgVentas.txtCant.getText().trim());
            int precio;
            int monto;
            if (art.getVentam().equals("SI")) {
                if (cant < art.getCantm()) {
                    precio = art.getPreciominorista();
                    monto = (int) (cant * precio);
                } else {
                    precio = art.getPrecio_venta();
                    monto = (int) (cant * precio);
                }
            } else {
                precio = art.getPreciominorista();
                monto = (int) (cant * precio);
            }
            int iva = art.getIva();
            DetalleFactura dfac = new DetalleFactura(codA);
            if (array.busca(dfac.getCodArticulo()) != -1) {
                int Nfila = array.busca(dfac.getCodArticulo());
                double cantTabla = Double.parseDouble(dlgVentas.tbDetalle.getValueAt(Nfila, 3).toString());
                if ((cantTabla + cant) <= 0) {
                    Mensajes.error("ERROR:\nAcabas de ingresar un número que dara como resultado: "+(cantTabla + cant));
                }if ((cantTabla+ cant) > art.getStock()) {
                    Mensajes.error("ERROR:\nLa cantidad que estas intentando vender supera el stock actual del producto.");
                } else {
                    if (art.getVentam().equals("SI")) {
                        if (cantTabla + cant < art.getCantm()) {
                            precio = art.getPreciominorista();
                        } else {
                            precio = art.getPrecio_venta();
                        }
                    } else {
                        precio = art.getPreciominorista();
                    }
                    addmismoItemFactura(Nfila, cantTabla, iva, cant, precio);
                    String total = String.valueOf(getTotal());
                    String exentas = String.valueOf(getExcetas());
                    String iva5 = String.valueOf(get5());
                    String iva10 = String.valueOf(get10());
                    DecimalFormat df = new DecimalFormat("#,###");
                    dlgVentas.txtExentaL.setText(exentas);
                    dlgVentas.txtExenta.setText(df.format(Integer.valueOf(exentas.trim().replace(".", "").replace(",", ""))));
                    dlgVentas.txt5L.setText(iva5);
                    dlgVentas.txt5.setText(df.format(Integer.valueOf(iva5.replace(".", "").replace(",", ""))));
                    dlgVentas.txt5libre.setText(df.format(Integer.valueOf(String.valueOf(get5Libre()).replace(".", "").replace(",", ""))));
                    dlgVentas.txt10L.setText(iva10);
                    dlgVentas.txt10.setText(df.format(Integer.valueOf(iva10.replace(".", "").replace(",", ""))));
                    dlgVentas.txt10Libre.setText(df.format(Integer.valueOf(String.valueOf(get10Libre()).replace(".", "").replace(",", ""))));
                    dlgVentas.txtTotalL.setText(total);
                    dlgVentas.txtTotal.setText(df.format(Integer.valueOf(total.replace(".", "").replace(",", ""))));
                }
            } else {
                if (cant < 0) {
                    Mensajes.error("ERROR:\nAcabas de ingresar un número que dara como resultado: "+cant);
                } else {
                    array.agregar(dfac);
                    insertar(String.valueOf(codA), codB, desc, String.valueOf(cant), String.valueOf(precio), String.valueOf(monto), iva, tabla);
                    String total = String.valueOf(getTotal());
                    String exentas = String.valueOf(getExcetas());
                    String iva5 = String.valueOf(get5());
                    String iva10 = String.valueOf(get10());
                    DecimalFormat df = new DecimalFormat("#,###");
                    dlgVentas.txtExentaL.setText(exentas);
                    dlgVentas.txtExenta.setText(df.format(Integer.valueOf(exentas.trim().replace(".", "").replace(",", ""))));
                    dlgVentas.txt5L.setText(iva5);
                    dlgVentas.txt5.setText(df.format(Integer.valueOf(iva5.replace(".", "").replace(",", ""))));
                    dlgVentas.txt5libre.setText(df.format(Integer.valueOf(String.valueOf(get5Libre()).replace(".", "").replace(",", ""))));
                    dlgVentas.txt10L.setText(iva10);
                    dlgVentas.txt10.setText(df.format(Integer.valueOf(iva10.replace(".", "").replace(",", ""))));
                    dlgVentas.txt10Libre.setText(df.format(Integer.valueOf(String.valueOf(get10Libre()).replace(".", "").replace(",", ""))));
                    dlgVentas.txtTotalL.setText(total);
                    dlgVentas.txtTotal.setText(df.format(Integer.valueOf(total.replace(".", "").replace(",", ""))));
                }
            }
        } catch (Exception e) {
            Mensajes.error("ERROR AL AGREGAR ARTICULO: " + e.getMessage().toUpperCase());
        }
    }
    
    public static void addTablaTR(JTable tabla) {
        try {
            //int f = dlgBuscarArticuloVenta.tbDetalle.getSelectedRow();
            int codA = art.getIdproducto();
            String codB = art.getCodinterno();
            String desc = art.getDescripcion();
            double cant = Double.parseDouble(dlgGestTransferencias.txtCant.getText().trim());
            int precio;
            int monto;
            precio = art.getPrecio_venta();
            monto = (int) (cant * precio);
            DetalleTransferencia dtrans = new DetalleTransferencia(codA);
            if (arrayT.busca(dtrans.getCodArticulo()) != -1) {
                int Nfila = arrayT.busca(dtrans.getCodArticulo());
                double cantTabla = Double.parseDouble(dlgGestTransferencias.tbDetalle.getValueAt(Nfila, 4).toString());
                if ((cantTabla + cant) <= 0) {
                    Mensajes.error("ERROR:\nAcabas de ingresar un número que dara como resultado: "+(cantTabla + cant));
                } else {
                    precio = art.getPrecio_venta();
                    addmismoItemTransferencia(Nfila, cantTabla, cant, precio);
                    String total = String.valueOf(getTotaltransferencia());
                    DecimalFormat df = new DecimalFormat("#,###");
                    dlgGestTransferencias.txtTotal.setText(df.format(Integer.valueOf(total.replace(".", "").replace(",", ""))));
                }
            } else {
                if (cant < 0) {
                    Mensajes.error("ERROR:\nAcabas de ingresar un número que dara como resultado: "+cant);
                } else {
                    arrayT.agregar(dtrans);
                    insertarTransferencia(String.valueOf(codA), codB, desc, String.valueOf(precio), String.valueOf(cant), String.valueOf(monto), tabla);
                    String total = String.valueOf(getTotaltransferencia());
                    DecimalFormat df = new DecimalFormat("#,###");
                    dlgGestTransferencias.txtTotal.setText(df.format(Integer.valueOf(total.replace(".", "").replace(",", ""))));
                }
            }
            BloquearOrigen();
        } catch (Exception e) {
            Mensajes.error("ERROR AL AGREGAR ARTICULO: " + e.getMessage().toUpperCase());
        }
    }

    public static void addTablaT(JTable tabla) {
        try {
            //int f = dlgBuscarArticuloVenta.tbDetalle.getSelectedRow();
            int codA = art.getIdproducto();
            String codB = art.getCodinterno();
            String desc = art.getDescripcion();
            double cant = Double.parseDouble(dlgGestTransferencias.txtCant.getText().trim());
            int precio;
            int monto;
            precio = art.getPrecio_venta();
            monto = (int) (cant * precio);
            DetalleTransferencia dtrans = new DetalleTransferencia(codA);
            if (arrayT.busca(dtrans.getCodArticulo()) != -1) {
                int Nfila = arrayT.busca(dtrans.getCodArticulo());
                double cantTabla = Double.parseDouble(dlgGestTransferencias.tbDetalle.getValueAt(Nfila, 4).toString());
                if ((cantTabla + cant) <= 0) {
                    Mensajes.error("ERROR:\nAcabas de ingresar un número que dara como resultado: "+(cantTabla + cant));
                }else if ((cantTabla+ cant) > art.getStock()) {
                    Mensajes.error("ERROR:\nLa cantidad que estas intentando Transferir supera el stock actual del producto.");
                } else {
                    precio = art.getPrecio_venta();
                    addmismoItemTransferencia(Nfila, cantTabla, cant, precio);
                    String total = String.valueOf(getTotaltransferencia());
                    DecimalFormat df = new DecimalFormat("#,###");
                    dlgGestTransferencias.txtTotal.setText(df.format(Integer.valueOf(total.replace(".", "").replace(",", ""))));
                }
            } else {
                if (cant < 0) {
                    Mensajes.error("ERROR:\nAcabas de ingresar un número que dara como resultado: "+cant);
                } else {
                    arrayT.agregar(dtrans);
                    insertarTransferencia(String.valueOf(codA), codB, desc, String.valueOf(precio), String.valueOf(cant), String.valueOf(monto), tabla);
                    String total = String.valueOf(getTotaltransferencia());
                    DecimalFormat df = new DecimalFormat("#,###");
                    dlgGestTransferencias.txtTotal.setText(df.format(Integer.valueOf(total.replace(".", "").replace(",", ""))));
                }
            }
            BloquearOrigen();
        } catch (Exception e) {
            Mensajes.error("ERROR AL AGREGAR ARTICULO: " + e.getMessage().toUpperCase());
        }
    }
    
    public static void consLinea()//Buscar linea en ArrayList
    {
        int fila = dlgVentas.tbDetalle.getSelectedRow();
        int cod = Integer.parseInt(dlgVentas.tbDetalle.getValueAt(fila, 0).toString());
        int p = array.busca(cod);
        if (p == -1) {
            Mensajes.informacion("Articulo no existe");
        } else {
            dfa = array.getFila(p);
            int codA = dfa.getCodArticulo();
        }
    }
    
    public static void consLineaT()//Buscar linea en ArrayList
    {
        int fila = dlgGestTransferencias.tbDetalle.getSelectedRow();
        int cod = Integer.parseInt(dlgGestTransferencias.tbDetalle.getValueAt(fila, 0).toString());
        int p = arrayT.busca(cod);
        if (p == -1) {
            Mensajes.informacion("Articulo no existe");
        } else {
            dtran = arrayT.getFila(p);
            int codA = dtran.getCodArticulo();
        }
    }
    

    public static void delRenglon(JTable tabla)//Quita un renglon del detalle
    {
        consLinea();
        int fila = dlgVentas.tbDetalle.getSelectedRow();
        int cod = Integer.parseInt(dlgVentas.tbDetalle.getValueAt(fila, 0).toString());
        int p = array.busca(cod);
        if (p != -1) {
            int res = Mensajes.confirmar("Desea quitar esta linea");
            if (res == 0) {
                array.eliminar(p);
                DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
                tb.removeRow(fila);
                String total = String.valueOf(getTotal());
                String exentas = String.valueOf(getExcetas());
                String iva5 = String.valueOf(get5());
                String iva10 = String.valueOf(get10());
                DecimalFormat df = new DecimalFormat("#,###");
                dlgVentas.txtExentaL.setText(exentas);
                dlgVentas.txtExenta.setText(df.format(Integer.valueOf(exentas.trim().replace(".", "").replace(",", ""))));
                dlgVentas.txt5L.setText(iva5);
                dlgVentas.txt5.setText(df.format(Integer.valueOf(iva5.replace(".", "").replace(",", ""))));
                dlgVentas.txt5libre.setText(df.format(Integer.valueOf(String.valueOf(get5Libre()).replace(".", "").replace(",", ""))));
                dlgVentas.txt10L.setText(iva10);
                dlgVentas.txt10.setText(df.format(Integer.valueOf(iva10.replace(".", "").replace(",", ""))));
                dlgVentas.txt10Libre.setText(df.format(Integer.valueOf(String.valueOf(get10Libre()).replace(".", "").replace(",", ""))));
                dlgVentas.txtTotalL.setText(total);
                dlgVentas.txtTotal.setText(df.format(Integer.valueOf(total.replace(".", "").replace(",", ""))));
            }
        }
    }
    
    public static void delRenglonT(JTable tabla)//Quita un renglon del detalle
    {
        consLineaT();
        int fila = dlgGestTransferencias.tbDetalle.getSelectedRow();
        int cod = Integer.parseInt(dlgGestTransferencias.tbDetalle.getValueAt(fila, 0).toString());
        int p = arrayT.busca(cod);
        if (p != -1) {
            int res = Mensajes.confirmar("Desea quitar esta linea");
            if (res == 0) {
                arrayT.eliminar(p);
                DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
                tb.removeRow(fila);
                String total = String.valueOf(getTotaltransferencia());
                DecimalFormat df = new DecimalFormat("#,###");
                dlgGestTransferencias.txtTotal.setText(df.format(Integer.valueOf(total.replace(".", "").replace(",", ""))));
                BloquearOrigen();
            }
        }
    }

    public static int calCulos()//Metodo que realiza los calculos finales de la venta
    {
        int total = Integer.parseInt(dlgVentas.txtTotal.getText().replace(".", "").replace(",", ""));
        int abono = Integer.parseInt(dlgVentas.txtAbono.getText().replace(".", "").replace(",", ""));
        int vuelto = abono - total;
        return /*Redondeo.redondearI*/ (vuelto);
    }

    public static void canCelar()//Cancelar la venta y vaciar ArrayList
    {
        array.vaciar();
    }
    
    public static void canCelarT()//Cancelar la venta y vaciar ArrayList
    {
        arrayT.vaciar();
    }

    public static void listFacturas(JTable tabla)//Lista las facturas realizadas
    {
        List lista = null;
        lista = GestionarFactura.listFacturas();
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0].toString();
            fila[1].toString();
            fila[2].toString();
            fila[3].toString();
            fila[4].toString();
            fila[6].toString();
            fila[7].toString();
            fila[8].toString();
            fila[9].toString();
            fila[10].toString();
            //fila[10].toString();
            if (fila[11].toString().equals("S")) {
                fila[11] = "ACTIVO";
            } else {
                fila[11] = "ANULADO";
            }
            tb.addRow(fila);
        }
    }
    
    public static void listFacturasMoviles(JTable tabla, String fecha)//Lista las facturas realizadas
    {
        List lista;
        lista = GestionarFactura.listFacturasMovil1(fecha);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0].toString();
            fila[1].toString();
            fila[2].toString();
            fila[3].toString();
            fila[4].toString();
            fila[5] = fila[5].toString() + "-" + fila[6].toString() + "-" + fila[7].toString();
            fila[6] = fila[8].toString();
            fila[7] = Fecha.formatoFechaDd(fila[9].toString());
            fila[8] = fila[10].toString();
            fila[9] = fila[11].toString();
            fila[10] = fila[12].toString();
            fila[11] = fila[13].toString();
            fila[12] = fila[14].toString();
            //fila[10].toString();
            if (fila[15].toString().equals("S")) {
                fila[13] = "";
            } else {
                fila[13] = "ANULADO";
            }
            fila[14] = fila[16].toString();
            fila[15] = fila[17].toString();
            fila[16] = fila[18].toString();
            tb.addRow(fila);
        }
    }
    
    public static void listVentasCabeceras(JTable tabla, String fechaD, String fechaH)//Lista las facturas realizadas
    {
        List lista;
        lista = GestionarFactura.listVentasCabeceras(fechaD, fechaH);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0].toString();
            fila[1]= fila[1].toString()+"-"+fila[2].toString();
            fila[2]= Fecha.formatoFechaVMRInverter(fila[3].toString());
            fila[3]=fila[4].toString();
            if(fila[5].toString().equals("CONTADO")){
                fila[4]="1";
            }else{
                fila[4]="2";
            }
            fila[5]=fila[6].toString();
            fila[6]="1";
            tb.addRow(fila);
        }
    }

    public static void listFacturasMovilesT(JTable tabla, String idT, String fecha)//Lista las facturas realizadas
    {
        List lista;
        lista = GestionarFactura.listFacturasMovilesT(idT, fecha);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0].toString();
            fila[1].toString();
            fila[2].toString();
            fila[3].toString();
            fila[4].toString();
            fila[5] = fila[5].toString() + "-" + fila[6].toString() + "-" + fila[7].toString();
            fila[6] = fila[8].toString();
            fila[7] = fila[9].toString();
            fila[8] = fila[10].toString();
            fila[9] = fila[11].toString();
            fila[10] = fila[12].toString();
            fila[11] = fila[13].toString();
            fila[12] = fila[14].toString();
            //fila[10].toString();
            if (fila[15].toString().equals("S")) {
                fila[13] = "";
            } else {
                fila[13] = "ANULADO";
            }
            fila[14] = fila[16].toString();
            fila[15] = fila[17].toString();
            fila[16] = fila[18].toString();
            tb.addRow(fila);
        }
    }
    public static void listFacturasMovilesTPE(JTable tabla, String idT, String idPE, String fecha)//Lista las facturas realizadas
    {
        List lista;
        lista = GestionarFactura.listFacturasMovilesTPE(idPE, idT, fecha);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0].toString();
            fila[1].toString();
            fila[2].toString();
            fila[3].toString();
            fila[4].toString();
            fila[5] = fila[5].toString() + "-" + fila[6].toString() + "-" + fila[7].toString();
            fila[6] = fila[8].toString();
            fila[7] = fila[9].toString();
            fila[8] = fila[10].toString();
            fila[9] = fila[11].toString();
            fila[10] = fila[12].toString();
            fila[11] = fila[13].toString();
            fila[12] = fila[14].toString();
            //fila[10].toString();
            if (fila[15].toString().equals("S")) {
                fila[13] = "";
            } else {
                fila[13] = "ANULADO";
            }
            fila[14] = fila[16].toString();
            fila[15] = fila[17].toString();
            fila[16] = fila[18].toString();
            tb.addRow(fila);
        }
    }
    
    public static void listDetalleVentasMoviles(JTable tabla)//Lista el detalle de la factura seleccionada
    {
        int x = dlgConsultarFacturasMovil.tbVentasMoviles.getSelectedRow();
        String cod = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 0).toString();
        String idemision = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 1).toString();
        List lista;
        lista = GestionarFactura.listDetallesVentasMovil(cod, idemision);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            tb.addRow(fila);
        }
    }
    

    public static void listFacturasCredito(JTable tabla, String cliente)//Lista las facturas realizadas
    {
        List lista = null;
        lista = GestionarFactura.listFacturasCredito(cliente);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0].toString();
            fila[1].toString();
            fila[2].toString();
            fila[3].toString();
            fila[4].toString();
            fila[6].toString();
            fila[7].toString();
            fila[8].toString();
            fila[9].toString();
            fila[10].toString();
            //fila[10].toString();
            if (fila[11].toString().equals("S")) {
                fila[11] = "ACTIVO";
            } else {
                fila[11] = "ANULADO";
            }
            tb.addRow(fila);
        }
    }

    public static void listFacturasCreditoPendiente(JTable tabla, String cliente)//Lista las facturas realizadas
    {
        List lista = null;
        lista = GestionarFactura.listFacturasCreditoPendiente(cliente);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0].toString();
            fila[1].toString();
            fila[2].toString();
            fila[3].toString();
            fila[4].toString();
            fila[6].toString();
            fila[7].toString();
            fila[8].toString();
            fila[9].toString();
            fila[10].toString();
            //fila[10].toString();
            if (fila[11].toString().equals("S")) {
                fila[11] = "ACTIVO";
            } else {
                fila[11] = "ANULADO";
            }
            tb.addRow(fila);
        }
    }

    public static void listFacturasCreditoActivo(JTable tabla, String cliente)//Lista las facturas realizadas
    {
        List lista = null;
        lista = GestionarFactura.listFacturasCreditoActivo(cliente);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0].toString();
            fila[1].toString();
            fila[2].toString();
            fila[3].toString();
            fila[4].toString();
            fila[6].toString();
            fila[7].toString();
            fila[8].toString();
            fila[9].toString();
            fila[10].toString();
            //fila[10].toString();
            if (fila[11].toString().equals("S")) {
                fila[11] = "ACTIVO";
            } else {
                fila[11] = "ANULADO";
            }
            tb.addRow(fila);
        }
    }

    public static void listFacturasCreditoPendienteActivo(JTable tabla, String cliente)//Lista las facturas realizadas
    {
        List lista = null;
        lista = GestionarFactura.listFacturasCreditoPendienteActivo(cliente);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0].toString();
            fila[1].toString();
            fila[2].toString();
            fila[3].toString();
            fila[4].toString();
            fila[6].toString();
            fila[7].toString();
            fila[8].toString();
            fila[9].toString();
            fila[10].toString();
            //fila[10].toString();
            if (fila[11].toString().equals("S")) {
                fila[11] = "ACTIVO";
            } else {
                fila[11] = "ANULADO";
            }
            tb.addRow(fila);
        }
    }

    public static void listFacturas2(JTable tabla)//Lista las facturas realizadas
    {
        List lista;
        lista = GestionarFactura.lisFacturas2();
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            fila[0].toString();
            fila[1].toString();
            fila[2].toString();
            fila[3].toString();
            fila[4].toString();
            tb.addRow(fila);
        }
    }

    public static void listDetalle(JTable tabla)//Lista el detalle de la factura seleccionada
    {
        int x = dlgConsultarFacturas.tblFactura.getSelectedRow();
        String cod = dlgConsultarFacturas.tblFactura.getValueAt(x, 0).toString();
        String fecha = dlgConsultarFacturas.tblFactura.getValueAt(x, 2).toString();
        String des = dlgConsultarFacturas.tblFactura.getValueAt(x, 4).toString();
        String total = dlgConsultarFacturas.tblFactura.getValueAt(x, 5).toString();
        List lista = null;
        lista = GestionarFactura.listDetalles(cod);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            tb.addRow(fila);
        }
    }

    public static void ListClientes()//Mostrar informacion del cliente
    {
        int x = dlgConsultarFacturas.tblFactura.getSelectedRow();
        String cod = dlgConsultarFacturas.tblFactura.getValueAt(x, 4).toString();
        ClienteMovil c = GestionarCliente.busCliente(cod);
        dlgConsultarFacturas.txtCodCliente.setText(String.valueOf(c.getidCliente()));
        dlgConsultarFacturas.txtRazonSocial.setText(c.getRazonSocial());
        dlgConsultarFacturas.txtRuc.setText(c.getRuc());
    }

    public static void selecVendedor()//Seleccionar Vendedor
    {
        int x = dlgConsultarFacturas.tblFactura.getSelectedRow();
        String cod = dlgConsultarFacturas.tblFactura.getValueAt(x, 10).toString();
        Vendedor ven = GestionarVendedor.busVendedor(cod);
        dlgConsultarFacturas.txtVendedor.setText(ven.getNombreV());
    }

    public static void fillCliente(JTable tabla, String nom) {
        List lista;
        lista = GestionarFactura.fillCliente(nom);
        for (int i = 1; i < lista.size(); i++) {
            DefaultTableModel tb = (DefaultTableModel) tabla.getModel();
            Object[] fila = (Object[]) lista.get(i);
            tb.addRow(fila);
        }
    }

    public static String anularFactura()//Metodo para anular facturas
    {
        String msg;
        int x = dlgConsultarFacturas.tblFactura.getSelectedRow();
        String cod = dlgConsultarFacturas.tblFactura.getValueAt(x, 0).toString();
        String usuario = UsuarioL = Login.getUsuarioLogueado();
        msg = GestionarFactura.actFactura(cod, usuario);
        if (msg == null) {
            Mensajes.informacion("Venta Anulada");
            controlFactura.actStockEliminarFactura();
        } else {
            Mensajes.error(msg);
        }
        return msg;
    }
    
    public static String anularVentaMovil()//Metodo para anular facturas
    {
        String msg;
        int x = dlgConsultarFacturasMovil.tbVentasMoviles.getSelectedRow();
        String cod = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 0).toString();
        String idemision = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 1).toString();
        //String usuario = UsuarioL = Login.getUsuarioLogueado();
        msg = GestionarFactura.actFacturaMovil(cod, idemision);
        if (msg == null) {
            Mensajes.informacion("Venta Móvil Anulada");
        } else {
            Mensajes.error(msg);
        }
        return msg;
    }

    public static String actStockEliminarFactura() {
        String msg = null;
        int f = dlgConsultarFacturas.tblDetalle.getRowCount();
        String usuario = UsuarioL = Login.getUsuarioLogueado();
        for (int i = 0; i < f; i++) {
            int coda = Integer.parseInt(dlgConsultarFacturas.tblDetalle.getValueAt(i, 1).toString());
            double stK = Double.parseDouble(dlgConsultarFacturas.tblDetalle.getValueAt(i, 0).toString());
            ArticuloMovil a = new ArticuloMovil(coda, stK);
            msg = GestionarArticulosMovil.actStockMAS(a);
        }
        if (msg == null) {
            Mensajes.informacion("Stock Actualizado");
        } else {
            Mensajes.error(msg);
        }
        return msg;
    }
    
    
    public static void actCantidad(JTable tabla) {
        try {
            int fila = tabla.getSelectedRow();
            String cod = (tabla.getValueAt(fila, 0).toString());
            double ca = Double.parseDouble(tabla.getValueAt(fila, 3).toString());
            int pre;/*= Integer.parseInt(tabla.getValueAt(fila, 4).toString().replace(".", "").replace(",", ""));*/
            int monto;/* = (int) (pre*cant);*/
            double cant = (Mensajes.ingresarNumerosV(cod, ca));
            ArticuloMovil ar;
            ar=GestionarArticulosMovil.busArticulo(cod);
            if (ar.getVentam().equals("SI")) {
                if (cant < ar.getCantm()) {
                    pre = ar.getPreciominorista();
                    monto = (int) (cant * pre);
                } else {
                    pre = ar.getPrecio_venta();
                    monto = (int) (cant * pre);
                }
            } else {
                pre = ar.getPreciominorista();
                monto = (int) (cant * pre);
            }
            tabla.setValueAt(String.valueOf(cant), fila, 3);
            tabla.setValueAt(String.valueOf(pre), fila, 4);
            int iva = Integer.parseInt(tabla.getValueAt(fila, 9).toString());
            
            switch (iva) {
                case 3 -> {
                    tabla.setValueAt(String.valueOf(monto), fila, 7);
                }
                case 2 -> {
                    tabla.setValueAt(String.valueOf(monto), fila, 6);
                }
                case 1 -> {
                    tabla.setValueAt(String.valueOf(monto), fila, 5);
                }
            }
            
            tabla.setValueAt(String.valueOf(monto), fila, 8);
            
            //int Gan = CalcGanancia();
            //dlgCompras1.tbDetalle.setValueAt(String.valueOf(Gan), fila, 16);
                String total = String.valueOf(getTotal());
                String exentas = String.valueOf(getExcetas());
                String iva5 = String.valueOf(get5());
                String iva10 = String.valueOf(get10());
                DecimalFormat df = new DecimalFormat("#,###");
                dlgVentas.txtExentaL.setText(exentas);
                dlgVentas.txtExenta.setText(df.format(Integer.parseInt(exentas.trim().replace(".", "").replace(",", ""))));
                dlgVentas.txt5L.setText(iva5);
                dlgVentas.txt5.setText(df.format(Integer.parseInt(iva5.replace(".", "").replace(",", ""))));
                dlgVentas.txt10L.setText(iva10);
                dlgVentas.txt10.setText(df.format(Integer.parseInt(iva10.replace(".", "").replace(",", ""))));
                dlgVentas.txtTotalL.setText(total);
                dlgVentas.txtTotal.setText(df.format(Integer.parseInt(total.replace(".", "").replace(",", ""))));
        } catch (NumberFormatException e) {
            Mensajes.error("Seleccione una fila de la tabla");
        }
    }

}
