
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;




public class Conexion {
    Connection conectar = null;
    
    private final String usuario = "root";
    private final String contrasenia = "carlitosmm23";
    private final String bd = "hoteleria";
    private final String ip = "localhost";
    private final String puerto = "3306";
    
    String cadena ="jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection estableceConexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,contrasenia);//Los datos de login para la base de datos
            //JOptionPane.showMessageDialog(null,"La conexión se ha realizado con éxito");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al conectarse a la base de datos, error: "+ e.toString());
        }
        return conectar;//retornando una variable de tipo Connection 
    }
    
}
