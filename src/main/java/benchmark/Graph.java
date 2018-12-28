package benchmark;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.Random;

public class Graph {
    private static final Random random = new Random();

    private Int2ObjectMap<VertexMeta> adjList;
    private int edges;

    public Graph(Int2ObjectMap<VertexMeta> adjList) {
        this.adjList = adjList;

        for (VertexMeta v : this.adjList.values()) {
            this.edges += v.edges;
        }

        this.edges /= 2;
    }

    //n*n
    public Graph(Graph o) {
        this.edges = o.edges;
        this.adjList = new Int2ObjectOpenHashMap<>(o.adjList.size());

        for (Int2ObjectMap.Entry<VertexMeta> e : o.adjList.int2ObjectEntrySet()) {
            this.adjList.put(e.getIntKey(), new VertexMeta(e.getValue()));
        }
    }

    @Override
    public String toString() {
        return adjList.toString();
    }

    public int vertexNumber() {
        return adjList.size();
    }

    public int edgeNumber() {
        return edges;
    }

    //n
    public void contract() {
        IntPair removed = pickRandomEdge();

        int first = removed.getF();
        int second = removed.getS();

        VertexMeta cToF = adjList.get(first);
        VertexMeta cToS = adjList.remove(second);

        for (IntPair v : cToS.adjVertices) {
            int edges = v.getS();

            if (edges != 0 && v.getF() != first) {
                VertexMeta cToV = adjList.get(v.getF());

                cToF.addEdges(v.getF(), edges);
                cToV.addEdges(first, edges);
                cToV.addEdges(second, -edges);
            }
        }

        edges -= cToF.getEdges(second);
        cToF.setEdges(second, 0);
    }

    //n * 2
    private IntPair pickRandomEdge() {
        int index = random.nextInt(edges * 2);

        for (Int2ObjectMap.Entry<VertexMeta> e : adjList.int2ObjectEntrySet()) {
            if (index - e.getValue().edges < 0) {
                for (IntPair i : e.getValue().adjVertices) {
                    if (index - i.getS() < 0) {
                        return new IntPair(e.getIntKey(), i.getF());
                    }

                    else index -= i.getS();
                }
            }

            else index -= e.getValue().edges;
        }

        throw new AssertionError();
    }
}
