package clustering;


import java.util.ArrayList;
import java.util.Random;

/* @author Muse */

public class Kmeans {
    ArrayList <Cluster> clusters = new ArrayList<Cluster>();
    double systemDistanceFromCentroids;
    double prevTotalDistance;
    double StopAt = 0.01;
    int k;
    int maxIterations = 10;
    Data values;
    Random random = new Random();    

    public void init (Data data, int k) {
            Point newCentroid, newpoint;
            Cluster newCluster;
            systemDistanceFromCentroids = Double.MAX_VALUE;
            System.out.println(k + " clusters will be created.");
            this.values=data;
            for (int i = 0; i < k; i++){
                newCentroid = new Point();
                newCentroid.x = random.nextDouble()*(values.maxXY.x - values.minXY.x) + values.minXY.x;
                newCentroid.y = random.nextDouble()*(values.maxXY.y - values.minXY.y) + values.minXY.y;
                newCluster = new Cluster();
                newCluster.setCentroid(newCentroid);
                clusters.add(newCluster);  
            }
            for (Cluster cluster : clusters) {
            System.out.println("Random centroid: "+cluster.centroid.x+","+cluster.centroid.y);
            }
            System.out.println("System entropy: "+systemDistanceFromCentroids);
    }
    
    public void clearClusters(){
        for (Cluster cluster : clusters) {
            cluster.resetPoints();    }   
   //     System.out.println("\nEmptied cluster and clusterDistance.");
}    

    public void assignPoints(){
        double minDistance, newDistance;
        EUDistance measureStyle = new EUDistance();
          for (Point p: values.points) {
            minDistance = Double.MAX_VALUE;
            Cluster closestCluster=null;
            for (Cluster c: clusters) {
                newDistance= measureStyle.getDistance(p, c.getCentroid());
                if (newDistance < minDistance ) {
                    minDistance = newDistance;
                    closestCluster=c;
                }   
            }    
            closestCluster.addPoint(p);
          }
    }

    public void updateClusters(){
        for (Cluster c : clusters) {
           c.clusterUpdate(); 
           c.printCluster();
        }
    }
    
    public void updateSystemDistance(){
        prevTotalDistance = systemDistanceFromCentroids;
        systemDistanceFromCentroids = 0.0;
        for (Cluster c : clusters) {
           double clusterDist = c.getClusterDistanceFromCentroid();
            systemDistanceFromCentroids+= clusterDist;  }
        System.out.println("System energy: " + systemDistanceFromCentroids/2);
    }

    public void run() {
        int counter = 0;    
        boolean done = false;
        prevTotalDistance = Double.MAX_VALUE;
        while ((counter < maxIterations) 
                & !done
                ) {
           System.out.println("\nRound #" + (counter+1));
           System.out.println("---------");
           clearClusters();
            assignPoints();
            updateClusters();
            updateSystemDistance(); 
           counter++;
           if((prevTotalDistance - systemDistanceFromCentroids) < StopAt) {
                done = true;}
           System.out.println("Change over previous: "+ ((systemDistanceFromCentroids - prevTotalDistance)/2));
            }
        System.out.println("\nSummary\n--------");
        System.out.println("Total iterations: "+counter);
     }
 

    public void summary(){
        for (Point point : values.points) {}
            //System.out.println("Point: "+point.x+","+point.y);}    
        for (Cluster cluster : clusters) {
            System.out.println("Final centroid: "+cluster.centroid.x+","+cluster.centroid.y);}
        System.out.println("System energy: "+ systemDistanceFromCentroids/2);
    }
}