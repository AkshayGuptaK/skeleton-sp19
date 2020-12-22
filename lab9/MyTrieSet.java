import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyTrieSet implements TrieSet61B {
  private class Node {
    private boolean isKey;
    private HashMap<Character, Node> links;
    public Node() {
      isKey = false;
      links = new HashMap<Character, Node>();
    }

    public boolean isKey() { return isKey; }

    public void setKey() { isKey = true; }

    public void clearLinks() { links.clear(); }

    public Node getLink(Character ch) { return links.get(ch); }

    public void addLink(Character key, Node val) { links.put(key, val); }

    public Set<Character> getChildren() { return links.keySet(); }
  }

  private Node root;

  public MyTrieSet() { root = new Node(); }

  @Override
  public void clear() {
    root.clearLinks();
  }

  @Override
  public boolean contains(String key) {
    if (key == null || key.length() < 1)
      return false;

    char[] keyChars = key.toCharArray();
    Node curr = root;
    Node next = null;

    for (Character ch : keyChars) {
      next = curr.getLink(ch);
      if (next == null) {
        return false;
      }
      curr = next;
    }
    return curr.isKey();
  }

  @Override
  public void add(String key) {
    if (key == null || key.length() < 1)
      return;
    char[] keyChars = key.toCharArray();
    Node curr = root;
    Node next = null;

    for (Character ch : keyChars) {
      next = curr.getLink(ch);
      if (next == null) {
        next = new Node();
        curr.addLink(ch, next);
      }
      curr = next;
    }
    curr.setKey();
  }

  private List<String> allKeysFromNode(String prefix, Node node) {
    List<String> results = new ArrayList<String>();
    if (node.isKey())
      results.add(prefix);
    for (Character ch : node.getChildren()) {
      results.addAll(allKeysFromNode(prefix + ch, node.getLink(ch)));
    }
    return results;
  }

  @Override
  public List<String> keysWithPrefix(String prefix) {
    List<String> results = new ArrayList<String>();
    if (prefix == null)
      return results;
    char[] prefixChars = prefix.toCharArray();
    Node curr = root;
    Node next = null;

    for (Character ch : prefixChars) {
      next = curr.getLink(ch);
      if (next == null) {
        return results;
      }
      curr = next;
    }
    return allKeysFromNode(prefix, curr);
  }

  @Override
  public String longestPrefixOf(String key) {
    throw new UnsupportedOperationException();
  }
}
