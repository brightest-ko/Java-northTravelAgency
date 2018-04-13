import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class UserMemberGUI2 extends JDialog implements ActionListener {

	JPanel mframe;
	//JPanel plabel = new JPanel(new GridLayout(10, 1, 10, 10));
	JLabel id11 = new JLabel("필수입력란(*)"),lempty = new JLabel(" ");	
	// JPanel
	String gender2 = null;
	JPanel p1 = new JPanel(new GridLayout(11, 1, 5, 5));
	JPanel leftempty = new JPanel(new GridLayout(10, 1, 100, 100));
	JPanel p2 = new JPanel(new GridLayout(11, 1, 5, 5));
	JPanel left = new JPanel(new BorderLayout());
	JPanel right = new JPanel(new GridLayout(10, 1, 10, 10));

	JPanel p3 = new JPanel();

	 JLabel lempty0 = new JLabel(" ");   //공백
	 JLabel lempty00 = new JLabel(" ");   //공백
	 
	 JLabel left1 = new JLabel("        ");
	 JLabel right1 = new JLabel("            ");
	 
	JLabel id1 = new JLabel("아이디(*)");
	JLabel lpass1 = new JLabel("비밀번호(*)");
	JLabel lpass2 = new JLabel("비번확인(*)");
	JLabel lname = new JLabel("이    름(*)");
	JLabel lbirth = new JLabel("생년월일");
	JLabel lph = new JLabel("전화번호(*)");
	JLabel lph1 = new JLabel("-");
	JLabel lph2 = new JLabel("-");
	JLabel laddr = new JLabel("주    소");
	JLabel lgender = new JLabel("성   별");

	// 이메일 추가

	JLabel lemail = new JLabel("이메일");
	JLabel lemail2 = new JLabel("@");

	JTextField tf_id = new JTextField(10);
	JPasswordField tf_pass1 = new JPasswordField(10);
	JPasswordField tf_pass2 = new JPasswordField(10);
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
	// JLabel lmale = new JLabel("남");
	JRadioButton male = new JRadioButton("남");
	// JLabel lfemale = new JLabel("여");
	JRadioButton female = new JRadioButton("여");

	/*
	 * JPanel pforeign = new JPanel(); ButtonGroup foreign = new ButtonGroup();
	 * JLabel llocals = new JLabel("내국인"); JRadioButton locals = new
	 * JRadioButton(); JLabel lforeigner = new JLabel("외국인"); JRadioButton
	 * foreigner = new JRadioButton();
	 */
	JButton confirm = new JButton("수정");
	JButton out = new JButton("탈퇴");
	JButton reset = new JButton("취소");

	IndexMain me;

	JPanel idck = new JPanel(new BorderLayout());
	JButton idck1 = new JButton("중복확인");

	JPanel email = new JPanel(new BorderLayout());

	JPanel ph = new JPanel(new GridLayout(1,3));

	String[] comboName1 = { "010", "011", "018", "017", "070" };
	JComboBox comboPH = new JComboBox(comboName1);

	// 이메일 콤보 박스
	String[] emailcombo = { "직접입력", "naver.com", "hanmail.net", "icloud.com","gmail.com" };
	JComboBox cm = new JComboBox(emailcombo);// combo mail
	
	
	UserMemberDAO dao = new UserMemberDAO();

	// IndexMain custo_logout;
	String loginID;

	private IndexMain2 me2;
	
	public UserMemberGUI2(IndexMain2 me2, String index, String loginID) {

		super(me2, "회원정보수정");
		this.me2 = me2;
		this.loginID = loginID;


		//setLayout(new BorderLayout(10, 10));
		//tf_id.setBorder(new LineBorder(Color.red));
//		tf_id.setBackground(new Color(180,215,254));  //180,215,254
//		tf_pass1.setBackground(new Color(180,215,254));
//		tf_pass2.setBackground(new Color(180,215,254));
//		tf_name.setBackground(new Color(180,215,254));
//		tf_ph1.setBackground(new Color(180,215,254));
//		tf_ph2.setBackground(new Color(180,215,254));
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
		//tf_pass1.setBackground(Color Red);
		//tf_pass1.
		//tf_name.setBorder(new LineBorder(Color.red));
		//tf_ph1.setBorder(new LineBorder(Color.red));
	   // tf_ph2.setBorder(new LineBorder(Color.red));
		
		leftempty.add(left1);
		right.add(right1);
		
		p1.add(lempty0,"North");
		p1.add(id11);
		p1.add(id1);
		p1.add(lpass1);
		p1.add(lpass2);
		p1.add(lname);
		p1.add(lbirth);
		p1.add(lph);
		// plabel.add(lph1);
		// plabel.add(lph2);
		p1.add(lemail);
		p1.add(laddr);
		p1.add(lgender);
		
		
		//idck.add(tf_id, "Center");
		//idck.add(idck1, "East");
		p2.add(lempty00,"North");
		p2.add(lempty);
		p2.add(tf_id);
		tf_id.setEditable(false);
		p2.add(tf_pass1);
		tf_pass1.setEchoChar('*');
		p2.add(tf_pass2);
		tf_pass2.setEchoChar('*');
		p2.add(tf_name);
		tf_name.setEditable(false);
		p2.add(tf_birth1);
		
		Font mfont = new Font("맑은 고딕", Font.BOLD, 13);
		id11.setFont(mfont);				id11.setForeground(Color.white);	
		id1.setFont(mfont);				id1.setForeground(Color.white);				
		//idp.setFont(mfont);				idp.setForeground(Color.white);	
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

		JPanel php1 = new JPanel(new BorderLayout());
		JPanel php2 = new JPanel(new BorderLayout());

		ph.add(comboPH);
		php1.add(lph1,"West");
		php1.add(tf_ph1,"Center");
		ph.add(php1);
		php2.add(lph2,"West");
		php2.add(tf_ph2,"Center");
		ph.add(php2);
		p2.add(ph);

		JPanel emailp1 = new JPanel(new BorderLayout());

		emailp1.add(tf_email1,"West");
		emailp1.add(lemail2,"Center");
		emailp1.add(tf_email2,"East");
		email.add(emailp1,"Center");
		email.add(cm,"East");
		p2.add(email);

		p2.add(tf_addr);
		// 그룹화
		gender.add(male);
		gender.add(female);

		pgender.add(male);
		pgender.add(female);
		p2.add(pgender);

		p3.add(confirm);
		p3.add(out);
		p3.add(reset);

//		add(plabel, "West");
//		add(p2, "Center");
//		add(p3, "South");
		
		ImageIcon mbg = new ImageIcon("bg.png");
		mframe = new JPanel(){
			public void paintComponent(Graphics g) {
				g.drawImage(mbg.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		JPanel p = new JPanel(new BorderLayout()); 
		p.setPreferredSize(new Dimension(428,450));
		left.add(leftempty, "West");
		left.add(p1, "Center");
		p.add(left,"West");
		p.add(p2, "Center");
		p.add(right, "East");
		p.add(p3, "South");
		mframe.add(p,"North");
		add(mframe);
		p.setBackground(new Color(255, 0, 0, 0));	
		leftempty.setBackground(new Color(255, 0, 0, 0));
		lph1.setForeground(Color.WHITE);
		lph2.setForeground(Color.WHITE);
		lemail2.setForeground(Color.WHITE);	
		ph.setBackground(new Color(255, 0, 0, 0));
		lph1.setBackground(new Color(255, 0, 0, 0));
		lph2.setBackground(new Color(255, 0, 0, 0));
		php1.setBackground(new Color(255, 0, 0, 0));
		php2.setBackground(new Color(255, 0, 0, 0));
		email.setBackground(new Color(255, 0, 0, 0));
		emailp1.setBackground(new Color(255, 0, 0, 0));
		p1.setBackground(new Color(255, 0, 0, 0));
		left.setBackground(new Color(255, 0, 0, 0));
		p2.setBackground(new Color(255, 0, 0, 0));
		male.setBackground(new Color(255, 0, 0, 0));
		female.setBackground(new Color(255, 0, 0, 0));
		pgender.setBackground(new Color(255, 0, 0, 0));
		p1.setBackground(new Color(255, 0, 0, 0));
		p3.setBackground(new Color(255, 0, 0, 0));
		right.setBackground(new Color(255, 0, 0, 0));

		//idck1.addActionListener(this);
		confirm.addActionListener(this);
		reset.addActionListener(this);
		out.addActionListener(this);
		// pgender.addActionListener(this);

		UserMemberDTO dto = new UserMemberDTO();
		// id, pwd1, pwd2, name, birth, hp, email, addr, gender
		dto = dao.my(loginID);
		tf_id.setText(dto.getId());
		tf_pass1.setText(dto.getPwd1());
		tf_pass2.setText(dto.getPwd2());
		tf_name.setText(dto.getName());
		if(dto.getBirth() != null){
			tf_birth1.setText(dto.getBirth().toString());
		}
		StringTokenizer hp = new StringTokenizer(dto.getHp(), "-");
		String[] hptoken = new String[3];
		int i = 0;
		while (hp.hasMoreTokens()) {
			hptoken[i] = hp.nextToken();
			i++;
		}
		if (hptoken[0].trim().equals("010"))
			comboPH.setSelectedItem("010");
		else if (hptoken[0].trim().equals("011"))
			comboPH.setSelectedItem("011");
		else if (hptoken[0].trim().equals("018"))
			comboPH.setSelectedItem("018");
		else if (hptoken[0].trim().equals("017"))
			comboPH.setSelectedItem("017");
		else if (hptoken[0].trim().equals("070"))
			comboPH.setSelectedItem("070");
		tf_ph1.setText(hptoken[1].trim());
		tf_ph2.setText(hptoken[2].trim());
		if (dto.getEmail() != null&&!dto.getEmail().trim().equals("@")) {
			StringTokenizer email = new StringTokenizer(dto.getEmail(), "@");
			String[] emtoken = new String[2];
			i = 0;
			while (email.hasMoreTokens()) {
				emtoken[i] = email.nextToken();
				i++;
			}
			tf_email1.setText(emtoken[0].trim());
			System.out.println(emtoken[1]);
			tf_email2.setText(emtoken[1].trim());
		}
		if (dto.getAddr() != null) {
			tf_addr.setText(dto.getAddr());
		}
		if (Character.toString(dto.getGender()).trim().equals("M")
				|| Character.toString(dto.getGender()).trim().equals("m")) {
			male.setSelected(true);
		} else {
			female.setSelected(true);
		}
		male.setEnabled(false);
		female.setEnabled(false);

		cm.addActionListener(this);
		
		
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
		// String email;//이메일 값을 그냥 스트링에 넣음
		String email = tf_email1.getText().trim() + "@" + tf_email2.getText().trim();
		;// 콤보 박스 값 넣기

		if (sstr.trim().equals("naver.com")) {
			tf_email2.setText(sstr);
			email = tf_email1.getText().trim() + "@" + tf_email2.getText().trim();
		} else if (sstr.trim().equals("hanmail.net")) {
			tf_email2.setText(sstr);
			email = tf_email1.getText().trim() + "@" + tf_email2.getText().trim();
		} else if (sstr.trim().equals("icloud.com")) {
			tf_email2.setText(sstr);
			email = tf_email1.getText().trim() + "@" + tf_email2.getText().trim();
		} else if (sstr.trim().equals("gmail.com")) {
			tf_email2.setText(sstr);
			email = tf_email1.getText().trim() + "@" + tf_email2.getText().trim();
		} else if (sstr.trim().equals("직접입력")) {
			tf_email2.requestFocus();
			email = tf_email1.getText().trim() + "@" + tf_email2.getText().trim();
		}

		if (obj == reset) {
			dispose();
		} else if (obj == confirm) {

			if (!checkInput())
				return; // 유효성체크
			
			String fieldName = comboPH.getSelectedItem().toString();
			if (fieldName.trim().equals("010")) {
				if (dao.memberUpdate(tf_pass1.getText().trim(), tf_pass2.getText().trim(), tf_birth1.getText(),
						fieldName + "-" + tf_ph1.getText().trim() + "-" + tf_ph2.getText().trim(),
						tf_email1.getText().trim() + "@" + tf_email2.getText().trim(), tf_addr.getText().trim(),
						tf_id.getText().trim()) > 0) {

					messageBox(this, tf_name.getText() + " 님의 정보가 수정되었습니다.");
					dispose();
				} else {
					messageBox(this, "정보가 수정되지 않았습니다.");
				}
			}
			if (fieldName.trim().equals("011")) {
				if (dao.memberUpdate(tf_pass1.getText().trim(), tf_pass2.getText().trim(), tf_birth1.getText(),
						fieldName + "-" + tf_ph1.getText().trim() + "-" + tf_ph2.getText().trim(),
						tf_email1.getText().trim() + "@" + tf_email2.getText().trim(), tf_addr.getText().trim(),
						tf_id.getText().trim()) > 0) {

					messageBox(this, tf_name.getText() + " 님의 정보가 수정되었습니다.");
					dispose();
				} else {
					messageBox(this, "정보가 수정되지 않았습니다.");
				}
			}
			if (fieldName.trim().equals("018")) {
				if (dao.memberUpdate(tf_pass1.getText().trim(), tf_pass2.getText().trim(), tf_birth1.getText(),
						fieldName + "-" + tf_ph1.getText().trim() + "-" + tf_ph2.getText().trim(),
						tf_email1.getText().trim() + "@" + tf_email2.getText().trim(), tf_addr.getText().trim(),
						tf_id.getText().trim()) > 0) {

					messageBox(this, tf_name.getText() + " 님의 정보가 수정되었습니다.");
					dispose();
				} else {
					messageBox(this, "정보가 수정되지 않았습니다.");
				}
			}
			if (fieldName.trim().equals("017")) {
				if (dao.memberUpdate(tf_pass1.getText().trim(), tf_pass2.getText().trim(), tf_birth1.getText(),
						fieldName + "-" + tf_ph1.getText().trim() + "-" + tf_ph2.getText().trim(),
						tf_email1.getText().trim() + "@" + tf_email2.getText().trim(), tf_addr.getText().trim(),
						tf_id.getText().trim()) > 0) {

					messageBox(this, tf_name.getText() + " 님의 정보가 수정되었습니다.");
					dispose();
				} else {
					messageBox(this, "정보가 수정되지 않았습니다.");
				}
			}
			if (fieldName.trim().equals("070")) {
				if (dao.memberUpdate(tf_pass1.getText().trim(), tf_pass2.getText().trim(), tf_birth1.getText(),
						fieldName + "-" + tf_ph1.getText().trim() + "-" + tf_ph2.getText().trim(),
						tf_email1.getText().trim() + "@" + tf_email2.getText().trim(), tf_addr.getText().trim(),
						tf_id.getText().trim()) > 0) {

					messageBox(this, tf_name.getText() + " 님의 정보가 수정되었습니다.");
					dispose();
				} else {
					messageBox(this, "정보가 수정되지 않았습니다.");
				}
			}
		} else if (obj == out) {
			{
				String message1 = "정말 탈퇴하시겠습니까?\n탈퇴하실경우 같은 아이디로 회원가입이 불가능합니다.";
				int ans1 = JOptionPane.showConfirmDialog(this, message1, "탈퇴", JOptionPane.YES_OPTION);
				if (ans1 == JOptionPane.YES_OPTION) {
					int rs = dao.delete(tf_id.getText());
					JOptionPane.showMessageDialog(this, "탈퇴가 정상처리되었습니다.");
					dispose();
					me2.dispose();
					IndexMain a = new IndexMain();

				}
			}
		}

	}
	
	public boolean checkInput() {
		if (tf_id.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(UserMemberGUI2.this, "아이디를 입력하세요!");
			tf_id.requestFocus();
			return false;
		} else if (!String.valueOf(tf_pass1.getText()).equals(String.valueOf(tf_pass2.getText()))) {
			JOptionPane.showMessageDialog(UserMemberGUI2.this, "비밀번호가 일치하지 않습니다!");
			tf_pass1.requestFocus();
			return false;
		} else if (tf_id.getText().trim().length() > 16) {
			JOptionPane.showMessageDialog(UserMemberGUI2.this, "아이디는 15자리 이하로 입력하세요!");
			tf_id.requestFocus();
			return false;
		} else if (String.valueOf(tf_pass1.getText()).trim().length() == 0) {
			JOptionPane.showMessageDialog(UserMemberGUI2.this, "비밀번호를 입력하세요!");
			tf_pass1.requestFocus();
			return false;
		} else if (String.valueOf(tf_pass1.getText()).trim().length() > 16) {
			JOptionPane.showMessageDialog(UserMemberGUI2.this, "비밀번호는 15자리 이하로 입력하세요!");
			tf_pass1.requestFocus();
			return false;
		} else if (tf_name.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(UserMemberGUI2.this, "이름을 입력하세요!");
			tf_name.requestFocus();
			return false;
		} else if (tf_ph1.getText().trim().length() <3||tf_ph1.getText().trim().length() >4) {
			JOptionPane.showMessageDialog(UserMemberGUI2.this, "전화번호를 확인하세요!");
			tf_name.requestFocus();
			return false;
		} else if (tf_ph2.getText().trim().length() !=4 ) {
			JOptionPane.showMessageDialog(UserMemberGUI2.this, "전화번호를 확인하세요!");
			tf_name.requestFocus();
			return false;
		} else if (!male.isSelected()&&!female.isSelected() ) {
			JOptionPane.showMessageDialog(UserMemberGUI2.this, "성별체크를 확인하세요!");
			tf_name.requestFocus();
			return false;
		}

		return true;
	}
		public static void messageBox(Object obj, String message) {
		JOptionPane.showMessageDialog((Component) obj, message);

	}

}
