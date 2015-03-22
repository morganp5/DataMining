package clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

import lombok.Getter;
import lombok.Setter;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class Data {
	ArrayList<Post> posts = new ArrayList<Post>();
    /** index of Attributes to be used */
    private int[] attributesUsed;

	@SuppressWarnings("deprecation")
	public void getData(String pathname,int[] attributes) throws IOException {
		attributesUsed = attributes;
		String csvFile = pathname;
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
	
	public void cleanTitleCSV() throws IOException{
		 CSVReader reader = new CSVReader(new FileReader("redditSubmissions_out.csv"));
		 String [] nextLine;
		 CSVWriter writer = new CSVWriter(new FileWriter("redditSubmissions_out2.csv"), ',');
		 String[] entries;
		 
		 ArrayList<String> stopWords = new ArrayList<String>();
	     Scanner r = new Scanner(new File("stop_words.txt"));
	     //String row = r.readLine();
	     
	     while(r.hasNext()){
	    	 stopWords.add(r.next());
	     }
	     r.close();
         Pattern pattern = Pattern.compile("[a-zA-Z']*");    
	     try {
			while ((nextLine = reader.readNext()) != null) {
				 entries = nextLine;
				 String[] words = nextLine[3].split(" ");
				 entries[3] = "";
				 int flag = 0;
				 for(int j = 0;j<words.length;j++){
					 Matcher matcher = pattern.matcher(words[j].toLowerCase());					 
		             if(matcher.find() && !stopWords.contains(matcher.group())){
		             	if(flag!=0){
		             		entries[3] = entries[3].concat(" ");
		             	}
		             	flag++;
		            	 entries[3] = entries[3].concat(words[j].toLowerCase());
		             }
		             //System.out.println(matcher.group());
		         }
				 //System.out.println(entries);
				 writer.writeNext(entries,true);
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 writer.close();
	     	
	}
	
	public int getNumAttributes(){
		return attributesUsed.length;
	}

}
