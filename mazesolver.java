import java.util.*;

public class pa4 {
    public static class Solver {

        private int numberOfNodes;
        private List<Integer>[] neighborList;

        // Constructor to initialize the Solver with the number of nodes.
        public Solver(int numberOfNodes) {
            this.numberOfNodes = numberOfNodes;
            this.neighborList = (List<Integer>[]) new ArrayList[numberOfNodes];

            // Initialize adjacency lists
            for (int i = 0; i < numberOfNodes; i++) {
                neighborList[i] = new ArrayList<>();
            }
        }
        // get neighbors of a node.
        public Iterable<Integer> adj(int v) {

            return neighborList[v];
        }

        // add an edge between two nodes.
        public void placeEdge(int v, int w) {
            neighborList[v].add(w);
            neighborList[w].add(v);
        }


        // BFS algorithm
        public List<Integer> breadthFirstSearch(int start, int end) {
            boolean[] visited = new boolean[numberOfNodes];
            int[] prev = new int[numberOfNodes];

            for (int i = 0; i < numberOfNodes; i++) {
                prev[i] = -1;
            }

            Deque<Integer> queue = new ArrayDeque<>();
            queue.addLast(start);
            visited[start] = true;

            while (!queue.isEmpty()) {
                int v = queue.removeFirst();

                for (int w : adj(v)) {
                    if (!visited[w]) {
                        queue.addLast(w);
                        visited[w] = true;
                        prev[w] = v;

                        if (w == end) {
                            return buildPathBFS(prev, start, end);
                        }
                    }
                }
            }

            return null;
        }

        // Helper method to build the path from BFS result.
        private List<Integer> buildPathBFS(int[] prev, int start, int end) {
            List<Integer> path = new ArrayList<>();
            int v = end;

            while (v != start) {
                path.add(0, v);
                v = prev[v];
            }

            path.add(0, start);
            return path;
        }


        // DFS algorithm
        public List<Integer> depthFirstSearch(int start, int end) {
            Set<Integer> visited = new HashSet<>();
            List<Integer> path = new ArrayList<>();

            getPathForDepth(start, end, visited, path);

            return path;
        }

        // Helper method for DFS recursion.
        private boolean getPathForDepth(int startNode, int endNode, Set<Integer> seenNodes, List<Integer> route) {
            if (seenNodes.contains(startNode)) {
                return false;
            }

            seenNodes.add(startNode);
            route.add(startNode);

            if (startNode == endNode) {
                return true;
            }

            for (int adjacent : neighborList[startNode]) {
                if (!seenNodes.contains(adjacent) && getPathForDepth(adjacent, endNode, seenNodes, route)) {
                    return true;
                }
            }

            route.remove(route.size() - 1);
            return false;
        }

        // display the path as a maze layout.
        static void displayPath(List<Integer> route, int mazeRows, int mazeCols) {
            char[][] mazeLayout = new char[mazeRows][mazeCols];
            for (char[] row : mazeLayout) {
                Arrays.fill(row, '-');
            }

            for (int position : route) {
                int rowPosition = position / mazeCols;
                int colPosition = position % mazeCols;
                mazeLayout[rowPosition][colPosition] = 'X';
            }

            for (char[] row : mazeLayout) {
                for (char cell : row) {
                    System.out.print(cell + " ");
                }
                System.out.println();
            }
        }

        // display the path as grid coordinates.
        static void displayCoordinates(List<Integer> route, int gridColumns) {
            System.out.print("Grid Coordinates->");
            for (int point : route) {
                int rowIndex = point / gridColumns;
                int colIndex = point % gridColumns;
                System.out.print(String.format(" (%d, %d)", rowIndex, colIndex));
            }
            System.out.println();
        }


        public static void main(String[] args) {

            // 'H' and 'V' matrices
            int[][] H = {
                    {0, 0,0, 0, 1, 1, 0, 0, 1,1, 1, 1, 0, 0, 0},
                    {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1},
                    {1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0},
                    {0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0},
                    {0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0},
                    {0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0},
                    {1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
                    {0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0},
                    {1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0},
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0},
                    {0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1},
                    {0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1}
            };

            int[][] V = {
                    {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                    {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0},
                    {0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0},
                    {0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0},
                    {0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0},
                    {1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1},
                    {1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1},
                    {0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0},
                    {0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
                    {0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1},
                    {0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1},
                    {0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1},
                    {1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0},
                    {1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0},
                    {0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},};


            int numVertices = V.length * H[0].length;
            int start = 0;
            int end = numVertices - 1;

            Solver graph = new Solver(numVertices);
            int counter = 0;


            int row = 0;
            // Create horizontal edges based on the 'H' array.
            while (row < H.length) {
                int col = 0;
                while (col < H[row].length) {
                    int currentNode = row * H[row].length + col;
                    if (H[row][col] == 0) {
                        int nextNode = currentNode + H[row].length;
                        graph.placeEdge(currentNode, nextNode);
                    }
                    col++;
                }
                row++;
            }


            // Create vertical edges based on the 'V' array.
            counter = 0;
            int i = 0;
            while (i < V.length) {
                int j = 0;
                while (j < V[i].length) {
                    if (V[i][j] == 0) {
                        graph.placeEdge(counter, counter + 1);
                    }
                    counter++;
                    j++;
                }
                counter++;
                i++;
            }


            // display BFS result
            System.out.println("BFS path:");
            List<Integer> bfsPath = graph.breadthFirstSearch(start, end);
            if (bfsPath != null && !bfsPath.isEmpty()) {
                displayPath(bfsPath, 15, 15);
                System.out.println(bfsPath);
                displayCoordinates(bfsPath, 15);
            } else {
                System.out.println("Path not found in BFS.");
            }

            // display DFS result
            System.out.println("\nDFS path:");
            List<Integer> dfsPath = graph.depthFirstSearch(start, end);
            if (dfsPath != null && !dfsPath.isEmpty()) {
                displayPath(dfsPath, 15, 15);
                System.out.println(dfsPath);
                displayCoordinates(dfsPath, 15);
            } else {
                System.out.println("Path not found in DFS.");
            }
        }

    }

}