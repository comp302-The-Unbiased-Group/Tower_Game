package MapEdit;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Map_edit {
    private int lastX = -1, lastY = -1;
    private TileType lastTile = TileType.GRASS;
    private final int width, height;
    private TileType[][] grid;
    private TileType selectedTile = TileType.GRASS;

    public enum TileType {
        PATH_HORIZONTAL, PATH_VERTICAL,
        PATH_TOP_LEFT, PATH_TOP_RIGHT,
        PATH_BOTTOM_LEFT, PATH_BOTTOM_RIGHT,
        GRASS, TOWER_EMPTY,
        ARCHER_TOWER, MAGE_TOWER, ARTILLERY_TOWER,
        DECORATIVE
    }

    public Map_edit(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new TileType[height][width];
    }

    public void initializeGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = TileType.GRASS;
            }
        }
    }

    public void setSelectedTile(TileType tile) {
        this.selectedTile = tile;
    }

    public TileType getTile(int x, int y) {
        return grid[y][x];
    }

    public String placeTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) return null;


        lastX = x;
        lastY = y;
        lastTile = grid[y][x];

        grid[y][x] = selectedTile;
        return null;
    }

    private boolean isEdge(int x, int y) {
        return x == 0 || y == 0 || x == width - 1 || y == height - 1;
    }

    private boolean isPathType(TileType type) {
        return switch (type) {
            case PATH_HORIZONTAL, PATH_VERTICAL,
                 PATH_TOP_LEFT, PATH_TOP_RIGHT,
                 PATH_BOTTOM_LEFT, PATH_BOTTOM_RIGHT -> true;
            default -> false;
        };
    }

    private Point findEdgePathTile(boolean fromTopLeft) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i = fromTopLeft ? y : (height - 1 - y);
                int j = fromTopLeft ? x : (width - 1 - x);
                if (isEdge(j, i) && isPathType(grid[i][j])) {
                    return new Point(j, i);
                }
            }
        }
        return null;
    }

    public String validateMap() {
        Point startTile = findEdgePathTile(true);
        Point endTile = findEdgePathTile(false);

        if (startTile == null || endTile == null) {
            return "At least two edge-connected path tiles must exist.";
        }
        if (!isConnected(startTile, endTile)) {
            return "Path from start to end is not continuous.";
        }

        int emptyLots = 0;
        for (TileType[] row : grid) {
            for (TileType t : row) {
                if (t == TileType.TOWER_EMPTY) emptyLots++;
            }
        }
        if (emptyLots < 4) {
            return "There must be at least 4 empty tower lots.";
        }
        return null;
    }

    public boolean saveMap(String filename) {
        Point startTile = findEdgePathTile(true);
        Point endTile = findEdgePathTile(false);
        try (FileWriter fw = new FileWriter(filename)) {
            if (startTile != null && endTile != null) {
                fw.write("START:" + startTile.x + "," + startTile.y + "\n");
                fw.write("END:" + endTile.x + "," + endTile.y + "\n");
            }
            for (TileType[] row : grid) {
                for (TileType t : row) {
                    fw.write(t.name() + " ");
                }
                fw.write("\n");
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean isConnected(Point start, Point end) {
        boolean[][] visited = new boolean[height][width];
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int x = p.x, y = p.y;
            if (x < 0 || x >= width || y < 0 || y >= height) continue;
            if (visited[y][x]) continue;
            TileType t = grid[y][x];
            if (!isPathType(t)) continue;

            visited[y][x] = true;

            queue.add(new Point(x + 1, y));
            queue.add(new Point(x - 1, y));
            queue.add(new Point(x, y + 1));
            queue.add(new Point(x, y - 1));
        }

        return visited[end.y][end.x];
    }
    public void undoLastPlacement() {
        if (lastX != -1 && lastY != -1) {
            grid[lastY][lastX] = lastTile;
        }
    }
}

