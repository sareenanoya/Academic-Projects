package algs21;
import stdlib.*;
// Exercise 2.1.14
/**
 * Complete the following method to sort a deck of cards,
 * with the restriction that the only allowed operations are to look
 * at the values of the top two cards, to exchange the top two cards,
 * and to move the top card to the bottom of the deck.
 */
public class MyDeckSort {
	public static void sort (MyDeck d) {
		int N = d.size();
		for (int i = 0; i < N; i++) {
			for (int j = 1; j < N; j++) {
				if (d.topGreaterThanNext()) {
					d.swapTopTwo();
				}
				d.moveTopToBottom();
			}
			d.moveTopToBottom();
		}

		// You must sort the Deck using only the public methods of Deck.
		// You may not change the Deck class.
		// It should be sufficient to use the following:
		//   d.size ();
		//   d.moveTopToBottom (); 
		//   d.topGreaterThanNext ();
		//   d.swapTopTwo ();
		// While debugging, you will want to print intermediate results.
		// You can use d.toString() for that:
		//   StdOut.format ("i=%-3d %s\n", i, d.toString ());
	}

	private static double time;
	private static void countops (MyDeck d) {
		boolean print = false;
		if (print) StdOut.println (d.toString ());
		Stopwatch sw = new Stopwatch ();
		sort (d);
		time = sw.elapsedTime ();
		if (print) StdOut.println (d.toString ());
		d.isSorted ();
	}
	public static void main (String[] args) {
		int N = 10;
		MyDeck d = new MyDeck (N);
		countops (d);
		//System.exit (0); // Comment this out to do a doubling test!
		double prevOps = d.ops ();
		double prevTime = time;
		for (int i = 0; i < 11; i++) {
			N *= 2;
			d = new MyDeck (N);
			countops (d);
			StdOut.format ("Elapsed count f(%,13d): %,17d: %10.3f [%10.3f : %10.3f]\n", N, d.ops(), d.ops() / prevOps, time, time/prevTime);
			prevOps = d.ops ();
			prevTime = time;
		}
	}
}

/**
 * The Deck class has the following API:
 *
 * <pre>
 * MyDeck (int N)                 // create a randomized Deck of size N
 * int size ()                    // return the size of N
 * int ops ()                     // return the number of operations performed on this Deck
 * boolean topGreaterThanNext ()  // compare top two items
 * void swapTopTwo ()             // swap top two itens
 * void moveTopToBottom ()        // move top item to bottom
 * void isSorted ()               // check if isSorted (throws exception if not)
 * </pre>
 */
class MyDeck {
	private int N;
	private int top;
	private long ops;
	private int[] a;

	public long ops () {
		return ops;
	}
	public int size () {
		return N;
	}
	public MyDeck (int N) {
		this.N = N;
		this.top = 0;
		this.ops = 0;
		this.a = new int[N];
		for (int i = 0; i < N; i++)
			a[i] = i;
		StdRandom.shuffle (a);
	}
	public boolean topGreaterThanNext () {
		int i = a[top];
		int j = a[(top + 1) % N];
		ops += 2;
		return i > j;
	}
	public void swapTopTwo () {
		int i = a[top];
		int j = a[(top + 1) % N];
		a[top] = j;
		a[(top + 1) % N] = i;
		ops += 4;
	}
	public void moveTopToBottom () {
		top = (top + 1) % N;
		ops += 1;
	}
	public String toString() {
		return toStringUnshifted() + toStringShifted();
	}
	public String toStringUnshifted () {
		if (N==0) return "[]";
		StringBuilder b = new StringBuilder ();
		b.append ('[');
		int last = (top + N - 1) % N;
		for (int k = top; ; k = (k + 1) % N) {
			b.append (a[k]);
			if (k == last) return b.append (']').toString();
			b.append (' ');
		} 
	}
	public String toStringShifted () {
		if (N==0) return "()";
		StringBuilder b = new StringBuilder ();
		b.append ('(');
		int last = N-1;
		for (int k = 0; ; k = k + 1) {
			b.append((top==k) ? '^' : ' ');
			b.append (a[k]);
			if (k == last) return b.append (')').toString();
			b.append (' ');
		} 
	}
	public void isSorted () {
		boolean print = false;
		long theOps = ops; // don't count the operations require by isSorted
		for (int i = 1; i < N; i++) {
			if (print) StdOut.format ("i=%-3d %s\n", i, toString ());
			if (topGreaterThanNext ()) throw new Error ();
			moveTopToBottom ();
		}
		if (print) StdOut.format ("i=%-3d %s\n", N, toString ());
		moveTopToBottom ();
		if (print) StdOut.format ("i=%-3d %s\n", N + 1, toString ());
		ops = theOps;
	}
}
