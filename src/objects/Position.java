package objects;

public class Position implements Comparable<Position>{
	private int x;
	private int y;
	
	public Position (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}

	public int compareTo (Position position) {
		int compareX = ((Position) position).getX();
		return this.getX() - compareX;
	}

	
}
