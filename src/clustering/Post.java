package clustering;

import lombok.Getter;
import lombok.Setter;

public class Post {
	@Getter
	@Setter
	private String imageId;
	@Getter
	@Setter
	private String unixtime;
	@Getter
	@Setter
	private String rawtime;
	@Getter
	@Setter
	private String title;
	@Getter
	@Setter
	private String totalVotes;
	@Getter
	@Setter
	private String redditId;
	@Getter
	@Setter
	private String numberOfUpvotes;
	@Getter
	@Setter
	private String subreddit;
	@Getter
	@Setter
	private String numberOfDownvotes;
	@Getter
	@Setter
	private String localTime;
	@Getter
	@Setter
	private String score;
	@Getter
	@Setter
	private String numberOfComments;
	@Getter
	@Setter
	private String username;
	@Getter
	@Setter
	private boolean original;

	public double x;
	public double y;

	public void setMin() {
		x = Double.MIN_VALUE;
		y = Double.MIN_VALUE;
	}

	public void setMax() {
		x = Double.MAX_VALUE;
		y = Double.MAX_VALUE;
	}

	public void print() {
		System.out.println("(" + x + ", " + y + ")" + getImageId()
				+ getUnixtime() + getTotalVotes());
	}
}