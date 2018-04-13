
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class IndexDAO {

	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;
	public IndexDAO() {}

	/*
	 * 
	 * public int userListInsert(UserJDialogGUI user){ int result = 0;
	 * 
	 * try{ ps =
	 * con.prepareStatement("insert into TB_USERLIST values(?, ?, ?, ?)");
	 * ps.setString(1, user.id.getText()); ps.setString(2, user.name.getText());
	 * ps.setInt(3, Integer.parseInt(user.age.getText())); ps.setString(4,
	 * user.addr.getText());
	 * 
	 * result = ps.executeUpdate(); } catch(SQLException e){
	 * System.out.println(e + "=> userListInsert fail"); } finally{ dbClose(); }
	 * return result; }
	 * 
	 * 
	 */
	public String getName(String id) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		String name = null;
		try {
			String sql = "select name from member_tb where id= ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				name = rs.getString(1);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> 전체 레코드 반환시 오류");
		} finally {

		}
		return name;
	}
	
	public List<UserGoodsDTO> goodsSelectAll() {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<UserGoodsDTO> list = null;
		try {
			list = new Vector<UserGoodsDTO>();
			String sql = "select * from goods_tb";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserGoodsDTO dto = new UserGoodsDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(7), rs.getInt(8), rs.getInt(9));
				dto.setOutdate(rs.getDate(5));
				dto.setIndate(rs.getDate(6));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> 전체 레코드 반환시 오류");
		} finally {

		}
		return list;
	}

	/*
	 * 
	 * public int userUpdate(UserJDialogGUI user){ int result = 0; String sql =
	 * "update TB_USERLIST set name = ?, age = ?, addr = ? where id = ?";
	 * 
	 * try{ ps = con.prepareStatement(sql); ps.setString(1,
	 * user.name.getText()); ps.setString(2, user.age.getText());
	 * ps.setString(3, user.addr.getText()); ps.setString(4,
	 * user.id.getText().trim());
	 * 
	 * result = ps.executeUpdate(); } catch(SQLException e){
	 * System.out.println(e + "=> userUpdate fail"); } finally{ dbClose(); }
	 * return result; }
	 * 
	 */

	// Ex06참고
	public List<UserGoodsDTO> goodsSearch1(String fieldName, String word) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<UserGoodsDTO> list = null;
		String sql = "select gid, packname, out_p, in_p, term,  price, ea,outdate,indate from goods_tb where " + fieldName.trim() + " like '%"
				+ word.trim() + "%'";
		try {
			list = new Vector<UserGoodsDTO>();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserGoodsDTO dto = new UserGoodsDTO(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7));
				dto.setOutdate(rs.getDate(8));
				dto.setIndate(rs.getDate(9));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> getgoodsSearch fail");
		} finally {
			dbs.dbClose();
		}
		return list;
	}
	public List<UserGoodsDTO> goodsSearch2(String fieldName, String word) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<UserGoodsDTO> list = null;
		String sql = "select gid, packname, out_p, in_p, term,  price, ea,outdate,indate from goods_tb where " + fieldName.trim() + " =  '"
				+ word.trim() + "'";
		try {
			list = new Vector<UserGoodsDTO>();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserGoodsDTO dto = new UserGoodsDTO(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7));
				dto.setOutdate(rs.getDate(8));
				dto.setIndate(rs.getDate(9));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> getgoodsSearch fail");
		} finally {
			dbs.dbClose();
		}
		return list;
	}


}
