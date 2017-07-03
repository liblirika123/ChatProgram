import java.io.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.*;
import java.awt.event.*; 
 
class SimpleNotePad extends WindowAdapter implements ActionListener {
  private Frame f;
  private TextArea pad;
  private FileDialog loadDl;
  private FileDialog saveDl;
  private MenuBar mb;
  private Menu newItem;
  private Menu open;
  private Menu save;
  private Menu saveAs;
  
  // 메뉴 아이템 객체 변수
  private MenuItem nf;
  private MenuItem op;
  private MenuItem sa;
  
  private Dimension dim1;
  private Dimension dim2;
  private int xpos;
  private int ypos;
  private File ffile;
  private PrintWriter out;
  private BufferedReader in;
  private String filePath;
  private String str;
  private String loadedStr;
  private String saveStr;
  
  public SimpleNotePad(){
   this.f = new Frame("제목 없음 - 메모장");
   this.pad = new TextArea("", 1, 1, pad.SCROLLBARS_BOTH);
   this.loadDl = new FileDialog(f, "열기", loadDl.LOAD);
   this.saveDl = new FileDialog(f, "다른 이름으로 저장", saveDl.SAVE);
  
   this.mb = new MenuBar();
   this.newItem = new Menu("New");
   this.open = new Menu("Open");
   this.save = new Menu("Save");
   this.saveAs = new Menu("SaveAs");
   
   // 메뉴아이템 객체 생성
   nf = new MenuItem("새메모 열기");
   op = new MenuItem("파일 열기");
   sa = new MenuItem("파일 저장");
   
   init();
   start();
   
   f.setSize(600, 400);
   this.dim1 = Toolkit.getDefaultToolkit().getScreenSize();
   this.dim2 = f.getSize();
   this.xpos = (int)(dim1.getWidth() / 2 - dim2.getWidth() / 2);
   this.ypos = (int)(dim1.getHeight() / 2 - dim2.getHeight() / 2);
   
   f.setLocation(xpos, ypos);
   f.setVisible(true);
   this.out = null;
   this.in = null;
 }
 
 public void init(){
	 newItem.add(nf);
	 open.add(op);
	 save.add(sa);
	 
	 mb.add(newItem);
	 mb.add(open);
	 mb.add(save);
	 mb.add(saveAs);
	 f.setMenuBar(mb);
   
	 pad.setFont(new Font("Fixedsys", Font.PLAIN, 15));
	 f.add(pad);
 }
 
 public void start(){
	 f.addWindowListener(this);
	 nf.addActionListener(this);
	 op.addActionListener(this);
	 sa.addActionListener(this);
 }
 
 @Override
 public void actionPerformed(ActionEvent e) {
 	MenuItem menu = (MenuItem)e.getSource();
 	String str = menu.getLabel();
 	
 	System.out.println("str : " + str);
 	if(str.equals("새메모 열기")) {
 		pad.setText(" ");
 		f.setTitle("제목 없음 - 메모장");
 	} else if(str.equals("파일 열기")) {	
 		loadDl.setVisible(true);
 	    filePath = loadDl.getDirectory() + loadDl.getFile();
 	    loadFromFile();
 	    pad.setText(saveStr);
 	    saveStr = "";
 	    f.setTitle(filePath + " - 메모장");
 	} else if(str.equals("파일 저장")) {	
 		saveDl.setVisible(true);
 	    filePath = saveDl.getDirectory() + saveDl.getFile();
 	    str = pad.getText();
 	    saveToFile();
 	    f.setTitle(filePath + " - 메모장");
 	}
 }
 
 @Override
 public void windowClosing(WindowEvent we){
   f.dispose();
 }
 
 // File I / 0 관련 메서드
 public void saveToFile(){
	 ffile = new File(filePath);
	 try {
		 out = new PrintWriter(new BufferedWriter(new FileWriter(ffile)));
	     out.write(str);
	     out.close();
	 } catch(Exception e){
		 e.printStackTrace();
	 }
 }
 
 public void loadFromFile(){
	 ffile = new File(filePath);
	 saveStr = "";
	 try {
		 in = new BufferedReader(new FileReader(ffile));
		 while((loadedStr = in.readLine()) != null){
			 saveStr += loadedStr + "\n";
		 }
		 in.close();
	 } catch(Exception e){
	    e.printStackTrace();
	 }
 }
 
 public static void main(String[] args){
   new SimpleNotePad();
 } 
}