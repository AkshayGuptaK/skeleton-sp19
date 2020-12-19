import java.util.*;

public class UnionFind {

    private int[] disjointSet;
    private int size;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        disjointSet = new int[n];
	Arrays.fill(disjointSet, -1);
	size = n;
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
	if (vertex >= size) {
	    throw new IllegalArgumentException("Not a valid index");
	}
    }

     /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
	validate(v1);
	return disjointSet[v1];
    }
   
    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
	validate(v1);
	return -parent(find(v1));
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
	validate(vertex);
      	LinkedList<Integer> nonroots = new LinkedList<>();
	int v = vertex;
	int p = parent(vertex);

	while (p >= 0) {
	    nonroots.addFirst(v);
	    v = p;
	    p = parent(v);
	}

	nonroots.removeFirst();
	for (int ver : nonroots) {
	    union(v, ver);
	}

        return v;
    }
    
    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
	validate(v1);
	validate(v2);
	return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
	validate(v1);
	validate(v2);
	int parent1 = find(v1);
	int parent2 = find(v2);

	if (parent1 == parent2) {
	    return; // already connected
	}

       	int size1 = sizeOf(v1);
        int size2 = sizeOf(v2);

	if (size1 > size2) {
	    disjointSet[parent2] = parent1;
	    disjointSet[parent1] = -(size1 + size2);
	}
	else {
	    disjointSet[parent1] = parent2;
	    disjointSet[parent2] = -(size1 + size2);
	}
    }

}
