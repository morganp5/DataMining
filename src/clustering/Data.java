package clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

import lombok.Getter;
import lombok.Setter;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class Data {
	ArrayList<Post> posts = new ArrayList<Post>();
    /** index of Attributes to be used */
    private int[] attributesUsed = {4, 10,11};

	@SuppressWarnings("deprecation")
	public void getData(String pathname) throws IOException {
		String csvFile = "redditSubmissions_out.csv";
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
		posts = (ArrayList<Post>) csvToBean.parse(beanStrategy, reader);
		posts.remove(0);
		for (Post post : posts) {
			post.addClusterPoint(attributesUsed);
			currentId = post.getImageId();
			if (!currentId.equals(previousId)) {
				post.setOriginal(true);
			}
			previousId = currentId;
		}
	}
	
	public int getNumAttributes(){
		return attributesUsed.length;
	}

}
