
package clustering;


public class Point {
    public double x;
    public double y;

   public void setMin(){
        x = Double.MIN_VALUE;
        y = Double.MIN_VALUE;
    }
    
    public void setMax(){
        x = Double.MAX_VALUE;
        y = Double.MAX_VALUE;        
    }
    
    public void print(){
        System.out.println("(" + x + ", " + y + ")");
    }
}