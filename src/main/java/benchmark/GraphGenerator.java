package benchmark;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class GraphGenerator {
    private static final Random random = new Random();

    public static Graph generate(int verticesN, int edgesN) {
        if (verticesN > edgesN) throw new RuntimeException();

        Int2ObjectMap<VertexMeta> adjList = new Int2ObjectOpenHashMap<>();
        int edges = 0;

        adjList.put(0, new VertexMeta(0, verticesN));

        for (int i = 1; i < verticesN; i++) {
            int to = random.nextInt(adjList.size());
            adjList.put(i, new VertexMeta(i, verticesN));
            adjList.get(i).addEdges(to, 1);
            adjList.get(to).addEdges(i, 1);
            edges++;
        }

        while (edges < edgesN) {
            int from, to;

            do {
                from = random.nextInt(verticesN);
                to = random.nextInt(verticesN);

            } while (from == to || adjList.get(from).containsEdge(to));

            adjList.get(from).addEdges(to, 1);
            adjList.get(to).addEdges(from, 1);
            edges++;
        }

        return new Graph(adjList);
    }

    public static Graph getFromFile() {
        InputStream stream = MinCut.class.getResourceAsStream("/test_graph.txt");

        Int2ObjectMap<VertexMeta> adjList = new Int2ObjectOpenHashMap<>();

        try (Scanner sc = new Scanner(stream)) {
            int vertices = Integer.parseInt(sc.nextLine());

            while (sc.hasNextLine()) {
                String[] tr = sc.nextLine().split(" ");

                int from = Integer.parseInt(tr[0]);

                VertexMeta meta = new VertexMeta(from, vertices);

                for (int i = 1; i < tr.length; i++) {
                    meta.addEdges(Integer.parseInt(tr[i]), 1);
                }

                adjList.put(from, meta);
            }
        }

        return new Graph(adjList);
    }
}
