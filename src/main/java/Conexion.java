
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alumno
 */
public class Conexion {
     private final String DB="czNJ8gixWD";
     private final String URL="jdbc:mysql://remotemysql.com:3306/"+DB+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";;
     private final String USER="czNJ8gixWD";
      private final String PASS="xTww7cML8W";
      
    public Connection openConnection(){
        Connection con=null;
        
            try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=(Connection) DriverManager.getConnection(URL,USER,PASS);
            
           if(con!=null){
                System.out.println("Conexión exitosa");
                return con;
            }else{
               System.out.println("Conexión Fallida");            
            }
            
        }catch(ClassNotFoundException | SQLException ex){
            System.out.println("error "+ex.getMessage());
        }return con;
        }
    
        public void closeConnection(Connection connect){
                try {
                    connect.close();
                    System.out.println("Conexión Cerrada Exitosamente");
                } catch (SQLException ex) {
                    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
        }  
       public ArrayList<Producto>coger_datosproductos(){
         ArrayList<Producto> listaProductos = new ArrayList<>();
           try {
             //Conexion con=new Conexion();
             
             
             Connection conn=openConnection();
             PreparedStatement stmt=openConnection().prepareStatement("SELECT * FROM producto");
             ResultSet rs= stmt.executeQuery();
             while(rs.next()){
                 Producto producto= new Producto();
                 producto.setCodigo(rs.getInt("id"));
                 //producto.setDescripcion(rs.getString("descripcion"));
                 producto.setNombre(rs.getString("nombre"));
                 producto.setPeso(rs.getDouble("peso_producto"));
                 producto.setPrecio(rs.getDouble("precio_producto"));
                 producto.setStock(rs.getInt("stock"));
                 listaProductos.add(producto);
             }
             closeConnection(conn);
             

         } catch (SQLException ex) {
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
         }
         return listaProductos;
    }
    public int crearPedido(Pedido pedido){
       int cod=0;
        try {
            Connection conn=openConnection();
            PreparedStatement stmt=openConnection().prepareStatement("insert into reserva(recogida,precio_reserva,peso_reserva) values (?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement stmt2=openConnection().prepareStatement("insert into detalle_reserva values (?,?)");
            
            
            stmt.setString(1, pedido.getTransporte());
            stmt.setDouble(2, pedido.getPrecio_pedido());
            stmt.setDouble(3, pedido.getPeso_pedido());
            stmt.executeUpdate();
            ResultSet rs= stmt.getGeneratedKeys();
            
            
            
            while (rs.next()) {
                    cod = rs.getInt(1);
            }
            HashMap<Producto,Integer> ped=pedido.getPedido();
             for (Map.Entry<Producto, Integer> entry : ped.entrySet()) {
                Producto key = entry.getKey();
                Integer cant=entry.getValue();
                stmt2.setInt(0, key.getCodigo());
                stmt2.setInt(1, cant);
                stmt2.executeUpdate();
             }
             
            closeConnection(conn);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cod;
    }
        
        
    }
    

