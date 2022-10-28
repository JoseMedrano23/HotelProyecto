
import java.awt.TextField;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.TableView;


public class Cliente {

    private int id;
    private String nombres;
    private String apellidos;
    private int dpi;
    private String servicio;
    private int noServicio;
    
    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public void setNoServicio(int noServicio) {
        this.noServicio = noServicio;
    }
    
    

    //getters
    public int getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getDpi() {
        return dpi;
    }

    public String getServicio() {
        return servicio;
    }

    public int getNoServicio() {
        return noServicio;
    }
    
    

    public void insertarCliente(JTextField paramNombres, JTextField paramApellidos, JTextField paramDpi, JTextField paramServicio, JTextField paramNoServicio) {

        setNombres(paramNombres.getText());//incorporo a mis variables el valor de los paramentros de las cajas de Texto
        setApellidos(paramApellidos.getText());
        setDpi(Integer.parseInt(paramDpi.getText()));
        setServicio(paramServicio.getText());
        setNoServicio(Integer.parseInt(paramNoServicio.getText()));
        
        Conexion objetoConexion = new Conexion();

        String consulta = "insert into Cliente(nombres,apellidos,dpi,servicio,noServicio ) values (?,?,?,?,?);";

    //  DefaultTableModel modelo = new DefaultTableModel();
        
        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);

            cs.setString(1, getNombres());//agrega en el primer paramentro lo que trae nombres en la caja
            cs.setString(2, getApellidos());
            cs.setInt(3, getDpi());
            cs.setString(4, getServicio());
            cs.setInt(5, getNoServicio());
          /*  
            if (paramNoServicio.getText() == modelo.getColumnName(noServicio)){
                JOptionPane.showMessageDialog(null, "no insertó correctamente el Cliente");
                
            }else {
                JOptionPane.showMessageDialog(null, " se insertó correctamente el Cliente");
            }
            
            */
            cs.execute();

           JOptionPane.showMessageDialog(null, "Se insertó correctamente el Cliente");
                

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No Se insertó correctamente el Cliente, error: " + e.toString());

        }

    }

    public void mostrarCliente(JTable paramTablaTotalClientes) {

        Conexion objetoConexion = new Conexion();//preparo mi conexion

        DefaultTableModel modelo = new DefaultTableModel();//cree mi modelo de tabla
        //ordenar numericamente y cree un onjeto 
        TableRowSorter<TableModel> OrdenarTable = new TableRowSorter<TableModel>(modelo);
        //orden de Cabecera 
        paramTablaTotalClientes.setRowSorter(OrdenarTable);//Ordene mi tabla

        String sql = "";

        modelo.addColumn("ID");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("DPI");
        modelo.addColumn("Servicio Escogido");
        modelo.addColumn("Numero Servicio");

        paramTablaTotalClientes.setModel(modelo); //ingresar al parametro el modelo que hemos creado

        sql = "select *from Cliente;";

        String[] datos = new String[6];
        Statement st;//preparando para la ejecucion 

        try {
            st = objetoConexion.estableceConexion().createStatement();//aqui conecto con mi base
            ResultSet rs = st.executeQuery(sql);//ejecutame la consulta de select *from cliente

            //recorrer la tabla
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                
                modelo.addRow(datos);//Insertando filas
                
            }

            paramTablaTotalClientes.setModel(modelo);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No se pudo mostrar los Registros, error: " + e.toString());
        }

    }

    public void seleccionarCliente(JTable paramTablaCliente, JTextField paramId, JTextField paramNombres, JTextField paramApellidos, JTextField paramDpi, JTextField paramServicio,JTextField paramNoServicio) {
        try {
            int fila = paramTablaCliente.getSelectedRow(); //contador de filas

            //Comparar la fila seleccionada 
            if (fila >= 0) { //Si es mayor a 0, se esta seleccionando el paramentro
                paramId.setText((paramTablaCliente.getValueAt(fila, 0).toString()));
                paramNombres.setText((paramTablaCliente.getValueAt(fila, 1).toString()));
                paramApellidos.setText((paramTablaCliente.getValueAt(fila, 2).toString()));
                paramDpi.setText((paramTablaCliente.getValueAt(fila, 3).toString()));
                paramServicio.setText((paramTablaCliente.getValueAt(fila, 4).toString()));
                paramNoServicio.setText((paramTablaCliente.getValueAt(fila, 5).toString()));
            } else {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error de seleccion, error: " + e.toString());
        }
    }

    public void modificarCliente(JTextField paramId, JTextField paramNombres, JTextField paramApellidos, JTextField paramDpi, JTextField paramServicio,JTextField paramNoServicio ) {

        setId(Integer.parseInt(paramId.getText()));//obtengo el valor en cadena y luego lo convierto a entero

        setId(Integer.parseInt(paramId.getText()));
        setNombres(paramNombres.getText());
        setApellidos(paramApellidos.getText());
        setDpi(Integer.parseInt(paramDpi.getText()));
        setServicio(paramServicio.getText());
        setNoServicio(Integer.parseInt(paramNoServicio.getText()));

        Conexion objetoConexion = new Conexion();

        String consulta = "UPDATE Cliente SET cliente.nombres = ?, cliente.apellidos =?, cliente.dpi = ?, cliente.servicio = ?,cliente.noServicio = ? WHERE cliente.id=?;";
        try {

            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);

            cs.setString(1, getNombres());//agrega en el primer paramentro lo que trae nombres en la caja
            cs.setString(2, getApellidos());
            cs.setInt(3, getDpi());
            cs.setString(4, getServicio());
            cs.setInt(5, getNoServicio());
            cs.setInt(6, getId());

            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Modificación Exitosa");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"No se modificó, error:"+ e.toString());
        }

    }

    public void eliminarCliente(JTextField paramId){
        
        setId(Integer.parseInt(paramId.getText()));
        
        Conexion objetoConexion = new Conexion();
        
        String consulta = "DELETE FROM Cliente WHERE cliente.id=?;"; 
        
        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);//Preparando consulta
            cs.setInt(1, getId());
            cs.execute();
            
            JOptionPane.showMessageDialog(null,"Se eliminó correctamente el Cliente");
                    
        } catch (SQLException e) {
            
            JOptionPane.showMessageDialog(null,"No se pudo eliminar, error: "+ e.toString());
        }
        
    }
    
   

}
