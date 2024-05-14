package Clases;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.toedter.calendar.JDateChooser;
import java.sql.CallableStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author user
 */
public class ClaseUsuario {

   
    int idSexo;
    
     public void establecerIdSexo(int idSexo) {
        this.idSexo = idSexo;
    }

    public void MostrarSexoCombo(JComboBox comboSexo){
        
        
        Clases.Connection1 objetoConexion = new Clases.Connection1();
        String sql = "select * from sexo";
        
        Statement st;
        
        try{
            st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            comboSexo.removeAllItems();
            
            while (rs.next()){
                String nombreSexo = rs.getString("sexo");
                this.establecerIdSexo(rs.getInt("ID"));
                
                comboSexo.addItem("nombreSexo");
                comboSexo.putClientProperty(nombreSexo, idSexo);
            }
                   
                  
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Eroor al mostrar sexo:"+e.toString());
        }
        finally{
            
            objetoConexion.cerrarConexion();
        }
        
    }
    
    public void AgregarUsuario(JTextField nombres,JTextField apellidos, JComboBox comboSexo, JTextField edad, JTextField Nombre_Doctor,JTextField Apellido_Doctor, JTextField Especialidad,JDateChooser cita){
        
        Connection1 objetoConexion = new Connection1();
        
        String consulta="insert into ListaCitas (nombres, apellidos, sexo, Edad, Nombre_Doctor, Apellido_Doctor, Especialidad, cita) values (?,?,?,?,?,?,?,?);";
        
        try {
            
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, nombres.getText());
            cs.setString(2, apellidos.getText());
            
            int idSexo=(int) comboSexo.getClientProperty(comboSexo.getSelectedItem());
            
            cs.setInt(3, idSexo);
            
            cs.setInt(4, Integer.parseInt(edad.getText()));
            
            cs.setString(5, Nombre_Doctor.getText());
            cs.setString(6, Apellido_Doctor.getText());
            cs.setString(7, Especialidad.getText());
                   
            Date fechaSeleccionada = cita.getDate();
            java.sql.Date fechaSQL = new java.sql.Date(fechaSeleccionada.getTime());
            
            cs.setDate(8, fechaSQL);
            
            JOptionPane.showMessageDialog(null, "Se agrego correctamente");                 
            
        } catch (Exception e) {
        }
        
        JOptionPane.showMessageDialog(null, "No se agrego correctamente");
    }
    
    public void MostrarUsuarios(JTable tablaTotalUsuarios){
    
        Clases.Connection1 objetConexion = new Clases.Connection1();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql="";
        
        modelo.addColumn("id");
        modelo.addColumn("nombres");
        modelo.addColumn("apellidos");
        modelo.addColumn("sexo");
        modelo.addColumn("edad");
        modelo.addColumn("nombres_docotres");
        modelo.addColumn("apellidos_doctores");
        modelo.addColumn("especialidad");
        modelo.addColumn("cita");
        
        tablaTotalUsuarios.setModel(modelo);
        
        sql="select ListaCitas.ID,ListaCitas.nombres,ListaCitas.apellidos,sexo.sexo,ListaCitas.edad, ListaCitas.Nombre_Doctor, ListaCitas.Apellido_Doctor,ListaCitas.Especialidad, ListaCitas.cita FROM ListaCitas JOIN sexo ON ListaCitas.sexo = sexo.id;";
        
        try {
            Statement st = objetConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {                
                String ID = rs.getString("ID");
                String nombres = rs.getString("nombres");
                String apellidos = rs.getString("apellidos");
                String sexo = rs.getString("sexo");
                String edad = rs.getString("edad");
                String Nombres_Doctores = rs.getString("Nombre_Doctor");
                String Apellidos_Doctores = rs.getString("Apellido_Doctor");
                String Especialidad = rs.getString("Especialidad");
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.sql.Date fechaSQL = rs.getDate("cita");
                String nuevaFecha = sdf.format(fechaSQL);
                
                modelo.addRow(new Object[]{ID,nombres,apellidos,sexo,edad,Nombres_Doctores,Apellidos_Doctores,Especialidad,nuevaFecha});
                
            }
            tablaTotalUsuarios.setModel(modelo);
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Error al mostrar"+ e.toString());
        }
        finally{
            objetConexion.cerrarConexion();
        }
    
}
    
    public void Seleccionar (JTable totalUsuarios, JTextField id, JTextField nombres, JTextField apellidos, JComboBox sexo, JTextField edad, JTextField nombres_doctores, JTextField apellidos_doctores, JTextField especialidad, JDateChooser cita){
        
        int fila = totalUsuarios.getSelectedRow();
        
        if(fila>=0) {
            
            id.setText(totalUsuarios.getValueAt(fila, 0).toString());
            nombres.setText(totalUsuarios.getValueAt(fila, 1).toString());
            apellidos.setText(totalUsuarios.getValueAt(fila, 2).toString());
            
            sexo.setSelectedItem(totalUsuarios.getValueAt(fila, 3).toString());
            
            edad.setText(totalUsuarios.getValueAt(fila, 4).toString());
            
            nombres_doctores.setText(totalUsuarios.getValueAt(fila, 5).toString());
            apellidos_doctores.setText(totalUsuarios.getValueAt(fila, 6).toString());
            especialidad.setText(totalUsuarios.getValueAt(fila, 7).toString());
            
            String fechaString = totalUsuarios.getValueAt(fila, 8).toString();
            
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaDate = sdf.parse(fechaString);
                
                cita.setDate(fechaDate);
            } catch (Exception e) {
                
                JOptionPane.showMessageDialog(null, "Error al seleccionar"+e.toString());
                
            }
            
        }
    }
        public void ModificarUsuario (JTextField ID, JTextField nombres,JTextField apellidos, JComboBox comboSexo, JTextField edad, JTextField Nombre_Doctor,JTextField Apellido_Doctor, JTextField Especialidad,JDateChooser cita){
           
            Connection1 objetoConexion = new Connection1();
            
            String consulta ="UPDATE ListaCitas SET ListaCitas.Nombres=?, ListaCitas.Apellidos=?, ListaCitas.sexo=?, ListaCitas.edad=?, ListaCitas.Nombre_Doctor=?, ListaCitas.Apellido_Doctor=?, ListaCitas.Especialidad=?, ListaCitas.cita=? Where ListaCitas.id=?";
            
            try {
                
                CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
                
                cs.setString(1, nombres.getText());
            cs.setString(2, apellidos.getText());
            
            int idSexo=(int) comboSexo.getClientProperty(comboSexo.getSelectedItem());
            
            cs.setInt(3, idSexo);
            
            cs.setInt(4, Integer.parseInt(edad.getText()));
            
            cs.setString(5, Nombre_Doctor.getText());
            cs.setString(6, Apellido_Doctor.getText());
            cs.setString(7, Especialidad.getText());
                   
            Date fechaSeleccionada = cita.getDate();
            java.sql.Date fechaSQL = new java.sql.Date(fechaSeleccionada.getTime());
            
            cs.setDate(8, fechaSQL);
            
            cs.setInt(9,Integer.parseInt(ID.getText()));
            
          cs.execute();
                
          JOptionPane.showMessageDialog(null,"Se modifico");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"No se modifico"+ e.toString());
            }
            finally{
                objetoConexion.cerrarConexion();
            }
        }
        
    public void EliminarUsuario(JTextField id){
        Connection1 objetoConexion = new Connection1();
        
        String consulta="delete from ListaCitas where ListaCitas.id=?;";
        
        try {
           CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            
            cs.setInt(1, Integer.parseInt(id.getText()));
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null,"Se elimino");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se elimino"+ e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
       
    }
    
}
    
    

