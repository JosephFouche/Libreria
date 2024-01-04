/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package libreria;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import java.sql.Date;

/**
 *
 * @author Eduardo
 */
public class HomePage extends javax.swing.JFrame {

    /**
     * Creates new form HomePage
     */
    Color mouseEnterColor = new Color(0,0,0);
    Color mouseExitColor = new Color(51,51,51);
    Color mouseExitColorLogout = new Color(102,102,255);
    DefaultTableModel model;
    public HomePage() {
        initComponents();
        showPieChart();
        detallesEstudianteATablas();
        detallesDeLibrosATablas();
        ponerDatosalMarco();
    }
    //mandar los detalles de los estudiantes a tabla
    public void detallesEstudianteATablas(){
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/libreria_ms","root","");
            Statement st = (Statement) con.createStatement();
            ResultSet rs = st.executeQuery("select * from detalles_estudiante");
             
            while(rs.next()){//This loop iterates through the rows of the result set (rs). Each iteration represents a row in the database table.
                //Inside the loop, data from each column of the current row is extracted using methods like rs.getString and rs.getInt.
                
                String estudianteId  = rs.getString("id_estudiante");//libroId: Retrieves the value of the "libro_id" column as a string.
                String estudianteNombre  = rs.getString("nombre");//libroNombre: Retrieves the value of the "libro_nombre" column as a string.
                String curso  = rs.getString("curso");//Retrieves the value of the "autor" column as a string.
                String turno  = rs.getString("turno");//Retrieves the value of the "cantidad" column as an integer.
                
                /*The extracted values are then used to create an object array (Object[] obj), where each element of the array corresponds to 
                a column in the database table. 
                The order of elements in the array matches the order in which the columns were retrieved from the result set.*/
                Object [] obj = {estudianteId, estudianteNombre, curso, turno};
                model = (DefaultTableModel)tbl_DetallesEstudiantes.getModel();
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
    public void ponerDatosalMarco(){
    
        Statement st = null;
        ResultSet rs = null;
        
        long l = System.currentTimeMillis();
        Date diadeHoy = new Date(l);
        
        try{
            Connection con  =DBConnection.getConnection();
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("select * from libro_detalles");
            rs.last();
            lbl_marcoLibros.setText(Integer.toString(rs.getRow()));
            
            rs = st.executeQuery("select * from detalles_estudiante");
            rs.last();
            lbl_marcoEstudiantes.setText(Integer.toString(rs.getRow()));
            
            rs = st.executeQuery("select * from libros_prestados_detalles where status = '"+"Pendiente"+"'");
            rs.last();
            lbl_ChartPrestados.setText(Integer.toString(rs.getRow()));
            
            rs = st.executeQuery("select * from libros_prestados_detalles where devolucion < '"+diadeHoy+"'and status = '"+"Pendiente"+"'");
            rs.last();
            lbl_ChartMorosos.setText(Integer.toString(rs.getRow()));
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        
        
    
public void showPieChart(){
        
        //create dataset
      DefaultPieDataset barDataset = new DefaultPieDataset( );
      
      try {
          Connection con = DBConnection.getConnection();
          String sql = "select libro_nombre , count(*) as prestamo_Conteo from libros_prestados_detalles group by libro_id";
          Statement st = con.createStatement();
          ResultSet rs = st.executeQuery(sql);
          while(rs.next()){
              barDataset.setValue(rs.getString("libro_nombre"), Double.valueOf(rs.getDouble("prestamo_Conteo")));
          }
      }catch(Exception e){
          e.printStackTrace();
      }
        
      
      //create chart
       JFreeChart piechart = ChartFactory.createPieChart("Detalles de Libros Prestados",barDataset, false,true,false);//explain
      
        PiePlot piePlot =(PiePlot) piechart.getPlot();
      
       //changing pie chart blocks colors
       piePlot.setSectionPaint("IPhone 5s", new Color(255,255,102));
        piePlot.setSectionPaint("SamSung Grand", new Color(102,255,102));
        piePlot.setSectionPaint("MotoG", new Color(255,102,153));
        piePlot.setSectionPaint("Nokia Lumia", new Color(0,204,204));
      
       
        piePlot.setBackgroundPaint(Color.white);
        
        //create chartPanel to display chart(graph)
        ChartPanel barChartPanel = new ChartPanel(piechart);
        panelPieChart.removeAll();
        panelPieChart.add(barChartPanel, BorderLayout.CENTER);
        panelPieChart.validate();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        lbl_marcoLibros = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jPanel51 = new javax.swing.JPanel();
        lbl_marcoEstudiantes = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        lbl_ChartPrestados = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jPanel53 = new javax.swing.JPanel();
        lbl_ChartMorosos = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_DetallesEstudiantes = new rojerusan.RSTableMetro();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_DetallesLibros = new rojerusan.RSTableMetro();
        panelPieChart = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_menu_48px_1.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Sistema de Administracion de Biblioteca");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/male_user_50px.png"))); // NOI18N
        jLabel4.setText("Bienvenido Admin");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("X");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 638, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1380, 60));

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 51, 51));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Home_26px_2.png"))); // NOI18N
        jLabel5.setText("   Home Page");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 60));

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel7.setText("   Panel");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel8.setText("   Panel");
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel9.setText("   Panel");
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel8.setBackground(new java.awt.Color(0, 0, 0));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel10.setText("   Panel");
        jPanel8.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel9.setBackground(new java.awt.Color(0, 0, 0));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel11.setText("   Panel");
        jPanel9.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel12.setText("   Panel");
        jPanel10.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel9.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel13.setText("   Panel");
        jPanel11.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel12.setBackground(new java.awt.Color(0, 0, 0));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel14.setText("   Panel");
        jPanel12.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel11.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel9.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel15.setText("   Panel");
        jPanel13.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel16.setText("   Panel");
        jPanel14.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel13.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel15.setBackground(new java.awt.Color(0, 0, 0));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel17.setText("   Panel");
        jPanel15.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel16.setBackground(new java.awt.Color(0, 0, 0));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel18.setText("   Panel");
        jPanel16.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel15.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel13.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel17.setBackground(new java.awt.Color(0, 0, 0));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel19.setText("   Panel");
        jPanel17.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel18.setBackground(new java.awt.Color(0, 0, 0));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel20.setText("   Panel");
        jPanel18.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel17.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel19.setBackground(new java.awt.Color(0, 0, 0));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel21.setText("   Panel");
        jPanel19.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel20.setBackground(new java.awt.Color(0, 0, 0));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel22.setText("   Panel");
        jPanel20.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel19.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel17.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel13.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel9.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel21.setBackground(new java.awt.Color(0, 0, 0));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel23.setText("   Panel");
        jPanel21.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel22.setBackground(new java.awt.Color(0, 0, 0));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel24.setText("   Panel");
        jPanel22.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel21.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel23.setBackground(new java.awt.Color(0, 0, 0));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel25.setText("   Panel");
        jPanel23.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel24.setBackground(new java.awt.Color(0, 0, 0));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel26.setText("   Panel");
        jPanel24.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel23.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel21.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel25.setBackground(new java.awt.Color(0, 0, 0));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel27.setText("   Panel");
        jPanel25.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel26.setBackground(new java.awt.Color(0, 0, 0));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel28.setText("   Panel");
        jPanel26.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel25.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel27.setBackground(new java.awt.Color(0, 0, 0));
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel29.setText("   Panel");
        jPanel27.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel28.setBackground(new java.awt.Color(0, 0, 0));
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel30.setText("   Panel");
        jPanel28.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel27.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel25.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel29.setBackground(new java.awt.Color(0, 0, 0));
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel31.setText("   Panel");
        jPanel29.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel30.setBackground(new java.awt.Color(0, 0, 0));
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel32.setText("   Panel");
        jPanel30.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel29.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel31.setBackground(new java.awt.Color(0, 0, 0));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel33.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel33.setText("   Panel");
        jPanel31.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel32.setBackground(new java.awt.Color(0, 0, 0));
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel34.setText("   Panel");
        jPanel32.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel31.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel29.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel33.setBackground(new java.awt.Color(0, 0, 0));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel35.setText("   Panel");
        jPanel33.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel34.setBackground(new java.awt.Color(0, 0, 0));
        jPanel34.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel36.setText("   Panel");
        jPanel34.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel33.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel35.setBackground(new java.awt.Color(0, 0, 0));
        jPanel35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel37.setText("   Panel");
        jPanel35.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel36.setBackground(new java.awt.Color(0, 0, 0));
        jPanel36.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Library_26px_1.png"))); // NOI18N
        jLabel38.setText("   Panel");
        jPanel36.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel35.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel33.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel29.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel25.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel21.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel9.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel5.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 340, 60));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 340, 60));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Características");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 150, -1));

        jPanel37.setBackground(new java.awt.Color(51, 51, 51));
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel40.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel40.setForeground(java.awt.Color.lightGray);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Book_26px.png"))); // NOI18N
        jLabel40.setText(" Administrar libros");
        jLabel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel40MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel40MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel40MouseExited(evt);
            }
        });
        jPanel37.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel3.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 340, 60));

        jPanel38.setBackground(new java.awt.Color(51, 51, 51));
        jPanel38.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel39.setForeground(java.awt.Color.lightGray);
        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Read_Online_26px.png"))); // NOI18N
        jLabel39.setText("  Administrar estudiantes");
        jLabel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel39MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel39MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel39MouseExited(evt);
            }
        });
        jPanel38.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 190, -1));

        jPanel39.setBackground(new java.awt.Color(255, 51, 51));
        jPanel39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel41.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Home_26px_2.png"))); // NOI18N
        jLabel41.setText("   Home Page");
        jPanel39.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel38.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 340, 60));

        jPanel3.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 340, 60));

        jPanel40.setBackground(new java.awt.Color(51, 51, 51));
        jPanel40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel42.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel42.setForeground(java.awt.Color.lightGray);
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Sell_26px.png"))); // NOI18N
        jLabel42.setText("   Libros Prestados");
        jLabel42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel42MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel42MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel42MouseExited(evt);
            }
        });
        jPanel40.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 200, -1));

        jPanel3.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 340, 60));

        jPanel41.setBackground(new java.awt.Color(51, 51, 51));
        jPanel41.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel43.setForeground(java.awt.Color.lightGray);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Return_Purchase_26px.png"))); // NOI18N
        jLabel43.setText("  Regresar Libro");
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel43MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel43MouseExited(evt);
            }
        });
        jPanel41.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel3.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 340, 60));

        jPanel42.setBackground(new java.awt.Color(51, 51, 51));
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel44.setForeground(java.awt.Color.lightGray);
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_View_Details_26px.png"))); // NOI18N
        jLabel44.setText("   Ver Registros");
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel44MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel44MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel44MouseExited(evt);
            }
        });
        jPanel42.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel3.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 340, 60));

        jPanel43.setBackground(new java.awt.Color(51, 51, 51));
        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel45.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel45.setForeground(java.awt.Color.lightGray);
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Books_26px.png"))); // NOI18N
        jLabel45.setText("Ver libros prestados");
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel45MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel45MouseExited(evt);
            }
        });
        jPanel43.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel44.setBackground(new java.awt.Color(255, 51, 51));
        jPanel44.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Home_26px_2.png"))); // NOI18N
        jLabel46.setText("   Home Page");
        jPanel44.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel43.add(jPanel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 340, 60));

        jPanel3.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 340, 60));

        jPanel45.setBackground(new java.awt.Color(51, 51, 51));
        jPanel45.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel47.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel47.setForeground(java.awt.Color.lightGray);
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Conference_26px.png"))); // NOI18N
        jLabel47.setText("Lista de Morosos");
        jLabel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel47MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel47MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel47MouseExited(evt);
            }
        });
        jPanel45.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel3.add(jPanel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 340, 60));

        jPanel46.setBackground(new java.awt.Color(102, 102, 255));
        jPanel46.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel48.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel48.setForeground(java.awt.Color.lightGray);
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Exit_26px_1.png"))); // NOI18N
        jLabel48.setText("Salir");
        jLabel48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel48MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel48MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel48MouseExited(evt);
            }
        });
        jPanel46.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 150, -1));

        jPanel3.add(jPanel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, 340, 60));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 340, 720));

        jPanel47.setBackground(new java.awt.Color(255, 255, 255));
        jPanel47.setForeground(java.awt.Color.lightGray);
        jPanel47.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel48.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel47.add(jPanel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 284, -1, -1));

        jPanel49.setBackground(new java.awt.Color(255, 51, 102));
        jPanel49.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel47.add(jPanel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, -1));

        jPanel50.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 51, 51)));

        lbl_marcoLibros.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        lbl_marcoLibros.setForeground(new java.awt.Color(102, 102, 102));
        lbl_marcoLibros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Book_Shelf_50px.png"))); // NOI18N
        lbl_marcoLibros.setText("10");

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lbl_marcoLibros, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lbl_marcoLibros)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel47.add(jPanel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 260, 140));

        jLabel50.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(102, 102, 102));
        jLabel50.setText("N° de Libros");
        jPanel47.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 130, -1));

        jLabel51.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 102, 102));
        jLabel51.setText("N° de Estudiantes");
        jPanel47.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 130, -1));

        jPanel51.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(102, 102, 255)));

        lbl_marcoEstudiantes.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        lbl_marcoEstudiantes.setForeground(new java.awt.Color(102, 102, 102));
        lbl_marcoEstudiantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_People_50px.png"))); // NOI18N
        lbl_marcoEstudiantes.setText("10");

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lbl_marcoEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lbl_marcoEstudiantes)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel47.add(jPanel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, 260, 140));

        jLabel53.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(102, 102, 102));
        jLabel53.setText("Detalles de Estudiantes");
        jPanel47.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 160, -1));

        jPanel52.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(102, 102, 255)));

        lbl_ChartPrestados.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        lbl_ChartPrestados.setForeground(new java.awt.Color(102, 102, 102));
        lbl_ChartPrestados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_Sell_26px.png"))); // NOI18N
        lbl_ChartPrestados.setText("10");

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lbl_ChartPrestados, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lbl_ChartPrestados)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel47.add(jPanel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 260, 140));

        jLabel55.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(102, 102, 102));
        jLabel55.setText("Lista de Morosos");
        jPanel47.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 250, 130, -1));

        jPanel53.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 51, 51)));

        lbl_ChartMorosos.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        lbl_ChartMorosos.setForeground(new java.awt.Color(102, 102, 102));
        lbl_ChartMorosos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/libreria/adminIcons/icons8_List_of_Thumbnails_50px.png"))); // NOI18N
        lbl_ChartMorosos.setText("10");

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lbl_ChartMorosos, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lbl_ChartMorosos)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel47.add(jPanel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 270, 260, 140));

        tbl_DetallesEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Curso", "Rama"
            }
        ));
        tbl_DetallesEstudiantes.setColorBackgoundHead(new java.awt.Color(102, 102, 255));
        tbl_DetallesEstudiantes.setColorBordeFilas(new java.awt.Color(102, 102, 255));
        tbl_DetallesEstudiantes.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_DetallesEstudiantes.setColorSelBackgound(new java.awt.Color(255, 51, 51));
        tbl_DetallesEstudiantes.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        tbl_DetallesEstudiantes.setFuenteFilas(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tbl_DetallesEstudiantes.setFuenteFilasSelect(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        tbl_DetallesEstudiantes.setFuenteHead(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tbl_DetallesEstudiantes.setRowHeight(40);
        jScrollPane1.setViewportView(tbl_DetallesEstudiantes);

        jPanel47.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 530, 90));

        jLabel57.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(102, 102, 102));
        jLabel57.setText("N° de Libros Prestados");
        jPanel47.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 160, -1));

        jLabel58.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(102, 102, 102));
        jLabel58.setText("Detalles de Libros");
        jPanel47.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 160, -1));

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
        jScrollPane2.setViewportView(tbl_DetallesLibros);

        jPanel47.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 530, 90));

        panelPieChart.setLayout(new java.awt.BorderLayout());
        jPanel47.add(panelPieChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 440, 360, 230));

        getContentPane().add(jPanel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 1030, 730));

        setSize(new java.awt.Dimension(1397, 788));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
        
        AdministrarLibros libros = new AdministrarLibros();
        libros.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel40MouseClicked

    private void jLabel40MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseExited
        jPanel37.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel40MouseExited

    private void jLabel40MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseEntered
        jPanel37.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel40MouseEntered

    private void jLabel39MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseEntered
        jPanel38.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel39MouseEntered

    private void jLabel39MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseExited
        jPanel38.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel39MouseExited

    private void jLabel42MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseEntered
        jPanel40.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel42MouseEntered

    private void jLabel42MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseExited
        jPanel40.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel42MouseExited

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        DevolverLibro devolver = new DevolverLibro();
        devolver.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel43MouseClicked

    private void jLabel43MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseEntered
        jPanel41.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel43MouseEntered

    private void jLabel43MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseExited
        jPanel41.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel43MouseExited

    private void jLabel44MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseEntered
        jPanel42.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel44MouseEntered

    private void jLabel44MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseExited
        jPanel42.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel44MouseExited

    private void jLabel45MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseEntered
        jPanel43.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel45MouseEntered

    private void jLabel45MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseExited
        jPanel43.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel45MouseExited

    private void jLabel47MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseEntered
        jPanel45.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel47MouseEntered

    private void jLabel47MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseExited
        jPanel45.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel47MouseExited

    private void jLabel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseClicked
        LibrosE libros = new LibrosE();
        libros.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel42MouseClicked

    private void jLabel39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseClicked
        AdministrarEstudiantes admin = new AdministrarEstudiantes();
        admin.setVisible(true);
        dispose();
        
    }//GEN-LAST:event_jLabel39MouseClicked

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked
        VerTodosRegistros ver = new VerTodosRegistros();
        ver.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel44MouseClicked

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked
        VerListaDePrestados verlista = new VerListaDePrestados();
        verlista.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel45MouseClicked

    private void jLabel47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseClicked
        ListaDeDeudores deuda = new ListaDeDeudores();
        deuda.setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel47MouseClicked

    private void jLabel48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseClicked
         PaginaAcceso acceso = new PaginaAcceso();
         acceso.setVisible(true);
         dispose();
    }//GEN-LAST:event_jLabel48MouseClicked

    private void jLabel48MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseEntered
        jPanel46.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel48MouseEntered

    private void jLabel48MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseExited
        jPanel46.setBackground(mouseExitColorLogout);
    }//GEN-LAST:event_jLabel48MouseExited

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
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_ChartMorosos;
    private javax.swing.JLabel lbl_ChartPrestados;
    private javax.swing.JLabel lbl_marcoEstudiantes;
    private javax.swing.JLabel lbl_marcoLibros;
    private javax.swing.JPanel panelPieChart;
    private rojerusan.RSTableMetro tbl_DetallesEstudiantes;
    private rojerusan.RSTableMetro tbl_DetallesLibros;
    // End of variables declaration//GEN-END:variables
}
