package es.urjc.mov;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;


public class Serverproc implements Runnable{
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	ListMsg listmsg;
	
	
	public Serverproc(Socket socketclient, ListMsg listmsg)throws Exception{
		this.socket = socketclient;
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			this.listmsg = listmsg;
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
			out.flush();
			if(checkLog == 0){
				System.err.println("Usuario y contraseña incorrectos");
			}else{
				System.err.println("Usuario y contraseña correctos");
			}
			
		}catch (Exception e){
			throw new RuntimeException(this + ": " + e);
		}
	}
	
	
	
	public void readChat(){
		try{
			int sizeUsuarioOrigen  = in.readInt();
			byte[] bufUsuarioOrigen = new byte[sizeUsuarioOrigen];
			
			in.read(bufUsuarioOrigen);
			String usuarioOrigen = new String(bufUsuarioOrigen, "UTF-8");
			System.err.println("\nUsuario Origen: " + usuarioOrigen);
			
			int sizeUsuarioDestino  = in.readInt();
			byte[] bufUsuarioDestino = new byte[sizeUsuarioDestino];
			
			in.read(bufUsuarioDestino);
			String usuarioDestino = new String(bufUsuarioDestino, "UTF-8");
			System.err.println("\nUsuario Destino: " + usuarioDestino);
			
			int sizeMensaje  = in.readInt();
			byte[] bufMensjae = new byte[sizeMensaje];
			
			in.read(bufMensjae);
			String msg = new String(bufMensjae, "UTF-8");
			System.err.println("\nMensaje: " + msg);
			
			
			int checkUser = CheckFile.checkUser(usuarioOrigen, usuarioDestino);
			if(checkUser == 1){
				System.err.println("No se pueden enviar mensajes a uno mismo");
				out.writeByte(Msg.USERS_EQUALS);
				out.flush();
			}else if(checkUser == 2){
				System.err.println("Mensaje enviado correctamente");
				out.writeByte(Msg.OK);
				out.flush();
				msg = msg + "]" + usuarioOrigen;
				listmsg.addNewMsg(usuarioDestino, msg);
				out.writeByte(Msg.OK);
				out.flush();
				
			}else{
				System.err.println("No existe el usuario destino");
				out.writeByte(Msg.ERROR);
				out.flush();
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendMsgs(){
		List<String> listmsgs;
		int length;
		try{
			int sizeUsuario  = in.readInt();
			byte[] bufUsuario = new byte[sizeUsuario];
			
			in.read(bufUsuario);
			String usuario = new String(bufUsuario, "UTF-8");
			System.err.println("\nUsuario que pide sus mensajes: " + usuario);
			
			listmsgs = listmsg.getMsg(usuario);
			if(listmsgs != null){			
				out.writeByte(Msg.OK);
				out.writeInt(listmsgs.size());
				for(String msg : listmsgs){
					length = msg.length();
					out.writeInt(length);
					out.write(msg.getBytes("UTF-8"));
				}
				//listmsg.deleteMsg(usuario);
			}else{
				out.writeByte(Msg.ERROR);
			}
			
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
					case Msg.CHAT:
						readChat();
						break;
					case Msg.GET_MSGS:
						sendMsgs();
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
