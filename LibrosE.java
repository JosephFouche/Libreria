/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package libreria;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.Date;
import javax.swing.JOptionPane;
/**
 *
 * @author Eduardo
 */
public class LibrosE extends javax.swing.JFrame {

    /**
     * Creates new form LibrosE
     */
    public LibrosE() {
        initComponents();
    }
    //buscar detalles de libros de la base de datos y mostrar en el panel de detalles de libros
    public void libroDetalles(){
        
        int libroId = Integer.parseInt(txt_IDLibro.getText());
        
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from libro_detalles where libro_id =?");
            pst.setInt(1,libroId);
            ResultSet rs = pst.executeQuery();
            
            
            if(rs.next()){
                lbl_libroId1.setText(rs.getString("libro_id"));
                lbl_nombreLibro1.setText(rs.getString("libro_nombre"));
                lbl_autor1.setText(rs.getString("autor"));
                lbl_cantidad1.setText(rs.getString("cantidad"));
            }else{
                lbl_errorLibro.setText("Id de libro invalido");
            } 
            
        }catch(Exception e ){
            e.printStackTrace();
        }
        
    }
        //buscar detalles de estudiantes de la base de datos y mostrar en el panel de detalles de estudiantes
    public void estudianteDetalles(){
        
        int estudianteId = Integer.parseInt(txt_EstudianteID.getText());
        
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from detalles_estudiante where id_estudiante =?");
            pst.setInt(1,estudianteId);
            ResultSet rs = pst.executeQuery();
            
            
            if(rs.next()){
               lbl_IdEstudiante.setText(rs.getString("id_estudiante"));
                lbl_nombreEstudiante.setText(rs.getString("nombre"));
                lbl_Curso.setText(rs.getString("curso"));
                lbl_Turno.setText(rs.getString("turno"));
            }else{
                lbl_estudiantError.setText("Id de Estudiante invalido");
            }
            
        }catch(Exception e ){
            e.printStackTrace();
        }
        
    }
    
    //insertar detalles de libros prestados a base de datos
    public boolean libroPrestado(){
        boolean estaPrestado = false;
        int libroId = Integer.parseInt(txt_IDLibro.getText());
        int estudianteId = Integer.parseInt(txt_EstudianteID.getText());
        String libroNombre = lbl_nombreLibro1.getText();
        String estudianteNombre = lbl_nombreEstudiante.getText();
        
        Date uFechaPrestamo = date_Prestamo.getDatoFecha();
        Date uFechaDevolucion = date_Devolucion.getDatoFecha();
        
        long l1 = uFechaPrestamo.getTime();
        long l2 = uFechaDevolucion.getTime();
        
        java.sql.Date sFechaPrestamo = new java.sql.Date(l1);
        java.sql.Date sDevolucion = new java.sql.Date(l2);
        
        try{
            Connection con = DBConnection.getConnection();
            String sql = "insert into libros_prestados_detalles(libro_id, libro_nombre, estudiante_id, estudiante_nombre, "
                    + "libro_prestado, devolucion, status) values(?,?,?,?,?,?,?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, libroId);
            pst.setString(2, libroNombre);
            pst.setInt(3, estudianteId);
            pst.setString(4, estudianteNombre);
            pst.setDate(5, sFechaPrestamo);
            pst.setDate(6, sDevolucion);
            pst.setString(7, "Pendiente");
            
            int rowCount = pst.executeUpdate();
            
            if(rowCount > 0){
                estaPrestado = true;
                
            }else{
                estaPrestado = false;
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return estaPrestado;
        
    }
    //actualizar conteo de libros
    public void actualizarLibro(){
    
        int libroID = Integer.parseInt(txt_IDLibro.getText());
        
        try{
            Connection con = DBConnection.getConnection();
            String sql = "update libros_prestados_detalles set cantidad = cantidad -1 where libro_id =? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, libroID);
            
            int rowCount = pst.executeUpdate();
            
            if( rowCount > 0){
                JOptionPane.showMessageDialog(this," conteo de libro actualizado");
                int conteoInicial = Integer.parseInt(lbl_cantidad1.getText());
                lbl_cantidad1.setText(Integer.toString(conteoInicial-1));
            }else{
                
                JOptionPane.showMessageDialog(this,"no se puede actualizar");
            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
//revisar si libro ya esta prestado o no
    
    public boolean yaestaPrestado(){
    
        boolean yaestaPrestado = false;
        int libroId = Integer.parseInt(txt_IDLibro.getText());
        int estudianteId = Integer.parseInt(txt_EstudianteID.getText());
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select * from libros_prestados_detalles where libro_id = ? and estudiante_id = ? and status = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, libroId);
            pst.setInt(2, estudianteId);
            pst.setString(3, "Pendiente");
            
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                yaestaPrestado = true;
                
            }else{
                yaestaPrestado = false;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return yaestaPrestado;
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_main = new javax.swing.JPanel();
        panel_main1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        lbl_cantidad1 = new javax.swing.JLabel();
        lbl_libroId1 = new javax.swing.JLabel();
        lbl_nombreLibro1 = new javax.swing.JLabel();
        lbl_autor1 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lbl_errorLibro = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_EstudianteID = new app.bolivia.swing.JCTextField();
        jLabel5 = new javax.swing.JLabel();
        date_Prestamo = new rojeru_san.componentes.RSDateChooser();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        date_Devolucion = new rojeru_san.componentes.RSDateChooser();
        rSMaterialButtonCircle2 = new necesario.RSMaterialButtonCircle();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lbl_Turno = new javax.swing.JLabel();
        lbl_IdEstudiante = new javax.swing.JLabel();
        lbl_nombreEstudiante = new javax.swing.JLabel();
        lbl_Curso = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lbl_estudiantError = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txt_IDLibro = new app.bolivia.swing.JCTextField();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_main.setBackground(new java.awt.Color(255, 255, 255));
        panel_main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_main1.setBackground(new java.awt.Color(255, 255, 255));
        panel_main1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(255, 51, 51));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(102, 102, 255));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Rewind_48px.png"))); // NOI18N
        jLabel9.setText("Atrás");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 28, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 60));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Literature_100px_1.png"))); // NOI18N
        jLabel10.setText("Detalles de Libros");
        jPanel11.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 290, 120));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 390, 5));

        lbl_cantidad1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_cantidad1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel11.add(lbl_cantidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 420, 190, 30));

        lbl_libroId1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_libroId1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel11.add(lbl_libroId1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 190, 30));

        lbl_nombreLibro1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_nombreLibro1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel11.add(lbl_nombreLibro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, 190, 30));

        lbl_autor1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_autor1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel11.add(lbl_autor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 190, 30));

        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Id de Libro:");
        jPanel11.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 190, 30));

        jLabel26.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Nombre de Libro:");
        jPanel11.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 190, 30));

        jLabel27.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Autor: ");
        jPanel11.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 190, 30));

        lbl_errorLibro.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_errorLibro.setForeground(new java.awt.Color(255, 255, 51));
        jPanel11.add(lbl_errorLibro, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 470, 190, 30));

        jLabel32.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Cantidad");
        jPanel11.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 190, 30));

        panel_main1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 470, 670));

        jLabel11.setBackground(new java.awt.Color(255, 51, 51));
        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 51, 51));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel11.setText("Libro Prestado");
        panel_main1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 20, 350, 120));

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel12.setText("X");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        panel_main1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 0, -1, -1));

        jLabel29.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 51, 51));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N
        jLabel29.setText("Día de Prestamo: ");
        panel_main1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 300, 180, 50));

        txt_EstudianteID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 51, 51)));
        txt_EstudianteID.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txt_EstudianteID.setPlaceholder("Ingresar ID del Estudiante");
        txt_EstudianteID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_EstudianteIDFocusLost(evt);
            }
        });
        txt_EstudianteID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_EstudianteIDActionPerformed(evt);
            }
        });
        panel_main1.add(txt_EstudianteID, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 220, 160, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 51, 51));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N
        jLabel5.setText("ID del Estudiante:");
        panel_main1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 220, 170, 50));

        date_Prestamo.setColorBackground(new java.awt.Color(255, 51, 51));
        date_Prestamo.setColorForeground(new java.awt.Color(255, 51, 51));
        panel_main1.add(date_Prestamo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 300, 200, -1));

        jLabel30.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 51, 51));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N
        jLabel30.setText("ID del Libro:");
        panel_main1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 150, 170, 50));

        jLabel31.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 51, 51));
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N
        jLabel31.setText("Devolución:");
        panel_main1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 390, 170, 50));

        date_Devolucion.setColorBackground(new java.awt.Color(255, 51, 51));
        date_Devolucion.setColorForeground(new java.awt.Color(255, 51, 51));
        panel_main1.add(date_Devolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 390, 210, -1));

        rSMaterialButtonCircle2.setBackground(new java.awt.Color(255, 51, 51));
        rSMaterialButtonCircle2.setText("Prestar");
        rSMaterialButtonCircle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle2ActionPerformed(evt);
            }
        });
        panel_main1.add(rSMaterialButtonCircle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 480, 350, -1));

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 390, 5));

        lbl_Turno.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_Turno.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_Turno, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 420, 190, 30));

        lbl_IdEstudiante.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_IdEstudiante.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_IdEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 190, 30));

        lbl_nombreEstudiante.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_nombreEstudiante.setForeground(new java.awt.Color(255, 255, 255));
        lbl_nombreEstudiante.setText(" ");
        jPanel1.add(lbl_nombreEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, 190, 30));

        lbl_Curso.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_Curso.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lbl_Curso, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, 190, 30));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Id de Estudiante:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 190, 30));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Nombre del Estudiante: ");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 190, 30));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Curso: ");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 190, 30));

        lbl_estudiantError.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_estudiantError.setForeground(new java.awt.Color(255, 255, 51));
        jPanel1.add(lbl_estudiantError, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 460, 190, 30));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/AddNewBookIcons/icons8_Student_Registration_100px_2.png"))); // NOI18N
        jLabel3.setText("Detalles de Estudiante");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 350, 120));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Turno:");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 190, 30));

        panel_main1.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 450, 670));

        txt_IDLibro.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 51, 51)));
        txt_IDLibro.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        txt_IDLibro.setPlaceholder("Ingresar ID del Libro");
        txt_IDLibro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_IDLibroFocusLost(evt);
            }
        });
        txt_IDLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_IDLibroActionPerformed(evt);
            }
        });
        panel_main1.add(txt_IDLibro, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 150, 160, -1));

        jPanel2.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        panel_main1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 120, -1, 10));

        panel_main.add(panel_main1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1390, 680));

        getContentPane().add(panel_main, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1390, 680));

        setBounds(0, 0, 1400, 700);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_IDLibroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IDLibroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IDLibroActionPerformed

    private void txt_IDLibroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_IDLibroFocusLost
           if(!txt_IDLibro.getText().equals("")){
           libroDetalles();
           } 
       
    }//GEN-LAST:event_txt_IDLibroFocusLost

    private void rSMaterialButtonCircle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle2ActionPerformed
           
        
        if(lbl_cantidad1.getText().equals("0")){
            JOptionPane.showMessageDialog(this, "este libro no esta disponible");
            
        }else{
            if (yaestaPrestado() == false){
                if(libroPrestado()== true){
                    JOptionPane.showMessageDialog(this, "Libro prestado exitosamente");
                    actualizarLibro();
                }else{
                    JOptionPane.showMessageDialog(this, "no se puede prestar el libro");
                }
            }else{
                JOptionPane.showMessageDialog(this, "el estudiante ya tiene este libro");
            }
        }
    }//GEN-LAST:event_rSMaterialButtonCircle2ActionPerformed

    private void txt_EstudianteIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_EstudianteIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_EstudianteIDActionPerformed

    private void txt_EstudianteIDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_EstudianteIDFocusLost
       
        if(!txt_EstudianteID.getText().equals("")){
           estudianteDetalles();
        } 
    }//GEN-LAST:event_txt_EstudianteIDFocusLost

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel9MouseClicked

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
            java.util.logging.Logger.getLogger(LibrosE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LibrosE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LibrosE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LibrosE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LibrosE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.componentes.RSDateChooser date_Devolucion;
    private rojeru_san.componentes.RSDateChooser date_Prestamo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lbl_Curso;
    private javax.swing.JLabel lbl_IdEstudiante;
    private javax.swing.JLabel lbl_Turno;
    private javax.swing.JLabel lbl_autor1;
    private javax.swing.JLabel lbl_cantidad1;
    private javax.swing.JLabel lbl_errorLibro;
    private javax.swing.JLabel lbl_estudiantError;
    private javax.swing.JLabel lbl_libroId1;
    private javax.swing.JLabel lbl_nombreEstudiante;
    private javax.swing.JLabel lbl_nombreLibro1;
    private javax.swing.JPanel panel_main;
    private javax.swing.JPanel panel_main1;
    private necesario.RSMaterialButtonCircle rSMaterialButtonCircle2;
    private app.bolivia.swing.JCTextField txt_EstudianteID;
    private app.bolivia.swing.JCTextField txt_IDLibro;
    // End of variables declaration//GEN-END:variables
}
