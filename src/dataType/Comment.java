package dataType;

public class Comment {
	private int imageid;
	private String email;
	private String comment;
	public Comment(int imageid, String email, String comment) {
		this.imageid = imageid;
		this.email = email;
		this.comment = comment;
	}
	public void setImageId(int imageid) {
		this.imageid = imageid;
	}
	public int getImageId() {
		return this.imageid;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return this.email;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getComment() {
		return this.comment;
	}
}
