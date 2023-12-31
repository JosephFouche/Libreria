/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package libreria;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import javax.swing.table.TableModel;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author Eduardo
 */
public class AdministrarLibros extends javax.swing.JFrame {

    /**
     * Creates new form AdministrarLibros
     */
    
    String libroNombre, autor;
    int libroId, cantidad;
    DefaultTableModel model;
    public AdministrarLibros() {
        initComponents();
        detallesDeLibrosATablas();
    }
    //mandar los detalles de los libros a tabla
    public void detallesDeLibrosATablas(){
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/libreria_ms","root","");
            Statement st = (Statement) con.createStatement();
            ResultSet rs = st.executeQuery("select * from libro_detalles");
             
            while(rs.next()){//This loop iterates through the rows of the result set (rs). Each iteration represents a row in the database table.
                //Inside the loop, data from each column of the current row is extracted using methods like rs.getString and rs.getInt.
                
                String libroId  = rs.getString("libro_id");//libroId: Retrieves the value of the "libro_id" column as a string.
                String libroNombre  = rs.getString("libro_nombre");//libroNombre: Retrieves the value of the "libro_nombre" column as a string.
                String autor  = rs.getString("autor");//Retrieves the value of the "autor" column as a string.
                int cantidad  = rs.getInt("cantidad");//Retrieves the value of the "cantidad" column as an integer.
                
                /*The extracted values are then used to create an object array (Object[] obj), where each element of the array corresponds to 
                a column in the database table. 
                The order of elements in the array matches the order in which the columns were retrieved from the result set.*/
                Object [] obj = {libroId, libroNombre, autor, cantidad};
                model = (DefaultTableModel)tbl_DetallesLibros.getModel();
                model.addRow(obj);
                
                /*In summary, this code fetches details of books from a database table ("libro_detalles") 
                        and dynamically adds rows to a JTable (tbl_DetallesLibros) in a Swing GUI application.
                                Each row in the JTable represents a book, and the columns in the table correspond to
                                        the attributes of the books (e.g., libroId, libroNombre, autor, cantidad).*/
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    //agregar libros a tbl_DetallesLibros
    public boolean agregarLibro(){
        boolean estaAgregado = false;    
        libroId = Integer.parseInt(txt_LibroId.getText());
        libroNombre  = txt_LibroNombre.getText();
        autor  = txt_Autor.getText();
        cantidad = Integer.parseInt(txt_Cantidad.getText());
        
        try{
            Connection con = DBConnection.getConnection();
            String sql = "Insert into libro_detalles values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, libroId);
            pst.setString(2, libroNombre);
            pst.setString(3, autor);
            pst.setInt(4, cantidad);
            
            int rowCount = pst.executeUpdate();
            if(rowCount >0){
                estaAgregado = true;
            }else{
                estaAgregado = false;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return estaAgregado;
    }
    
    //actualizar registro de libros
    public boolean actualizarLibros(){
        
        boolean estaActualizado = false;
        libroId = Integer.parseInt(txt_LibroId.getText());
        libroNombre = txt_LibroNombre.getText();
        autor = txt_Autor.getText();
        cantidad = Integer.parseInt(txt_Cantidad.getText());
        
        try{
        
            Connection con = DBConnection.getConnection();
            String sql = "update libro_detalles set libro_nombre = ?, autor = ?, cantidad = ? where libro_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, libroNombre);
            pst.setString(2, autor);
            pst.setInt(3, cantidad);
            pst.setInt(4, libroId);
            
            int rowCount = pst.executeUpdate();
            
            if(rowCount >0){
                estaActualizado = true;
            }else{
                estaActualizado = false;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return estaActualizado;
    }
    
    //metodo para eliminar detalles de libro
    public boolean eliminarLibro(){
        boolean estaEliminado = false;
        libroId = Integer.parseInt(txt_LibroId.getText());
        
        try{
            Connection con = DBConnection.getConnection();
            String sql = "delete from libro_detalles where libro_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, libroId);
            
            int rowCount = pst.executeUpdate();
            if(rowCount >0){
                estaEliminado = true;
            }else{
                estaEliminado = false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return estaEliminado;
    }
    //metodo para limpiar tabla
    public void limpiarTabla(){
        
        DefaultTableModel model = (DefaultTableModel) tbl_DetallesLibros.getModel();
        model.setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_LibroId = new app.bolivia.swing.JCTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_LibroNombre = new app.bolivia.swing.JCTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_Autor = new app.bolivia.swing.JCTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_Cantidad = new app.bolivia.swing.JCTextField();
        rSMaterialButtonCircle1 = new necesario.RSMaterialButtonCircle();
        rSMaterialButtonCircle2 = new necesario.RSMaterialButtonCircle();
        rSMaterialButtonCircle3 = new necesario.RSMaterialButtonCircle();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_DetallesLibros = new rojerusan.RSTableMetro();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Rewind_48px.png"))); // NOI18N
        jLabel1.setText("Atrás");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 50));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N
        jLabel4.setText("Ingresar ID del Libro");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 210, 50));

        txt_LibroId.setBackground(new java.awt.Color(102, 102, 255));
        txt_LibroId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_LibroId.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txt_LibroId.setPlaceholder("Ingresar ID del Libro");
        txt_LibroId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_LibroIdFocusLost(evt);
            }
        });
        txt_LibroId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_LibroIdActionPerformed(evt);
            }
        });
        jPanel1.add(txt_LibroId, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, -1, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Moleskine_26px.png"))); // NOI18N
        jLabel9.setText("Ingresar nombre del Libro");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 260, 50));

        txt_LibroNombre.setBackground(new java.awt.Color(102, 102, 255));
        txt_LibroNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_LibroNombre.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txt_LibroNombre.setPlaceholder("Ingresar nombre del Libro");
        txt_LibroNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_LibroNombreActionPerformed(evt);
            }
        });
        jPanel1.add(txt_LibroNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, -1, -1));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N
        jLabel14.setText("Nombre del Autor");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 210, 50));

        txt_Autor.setBackground(new java.awt.Color(102, 102, 255));
        txt_Autor.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Autor.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txt_Autor.setPlaceholder("Nombre del Autor");
        txt_Autor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AutorActionPerformed(evt);
            }
        });
        jPanel1.add(txt_Autor, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, -1, -1));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Unit_26px.png"))); // NOI18N
        jLabel15.setText("Cantidad");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 210, 50));

        txt_Cantidad.setBackground(new java.awt.Color(102, 102, 255));
        txt_Cantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_Cantidad.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txt_Cantidad.setPlaceholder("Cantidad");
        txt_Cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CantidadActionPerformed(evt);
            }
        });
        jPanel1.add(txt_Cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 480, -1, -1));

        rSMaterialButtonCircle1.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle1.setText("Borrar");
        rSMaterialButtonCircle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 570, 120, -1));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle2.setText("Agregar");
        rSMaterialButtonCircle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle2ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 570, 150, -1));

        rSMaterialButtonCircle3.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle3.setText("Actualizar");
        rSMaterialButtonCircle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle3ActionPerformed(evt);
            }
        });
        jPanel1.add(rSMaterialButtonCircle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 570, 160, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 830));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(102, 102, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("X");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 60, 40));

        tbl_DetallesLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Libro ID", "Nombre", "Autor", "Cantidad"
            }
        ));
        tbl_DetallesLibros.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        tbl_DetallesLibros.setColorBordeFilas(new java.awt.Color(102, 102, 255));
        tbl_DetallesLibros.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_DetallesLibros.setColorSelBackgound(new java.awt.Color(255, 51, 51));
        tbl_DetallesLibros.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        tbl_DetallesLibros.setFuenteFilas(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tbl_DetallesLibros.setFuenteFilasSelect(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        tbl_DetallesLibros.setFuenteHead(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tbl_DetallesLibros.setRowHeight(40);
        tbl_DetallesLibros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_DetallesLibrosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_DetallesLibros);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 530, 250));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel3.setText("Administrar Libros");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 370, -1));

        jPanel5.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, 350, 5));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 0, 650, 820));

        setBounds(0, 0, 1228, 824);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        
        HomePage home = new HomePage();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void txt_LibroIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_LibroIdFocusLost
      
    }//GEN-LAST:event_txt_LibroIdFocusLost

    private void txt_LibroIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_LibroIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_LibroIdActionPerformed

    private void txt_LibroNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_LibroNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_LibroNombreActionPerformed

    private void txt_AutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_AutorActionPerformed

    private void txt_CantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_CantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_CantidadActionPerformed

    private void rSMaterialButtonCircle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2ActionPerformed
      if(agregarLibro()==true){
          JOptionPane.showMessageDialog(this, "Libro agregado");
          limpiarTabla();
          detallesDeLibrosATablas();
      }else{
          JOptionPane.showMessageDialog(this, "Adicion de libro fallido");
      }
    }//GEN-LAST:event_rSMaterialButtonCircle2ActionPerformed

    private void rSMaterialButtonCircle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle3ActionPerformed
        if(actualizarLibros()==true){
          JOptionPane.showMessageDialog(this, "Actualizado");
          limpiarTabla();
          detallesDeLibrosATablas();
      }else{
          JOptionPane.showMessageDialog(this, "Error al actualizar");
      }
    }//GEN-LAST:event_rSMaterialButtonCircle3ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
       System.exit(0);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void tbl_DetallesLibrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_DetallesLibrosMouseClicked
        
        int rowNo = tbl_DetallesLibros.getSelectedRow();
        TableModel model = tbl_DetallesLibros.getModel();
        txt_LibroId.setText(model.getValueAt(rowNo,0).toString());
        txt_LibroNombre.setText(model.getValueAt(rowNo,1).toString());
        txt_Autor.setText(model.getValueAt(rowNo,2).toString());
        txt_Cantidad.setText(model.getValueAt(rowNo,3).toString());
    }//GEN-LAST:event_tbl_DetallesLibrosMouseClicked

    private void rSMaterialButtonCircle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1ActionPerformed
        if(eliminarLibro()==true){
          JOptionPane.showMessageDialog(this, "Libro eliminado");
          limpiarTabla();
          detallesDeLibrosATablas();
      }else{
          JOptionPane.showMessageDialog(this, "Error al eliminar");
      }
    }//GEN-LAST:event_rSMaterialButtonCircle1ActionPerformed

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
            java.util.logging.Logger.getLogger(AdministrarLibros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdministrarLibros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdministrarLibros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdministrarLibros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdministrarLibros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle3;
    private rojerusan.RSTableMetro tbl_DetallesLibros;
    private app.bolivia.swing.JCTextField txt_Autor;
    private app.bolivia.swing.JCTextField txt_Cantidad;
    private app.bolivia.swing.JCTextField txt_LibroId;
    private app.bolivia.swing.JCTextField txt_LibroNombre;
    // End of variables declaration//GEN-END:variables
}
