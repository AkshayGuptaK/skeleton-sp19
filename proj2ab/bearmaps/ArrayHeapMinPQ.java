package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }

    private ArrayList<PriorityNode> heap;
    private HashMap<T, Integer> items;

    public ArrayHeapMinPQ() {
        heap = new ArrayList<PriorityNode>(1);
        items = new HashMap<T, Integer>();
    }

    private int getNodeIndex(PriorityNode node) {
        return items.get(node.getItem());
    }

    private int getParentIndex(int childIndex) {
        return childIndex / 2;
    }

    private int getLeftChildIndex(int parentIndex) {
        return parentIndex * 2;
    }

    private int getRightChildIndex(int parentIndex) {
        return parentIndex * 2 + 1;
    }

    private boolean compareAndSwap(PriorityNode parent, PriorityNode child) {
        if (parent == null || child == null)
            return false;
        if (parent.compareTo(child) < 0) {
            T parentItem = parent.getItem();
            T childItem = child.getItem();
            int parentIndex = items.get(parentItem);
            int childIndex = items.get(childItem);
            heap.set(parentIndex, child);
            heap.set(childIndex, parent);
            items.put(parentItem, childIndex);
            items.put(childItem, parentIndex);
            return true;
        }
        return false;
    }

    private void promote(int childIndex) {
        int parentIndex = getParentIndex(childIndex);
        PriorityNode parent = heap.get(parentIndex);
        PriorityNode child = heap.get(childIndex);
        while (compareAndSwap(parent, child)) {
            parentIndex = getParentIndex(parentIndex);
            parent = heap.get(parentIndex);
        }
    }

    private void demote(PriorityNode parent) {
        int parentIndex = getNodeIndex(parent);
        int leftChildIndex = getLeftChildIndex(parentIndex);
        int rightChildIndex = getRightChildIndex(parentIndex);
        PriorityNode leftChild = heap.get(leftChildIndex);
        PriorityNode rightChild = heap.get(rightChildIndex);
        if (leftChild == null)
            return;
        if (rightChild == null) {
            compareAndSwap(parent, leftChild);
            return;
        }
        PriorityNode highestChild = (leftChild.compareTo(rightChild) > 0) ? leftChild : rightChild;
        if (compareAndSwap(parent, highestChild))
            demote(parent);
    }

    @Override
    void add(T item, double priority) {
        if (contains(item) == true) {
            throw new IllegalArgumentException("Item already exists");
        }
        heap.add(new PriorityNode(item, priority));
        items.put(item, size() + 1);
        promote(size());
    }

    @Override
    boolean contains(T item) {
        return items.containsKey(item);
    }

    @Override
    T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return heap.get(1).getItem();
    }

    @Override
    T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode last = heap.remove(size());
        T smallest = heap.set(1, last).getItem();
        items.remove(smallest);
        items.put(last.getItem(), 1);
        demote(last);
        return smallest;
    }

    @Override
    int size() {
        return items.size();
    }

    @Override
    void changePriority(T item, double priority) {
        if (contains(item) == false) {
            throw new NoSuchElementException("PQ does not contain" + item);
        }
        int index = items.get(item);
        PriorityNode node = heap.get(index);
        node.setPriority(priority);
        promote(index);
        demote(node);
    }
}
