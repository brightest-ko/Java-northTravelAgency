import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

public class UserReservDAO {
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;

	public UserReservDAO() {
	}

	public List<UserReservDTO> reservselectAll(String id) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<UserReservDTO> list = null;
		try {
			list = new Vector<UserReservDTO>();
			st = con.createStatement();
			rs = st.executeQuery(
					"select reserv_num,gid,packname, out_p, in_p, outdate, indate, term, price, reservdate,cancel from reserv_tb where id='"
							+ id + "' order by cancel, reservdate desc");

			while (rs.next()) {
				UserReservDTO dto = new UserReservDTO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getDate(6), rs.getDate(7), rs.getInt(8), rs.getInt(9), rs.getDate(10),
						rs.getString(11));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "= > userSelectAll fail");
		} finally {
			dbs.dbClose();
		}
		return list;
	}

	public int reservInsert(String id, int gid) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		int affectedRow = 0;
		String packname = null, out_p = null, in_p = null;
		Date indate = null;
		Date outdate = null;
		int ea = -1, term = -1, price = -1;
		String sql = "select packname, out_p,in_p,outdate,indate,term,price,ea from goods_tb where gid = ?";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, gid);
			rs = ps.executeQuery();
			while (rs.next()) {
				packname = rs.getString(1);
				out_p = rs.getString(2);
				in_p = rs.getString(3);
				outdate = rs.getDate(4);
				indate = rs.getDate(5);
				term = rs.getInt(6);
				price = rs.getInt(7);
				ea = rs.getInt(8);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> 상품검색 오류");
		}
		if (packname != null && out_p != null && in_p != null && outdate != null && ea > 0 && term != -1
				&& price != -1) {
			sql = "insert into reserv_tb(reserv_num,gid,packname,out_p,in_p,outdate,indate,term,price,ea,reservdate,id,cancel) values(reserv_seq.nextval,?,?,?,?,?,?,?,?,?,sysdate,?,'예약완료')";
			try {
				ps = con.prepareStatement(sql);
				ps.setInt(1, gid);
				ps.setString(2, packname);
				ps.setString(3, out_p);
				ps.setString(4, in_p);
				ps.setDate(5, outdate);
				ps.setDate(6, indate);
				ps.setInt(7, term);
				ps.setInt(8, price);
				ps.setInt(9, ea);
				ps.setString(10, id);
				affectedRow = ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e + "=> 예약(추가)시 오류");
			}
			if(affectedRow>0){
			sql = "update goods_tb set ea = ea-1 where gid=?";
			try {
				ps = con.prepareStatement(sql);
				ps.setInt(1, gid);
				affectedRow += ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e + "=> 예약(추가) 후 goods_tb 수정시 오류");
			} finally {
				dbs.dbClose();
			}}
		}
		return affectedRow;
	}

	public int reservDelete(int reserv_num, int gid) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		int affectedRow = 0;
		String sql = "update reserv_tb set cancel = '예약취소' where reserv_num = ? and cancel = '예약완료' ";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, reserv_num);
			affectedRow = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=> 예약취소(삭제)시 오류");
		}
		if (affectedRow > 0) {
			sql = "update goods_tb set ea = ea+1 where gid=?";
			try {
				ps = con.prepareStatement(sql);
				// ps.setInt(1, affectedRow);
				ps.setInt(1, gid);
				affectedRow += ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e + "=> 예약취소(삭제) 후 goods_tb 수정시 오류");
			} finally {
				dbs.dbClose();
			}
		}
		return affectedRow;
	}
}
