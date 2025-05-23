package MapEdit;
import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Map_edit {
    private final int width, height;
    private TileType[][] grid;
    private TileType selectedTile = TileType.PATH;
    private Point startTile = null;
    private Point endTile = null;

    public enum TileType {
        PATH, START, END, TOWER_PREPLACED, TOWER_EMPTY, GRASS, DECORATIVE
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

        if (selectedTile == TileType.START) {
            if (isEdge(x, y)) {
                startTile = new Point(x, y);
                grid[y][x] = TileType.START;
            } else {
                return "Start tile must be on the edge of the map.";
            }
        } else if (selectedTile == TileType.END) {
            if (isEdge(x, y)) {
                endTile = new Point(x, y);
                grid[y][x] = TileType.END;
            } else {
                return "End tile must be on the edge of the map.";
            }
        } else {
            grid[y][x] = selectedTile;
        }
        return null;
    }

    public String validateMap() {
        if (startTile == null || endTile == null) {
            return "Both start and end tiles must be placed.";
        }
        if (!isConnected()) {
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
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("START:" + startTile.x + "," + startTile.y + "\n");
            fw.write("END:" + endTile.x + "," + endTile.y + "\n");
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

    private boolean isEdge(int x, int y) {
        return x == 0 || y == 0 || x == width - 1 || y == height - 1;
    }

    private boolean isConnected() {
        boolean[][] visited = new boolean[height][width];
        Queue<Point> queue = new LinkedList<>();
        queue.add(startTile);

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int x = p.x, y = p.y;
            if (x < 0 || x >= width || y < 0 || y >= height) continue;
            if (visited[y][x]) continue;
            TileType t = grid[y][x];
            if (t != TileType.PATH && t != TileType.START && t != TileType.END) continue;

            visited[y][x] = true;

            queue.add(new Point(x + 1, y));
            queue.add(new Point(x - 1, y));
            queue.add(new Point(x, y + 1));
            queue.add(new Point(x, y - 1));
        }

        return visited[endTile.y][endTile.x];
    }
}
