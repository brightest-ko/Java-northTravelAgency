import java.sql.Date;

public class UserReservDTO {
	int reserv_num;
	int gid;
	String packname,out_p, in_p;
	java.sql.Date outdate;
	java.sql.Date indate;
	int term, price;
	java.sql.Date reservdate;
	String cancel;
	
	public int getReserv_num() {
		return reserv_num;
	}
	public void setReserv_num(int reserv_num) {
		this.reserv_num = reserv_num;
	}
	public UserReservDTO(){
		
	}
	public UserReservDTO(int reserv_num,int gid,String packname, String out_p, String in_p, Date outdate, Date indate, int term,int  price, Date reservdate,String cancel) {
		super();
		this.reserv_num = reserv_num;
		this.gid = gid;
		this.packname = packname;
		this.out_p = out_p;
		this.in_p = in_p;
		this.outdate = outdate;
		this.indate = indate;
		this.term = term;
		this.price = price;
		this.reservdate = reservdate;
		this.cancel = cancel;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getCancel() {
		return cancel;
	}
	public void setCancel(String cancel) {
		this.cancel = cancel;
	}
	public String getPackname() {
		return packname;
	}
	public void setPackname(String packname) {
		this.packname = packname;
	}
	public String getOut_p() {
		return out_p;
	}
	public void setOut_p(String out_p) {
		this.out_p = out_p;
	}
	public String getIn_p() {
		return in_p;
	}
	public void setIn_p(String in_p) {
		this.in_p = in_p;
	}
	public Date getOutdate() {
		return outdate;
	}
	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}
	public Date getIndate() {
		return indate;
	}
	public void setIndate(Date indate) {
		this.indate = indate;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getReservdate() {
		return reservdate;
	}
	public void setReservdate(Date reservdate) {
		this.reservdate = reservdate;
	}
}
