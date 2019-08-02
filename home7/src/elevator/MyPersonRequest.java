package elevator;

public class MyPersonRequest {
    private int from;
    private int to;
    private int id;

    public MyPersonRequest(int from,int to,int id) {
        this.from = from;
        this.to = to;
        this.id = id;
    }

    public int getFromFloor() {
        return from;
    }

    public int getToFloor() {
        return to;
    }

    public int getPersonId() {
        return id;
    }

    public String toString() {
        return "id:" + id + " " + from + " " + to;
    }
}
