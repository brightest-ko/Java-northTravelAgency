public class Member2DTO {
	
	private String id,pwd1,pwd2,name,email,hp,addr,leave;
	
	private char gender,master;
	 
	
	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	private java.sql.Date regdate,birth;
			
	public Member2DTO() { super(); } 
	
	Member2DTO(String id,String pwd1,String pwd2,String name,	String email,String hp,String addr){
		super(); 
		this.id = id;
		this.pwd1=pwd1;
		this.pwd2=pwd2;
		this.name = name;
		this.email= email;
		this.hp = hp;
		this.addr = addr;
	}
	
	public Member2DTO (String id,String pwd1,String pwd2,String name,
			String email,String hp,String addr,char gender, char master,
			java.sql.Date birth) {
		super(); 
		this.id = id;
		this.pwd1=pwd1;
		this.pwd2=pwd2;
		this.name = name;
		this.email= email;
		this.hp = hp;
		this.addr = addr;
		this.gender = gender;
		this.master = master;
		this.birth = birth;		
	}
	
	public Member2DTO (String id,String pwd1,String pwd2,String name,
			String email,String hp,String addr,char gender, char master,
			java.sql.Date regdate, java.sql.Date birth) {
		
		super(); 
		this.id = id;
		this.pwd1=pwd1;
		this.pwd2=pwd2;
		this.name = name;
		this.email= email;
		this.hp = hp;
		this.addr = addr;
		this.gender = gender;
		this.master = master;
		this.regdate = regdate;
		this.birth = birth;		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd1() {
		return pwd1;
	}

	public void setPwd1(String pwd1) {
		this.pwd1 = pwd1;
	}

	public String getPwd2() {
		return pwd2;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public char getMaster() {
		return master;
	}

	public void setMaster(char master) {
		this.master = master;
	}

	public java.sql.Date getRegdate() {
		return regdate;
	}

	public void setRegdate(java.sql.Date regdate) {
		this.regdate = regdate;
	}

	public java.sql.Date getBirth() {
		return birth;
	}

	public void setBirth(java.sql.Date birth) {
		this.birth = birth;
	}
 
 
}