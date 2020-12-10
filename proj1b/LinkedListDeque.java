public class LinkedListDeque<T> implements Deque<T> {
    
    private class Node {
	public T item;
	public Node next;
	public Node prev;

	public Node(T i, Node n, Node p) {
	    item = i;
	    next = n;
	    prev = p;
	}
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
	sentinel  = new Node(null, null, null);
	sentinel.next = sentinel;
	sentinel.prev = sentinel;
	size = 0;
    }

    @Override
    public int size() {
	return size;
    }

    @Override
    public void addFirst(T x) {
	Node currentFirst = sentinel.next;
	sentinel.next  = new Node(x, currentFirst, sentinel);
	currentFirst.prev = sentinel.next;
	size += 1;
    }

    @Override
    public T removeFirst() {
	Node first = sentinel.next;
	Node second = first.next;
	sentinel.next = second;
	second.prev = sentinel;
	size -=1;
	return first.item;
    }

    @Override
    public void addLast(T x) {
	Node currentLast = sentinel.prev;
	sentinel.prev = new Node(x, sentinel, currentLast);
	currentLast.next = sentinel.prev;
	size += 1;
    }

    @Override
    public T removeLast() {
	Node last = sentinel.prev;
	Node secondLast = last.prev;
	sentinel.prev = secondLast;
        secondLast.next = sentinel;
	size -=1;
	return last.item;
    }

    public T get(int index) {
	Node p = sentinel.next;
	return getRecursiveHelper(p, index);
    }

    public T getRecursiveHelper(Node p, int index) {
	if (p == null) {
	    return null;
	}
	if (index == 0) {
	    return p.item;
	}
	return getRecursiveHelper(p.next, index - 1);
    }

    @Override
    public void printDeque() {
	Node p = sentinel.next;
	int printed = 0;

	while (p != null && printed < size) {
	    System.out.print(p.item);
	    System.out.print(" ");
	    printed += 1;
	}

	System.out.println();
    }
}
