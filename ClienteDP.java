import java.util.*;

public class ClienteDP
{
	private String nocta,nombre,tipo, fecha, hora;
	private int saldo;
	
	
	/*** Constructores ***/
	public ClienteDP()
	{
		this.nocta  = "";
		this.nombre = "";
		this.tipo   = "";
		this.saldo  = 0;
		this.fecha  = "";
		this.hora   = "";
	}
	
	public ClienteDP(String datos)
	{
		StringTokenizer st = new StringTokenizer(datos,"_");
		
		this.nocta  = st.nextToken();
		this.nombre = st.nextToken();
		this.tipo   = st.nextToken();
		this.saldo  = Integer.parseInt(st.nextToken());
		this.fecha  = st.nextToken();
		this.hora   = st.nextToken();
	}
	
	/*** Accesors ***/
	public String getNocta()
	{
		return this.nocta;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public String getTipo()
	{
		return this.tipo;
	}
	
	public int getSaldo()
	{
		return this.saldo;
	}
	
	public String getFecha() {
		return this.fecha;
	}
	
	public String getHora() {
		return this.hora;
	}
	
	
	/*** Mutators ***/
	public void setNocta(String str)
	{
		this.nocta = str;
	}
	
	public void setNombre(String str)
	{
		this.nombre = str;
	}
	
	public void setTipo(String str)
	{
		this.tipo = str;
	}
	
	public void setSaldo(int n)
	{
		this.saldo = n;
	}
	
	public void setFecha(String str) {
		this.fecha = str;
	}
	
	public void setHora(String str) {
		this.hora = str;
	}
	
	public String toString()
	{
		return this.nocta+"_"+this.nombre+"_"+this.tipo+"_"+this.saldo+"_"+this.fecha+"_"+this.hora;
	}
    
    public String toSQLString(){
    	return "'" + this.nocta + "','" + this.nombre+"','"+this.tipo+"',"+this.saldo+",'" + this.fecha + "','" + this.hora + "'";
    }
}