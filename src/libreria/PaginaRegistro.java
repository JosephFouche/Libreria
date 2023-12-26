
package libreria;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Eduardo
 */
public class PaginaRegistro extends javax.swing.JFrame {

    /**
     * Creates new form PaginaRegistro
     */
    public PaginaRegistro() {
        initComponents();
    }
//metodo para insertar valores en la tabla usuarios
    public void insertarRegistroDetalles(){
        String name = txt_user.getText();
        String pwd = txt_password.getText();
        String email = txt_email.getText();
        String contact = txt_contact.getText();
        
        
        try{
            Connection con = DBConnection.getConnection();
            String sql = "insert into usuarios(nombre, contraseña,correo,contacto) values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            
            pst.setString(1,name);
            pst.setString(2,pwd);
            pst.setString(3,email);
            pst.setString(4,contact);
            
            int updatedRowCount  = pst.executeUpdate();
            
            if(updatedRowCount > 0){
                JOptionPane.showMessageDialog(this, "Registrado con exito");
            }else{
                JOptionPane.showMessageDialog(this, "Registrado fallido");
            }
            
        }catch(Exception e){
          e.printStackTrace();
        }
    }
    
    //validación
    public boolean validarRegistro(){
        
        String name = txt_user.getText();
        String pwd = txt_password.getText();
        String email = txt_email.getText();
        String contact = txt_contact.getText();
        
        if(name.equals("")){
            JOptionPane.showMessageDialog(this, "Porfavor ingresar usuario");
            return false;
        
        }
         if(pwd.equals("")){
            JOptionPane.showMessageDialog(this, "Porfavor ingresar contraseña");
            return false;
        
        }
          if(email.equals("")|| !email.matches("^.+@.+\\..+$")){
            JOptionPane.showMessageDialog(this, "Porfavor ingresar mail valido ");
            return false;
        
        }
           if(contact.equals("")){
            JOptionPane.showMessageDialog(this, "Porfavor numero de contacto");
            return false;
        
        }
        return true;
    }
    // verificar usuarios repetidos
    public boolean verificaUsuarioDuplicado(){
        
        String name = txt_user.getText();
        boolean isExist = false;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/libreria_ms","root","");
            
            PreparedStatement pst = con.prepareStatement("select * from usuarios where nombre = ?");
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                isExist = true;
            }else{
                isExist = false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return isExist;
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_user = new app.bolivia.swing.JCTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_password = new app.bolivia.swing.JCTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jCTextField3 = new app.bolivia.swing.JCTextField();
        jLabel12 = new javax.swing.JLabel();
        jCTextField4 = new app.bolivia.swing.JCTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_email = new app.bolivia.swing.JCTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_contact = new app.bolivia.swing.JCTextField();
        rSMaterialButtonCircle1 = new necesario.RSMaterialButtonCircle();
        rSMaterialButtonCircle2 = new necesario.RSMaterialButtonCircle();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("Desarrollador");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 140, 30));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 255));
        jLabel2.setText("Poli Biblioteca");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 180, 30));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("Unico");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 50, 30));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/icons/signup-library-icon.png"))); // NOI18N
        jLabel5.setText("jLabel5");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 580, 410));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 51, 51));
        jLabel6.setText("Bienvenido");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 150, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 580));

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/icons/icons8_Account_50px.png"))); // NOI18N
        jLabel4.setText("Usuario: ");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 210, 50));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("X");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 30, 30));

        txt_user.setBackground(new java.awt.Color(102, 102, 255));
        txt_user.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_user.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txt_user.setPlaceholder("Ingrese Usuario Aquí");
        txt_user.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_userFocusLost(evt);
            }
        });
        txt_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_userActionPerformed(evt);
            }
        });
        jPanel2.add(txt_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Crear cuenta nueva");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 210, 30));

        txt_password.setBackground(new java.awt.Color(102, 102, 255));
        txt_password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_password.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txt_password.setPlaceholder("Ingrese Contraseña aquí");
        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });
        jPanel2.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/icons/icons8_Secure_50px.png"))); // NOI18N
        jLabel9.setText("Contraseña:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 210, 50));

        jPanel3.setBackground(new java.awt.Color(102, 102, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/icons/icons8_Account_50px.png"))); // NOI18N
        jLabel10.setText("Usuario: ");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 210, 50));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Registrarse");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 130, 30));

        jCTextField3.setBackground(new java.awt.Color(102, 102, 255));
        jCTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jCTextField3.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jCTextField3.setPlaceholder("Ingrese Usuario Aquí");
        jCTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCTextField3ActionPerformed(evt);
            }
        });
        jPanel3.add(jCTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Crear cuenta nueva");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 210, 30));

        jCTextField4.setBackground(new java.awt.Color(102, 102, 255));
        jCTextField4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        jCTextField4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jCTextField4.setPlaceholder("Ingrese Usuario Aquí");
        jCTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCTextField4ActionPerformed(evt);
            }
        });
        jPanel3.add(jCTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, -1));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/icons/icons8_Account_50px.png"))); // NOI18N
        jLabel13.setText("Usuario: ");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 210, 50));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 300, 550));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/icons/icons8_Secured_Letter_50px.png"))); // NOI18N
        jLabel14.setText("Correo Electrónico:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 210, 50));

        txt_email.setBackground(new java.awt.Color(102, 102, 255));
        txt_email.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_email.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txt_email.setPlaceholder("Ingrese Correo Aquí");
        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });
        jPanel2.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/icons/icons8_Google_Mobile_50px.png"))); // NOI18N
        jLabel15.setText("Contacto:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 210, 50));

        txt_contact.setBackground(new java.awt.Color(102, 102, 255));
        txt_contact.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_contact.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txt_contact.setPlaceholder("Ingrese número Aquí");
        txt_contact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_contactActionPerformed(evt);
            }
        });
        jPanel2.add(txt_contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, -1, -1));

        rSMaterialButtonCircle1.setBackground(new java.awt.Color(0, 153, 255));
        rSMaterialButtonCircle1.setText("Acceder");
        jPanel2.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 250, 50));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle2.setText("Registrarse");
        rSMaterialButtonCircle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle2ActionPerformed(evt);
            }
        });
        jPanel2.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 250, 50));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Registrarse");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 130, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 0, 300, 580));

        setSize(new java.awt.Dimension(921, 579));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_userActionPerformed

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void jCTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCTextField3ActionPerformed

    private void jCTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCTextField4ActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void txt_contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactActionPerformed

    private void rSMaterialButtonCircle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2ActionPerformed
       if(validarRegistro()== true){
          if(verificaUsuarioDuplicado()==false){
              insertarRegistroDetalles();
          }else{
              JOptionPane.showMessageDialog(this, "Nombre de usuario ya existe ");
          }
        
       }
    }//GEN-LAST:event_rSMaterialButtonCircle2ActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void txt_userFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_userFocusLost
        if(verificaUsuarioDuplicado()==true){
            
            
            JOptionPane.showMessageDialog(this, "Nombre de usuario ya existe ");
        
        }
    }//GEN-LAST:event_txt_userFocusLost

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
            java.util.logging.Logger.getLogger(PaginaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaginaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaginaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaginaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaginaRegistro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.bolivia.swing.JCTextField jCTextField3;
    private app.bolivia.swing.JCTextField jCTextField4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
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
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private app.bolivia.swing.JCTextField txt_contact;
    private app.bolivia.swing.JCTextField txt_email;
    private app.bolivia.swing.JCTextField txt_password;
    private app.bolivia.swing.JCTextField txt_user;
    // End of variables declaration//GEN-END:variables
}
