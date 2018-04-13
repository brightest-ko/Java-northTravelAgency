import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class UserReserveGUI extends JFrame implements ActionListener, MouseListener {

	JPanel rvPanel, lbPanel;
	JButton exitBtn;
	JButton delBtn;
	JLabel rslabel;
	Object[][] rowData;
	Object[] columnName = { "예약번호","No", "상품명", "출발지", "도착지", "출발일", "도착일", "기간", "가격", "구매날짜", "예약확인" };
	DefaultTableModel reserv_tb;

	JTable table1 = new JTable(reserv_tb);
	
	JScrollPane jsp1 = new JScrollPane(table1);
	int selectedRow = -1;

	JPanel rp = new JPanel();
	UserReservDAO dao;

	ImageIcon bg;
	
	//IndexMain custo_logout;
	//String loginID = custo_logout.loginID;
	String loginID;
	String gidS;
	int gid;
	
	String reserv_numS;
	int reserv_num;

	public UserReserveGUI(String loginID) {
		super("예약 내역");
		

		// JFrame 스크린 중앙에 실행
		setBounds(200, 200, 960, 420);
		Dimension frameSize =getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
		setVisible(true);
		this.loginID = loginID;
		createIndex();
		addIndex();
		//setSize(700, 500);
		setResizable(false);

		// System.out.println(loginID);
		List<UserReservDTO> list = dao.reservselectAll(loginID);
		setTableData(list);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

	}

	public void createIndex() {
		dao = new UserReservDAO();
		
		JTableHeader header1 = table1.getTableHeader();
		header1.setBackground(Color.white);
		header1.setForeground(Color.black);
		Font hfont = new Font("맑은고딕", Font.BOLD, 12);
		header1.setFont(hfont);
		header1.setPreferredSize(new Dimension(0, 30));
		header1.setReorderingAllowed(false);
		header1.setResizingAllowed(false);

		// List<IndexDTO> list =dao.reservselectAll(reserv_tb);
		// setTableData(list);
	}

	public void setTableData(List<UserReservDTO> list) {

		reserv_tb = new DefaultTableModel();

		Object[][] rowData = new Object[list.size()][columnName.length];
		for (int i = 0; i < list.size(); i++) {
			UserReservDTO dto = list.get(i);
			rowData[i][0] = dto.getReserv_num();
			rowData[i][1] = dto.getGid();
			rowData[i][2] = dto.getPackname();
			rowData[i][3] = dto.getOut_p();
			rowData[i][4] = dto.getIn_p();
			rowData[i][5] = dto.getOutdate();
			rowData[i][6] = dto.getIndate();
			rowData[i][7] = dto.getTerm();
			rowData[i][8] = dto.getPrice();
			rowData[i][9] = dto.getReservdate();
			rowData[i][10] = dto.getCancel();
		}
		reserv_tb.setDataVector(rowData, columnName);
		table1.setModel(reserv_tb);
		table1.setRowHeight(25);
		

		table1.getColumnModel().getColumn(0).setPreferredWidth(30);
		table1.getColumnModel().getColumn(1).setPreferredWidth(10);
		table1.getColumnModel().getColumn(2).setPreferredWidth(170);
		table1.getColumnModel().getColumn(3).setPreferredWidth(45);
		table1.getColumnModel().getColumn(4).setPreferredWidth(45);
		table1.getColumnModel().getColumn(5).setPreferredWidth(50);
		table1.getColumnModel().getColumn(6).setPreferredWidth(50);
		table1.getColumnModel().getColumn(7).setPreferredWidth(10);
		table1.getColumnModel().getColumn(8).setPreferredWidth(40);
		table1.getColumnModel().getColumn(9).setPreferredWidth(40);
		table1.getColumnModel().getColumn(10).setPreferredWidth(40);

		table1.setAutoCreateRowSorter(true);
		TableRowSorter table1sorter = new TableRowSorter(table1.getModel());
		table1.setRowSorter(table1sorter);

		
		// JTable 정렬......
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer dtc = new DefaultTableCellRenderer();
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		dtc.setHorizontalAlignment(SwingConstants.CENTER);
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcms = table1.getColumnModel();
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcms.getColumnCount(); i++) {
			tcms.getColumn(i).setCellRenderer(dtc);
		}
		if (reserv_tb.getRowCount() > 0)
			table1.setRowSelectionInterval(0, 0);
	}

	public void addIndex() {
		bg = new ImageIcon("title.png");
		lbPanel = new JPanel() {
			public void paintComponent(Graphics g){
				g.drawImage(bg.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		lbPanel.setPreferredSize(new Dimension(50, 100));
//		//lbPanel.setBackground(Color.LIGHT_GRAY);
//		Font font = new Font("고딕", Font.PLAIN, 35);
//		JLabel rslabel = new JLabel("");
//		rslabel.setFont(font);
//		// rslabel =new JLabel("잠좀깨라");
//		lbPanel.add(rslabel);
		add(lbPanel, "North");

		
		JPanel jp1 = new JPanel(new BorderLayout());
		//jp1.setBorder(border);
		jp1.add(new JScrollPane(table1));
		add(jp1, "Center");

		delBtn = new JButton("예약 취소");
		exitBtn = new JButton("나가기");

		JPanel rvPanel = new JPanel(new FlowLayout());
		rvPanel.setPreferredSize(new Dimension(50, 40));
		rvPanel.setBackground(Color.white);
		// rvPanel.add(rslabel);
		rvPanel.add(delBtn);
		rvPanel.add(exitBtn);

		add(rvPanel, "South");

		delBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		table1.addMouseListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == exitBtn) {
			dispose();
		} else if (obj == delBtn) {
			String message0 = "정말 취소하시겠습니까?";
			int ans0 = JOptionPane.showConfirmDialog(this, message0, "Cancel", JOptionPane.YES_OPTION);
			if (ans0 == JOptionPane.YES_OPTION) {
				if (dao.reservDelete(reserv_num, gid) > 0) {
					JOptionPane.showMessageDialog(this, "예약 취소가 완료되었습니다.");
					List<UserReservDTO> list = dao.reservselectAll(loginID);
					setTableData(list);
				}else
					JOptionPane.showMessageDialog(this, "이미 예약취소된 상태이거나 다른 이유로\n예약 취소가 실패했습니다.");
			}
		}
	}


	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table1) {
			// 마우스로 클릭한 테이블의 행 인덱스 얻기
			selectedRow = table1.getSelectedRow();
			gidS = table1.getValueAt(selectedRow, 1).toString();
			gid = Integer.parseInt(gidS);
			reserv_numS = table1.getValueAt(selectedRow, 0).toString();
			reserv_num = Integer.parseInt(reserv_numS);
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