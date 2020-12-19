package hw2;

public class Percolation {
  private boolean[] opens;
  private int gridSize;
  private int numberOpen;
  private UnionFind disjointSet;

  private UnionFind connectRow(UnionFind set, int row) {
    for (int i = coordsToIndex(row, 0); i < coordsToIndex(row, gridSize - 1);
         i++) {
      set.union(i, i + 1);
    }
    return set;
  }

  private UnionFind initDisjointSet(int n) {
    UnionFind set = new UnionFind(n);
    set = connectRow(set, 0);
    set = connectRow(set, gridSize - 1);
    return set;
  }

  public Percolation(int N) {
    gridSize = N;
    numberOpen = 0;
    disjointSet = initDisjointSet(N * N);
    opens = new boolean[N * N];
  }

  private int coordsToIndex(int row, int col) { return row * gridSize + col; }

  private void connectIfOpen(int v, int row, int col) {
    if (isOpen(row, col)) {
      disjointSet.union(v, coordsToIndex(row, col));
    }
  }

  private void connectAdjacents(int row, int col) {
    int index = coordsToIndex(row, col);
    if (row > 0) {
      connectIfOpen(index, row - 1, col);
    }
    if (row < gridSize - 1) {
      connectIfOpen(index, row + 1, col);
    }
    if (col > 0) {
      connectIfOpen(index, row, col - 1);
    }
    if (col < gridSize - 1) {
      connectIfOpen(index, row, col + 1);
    }
  }

  public void open(int row, int col) {
    if (!isOpen(row, col)) {
      opens[coordsToIndex(row, col)] = true;
      numberOpen++;
    }
    connectAdjacents(row, col);
  }

  public boolean isOpen(int row, int col) {
    return opens[coordsToIndex(row, col)];
  }

  public boolean isFull(int row, int col) {
    return isOpen(row, col) &&
        disjointSet.connected(0, coordsToIndex(row, col));
  }

  public int numberOfOpenSites() { return numberOpen; }

  public boolean percolates() {
    return disjointSet.connected(0, coordsToIndex(gridSize - 1, 0));
  }

  public static void main(String[] args) {}
}
