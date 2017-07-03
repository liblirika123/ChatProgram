import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.Iterator;

public class ClientChatter extends Thread{
	Socket s = null;
	BufferedReader br = null; // ���� ��� ����
	PrintWriter pw = null; // ���� �Է�
	String id = null;
	StringBuffer tranMsg = null; // �۽��ϱ����� ��Ʈ������
	boolean flag;
	
	ChatForm cf = null;
	IDList idl = null;
	
	UserListThread ult = null;
	
	public ClientChatter(ChatForm cf) {
		try {
			this.tranMsg = new StringBuffer("");
			this.flag = true;
			this.cf = cf;
			this.s = new Socket("localhost", 20000);
			this.ult = new UserListThread(cf, s);
		} catch(Exception e) {
			System.out.println(e);
			System.out.println("#Socket ���� or I/O ��Ʈ�� �������� ���� �߻�");
		}
	}
	
	// ä�ü� ����� �г��� ���� �޼���
	public void login() {
		try {
			this.pw = new PrintWriter(s.getOutputStream());
			System.out.println("login");
			id = "aaa";
			pw.println(id);
			pw.flush();
			pw.close();
			tranMsg.append("#" + id.toString() + "���� �濡 �����ϼ̽��ϴ�.");
		} catch(Exception e) {
			System.out.println("%");
			System.out.println(e);
		}
	}
	
	public void ready() {
		try {
			System.out.println("#�����...");
			String message = br.readLine();
			System.out.println(message);
		} catch(Exception e) {
			System.out.println("@");
			System.out.println(e);
		}
	}
	
	public void setIDL() {
		System.out.println("setIDL");
		ult.start();
	}
	
	// �޼��� ����
	@Override
	public void run() {
		System.out.println("run");
		String message = "";
		try {
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while(flag) {
				message = br.readLine();
				cf.screen.append(message + "\n");
				cf.screen.setCaretPosition(cf.screen.getText().length());
				System.out.println(message);
			}
			br.close();
		} catch(Exception e) {
			System.out.println("#");
			System.out.println(e);
		}
	}
	
	// �޼��� �۽�
	public void transfer() {
		System.out.println("transfer");
		try {
			this.pw = new PrintWriter(s.getOutputStream());
			while(flag) {
				// send��ư�� ���������� while���� �̿��� ���
				while(tranMsg.toString().equals("")) {
					try {
						Thread.sleep(100); // 0.5�ʾ� ������
					} catch(Exception e) {
						System.out.println("&");
						System.out.println(e);
					}
				}
				pw.println(tranMsg.toString());
				pw.flush();
				pw.close();
				tranMsg = new StringBuffer("");
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}