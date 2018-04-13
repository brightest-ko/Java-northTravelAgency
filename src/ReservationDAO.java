import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

public class ReservationDAO {

	Connection con = null;
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	ReservationDAO() {}

	// 어떻게 보여주는지 메인 서비스에서 한는 행동을 보아라!
	public List<ReservationDTO> reservAll() {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<ReservationDTO> list = null;
		try {
			list = new Vector<ReservationDTO>();
			String sql = "select reserv_num,gid,packname,ea,id,reservdate from reserv_tb where cancel = '예약완료' order by reservdate desc";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ReservationDTO dto = new ReservationDTO(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				dto.setReserv_num(rs.getInt(1));
				dto.setReservdate(rs.getDate(6));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> 전체 레코드 반환시 오류");
		} finally {
			dbs.dbClose();
		}

		dbs.conClose();
		return list;
	}

	
	public int reservInsert(String id, int gid) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		int affectedRow = 0;
		String packname=null,out_p=null,in_p=null;
		Date indate = null;
		Date outdate = null;
		int ea=-1,term=-1,price = -1;
		String sql = "select packname, out_p,in_p,outdate,indate,term,price,ea from goods_tb where gid = ?";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, gid);
			rs = ps.executeQuery();
			while (rs.next()) {
				packname = rs.getString(1);
				out_p= rs.getString(2);
				in_p= rs.getString(3);
				outdate= rs.getDate(4);
				indate= rs.getDate(5);
				term= rs.getInt(6);
				price= rs.getInt(7);
				ea = rs.getInt(8);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> 상품검색 오류");
		} finally {
			dbs.dbClose();
		}
		if (packname!=null && out_p!=null && in_p!=null && outdate != null &&
		ea>0 && term!=-1 && price != -1) {
			sql = "insert into reserv_tb(reserv_num,gid,packname,out_p,in_p,outdate,indate,term,price,ea,reservdate,id,cancel) "
					+ "values(reserv_seq.nextval,?,?,?,?,?,?,?,?,?,sysdate,?,'예약완료')";
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
				affectedRow += ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e + "=> 예약(추가)시 오류");
			} finally {
				dbs.dbClose();
			}
			sql = "update goods_tb set ea = ea-1 where gid=?";
			try {
				ps = con.prepareStatement(sql);
				ps.setInt(1, gid);
				affectedRow += ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e + "=> 예약(추가) 후 goods_tb 수정시 오류");
			} finally {
				dbs.dbClose();
			}
		}

		dbs.conClose();
		return affectedRow;
	}

	/*public int reservDelete(String id, int gid) {
		int affectedRow = 0;
		String sql = "delete reserv_tb where id = ? and gid = ? ";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setInt(2, gid);
			affectedRow += ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=> 예약취소(삭제)시 오류");
		}
		sql = "update goods_tb set ea = ea+? where gid=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, affectedRow);
			ps.setInt(2, gid);
			affectedRow += ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=> 예약취소(삭제) 후 goods_tb 수정시 오류");
		} finally {
			dbs.dbClose();
		}
		return affectedRow;
	}*/
	
	public int reservDelete(int reserv_num,int gid) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		int affectedRow = 0;
		String sql = "update reserv_tb set cancel = '예약취소' where reserv_num = ?";
				//+ "id = ? and gid = ? ";
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, reserv_num);
			//ps.setString(1, id);
			//ps.setInt(2, gid);
			affectedRow += ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=> 예약취소(삭제)시 오류");
		} finally {
			dbs.dbClose();
		}
		sql = "update goods_tb set ea = ea+1 where gid=?";
		try {
			ps = con.prepareStatement(sql);
			//ps.setInt(1, affectedRow);
			ps.setInt(1, gid);
			affectedRow += ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=> 예약취소(삭제) 후 goods_tb 수정시 오류");
		} finally {
			dbs.dbClose();
		}

		dbs.conClose();
		return affectedRow;
	}

	public List<ReservationDTO> reservUpdate() {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<ReservationDTO> list = null;
		List<ReservationDTO> list2 = null;
		List<ReservationDTO> list3 = null;
		try {
			list = new Vector<ReservationDTO>();
			String sql = "select gid from reserv_tb order by reservdate desc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ReservationDTO dto = new ReservationDTO();
				dto.setGid(rs.getInt(1));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> reserv_tb에 있는 모든 gid 레코드 반환시 오류");
		} finally {
			dbs.dbClose();
		}

		try {
			list2 = new Vector<ReservationDTO>();
			for (ReservationDTO d : list) {
				String sql = "select gid,packname,out_p,in_p,outdate,indate,term,price,ea from goods_tb where gid = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, d.getGid());
				rs = ps.executeQuery();
				while (rs.next()) {
					ReservationDTO dto = new ReservationDTO(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5),rs.getDate(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
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
				for (ReservationDTO d : list2) {
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
			list3 = new Vector<ReservationDTO>();
			String sql = "select reserv_num,gid,packname,ea,id,reservdate from reserv_tb where cancel = '예약완료' order by reservdate desc";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ReservationDTO dto = new ReservationDTO(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				dto.setReserv_num(rs.getInt(1));
				dto.setReservdate(rs.getDate(6));
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
	public List<ReservationDTO> reservSearch(String fieldName, String word) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<ReservationDTO> list = null;
		String sql ="select reserv_num,gid,packname,ea,id,reservdate from reserv_tb "
				+ "where cancel = '예약완료' and " + fieldName.trim() + " like '%" + word.trim() + "%' order by reservdate desc";
		try {
			list = new Vector<ReservationDTO>();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ReservationDTO dto = new ReservationDTO(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				dto.setReserv_num(rs.getInt(1));
				dto.setReservdate(rs.getDate(6));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> getUserSearch fail");
		} finally {
			dbs.dbClose();
		}

		return list;
	}
	public List<ReservationDTO> reservSearch2(String fieldName, String word) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<ReservationDTO> list = null;
		String sql ="select reserv_num,gid,packname,ea,id,reservdate from reserv_tb "
				+ "where cancel = '예약완료' and " + fieldName.trim() + " = " + word+ " order by reservdate desc";
		try {
			list = new Vector<ReservationDTO>();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ReservationDTO dto = new ReservationDTO(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				dto.setReserv_num(rs.getInt(1));
				dto.setReservdate(rs.getDate(6));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> getUserSearch fail");
		} finally {
			dbs.dbClose();
		}

		return list;
	}
	
	public List<ReservationDTO> reservSearch3(String word, String word2) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		ps = dbs.ps;
		rs = dbs.rs;
		List<ReservationDTO> list = null;
		String sql ="select reserv_num,gid,packname,ea,id,reservdate from reserv_tb "
				+ "where cancel = '예약완료' and reservdate>= '" + word.trim() + "' and reservdate < '" + word2.trim() + "' order by reservdate desc";
		try {
			list = new Vector<ReservationDTO>();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ReservationDTO dto = new ReservationDTO(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				dto.setReserv_num(rs.getInt(1));
				dto.setReservdate(rs.getDate(6));
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
