import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ChatForm extends JFrame implements ActionListener {
	private Toolkit tool = Toolkit.getDefaultToolkit();
	private final int WIN_WIDTH = (int)tool.getScreenSize().getWidth();
	private final int WIN_HEIGHT = (int)tool.getScreenSize().getHeight();
	private final int FRAME_WIDTH = 600;
	private final int FRAME_HEIGHT = 400;
	private final int FRAME_XPOS = WIN_WIDTH / 2 - FRAME_WIDTH / 2;
	private final int FRAME_YPOS = WIN_HEIGHT / 2 - FRAME_HEIGHT / 2;
	
	private JPanel leftP, rightP; // 채팅패널, 유저 리스트패널
	private JPanel screenP, inputP; // 스크린 패널, 입력패널, 유저리스트패널
	private JPanel listP, clockP;
	private JScrollPane pane;
	JTextArea screen, listTA; // 채팅창 스크린, 유저 리스트
	private JTextArea inputTA; // 메시지 입력창
	private JButton sendBtn; // 메시지 보내기 버튼
	private JButton closeBtn; // 종료 버튼
	private Border border = BorderFactory.createLineBorder(Color.black);
	private ClientChatter chatter = null; // 클라이언트
	
	public ChatForm() {
		setTitle("Chat Form");
		setLayout(new BorderLayout());
		
		leftP = new JPanel();
		leftP.setPreferredSize(new Dimension(400, 80));
		leftP.setLayout(new BorderLayout());
		leftP.setBorder(border);
		add(leftP, BorderLayout.WEST);
		
		screenP = new JPanel();
		screenP.setLayout(new BorderLayout());
		screenP.setBorder(border);
		leftP.add(screenP, BorderLayout.CENTER);
		inputP = new JPanel();
		inputP.setLayout(null);
		inputP.setPreferredSize(new Dimension(200, 80));
		inputP.setBackground(new Color(190, 190, 190));
		inputP.setBorder(border);
		leftP.add(inputP, BorderLayout.SOUTH);
		
		screen = new JTextArea();
		screen.setEnabled(false);
		// disabled 상태일때의 글꼴 설정
		screen.setDisabledTextColor(Color.black);
		pane = new JScrollPane(screen, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		screenP.add(pane);
		inputTA = new JTextArea();
		inputTA.setBounds(10, 10, 270, 60);
		inputTA.setBorder(border);
		inputP.add(inputTA);
		sendBtn = new JButton("보내기");
		sendBtn.setBounds(290, 10, 80, 25);
		sendBtn.setBorder(border);
		sendBtn.addActionListener(this);
		inputP.add(sendBtn);
		closeBtn = new JButton("종료");
		closeBtn.setBounds(290, 45, 80, 25);
		closeBtn.setBorder(border);
		closeBtn.addActionListener(this);
		inputP.add(closeBtn);
		
		rightP = new JPanel();
		rightP.setPreferredSize(new Dimension(185, 80));
		rightP.setLayout(new BorderLayout());
		rightP.setBorder(border);
		add(rightP, BorderLayout.EAST);
		
		listP = new JPanel();
		listP.setLayout(new BorderLayout());
		listP.setPreferredSize(new Dimension(200, 280));
		listP.setBorder(border);
		rightP.add(listP, BorderLayout.NORTH);
		
		clockP = new JPanel();
		clockP.setPreferredSize(new Dimension(200, 80));
		clockP.setBackground(new Color(190, 190, 190));
		clockP.setBorder(border);
		rightP.add(clockP, BorderLayout.SOUTH);
		
		listTA = new JTextArea();
		listTA.setEnabled(false);
		listTA.setDisabledTextColor(Color.black);
		listTA.setBackground(new Color(240, 240, 240));
		pane = new JScrollPane(listTA, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		listP.add(pane, BorderLayout.CENTER);
		
		setBounds(FRAME_XPOS, FRAME_YPOS, FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		
		if(btn == sendBtn) {
			if(chatter.flag) {chatter.tranMsg.append("[" + chatter.id.toString() + "] : ");}
			chatter.tranMsg.append(getInputText());
			inputTA.setText("");
		} else if(btn == closeBtn) {
			chatter.tranMsg.append("#" + chatter.id.toString() + "님이 방을 나가셨습니다.");
			try {Thread.sleep(100);} catch(Exception ee) {}
			System.exit(0);
		}
	}
	
	public void startClient() {
		chatter = new ClientChatter(this);
	
		chatter.login();
		chatter.setIDL();
		chatter.start();
		chatter.transfer();
	}
	
	public String getInputText() {
		return inputTA.getText();
	}
}
