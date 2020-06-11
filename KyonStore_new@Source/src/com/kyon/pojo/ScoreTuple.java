package com.kyon.pojo;

public class ScoreTuple implements Comparable<ScoreTuple> {
	private String key = "";
	private double score = 0.0;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		long temp;
		temp = Double.doubleToLongBits(score);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreTuple other = (ScoreTuple) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (Double.doubleToLongBits(score) != Double.doubleToLongBits(other.score))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ScoreTuple [key=" + key + ", score=" + score + "]";
	}
	public ScoreTuple() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ScoreTuple(String key, double score) {
		super();
		this.key = key;
		this.score = score;
	}
	
	@Override
	public int compareTo(ScoreTuple st) {
		//自定义比较方法，如果认为此实体本身大则返回-1，否则返回1（反序）
		if(this.getScore() > st.getScore())
			return -1;
		return 1;
	}
	
	
}
