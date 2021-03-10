package dataType;

public class Like {
	private String email;
	private int imageId;
	private boolean likeSwitch;
	public Like(String email, int imageId, boolean likeSwitch) {
		this.email = email;
		this.imageId = imageId;
		this.likeSwitch = likeSwitch;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return this.email;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public int getImageId() {
		return this.imageId;
	}
	public void setLikeSwitch(boolean likeSwitch) {
		this.likeSwitch = likeSwitch;
	}
	public boolean getLikeSwitch() {
		return this.likeSwitch;
	}
}
