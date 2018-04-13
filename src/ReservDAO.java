import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

public class ReservDAO {

	Connection con = null;
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	ReservDAO() {
	}

	// 어떻게 보여주는지 메인 서비스에서 한는 행동을 보아라!
	public List<ReservDTO> reservAll() {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<ReservDTO> list = null;
		try {
			list = new Vector<ReservDTO>();
			String sql = "select reserv_num,gid,packname,ea,id,reservdate,cancel from reserv_tb where cancel = '예약취소' order by cancel desc, reservdate desc";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ReservDTO dto = new ReservDTO(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				dto.setReserv_num(rs.getInt(1));
				dto.setReservdate(rs.getDate(6));
				dto.setCancel(rs.getString(7));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> 전체 레코드 반환시 오류");
		} finally {
			dbs.dbClose();
		}

		return list;
	}

	public int reservInsert(int reserv_num, int gid) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		int affectedRow = 0;
		String sql = "update reserv_tb set cancel = '예약완료' where reserv_num = ? and cancel = '예약취소' ";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, reserv_num);
			// ps.setString(1, id);
			// ps.setInt(2, gid);
			affectedRow = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=> 재예약시 오류");
		} finally {
			dbs.dbClose();
		}
		if (affectedRow > 0) {
			sql = "update goods_tb set ea = ea-1 where gid=?";
			try {
				ps = con.prepareStatement(sql);
				// ps.setInt(1, affectedRow);
				ps.setInt(1, gid);
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e + "=> 재예약 후 goods_tb 수정시 오류");
			} finally {
				dbs.dbClose();
			}
		}

		return affectedRow;
	}

	public int reservDelete(int resev_num, int gid) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		int affectedRow = 0;
		String sql = "delete reserv_tb where reserv_num = ? and cancel = '예약취소' ";
		try {
			ps = con.prepareStatement(sql);
			// ps.setString(1, id);
			ps.setInt(1, resev_num);
			affectedRow += ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=> 영구삭제 시 오류");
		} finally {
			dbs.dbClose();
		}

		return affectedRow;
	}

	public List<ReservDTO> reservUpdate() {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<ReservDTO> list = null;
		List<ReservDTO> list2 = null;
		List<ReservDTO> list3 = null;
		try {
			list = new Vector<ReservDTO>();
			String sql = "select gid from reserv_tb order by reservdate desc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ReservDTO dto = new ReservDTO();
				dto.setGid(rs.getInt(1));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> reserv_tb에 있는 모든 gid 레코드 반환시 오류");
		} finally {
			dbs.dbClose();
		}

		try {
			list2 = new Vector<ReservDTO>();
			for (ReservDTO d : list) {
				String sql = "select gid,packname,out_p,in_p,outdate,indate,term,price,ea from goods_tb where gid = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, d.getGid());
				rs = ps.executeQuery();
				while (rs.next()) {
					ReservDTO dto = new ReservDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getDate(5), rs.getDate(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
					list2.add(dto);
				}
			}
		} catch (SQLException e) {
			System.out.println(e + "=> reserv_tb에 있는 goods_tb 전체 레코드 반환시 오류");
		} finally {
			dbs.dbClose();
		}
		if (list2 != null) {
			try {
				for (ReservDTO d : list2) {
					String sql = "update reserv_tb set packname = ?,ea= ? where gid = ?";
					ps = con.prepareStatement(sql);
					ps.setString(1, d.getPackname());
					ps.setInt(2, d.getEa());
					ps.setInt(3, d.getGid());
					int result = ps.executeUpdate();
				}
			} catch (SQLException e) {
				System.out.println(e + "=> 수정시 오류");
			} finally {
				dbs.dbClose();
			}
		}

		// 수정 후 수정된 화면 보여주기

		try {
			list3 = new Vector<ReservDTO>();
			String sql = "select reserv_num,gid,packname,ea,id,reservdate,cancel from reserv_tb where cancel = '예약취소'  order by cancel, reservdate desc";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ReservDTO dto = new ReservDTO(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				dto.setReserv_num(rs.getInt(1));
				dto.setReservdate(rs.getDate(6));
				dto.setCancel(rs.getString(7));
				list3.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> 전체 레코드 반환시 오류");
		} finally {
			dbs.dbClose();
		}

		return list3;
	}

	// Ex06참고
	public List<ReservDTO> reservSearch(String fieldName, String word) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<ReservDTO> list = null;
		String sql = "select reserv_num,gid,packname,ea,id,reservdate,cancel from reserv_tb " + "where "
				+ fieldName.trim() + " like '%" + word.trim() + "%' order by reservdate desc";
		try {
			list = new Vector<ReservDTO>();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ReservDTO dto = new ReservDTO(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				dto.setReserv_num(rs.getInt(1));
				dto.setReservdate(rs.getDate(6));
				dto.setCancel(rs.getString(7));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> getUserSearch fail");
		} finally {
			dbs.dbClose();
		}

		return list;
	}

	public List<ReservDTO> reservSearch2(String fieldName, String word) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<ReservDTO> list = null;
		String sql = "select reserv_num,gid,packname,ea,id,reservdate,cancel from reserv_tb " + "where "
				+ fieldName.trim() + " = " + word + " order by reservdate desc";
		try {
			list = new Vector<ReservDTO>();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ReservDTO dto = new ReservDTO(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				dto.setReserv_num(rs.getInt(1));
				dto.setReservdate(rs.getDate(6));
				dto.setCancel(rs.getString(7));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> getUserSearch fail");
		} finally {
			dbs.dbClose();
		}

		return list;
	}
	public List<ReservDTO> reservSearch3(String word, String word2) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<ReservDTO> list = null;
		String sql = "select reserv_num,gid,packname,ea,id,reservdate,cancel from reserv_tb "
				+ "where cancel = '예약취소' and reservdate>= '" + word.trim() + "' and reservdate < '" + word2.trim()
				+ "' order by reservdate desc";
		try {
			list = new Vector<ReservDTO>();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ReservDTO dto = new ReservDTO(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				dto.setReserv_num(rs.getInt(1));
				dto.setReservdate(rs.getDate(6));
				dto.setCancel(rs.getString(7));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> getUserSearch fail");
		} finally {
			dbs.dbClose();
		}

		return list;
	}
}
