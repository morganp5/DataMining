package clustering;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SortMapByValue
	{
	    public  boolean ASC = true;
	    public boolean DESC = false;

	    public Map<String, Pair> sortByComparator(Map<String, Pair> mMap, final boolean order)
	    {

	        List<Entry<String, Pair>> list = new LinkedList<Entry<String, Pair>>(mMap.entrySet());

	        // Sorting the list based on values
	        Collections.sort(list, new Comparator<Entry<String, Pair>>()
	        {
	            public int compare(Entry<String, Pair> o1,
	                    Entry<String, Pair> o2)
	            {
	                if (order)
	                {
	                	double v1 = o1.getValue().getAverageScore();
	                	double v2 = o2.getValue().getAverageScore();
	                	if(v1>v2)
	                		return 1;
	                	else
	                		return 0;
	        
	                }
	                else
	                {
	                	double v1 = o1.getValue().getAverageScore();
	                	double v2 = o2.getValue().getAverageScore();
	                	if(v1>v2)
	                		return 0;
	                	else
	                		return 1;
	        
	                }
	            }
	        });

	        // Maintaining insertion order with the help of LinkedList
	        Map<String, Pair> sortedMap = new LinkedHashMap<String, Pair>();
	        for (Entry<String, Pair> entry : list)
	        {
	            sortedMap.put(entry.getKey(), entry.getValue());
	        }

	        return sortedMap;
	    }

	    public static void printMap(Map<String, Pair> map)
	    {
	        for (Entry<String, Pair> entry : map.entrySet())
	        {
	            System.out.println("Key : " + entry.getKey() + " Avarege : "+ entry.getValue().getAverageScore() + "Frequence:" + entry.getValue().getFreqUsed());
	        }
	    }
	}