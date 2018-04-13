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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class ReservGUI extends JPanel implements ActionListener, MouseListener, KeyListener, FocusListener {

	JPanel ptext, p1, p2, p3, p4, p5, p6, pbutton, psearch;
	JLabel lgid, lpackname, lea, lid, lreservdate, lempty1;
	JLabel[] lempty = new JLabel[10];
	JTextField tf_gid, tf_packname, tf_ea, tf_id, tf_reservdate;

	JButton btnadd, btnupdate, btndelete, btncancel;
	private static final int NONE = 0;
	private static final int ADD = 1;
	private static final int UPDATE = 2;
	private static final int DELETE = 3;
	private static final int CANCEL = 4;
	int cmd = NONE;

	String strId, strTitle, strWriter, strPub, strCate, strDate, strCount, strLimit, strAvailable;

	Object[] columnName = { "예약번호", "회원ID", "상품번호", "상품명", "상품수량", "예약일", "예약확인" };
	JTable table;
	DefaultTableModel resevationTable;
	// 선택한 행의 인덱스를 저장하기 위한 변수
	int selectedRow = -1;

	String[] comboName = { " 검색조건 ", " 상품번호 ", " 상품명 ", " 회원ID ", " 예약일 " };
	JComboBox combo = new JComboBox(comboName);
	JTextField jtf = new JTextField(20);
	JPanel psbutton;
	JButton allbutton = new JButton("전체보기");
	JButton search = new JButton("검색");

	ReservDAO dao;

	Object[][] rowData;

	int reserv_num = -1;
	String reserv_numS = null;

	public ReservGUI() {

		dao = new ReservDAO();
		System.out.println("2");
		setLayout(new FlowLayout());
		// text

		lgid = new JLabel("상품번호");
		lpackname = new JLabel("상품명");
		lea = new JLabel("상품수량");
		lid = new JLabel("회원ID");
		lreservdate = new JLabel("예약일");
		for (int i = 0; i < 10; i++) {
			lempty[i] = new JLabel("");
		}
		lempty1 = new JLabel("       ");
		tf_gid = new JTextField(25);
		tf_gid.setBorder(new LineBorder(Color.black));
		tf_packname = new JTextField(25);
		tf_packname.setBorder(new LineBorder(Color.black));
		tf_ea = new JTextField(25);
		tf_ea.setBorder(new LineBorder(Color.black));
		tf_id = new JTextField(25);
		tf_id.setBorder(new LineBorder(Color.black));
		tf_reservdate = new JTextField(25);
		tf_reservdate.setBorder(new LineBorder(Color.black));

		ptext = new JPanel(new BorderLayout(10, 10));
		ptext.setBorder(new TitledBorder("예약정보"));

		p1 = new JPanel(new GridLayout(10, 1, 10, 10));
		p1.add(lgid);
		p1.add(lempty[1]);
		p1.add(lpackname);
		p1.add(lempty[2]);
		p1.add(lea);
		p1.add(lempty[3]);
		p1.add(lid);
		p1.add(lempty[4]);
		p1.add(lreservdate);
		p1.add(lempty[5]);
		p2 = new JPanel(new GridLayout(10, 1, 10, 10));
		p2.add(tf_gid);
		p2.add(lempty[6]);
		p2.add(tf_packname);
		p2.add(lempty[7]);
		p2.add(tf_ea);
		p2.add(lempty[8]);
		p2.add(tf_id);
		p2.add(lempty[9]);
		p2.add(tf_reservdate);
		// p3.add(lempty[0]);
		p3 = new JPanel(new GridLayout(10, 1, 10, 10));
		p3.add(lempty1);
		ptext.add(p1, "West");
		ptext.add(p2, "East");
		ptext.add(p3, "Center");

		// 버튼
		pbutton = new JPanel(new FlowLayout());

		btnadd = new JButton("재예약");
		btndelete = new JButton("영구 삭제");
		btnupdate = new JButton("갱신");
		btncancel = new JButton("취소");

		btnadd.addActionListener(this);
		btnupdate.addActionListener(this);
		btndelete.addActionListener(this);
		btncancel.addActionListener(this);

		pbutton.add(btnadd);
		pbutton.add(btndelete);
		pbutton.add(btnupdate);
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
		pp.add(allbutton);
		psearch.add(pp, "East");

		allbutton.addActionListener(this);
		search.addActionListener(this);

		// 테이블 및 모델 객체 생성
		table = new JTable();

		/* 테이블 꾸미기 */
		// 1]제목 꾸미기 다시 확인부탁
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.red);
		header.setForeground(Color.white);
		//
		header.setPreferredSize(new Dimension(0, 30));
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		/*
		 * resevationTable = new DefaultTableModel();
		 * resevationTable.setDataVector(rowData, columnName);
		 * table.setModel(resevationTable);
		 */

		JPanel center = new JPanel(new BorderLayout());
		center.setBorder(new TitledBorder("결과창"));
		center.add(new JScrollPane(table), "Center");
		center.add(psearch, "South");
		add(center);

		table.addMouseListener(this);

		setTableData(dao.reservAll());
		setText(NONE);
		setButton(NONE);
		combo.addActionListener(this);
		jtf.addMouseListener(this);

		combo.addMouseListener(this);
		jtf.addKeyListener(this);
		jtf.addFocusListener(this); // 텍스트안에 포커스가 들어가 있음을 구분하기 위함

		control.setPreferredSize(new Dimension(400, 400));
		center.setPreferredSize(new Dimension(900, 500));

	}

	public void setTableData(List<ReservDTO> list) {
		table.setPreferredScrollableViewportSize(new Dimension(800, 500));

		resevationTable = new DefaultTableModel(rowData, 0) {
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

		Object[][] rowData = new Object[list.size()][columnName.length];
		for (int i = 0; i < list.size(); i++) {
			ReservDTO dto = list.get(i);

			rowData[i][0] = dto.getReserv_num();
			rowData[i][2] = dto.getGid();
			rowData[i][3] = dto.getPackname();
			rowData[i][4] = dto.getEa();
			rowData[i][1] = dto.getId();
			rowData[i][5] = dto.getReservdate();
			rowData[i][6] = dto.getCancel();
		}
		resevationTable.setDataVector(rowData, columnName);
		table.setModel(resevationTable);
		table.setRowHeight(25);

		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);
		table.getColumnModel().getColumn(6).setPreferredWidth(60);

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
		if (resevationTable.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
	}

	private void setText(int command) {
		switch (command) {
		case DELETE:
			tf_gid.setEditable(false);
			tf_packname.setEditable(false);
			tf_ea.setEditable(false);
			tf_id.setEditable(false);
			tf_reservdate.setEditable(false);
			break;
		case UPDATE:
		case CANCEL:
		case NONE:
			tf_gid.setText("");
			tf_gid.setEditable(false);
			tf_packname.setText("");
			tf_packname.setEditable(false);
			tf_ea.setText("");
			tf_ea.setEditable(false);
			tf_id.setText("");
			tf_id.setEditable(false);
			tf_reservdate.setText("");
			tf_reservdate.setEditable(false);
			break;
		}
	}

	private void setButton(int command) {
		// 취소 버튼을 제외하고, 어떤 버튼이 눌리더라도 모든 버튼 비활성화
		btnadd.setEnabled(false);
		btnupdate.setEnabled(false);
		btndelete.setEnabled(false);
		btncancel.setEnabled(false);
		switch (command) {
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
		String fieldName = combo.getSelectedItem().toString();

		Object obj = e.getSource();
		if (obj == btnadd) {
			setText(DELETE);
			if (tf_id.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "회원id를 입력하세요!");
			} else if (tf_gid.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "상품번호를 입력하세요!");
			} else {
				if (dao.reservInsert(reserv_num, Integer.parseInt((tf_gid.getText()))) > 0) {
					JOptionPane.showMessageDialog(this, "재예약 되었습니다!");
					setTableData(dao.reservAll());
				} else {
					JOptionPane.showMessageDialog(this, "이미 예약완료상태이거나 다른 이유로\n재예약을 실패했습니다");
				}
			}
		} else if (obj == btnupdate) {
			setText(UPDATE);
			List<ReservDTO> list = dao.reservUpdate();
			setTableData(list);
		} else if (obj == btndelete) {
			setText(DELETE);
			if (tf_id.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "회원id를 입력하세요!");
			} else if (tf_gid.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "상품번호를 입력하세요!");
			} else {
				if (dao.reservDelete(reserv_num, Integer.parseInt((tf_gid.getText()))) > 0) {
					JOptionPane.showMessageDialog(this, "영구 삭제되었습니다!");
					setTableData(dao.reservAll());
				} else {
					JOptionPane.showMessageDialog(this, "예약완료상태거나 다른 이유로\n영구 삭제를 실패했습니다");
				}
			}
		} else if (obj == btncancel) {
			setText(CANCEL);
		} else if (obj == search) {
			// System.out.println("필드명 " + fieldName);
			if (fieldName.trim().equals("검색조건")) {
				setTableData(dao.reservAll());
			} else {
				if (jtf.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(this, "검색단어를 입력하세요!");
					jtf.requestFocus();
				} else if (fieldName.trim().equals("상품번호")) {
					List<ReservDTO> list = dao.reservSearch2("gid", jtf.getText());
					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(this, "상품번호 검색결과가 없습니다.\n검색어를 확인해주세요");
					} else {
						setTableData(list);
					}
					jtf.setText("");
				} else if (fieldName.trim().equals("상품명")) {
					List<ReservDTO> list = dao.reservSearch("packname", jtf.getText());
					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(this, "상품명 검색결과가 없습니다.\n검색어를 확인해주세요");
					} else {
						setTableData(list);
					}
					jtf.setText("");
				} else if (fieldName.trim().equals("회원ID")) {
					List<ReservDTO> list = dao.reservSearch("id", jtf.getText());
					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(this, "회원ID 검색결과가 없습니다.\n검색어를 확인해주세요");
					} else {
						setTableData(list);
					}
					jtf.setText("");
				} else if (fieldName.trim().equals("예약일")) {
					StringTokenizer rd = new StringTokenizer(jtf.getText().trim(), "/-");
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
					} else if (monthInt == 4 || monthInt == 6 || monthInt == 9 || monthInt == 11) {
						if (dayInt + 1 > 30) {
							dayInt = 0;
							monthInt += 1;
						}
					} else if (monthInt == 12) {
						if (dayInt + 1 > 31) {
							dayInt = 0;
							monthInt = 1;
							yearInt += 1;
						}
					} else {
						if (dayInt + 1 > 28) {
							dayInt = 0;
							monthInt += 1;
						}
					}
					String word2 = yearInt + "/" + monthInt + "/" + (dayInt + 1);

					List<ReservDTO> list = dao.reservSearch3(jtf.getText(), word2);
					if (list.isEmpty()) {
						JOptionPane.showMessageDialog(this, "예약일 검색결과가 없습니다.\n검색어를 확인해주세요");
					} else {
						setTableData(list);
					}
					jtf.setText("");
				}
			}
		} else if (obj == combo) {
			if (fieldName.trim().equals("예약일")) {
				jtf.setText("날짜 검색 예)2016-02-01 or 16-02-01 or 16/02/01");
			}
		} else if (obj == allbutton) {
			setTableData(dao.reservAll());
		}
		setText(NONE);
		setButton(NONE);
	}

	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			// 마우스로 클릭한 테이블의 행 인덱스 얻기
			selectedRow = table.getSelectedRow();
			reserv_numS = table.getValueAt(selectedRow, 0).toString();
			reserv_num = Integer.parseInt(reserv_numS);
			tf_gid.setText(table.getValueAt(selectedRow, 2).toString());
			tf_packname.setText((String) table.getValueAt(selectedRow, 3));
			tf_ea.setText(table.getValueAt(selectedRow, 4).toString());
			tf_id.setText((String) table.getValueAt(selectedRow, 1));
			tf_reservdate.setText(table.getValueAt(selectedRow, 5).toString());
		} else if (e.getSource() == jtf) {
			jtf.setText(" ");
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
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

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

	public Insets getInsets() {
		return new Insets(10, 10, 10, 10);
	}

}
