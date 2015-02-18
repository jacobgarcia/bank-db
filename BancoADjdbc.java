import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class BancoADjdbc{
    // Microsoft Access Database
	/*public BancoADjdbc(){
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			DriverManager.getConnection("jdbc:odbc:baseBanco");
			System.out.println("Conexiâˆšâ‰¥n exitâˆšâ‰¥sa a la Base de Datos Access, Driver JDBC Tipo 1... ");
		}
		catch(ClassNotFoundException nfe){
			System.out.println("Error: " + nfe);
		}

		catch(SQLException sqle){
			System.out.println("Error de conexiâˆšâ‰¥n a la Base de Datos Access: \n" + sqle);
		}
	}*/
    
    private Connection conexion;
    private Statement statement;
    private ClienteDP clientedp;
    private PrintWriter printWriterArchivoSalida;
    private BufferedReader bufferedReaderArchivoEntrada;
    
	public BancoADjdbc(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/banco","root","admin");
            
			System.out.println("Conexi—n exit—sa a la Base de Datos Banco, Driver JDBC Tipo 4");
		}
		catch(ClassNotFoundException cnfe){
			System.out.println("Error: " + cnfe);
		}
		catch(InstantiationException ie){
			System.out.println("Error: " + ie);
		}
		catch(IllegalAccessException iae){
			System.out.println("Error: " + iae);
		}
		catch(SQLException sqle){
			System.out.println("Error: \n" + sqle);
		
		}
	}
    
    public String capturar(String datos){
        String insertCliente = "";
        String respuesta = "";
        
        /* Primer Forma */
        /*StringTokenizer st = new StringTokenizer(datos,"_");
        String nocuenta = st.nextToken();
        String nombre = st.nextToken();
        String tipo = st.nextToken();
        String saldo = st.nextToken();*/
        
        /* Crear String con instrucci—n SQL */
        //insertCliente = "INSERT INTO Cliente VALUES(" + clientedp.toSQLString() + ");";
        
        /* Segunda Forma */
        clientedp = new ClienteDP(datos);
        
        /* Crear String con instrucci—n SQL */
        insertCliente = "INSERT INTO Cliente VALUES(" + clientedp.toSQLString() + ");";
        
        try {
            //1) Abrir la base de datos Banco
            statement = conexion.createStatement();
            
            //2) Capturar datos en la tabla correspondiente
            statement.executeUpdate(insertCliente);
            
            //3) Cerrar la base de datos Banco
            statement.close();
            
            respuesta = "Datos: " + datos;
            System.out.println(conexion.nativeSQL(insertCliente));
        }
        catch(SQLException sqle){
            	System.out.println("Error: \n" + sqle);
        }
        return respuesta;
    }
    
    public String consultarClientes(){
        ResultSet result = null;
        String query = "";
        String respuesta = "";
        
        query = "SELECT * FROM Cliente";
        
        clientedp = new ClienteDP();
        try{
            
            //1) Abrir la base de datos Banco
            statement = conexion.createStatement();
        
            //2) Procesar datos de la tabla resultante
            result = statement.executeQuery(query);
            
            while(result.next()){
                clientedp.setNocta(result.getString(1));
                clientedp.setNombre(result.getString(2));
                clientedp.setTipo(result.getString(3));
                clientedp.setSaldo(result.getInt(4));
                
                respuesta = respuesta + clientedp.toString() + "\n";
            }
            
            //3) Cerra la base de datos banco
            statement.close();
            System.out.println(conexion.nativeSQL(query));
        }
        catch(SQLException sqle){
            System.out.println("Error: \n" + sqle);
            respuesta = "No se pudo realizar la consulta";
        }
        
        return respuesta;
    }
    
    public String consultarTipo(String tipo){
        ResultSet result = null;
        String query = "";
        String respuesta = "";
        
        query = "SELECT * FROM Cliente WHERE cuenta = '" + tipo.toString() + "'";
        
        clientedp = new ClienteDP();
        try{
            
            //1) Abrir la base de datos Banco
            statement = conexion.createStatement();
            
            //2) Procesar datos de la tabla resultante
            result = statement.executeQuery(query);
            
            while(result.next()){
                clientedp.setNocta(result.getString(1));
                clientedp.setNombre(result.getString(2));
                clientedp.setTipo(result.getString(3));
                clientedp.setSaldo(result.getInt(4));
                
                respuesta = respuesta + clientedp.toString() + "\n";
            }
            
            if(respuesta == "")
                respuesta = "No se encontr— ninguna cuenta de tipo: " + tipo;
            
            //3) Cerra la base de datos banco
            statement.close();
            System.out.println(conexion.nativeSQL(query));
        }
        catch(SQLException sqle){
            System.out.println("Error: \n" + sqle);
            respuesta = "No se pudo realizar la consulta";
        }
        
        return respuesta;
    }
    
    public String consultarNoCuenta(String cuenta){
        ResultSet result = null;
        String query = "";
        String respuesta = "";
        
        query = "SELECT * FROM Cliente WHERE nocta = '" + cuenta.toString() + "'";
        
        clientedp = new ClienteDP();
        try{
            
            //1) Abrir la base de datos Banco
            statement = conexion.createStatement();
            
            //2) Procesar datos de la tabla resultante
            result = statement.executeQuery(query);
            
            if(result.next()){
                clientedp.setNocta(result.getString(1));
                clientedp.setNombre(result.getString(2));
                clientedp.setTipo(result.getString(3));
                clientedp.setSaldo(result.getInt(4));
                
                respuesta = respuesta + clientedp.toString() + "\n";
            }
            
            if(respuesta.equals(""))
                respuesta = "NO_DATOS";
            
            //3) Cerra la base de datos banco
            statement.close();
            System.out.println(conexion.nativeSQL(query));
        }
        catch(SQLException sqle){
            System.out.println("Error: \n" + sqle);
            respuesta = "ERROR";
        }
        
        return respuesta;
    }
    
    public String borrarCliente(String cuenta){
        String deleteSQL = "";
        String respuesta = "";
        
        deleteSQL = "DELETE FROM Cliente WHERE nocta = '" + cuenta.toString() + "'";
        
        try{
            
            //1) Abrir la base de datos Banco
            statement = conexion.createStatement();
            
            //2) Ejecutar DELETE Statement
            statement.executeUpdate(deleteSQL);
            
            respuesta = "Cliente eliminado de la Base de Datos";
            //3) Cerra la base de datos banco
            statement.close();
            System.out.println(conexion.nativeSQL(deleteSQL));
        }
        catch(SQLException sqle){
            System.out.println("Error: \n" + sqle);
            respuesta = "ERROR";
        }
        
        return respuesta;
    }
    
    public String deposito(String cuenta, int cantidad) {
    	ResultSet result;
        String updateSQL = "";
        int respuesta = 0;
        String res = "";
        String query = "";

        int saldo = 0;
        query = "SELECT saldo FROM Cliente WHERE nocta = '" + cuenta.toString() + "'";
        
        try{
            
            //1) Abrir la base de datos Banco
            statement = conexion.createStatement();
            
            //2) Procesar datos de la tabla resultante
            result = statement.executeQuery(query);
            
            if(result.next()){
                clientedp.setSaldo(result.getInt(1));
                
                respuesta = clientedp.getSaldo();
            }
            
            if (clientedp.getTipo().equals("AHORRO") || clientedp.getTipo().equals("INVERSION"))
                saldo = respuesta + cantidad;
            else
            	 saldo = respuesta - cantidad;
            
            updateSQL = "UPDATE Cliente SET saldo = " + saldo + " WHERE nocta = '" + cuenta.toString() + "'";
            
            //3) Ejecutar UPDATE Statement
            statement.executeUpdate(updateSQL);
            
            res = "Dep—sito Exit—so";
            //3) Cerra la base de datos banco
            statement.close();
            System.out.println(conexion.nativeSQL(updateSQL));
        }
        catch(SQLException sqle){
            System.out.println("Error: \n" + sqle);
            res = "ERROR";
        }
        
        return res;

	}
    
    public String retiro(String cuenta, int cantidad) {
    	ResultSet result;
        String updateSQL = "";
        int saldo = 0;
        String res = "";
        String query = "";
        
        Boolean hipo = false;
        
        query = "SELECT * FROM Cliente WHERE nocta = '" + cuenta.toString() + "'";
        
        try{
            
            //1) Abrir la base de datos Banco
            statement = conexion.createStatement();
            
            //2) Procesar datos de la tabla resultante
            result = statement.executeQuery(query);
            
            if(result.next()){
                clientedp.setSaldo(result.getInt("saldo"));
                clientedp.setTipo(result.getString("cuenta"));
                
                saldo = clientedp.getSaldo();
            }
            
            if (clientedp.getTipo().equals("AHORRO") || clientedp.getTipo().equals("INVERSION"))
            	 saldo -= cantidad;
            
			if (clientedp.getTipo().equals("CREDITO"))
				 saldo += cantidad;
			
			if (clientedp.getTipo().equals("HIPOTECA"))
				 hipo = true;
            
            updateSQL = "UPDATE Cliente SET saldo = " + saldo + " WHERE nocta = '" + cuenta.toString() + "'";
            
            //3) Ejecutar UPDATE Statement
            statement.executeUpdate(updateSQL);
            
            if (!hipo)
            	res = "Retiro Exit—so";
            else
            	res = "Para una cuenta de 'HIPOTECA' no se puede realizar un retiro";
            
            //3) Cerra la base de datos banco
            statement.close();
            System.out.println(conexion.nativeSQL(updateSQL));
        }
        catch(SQLException sqle){
            System.out.println("Error: \n" + sqle);
            res = "ERROR";
        }
        
        return res;
	}
	
	//Transferencia
	public String transferencia(String cuentaOrigen, int cantidad, String cuentaDestino) {
    	ResultSet result;
        String updateOrigenSQL = "";
        String updateDestinoSQL="";
        int saldoDestino = 0;
        int saldoOrigen = 0;
        String res = "";
        String query = "";
        String query2 = "";
        
        Boolean hipo = false;
        
        //Primero se hace la transferencia despues se descuenta de Origen
        query = "SELECT * FROM Cliente WHERE nocta = '" + cuentaDestino.toString() + "'";
        query2 = "SELECT * FROM Cliente WHERE nocta = '" + cuentaOrigen.toString() + "'"; 
        
        try{
            
            //1) Abrir la base de datos Banco
            statement = conexion.createStatement();
            
            //2) Procesar datos de la tabla resultante
            result = statement.executeQuery(query);
            
            if(result.next()){
                clientedp.setSaldo(result.getInt("saldo"));
                clientedp.setTipo(result.getString("cuenta"));
                
                saldoDestino = clientedp.getSaldo();
            }
            
            if (clientedp.getTipo().equals("AHORRO") || clientedp.getTipo().equals("INVERSION"))
            	 saldoDestino += cantidad;
            
			if (clientedp.getTipo().equals("CREDITO") || clientedp.getTipo().equals("HIPOTECA"))
				 saldoDestino -= cantidad;
			
			updateDestinoSQL = "UPDATE Cliente SET saldo = " + saldoDestino + " WHERE nocta = '" + cuentaDestino.toString() + "'";
				 
			result = statement.executeQuery(query2);         
			if(result.next()){
                clientedp.setSaldo(result.getInt("saldo"));
                clientedp.setTipo(result.getString("cuenta"));
                
                saldoOrigen = clientedp.getSaldo();
            }
            if (clientedp.getTipo().equals("AHORRO") || clientedp.getTipo().equals("INVERSION"))
            	 saldoOrigen -= cantidad;
            	 
            
			if (clientedp.getTipo().equals("CREDITO") || clientedp.getTipo().equals("HIPOTECA"))
				 hipo = true;
				 
            updateOrigenSQL = "UPDATE Cliente SET saldo = " + saldoOrigen + " WHERE nocta = '" + cuentaOrigen.toString() + "'";
                        
            //3) Ejecutar UPDATE Statement
            
            if (!hipo)
            {
            	res = "Transferencia Exit—so";
            	statement.executeUpdate(updateDestinoSQL); 
            	statement.executeUpdate(updateOrigenSQL);
            }
            else
            	res = "No puedes transferir de una cuenta de Credito o Hipoteca";
            
            //3) Cerra la base de datos banco
            statement.close();
            System.out.println(conexion.nativeSQL(updateOrigenSQL));
            System.out.println(conexion.nativeSQL(updateDestinoSQL));
        }
        catch(SQLException sqle){
            System.out.println("Error: \n" + sqle);
            res = "ERROR";
        }
        
        return res;
	}
    
    public String baseDatosArchivo() {
    	String clientes = consultarClientes();
    	String respuesta = "";
    	try {
			//1) Abrir el archivo
    		printWriterArchivoSalida = new PrintWriter(new FileWriter("Clientes.txt"), true);
    		
    		//2) Guardar y escribir archivo
    		printWriterArchivoSalida.println(clientes);
    		
    		//3) Cerrar el archivo
    		printWriterArchivoSalida.close();
    		
    		respuesta = "La informaci—n de la base de datos se transfiri—\ncorrectamente al archivo de texto";
    		
		} catch (IOException ioe) {
			System.out.println("Error: " + ioe);
			respuesta = "No se pudieron guardar los cambios en el archivo de texto";
		}
		return respuesta;
	}
    
    public String desplegarArchivo() {
    	String datos = "";
    	try {
    		//1) Abrir el archivo en modo lectura
			bufferedReaderArchivoEntrada = new BufferedReader(new FileReader("Clientes.txt"));
			
			//2) Lectura de Datos
			while (bufferedReaderArchivoEntrada.ready())
				datos += bufferedReaderArchivoEntrada.readLine() + "\n";
			
			//3) Cerramos el archivo
			bufferedReaderArchivoEntrada.close();
			
		} catch (IOException ioe) {
			System.out.println("Error: " + ioe);
			datos = "El archivo de texto no contiene informaci—n";
		}
		return datos;
	}
    
    public String archivoABaseDatos() {
		String datos = "";
		String insertCliente = "";
		
		try {
			//1) Abrir el archivo en modo lectura
			bufferedReaderArchivoEntrada = new BufferedReader(new FileReader("Clientes.txt"));
			
			//2) Lectura de datos del archivo
			while (bufferedReaderArchivoEntrada.ready()) {
				datos = bufferedReaderArchivoEntrada.readLine();
				
				if(!datos.equals("")){
					clientedp = new ClienteDP(datos);
				
					 /* Crear String con instrucci—n SQL */
			        insertCliente = "INSERT INTO Cliente VALUES(" + clientedp.toSQLString() + ");";
			        
			        try {
			            //3) Abrir la base de datos Banco
			            statement = conexion.createStatement();
			            
			            //4) Capturar datos en la tabla correspondiente
			            statement.executeUpdate(insertCliente);
			            
			            //5) Cerrar la base de datos Banco
			            statement.close();
	
			            System.out.println(conexion.nativeSQL(insertCliente));
					} catch (SQLException sqle) {
						System.out.println("Error SQL: \n" + sqle);
					}
				}
			}
			
			//6) Cerramos el archivo
			bufferedReaderArchivoEntrada.close();
			
			datos = "Datos pasados a la BD exit—samente";
			
		} catch (IOException ioe) {
			System.out.println("Error: " + ioe);
			datos = "El archivo de texto no contiene informaci—n";
		}
		return datos;
	}
}