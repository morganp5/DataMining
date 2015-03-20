package clustering;


public class EUDistance implements Distance {

    public double getDistance(Post A, Post B){
       return (Math.sqrt ( Math.pow(A.x - B.x,2) + Math.pow(A.y - B.y,2) ));
}
}
