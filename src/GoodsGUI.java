import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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

public class GoodsGUI extends JPanel implements ActionListener, MouseListener, KeyListener, FocusListener {
	/*
	 * private int gid; //pk키 시퀀스(1번부터 받는다고 immedi ~ private String packname;
	 * private String out_p; private String in_p; private java.sql.Date
	 * outdate;// private java.sql.Date indate; private int term; private int
	 * price; private int ea;
	 */

	JPanel ptext, p1, p2, p3, p4, p5, p6, p7, p8, p9, pbutton, psearch;
	// JLabel lgid, lpackname, lea, lid, lreservdate, lempty1;
	JLabel lgid, lpackname, loutp, linp, loutdate, lindate, lterm, lprice, lea, lempty1;
	JLabel[] lempty = new JLabel[21];
	// JLabel[] lempty = new JLabel[10]; //라벨 포문용 9개임
	JTextField tf_gid, tf_packname, tf_out_p, tf_in_p, tf_outdate, tf_indate, tf_term, tf_price, tf_ea, tf_serach; // 9개

	String strgid, strpackname, strout_p, strin_p, str_outdate, strindate, strterm, strprice, strea, strsearch;

	JButton btnadd, btnupdate, btndelete, btncancel, btnsearch2;

	private static final int NONE = 0;
	private static final int ADD = 1;
	private static final int UPDATE = 2;
	private static final int DELETE = 3;
	private static final int CANCEL = 4;
	int cmd = NONE;

	Object[] columnName = { "상품ID", "상품명", "출발지", "목적지", "출발날짜", "복귀날짜", "기간", "가격", "수량" };

	JTable table;
	DefaultTableModel goodsTable;

	// 선택한 행의 인덱스를 저장하기 위한 변수
	int selectedRow = -1;
	// 콤보 박스
	String[] comboName = { " 검색조건 ", " 상품ID ", " 상품명 ", " 목적지 ", " 출발일 ", " 도착일 " };
	JComboBox combo = new JComboBox(comboName);
	JTextField jtf = new JTextField(20);
	JButton search = new JButton("검색");
	GoodsDAO dao = new GoodsDAO();
	Object[][] rowData;
	// 컬럼명을 저장할 오브젝트 타입 배열

	public GoodsGUI() {

		//////
		setLayout(new FlowLayout());
		// 레이블
		lgid = new JLabel("상 품 아 이 디");
		lpackname = new JLabel("상   품   명");
		loutp = new JLabel("출  발  지");
		linp = new JLabel("목  적  지");
		loutdate = new JLabel("출 발 날 짜");
		lindate = new JLabel("복 귀 날 짜");
		lterm = new JLabel("체 류 기 간");
		lprice = new JLabel("가       격");
		lea = new JLabel("수       량");

		for (int i = 0; i < 20; i++) {
			lempty[i] = new JLabel("");
		}

		// text
		lempty1 = new JLabel("   ");
		tf_gid = new JTextField(25);
		tf_gid.setBorder(new LineBorder(Color.black));
		tf_gid.setPreferredSize(new Dimension(0, 25));
		tf_packname = new JTextField(25);
		tf_packname.setBorder(new LineBorder(Color.black));
		tf_out_p = new JTextField(25);
		tf_out_p.setBorder(new LineBorder(Color.black));
		tf_in_p = new JTextField(25);
		tf_in_p.setBorder(new LineBorder(Color.black));
		tf_outdate = new JTextField(25);
		tf_outdate.setBorder(new LineBorder(Color.black));
		tf_indate = new JTextField(25);
		tf_indate.setBorder(new LineBorder(Color.black));
		tf_term = new JTextField(25);
		tf_term.setBorder(new LineBorder(Color.black));
		tf_price = new JTextField(25);
		tf_price.setBorder(new LineBorder(Color.black));
		tf_ea = new JTextField(25);
		tf_ea.setBorder(new LineBorder(Color.black));

		ptext = new JPanel(new BorderLayout(10, 10));
		ptext.setBorder(new TitledBorder("회원정보"));

		// 좌측 입력 받는창
		p1 = new JPanel(new GridLayout(9, 1, 10, 10));
		// p1.add(lempty[0]);
		p1.add(lgid);
		p1.add(lpackname);
		p1.add(loutp);
		p1.add(linp);
		p1.add(loutdate);
		p1.add(lindate);
		p1.add(lterm);
		p1.add(lprice);
		p1.add(lea);
		// p1.add(lempty[1]);

		p2 = new JPanel(new GridLayout(9, 1, 15, 15));
		// p2.add(lempty[2]);
		p2.add(tf_gid);
		p2.add(tf_packname);
		p2.add(tf_out_p);
		p2.add(tf_in_p);
		p2.add(tf_outdate);
		p2.add(tf_indate);
		p2.add(tf_term);
		p2.add(tf_price);
		p2.add(tf_ea);
		// p2.add(lempty[3]);

		p3 = new JPanel(new GridLayout(9, 1));
		p3.add(lempty1);
		ptext.add(p1, "West");
		ptext.add(p2, "East");
		ptext.add(p3, "Center");

		pbutton = new JPanel(new FlowLayout());

		btnadd = new JButton("추가");
		btnupdate = new JButton("수정");
		btndelete = new JButton("삭제");
		btncancel = new JButton("취소");
		// 추가 전체검색 수정
		btnsearch2 = new JButton("전체 보기");

		btnadd.addActionListener(this);
		btnupdate.addActionListener(this);
		btndelete.addActionListener(this);
		btncancel.addActionListener(this);
		// 추가 전체검색 이벤트

		pbutton.add(btnadd);
		pbutton.add(btnupdate);
		pbutton.add(btndelete);
		pbutton.add(btncancel);

		JPanel control = new JPanel(new BorderLayout());
		control.add(ptext, "Center");
		control.add(pbutton, "South");
		add(control);

		psearch = new JPanel(new BorderLayout());
		JPanel pp = new JPanel();
		// psearch.setBackground(Color.YELLOW);
		psearch.add(combo, "West");
		psearch.add(jtf, "Center");
		pp.add(search);
		pp.add(btnsearch2);
		psearch.add(pp, "East");

		search.addActionListener(this);
		btnsearch2.addActionListener(this);
		table = new JTable();

		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.blue);
		header.setForeground(Color.white);
		//
		header.setPreferredSize(new Dimension(0, 30));
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);

		JPanel center = new JPanel(new BorderLayout());
		center.setBorder(new TitledBorder("결과창"));
		center.add(new JScrollPane(table), "Center");
		center.add(psearch, "South");
		add(center);

		table.addMouseListener(this);
		setTableData(dao.getRecordAll());
		setText(NONE);
		setButton(NONE);
		combo.addMouseListener(this);

		jtf.addKeyListener(this); //
		jtf.addFocusListener(this); // 텍스트안에 포커스가 들어가 있음을 구분하기 위함

		combo.addActionListener(this);
		jtf.addMouseListener(this);
		// table.isCellEditable(col, 0){ return false};
		tf_outdate.addMouseListener(this);
		tf_indate.addMouseListener(this);

		control.setPreferredSize(new Dimension(400, 400));
		center.setPreferredSize(new Dimension(900, 500));
		tf_outdate.setText("검색예시) 16-02-01 or 16/02/01");
		tf_indate.setText("검색예시) 16-03-01 or 16/03/01");
		//검색예시) 16-02-01 or 16/02/01
		//검색예시) 16-03-01 or 16/03/01
	}

	public Insets getInsets() {
		return new Insets(10, 10, 10, 10);
	}

	public void setTableData(List<GoodsDTO> list) {

		table.setPreferredScrollableViewportSize(new Dimension(900, 500));

		// 이너클래스로 오름차순 내림차순 6,7,8은 왜????
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
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(goodsTable);
		table.setRowSorter(sorter);

		Object[][] rowData = new Object[list.size()][columnName.length];
		for (int i = 0; i < list.size(); i++) {
			GoodsDTO dto = list.get(i);
			// dto 수정
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

		// 테이블 너비 조절....
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(40);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		table.getColumnModel().getColumn(6).setPreferredWidth(40);
		table.getColumnModel().getColumn(7).setPreferredWidth(40);
		table.getColumnModel().getColumn(8).setPreferredWidth(40);

		// 제이테이블 가운데 정렬......
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

		if (goodsTable.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
	}
	

	// 텍스트필드 초기화
	private void setText(int command) {
		switch (command) {

		case ADD:
			tf_gid.setText("");
			tf_gid.setEditable(false);
			tf_packname.setText("");
			tf_packname.setEditable(true);
			tf_out_p.setText("");
			tf_out_p.setEditable(true);
			tf_in_p.setText("");
			tf_in_p.setEditable(true);
			tf_outdate.setText("");
			tf_outdate.setEditable(true);
			tf_indate.setText("");
			tf_indate.setEditable(true);
			tf_term.setText("");
			tf_term.setEditable(false);
			tf_price.setText("");
			tf_price.setEditable(true);
			tf_ea.setText("");
			tf_ea.setEditable(true);
			break;
		// case DELETE:
		//
		// tf_gid.setEditable(false);
		//
		// tf_packname.setEditable(true);
		//
		// tf_out_p.setEditable(true);
		//
		// tf_in_p.setEditable(true);
		//
		// tf_outdate.setEditable(true);
		//
		// tf_indate.setEditable(true);
		//
		// tf_term.setEditable(true);
		//
		// tf_price.setEditable(true);
		//
		// tf_ea.setEditable(true);
		// break;
		case UPDATE:
			tf_gid.setEditable(false);
			tf_packname.setEditable(true);
			tf_out_p.setEditable(true);
			tf_in_p.setEditable(true);
			tf_outdate.setEditable(true);
			tf_indate.setEditable(true);
			tf_term.setText("");
			tf_term.setEditable(false);
			tf_price.setEditable(true);
			tf_ea.setEditable(true);
			break;

		case CANCEL:
			tf_gid.setText("");
			tf_gid.setEditable(false);
			tf_packname.setText("");
			tf_packname.setEditable(true);
			tf_out_p.setText("");
			tf_out_p.setEditable(true);
			tf_in_p.setText("");
			tf_in_p.setEditable(true);
			tf_outdate.setText("검색예시) 16-02-01 or 16/02/01");
			tf_outdate.setEditable(true);
			tf_indate.setText("검색예시) 16-03-01 or 16/03/01");
			tf_indate.setEditable(true);
			tf_term.setText("");
			tf_term.setEditable(true);
			tf_price.setText("");
			tf_price.setEditable(true);
			tf_ea.setText("");
			tf_ea.setEditable(true);
			break;

		case NONE:
			//검색예시) 16-02-01 or 16/02/01
			//검색예시) 16-03-01 or 16/03/01
			
			tf_gid.setText("");
			tf_gid.setEditable(false);
			tf_packname.setText("");
			tf_packname.setEditable(false);
			tf_out_p.setText("");
			tf_out_p.setEditable(false);
			tf_in_p.setText("");
			tf_in_p.setEditable(false);
			tf_outdate.setText("검색예시) 16-02-01 or 16/02/01");
			tf_outdate.setEditable(false);
			tf_indate.setText("검색예시) 16-03-01 or 16/03/01");
			tf_indate.setEditable(false);
			tf_term.setText("");
			tf_term.setEditable(false);
			tf_price.setText("");
			tf_price.setEditable(false);
			tf_ea.setText("");
			tf_ea.setEditable(false);
			break;
		}
	}

	// 버튼 활성화 비활성화
	private void setButton(int command) {
		// 취소 버튼을 제외하고, 어떤 버튼이 눌리더라도 모든 버튼 비활성화
		btnadd.setEnabled(false);
		btnupdate.setEnabled(false);
		btndelete.setEnabled(false);
		btncancel.setEnabled(false);
		switch (command) {
		case ADD:
			btnadd.setEnabled(true);
			btncancel.setEnabled(true);
			cmd = ADD;
			break;
		case UPDATE:
			btnupdate.setEnabled(true);
			btncancel.setEnabled(true);
			cmd = UPDATE;
			break;
		// case DELETE:
		// btndelete.setEnabled(true);
		// btncancel.setEnabled(true);
		// cmd = DELETE;
		// break;
		case NONE:
			btnadd.setEnabled(true);
			btnupdate.setEnabled(true);
			btndelete.setEnabled(true);
			btncancel.setEnabled(true);
			cmd = NONE;
			break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		if (obj == btnadd) {
			try {
				if (cmd != ADD) {
					setText(ADD);
					setButton(ADD);
					return;
				}
				if (tf_packname.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(this, "상품명을 입력하세요!");
				} else {
					// String outdate= tf_out_p.getText(); //"2016-12-25";
					// String indate= tf_in_p.getText(); //"2016-12-30";
					String outdate1 = null;
					String outdate2 = null;
					String outdate3 = null;
					// String outdate4=null;
					String indate1 = null;
					String indate2 = null;
					String indate3 = null;
					// String indate4=null;
					long d1, d2 = 0;
					int days = 0;

					StringTokenizer terms1 = new StringTokenizer(tf_outdate.getText().trim(), "-/"); // 여기
																										// 수정
					String[] tmtoken1 = new String[3];
					int i = 0;
					while (terms1.hasMoreTokens()) {
						tmtoken1[i] = terms1.nextToken();
						i++;
					}
					outdate1 = tmtoken1[0].trim();
					outdate2 = tmtoken1[1].trim();
					outdate3 = tmtoken1[2].trim();
					System.out.println(outdate1 + outdate2 + outdate3);

					StringTokenizer terms2 = new StringTokenizer(tf_indate.getText().trim(), "-/");// 여기
																									// 수정
					String[] tmtoken2 = new String[3];
					int c = 0;
					while (terms2.hasMoreTokens()) {
						tmtoken2[c] = terms2.nextToken();
						c++;
					}
					indate1 = tmtoken2[0].trim();
					indate2 = tmtoken2[1].trim();
					indate3 = tmtoken2[2].trim();
					System.out.println(indate1 + indate2 + indate3);
					// 객체 생성
					// 날짜 지정
					Calendar c1 = Calendar.getInstance();
					Calendar c2 = Calendar.getInstance();
					c1.set(Integer.parseInt(outdate1), Integer.parseInt(outdate2) - 1, Integer.parseInt(outdate3));
					c2.set(Integer.parseInt(indate1), Integer.parseInt(indate2) - 1, Integer.parseInt(indate3));
					// MilliSecond 로 변환
					d1 = c1.getTime().getTime(); // outdate
					d2 = c2.getTime().getTime(); // indate
				
					if (!checkInput(days))
						return; // 유효성체크

					if (dao.Insert(tf_packname.getText(), tf_out_p.getText(), tf_in_p.getText(), tf_outdate.getText(),
							tf_indate.getText(), days, // Integer.parseInt(tf_term.getText()),
							Integer.parseInt(tf_price.getText()), Integer.parseInt(tf_ea.getText())) > 0) {
						JOptionPane.showMessageDialog(this, "추가되었습니다!");
						setTableData(dao.getRecordAll());

					} else {
						JOptionPane.showMessageDialog(this, "예약 취소를 실패했습니다");
					}
				}
			} catch (Exception ee) {
				System.out.println(ee + "자료 추가 오류 발생");

			}
			setText(NONE);
			setButton(NONE);
		} else if (obj == btnsearch2) {
			try {
				setTableData(dao.getRecordAll());
				jtf.setText("");
			} catch (Exception eee) {
				System.out.println(eee + "에러 안날듯 함");
			}
		} else if (obj == btnupdate) {
			try {
				if (cmd != UPDATE) {
					setText(UPDATE);
					setButton(UPDATE);
					return;
				}

				
				String outdate1 = null;
				String outdate2 = null;
				String outdate3 = null;
				// String outdate4=null;
				String indate1 = null;
				String indate2 = null;
				String indate3 = null;
				// String indate4=null;
				long d1, d2 = 0;
				int days = 0;
				StringTokenizer terms1 = new StringTokenizer(tf_outdate.getText().trim(), "-/"); // 여기
																									// 수정
				String[] tmtoken1 = new String[3];
				int i = 0;
				while (terms1.hasMoreTokens()) {
					tmtoken1[i] = terms1.nextToken();
					i++;
				}
				outdate1 = tmtoken1[0].trim();
				outdate2 = tmtoken1[1].trim();
				outdate3 = tmtoken1[2].trim();
				System.out.println(outdate1 + outdate2 + outdate3);

				StringTokenizer terms2 = new StringTokenizer(tf_indate.getText().trim(), "-/");// 여기
																								// 수정
				String[] tmtoken2 = new String[3];
				int c = 0;
				while (terms2.hasMoreTokens()) {
					tmtoken2[c] = terms2.nextToken();
					c++;
				}
				indate1 = tmtoken2[0].trim();
				indate2 = tmtoken2[1].trim();
				indate3 = tmtoken2[2].trim();
				System.out.println(indate1 + indate2 + indate3);
				// 객체 생성
				// 날짜 지정
				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				c1.set(Integer.parseInt(outdate1), Integer.parseInt(outdate2) - 1, Integer.parseInt(outdate3));
				c2.set(Integer.parseInt(indate1), Integer.parseInt(indate2) - 1, Integer.parseInt(indate3));
				// MilliSecond 로 변환
				d1 = c1.getTime().getTime(); // outdate
				d2 = c2.getTime().getTime(); // indate
				// 계산
				days = (int) ((d2 - d1) / (1000 * 60 * 60 * 24) + 1); 
				System.out.print(days);

				if (!checkInput(days))
					return; // 유효성체크

				
				if (dao.update(tf_packname.getText(), tf_out_p.getText(), tf_in_p.getText(), tf_outdate.getText(),
						tf_indate.getText(), days, Integer.parseInt(tf_price.getText()),
						Integer.parseInt(tf_ea.getText()), Integer.parseInt(tf_gid.getText())) > 0) {
					JOptionPane.showMessageDialog(this, "수정되었습니다!");
					setTableData(dao.getRecordAll());
				} else {
					JOptionPane.showMessageDialog(this, "업데이트에 실패했습니다");
				}

				// dao.update(gid, packname)
			} catch (Exception ee) {
				System.out.println(ee + "업데이트 오류 발생");
			}

			setText(NONE);
			setButton(NONE);
		}

		else if (obj == btndelete) {// 완료
			try {
				// dao.Delete(Integer.parseInt(tf_gid.setText(),tf_packname.getText())
				if (dao.Delete(Integer.parseInt(tf_gid.getText())) > 0) {
					JOptionPane.showMessageDialog(this, "삭제 되었습니다!");
					setTableData(dao.getRecordAll());
				} else {
					JOptionPane.showMessageDialog(this, "데이터 삭제에  실패했습니다");
				}
				// dao.Delete(Integer.parseInt(tf_gid.getText()),
				// tf_packname.getText());
				// setTableData(dao.getRecordAll());
			} catch (Exception ee) {
				System.out.println(ee + "삭제 오류 발생");
			}
			setText(NONE);
			setButton(NONE);
		}
		// "검색조건", "상품번호", "상품명", "전체보기"
		else if (obj == btncancel) {
			setText(CANCEL);
			setTableData(dao.getRecordAll());
			// JOptionPane.showMessageDialog(this, "취소하였습니다.");
			setText(NONE);
			setButton(NONE);
		}

		else if (obj == search) {
			String fieldName = combo.getSelectedItem().toString();
			// System.out.println("필드명 " + fieldName);

			/*
			 * if (fieldName.trim().equals("검색조건")) {
			 * setTableData(dao.getRecordAll());
			 * //JOptionPane.showMessageDialog(this, "리셋하였습니다."); } else {
			 */

			if (jtf.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "검색단어를 입력하세요!");
				jtf.requestFocus();
			}
			if (fieldName.trim().equals("상품ID")) {
				List<GoodsDTO> list = dao.getRecordsearch3("gid", jtf.getText());
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(this, "없는 상품ID입니다!");
				} else {
					setTableData(list);
				}
				jtf.setText("");
			} else if (fieldName.trim().equals("상품명")) {
				List<GoodsDTO> list = dao.getRecordsearch("packname", jtf.getText());
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(this, "없는 상품명입니다!");
				} else {
					setTableData(list);
				}
				jtf.setText("");
			} else if (fieldName.trim().equals("목적지")) {
				List<GoodsDTO> list = dao.getRecordsearch("in_p", jtf.getText());
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(this, "없는 목적지입니다!");
				} else {
					setTableData(list);
				}
				jtf.setText("");
			} else if (fieldName.trim().equals("출발일")) {
				List<GoodsDTO> list = dao.getRecordsearch2("outdate", jtf.getText());
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(this, "없는 출발일입니다!");
				} else {
					setTableData(list);
				}
				jtf.setText("");
			} else if (fieldName.trim().equals("도착일")) {
				List<GoodsDTO> list = dao.getRecordsearch2("indate", jtf.getText());
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(this, "없는 도착일입니다!");
				} else {
					setTableData(list);
				}
				jtf.setText("");
			}
		} else if (obj == combo) {
			String fieldName = combo.getSelectedItem().toString();
			if (fieldName.trim().equals("출발일") || fieldName.trim().equals("도착일")) {
				jtf.setText("검색예시) 2016-02-01 or 16-02-01 or 16/02/01 or 160201");
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			selectedRow = table.getSelectedRow();
			// TableModel model = table.getModel();
			tf_gid.setText(table.getValueAt(selectedRow, 0).toString());
			tf_packname.setText((String) table.getValueAt(selectedRow, 1));
			tf_out_p.setText((String) table.getValueAt(selectedRow, 2));
			tf_in_p.setText((String) table.getValueAt(selectedRow, 3));
			tf_outdate.setText((String) table.getValueAt(selectedRow, 4).toString());
			tf_indate.setText((String) table.getValueAt(selectedRow, 5).toString());
			tf_term.setText(table.getValueAt(selectedRow, 6).toString());
			tf_price.setText(table.getValueAt(selectedRow, 7).toString());
			tf_ea.setText(table.getValueAt(selectedRow, 8).toString());
		} else if (e.getSource() == jtf) {// 콤보박스 필드 버튼 이벤트 필드 날림....
			jtf.setText("");
		} 
		
	/*	else if (e.getSource() == tf_outdate) {
			tf_outdate.setText("");
		} else if (e.getSource() == tf_indate) {
			tf_indate.setText("");
		}   */
	}

	// 입력 체크
	public boolean checkInput(int days) {
		if (tf_packname.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(GoodsGUI.this, "상품명을 입력하세요!");
			tf_packname.requestFocus();
			return false;
		} else if (tf_out_p.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(GoodsGUI.this, "출발지를 입력하세요!");
			tf_out_p.requestFocus();
			return false;
		} else if (tf_in_p.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(GoodsGUI.this, "도착지를 입력하세요!");
			tf_in_p.requestFocus();
			return false;
		} else if (tf_outdate.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(GoodsGUI.this, "출발날짜를 입력하세요!");
			tf_outdate.requestFocus();
			return false;
		} else if (tf_indate.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(GoodsGUI.this, "복귀날짜를 입력하세요!");
			tf_indate.requestFocus();
			return false;
		} else if (days < 0) {
			JOptionPane.showMessageDialog(GoodsGUI.this, "복귀날짜가 출발날짜보다 빠릅니다.");
			tf_indate.requestFocus();
			return false;
		} else if (tf_price.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(GoodsGUI.this, "상품 금액을 입력하세요!");
			tf_price.requestFocus();
			return false;
		} else if (tf_price.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(GoodsGUI.this, "상품명을 입력하세요!");
			tf_price.requestFocus();
			return false;
		} else if (tf_ea.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(GoodsGUI.this, "상품 갯수를 입력하세요!");
			tf_ea.requestFocus();
			return false;
		} else {// SEARCH인 경우
			if ((tf_gid.getText().trim().equals("") && tf_packname.getText().trim().length() == 0)
					|| tf_gid.getText().trim().length() >= 9) {
				JOptionPane.showMessageDialog(GoodsGUI.this, "유효한 검색 문자열이 아니예요!");
				return false;
			}

		}

		return true;
	}

	// 오버라이딩....
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	// 포커스 ㅇㅅㅇ
	boolean focusYn = false;

	@Override
	public void focusGained(FocusEvent arg0) {
		focusYn = true; // 포커스가 텍스트안으로 들어왔음

	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		focusYn = false; // 포커스가 텍스트밖으로 빠져나감
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (focusYn == true) { // 텍스트필드안에 포커스가 들어오고
			if (e.getKeyCode() == KeyEvent.VK_ENTER) { // 엔터키가 눌러졌으면
				search.doClick(); // b1을 클릭하라
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
