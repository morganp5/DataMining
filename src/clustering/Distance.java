package clustering;

public interface Distance {
    public double getDistance(double[] x1, double[] x2); 
    @Override
    public String toString();
}
