import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;

@SuppressWarnings("serial")
public class ClienteGUI extends JFrame implements ActionListener
{
	private JTextField tfNocuenta, tfNombre, tfTipo, tfSaldo;
	private JButton bCapturar, bConsultar, bConsultarTipo, bConsultarNocta, bBorrarCliente, bCancelar, bRetiro, bDeposito, bArchivoTexto, bDatosArchivo, bArchivoABase, bSalir;
	private JTextArea taDatos;
	private JPanel panel1, panel2;
	
	private JComboBox combo;
	private String opciones[] = {"INVERSION", "AHORRO","CREDITO","HIPOTECA"};
	
	//private ClienteAD cliente = new ClienteAD();
	private BancoADjdbc banco = new BancoADjdbc();
	
	public ClienteGUI()
	{
		super("Banco OOP BCH");
		
		// 1. Crear objetos
		tfNocuenta = new JTextField();
		tfNombre   = new JTextField();
		tfTipo     = new JTextField();
		tfSaldo    = new JTextField();
		
		bCapturar  = new JButton("Capturar Cliente");
		bConsultar = new JButton("Consultar Clientes");
		bConsultarTipo = new JButton("Consultar Tipo de Cuenta");
		bConsultarNocta = new JButton("Consultar No. Cuenta");
		bBorrarCliente = new JButton("Borrar Cliente");
		bCancelar = new JButton("Cancelar Transacción");
		bRetiro = new JButton("Retirar de Una Cuenta");
		bDeposito = new JButton("Depositar a Una Cuenta");
		bDatosArchivo = new JButton("Pasar datos al archivo de texto");
		bArchivoTexto = new JButton("Desplegar archivo texto");
		bArchivoABase = new JButton("Pasar datos del Archivo a la BD");
		bSalir     = new JButton("Salir"); 
		
		// Adicionar deteccion de eventos a los botones
		bCapturar.addActionListener(this);
		bConsultar.addActionListener(this);
		bConsultarTipo.addActionListener(this);
		bConsultarNocta.addActionListener(this);
		bBorrarCliente.addActionListener(this);
		bCancelar.addActionListener(this);
		bRetiro.addActionListener(this);
		bDeposito.addActionListener(this);
		bDatosArchivo.addActionListener(this);
		bArchivoTexto.addActionListener(this);
		bArchivoABase.addActionListener(this);
		bSalir.addActionListener(this);
		
		combo = new JComboBox(opciones);
		combo.addActionListener(this);
		
		taDatos    = new JTextArea(10,30);
		panel1     = new JPanel();
		panel2     = new JPanel();
		
		// 2. Adicionar los objetos al panel1
		panel1.setLayout(new GridLayout(11,2));
		panel2.setLayout(new FlowLayout());
		
		panel1.add(new JLabel("NO. DE CUENTA"));
		panel1.add(tfNocuenta);
		
		panel1.add(new JLabel("NOMBRE DEL CLIENTE"));
		panel1.add(tfNombre);
		
		panel1.add(new JLabel("TIPOS DE CUENTA"));
		panel1.add(combo);
		
		panel1.add(new JLabel("SALDO"));
		panel1.add(tfSaldo);
		
		panel1.add(new JLabel("TIPO DE CUENTA"));
		panel1.add(tfTipo);
		tfTipo.setEditable(false);
		
		panel1.add(bCapturar);
		panel1.add(bConsultar);
		panel1.add(bConsultarTipo);
		panel1.add(bConsultarNocta);
		panel1.add(bRetiro);
		panel1.add(bDeposito);
		panel1.add(bBorrarCliente);
		panel1.add(bCancelar);
		panel1.add(bArchivoTexto);
		panel1.add(bDatosArchivo);
		panel1.add(bArchivoABase);
		panel1.add(bSalir);
		panel2.add(panel1);
		panel2.add(new JScrollPane(taDatos));
		
		// 3. Adicionar panel2 al JFrame
		add(panel2);
		setSize(500,550);
		setVisible(true);
		
		//Deshabilitar botones
		bBorrarCliente.setEnabled(false);
		bCancelar.setEnabled(false);
		bDeposito.setEnabled(false);
		bRetiro.setEnabled(false);
		
	}
	
	public void desplegar(String datos)
	{
		StringTokenizer st = new StringTokenizer(datos,"_");
		
		tfNocuenta.setText(st.nextToken());
		tfNombre.setText(st.nextToken());
		tfTipo.setText(st.nextToken());
		tfSaldo.setText(st.nextToken());
	}
	
	private String obtenerDatos()
	{
		String datos="";
		
		String cuenta = tfNocuenta.getText();
		String nombre = tfNombre.getText();
		String tipo   = (String)combo.getSelectedItem();
		String saldo  = tfSaldo.getText();
		
		if(cuenta.equals("") || nombre.equals("") || saldo.isEmpty())
			datos = "VACIO";
		else
		{
			try
			{
				int s = Integer.parseInt(saldo);
				datos = cuenta+"_"+nombre+"_"+tipo+"_"+s;
			}
			catch(NumberFormatException nfe)
			{
				datos = "NO_NUMERICO";
			}
		}
		
		return datos;
	}
	
	private void inactivarBotones(){
		bCapturar.setEnabled(false);
		bConsultar.setEnabled(false);
		bConsultarTipo.setEnabled(false);
		bConsultarNocta.setEnabled(false);
		bBorrarCliente.setEnabled(true);
		bCancelar.setEnabled(true);
		bRetiro.setEnabled(true);
		bDeposito.setEnabled(true);
	}
	
	private void activarBotones(){
		bCapturar.setEnabled(true);
		bConsultar.setEnabled(true);
		bConsultarTipo.setEnabled(true);
		bConsultarNocta.setEnabled(true);
		bBorrarCliente.setEnabled(false);
		bCancelar.setEnabled(false);
		bRetiro.setEnabled(false);
		bDeposito.setEnabled(false);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == bCapturar)
		{
			String datos="";
			String resultado="";
			
			// 1. Obtner dato de los JTextFields
			datos = obtenerDatos();
			
			// 2. Checar si algun campo es vacio o saldo no numerico
			if(datos.equals("VACIO"))
				taDatos.setText("Algun campo esta vacío...");
			else
			{
				if(datos.equals("NO_NUMERICO"))
					taDatos.setText("Saldo debe ser numerico...");
				else
				{
					// 3. Capturar los datos del cliente
					resultado = banco.capturar(datos);
				
					// 4. Desplegar resultado de la transaccion
					taDatos.setText(resultado);
					//taDatos.setText(datos);
				}
			}
		}
		
		if(e.getSource() == bConsultar)
		{
			String datos = banco.consultarClientes();
			taDatos.setText(datos);
		}

		if(e.getSource() == bConsultarTipo)
		{
			String tipo = tfTipo.getText();
			String datos = banco.consultarTipo(tipo);
            
            if(datos.equals("NO_DATOS"))
                taDatos.setText("No se encontr√≥ ninguna cuenta del tipo: " + tipo);
            
            if(datos.equals("ERROR"))
                taDatos.setText("No se pudo realizar la consulta de manera correcta");
            
            if(!datos.equals("NO_DATOS") && !datos.equals("ERROR"))
                taDatos.setText(datos);
		}
		
		if(e.getSource() == bConsultarNocta)
		{
			String cta = tfNocuenta.getText();
			String datos = banco.consultarNoCuenta(cta);
			StringTokenizer st = new StringTokenizer(datos,"_");
			
			if(datos.equals("ERROR"))
				taDatos.setText("No se pudo realizar la consulta de manera correcta");

            if(datos.equals("NO_DATOS"))
                taDatos.setText("No se localiz√≥ el N√∫mero de Cuenta para: " + cta);
            
            if(!datos.equals("NO_DATOS") && !datos.equals("ERROR")){
            
	            st.nextToken(); /* La cuenta ya ha sido escrito previamente por el usuario 
					por lo que no se requiere de guardar el String */
				String nombre = st.nextToken();
				String tipo = st.nextToken();
				String saldo = st.nextToken();
				
				/* Pon la información en los TextFields */
				tfNombre.setText(nombre);
				tfTipo.setText(tipo);
				tfSaldo.setText(saldo);
	
				taDatos.setText(datos);
	            
	            inactivarBotones();
            }
		}
		
		if(e.getSource() == bBorrarCliente){
			String cta = tfNocuenta.getText();
			String datos = banco.borrarCliente(cta);
			taDatos.setText(datos);
			activarBotones();
		}
		
		if(e.getSource() == bCancelar)
			activarBotones();

		
		if(e.getSource() == bSalir)
			System.exit(0);
		
		if (e.getSource() == bDeposito) {
			String cta = tfNocuenta.getText();
			String ctd = JOptionPane.showInputDialog("Cantidad a Depositar: ");
			String respuesta = "";
			int cantidad = Integer.parseInt(ctd);
			respuesta = banco.deposito(cta, cantidad);
			taDatos.setText(respuesta);
			activarBotones();
		}
		
		if (e.getSource() == bRetiro) {
			String cta = tfNocuenta.getText();
			String ctd = JOptionPane.showInputDialog("Cantidad a Retirar: ");
			String respuesta = "";
			int cantidad = Integer.parseInt(ctd);
			respuesta = banco.retiro(cta, cantidad);
			taDatos.setText(respuesta);
			activarBotones();
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
	}
	
	public static void main(String args[])
	{
		new ClienteGUI();
	}
}