package org.vaib;

public class SimpleCordinate {

	public int x;
	public int y;
	
	public SimpleCordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	public SimpleCordinate(double x, double y) {
		this.x = (new Double(x)).intValue();
		this.y = (new Double(y)).intValue();
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("X : ").append(x).append(" Y : ").append(y);
		return builder.toString();
	}
	
}
