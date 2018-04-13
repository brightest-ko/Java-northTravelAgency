import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
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
import javax.swing.JPasswordField;
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

// Index Main 로그인 경우
public class IndexMain2 extends JFrame implements ActionListener,MouseListener,KeyListener,FocusListener {

	boolean login_out=false;
	// 선언문
	ImageIcon image;

	JMenu m;
	JMenuItem quit, logout;
	JMenuBar mb;

	JPanel loginPanel;
	JLabel lableId;
	JLabel lablePw;
	JLabel empty;
	JLabel hello;
	JButton logoutBtn;
	JButton loginBtn;
	JButton myBtn, buyBtn;

	String[] searchSelectName = { " 검색조건 ", " 상품명 ", " 목적지 ", " 출발일 ", " 도착일 " };
	JComboBox seachSelect;
	JTextField searchText;
	JButton allbtn, searchBtn;
	JButton buylistBtn;

	JTextField tfid, search;
	JPasswordField tfpw;

	// 실제 데이타를 저장할 Object타입의 2차원 배열
	Object[][] rowData;
	// 컬럼명을 저장할 Object타입의 1차원배열
	Object[] columnName = { "No", "상품명", "출발지", "도착지", "출발일", "도착일", "기간", "가격", "개수" };
	JTable table;
	DefaultTableModel goodsTable;

	// 데이타베이스에 접속해서 DML작업을 위한 DAO계열 클래스 변수 선언
	IndexDAO dao;

	UserReservDAO rdao = new UserReservDAO();

	IndexMain custo_logout;
	//String loginID;
	String loginID = custo_logout.loginID;
	String name = custo_logout.name;

	String gidS;
	int gid=-1;
	int selectedRow = -1;

	public IndexMain2() {
		
		super("TeamProject - NORTH _ Login");
		
		createIndex();
		addIndex();

		// JFrame 스크린 중앙에 실행
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

		// dao객체 생성
		dao = new IndexDAO();

		// JMenu 바 - 관리
		m = new JMenu("메뉴");
		logout = new JMenuItem("로그아웃");
		quit = new JMenuItem("종료");
		mb = new JMenuBar();

		// 버튼 생성

		search = new JTextField(30);

		// 테이블 및 모델 객체 생성
		table = new JTable();

		/* 테이블 꾸미기 */
		// 1]제목 꾸미기 다시 확인부탁
		JTableHeader header = table.getTableHeader();
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

		goodsTable = new DefaultTableModel(rowData, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		// 프로그램 최초 실행시 모든 레코드 출력
		List<UserGoodsDTO> list = dao.goodsSelectAll();
		setTableData(list);
	}

	public void setTableData(List<UserGoodsDTO> list) {

		
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
		// 커서위치 처음으로
		if (goodsTable.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
	}

	private void addIndex() {
		
		

		m.add(logout);
		m.add(quit);
		mb.add(m);

		setJMenuBar(mb);

		// 로그인 영역
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
				"                                                                                                                                                                                     ");
		tfpw = new JPasswordField(10);
		tfpw.setBorder(new LineBorder(Color.WHITE));
		logoutBtn = new JButton("Logout");
		// String name =dao.selectname;
		hello = new JLabel(name+"님 반갑습니다!   ");
		hello.setForeground(Color.white);
		// 내 정보 (수정)버튼
		ImageIcon my = new ImageIcon("my1.png");
		myBtn = new JButton(my);
		// 예약 확인 버튼
		ImageIcon buy = new ImageIcon("buy.png");
		buylistBtn = new JButton(buy);

		loginPanel.setPreferredSize(new Dimension(500, 370));
		loginPanel.setBackground(Color.LIGHT_GRAY);
		loginPanel.add(empty);
		loginPanel.add(hello);
		loginPanel.add(logoutBtn);
		loginPanel.add(myBtn);
		loginPanel.add(buylistBtn);

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
		allbtn.addActionListener(this);		
		logout.addActionListener(this); // ID 중복 체크 이벤트 등록
		buyBtn.addActionListener(this);
		buylistBtn.addActionListener(this);
		searchBtn.addActionListener(this);
		logoutBtn.addActionListener(this);
		quit.addActionListener(this);
		table.addMouseListener(this);
		myBtn.addActionListener(this);
		searchText.addKeyListener(this);
		searchText.addFocusListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == quit) {
			System.exit(0);
		} else if (e.getSource() == buyBtn) {
			// login 일 때 & logout 일 때 if문 돌려야함//
			String message1 = "구매하시겠습니까?";
			String message2 = "구매되었습니다. \n예약 정보를 확인하시겠습니까?";
			int ans1 = JOptionPane.showConfirmDialog(this, message1, "Exit", JOptionPane.YES_OPTION);
			if (ans1 == JOptionPane.YES_OPTION) {
				// 구매하기 dao불러오기
				System.out.println(gid);
				if (rdao.reservInsert(loginID, gid) > 0) {
					int ans2 = JOptionPane.showConfirmDialog(this, message2, "Exit", JOptionPane.YES_OPTION);
					if (ans2 == JOptionPane.YES_OPTION) {
						// 예약정보확인 dial table 불러오기
						new UserReserveGUI(loginID);
					}
				} else {
					JOptionPane.showMessageDialog(this, "예약 상품이 매진되었습니다.");
				}
			}
		} else if (e.getSource() == buylistBtn) {
			new UserReserveGUI(loginID);
		} else if (e.getSource() == logoutBtn) {
			dispose();
			custo_logout = new IndexMain();
			custo_logout.setVisible(true);
		}else if (e.getSource() == logout) {
			dispose();
			custo_logout = new IndexMain();
			custo_logout.setVisible(true);
		}
		/*
		 * else if(e.getSource() == update){ new MemberGUI(this, "수정"); } else
		 * if(e.getSource() == delete){ int row = table.getSelectedRow();
		 * System.out.println("선택 행 : " + row); Object obj =
		 * table.getValueAt(row, 0); // 행 열에 해당하는 value
		 * System.out.println("값 : " + obj);
		 * 
		 * if(dao.userDelete(obj.toString()) > 0){ MemberGUI.messageBox(this,
		 * "레코드 삭제"); dao.memberSelectAll(goodsTable);
		 * if(goodsTable.getRowCount() > 0){ table.setRowSelectionInterval(0,
		 * 0); } } else{ MemberGUI.messageBox(this, "레코드가 삭제되지 않음."); } }
		 */
		else if (obj == quit) {
			System.exit(0);
		} else if (obj == searchBtn) {
			String fieldName = seachSelect.getSelectedItem().toString();
			System.out.println("필드명 " + fieldName);
			if (fieldName.trim().equals("전체보기")) {
				setTableData(dao.goodsSelectAll());
			} else {
				if (searchText.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(this, "검색단어를 입력하세요!");
					searchText.requestFocus();
				} else if (fieldName.trim().equals("상품명")) {
					System.out.println("1");
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
		}else if (obj == allbtn) {
			setTableData(dao.goodsSelectAll());
		}
		else if (obj == myBtn) {
			UserMemberGUI2 um = new UserMemberGUI2(this, "회원정보수정",loginID);	
		}
		else if(obj==seachSelect){
			String fieldName = seachSelect.getSelectedItem().toString();
			if (fieldName.trim().equals("출발일")) {
				searchText.setText("출발일 검색 예)2016-02-01 or 16-02-01 or 16/02/01 or 160201");
			}else if (fieldName.trim().equals("도착일")) {
				searchText.setText("도착일 검색 예)2016-02-01 or 16-02-01 or 16/02/01 or 160201");
			}
		}
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		if (obj == table) {
			// 마우스로 클릭한 테이블의 행 인덱스 얻기
			selectedRow = table.getSelectedRow();
			gidS = table.getValueAt(selectedRow, 0).toString();
			gid = Integer.parseInt(gidS);
		}
		else if(obj==searchText){
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
	boolean focus1 = false;

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		 focus1 = true; //포커스가 텍스트안으로 들어왔음
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		focus1 = false; //포커스가 텍스트밖으로 빠져나감
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (focus1 == true) { // 텍스트필드안에 포커스가 들어오고
			if (e.getKeyCode() == KeyEvent.VK_ENTER) { // 엔터키가 눌러졌으면
				searchBtn.doClick(); // b1을 클릭하라
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}