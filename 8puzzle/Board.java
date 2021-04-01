import edu.princeton.cs.algs4.Stack;

public final class Board {

    private final int n;
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = getBoardCopy(tiles);
    }

    // creates copy of tiles array
    private int[][] getBoardCopy(int[][] arr) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = arr[i][j];
            }
        }
        return copy;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int distance = 0;
        int x = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                x++;
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != x) {
                    distance++;
                }
            }
        }
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    int targetX = (tiles[i][j] - 1) / n;
                    int targetY = (tiles[i][j] - 1) % n;
                    int dx = i - targetX;
                    int dy = j - targetY;
                    manhattan += Math.abs(dx) + Math.abs(dy);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass().equals(Board.class)) {
            Board that = (Board) y;
            if (this.dimension() != that.dimension()) return false;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (tiles[i][j] != that.tiles[i][j]) return false;
                }
            }
            return true;
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<>();
        int row = -1;
        int col = -1;
        boolean found = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    row = i;
                    col = j;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        for (int i = -1; i <= 1; i++) {
            if (i == 0) continue;
            int newRow = row + i;
            if (newRow < 0 || newRow >= n) continue;
            int[][] copyBoard = getBoardCopy(tiles);
            int tmp = copyBoard[row][col];
            copyBoard[row][col] = copyBoard[newRow][col];
            copyBoard[newRow][col] = tmp;
            boards.push(new Board(copyBoard));
        }
        for (int i = -1; i <= 1; i++) {
            if (i == 0) continue;
            int newCol = col + i;
            if (newCol < 0 || newCol >= n) continue;
            int[][] copyBoard = getBoardCopy(tiles);
            int tmp = copyBoard[row][col];
            copyBoard[row][col] = copyBoard[row][newCol];
            copyBoard[row][newCol] = tmp;
            boards.push(new Board(copyBoard));
        }
        return boards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()  {
        int[][] copyTiles = getBoardCopy(tiles);
        if (copyTiles[0][0] != 0 && copyTiles[1][1] != 0) {
            int tmp = copyTiles[0][0];
            copyTiles[0][0] = copyTiles[1][1];
            copyTiles[1][1] = tmp;
        } else {
            int tmp = copyTiles[0][1];
            copyTiles[0][1] = copyTiles[1][0];
            copyTiles[1][0] = tmp;
        }
        return new Board(copyTiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[][] {{1, 0}, {2, 3}};

        Board board = new Board(tiles);

        System.out.println(board.isGoal());
    }
}