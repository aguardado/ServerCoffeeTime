package es.urjc.mov;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private ServerSocket socket;
	ListMsg listmsg;
	
	 
    public Server (String port) throws IOException{
    	this.socket = new ServerSocket(Integer.parseInt(port));
    	listmsg = new ListMsg();
    }
    
    public void start() {
    	System.err.println("Servidor arrancado");
    	for(;;){
    		try{
    			Socket clientSocket = this.socket.accept();
    			System.err.println("Cliente aceptado");
    		
    			new Thread(new Serverproc(clientSocket, listmsg)).start();
    			
    		}catch(Exception e){
    			System.err.println("Error aceptar cliente");
    			e.printStackTrace();
    		}
    	}
    }
	
    public static void main(String args[]){

    	Server server = null;
    	try{
    		server = new Server("9999");
        	server.start();
    	}catch (IOException e){
    		System.err.println("Error arrancar server");
    		e.printStackTrace();
    	}
    	
    }
}
