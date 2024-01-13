import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Ass3 {

    public static void bfs(int v, int [][] e) {
        int [] q = new int[100];
        int front = 0, rear = 0;
        boolean [] visited = new boolean[100];
        q[rear++] = v;
        visited[v] = true;
        while (front < rear) {
            int t = q[front++];
            System.out.print(t + " ");
            for (int i = 0; i < e.length; i++) {
                if (e[i][0] == t && !visited[e[i][1]]) {
                    q[rear++] = e[i][1];
                    visited[e[i][1]] = true;
                }
            }
        }
        System.out.println();
    }

    public static void dfs(int v, int [][] e) {
        boolean [] visited = new boolean[100];
        dfs(v, e, visited);
        System.out.println();
    }
    
    public static void dfs(int v, int [][] e, boolean [] visited) {
        visited[v] = true;
        System.out.print(v + " ");
        for (int i = 0; i < e.length; i++) {
            if (e[i][0] == v && !visited[e[i][1]]) {
                dfs(e[i][1], e, visited);
            }
        }
    }

    public static void findCycles(int[][] e) {
        boolean[] visited = new boolean[100];
        boolean[] inStack = new boolean[100];
        List<Integer> currentPath = new LinkedList<>();

        for (int i = 0; i < e.length; i++) {
            if (!visited[e[i][0]]) {
                dfsWithCycleDetection(e[i][0], e, visited, inStack, currentPath);
            }
        }
    }

    public static void dfsWithCycleDetection(int v, int[][] e, boolean[] visited, boolean[] inStack, List<Integer> currentPath) {
        visited[v] = true;
        inStack[v] = true;
        currentPath.add(v);

        for (int i = 0; i < e.length; i++) {
            if (e[i][0] == v) {
                int neighbor = e[i][1];

                if (!visited[neighbor]) {
                    dfsWithCycleDetection(neighbor, e, visited, inStack, currentPath);
                } else if (inStack[neighbor]) {
                    printCycle(currentPath, neighbor);
                }
            }
        }

        inStack[v] = false;
        currentPath.remove(currentPath.size() - 1);
    }
    
    
    
    public static void printCycle(List<Integer> currentPath, int endNode) {
        System.out.print("Cycle: ");
        int startIndex = currentPath.indexOf(endNode);
        for (int i = startIndex; i < currentPath.size(); i++) {
            System.out.print(currentPath.get(i) + " ");
        }
        System.out.print(currentPath.get(startIndex) + " ");
        System.out.println();
    }
    public static boolean isBipartite(int[][] e, int[] color) {
        Arrays.fill(color, -1);

        for (int i = 0; i < e.length; i++) {
            if (color[i] == -1) {
                if (!bfsForBipartiteCheck(i, e, color)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean bfsForBipartiteCheck(int startNode, int[][] e, int[] color) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);
        color[startNode] = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int i = 0; i < e.length; i++) {
                if (e[i][0] == current) {
                    int neighbor = e[i][1];

                    if (neighbor >= color.length) {
                        continue;
                    }

                    if (color[neighbor] == -1) {
                        color[neighbor] = 1 - color[current];
                        queue.add(neighbor);
                    } else if (color[neighbor] == color[current]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void printTree(int[][] e) {
        System.out.println("Tree Structure:");

        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < e.length; i++) {
            int from = e[i][0];
            int to = e[i][1];

            while (adjacencyList.size() <= from) {
                adjacencyList.add(new ArrayList<>());
            }

            adjacencyList.get(from).add(to);
        }

        for (int i = 1; i < adjacencyList.size(); i++) {
            System.out.print("Vertex " + i + " -> ");
            List<Integer> neighbors = adjacencyList.get(i);
            for (int j = 0; j < neighbors.size(); j++) {
                System.out.print(neighbors.get(j));
                if (j < neighbors.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    

    public static void doEverything(int [] v, int [][] e){
        System.out.println("DFS:");
        dfs(1, e);
        System.out.println("BFS:");
        bfs(1, e);
        System.out.println("Cycles:");
        findCycles(e);
        int[] color = new int[v.length];
        boolean isBipartite = isBipartite(e, color);

        if (isBipartite) {
            System.out.println("The graph is bipartite.");
        } else {
            System.out.println("The graph is not bipartite.");
        }
        printTree(e);
    }

    public static void main(String[] args) throws Exception {
        int [] v = {1,2,3,4};
        int [][] e = {{1, 3},{1, 4}, {2, 1}, {2, 3}, {3, 4}, {4, 1}, {4, 2}};
        doEverything(v, e);
    }
}
