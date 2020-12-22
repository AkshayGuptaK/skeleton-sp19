public class RabinKarpAlgorithm {

  /**
   * This algorithm returns the starting index of the matching substring.
   * This method will return -1 if no matching substring is found, or if the
   * input is invalid.
   */
  public static int rabinKarp(String input, String pattern) {
    int index = 0;
    int inputLength = input.length();
    int patternLength = pattern.length();

    if (inputLength == 0 || inputLength < patternLength)
      return -1;

    RollingString patternToMatch = new RollingString(pattern, patternLength);
    int patternHash = patternToMatch.hashCode();
    RollingString rollingInput =
        new RollingString(input.substring(0, patternLength), patternLength);
    while (index + inputLength < patternLength) {
      if (rollingInput.hashCode() == patternHash &&
          rollingInput.equals(patternToMatch))
        return index;
      rollingInput.addChar(input.charAt(index + inputLength));
      index++;
    }
    return -1;
  }
}
