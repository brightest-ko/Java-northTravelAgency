
public class GoodsDTO {
	private int gid; //pk키 시퀀스(1번부터 받는다고 immedi ~
	private String packname;
	private String out_p;
	private String in_p;	
	private java.sql.Date outdate;//   
	private java.sql.Date indate;
	private int  term;
	private int price;
	private int ea;
	public GoodsDTO () {
		
	}
	
	public GoodsDTO(int gid){
		super();
		this.gid = gid;
	}
	
public GoodsDTO (int gid, String packname) {
	super();
	this.gid = gid;
	this.packname = packname;
	}
	
	public GoodsDTO (int gid, String packname, String out_p, String in_p, int term, int price, int ea) {
		super();
		this.gid = gid;
		this.packname = packname;
		this.out_p = out_p;
		this.in_p = in_p;		
		this.term = term;
		this.price = price;
		this.ea = ea;		
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
	public java.sql.Date getOutdate() {
		return outdate;
	}
	public void setOutdate(java.sql.Date outdate) {
		this.outdate = outdate;
	}
	public java.sql.Date getIndate() {
		return indate;
	}
	public void setIndate(java.sql.Date indate) {
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
	public int getEa() {
		return ea;
	}
	public void setEa(int ea) {
		this.ea = ea;
	}
	
	
}
