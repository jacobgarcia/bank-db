import java.util.*;

public class ClienteDP
{
	private String nocta,nombre,tipo;
	private int saldo;
	
	
	/*** Constructores ***/
	public ClienteDP()
	{
		this.nocta  = "";
		this.nombre = "";
		this.tipo   = "";
		this.saldo  = 0;
	}
	
	public ClienteDP(String datos)
	{
		StringTokenizer st = new StringTokenizer(datos,"_");
		
		this.nocta  = st.nextToken();
		this.nombre = st.nextToken();
		this.tipo   = st.nextToken();
		this.saldo  = Integer.parseInt(st.nextToken());
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
	
	public String toString()
	{
		return this.nocta+"_"+this.nombre+"_"+this.tipo+"_"+this.saldo;
	}
    
    public String toSQLString(){
        return "'" + this.nocta + "','" + this.nombre+"','"+this.tipo+"',"+this.saldo+"";
    }
}