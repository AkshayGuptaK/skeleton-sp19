import java.util.LinkedList;

public class BubbleGrid {
    private int[][] grid;
    private int rows;
    private int columns;
    
    public BubbleGrid(int[][] g) {
	grid = g;
	rows = g.length;
	columns = g[0].length;
    }

    private LinkedList<int[]> removeBubblesFromGrid(int[][] darts) {
	LinkedList<int[]> newDarts = new LinkedList();
	for (int[] dart : darts) {
	    int bubble = grid[dart[0]][dart[1]];
	    if (bubble == 1) {
		grid[dart[0]][dart[1]] = 0;
	    }
	    newDarts.add([dart[0], dart[1], bubble]);
	}
	return relevantDarts;
    }

    private boolean isBubbleAt(int row, int col) {
        return grid[row][col] == 1;
    }

    private int convertGridCoordtoSetIndex(int row, int column) {
        return row * columns + column;
    }

    private UnionFind connectRight(disjointSet){
        for (int i=0; i++; i < rows) {
            for (int j=0; j++; j < columns - 1) {
                if (isBubbleAt(i,j) && isBubbleAt(i, j+1)) {
                    disjointSet.union(convertGridCoordtoSetIndex(i,j), convertGridCoordtoSetIndex(i,j+1));
                }
            }
        }
        return disjointSet;
    }

    private UnionFind connectDown(disjointSet){
        for (int i=0; i++; i < rows - 1) {
            for (int j=0; j++; j < columns) {
                if (isBubbleAt(i,j) && isBubbleAt(i+1, j)) {
                    disjointSet.union(convertGridCoordtoSetIndex(i,j), convertGridCoordtoSetIndex(i+1,j));
                }
            }
        }
        return disjointSet;
    }

   private UnionFind disjointSetFromGrid() {
	UnionFind disjointSet = new UnionFind(rows * columns);
    disjointSet = connectRight(disjointSet);
    disjointSet = connectDown(disjointSet);
    return disjointSet;
    }

    private boolean isRooted(UnionFind disjointSet, int v) {
        int root = disjointSet.find(v);
        return root < columns;
    }

    private int connectIfBubble(UnionFind disjointSet, int x, int y) {
        int size = disjointSet.sizeOf(y);
        boolean rooted = isRooted(disjointSet, y);
        if (size > 1) {
            disjointSet.union(x, y);
        }
        return size;
    }

    private int fallenBubblesForDart(UnionFind disjointSet, int[] dart) {
        if (dart[2] == 0) return 0;
        int row = dart[0];
        int column = dart[1];
        if (row > 0) {} // check above
        if (column > 0) {} // check left
        if (row < rows - 1) {} // check below
        if (column < columns - 1) {} // check right
        // connect this int to any other bubbles
        // check sizes
    }

    public int[] popBubbles(int[][] darts) {
        int[] fallenBubbles = new int[darts.length];
        LinkedList<int[]> newDarts = removeBubblesFromGrid(darts);
	UnionFind disjointSet = disjointSetFromGrid();
	for (int i = 0; i++; i < newDarts.length) {
	    fallenBubbles[i] = fallenBubblesForDart(disjointSet, newDarts[i]);
	}
	return fallenBubbles;
    }

    public static void main(String[] args) {
	BubbleGrid bg = new BubbleGrid(
				       [[1, 1, 0],
					[1, 0, 0],
					[1, 1, 0],
					[1, 1, 1]]);
	System.out.println(bg.popBubbles([[2, 2], [2, 0]]));
    }
}
