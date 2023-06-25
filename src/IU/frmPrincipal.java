package IU;

import Componentes.ConexionBD;
import Componentes.ReporteMovil;
import Componentes.Mensajes;
import Componentes.Reloj;
import Componentes.Software;
import Componentes.traerIP;
import Controladores.ControlLogeo;
import Datos.GestionarImagen;
import Modelo.Imagen;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mariadb.jdbc.MariaDbConnection;
import org.mariadb.jdbc.MariaDbStatement;

public final class frmPrincipal extends javax.swing.JFrame {
    
    public ReporteMovil jasper;

    public MariaDbStatement sentencia;
    public MariaDbConnection con;
    private ResultSet rs;
    
    public MariaDbStatement sentenciaMovil;
    public MariaDbConnection conMovil;
    private ResultSet rsMovil;
    private Dimension dim;
    
    public frmPrincipal() {
        
        initComponents();
        //dim=super.getToolkit().getScreenSize();
        //super.setSize(dim);
        this.setExtendedState(MAXIMIZED_BOTH);
        
        
        /*Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int ancho = (int)d.getWidth();
        int alto = (int)d.getHeight();
        this.setSize(ancho, alto);
        panelImage1.setSize(ancho, alto);*/
        try { 
            lbIp.setText("DIRECCIÓN IP : "+traerIP.getIP());
        } catch (Exception e) {
        }
        titulo();
        prepararBD();
        Iniciar();
        cargarIcono();
        cargarTapiz();
        informacionGral();
        mnNCProveedor.setVisible(false);
        mnPagoProveedor.setVisible(false);
        mnNCVenta.setVisible(false);
        mnGNCVenta.setVisible(false);
        lbversion.setText(Software.getVersion());
        jasper = new ReporteMovil();
    }
    
    void titulo(){
        if(Software.getSoftware().equals("null")){
            this.setTitle("Ventana principal");
        }else{
            this.setTitle("Ventana principal del sistema "+Software.getSoftware()+" - "+Software.getDescripcion());
        }
        if(Software.getVersion().equals("null")){
            lbversion.setText("©: No disponible");
        }else{
            lbversion.setText(Software.getVersion());
        }
    }

    private void Iniciar() {
        Reloj hilo = new Reloj(lblFecha);
        hilo.start();
    }

    void ubicacion() {
        int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setSize(ancho-100, alto-100);
        setLocationRelativeTo(null);
    }

    void cargarTapiz() {
        try {
            Imagen imagen = GestionarImagen.fondoPrincipal();
            String nombre = "/Recursos/" + imagen.getImgFondo();
            ((JPanelConFondo) panelFondo).setImagen(nombre);
        } catch (Exception e) {
            Mensajes.informacion("Error al cargar Fondo del Sistema.");
        }
    }

    void prepararBD() {
        try {
            con = (MariaDbConnection) new ConexionBD().getConexion();
            if (con == null) {
                System.out.println("No hay Conexion con la Base de Datos");
            } else {
                sentencia = (MariaDbStatement) con.createStatement();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            conMovil = (MariaDbConnection) new ConexionBD().getConexionMovil();
            if (conMovil == null) {
                System.out.println("No hay Conexion con la Base de Datos Movil");
            } else {
                sentenciaMovil = (MariaDbStatement) conMovil.createStatement();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void informacionGral() {
        try {
            rs = sentencia.executeQuery("select * from v_sucursal where suc_indicador='S'");
            rs.first();
            try {
                if (rs.getRow() != 0) {
                    //txtCod.setText(rs.getString(1));
                    lbEmpresa.setText(rs.getString("razon_social"));
                    lbRuc.setText(rs.getString("ruc"));
                    lbSucursal.setText(rs.getString("suc_nombre"));
                } else {
                    System.out.println("No se puede cargar Información Gral.");
                    lbEmpresa.setText("SIN ESPECIFICAR");
                    lbRuc.setText("SIN ESPECIFICAR");
                    lbSucursal.setText("SIN ESPECIFICAR");
                }
            } catch (SQLException ee) {
                System.out.println(ee.getMessage());
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelImage1 = new org.edisoncor.gui.panel.PanelImage();
        panelFondo = new JPanelConFondo();
        btnClientes = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnGestionarVentasMovil = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbEmpresa = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbSucursal = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbRuc = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbUsuario = new javax.swing.JLabel();
        lbPerfil = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel5 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JToolBar.Separator();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabel13 = new javax.swing.JLabel();
        lbversion = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jLabel15 = new javax.swing.JLabel();
        lbIp = new javax.swing.JLabel();
        btnSalir = new RSMaterialComponent.RSButtonMaterialIconUno();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        mbBarraMenu = new javax.swing.JMenuBar();
        mnSistema = new javax.swing.JMenu();
        itemFondo = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem52 = new javax.swing.JMenuItem();
        mnCalc = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        mnCerrarSistema = new javax.swing.JMenuItem();
        mnConfiguracion = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        mnMantenimiento = new javax.swing.JMenu();
        mnInformacion = new javax.swing.JMenu();
        itemEmpresa = new javax.swing.JMenuItem();
        itemSucursal = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        mnGTimbradoM = new javax.swing.JMenuItem();
        mnGPuntoEmisionM = new javax.swing.JMenuItem();
        jSeparator22 = new javax.swing.JPopupMenu.Separator();
        mnGCiudadM = new javax.swing.JMenuItem();
        mnGClasificacionM = new javax.swing.JMenuItem();
        mnGUMM = new javax.swing.JMenuItem();
        mnGImpuestoM = new javax.swing.JMenuItem();
        itemFamilia = new javax.swing.JMenuItem();
        itemLaboratorio = new javax.swing.JMenuItem();
        itemCiudades = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuItem51 = new javax.swing.JMenuItem();
        jMenuItem53 = new javax.swing.JMenuItem();
        S1 = new javax.swing.JPopupMenu.Separator();
        mnLogistica = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        mnEmpleados = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        S2 = new javax.swing.JPopupMenu.Separator();
        mnComision = new javax.swing.JMenuItem();
        mnProveedores = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        mnClientes = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        rpClientes = new javax.swing.JMenu();
        mnGProductosM2 = new javax.swing.JMenuItem();
        jMenuItem43 = new javax.swing.JMenuItem();
        jMenuItem44 = new javax.swing.JMenuItem();
        S3 = new javax.swing.JPopupMenu.Separator();
        mnSeguridad = new javax.swing.JMenu();
        smModUsuarios = new javax.swing.JMenuItem();
        smModUsuariosD = new javax.swing.JMenuItem();
        S4 = new javax.swing.JPopupMenu.Separator();
        itemExportar = new javax.swing.JMenuItem();
        itemImportar = new javax.swing.JMenuItem();
        divisor3 = new javax.swing.JMenu();
        mnCaja = new javax.swing.JMenu();
        mnIniciarCaja = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JPopupMenu.Separator();
        mnCierredeCaja = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        rpVendedores1 = new javax.swing.JMenu();
        itemTVentasv1 = new javax.swing.JMenuItem();
        itemTVentasv = new javax.swing.JMenuItem();
        divisor4 = new javax.swing.JMenu();
        mnGsIn = new javax.swing.JMenu();
        mnIngresosVarios = new javax.swing.JMenuItem();
        mnIngresosVarios1 = new javax.swing.JMenuItem();
        jSeparator19 = new javax.swing.JPopupMenu.Separator();
        mnGestGastos = new javax.swing.JMenuItem();
        jSeparator21 = new javax.swing.JPopupMenu.Separator();
        rpVendedores2 = new javax.swing.JMenu();
        itemRGL = new javax.swing.JMenuItem();
        itemRGR = new javax.swing.JMenuItem();
        itemRGA = new javax.swing.JMenuItem();
        divisor5 = new javax.swing.JMenu();
        mnProductos = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        rpArticulos = new javax.swing.JMenu();
        mnGProductosM1 = new javax.swing.JMenuItem();
        mnGProductosM3 = new javax.swing.JMenuItem();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem38 = new javax.swing.JMenuItem();
        jMenuItem39 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuItem40 = new javax.swing.JMenuItem();
        jMenuItem41 = new javax.swing.JMenuItem();
        mnCompras = new javax.swing.JMenu();
        jMenuItem30 = new javax.swing.JMenuItem();
        mnNCProveedor = new javax.swing.JMenuItem();
        mnPagoProveedor = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mnGC = new javax.swing.JMenuItem();
        rpCompras = new javax.swing.JMenu();
        mnVentas = new javax.swing.JMenu();
        jMenuItem23 = new javax.swing.JMenuItem();
        mnPresupuesto = new javax.swing.JMenuItem();
        mnNCVenta = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mnGV = new javax.swing.JMenuItem();
        mnGVM = new javax.swing.JMenuItem();
        mnGVE = new javax.swing.JMenuItem();
        mnGNCVenta = new javax.swing.JMenuItem();
        mnGPE = new javax.swing.JMenuItem();
        rpVentas = new javax.swing.JMenu();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem64 = new javax.swing.JMenuItem();
        jMenuItem65 = new javax.swing.JMenuItem();
        jMenuItem66 = new javax.swing.JMenuItem();
        mnTransferencias = new javax.swing.JMenu();
        itemGestionarTR = new javax.swing.JMenuItem();
        S8 = new javax.swing.JPopupMenu.Separator();
        rpVendedores3 = new javax.swing.JMenu();
        mnReparto = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jSeparator20 = new javax.swing.JPopupMenu.Separator();
        MitemRDC = new javax.swing.JMenuItem();
        S7 = new javax.swing.JPopupMenu.Separator();
        rpVendedores = new javax.swing.JMenu();
        jMenuItem50 = new javax.swing.JMenuItem();
        divisor1 = new javax.swing.JMenu();
        mnAyuda1 = new javax.swing.JMenu();
        jMenuItem19 = new javax.swing.JMenuItem();
        divisor2 = new javax.swing.JMenu();
        mnAyuda = new javax.swing.JMenu();
        jMenuItem17 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 255));
        setResizable(false);

        panelImage1.setBackground(new java.awt.Color(17, 35, 46));
        panelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/fondo.png"))); // NOI18N
        panelImage1.setPreferredSize(new java.awt.Dimension(690, 418));

        btnClientes.setBackground(new java.awt.Color(17, 35, 46));
        btnClientes.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Gestionar_clientes.png"))); // NOI18N
        btnClientes.setText("CLIENTES - F3");
        btnClientes.setBorderPainted(false);
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClientes.setFocusPainted(false);
        btnClientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnProductos.setBackground(new java.awt.Color(17, 35, 46));
        btnProductos.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Gestionar_Productos.png"))); // NOI18N
        btnProductos.setText("PRODUCTOS - F1");
        btnProductos.setBorderPainted(false);
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProductos.setFocusPainted(false);
        btnProductos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProductos.setIconTextGap(2);
        btnProductos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnProductos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnGestionarVentasMovil.setBackground(new java.awt.Color(17, 35, 46));
        btnGestionarVentasMovil.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnGestionarVentasMovil.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionarVentasMovil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Gestionar_Ventas.png"))); // NOI18N
        btnGestionarVentasMovil.setText("VENTAS MÓVIL");
        btnGestionarVentasMovil.setBorderPainted(false);
        btnGestionarVentasMovil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGestionarVentasMovil.setFocusPainted(false);
        btnGestionarVentasMovil.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGestionarVentasMovil.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnGestionarVentasMovil.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGestionarVentasMovil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarVentasMovilActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnGestionarVentasMovil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1142, Short.MAX_VALUE))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btnProductos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGestionarVentasMovil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(373, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/tm-services - copia.png"))); // NOI18N

        jSeparator10.setBackground(new java.awt.Color(179, 215, 236));
        jSeparator10.setForeground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(210, 229, 235));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("EMPRESA:");

        lbEmpresa.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        lbEmpresa.setText("SIN ESPECIFICAR");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("SUCURSAL:");

        lbSucursal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbSucursal.setForeground(new java.awt.Color(255, 255, 255));
        lbSucursal.setText("SIN ESPECIFICAR");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("R.U.C.");

        lbRuc.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbRuc.setForeground(new java.awt.Color(255, 255, 255));
        lbRuc.setText("SIN ESPECIFICAR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbRuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 14, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbEmpresa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lbRuc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbSucursal))
                .addGap(5, 5, 5))
        );

        jPanel2.setBackground(new java.awt.Color(210, 229, 235));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("NOMBRE:");

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuario.setText("SIN ESPECIFICAR");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("USUARIO:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("PERFIL:");

        lbUsuario.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lbUsuario.setText("SIN ESPECIFICAR");

        lbPerfil.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbPerfil.setForeground(new java.awt.Color(255, 255, 255));
        lbPerfil.setText("SIN ESPECIFICAR");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addComponent(lbUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbUsuario)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lbPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jSeparator17.setForeground(new java.awt.Color(39, 61, 75));

        jToolBar1.setBackground(new java.awt.Color(39, 61, 75));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jLabel5.setText("    ");
        jToolBar1.add(jLabel5);

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblFecha.setText("Fecha: ");
        jToolBar1.add(lblFecha);

        jLabel9.setText("   ");
        jToolBar1.add(jLabel9);
        jToolBar1.add(jSeparator18);

        jLabel10.setText("   ");
        jToolBar1.add(jLabel10);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("DATA BASE: P-VENTA - port: 3306");
        jToolBar1.add(jLabel8);

        jLabel12.setText("   ");
        jToolBar1.add(jLabel12);
        jToolBar1.add(jSeparator3);

        jLabel13.setText("   ");
        jToolBar1.add(jLabel13);

        lbversion.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbversion.setForeground(new java.awt.Color(255, 255, 255));
        lbversion.setText("Copyright ");
        jToolBar1.add(lbversion);

        jLabel14.setText("   ");
        jToolBar1.add(jLabel14);
        jToolBar1.add(jSeparator7);

        jLabel15.setText("   ");
        jToolBar1.add(jLabel15);

        lbIp.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbIp.setForeground(new java.awt.Color(255, 255, 255));
        lbIp.setText("ip");
        jToolBar1.add(lbIp);

        btnSalir.setBackground(new java.awt.Color(17, 35, 46));
        btnSalir.setText("CERRAR EL SISTEMA");
        btnSalir.setBackgroundHover(new java.awt.Color(17, 35, 46));
        btnSalir.setEffectButton(null);
        btnSalir.setForegroundIcon(new java.awt.Color(39, 61, 75));
        btnSalir.setForegroundText(new java.awt.Color(39, 61, 75));
        btnSalir.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.EXIT_TO_APP);
        btnSalir.setPositionIcon(rojeru_san.efectos.ValoresEnum.POSITIONICON.RIGHT);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("¡El mejor software de gestión empresarial");

        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/icon.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("La App de ventas en móviles de repartos");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("a tu alcance!");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 51, 0));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("+");

        javax.swing.GroupLayout panelImage1Layout = new javax.swing.GroupLayout(panelImage1);
        panelImage1.setLayout(panelImage1Layout);
        panelImage1Layout.setHorizontalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator10)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelImage1Layout.setVerticalGroup(
            panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addGroup(panelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImage1Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel17)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel20)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel19)
                        .addGap(19, 19, 19)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        mbBarraMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        mnSistema.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/menubuttonofthreelines_79781.png"))); // NOI18N
        mnSistema.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        mnSistema.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        itemFondo.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        itemFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/baseline_image_black_24.png"))); // NOI18N
        itemFondo.setText("Gestionar Fondo del Sistema");
        itemFondo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFondoActionPerformed(evt);
            }
        });
        mnSistema.add(itemFondo);
        mnSistema.add(jSeparator4);

        jMenuItem52.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/baseline_link_off_black_24.png"))); // NOI18N
        jMenuItem52.setText("Cerrar sesión");
        jMenuItem52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem52ActionPerformed(evt);
            }
        });
        mnSistema.add(jMenuItem52);

        mnCalc.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        mnCalc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/baseline_calculate_black_24.png"))); // NOI18N
        mnCalc.setText("Calculadora de windows");
        mnCalc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCalcActionPerformed(evt);
            }
        });
        mnSistema.add(mnCalc);
        mnSistema.add(jSeparator12);

        mnCerrarSistema.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        mnCerrarSistema.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        mnCerrarSistema.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/outline_exit_to_app_black_24.png"))); // NOI18N
        mnCerrarSistema.setText("Cerrar el sistema              ");
        mnCerrarSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCerrarSistemaActionPerformed(evt);
            }
        });
        mnSistema.add(mnCerrarSistema);

        mbBarraMenu.add(mnSistema);

        mnConfiguracion.setText("Configuración");
        mnConfiguracion.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        mnConfiguracion.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/setting15 (2).png"))); // NOI18N
        jMenuItem1.setText("Establecer impresora predeterminada");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mnConfiguracion.add(jMenuItem1);

        jMenuItem9.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/datalook15.png"))); // NOI18N
        jMenuItem9.setText("Información del Software");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        mnConfiguracion.add(jMenuItem9);

        mbBarraMenu.add(mnConfiguracion);

        mnMantenimiento.setText("Mantenimiento");
        mnMantenimiento.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        mnMantenimiento.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        mnInformacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/mantenimiento_25.png"))); // NOI18N
        mnInformacion.setText("Informaciones generales y auxiliares");
        mnInformacion.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        itemEmpresa.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_PC.png"))); // NOI18N
        itemEmpresa.setText("Gestionar empresa");
        itemEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemEmpresaActionPerformed(evt);
            }
        });
        mnInformacion.add(itemEmpresa);

        itemSucursal.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemSucursal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_PC.png"))); // NOI18N
        itemSucursal.setText("Gestionar sucursales");
        itemSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSucursalActionPerformed(evt);
            }
        });
        mnInformacion.add(itemSucursal);
        mnInformacion.add(jSeparator14);

        mnGTimbradoM.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGTimbradoM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_CEL.png"))); // NOI18N
        mnGTimbradoM.setText("Gestionar Timbrado");
        mnGTimbradoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGTimbradoMActionPerformed(evt);
            }
        });
        mnInformacion.add(mnGTimbradoM);

        mnGPuntoEmisionM.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGPuntoEmisionM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_CEL.png"))); // NOI18N
        mnGPuntoEmisionM.setText("Gestionar Punto de Emisión");
        mnGPuntoEmisionM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGPuntoEmisionMActionPerformed(evt);
            }
        });
        mnInformacion.add(mnGPuntoEmisionM);
        mnInformacion.add(jSeparator22);

        mnGCiudadM.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGCiudadM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_CEL.png"))); // NOI18N
        mnGCiudadM.setText("Gestionar Ciudades");
        mnGCiudadM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGCiudadMActionPerformed(evt);
            }
        });
        mnInformacion.add(mnGCiudadM);

        mnGClasificacionM.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGClasificacionM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_CEL.png"))); // NOI18N
        mnGClasificacionM.setText("Gestionar Clasificación");
        mnGClasificacionM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGClasificacionMActionPerformed(evt);
            }
        });
        mnInformacion.add(mnGClasificacionM);

        mnGUMM.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGUMM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_CEL.png"))); // NOI18N
        mnGUMM.setText("Gestionar Unidad de medida");
        mnGUMM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGUMMActionPerformed(evt);
            }
        });
        mnInformacion.add(mnGUMM);

        mnGImpuestoM.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGImpuestoM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_CEL.png"))); // NOI18N
        mnGImpuestoM.setText("Gestionar Impuesto");
        mnGImpuestoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGImpuestoMActionPerformed(evt);
            }
        });
        mnInformacion.add(mnGImpuestoM);

        itemFamilia.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemFamilia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_PC.png"))); // NOI18N
        itemFamilia.setText("Gestionar familias");
        itemFamilia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemFamiliaActionPerformed(evt);
            }
        });
        mnInformacion.add(itemFamilia);

        itemLaboratorio.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemLaboratorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_PC.png"))); // NOI18N
        itemLaboratorio.setText("Gestionar Marcas");
        itemLaboratorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemLaboratorioActionPerformed(evt);
            }
        });
        mnInformacion.add(itemLaboratorio);

        itemCiudades.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemCiudades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_PC.png"))); // NOI18N
        itemCiudades.setText("Gestionar mas Ciudades");
        itemCiudades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCiudadesActionPerformed(evt);
            }
        });
        mnInformacion.add(itemCiudades);

        jMenuItem35.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_PC.png"))); // NOI18N
        jMenuItem35.setText("Gestionar motivos");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        mnInformacion.add(jMenuItem35);
        mnInformacion.add(jSeparator9);

        jMenuItem51.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_PC.png"))); // NOI18N
        jMenuItem51.setText("Gestionar motivos de ingresos");
        jMenuItem51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem51ActionPerformed(evt);
            }
        });
        mnInformacion.add(jMenuItem51);

        jMenuItem53.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/MANT_PC.png"))); // NOI18N
        jMenuItem53.setText("Gestionar motivos de gastos");
        jMenuItem53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem53ActionPerformed(evt);
            }
        });
        mnInformacion.add(jMenuItem53);

        mnMantenimiento.add(mnInformacion);
        mnMantenimiento.add(S1);

        mnLogistica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/logistic_25.png"))); // NOI18N
        mnLogistica.setText("Puntos de Control & Logística");
        mnLogistica.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jMenuItem10.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/gestionarPC.png"))); // NOI18N
        jMenuItem10.setText("Gestionar  Puntos de Control & Logistica                           ");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        mnLogistica.add(jMenuItem10);

        mnMantenimiento.add(mnLogistica);

        mnEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleados_25.png"))); // NOI18N
        mnEmpleados.setText("Recurso Humano");
        mnEmpleados.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jMenuItem8.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleadoss_25.png"))); // NOI18N
        jMenuItem8.setText("Gestionar de Funcionarios");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        mnEmpleados.add(jMenuItem8);
        mnEmpleados.add(S2);

        mnComision.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnComision.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/comision_25.png"))); // NOI18N
        mnComision.setText("Comisiones");
        mnComision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnComisionActionPerformed(evt);
            }
        });
        mnEmpleados.add(mnComision);

        mnMantenimiento.add(mnEmpleados);

        mnProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/proveedores_25.png"))); // NOI18N
        mnProveedores.setText("Proveedores");
        mnProveedores.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem7.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleadoss_25.png"))); // NOI18N
        jMenuItem7.setText("Gestionar Proveedores                                    ");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        mnProveedores.add(jMenuItem7);

        mnMantenimiento.add(mnProveedores);

        mnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/clientes_25.png"))); // NOI18N
        mnClientes.setText("Clientes");
        mnClientes.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem6.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleadoss_25.png"))); // NOI18N
        jMenuItem6.setText("Gestionar Clientes                                  ");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        mnClientes.add(jMenuItem6);

        rpClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reports.png"))); // NOI18N
        rpClientes.setText("Generar Reportes");
        rpClientes.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        mnGProductosM2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGProductosM2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        mnGProductosM2.setText("Listado de Clientes");
        mnGProductosM2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGProductosM2ActionPerformed(evt);
            }
        });
        rpClientes.add(mnGProductosM2);

        jMenuItem43.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem43.setText("Artículos comprados por el cliente");
        rpClientes.add(jMenuItem43);

        jMenuItem44.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem44.setText("Ventas por cliente");
        rpClientes.add(jMenuItem44);

        mnClientes.add(rpClientes);

        mnMantenimiento.add(mnClientes);
        mnMantenimiento.add(S3);

        mnSeguridad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/seguridad_25.png"))); // NOI18N
        mnSeguridad.setText("Seguridad");
        mnSeguridad.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        smModUsuarios.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        smModUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/usuarios_25.png"))); // NOI18N
        smModUsuarios.setText("Gestionar usuarios");
        smModUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smModUsuariosActionPerformed(evt);
            }
        });
        mnSeguridad.add(smModUsuarios);

        smModUsuariosD.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        smModUsuariosD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/usuarios_25.png"))); // NOI18N
        smModUsuariosD.setText("Gestionar usuarios - Desarrollador");
        smModUsuariosD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smModUsuariosDActionPerformed(evt);
            }
        });
        mnSeguridad.add(smModUsuariosD);
        mnSeguridad.add(S4);

        itemExportar.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/backup_25 (1).png"))); // NOI18N
        itemExportar.setText("Generar respaldo (Backup)");
        itemExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemExportarActionPerformed(evt);
            }
        });
        mnSeguridad.add(itemExportar);

        itemImportar.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemImportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/restore_25.png"))); // NOI18N
        itemImportar.setText("Restaurar backup (Importar BD)");
        itemImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemImportarActionPerformed(evt);
            }
        });
        mnSeguridad.add(itemImportar);

        mnMantenimiento.add(mnSeguridad);

        mbBarraMenu.add(mnMantenimiento);

        divisor3.setText("|");
        divisor3.setEnabled(false);
        divisor3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        divisor3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        divisor3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        mbBarraMenu.add(divisor3);

        mnCaja.setText("Movimiento Diario");
        mnCaja.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        mnCaja.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        mnIniciarCaja.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnIniciarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/abrir - copia.png"))); // NOI18N
        mnIniciarCaja.setText("Inicializar Mov. Diario (Caja)");
        mnIniciarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnIniciarCajaActionPerformed(evt);
            }
        });
        mnCaja.add(mnIniciarCaja);
        mnCaja.add(jSeparator15);

        mnCierredeCaja.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnCierredeCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cerrar - copia.png"))); // NOI18N
        mnCierredeCaja.setText("Finalizar Mov. Diario (Cierre de caja)");
        mnCierredeCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCierredeCajaActionPerformed(evt);
            }
        });
        mnCaja.add(mnCierredeCaja);
        mnCaja.add(jSeparator2);

        rpVendedores1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reportex25.png"))); // NOI18N
        rpVendedores1.setText("Generar Reportes");
        rpVendedores1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        itemTVentasv1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemTVentasv1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        itemTVentasv1.setText("Mov. Diario (Cierre de caja)");
        itemTVentasv1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemTVentasv1ActionPerformed(evt);
            }
        });
        rpVendedores1.add(itemTVentasv1);

        itemTVentasv.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemTVentasv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        itemTVentasv.setText("Reporte total de ventas ");
        itemTVentasv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemTVentasvActionPerformed(evt);
            }
        });
        rpVendedores1.add(itemTVentasv);

        mnCaja.add(rpVendedores1);

        mbBarraMenu.add(mnCaja);

        divisor4.setText("|");
        divisor4.setEnabled(false);
        divisor4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        divisor4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        divisor4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        mbBarraMenu.add(divisor4);

        mnGsIn.setText("Gastos e Ingresos");
        mnGsIn.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        mnGsIn.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        mnIngresosVarios.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnIngresosVarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/AgregarIngresos - copia.png"))); // NOI18N
        mnIngresosVarios.setText("Registrar Ingresos y/o Cobranzas");
        mnIngresosVarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnIngresosVariosActionPerformed(evt);
            }
        });
        mnGsIn.add(mnIngresosVarios);

        mnIngresosVarios1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnIngresosVarios1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/GestionarIngresos - copia.png"))); // NOI18N
        mnIngresosVarios1.setText("Gestionar todos los Ingresos Diarios");
        mnIngresosVarios1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnIngresosVarios1ActionPerformed(evt);
            }
        });
        mnGsIn.add(mnIngresosVarios1);
        mnGsIn.add(jSeparator19);

        mnGestGastos.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGestGastos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/GestionarGastos - copia.png"))); // NOI18N
        mnGestGastos.setText("Gestionar todos los Gastos Diarios");
        mnGestGastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGestGastosActionPerformed(evt);
            }
        });
        mnGsIn.add(mnGestGastos);
        mnGsIn.add(jSeparator21);

        rpVendedores2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reportex25.png"))); // NOI18N
        rpVendedores2.setText("Generar Reportes");
        rpVendedores2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        itemRGL.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemRGL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        itemRGL.setText("Reporte de Gastos LOCAL");
        itemRGL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRGLActionPerformed(evt);
            }
        });
        rpVendedores2.add(itemRGL);

        itemRGR.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemRGR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        itemRGR.setText("Reporte de Gastos EN REPARTOS");
        itemRGR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRGRActionPerformed(evt);
            }
        });
        rpVendedores2.add(itemRGR);

        itemRGA.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemRGA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        itemRGA.setText("Reporte de Gastos ADMINISTRATIVOS");
        itemRGA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemRGAActionPerformed(evt);
            }
        });
        rpVendedores2.add(itemRGA);

        mnGsIn.add(rpVendedores2);

        mbBarraMenu.add(mnGsIn);

        divisor5.setText("|");
        divisor5.setEnabled(false);
        divisor5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        divisor5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        divisor5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        mbBarraMenu.add(divisor5);

        mnProductos.setText("Productos");
        mnProductos.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        mnProductos.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Gestionar_Productos_25.png"))); // NOI18N
        jMenuItem2.setText("Gestionar Productos  ");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        mnProductos.add(jMenuItem2);

        jMenuItem3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Transf 30.png"))); // NOI18N
        jMenuItem3.setText("Ajustar stock de Productos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        mnProductos.add(jMenuItem3);
        mnProductos.add(jSeparator1);

        jMenuItem4.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Salidaproductos_35.png"))); // NOI18N
        jMenuItem4.setText("Aplicar salida a Productos");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        mnProductos.add(jMenuItem4);

        jMenuItem5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/SalidaControl_35.png"))); // NOI18N
        jMenuItem5.setText("Controlar salidas aplicadas a Productos");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        mnProductos.add(jMenuItem5);

        rpArticulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reports.png"))); // NOI18N
        rpArticulos.setText("Generar Reportes");
        rpArticulos.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        mnGProductosM1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGProductosM1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        mnGProductosM1.setText("Listado de Productos Lácteos - Solo Precio de Venta");
        mnGProductosM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGProductosM1ActionPerformed(evt);
            }
        });
        rpArticulos.add(mnGProductosM1);

        mnGProductosM3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGProductosM3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        mnGProductosM3.setText("Listado de Productos Lácteos - Completo");
        mnGProductosM3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGProductosM3ActionPerformed(evt);
            }
        });
        rpArticulos.add(mnGProductosM3);

        jMenuItem37.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem37.setText("Listado de Artículos por Rubro");
        jMenuItem37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem37ActionPerformed(evt);
            }
        });
        rpArticulos.add(jMenuItem37);

        jMenuItem38.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem38.setText("Stock Valorizado");
        rpArticulos.add(jMenuItem38);

        jMenuItem39.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem39.setText("Stock Inicial");
        rpArticulos.add(jMenuItem39);
        rpArticulos.add(jSeparator8);

        jMenuItem40.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem40.setText("Salidas por Artículo");
        rpArticulos.add(jMenuItem40);

        jMenuItem41.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem41.setText("Salidas por Motivo");
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem41ActionPerformed(evt);
            }
        });
        rpArticulos.add(jMenuItem41);

        mnProductos.add(rpArticulos);

        mbBarraMenu.add(mnProductos);

        mnCompras.setText("Compras");
        mnCompras.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        mnCompras.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jMenuItem30.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItem30.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add_compra_25.png"))); // NOI18N
        jMenuItem30.setText("Registrar compras de proveedores");
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        mnCompras.add(jMenuItem30);

        mnNCProveedor.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnNCProveedor.setText("Notas de Crédito del Proveedor");
        mnNCProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnNCProveedorActionPerformed(evt);
            }
        });
        mnCompras.add(mnNCProveedor);

        mnPagoProveedor.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnPagoProveedor.setText("Pago a Proveedores");
        mnPagoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPagoProveedorActionPerformed(evt);
            }
        });
        mnCompras.add(mnPagoProveedor);
        mnCompras.add(jSeparator6);

        mnGC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        mnGC.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Gestionar_compras25.png"))); // NOI18N
        mnGC.setText("Gestionar todas las compras realizadas                                  ");
        mnGC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGCActionPerformed(evt);
            }
        });
        mnCompras.add(mnGC);

        rpCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reports.png"))); // NOI18N
        rpCompras.setText("Generar Reportes");
        rpCompras.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnCompras.add(rpCompras);

        mbBarraMenu.add(mnCompras);

        mnVentas.setText("Ventas");
        mnVentas.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        mnVentas.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jMenuItem23.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem23.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Realizar Ventas - 35.png"))); // NOI18N
        jMenuItem23.setText("Realizar ventas");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        mnVentas.add(jMenuItem23);

        mnPresupuesto.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnPresupuesto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ventapresupuestox40 - copia.png"))); // NOI18N
        mnPresupuesto.setText("Generar presupuestos");
        mnPresupuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPresupuestoActionPerformed(evt);
            }
        });
        mnVentas.add(mnPresupuesto);

        mnNCVenta.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnNCVenta.setText("  ");
        mnNCVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnNCVentaActionPerformed(evt);
            }
        });
        mnVentas.add(mnNCVenta);
        mnVentas.add(jSeparator5);

        mnGV.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        mnGV.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Gestionar_Ventas 35.png"))); // NOI18N
        mnGV.setText("Gestionar todas las ventas realizadas                                     ");
        mnGV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGVActionPerformed(evt);
            }
        });
        mnVentas.add(mnGV);

        mnGVM.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGVM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Gestionar_Ventas 35.png"))); // NOI18N
        mnGVM.setText("Gestionar todas las ventas móviles                                     ");
        mnGVM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGVMActionPerformed(evt);
            }
        });
        mnVentas.add(mnGVM);

        mnGVE.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGVE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/setting15 (3).png"))); // NOI18N
        mnGVE.setText("Extracto de cuentas - ventas crédito");
        mnGVE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGVEActionPerformed(evt);
            }
        });
        mnVentas.add(mnGVE);

        mnGNCVenta.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGNCVenta.setText("Consultar Notas de Crédito");
        mnGNCVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGNCVentaActionPerformed(evt);
            }
        });
        mnVentas.add(mnGNCVenta);

        mnGPE.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnGPE.setText("Gestionar presupuestos elaborados");
        mnGPE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGPEActionPerformed(evt);
            }
        });
        mnVentas.add(mnGPE);

        rpVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reports.png"))); // NOI18N
        rpVentas.setText("Generar Reportes");
        rpVentas.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jMenuItem26.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        jMenuItem26.setText("Resumen del Día");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        rpVentas.add(jMenuItem26);

        jMenuItem64.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        jMenuItem64.setText("Ventas del Día");
        jMenuItem64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem64ActionPerformed(evt);
            }
        });
        rpVentas.add(jMenuItem64);

        jMenuItem65.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        jMenuItem65.setText("Ventas por Artículo");
        jMenuItem65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem65ActionPerformed(evt);
            }
        });
        rpVentas.add(jMenuItem65);

        jMenuItem66.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        jMenuItem66.setText("Ventas entre fechas");
        jMenuItem66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem66ActionPerformed(evt);
            }
        });
        rpVentas.add(jMenuItem66);

        mnVentas.add(rpVentas);

        mbBarraMenu.add(mnVentas);

        mnTransferencias.setText("Transferencias");
        mnTransferencias.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        mnTransferencias.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        itemGestionarTR.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemGestionarTR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Transf 30.png"))); // NOI18N
        itemGestionarTR.setText("Gestionar todas las Transferencias realizadas");
        itemGestionarTR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGestionarTRActionPerformed(evt);
            }
        });
        mnTransferencias.add(itemGestionarTR);
        mnTransferencias.add(S8);

        rpVendedores3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reportex25.png"))); // NOI18N
        rpVendedores3.setText("Generar Reportes");
        rpVendedores3.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        mnTransferencias.add(rpVendedores3);

        mbBarraMenu.add(mnTransferencias);

        mnReparto.setText("Repartos");
        mnReparto.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        mnReparto.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jMenuItem18.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/GestRepartos_25.png"))); // NOI18N
        jMenuItem18.setText("Gestionar Repartos");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        mnReparto.add(jMenuItem18);
        mnReparto.add(jSeparator20);

        MitemRDC.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        MitemRDC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/billetera.png"))); // NOI18N
        MitemRDC.setText("Generar Recibo de Dinero - Pago de Comisiones");
        MitemRDC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MitemRDCActionPerformed(evt);
            }
        });
        mnReparto.add(MitemRDC);
        mnReparto.add(S7);

        rpVendedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reportex25.png"))); // NOI18N
        rpVendedores.setText("Generar Reportes");
        rpVendedores.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        jMenuItem50.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jMenuItem50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/List.png"))); // NOI18N
        jMenuItem50.setText("Reporte de Reparto por Móvil");
        jMenuItem50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem50ActionPerformed(evt);
            }
        });
        rpVendedores.add(jMenuItem50);

        mnReparto.add(rpVendedores);

        mbBarraMenu.add(mnReparto);

        divisor1.setText("|");
        divisor1.setEnabled(false);
        divisor1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        divisor1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        divisor1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        mbBarraMenu.add(divisor1);

        mnAyuda1.setText("Datos para Contabilidad");
        mnAyuda1.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        mnAyuda1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jMenuItem19.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/baseline_file_download_black_24.png"))); // NOI18N
        jMenuItem19.setText("Formulario de visualización y exportación.");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        mnAyuda1.add(jMenuItem19);

        mbBarraMenu.add(mnAyuda1);

        divisor2.setText("|");
        divisor2.setEnabled(false);
        divisor2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        divisor2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        divisor2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        mbBarraMenu.add(divisor2);

        mnAyuda.setText("Sistema");
        mnAyuda.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        mnAyuda.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jMenuItem17.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/baseline_help_black_24.png"))); // NOI18N
        jMenuItem17.setText("Información del Sistema");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        mnAyuda.add(jMenuItem17);

        mbBarraMenu.add(mnAyuda);

        setJMenuBar(mbBarraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImage1, javax.swing.GroupLayout.DEFAULT_SIZE, 1598, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImage1, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            dlgAjusteStock ajuste = new dlgAjusteStock(this, true);
            ajuste.setLocationRelativeTo(null);
            ajuste.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
       /* try {
            dlgSalidaMercaderia salida = new dlgSalidaMercaderia(this, true);
            salida.setLocationRelativeTo(null);
            salida.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
       //Mensajes.Sistema("La función: Aplicar salidas a artículos se encuentra bloqueada \nen estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            dlgConSalidas conSalidas = new dlgConSalidas(this, true);
            conSalidas.setLocationRelativeTo(null);
            conSalidas.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
        //Mensajes.Sistema("La función: Controlar salidas aplicadas a artículos se encuentra bloqueada \nen estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        //Mensajes.Sistema("Esta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        abrirClientes();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        //Mensajes.Sistema("Esta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        abrirProductosMoviles();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void mnCerrarSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCerrarSistemaActionPerformed
        // TODO add your handling code here:
        salir();
    }//GEN-LAST:event_mnCerrarSistemaActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        //abrirProveedor();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void itemLaboratorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemLaboratorioActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            dlgLaboratorio laboratorio = new dlgLaboratorio(this, true);
            laboratorio.setLocationRelativeTo(null);
            laboratorio.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_itemLaboratorioActionPerformed

    private void itemCiudadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCiudadesActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            dlgCiudad ciu = new dlgCiudad(this, true);
            ciu.setLocationRelativeTo(null);
            ciu.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_itemCiudadesActionPerformed

    private void itemFamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemFamiliaActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            dlgFamilia familia = new dlgFamilia(this, true);
            familia.setLocationRelativeTo(null);
            familia.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_itemFamiliaActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        try {
            dlgVendedor vend = new dlgVendedor(this, true);
            vend.setLocationRelativeTo(null);
            vend.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        //abrirCompras();
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
       /* try {
            dlgMotivo motivo = new dlgMotivo(this, true);
            motivo.setLocationRelativeTo(null);
            motivo.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_jMenuItem35ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:
       Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        //abrirFactura();
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void mnPresupuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPresupuestoActionPerformed
        // TODO add your handling code here:
        /*   dlgPresupuestos presupuesto = new dlgPresupuestos(this, false);
        presupuesto.setLocationRelativeTo(null);
        presupuesto.setVisible(true);*/
        Mensajes.Sistema("La función: Generar presupuestos de venta se encuentra bloqueada \nen estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_mnPresupuestoActionPerformed

    private void mnGVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGVActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*    try {
            dlgConsultarFacturas cf = new dlgConsultarFacturas(this, true);
            cf.setLocationRelativeTo(null);
            cf.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No hay conexión con el servidor");
        }*/
        //Mensajes.informacion("ESTA FUNCION ESTARA DISPONIBLE EN LA SIGUIENTE ACTUALIZACION");
    }//GEN-LAST:event_mnGVActionPerformed

    private void mnGPEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGPEActionPerformed
        // TODO add your handling code here:
        /*  try {
            dlgConsultarPresupuesto cp = new dlgConsultarPresupuesto(this, false);
            cp.setLocationRelativeTo(null);
            cp.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No hay conexión con el servidor");
        }*/
        Mensajes.Sistema("La función: Gestionar presupuestos elaborados se encuentra bloqueada \nen estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_mnGPEActionPerformed

    private void mnGNCVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGNCVentaActionPerformed
        // TODO add your handling code here:
        try {
            dlgConsNotaCredito cnc = new dlgConsNotaCredito(this, false);
            cnc.setLocationRelativeTo(null);
            cnc.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No hay conexión con el servidor");
        }
    }//GEN-LAST:event_mnGNCVentaActionPerformed

    private void mnNCVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnNCVentaActionPerformed
        // TODO add your handling code here:
        dlgNotasCredito nc = new dlgNotasCredito(this, true);
        nc.setLocationRelativeTo(null);
        nc.setVisible(true);
    }//GEN-LAST:event_mnNCVentaActionPerformed

    private void jMenuItem52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem52ActionPerformed
        // TODO add your handling code here:
        CerrarCesion();


    }//GEN-LAST:event_jMenuItem52ActionPerformed

    public void salir() {
        int rpta = Mensajes.confirmar("¿Seguro que desea salir del sistema?");
        if (rpta == 0) {
            String msg = ControlLogeo.desLogeo();
            System.exit(0);
            //this.dispose();

        }
    }

    public void CerrarCesion() {
        int rpta = Mensajes.confirmar("¿Seguro que desea Cerrar Sesión?");
        if (rpta == 0) {
            String msg = ControlLogeo.desLogeo();
            //System.exit(0);
            this.dispose();
            frmAcceso ac = new frmAcceso();
            ac.setLocationRelativeTo(null);
            ac.setVisible(true);

        }
    }
    
    void abrirImpresoras() {
        try {
            dlgImpresoras impre = new dlgImpresoras(this, true);
            impre.setLocationRelativeTo(null);
            impre.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }

    }

    void abrirFactura() {
        try {
            dlgVentas factura = new dlgVentas(this, false);
            factura.setLocationRelativeTo(this);
            factura.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }

    }

    void abrirCompras() {
        try {
            dlgCompras1 compras = new dlgCompras1(this, true);
            compras.setLocationRelativeTo(null);
            compras.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }

    }

    void abrirClientes() {
        try {
            dlgClientes clientes = new dlgClientes(this, true);
            //clientes.setSize(1000, 540);
            clientes.setLocationRelativeTo(null);
            clientes.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }
    
    void abrirCiudadMovil() {
        try {
            dlgCiudadMovil ciudadm = new dlgCiudadMovil(this, true);
            ciudadm.setLocationRelativeTo(null);
            ciudadm.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }
    
    void abrirClasificacionMovil() {
        try {
            dlgClasificacionMovil clasifM = new dlgClasificacionMovil(this, true);
            clasifM.setLocationRelativeTo(null);
            clasifM.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }
    
    void abrirTimbradoMovil() {
        try {
            dlgTimbradoMovil TimbradoM = new dlgTimbradoMovil(this, true);
            TimbradoM.setLocationRelativeTo(null);
            TimbradoM.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }
    
    void abrirPuntoEmisionMovil() {
        try {
            dlgPuntoEmisionMovil PPM = new dlgPuntoEmisionMovil(this, true);
            PPM.setLocationRelativeTo(null);
            PPM.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }
    
    void abrirUMMovil() {
        try {
            dlgUMMovil umM = new dlgUMMovil(this, true);
            umM.setLocationRelativeTo(null);
            umM.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }
    
    void abrirIVAMovil() {
        try {
            dlgIVAMovil ivaM = new dlgIVAMovil(this, true);
            ivaM.setLocationRelativeTo(null);
            ivaM.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }

    void abrirArticulos() {
        try {
            dlgArticulos articulo = new dlgArticulos(this, true);
            articulo.setLocationRelativeTo(null);
            articulo.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }
     void abrirProductosMoviles() {
        try {
            dlgArticulosMovil articulo = new dlgArticulosMovil(this, true);
            articulo.setLocationRelativeTo(null);
            articulo.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }
     
     void abrirTransferencias() {
        try {
            //dlgGestTransferencias transf = new dlgGestTransferencias(this, true);
            dlgTransferencias transf = new dlgTransferencias(this, true);
            transf.setLocationRelativeTo(null);
            transf.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }

    private void smModUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smModUsuariosActionPerformed
        // TODO add your handling code here:
        try {
            dlgGestUsuario gu = new dlgGestUsuario(this, true);
            gu.setLocationRelativeTo(null);
            gu.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }//GEN-LAST:event_smModUsuariosActionPerformed

    private void mnComisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnComisionActionPerformed
        // TODO add your handling code here:
        /*dlgComisionEmpleado ce = new dlgComisionEmpleado(this, true);
        ce.setLocationRelativeTo(null);
        ce.setVisible(true);*/
       Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_mnComisionActionPerformed

    private void mnGCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGCActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
            /*try {
            dlgConsultarCompras consCompras = new dlgConsultarCompras(this, true);
            consCompras.setLocationRelativeTo(null);
            consCompras.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_mnGCActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
       /* try {
            dlgReporteResumenDia r = new dlgReporteResumenDia(this, true);
            r.setLocationRelativeTo(null);
            r.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Sin registros");
        }*/
       Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void mnCierredeCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCierredeCajaActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            String fe = generarCodigos.getFecha("SELECT ca_fechainicio FROM caja where ca_indicador='S' ORDER BY ca_id DESC LIMIT 1");
            if (!fe.equals(Fecha.fechaCorrecta())) {
                Mensajes.informacion("La caja ya fue cerrada.\n\nPodra acceder a este formulario para visualizar los movimientos en la siguiente apertura de caja.\nLa apertura puede realizarse con los perfiles ADMINISTRADOR y VENTAS.");
            } else {
                dlgCajaDia cajaDia = new dlgCajaDia(this, false);
                cajaDia.setLocationRelativeTo(null);
                cajaDia.setVisible(true);
            }
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_mnCierredeCajaActionPerformed

    private void mnIngresosVariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnIngresosVariosActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            String fe = generarCodigos.getFecha("SELECT ca_fechainicio FROM caja where ca_indicador='S' ORDER BY ca_id DESC LIMIT 1");
            if (!fe.equals(Fecha.fechaCorrecta())) {
                Mensajes.informacion("La caja del día aún no ha sido inicializada.\n\nPara registrar cobranzas u otros ingresos sera necesario hacerlo.\nLa apertura puede realizarse con los perfiles ADMINISTRADOR y VENTAS.");
            } else {
                dlgIngreso ingreso = new dlgIngreso(this, true);
                ingreso.setLocationRelativeTo(null);
                ingreso.setVisible(true);
            }
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_mnIngresosVariosActionPerformed

    private void itemFondoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemFondoActionPerformed
        // TODO add your handling code here:
        try {
            dlgFondo fondo = new dlgFondo(this, true);
            fondo.setLocationRelativeTo(null);
            fondo.setVisible(true);
            //((JPanelConFondo) panelFondo).setImagen("/Recursos/imagen8.jpg");
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }//GEN-LAST:event_itemFondoActionPerformed

    private void jMenuItem37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem37ActionPerformed
        // TODO add your handling code here:
       /* GenerarReporte nr;
        nr = new GenerarReporte();
        nr.MostrarReporte(System.getProperty("user.dir") + "/Reportes/Articulos/ArticuloXRubro/RubroArticulo.jasper", "Reporte de Artículos por Rubro", "ArticuloXRubro.pdf");
        nr.cerrar();*/
    }//GEN-LAST:event_jMenuItem37ActionPerformed

    private void jMenuItem64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem64ActionPerformed
        // TODO add your handling code here:
        /*GenerarReporte gr;
        gr = new GenerarReporte();
        String fecha = Fecha.fechaCorrecta();
        gr.MostrarReporteConParametro2(System.getProperty("user.dir") + "/Reportes/Facturas/TotalFactura.jasper", "Factura de Ventas del Día", fecha);
        gr.cerrar();*/
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_jMenuItem64ActionPerformed

    private void jMenuItem65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem65ActionPerformed
        // TODO add your handling code here:
       /* String carpeta = "Facturas";
        String jasper = "VentasXArticulos";
        dlgFechasReportes fr = new dlgFechasReportes(carpeta, jasper);
        fr.setLocationRelativeTo(null);
        fr.setVisible(true);
        fr.setTitle("Reporte de ventas por artículo");*/
       Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_jMenuItem65ActionPerformed

    private void jMenuItem66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem66ActionPerformed
        // TODO add your handling code here:
        /*String carpeta = "Facturas";
        String jasper = "VentasEntreFechas";
        dlgFechasReportes fr = new dlgFechasReportes(carpeta, jasper);
        fr.setLocationRelativeTo(null);
        fr.setVisible(true);
        fr.setTitle("Reporte de ventas entre fechas");*/
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_jMenuItem66ActionPerformed

    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem41ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem41ActionPerformed

    private void itemEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemEmpresaActionPerformed
        // TODO add your handling code here:
        try {
            dlgEmpresa empresa = new dlgEmpresa(this, true);
            empresa.setLocationRelativeTo(null);
            empresa.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }//GEN-LAST:event_itemEmpresaActionPerformed

    private void itemSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSucursalActionPerformed
        // TODO add your handling code here:
        try {
            dlgSucursal sucursal = new dlgSucursal(this, true);
            sucursal.setLocationRelativeTo(null);
            sucursal.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }//GEN-LAST:event_itemSucursalActionPerformed

    private void itemExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemExportarActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_itemExportarActionPerformed

    private void itemImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemImportarActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_itemImportarActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        dlgAyuda a = new dlgAyuda(this, true);
        a.setLocationRelativeTo(null);
        a.setVisible(true);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void mnPagoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPagoProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnPagoProveedorActionPerformed

    private void mnNCProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnNCProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnNCProveedorActionPerformed

    private void mnIniciarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnIniciarCajaActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
       /* try {
            String fe = generarCodigos.getFecha("SELECT ca_fechainicio FROM caja WHERE ca_indicador='S' ORDER BY ca_id DESC LIMIT 1");
            if (!fe.equals(Fecha.fechaCorrecta())) {
                dlgCaja caja = new dlgCaja(this, true);
                caja.setLocationRelativeTo(null);
                caja.setVisible(true);
            } else {
                Mensajes.informacion("La caja ya fue inicializada.\n\nPuede comenzar a registrar compras o realizar ventas\nsin ningún inconveniente.");
            }
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_mnIniciarCajaActionPerformed

    private void jMenuItem51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem51ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            dlgDetalleIngreso detalleI = new dlgDetalleIngreso(this, true);
            detalleI.setLocationRelativeTo(null);
            detalleI.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_jMenuItem51ActionPerformed

    private void jMenuItem53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem53ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            dlgDetalleGasto detalleG = new dlgDetalleGasto(this, true);
            detalleG.setLocationRelativeTo(null);
            detalleG.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_jMenuItem53ActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        // TODO add your handling code here:
        //Mensajes.Sistema("Esta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        abrirClientes();
    }//GEN-LAST:event_btnClientesActionPerformed

    private void mnCalcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCalcActionPerformed
        // TODO add your handling code here:
        try        
    {
        Runtime rt = Runtime.getRuntime();           
        Process p = rt.exec("calc");            
        p.waitFor();        
    }        
    catch ( IOException | InterruptedException ioe )       
    {            
        ioe.getMessage();
    }
    }//GEN-LAST:event_mnCalcActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        abrirImpresoras();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void mnGVEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGVEActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("Esta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            dlgConsultarCreditos cc = new dlgConsultarCreditos(this, true);
            cc.setLocationRelativeTo(null);
            cc.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No hay conexión con el servidor");
        }*/
    }//GEN-LAST:event_mnGVEActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        dlgSoftware a = new dlgSoftware(this, true);
        a.setLocationRelativeTo(null);
        a.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void smModUsuariosDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smModUsuariosDActionPerformed
        // TODO add your handling code here:
        try {
            dlgGestUsuarioD gud = new dlgGestUsuarioD(this, true);
            gud.setLocationRelativeTo(null);
            gud.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }//GEN-LAST:event_smModUsuariosDActionPerformed

    private void mnGCiudadMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGCiudadMActionPerformed
        // TODO add your handling code here:
        abrirCiudadMovil();
    }//GEN-LAST:event_mnGCiudadMActionPerformed

    private void mnGClasificacionMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGClasificacionMActionPerformed
        // TODO add your handling code here:
        abrirClasificacionMovil();
    }//GEN-LAST:event_mnGClasificacionMActionPerformed

    private void mnGUMMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGUMMActionPerformed
        // TODO add your handling code here:
        abrirUMMovil();
    }//GEN-LAST:event_mnGUMMActionPerformed

    private void mnGImpuestoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGImpuestoMActionPerformed
        // TODO add your handling code here:
        abrirIVAMovil();
        
    }//GEN-LAST:event_mnGImpuestoMActionPerformed

    private void mnGTimbradoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGTimbradoMActionPerformed
        // TODO add your handling code here:
        abrirTimbradoMovil();
    }//GEN-LAST:event_mnGTimbradoMActionPerformed

    private void mnGProductosM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGProductosM1ActionPerformed
        // TODO add your handling code here:
        jasper.ListaProductosMovilesV("\\Reports\\articulos\\ListaProductos.jasper");
    }//GEN-LAST:event_mnGProductosM1ActionPerformed

    private void mnGProductosM2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGProductosM2ActionPerformed
        // TODO add your handling code here:
        jasper.ListaClientesMoviles("\\Reports\\clientes\\ListaClientes.jasper");
    }//GEN-LAST:event_mnGProductosM2ActionPerformed

    private void mnGPuntoEmisionMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGPuntoEmisionMActionPerformed
        // TODO add your handling code here:
        abrirPuntoEmisionMovil();
    }//GEN-LAST:event_mnGPuntoEmisionMActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
       /* try {
            dlgMovilesReparto mreparto = new dlgMovilesReparto(this, true);
            mreparto.setLocationRelativeTo(null);
            mreparto.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*String fe = generarCodigos.getFecha("SELECT ca_fechainicio FROM caja where ca_indicador='S' ORDER BY ca_id DESC LIMIT 1");
        if (!fe.equals(Fecha.fechaCorrecta())) {
            Mensajes.informacion("La caja del día aún no ha sido inicializada.\n\nPara poder comenzar a registrar los repartos sera necesario hacerlo.\nLa apertura puede realizarse con los perfiles ADMINISTRADOR y VENTAS.");
        } else {
            abrirRepartos();
        }*/
        
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void MitemRDCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MitemRDCActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            dlgReporteComisiones ReCom = new dlgReporteComisiones(this, false);
            ReCom.setLocationRelativeTo(this);
            ReCom.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_MitemRDCActionPerformed

    private void mnGProductosM3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGProductosM3ActionPerformed
        // TODO add your handling code here:
        jasper.ListaProductosMovilesH("\\Reports\\articulos\\ListaProductos_Completo.jasper");
    }//GEN-LAST:event_mnGProductosM3ActionPerformed

    private void jMenuItem50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem50ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
       /* try {
            dlgReporteGananciaReparto ReCom = new dlgReporteGananciaReparto(this, true);
            ReCom.setLocationRelativeTo(this);
            ReCom.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_jMenuItem50ActionPerformed

    private void itemTVentasvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemTVentasvActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            dlgReporteTotalVentas rsc = new dlgReporteTotalVentas(this, false);
            rsc.setLocationRelativeTo(null);
            rsc.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No hay conexión con el servidor");
        }*/
    }//GEN-LAST:event_itemTVentasvActionPerformed

    private void itemTVentasv1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemTVentasv1ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
            dlgReporteResumenCaja rsc = new dlgReporteResumenCaja(this, false);
            rsc.setLocationRelativeTo(null);
            rsc.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No hay conexión con el servidor");
        }*/
    }//GEN-LAST:event_itemTVentasv1ActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        // TODO add your handling code here:
        abrirProductosMoviles();
    }//GEN-LAST:event_btnProductosActionPerformed

    private void mnGestGastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGestGastosActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
       /* try {
                dlgGestGastos Ggastos = new dlgGestGastos(this, true);
                Ggastos.setLocationRelativeTo(null);
                Ggastos.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_mnGestGastosActionPerformed

    private void mnIngresosVarios1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnIngresosVarios1ActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_mnIngresosVarios1ActionPerformed

    private void itemRGLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRGLActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
       /* try {
                dlgReporteGastoLocal RG = new dlgReporteGastoLocal(this, true);
                RG.setLocationRelativeTo(null);
                RG.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_itemRGLActionPerformed

    private void itemRGRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRGRActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
                dlgReporteGastoReparto RG = new dlgReporteGastoReparto(this, true);
                RG.setLocationRelativeTo(null);
                RG.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_itemRGRActionPerformed

    private void itemRGAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemRGAActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*try {
                dlgReporteGastoAdministrativo RG = new dlgReporteGastoAdministrativo(this, true);
                RG.setLocationRelativeTo(null);
                RG.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }*/
    }//GEN-LAST:event_itemRGAActionPerformed

    private void itemGestionarTRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarTRActionPerformed
        // TODO add your handling code here:
        Mensajes.Sistema("RESTRICCIÓN DEL SISTEMA:\nEsta función se encuentra bloqueada en estos momentos.\nPara más información comuniquese con el proveedor del sistema.");
        /*String fe = generarCodigos.getFecha("SELECT ca_fechainicio FROM caja where ca_indicador='S' ORDER BY ca_id DESC LIMIT 1");
        if (!fe.equals(Fecha.fechaCorrecta())) {
            Mensajes.informacion("La caja del día aún no ha sido inicializada.\n\nPara poder comenzar a registrar los repartos sera necesario hacerlo.\nLa apertura puede realizarse con los perfiles ADMINISTRADOR y VENTAS.");
        } else {
            abrirTransferencias();
        }*/
        //Mensajes.Sistema("Esta función se encuentra bloqueada en estos momentos por motivos de desarrollo.\nPara más información comuniquese con el proveedor del sistema.");
    }//GEN-LAST:event_itemGestionarTRActionPerformed

    private void btnGestionarVentasMovilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionarVentasMovilActionPerformed
        // TODO add your handling code here:
        try {
            dlgConsultarFacturasMovil cf = new dlgConsultarFacturasMovil(this, true);
            cf.setLocationRelativeTo(null);
            cf.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No hay conexión con el servidor");
        }
    }//GEN-LAST:event_btnGestionarVentasMovilActionPerformed

    private void mnGVMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGVMActionPerformed
        // TODO add your handling code here:
        try {
            dlgConsultarFacturasMovil cf = new dlgConsultarFacturasMovil(this, true);
            cf.setLocationRelativeTo(null);
            cf.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No hay conexión con el servidor");
        }
    }//GEN-LAST:event_mnGVMActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        salir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
        dlgExportaciones a = new dlgExportaciones(this, true);
        a.setLocationRelativeTo(null);
        a.setVisible(true);
    }//GEN-LAST:event_jMenuItem19ActionPerformed
    void abrirProveedor() {
        try {
            dlgProveedores proveedor = new dlgProveedores(this, true);
            proveedor.setLocationRelativeTo(null);
            proveedor.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }
    void abrirRepartos() {
        try {
            dlgRepartos repartos = new dlgRepartos(null, true);
            repartos.setLocationRelativeTo(null);
            repartos.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Servidor no esta activo");
        }
    }

    void cargarIcono() {
        try {
            java.awt.Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Recursos/icon.png"));
            setIconImage(icon);
            setVisible(true);
            this.setLocationRelativeTo(null);
        } catch (Exception e) {
            Mensajes.error("No se pudo cargo el icono del sistema.");
        }
    }

    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
 /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new frmPrincipal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MitemRDC;
    public static javax.swing.JPopupMenu.Separator S1;
    public static javax.swing.JPopupMenu.Separator S2;
    public static javax.swing.JPopupMenu.Separator S3;
    public static javax.swing.JPopupMenu.Separator S4;
    public static javax.swing.JPopupMenu.Separator S7;
    public static javax.swing.JPopupMenu.Separator S8;
    public static javax.swing.JButton btnClientes;
    public static javax.swing.JButton btnGestionarVentasMovil;
    public static javax.swing.JButton btnProductos;
    public static RSMaterialComponent.RSButtonMaterialIconUno btnSalir;
    private javax.swing.JMenu divisor1;
    private javax.swing.JMenu divisor2;
    public static javax.swing.JMenu divisor3;
    public static javax.swing.JMenu divisor4;
    public static javax.swing.JMenu divisor5;
    private javax.swing.JMenuItem itemCiudades;
    private javax.swing.JMenuItem itemEmpresa;
    private javax.swing.JMenuItem itemExportar;
    private javax.swing.JMenuItem itemFamilia;
    public static javax.swing.JMenuItem itemFondo;
    private javax.swing.JMenuItem itemGestionarTR;
    private javax.swing.JMenuItem itemImportar;
    private javax.swing.JMenuItem itemLaboratorio;
    private javax.swing.JMenuItem itemRGA;
    private javax.swing.JMenuItem itemRGL;
    private javax.swing.JMenuItem itemRGR;
    private javax.swing.JMenuItem itemSucursal;
    private javax.swing.JMenuItem itemTVentasv;
    private javax.swing.JMenuItem itemTVentasv1;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem37;
    private javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    public static javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem43;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem50;
    private javax.swing.JMenuItem jMenuItem51;
    private javax.swing.JMenuItem jMenuItem52;
    private javax.swing.JMenuItem jMenuItem53;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem64;
    private javax.swing.JMenuItem jMenuItem65;
    private javax.swing.JMenuItem jMenuItem66;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator15;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JToolBar.Separator jSeparator18;
    private javax.swing.JPopupMenu.Separator jSeparator19;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator20;
    private javax.swing.JPopupMenu.Separator jSeparator21;
    private javax.swing.JPopupMenu.Separator jSeparator22;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    public static javax.swing.JLabel lbEmpresa;
    private javax.swing.JLabel lbIp;
    public static javax.swing.JLabel lbPerfil;
    public static javax.swing.JLabel lbRuc;
    public static javax.swing.JLabel lbSucursal;
    public static javax.swing.JLabel lbUsuario;
    private javax.swing.JLabel lblFecha;
    public static javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lbversion;
    private javax.swing.JMenuBar mbBarraMenu;
    public static javax.swing.JMenu mnAyuda;
    public static javax.swing.JMenu mnAyuda1;
    public static javax.swing.JMenu mnCaja;
    private javax.swing.JMenuItem mnCalc;
    private javax.swing.JMenuItem mnCerrarSistema;
    private javax.swing.JMenuItem mnCierredeCaja;
    public static javax.swing.JMenu mnClientes;
    public static javax.swing.JMenuItem mnComision;
    public static javax.swing.JMenu mnCompras;
    public static javax.swing.JMenu mnConfiguracion;
    public static javax.swing.JMenu mnEmpleados;
    private javax.swing.JMenuItem mnGC;
    private javax.swing.JMenuItem mnGCiudadM;
    private javax.swing.JMenuItem mnGClasificacionM;
    private javax.swing.JMenuItem mnGImpuestoM;
    public static javax.swing.JMenuItem mnGNCVenta;
    public static javax.swing.JMenuItem mnGPE;
    private javax.swing.JMenuItem mnGProductosM1;
    private javax.swing.JMenuItem mnGProductosM2;
    private javax.swing.JMenuItem mnGProductosM3;
    private javax.swing.JMenuItem mnGPuntoEmisionM;
    private javax.swing.JMenuItem mnGTimbradoM;
    private javax.swing.JMenuItem mnGUMM;
    private javax.swing.JMenuItem mnGV;
    public static javax.swing.JMenuItem mnGVE;
    private javax.swing.JMenuItem mnGVM;
    private javax.swing.JMenuItem mnGestGastos;
    public static javax.swing.JMenu mnGsIn;
    public static javax.swing.JMenu mnInformacion;
    private javax.swing.JMenuItem mnIngresosVarios;
    private javax.swing.JMenuItem mnIngresosVarios1;
    private javax.swing.JMenuItem mnIniciarCaja;
    public static javax.swing.JMenu mnLogistica;
    public static javax.swing.JMenu mnMantenimiento;
    public static javax.swing.JMenuItem mnNCProveedor;
    public static javax.swing.JMenuItem mnNCVenta;
    public static javax.swing.JMenuItem mnPagoProveedor;
    public static javax.swing.JMenuItem mnPresupuesto;
    public static javax.swing.JMenu mnProductos;
    public static javax.swing.JMenu mnProveedores;
    public static javax.swing.JMenu mnReparto;
    public static javax.swing.JMenu mnSeguridad;
    public static javax.swing.JMenu mnSistema;
    public static javax.swing.JMenu mnTransferencias;
    public static javax.swing.JMenu mnVentas;
    public static javax.swing.JPanel panelFondo;
    private org.edisoncor.gui.panel.PanelImage panelImage1;
    public static javax.swing.JMenu rpArticulos;
    public static javax.swing.JMenu rpClientes;
    public static javax.swing.JMenu rpCompras;
    public static javax.swing.JMenu rpVendedores;
    public static javax.swing.JMenu rpVendedores1;
    public static javax.swing.JMenu rpVendedores2;
    public static javax.swing.JMenu rpVendedores3;
    public static javax.swing.JMenu rpVentas;
    private javax.swing.JMenuItem smModUsuarios;
    public static javax.swing.JMenuItem smModUsuariosD;
    // End of variables declaration//GEN-END:variables
}
