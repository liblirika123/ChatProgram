
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class LoginForm extends JFrame implements ActionListener {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final int WIN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final int WIN_HEIGHT =  (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private static final int FRAME_WIDTH = 300;
	private static final int FRAME_HEIGHT = 200;
	private static final int FRAME_XPOS = WIN_WIDTH / 2 - FRAME_WIDTH / 2;
	private static final int FRAME_YPOS = WIN_HEIGHT / 2 - FRAME_HEIGHT / 2;
	
	private JLabel userLab, passLab; // 라벨
	private JTextField userTF; // ID 입력 창
	private JPasswordField passTF; // PW 입력 창
	private JButton loginBtn, cancelBtn; // 로그인, 취소 버튼
	private String id = "root"; // 관리자 ID
	private String pw = "password"; // 관리자 PW
	
	ClientProgram cp = null;
	
	public LoginForm(ClientProgram cp) {
		userLab = new JLabel("User");
		userLab.setBounds(10, 25, 80, 20);
		userLab.setFont(new Font("Arial",Font.BOLD, 15));
		add(userLab);
		passLab = new JLabel("Password");
		passLab.setBounds(10, 65, 80, 20);
		passLab.setFont(new Font("Arial", Font.BOLD, 15));
		add(passLab);
		userTF = new JTextField(10);
		userTF.setBounds(130, 25, 140, 25);
		add(userTF);
		passTF = new JPasswordField(10);
		passTF.setBounds(130, 65, 140, 25);
		add(passTF);
		loginBtn = new JButton("Login");
		loginBtn.setBounds(10, 105, 100, 25);
		loginBtn.addActionListener(this);
		add(loginBtn);		
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(170, 105, 100, 25);
		cancelBtn.addActionListener(this);
		add(cancelBtn);
		
		setTitle("Login Page");
		setLayout(null);
		setBounds(FRAME_XPOS, FRAME_YPOS, FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.cp = cp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		String label = btn.getLabel();
		
		if(label.equals("Login")) {
			String userText = userTF.getText(); // 입력된 ID 값
			String passText = passTF.getText(); // 입력된 PW 값
			
			//// 로그인 성공여부 확인 ////
			if(userText.equals(id) && passText.equals(pw)) {
				JOptionPane.showMessageDialog(null, "로그인 성공!");
				cp.flag = false;
				hide();
			} else {
				JOptionPane.showMessageDialog(null, "로그인 실패");
			}
		} else if(label.equals("Cancel")) {
			System.exit(1);
		}
	}
}
