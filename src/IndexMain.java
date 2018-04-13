import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

// Index Main -  로그아웃일 경우
public class IndexMain extends JFrame implements ActionListener, MouseListener, KeyListener, FocusListener {

	boolean login_out = false;
	static String loginID;
	static String name;

	Connection con;
	Statement st;  
	PreparedStatement ps;
	ResultSet rs;
	DBSington dbs;

	String id;
	String pass;
	String sql;

	// 선언문
	ImageIcon image;

	JMenu m;
	JMenuItem quit, mjoin;
	JMenuBar mb;

	JPanel loginPanel;
	JLabel lableId;
	JLabel lablePw;
	JLabel empty;
	JButton loginBtn;
	JButton joinBtn;

	String[] searchSelectName = { " 검색조건 ", " 상품명 ", " 목적지 ", " 출발일 ", " 도착일 " };
	JComboBox seachSelect;
	JTextField searchText;
	JButton allbtn, searchBtn, buyBtn;

	JTextField tfid, search;
	TextField tfpw;

	// 실제 데이타를 저장할 Object타입의 2차원 배열
	Object[][] rowData;
	// 컬럼명을 저장할 Object타입의 1차원배열
	Object[] columnName = { "No", "상품명", "출발지", "도착지", "출발일", "도착일", "기간", "가격", "개수" };
	JTable table;
	DefaultTableModel goodsTable;

	// 데이타베이스에 접속해서 DML작업을 위한 DAO계열 클래스 변수 선언
	IndexDAO dao = new IndexDAO();

	IndexMain2 custo_login;
	Master master;

	public IndexMain() {
		super("TeamProject - NORTH");
		dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;

		createIndex();
		addIndex();
		// 스크린 중앙에 실행
		setBounds(200, 200, 1000, 700);
		Dimension frameSize =getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
		setVisible(true);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	public void createIndex() { // 생성

		// JMenu 바 - 관리
		m = new JMenu("메뉴");
		mjoin = new JMenuItem("회원가입");
		quit = new JMenuItem("종료");
		mb = new JMenuBar();

		// 버튼 생성

		search = new JTextField(30);

		// 테이블 및 모델 객체 생성
		table = new JTable();

		/* 테이블 꾸미기 */
		// 1]제목 꾸미기 다시 확인부탁
		JTableHeader header = table.getTableHeader();
		//header.setBackground(Color.blue);
		Font hfont = new Font("맑은 고딕", Font.BOLD, 13);
		header.setFont(hfont);
		header.setBackground(new java.awt.Color(105, 158, 222));
		header.setForeground(Color.white);
		//
		header.setPreferredSize(new Dimension(0, 35));
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		/*
		 * resevationTable = new DefaultTableModel();
		 * resevationTable.setDataVector(rowData, columnName);
		 * table.setModel(resevationTable);
		 */

		
		

		
		// 프로그램 최초 실행시 모든 레코드 출력
		List<UserGoodsDTO> list = dao.goodsSelectAll();
		setTableData(list);
		}

		public void setTableData(List<UserGoodsDTO> list) {
			
			   
			//이너클래스로 오름차순 내림차순 6,7,8은 왜????
			goodsTable = new DefaultTableModel(rowData, 0) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
				public Class getColumnClass(int column) {
		               Class returnValue;
		               if ((column >= 0) && (column < getColumnCount())) {
		                 returnValue = getValueAt(0, column).getClass();
		               } else {
		                 returnValue = Object.class;
		               }
		               return returnValue;
		             }

			};		
			 RowSorter<TableModel> sorter =
		             new TableRowSorter<TableModel>(goodsTable);
		           table.setRowSorter(sorter);
		           
		
		Object[][] rowData = new Object[list.size()][columnName.length];
		for (int i = 0; i < list.size(); i++) {
			UserGoodsDTO dto = list.get(i);
			rowData[i][0] = dto.getGid();
			rowData[i][1] = dto.getPackname();
			rowData[i][2] = dto.getOut_p();
			rowData[i][3] = dto.getIn_p();
			rowData[i][4] = dto.getOutdate();
			rowData[i][5] = dto.getIndate();
			rowData[i][6] = dto.getTerm();
			rowData[i][7] = dto.getPrice();
			rowData[i][8] = dto.getEa();
		}
		goodsTable.setDataVector(rowData, columnName);
		table.setModel(goodsTable);
		table.setRowHeight(25);

		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		table.getColumnModel().getColumn(5).setPreferredWidth(50);
		table.getColumnModel().getColumn(6).setPreferredWidth(15);
		table.getColumnModel().getColumn(7).setPreferredWidth(50);
		table.getColumnModel().getColumn(8).setPreferredWidth(15);

//		table.setAutoCreateRowSorter(true);
//		TableRowSorter tablesorter = new TableRowSorter(table.getModel());
//		table.setRowSorter(tablesorter);

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
		// 커서위치 처음으로
		if (goodsTable.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
	}

	private void addIndex() {

		m.add(mjoin);
		m.add(quit);
		mb.add(m);

		setJMenuBar(mb);

		image = new ImageIcon("images.png");
		loginPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(image.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		lableId = new JLabel("I D : ");
		lableId.setForeground(Color.white);
		lablePw = new JLabel("P W : ");
		lablePw.setForeground(Color.white);
		tfid = new JTextField(10);
		tfid.setBorder(new LineBorder(Color.WHITE));
		empty = new JLabel(
				"                                                                                                                                                                            ");
		tfpw = new TextField(10);
		// tfpw.setBorder(new LineBorder(Color.WHITE));
		tfpw.setEchoChar('●');
		loginBtn = new JButton("Login");
		ImageIcon join = new ImageIcon("join.png");
		joinBtn = new JButton(join);

		loginPanel.setPreferredSize(new Dimension(500, 360));
		loginPanel.setBackground(Color.LIGHT_GRAY);
		loginPanel.add(empty);
		loginPanel.add(lableId);
		loginPanel.add(tfid);
		loginPanel.add(lablePw);
		loginPanel.add(tfpw);
		loginPanel.add(loginBtn);
		loginPanel.add(joinBtn);

		add(loginPanel, "North");

		// 검색영역
		JPanel panelS = new JPanel(new BorderLayout(0, 10));
		panelS.setBorder(new TitledBorder("상품보기"));
		panelS.setBackground(Color.white);
		JPanel searchPanel = new JPanel(new FlowLayout());
		seachSelect = new JComboBox(searchSelectName);
		searchText = new JTextField(50);
		ImageIcon search = new ImageIcon("search.png");
		searchBtn = new JButton(search);
		ImageIcon reserve = new ImageIcon("reserve.png");
		buyBtn = new JButton(reserve); 
		ImageIcon list = new ImageIcon("list.png");
		allbtn = new JButton(list);

		searchPanel.setBackground(Color.white);
		searchPanel.add(seachSelect);
		searchPanel.add(searchText);
		searchPanel.add(searchBtn);
		searchPanel.add(buyBtn);
		searchPanel.add(allbtn);

		JPanel GTable = new JPanel(new BorderLayout());
		GTable.setBackground(Color.white);
		GTable.add(new JScrollPane(table));

		panelS.add(searchPanel, "North");
		panelS.add(GTable, "Center");
		panelS.setSize(800, 200);

		add(panelS, "Center");

		searchText.addMouseListener(this);
		seachSelect.addActionListener(this);
		mjoin.addActionListener(this);
		allbtn.addActionListener(this);
		quit.addActionListener(this);
		joinBtn.addActionListener(this);
		searchBtn.addActionListener(this);
		buyBtn.addActionListener(this);
		loginBtn.addActionListener(this);
		tfpw.addKeyListener(this);
		tfpw.addFocusListener(this);
		searchText.addKeyListener(this);
		searchText.addFocusListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == quit) {
			System.exit(0);
		} else if (obj == joinBtn) {
			new UserMemberGUI(this, "회원가입");
		} else if (obj == mjoin) {
			new UserMemberGUI(this, "회원가입");
		} else if (obj == buyBtn) {
			// login 일 때 & logout 일 때 if문 돌려야함//
			JOptionPane.showMessageDialog(this, "로그인 후에 이용해주세요.");
			tfid.requestFocus();
		} else if (obj == loginBtn) {
			loginCheck();
		} else if (obj == searchBtn) {
			String fieldName = seachSelect.getSelectedItem().toString();
			System.out.println("필드명 " + fieldName);
			if (fieldName.trim().equals("검색조건")) {
				setTableData(dao.goodsSelectAll());
			} else {
				if (searchText.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(this, "검색단어를 입력하세요!");
					searchText.requestFocus();
				} else if (fieldName.trim().equals("상품명")) {
					List<UserGoodsDTO> list = dao.goodsSearch1("packname", searchText.getText());
					setTableData(list);
					searchText.setText("");
				} else if (fieldName.trim().equals("목적지")) {
					List<UserGoodsDTO> list = dao.goodsSearch1("in_p", searchText.getText());
					setTableData(list);
					searchText.setText("");
				} else if (fieldName.trim().equals("출발일")) {
					List<UserGoodsDTO> list = dao.goodsSearch2("outdate", searchText.getText());
					setTableData(list);
					searchText.setText("");
				} else if (fieldName.trim().equals("도착일")) {
					List<UserGoodsDTO> list = dao.goodsSearch2("indate", searchText.getText());
					setTableData(list);
					searchText.setText("");
				} // 출발일 도착일은 이거검색하는 방법 조금 더 생각해보자
			}
		} else if (obj == allbtn) {
			setTableData(dao.goodsSelectAll());
		} else if (obj == seachSelect) {
			String fieldName = seachSelect.getSelectedItem().toString();
			if (fieldName.trim().equals("출발일")) {
				searchText.setText("출발일 검색 예)2016-02-01 or 16-02-01 or 16/02/01 or 160201");
			} else if (fieldName.trim().equals("도착일")) {
				searchText.setText("도착일 검색 예)2016-02-01 or 16-02-01 or 16/02/01 or 160201");
			}
		}
	}

	public void loginCheck() {
		id = tfid.getText().trim();
		pass = tfpw.getText().trim();
		if (id.length() == 0) {
			JOptionPane.showMessageDialog(this, "ID를 입력하세요!");
			tfpw.setText(null);
			tfid.requestFocus(); // Focus 맞춰주기.
			return;
		} // id에 입력값이 없는 경우
		else if (pass.length() == 0) {
			JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요!");
			tfpw.requestFocus(); // Focus 맞춰주기.
			return;
		} // pass 에 입력값이 없는 경우
		sql = "select pwd1,master,leave from member_tb where id='" + id + "'";
		System.out.println(sql);
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {// id가 존재한다면 하나의 레코드를 가저온다. while를 쓸 필요없다.
				if (rs.getString("leave").equals("가입")) {
					System.out.println(rs.getString("master"));
					if (rs.getString("master").equals("u")||rs.getString("master").equals("U")) {
						if (pass.equals(rs.getString("pwd1"))) {
							loginID = id;
							name = dao.getName(id);
							JOptionPane.showMessageDialog(this, name + "님 환영합니다!");
							setVisible(false);
							custo_login = new IndexMain2();
							custo_login.setVisible(true);
							rs.close();
							return;
						} else {
							JOptionPane.showMessageDialog(this, "비밀번호가 일치 하지 않습니다!.");
							this.tfpw.setText("");
							this.requestFocus();
						}
					}
					if (rs.getString("master").equals("m")||rs.getString("master").equals("M")) {
						if (pass.equals(rs.getString("pwd1"))) {
							loginID = id;
							name = dao.getName(id);
							JOptionPane.showMessageDialog(this, name + "님 환영합니다!\n관리자모드입니다.");
							setVisible(false);
							master = new Master();
							master.setVisible(true);
							rs.close();
							return;
						} else {
							JOptionPane.showMessageDialog(this, "비밀번호가 일치 하지 않습니다!.");
							this.tfpw.setText("");
							this.requestFocus();
						}
					}
				} else {
					JOptionPane.showMessageDialog(this, "탈퇴하신 아이디 입니다.");
					tfid.setText("");
					tfpw.setText("");
					tfid.requestFocus();
				}
			} else {
				JOptionPane.showMessageDialog(this, "가입하지 않는 아이디 입니다.");
				tfid.setText("");
				tfpw.setText("");
				tfid.requestFocus();
			}
		} catch (Exception e) {
			System.out.println("ID, pass 검색 실패!" + e);
		}
	}

	boolean focusYn = false;
	boolean focus1 = false;

	@Override
	public void keyPressed(KeyEvent e) {
		if (focusYn == true) { // 텍스트필드안에 포커스가 들어오고
			if (e.getKeyCode() == KeyEvent.VK_ENTER) { // 엔터키가 눌러졌으면
				loginBtn.doClick(); // b1을 클릭하라
			}
		}/*else if (focus1 == true) { // 텍스트필드안에 포커스가 들어오고
			if (e.getKeyCode() == KeyEvent.VK_ENTER) { // 엔터키가 눌러졌으면
				searchBtn.doClick(); // b1을 클릭하라
			}
		}*/
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		focusYn = true; // 포커스가 텍스트안으로 들어왔음
		//focus1 = true; // 포커스가 텍스트밖으로 빠져나감

	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		focusYn = false; // 포커스가 텍스트밖으로 빠져나감
		//focus1 = false; //포커스가 텍스트밖으로 빠져나감
	}

	public static void main(String[] args) {
		new IndexMain();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj == searchText) {
			searchText.setText("");
		}
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}