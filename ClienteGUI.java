import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

@SuppressWarnings("serial")
public class ClienteGUI extends JFrame implements ActionListener{
	
	private JTextField tfNocuenta, tfNombre, tfSaldo, tfDate;
	private JButton bCapturar, bConsultar, bConsultarTipo, bConsultarNocta, bBorrarCliente, bCancelar, bRetiro, bDeposito, bTransferencia, bArchivoTexto, bDatosArchivo, bArchivoABase, bSalir, bVerRetiros, bVerDepositos;
	private JTextArea taDatos;
	private JPanel panel1, panel2;
	
	private JComboBox combo;
	private String opciones[] = {"INVERSION", "AHORRO","CREDITO","HIPOTECA"};

	private Timer clock;

	private BancoADjdbc banco = new BancoADjdbc();
	
	public ClienteGUI(){
		
		super("Banco BD/BCH");
		
		// 1) Crear objetos - JTextFields
		tfNocuenta = new JTextField();
		tfNombre   = new JTextField();
		tfSaldo    = new JTextField();
		tfDate	   = new JTextField();
		
		// JButtons
		bCapturar  = new JButton("Capturar Cliente");
		bConsultar = new JButton("Consultar Clientes");
		bConsultarTipo = new JButton("Consultar Tipo de Cuenta");
		bConsultarNocta = new JButton("Consultar No. Cuenta");
		bBorrarCliente = new JButton("Borrar Cliente");
		bRetiro = new JButton("Retirar de Una Cuenta");
		bDeposito = new JButton("Depositar a Una Cuenta");
		bTransferencia = new JButton("Transferir a otra Cuenta");
		bDatosArchivo = new JButton("Pasar datos al archivo de texto");
		bArchivoTexto = new JButton("Desplegar archivo texto");
		bArchivoABase = new JButton("Pasar datos del Archivo a la BD");
		bVerDepositos = new JButton("Consultar Depósitos");
		bVerRetiros = new JButton("Consultar Retiros");
		bCancelar = new JButton("Cancelar Transacción");
		bSalir     = new JButton("Salir"); 
		
		// 2) Adicionar deteccion de eventos a los botones
		bCapturar.addActionListener(this);
		bConsultar.addActionListener(this);
		bConsultarTipo.addActionListener(this);
		bConsultarNocta.addActionListener(this);
		bBorrarCliente.addActionListener(this);
		bCancelar.addActionListener(this);
		bRetiro.addActionListener(this);
		bDeposito.addActionListener(this);
		bTransferencia.addActionListener(this);
		bDatosArchivo.addActionListener(this);
		bArchivoTexto.addActionListener(this);
		bArchivoABase.addActionListener(this);
		bVerDepositos.addActionListener(this);
		bVerRetiros.addActionListener(this);
		bSalir.addActionListener(this);
		
		// JComboBox
		combo = new JComboBox(opciones);
		combo.addActionListener(this);
		
		// Reloj
		clock = new Timer(1000, updateClockAction);
		clock.start();
		
		// JTextArea y JPanel's
		taDatos    = new JTextArea(11,38);
		panel1     = new JPanel();
		panel2     = new JPanel();
		
		// 3) Adicionar los objetos al panel1
		panel1.setLayout(new GridLayout(13,2));
		panel2.setLayout(new FlowLayout());
		
		panel1.add(new JLabel("NO. DE CUENTA"));
		panel1.add(tfNocuenta);
		
		panel1.add(new JLabel("NOMBRE DEL CLIENTE"));
		panel1.add(tfNombre);
		
		panel1.add(new JLabel("TIPO DE CUENTA"));
		panel1.add(combo);
		
		panel1.add(new JLabel("SALDO"));
		panel1.add(tfSaldo);
		
		panel1.add(new JLabel("FECHA/HORA"));
		panel1.add(tfDate);
		tfDate.setEditable(false);
		
		panel1.add(bCapturar);
		panel1.add(bConsultar);
		panel1.add(bConsultarTipo);
		panel1.add(bConsultarNocta);
		panel1.add(bArchivoTexto);
		panel1.add(bDatosArchivo);
		panel1.add(bArchivoABase);
		panel1.add(bVerDepositos);
		panel1.add(bVerRetiros);
		panel1.add(bRetiro);
		panel1.add(bDeposito);
		panel1.add(bTransferencia);
		panel1.add(bBorrarCliente);
		panel1.add(bCancelar);
		panel1.add(bSalir);
		panel2.add(panel1);
		panel2.add(new JScrollPane(taDatos));
		
		// 4) Adicionar panel2 al JFrame
		add(panel2);
		setSize(500,610);
		setVisible(true);
		
		// 5) Deshabilitar botones
		bBorrarCliente.setEnabled(false);
		bCancelar.setEnabled(false);
		bDeposito.setEnabled(false);
		bRetiro.setEnabled(false);
		bTransferencia.setEnabled(false);	
	}
	
	private void clearFields(){
		tfNocuenta.setText("");
		tfNombre.setText("");
		tfSaldo.setText("");
	}
	
	private String obtenerDatos(){
		String datos = "";
		String cuenta = tfNocuenta.getText();
		String nombre = tfNombre.getText();
		String tipo   = (String)combo.getSelectedItem();
		String saldo  = tfSaldo.getText();
		StringTokenizer st = new StringTokenizer(tfDate.getText(), " ");
		String fecha = st.nextToken();
		String hora = st.nextToken();
		
		if(cuenta.equals("") || nombre.equals("") || saldo.isEmpty())
			datos = "VACIO";
		else{
			
			try{
				int intSaldo = Integer.parseInt(saldo);
				
				datos = cuenta + "_" + nombre + "_" + tipo + "_" + intSaldo +"_" + fecha + "_" + hora;
			}catch(NumberFormatException nfe){
				datos = "NO_NUMERICO";
			}
		}
		
		return datos;
	}
	
	private void habilitarBotones(boolean value){
		bCapturar.setEnabled(value);
		bConsultar.setEnabled(value);
		bConsultarTipo.setEnabled(value);
		bConsultarNocta.setEnabled(value);
		bBorrarCliente.setEnabled(!value);
		bCancelar.setEnabled(!value);
		bRetiro.setEnabled(!value);
		bDeposito.setEnabled(!value);
		bTransferencia.setEnabled(!value);
		combo.setEnabled(value);
		bArchivoABase.setEnabled(value);
		bArchivoTexto.setEnabled(value);
		bDatosArchivo.setEnabled(value);
		bVerDepositos.setEnabled(value);
		bVerRetiros.setEnabled(value);
		
		tfNocuenta.setEnabled(value);
		tfNombre.setEnabled(value);
		tfSaldo.setEnabled(value);
	}
	
	private void print(String mensaje){
		if (mensaje.equals("VACIO"))
			taDatos.setText("Todos los campos deben contener información. Por favor introduce\nla información en los campos faltantes.");
		
		if (mensaje.equals("NO_NUMERICO"))
			taDatos.setText("El saldo debe ser numérico. Por favor corrige este campo.");
		
		if (mensaje.equals("CLAVE_DUPLICADA"))
			taDatos.setText("Lo sentimos, pero la clave que está intentando ingresar ya se \nencuentra en la base de datos.");
		
        if(mensaje.equals("NO_TIPO"))
            taDatos.setText("No se encontró ninguna cuenta del tipo: " + combo.getSelectedItem().toString());
        
        if(mensaje.equals("ERROR"))
            taDatos.setText("No se pudo realizar la consulta de manera correcta.");
        
        if(mensaje.equals("NO_CUENTA"))
            taDatos.setText("No se localizó el Número de Cuenta para: " + tfNocuenta.getText());
        
        if(mensaje.equals("CLAVE_VACIA"))
            taDatos.setText("Por favor introduce un número de cuenta para consultar.");
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == bCapturar){
			String resultado = "";
			
			// 1. Obtner dato de los JTextFields
			String datos = obtenerDatos();
			
			// 2. Checar si algún campo está vacío o el saldo no es numérico
			if(datos.equals("VACIO"))
				print("VACIO");
			else
			{
				if(datos.equals("NO_NUMERICO"))
					print("NO_NUMERICO");
				else
				{
					// 3. Capturar los datos del cliente
					resultado = banco.capturar(datos);
				
					if(resultado.equals("CLAVE_DUPLICADA"))
						print("CLAVE_DUPLICADA");
					else{
						clearFields();
					
						// 4. Desplegar resultado de la transaccion
						taDatos.setText(resultado);
					}
				}
			}
		}
		
		if(e.getSource() == bConsultar){
			String datos = banco.consultarClientes();
			taDatos.setText(datos);
		}

		if(e.getSource() == bConsultarTipo){
			String tipo = combo.getSelectedItem().toString();
			String datos = banco.consultarTipo(tipo);
            
            if(datos.equals("NO_TIPO"))
                print("NO_TIPO");
            
            if(datos.equals("ERROR"))
                print("ERROR");
            
            if(!datos.equals("NO_TIPO") && !datos.equals("ERROR"))
                taDatos.setText(datos);
		}
		
		if(e.getSource() == bConsultarNocta){
			String cuenta = tfNocuenta.getText();
			String datos = banco.consultarNoCuenta(cuenta);
			StringTokenizer st = new StringTokenizer(datos,"_");
			
			if(datos.equals("ERROR"))
				print("ERROR");

            if(datos.equals("NO_CUENTA"))
                print("NO_CUENTA");
            
            if(datos.equals("CLAVE_VACIA"))
                print("CLAVE_VACIA");
            
            
            if(!datos.equals("NO_CUENTA") && !datos.equals("ERROR")&& !datos.equals("CLAVE_VACIA")){
            
	            st.nextToken(); /* La cuenta ya ha sido escrito previamente por el usuario 
					por lo que no se requiere de guardar el String */
				String nombre = st.nextToken();
				String tipo = st.nextToken();
				String saldo = st.nextToken();
				int tipoReal = 0;
				
				/* Pon la información en los TextFields */
				tfNombre.setText(nombre);
				
				if(tipo.equals("INVERSION"))
					tipoReal = 0;
				
				if(tipo.equals("AHORRO"))
					tipoReal = 1;
				
				if(tipo.equals("CREDITO"))
					tipoReal = 2;
				
				if(tipo.equals("HIPOTECA"))
					tipoReal = 3;
				
				combo.setSelectedIndex(tipoReal);
				
				tfSaldo.setText(saldo);
	
				taDatos.setText(datos);
	            
	            habilitarBotones(false);
            }
		}
		
		if(e.getSource() == bBorrarCliente){
			String cuenta = tfNocuenta.getText();
			String datos = banco.borrarCliente(cuenta);
			taDatos.setText(datos);
			habilitarBotones(true);
		}
		
		if(e.getSource() == bCancelar){
			habilitarBotones(true);
			clearFields();
		}

		
		if(e.getSource() == bSalir)
			System.exit(0);
		
		if (e.getSource() == bDeposito) {
			String cuenta = tfNocuenta.getText();
			String ctd = JOptionPane.showInputDialog("Cantidad a Depositar: ");
			String respuesta = "";
			
			int cantidad = Integer.parseInt(ctd);
			
			StringTokenizer st = new StringTokenizer(tfDate.getText(), " ");
			String fecha = st.nextToken();
			String hora = st.nextToken();
			
			respuesta = banco.deposito(cuenta, cantidad, fecha, hora);
			taDatos.setText(respuesta);
			habilitarBotones(true);
		}
		
		if (e.getSource() == bRetiro) {
			String cta = tfNocuenta.getText();
			String ctd = JOptionPane.showInputDialog("Cantidad a Retirar: ");
			String respuesta = "";
			int cantidad = Integer.parseInt(ctd);
			
			StringTokenizer st = new StringTokenizer(tfDate.getText(), " ");
			String fecha = st.nextToken();
			String hora = st.nextToken();
			
			respuesta = banco.retiro(cta, cantidad, fecha, hora);
			taDatos.setText(respuesta);
			habilitarBotones(true);
		}
		
		if(e.getSource() == bTransferencia)
		{
			String cta = tfNocuenta.getText();
			String cta2 = JOptionPane.showInputDialog("Cuenta a la que desea Transferir: ");
			String ctd = JOptionPane.showInputDialog("Cantidad a Transferir: ");
			String respuesta = "";
			int cantidad = Integer.parseInt(ctd);
			
			respuesta = banco.transferencia(cta, cantidad, cta2);
			taDatos.setText(respuesta);
			habilitarBotones(true);
		}
		
		if (e.getSource() == bDatosArchivo) {
			String respuesta = banco.baseDatosArchivo();
			taDatos.setText(respuesta);
		}
		
		if (e.getSource() == bArchivoTexto) {
			String respuesta = banco.desplegarArchivo();
			taDatos.setText(respuesta);
		}
		
		if (e.getSource() == bArchivoABase) {
			String respuesta = banco.archivoABaseDatos();
			taDatos.setText(respuesta);
		}
		
		if(e.getSource() == bVerDepositos){
			String datos = banco.consultar("Deposito");
			taDatos.setText(datos);
		}
		
		if(e.getSource() == bVerRetiros){
			String datos = banco.consultar("Retiro");
			taDatos.setText(datos);
		}
	}
	
	ActionListener updateClockAction = new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
				Date date = new Date();
				/* MySQL Date format */
			    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat formatoHora  = new SimpleDateFormat("hh:mm:ss");
				
				/* Adicionar la fecha al textfield */
				String fecha = formatoFecha.format(date);
				String hora  = formatoHora.format(date);
				tfDate.setText(fecha + " " + hora);
		    }
		};
	
	public static void main(String args[]){
		new ClienteGUI();
	}
}