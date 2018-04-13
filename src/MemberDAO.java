
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class MemberDAO {
	Connection con= null;
	Statement st = null;
	PreparedStatement ps= null;
	ResultSet rs= null;

	MemberDAO(){
	}
	
	public List<MemberDTO> getRecordAll() {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<MemberDTO> list = null;
		
		try {
			list = new Vector<MemberDTO>();
			st = con.createStatement();
			String sql = "select * from member_tb where leave='가입' order by master, regdate desc";			
			rs = st.executeQuery(sql);
			
//			rs = st.executeQuery("select * from tb_userlist order by id");
//			// DefaultTableModel에 있는 기존 데이터 지우기
//			for (int i = 0; i < MemberTable.getRowCount();) {
//				t_model.removeRow(0);	
			
		
			while (rs.next()) {							
				String id = rs.getString("id");
				String  pwd1= rs.getString("pwd1");
				String  pwd2= rs.getString("pwd2");
				String  name= rs.getString("name");
				String  email= rs.getString("email");
				String hp = rs.getString("hp");
				String addr = rs.getString("addr");
				
				char gender= rs.getString("gender").charAt(0);
				char master= rs.getString("master").charAt(0);
				java.sql.Date birth = rs.getDate("birth");
				java.sql.Date regdate = rs.getDate("regdate");

								
				MemberDTO dto = new MemberDTO();
				
				dto.setId(id);
				dto.setPwd1(pwd1);
				dto.setPwd2(pwd2);
				dto.setName(name);
				dto.setEmail(email);
				dto.setHp(hp);
				dto.setAddr(addr);
				dto.setGender(gender);          
				dto.setMaster(master);			
				dto.setBirth(birth);
				dto.setRegdate(regdate);
								
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "전체 레코드 반환시 오류");
		} finally { dbs.dbClose();} 
		return list;
	}	
	

	public int insert(String id,String pwd1,String pwd2,String name,
			String email,String hp,String addr,String gender,String master,
			String birth) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		int rs = 0;
		String sql = "insert into member_tb "
				+ "values(?,?,?,?,?,?,?,?,?,?,sysdate,'가입')";
		
		try {
			ps = con.prepareStatement(sql);
			 
			ps.setString(1,id);
 			ps.setString(2,pwd1);
 			ps.setString(3,pwd2);
 			ps.setString(4,name);
 			ps.setString(5,birth);
 			ps.setString(6,gender);
			ps.setString(7, email);
 			ps.setString(8,hp);
 			ps.setString(9, master);
 			ps.setString(10,addr);
 			//11번 가입날짜는 현재날짜로 입력
 			
			rs = ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e + "=> 입력시 오류");
		} finally { dbs.dbClose();} 
		return rs;
	}
	
	public int update(String pwd1,String pwd2,String name,
			String email,String hp,String addr,String gender,String master,
			String birth,String id) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		int rs = 0;
		String sql = "UPDATE member_tb SET pwd1=?,"
		+ "pwd2=?,name=?,birth=?,gender=?,email=?,"
		+ "hp=?,master=?,addr=?" 
		+ " WHERE id=?";
		
		try {
			ps = con.prepareStatement(sql);		 
			
 			ps.setString(1,pwd1);
 			ps.setString(2,pwd2);
 			ps.setString(3,name);
 			ps.setString(4,birth);
 			ps.setString(5,gender);
			ps.setString(6, email);
 			ps.setString(7,hp);
 			ps.setString(8, master);
 			ps.setString(9,addr);
 			ps.setString(10,id);
 			
			rs = ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e + "=> 수정시 오류");
		} finally { dbs.dbClose();} 
		return rs;
	}
	
	public int delete(String id) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		String sql = "select * from reserv_tb WHERE id=? and cancel='예약완료'";
		int re=-1;
		boolean a =true;
		try {
			ps = con.prepareStatement(sql);	 			
			ps.setString(1, id);			
			rs = ps.executeQuery();			
			while (rs.next()) {	
				a=false;
			}
		} catch (SQLException e) {
			System.out.println(e + "=> 삭제시 오류");
		}
		if (a) {
			sql = "update member_tb set leave='탈퇴'  WHERE id=?";

			try {
				ps = con.prepareStatement(sql);
				ps.setString(1, id);
				re = ps.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e + "=> 삭제시 오류");
			}
			dbs.dbClose();
		}
		return re;
	}
	
	public List<MemberDTO> search(String fieldName, String word) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<MemberDTO> list = null;
		String sql = "select * from member_tb where " + fieldName.trim() + " like '%" + word.trim() + "%'";
		try {
			list = new Vector<MemberDTO>();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String  pwd1= rs.getString("pwd1");
				String  pwd2= rs.getString("pwd2");
				String  name= rs.getString("name");
				String  email= rs.getString("email");
				String hp = rs.getString("hp");
				String addr = rs.getString("addr");
				
				char gender= rs.getString("gender").charAt(0);
				char master= rs.getString("master").charAt(0);
				java.sql.Date birth = rs.getDate("birth");
				java.sql.Date regdate = rs.getDate("regdate");
								
				MemberDTO dto = new MemberDTO();
				
				dto.setId(id);
				dto.setPwd1(pwd1);
				dto.setPwd2(pwd2);
				dto.setName(name);
				dto.setEmail(email);
				dto.setHp(hp);
				dto.setAddr(addr);
				dto.setGender(gender);          
				dto.setMaster(master);			
				dto.setBirth(birth);
				dto.setRegdate(regdate);
								
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> getUserSearch fail");
		} finally {
			dbs.dbClose();
		}
		return list;
	}
		
	public List<MemberDTO> search2(String word,String word2) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<MemberDTO> list = null;
		String sql = "select * from member_tb where regdate >= '" + word.trim() + "' and regdate < '" + word2.trim() + "'";
		try {
			list = new Vector<MemberDTO>();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String  pwd1= rs.getString("pwd1");
				String  pwd2= rs.getString("pwd2");
				String  name= rs.getString("name");
				String  email= rs.getString("email");
				String hp = rs.getString("hp");
				String addr = rs.getString("addr");
				
				char gender= rs.getString("gender").charAt(0);
				char master= rs.getString("master").charAt(0);
				java.sql.Date birth = rs.getDate("birth");
				java.sql.Date regdate = rs.getDate("regdate");
								
				MemberDTO dto = new MemberDTO();
				
				dto.setId(id);
				dto.setPwd1(pwd1);
				dto.setPwd2(pwd2);
				dto.setName(name);
				dto.setEmail(email);
				dto.setHp(hp);
				dto.setAddr(addr);
				dto.setGender(gender);          
				dto.setMaster(master);			
				dto.setBirth(birth);
				dto.setRegdate(regdate);
								
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> getUserSearch fail");
		} finally {
			dbs.dbClose();
		}
		return list;
	}
	
	public boolean IdCheck(String id) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		  boolean result = true;
		  try {
		   ps = con.prepareStatement("select * from member_tb where id = ?");
		   ps.setString(1, id.trim());
		   rs = ps.executeQuery();
		   if (rs.next()) {
		    result = false;
		   }
		  } catch (SQLException e) {
		   System.out.println(e + "idcheck 실패");
		  } finally {
		   dbs.dbClose();
		  }
		  return result;
		 } 
	
	
}