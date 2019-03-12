import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] opened;
    private final WeightedQuickUnionUF qf;
    private int numberOpenSites = 0;
    private static final int top = 0;
    private final int bottom;
    private final int size;

    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        if (n == 0) {
            throw new IllegalArgumentException("Illegal argument n = 0");
        }
        this.qf = new WeightedQuickUnionUF(n * n + 2);
        this.bottom = n * n + 1;
        this.size = n;
        this.opened = new boolean[n][n];
    }

    private void checkBounds(int row, int col) {
        if (row > size || col > size || row < 1 || col < 1) {
            throw new IllegalArgumentException(
                    String.format("Illegal Argument %s %s for size %s", row, col, this.size));
        }
    }

    private int getQUFindIndex(int row, int col) {
        return (row - 1) * this.size + col;
    }

    public void open(int row, int col) {
        this.checkBounds(row, col);
        if (!this.isOpen(row, col)) {
            this.numberOpenSites++;
            this.opened[row - 1][col - 1] = true;
            if (row == 1) {
                this.qf.union(this.getQUFindIndex(row, col), Percolation.top);
            }
            if (row == this.size) {
                this.qf.union(this.getQUFindIndex(row, col), this.bottom);
            }
            if (row > 1 && this.isOpen(row -1, col)) {
                this.qf.union(this.getQUFindIndex(row, col),  this.getQUFindIndex(row -1, col));
            }
            if (row < size && this.isOpen(row + 1, col)) {
                this.qf.union(this.getQUFindIndex(row, col),  this.getQUFindIndex(row + 1, col));
            }
            if (col > 1 && this.isOpen(row, col - 1)) {
                this.qf.union(this.getQUFindIndex(row, col),  this.getQUFindIndex(row, col - 1));
            }
            if (col < size && this.isOpen(row, col + 1)) {
                this.qf.union(this.getQUFindIndex(row, col),  this.getQUFindIndex(row, col + 1));
            }
        }
    }


    public boolean isOpen(int row, int col) {
        this.checkBounds(row, col);
        return this.opened[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        this.checkBounds(row, col);
        int index = this.getQUFindIndex(row, col);
        return this.qf.connected(Percolation.top, index);
    }

    public int numberOfOpenSites() {
        return this.numberOpenSites;
    }

    public boolean percolates() {
        return this.qf.connected(Percolation.top, this.bottom);
    }


    public static void main(String[] args) {
        int n = 5;
        Percolation percolationSimulation = new Percolation(n);
        System.out.println();
        do {
            int randomRow = StdRandom.uniform(1, n + 1);
            int randomCol = StdRandom.uniform(1, n + 1);
            System.out.println(String.format("Random number [%s][%s]", randomRow, randomCol));
            percolationSimulation.open(randomRow, randomCol);
        } while (!percolationSimulation.percolates());
    }

}


