package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
  private class ArrayRingBufferIterator implements Iterator<T> {
    private int position;
    public ArrayRingBufferIterator() { position = first; }
    public boolean hasNext() { return position == last; }
    public T next() {
      T item = rb[position];
      position = incrementAndWrap(position);
      return item;
    }
  }
  /* Index for the next dequeue or peek. */
  private int first;
  /* Index for the next enqueue. */
  private int last;
  /* Variable for the fillCount. */
  private int fillCount;
  /* Array for storing the buffer data. */
  private T[] rb;

  private int incrementAndWrap(int x) {
    x++;
    if (x == rb.length) {
      x = 0;
    }
    return x;
  }

  /**
   * Create a new ArrayRingBuffer with the given capacity.
   */
  public ArrayRingBuffer(int capacity) {
    rb = (T[]) new Object[capacity];
    first = 0;
    last = 0;
    fillCount = 0;
  }

  @Override
  public int fillCount() {
    return fillCount;
  }

  @Override
  public int capacity() {
    return rb.length;
  }

  /**
   * Adds x to the end of the ring buffer. If there is no room, then
   * throw new RuntimeException("Ring buffer overflow").
   */
  @Override
  public void enqueue(T x) {
    if (this.isFull()) {
      throw new RuntimeException("Ring buffer overflow");
    }
    rb[last] = x;
    fillCount++;
    last = incrementAndWrap(last);
    return;
  }

  /**
   * Dequeue oldest item in the ring buffer. If the buffer is empty, then
   * throw new RuntimeException("Ring buffer underflow").
   */
  @Override
  public T dequeue() {
    if (this.isEmpty()) {
      throw new RuntimeException("Ring buffer underflow");
    }
    T item = rb[first];
    fillCount--;
    first = incrementAndWrap(first);
    return item;
  }

  /**
   * Return oldest item, but don't remove it. If the buffer is empty, then
   * throw new RuntimeException("Ring buffer underflow").
   */
  @Override
  public T peek() {
    if (this.isEmpty()) {
      throw new RuntimeException("Ring buffer underflow");
    }
    return rb[first];
  }

  @Override
  public Iterator<T> iterator() {
    return new ArrayRingBufferIterator();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof ArrayRingBuffer) {
      if (this.fillCount() == ((ArrayRingBuffer)o).fillCount()) {
        Iterator<T> iter = this.iterator();
        Iterator<T> oIter = ((ArrayRingBuffer<T>)o).iterator();
        while (iter.hasNext()) {
          if (iter.next() != oIter.next()) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }
}
