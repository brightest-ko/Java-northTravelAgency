
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

public class Member2DAO {
	Connection con= null;
	Statement st = null;
	PreparedStatement ps= null;
	ResultSet rs= null;
	DBSington dbs;
	
	Member2DAO(){}
	
	public List<Member2DTO> getRecordAll() {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<Member2DTO> list = null;
		
		try {
			list = new Vector<Member2DTO>();
			st = con.createStatement();
			String sql = "select * from member_tb where leave = '탈퇴' order by leave desc,master,regdate desc ";			
			rs = st.executeQuery(sql);
			
//			rs = st.executeQuery("select * from tb_userlist order by id");
//			// DefaultTableModel에 있는 기존 데이터 지우기
//			for (int i = 0; i < Member2Table.getRowCount();) {
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
				String leave= rs.getString("leave");

								
				Member2DTO dto = new Member2DTO();
				
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
				dto.setLeave(leave);
								
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "전체 레코드 반환시 오류");
		} finally { dbs.dbClose();} 
		return list;
	}	
	

	public int insert(String id) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		int rs = 0;
		String sql = "update member_tb set leave = '가입' where id = ? and leave = '탈퇴' ";
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1,id);
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
		
		int affectedRow = 0;
		String sql = "delete reserv_tb where id = ? and cancel = '예약취소' ";
		try {
			ps = con.prepareStatement(sql);
			//ps.setString(1, id);
			ps.setString(1, id);
			affectedRow = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=>예약 영구삭제 시 오류");
		}

		sql = "DELETE member_tb WHERE id=? and leave = '탈퇴'";
		try {
			ps = con.prepareStatement(sql);	 			
			ps.setString(1, id);		
			affectedRow = ps.executeUpdate();				
		} catch (SQLException e) {
			System.out.println(e + "=>예약 삭제시 오류");
		}
		dbs.dbClose();
		return affectedRow;
	}
	
	public List<Member2DTO> search(String fieldName, String word) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<Member2DTO> list = null;
		String sql = "select * from member_tb where leave = '탈퇴' and " + fieldName.trim() + " like '%" + word.trim() + "%'";
		try {
			list = new Vector<Member2DTO>();
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

								
				Member2DTO dto = new Member2DTO();
				
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
		
	public List<Member2DTO> search2(String word,String word2) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<Member2DTO> list = null;
		String sql = "select * from member_tb where leave = '탈퇴' and regdate >= '" + word.trim() + "' and regdate < '" + word2.trim() + "'";
		try {
			list = new Vector<Member2DTO>();
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
								
				Member2DTO dto = new Member2DTO();
				
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