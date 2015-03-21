package clustering;

import java.util.HashMap;
import java.util.LinkedList;

public class Cluster {
	LinkedList<Post> Posts = new LinkedList<Post>();
	Distance distanceFormula;
	int centroidSize;
	String mostPopularSub = "";
	double numOfPopular = 0;
	double originalImageCount = 0;
	double clusterDistanceFromCentroid = Double.MAX_VALUE;
	/* centroid of the cluster */
	private double[] centroid;

	public Cluster(Distance type) {
		distanceFormula = type;
	}

	public Cluster() {
		// TODO Auto-generated constructor stub
	}

	public void init(int size) {
		centroid = new double[size];
		centroidSize = size;
	}

	public int getSize() {
		return Posts.size();
	}

	public double[] getCentroid() {
		return (centroid);
	}

	public boolean isNull() {
		return Posts.size() == 0;
	}

	public void addPost(Post p) {
		Posts.add(p);
	}

	public Cluster split() {
		Cluster emptyCluster = new Cluster(distanceFormula);
		emptyCluster.centroid = new double[centroidSize];
		for (int i = 0; i < Posts.size() / 2; i++) {
			Post post = Posts.remove(0);
			emptyCluster.addPost(post);
		}
		return emptyCluster;
	}

	public void resetPoints() {
		Posts.clear();
		clusterDistanceFromCentroid = 0.0;
	}

	public void clusterUpdate() {
		this.calculateCentroid();
		this.getClusterDistanceFromCentroid();
	}

	public void calculateCentroid() {
		for (Post point : Posts) {
			double[] r = point.getClusterPoints();
			for (int j = 0; j < centroidSize; j++) {
				centroid[j] += r[j];
			}
		}
		for (int i = 0; i < centroid.length; i++) {
			centroid[i] /= Posts.size();
		}
		printCentroid();
	}

	public double getClusterDistanceFromCentroid() {
		Distance distance = distanceFormula;
		for (Post point : Posts) {
			double[] clusterPoints = point.getClusterPoints();
			clusterDistanceFromCentroid += distance.getDistance(clusterPoints,
					centroid);
		}
		return (clusterDistanceFromCentroid);
	}

	public void getStats() {
		HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();
		originalImageCount = 0;
		for (Post p : Posts) {
			String sub = p.getSubreddit();
			Integer i = wordCounts.get(sub);
			// Tracks Number Of Original Images
			if (p.isOriginal())
				originalImageCount++;
			// Count number of time each subreddit present
			if (i == null) {
				wordCounts.put(sub, 1);
				i = 1;
			} else
				wordCounts.put(sub, i + 1);
			if (i > numOfPopular) {
				numOfPopular = wordCounts.get(sub);
				mostPopularSub = sub;
			}
		}

	}

	public double getOriginalPercentage() {
		double perc = 0;
		if (Posts.size() > 0) {
			perc = (originalImageCount / Posts.size()) * 100;
		}
		return perc;
	}

	public void printCentroid() {
		String centroidString = "Centroid: (";
		for (int x = 0; x < centroidSize; x++) {
			double centroidElement = centroid[x];
			if(x==0){
				centroidString = centroidString + centroidElement;
			}
			else centroidString = centroidString + ", " + centroidElement;
		}
		centroidString += ')';
		System.out.println(centroidString);
	}

	public void printCluster() {
		System.out.println("\n# points in cluster: " + Posts.size());
		getStats();
		System.out.println("Main Subreddit " + mostPopularSub);
		System.out.println("Percentage Original Content " + Math.round(getOriginalPercentage()));
		System.out.println("Cluster entropy: " + clusterDistanceFromCentroid
				+ "\n");
	}
}
