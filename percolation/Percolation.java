import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final WeightedQuickUnionUF ufGrid;
    private final WeightedQuickUnionUF ufFull;
    private final boolean[][] opened;
    private final int top;
    private final int bottom;
    private int openedSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("size should be bigger than 0");
        this.n = n;
        this.ufGrid = new WeightedQuickUnionUF(n * n + 2);
        this.ufFull = new WeightedQuickUnionUF(n * n + 1);
        this.opened = new boolean[n][n];
        this.openedSites = 0;
        this.top = n * n;
        this.bottom = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }

        int rowCol1DIndex = xyTo1D(row, col) - 1;
        opened[row - 1][col - 1] = true;
        openedSites++;

        if (row == 1) {
            ufGrid.union(top, rowCol1DIndex);
            ufFull.union(top, rowCol1DIndex);
        }

        if (row == n) {
            ufGrid.union(bottom, rowCol1DIndex);
        }

        int colLeft = col - 1;
        if (colLeft > 0 && colLeft <= n && isOpen(row, colLeft)) {
            ufGrid.union(rowCol1DIndex, xyTo1D(row, colLeft) - 1);
            ufFull.union(rowCol1DIndex, xyTo1D(row, colLeft) - 1);
        }

        int colRight = col + 1;
        if (colRight > 0 && colRight <= n && isOpen(row, colRight)) {
            ufGrid.union(rowCol1DIndex, xyTo1D(row, colRight) - 1);
            ufFull.union(rowCol1DIndex, xyTo1D(row, colRight) - 1);
        }

        int rowUp = row - 1;
        if (rowUp > 0 && rowUp <= n && isOpen(rowUp, col)) {
            ufGrid.union(rowCol1DIndex, xyTo1D(rowUp, col) - 1);
            ufFull.union(rowCol1DIndex, xyTo1D(rowUp, col) - 1);
        }

        int rowDown = row + 1;
        if (rowDown > 0 && rowDown <= n && isOpen(rowDown, col)) {
            ufGrid.union(rowCol1DIndex, xyTo1D(rowDown, col) - 1);
            ufFull.union(rowCol1DIndex, xyTo1D(rowDown, col) - 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return opened[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col) && ufFull.find(xyTo1D(row, col) - 1) == ufFull.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1 && !isOpen(1, 1)) return false;
        return ufGrid.find(top) == ufGrid.find(bottom);
    }

    // converts 2d indices to 1d
    private int xyTo1D(int row, int col) {
        return (n * (row-1) + col);
    }

    // validates the indices
    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IllegalArgumentException("row and/or col out of bounds");
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(2);
        percolation.open(1, 1);
    }
}