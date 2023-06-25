package IU;

import Componentes.ConexionBD;
import Componentes.Login;
import Componentes.Reporte;
import Componentes.Mensajes;
import Componentes.Operacion;
import Componentes.RenderDecimal1;
import Componentes.RenderDecimal2;
import Componentes.Software;
import Controladores.CabecerasTablas;
import Controladores.controlFactura;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import org.mariadb.jdbc.MariaDbConnection;
import org.mariadb.jdbc.MariaDbStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

public class dlgConsultarFacturas extends javax.swing.JDialog {

    CabecerasTablas cabe = new CabecerasTablas();
    public static MariaDbConnection con;
    public static MariaDbStatement st;
    public static ResultSet rss;

    public static String UsuarioL = "";
    public Reporte jasper;
    static String emp;
    static String dir;
    static String cel;

    public dlgConsultarFacturas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        titulo();
        prepararBD();
        jasper= new Reporte();
        cabe.consFacturas(tblFactura);
        cabe.detalleFactura(tblDetalle);
        controlFactura.listFacturas(tblFactura);
        Renders();

    }
    final void titulo(){
        if(Software.getSoftware().equals("null")){
            this.setTitle("Gestión de ventas realizadas");
        }else{
            this.setTitle(Software.getSoftware()+" - Gestión de ventas realizadas");
        }
    }

    public static void Renders() {
        dlgConsultarFacturas.tblFactura.getColumnModel().getColumn(9).setCellRenderer(new RenderDecimal2());
    }

    public static void RendersD() {
        dlgConsultarFacturas.tblDetalle.getColumnModel().getColumn(4).setCellRenderer(new RenderDecimal1());
        dlgConsultarFacturas.tblDetalle.getColumnModel().getColumn(5).setCellRenderer(new RenderDecimal2());
    }
    
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
    
    public static void limpiarCampos() {
        txtCodCliente.setText("");
        txtRuc.setText("");
        txtRazonSocial.setText("");
        txtVendedor.setText("");
    }

    public static void imprimirTicket(String fecha, String hora, String fac, String caja, String condicion, String total) {

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

            e.setNumber(0);
            //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
            int filas = tblDetalle.getRowCount();
            int tamaño = filas+15;
            printer.setOutSize(tamaño, 80);
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
            printer.printTextWrap(2, 1, 1, 40, "______________RE-IMPRESION______________");
            printer.printTextWrap(2, 1, 41, 67, "FECHA: " + fecha + " " + hora); //FECHA Y HORA
            printer.printTextWrap(2, 1, 71, 78, condicion); //CONDICION DE VENTA
            printer.printTextWrap(3, 1, 1, 24, "TICKET N: " +fac); //FACTURA O PEDIDO
            printer.printTextWrap(3, 1, 26, 37, "CAJA N: " + caja); //CAJA
            printer.printTextWrap(3, 1, 41, 80, "VENDEDOR: " + txtVendedor.getText()); //VENDEDOR
            printer.printTextWrap(4, 1, 1, 40, "CLIENTE: " + txtRazonSocial.getText());//RUC Y RS
            printer.printTextWrap(4, 1, 41, 80, "R.U.C.: " + txtRuc.getText());//RUC Y RS
            printer.printTextWrap(5, 1, 1, 40, "________________________________________");
            printer.printTextWrap(5, 1, 41, 79, "CANT   P.PUBL  %DESC  P.UNIT    TOTAL");
            for (int i = 0; i < filas; i++) {
                int p = 6 + i; //Fila
                DecimalFormat formateador = new DecimalFormat("#,###");
                String DES = printer.alinharADireita(10, tblDetalle.getValueAt(i, 3).toString());
                String Cant = tblDetalle.getValueAt(i, 0).toString();
                //int pp = Integer.parseInt(tblDetalle.getValueAt(i, 17).toString())/Integer.parseInt(tblDetalle.getValueAt(i, 3).toString());
                String Ppublic = /*String.valueOf(pp);*/ "*";
                String Desc = /*tblDetalle.getValueAt(i, 16).toString();*/"*";
                String Punit = tblDetalle.getValueAt(i, 4).toString();
                String Mont = tblDetalle.getValueAt(i, 5).toString();
                printer.printTextWrap(p, 1, 1, 40, DES);
                printer.printTextWrap(p, 1, 41, 45, formateador.format(Integer.parseInt(Cant.replace(".", "").replace(",", ""))));
                printer.printTextWrap(p, 1, 48, 54, Ppublic);
                //printer.printTextWrap(p, 1, 48, 54, formateador.format(Integer.parseInt(Ppublic.replace(".", "").replace(",", ""))));
                printer.printTextWrap(p, 1, 56, 61, Desc);
                //printer.printTextWrap(p, 1, 56, 61, formateador.format(Integer.parseInt(Desc.replace(".", "").replace(",", ""))));
                printer.printTextWrap(p, 1, 63, 69, formateador.format(Integer.parseInt(Punit.replace(".", "").replace(",", ""))));
                printer.printTextWrap(p, 1, 72, 79, formateador.format(Integer.parseInt(Mont.replace(".", "").replace(",", ""))));
            }
            printer.printTextWrap(filas+6, 1, 1, 40, "---------------------------------------");

            DecimalFormat formateador = new DecimalFormat("#,###");
            String tot = printer.alinharADireita(10, formateador.format(Integer.parseInt(total.replace(".", "").replace(",", ""))));
            printer.printTextWrap(filas + 6, 1, 41, 55, "TOTAL A PAGAR:");
            printer.printTextWrap(filas + 6, 1, 66, 79, (tot));
            printer.printTextWrap(filas + 7, 1, 1, 40, "---------------------------------------");
            String efe = printer.alinharADireita(10, "0");
            printer.printTextWrap(filas + 7, 1, 41, 52, "EFECTIVO : ");
            printer.printTextWrap(filas + 7, 1, 55, 80, efe);

            String cam = printer.alinharADireita(10, "0");
            printer.printTextWrap(filas + 8, 1, 1, 12, "VUELTO   : ");
            printer.printTextWrap(filas + 8, 1, 15, 40, cam);

            printer.printTextWrap(filas + 8, 1, 41, 80, "=======================================");
            printer.printTextWrap(filas + 9, 1, 6, 34, "GRACIAS POR SU PREFERENCIA!!");
            printer.printTextWrap(filas + 9, 1, 52, 66, "SISTEMA E-FARM");
            printer.printTextWrap(filas + 10, 1, 1, 40, "=======================================");

            ///CREAR ARCHIVO EN CARPETA DEL PROYECTO PARA PEDIDOS
            //printer.toFile("C:\\tmp\\impresion.txt");
            printer.toFile("re-impresion.txt");
            FileInputStream inputStream = null;

            try {
                inputStream = new FileInputStream("re-impresion.txt");
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
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Blanco = new org.edisoncor.gui.panel.PanelImage();
        Oscuro = new org.edisoncor.gui.panel.PanelImage();
        jPanel1 = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFactura = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowInddex, int celIndex)
            {
                return false;
            }
        };
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtVendedor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCodCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtRuc = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowInddex, int celIndex)
            {
                return false;
            }
        };
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        itemBuscarC = new javax.swing.JMenuItem();
        itemBuscarA = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        itemQuitar = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        itemSalir = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        Blanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/fondoBlanco.jpg"))); // NOI18N
        Blanco.setPreferredSize(new java.awt.Dimension(690, 418));

        Oscuro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/fondo.png"))); // NOI18N
        Oscuro.setPreferredSize(new java.awt.Dimension(690, 418));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        btnImprimir.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/printer30.png"))); // NOI18N
        btnImprimir.setText("Re-Imprimir - F6");
        btnImprimir.setEnabled(false);
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        jPanel1.add(btnImprimir);

        btnActualizar.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/receipt_106581 - copia - copia.png"))); // NOI18N
        btnActualizar.setText("Actualizar Listado - F5");
        btnActualizar.setToolTipText("Actualizar Comprobantes");
        btnActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnActualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar);

        btnAnular.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        btnAnular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/receipt_106581 - copia (2) - copia.png"))); // NOI18N
        btnAnular.setText("Anular Venta - Supr");
        btnAnular.setToolTipText("Anular Comprobante");
        btnAnular.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAnular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });
        jPanel1.add(btnAnular);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/back30.png"))); // NOI18N
        btnSalir.setText("Salir - Alt+F4");
        btnSalir.setToolTipText("Salir");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setPreferredSize(new java.awt.Dimension(53, 47));
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir);

        javax.swing.GroupLayout OscuroLayout = new javax.swing.GroupLayout(Oscuro);
        Oscuro.setLayout(OscuroLayout);
        OscuroLayout.setHorizontalGroup(
            OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OscuroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        OscuroLayout.setVerticalGroup(
            OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OscuroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setOpaque(false);

        tblFactura.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        tblFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblFactura.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblFactura.setFillsViewportHeight(true);
        tblFactura.getTableHeader().setResizingAllowed(false);
        tblFactura.getTableHeader().setReorderingAllowed(false);
        tblFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFacturaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblFacturaMousePressed(evt);
            }
        });
        tblFactura.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblFacturaPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(tblFactura);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setOpaque(false);

        jLabel2.setText("Vendedor:");

        txtVendedor.setEditable(false);
        txtVendedor.setBackground(new java.awt.Color(255, 255, 255));
        txtVendedor.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtVendedor.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("ID:");

        txtCodCliente.setEditable(false);
        txtCodCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtCodCliente.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtCodCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("Razón Social:");

        txtRazonSocial.setEditable(false);
        txtRazonSocial.setBackground(new java.awt.Color(255, 255, 255));
        txtRazonSocial.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtRazonSocial.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("R.U.C.: ");

        txtRuc.setEditable(false);
        txtRuc.setBackground(new java.awt.Color(255, 255, 255));
        txtRuc.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtRuc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRuc.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblDetalle.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDetalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDetalle.setOpaque(false);
        jScrollPane2.setViewportView(tblDetalle);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout BlancoLayout = new javax.swing.GroupLayout(Blanco);
        Blanco.setLayout(BlancoLayout);
        BlancoLayout.setHorizontalGroup(
            BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Oscuro, javax.swing.GroupLayout.DEFAULT_SIZE, 859, Short.MAX_VALUE)
            .addGroup(BlancoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        BlancoLayout.setVerticalGroup(
            BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BlancoLayout.createSequentialGroup()
                .addComponent(Oscuro, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jMenu2.setText("Opciones");
        jMenu2.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jMenu2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        itemBuscarC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        itemBuscarC.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemBuscarC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/printer15.png"))); // NOI18N
        itemBuscarC.setText("Re-Imprimir Venta               ");
        itemBuscarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBuscarCActionPerformed(evt);
            }
        });
        jMenu2.add(itemBuscarC);

        itemBuscarA.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        itemBuscarA.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemBuscarA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/receipt_106581 - copia - copia - copia.png"))); // NOI18N
        itemBuscarA.setText("Actualizar Listado");
        itemBuscarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBuscarAActionPerformed(evt);
            }
        });
        jMenu2.add(itemBuscarA);
        jMenu2.add(jSeparator4);

        itemQuitar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        itemQuitar.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/receipt_106581 - copia (2) - copia - copia.png"))); // NOI18N
        itemQuitar.setText("Anular Venta");
        itemQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemQuitarActionPerformed(evt);
            }
        });
        jMenu2.add(itemQuitar);
        jMenu2.add(jSeparator5);

        itemSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        itemSalir.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/back15.png"))); // NOI18N
        itemSalir.setText("Salir");
        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalirActionPerformed(evt);
            }
        });
        jMenu2.add(itemSalir);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Búsqueda");
        jMenu1.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jMenu1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem1.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/search15.png"))); // NOI18N
        jMenuItem1.setText("por N° de Ticket o Boleta Crédito               ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Blanco, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Blanco, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void llamarReporteFactura() {
        Reporte gr;
        gr = new Reporte();
        //int codF = Integer.parseInt(txtCodFactura.getText());
        //gr.MostrarReporteConParametro(System.getProperty("user.dir")+"/Reportes/Facturas/Factura.jasper", "Factura de Venta", codF,"Facturas/Fact-"+codF+".pdf");
        //gr.cerrar();
    }
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        try {
            String cod = (JOptionPane.showInputDialog("Ingrese N° de factura"));
            for (int i = 0; i < tblFactura.getRowCount(); i++) {
                if (tblFactura.getValueAt(i, 6).equals(cod)) {
                    tblFactura.changeSelection(i, 1, false, false);
                    tblFacturaMousePressed(null);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Consulta cancelada" + e.getMessage());
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        // TODO add your handling code here:
        if (dlgConsultarFacturas.tblFactura.getSelectedRow() < 0) {
            Mensajes.error("Seleccione una fila de la tabla");
        } else {
            int x = dlgConsultarFacturas.tblFactura.getSelectedRow();
            String estado = dlgConsultarFacturas.tblFactura.getValueAt(x, 11).toString();
            if (estado.equals("ANULADO")) {
                Mensajes.informacion("Esta venta ya fue anulada");
            } else {
                if (tblFactura.getSelectedRow() < 0) {
                    Mensajes.error("Seleccione la venta que desea Anular");
                } else {
                    String msg;
                    int rpta = Mensajes.confirmar("¿Seguro que desea Anular esta Venta?");
                    if (rpta == 0) {
                        try {
                            msg = controlFactura.anularFactura();
                            if (msg == null) {
                                btnActualizar.doClick();
                            }

                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnAnularActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        if (dlgConsultarFacturas.tblFactura.getSelectedRow() < 0) {
            Mensajes.error("Seleccione una fila de la tabla");
        } else {
            int x = dlgConsultarFacturas.tblFactura.getSelectedRow();
            String estado = dlgConsultarFacturas.tblFactura.getValueAt(x, 11).toString();
            if (estado.equals("ANULADO")) {
                Mensajes.informacion("VENTA ANULADA: Imposible realizar la re-impresión");
            } else {
                int rpta = Mensajes.confirmar("¿Seguro que desea re-imprimir la venta?");
                if (rpta == 0) {
                    try {
                        String cod = dlgConsultarFacturas.tblFactura.getValueAt(x, 0).toString();
                        String fecha = dlgConsultarFacturas.tblFactura.getValueAt(x, 2).toString();
                        String hora = dlgConsultarFacturas.tblFactura.getValueAt(x, 3).toString();
                        String fact = dlgConsultarFacturas.tblFactura.getValueAt(x, 6).toString();
                        String caja = dlgConsultarFacturas.tblFactura.getValueAt(x, 5).toString();
                        String condicion = dlgConsultarFacturas.tblFactura.getValueAt(x, 7).toString();
                        String total = dlgConsultarFacturas.tblFactura.getValueAt(x, 9).toString();
                        if (condicion.equals("CONTADO")) {
                            imprimirTicket(fecha, hora, fact, caja, condicion, total);
                            try {
                                UsuarioL = Login.getUsuarioLogueado();
                                StringBuilder sql = new StringBuilder("INSERT INTO reimpresiones (re_fac_codigo, re_descripcion, re_tipo, fecha, usu) VALUES (");
                                sql.append(cod).append(", ");
                                sql.append("'RE-IMPRESION DE TICKET','");
                                sql.append(condicion).append("',");
                                sql.append("now(),'");
                                sql.append(UsuarioL).append("')");
                                String msg = Operacion.exeOperacion(sql.toString());
                                if (msg == null) {
                                    System.out.println("la re-impresion ha sido registrada");
                                } else {
                                    System.out.println("Error en registrar la re-impresion: " + msg);
                                }
                            } catch (Exception e) {
                                Mensajes.error("Hubo un error en el registro de la re-impresión");
                            }

                        } else {
                            jasper.BoletaCreditoRE("\\Reports\\ventas\\venta_credito_reimpresion.jasper", "cod", Integer.parseInt(cod));
                            try {
                                UsuarioL = Login.getUsuarioLogueado();
                                StringBuilder sql = new StringBuilder("INSERT INTO reimpresiones (re_fac_codigo, re_descripcion, re_tipo, fecha, usu) VALUES (");
                                sql.append(cod).append(", ");
                                sql.append("'RE-IMPRESION DE BOLETA CRÉDITO','");
                                sql.append(condicion).append("',");
                                sql.append("now(),'");
                                sql.append(UsuarioL).append("')");
                                String msg = Operacion.exeOperacion(sql.toString());
                                if (msg == null) {
                                    System.out.println("la re-impresion ha sido registrada");
                                } else {
                                    System.out.println("Error en registrar la re-impresion: " + msg);
                                }
                            } catch (Exception e) {
                                Mensajes.error("Hubo un error en el registro de la re-impresión");
                            }
                        }
                    } catch (Exception e) {
                        Mensajes.informacion(e.getMessage());
                    }
                }
            }
        }        
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void tblFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFacturaMouseClicked
        // TODO add your handling code here:
        /*try {
            CabecerasTablas.limpiarTablas(tblDetalle);
            controlFactura.listDetalle(tblDetalle);
            controlFactura.ListClientes();
            controlFactura.selecVendedor();
            RendersD();
        } catch (Exception e) {
            Mensajes.error(e.getMessage());
        }*/
    }//GEN-LAST:event_tblFacturaMouseClicked

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        int rpta = Mensajes.confirmar("¿Seguro que desea salir del formulario?");
        if (rpta == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void tblFacturaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblFacturaPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblFacturaPropertyChange

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        try {
            CabecerasTablas.limpiarTablas(tblFactura);
            CabecerasTablas.limpiarTablas(tblDetalle);
            cabe.consFacturas(tblFactura);
        cabe.detalleFactura(tblDetalle);
        controlFactura.listFacturas(tblFactura);
        Renders();
            limpiarCampos();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void tblFacturaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFacturaMousePressed
        // TODO add your handling code here:
        try {
            CabecerasTablas.limpiarTablas(tblDetalle);
            controlFactura.listDetalle(tblDetalle);
            controlFactura.ListClientes();
            controlFactura.selecVendedor();
            RendersD();
        } catch (Exception e) {
            //Mensajes.error(e.getMessage());
        }
    }//GEN-LAST:event_tblFacturaMousePressed

    private void itemBuscarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBuscarCActionPerformed
        // TODO add your handling code here:
        btnImprimirActionPerformed(null);
    }//GEN-LAST:event_itemBuscarCActionPerformed

    private void itemBuscarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBuscarAActionPerformed
        // TODO add your handling code here:
        btnActualizarActionPerformed(null);
    }//GEN-LAST:event_itemBuscarAActionPerformed

    private void itemQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemQuitarActionPerformed
        // TODO add your handling code here:
        btnAnularActionPerformed(null);
    }//GEN-LAST:event_itemQuitarActionPerformed

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalirActionPerformed
        // TODO add your handling code here:
        btnSalirActionPerformed(null);
    }//GEN-LAST:event_itemSalirActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dlgConsultarFacturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dlgConsultarFacturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dlgConsultarFacturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dlgConsultarFacturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                dlgConsultarFacturas dialog = new dlgConsultarFacturas(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.panel.PanelImage Blanco;
    private org.edisoncor.gui.panel.PanelImage Oscuro;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnSalir;
    private javax.swing.JMenuItem itemBuscarA;
    private javax.swing.JMenuItem itemBuscarC;
    private javax.swing.JMenuItem itemQuitar;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    public static javax.swing.JTable tblDetalle;
    public static javax.swing.JTable tblFactura;
    public static javax.swing.JTextField txtCodCliente;
    public static javax.swing.JTextField txtRazonSocial;
    public static javax.swing.JTextField txtRuc;
    public static javax.swing.JTextField txtVendedor;
    // End of variables declaration//GEN-END:variables
}
