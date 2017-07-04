
public class ClientProgram {
	LoginForm lf = new LoginForm(this);
	ChatForm  cf = new ChatForm();
	boolean flag = true;
	
	public ClientProgram() {
		openCF();
		//openLF();
		//checkLogin();
	}
	
	public void checkLogin() {
		while(flag) {try{ Thread.sleep(1000);} catch(Exception e) {}}
		openCF();
	}
	
	public void openLF() {
		this.lf.setVisible(true);
	}
	
	public void openCF() {
		cf.setVisible(true);
		cf.startClient();
	}
	
	public ClientProgram getCP() {
		return this;
	}
}
