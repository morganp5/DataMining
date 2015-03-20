package clustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;

import lombok.Synchronized;

public class Kmeans {
	ArrayList<Cluster> clusters = new ArrayList<Cluster>();
	double systemDistanceFromCentroids;
	double prevTotalDistance;
	double StopAt = 0.01;
	int k;
	int maxIterations = 5;
	Data values;
	Random random = new Random();

	public void init(Data data, int k, Distance distanceAlgorithm) {
		Post newCentroid, newpoint;
		Cluster newCluster;
		systemDistanceFromCentroids = Double.MAX_VALUE;
		System.out.println(k + " clusters will be created.");
		this.values = data;
		for (int i = 0; i < k; i++) {
			newCentroid = new Post();
			newCentroid.x = random.nextDouble()
					* (values.maxXY.x - values.minXY.x) + values.minXY.x;
			newCentroid.y = random.nextDouble()
					* (values.maxXY.y - values.minXY.y) + values.minXY.y;
			newCluster = new Cluster(distanceAlgorithm);
			newCluster.setCentroid(newCentroid);
			clusters.add(newCluster);
		}
		for (Cluster cluster : clusters) {
			System.out.println("Random centroid: " + cluster.centroid.x + ","
					+ cluster.centroid.y);
		}
		System.out.println("System entropy: " + systemDistanceFromCentroids);
	}

	public void clearClusters() {
		for (Cluster cluster : clusters) {
			cluster.resetPoints();
		}
	}

	public void assignPoints() {
		double minDistance, newDistance;
		ManhattanDistance measureStyle = new ManhattanDistance();
		for (Post p : values.points) {
			minDistance = Double.MAX_VALUE;
			Cluster closestCluster = null;
			for (Cluster c : clusters) {
				newDistance = measureStyle.getDistance(p, c.getCentroid());
				if (newDistance < minDistance) {
					minDistance = newDistance;
					closestCluster = c;
				}
			}
			closestCluster.addPoint(p);
		}
	}

	public void updateClusters() {
		for (Cluster c : clusters) {
			c.clusterUpdate();
			c.printCluster();
		}
	}
	Cluster largestCluster = new Cluster();

	public void updateSystemDistance() {
		double largestDistance = 0;
		prevTotalDistance = systemDistanceFromCentroids;
		systemDistanceFromCentroids = 0.0;
		for (Cluster c : clusters) {
			double clusterDist = c.getClusterDistanceFromCentroid();
			systemDistanceFromCentroids += clusterDist;
			if (clusterDist > largestDistance) {
				largestCluster = c;
				largestDistance = clusterDist;
			}
		}
		System.out.println("System energy: " + systemDistanceFromCentroids / 2);
		largestDistance = 0;
	}

	public void run() {
		int counter = 0;
		boolean done = false;
		prevTotalDistance = Double.MAX_VALUE;
		while ((counter < maxIterations) & !done) {
			System.out.println("\nRound #" + (counter + 1));
			System.out.println("---------");
			clearClusters();
			assignPoints();
			updateClusters();
			updateSystemDistance();
			// Check if any cluster is empty and splits the largest distance
			// cluster in half
			ListIterator<Cluster> it = clusters.listIterator();
			while(it.hasNext()) {
				Cluster c = it.next();	
				if (c.isNull()) {
					System.out.println("Spliting empty cluster");
					c = largestCluster.split();
					c.calculateCentroid();
					it.set(c);
				}
			}
			counter++;
			if ((prevTotalDistance - systemDistanceFromCentroids) < StopAt) {
				done = true;
			}
			System.out.println("Change over previous: "
					+ ((systemDistanceFromCentroids - prevTotalDistance) / 2));
		}
		System.out.println("\nSummary\n--------");
		System.out.println("Total iterations: " + counter);
	}

	public void summary() {
		for (Cluster cluster : clusters) {
			System.out.println("Final centroid: " + cluster.centroid.x + ","
					+ cluster.centroid.y);
		}
		System.out.println("System energy: " + systemDistanceFromCentroids / 2);
	}
}