package clustering;

import lombok.Getter;
import lombok.Setter;

public class Pair {
	int score;
	int usedNTimes;
	
	public void getPair(){
		score = 0;
		usedNTimes = 0;
	}
	
	public void useThis(int n){
		this.score += n;
		this.usedNTimes++;
	}
	
	public double getAverageScore(){
		return this.score/this.usedNTimes;
	}
	
	public int getFreqUsed(){
		return this.usedNTimes;
	}

}