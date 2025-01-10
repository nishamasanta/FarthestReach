import java.util.*;

public class farthestreach {

    static class Cell {
        int row, col, steps;

        public Cell(int row, int col, int steps) {
            this.row = row;
            this.col = col;
            this.steps = steps;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input dimensions
        int n = sc.nextInt();
        int m = sc.nextInt();
        sc.nextLine();

        // Input grid
        char[][] grid = new char[n][m];
        int startRow = 0, startCol = 0;
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < m; j++) {
                grid[i][j] = line.charAt(2 * j);
                if (grid[i][j] == 'S') {
                    startRow = i;
                    startCol = j;
                }
            }
        }

        // Input max moves
        int k = sc.nextInt();

        // Find farthest cells
        List<int[]> result = findFarthestCells(grid, n, m, startRow, startCol, k);

        // Print results
        for (int[] cell : result) {
            System.out.println(cell[0] + " " + cell[1]);
        }
    }

    private static List<int[]> findFarthestCells(char[][] grid, int n, int m, int startRow, int startCol, int k) {
        boolean[][] visited = new boolean[n][m];
        Queue<Cell> queue = new LinkedList<>();
        queue.add(new Cell(startRow, startCol, 0));
        visited[startRow][startCol] = true;

        int maxDistance = 0;
        List<int[]> farthestCells = new ArrayList<>();

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            int row = current.row;
            int col = current.col;
            int steps = current.steps;

            // Apply gravity
            while (row + 1 < n && grid[row + 1][col] == 'E') {
                row++;
                steps++;
                if (steps > k) break;
            }

            // Skip if out of moves or invalid cell
            if (steps > k || grid[row][col] == 'B' || row == n - 1) continue;

            // Calculate Manhattan distance
            int distance = Math.abs(startRow - row) + Math.abs(startCol - col);

            // Update farthest cells
            if (distance > maxDistance) {
                maxDistance = distance;
                farthestCells.clear();
            }
            if (distance == maxDistance) {
                farthestCells.add(new int[]{row, col});
            }

            // Explore neighbors
            int[] dRow = {0, 0, -1}; // Left, Right, Up
            int[] dCol = {-1, 1, 0};

            for (int i = 0; i < 3; i++) {
                int newRow = row + dRow[i];
                int newCol = col + dCol[i];

                if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < m && !visited[newRow][newCol]) {
                    if (grid[newRow][newCol] != 'B') {
                        visited[newRow][newCol] = true;
                        queue.add(new Cell(newRow, newCol, steps + 1));
                    }
                }
            }
        }

        return farthestCells;
    }
}

