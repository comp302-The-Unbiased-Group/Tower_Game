/*package application;

import java.awt.Point;

public class PathFinding {
	
	private TileType[][] Map;
	private int currX;
	private int currY;
	private int rows;
	private int columns;
	private int previousX = -1;
	private int previousY = -1;
	private boolean finished = false;
	
	 public PathFinding(TileType[][] map, int startX, int startY) {
	        this.Map = map;
	        this.rows = map.length;
	        this.columns = map[0].length;
	        this.currX = startX;
	        this.currY = startY;
	    }
	
	 public Point nextStep() {
	        if (finished) return null;

	        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}}; 

	        for (int[] dir : directions) {
	            int nx = currX + dir[0];
	            int ny = currY + dir[1];

	            if (nx < 0 || ny < 0 || nx >= columns || ny >= rows) continue;
	            if (Map[ny][nx] != TileType.PATH) continue;
	            if (nx == previousX && ny == previousY) continue; // 

	     
	            previousX = currX;
	            previousY = currY;
	            currX = nx;
	            currY = ny;

	            return new Point(currX, currY);
	            
	        
	        
	        }
	        finished = true; // No more valid moves
	        return null; // No valid moves
	      }
	 
	 public boolean isFinished() {
	        return finished;
	    }
	
	
	
	
	
	
	
	
	

	public TileType[][] getMap() {
		return Map;
	}

	public void setMap(TileType[][] map) {
		Map = map;
	}
	
	
	

}*/
