import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;


public class Percolation {

    public WeightedQuickUnionUF uf;
    public ArrayList<Integer> grid_status;
    public int N;
    private int virtualTop;
    private int virtualBottom;

    public Percolation(int N) {
        if (N <= 0){
            throw new IllegalArgumentException("N must be greater than zero!");
        }
        this.N = N;
        grid_status = new ArrayList<>(N*N);
        for (int i=0; i< N*N ; i++){
            grid_status.add(0);
        }
        uf = new WeightedQuickUnionUF(N * N + 2);
        virtualTop = N * N;
        virtualBottom = N * N + 1;
    }

    public void open(int row, int col) {
        validateIndices(row, col);
        int pos = xyTo1D(row, col);

        if (grid_status.get(pos) == 1) return;
        this.grid_status.set(pos, 1);

        if (row == 0){
            uf.union(pos, virtualTop);
        }
        if (row == N - 1) {
            uf.union(pos, virtualBottom);
        }
        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };

        for (int[] dir : directions){
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (isValid(newRow, newCol) && isOpen(newRow, newCol)){
                uf.union(pos, xyTo1D(newRow, newCol));
            }

        }
    }

    public boolean isOpen(int row, int col) {
        return grid_status.get(xyTo1D(row, col)) == 1;
    }

    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        if (!isOpen(row, col)) return false;
        return uf.connected(xyTo1D(row, col), virtualTop);
    }

    public int numberOfOpenSites() {
        int sum = grid_status.stream().mapToInt(Integer::intValue).sum();
        return sum;
    }

    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }

    public int xyTo1D(int x, int y){
        return x * N + y;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                int index = xyTo1D(row, col);
                sb.append(grid_status.get(index)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // Helper Function 1
    private boolean isValid(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    // Helper Function 2
    private void validateIndices(int row, int col){
        if (row < 0 || row >= this.N || col<0 || col >= this.N){
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    public static void main(String[] args){
        /*Percolation grid1 = new Percolation(3);
        grid1.open(0,2);
        grid1.open(1,2);
        System.out.println(grid1);
        System.out.println(grid1.uf.connected(grid1.xyTo1D(0,2), grid1.xyTo1D(1,2)));
        System.out.println(grid1.isFull(1, 2));*/
        Percolation p = new Percolation(1);
        System.out.println(p.percolates());
        p.open(0, 0);
        System.out.println(p.percolates());
    }}
