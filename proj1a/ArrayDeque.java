public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int start;

    private static final int rFactor = 2;
    private static final double usageRatio = 0.25;

    public ArrayDeque() {
	items = (Item[]) new Object[8];
	size = 0;
	start = 0;
    }

    private void resize(int capacity) {
       Item[] arr = (Item[]) new Object[capacity];
       if (start < 0) {
	   int itemsAtEnd = -start;
	   System.arraycopy(items, convertIndex(0), arr, 0, itemsAtEnd);
	   System.arraycopy(items, 0, arr, itemsAtEnd, size - itemsAtEnd);
       } else {
	   System.arraycopy(items, start, arr, 0, size);
       }       
       items = arr;
       start = 0;
    }

    private int convertIndex(int index) {
	int startAdjustedIndex = start + index;
	if (startAdjustedIndex < 0) {
	    return items.length + startAdjustedIndex;
	}
	return startAdjustedIndex;
    }

    private void set(int index, Item val) {
	items[convertIndex(index)] = val;
    }

    public void addFirst(Item x) {
	if (size == items.length) {
	    resize(size * rFactor);
	}
	
	set(-1, x);
	start -= 1;
	size += 1;
    }

    public Item removeFirst() {
	if (size == 0) {
	    return null;
	}
	Item first = get(0);
	set(0, null);
	size -= 1;
	start += 1;

	if (size / items.length < usageRatio && items.length > 8) {
	    resize(items.length / rFactor);
	}
	return first;
    }

    public void addLast(Item x) {
	if (size == items.length) {
	    resize(size * rFactor);
	}
	set(size, x);
	size += 1;
    }

    public Item removeLast() {
	if (size == 0) {
	    return null;
	}
	Item last  = get(size - 1);
	set(size - 1, null);
	size -= 1;

	if (size / items.length < usageRatio && items.length > 8) {
	    resize(items.length / rFactor);
	}
	return last;
    }

    public Item get(int i) {
	return items[convertIndex(i)];
    }

    public boolean isEmpty() {
	return size == 0;
    }

    public int size() {
	return size;
    }

    public void printDeque() {
	for (int i = 0; i < size; i++) {
	    System.out.print(get(i));
	    System.out.print(" ");
	}
	System.out.println();
    }
}
