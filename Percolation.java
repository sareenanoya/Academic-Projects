package algs15.perc;
import algs15.*;

// Uncomment the import statements above.

// You can test this using InteractivePercolationVisualizer and PercolationVisualizer
// All methods should make at most a constant number of calls to a UF data structure.
// You can use more than one UF data structure.
// You can assume that N>1
//
// Note that you can print out a UF structure using its toString method.
// This may be useful for debugging.
public class Percolation {
	int N;
	boolean[] open;
	private UF uf;
	public Percolation(int N) {
		if (N < 2) throw new IllegalArgumentException();
		this.N = N;
		this.open = new boolean[N * N];
		this.uf = new WeightedUF(N * N + 2);
		for (int i = 0; i < N * N; i++) {
			this.open[i] = false;
		}
	}

	// open site (row i, column j) if it is not already
	public void open(int i, int j) {
		int x = i * N + j;
		if (!open[x]) {
			open[x] = true;
			int position = ((i * N + j) + 1);
			if (i == 0) {
				uf.union(0, position);    //connect w/ source
			}
			if (i > 0) {
				if (isOpen(i - 1, j)) {
					uf.union(position, position - N);    //connect w/ its top grid
				}
			}
			if (i == (N - 1)) {
				uf.union(N * N + 1, position);    //connect w/ sink
			}
			if (i < N - 1) {
				if (isOpen(i + 1, j)) {
					uf.union(position, position + N);    //connect w/ its bottom grid
				}
			}
			if (j > 0 && isOpen(i, j - 1)) {
				uf.union(position, position - 1);    //connecting w/ left grid
			}
			if (j < N - 1 && isOpen(i, j + 1)) {
				uf.union(position, position + 1);    //connect
			}
		}
	}
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		return open[i * N + j];
	}
	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		return uf.connected(0, i * N + j + 1);
	}
	// does the system percolate?
	public boolean percolates() {
		return uf.connected(0, N * N + 1);
	}
}