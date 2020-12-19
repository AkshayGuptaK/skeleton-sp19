import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
  private class Node {
    K key;
    V value;
    Node left;
    Node right;
    Node parent;

    Node(K k, V v) {
      key = k;
      value = v;
      left = null;
      right = null;
      parent = null;
    }
  }

  private class BSTMapIterator implements Iterator<K> {
    private Node position;

    BSTMapIterator() { position = findLeftMostChild(root); }

    public boolean hasNext() { return position != null; }

    public K next() {
      K key = position.key;
      if (isLeaf(position)) {
        if (isLeftChild(position)) {
          position = position.parent;
        } else {
          position = findNextRightParent(position);
        }
      } else if (position.right != null) {
        position = findLeftMostChild(position.right);
      } else {
        position = findNextRightParent(position);
      }
      return key;
    }
  }

  private int size;
  private Node root;

  public BSTMap() {
    size = 0;
    root = null;
  }

  private boolean isLeaf(Node node) {
    return node.left == null && node.right == null;
  }

  private boolean isLeftChild(Node node) { return node.parent.left == node; }

  private Node findLeftMostChild(Node node) {
    if (node == null)
      return null;
    Node leftNode = findLeftMostChild(node.left);
    if (leftNode == null) {
      return node;
    }
    return leftNode;
  }

  private Node findNextRightParent(Node node) {
    if (node == null)
      return null;
    if (isLeftChild(node)) {
      return node.parent;
    }
    return findNextRightParent(node.parent);
  }

  @Override
  public void clear() {
    root = null;
  }

  private Node recurSearch(Node node, K key) {
    if (node == null) {
      return null;
    }
    int comparison = key.compareTo(node.key);
    if (comparison < 0) {
      return recurSearch(node.left, key);
    }
    if (comparison > 0) {
      return recurSearch(node.right, key);
    }
    return node;
  }

  private Node recurInsert(Node node, K key, V val) {
    Node newNode;
    if (node == null) {
      size++;
      return new Node(key, val);
    }
    int comparison = key.compareTo(node.key);
    if (comparison < 0) {
      newNode = recurInsert(node.left, key, val);
      node.left = newNode;
      newNode.parent = node.left;
      return node;
    }
    if (comparison > 0) {
      newNode = recurInsert(node.right, key, val);
      node.right = newNode;
      newNode.parent = node.right;
      return node;
    }
    node.value = val;
    return node;
  }

  @Override
  public boolean containsKey(K key) {
    return recurSearch(root, key) != null;
  }

  @Override
  public V get(K key) {
    Node node = recurSearch(root, key);
    if (node == null)
      return null;
    return node.value;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void put(K key, V value) {
    root = recurInsert(root, key, value);
  }

  private void replaceNodeWith(Node node, Node newNode) {
    if (isLeftChild(node)) {
      node.parent.left = newNode;
    } else {
      node.parent.right = newNode;
    }
  }

  private void deleteNode(Node node) {
    if (isLeaf(node)) {
      replaceNodeWith(node, null);
    } else if (node.left == null) {
      replaceNodeWith(node, node.right);
    } else if (node.right == null) {
      replaceNodeWith(node, node.left);
    } else {
      Node successor = findLeftMostChild(node.right);
      deleteNode(successor);
      successor.right = node.right;
      successor.left = node.left;
      replaceNodeWith(node, successor);
    }
  }

  @Override
  public V remove(K key) {
    Node node = recurSearch(root, key);
    if (node == null)
      return null;
    V val = node.value;
    deleteNode(node);
    return val;
  }

  @Override
  public V remove(K key, V value) {
    Node node = recurSearch(root, key);
    if (node == null || node.value != value)
      return null;
    deleteNode(node);
    return value;
  }

  @Override
  public Set<K> keySet() {
    Set<K> s = new TreeSet<K>();
    for (K key : this) {
      s.add(key);
    }
    return s;
  }

  @Override
  public Iterator<K> iterator() {
    return new BSTMapIterator();
  }

  public void printInOrder() {
    for (K key : this) {
      System.out.println(key);
    }
  }
}
