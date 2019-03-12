import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double BOUND = 1.96;
    private double[] fractions;
    private final int experimentsCount;

    public PercolationStats(int n, int trials) { // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Given n <= 0 || trials <= 0");
        }
        this.fractions = new double[trials];
        this.run(n, trials);
        this.experimentsCount = trials;
    }

    private void run(int n, int trials) {
        for (int i = 0; i < trials; i++) {
            Percolation  pr = new Percolation(n);
            while (!pr.percolates()) {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);
                if (!pr.isOpen(randomRow, randomCol))
                {
                    pr.open(randomRow, randomCol);
                }

            }
            double fraction = (double) pr.numberOfOpenSites() / (n * n);
            fractions[i] = fraction;
        }
    }

    public double mean() { // sample mean of percolation threshold
        return StdStats.mean(fractions);
    }

    public double stddev() { // sample standard deviation of percolation threshold
        return StdStats.stddev(fractions);
    }

    public double confidenceLo() { // low  endpoint of 95% confidence interval
        return mean() - ((BOUND * stddev()) / Math.sqrt(experimentsCount));
    }

    public double confidenceHi() {  // high endpoint of 95% confidence interval
        return mean() + ((BOUND * stddev()) / Math.sqrt(experimentsCount));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }

}
