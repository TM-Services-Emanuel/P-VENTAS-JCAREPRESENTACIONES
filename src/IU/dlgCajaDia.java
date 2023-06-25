package IU;

import Componentes.ConexionBD;
import Componentes.Fecha;
import Componentes.Mensajes;
import Componentes.Reporte;
import Componentes.Software;
import Componentes.generarCodigos;
import Componentes.validarCampos;
import Controladores.ControlCaja;
import Datos.GestionarCaja;
import Modelo.Caja;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.mariadb.jdbc.MariaDbConnection;
import org.mariadb.jdbc.MariaDbStatement;

public final class dlgCajaDia extends javax.swing.JDialog {
    public String NCaja;
    public int ING;
    public int GAS;
    public int INI;
    
    public Reporte jasper;
    
    public static ResultSet rs;
    public static MariaDbConnection con;
    public static MariaDbStatement sentencia;
    public static MariaDbConnection conMovil;
    public static MariaDbStatement sentenciaMovil;
    
    public dlgCajaDia(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jasper = new Reporte();
        titulo();
        Invisible();
        txtDesde.setText(Fecha.formatoFecha(dcDesde.getText()));
        txtHasta.setText(Fecha.formatoFecha(dcHasta.getText()));
        
        try {
            NCaja=(generarCodigos.ObtenerCodigo("SELECT ca_id from caja where ca_indicador='S' ORDER BY ca_id DESC LIMIT 1"));
            System.out.println("N CAja hoy: "+NCaja);
            Caja caj = GestionarCaja.busCaja(NCaja);
            DecimalFormat df = new DecimalFormat("#,###");
            lbInicial.setText(df.format(Integer.valueOf(String.valueOf(caj.getCaInicial()).trim().replace(".", "").replace(",", ""))));
            INI=((caj.getCaInicial()));
            lbNCaja.setText(String.valueOf(caj.getCaId()));
            lbFecha.setText(Fecha.formatoFechaFF(caj.getFechaI()));
            lbHora.setText(Fecha.FormatoHoraSinSSString(caj.getHoraI()));
            lbUsuI.setText(String.valueOf(caj.getUsuarioI()));
            txtEntregado.setText(df.format(caj.getCaEntregado()));
            txtDiferencia.setText(df.format(caj.getCaDiferencia()));
            if(caj.getIndicador().equals("S")){
                lbEstado.setText("ABIERTO");
            }else{
                lbEstado.setText("CERRADO");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        totalVentasCont();
        totalVentasCred();
        totalCompraCont();
        totalCompraCred();
        totalGasto();
        totalIngreso();
        gastoTotal();
        ingresoTotal();
        totalCaja();
        calcular();
    }
    
    final void titulo(){
        if(Software.getSoftware().equals("null")){
            this.setTitle("Cerrar movimientos del día");
        }else{
            this.setTitle(Software.getSoftware()+" - Cerrar movimientos del día");
        }
    }
    
    public void calcular(){
        int entregar, entregado,dif;
        entregar=Integer.parseInt(txtEntregar.getText().trim().replace(",", "").replace(".", ""));
        entregado=Integer.parseInt(txtEntregado.getText().trim().replace(",", "").replace(".", ""));
        if(entregar>=0){
            dif=entregado-entregar;
            DecimalFormat df = new DecimalFormat("#,###");
            txtDiferencia.setText((df.format(dif)));
        }
        
    }
    
    public static void prepararBD() {
        try {
            con = (MariaDbConnection) new ConexionBD().getConexion();
            conMovil = (MariaDbConnection) new ConexionBD().getConexionMovil();
            if (con == null) {
                System.out.println("No hay Conexion con la Base de Datos");
            } else {
                sentencia = (MariaDbStatement) con.createStatement();
            }
            if (conMovil == null) {
                System.out.println("No hay Conexion con la Base de Datos Movil");
            } else {
                sentenciaMovil = (MariaDbStatement) conMovil.createStatement();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void CalcularDiferencia(String desde, String hasta) {
        prepararBD();
        try {
            DecimalFormat df = new DecimalFormat("#,###");
            StringBuilder sql = new StringBuilder("SELECT SUM(ca_diferencia) FROM caja WHERE ca_fechainicio>='");
            sql.append(desde);
            sql.append("' AND ca_fechainicio<='");
            sql.append(hasta).append("'");
            rs = sentencia.executeQuery(sql.toString());
            rs.first();
            txtDifAcumulada.setText(df.format(rs.getInt(1)));
            rs.close();
        } catch (SQLException ex) {
            Mensajes.error("Error obteniendo las diferencias acumuladas: " + ex.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Blanco = new org.edisoncor.gui.panel.PanelImage();
        Oscuro = new org.edisoncor.gui.panel.PanelImage();
        jPanel5 = new javax.swing.JPanel();
        btnCerrar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        txtDesde = new javax.swing.JTextField();
        txtHasta = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        lbNCaja = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtEntregar = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtDiferencia = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtEntregado = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lbInicial = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbFecha = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbUsuI = new javax.swing.JLabel();
        lbEstado = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbHora = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtDifAcumulada = new javax.swing.JLabel();
        dcDesde = new datechooser.beans.DateChooserCombo();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        dcHasta = new datechooser.beans.DateChooserCombo();
        btnCalcular = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtTotalGastos = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtGastoTotal = new javax.swing.JTextField();
        txtTotalCompraC = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        txtTotalCompra = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtTotalVentas = new javax.swing.JTextField();
        txtTotalIngreso = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtIngresoT = new javax.swing.JTextField();
        txtTotalVentasC = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        barMenu = new javax.swing.JMenuBar();
        menuOpciones = new javax.swing.JMenu();
        itemNuevo = new javax.swing.JMenuItem();
        itemModificar = new javax.swing.JMenuItem();
        itemSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        Blanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/fondoBlanco.jpg"))); // NOI18N
        Blanco.setPreferredSize(new java.awt.Dimension(690, 418));

        Oscuro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/fondo.png"))); // NOI18N
        Oscuro.setPreferredSize(new java.awt.Dimension(690, 418));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new java.awt.GridLayout(1, 9));

        btnCerrar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 9)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cerrar - copia.png"))); // NOI18N
        btnCerrar.setText("Cerrar Caja-F9");
        btnCerrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCerrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel5.add(btnCerrar);

        btnImprimir.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 9)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/printer30.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.setEnabled(false);
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImprimir.setVerifyInputWhenFocusTarget(false);
        btnImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        jPanel5.add(btnImprimir);

        btnGuardar.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 9)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/save30.png"))); // NOI18N
        btnGuardar.setText("Guardar - F6");
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setVerifyInputWhenFocusTarget(false);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel5.add(btnGuardar);

        btnSalir.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 9)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/back30.png"))); // NOI18N
        btnSalir.setText("Salir-Alt+F4");
        btnSalir.setToolTipText("Salir");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel5.add(btnSalir);

        javax.swing.GroupLayout OscuroLayout = new javax.swing.GroupLayout(Oscuro);
        Oscuro.setLayout(OscuroLayout);
        OscuroLayout.setHorizontalGroup(
            OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OscuroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtHasta, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(txtDesde))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        OscuroLayout.setVerticalGroup(
            OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OscuroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OscuroLayout.createSequentialGroup()
                        .addComponent(txtDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel16.setText("VISOR DE MOVIMIENTO DIARIO N°");

        lbNCaja.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbNCaja.setText("NCaja");

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setOpaque(false);

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel17.setText("EFE. A ENTREGAR");

        txtEntregar.setBackground(new java.awt.Color(255, 255, 255));
        txtEntregar.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txtEntregar.setForeground(new java.awt.Color(0, 102, 0));
        txtEntregar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtEntregar.setText("0");
        txtEntregar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtEntregar.setOpaque(true);

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel18.setText("EFE. ENTREGADO");

        txtDiferencia.setBackground(new java.awt.Color(255, 255, 255));
        txtDiferencia.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txtDiferencia.setForeground(new java.awt.Color(205, 0, 0));
        txtDiferencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtDiferencia.setText("0");
        txtDiferencia.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDiferencia.setOpaque(true);

        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel20.setText("DIF. DEL DÍA");

        txtEntregado.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtEntregado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEntregado.setText("0");
        txtEntregado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEntregadoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEntregadoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17))
                        .addGap(11, 11, 11))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEntregar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtDiferencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtEntregado, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEntregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEntregado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiferencia))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setBackground(new java.awt.Color(17, 35, 46));
        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("  Efectivo base inicializado para los movimientos del día:");
        jLabel6.setOpaque(true);

        lbInicial.setBackground(new java.awt.Color(17, 35, 46));
        lbInicial.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbInicial.setForeground(new java.awt.Color(255, 0, 0));
        lbInicial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbInicial.setText("0");
        lbInicial.setOpaque(true);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("DETALLE DE LA APERTURA"));
        jPanel6.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel4.setText("Fecha:");

        lbFecha.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbFecha.setText("FECHA ");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel1.setText("Realizado por:");

        lbUsuI.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbUsuI.setText("USUSARIO");

        lbEstado.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbEstado.setForeground(new java.awt.Color(0, 102, 0));
        lbEstado.setText("ESTADO");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel19.setText("Estado actual:");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel7.setText("Hora:");

        lbHora.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbHora.setText("HORA");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(50, 50, 50))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(55, 55, 55)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lbUsuI, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                        .addComponent(lbHora, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbFecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbFecha))
                .addGap(3, 3, 3)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lbHora))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbUsuI))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(lbEstado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setOpaque(false);

        txtDifAcumulada.setBackground(new java.awt.Color(17, 35, 46));
        txtDifAcumulada.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txtDifAcumulada.setForeground(new java.awt.Color(205, 0, 0));
        txtDifAcumulada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtDifAcumulada.setText("0");
        txtDifAcumulada.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDifAcumulada.setOpaque(true);

        dcDesde.setFieldFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));
        dcDesde.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                dcDesdeOnCommit(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("DESDE");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setText("HASTA");

        dcHasta.setFieldFont(new java.awt.Font("Times New Roman", java.awt.Font.PLAIN, 14));
        dcHasta.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                dcHastaOnCommit(evt);
            }
        });

        btnCalcular.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnCalcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/calcularx30.png"))); // NOI18N
        btnCalcular.setText("CALCULAR");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(17, 35, 46));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Diferencia acumulada");
        jLabel2.setOpaque(true);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(dcHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(dcDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(btnCalcular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDifAcumulada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(dcDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(dcHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtDifAcumulada)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbNCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, 0)
                        .addComponent(lbInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbNCaja, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbInicial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtTotalGastos.setEditable(false);
        txtTotalGastos.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalGastos.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtTotalGastos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalGastos.setText("0");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Suma de gastos, pagos y/o retiros:");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("TOTAL DE EGRESOS DEL MOVIMIENTO DEL DÍA:");

        txtGastoTotal.setEditable(false);
        txtGastoTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtGastoTotal.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtGastoTotal.setForeground(new java.awt.Color(255, 0, 0));
        txtGastoTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGastoTotal.setText("0");

        txtTotalCompraC.setEditable(false);
        txtTotalCompraC.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalCompraC.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtTotalCompraC.setForeground(new java.awt.Color(0, 0, 102));
        txtTotalCompraC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalCompraC.setText("0");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("PARA CONSIDERAR: Compras a crédito");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("PARA CONSIDERAR: Compras a contado:");

        txtTotalCompra.setEditable(false);
        txtTotalCompra.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalCompra.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtTotalCompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalCompra.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel15)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotalCompraC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtGastoTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                .addComponent(txtTotalGastos))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTotalCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalGastos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGastoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalCompraC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtTotalVentas.setEditable(false);
        txtTotalVentas.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalVentas.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtTotalVentas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalVentas.setText("0");

        txtTotalIngreso.setEditable(false);
        txtTotalIngreso.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalIngreso.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtTotalIngreso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalIngreso.setText("0");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Valor de la ventas a contado:");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Valor de los otros ingresos:");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 11)); // NOI18N
        jLabel14.setText("TOTAL  DE INGRESOS DEL MOV. DEL DÍA:");

        txtIngresoT.setEditable(false);
        txtIngresoT.setBackground(new java.awt.Color(255, 255, 255));
        txtIngresoT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtIngresoT.setForeground(new java.awt.Color(0, 102, 0));
        txtIngresoT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIngresoT.setText("0");

        txtTotalVentasC.setEditable(false);
        txtTotalVentasC.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalVentasC.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtTotalVentasC.setForeground(new java.awt.Color(0, 0, 102));
        txtTotalVentasC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalVentasC.setText("0");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("A CONSIDERAR: Valor de ventas a Crédito:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIngresoT, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotalVentasC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotalIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotalVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtIngresoT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTotalVentasC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BlancoLayout = new javax.swing.GroupLayout(Blanco);
        Blanco.setLayout(BlancoLayout);
        BlancoLayout.setHorizontalGroup(
            BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Oscuro, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
            .addGroup(BlancoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(BlancoLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BlancoLayout.setVerticalGroup(
            BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BlancoLayout.createSequentialGroup()
                .addComponent(Oscuro, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        barMenu.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        menuOpciones.setText("Opciones");
        menuOpciones.setFocusable(false);
        menuOpciones.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        menuOpciones.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        itemNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        itemNuevo.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        itemNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/document15.png"))); // NOI18N
        itemNuevo.setText("Cerrar Caja");
        itemNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNuevoActionPerformed(evt);
            }
        });
        menuOpciones.add(itemNuevo);

        itemModificar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        itemModificar.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        itemModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/printer15.png"))); // NOI18N
        itemModificar.setText("Imprimir");
        itemModificar.setEnabled(false);
        itemModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemModificarActionPerformed(evt);
            }
        });
        menuOpciones.add(itemModificar);

        itemSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        itemSalir.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        itemSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/back15.png"))); // NOI18N
        itemSalir.setText("Salir");
        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalirActionPerformed(evt);
            }
        });
        menuOpciones.add(itemSalir);

        barMenu.add(menuOpciones);

        setJMenuBar(barMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Blanco, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Blanco, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void Invisible(){
        txtDesde.setVisible(false);
        txtHasta.setVisible(false);
    }
    void totalVentasCont() {
        try {
            String TotalVenta=(generarCodigos.getDecimales("SELECT IFNULL((select SUM(fac_totalfinal) from factura where caja_ca_id = "+NCaja+" and fac_indicador='S' and fac_tipoventa='CONTADO'),0)"));
            DecimalFormat df = new DecimalFormat("#,###");
            txtTotalVentas.setText(df.format(Integer.valueOf((TotalVenta.trim().replace(".", "").replace(",", "")))));
        } catch (Exception e) {
            txtTotalVentas.setText("0");
        }
    }
    void totalVentasCred() {
        try {
            String TotalVenta=(generarCodigos.getDecimales("SELECT IFNULL((select SUM(fac_totalfinal) from factura where caja_ca_id = "+NCaja+" and fac_indicador='S' and fac_tipoventa='CREDITO'),0)"));
            DecimalFormat df = new DecimalFormat("#,###");
            txtTotalVentasC.setText(df.format(Integer.valueOf((TotalVenta.trim().replace(".", "").replace(",", "")))));   
        } catch (Exception e) {
            txtTotalVentasC.setText("0");
        }
    }
    
    void totalCompraCont() {
        try {
            String TotalCompra=(generarCodigos.getDecimales("SELECT IFNULL((select SUM(com_total) from compra where caja_ca_id = " +NCaja+ " and com_indicador='S' and com_condpago='CONTADO' and tipo='L'),0)"));
            DecimalFormat df = new DecimalFormat("#,###");
            txtTotalCompra.setText(df.format(Integer.valueOf((TotalCompra.trim().replace(".", "").replace(",", "")))));
        } catch (Exception e) {
            txtTotalCompra.setText("0");
        }
        
    }
    void totalCompraCred() {
        try {
            String TotalCompra=(generarCodigos.getDecimales("SELECT IFNULL((select SUM(com_total) from compra where caja_ca_id = " +NCaja+ " and com_indicador='S' and com_condpago='CREDITO' and tipo='L'),0)"));
            DecimalFormat df = new DecimalFormat("#,###");
            txtTotalCompraC.setText(df.format(Integer.valueOf((TotalCompra.trim().replace(".", "").replace(",", "")))));
        } catch (Exception e) {
            txtTotalCompraC.setText("0");
        }
        
    }

    void totalGasto() {
        try {
            DecimalFormat df = new DecimalFormat("#,###");
            String TotalGasto=(generarCodigos.getDecimales("SELECT IFNULL((select SUM(ga_importe) from gastos where caja_ca_id = " +NCaja+ " and ga_indicador='S' AND tipo='L'),0)"));
            txtTotalGastos.setText(df.format(Integer.valueOf((TotalGasto.trim().replace(".", "").replace(",", "")))));
        } catch (Exception e) {
            txtTotalGastos.setText("0");
        }
    }
    
    void totalIngreso() {
        try {
            String TotalIngreso=(generarCodigos.getDecimales("SELECT IFNULL((select SUM(ing_importe) from ingreso where caja_ca_id = " +NCaja+ " and ing_indicador='S'),0)"));
            DecimalFormat df = new DecimalFormat("#,###");
            txtTotalIngreso.setText(df.format(Integer.valueOf((TotalIngreso.trim().replace(".", "").replace(",", "")))));
        } catch (Exception e) {
            txtTotalIngreso.setText("0");
        }
        
    }

    void gastoTotal() {
        try {
            DecimalFormat df = new DecimalFormat("#,###");
            int G=Integer.parseInt(txtTotalGastos.getText().replace(",", "").replace(".", ""));
            GAS=G;
            txtGastoTotal.setText(df.format(G));
        } catch (NumberFormatException e) {
            txtGastoTotal.setText("0");
        }
    }
    
    void ingresoTotal(){
        try {
            DecimalFormat df = new DecimalFormat("#,###");
            int IG= Integer.parseInt(txtTotalVentas.getText().replace(",", "").replace(".", ""))+(Integer.parseInt(txtTotalIngreso.getText().replace(",", "").replace(".", "")));
            ING=IG;
            txtIngresoT.setText(df.format(IG));
        } catch (Exception e) {
            txtIngresoT.setText("0");
        }
    }
    
    void totalCaja(){
        DecimalFormat df = new DecimalFormat("#,###");
        int res = ING-GAS;
        txtEntregar.setText(df.format(res));
    }
    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        if (Integer.parseInt(txtEntregar.getText().trim().replace(",", "").replace(".", "")) < 0) {
            Mensajes.error("El cierre final de la caja contiene saldo negativo.\nNo se puede proceder a cerrar la caja.\nConsejo: verifique si una o varias operaciones no fueron duplicados.");
        } else {
            int resp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea Cerrar la Caja y Finalizar las Operaciones?", "Cierre de caja", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (resp == JOptionPane.YES_OPTION) {
                try {
                    ControlCaja.CerrarCaja();
                    btnCerrar.setEnabled(false);
                    btnImprimir.setEnabled(true);
                    txtEntregado.setEnabled(false);
                    dcDesde.setEnabled(false);
                    dcHasta.setEnabled(false);
                    btnCalcular.setEnabled(false);
                    btnGuardar.setEnabled(false);
                    Caja caj = GestionarCaja.busCaja(NCaja);
                    if (caj.getIndicador().equals("S")) {
                        lbEstado.setText("ABIERTO");
                    } else {
                        lbEstado.setText("CERRADO");
                    }
                } catch (Exception e) {
                    Mensajes.error(e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:

                jasper.reporteUnParametroVertical("\\Reports\\caja\\ResumenCajaF.jasper", "caja", Integer.parseInt(lbNCaja.getText().trim()));

    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        int rpta = Mensajes.confirmar("¿Seguro que desea salir del formulario?");
        if (rpta == 0) {
            this.dispose();
        }

    }//GEN-LAST:event_btnSalirActionPerformed

    private void itemNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNuevoActionPerformed
        // TODO add your handling code here:
        btnCerrar.doClick();
    }//GEN-LAST:event_itemNuevoActionPerformed

    private void itemModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemModificarActionPerformed

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalirActionPerformed
        // TODO add your handling code here:
        btnSalirActionPerformed(null);
    }//GEN-LAST:event_itemSalirActionPerformed

    private void txtEntregadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEntregadoKeyPressed
        // TODO add your handling code here:
        validarCampos.soloNumeros(txtEntregado);
    }//GEN-LAST:event_txtEntregadoKeyPressed

    private void txtEntregadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEntregadoKeyReleased
        // TODO add your handling code here:
        try {
            if (txtEntregado.getText().trim().length() >= 1) {
                DecimalFormat df = new DecimalFormat("#,###");
                txtEntregado.setText(df.format(Integer.valueOf(txtEntregado.getText().trim().replace(".", "").replace(",", ""))));
            } else {
                txtEntregado.setText("0");
                txtEntregado.selectAll();
            }
        } catch (NumberFormatException e) {
            System.out.println("c: " + e.getMessage());
        }
        calcular();
    }//GEN-LAST:event_txtEntregadoKeyReleased

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        try {
                int resp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea insertar el registro?", "Insertar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (resp == JOptionPane.YES_OPTION) {
                    ControlCaja.actCaja();
                }
            } catch (HeadlessException ee) {
                System.out.println(ee.getMessage());
            }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void dcDesdeOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_dcDesdeOnCommit
        // TODO add your handling code here:
        txtDesde.setText(Fecha.formatoFecha(dcDesde.getText()));
    }//GEN-LAST:event_dcDesdeOnCommit

    private void dcHastaOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_dcHastaOnCommit
        // TODO add your handling code here:
        txtHasta.setText(Fecha.formatoFecha(dcHasta.getText()));
    }//GEN-LAST:event_dcHastaOnCommit

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        // TODO add your handling code here:
        try {
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
            Date desde = sdformat.parse(txtDesde.getText().trim());
            Date hasta = sdformat.parse(txtHasta.getText().trim());
            if (desde.before(hasta)) {
                try {
                    CalcularDiferencia(txtDesde.getText().trim(), txtHasta.getText().trim());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                Mensajes.error("ERROR:\nLa fecha DESDE no puede ser mayor o igual a la fecha HASTA.\nCorrija esta observación y vuelva a intentarlo.");
            }
        } catch (ParseException ee) {
        }
    }//GEN-LAST:event_btnCalcularActionPerformed
    
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dlgCajaDia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            dlgCajaDia dialog = new dlgCajaDia(new javax.swing.JFrame(), true);
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
    private javax.swing.JMenuBar barMenu;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnSalir;
    private datechooser.beans.DateChooserCombo dcDesde;
    private datechooser.beans.DateChooserCombo dcHasta;
    public static javax.swing.JMenuItem itemModificar;
    public static javax.swing.JMenuItem itemNuevo;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbEstado;
    private javax.swing.JLabel lbFecha;
    private javax.swing.JLabel lbHora;
    private javax.swing.JLabel lbInicial;
    public static javax.swing.JLabel lbNCaja;
    private javax.swing.JLabel lbUsuI;
    private javax.swing.JMenu menuOpciones;
    private javax.swing.JTextField txtDesde;
    public static javax.swing.JLabel txtDifAcumulada;
    public static javax.swing.JLabel txtDiferencia;
    public static javax.swing.JTextField txtEntregado;
    public static javax.swing.JLabel txtEntregar;
    private javax.swing.JTextField txtGastoTotal;
    private javax.swing.JTextField txtHasta;
    private javax.swing.JTextField txtIngresoT;
    private javax.swing.JTextField txtTotalCompra;
    private javax.swing.JTextField txtTotalCompraC;
    private javax.swing.JTextField txtTotalGastos;
    private javax.swing.JTextField txtTotalIngreso;
    private javax.swing.JTextField txtTotalVentas;
    private javax.swing.JTextField txtTotalVentasC;
    // End of variables declaration//GEN-END:variables
}
