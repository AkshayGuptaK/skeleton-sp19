import static java.lang.Math.abs;

public class OffByN implements CharacterComparator {
    private int difference;
    public OffByN(int N) {
	difference = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
	return abs(x - y) == difference;
    }
}
