import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

public class Master extends JFrame implements ActionListener{
    
	JMenu m;
	JMenuItem mlogout,mexit;       
	JMenuBar mb;  
	IndexMain custo_logout;
	Master(){            
		//JFrame frame = new JFrame();
		m = new JMenu("관리");   
		mlogout = new JMenuItem("로그아웃");
		mexit = new JMenuItem("나가기");
		mb = new JMenuBar();    
		         
		mlogout.addActionListener(this);
		mexit.addActionListener(this);
		   
		m.add(mlogout);
		m.add(mexit);
		mb.add(m);
		setJMenuBar(mb);            
		
		GoodsGUI goods = new GoodsGUI();  
		ReservationGUI re = new ReservationGUI();
		ReservGUI re2 = new ReservGUI();
		MemberGUI mem= new MemberGUI();
		Member2GUI mem2= new Member2GUI();
		
		JTabbedPane tab = new JTabbedPane();

		tab.addTab("상품관리",goods);
		tab.addTab("회원관리",mem);
		tab.addTab("탈퇴회원관리",mem2);
		tab.addTab("예약완료관리",re);
		tab.addTab("예약취소관리",re2);
		   
		(getContentPane()).add(tab);
		pack();
		
		// JFrame 스크린 중앙에 실행
		setBounds(200, 200, 1500, 700);
		Dimension frameSize =getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
		setVisible(true);
		setResizable(false);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){    
				System.exit(0);
			}     
		});  
	}      
	@Override   
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == mexit) {
				System.exit(0);
		}else if(obj == mlogout) {
			dispose();
			custo_logout = new IndexMain();
			custo_logout.setVisible(true);
		}
	}
	public static void main(String[] args)
	{
		Master m = new Master();
	}
}
