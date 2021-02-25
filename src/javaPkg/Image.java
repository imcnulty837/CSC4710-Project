package javaPkg;

public class Image {
	protected String url;
	protected String imageId;
	protected String description;
	protected String timestamp;
	protected String email;
	
	public Image() {
		
	}
	
	public Image(String url, String imageId, String description, String timestamp, String email) {
		this.url = url;
		this.imageId = imageId;
		this.description = description;
		this.timestamp = timestamp;
		this.email = email;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
