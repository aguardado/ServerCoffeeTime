package es.urjc.mov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CheckFile {
	
	public static int checkInfoOk(String name, String pswd){
		int status = 0;
		BufferedReader bf = null;
		try{
			FileReader fr = new FileReader("InfoUsersCoffe.txt");
			bf = new BufferedReader(fr);
			
			String sCadena;
			while ((sCadena = bf.readLine())!=null) {
				//System.err.println("\nLinea : " + sCadena);
				String tokens[] = sCadena.split(" ");
				String user = tokens[0];
				String pass = tokens[1];
				
				if(user.equals(name)  && pass.equals(pswd)){
					status = 1; 
					break;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (bf != null){
					bf.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return status;
	}
	
	public static int checkUser(String origen, String destino){
		int status = 0;
		BufferedReader bf = null;
		try{
			FileReader fr = new FileReader("InfoUsersCoffe.txt");
			bf = new BufferedReader(fr);
			
			String sCadena;
			while ((sCadena = bf.readLine())!=null) {
				String tokens[] = sCadena.split(" ");
				String user = tokens[0];
			
				if((user.equals(origen)) && (user.equals(destino))){
					status = 1; 
					break;
				}else if(user.equals(destino)){
					status = 2;
					break;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (bf != null){
					bf.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return status;
	}

}
