public class Pair {
    public int x;
    public int y;
    public Pair parent;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setParent(Pair parent) {
        this.parent = parent;
    }
}