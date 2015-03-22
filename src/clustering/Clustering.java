package clustering;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Clustering {

	Distance distanceAlgorithm = new EUDistance();

	public static Distance getDistanceAlgorithm() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Select Distance Formula: E (Euclidean Distance)"
				+ " ES (Euclidean Distance Squred ) M (Manhatten)");
		String dist = scan.next();
		System.out.println(dist);
		Distance distanceAlgorithm = new EUDistance();
		if (dist.equals("E")) {
			distanceAlgorithm = new EUDistance();
		} else if (dist.equals("ES")) {
			distanceAlgorithm = new EUDistanceSquared();
		} else if (dist.equals("M")) {
			distanceAlgorithm = new ManhattanDistance();
		}
		scan.close();
		return distanceAlgorithm;
	}

	public static int[] selectAttributes() {
		Scanner scan = new Scanner(System.in);
		System.out.println("How Many elements would you like to cluster on");
		int count = scan.nextInt();
		String[] attributes = { "0:imageId", "1:unixtime", "2:Rawtime",
				"3:Title", "4:Total Votes", "5:Reddit Id",
				"6:Number Of Upvotes", "7:Subreddit", "8:Number Of Downvotes",
				"9:Local Time", "10:Score", "11:numberOfComments",
				"12:Username" };
		System.out
				.println("Enter Number for Each Atttribute You Would Like To Use"
						+ "\nSeperated By A Space, Press Enter to terminate");
		for (String string : attributes) {
			System.out.println(string);
		}
		int[] numbers = new int[count];
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < count; i++) {
			if (scan.hasNextInt()) {
				numbers[i] = scan.nextInt();
				System.out.println(numbers[i]);
			} else {
				System.out.println("You didn't provide enough numbers");
				break;
			}
		}
		return numbers;
	}

	public static void main(String[] args) throws IOException {
		Data input = new Data();		
		int[] atts = selectAttributes();
		input.getData("redditSubmissions_out.csv",atts);
		Distance distanceAlgorithm = getDistanceAlgorithm();
		Kmeans kmeans = new Kmeans();

		kmeans.setDistanceAlgorithm(distanceAlgorithm);
		kmeans.init(input, 4);
		kmeans.run();
		kmeans.summary();
	}
}
