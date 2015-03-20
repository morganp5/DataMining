package clustering;

import java.io.IOException;


/**
 * @author Muse
 */
public class Clustering {

    public static void main(String[] args) throws IOException {
        Data input=new Data();
        input.getData(".csv");
        input.printData();
        EUDistance Eu = new EUDistance();
        Kmeans kmeans = new Kmeans();
        kmeans.init(input, 5,Eu);
        kmeans.run();
        kmeans.summary();
    }
}
