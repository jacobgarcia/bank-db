import java.util.*;

public class TransaccionDP
{
	private String nocta, tipo, fecha, hora;
	private int saldoAnterior, cantidad, saldoActual;
	
	
	/*** Constructores ***/
	public TransaccionDP()
	{
		this.nocta  = "";
		this.tipo   = "";
		this.saldoAnterior  = 0;
		this.cantidad = 0;
		this.saldoActual = 0;
		this.fecha = "";
		this.hora = "";
	}
	
	public TransaccionDP(String datos)
	{
		StringTokenizer st = new StringTokenizer(datos,"_");
		
		this.nocta  = st.nextToken();
		this.tipo   = st.nextToken();
		this.saldoAnterior  = Integer.parseInt(st.nextToken());
		this.cantidad = Integer.parseInt(st.nextToken());
		this.saldoActual = Integer.parseInt(st.nextToken());
		this.fecha = st.nextToken();
		this.hora = st.nextToken();
	}
	
	/*** Accesors ***/
	public String getNocta()
	{
		return this.nocta;
	}
		
	public String getTipo()
	{
		return this.tipo;
	}
	
	public int getSaldoAnterior()
	{
		return this.saldoAnterior;
	}
	
	public int getCantidad()
	{
		return this.cantidad;
	}
	
	public int getSaldoActual()
	{
		return this.saldoAnterior;
	}
	
	public String getFecha()
	{
		return this.fecha;
	}
	
	public String getHora()
	{
		return this.hora;
	}
	
	
	/*** Mutators ***/
	public void setNocta(String str)
	{
		this.nocta = str;
	}
		
	public void setTipo(String str)
	{
		this.tipo = str;
	}
	
	public void setSaldoAnterior(int n)
	{
		this.saldoAnterior = n;
	}
	
	public void setCantidad(int n)
	{
		this.cantidad = n;
	}
	
	public void setSaldoActual(int n)
	{
		this.saldoActual = n;
	}
	
	public void setFecha(String str)
	{
		this.fecha = str;
	}
	
	public void setHora(String str)
	{
		this.hora = str;
	}
	
	public String toString()
	{
		return this.nocta+"_"+this.tipo+"_"+this.saldoAnterior+"_"+this.cantidad+"_"+this.saldoActual+"_"+this.fecha+"_"+this.hora;
	}
    
    public String toSQLString(){
        return "'" + this.nocta + "','" + this.tipo+"','" + this.saldoAnterior + "'," + this.cantidad + "'," + this.saldoActual + "'," + this.fecha + "'," + this.hora;
    }
}