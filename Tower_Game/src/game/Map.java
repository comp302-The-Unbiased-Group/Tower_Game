package game;

import enemy.Enemy;
import tile.Tile;
import tile.BuildableTile;
import tile.DecorativeTile;
import tile.PathTile;

import java.awt.Point;
import java.util.*;

public class Map {

    private Tile[][] tiles; // Grid of tiles on map
    private List<Point> path_ = new ArrayList<>(); // Enemy path (in pixels)
    private Point start_point; // Path starting point
    private Point end_point;   // Path ending point

    public Map(int width, int height) {
        tiles = new Tile[width][height];
    }

    // check if a tile is buildable
    public boolean is_buildable(int x, int y) {
        return tiles[x][y] instanceof BuildableTile;
    }

    public Tile get_tile(int x, int y) {
        return tiles[x][y];
    }

    public void set_tile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }

    public void set_start_point(Point startPoint) {
        this.start_point = startPoint;
    }

    public void set_end_point(Point endPoint) {
        this.end_point = endPoint;
    }

    // adding pixel-based point to the enemy path
    public void add_path_point(Point point) {
        path_.add(point);
    }

    public List<Point> get_path() {
        return path_;
    }

    // validate if there's a connected path from start to end
    public boolean validate_path() {
        if ( path_.isEmpty()  || start_point == null || end_point == null)
            return false;
        
        //bfs to check if path is connected
        Set<Point> visited = new HashSet<>();
        Queue<Point> queue = new ArrayDeque<>();
        queue.add(start_point);
        

        while (!queue.isEmpty()) {
        	
            Point current = queue.poll();
            
            if (visited.contains(current)) {
            	continue;
            }

            visited.add(current);
            
            if (current.equals(end_point)) {
            	return true;
            }

            for (Point neighbor : getNeighbors(current)) { //find connected path tiles
                if (!visited.contains(neighbor)) {
                	queue.add(neighbor);
                }
            }
        }

        return false; //if this loop finishes and we never reached the end_point, that means the path is broken or disconnected, so the function will return false.
    }

    // getting path-connected neighbor points (up,down,left,right)
    private List<Point> getNeighbors(Point p) {
        int[][] directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
        List<Point> neighbors = new ArrayList<>();

        for (int[] dir : directions) {
        	
            int neighbor_x = p.x + dir[0] * 64;//moves 64 pixels in each direction
            int neighbor_y = p.y + dir[1] * 64;
            Point neighbor = new Point(neighbor_x, neighbor_y);
            
            if (path_.contains(neighbor)) {
            	neighbors.add(neighbor);
            }
        }

        return neighbors;
    }
}

