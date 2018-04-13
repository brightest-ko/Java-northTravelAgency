import java.sql.Date;

public class ReservDTO {

	private int reserv_num;
	private int gid; // number references goods_tb(gid),
	private String packname;// varchar2(30) not null,
	private String out_p;// varchar2(15),
	private String in_p;// varchar2(15),
	private Date outdate;// date// date,
	private Date indate;// date,
	private int term;// number(5),
	private int price; // number(10) not null,
	private int ea; // number(5) not null,
	private Date reservdate;// date,
	private String id;// varchar2(15) references member_tb(id)
	private String cancel;

	public ReservDTO(){
		
	}
	public ReservDTO(int gid, String packname,String out_p,String in_p,Date outdate,Date indate,int term,int price,int ea) {
		super();
		this.gid = gid;
		this.packname = packname;
		this.out_p = out_p;
		this.in_p = in_p;
		this.outdate = outdate;
		this.indate = indate;
		this.term = term;
		this.price = price;
		this.ea = ea;
	}
	
	public int getReserv_num() {
		return reserv_num;
	}

	public void setReserv_num(int reserv_num) {
		this.reserv_num = reserv_num;
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

	public String getCancel() {
		return cancel;
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}


	public ReservDTO(int gid, String packname, int ea, String id) {
		super();
		this.gid = gid;
		this.packname = packname;
		this.ea = ea;
		this.id = id;
	}


	public ReservDTO(int gid) {
		super();
		this.gid = gid;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getPackname() {
		return packname;
	}

	public void setPackname(String packname) {
		this.packname = packname;
	}

	public int getEa() {
		return ea;
	}

	public void setEa(int ea) {
		this.ea = ea;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public java.sql.Date getReservdate() {
		return reservdate;
	}

	public void setReservdate(java.sql.Date reservdate) {
		this.reservdate = reservdate;
	}
}
