package clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

import lombok.Getter;
import lombok.Setter;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class Data {
	ArrayList<Post> points = new ArrayList();
	public Post maxXY = new Post();
	public Post minXY = new Post();

	@SuppressWarnings("deprecation")
	public void getData(String pathname) throws IOException {
		String csvFile = "redditSubmissions_out.csv";
		Post newpoint;
		double xnew, ynew;
		minXY.setMax();
		maxXY.setMin();
		String previousId = "-1";
		String currentId = "-1";
		HeaderColumnNameTranslateMappingStrategy<Post> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<Post>();

		beanStrategy.setType(Post.class);
		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("#image_id", "imageId");
		columnMapping.put("unixtime", "unixtime");
		columnMapping.put("rawtime", "rawtime");
		columnMapping.put("title", "title");
		columnMapping.put("total_votes", "totalVotes");
		columnMapping.put("reddit_id", "redditId");
		columnMapping.put("number_of_upvotes", "numberOfUpvotes");
		columnMapping.put("subreddit", "subreddit");
		columnMapping.put("number_of_downvotes", "numberOfDownvotes");
		columnMapping.put("localtime", "localTime");
		columnMapping.put("score", "score");
		columnMapping.put("number_of_comments", "numberOfComments");
		columnMapping.put("username", "username");

		beanStrategy.setColumnMapping(columnMapping);

		CsvToBean<Post> csvToBean = new CsvToBean<Post>();
		CSVReader reader = new CSVReader(new FileReader(csvFile));
		points = (ArrayList<Post>) csvToBean.parse(beanStrategy, reader);
		points.remove(0);
		for (Post post : points) {
			currentId = post.getImageId();
			if (!currentId.equals(previousId)) {
				post.setOriginal(true);
			}
			xnew = Integer.parseInt(post.getScore());
			ynew = Integer.parseInt(post.getNumberOfComments());
			post.x = xnew;
			post.y = ynew;
			maxXY.x = Math.max(maxXY.x, xnew);
			maxXY.y = Math.max(maxXY.y, ynew);
			minXY.x = Math.min(minXY.x, xnew);
			minXY.y = Math.min(minXY.y, ynew);
			previousId = currentId;
		}
	}

	public void printData() {
		if (points.size() > 0) {
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
