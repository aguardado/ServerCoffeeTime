package es.urjc.mov;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListMsg {
	HashMap<String, List<String>> listOfMsg;
	

	
	public ListMsg(){
		listOfMsg = new HashMap<String, List<String>>();
	}
	
	
	public synchronized void addNewMsg(String userDest, String msg){
		List<String> listMsgs = listOfMsg.get(userDest);
		if(listMsgs == null){
			listMsgs = new ArrayList<String>();
		}
		listMsgs.add(msg);
		listOfMsg.put(userDest, listMsgs);
	}
		
	

	public synchronized void deleteMsg(String userDest){
		if(listOfMsg.get(userDest) !=  null){
			this.listOfMsg.remove(userDest);
		}
	}
	
	
	public synchronized List<String> getMsg(String userDest){
		List<String> newMsgs;
		
		newMsgs = listOfMsg.get(userDest);
		
		return newMsgs;
	}

}
