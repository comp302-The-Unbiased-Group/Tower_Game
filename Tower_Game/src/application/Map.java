package application;
import java.awt.Point;
import java.util.*;

public class Map {
	
    private Tile[][] tiles;
    private List<Point> path_ = new ArrayList<>();
    private Point start_point;
    private Point end_point;

    public Map(int width, int height) {
        tiles = new Tile[width][height];
    }
}