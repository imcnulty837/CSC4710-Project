package javaPkg;

public class ImageTag {
	private int imageid;
	private int tagid;
	
	public ImageTag(int imageid, int tagid) {
		this.imageid = imageid;
		this.tagid = tagid;
	}
	
	public void setImageId(int imageid) {
		this.imageid = imageid;
	}
	
	public int getImageId() {
		return this.imageid;
	}
	
	public void setTagId(int tagid) {
		this.tagid = tagid;
	}
	
	public int getTagId() {
		return this.tagid;
	}
}
