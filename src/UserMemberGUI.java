import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class UserMemberGUI extends JDialog implements ActionListener {
	
	JPanel mframe;

	//String strgid, strpackname, strout_p, strin_p, str_outdate, strindate, strterm, strprice, strea, strsearch;

	// JButton btnadd, btnupdate, btndelete, btncancel,btnsearch2;
	// JPanel ptext, p1, p2, p3, p4, p5, p6, p7, p8, p9, pbutton, psearch;

	//
	// JPanel
	//JLabel[] empty = new JLabel[10];// 공백 ㅇㅅㅇ
	 JLabel lempty1 = new JLabel("");   //공백
	 JLabel lempty2 = new JLabel("");   //공백
	 JLabel lempty3 = new JLabel("");   //공백
	 JLabel lempty4 = new JLabel("");   //공백
	 JLabel lempty5 = new JLabel("");   //공백
	 JLabel lempty6 = new JLabel("");   //공백
	 JLabel lempty7 = new JLabel("");   //공백
	 JLabel lempty8 = new JLabel("");   //공백
	 JLabel lempty9 = new JLabel(" ");   //공백
	 JLabel lempty10 = new JLabel(" ");   //공백
	 JLabel lempty0 = new JLabel(" ");   //공백
	 JLabel lempty00 = new JLabel(" ");   //공백
	 
	 JLabel left1 = new JLabel("        ");
	 JLabel right1 = new JLabel("            ");
	
	 
	String gender2 = null;
	JPanel idp = new JPanel(new BorderLayout());
//	bg = new ImageIcon("bg.png");
//	loginPanel = new JPanel() {
//		public void paintComponent(Graphics g) {
//			g.drawImage(bg.getImage(), 0, 0, null);
//			setOpaque(false);
//			super.paintComponent(g);
//		}
//	};

	JPanel plabel = new JPanel(new GridLayout(11, 1, 10, 10));
	JPanel ptext = new JPanel(new GridLayout(11, 1, 10, 10));
	JPanel leftempty = new JPanel(new GridLayout(10, 1, 100, 100));
	//JPanel rightempty = new JPanel(new GridLayout(10, 1, 100, 100));
	JPanel left = new JPanel(new BorderLayout());
	JPanel right = new JPanel(new GridLayout(10, 1, 10, 10));

	JPanel peast = new JPanel(new GridLayout(10, 1, 10, 10));
	JPanel psouth = new JPanel();// 버튼그룹함

	//JPanel p1 = new JPanel(new GridLayout(9, 1, 5, 5));

	//JPanel p2 = new JPanel(new GridLayout(9, 1, 5, 5));

	//JPanel p3 = new JPanel();

	JLabel id11 = new JLabel("필수입력란(*)");
	JLabel id1 = new JLabel("아이디(*)");
	JLabel lpass1 = new JLabel("비밀번호(*)");
	JLabel lpass2 = new JLabel("비번확인(*)");
	JLabel lname = new JLabel("이    름(*)");
	JLabel lbirth = new JLabel("생년월일");
	JLabel lph = new JLabel("전화번호(*)");
	JLabel lph1 = new JLabel("-");
	JLabel lph2 = new JLabel("-");
	JLabel laddr = new JLabel("주    소");
	JLabel lgender = new JLabel("성   별(*)");
	// 이메일 추가
	JLabel lemail = new JLabel("이메일");
	JLabel lemail2 = new JLabel("@");	
	

	JTextField tf_id = new JTextField(10);
	//tf_id.setBorder(new LineBorder(Color.red));
	
	
	JPasswordField tf_pass1 = new JPasswordField(15);
	JPasswordField tf_pass2 = new JPasswordField(15);
	JTextField tf_name = new JTextField(10);
	JTextField tf_birth1 = new JTextField(6);

	JTextField tf_ph1 = new JTextField(8);
	JTextField tf_ph2 = new JTextField(8);
	JTextField tf_addr = new JTextField();
	// 이메일
	JTextField tf_email1 = new JTextField(8);
	JTextField tf_email2 = new JTextField(8);
	// 라디옹ㅇㅇㅇㅇㅇ
	JPanel pgender = new JPanel();
	ButtonGroup gender = new ButtonGroup();
	JRadioButton male = new JRadioButton("남");
	JRadioButton female = new JRadioButton("여");

	/*
	 * JPanel pforeign = new JPanel(); ButtonGroup foreign = new ButtonGroup();
	 * JLabel llocals = new JLabel("내국인"); JRadioButton locals = new
	 * JRadioButton(); JLabel lforeigner = new JLabel("외국인"); JRadioButton
	 * foreigner = new JRadioButton();
	 */
	// 버튼 라벨 2개필요
	JButton confirm = new JButton("가입");
	JButton reset = new JButton("취소");

	IndexMain me;

	JPanel idck = new JPanel(new BorderLayout());
	//ImageIcon idckbtn = new ImageIcon("idck.png");
	JButton idck1 = new JButton("중복 확인");

	JPanel email = new JPanel(new BorderLayout());

	JPanel ph = new JPanel(new GridLayout(1,3));

	String[] comboName1 = { "010", "011", "018", "017", "070" };
	JComboBox comboPH = new JComboBox(comboName1);

	// 이메일 콤보 박스
	String[] emailcombo = { "직접입력", "naver.com", "hanmail.net", "icloud.com","gmail.com" };
	JComboBox cm = new JComboBox(emailcombo);// combo mail
	UserMemberDAO dao = new UserMemberDAO();
	
	/*
	 * JPanel birth = new JPanel(new FlowLayout()); String[] comboName2 = {
	 * Integer.toString(a) }; JComboBox comboPH = new JComboBox(comboName2);
	 */
	

	public UserMemberGUI(IndexMain me, String index) {
		super(me, index);
		this.me = me;
		// 왼쪽 이름 아래로 쭈욱
		setLayout(new BorderLayout());
	
		//tf_id.setBorder(new LineBorder(Color.red));
		tf_id.setBorder(new LineBorder(new Color(250, 242, 55), 2));  //180,215,254
		tf_pass1.setBorder(new LineBorder(new Color(250, 242, 55), 2));
		tf_pass2.setBorder(new LineBorder(new Color(250, 242, 55), 2));
		tf_name.setBorder(new LineBorder(new Color(250, 242, 55), 2));
		tf_ph1.setBorder(new LineBorder(new Color(250, 242, 55), 2));
		tf_ph2.setBorder(new LineBorder(new Color(250, 242, 55), 2));
		tf_birth1.setBorder(new LineBorder(Color.white));
		tf_addr.setBorder(new LineBorder(Color.white));
		tf_email1.setBorder(new LineBorder(Color.white));
		tf_email2.setBorder(new LineBorder(Color.white));

		Font mfont = new Font("맑은 고딕", Font.BOLD, 13);
		id11.setFont(mfont);				id11.setForeground(Color.white);	
		id1.setFont(mfont);				id1.setForeground(Color.white);				
		idp.setFont(mfont);				idp.setForeground(Color.white);	
		lpass1.setFont(mfont);			lpass1.setForeground(Color.white);	
		lpass2.setFont(mfont);			lpass2.setForeground(Color.white);	
		lname.setFont(mfont);			lname.setForeground(Color.white);	
		lbirth.setFont(mfont);			lbirth.setForeground(Color.white);	
		lph.setFont(mfont);				lph.setForeground(Color.white);	
		lemail.setFont(mfont);			lemail.setForeground(Color.white);	
		laddr.setFont(mfont);			laddr.setForeground(Color.white);	
		lgender.setFont(mfont);			lgender.setForeground(Color.white);	
		male.setFont(mfont);				male.setForeground(Color.white);	
		female.setFont(mfont);			female.setForeground(Color.white);	
		Font mfont2 = new Font("맑은 고딕", Font.BOLD, 12);
		cm.setFont(mfont2);
		cm.setBackground(Color.white);
		comboPH.setFont(mfont2);
		comboPH.setBackground(Color.white);

		plabel.add(lempty0,"North");
		plabel.add(id11);
		plabel.add(id1);
		plabel.add(idp);
		plabel.add(lpass1);
		plabel.add(lpass2);
		plabel.add(lname);
		plabel.add(lbirth);
		plabel.add(lph);
		// plabel.add(lph1);
		// plabel.add(lph2);
		plabel.add(lemail);
		plabel.add(laddr);
		plabel.add(lgender);

		// 중복확인 idck1
		//Font bfont = new Font("맑은 고딕", Font.BOLD, 12);
		//idck1.setBackground(new java.awt.Color(102, 222, 232));
		//idck1.setBackground(Color.white);
		//idck1.setBorder(new LineBorder(new Color(154, 154, 154)));
		//idck1.setForeground(Color.white);
		//idck1.setFont(bfont);
	/*	for(int i = 0;i<20;i++) 	{Color.white
			empty[i] = new JLabel("");
		}   */
		
		// 가운데 쭈욱
		ptext.add(lempty00,"North");
		ptext.add(lempty10);
		idp.add(tf_id,"Center");
		idp.add(idck1,"East");
		ptext.add(idp);
		ptext.add(tf_pass1);
		ptext.add(tf_pass2);
		ptext.add(tf_name);
		ptext.add(tf_birth1);
		ptext.add(tf_addr);
		// 전화
		JPanel php1 = new JPanel(new BorderLayout());
		JPanel php2 = new JPanel(new BorderLayout());

		ph.add(comboPH);
		php1.add(lph1,"West");
		php1.add(tf_ph1,"Center");
		ph.add(php1);
		php2.add(lph2,"West");
		php2.add(tf_ph2,"Center");
		ph.add(php2);
		ptext.add(ph);
		
		tf_pass1.setEchoChar('*');
		//tf_pass1.
		//tf_pass1.setBorder(new LineBorder(Color.red));		
				
		tf_pass2.setEchoChar('*');
		//tf_pass2.setBorder(new LineBorder(Color.red));
		// 이메일
		
		JPanel emailp1 = new JPanel(new BorderLayout());

		emailp1.add(tf_email1,"West");
		emailp1.add(lemail2,"Center");
		emailp1.add(tf_email2,"East");
		email.add(emailp1,"Center");
		email.add(cm,"East");
		ptext.add(email);
		// 주소
		ptext.add(tf_addr);
		// 라디오버튼 그룹
		gender.add(male);
		gender.add(female);
		pgender.add(male);
		pgender.add(female);
		ptext.add(pgender);
		// 아이디 체크쪽 쭈욱
		
		peast.add(lempty9);
		//peast.add(idck1);
		peast.add(lempty1);
		peast.add(lempty2);
		peast.add(lempty3);
		peast.add(lempty4);
		peast.add(lempty5);
		peast.add(lempty6);
		peast.add(lempty7);
		peast.add(lempty8);
		
		leftempty.add(left1);
		right.add(right1);

		psouth.add(confirm);
		psouth.add(reset);

		ImageIcon mbg = new ImageIcon("bg.png");
		mframe = new JPanel(){
			public void paintComponent(Graphics g) {
				g.drawImage(mbg.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		JPanel p = new JPanel(new BorderLayout()); 
		left.add(leftempty, "West");
		left.add(plabel, "Center");
		p.add(left,"West");
		p.add(ptext, "Center");
		p.add(right, "East");
		p.add(psouth, "South");
		p.setPreferredSize(new Dimension(428,450));
		mframe.add(p,"North");
		add(mframe);
		p.setBackground(new Color(255, 0, 0, 0));		
//		
		//add(peast, "East");
		ph.setBackground(new Color(255, 0, 0, 0));
		lph1.setForeground(Color.WHITE);
		lph2.setForeground(Color.WHITE);
		lemail2.setForeground(Color.WHITE);		
		php1.setBackground(new Color(255, 0, 0, 0));
		php2.setBackground(new Color(255, 0, 0, 0));
		email.setBackground(new Color(255, 0, 0, 0));
		emailp1.setBackground(new Color(255, 0, 0, 0));
		leftempty.setBackground(new Color(255, 0, 0, 0));
		left.setBackground(new Color(255, 0, 0, 0));
		ptext.setBackground(new Color(255, 0, 0, 0));
		male.setBackground(new Color(255, 0, 0, 0));
		female.setBackground(new Color(255, 0, 0, 0));
		pgender.setBackground(new Color(255, 0, 0, 0));
		plabel.setBackground(new Color(255, 0, 0, 0));
		psouth.setBackground(new Color(255, 0, 0, 0));
		right.setBackground(new Color(255, 0, 0, 0));
		
		
		idck1.addActionListener(this);
		confirm.addActionListener(this);
		reset.addActionListener(this);
		cm.addActionListener(this);

		//setBounds(500, 500, 427, 480);
		setBounds(500, 500, 940, 574);
		// JFrame 스크린 중앙에 실행
		Dimension frameSize =getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	@Override

	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		String sstr = cm.getSelectedItem().toString();
		// String email;
		String fieldName2 = cm.getSelectedItem().toString();
		// String email;//이메일 값을 그냥 스트링에 넣음
		String email = tf_email1.getText().trim() + " @ " + tf_email2.getText().trim();
		;// 콤보 박스 값 넣기

		if (sstr.trim().equals("naver.com")) {
			tf_email2.setText(sstr);
			email = tf_email1.getText().trim() + " @ " + tf_email2.getText().trim();
		} else if (sstr.trim().equals("hanmail.net")) {
			tf_email2.setText(sstr);
			email = tf_email1.getText().trim() + " @ " + tf_email2.getText().trim();
		} else if (sstr.trim().equals("icloud.com")) {
			tf_email2.setText(sstr);
			email = tf_email1.getText().trim() + " @ " + tf_email2.getText().trim();
		} else if (sstr.trim().equals("gmail.com")) {
			tf_email2.setText(sstr);
			email = tf_email1.getText().trim() + " @ " + tf_email2.getText().trim();
		} else if (sstr.trim().equals("직접입력")) {
			tf_email2.requestFocus();
			tf_email2.setText("");
			email = tf_email1.getText().trim() + " @ " + tf_email2.getText().trim();
		}

		/*
		 * String fieldName2 = cm.getSelectedItem().toString(); String email;
		 * if(fieldName2.trim().equals("naver.com")) {
		 * tf_email2.setText("naver.com"); email = tf_email2.getText().trim() +
		 * "@daum.net"; }else if(fieldName2.trim().equals("daum.net")) {
		 * tf_email2.setText(fieldName2); email = tf_email2.getText().trim() +
		 * "@daum.net"; }else if(fieldName2.trim().equals("icloud.com")) {
		 * tf_email2.setText(fieldName2); email = tf_email2.getText().trim() +
		 * "@icloud.com"; }
		 * 
		 * 
		 * /* if(e.getSource() == cm) { //텍스트에 띄우기 ... //항목의 반환형이 오브젝트의 참조값으로
		 * 반환되니 String으로 형변환 해줘야 합니다
		 * 
		 * String eeee = (String)cm.getSelectedItem(); tf_email2.getText(eeee);
		 * }
		 */
		// male female
		if (male.isSelected() == true) {
			gender2 = "M";
		} else if (female.isSelected() == true) {
			gender2 = "F";
		}

		if (obj == idck1) {
			// id 텍스트 박스에 값이 없으면 메세지 풀력,있으면 DB연동

			if (tf_id.getText().trim().equals("")) {
				messageBox(this, "ID를 입력해주세요.");
				tf_id.requestFocus();// 포커스 이동
			} else {
				if (dao.IdCheck(tf_id.getText())) {
					messageBox(this, tf_id.getText() + " 는 사용 가능합니다.");
					idck1.setEnabled(false);
					tf_id.setEditable(false);
				} else {
					messageBox(this, tf_id.getText() + "는 중복입니다.");
					tf_id.setText("");
					tf_id.requestFocus();
				}
			}
		

		} else if (obj == confirm) {
			if (!checkInput())
				return; // 유효성체크
			String fieldName = comboPH.getSelectedItem().toString();		

			if (fieldName.trim().equals("010")) {
				if (dao.memberInsert(tf_id.getText().trim(), tf_pass1.getText().trim(), tf_pass2.getText().trim(),
						tf_name.getText(), tf_birth1.getText(),
						fieldName + " - " + tf_ph1.getText().trim() + " - " + tf_ph2.getText().trim(), email,
						tf_addr.getText().trim(), gender2) > 0) {
					messageBox(this, tf_name.getText() + " 님 가입 축하드립니다.");
					dispose();
				}
			} else if (fieldName.trim().equals("011")) {

				if (dao.memberInsert(tf_id.getText().trim(), tf_pass1.getText().trim(), tf_pass2.getText().trim(),
						tf_name.getText(), tf_birth1.getText(),
						fieldName + " - " + tf_ph1.getText().trim() + " - " + tf_ph2.getText().trim(), email,
						tf_addr.getText().trim(), gender2) > 0) {
					messageBox(this, tf_name.getText() + " 님 가입 축하드립니다.");
					dispose();
				}

				else {
					messageBox(this, "가입되지 않았습니다.");
				}
			} else if (fieldName.trim().equals("018")) {

				if (dao.memberInsert(tf_id.getText().trim(), tf_pass1.getText().trim(), tf_pass2.getText().trim(),
						tf_name.getText(), tf_birth1.getText(),
						fieldName + " - " + tf_ph1.getText().trim() + " - " + tf_ph2.getText().trim(), email,
						tf_addr.getText().trim(), gender2) > 0) {
					messageBox(this, tf_name.getText() + " 님 가입 축하드립니다.");
					dispose();
				}

				else {
					messageBox(this, "가입되지 않았습니다.");
				}
			} else if (fieldName.trim().equals("017")) {
				System.out.println(email);
				if (dao.memberInsert(tf_id.getText().trim(), tf_pass1.getText().trim(), tf_pass2.getText().trim(),
						tf_name.getText(), tf_birth1.getText(),
						fieldName + " - " + tf_ph1.getText().trim() + " - " + tf_ph2.getText().trim(), email,
						tf_addr.getText().trim(), gender2) > 0) {
					messageBox(this, tf_name.getText() + " 님 가입 축하드립니다.");
					dispose();
				}
			} else if (fieldName.trim().equals("070")) {

				if (dao.memberInsert(tf_id.getText().trim(), tf_pass1.getText().trim(), tf_pass2.getText().trim(),
						tf_name.getText(), tf_birth1.getText(),
						fieldName + " - " + tf_ph1.getText().trim() + " - " + tf_ph2.getText().trim(), email,
						tf_addr.getText().trim(), gender2) > 0) {
					messageBox(this, tf_name.getText() + " 님 가입 축하드립니다.");
					dispose();
				}

				else {
					messageBox(this, "가입되지 않았습니다.");
				}
			}

			else {
				messageBox(this, "가입되지 않았습니다.");
				/*
				 * tf_pass1.setText(""); tf_pass2.setText("");
				 * tf_name.setText(""); tf_birth1.setText("");
				 * tf_ph1.setText(""); tf_ph2.setText(""); tf_addr.setText("");
				 */
				// male.setSelected(false);
				// female.setSelected(false);
			}
		}

		else if (obj == reset) {
			dispose();
		}
	}

	public boolean checkInput() {
		if (tf_id.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(UserMemberGUI.this, "아이디를 입력하세요!");
			tf_id.requestFocus();
			return false;
		} else if (idck1.isEnabled()) {
			JOptionPane.showMessageDialog(UserMemberGUI.this, "아이디 중복확인해주세요");
			tf_id.requestFocus();
			return false;
		} else if (!String.valueOf(tf_pass1.getText()).equals(String.valueOf(tf_pass2.getText()))) {
			JOptionPane.showMessageDialog(UserMemberGUI.this, "비밀번호가 일치하지 않습니다!");
			tf_pass1.requestFocus();
			return false;
		} else if (tf_id.getText().trim().length() > 16) {
			JOptionPane.showMessageDialog(UserMemberGUI.this, "아이디는 15자리 이하로 입력하세요!");
			tf_id.requestFocus();
			return false;
		} else if (String.valueOf(tf_pass1.getText()).trim().length() == 0) {
			JOptionPane.showMessageDialog(UserMemberGUI.this, "비밀번호를 입력하세요!");
			tf_pass1.requestFocus();
			return false;
		} else if (String.valueOf(tf_pass1.getText()).trim().length() > 16) {
			JOptionPane.showMessageDialog(UserMemberGUI.this, "비밀번호는 15자리 이하로 입력하세요!");
			tf_pass1.requestFocus();
			return false;
		} else if (tf_name.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(UserMemberGUI.this, "이름을 입력하세요!");
			tf_name.requestFocus();
			return false;
		} else if (tf_ph1.getText().trim().length() <3||tf_ph1.getText().trim().length() >4) {
			JOptionPane.showMessageDialog(UserMemberGUI.this, "전화번호를 확인하세요!");
			tf_name.requestFocus();
			return false;
		} else if (tf_ph2.getText().trim().length() !=4 ) {
			JOptionPane.showMessageDialog(UserMemberGUI.this, "전화번호를 확인하세요!");
			tf_name.requestFocus();
			return false;
		} else if (!male.isSelected()&&!female.isSelected() ) {
			JOptionPane.showMessageDialog(UserMemberGUI.this, "성별체크를 확인하세요!");
			tf_name.requestFocus();
			return false;
		}

		return true;
	}
	/*
	public Insets getInsets() {
		return new Insets(10, 10, 10, 10);
	}*/

	public static void messageBox(Object obj, String message) {
		JOptionPane.showMessageDialog((Component) obj, message);

	}

}
