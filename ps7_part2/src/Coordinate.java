public class Coordinate {
    public int x;
    public int y;
    public Coordinate parent;
    public int powerUsed;
    public Room room;
    public int step;
    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coordinate(int x, int y, Room room, int powerUsed) {
        this.x = x;
        this.y = y;
        this.room = room;
        this.powerUsed = powerUsed;
    }
    public void setParent(Coordinate parent) {
        this.parent = parent;
    }
}