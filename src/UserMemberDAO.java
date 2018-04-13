import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

public class UserMemberDAO {
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;

	public UserMemberDAO() {}
	
	public UserMemberDTO my(String id) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		UserMemberDTO dto = new UserMemberDTO();
		try {
			String sql = "select id, pwd1, pwd2, name, birth, hp, email, addr, gender from member_tb where id= ? ";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				dto = new UserMemberDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getString(7),rs.getString(8));
				dto.setGender(rs.getString(9).charAt(0));
			}
		} catch (SQLException e) {
			System.out.println(e + "=> 전체 레코드 반환시 오류");
		} finally {
			dbs.dbClose();
		}
		return dto;
	}

	public int memberInsert(String id,String pwd1, String pwd2, String name, String birth, 
			String hp,String email, String addr, String gender ) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		int result = 0;		
		String sql = "insert into  member_tb(id, pwd1, pwd2, name, birth, hp, gender, email ,addr,master,regdate,leave)"
				+" values(?, ?, ?, ?, ?, ?, ?,?,?,'u',sysdate,'가입')";	
		
		if (id==null ||pwd1==null ||pwd2==null ||name==null ||birth==null ||hp==null ||email==null ||addr==null ||gender==null )
		{			
		    JOptionPane.showMessageDialog( null,"기재사항을 다시 확인해주세요.");
		    return result;
		   }
		else if (!pwd1.trim().equals(pwd2.trim())) {
		    JOptionPane.showMessageDialog(null, "PassWord를 다시 확인!");
		    return result;
		   } 
		else{		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pwd1);
			ps.setString(3, pwd2);
			ps.setString(4, name);
			ps.setString(5, birth);
			ps.setString(6, hp);
			ps.setString(7, gender);
			ps.setString(8, email);
			ps.setString(9, addr);
			

			
			result += ps.executeUpdate();

		
		} catch (SQLException e) {
			System.out.println(e + "=>SQL 오류");
		} finally {
			dbs.dbClose();
		}
		   }
		return result;
	}

	// 아이디 중복확인
	public boolean IdCheck(String id) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		boolean result = true;
		try {
			ps = con.prepareStatement("select * from member_tb where id = ? ");
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
	
	public int memberUpdate(String pwd1, String pwd2,String birth, 
			String hp,String email, String addr,String id) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		int result = 0;		
		String sql = "update  member_tb set pwd1 = ?, pwd2 = ?, birth=?, hp=?, email=? ,addr=?"
				+"where id= ?";	
		
		if (id==null ||pwd1==null ||pwd2==null ||birth==null ||hp==null ||email==null ||addr==null)
		{			
		    JOptionPane.showMessageDialog( null,"기재사항을 다시 확인해주세요.");
		    return result;
		   }
		else if (!pwd1.trim().equals(pwd2.trim())) {
		    JOptionPane.showMessageDialog(null, "PassWord를 다시 확인!");
		    return result;
		   } 
		else{		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, pwd1);
			ps.setString(2, pwd2);
			ps.setString(3, birth);
			ps.setString(4, hp);
			ps.setString(5, email);
			ps.setString(6, addr);
			ps.setString(7, id);			
			result += ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=>SQL 오류");
		} finally {
			dbs.dbClose();
		}
		   }
		return result;
	}
	
	 public int delete(String id) {
			DBSington dbs = new DBSington();
			con = dbs.con;
			st = dbs.st;
			ps = dbs.ps;
			rs = dbs.rs;
		  int rs = 0;
		  String sql = "update member_tb set leave = '탈퇴' where id = ?";
		  
		  try {
		   ps = con.prepareStatement(sql);
		   ps.setString(1,id);
		   rs = ps.executeUpdate();
		   
		  } catch (SQLException e) {
		   System.out.println(e + "=> 입력시 오류");
		  } finally { dbs.dbClose();} 
		  return rs;
		 } 
	 /*
	 public boolean memberDelete(String id, String pwd1){
	       
	        boolean del =false ;
	        Connection con =null;
	        PreparedStatement ps =null;
	       
	        try {
	           
	            String sql = "delete from tb_member where id=? and pwd=?";
	           
	            ps = con.prepareStatement(sql);
	            ps.setString(1, id);
	            ps.setString(2, pwd1);
	            int r = ps.executeUpdate(); 
	           
	            if (r>0) del=true; 
	           
	        } catch (Exception e) {
	            System.out.println(e + "-> 오류발생");
	        }      
	        return del;
	    }*/


}
