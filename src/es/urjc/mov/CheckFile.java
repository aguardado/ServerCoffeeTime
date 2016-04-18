package es.urjc.mov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class CheckFile {
	
	public int checkInfoOk(String name, String pswd){
		int status = 0;
		try{
			FileReader fr = new FileReader("InfoUsersCoffe.txt");
			BufferedReader bf = new BufferedReader(fr);
			
			String sCadena;
			while ((sCadena = bf.readLine())!=null) {
				StringTokenizer stringTokenizer = new StringTokenizer(sCadena);
				while (stringTokenizer.hasMoreElements()) {
					String usuario = stringTokenizer.nextElement().toString();
					String contraseña = stringTokenizer.nextElement().toString();
					
					StringBuilder sb = new StringBuilder();
					sb.append("\nUUSuario : " + usuario);
					sb.append("\nCCOntraseña : " + contraseña);
					//System.out.println("Usuario_check: " + stringTokenizer.);
					//System.out.println("Contraseña_check: " + stringTokenizer.countTokens());
				}
				
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}

}
