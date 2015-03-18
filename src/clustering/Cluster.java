package clustering;

import java.util.LinkedList;


public class Cluster {
    Point centroid = new Point();
    LinkedList <Point> points = new LinkedList <Point>();
    double clusterDistanceFromCentroid = Double.MAX_VALUE;
    
    public void setCentroid(Point newCentroid){
        centroid = newCentroid;
    }    

    public Point getCentroid() {
        return(centroid);
    }
    
    public void addPoint (Point p){
        points.add(p);
    }

    public void resetPoints(){
        points.clear();
        clusterDistanceFromCentroid = 0.0;
    }    
    
    public void clusterUpdate(){
        this.calculateCentroid();
        this.getClusterDistanceFromCentroid();
    }
    
    public void calculateCentroid(){
        centroid.x = 0d;
        centroid.y = 0d;
        Double totalX = 0.0;
        Double totalY = 0.0;
        int n = points.size();

        for (Point point : points){
            totalX += point.x;
            totalY += point.y; }
        centroid.x = totalX / n;
        centroid.y = totalY / n;
        System.out.print("Centroid: (" + centroid.x + "," + centroid.y + ")");
    }    

    public double getClusterDistanceFromCentroid() {
        EUDistance distance = new EUDistance();
        for (Point point : points) {
            clusterDistanceFromCentroid += distance.getDistance(point, centroid);
        }
         return(clusterDistanceFromCentroid);
    }
    
    public void printCluster(){
        System.out.println("\n# points in cluster: " + points.size());
        System.out.println("Cluster entropy: " + clusterDistanceFromCentroid + "\n");
    }
}
