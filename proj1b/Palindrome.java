public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
	Deque<Character> d = new ArrayDeque();
	for (char c : word.toCharArray()) {
	    d.addLast(c);
	}
	return d;
    }

    public boolean isPalindrome(String word) {
	Deque<Character> d = wordToDeque(word);
	while (d.size() > 0) {
	    Character first = d.removeFirst();
	    Character last = d.removeLast();
	    if (last != null && first != last) {
		return false;
	    }
	}
	return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
	Deque<Character> d = wordToDeque(word);
	while (d.size() > 0) {
	    Character first = d.removeFirst();
	    Character last = d.removeLast();
	    if (last != null && !cc.equalChars(first, last)) {
		return false;
	    }
	}
	return true;
    }

    public static void main(String args[]) {
	Palindrome p = new Palindrome();
	CharacterComparator c = new OffByOne();
	if (p.isPalindrome("abba", c)) {
	    System.out.println("abba");
	}
	if (p.isPalindrome("oyo", c)) {
	    System.out.println("oyo");
	}	
	if (p.isPalindrome("z", c)) {
	    System.out.println("z");
	}
	if (p.isPalindrome("flake", c)) {
	    System.out.println("flake");
	}
	if (p.isPalindrome("bc", c)) {
	    System.out.println("bc");
	}
    }
}
