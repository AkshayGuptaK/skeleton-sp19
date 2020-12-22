public class MyHashMap<K, V> implements Map61B<K, V> {
  private int size;
  private double loadFactor;

  public MyHashMap() {
    size = 16;
    loadFactor = 0.75;
  }

  public MyHashMap(int initialSize) {
    size = initialSize;
    loadFactor = 0.75;
  }

  public MyHashMap(int initialSize, double loadF) {
    size = initialSize;
    loadFactor = loadF;
  }
}
