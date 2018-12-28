package benchmark;

public class IntPair {
    private int f, s;

    public IntPair(int f, int s) {
        this.f = f;
        this.s = s;
    }

    public IntPair(IntPair p) {
        this.f = p.f;
        this.s = p.s;
    }

    public int getF() {
        return f;
    }

    public int getS() {
        return s;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setS(int s) {
        this.s = s;
    }

    public void increaseS(int by) {
        this.s += by;
    }

    @Override
    public String toString() {
        return "[" + f + ", " + s + "]";
    }
}
