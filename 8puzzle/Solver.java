import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private SearchNode finalNode;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("board can not be null");
        MinPQ<SearchNode> visitingNodes = new MinPQ<>();
        MinPQ<SearchNode> visitingNodesTwin = new MinPQ<>();
        this.finalNode = null;
        this.solvable = false;
        // A*
        visitingNodes.insert(new SearchNode(initial, null, 0));
        visitingNodesTwin.insert(new SearchNode(initial.twin(), null, 0));

        SearchNode current;
        SearchNode currentTwin;
        while (true) {
            current = visitingNodes.delMin();
            currentTwin = visitingNodesTwin.delMin();
            if (current.board.isGoal()) {
                finalNode = current;
                solvable = true;
                break;
            } else if (currentTwin.board.isGoal()) {
                finalNode = null;
                solvable = false;
                break;
            } else {
                for (Board neighbor : current.board.neighbors()) {
                    if (current.previous != null) {
                        if (!neighbor.equals(current.previous.board))
                            visitingNodes.insert(new SearchNode(neighbor, current, current.moves + 1));
                    } else {
                        visitingNodes.insert(new SearchNode(neighbor, current, current.moves + 1));
                    }
                }
                for (Board neighbor : currentTwin.board.neighbors()) {
                    if (currentTwin.previous != null) {
                        if (!neighbor.equals(currentTwin.previous.board))
                            visitingNodesTwin.insert(new SearchNode(neighbor, currentTwin, currentTwin.moves + 1));
                    } else {
                        visitingNodesTwin.insert(new SearchNode(neighbor, currentTwin, currentTwin.moves + 1));
                    }
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return finalNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> sequence = new Stack<>();
        for (SearchNode i = finalNode; i != null; i = i.previous) {
            sequence.push(i.board);
        }
        return sequence;
    }

    private static class SearchNode implements Comparable<SearchNode> {

        private Board board;
        private SearchNode previous;
        private int moves;
        private int priority;

        public SearchNode(Board board, SearchNode previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.priority = board.manhattan() + moves;
        }

        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // int[][] tiles = new int[][] {{2, 3, 5}, {1, 0, 4}, {7, 8, 6}};
        int[][] tiles = new int[][] {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
        Board board = new Board(tiles);
        Solver solver = new Solver(board);

        System.out.println(solver.isSolvable());
    }
}