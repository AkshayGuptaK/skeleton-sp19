import static java.lang.Math.abs;


public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
	return abs(x - y) == 1;
    }
}
