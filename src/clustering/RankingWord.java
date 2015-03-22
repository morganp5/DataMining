package clustering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;

/**
 * Calculate the Manhattan distance.
 */
public class RankingWord {
	private static final Map<String,Pair> m_map = new HashMap<String,Pair>();
	
	public void rankingAllWords(Data data,int[] atts) throws IOException{
		
		for (int x=0 ; x < data.posts.size(); x++) {
			Post post = data.posts.get(x);
			String[] words = post.getTitle().split(" ");
			for(String word:words){
				if(m_map.containsKey(word)){
					Pair p = m_map.get(word);
					p.useThis(Integer.parseInt(post.getScore()));
				}else{
					Pair p = new Pair();
					m_map.put(word, p);
					p.useThis(Integer.parseInt(post.getScore()));
				}
			}
			
		}
		
	}
	
	public int rankingOfWord(String key){
		int k = 0;
		for (Entry<String, Pair> entry : m_map.entrySet())
        {
			k++;
			if(entry.getKey() == key){
				return k;
			}
        }
		return -1;
		
	}
	
	public void rankingPrint(){
		SortMapByValue sortedMap = new SortMapByValue();
		Map<String, Pair> sortedMapAsc = sortedMap.sortByComparator(m_map, sortedMap.DESC);
        SortMapByValue.printMap(sortedMapAsc);	
	}
	 
}