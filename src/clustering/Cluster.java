package clustering;

import java.util.HashMap;
import java.util.LinkedList;

public class Cluster {
	Post centroid = new Post();
	LinkedList<Post> points = new LinkedList<Post>();
	double clusterDistanceFromCentroid = Double.MAX_VALUE;
	Distance distanceFormula;

	public Cluster(Distance type) {
		distanceFormula = type;
	}

	public Cluster() {
		// TODO Auto-generated constructor stub
	}

	public void setCentroid(Post newCentroid) {
		centroid = newCentroid;
	}

	public int getSize() {
		return points.size();
	}

	public Post getCentroid() {
		return (centroid);
	}
	public boolean isNull(){
		System.out.println(points.size()==0);
		return points.size()==0;
	}
	public void addPoint(Post p) {
		points.add(p);
	}

	public Cluster split() {
		Cluster emptyCluster = new Cluster(distanceFormula);
		for (int i = 0; i < points.size() / 2; i++) {
			Post point = points.remove(0);
			emptyCluster.addPoint(point);
		}
		System.out.println("C SIZE" + emptyCluster.getSize());
		return emptyCluster;
	}

	public void resetPoints() {
		points.clear();
		clusterDistanceFromCentroid = 0.0;
	}

	public void clusterUpdate() {
		this.calculateCentroid();
		this.getClusterDistanceFromCentroid();
	}

	public void calculateCentroid() {
		centroid.x = 0d;
		centroid.y = 0d;
		Double totalX = 0.0;
		Double totalY = 0.0;
		int n = points.size();

		for (Post point : points) {
			totalX += point.x;
			totalY += point.y;
		}
		centroid.x = totalX / n;
		centroid.y = totalY / n;
		System.out.print("Centroid: (" + centroid.x + "," + centroid.y + ")");
	}/*
	 * public double calculateVariance(Partition partition) { double []mean =
	 * calculateMean(records); double []variance = new
	 * double[records.get(0).getData().length]; for (Record r : records) {
	 * double [] values = r.getData(); for (int i = 0; i < values.length; i++) {
	 * variance[i] += (mean[i] - values[i]) * (mean[i] - values[i]); } }
	 * 
	 * double sum = 0; for (int i = 0; i < variance.length; i++) { variance[i] =
	 * variance[i]/ records.size(); sum += variance[i]; } return sum; } /*
	 * public double [] calculateMean() {
	 * 
	 * for (Point p : points) { double mean = mean + p.x + p.y ; for (int i = 0;
	 * i < values.length; i++) { means[i] += values[i]; } }
	 * 
	 * for (int i = 0; i < means.length; i++) { means[i] = means[i]/
	 * records.size(); }
	 * 
	 * return means; }
	 */

	public double getClusterDistanceFromCentroid() {
		Distance distance = distanceFormula;
		for (Post point : points) {
			clusterDistanceFromCentroid += distance
					.getDistance(point, centroid);
		}
		return (clusterDistanceFromCentroid);
	}

	String mostPopularSub = "";
	double numOfPopular = 0;
	double originalImageCount = 0;

	public void getStats() {
		HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();
		originalImageCount = 0;
		for (Post p : points) {
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
		if (points.size() > 0) {
			perc = (originalImageCount / points.size()) * 100;
		}
		return perc;
	}

	public void printCluster() {
		System.out.println("\n# points in cluster: " + points.size());
		getStats();
		System.out.println("Main Subreddit " + mostPopularSub
				+ "Percentage OC " + Math.round(getOriginalPercentage()));
		System.out.println("Cluster entropy: " + clusterDistanceFromCentroid
				+ "\n");
	}
}
