import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.Iterator;

public class UserListThread extends Thread {
	ObjectInputStream ois = null;
	IDList idl = null;
	
	ChatForm cf = null;
	Socket s = null;
	Iterator<String> iter = null;
	
	public UserListThread(ChatForm cf, Socket s) {
		this.cf = cf;
		this.s = s;
	}
	
	@Override
	public synchronized void run() {
		System.out.println("@run ����");
		try {
			ois = new ObjectInputStream(s.getInputStream());
			while(true) {
				idl = (IDList) ois.readObject();
				iter = idl.idList.iterator();
				while(iter.hasNext()) {
					cf.listTA.append(iter.next() + "\n");
				}
			}
		} catch(Exception e) {
			System.out.println("#UserList Run Exception");
			e.printStackTrace();
		}
	}
}