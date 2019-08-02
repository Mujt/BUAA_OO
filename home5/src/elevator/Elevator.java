package elevator;

public class Elevator {
    private long beginTime;
    private int floorNow;

    public Elevator(long begin) {
        beginTime = begin;
        floorNow = 1;
    }

    public int getNow() {
        return floorNow;
    }

    public void toRun(int floor) {
        floorNow = floor;
    }
}
