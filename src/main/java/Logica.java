
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Usuario
 */
public class Logica {
    //lista de la interfaz del catalogo
    private List<producto_interfaz> lpi;
    //lista de la interfaz de los articulos pedidos
    private List<pedido_interfaz> lped;
    
    
    //objeto pedido
    private Pedido pedido;
    


    public Logica() {
        Conexion conexion=new Conexion();
        
        lpi= new ArrayList<>();
        ArrayList<Producto> listaProductos = conexion.coger_datosproductos();
        for(Producto p:listaProductos){
            lpi.add(new producto_interfaz(p));
        }
        lped= new ArrayList<>();
        pedido=new Pedido();
        pedido.setCodigo_pedido(conexion.crearPedido(pedido));
        
        
        
    }

    //funcion que recorre la lista de los productos que tenemos y 
    public void llenarProductos(JPanel jPanel2){
        

        for(producto_interfaz p:lpi){

            jPanel2.add(p);
        }
        
       
    }
     

    public void anyadirProductos(JPanel jPanel2,JPanel jPanel3) {
        Component []comp =jPanel2.getComponents();
         
         
        for(Component c:comp){
            if(c instanceof producto_interfaz){
                producto_interfaz pi=(producto_interfaz) c;
                if(pi.getSeleccion().isSelected()==true){
                    //cosas del elemento a añadir o a modificar
                    Producto cod=pi.getProducto();
                    int cant=(Integer)pi.getjSpinner1().getValue();
                    pedido.anyadirProducto(cod, cant);
                    int a=compruebaProductos(pi);
                    if(a!=-1){
                        lped.add(a, new pedido_interfaz(cod,cant));
                    }else{
                        lped.add(new pedido_interfaz(cod,cant));
                    }
                    pi.getjSpinner1().setEnabled(false);
                    pi.getSeleccion().setSelected(false);
                    
                }
            }
        }
        jPanel3.removeAll();
        for(pedido_interfaz p:lped){

            jPanel3.add(p);
        }
          
    }
    private int compruebaProductos(producto_interfaz pi){
        int flag=-1;
        
        for(int i=0;i<lped.size();i++){
            if(lped.get(i).getProducto()==pi.getProducto()){
                
                flag=i;
                break;
            }
            
        }
        
        return flag;
    }
    

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    
    
    
    
}
