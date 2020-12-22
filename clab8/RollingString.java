import java.util.LinkedList;

/**
 * A String-like class that allows users to add and remove characters in the
 * String in constant time and have a constant-time hash function. Used for the
 * Rabin-Karp string-matching algorithm.
 */
class RollingString {

  /**
   * Number of total possible int values a character can take on.
   * DO NOT CHANGE THIS.
   */
  static final int UNIQUECHARS = 128;

  /**
   * The prime base that we are using as our mod space. Happens to be 61B. :)
   * DO NOT CHANGE THIS.
   */
  static final int PRIMEBASE = 6113;

  /**
   * Initializes a RollingString with a current value of String s.
   * s must be the same length as the maximum length.
   */

  private LinkedList<Character> strList;
  private int fixedLength;
  private int unModdedHash;

  private int calcHashCode(String s, int length) {
    int sum = 0;
    for (int i = 0; i < length; i++) {
      sum += s.charAt(i) * Math.pow(UNIQUECHARS, i);
    }
    return sum;
  }

  public RollingString(String s, int length) {
    assert (s.length() == length);
    LinkedList<Character> lst = new LinkedList<Character>(s);
    fixedLength = length;
    unModdedHash = calcHashCode(s, length);
  }

  /**
   * Adds a character to the back of the stored "string" and
   * removes the first character of the "string".
   * Should be a constant-time operation.
   */
  public void addChar(char c) {
    char removed = strList.removeFirst();
    strList.addLast(c);
    unModdedHash -= removed * Math.pow(UNIQUECHARS, fixedLength - 1);
    unModdedHash = (unModdedHash * UNIQUECHARS) + c;
  }

  /**
   * Returns the "string" stored in this RollingString, i.e. materializes
   * the String. Should take linear time in the number of characters in
   * the string.
   */
  public String toString() {
    StringBuilder strb = new StringBuilder();
    for (Character ch : strList) {
      strb.append(ch);
    }
    return strb.toString();
  }

  /**
   * Returns the fixed length of the stored "string".
   * Should be a constant-time operation.
   */
  public int length() { return fixedLength; }

  /**
   * Checks if two RollingStrings are equal.
   * Two RollingStrings are equal if they have the same characters in the same
   * order, i.e. their materialized strings are the same.
   */
  @Override
  public boolean equals(Object o) {
    return (this.toString()).equals(o.toString());
  }

  /**
   * Returns the hashcode of the stored "string".
   * Should take constant time.
   */
  @Override
  public int hashCode() {
    return unModdedHash % PRIMEBASE;
  }
}
