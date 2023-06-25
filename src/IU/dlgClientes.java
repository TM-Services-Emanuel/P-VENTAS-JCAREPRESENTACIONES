package IU;

import Componentes.Mensajes;
import Componentes.Software;
import Componentes.clsExportarExcel;
import Controladores.CabecerasTablas;
import Controladores.controlCliente;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public final class dlgClientes extends javax.swing.JDialog {

    CabecerasTablas cabe = new CabecerasTablas();
    clsExportarExcel Export;
    public dlgClientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        titulo();
        cabe.cliente(tablaClientes);
        controlCliente.listClientes(tablaClientes, "clientes.idcliente");
        tablaClientes.getTableHeader().setReorderingAllowed(false);
        tablaClientes.getTableHeader().setResizingAllowed(false);
        cargarIcono();
        cbkBuscarActionPerformed(null);
    }
    
    final void titulo(){
        if(Software.getSoftware().equals("null")){
            this.setTitle("Gestionar clientes");
        }else{
            this.setTitle(Software.getSoftware()+" - Gestionar clientes");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        itemPModificarP = new javax.swing.JMenuItem();
        itemPEliminarP = new javax.swing.JMenuItem();
        Blanco = new org.edisoncor.gui.panel.PanelImage();
        Oscuro = new org.edisoncor.gui.panel.PanelImage();
        btnSalir1 = new newscomponents.RSButtonBigIcon_new();
        jPanel1 = new javax.swing.JPanel();
        btnNuevoP = new newscomponents.RSButtonBigIcon_new();
        btnModificarP = new newscomponents.RSButtonBigIcon_new();
        btnEliminarP = new newscomponents.RSButtonBigIcon_new();
        jPanel2 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        cbkBuscar = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        btnExportar = new newscomponents.RSButtonBigIcon_new();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowInddex, int celIndex)
            {
                return false;
            }
        };

        jPopupMenu1.setLabel("Opciones");

        itemPModificarP.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemPModificarP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editUser15.png"))); // NOI18N
        itemPModificarP.setText("     Modificar");
        itemPModificarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPModificarPActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemPModificarP);

        itemPEliminarP.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        itemPEliminarP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/deleteUser15 (2).png"))); // NOI18N
        itemPEliminarP.setText("     Eliminar");
        itemPEliminarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPEliminarPActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemPEliminarP);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        Blanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/fondoBlanco.jpg"))); // NOI18N
        Blanco.setPreferredSize(new java.awt.Dimension(690, 418));
        Blanco.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Oscuro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/fondo.png"))); // NOI18N
        Oscuro.setPreferredSize(new java.awt.Dimension(690, 418));

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

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(1, 5));

        btnNuevoP.setBackground(new java.awt.Color(17, 35, 46));
        btnNuevoP.setBorder(null);
        btnNuevoP.setText("NUEVO");
        btnNuevoP.setToolTipText("CREAR UN NUEVO CLIENTE");
        btnNuevoP.setBgHover(new java.awt.Color(39, 61, 75));
        btnNuevoP.setFocusPainted(false);
        btnNuevoP.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        btnNuevoP.setIconTextGap(0);
        btnNuevoP.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.ADD_BOX);
        btnNuevoP.setPixels(0);
        btnNuevoP.setSizeIcon(50.0F);
        btnNuevoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoPActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevoP);

        btnModificarP.setBackground(new java.awt.Color(17, 35, 46));
        btnModificarP.setBorder(null);
        btnModificarP.setText("MODIFICAR");
        btnModificarP.setToolTipText("MODIFICAR UN CLIENTE SELECCIONADO");
        btnModificarP.setBgHover(new java.awt.Color(39, 61, 75));
        btnModificarP.setFocusPainted(false);
        btnModificarP.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        btnModificarP.setIconTextGap(0);
        btnModificarP.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.EDIT);
        btnModificarP.setPixels(0);
        btnModificarP.setSizeIcon(50.0F);
        btnModificarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificarP);

        btnEliminarP.setBackground(new java.awt.Color(17, 35, 46));
        btnEliminarP.setBorder(null);
        btnEliminarP.setText("ELIMINAR");
        btnEliminarP.setToolTipText("ELIMINAR UN CLIENTE SELECCIONADO");
        btnEliminarP.setBgHover(new java.awt.Color(39, 61, 75));
        btnEliminarP.setFocusPainted(false);
        btnEliminarP.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        btnEliminarP.setIconTextGap(0);
        btnEliminarP.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.DELETE_FOREVER);
        btnEliminarP.setPixels(0);
        btnEliminarP.setSizeIcon(50.0F);
        btnEliminarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminarP);

        javax.swing.GroupLayout OscuroLayout = new javax.swing.GroupLayout(Oscuro);
        Oscuro.setLayout(OscuroLayout);
        OscuroLayout.setHorizontalGroup(
            OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OscuroLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 706, Short.MAX_VALUE)
                .addComponent(btnSalir1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        OscuroLayout.setVerticalGroup(
            OscuroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(OscuroLayout.createSequentialGroup()
                .addComponent(btnSalir1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 44, Short.MAX_VALUE))
        );

        Blanco.add(Oscuro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 988, 65));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setOpaque(false);

        txtBuscar.setEnabled(false);
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

        cbkBuscar.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        cbkBuscar.setSelected(true);
        cbkBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        cbkBuscar.setOpaque(false);
        cbkBuscar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        cbkBuscar.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        cbkBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkBuscarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/baseline_search_black_20.png"))); // NOI18N
        jLabel1.setText("BUSCADOR DE PRODUCTO/S");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbkBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 674, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbkBuscar)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );

        Blanco.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 65, -1, -1));

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
        Blanco.add(btnExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(964, 65, 24, 31));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        tablaClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tablaClientes.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaClientes.setGridColor(new java.awt.Color(255, 255, 255));
        tablaClientes.setRowHeight(20);
        tablaClientes.setShowGrid(true);
        tablaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaClientesMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaClientesMousePressed(evt);
            }
        });
        tablaClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaClientesKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaClientes);

        Blanco.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 98, 988, 465));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Blanco, javax.swing.GroupLayout.PREFERRED_SIZE, 988, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Blanco, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        /*if(txtBuscar.getText().length()> 0){   
            CabecerasTablas.limpiarTablas(tablaClientes);
            controlCliente.listClientes(tablaClientes, "clientes.idcliente");    
        }*/
        /*String cod = txtBuscar.getText();
        CabecerasTablas.limpiarTablas(tablaClientes);
        controlCliente.filtClientes(tablaClientes, cod);*/

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tablaClientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaClientesMousePressed
        // TODO add your handling code here:
        if(SwingUtilities.isRightMouseButton(evt))
        {
            Point p = evt.getPoint();
            int number = tablaClientes.rowAtPoint(p);
            ListSelectionModel modelos = tablaClientes.getSelectionModel();
            modelos.setSelectionInterval(number, number);
        }
    }//GEN-LAST:event_tablaClientesMousePressed

    private void itemPModificarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPModificarPActionPerformed
        // TODO add your handling code here:
        btnModificarPActionPerformed(null);
    }//GEN-LAST:event_itemPModificarPActionPerformed

    private void itemPEliminarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPEliminarPActionPerformed
        // TODO add your handling code here:
        btnEliminarPActionPerformed(null);
    }//GEN-LAST:event_itemPEliminarPActionPerformed

    private void tablaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaClientesMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2)
        {
            try {
                dlgGestClientes cli = new dlgGestClientes(null, true);
                cli.setLocationRelativeTo(null);
                controlCliente.aModificar();
                cli.setTitle("Modificación de Cliente");
                dlgGestClientes.btnModificar.setEnabled(true);
                dlgGestClientes.itemModificar.setEnabled(true);
                dlgGestClientes.btnGuardar.setEnabled(false);
                dlgGestClientes.itemGuardar.setEnabled(false);
                dlgGestClientes.btnNuevo.setEnabled(false);
                dlgGestClientes.itemNuevo.setEnabled(false);
                dlgGestClientes.btnCancelar.setEnabled(true);
                dlgGestClientes.itemCancelar.setEnabled(true);
                cli.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("No se pudo cargar información del Cliente");
        }
        }
    }//GEN-LAST:event_tablaClientesMouseClicked

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped
        // TODO add your handling code here:
        char c=evt.getKeyChar();
        if(Character.isLowerCase(c)){
            String cad=(""+c).toUpperCase();
            c=cad.charAt(0);
            evt.setKeyChar(c);
        }
        int limite=199;
        if (txtBuscar.getText().length()== limite)
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtBuscarKeyTyped

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String cod = txtBuscar.getText();
            if (cod.length() <= 0) {
                CabecerasTablas.limpiarTablas(tablaClientes);
                controlCliente.listClientes(tablaClientes, "clientes.idcliente");
                txtBuscar.requestFocus();
            } else {
                CabecerasTablas.limpiarTablas(tablaClientes);
                controlCliente.filtClientes(tablaClientes, cod);
                tablaClientes.requestFocus();
                int r=tablaClientes.getRowCount ();
                tablaClientes.changeSelection ( tablaClientes.getRowCount ()-r, 5, false, false );
            } 
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void tablaClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaClientesKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                dlgGestClientes cli = new dlgGestClientes(null, true);
                cli.setLocationRelativeTo(null);
                controlCliente.aModificar();
                cli.setTitle("Modificación de Cliente");
                dlgGestClientes.btnModificar.setEnabled(true);
                dlgGestClientes.itemModificar.setEnabled(true);
                dlgGestClientes.btnGuardar.setEnabled(false);
                dlgGestClientes.itemGuardar.setEnabled(false);
                dlgGestClientes.btnNuevo.setEnabled(false);
                dlgGestClientes.itemNuevo.setEnabled(false);
                dlgGestClientes.btnCancelar.setEnabled(true);
                dlgGestClientes.itemCancelar.setEnabled(true);
                cli.setVisible(true);
            } catch (Exception e) {
                Mensajes.informacion("No se pudo cargar información del Cliente" + e.getMessage());
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            txtBuscar.requestFocus();
            txtBuscar.selectAll();
        }
    }//GEN-LAST:event_tablaClientesKeyPressed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void cbkBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkBuscarActionPerformed
        // TODO add your handling code here:
        habilitarbusqueda();
    }//GEN-LAST:event_cbkBuscarActionPerformed

    private void btnNuevoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoPActionPerformed
        // TODO add your handling code here:
        dlgGestClientes cli = new dlgGestClientes(null, true);
        cli.setLocationRelativeTo(null);
        cli.setTitle("Agregar Cliente");
        cli.setVisible(true);
    }//GEN-LAST:event_btnNuevoPActionPerformed

    private void btnModificarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPActionPerformed
        // TODO add your handling code here:
        try {
            dlgGestClientes cli = new dlgGestClientes(null, true);
            cli.setLocationRelativeTo(null);
            controlCliente.aModificar();
            cli.setTitle("Modificación de Cliente");
            dlgGestClientes.btnModificar.setEnabled(true);
            dlgGestClientes.itemModificar.setEnabled(true);
            dlgGestClientes.btnGuardar.setEnabled(false);
            dlgGestClientes.itemGuardar.setEnabled(false);
            dlgGestClientes.btnNuevo.setEnabled(false);
            dlgGestClientes.itemNuevo.setEnabled(false);
            dlgGestClientes.btnCancelar.setEnabled(true);
            dlgGestClientes.itemCancelar.setEnabled(true);
            cli.setVisible(true);
        } catch (Exception e) {
            Mensajes.informacion("Seleccione un fila de la tabla");
        }
    }//GEN-LAST:event_btnModificarPActionPerformed

    private void btnEliminarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPActionPerformed
        // TODO add your handling code here:
        if (tablaClientes.getSelectedRow() < 0) {
            Mensajes.informacion("Seleccione un fila de la tabla");
        } else {
            try {
                int resp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar el registro?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (resp == JOptionPane.YES_OPTION) {
                    controlCliente.delCliente();
                    CabecerasTablas.limpiarTablas(tablaClientes);
                    controlCliente.listClientes(tablaClientes, "clientes.cli_codigo");
                }
            } catch (Exception e) {
                //Mensajes.informacion("Seleccione un fila de la tabla");
            }
        }
    }//GEN-LAST:event_btnEliminarPActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        // TODO add your handling code here:
        try {
            Export = new clsExportarExcel();
            Export.exportarExcel(tablaClientes);
        } catch (IOException ex) {
            Logger.getLogger(dlgClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnExportarActionPerformed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        // TODO add your handling code here:
        int rpta = Mensajes.confirmar("¿Seguro que desea salir del formulario?");
        if (rpta == 0) {
            this.dispose();
        }else{
            txtBuscar.requestFocus();
        }
    }//GEN-LAST:event_btnSalir1ActionPerformed

    void habilitarbusqueda(){
        if(cbkBuscar.isSelected()){
            txtBuscar.setEnabled(true);
            txtBuscar.requestFocus();
        }else{
            txtBuscar.setEnabled(false);
            txtBuscar.setText("");
            CabecerasTablas.limpiarTablas(tablaClientes);
            controlCliente.listClientes(tablaClientes, "clientes.idcliente");
        }
    }
    void cargarIcono() {
        try {
            java.awt.Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Iconos/logo1.png"));
            setIconImage(icon);
        } catch (Exception e) {
            Mensajes.error("No se pudo cargo icono");
        }
    }
    
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
            java.util.logging.Logger.getLogger(dlgClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            dlgClientes dialog = new dlgClientes(new javax.swing.JFrame(), true);
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
    public static newscomponents.RSButtonBigIcon_new btnEliminarP;
    public static newscomponents.RSButtonBigIcon_new btnExportar;
    public static newscomponents.RSButtonBigIcon_new btnModificarP;
    public static newscomponents.RSButtonBigIcon_new btnNuevoP;
    public static newscomponents.RSButtonBigIcon_new btnSalir1;
    private javax.swing.JCheckBox cbkBuscar;
    private javax.swing.JMenuItem itemPEliminarP;
    private javax.swing.JMenuItem itemPModificarP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablaClientes;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
