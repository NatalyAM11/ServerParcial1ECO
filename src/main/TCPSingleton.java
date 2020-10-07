package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

public class TCPSingleton extends Thread {
	
	private static TCPSingleton unicaInstancia;
	
	
	//constructor
	private TCPSingleton () {
		
	}
	
	private ServerSocket server;
	
	//writer and reader
	BufferedWriter writer;
	BufferedReader reader;
	
	Gson gson;
	Jugador objeto;
	
	
	//getInstance
	public static TCPSingleton getInstance() {
		if (unicaInstancia==null) {
			unicaInstancia= new TCPSingleton();
			unicaInstancia.start();
		}
		
		return unicaInstancia;
		
	}
	
	
	
	//Observer
	private OnMessageListener observer;
	
	public void setObserver(OnMessageListener observer) {
		this.observer=observer;
	}
	
	
	
	//Hilo conexion tcp
	public void run () {
		
		try {
			
			//Conexión
			server = new ServerSocket(5000);
			System.out.println("Esperando conexión");
			Socket socket= server.accept();
			System.out.println("Conectado con el usuario");
			
			
			//Input Output
			OutputStream os= socket.getOutputStream();
			InputStream is=socket.getInputStream();
			
			
			//Writer reader
			writer=new BufferedWriter(new OutputStreamWriter(os));
			reader= new BufferedReader(new InputStreamReader(is));
			
			
			//Recibo el mensaje 
		    recibir();
		   		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	
	
	
	
	public void recibir() {
		
		
		new Thread (
				()->{
					
					try {
						
						
					//Recepción mensaje
				    while(true) {
				    	System.out.println("Esperando mensaje");
				    	
				    	
				    	String line;
						line = reader.readLine();
				    	
				    	
				    	gson= new Gson();
				    	objeto= gson.fromJson(line, Jugador.class);
				    	
				    	if(line!= null) {
					    	observer.onMessage(line);
					    }
				    		
				    	System.out.println(objeto.getX());
				    	System.out.println(objeto.getY());
				    	System.out.println(line);
				   
				    }
				    
				    
				    
				    } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
				
				).start();
	
		
	}

	

	
}
