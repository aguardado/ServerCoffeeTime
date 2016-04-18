package es.urjc.mov;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Serverproc implements Runnable{
	
	private Socket socket;
	private DataInputStream in;
	//private DataOutputStream out;
	
	
	
	public Serverproc(Socket socketclient){
		this.socket = socketclient;
		try {
			in = new DataInputStream(socket.getInputStream());
			//out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//checkInfoOk(String name, String pswd)
	//http://www.mkyong.com/java/java-stringtokenizer-example/
	
	public void readLog(){
		try{
			int sizeUsuario  = in.readInt();
			byte[] bufUsuario = new byte[sizeUsuario];
			
			in.read(bufUsuario);
			String usuario = new String(bufUsuario, "UTF-8");
			System.err.println("\nUsuario: " + usuario);
			
			int sizeContraseña  = in.readInt();
			byte[] bufContraseña = new byte[sizeContraseña];
			
			in.read(bufContraseña);
			String contraseña = new String(bufContraseña, "UTF-8");
			System.err.println("\nContraseña: " + contraseña);
			
			
		}catch (Exception e){
			throw new RuntimeException(this + ": " + e);
		}
	}
	
	public void readInfoClient(){
		try{
			int size  = in.readInt();
			byte[] buf = new byte[size];
			
			in.read(buf);
			String msg = new String(buf, "UTF-8");
			System.err.println(msg);
		}catch (Exception e){
			throw new RuntimeException(this + ": " + e);
		}
	
		
	}
	//hacer un finally para cerrar la conexion, sino dejo la conexion abierta

	public void run() {
		for(;;){
			try {
				byte header = in.readByte();
				switch(header){
				case Msg.USER:
					System.err.println("USER: ");
					readInfoClient();
					break;
				case Msg.PSWD:
					System.err.println("CONTRASEÑA: ");
					readInfoClient();
					break;
				case Msg.LOG:
					readLog();
					break;
				default:
					System.err.println("ERROR: this mesage dont exist");
				}
			} catch (EOFException e) {
				break;
			} catch (Exception e) {
				System.err.println("OJO");
				throw new RuntimeException(this + ": " + e);
				
			}	
		}
	}

}
