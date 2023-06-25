package IU;

import Componentes.ConexionBD;
import Componentes.Fecha;
import Componentes.Mensajes;
import Componentes.Numero_a_Letra;
import Componentes.PrinterService;
import Componentes.Redondeo;
import Componentes.RenderDecimal2;
import Componentes.RenderDecimal1;
import Componentes.cargarComboBoxMovil;
import Componentes.Software;
import Componentes.clsExportarExcel;
import Controladores.CabecerasTablas;
import Controladores.controlFactura;
import static Controladores.controlFactura.getTotalVentasMoviles;
import static Controladores.controlFactura.getTotalVentasMovilesA;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import org.mariadb.jdbc.MariaDbConnection;
import org.mariadb.jdbc.MariaDbStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dlgConsultarFacturasMovil extends javax.swing.JDialog {

    public static ResultSet rs;
    public static MariaDbStatement sentencia;
    public static MariaDbConnection con;

    static public Numero_a_Letra d;

    clsExportarExcel Export;

    public dlgConsultarFacturasMovil(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dtfR = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date dateObj = calendar.getTime();
        String formattedDate = dtf.format(dateObj);
        String formattedDateR = dtfR.format(dateObj);
        txtFecha.setText(formattedDate);
        txtFechaFiltro.setText(formattedDateR);
        //dcFecha.setDate(dateObj);
        titulo();
        CabecerasTablas.consVentasMoviles(tbVentasMoviles);
        CabecerasTablas.consDetalleVentasMoviles(tbDetalleVentasMoviles);
        controlFactura.listFacturasMoviles(tbVentasMoviles, txtFechaFiltro.getText().trim());
        Renders();
        txtIdPE.setVisible(false);
        txtIdT.setVisible(false);
        txtFechaFiltro.setVisible(false);
        CargarTotalVentas();
        CargarTotalVentasA();

        d = new Numero_a_Letra();

    }

    public static void prepararBD() {
        {
            try {
                con = (MariaDbConnection) new ConexionBD().getConexionMovil();
                if (con == null) {
                    System.out.println("No hay Conexion con la Base de Datos");
                } else {
                    sentencia = (MariaDbStatement) con.createStatement();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    final void titulo() {
        if (Software.getSoftware().equals("null")) {
            this.setTitle("Gestión de Ventas Móviles realizadas");
        } else {
            this.setTitle(Software.getSoftware() + " - Gestión de Ventas Móviles realizadas");
        }
    }

    public static void Renders() {
        dlgConsultarFacturasMovil.tbVentasMoviles.getColumnModel().getColumn(11).setCellRenderer(new RenderDecimal2());
    }

    public static void RendersD() {
        dlgConsultarFacturasMovil.tbDetalleVentasMoviles.getColumnModel().getColumn(4).setCellRenderer(new RenderDecimal1());
        dlgConsultarFacturasMovil.tbDetalleVentasMoviles.getColumnModel().getColumn(5).setCellRenderer(new RenderDecimal2());
    }

    public void filtrar() {
        if (ckHabilitar.isSelected()) {
            cboTimbrado.setEnabled(true);
            btnFiltrar.setEnabled(true);
            cargarComboBoxMovil.cargar(cboTimbrado, "SELECT idtimbrado,descripcion FROM timbrado WHERE estado='Activo'");
        } else {
            cboTimbrado.setSelectedIndex(0);
            cboTimbrado.setEnabled(false);
            cbPE.setEnabled(false);
            btnFiltrar.setEnabled(false);
            btnActualizarActionPerformed(null);

        }
    }

    public static void CargarTotalVentas() {
        DecimalFormat df = new DecimalFormat("#,###");
        String CT = String.valueOf(getTotalVentasMoviles());
        lbTotalVentas.setText(df.format(Integer.parseInt(CT.replace(".", "").replace(",", ""))));
    }

    public static void CargarTotalVentasA() {
        DecimalFormat df = new DecimalFormat("#,###");
        String CT = String.valueOf(getTotalVentasMovilesA());
        lbTotalVentas1.setText(df.format(Integer.parseInt(CT.replace(".", "").replace(",", ""))));
    }

    public static void imprimirTicketOriginal(String Timbrado, String Desde, String Hasta, String Condicion, String Factura,
            String Fechas, String Hora, String Cliente, String Ruc, String Vendedor, String Total, String Exenta, String Cinco, String Diez, String estado) {
        //Impresora matricial tmu-220
        String empresa = null;
        String ruc = null;
        String celular = null;
        String direccion = null;

        PrinterService printerService = new PrinterService();

        System.out.println(printerService.getPrinters());
        DecimalFormat formateador = new DecimalFormat("#,###");
        prepararBD();
        try {
            rs = sentencia.executeQuery("SELECT * FROM empresa WHERE estado='S'");
            rs.last();
            empresa = rs.getString("razon_social");
            ruc = rs.getString("ruc");
            celular = rs.getString("telefono");
            direccion = rs.getString("direccion");
            rs.close();
        } catch (SQLException ex) {
            Mensajes.error("Error obteniendo datos de la empresa para la impresion de factura.");
        }
        try {
            String ea;
            if(estado.equals("ANULADO")){
                ea="--- FACTURA ANULADA ---";
            }else{
                ea="";
            }
            String Ticket = "        " + ea + "\n";
            Ticket += "       " + empresa + "\n";
            Ticket += "     VENTAS DE LACTEOS LACTOLANDA\n";
            Ticket += "           RUC: " + ruc + "\n";
            Ticket += "     CEL: " + celular + "\n";
            Ticket += "       " + direccion + "\n";
            Ticket += "         VILLARRICA - PARAGUAY\n";
            Ticket += "----------------------------------------\n";
            Ticket += "          TIMBRADO: " + Timbrado + "\n";
            Ticket += "VALIDO DESDE:" + Desde + " HASTA:" + Hasta + "\n";
            Ticket += "            I.V.A. INCLUIDO\n";
            Ticket += "----------------------------------------\n";
            Ticket += "FACTURA " + Condicion + " NRO: " + Factura + "\n";
            Ticket += "FECHA/HORA: " + (Fechas) + " " + Hora + "\n";
            Ticket += "VENDEDOR: " + Vendedor + "\n";
            Ticket += "\n";
            Ticket += "CLIENTE: " + Cliente + "\n";
            Ticket += "RUC/CI: " + Ruc + "\n";
            Ticket += "----------------------------------------\n";
            Ticket += String.format("%1$1s %2$8s %3$1s %4$9s %5$11s", "IVA", "CANT", "", "PRECIO", "   SUBTOTAL");
            Ticket += "\n";
            Ticket += "----------------------------------------\n";
            int filas = tbDetalleVentasMoviles.getRowCount();
            for (int i = 0; i < filas; i++) {
                String codB = tbDetalleVentasMoviles.getValueAt(i, 1).toString().trim();
                String Descripcion = tbDetalleVentasMoviles.getValueAt(i, 2).toString().trim();
                String cant = tbDetalleVentasMoviles.getValueAt(i, 3).toString().trim();
                String Punit = tbDetalleVentasMoviles.getValueAt(i, 4).toString().trim().replace(".", "").replace(",", "");
                String Mont = tbDetalleVentasMoviles.getValueAt(i, 5).toString().trim().replace(".", "").replace(",", "");
                String iva = tbDetalleVentasMoviles.getValueAt(i, 6).toString().trim() + "%";
                String um = tbDetalleVentasMoviles.getValueAt(i, 7).toString().trim();

                Ticket += String.format("%1$1s", codB /*+ "-" + Descripcion */ + "\n");
                Ticket += String.format("%1$-1s", /*codB + "-" + */ Descripcion + "\n");
                Ticket += String.format("%1$-5s %2$-10s %3$-10s %4$-12s", iva, cant + " " + um, formateador.format(Integer.parseInt(Punit.replace(".", "").replace(",", ""))), formateador.format(Integer.parseInt(Mont.replace(".", "").replace(",", ""))));
            }
            Ticket += "\n";
            Ticket += "========================================\n";
            //Ticket += String.format("%1$5s %2$20s", "TOTAL A PAGAR Gs:", formateador.format(Integer.parseInt(Total))) + "\n";
            Ticket += "TOTAL A PAGAR Gs: " + formateador.format(Integer.parseInt(Total)) + "\n";
            //Ticket += "           TOTAL Gs.:"+tot+"\n";
            Ticket += "========================================\n";
            String letras = d.Convertir(Total, true);
            Ticket += String.format("%1$1s", letras + "\n");
            //Ticket += "\n";
            Ticket += "========================================\n";
            Ticket += "\n";
            Ticket += "----------- TOTALES GRAVADA ------------\n";
            Ticket += "EXENTAS     ------>              " + formateador.format(Integer.parseInt(Exenta.replace(".", "").replace(",", ""))) + "\n";
            Ticket += "GRAVADA 5%  ------>              " + formateador.format(Integer.parseInt(Cinco.replace(".", "").replace(",", ""))) + "\n";
            Ticket += "GRAVADA 10% ------>              " + formateador.format(Integer.parseInt(Diez.replace(".", "").replace(",", ""))) + "\n";
            Ticket += "-------- LIQUIDACION DEL I.V.A. --------\n";
            int iv5 = Redondeo.redondearI(Integer.parseInt(Cinco.replace(".", "").replace(",", "")) / 21);
            Ticket += "I.V.A. 5%   ------>              " + formateador.format(iv5) + "\n";
            int iv10 = Redondeo.redondearI(Integer.parseInt(Diez.replace(".", "").replace(",", "")) / 11);
            Ticket += "I.V.A. 10%  ------>              " + formateador.format(Redondeo.redondearI(iv10)) + "\n";
            Ticket += "----------------------------------------\n";
            String totaliva = String.valueOf(iv5 + iv10);
            //Ticket += String.format("%1$5s %2$23s", "TOTAL I.V.A.", formateador.format(Integer.parseInt(totaliva.replace(".", "").replace(",", "")))) + "\n";
            Ticket += "TOTAL I.V.A.: " + formateador.format(Integer.parseInt(totaliva.replace(".", "").replace(",", ""))) + "\n";
            Ticket += "----------------------------------------\n";
            Ticket += "\n";
            Ticket += "EFECTIVO:  0\n";
            Ticket += "VUELTO:    0\n";
            Ticket += "\n";
            Ticket += "ORIGINAL:  CLIENTE\n";
            Ticket += "DUPLICADO: ARCHIVO TRIBUTARIO\n";
            Ticket += "\n";
            Ticket += "        " + empresa + "\n";
            Ticket += "         AGRADECE SU PREFERENCIA\n";
            Ticket += "\n";
            //Ticket += "\n";
            //Ticket += "\n";
            //Ticket += "\n";
            //Ticket += "\n";
            //Ticket += "\n";

            printerService.printString(Ticket);
            //print some stuff

           // printerService.imprimirTXT(Ticket, Factura);

            // cut that paper!
            byte[] cutP = new byte[]{0x1d, 'V', 1};

            printerService.printBytes(cutP);
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Blanco = new org.edisoncor.gui.panel.PanelImage();
        Oscuro = new org.edisoncor.gui.panel.PanelImage();
        jPanel2 = new javax.swing.JPanel();
        btnActualizar = new newscomponents.RSButtonBigIcon_new();
        btnReimprimir = new newscomponents.RSButtonBigIcon_new();
        btnAnular = new newscomponents.RSButtonBigIcon_new();
        btnSalir1 = new newscomponents.RSButtonBigIcon_new();
        txtFechaFiltro = new javax.swing.JTextField();
        btnExportar = new newscomponents.RSButtonBigIcon_new();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbVentasMoviles = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowInddex, int celIndex)
            {
                return false;
            }
        };
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cboTimbrado = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbPE = new javax.swing.JComboBox<>();
        txtIdPE = new javax.swing.JTextField();
        txtIdT = new javax.swing.JTextField();
        ckHabilitar = new rojerusan.RSCheckBox();
        btnFiltrar = new RSMaterialComponent.RSButtonMaterialRippleIcon();
        lbTotalVentas = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dcFecha1 = new datechooser.beans.DateChooserCombo();
        txtFecha = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lbTotalVentas1 = new javax.swing.JLabel();
        bntBuscarNF = new newscomponents.RSButtonBigIcon_new();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDetalleVentasMoviles = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowInddex, int celIndex)
            {
                return false;
            }
        };
        btnReimprimirLote = new newscomponents.RSButtonBigIcon_new();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        Blanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/fondoBlanco.jpg"))); // NOI18N
        Blanco.setPreferredSize(new java.awt.Dimension(690, 418));

        Oscuro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/fondo.png"))); // NOI18N
        Oscuro.setPreferredSize(new java.awt.Dimension(690, 418));

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        btnActualizar.setBackground(new java.awt.Color(17, 35, 46));
        btnActualizar.setBorder(null);
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.setToolTipText("ACTUALIZAR LISTA COMPLETA");
        btnActualizar.setBgHover(new java.awt.Color(39, 61, 75));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        btnActualizar.setIconTextGap(0);
        btnActualizar.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.REFRESH);
        btnActualizar.setPixels(0);
        btnActualizar.setSizeIcon(50.0F);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel2.add(btnActualizar);

        btnReimprimir.setBackground(new java.awt.Color(17, 35, 46));
        btnReimprimir.setBorder(null);
        btnReimprimir.setText("RE-IMPRIMIR");
        btnReimprimir.setToolTipText("RE-IMPRIMIR FACTURA MÓVIL");
        btnReimprimir.setBgHover(new java.awt.Color(39, 61, 75));
        btnReimprimir.setFocusPainted(false);
        btnReimprimir.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        btnReimprimir.setIconTextGap(0);
        btnReimprimir.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.PRINT);
        btnReimprimir.setPixels(0);
        btnReimprimir.setSizeIcon(50.0F);
        btnReimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReimprimirActionPerformed(evt);
            }
        });
        jPanel2.add(btnReimprimir);

        btnAnular.setBackground(new java.awt.Color(17, 35, 46));
        btnAnular.setBorder(null);
        btnAnular.setText("ANULAR");
        btnAnular.setToolTipText("ANULAR VENTA MÓVIL");
        btnAnular.setBgHover(new java.awt.Color(39, 61, 75));
        btnAnular.setFocusPainted(false);
        btnAnular.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        btnAnular.setIconTextGap(0);
        btnAnular.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.CANCEL);
        btnAnular.setPixels(0);
        btnAnular.setSizeIcon(50.0F);
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });
        jPanel2.add(btnAnular);

        btnSalir1.setBackground(new java.awt.Color(17, 35, 46));
        btnSalir1.setBorder(null);
        btnSalir1.setBgHover(new java.awt.Color(255, 0, 0));
        btnSalir1.setFocusPainted(false);
        btnSalir1.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        btnSalir1.setIconTextGap(0);
        btnSalir1.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.CLOSE);
        btnSalir1.setPixels(0);
        btnSalir1.setSizeIcon(20.0F);
        btnSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout OscuroLayout = new javax.swing.GroupLayout(Oscuro);
        Oscuro.setLayout(OscuroLayout);
        OscuroLayout.setHorizontalGroup(
            OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OscuroLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OscuroLayout.createSequentialGroup()
                        .addComponent(txtFechaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121))))
        );
        OscuroLayout.setVerticalGroup(
            OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(OscuroLayout.createSequentialGroup()
                .addComponent(btnSalir1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(txtFechaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnExportar.setBackground(new java.awt.Color(255, 255, 255));
        btnExportar.setBorder(null);
        btnExportar.setToolTipText("EXPORTAR INFORMACIÓN DE TABLA DE PRODUCTOS A EXCEL");
        btnExportar.setBgHover(new java.awt.Color(255, 255, 255));
        btnExportar.setFgIcon(new java.awt.Color(39, 61, 75));
        btnExportar.setFgIconHover(new java.awt.Color(0, 102, 0));
        btnExportar.setFocusPainted(false);
        btnExportar.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        btnExportar.setIconTextGap(0);
        btnExportar.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.FILE_DOWNLOAD);
        btnExportar.setPixels(0);
        btnExportar.setSizeIcon(30.0F);
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jScrollPane1.setOpaque(false);

        tbVentasMoviles.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tbVentasMoviles.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        tbVentasMoviles.setModel(new javax.swing.table.DefaultTableModel(
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
        tbVentasMoviles.setGridColor(new java.awt.Color(255, 255, 255));
        tbVentasMoviles.setOpaque(false);
        tbVentasMoviles.setRowHeight(20);
        tbVentasMoviles.setSelectionBackground(new java.awt.Color(7, 145, 255));
        tbVentasMoviles.setShowGrid(true);
        tbVentasMoviles.getTableHeader().setResizingAllowed(false);
        tbVentasMoviles.getTableHeader().setReorderingAllowed(false);
        tbVentasMoviles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbVentasMovilesMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbVentasMovilesMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbVentasMoviles);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 11)); // NOI18N
        jLabel2.setText("TIMBRADO:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        cboTimbrado.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        cboTimbrado.setEnabled(false);
        cboTimbrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTimbradoActionPerformed(evt);
            }
        });
        jPanel3.add(cboTimbrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 25, 200, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 11)); // NOI18N
        jLabel3.setText("P-EMISIÓN:");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        cbPE.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        cbPE.setEnabled(false);
        cbPE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPEActionPerformed(evt);
            }
        });
        jPanel3.add(cbPE, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 55, 200, -1));
        jPanel3.add(txtIdPE, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 50, -1));
        jPanel3.add(txtIdT, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 50, 20));

        ckHabilitar.setForeground(new java.awt.Color(0, 0, 0));
        ckHabilitar.setText("Mostrar tabla de facturas por medio de filtrados.");
        ckHabilitar.setColorCheck(new java.awt.Color(0, 102, 0));
        ckHabilitar.setColorUnCheck(new java.awt.Color(51, 51, 51));
        ckHabilitar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        ckHabilitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckHabilitarActionPerformed(evt);
            }
        });
        jPanel3.add(ckHabilitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 20));

        btnFiltrar.setBackground(new java.awt.Color(17, 35, 46));
        btnFiltrar.setText("GENERAR FILTRADO");
        btnFiltrar.setBackgroundHover(new java.awt.Color(39, 61, 75));
        btnFiltrar.setEnabled(false);
        btnFiltrar.setFont(new java.awt.Font("Roboto Bold", 1, 10)); // NOI18N
        btnFiltrar.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.FILTER_LIST);
        btnFiltrar.setMultiClickThreshhold(1L);
        btnFiltrar.setTypeBorder(RSMaterialComponent.RSButtonMaterialRippleIcon.TYPEBORDER.ROUND);
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });
        jPanel3.add(btnFiltrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 85, 120, 25));

        lbTotalVentas.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lbTotalVentas.setForeground(new java.awt.Color(0, 102, 0));
        lbTotalVentas.setText("0");

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(17, 35, 46));
        jLabel4.setText("RESUMEN: TOTAL DE VENTAS= ");

        dcFecha1.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    dcFecha1.setCalendarBackground(new java.awt.Color(255, 255, 255));
    dcFecha1.setFieldFont(new java.awt.Font("Roboto Black", java.awt.Font.BOLD, 15));
    dcFecha1.setNavigateFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 11));
    dcFecha1.setShowOneMonth(true);
    dcFecha1.addCommitListener(new datechooser.events.CommitListener() {
        public void onCommit(datechooser.events.CommitEvent evt) {
            dcFecha1OnCommit(evt);
        }
    });

    txtFecha.setEditable(false);
    txtFecha.setBackground(new java.awt.Color(255, 255, 255));
    txtFecha.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
    txtFecha.setForeground(new java.awt.Color(17, 35, 46));
    txtFecha.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    txtFecha.setBorder(null);

    jLabel5.setFont(new java.awt.Font("Roboto", 1, 15)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(17, 35, 46));
    jLabel5.setText("TOTAL DE ANULADOS= ");

    lbTotalVentas1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
    lbTotalVentas1.setForeground(new java.awt.Color(0, 102, 0));
    lbTotalVentas1.setText("0");

    bntBuscarNF.setBackground(new java.awt.Color(255, 255, 255));
    bntBuscarNF.setBorder(null);
    bntBuscarNF.setToolTipText("REALIZAR BUSQUEDA POR: N° DE FACTURA MÓVIL EMITIDA");
    bntBuscarNF.setBgHover(new java.awt.Color(255, 255, 255));
    bntBuscarNF.setFgIcon(new java.awt.Color(39, 61, 75));
    bntBuscarNF.setFgIconHover(new java.awt.Color(51, 255, 204));
    bntBuscarNF.setFocusPainted(false);
    bntBuscarNF.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
    bntBuscarNF.setIconTextGap(0);
    bntBuscarNF.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.SEARCH);
    bntBuscarNF.setPixels(0);
    bntBuscarNF.setSizeIcon(30.0F);
    bntBuscarNF.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            bntBuscarNFActionPerformed(evt);
        }
    });

    jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    jScrollPane2.setAutoscrolls(true);
    jScrollPane2.setOpaque(false);

    tbDetalleVentasMoviles.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    tbDetalleVentasMoviles.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
    tbDetalleVentasMoviles.setModel(new javax.swing.table.DefaultTableModel(
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
    tbDetalleVentasMoviles.setGridColor(new java.awt.Color(255, 255, 255));
    tbDetalleVentasMoviles.setOpaque(false);
    tbDetalleVentasMoviles.setRowHeight(20);
    tbDetalleVentasMoviles.setShowGrid(true);
    tbDetalleVentasMoviles.getTableHeader().setResizingAllowed(false);
    tbDetalleVentasMoviles.getTableHeader().setReorderingAllowed(false);
    jScrollPane2.setViewportView(tbDetalleVentasMoviles);

    btnReimprimirLote.setBackground(new java.awt.Color(255, 255, 255));
    btnReimprimirLote.setBorder(null);
    btnReimprimirLote.setText("RE-IMPRIMIR FACTURAS MÓVILES POR LOTE.");
    btnReimprimirLote.setBgHover(new java.awt.Color(255, 255, 255));
    btnReimprimirLote.setFgHover(new java.awt.Color(255, 0, 0));
    btnReimprimirLote.setFgIcon(new java.awt.Color(17, 35, 46));
    btnReimprimirLote.setFgIconHover(new java.awt.Color(255, 0, 0));
    btnReimprimirLote.setFgText(new java.awt.Color(17, 35, 46));
    btnReimprimirLote.setFocusPainted(false);
    btnReimprimirLote.setFont(new java.awt.Font("Roboto", 1, 11)); // NOI18N
    btnReimprimirLote.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
    btnReimprimirLote.setIconTextGap(5);
    btnReimprimirLote.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.PRINT);
    btnReimprimirLote.setPixels(0);
    btnReimprimirLote.setSizeIcon(50.0F);
    btnReimprimirLote.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
    btnReimprimirLote.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnReimprimirLoteActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout BlancoLayout = new javax.swing.GroupLayout(Blanco);
    Blanco.setLayout(BlancoLayout);
    BlancoLayout.setHorizontalGroup(
        BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(Oscuro, javax.swing.GroupLayout.DEFAULT_SIZE, 1072, Short.MAX_VALUE)
        .addComponent(jScrollPane1)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BlancoLayout.createSequentialGroup()
            .addGap(0, 0, 0)
            .addGroup(BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, BlancoLayout.createSequentialGroup()
                    .addComponent(jScrollPane2)
                    .addGap(0, 0, 0)
                    .addGroup(BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(BlancoLayout.createSequentialGroup()
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(2, 2, 2))
                        .addGroup(BlancoLayout.createSequentialGroup()
                            .addComponent(btnReimprimirLote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(5, 5, 5))))
                .addGroup(BlancoLayout.createSequentialGroup()
                    .addComponent(jLabel4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbTotalVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbTotalVentas1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(dcFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(BlancoLayout.createSequentialGroup()
                    .addComponent(bntBuscarNF, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(1, 1045, Short.MAX_VALUE))))
    );
    BlancoLayout.setVerticalGroup(
        BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(BlancoLayout.createSequentialGroup()
            .addComponent(Oscuro, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(2, 2, 2)
            .addComponent(bntBuscarNF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbTotalVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbTotalVentas1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addComponent(dcFecha1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtFecha)
                .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGap(0, 0, 0)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(5, 5, 5)
            .addGroup(BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(BlancoLayout.createSequentialGroup()
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                    .addComponent(btnReimprimirLote, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGap(5, 5, 5))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(Blanco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1072, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(Blanco, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbVentasMovilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentasMovilesMouseClicked
        // TODO add your handling code here:
        /*try {
            CabecerasTablas.limpiarTablas(tbDetalleCompra);
            controlCompra.listarDetalleCompras(tbDetalleCompra);
            RendersD();
        } catch (Exception e) {
            Mensajes.error(e.toString());
        }*/
    }//GEN-LAST:event_tbVentasMovilesMouseClicked

    private void tbVentasMovilesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVentasMovilesMousePressed
        // TODO add your handling code here:
        try {
            CabecerasTablas.limpiarTablasDetalleVentasMoviles(tbDetalleVentasMoviles);
            controlFactura.listDetalleVentasMoviles(tbDetalleVentasMoviles);
            RendersD();
        } catch (Exception e) {
            Mensajes.error(e.toString());
        }
    }//GEN-LAST:event_tbVentasMovilesMousePressed

    private void cboTimbradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTimbradoActionPerformed
        // TODO add your handling code here:
        try {
            int codT = 0;
            if (cboTimbrado.getSelectedIndex() == 0) {
                txtIdT.setText("");
                cbPE.setSelectedIndex(0);
                cbPE.setEnabled(false);
            } else {
                cbPE.setEnabled(true);
                try {
                    String id = cargarComboBoxMovil.getCodidgo(cboTimbrado);
                    txtIdT.setText(id);
                } catch (Exception e) {
                    txtIdT.setText("");
                }
                prepararBD();
                String timb;
                timb = cboTimbrado.getSelectedItem().toString();
                try {
                    rs = sentencia.executeQuery("SELECT * FROM timbrado WHERE descripcion='" + timb + "'");
                    rs.last();
                    codT = rs.getInt("idtimbrado");
                    rs.close();
                } catch (SQLException ex) {
                    Mensajes.error("Error al querer obtener valor del Timbrado: " + ex.getMessage());
                }
                cargarComboBoxMovil.cargarPE(cbPE, "SELECT idemision,establecimiento,puntoemision,direccion FROM v_puntoemision WHERE idtimbrado=" + codT);
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_cboTimbradoActionPerformed

    private void cbPEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPEActionPerformed
        // TODO add your handling code here:
        try {
            String id = cargarComboBoxMovil.getCodidgo(cbPE);
            txtIdPE.setText(id);
        } catch (Exception e) {
            txtIdPE.setText("");
        }
    }//GEN-LAST:event_cbPEActionPerformed

    private void dcFecha1OnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_dcFecha1OnCommit
        // TODO add your handling code here:
        //txtFecha.setText(InicializarFecha());
        String fe = dcFecha1.getText();
        //Fecha.formatoFechaVM(fe);
        txtFecha.setText(Fecha.formatoFechaVM(fe));
        txtFechaFiltro.setText(Fecha.formatoFechaVMR(fe));
        btnActualizarActionPerformed(null);

    }//GEN-LAST:event_dcFecha1OnCommit

    private void ckHabilitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckHabilitarActionPerformed
        // TODO add your handling code here:
        filtrar();
    }//GEN-LAST:event_ckHabilitarActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        // TODO add your handling code here:
        if (cboTimbrado.getSelectedIndex() == 0) {
            Mensajes.error("SELECCIONE UN TIMBRADO PARA HACER EFECTIVO EL FILTRADO");
        } else {
            if (cbPE.getSelectedIndex() == 0) {
                CabecerasTablas.limpiarTablasVentasMoviles(tbVentasMoviles);
                CabecerasTablas.limpiarTablasDetalleVentasMoviles(tbDetalleVentasMoviles);
                CabecerasTablas.consVentasMoviles(tbVentasMoviles);
                CabecerasTablas.consDetalleVentasMoviles(tbDetalleVentasMoviles);
                controlFactura.listFacturasMovilesT(tbVentasMoviles, txtIdT.getText(), txtFechaFiltro.getText().trim());
                Renders();

            } else {
                CabecerasTablas.limpiarTablasVentasMoviles(tbVentasMoviles);
                CabecerasTablas.limpiarTablasDetalleVentasMoviles(tbDetalleVentasMoviles);
                CabecerasTablas.consVentasMoviles(tbVentasMoviles);
                CabecerasTablas.consDetalleVentasMoviles(tbDetalleVentasMoviles);
                controlFactura.listFacturasMovilesTPE(tbVentasMoviles, txtIdT.getText(), txtIdPE.getText(), txtFechaFiltro.getText().trim());
                Renders();

            }
        }

        CargarTotalVentas();
        CargarTotalVentasA();
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnReimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReimprimirActionPerformed
        // TODO add your handling code here:
        if (dlgConsultarFacturasMovil.tbVentasMoviles.getSelectedRow() < 0) {
            Mensajes.error("Indique la factura móvil que necesita re-imprimir");
        } else {
            int x = dlgConsultarFacturasMovil.tbVentasMoviles.getSelectedRow();
            String estado = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 13).toString();
            if (estado.equals("ANULADO")) {
                Mensajes.informacion("NO ES POSIBLE REALIZAR LA REIMPRESIÓN DE FACTURA.\nMotivo: Esta Venta Móvil se encuentra anulada");
            } else {
            try {
                int rpta = Mensajes.confirmar("Seguro que desea re-imprimir esta factura móvil?");
                if (rpta == 0) {
                    String Timbrado = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 2).toString();
                    String Desde = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 3).toString();
                    String Hasta = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 4).toString();
                    String Condicion = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 6).toString();
                    String Factura = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 5).toString();
                    String Fecha = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 7).toString();
                    String Hora = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 8).toString();
                    String Cliente = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 10).toString();
                    String Ruc = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 9).toString();
                    String Vendedor = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 12).toString();
                    String Total = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 11).toString().replace(".", "").replace(",", "");
                    String Estado = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 13).toString();
                    String Exenta = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 14).toString();
                    String Cinco = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 15).toString();
                    String Diez = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 16).toString();

                    imprimirTicketOriginal(Timbrado, Desde, Hasta, Condicion, Factura, Fecha, Hora, Cliente, Ruc, Vendedor, Total, Exenta, Cinco, Diez, Estado);

                }

            } catch (Exception e) {

                }
            }
        }
    }//GEN-LAST:event_btnReimprimirActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        CabecerasTablas.limpiarTablasVentasMoviles(tbVentasMoviles);
        CabecerasTablas.limpiarTablasDetalleVentasMoviles(tbDetalleVentasMoviles);
        CabecerasTablas.consVentasMoviles(tbVentasMoviles);
        CabecerasTablas.consDetalleVentasMoviles(tbDetalleVentasMoviles);
        controlFactura.listFacturasMoviles(tbVentasMoviles, txtFechaFiltro.getText().trim());
        Renders();
        CargarTotalVentas();
        CargarTotalVentasA();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        // TODO add your handling code here:
        if (dlgConsultarFacturasMovil.tbVentasMoviles.getSelectedRow() < 0) {
            Mensajes.error("Indique la factura móvil que necesita anular");
        } else {
            int x = dlgConsultarFacturasMovil.tbVentasMoviles.getSelectedRow();
            String estado = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 13).toString();
            if (estado.equals("ANULADO")) {
                Mensajes.informacion("No es posible realizar la anulación.\nMOTIVO: Esta factura móvil se encuentra anulada");
            } else {
                try {
                    String msg;
                    int rpta = Mensajes.confirmar("Seguro que desea anular esta factura móvil?");
                    if (rpta == 0) {
                        msg = controlFactura.anularVentaMovil();
                        if (msg == null) {
                            CabecerasTablas.limpiarTablasVentasMoviles(tbVentasMoviles);
                            CabecerasTablas.limpiarTablasDetalleVentasMoviles(tbDetalleVentasMoviles);
                            CabecerasTablas.consVentasMoviles(tbVentasMoviles);
                            CabecerasTablas.consDetalleVentasMoviles(tbDetalleVentasMoviles);
                            controlFactura.listFacturasMoviles(tbVentasMoviles, txtFechaFiltro.getText().trim());
                            Renders();
                            CargarTotalVentas();
                            CargarTotalVentasA();
                        }
                    }
                } catch (Exception e) {
                    //Mensajes.informacion("Seleccione la fila a eliminar:" + e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btnAnularActionPerformed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        // TODO add your handling code here:
        int rpta = Mensajes.confirmar("¿Seguro que desea salir del formulario?");
        if (rpta == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSalir1ActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        // TODO add your handling code here:
        try {
            Export = new clsExportarExcel();
            Export.exportarExcel(tbVentasMoviles);
        } catch (IOException ex) {
            Logger.getLogger(dlgClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnExportarActionPerformed

    private void bntBuscarNFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntBuscarNFActionPerformed
        // TODO add your handling code here:
        try {
            String cod = (JOptionPane.showInputDialog("Ingrese N° de Factura Móvil"));
            for (int i = 0; i < tbVentasMoviles.getRowCount(); i++) {
                if (tbVentasMoviles.getValueAt(i, 5).equals(cod)) {
                    tbVentasMoviles.changeSelection(i, 1, false, false);
                    tbVentasMovilesMousePressed(null);
                    break;
                }
            }
        } catch (HeadlessException e) {
            Mensajes.informacion("Consulta cancelada" + e.getMessage());
        }
    }//GEN-LAST:event_bntBuscarNFActionPerformed

    private void btnReimprimirLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReimprimirLoteActionPerformed
        // TODO add your handling code here:
        try {
            int cant = tbVentasMoviles.getRowCount();
            //Mensajes.informacion(String.valueOf(cant));
            if (cant <= 0) {
                Mensajes.informacion("No se contabilizan facturas móviles.\nVerifique la fecha del formulario y si las exportaciones se realizaron exitosamente.");
            } else {
                int rpta = Mensajes.confirmar("Se han contabilizado " + String.valueOf(cant) + " facturas móviles." + "\n"
                        + "Seguro que desea comenzar la re-impresión masiva?"
                        +"\n\n"+"PUNTOS A VERIFICACIÓN A TENER EN CUENTA ANTES DE ACEPTAR:\n"
                        + "- Controlar el punto de emisión selecionado.\n"
                        + "- Verificar la fecha de trabajo.\n"
                        + "- Revisar el volumen del rollo de papel, esto evitara que la tarea se corte o genere errores en la impresión.\n\n"
                        + "Si todo esta en orden, seleccione la opción YES para iniciar a enviar las facturas a la cola de impresión.");
                        
                if (rpta == 0) {
                    int i = 0;
                    do {
                        tbVentasMoviles.changeSelection(i, 1, false, false);
                        tbVentasMovilesMousePressed(null);
                        int x = dlgConsultarFacturasMovil.tbVentasMoviles.getSelectedRow();
                        String Timbrado = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 2).toString();
                        String Desde = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 3).toString();
                        String Hasta = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 4).toString();
                        String Condicion = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 6).toString();
                        String Factura = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 5).toString();
                        String Fecha = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 7).toString();
                        String Hora = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 8).toString();
                        String Cliente = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 10).toString();
                        String Ruc = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 9).toString();
                        String Vendedor = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 12).toString();
                        String Total = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 11).toString().replace(".", "").replace(",", "");
                        String Estado = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 13).toString();
                        String Exenta = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 14).toString();
                        String Cinco = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 15).toString();
                        String Diez = dlgConsultarFacturasMovil.tbVentasMoviles.getValueAt(x, 16).toString();
                        imprimirTicketOriginal(Timbrado, Desde, Hasta, Condicion, Factura, Fecha, Hora, Cliente, Ruc, Vendedor, Total, Exenta, Cinco, Diez, Estado);
                        i++;
                    } while (i < cant);
                    {
                        Mensajes.Sistema("Lote de facturas móviles enviados correctamente a la impresora.");
                        tbVentasMoviles.clearSelection();
                        CabecerasTablas.limpiarTablasDetalleVentasMoviles(tbDetalleVentasMoviles);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Consulta cancelada" + e.getMessage());
        }
    }//GEN-LAST:event_btnReimprimirLoteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dlgConsultarFacturasMovil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dlgConsultarFacturasMovil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dlgConsultarFacturasMovil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dlgConsultarFacturasMovil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dlgConsultarFacturasMovil dialog = new dlgConsultarFacturasMovil(new javax.swing.JFrame(), true);
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
    public static newscomponents.RSButtonBigIcon_new bntBuscarNF;
    public static newscomponents.RSButtonBigIcon_new btnActualizar;
    public static newscomponents.RSButtonBigIcon_new btnAnular;
    public static newscomponents.RSButtonBigIcon_new btnExportar;
    private RSMaterialComponent.RSButtonMaterialRippleIcon btnFiltrar;
    public static newscomponents.RSButtonBigIcon_new btnReimprimir;
    public static newscomponents.RSButtonBigIcon_new btnReimprimirLote;
    public static newscomponents.RSButtonBigIcon_new btnSalir1;
    private javax.swing.JComboBox<String> cbPE;
    private javax.swing.JComboBox<String> cboTimbrado;
    private rojerusan.RSCheckBox ckHabilitar;
    public static datechooser.beans.DateChooserCombo dcFecha1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JLabel lbTotalVentas;
    public static javax.swing.JLabel lbTotalVentas1;
    public static javax.swing.JTable tbDetalleVentasMoviles;
    public static javax.swing.JTable tbVentasMoviles;
    public static javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFechaFiltro;
    private javax.swing.JTextField txtIdPE;
    private javax.swing.JTextField txtIdT;
    // End of variables declaration//GEN-END:variables
}
