import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

public class GoodsDAO {
	Connection con = null;
	Statement st = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	GoodsDTO dto;

	GoodsDAO() {
	}

	public List<GoodsDTO> getRecordAll() { // 전체 뜨는거
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		psmt = dbs.ps;
		rs = dbs.rs;

		List<GoodsDTO> list = null;
		try {
			list = new Vector<GoodsDTO>();

			String sql = "SELECT * FROM goods_tb"

					+ " ORDER BY gid DESC";

			psmt = con.prepareStatement(sql);

			rs = psmt.executeQuery();

			while (rs.next()) {

				// int gid, String packname, String out_p, String in_p,
				// java.sql.Date outdate, java.sql.Date indate, int term, int
				// price, int ea

				dto = new GoodsDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(7),
						rs.getInt(8), rs.getInt(9));

				dto.setOutdate(rs.getDate("outdate"));
				dto.setIndate(rs.getDate("indate"));

				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("전체 레코드 반환시 오류:" + e.getMessage());
		} finally {
			dbs.dbClose();

		}
		return list;

	}

	public List<GoodsDTO> getRecordAll2() { // 전체 뜨는거
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		psmt = dbs.ps;
		rs = dbs.rs;

		List<GoodsDTO> list = null;
		try {
			list = new Vector<GoodsDTO>();

			String sql = "select indate - outdate+1 from goods_tb where term = null ";

			psmt = con.prepareStatement(sql);

			rs = psmt.executeQuery();

			while (rs.next()) {
				dto = new GoodsDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(7),
						rs.getInt(8), rs.getInt(9));

				dto.setOutdate(rs.getDate("outdate"));
				dto.setIndate(rs.getDate("indate"));

				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("전체 레코드 반환시 오류:" + e.getMessage());
		} finally {
			dbs.dbClose();

		}
		return list;
	}



	
	  // 인트값 예외 처리 용인듯
	  
	public int Insert(String packname, String out_p, String in_p, String outdate, String indate, int term, int price,
			int ea) {
		
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		psmt = dbs.ps;
		rs = dbs.rs;

		int affectedRow = 0;
		String sql = "INSERT INTO goods_tb(gid, packname, out_p, in_p, outdate,indate, term, price, ea)"

				+ " VALUES(goods_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";

		// Statement st = null;
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, packname);
			psmt.setString(2, out_p);
			psmt.setString(3, in_p);
			psmt.setString(4, outdate);
			psmt.setString(5, indate);
			psmt.setInt(6, term);
			psmt.setInt(7, price);
			psmt.setInt(8, ea);
			
			
			affectedRow += psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=>SQL 오류");
		} finally {
			dbs.dbClose();
		}
		return affectedRow;
	}
	 

	// 수정(업데이트) 처리
	public int update(String packname, String out_p, String in_p, String outdate, String indate, int term, int price,
			int ea, int gid) {		
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		psmt = dbs.ps;
		rs = dbs.rs;		
		int affectedRow = 0;
		// UPDATE EMP01 SET DEPTNO=20,JOB='MANAGER' WHERE ENAME 'SCOTT';
		String sql = "update goods_tb set packname=?, out_p=?" + ", in_p=?, outdate=?,indate=?, term=?, price=?, ea=?"
				+ " where gid =? ";

		
		try {
			// System.out.println("진행중~~~");
			psmt = con.prepareStatement(sql);
			psmt.setString(1, packname);
			psmt.setString(2, out_p);
			psmt.setString(3, in_p);
			psmt.setString(4, outdate);
			psmt.setString(5, indate);
			psmt.setInt(6, term);
			psmt.setInt(7, price);
			psmt.setInt(8, ea);
			psmt.setInt(9, gid);

			// dto.setOutdate(rs.getDate("outdate"));
			// dto.setIndate(rs.getDate("indate"));

			affectedRow += psmt.executeUpdate();

			// rs = psmt.executeQuery();

		} catch (SQLException e) {
			System.out.println(e + "=>SQL 오류");
		} finally {
			dbs.dbClose();
		}

		return affectedRow;
	}
	
	
	// 삭제 처리
	public int Delete(int gid) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		psmt = dbs.ps;
		rs = dbs.rs;
		int affectedRow = 0;
		String sql = "delete goods_tb where gid = ?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, gid);			
			affectedRow += psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=> 데이터삭제시 오류");
		} finally {
			dbs.dbClose();
		}
		return affectedRow;
	}

	public List<GoodsDTO> getRecordsearch(String fieldName, String word) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		psmt = dbs.ps;
		rs = dbs.rs;
		List<GoodsDTO> list = null;
		String sql = "select * from goods_tb where " + fieldName.trim() + " like '%" + word.trim() + "%'";
		try {
			list = new Vector<GoodsDTO>();
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
						GoodsDTO dto = new GoodsDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(7), rs.getInt(8), rs.getInt(9));
				dto.setOutdate(rs.getDate("outdate"));
				dto.setIndate(rs.getDate("indate"));

				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> getUserSearch fail");
		} finally {
			dbs.dbClose();
		}
		return list;
	}

	
	public List<GoodsDTO> getRecordsearch3(String fieldName, String word) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		psmt = dbs.ps;
		rs = dbs.rs;
		List<GoodsDTO> list = null;
		

		String sql = "select * from goods_tb where " + fieldName.trim() + "  = " + word + "";
		try {
			list = new Vector<GoodsDTO>();
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				GoodsDTO dto = new GoodsDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(7), rs.getInt(8), rs.getInt(9));
				dto.setOutdate(rs.getDate("outdate"));
				dto.setIndate(rs.getDate("indate"));
				list.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> getUserSearch fail");
		} finally {
			dbs.dbClose();
		}
		return list;
	}

	
	public List<GoodsDTO> getRecordsearch2(String fieldName, String word) {
		DBSington dbs = new DBSington();
		con = dbs.con;
		st = dbs.st;
		psmt = dbs.ps;
		rs = dbs.rs;
		List<GoodsDTO> list = null;

		
		String sql = "select * from goods_tb where " + fieldName.trim() + " ='" + word + "'";
		try {
			list = new Vector<GoodsDTO>();
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				GoodsDTO dto = new GoodsDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(7), rs.getInt(8), rs.getInt(9));
				dto.setOutdate(rs.getDate("outdate"));
				dto.setIndate(rs.getDate("indate"));
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
