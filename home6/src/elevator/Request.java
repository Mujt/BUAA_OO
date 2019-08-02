package elevator;

public class Request {
    private int id;
    private int toFloor;

    public Request(int id,int toFloor) {
        this.id = id;
        this.toFloor = toFloor;
    }

    public int  getId() {
        return id;
    }

    public int getTo() {
        return toFloor;
    }
}
