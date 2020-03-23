package entity;

public class Info {
	String vid;
	String vname;
	String vpath;
	String vtype;
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	public String getVpath() {
		return vpath;
	}
	public void setVpath(String vpath) {
		this.vpath = vpath;
	}
	public String getVtype() {
		return vtype;
	}
	public void setVtype(String vtype) {
		this.vtype = vtype;
	}
	//¹¹ÔìÆ÷
	public Info(String vid,String vname,String vpath,String vtype) {
		this.vid = vid;
		this.vname =vname;
		this.vpath = vpath;
		this.vtype = vtype;
	}
}
