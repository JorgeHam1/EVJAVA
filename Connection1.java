/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author user
 */
public class Connection1 {
    
   Connection Conectar = null;
   
   String usuario="Ham";
   String password="Jorge_Ham_24005";
   String bd="hospitalham";
   String ip="localhost";
   String puerto="3306";
   
   String cadena="jdbc:mysql://"+ip+":"+puerto+"/"+bd;
   
   public Connection estableceConexion(){
       try {
           Class.forName("com.mysql.jdbc.Driver");
           Conectar = DriverManager.getConnection(cadena,usuario,password);
           
           JOptionPane.showMessageDialog(null,"Se conecto");
       }
       catch (Exception e){
           
           JOptionPane.showMessageDialog(null, "No se conecto");
       }
       return Conectar;
   }
   
   public void cerrarConexion(){
       try{
           if(Conectar!=null && !Conectar.isClosed()){
               
               Conectar.close();
               JOptionPane.showMessageDialog(null, "Se cerro correctamente");
           }
       }catch (Exception e){
           
           JOptionPane.showMessageDialog(null,"No se cerro correctamente");
       }
   }
              
}
