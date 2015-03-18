package clustering;

import java.util.ArrayList;
import java.io.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class Data {
	ArrayList<Point> points = new ArrayList();
	public Point maxXY = new Point();
	public Point minXY = new Point();

	public void getData(String pathname) throws IOException{
		String csvFile = "redditSubmissions_out.csv";
		Point newpoint;
		double xnew, ynew;
		minXY.setMax();
		maxXY.setMin();
		// create CSVReader object
		CSVReader reader = new CSVReader(new FileReader(csvFile), ',');
		// read line by line
		String[] record = null;
		// skip header row
		reader.readNext();
		while ((record = reader.readNext()) != null) {
			newpoint = new Point();
			xnew = Integer.parseInt(record[0]);
			ynew = Integer.parseInt(record[10]);
			newpoint.x = xnew;
			newpoint.y = ynew;

			points.add(newpoint);

			maxXY.x = Math.max(maxXY.x, xnew);
			maxXY.y = Math.max(maxXY.y, ynew);
			minXY.x = Math.min(minXY.x, xnew);
			minXY.y = Math.min(minXY.y, ynew);
		}
	}

	public void printData() {
		if (points.size() > 0) {
			/*
			 * for (int i = 0; i < points.size(); i++) { points.get(i).print();
			 * }
			 */

			System.out.println("Max point:");
			maxXY.print();
			System.out.println("Min point:");
			minXY.print();
		}

		else {
			System.out.println("No points.");
		}
	}
}
