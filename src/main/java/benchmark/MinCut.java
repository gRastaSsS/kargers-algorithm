package benchmark;

public class MinCut {
    // n^4 * log(n)
    public static int minCut(Graph graph) {
        int ans = Integer.MAX_VALUE;

        int iterations = countIterations(graph);

        for (int i = 0; i < iterations; i++) {
            int currCut = getCut(graph);

            if (currCut < ans) {
                ans = currCut;
            }
        }

        return ans;
    }

    // n^2 * log(n)
    private static int countIterations(Graph graph) {
        int vertices = graph.vertexNumber();
        return vertices * (vertices - 1) * (int) Math.log(vertices);
    }

    // n^2
    private static int getCut(Graph graph) {
        Graph g = new Graph(graph);

        while (g.vertexNumber() > 2) {
            g.contract();
        }

        return g.edgeNumber();
    }
}
