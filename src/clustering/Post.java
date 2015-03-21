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

	private double[] clusterPoints;
	public Post(){
		
		
	}
	public void addClusterPoint(int[] clusterOn) {
		clusterPoints = new double[clusterOn.length];
		String[] attributes = { imageId, unixtime, rawtime, title, totalVotes,
				redditId, numberOfUpvotes, subreddit, numberOfDownvotes,
				localTime, score, numberOfComments, username };
		for (int i = 0; i < clusterOn.length; i++) {
			clusterPoints[i] = Double.parseDouble(attributes[clusterOn[i]]);
		}
	}

	public double[] getClusterPoints() {
		return clusterPoints;
	}

	public int getNumClusterOn() {
		return clusterPoints.length;
	}

}