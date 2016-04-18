package es.urjc.mov;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket socket;
	
	public Server (String port) throws IOException{
		this.socket = new ServerSocket(Integer.parseInt(port));
	}
	
	public void start(){
		System.err.println("Servidor arrancado");
		for(;;){
			try{
				Socket clientSocket = this.socket.accept();
				System.err.println("Cliente aceptado");
				
				new Thread(new Serverproc(clientSocket)).start();
			}catch(Exception e){
				System.err.println("Error aceptar cliente");
				e.printStackTrace();
				
			}
		}
	}
	
	public static void main(String args[]){
    	Server server = null;
    	try{
    		server = new Server("8080");
        	server.start();
    	}catch (IOException e){
    		System.err.println("Error arrancar server");
    		e.printStackTrace();
    	}
    	
    }

}
