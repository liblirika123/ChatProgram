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
	BufferedReader br = null; // 소켓 출력 버퍼
	PrintWriter pw = null; // 소켓 입력
	String id = null;
	StringBuffer tranMsg = null; // 송신하기위한 스트링버퍼
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
			System.out.println("#Socket 생성 or I/O 스트림 생성에서 예외 발생");
		}
	}
	
	// 채팅서 사용할 닉네임 설정 메서드
	public void login() {
		try {
			this.pw = new PrintWriter(s.getOutputStream());
			System.out.println("login");
			id = "aaa";
			pw.println(id);
			pw.flush();
			pw.close();
			tranMsg.append("#" + id.toString() + "님이 방에 입장하셨습니다.");
		} catch(Exception e) {
			System.out.println("%");
			System.out.println(e);
		}
	}
	
	public void ready() {
		try {
			System.out.println("#대기중...");
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
	
	// 메세지 수신
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
	
	// 메세지 송신
	public void transfer() {
		System.out.println("transfer");
		try {
			this.pw = new PrintWriter(s.getOutputStream());
			while(flag) {
				// send버튼이 눌릴때까지 while문을 이용해 대기
				while(tranMsg.toString().equals("")) {
					try {
						Thread.sleep(100); // 0.5초씩 딜레이
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