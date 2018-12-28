package benchmark;

import java.util.Arrays;

public class VertexMeta {
    public IntPair[] adjVertices;
    public int vertex;
    public int edges;

    @Override
    public String toString() {
        return Arrays.toString(adjVertices);
    }

    public void addEdges(int toVertex, int numOfEdges) {
        this.adjVertices[toVertex].increaseS(numOfEdges);
        this.edges += numOfEdges;
    }

    public void setEdges(int toVertex, int numOfEdges) {
        this.edges -= (this.adjVertices[toVertex].getS() - numOfEdges);
        this.adjVertices[toVertex].setS(numOfEdges);
    }

    public int getEdges(int toVertex) {
        return this.adjVertices[toVertex].getS();
    }

    public boolean containsEdge(int toVertex) {
        return this.adjVertices[toVertex].getS() != 0;
    }

    public VertexMeta(int vertex, int vertices) {
        this.vertex = vertex;
        this.adjVertices = new IntPair[vertices];

        for (int i = 0; i < vertices; i++) {
            this.adjVertices[i] = new IntPair(i, 0);
        }
    }

    public VertexMeta(VertexMeta o) {
        this.vertex = o.vertex;
        this.edges = o.edges;
        this.adjVertices = new IntPair[o.adjVertices.length];

        for (int i = 0; i < o.adjVertices.length; i++) {
            this.adjVertices[i] = new IntPair(o.adjVertices[i]);
        }
    }
}
