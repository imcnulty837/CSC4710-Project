package javaPkg.dataType;

public class Follow {
	private String followeremail;
	private String followeeemail;
	public Follow(String followeremail, String followeeemail) {
		this.followeremail = followeremail;
		this.followeeemail = followeeemail;
	}
	public void setFollowerEmail(String followeremail) {
		this.followeremail = followeremail;
	}
	public String getFollowerEmail() {
		return this.followeremail;
	}
	public void setFolloweeEmail(String followeeemail) {
		this.followeeemail = followeeemail;
	}
	public String getFolloweeEmail() {
		return this.followeeemail;
	}
}
