package es.urjc.mov;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Serverproc implements Runnable{
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	
	
	public Serverproc(Socket socketclient){
		this.socket = socketclient;
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
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
			int checkLog = CheckFile.checkInfoOk(usuario, contraseña);
			out.writeInt(checkLog);
			if(checkLog == 0){
				System.err.println("Usuario y contraseña incorrectos");
			}else{
				System.err.println("Usuario y contraseña correctos");
			}
			
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
	
	@Override
	public void run() {
			try {
				byte header = in.readByte();
				switch(header){
				case Msg.LOG:
					readLog();
					break;
				default:
					System.err.println("ERROR: this mesage dont exist");
				}
			} catch (EOFException o) {
			} catch (Exception e) {
				e.printStackTrace();
			}	
	}

}
