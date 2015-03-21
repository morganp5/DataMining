package clustering;

import java.io.IOException;


public class Clustering {

    public static void main(String[] args) throws IOException {
        Data input=new Data();
        input.getData(".csv");
        EUDistance Eu = new EUDistance();
        Kmeans kmeans = new Kmeans();
        kmeans.setDistanceAlgorithm(Eu);
        kmeans.init(input, 4);
        kmeans.run();
        kmeans.summary();
    }
}
