package IU;

import Componentes.CustomFont;
import Componentes.Fecha;
import Componentes.Mensajes;
import Componentes.RenderDecimal2;
import Componentes.RenderDecimalconPuntos;
import Componentes.Software;
import Componentes.cargarComboBox;
import Componentes.validarCampos;
import Controladores.CabecerasTablas;
import Controladores.controlSalida;
import Datos.GestionarArticulosMovil;
import Datos.GestionarSalida;
import Modelo.ArticuloMovil;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

public class dlgSalidaMercaderia extends javax.swing.JDialog {

    CabecerasTablas cabe = new CabecerasTablas();
    static ArticuloMovil arti;
    CustomFont cf = new CustomFont();

    public dlgSalidaMercaderia(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        titulo();
        lblCodArt.setVisible(false);
        cabe.salidas(tbDetalle);
        cargarComboBox.cargar(cbMotivo, "SELECT * FROM motivo WHERE mot_indicador='S'");
        cargarComboBox.cargar(cbProveedor, "SELECT pro_codigo, pro_razonsocial, pro_indicador FROM proveedor WHERE pro_indicador='S'");
        cbMotivoActionPerformed(null);
        btnCancelarActionPerformed(null);
        invisible();
    }
    
    private void invisible(){
    btnQuitar.setVisible(false);
        btnAdd.setVisible(false);
        lblMotivo.setVisible(false);
        txtTotalL.setVisible(false);
        txtProveedor.setVisible(false);
        txtFechaR.setVisible(false);
    }
    
    
    final void titulo(){
        if(Software.getSoftware().equals("null")){
            this.setTitle("Aplicar salida a artículos");
        }else{
            this.setTitle(Software.getSoftware()+" - Aplicar salida a artículos");
        }
    }

    void limpiarCampos() {
        txtCod.setText("");
        dFecha.setText("");
        cbMotivo.setSelectedIndex(0);
        cbProveedor.setSelectedIndex(0);
        txtCant.setText("0.0");
        txtTotalL.setText("0");
        txtTotal.setText("0");
        txtObs.setText("");
        controlSalida.array.vaciar();

    }
    public static void cant() {
        int total = tbDetalle.getRowCount();
        etiCant.setText("Registros acumulados: " + String.valueOf(total));
    }
    
    public static void Renders() {
        dlgSalidaMercaderia.tbDetalle.getColumnModel().getColumn(5).setCellRenderer(new RenderDecimal2());
        dlgSalidaMercaderia.tbDetalle.getColumnModel().getColumn(6).setCellRenderer(new RenderDecimal2());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        Blanco = new org.edisoncor.gui.panel.PanelImage();
        Oscuro = new org.edisoncor.gui.panel.PanelImage();
        jPanel4 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        txtTotalL = new javax.swing.JTextField();
        lblMotivo = new javax.swing.JLabel();
        btnQuitar = new javax.swing.JButton();
        txtProveedor = new javax.swing.JTextField();
        txtFechaR = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lblSalida1 = new javax.swing.JLabel();
        dFecha = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtTotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCod = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbProveedor = new javax.swing.JComboBox<>();
        btnProveedor = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblCodArt = new javax.swing.JTextField();
        txtArt = new javax.swing.JTextField();
        cbMotivo = new javax.swing.JComboBox();
        txtCant = new javax.swing.JTextField();
        btnMotivo = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDetalle = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowInddex, int celIndex)
            {
                return false;
            }
        };
        etiCant = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itemNuevo = new javax.swing.JMenuItem();
        itemModificar = new javax.swing.JMenuItem();
        itemGuardar = new javax.swing.JMenuItem();
        itemCancelar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemBuscar = new javax.swing.JMenuItem();
        itemQuitar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        itemSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel2.setLayout(new java.awt.GridLayout(1, 3));

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

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Create.png"))); // NOI18N
        btnAdd.setToolTipText("Agregar a detalle");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        lblMotivo.setText("jLabel9");

        btnQuitar.setText("Quitar");
        btnQuitar.setToolTipText("Quitar un Renglón");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout OscuroLayout = new javax.swing.GroupLayout(Oscuro);
        Oscuro.setLayout(OscuroLayout);
        OscuroLayout.setHorizontalGroup(
            OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OscuroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalL, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(lblMotivo)
                .addGap(18, 18, 18)
                .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OscuroLayout.createSequentialGroup()
                        .addComponent(txtProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFechaR, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        OscuroLayout.setVerticalGroup(
            OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OscuroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(OscuroLayout.createSequentialGroup()
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(11, 11, 11)
                            .addComponent(txtTotalL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(OscuroLayout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addComponent(lblMotivo))
                        .addGroup(OscuroLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addGroup(OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtFechaR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(6, 6, 6)
                            .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setOpaque(false);

        lblSalida1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSalida1.setText("Operación N°");

        dFecha.setEditable(false);
        dFecha.setBackground(new java.awt.Color(255, 255, 255));
        dFecha.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        dFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel1.setText("Fecha Salida");

        jPanel5.setBackground(new java.awt.Color(17, 35, 46));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Digital-7 Mono", 1, 36)); // NOI18N
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
        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("SALIDA TOTAL");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTotal))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtCod.setEditable(false);
        txtCod.setBackground(new java.awt.Color(255, 255, 255));
        txtCod.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtCod.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodActionPerformed(evt);
            }
        });

        txtObs.setColumns(20);
        txtObs.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txtObs.setRows(2);
        txtObs.setAutoscrolls(false);
        txtObs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtObsKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObsKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(txtObs);

        jLabel2.setText("Observación");

        jLabel3.setText("Proveedor");

        cbProveedor.setBackground(new java.awt.Color(255, 255, 204));
        cbProveedor.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        cbProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProveedorActionPerformed(evt);
            }
        });
        cbProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbProveedorKeyPressed(evt);
            }
        });

        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Create.png"))); // NOI18N
        btnProveedor.setToolTipText("Gestionar Proveedor");
        btnProveedor.setBorderPainted(false);
        btnProveedor.setContentAreaFilled(false);
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSalida1)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cbProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(lblSalida1)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(cbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);

        lblCodArt.setEditable(false);
        lblCodArt.setBackground(new java.awt.Color(255, 255, 255));
        lblCodArt.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        lblCodArt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtArt.setEditable(false);
        txtArt.setBackground(new java.awt.Color(255, 255, 255));
        txtArt.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

        cbMotivo.setBackground(new java.awt.Color(255, 255, 204));
        cbMotivo.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        cbMotivo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMotivoItemStateChanged(evt);
            }
        });
        cbMotivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbMotivoMouseClicked(evt);
            }
        });
        cbMotivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMotivoActionPerformed(evt);
            }
        });
        cbMotivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbMotivoKeyPressed(evt);
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

        btnMotivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Create.png"))); // NOI18N
        btnMotivo.setToolTipText("Gestionar Motivo");
        btnMotivo.setBorderPainted(false);
        btnMotivo.setContentAreaFilled(false);
        btnMotivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMotivoActionPerformed(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("CANT");

        btnBuscar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(0, 0, 51));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Create.png"))); // NOI18N
        btnBuscar.setText("F9-Agregar");
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnBuscar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtArt, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnBuscar)
                        .addGap(98, 98, 98)
                        .addComponent(lblCodArt, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCant)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(lblCodArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtArt, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnMotivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCant))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setOpaque(false);

        tbDetalle.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
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
        ));
        tbDetalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbDetalle.setOpaque(false);
        tbDetalle.getTableHeader().setResizingAllowed(false);
        tbDetalle.getTableHeader().setReorderingAllowed(false);
        tbDetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetalleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDetalle);

        etiCant.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 11)); // NOI18N
        etiCant.setText("Registros acumulados:");

        javax.swing.GroupLayout BlancoLayout = new javax.swing.GroupLayout(Blanco);
        Blanco.setLayout(BlancoLayout);
        BlancoLayout.setHorizontalGroup(
            BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Oscuro, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
            .addGroup(BlancoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(BlancoLayout.createSequentialGroup()
                        .addComponent(etiCant, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        BlancoLayout.setVerticalGroup(
            BlancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BlancoLayout.createSequentialGroup()
                .addComponent(Oscuro, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiCant)
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

        itemBuscar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        itemBuscar.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        itemBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/search20.png"))); // NOI18N
        itemBuscar.setText("Buscar Artículo");
        itemBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBuscarActionPerformed(evt);
            }
        });
        jMenu1.add(itemBuscar);

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
        jMenu1.add(jSeparator3);

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
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Blanco, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Blanco, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbMotivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMotivoActionPerformed
        // TODO add your handling code here:
        if (cbMotivo.getSelectedIndex() == 0) {
            lblMotivo.setText("");
        } else {
            String id = cargarComboBox.getCodidgo(cbMotivo);
            lblMotivo.setText(id);
        }

    }//GEN-LAST:event_cbMotivoActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        arti = GestionarArticulosMovil.busArticulo(lblCodArt.getText());
        try {
            if (txtArt.getText().isEmpty()) {
                Mensajes.informacion("Seleccione un Articulo");
                btnBuscar.doClick();
                //btnBuscar.requestFocus();
            } else if (lblMotivo.getText().isEmpty()) {
                Mensajes.informacion("Seleccione un Motivo");
                cbMotivo.requestFocus();
            } else if (Double.parseDouble(txtCant.getText()) <=0) {
                Mensajes.error("Ingrese un numero mayor a 0");
                txtCant.requestFocus();
                txtCant.selectAll();
            }else if (Double.parseDouble(txtCant.getText()) > arti.getStock()) {
                Mensajes.informacion("Stock Insuficiente");
                txtCant.setText(String.valueOf(arti.getStock()));
                txtCant.requestFocus();
                txtCant.selectAll();
            } else {
                controlSalida.addTabla(tbDetalle);
                
                lblCodArt.setText("");
                txtArt.setText("");
                txtCant.setText("");
                cbMotivo.setSelectedIndex(0);
                cant();
                btnMotivo.setEnabled(false);
                cbMotivo.setEnabled(false);
                txtCant.setEnabled(false);
                btnBuscar.doClick();
                //Renders();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        // TODO add your handling code here:
        try {
            controlSalida.delRenglon(tbDetalle);
            cant();
            Renders();
        } catch (Exception e) {
            Mensajes.error("Seleccione una fila de la tabla");
        }
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        int rpta = Mensajes.confirmar("¿Seguro que desea salir del formulario?");
        if (rpta == 0) {
            controlSalida.canCelar();
            this.dispose();
        }

    }//GEN-LAST:event_btnSalirActionPerformed

    private void tbDetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetalleMouseClicked
        // TODO add your handling code here:
        /*int fila = tbDetalle.getSelectedRow();
        String des = tbDetalle.getValueAt(fila, 1).toString();
        String cant = tbDetalle.getValueAt(fila, 4).toString();
        txtCant.setText(cant);
        txtArt.setText(des);*/
    }//GEN-LAST:event_tbDetalleMouseClicked

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        //limpiarCampos();
        String cod = GestionarSalida.getCodigo();
        txtCod.setText(cod);
        dFecha.setText(Fecha.fechaCorrectaFachada());
        txtFechaR.setText(Fecha.fechaCorrecta());
        cargarComboBox.cargar(cbMotivo, "SELECT * FROM motivo WHERE mot_indicador='S'");
        btnNuevo.setEnabled(false);
        itemNuevo.setEnabled(false);
        btnModificar.setEnabled(false);
        itemModificar.setEnabled(false);
        btnGuardar.setEnabled(true);
        itemGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        itemCancelar.setEnabled(true);
        itemBuscar.setEnabled(true);
        btnBuscar.setEnabled(true);
        itemQuitar.setEnabled(true);
        cbProveedor.setEnabled(true);
        btnProveedor.setEnabled(true);
        txtObs.setEnabled(true);
        tbDetalle.clearSelection();

        cbProveedor.requestFocus();
        cbProveedor.setPopupVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        /*    if (validarCampos.estaVacio(txtDescripcion)&& validarCampos.estaVacio(txtCostoL) && validarCampos.estaVacio(txtPrecioPublicoL)&& validarCampos.estaVacio(txtPrecioVentaL)
            && validarCampos.estaVacio(txtCodLab)&& validarCampos.estaVacio(txtCodPro)&& validarCampos.estaVacio(txtCodFam)
            && validarCampos.estaVacio(txtStock)&& validarCampos.estaVacio(txtStockMin)){
            try{
                int resp = JOptionPane.showConfirmDialog(this,"¿Seguro que desea modificar el registro?", "Modificar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (resp == JOptionPane.YES_OPTION){
                    controlArticulo.actArticulo();
                    limpiarCampos();
                    CabecerasTablas.limpiarTablas(tbProductos);
                    controlArticulo.listArticulo(tbProductos, "cod");
                    cant();
                    this.dispose();
                }
            }catch(Exception ee){System.out.println(ee.getMessage());}
        } else {
            Mensajes.informacion("Debe llenar obligatoriamente los campos señalados");
        }*/
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if(txtProveedor.getText().trim().isEmpty()){
            Mensajes.error("Seleccione un Proveedor");
            cbProveedor.requestFocus();
        }else if(txtObs.getText().trim().isEmpty()){
            Mensajes.error("Ingrese Observación para mayor información de la Salida");
            txtObs.requestFocus();
        }else if (tbDetalle.getRowCount() == 0) {
            Mensajes.error("Tabla de salida vacio, ingrese al menos un artículo");
            btnBuscar.doClick();
        } else {
            int resp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea guardar el registro?", "Guardar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (resp == JOptionPane.YES_OPTION) {
                try {
                    String cod = GestionarSalida.getCodigo();
                    txtCod.setText(cod);
                    controlSalida.addSalida();
                    //controlSalida.addDetalleSalida();
                    //controlSalida.actStock();
                    btnCancelarActionPerformed(null);
                } catch (Exception e) {
                    Mensajes.error("Error al Guardar: " + e.getMessage());
                }
            }

        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        btnNuevo.setEnabled(true);
        itemNuevo.setEnabled(true);
        btnModificar.setEnabled(false);
        itemModificar.setEnabled(false);
        btnGuardar.setEnabled(false);
        itemGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        itemCancelar.setEnabled(false);
        itemBuscar.setEnabled(false);
        btnBuscar.setEnabled(false);
        itemQuitar.setEnabled(false);
        btnMotivo.setEnabled(false);
        cbMotivo.setEnabled(false);
        dFecha.setEnabled(false);
        txtCod.setEnabled(false);
        txtCant.setEnabled(false);
        cbProveedor.setEnabled(false);
        btnProveedor.setEnabled(false);
        txtObs.setEnabled(false);
        //tbDetalle.clearSelection();
        cabe.salidas(tbDetalle);
        cant();
        btnNuevo.requestFocus();
    }//GEN-LAST:event_btnCancelarActionPerformed

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

    private void itemBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBuscarActionPerformed
        // TODO add your handling code here:
       /* try {
            dlgBuscarArticulo art = new dlgBuscarArticulo(null, true);
            art.setLocationRelativeTo(null);
            art.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No hay conexión con el servidor");
        }*/
       btnBuscarActionPerformed(null);
    }//GEN-LAST:event_itemBuscarActionPerformed

    private void itemQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemQuitarActionPerformed
        // TODO add your handling code here:
        btnQuitarActionPerformed(null);
    }//GEN-LAST:event_itemQuitarActionPerformed

    private void txtCantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantKeyPressed
        // TODO add your handling code here:
        validarCampos.soloDecimales(txtCant);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnAdd.doClick();
        }
    }//GEN-LAST:event_txtCantKeyPressed

    private void btnMotivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMotivoActionPerformed
        // TODO add your handling code here:
        dlgMotivo la = new dlgMotivo(null, true);
        la.setLocationRelativeTo(null);
        la.setVisible(true);
    }//GEN-LAST:event_btnMotivoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        try {
            dlgBuscarArticulo art = new dlgBuscarArticulo(null, true);
            art.setLocationRelativeTo(null);
            art.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No hay conexión con el servidor");
        }
       
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtTotalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyReleased
        // TODO add your handling code here:
        if (txtTotal.getText().trim().length() >= 1) {
            DecimalFormat df = new DecimalFormat("#,###");
            txtTotal.setText(df.format(Integer.valueOf(txtTotal.getText().trim().replace(".", "").replace(",", ""))));
        }
    }//GEN-LAST:event_txtTotalKeyReleased

    private void txtCantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantActionPerformed

    private void cbMotivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbMotivoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                txtCant.requestFocus();
                txtCant.selectAll();
        }
    }//GEN-LAST:event_cbMotivoKeyPressed

    private void cbMotivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbMotivoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMotivoMouseClicked

    private void cbMotivoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMotivoItemStateChanged
        // TODO add your handling code here
    }//GEN-LAST:event_cbMotivoItemStateChanged

    private void txtCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        // TODO add your handling code here:
        dlgProveedores pro = new dlgProveedores(null, true);
        pro.setSize(1230, 570);
        pro.setLocationRelativeTo(null);
        pro.setVisible(true);
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void cbProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProveedorActionPerformed
        // TODO add your handling code here:
        if (cbProveedor.getSelectedIndex() == 0) {
            txtProveedor.setText("");
        } else {
            cbProveedor.setPopupVisible(true);
            String id = cargarComboBox.getCodidgo(cbProveedor);
            txtProveedor.setText(String.valueOf(id));
        }
    }//GEN-LAST:event_cbProveedorActionPerformed

    private void txtObsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObsKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtObsKeyTyped

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalirActionPerformed
        // TODO add your handling code here:
        btnSalirActionPerformed(null);
    }//GEN-LAST:event_itemSalirActionPerformed

    private void cbProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbProveedorKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                txtObs.requestFocus();
        }
    }//GEN-LAST:event_cbProveedorKeyPressed

    private void txtObsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObsKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                btnBuscar.doClick();
        }
    }//GEN-LAST:event_txtObsKeyPressed

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
            java.util.logging.Logger.getLogger(dlgSalidaMercaderia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            dlgSalidaMercaderia dialog = new dlgSalidaMercaderia(new javax.swing.JFrame(), true);
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
    public static javax.swing.JButton btnAdd;
    public static javax.swing.JButton btnBuscar;
    public static javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnGuardar;
    public static javax.swing.JButton btnModificar;
    public static javax.swing.JButton btnMotivo;
    public static javax.swing.JButton btnNuevo;
    public static javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnSalir;
    public static javax.swing.JComboBox cbMotivo;
    public static javax.swing.JComboBox<String> cbProveedor;
    public static javax.swing.JTextField dFecha;
    public static javax.swing.JLabel etiCant;
    private javax.swing.JMenuItem itemBuscar;
    private javax.swing.JMenuItem itemCancelar;
    private javax.swing.JMenuItem itemGuardar;
    private javax.swing.JMenuItem itemModificar;
    private javax.swing.JMenuItem itemNuevo;
    private javax.swing.JMenuItem itemQuitar;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    public static javax.swing.JTextField lblCodArt;
    public static javax.swing.JLabel lblMotivo;
    public static javax.swing.JLabel lblSalida1;
    public static javax.swing.JTable tbDetalle;
    public static javax.swing.JTextField txtArt;
    public static javax.swing.JTextField txtCant;
    public static javax.swing.JTextField txtCod;
    public static javax.swing.JTextField txtFechaR;
    public static javax.swing.JTextArea txtObs;
    public static javax.swing.JTextField txtProveedor;
    public static javax.swing.JTextField txtTotal;
    public static javax.swing.JTextField txtTotalL;
    // End of variables declaration//GEN-END:variables
}
