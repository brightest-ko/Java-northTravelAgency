import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class Member2GUI extends JPanel implements ActionListener, MouseListener,KeyListener,FocusListener {
	JButton[] buttons;
	String[] btnTitle = { "회원복구", "수정", "영구삭제", "취소", "아이디중복" };
	JLabel[] lEmpty = new JLabel[18];

	JTextField tfId, tfName, tfBirth, tfEmail, tfHp, tfAddr, tfRegdate, tfSearch;
	JPasswordField tfPwd1, tfPwd2;
	JRadioButton male, feMale, admin, user;
	ButtonGroup gb1, gb2;

	JTable table;
	DefaultTableModel memberTable;

	String[] comboName = { " 검색조건 ", " Id ", " Name ", " Email ", " Hp ", " 가입날짜 " };
	JComboBox combo;
	JButton search;
	JButton searchAll;

	Object[][] rowData;
	// 컬럼명을 저장할 Object타입의 1차원배열
	Object[] columnNames = { "아이디", "이름", "Email", "연락처", "생년월일", "주소", "성별", "가입일", "권한", "비밀번호","회원여부" };

	Member2DAO dao;

	// 선택한 행의 인덱스를 저장하기 위한 변수
	int selectedRow = -1;

	// 전체 및 검색을 구분하기 위한 int형 상수선언
	public static final int ALL = 1;
	public static final int SEARCH = 2;

	// 버튼
	private static final int NONE = 10;
	private static final int ADD = 11;
	private static final int UPDATE = 12;
	private static final int DELETE = 13;
	private static final int CANCEL = 14;
	int cmd = NONE;

	/* 원본 비밀번호를 저장할 컬렉션 */
	List<String> originalPassword = new Vector<String>();

	public Member2GUI() {

		createComponent();
		setLayout(new FlowLayout());
		addComponent();
		addListener();
		setVisible(true);
	}

	private void createComponent() {

		dao = new Member2DAO();

		buttons = new JButton[5];
		// 버튼 생성
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(btnTitle[i]);
		}
		// 텍스트 필드 생성
		tfId = new JTextField(15);
		tfId.setBorder(new LineBorder(Color.DARK_GRAY));
		tfPwd1 = new JPasswordField(15);
		tfPwd1.setBorder(new LineBorder(Color.DARK_GRAY));
		tfPwd2 = new JPasswordField(15);
		tfPwd2.setBorder(new LineBorder(Color.DARK_GRAY));
		tfName = new JTextField(15);
		tfName.setBorder(new LineBorder(Color.DARK_GRAY));
		tfBirth = new JTextField(15);
		tfBirth.setBorder(new LineBorder(Color.DARK_GRAY));
		tfEmail = new JTextField(15);
		tfEmail.setBorder(new LineBorder(Color.DARK_GRAY));
		tfHp = new JTextField(15);
		tfHp.setBorder(new LineBorder(Color.DARK_GRAY));
		tfAddr = new JTextField(15);
		tfAddr.setBorder(new LineBorder(Color.DARK_GRAY));
		tfRegdate = new JTextField(15);
		tfRegdate.setBorder(new LineBorder(Color.DARK_GRAY));
		
		
		

		male = new JRadioButton("남자", false);
		feMale = new JRadioButton("여자", false);
		admin = new JRadioButton("관리자", false);
		user = new JRadioButton("일반", false);
		gb1 = new ButtonGroup();
		gb2 = new ButtonGroup();

		// 테이블 및 모델 객체 생성
		table = new JTable();

		/* 테이블 꾸미기 */
		// 1]제목 꾸미기
		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(255,128,64));
		header.setForeground(Color.white);
		header.setPreferredSize(new Dimension(0, 30));
		header.setReorderingAllowed(false);
		header.setResizingAllowed(true);

		table.setPreferredScrollableViewportSize(new Dimension(900, 500));

		memberTable = new DefaultTableModel(rowData, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		combo = new JComboBox(comboName);
		tfSearch = new JTextField(20);
		search = new JButton("검색");
		searchAll = new JButton("전체보기");

		setTableData(dao.getRecordAll());

		setText(NONE);
		setButton(NONE);

	}

	// 모든 레코드를 JTable에 출력하는 메소드
	private void setTableData(List<Member2DTO> list) {

		// 1]DAO에서 모든 레코드 데이타를 컬렉션으로 얻기

		// else
		// list = dao.getSearchRecord(tfId.getText().trim(),
		// tfName.getText().trim());

		// 2]컬렉션에 저장된 모든 레코드를 model에 저장
		rowData = new Object[list.size()][columnNames.length];

		/* 비밀번호를 위한 변수 */
		StringBuffer sbPassword = new StringBuffer();

		/* 컬렉션에 저장하기전 모든 데이타 삭제 */
		originalPassword.removeAll(originalPassword);

		for (int i = 0; i < list.size(); i++) {

			Member2DTO dto = list.get(i);

			rowData[i][0] = dto.getId();
			rowData[i][1] = dto.getName();
			if (dto.getEmail() != null) {
				rowData[i][2] = dto.getEmail();
			} else {
				rowData[i][2] = "";
			}
			rowData[i][3] = dto.getHp();
			if (dto.getBirth() != null) {
				rowData[i][4] = dto.getBirth();
			} else {
				rowData[i][4] = "";
			}
			if (dto.getAddr() != null) {
				rowData[i][5] = dto.getAddr();
			} else {
				rowData[i][5] = "";
			}
			rowData[i][6] = dto.getGender();
			
			if (dto.getRegdate() != null) {
				rowData[i][7] = dto.getRegdate();
			} else {
				rowData[i][7] = "";
			}
			
			rowData[i][8] = dto.getMaster();
			rowData[i][9] = dto.getPwd1();
			rowData[i][10] = dto.getLeave();

		}

		memberTable.setDataVector(rowData, columnNames);

		// 3]model을 table에 연결:model저장된 데이타가 table에 뿌려짐
		table.setModel(memberTable);
		table.setRowHeight(25);

		// 4]데이타 정렬

		// TableColumnModel tcm = table.getColumnModel();
		// table.setRowHeight(25);
		// DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		// dtcr.setHorizontalAlignment(JLabel.CENTER);

		// for(int i=2;i < tcm.getColumnCount() ;i++){
		// tcm.getColumn(i).setCellRenderer(dtcr);
		// if(i==3) tcm.getColumn(i).setPreferredWidth(15);
		// }

		// 테이블 칼럼 간격
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);
		table.getColumnModel().getColumn(6).setPreferredWidth(8);
		table.getColumnModel().getColumn(7).setPreferredWidth(60);
		table.getColumnModel().getColumn(8).setPreferredWidth(8);
		table.getColumnModel().getColumn(9).setPreferredWidth(30);
		table.getColumnModel().getColumn(10).setPreferredWidth(30);
		
		table.setAutoCreateRowSorter(true);
		TableRowSorter tablesorter = new TableRowSorter(table.getModel());
		table.setRowSorter(tablesorter);
		
		
		// 제이테이블 정렬......
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer dtc = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		dtc.setHorizontalAlignment(SwingConstants.CENTER);
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcms = table.getColumnModel();
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcms.getColumnCount(); i++) {
			tcms.getColumn(i).setCellRenderer(dtc);
		}

	}

	private void addComponent() {

		JPanel West = new JPanel(new BorderLayout(0, 10)); // 위아래 갭

		JPanel CenterInWest = new JPanel(new BorderLayout());

		CenterInWest.setBorder(new TitledBorder("회원정보"));

		JPanel p1 = new JPanel(new GridLayout(9, 1, 10, 10));
		JPanel p2 = new JPanel(new GridLayout(9, 1, 5, 5));
		JPanel p3 = new JPanel(new GridLayout(9, 1));
		JPanel p4 = new JPanel(new GridLayout(2, 3));

		JPanel pid = new JPanel(new BorderLayout());

		JLabel JId = new JLabel("아   이   디(*)");
		JLabel JPwd1 = new JLabel("비 밀 번 호(*)");
		JLabel JPwd2 = new JLabel("비 번 확 인(*)");
		JLabel JName = new JLabel("이        름(*)");
		JLabel JBirth = new JLabel("생 년 월 일");
		JLabel JEmail = new JLabel("Email        ");
		JLabel JHp = new JLabel("연   락   처(*)");
		JLabel JAddr = new JLabel("주         소");
		JLabel JRegdate = new JLabel("가 입  날 짜");
		JLabel JGender = new JLabel("성        별");
		JLabel JMaster = new JLabel("권        한(*)");

		p1.add(JId);
		p1.add(JPwd1);
		p1.add(JPwd2);
		p1.add(JName);
		p1.add(JBirth);
		p1.add(JEmail);
		p1.add(JHp);
		p1.add(JAddr);
		p1.add(JRegdate);

		pid.add(tfId, "Center");
		pid.add(buttons[4], "East");

		p2.add(pid);
		p2.add(tfPwd1);
		p2.add(tfPwd2);
		p2.add(tfName);
		p2.add(tfBirth);
		p2.add(tfEmail);
		p2.add(tfHp);
		p2.add(tfAddr);
		p2.add(tfRegdate);

		JLabel empty = new JLabel("         ");
		p3.add(empty);
		// p3.add(buttons[4]);

		p4.add(JGender);
		p4.add(male);
		p4.add(feMale);
		p4.add(JMaster);
		p4.add(admin);
		p4.add(user);
		
		Font font = new Font("고딕", Font.PLAIN, 15);
		JLabel milabel = new JLabel("(*)필수입력란");
		milabel.setFont(font);
				
		CenterInWest.add(milabel, "North");
		CenterInWest.add(p1, "West");
		CenterInWest.add(p2, "East");
		CenterInWest.add(p3, "Center");
		CenterInWest.add(p4, "South");

		gb1.add(male);
		gb1.add(feMale);
		gb2.add(admin);
		gb2.add(user);

		West.add(CenterInWest, "Center");

		JPanel pnlSouthInWest = new JPanel();
		for (int i = 0; i < buttons.length - 1; i++) {
			pnlSouthInWest.add(buttons[i]);
		}

		West.add(pnlSouthInWest, "South");

		JPanel Center = new JPanel(new BorderLayout());

		JPanel p = new JPanel(new BorderLayout());
		JPanel pp = new JPanel();
		p.add(combo, "West");
		p.add(tfSearch, "Center");
		pp.add(search);
		pp.add(searchAll);
		p.add(pp, "East");

		Center.setBorder(new TitledBorder("결과창"));
		Center.add(new JScrollPane(table), "Center");
		Center.add(p, "South");

		add(West, "West");
		add(Center, "Center");

		West.setPreferredSize(new Dimension(400, 400));
		Center.setPreferredSize(new Dimension(900, 500));

	}

	private void addListener() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].addActionListener(this);
		}
		search.addActionListener(this);
		searchAll.addActionListener(this);

		table.addMouseListener(this);
		combo.addActionListener(this);

		tfSearch.addKeyListener(this);	
		tfSearch.addFocusListener(this); //텍스트안에 포커스가 들어가 있음을 구분하기 위함

	}///////////////////////
	
	public void clearTextField() {

		if (!tfId.isEditable())
			tfId.setEditable(true);

		tfId.setText("");
		tfPwd1.setText("");
		tfPwd2.setText("");
		tfName.setText("");
		tfBirth.setText("");
		tfEmail.setText("");
		tfHp.setText("");
		tfAddr.setText("");
		tfRegdate.setText("");

		tfId.requestFocus();
	}

	private void setText(int command) {
		switch (command) {
		case ADD:
			tfId.setText("");
			tfId.setEditable(true);
			tfPwd1.setText("");
			tfPwd1.setEditable(true);
			tfPwd2.setText("");
			tfPwd2.setEditable(true);
			tfName.setText("");
			tfName.setEditable(true);
			tfBirth.setText("");
			tfBirth.setEditable(true);
			tfEmail.setText("");
			tfEmail.setEditable(true);
			tfHp.setText("");
			tfHp.setEditable(true);
			tfAddr.setText("");
			tfAddr.setEditable(true);
			tfRegdate.setText("");
			tfRegdate.setEditable(false);
			tfId.requestFocus();
			break;
		case DELETE:

			tfId.setEditable(false);
			tfPwd1.setEditable(false);
			tfPwd2.setEditable(false);
			tfName.setEditable(false);
			tfBirth.setEditable(false);
			tfEmail.setEditable(false);
			tfHp.setEditable(false);
			tfAddr.setEditable(false);
			tfRegdate.setEditable(false);
			tfId.requestFocus();
			break;
		case UPDATE:
			tfId.setEditable(false);
			tfPwd1.setEditable(true);
			tfPwd2.setEditable(true);
			tfName.setEditable(true);
			tfBirth.setEditable(true);
			tfEmail.setEditable(true);
			tfHp.setEditable(true);
			tfAddr.setEditable(true);
			tfRegdate.setEditable(true);
			tfId.requestFocus();
			break;
		case CANCEL:
		case NONE:

			tfId.setText("");
			tfId.setEditable(false);
			tfPwd1.setText("");
			tfPwd1.setEditable(false);
			tfPwd2.setText("");
			tfPwd2.setEditable(false);
			tfName.setText("");
			tfName.setEditable(false);
			tfBirth.setText("");
			tfBirth.setEditable(false);
			tfEmail.setText("");
			tfEmail.setEditable(false);
			tfHp.setText("");
			tfHp.setEditable(false);
			tfAddr.setText("");
			tfAddr.setEditable(false);
			tfRegdate.setText("");
			tfRegdate.setEditable(false);
			tfId.requestFocus();
			break;
		}
	}

	private void setButton(int command) {
		// 취소 버튼을 제외하고, 어떤 버튼이 눌리더라도 모든 버튼 비활성화
		buttons[0].setEnabled(false); // 추가
		buttons[1].setEnabled(false); // 수정
		buttons[2].setEnabled(false); // 삭제
		buttons[3].setEnabled(false); // 취소
		feMale.setEnabled(false);
		male.setEnabled(false);
		admin.setEnabled(false);
		user.setEnabled(false);

		switch (command) {
		case ADD:
			buttons[0].setEnabled(true);
			buttons[3].setEnabled(true);
			buttons[4].setEnabled(true);
			feMale.setEnabled(true);
			male.setEnabled(true);
			admin.setEnabled(true);
			user.setEnabled(true);

			cmd = ADD;
			break;
		case UPDATE:
			buttons[1].setEnabled(true);
			buttons[3].setEnabled(true);
			buttons[4].setEnabled(false);
			feMale.setEnabled(true);
			male.setEnabled(true);
			admin.setEnabled(true);
			user.setEnabled(true);
			tfRegdate.setEnabled(false);

			cmd = UPDATE;
			break;
		case CANCEL:

		case NONE:
			buttons[0].setEnabled(true);
			buttons[1].setEnabled(true);
			buttons[2].setEnabled(true);
			buttons[3].setEnabled(true);
			buttons[4].setEnabled(false);
			feMale.setEnabled(false);
			male.setEnabled(false);
			admin.setEnabled(false);
			user.setEnabled(false);

			cmd = NONE;
			break;
		}
	}

	// 유효성 체크를 위한 메소드
	public boolean checkInput(int flag) {

		if (flag == ALL) {
			if (tfId.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(Member2GUI.this, "아이디를 입력하세요!");
				tfId.requestFocus();
				return false;
			} else if (buttons[4].isEnabled()) {
				JOptionPane.showMessageDialog(Member2GUI.this, "아이디 중복확인해주세요");
				tfId.requestFocus();
				return false;
			} else if (!String.valueOf(tfPwd1.getPassword()).equals(String.valueOf(tfPwd2.getPassword()))) {
				JOptionPane.showMessageDialog(Member2GUI.this, "비밀번호가 일치하지 않습니다!");
				tfPwd2.requestFocus();
				return false;
			} else if (tfId.getText().trim().length() > 16) {
				JOptionPane.showMessageDialog(Member2GUI.this, "아이디는 15자리 이하로 입력하세요!");
				tfId.requestFocus();
				return false;
			} else if (String.valueOf(tfPwd1.getPassword()).trim().length() == 0) {
				JOptionPane.showMessageDialog(Member2GUI.this, "비밀번호를 입력하세요!");
				tfPwd1.requestFocus();
				return false;
			} else if (String.valueOf(tfPwd1.getPassword()).trim().length() > 16) {
				JOptionPane.showMessageDialog(Member2GUI.this, "비밀번호는 15자리 이하로 입력하세요!");
				tfPwd1.requestFocus();
				return false;
			} else if (tfName.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(Member2GUI.this, "이름을 입력하세요!");
				tfName.requestFocus();
				return false;
			} else if (tfHp.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(Member2GUI.this, "전화번호를 확인하세요!");
				tfName.requestFocus();
				return false;
			} else if (!male.isSelected()&&!feMale.isSelected() ) {
				JOptionPane.showMessageDialog(Member2GUI.this, "성별체크를 확인하세요!");
				tfName.requestFocus();
				return false;
			}
		}

		else {// SEARCH인 경우

			if ((tfId.getText().trim().equals("") && tfName.getText().trim().length() == 0)
					|| tfId.getText().trim().length() >= 9

			) {
				JOptionPane.showMessageDialog(Member2GUI.this, "유효한 검색 문자열이 아니예요!");
				return false;
			}
		}
		return true;
	}

	
	 boolean focusYn = false;
	 
	 @Override
	 public void keyPressed(KeyEvent e) {
	  if(focusYn==true){ //텍스트필드안에 포커스가 들어오고
	   if(e.getKeyCode() == KeyEvent.VK_ENTER){ //엔터키가 눌러졌으면
	    search.doClick(); //b1을 클릭하라
	   }
	  }
	 }	
	 

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String fieldName = combo.getSelectedItem().toString();
		Object obj = e.getSource();

		if (obj == buttons[0]) { // 추가

			String id = tfId.getText().trim();

			int rs = dao.insert(id);

			if (rs > 0) {
				JOptionPane.showMessageDialog(buttons[0], "재회원가입(회원복구) 성공했어요!");
			} else
				JOptionPane.showMessageDialog(buttons[0], "'탈퇴하지않은 회원'등의 이유로\n재회원가입(회원복구) 실패했어요");

			// 입력창 클리어
			// 전체 데이타 뿌려주기
			setTableData(dao.getRecordAll());
			setText(NONE);
			setButton(NONE);
			gb1.clearSelection();
			gb2.clearSelection();
			table.addMouseListener(this); // 테이블 리스너 다시 붙이기

		}
		if (obj == buttons[4]) {// 아이디체크
			// id 텍스트 박스에 값이 없으면 메세지 풀력,있으면 DB연동

			if (tfId.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(buttons[4], "ID를 입력해주세요.");
				tfId.requestFocus();// 포커스 이동
			}
			else if (tfId.getText().trim().length() >= 9) {
				JOptionPane.showMessageDialog(Member2GUI.this, "아이디는 8자이하!");
				tfId.requestFocus();}
			else {
				if (dao.IdCheck(tfId.getText())) {
					JOptionPane.showMessageDialog(buttons[4], tfId.getText() + " 는 사용 가능합니다.");
					buttons[4].setEnabled(false);
					tfId.setEditable(false);
				} else {
					JOptionPane.showMessageDialog(buttons[4], tfId.getText() + "는 중복입니다.");
					tfId.requestFocus();
				}
			}
		} else if (obj == buttons[1]) {// 수정
			if (cmd != UPDATE) {
				setText(UPDATE);
				setButton(UPDATE);
				table.removeMouseListener(this); // 테이블 리스너 떼기
				System.out.println("리스너 때기");
				return;
			}
			if (!checkInput(ALL))
				return; // 유효성체크

			String pwd1 = String.valueOf(tfPwd1.getPassword());
			String pwd2 = String.valueOf(tfPwd2.getPassword());
			String name = tfName.getText().trim();
			String email = tfEmail.getText().trim();
			String hp = tfHp.getText().trim();
			String addr = tfAddr.getText().trim();
			String gender = null;
			String master = null;
			String birth = tfBirth.getText().trim();
			String regdate = tfBirth.getText().trim();
			String id = tfId.getText().trim();

			if (male.isSelected() == true) {
				gender = "M";
			} else if (feMale.isSelected() == true) {
				gender = "F";
			}
			if (admin.isSelected() == true) {
				master = "M";
			} else if (user.isSelected() == true) {
				master = "U";
			}

			Member2DTO dto = new Member2DTO();

			int rs = dao.update(pwd1, pwd2, name, email, hp, addr, gender, master, birth, id);

			if (rs == 1) {
				JOptionPane.showMessageDialog(buttons[1], "수정 성공했어요!");
			} else
				JOptionPane.showMessageDialog(buttons[1], "수정 실패했어요!");

			// 입력창 클리어
			clearTextField();
			// 전체 데이타 뿌려주기
			setTableData(dao.getRecordAll());

			// 입력창 클리어
			clearTextField();
			// 전체 데이타 뿌려주기
			setTableData(dao.getRecordAll());
			setText(NONE);
			setButton(NONE);
			gb1.clearSelection();
			gb2.clearSelection();
			table.addMouseListener(this); // 테이블 리스너 다시 붙여
			System.out.println("리스너 다시 붙여");

		} else if (obj == buttons[2]) {// 삭제
			setText(DELETE);
			String message1="영구삭제시 예약취소내역도 삭제됩니다\n영구삭제 하시겠습니까?";
			int ans1 = JOptionPane.showConfirmDialog(this, message1, "Exit", JOptionPane.YES_OPTION);
			if (ans1 == JOptionPane.YES_OPTION) {
				int affectedRow = dao.delete(tfId.getText().trim());
				if (affectedRow > 0) {
					JOptionPane.showMessageDialog(buttons[2], "영구삭제를 성공했습니다");
				} else
					JOptionPane.showMessageDialog(buttons[2], "영구삭제를 실패했습니다");
				// 삭제처리후 다시 -1로 초기화
				selectedRow = -1;
				// 전체 데이타 뿌려주기
				setTableData(dao.getRecordAll());
				// 입력창 클리어
			}clearTextField();
			setText(NONE);
			setButton(NONE);

		} else if (obj == buttons[3]) {// 취소
			table.addMouseListener(this); // 추가 후 취소 눌렀을시 다시 붙이게끔
			System.out.println("리스너 다시 붙여");
			setText(CANCEL);
			setText(NONE);
			setButton(NONE);
			gb1.clearSelection();
			gb2.clearSelection();

		} else if (obj == search) {// 검색
			

			if (fieldName.trim().equals("검색조건")) {
				setTableData(dao.getRecordAll());
			} else {
				if (tfSearch.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(this, "검색단어를 입력하세요!");
					tfSearch.requestFocus();
				} else {
					if (fieldName.trim().equals("가입날짜")) {

						StringTokenizer rd = new StringTokenizer(tfSearch.getText(),"/-");
						String[] day = new String[3];
						int i = 0;
						while (rd.hasMoreTokens()) {
							day[i] = rd.nextToken();
							i++;
						}
						int yearInt = Integer.parseInt(day[0]);
						int monthInt = Integer.parseInt(day[1]);
						int dayInt = Integer.parseInt(day[2]);
						if (monthInt == 1 || monthInt == 3 || monthInt == 5 || monthInt == 7 || monthInt == 8
								|| monthInt == 10) {
							if (dayInt + 1 > 31) {
								dayInt = 0;
								monthInt += 1;
							}
						}else if(monthInt == 4 ||monthInt == 6 ||monthInt == 9 ||monthInt == 11){
							if (dayInt + 1 > 30) {
								dayInt = 0;
								monthInt += 1;
							}
						}else if(monthInt == 12){
							if (dayInt + 1 > 31) {
								dayInt = 0;
								monthInt = 1;
								yearInt += 1;
							}
						}else{
							if (dayInt + 1 > 28) {
								dayInt = 0;
								monthInt += 1;
							}
						}
						String word2 =yearInt+"/"+monthInt+"/"+(dayInt+1);
						List<Member2DTO> list = dao.search2(tfSearch.getText(),word2);
						if(list.isEmpty()){
							JOptionPane.showMessageDialog(this, "검색결과가 없습니다\n검색어를 확인해주세요");
						}else{
							setTableData(list);
						}
						tfSearch.setText("");
					} else {
						List<Member2DTO> list = dao.search(fieldName, tfSearch.getText());
						if(list.isEmpty()){
							JOptionPane.showMessageDialog(this, "검색결과가 없습니다\n검색어를 확인해주세요");
						}else{
							setTableData(list);
						}
						tfSearch.setText("");
						
					}
				}
			}

		}else if (obj == combo) {
			if (fieldName.trim().equals("가입날짜")) {
				tfSearch.setText("날짜 검색 예)2016-02-01 or 16-02-01 or 16/02/01");
			} else if (fieldName.trim().equals("Hp")) {
				tfSearch.setText("연락처 검색 예) 010-1234-5678");
			} else if (fieldName.trim().equals("Email")) {
				tfSearch.setText("Email 검색 예) 1234@1234.xxx");
			} else if (fieldName.trim().equals("Name")) {
				tfSearch.setText("");
			} else if (fieldName.trim().equals("검색조건")) {
				tfSearch.setText("");
			} else if (fieldName.trim().equals("Id")) {
				tfSearch.setText("");
			}
		
		}else if (obj == searchAll) { // 전체보기
		setTableData(dao.getRecordAll());
	}
	}

	
	public Insets getInsets() {
		return new Insets(10, 10, 10, 10);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// 마우스로 클릭한 테이블의 행 인덱스 얻기
		selectedRow = table.getSelectedRow();
		clearTextField();

		// 아이디는 수정못하게 설정
		tfId.setEditable(false);

		tfId.setText((String) table.getValueAt(selectedRow, 0));
		tfName.setText((String) table.getValueAt(selectedRow, 1));
		tfEmail.setText((String) table.getValueAt(selectedRow, 2));
		tfHp.setText((String) table.getValueAt(selectedRow, 3));
		tfBirth.setText(table.getValueAt(selectedRow, 4).toString());
		tfAddr.setText((String) table.getValueAt(selectedRow, 5));
		tfRegdate.setText(table.getValueAt(selectedRow, 7).toString());
		tfPwd1.setText((String) table.getValueAt(selectedRow, 9));
		tfPwd2.setText((String) table.getValueAt(selectedRow, 9));

		if (table.getValueAt(selectedRow, 6).toString().trim().equals("m")
				|| table.getValueAt(selectedRow, 6).toString().trim().equals("M")) {
			male.setSelected(true);
		} else
			feMale.setSelected(true);

		if (table.getValueAt(selectedRow, 8).toString().trim().equals("m")
				|| table.getValueAt(selectedRow, 8).toString().trim().equals("M")) {
			admin.setSelected(true);
		} else
			user.setSelected(true);

	}

	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		 focusYn = true; //포커스가 텍스트안으로 들어왔음
			tfSearch.setText("");
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		focusYn = false; //포커스가 텍스트밖으로 빠져나감
	}
}
