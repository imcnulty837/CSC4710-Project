package javaPkg;

import java.sql.Timestamp;

public class Image {
	protected int imageId;
	protected Timestamp timestamp;
	protected String email;
	protected String url;
	protected String description;
	protected int likeCount;
	protected boolean likeSwitch;
	
	public Image() {
		
	}
	
	public Image(String url, String email, String description) {
		this.url = url;
		this.description = description;
		this.email = email;
	}
	
	public Image(int imageId,  Timestamp timestamp, String email, String url, String description, boolean likeSwitch) {
		this.url = url;
		this.imageId = imageId;
		this.description = description;
		this.timestamp = timestamp;
		this.email = email;
		this.likeSwitch = likeSwitch;
	}
	
	public Image(int imageId,  Timestamp timestamp, String email, String url, String description, int count, boolean likeSwitch) {
		this.url = url;
		this.imageId = imageId;
		this.description = description;
		this.timestamp = timestamp;
		this.email = email;
		this.likeCount = count;
		this.likeSwitch = likeSwitch;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getLikeCount() {
		return this.likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public boolean isLikeSwitch() {
		return likeSwitch;
	}

	public void setLikeSwitch(boolean likeSwitch) {
		this.likeSwitch = likeSwitch;
	}
}
