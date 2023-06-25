package IU;

import Componentes.ConexionBD;
import Componentes.Fecha;
import Componentes.Login;
import Componentes.Mensajes;
import Componentes.Numero_a_Letra;
import Componentes.RenderDecimal;
import Componentes.RenderDecimal2;
import Componentes.Reporte;
import Componentes.Software;
import Componentes.generarCodigos;
import Componentes.validarCampos;
import Componentes.PrinterService;
import Componentes.RenderDecimal1;
import Componentes.traerIP;
import Controladores.CabecerasTablas;
import Controladores.controlFactura;
import Datos.GestionarArticulosMovil;
import Datos.GestionarCliente;
import Datos.GestionarFactura;
import Datos.GestionarVendedor;
import java.awt.event.KeyEvent;
import Modelo.ArticuloMovil;
import Modelo.ClienteMovil;
import Modelo.Vendedor;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import org.mariadb.jdbc.MariaDbConnection;
import org.mariadb.jdbc.MariaDbStatement;

public final class dlgVentas extends javax.swing.JDialog {

    public static MariaDbConnection con;
    public static MariaDbStatement stTransaccion;
    public static MariaDbStatement st;
    public static MariaDbConnection conMovil;
    public static MariaDbStatement stTransaccionMovil;
    public static MariaDbStatement stMovil;
    public static ResultSet rs;
    public static int PrecioVenta;
    public static double costoiva;
    public static double descuento;
    public static String UsuarioL = "";
    public Reporte jasper;
    static String emp;
    static String dir;
    static String cel;

    private static int idEmision;
    private static String Timbrado;
    private static String Establecimiento;
    private static String Emision;
    private static String Desde;
    private static String Hasta;
    private static int facturaactual;
    private static int facturafin;

    static public Numero_a_Letra d;
    
    private Dimension dim;

    public dlgVentas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //dim=super.getToolkit().getScreenSize();
        //super.setSize(dim);
        //Blanco.setSize(dim);
        jasper = new Reporte();
        CabecerasTablas.ventas(tbDetalle);
        Cancelar();
        pintarCondicion();
        Invisible();
        prepararBD();
        obtenerNFactura();
        titulo();
        d = new Numero_a_Letra();
    }

    final void titulo() {
        if (Software.getSoftware().equals("null")) {
            this.setTitle("Venta de Productos");
        } else {
            this.setTitle(Software.getSoftware() + " - Venta de Productos");
        }
        if (Timbrado == null) {
            lbTimbrado.setText("");
        } else {
            lbTimbrado.setText("TIMBRADO N°: " + Timbrado);
        }
        if (Desde == null && Hasta == null) {
            lbValidaz.setText("");
        } else {
            lbValidaz.setText("PERIODO DE VALIDEZ: " + Desde + " AL " + Hasta);
        }
    }

    public void Cancelar() {
        limpiarCampos();
        //dcFecha.setEnabled(false);
        btnBuscarClientes.setEnabled(false);
        rContado.setEnabled(false);
        rContado.setSelected(true);
        rCredito.setEnabled(false);
        btnBuscarArticulo.setEnabled(false);
        txtCant.setEnabled(false);
        btnNuevo.setEnabled(true);
        btnNuevo.requestFocus();
        itemNuevo.setEnabled(true);
        btnModificar.setEnabled(false);
        itemModificar.setEnabled(false);
        btnGuardar.setEnabled(false);
        itemGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        itemCancelar.setEnabled(false);
        itemBuscarA.setEnabled(false);
        itemBuscarC.setEnabled(false);
        itemQuitar.setEnabled(false);
        btnSalir.setEnabled(true);
        itemSalir.setEnabled(true);
        cant();
    }

    public void Rendes() {
        dlgVentas.tbDetalle.getColumnModel().getColumn(4).setCellRenderer(new RenderDecimal2());
        dlgVentas.tbDetalle.getColumnModel().getColumn(5).setCellRenderer(new RenderDecimal1());
        dlgVentas.tbDetalle.getColumnModel().getColumn(6).setCellRenderer(new RenderDecimal1());
        dlgVentas.tbDetalle.getColumnModel().getColumn(7).setCellRenderer(new RenderDecimal1());
        dlgVentas.tbDetalle.getColumnModel().getColumn(8).setCellRenderer(new RenderDecimal());
    }

    public void Invisible() {
        btnAdd.setVisible(false);
        btnRestar.setVisible(false);
        lbCond.setVisible(false);
        txtTotalL.setVisible(false);
        lbCred.setVisible(false);
        txtExentaL.setVisible(false);
        txt5L.setVisible(false);
        txt10L.setVisible(false);
        txtNetoL.setVisible(false);
        txtDescuentoL.setVisible(false);
        txtCodArticulo.setVisible(false);
        txtCodCliente.setVisible(false);
        txtdisponibleL.setVisible(false);
        txtFecha.setVisible(false);
        txtidEmision.setVisible(false);
        txt5libre.setVisible(false);
        txt10Libre.setVisible(false);
        btnCant.setVisible(false);
    }

    public static void prepararBD() {
        try {
            con = (MariaDbConnection) new ConexionBD().getConexion();
            if (con == null) {
                System.out.println("No hay Conexion con la Base de Datos");
            } else {
                st = (MariaDbStatement) con.createStatement();
                stTransaccion = (MariaDbStatement) con.createStatement();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conMovil = (MariaDbConnection) new ConexionBD().getConexionMovil();
            if (conMovil == null) {
                System.out.println("No hay Conexion con la Base de Datos Movil");
            } else {
                stTransaccionMovil = (MariaDbStatement) conMovil.createStatement();
                stMovil = (MariaDbStatement) conMovil.createStatement();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cant() {
        int total = tbDetalle.getRowCount();
        if (total == 0) {
            etiCant.setText("Lista vacía");
        } else if (total == 1) {
            etiCant.setText("1 producto listado para la venta");
        } else if (total > 1) {
            etiCant.setText(String.valueOf(total) + " productos listados para la venta");
        }
    }

    private void pintarCondicion() {
        if (rContado.isSelected()) {
            rContado.setFont(new java.awt.Font("Tahoma", 1, 11));
            rContado.setForeground(new java.awt.Color(0, 0, 51));
            rCredito.setFont(new java.awt.Font("Tahoma", 0, 11));
            rCredito.setForeground(new java.awt.Color(0, 0, 0));
            lbCond.setText("CONTADO");
        } else {
            rContado.setFont(new java.awt.Font("Tahoma", 0, 11));
            rContado.setForeground(new java.awt.Color(0, 0, 0));
            rCredito.setFont(new java.awt.Font("Tahoma", 1, 11));
            rCredito.setForeground(new java.awt.Color(0, 0, 51));
            lbCond.setText("CREDITO");
        }
    }

    private void limpiarCampos() {
        txtCod.setText("");
        txtidEmision.setText("");
        txtFacturaN.setText("");
        txtFecha.setText("");
        txtfechaF.setText("");
        txtHora.setText("");
        txtFacturaN.setText("");
        txtCodCliente.setText("");
        txtRuc.setText("");
        txtRazonS.setText("");
        rContado.isSelected();
        txtTotalL.setText("0");
        txtTotal.setText("0");
        txtArt.setText("");
        txtCant.setText("");
        txtExenta.setText("0");
        txtExentaL.setText("0");
        txt5L.setText("0");
        txt5.setText("0");
        txt10L.setText("0");
        txt10.setText("0");
        txtNetoL.setText("0");
        txtDescuentoL.setText("0");
        lbCond.setText("");
        txtCaja.setText("");
        txtEstablecimiento.setText("");
        txtEmision.setText("");
        rContado.setSelected(true);
        CabecerasTablas.limpiarTablasVentas(tbDetalle);
        CabecerasTablas.ventas(tbDetalle);
        controlFactura.canCelar();
        txtCodVendedor.setText("");
        lbEmpleado.setText("");
    }

    public static void habilitarCANTCOSTO() {
        if (txtArt.getText().isEmpty()) {
            txtCant.setEnabled(false);
        } else {
            txtCant.setEnabled(true);
        }
    }

    /* public static void imprimirTicketO() {

        try {

            PrinterMatrix printer = new PrinterMatrix();
            Extenso e = new Extenso();

            e.setNumber(20.30);
            //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
            int filas = tbDetalle.getRowCount();
            int tamaño = filas+40;
            printer.setOutSize(tamaño, 80);

            //Imprimir = 1ra linea de la columa de 1 a 32
            printer.printTextWrap(0, 1, 3, 40, "========================================");
            printer.printTextWrap(1, 1, 32, 80, "FARMAMILI"); //NOMBRE EMPRESA
            printer.printTextWrap(3, 1, 28, 80, "de Alfonso Avalos Gimenez."); //PROPIETARIO
            printer.printTextWrap(4, 1, 16, 80, "Jose M. A. Godoy c/ Las Residentas - (0971) 799 004"); //DIRECCION
            printer.printTextWrap(5, 1, 3, 80, "_____________________________________________________________________________");
            printer.printTextWrap(6, 1, 3, 80, "FECHA: " + Fecha.fechaCorrecta() + " " + Fecha.darHora()); //FECHA Y HORA
            printer.printTextWrap(6, 1, 65, 80, lbCond.getText()); //CONDICION DE VENTA
            printer.printTextWrap(7, 1, 3, 80, "TICKET N°: " + txtCodFactura.getText()); //FACTURA O PEDIDO
            printer.printTextWrap(7, 1, 65, 80, "CAJA N°: " + txtCaja.getText()); //CAJA
            printer.printTextWrap(8, 1, 3, 80, "VENDEDOR: " + lbEmpleado.getText()); //VENDEDOR
            printer.printTextWrap(9, 1, 3, 80, "CLIENTE: " + txtRuc.getText() + " / " + txtRazonS.getText());//RUC Y RS
            printer.printTextWrap(10, 1, 3, 80, "_____________________________________________________________________________");
            printer.printTextWrap(11, 1, 3, 80, "DESCRIPCION                            CANT  P.PUBL  %DESC  P.UNIT   TOTAL");
            printer.printTextWrap(12, 1, 0, 80, " ");

            for (int i = 0; i < filas; i++) {
                int p = 13 + i; //Fila
                DecimalFormat formateador = new DecimalFormat("#,###");
                String DES = printer.alinharADireita(10, tbDetalle.getValueAt(i, 2).toString());
                String Cant = tbDetalle.getValueAt(i, 3).toString();
                String Ppublic = tbDetalle.getValueAt(i, 17).toString();
                String Desc = tbDetalle.getValueAt(i, 16).toString();
                String Punit = tbDetalle.getValueAt(i, 5).toString();
                String Mont = tbDetalle.getValueAt(i, 13).toString();
                printer.printTextWrap(p, 1, 3, 40, DES);
                printer.printTextWrap(p, 1, 43, 46, formateador.format(Integer.parseInt(Cant.replace(".", "").replace(",", ""))));
                printer.printTextWrap(p, 1, 48, 57, formateador.format(Integer.parseInt(Ppublic.replace(".", "").replace(",", ""))));
                printer.printTextWrap(p, 1, 58, 62, formateador.format(Integer.parseInt(Desc.replace(".", "").replace(",", ""))));
                printer.printTextWrap(p, 1, 64, 71, formateador.format(Integer.parseInt(Punit.replace(".", "").replace(",", ""))));
                printer.printTextWrap(p, 1, 72, 80, formateador.format(Integer.parseInt(Mont.replace(".", "").replace(",", ""))));

            }

            printer.printTextWrap(filas + 17, 1, 3, 80, "-----------------------------------------------------------------------------");

            DecimalFormat formateador = new DecimalFormat("#,###");
            String tot = printer.alinharADireita(10, formateador.format(Integer.parseInt(txtTotalL.getText().replace(".", "").replace(",", ""))));
            printer.printTextWrap(filas + 18, 1, 3, 20, "TOTAL A PAGAR:");
            printer.printTextWrap(filas + 18, 1, 67, 80, (tot));
            printer.printTextWrap(filas + 19, 1, 3, 80, "-----------------------------------------------------------------------------");
            String efe = printer.alinharADireita(10, txtAbono.getText());
            printer.printTextWrap(filas + 20, 1, 3, 80, "EFECTIVO : ");
            printer.printTextWrap(filas + 20, 1, 20, 80, efe);

            String cam = printer.alinharADireita(10, txtVuelto.getText());
            printer.printTextWrap(filas + 21, 1, 3, 80, "VUELTO   : ");
            printer.printTextWrap(filas + 21, 1, 20, 80, cam);

            printer.printTextWrap(filas + 22, 1, 3, 80, "=============================================================================");
            printer.printTextWrap(filas + 23, 1, 27, 80, "!GRACIAS POR SU PREFERENCIA!");
            printer.printTextWrap(filas + 26, 1, 12, 80, "E-FARM - v1.2.1 - Software Integral de Gestión Farmacéutica");
            printer.printTextWrap(filas + 27, 1, 3, 80, "=============================================================================");

            ///CREAR ARCHIVO EN CARPETA DEL PROYECTO PARA PEDIDOS
            //printer.toFile("C:\\tmp\\impresion.txt");
            printer.toFile("impresion.txt");
            FileInputStream inputStream = null;

            try {
                inputStream = new FileInputStream("impresion.txt");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
            if (inputStream == null) {
                return;
            }

            DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc document = new SimpleDoc(inputStream, docFormat, null);
            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

            if (defaultPrintService != null) {
                DocPrintJob printJob = defaultPrintService.createPrintJob();
                try {
                    printJob.print(document, attributeSet);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.err.println("No existen impresoras instaladas");
            }

            inputStream.close();
            //imprimirFin(subTotal, total, dineroR, devolucion); //ESTE METODO NO SE UTILIZARA

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al imprimir " + e);
        }
    }
     */
    // ticketera mtu matricial
    /*public static void imprimirTicket() {

        try {
            try {
                String sql = "select em_razonsocial, em_direccion, em_celular from empresa where em_indicador='S'";
                st = (MariaDbStatement) con.createStatement();
                rss = st.executeQuery(sql);
                try{
                    rss.first();
                    emp=rss.getString(1);
                    dir=rss.getString(2);
                    cel=rss.getString(3);
                } catch (SQLException e) {
                }
            } catch (SQLException ee) {
                System.out.println(ee.getMessage());
            }
            PrinterMatrix printer = new PrinterMatrix();
            Extenso e = new Extenso();

            e.setNumber(1);
            //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
            int filas = tbDetalle.getRowCount();
            int tamaño = filas+15;
            printer.setOutSize(tamaño, 80);

            //Imprimir = 1ra linea de la columa de 1 a 32
            //printer.printTextWrap(0, 1, 15, 24, "FARMAMILI"); //NOMBRE EMPRESA
            switch (emp.length()) {
                case 1 -> printer.printTextWrap(0, 1, 20, 40, emp); //NOMBRE EMPRESA
                case 2 -> printer.printTextWrap(0, 1, 19, 40, emp); //NOMBRE EMPRESA
                case 3 -> printer.printTextWrap(0, 1, 19, 40, emp); //NOMBRE EMPRESA
                case 4 -> printer.printTextWrap(0, 1, 18, 40, emp); //NOMBRE EMPRESA
                case 5 -> printer.printTextWrap(0, 1, 18, 40, emp); //NOMBRE EMPRESA
                case 6 -> printer.printTextWrap(0, 1, 17, 40, emp); //NOMBRE EMPRESA
                case 7 -> printer.printTextWrap(0, 1, 17, 40, emp); //NOMBRE EMPRESA
                case 8 -> printer.printTextWrap(0, 1, 16, 40, emp); //NOMBRE EMPRESA
                case 9 -> printer.printTextWrap(0, 1, 16, 40, emp); //NOMBRE EMPRESA
                case 10 -> printer.printTextWrap(0, 1, 15, 40, emp); //NOMBRE EMPRESA
                case 11 -> printer.printTextWrap(0, 1, 15, 40, emp); //NOMBRE EMPRESA
                case 12 -> printer.printTextWrap(0, 1, 14, 40, emp); //NOMBRE EMPRESA
                case 13 -> printer.printTextWrap(0, 1, 14, 40, emp); //NOMBRE EMPRESA
                case 14 -> printer.printTextWrap(0, 1, 13, 40, emp); //NOMBRE EMPRESA
                case 15 -> printer.printTextWrap(0, 1, 13, 40, emp); //NOMBRE EMPRESA
                case 16 -> printer.printTextWrap(0, 1, 12, 40, emp); //NOMBRE EMPRESA
                case 17 -> printer.printTextWrap(0, 1, 12, 40, emp); //NOMBRE EMPRESA
                case 18 -> printer.printTextWrap(0, 1, 11, 40, emp); //NOMBRE EMPRESA
                case 19 -> printer.printTextWrap(0, 1, 11, 40, emp); //NOMBRE EMPRESA
                case 20 -> printer.printTextWrap(0, 1, 10, 40, emp); //NOMBRE EMPRESA
                case 21 -> printer.printTextWrap(0, 1, 10, 40, emp); //NOMBRE EMPRESA
                case 22 -> printer.printTextWrap(0, 1, 9, 40, emp); //NOMBRE EMPRESA
                case 23 -> printer.printTextWrap(0, 1, 9, 40, emp); //NOMBRE EMPRESA
                case 24 -> printer.printTextWrap(0, 1, 8, 40, emp); //NOMBRE EMPRESA
                case 25 -> printer.printTextWrap(0, 1, 8, 40, emp); //NOMBRE EMPRESA
                case 26 -> printer.printTextWrap(0, 1, 7, 40, emp); //NOMBRE EMPRESA
                case 27 -> printer.printTextWrap(0, 1, 7, 40, emp); //NOMBRE EMPRESA
                case 28 -> printer.printTextWrap(0, 1, 6, 40, emp); //NOMBRE EMPRESA
                case 29 -> printer.printTextWrap(0, 1, 6, 40, emp); //NOMBRE EMPRESA
                case 30 -> printer.printTextWrap(0, 1, 5, 40, emp); //NOMBRE EMPRESA
                case 31 -> printer.printTextWrap(0, 1, 5, 40, emp); //NOMBRE EMPRESA
                case 32 -> printer.printTextWrap(0, 1, 4, 40, emp); //NOMBRE EMPRESA
                case 33 -> printer.printTextWrap(0, 1, 4, 40, emp); //NOMBRE EMPRESA
                case 34 -> printer.printTextWrap(0, 1, 3, 40, emp); //NOMBRE EMPRESA
                case 35 -> printer.printTextWrap(0, 1, 3, 40, emp); //NOMBRE EMPRESA
                case 36 -> printer.printTextWrap(0, 1, 2, 40, emp); //NOMBRE EMPRESA
                case 37 -> printer.printTextWrap(0, 1, 2, 40, emp); //NOMBRE EMPRESA
                case 38 -> printer.printTextWrap(0, 1, 1, 40, emp); //NOMBRE EMPRESA
                case 39 -> printer.printTextWrap(0, 1, 1, 40, emp); //NOMBRE EMPRESA
            }
            //printer.printTextWrap(0, 1, 15, 40, emp); //NOMBRE EMPRESA
            printer.printTextWrap(0, 1, 41, 80, "_______________________________________"); //PROPIETARIO
            //printer.printTextWrap(1, 1, 3, 37, "Jose M. A. Godoy c/ Las Residentas"); //DIRECCION
            switch (dir.length()) {
                case 1 -> printer.printTextWrap(1, 1, 20, 40, dir); //NOMBRE EMPRESA
                case 2 -> printer.printTextWrap(1, 1, 19, 40, dir); //NOMBRE EMPRESA
                case 3 -> printer.printTextWrap(1, 1, 19, 40, dir); //NOMBRE EMPRESA
                case 4 -> printer.printTextWrap(1, 1, 18, 40, dir); //NOMBRE EMPRESA
                case 5 -> printer.printTextWrap(1, 1, 18, 40, dir); //NOMBRE EMPRESA
                case 6 -> printer.printTextWrap(1, 1, 17, 40, dir); //NOMBRE EMPRESA
                case 7 -> printer.printTextWrap(1, 1, 17, 40, dir); //NOMBRE EMPRESA
                case 8 -> printer.printTextWrap(1, 1, 16, 40, dir); //NOMBRE EMPRESA
                case 9 -> printer.printTextWrap(1, 1, 16, 40, dir); //NOMBRE EMPRESA
                case 10 -> printer.printTextWrap(1, 1, 15, 40, dir); //NOMBRE EMPRESA
                case 11 -> printer.printTextWrap(1, 1, 15, 40, dir); //NOMBRE EMPRESA
                case 12 -> printer.printTextWrap(1, 1, 14, 40, dir); //NOMBRE EMPRESA
                case 13 -> printer.printTextWrap(1, 1, 14, 40, dir); //NOMBRE EMPRESA
                case 14 -> printer.printTextWrap(1, 1, 13, 40, dir); //NOMBRE EMPRESA
                case 15 -> printer.printTextWrap(1, 1, 13, 40, dir); //NOMBRE EMPRESA
                case 16 -> printer.printTextWrap(1, 1, 12, 40, dir); //NOMBRE EMPRESA
                case 17 -> printer.printTextWrap(1, 1, 12, 40, dir); //NOMBRE EMPRESA
                case 18 -> printer.printTextWrap(1, 1, 11, 40, dir); //NOMBRE EMPRESA
                case 19 -> printer.printTextWrap(1, 1, 11, 40, dir); //NOMBRE EMPRESA
                case 20 -> printer.printTextWrap(1, 1, 10, 40, dir); //NOMBRE EMPRESA
                case 21 -> printer.printTextWrap(1, 1, 10, 40, dir); //NOMBRE EMPRESA
                case 22 -> printer.printTextWrap(1, 1, 9, 40, dir); //NOMBRE EMPRESA
                case 23 -> printer.printTextWrap(1, 1, 9, 40, dir); //NOMBRE EMPRESA
                case 24 -> printer.printTextWrap(1, 1, 8, 40, dir); //NOMBRE EMPRESA
                case 25 -> printer.printTextWrap(1, 1, 8, 40, dir); //NOMBRE EMPRESA
                case 26 -> printer.printTextWrap(1, 1, 7, 40, dir); //NOMBRE EMPRESA
                case 27 -> printer.printTextWrap(1, 1, 7, 40, dir); //NOMBRE EMPRESA
                case 28 -> printer.printTextWrap(1, 1, 6, 40, dir); //NOMBRE EMPRESA
                case 29 -> printer.printTextWrap(1, 1, 6, 40, dir); //NOMBRE EMPRESA
                case 30 -> printer.printTextWrap(1, 1, 5, 40, dir); //NOMBRE EMPRESA
                case 31 -> printer.printTextWrap(1, 1, 5, 40, dir); //NOMBRE EMPRESA
                case 32 -> printer.printTextWrap(1, 1, 4, 40, dir); //NOMBRE EMPRESA
                case 33 -> printer.printTextWrap(1, 1, 4, 40, dir); //NOMBRE EMPRESA
                case 34 -> printer.printTextWrap(1, 1, 3, 40, dir); //NOMBRE EMPRESA
                case 35 -> printer.printTextWrap(1, 1, 3, 40, dir); //NOMBRE EMPRESA
                case 36 -> printer.printTextWrap(1, 1, 2, 40, dir); //NOMBRE EMPRESA
                case 37 -> printer.printTextWrap(1, 1, 2, 40, dir); //NOMBRE EMPRESA
                case 38 -> printer.printTextWrap(1, 1, 1, 40, dir); //NOMBRE EMPRESA
                case 39 -> printer.printTextWrap(1, 1, 1, 40, dir); //NOMBRE EMPRESA
            }
            //printer.printTextWrap(1, 1, 3, 37, dir); //DIRECCION
            //printer.printTextWrap(1, 1, 43, 73, "Cel:(0981) 700 414"); //DIRECCION
            printer.printTextWrap(1, 1, 43, 73, "Cel:"+cel); //DIRECCION
            printer.printTextWrap(2, 1, 1, 40, "________________________________________");
            printer.printTextWrap(2, 1, 41, 67, "FECHA: " + txtFecha.getText() + " " + Fecha.darHora()); //FECHA Y HORA
            printer.printTextWrap(2, 1, 71, 78, lbCond.getText()); //CONDICION DE VENTA
            printer.printTextWrap(3, 1, 1, 24, "TICKET N: " +txtTC.getText()+""+txtCodFactura.getText()); //FACTURA O PEDIDO
            printer.printTextWrap(3, 1, 26, 37, "CAJA N: " + txtCaja.getText()); //CAJA
            printer.printTextWrap(3, 1, 41, 80, "VENDEDOR: " + lbEmpleado.getText()); //VENDEDOR
            printer.printTextWrap(4, 1, 1, 40, "CLIENTE: " + txtRazonS.getText());//RUC Y RS
            printer.printTextWrap(4, 1, 41, 80, "R.U.C.: " + txtRuc.getText());//RUC Y RS
            printer.printTextWrap(5, 1, 1, 40, "________________________________________");
            printer.printTextWrap(5, 1, 41, 79, "CANT   P.PUBL  %DESC  P.UNIT    TOTAL");
            for (int i = 0; i < filas; i++) {
                int p = 6 + i; //Fila
                DecimalFormat formateador = new DecimalFormat("#,###");
                String DES = printer.alinharADireita(10, tbDetalle.getValueAt(i, 2).toString());
                //String DES = tbDetalle.getValueAt(i, 2).toString();
                String Cant = tbDetalle.getValueAt(i, 3).toString();
                int pp = (int) (Integer.parseInt(tbDetalle.getValueAt(i, 17).toString())/Float.parseFloat(tbDetalle.getValueAt(i, 3).toString()));
                String Ppublic = String.valueOf(pp);
                String Desc = tbDetalle.getValueAt(i, 16).toString();
                String Punit = tbDetalle.getValueAt(i, 5).toString();
                String Mont = tbDetalle.getValueAt(i, 13).toString();
                printer.printTextWrap(p, 1, 1, 40, DES);
                printer.printTextWrap(p, 1, 41, 45, formateador.format(Integer.parseInt(Cant.replace(".", "").replace(",", ""))));
                printer.printTextWrap(p, 1, 48, 54, formateador.format(Integer.parseInt(Ppublic.replace(".", "").replace(",", ""))));
                printer.printTextWrap(p, 1, 56, 61, formateador.format(Integer.parseInt(Desc.replace(".", "").replace(",", ""))));
                printer.printTextWrap(p, 1, 63, 69, formateador.format(Integer.parseInt(Punit.replace(".", "").replace(",", ""))));
                printer.printTextWrap(p, 1, 72, 79, formateador.format(Integer.parseInt(Mont.replace(".", "").replace(",", ""))));
            }
            printer.printTextWrap(filas+6, 1, 1, 40, "---------------------------------------");

            DecimalFormat formateador = new DecimalFormat("#,###");
            String tot = printer.alinharADireita(10, formateador.format(Integer.parseInt(txtTotalL.getText().replace(".", "").replace(",", ""))));
            printer.printTextWrap(filas + 6, 1, 41, 55, "TOTAL A PAGAR:");
            printer.printTextWrap(filas + 6, 1, 66, 79, (tot));
            printer.printTextWrap(filas + 7, 1, 1, 40, "---------------------------------------");
            String efe = printer.alinharADireita(10, txtAbono.getText());
            printer.printTextWrap(filas + 7, 1, 41, 52, "EFECTIVO : ");
            printer.printTextWrap(filas + 7, 1, 55, 80, efe);

            String cam = printer.alinharADireita(10, txtVuelto.getText());
            printer.printTextWrap(filas + 8, 1, 1, 12, "VUELTO   : ");
            printer.printTextWrap(filas + 8, 1, 15, 40, cam);

            printer.printTextWrap(filas + 8, 1, 41, 80, "=======================================");
            printer.printTextWrap(filas + 9, 1, 6, 34, "GRACIAS POR SU PREFERENCIA!!");
            printer.printTextWrap(filas + 9, 1, 52, 66, "SISTEMA E-FARM");
            printer.printTextWrap(filas + 10, 1, 1, 40, "=======================================");

            ///CREAR ARCHIVO EN CARPETA DEL PROYECTO PARA PEDIDOS
            //printer.toFile("C:\\tmp\\impresion.txt");
            printer.toFile("impresion.txt");
            FileInputStream inputStream = null;

            try {
                inputStream = new FileInputStream("impresion.txt");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar ticket: "+ex.getMessage());
            }
            if (inputStream == null) {
                return;
            }

            DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc document = new SimpleDoc(inputStream, docFormat, null);
            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

            if (defaultPrintService != null) {
                DocPrintJob printJob = defaultPrintService.createPrintJob();
                try {
                    printJob.print(document, attributeSet);
                    byte[] cutP = new byte[] { 0x1d, 'V', 1 };
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.err.println("No existen impresoras instaladas");
            }

            inputStream.close();
            //imprimirFin(subTotal, total, dineroR, devolucion); //ESTE METODO NO SE UTILIZARA

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al imprimir " + e);
        }
    }*/
    public static void obtenerNFactura() {
        String ip = traerIP.getIP();
        prepararBD();
        try {
            rs = stMovil.executeQuery("SELECT * FROM v_puntoemision3 WHERE ip='" + ip.trim() + "' AND tipo='L' AND estado='Activo'");
            rs.last();
            idEmision = rs.getInt("idemision");
            Timbrado = rs.getString("timbra");
            Establecimiento = rs.getString("establecimiento");
            Emision = rs.getString("puntoemision");
            Desde = rs.getString("fechadesde");
            Hasta = rs.getString("fechahasta");
            facturaactual = rs.getInt("facturaactual");
            facturafin = rs.getInt("facturafin");
            rs.close();
        } catch (SQLException ex) {
            Mensajes.error("ERROR FATAL: NO SE ENCUENTRA O NO EXISTE PUNTO DE EMISIÓN PARA ESTA TERMINAL");
            btnNuevo.setEnabled(false);
            itemNuevo.setEnabled(false);
        }
    }

    /*public static void SendData() {
        String empresa=null;
        String ruc=null;
        String celular=null;
        String direccion=null;
        try {
            int filas = tbDetalle.getRowCount();
            DecimalFormat formateador = new DecimalFormat("#,###");
            String tot = formateador.format(Integer.parseInt(txtTotalL.getText().replace(".", "").replace(",", "")));
            try {
                rs = st.executeQuery("SELECT * FROM empresa WHERE em_indicador='S'");
                rs.last();
                empresa = rs.getString("em_razonsocial");
                ruc = rs.getString("em_ruc");
                celular = rs.getString("em_telefono") + "-" + rs.getString("em_celular");
                direccion = rs.getString("em_direccion");
                rs.close();
            } catch (SQLException ex) {
                Mensajes.error("Error obteniendo datos de la empresa para la impresion de factura.");
            }
            DecimalFormat nformat = new DecimalFormat("##,###,###");
            String msg = empresa + "\n";
            getByteString(msg, 0, 0, 0, 1);
            String msg1 = "VENTAS DE LACTEOS LACTOLANDA\n";
            msg1 += "RUC: " + ruc + "\n";
            msg1 += "CEL: " + celular + "\n";
            msg1 += direccion + "\n";
            msg1 += "CNEL. OVIEDO - PARAGUAY\n";
            msg1 += "I.V.A. INCLUIDO\n";
            msg1 += "------------------------------------------------\n";//48
            mmOutputStream.write(ESC_ALIGN_CENTER);
            mmOutputStream.write(0x1C);
            mmOutputStream.write(0x2E); // Cancelamos el modo de caracteres chino (FS .)
            mmOutputStream.write(0x1B);
            mmOutputStream.write(0x74);
            mmOutputStream.write(0x10); // Seleccionamos los caracteres escape (ESC t n) - n = 16(0x10) para WPC1252
            mmOutputStream.write(getByteString(msg1, 0, 0, 0, 0));
            String msg2 = "TIMBRADO: " + Timbrado + "\n";
            msg2 += "VALIDO DESDE: " + Desde + " HASTA: " + Hasta + "\n";
            msg2 += "FACTURA " + lbCond.getText().trim() + " NRO: " + txtEstablecimiento.getText().trim()
                + "-" + txtEmision.getText().trim() + "-" + txtFacturaN.getText().trim() + "\n";
            msg2 += "FECHA/HORA: " + txtfechaF.getText().trim() + " " + txtHora.getText().trim() + "\n";
            msg2 += "VENDEDOR: " + lbEmpleado.getText().trim() + "\n";
            msg2 += "\n";
            msg2 += "CLIENTE: " + txtRazonS.getText().trim() + "\n";
            msg2 += "RUC/CI: " + txtRuc.getText().trim() + "\n";
            msg2 += "------------------------------------------------\n";
            msg2 += String.format("%1$1s %2$10s %3$1s %4$12s %5$17s", "IVA", "CANT", "", "PRECIO", "   SUB-TOTAL");
            msg2 += "\n";
            msg2 += "------------------------------------------------\n";
            for (int i = 0; i < filas; i++) {

                String codB = tbDetalle.getValueAt(i, 1).toString().trim();
                String Descripcion = tbDetalle.getValueAt(i, 2).toString().trim();
                String cant = tbDetalle.getValueAt(i, 3).toString().trim();
                String Punit = tbDetalle.getValueAt(i, 4).toString().trim();
                String Mont = tbDetalle.getValueAt(i, 8).toString().trim();
                int idiva = Integer.parseInt(tbDetalle.getValueAt(i, 9).toString().trim());
                String iva = null;
                switch (idiva) {
                    case 1 ->
                        iva = "EXENTA";
                    case 2 ->
                        iva = "5%";
                    case 3 ->
                        iva = "10%";
                    default -> {
                    }
                }
                
                
                msg2 += String.format("%1$-1s", codB + "/" + Descripcion + "\n");
                msg2 += String.format("%1$-9s %2$-12s %3$-15s %4$-9s", iva + "%", cant , Punit, Mont);
            }
            msg2 += "\n";
            mmOutputStream.write(ESC_ALIGN_LEFT);
            mmOutputStream.write(0x1C);
            mmOutputStream.write(0x2E); // Cancelamos el modo de caracteres chino (FS .)
            mmOutputStream.write(0x1B);
            mmOutputStream.write(0x74);
            mmOutputStream.write(0x10); // Seleccionamos los caracteres escape (ESC t n) - n = 16(0x10) para WPC1252
            mmOutputStream.write(getByteString(msg2, 0, 0, 0, 0));
            String msg3 = "------------------------\n";

            msg3 += "TOTAL Gs." + tot + "\n";
            msg3 += "------------------------\n";
            mmOutputStream.write(ESC_ALIGN_CENTER);
            mmOutputStream.write(0x1C);
            mmOutputStream.write(0x2E); // Cancelamos el modo de caracteres chino (FS .)
            mmOutputStream.write(0x1B);
            mmOutputStream.write(0x74);
            mmOutputStream.write(0x10); // Seleccionamos los caracteres escape (ESC t n) - n = 16(0x10) para WPC1252
            mmOutputStream.write(getByteString(msg3, 0, 0, 1, 1));
            String msg4 = d.Convertir(tot.replace(".", "").replace(",", ""), true) + "\n";
            msg4 += "------------------------------------------------\n";
            msg4 += "\n";
            msg4 += "TOTALES GRAVADA" + "\n";
            msg4 += "EXENTAS   -------->              " + txtExenta.getText().trim() + "\n";
            msg4 += "GRAV.  5% -------->              " + txt5libre.getText().trim() + "\n";
            msg4 += "GRAV. 10% -------->              " + txt10Libre.getText().trim() + "\n\n";
            msg4 += "LIQUIDACIÓN DEL I.V.A." + "\n";
            msg4 += "IVA 5%    -------->              " + txt5.getText().trim() + "\n";
            msg4 += "IVA 10%   -------->              " + txt10.getText().trim() + "\n";
            mmOutputStream.write(ESC_ALIGN_LEFT);
            mmOutputStream.write(0x1C);
            mmOutputStream.write(0x2E); // Cancelamos el modo de caracteres chino (FS .)
            mmOutputStream.write(0x1B);
            mmOutputStream.write(0x74);
            mmOutputStream.write(0x10); // Seleccionamos los caracteres escape (ESC t n) - n = 16(0x10) para WPC1252
            mmOutputStream.write(getByteString(msg4, 0, 0, 0, 0));
            String msg5 = "------------------------------------------------\n";
            int totaliva = Integer.parseInt(txt5L.getText()) + Integer.parseInt(txt10L.getText());
            msg5 += "TOTAL I.V.A.: " + nformat.format(Integer.parseInt(String.valueOf(totaliva))) + "\n";
            msg5 += "------------------------------------------------\n";
            mmOutputStream.write(ESC_ALIGN_CENTER);
            mmOutputStream.write(0x1C);
            mmOutputStream.write(0x2E); // Cancelamos el modo de caracteres chino (FS .)
            mmOutputStream.write(0x1B);
            mmOutputStream.write(0x74);
            mmOutputStream.write(0x10); // Seleccionamos los caracteres escape (ESC t n) - n = 16(0x10) para WPC1252
            mmOutputStream.write(getByteString(msg5, 0, 0, 0, 0));
            String msg6 = "EFECTIVO: 0 \n";
            msg6 += "VUELTO:   0" + "\n";
            msg6 += "\n";
            msg6 += "ORIGINAL: CLIENTE\n";
            msg6 += "\n";
            msg6 += "\n";
            mmOutputStream.write(ESC_ALIGN_LEFT);
            mmOutputStream.write(0x1C);
            mmOutputStream.write(0x2E); // Cancelamos el modo de caracteres chino (FS .)
            mmOutputStream.write(0x1B);
            mmOutputStream.write(0x74);
            mmOutputStream.write(0x10); // Seleccionamos los caracteres escape (ESC t n) - n = 16(0x10) para WPC1252
            mmOutputStream.write(getByteString(msg6, 0, 0, 0, 0));
            String msg7 = empresa + "\n";
            msg7 += "AGRADECE SU PREFERENCIA";
            msg7 += "\n\n";
            mmOutputStream.write(ESC_ALIGN_CENTER);
            mmOutputStream.write(0x1C);
            mmOutputStream.write(0x2E); // Cancelamos el modo de caracteres chino (FS .)
            mmOutputStream.write(0x1B);
            mmOutputStream.write(0x74);
            mmOutputStream.write(0x10); // Seleccionamos los caracteres escape (ESC t n) - n = 16(0x10) para WPC1252
            mmOutputStream.write(getByteString(msg7, 0, 0, 0, 0));
            mmOutputStream.write("\n\n".getBytes());

        } catch (Exception e) {
            Mensajes.error("ERROR_IFL: Ocurrio un error inesperado enviando la orden de impresión.\nMOTIVO: "+e.getMessage().toString());
        }
    }*/

    public static void imprimirTicketOriginal() {
        //Impresora matricial tmu-220
        String empresa = null;
        String ruc = null;
        String celular = null;
        String direccion = null;

        PrinterService printerService = new PrinterService();

        System.out.println(printerService.getPrinters());
        int filas = tbDetalle.getRowCount();
        DecimalFormat formateador = new DecimalFormat("#,###");
        String tot = formateador.format(Integer.parseInt(txtTotalL.getText().replace(".", "").replace(",", "")));
        try {
            rs = st.executeQuery("SELECT * FROM empresa WHERE em_indicador='S'");
            rs.last();
            empresa = rs.getString("em_razonsocial");
            ruc = rs.getString("em_ruc");
            celular = rs.getString("em_telefono") + "-" + rs.getString("em_celular");
            direccion = rs.getString("em_direccion");
            rs.close();
        } catch (SQLException ex) {
            Mensajes.error("Error obteniendo datos de la empresa para la impresion de factura.");
        }
        String Ticket= "              " + empresa + "\n";
        Ticket += "         VENTAS DE LACTEOS LACTOLANDA\n";
        Ticket += "                RUC: " + ruc + "\n";
        Ticket += "         CEL: " + celular + "\n";
        Ticket += "     " + direccion + "\n";
        Ticket += "            CNEL. OVIEDO - PARAGUAY\n";
        Ticket += "----------------------------------------------\n";
        Ticket += "              TIMBRADO: " + Timbrado + "\n";
        Ticket += "  VALIDO DESDE: " + Desde + " HASTA: " + Hasta + "\n";
        Ticket += "               I.V.A. INCLUIDO\n";
        Ticket += "----------------------------------------------\n";
        Ticket += "FACTURA " + lbCond.getText().trim() + " NRO: " + txtEstablecimiento.getText().trim()
                + "-" + txtEmision.getText().trim() + "-" + txtFacturaN.getText().trim() + "\n";
        Ticket += "FECHA/HORA: " + txtfechaF.getText().trim() + " " + txtHora.getText().trim() + "\n";
        Ticket += "VENDEDOR: " + lbEmpleado.getText().trim() + "\n";
        Ticket += "\n";
        Ticket += "CLIENTE: " + txtRazonS.getText().trim() + "\n";
        Ticket += "RUC/CI: " + txtRuc.getText().trim() + "\n";
        Ticket += "----------------------------------------------\n";
        Ticket += String.format("%1$1s %2$10s %3$1s %4$12s %5$16s", "IVA", "CANT", "", "PRECIO", "   SUBTOTAL");
        Ticket += "\n";
        Ticket += "----------------------------------------------\n";
        for (int i = 0; i < filas; i++) {
            String codB = tbDetalle.getValueAt(i, 1).toString().trim();
            String Descripcion = tbDetalle.getValueAt(i, 2).toString().trim();
            String cant = tbDetalle.getValueAt(i, 3).toString().trim();
            String Punit = tbDetalle.getValueAt(i, 4).toString().trim();
            String Mont = tbDetalle.getValueAt(i, 8).toString().trim();
            int idiva = Integer.parseInt(tbDetalle.getValueAt(i, 9).toString().trim());
            String iva = null;
            switch (idiva) {
                case 1 ->
                    iva = "EXENTA";
                case 2 ->
                    iva = "5%";
                case 3 ->
                    iva = "10%";
                default -> {
                }
            }

            Ticket += String.format("%1$1s", codB + "-" + Descripcion + "\n");
            Ticket += String.format("%1$-9s %2$-12s %3$-14s %4$-10s", iva, cant, formateador.format(Integer.parseInt(Punit.replace(".", "").replace(",", ""))), formateador.format(Integer.parseInt(Mont.replace(".", "").replace(",", ""))));
        }
        Ticket += "\n";
        Ticket += "==============================================\n";
        Ticket += String.format("%1$5s %2$20s", "TOTAL A PAGAR Gs:", tot) + "\n";
        //Ticket += "           TOTAL Gs.:"+tot+"\n";
        Ticket += "==============================================\n";
        String letras = d.Convertir(tot.replace(".", "").replace(",", ""), true);
        Ticket += String.format("%1$1s", letras + "\n");
        //Ticket += "\n";
        Ticket += "==============================================\n";
        Ticket += "\n";
        Ticket += "-------------- TOTALES GRAVADA ---------------\n";
        Ticket += "EXENTAS     ------>              " + txtExenta.getText().trim() + "\n";
        Ticket += "GRAVADA 5%  ------>              " + txt5libre.getText().trim() + "\n";
        Ticket += "GRAVADA 10% ------>              " + txt10Libre.getText().trim() + "\n";
        Ticket += "----------- LIQUIDACION DEL I.V.A. -----------\n";
        Ticket += "I.V.A. 5%   ------>              " + txt5.getText() + "\n";
        Ticket += "I.V.A. 10%  ------>              " + txt10.getText() + "\n";
        Ticket += "----------------------------------------------\n";
        String totaliva = String.valueOf(Integer.parseInt(txt5L.getText()) + Integer.parseInt(txt10L.getText()));
        Ticket += String.format("%1$5s %2$23s", "TOTAL I.V.A.", formateador.format(Integer.parseInt(totaliva.replace(".", "").replace(",", "")))) + "\n";
        Ticket += "----------------------------------------------\n";
        Ticket += "\n";
        Ticket += "EFECTIVO:  " + txtAbono.getText().trim() + "\n";
        Ticket += "VUELTO:    " + txtVuelto.getText().trim() + "\n";
        Ticket += "\n";
        Ticket += "ORIGINAL:  CLIENTE\n";
       // Ticket += "DUPLICADO: Archivo Tributario\n";
        Ticket += "\n";
        Ticket += "              " + empresa + "\n";
        Ticket += "            AGRADECE SU PREFERENCIA\n";
        Ticket += "\n";

        printerService.printString(Ticket);
        //print some stuff

        // cut that paper!
        byte[] cutP = new byte[]{0x1d, 'V', 1};
        final byte[] openCD={27,112,0,60,120};
        printerService.printBytes(openCD);
        printerService.printBytes(cutP);

    }
   
    public static void imprimirTicketDuplicado() {
        //Impresora matricial tmu-220
        String empresa = null;
        String ruc = null;
        String celular = null;
        String direccion = null;

        PrinterService printerService = new PrinterService();

        System.out.println(printerService.getPrinters());
        int filas = tbDetalle.getRowCount();
        DecimalFormat formateador = new DecimalFormat("#,###");
        String tot = formateador.format(Integer.parseInt(txtTotalL.getText().replace(".", "").replace(",", "")));
        try {
            rs = st.executeQuery("SELECT * FROM empresa WHERE em_indicador='S'");
            rs.last();
            empresa = rs.getString("em_razonsocial");
            ruc = rs.getString("em_ruc");
            celular = rs.getString("em_telefono") + "-" + rs.getString("em_celular");
            direccion = rs.getString("em_direccion");
            rs.close();
        } catch (SQLException ex) {
            Mensajes.error("Error obteniendo datos de la empresa para la impresion de factura.");
        }
        String Ticket= "              " + empresa + "\n";
        Ticket += "         VENTAS DE LACTEOS LACTOLANDA\n";
        Ticket += "                RUC: " + ruc + "\n";
        Ticket += "         CEL: " + celular + "\n";
        Ticket += "     " + direccion + "\n";
        Ticket += "            CNEL. OVIEDO - PARAGUAY\n";
        Ticket += "----------------------------------------------\n";
        Ticket += "              TIMBRADO: " + Timbrado + "\n";
        Ticket += "  VALIDO DESDE: " + Desde + " HASTA: " + Hasta + "\n";
        Ticket += "               I.V.A. INCLUIDO\n";
        Ticket += "----------------------------------------------\n";
        Ticket += "FACTURA " + lbCond.getText().trim() + " NRO: " + txtEstablecimiento.getText().trim()
                + "-" + txtEmision.getText().trim() + "-" + txtFacturaN.getText().trim() + "\n";
        Ticket += "FECHA/HORA: " + txtfechaF.getText().trim() + " " + txtHora.getText().trim() + "\n";
        Ticket += "VENDEDOR: " + lbEmpleado.getText().trim() + "\n";
        Ticket += "\n";
        Ticket += "CLIENTE: " + txtRazonS.getText().trim() + "\n";
        Ticket += "RUC/CI: " + txtRuc.getText().trim() + "\n";
        Ticket += "----------------------------------------------\n";
        Ticket += String.format("%1$1s %2$10s %3$1s %4$12s %5$16s", "IVA", "CANT", "", "PRECIO", "   SUBTOTAL");
        Ticket += "\n";
        Ticket += "----------------------------------------------\n";
        for (int i = 0; i < filas; i++) {
            String codB = tbDetalle.getValueAt(i, 1).toString().trim();
            String Descripcion = tbDetalle.getValueAt(i, 2).toString().trim();
            String cant = tbDetalle.getValueAt(i, 3).toString().trim();
            String Punit = tbDetalle.getValueAt(i, 4).toString().trim();
            String Mont = tbDetalle.getValueAt(i, 8).toString().trim();
            int idiva = Integer.parseInt(tbDetalle.getValueAt(i, 9).toString().trim());
            String iva = null;
            switch (idiva) {
                case 1 ->
                    iva = "EXENTA";
                case 2 ->
                    iva = "5%";
                case 3 ->
                    iva = "10%";
                default -> {
                }
            }

            Ticket += String.format("%1$1s", codB + "-" + Descripcion + "\n");
            Ticket += String.format("%1$-9s %2$-12s %3$-14s %4$-10s", iva, cant, formateador.format(Integer.parseInt(Punit.replace(".", "").replace(",", ""))), formateador.format(Integer.parseInt(Mont.replace(".", "").replace(",", ""))));
        }
        Ticket += "\n";
        Ticket += "==============================================\n";
        Ticket += String.format("%1$5s %2$20s", "TOTAL A PAGAR Gs:", tot) + "\n";
        //Ticket += "           TOTAL Gs.:"+tot+"\n";
        Ticket += "==============================================\n";
        String letras = d.Convertir(tot.replace(".", "").replace(",", ""), true);
        Ticket += String.format("%1$1s", letras + "\n");
        //Ticket += "\n";
        Ticket += "==============================================\n";
        Ticket += "\n";
        Ticket += "-------------- TOTALES GRAVADA ---------------\n";
        Ticket += "EXENTAS     ------>              " + txtExenta.getText().trim() + "\n";
        Ticket += "GRAVADA 5%  ------>              " + txt5libre.getText().trim() + "\n";
        Ticket += "GRAVADA 10% ------>              " + txt10Libre.getText().trim() + "\n";
        Ticket += "----------- LIQUIDACION DEL I.V.A. -----------\n";
        Ticket += "I.V.A. 5%   ------>              " + txt5.getText() + "\n";
        Ticket += "I.V.A. 10%  ------>              " + txt10.getText() + "\n";
        Ticket += "----------------------------------------------\n";
        String totaliva = String.valueOf(Integer.parseInt(txt5L.getText()) + Integer.parseInt(txt10L.getText()));
        Ticket += String.format("%1$5s %2$23s", "TOTAL I.V.A.", formateador.format(Integer.parseInt(totaliva.replace(".", "").replace(",", "")))) + "\n";
        Ticket += "----------------------------------------------\n";
        Ticket += "\n";
        Ticket += "EFECTIVO:  " + txtAbono.getText().trim() + "\n";
        Ticket += "VUELTO:    " + txtVuelto.getText().trim() + "\n";
        Ticket += "\n";
        Ticket += "DUPLICADO:  ARCHIVO TRIBUTARIO\n";
       // Ticket += "DUPLICADO: Archivo Tributario\n";
        Ticket += "\n";
        Ticket += "              " + empresa + "\n";
        Ticket += "            AGRADECE SU PREFERENCIA\n";
        Ticket += "\n";

        printerService.printString(Ticket);
        //print some stuff

        // cut that paper!
        byte[] cutP = new byte[]{0x1d, 'V', 1};

        printerService.printBytes(cutP);

    }
    /*public static void imprimirTicket() {
        //Impresora termica
        PrinterService printerService = new PrinterService();

        System.out.println(printerService.getPrinters());
        int filas = tbDetalle.getRowCount();
        DecimalFormat formateador = new DecimalFormat("#,###");
        String tot = formateador.format(Integer.parseInt(txtTotalL.getText().replace(".", "").replace(",", "")));

        String Ticket = "                TICKET DE VENTA\n";
        Ticket += "-----------------------------------------------\n";
        Ticket += "FECHA: " + txtFecha.getText().trim() + " " + Fecha.darHora() + "\n";
        Ticket += "VENDEDOR/A: " + lbEmpleado.getText().trim() + "\n";
        Ticket += "-----------------------------------------------\n";
        for (int i = 0; i < filas; i++) {
            String Punit = tbDetalle.getValueAt(i, 5).toString().trim();
            String Mont = tbDetalle.getValueAt(i, 13).toString().trim();
            String Descripcion = tbDetalle.getValueAt(i, 2).toString().trim();
            Ticket += String.format("%1$1s", (i + 1) + "- " + Descripcion + "\n");
            //Ticket += String.format("%1$11s %2$15s %3$19s" ,"CANT: "+tbDetalle.getValueAt(i, 3).toString(), "PRECIO: "+formateador.format(Integer.parseInt(Punit.replace(".", "").replace(",", ""))), "SUBTOTAL: "+formateador.format(Integer.parseInt(Mont.replace(".", "").replace(",", ""))))+ "\n";
            Ticket += String.format("%1$-11s %2$-15s %3$-19s", "CANT: " + tbDetalle.getValueAt(i, 3).toString(), "PRECIO: " + formateador.format(Integer.parseInt(Punit.replace(".", "").replace(",", ""))), "SUBTOTAL: " + formateador.format(Integer.parseInt(Mont.replace(".", "").replace(",", "")))) + "\n";
        }
        Ticket += "-----------------------------------------------\n";
        Ticket += String.format("%1$6s %2$32s", "TOTAL A PAGAR:", tot) + "\n";
        Ticket += "-----------------------------------------------\n";
        Ticket += "          GRACIAS POR SU PREFERENCIA!\n";
        Ticket += "\n\n\n\n\n\n";

        printerService.printString("POS-80C", Ticket);
        //print some stuff

        // cut that paper!
        byte[] cutP = new byte[]{0x1d, 'V', 1};

        printerService.printBytes("POS-80C", cutP);

    }*/
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        dlgFinFactura = new javax.swing.JDialog();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtAbono = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtVuelto = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnVerBoleta = new javax.swing.JButton();
        btnModificarV = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtCodVendedor = new javax.swing.JTextField();
        lbEmpleado = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuEmergente = new javax.swing.JPopupMenu();
        itemCantidad = new javax.swing.JMenuItem();
        Blanco = new org.edisoncor.gui.panel.PanelImage();
        Oscuro = new org.edisoncor.gui.panel.PanelImage();
        jPanel4 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lbCred = new javax.swing.JLabel();
        txtdisponibleL = new javax.swing.JTextField();
        lbCond = new javax.swing.JLabel();
        txtTotalL = new javax.swing.JTextField();
        btnRestar = new javax.swing.JButton();
        txtCodCliente = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        txtFecha = new javax.swing.JTextField();
        txtidEmision = new javax.swing.JTextField();
        btnCant = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtCaja = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtFacturaN = new javax.swing.JTextField();
        txtEstablecimiento = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        txtRazonS = new javax.swing.JTextField();
        txtRuc = new javax.swing.JTextField();
        btnBuscarClientes = new javax.swing.JButton();
        txtfechaF = new javax.swing.JTextField();
        lbTimbrado = new javax.swing.JLabel();
        lbValidaz = new javax.swing.JLabel();
        txtEmision = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtArt = new javax.swing.JTextField();
        txtCant = new javax.swing.JTextField();
        btnBuscarArticulo = new javax.swing.JButton();
        etiCant = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDetalle = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowInddex, int celIndex)
            {
                return false;
            }
        };
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtExenta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt5 = new javax.swing.JTextField();
        txt10 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNetoL = new javax.swing.JTextField();
        txtDescuentoL = new javax.swing.JTextField();
        txtCodArticulo = new javax.swing.JTextField();
        txtExentaL = new javax.swing.JTextField();
        txt5L = new javax.swing.JTextField();
        txt10L = new javax.swing.JTextField();
        txt5libre = new javax.swing.JTextField();
        txt10Libre = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        rContado = new javax.swing.JRadioButton();
        rCredito = new javax.swing.JRadioButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itemNuevo = new javax.swing.JMenuItem();
        itemModificar = new javax.swing.JMenuItem();
        itemGuardar = new javax.swing.JMenuItem();
        itemCancelar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemBuscarC = new javax.swing.JMenuItem();
        itemBuscarA = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        itemQuitar = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        itemSalir = new javax.swing.JMenuItem();

        dlgFinFactura.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        dlgFinFactura.setResizable(false);
        dlgFinFactura.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                dlgFinFacturaWindowOpened(evt);
            }
        });
        dlgFinFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dlgFinFacturaKeyPressed(evt);
            }
        });

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "VUELTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(0, 153, 0))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setText("EFECTIVO");

        txtAbono.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
        txtAbono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAbono.setText("0");
        txtAbono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAbonoMouseClicked(evt);
            }
        });
        txtAbono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbonoActionPerformed(evt);
            }
        });
        txtAbono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAbonoKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setText("VUELTO");

        txtVuelto.setEditable(false);
        txtVuelto.setBackground(new java.awt.Color(255, 255, 255));
        txtVuelto.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
        txtVuelto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtVuelto.setText("0");
        txtVuelto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVueltoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtVuelto, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(txtAbono))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAbono, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtVuelto, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addGap(42, 42, 42))
        );

        jPanel13.setBackground(new java.awt.Color(17, 35, 46));
        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("TOTAL A COBRAR");

        lblTotal.setFont(new java.awt.Font("Digital-7 Mono", 1, 36)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 0, 0));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setText("0");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("EFECTIVO", jPanel10);

        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/accept.png"))); // NOI18N
        btnConfirmar.setText("CONFIRMAR");
        btnConfirmar.setEnabled(false);
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnVerBoleta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/printer30.png"))); // NOI18N
        btnVerBoleta.setText("VER TICKET");
        btnVerBoleta.setEnabled(false);
        btnVerBoleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerBoletaActionPerformed(evt);
            }
        });

        btnModificarV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/edit30.png"))); // NOI18N
        btnModificarV.setText("MODIFICAR");
        btnModificarV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarVActionPerformed(evt);
            }
        });

        jLabel9.setText("ID Vendedor");

        txtCodVendedor.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtCodVendedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVendedorActionPerformed(evt);
            }
        });
        txtCodVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodVendedorKeyPressed(evt);
            }
        });

        lbEmpleado.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 11)); // NOI18N
        lbEmpleado.setForeground(new java.awt.Color(27, 57, 84));
        lbEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jMenu2.setText("Opciones");
        jMenu2.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jMenu2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/save15.png"))); // NOI18N
        jMenuItem1.setText("Confirmar Venta");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/edit15.png"))); // NOI18N
        jMenuItem2.setText("Modificar Venta");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar2.add(jMenu2);

        dlgFinFactura.setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout dlgFinFacturaLayout = new javax.swing.GroupLayout(dlgFinFactura.getContentPane());
        dlgFinFactura.getContentPane().setLayout(dlgFinFacturaLayout);
        dlgFinFacturaLayout.setHorizontalGroup(
            dlgFinFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgFinFacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgFinFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dlgFinFacturaLayout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(dlgFinFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnModificarV, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnVerBoleta, javax.swing.GroupLayout.PREFERRED_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(dlgFinFacturaLayout.createSequentialGroup()
                        .addGroup(dlgFinFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(dlgFinFacturaLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCodVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        dlgFinFacturaLayout.setVerticalGroup(
            dlgFinFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgFinFacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgFinFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCodVendedor)
                    .addComponent(lbEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(dlgFinFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dlgFinFacturaLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVerBoleta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnModificarV))
                    .addGroup(dlgFinFacturaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("EFECTIVO");

        itemCantidad.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        itemCantidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Modify.png"))); // NOI18N
        itemCantidad.setText("Modificar Cantidad");
        itemCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCantidadActionPerformed(evt);
            }
        });
        menuEmergente.add(itemCantidad);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        Blanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/fondoBlanco.jpg"))); // NOI18N
        Blanco.setPreferredSize(new java.awt.Dimension(690, 418));

        Oscuro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/fondo.png"))); // NOI18N
        Oscuro.setPreferredSize(new java.awt.Dimension(690, 418));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        btnNuevo.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 9)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/document30.png"))); // NOI18N
        btnNuevo.setText("Nuevo-F1");
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setMaximumSize(new java.awt.Dimension(85, 57));
        btnNuevo.setMinimumSize(new java.awt.Dimension(85, 57));
        btnNuevo.setPreferredSize(new java.awt.Dimension(90, 60));
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel4.add(btnNuevo);

        btnModificar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 9)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/edit30.png"))); // NOI18N
        btnModificar.setText("Modif-F5");
        btnModificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificar.setMaximumSize(new java.awt.Dimension(63, 57));
        btnModificar.setMinimumSize(new java.awt.Dimension(63, 57));
        btnModificar.setPreferredSize(new java.awt.Dimension(90, 60));
        btnModificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel4.add(btnModificar);

        btnGuardar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 9)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/save30.png"))); // NOI18N
        btnGuardar.setText("Guardar-F6");
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setMaximumSize(new java.awt.Dimension(71, 57));
        btnGuardar.setMinimumSize(new java.awt.Dimension(71, 57));
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel4.add(btnGuardar);

        btnCancelar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 9)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancel30.png"))); // NOI18N
        btnCancelar.setText("Cancel-Esc");
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelar.setMaximumSize(new java.awt.Dimension(75, 57));
        btnCancelar.setMinimumSize(new java.awt.Dimension(75, 57));
        btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCancelar);

        btnSalir.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 9)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/back30.png"))); // NOI18N
        btnSalir.setText("Salir-Alt+F4");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalir);

        lbCred.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        lbCred.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbCond.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lbCond.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTotalL.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTotalL.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalL.setText("0");

        btnRestar.setText("R");
        btnRestar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestarActionPerformed(evt);
            }
        });

        txtCodCliente.setEditable(false);
        txtCodCliente.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 12)); // NOI18N
        txtCodCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodClienteActionPerformed(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Create.png"))); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        txtFecha.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });

        txtidEmision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidEmisionActionPerformed(evt);
            }
        });

        btnCant.setText("Act. Precio");
        btnCant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCantActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout OscuroLayout = new javax.swing.GroupLayout(Oscuro);
        Oscuro.setLayout(OscuroLayout);
        OscuroLayout.setHorizontalGroup(
            OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OscuroLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbCred, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtdisponibleL, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbCond, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTotalL, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OscuroLayout.createSequentialGroup()
                        .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(OscuroLayout.createSequentialGroup()
                        .addComponent(btnRestar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnCant, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        OscuroLayout.setVerticalGroup(
            OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OscuroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbCond, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(OscuroLayout.createSequentialGroup()
                        .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbCred, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRestar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd)
                            .addComponent(btnCant))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtdisponibleL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtidEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setOpaque(false);

        jLabel1.setText("Operación N°");

        txtCod.setEditable(false);
        txtCod.setBackground(new java.awt.Color(255, 255, 255));
        txtCod.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtCod.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setText("Fecha y Hora");

        jLabel19.setText("Mov. Caja N°");

        txtCaja.setEditable(false);
        txtCaja.setBackground(new java.awt.Color(255, 255, 255));
        txtCaja.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtCaja.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setText("Comprobante  N°: ");

        txtFacturaN.setEditable(false);
        txtFacturaN.setBackground(new java.awt.Color(255, 255, 255));
        txtFacturaN.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtFacturaN.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFacturaN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFacturaNActionPerformed(evt);
            }
        });
        txtFacturaN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFacturaNKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFacturaNKeyTyped(evt);
            }
        });

        txtEstablecimiento.setEditable(false);
        txtEstablecimiento.setBackground(new java.awt.Color(255, 255, 255));
        txtEstablecimiento.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtEstablecimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstablecimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstablecimientoActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtRazonS.setEditable(false);
        txtRazonS.setBackground(new java.awt.Color(255, 255, 255));
        txtRazonS.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        txtRazonS.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRazonS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRazonSActionPerformed(evt);
            }
        });

        txtRuc.setEditable(false);
        txtRuc.setBackground(new java.awt.Color(255, 255, 255));
        txtRuc.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        txtRuc.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnBuscarClientes.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnBuscarClientes.setForeground(new java.awt.Color(0, 0, 51));
        btnBuscarClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/search15.png"))); // NOI18N
        btnBuscarClientes.setText("F3-Clientes");
        btnBuscarClientes.setBorderPainted(false);
        btnBuscarClientes.setContentAreaFilled(false);
        btnBuscarClientes.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btnBuscarClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBuscarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClientesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(btnBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRazonS, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRazonS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscarClientes))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtfechaF.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtfechaF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lbTimbrado.setBackground(new java.awt.Color(17, 35, 46));
        lbTimbrado.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbTimbrado.setForeground(new java.awt.Color(255, 255, 255));
        lbTimbrado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTimbrado.setText("TIMBRADO N°: ");
        lbTimbrado.setOpaque(true);

        lbValidaz.setBackground(new java.awt.Color(17, 35, 46));
        lbValidaz.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbValidaz.setForeground(new java.awt.Color(255, 255, 255));
        lbValidaz.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbValidaz.setText("VALIDEZ:");
        lbValidaz.setOpaque(true);

        txtEmision.setEditable(false);
        txtEmision.setBackground(new java.awt.Color(255, 255, 255));
        txtEmision.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtEmision.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmisionActionPerformed(evt);
            }
        });

        txtHora.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtHora.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtfechaF, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHora, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                            .addComponent(txtCaja)
                            .addComponent(txtCod))
                        .addGap(27, 27, 27)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbValidaz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(txtEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(txtFacturaN, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbTimbrado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(lbValidaz, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHora, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtfechaF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtFacturaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(17, 35, 46));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("TOTAL A FACTURAR");

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(51, 51, 51));
        txtTotal.setFont(new java.awt.Font("Digital-7 Mono", 1, 40)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(255, 0, 0));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setText("0");
        txtTotal.setBorder(null);
        txtTotal.setOpaque(false);
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTotal)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);

        txtArt.setEditable(false);
        txtArt.setBackground(new java.awt.Color(255, 255, 255));
        txtArt.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtArt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtArtActionPerformed(evt);
            }
        });
        txtArt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtArtKeyReleased(evt);
            }
        });

        txtCant.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtCant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantActionPerformed(evt);
            }
        });
        txtCant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantKeyPressed(evt);
            }
        });

        btnBuscarArticulo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnBuscarArticulo.setForeground(new java.awt.Color(0, 0, 51));
        btnBuscarArticulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Create.png"))); // NOI18N
        btnBuscarArticulo.setText("F9-Agregar");
        btnBuscarArticulo.setActionCommand("F9 - Buscar Artículo");
        btnBuscarArticulo.setBorderPainted(false);
        btnBuscarArticulo.setContentAreaFilled(false);
        btnBuscarArticulo.setFocusable(false);
        btnBuscarArticulo.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btnBuscarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarArticuloActionPerformed(evt);
            }
        });

        etiCant.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        etiCant.setText("Artículos registrados:");

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPane1MousePressed(evt);
            }
        });

        tbDetalle.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tbDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDetalle.getTableHeader().setResizingAllowed(false);
        tbDetalle.getTableHeader().setReorderingAllowed(false);
        tbDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetalleMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbDetalleMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbDetalle);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 9)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("EXCENTAS");

        txtExenta.setEditable(false);
        txtExenta.setBackground(new java.awt.Color(255, 255, 255));
        txtExenta.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtExenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExenta.setText("0");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 9)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("IVA 5%");

        txt5.setEditable(false);
        txt5.setBackground(new java.awt.Color(255, 255, 255));
        txt5.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txt5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt5.setText("0");

        txt10.setEditable(false);
        txt10.setBackground(new java.awt.Color(255, 255, 255));
        txt10.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txt10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt10.setText("0");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 9)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("IVA 10%");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtExenta, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt5)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt10)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(3, 3, 3)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtExenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        txtNetoL.setEditable(false);
        txtNetoL.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNetoL.setText("0");

        txtDescuentoL.setEditable(false);
        txtDescuentoL.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescuentoL.setText("0");

        txtCodArticulo.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        txtCodArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodArticuloActionPerformed(evt);
            }
        });

        txtExentaL.setEditable(false);
        txtExentaL.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExentaL.setText("0");

        txt5L.setEditable(false);
        txt5L.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt5L.setText("0");

        txt10L.setEditable(false);
        txt10L.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt10L.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(etiCant, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtNetoL, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescuentoL, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtExentaL, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt5L, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt10L, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(txt5libre, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt10Libre, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnBuscarArticulo)
                        .addGap(0, 0, 0)
                        .addComponent(txtArt, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(etiCant)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDescuentoL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNetoL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt10L, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt5L, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtExentaL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt5libre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt10Libre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(java.awt.Color.white);
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup1.add(rContado);
        rContado.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        rContado.setSelected(true);
        rContado.setText("CONTADO");
        rContado.setOpaque(false);
        rContado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rContadoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rCredito);
        rCredito.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        rCredito.setText("CREDITO");
        rCredito.setOpaque(false);
        rCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rCreditoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rContado, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rContado, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BlancoLayout = new javax.swing.GroupLayout(Blanco);
        Blanco.setLayout(BlancoLayout);
        BlancoLayout.setHorizontalGroup(
            BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BlancoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BlancoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(Oscuro, javax.swing.GroupLayout.DEFAULT_SIZE, 926, Short.MAX_VALUE)
        );
        BlancoLayout.setVerticalGroup(
            BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BlancoLayout.createSequentialGroup()
                .addComponent(Oscuro, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(BlancoLayout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Opciones");
        jMenu1.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jMenu1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        itemNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        itemNuevo.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/document15.png"))); // NOI18N
        itemNuevo.setText("Nuevo");
        itemNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNuevoActionPerformed(evt);
            }
        });
        jMenu1.add(itemNuevo);

        itemModificar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        itemModificar.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/edit15.png"))); // NOI18N
        itemModificar.setText("Guardar Modificación");
        itemModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemModificarActionPerformed(evt);
            }
        });
        jMenu1.add(itemModificar);

        itemGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        itemGuardar.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/save15.png"))); // NOI18N
        itemGuardar.setText("Guardar Nuevo");
        itemGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGuardarActionPerformed(evt);
            }
        });
        jMenu1.add(itemGuardar);

        itemCancelar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        itemCancelar.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancel15.png"))); // NOI18N
        itemCancelar.setText("Cancelar");
        itemCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCancelarActionPerformed(evt);
            }
        });
        jMenu1.add(itemCancelar);
        jMenu1.add(jSeparator1);

        itemBuscarC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        itemBuscarC.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemBuscarC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/search20.png"))); // NOI18N
        itemBuscarC.setText("Cambiar Cliente");
        itemBuscarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBuscarCActionPerformed(evt);
            }
        });
        jMenu1.add(itemBuscarC);

        itemBuscarA.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        itemBuscarA.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemBuscarA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/search20.png"))); // NOI18N
        itemBuscarA.setText("Buscar Artículo");
        itemBuscarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBuscarAActionPerformed(evt);
            }
        });
        jMenu1.add(itemBuscarA);
        jMenu1.add(jSeparator4);

        itemQuitar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        itemQuitar.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/vcsremoved_93492 - copia.png"))); // NOI18N
        itemQuitar.setText("Quitar Artículo");
        itemQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemQuitarActionPerformed(evt);
            }
        });
        jMenu1.add(itemQuitar);
        jMenu1.add(jSeparator5);

        itemSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        itemSalir.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/back15.png"))); // NOI18N
        itemSalir.setText("Salir");
        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalirActionPerformed(evt);
            }
        });
        jMenu1.add(itemSalir);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(Blanco, javax.swing.GroupLayout.PREFERRED_SIZE, 926, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Blanco, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        int rpta = Mensajes.confirmar("¿Seguro que desea salir del formulario?");
        if (rpta == 0) {
            controlFactura.canCelar();
            this.dispose();
        }

    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        controlFactura.addTabla(tbDetalle);
        Rendes();
        cant();
        txtCodArticulo.setText("");
        txtArt.setText("");
        txtCant.setText("");
        btnBuscarArticulo.requestFocus();
        habilitarCANTCOSTO();
        btnBuscarArticuloActionPerformed(null);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRestarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestarActionPerformed
        // TODO add your handling code here:
        try {
            controlFactura.delRenglon(tbDetalle);
            Rendes();
            cant();
        } catch (Exception e) {
            Mensajes.error("Seleccione una fila de la tabla");
        }
    }//GEN-LAST:event_btnRestarActionPerformed

    private void txtCantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantKeyPressed
        // TODO add your handling code here:
        validarCampos.soloDecimales(txtCant);
        ArticuloMovil Ar = GestionarArticulosMovil.busArticulo((txtCodArticulo.getText()));
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtCant.getText().isEmpty()) {
                txtCant.selectAll();
            } else if (txtCant.getText().equals("0")) {
                txtCant.selectAll();
            } else if (Double.parseDouble(txtCant.getText()) == 0.0) {
                txtCant.selectAll();
            } else if (Double.parseDouble(txtCant.getText()) > Ar.getStock()) {
                Mensajes.error("La cantidad que estas intentando vender supera el stock actual del producto");
                txtCant.requestFocus();
                txtCant.setText(String.valueOf(Ar.getStock()).trim().replace(".0", "").replace(",", ""));
                txtCant.selectAll();
            } else {
                btnAdd.doClick();
            }
        }
    }//GEN-LAST:event_txtCantKeyPressed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        obtenerNFactura();
        if (facturaactual <= facturafin) {
            String cod = GestionarFactura.getCodigo();
            txtCod.setText(cod);
            txtidEmision.setText(String.valueOf(idEmision));
            txtEstablecimiento.setText(Establecimiento);
            txtEmision.setText(Emision);
            switch (String.valueOf(facturaactual).length()) {
                case 1 ->
                    txtFacturaN.setText("000000" + (facturaactual + 1));
                case 2 ->
                    txtFacturaN.setText("00000" + (facturaactual + 1));
                case 3 ->
                    txtFacturaN.setText("0000" + (facturaactual + 1));
                case 4 ->
                    txtFacturaN.setText("000" + (facturaactual + 1));
                case 5 ->
                    txtFacturaN.setText("00" + (facturaactual + 1));
                case 6 ->
                    txtFacturaN.setText("0" + (facturaactual + 1));
                case 7 ->
                    txtFacturaN.setText(String.valueOf(facturaactual + 1));
                default -> {
                }
            }
            try {
                String FechaI = String.valueOf(Fecha.fechaCorrecta());
                txtCaja.setText(generarCodigos.ObtenerCodigo("SELECT ca_id from caja WHERE ca_fechainicio='" + FechaI + "' and ca_indicador='S'"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            ClienteMovil Cl = GestionarCliente.busCliente("1");
            controlFactura.selectClienteInicio("1");
            //txtCodCliente.setText(String.valueOf(Cl.getCodCliente()));
            //txtRuc.setText(String.valueOf(Cl.getRuc()));
            //txtRazonS.setText(" " + String.valueOf(Cl.getRazonSocial()));
            //lbCred.setText(Cl.getCredito());
            rContado.setSelected(true);
            pintarCondicion();
            txtFecha.setText(Fecha.fechaCorrecta());
            txtfechaF.setText(Fecha.fechaCorrectaFachada());
            txtHora.setText(Fecha.darHoraSinSS());
            btnBuscarClientes.setEnabled(true);
            rContado.setEnabled(true);
            rCredito.setEnabled(true);
            btnBuscarArticulo.setEnabled(true);
            txtCant.setEnabled(true);
            btnNuevo.setEnabled(false);
            itemNuevo.setEnabled(false);
            btnModificar.setEnabled(false);
            itemModificar.setEnabled(false);
            btnGuardar.setEnabled(true);
            itemGuardar.setEnabled(true);
            btnCancelar.setEnabled(true);
            itemCancelar.setEnabled(true);
            itemBuscarA.setEnabled(true);
            itemBuscarC.setEnabled(true);
            itemQuitar.setEnabled(true);
            btnSalir.setEnabled(false);
            itemSalir.setEnabled(false);
            btnBuscarArticulo.doClick();
            habilitarCANTCOSTO();
        } else {
            Mensajes.error("OBSERVACIÓN:\nNo es posible continuar vendiendo.\nSe ha alcanzado la cantidad máxima de facturación para el punto de expedición o emisión actual.\n");
        }

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (tbDetalle.getRowCount() <= 0) {
            Mensajes.informacion("La lista de productos a facturar esta vacía.\nRECOMENDACIÓN: Inserte en la tabla los detalles de la venta e intente procesarlo nuevamente.");
            btnBuscarArticulo.doClick();
        } else {
            if (lbCond.getText().equals("CONTADO")) {
                DecimalFormat df = new DecimalFormat("#,###");
                if (dlgVentas.txtTotalL.getText().trim().length() >= 1) {
                    dlgVentas.lblTotal.setText(df.format(Integer.valueOf(dlgVentas.txtTotalL.getText().trim().replace(".", "").replace(",", ""))));
                }
                dlgFinFactura.setSize(325, 390);
                dlgFinFactura.setLocationRelativeTo(this);
                dlgFinFactura.setModal(true);
                dlgFinFactura.setTitle("Finalizar Venta");
                dlgFinFactura.setVisible(true);
                btnConfirmar.requestFocus();
            } else {
                DecimalFormat df = new DecimalFormat("#,###");
                if (dlgVentas.txtTotalL.getText().trim().length() >= 1) {
                    dlgVentas.lblTotal.setText(df.format(Integer.valueOf(dlgVentas.txtTotalL.getText().trim().replace(".", "").replace(",", ""))));
                    dlgFinFactura.setSize(325, 390);
                    dlgFinFactura.setLocationRelativeTo(this);
                    dlgFinFactura.setModal(true);
                    dlgFinFactura.setTitle("Finalizar Venta");
                    dlgFinFactura.setVisible(true);
                    btnConfirmar.requestFocus();
                } else {
                    Mensajes.informacion("Línea de crédito insuficiente");
                }
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        int rpta = Mensajes.confirmar("¿Seguro que desea Cancelar esta VENTA?");
        if (rpta == 0) {
            limpiarCampos();
            //dcFecha.setEnabled(false);
            btnBuscarClientes.setEnabled(false);
            rContado.setEnabled(false);
            rContado.setSelected(true);
            rCredito.setEnabled(false);
            btnBuscarArticulo.setEnabled(false);
            txtCant.setEnabled(false);
            btnNuevo.setEnabled(true);
            btnNuevo.requestFocus();
            itemNuevo.setEnabled(true);
            btnModificar.setEnabled(false);
            itemModificar.setEnabled(false);
            btnGuardar.setEnabled(false);
            itemGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
            itemCancelar.setEnabled(false);
            itemBuscarA.setEnabled(false);
            itemBuscarC.setEnabled(false);
            itemQuitar.setEnabled(false);
            btnSalir.setEnabled(true);
            itemSalir.setEnabled(true);
            cant();
        }

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBuscarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClientesActionPerformed
        // TODO add your handling code here:
        try {
            dlgBuscarCliente bcliente = new dlgBuscarCliente(null, true);
            bcliente.setLocationRelativeTo(null);
            bcliente.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No hay conexión con el servidor");
        }

    }//GEN-LAST:event_btnBuscarClientesActionPerformed

    private void btnBuscarArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarArticuloActionPerformed
        // TODO add your handling code here:
        try {
            dlgBuscarArticuloVenta baf = new dlgBuscarArticuloVenta(null, true);
            //baf.setLocationRelativeTo(null);
           // baf.setLocation(310, 365);
            baf.setLocation(125, 365);
            baf.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No hay conexión con el servidor");
        }

    }//GEN-LAST:event_btnBuscarArticuloActionPerformed

    private void rContadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rContadoActionPerformed
        // TODO add your handling code here:
        pintarCondicion();
    }//GEN-LAST:event_rContadoActionPerformed

    private void rCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rCreditoActionPerformed
        // TODO add your handling code here:
        pintarCondicion();
    }//GEN-LAST:event_rCreditoActionPerformed

    private void itemNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNuevoActionPerformed
        // TODO add your handling code here:
        btnNuevoActionPerformed(null);
    }//GEN-LAST:event_itemNuevoActionPerformed

    private void itemModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemModificarActionPerformed
        // TODO add your handling code here:
        btnModificarActionPerformed(null);
    }//GEN-LAST:event_itemModificarActionPerformed

    private void itemGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuardarActionPerformed
        // TODO add your handling code here:
        btnGuardarActionPerformed(null);
    }//GEN-LAST:event_itemGuardarActionPerformed

    private void itemCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCancelarActionPerformed
        // TODO add your handling code here:
        btnCancelarActionPerformed(null);
    }//GEN-LAST:event_itemCancelarActionPerformed

    private void itemBuscarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBuscarAActionPerformed
        // TODO add your handling code here:
        btnBuscarArticulo.doClick();
    }//GEN-LAST:event_itemBuscarAActionPerformed

    private void itemQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemQuitarActionPerformed
        // TODO add your handling code here:S
        btnRestar.doClick();
    }//GEN-LAST:event_itemQuitarActionPerformed

    private void txtCantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtCantActionPerformed

    private void txtArtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtArtActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtArtActionPerformed

    private void txtArtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArtKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtArtKeyReleased

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtFacturaNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFacturaNKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFacturaNKeyPressed

    private void txtFacturaNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFacturaNKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();

        if (Character.isLetter(c)) {
            getToolkit().beep();

            evt.consume();

            System.out.println("Ingresa Solo Numeros");
        }
        int limite = 15;
        if (txtFacturaN.getText().length() == limite) {
            evt.consume();
        }
    }//GEN-LAST:event_txtFacturaNKeyTyped

    private void txtRazonSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRazonSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonSActionPerformed

    private void txtAbonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAbonoKeyReleased
        // TODO add your handling code here:
        try {
            if (txtAbono.getText().trim().length() >= 1) {
                DecimalFormat df = new DecimalFormat("#,###");
                txtAbono.setText(df.format(Integer.valueOf(txtAbono.getText().trim().replace(".", "").replace(",", ""))));

            } else {
                txtAbono.setText("0");
                txtAbono.selectAll();

            }
        } catch (NumberFormatException e) {
            System.out.println("c: " + e.getMessage());
        }
        /* try {
            if (txtAbono.getText().trim().length() >= 1) {
                DecimalFormat dff = new DecimalFormat("#0");
                txtAbonoL.setText(dff.format(Integer.valueOf(txtAbono.getText().trim().replace(".", "").replace(",", ""))));

            } else {
                txtAbonoL.setText("0");

            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }*/
    }//GEN-LAST:event_txtAbonoKeyReleased

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        // TODO add your handling code here:
        String cod = GestionarFactura.getCodigo();
        txtCod.setText(cod);
        try {
            prepararBD();
            rs = stMovil.executeQuery("SELECT * FROM v_puntoemision3 WHERE idemision=" + txtidEmision.getText().trim());
            rs.last();
            int fa = rs.getInt("facturaactual");
            rs.close();
            switch (String.valueOf(fa).length()) {
                case 1 ->
                    txtFacturaN.setText("000000" + (fa + 1));
                case 2 ->
                    txtFacturaN.setText("00000" + (fa + 1));
                case 3 ->
                    txtFacturaN.setText("0000" + (fa + 1));
                case 4 ->
                    txtFacturaN.setText("000" + (fa + 1));
                case 5 ->
                    txtFacturaN.setText("00" + (fa + 1));
                case 6 ->
                    txtFacturaN.setText("0" + (fa + 1));
                case 7 ->
                    txtFacturaN.setText(String.valueOf(fa + 1));
                default -> {
                }
            }
        } catch (SQLException pr) {
        }

        String NFactura = txtEstablecimiento.getText().trim() + "-" + txtEmision.getText().trim() + "-" + txtFacturaN.getText().trim();
        String cond = lbCond.getText();
        String est;
        if (cond.equals("CONTADO")) {
            est = "ABONADO";
        } else {
            est = "PENDIENTE";
        }
        UsuarioL = Login.getUsuarioLogueado();
        try {
            int resp = JOptionPane.showConfirmDialog(dlgFinFactura, "¿Seguro que deseas registrar esta Venta al sistema?", "CONFIRMACIÓN DE VENTA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (resp == JOptionPane.YES_OPTION) {
                try {
                    prepararBD();
                    con.setAutoCommit(false);
                    String sql = "insert into factura values(" + txtCod.getText().trim() + "," + txtCodVendedor.getText().trim() + "," + txtCodCliente.getText().trim() + "," + txtCaja.getText().trim() + "," + txtidEmision.getText().trim() + ",'" + NFactura + "','" + lbCond.getText() + "','"
                            + txtFecha.getText() + "','" + txtHora.getText() + "'," + txtTotalL.getText() + "," + txtExentaL.getText() + "," + txt5L.getText() + "," + txt10L.getText() + ",'S','" + UsuarioL + "','" + est + "')";
                    String sql4 = "UPDATE puntoemision set facturaactual=" + Integer.parseInt(txtFacturaN.getText().trim()) + " WHERE idemision=" + txtidEmision.getText().trim();
                    String sql5 = "UPDATE ref set nventa=" + Integer.parseInt(txtFacturaN.getText().trim()) + " WHERE idemision=" + txtidEmision.getText().trim();
                    stTransaccion.executeUpdate(sql);
                    stTransaccionMovil.executeUpdate(sql4);
                    stTransaccionMovil.executeUpdate(sql5);
                    int fila = tbDetalle.getRowCount();
                    for (int j = 0; j < fila; j++) {
                        String filas[] = {tbDetalle.getValueAt(j, 0).toString(), tbDetalle.getValueAt(j, 9).toString(), tbDetalle.getValueAt(j, 3).toString(), tbDetalle.getValueAt(j, 4).toString(), tbDetalle.getValueAt(j, 8).toString()};
                        sql = "insert into detalle_factura values(" + txtCod.getText() + "," + filas[0] + "," + filas[1] + "," + filas[2] + "," + filas[3] + "," + filas[4] + ")";
                        String sql2 = "UPDATE productos SET stock=(stock-" + filas[2] + ") WHERE  idproducto=" + filas[0];
                        stTransaccion.executeUpdate(sql);
                        stTransaccionMovil.executeUpdate(sql2);
                    }
                    con.commit();
                    stTransaccion.close();
                    stTransaccionMovil.close();
                    Mensajes.informacion("VENTA REGISTRADA EXITOSAMENTE!");
                    dlgFinFactura.dispose();
                    if (cond.equals("CONTADO")) {
                        imprimirTicketOriginal();
                        imprimirTicketDuplicado();
                        CabecerasTablas.limpiarTablasVentas(tbDetalle);
                        CabecerasTablas.ventas(tbDetalle);
                        controlFactura.canCelar();
                        Cancelar();
                        txtAbono.setText("0");
                        txtVuelto.setText("0");
                        cant();
                    } else {
                        imprimirTicketOriginal();
                        imprimirTicketDuplicado();
                        CabecerasTablas.limpiarTablasVentas(tbDetalle);
                        CabecerasTablas.ventas(tbDetalle);
                        controlFactura.canCelar();
                        Cancelar();
                        txtAbono.setText("0");
                        txtVuelto.setText("0");
                        cant();
                        //jasper.BoletaCredito("\\Reports\\ventas\\venta_credito.jasper", "cod", Integer.parseInt(cod));
                    }
                    //CabecerasTablas.limpiarTablas(tbDetalle);
                    //cabe.compras(tbDetalle);
                    //controlFactura.canCelar();  
                } catch (SQLException e) {
                    try {
                        con.rollback();
                        Mensajes.error("TRANSACCIÓN FALLIDA: La venta no fue registrada en el sistema.\nError:ADD_V: " + e.getMessage().toUpperCase());
                        controlFactura.canCelar();
                        dlgFinFactura.dispose();
                    } catch (SQLException se) {
                        Mensajes.error(se.getMessage());
                    }
                }
                //Cancelar();
                //txtAbono.setText("0");
                //txtVuelto.setText("0");
                //cant();
            }
        } catch (Exception ee) {
            System.out.println(ee.getMessage());
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnVerBoletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerBoletaActionPerformed
        // TODO add your handling code here:
        //llamarReporteFactura();
    }//GEN-LAST:event_btnVerBoletaActionPerformed

    private void btnModificarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarVActionPerformed
        // TODO add your handling code here:
        dlgFinFactura.dispose();
        txtCodVendedor.setText("");
        lbEmpleado.setText("");
        txtAbono.setText("0");
        txtVuelto.setText("0");
    }//GEN-LAST:event_btnModificarVActionPerformed

    private void txtAbonoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAbonoMouseClicked
        // TODO add your handling code here:
        //txtAbono.selectAll();
    }//GEN-LAST:event_txtAbonoMouseClicked

    private void dlgFinFacturaWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dlgFinFacturaWindowOpened
        // TODO add your handling code here:

    }//GEN-LAST:event_dlgFinFacturaWindowOpened

    private void dlgFinFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dlgFinFacturaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dlgFinFacturaKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        btnConfirmar.doClick();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void txtCodVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVendedorActionPerformed
        // TODO add your handling code here:
        try {
            if (Integer.parseInt(txtCodVendedor.getText()) <= 0) {
                Mensajes.error("CÓDIGO EQUIVOCADO O NO POSEE PERFIL PARA VENTA");
                btnConfirmar.setEnabled(false);
                txtCodVendedor.requestFocus();
                txtCodVendedor.selectAll();
            } else {
                try {
                    Vendedor vn = GestionarVendedor.busVendedorFactura(txtCodVendedor.getText());
                    lbEmpleado.setText(vn.getNombreV());
                    btnConfirmar.setEnabled(true);
                    txtAbono.requestFocus();
                    txtAbono.selectAll();
                } catch (Exception e) {
                }

            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtCodVendedorActionPerformed

    private void txtCodVendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodVendedorKeyPressed
        // TODO add your handling code here:
        validarCampos.soloNumeros(txtCodVendedor);
    }//GEN-LAST:event_txtCodVendedorKeyPressed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        btnModificarV.doClick();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void txtAbonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbonoActionPerformed
        // TODO add your handling code here:
        if (Integer.parseInt(txtAbono.getText().replace(".", "").replace(",", "")) == 0) {
            btnConfirmar.doClick();
        } else {
            try {
                int calculos = controlFactura.calCulos();
                //txtVuelto.setText(String.valueOf(calculos));
                DecimalFormat df = new DecimalFormat("#,###");
                txtVuelto.setText(df.format(Integer.parseInt(String.valueOf(calculos).trim().replace(".", "").replace(",", ""))));
                txtVuelto.requestFocus();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_txtAbonoActionPerformed

    private void txtVueltoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVueltoActionPerformed
        // TODO add your handling code here:
        btnConfirmar.doClick();
    }//GEN-LAST:event_txtVueltoActionPerformed

    private void itemBuscarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBuscarCActionPerformed
        // TODO add your handling code here:
        btnBuscarClientes.doClick();
    }//GEN-LAST:event_itemBuscarCActionPerformed

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalirActionPerformed
        // TODO add your handling code here:
        btnSalirActionPerformed(null);
    }//GEN-LAST:event_itemSalirActionPerformed

    private void txtEstablecimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstablecimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstablecimientoActionPerformed

    private void jScrollPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MousePressed

    private void tbDetalleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetalleMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDetalleMousePressed

    private void tbDetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetalleMouseClicked
        // TODO add your handling code here:
        if (evt.getButton() == 1) {
            if (evt.getClickCount() == 2) {
                menuEmergente.show(tbDetalle, evt.getX(), evt.getY());
            }

        }
    }//GEN-LAST:event_tbDetalleMouseClicked

    private void itemCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCantidadActionPerformed
        // TODO add your handling code here:
        btnCantActionPerformed(null);
    }//GEN-LAST:event_itemCantidadActionPerformed

    private void txtidEmisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidEmisionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidEmisionActionPerformed

    private void txtEmisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmisionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmisionActionPerformed

    private void txtFacturaNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFacturaNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFacturaNActionPerformed

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void btnCantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCantActionPerformed
        // TODO add your handling code here:
        if (tbDetalle.getSelectedRowCount() != 0) {
            try {
                controlFactura.actCantidad(tbDetalle);
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_btnCantActionPerformed

    private void txtCodArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodArticuloActionPerformed

    private void txtCodClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodClienteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dlgVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        java.awt.EventQueue.invokeLater(() -> {
            dlgVentas dialog = new dlgVentas(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.panel.PanelImage Blanco;
    private org.edisoncor.gui.panel.PanelImage Oscuro;
    private javax.swing.JButton btnAdd;
    public static javax.swing.JButton btnBuscarArticulo;
    public static javax.swing.JButton btnBuscarClientes;
    public static javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCant;
    public static javax.swing.JButton btnConfirmar;
    public static javax.swing.JButton btnGuardar;
    public static javax.swing.JButton btnModificar;
    private javax.swing.JButton btnModificarV;
    public static javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRestar;
    public static javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVerBoleta;
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JDialog dlgFinFactura;
    public static javax.swing.JLabel etiCant;
    private javax.swing.JMenuItem itemBuscarA;
    private javax.swing.JMenuItem itemBuscarC;
    private javax.swing.JMenuItem itemCancelar;
    private javax.swing.JMenuItem itemCantidad;
    private javax.swing.JMenuItem itemGuardar;
    private javax.swing.JMenuItem itemModificar;
    public static javax.swing.JMenuItem itemNuevo;
    private javax.swing.JMenuItem itemQuitar;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JLabel lbCond;
    public static javax.swing.JLabel lbCred;
    public static javax.swing.JLabel lbEmpleado;
    private javax.swing.JLabel lbTimbrado;
    private javax.swing.JLabel lbValidaz;
    public static javax.swing.JLabel lblTotal;
    private javax.swing.JPopupMenu menuEmergente;
    public static javax.swing.JRadioButton rContado;
    public static javax.swing.JRadioButton rCredito;
    public static javax.swing.JTable tbDetalle;
    public static javax.swing.JTextField txt10;
    public static javax.swing.JTextField txt10L;
    public static javax.swing.JTextField txt10Libre;
    public static javax.swing.JTextField txt5;
    public static javax.swing.JTextField txt5L;
    public static javax.swing.JTextField txt5libre;
    public static javax.swing.JTextField txtAbono;
    public static javax.swing.JTextField txtArt;
    public static javax.swing.JTextField txtCaja;
    public static javax.swing.JTextField txtCant;
    public static javax.swing.JTextField txtCod;
    public static javax.swing.JTextField txtCodArticulo;
    public static javax.swing.JTextField txtCodCliente;
    public static javax.swing.JTextField txtCodVendedor;
    public static javax.swing.JTextField txtDescuentoL;
    public static javax.swing.JTextField txtEmision;
    public static javax.swing.JTextField txtEstablecimiento;
    public static javax.swing.JTextField txtExenta;
    public static javax.swing.JTextField txtExentaL;
    public static javax.swing.JTextField txtFacturaN;
    public static javax.swing.JTextField txtFecha;
    public static javax.swing.JTextField txtHora;
    public static javax.swing.JTextField txtNetoL;
    public static javax.swing.JTextField txtRazonS;
    public static javax.swing.JTextField txtRuc;
    public static javax.swing.JTextField txtTotal;
    public static javax.swing.JTextField txtTotalL;
    public static javax.swing.JTextField txtVuelto;
    public static javax.swing.JTextField txtdisponibleL;
    public static javax.swing.JTextField txtfechaF;
    private javax.swing.JTextField txtidEmision;
    // End of variables declaration//GEN-END:variables

}
