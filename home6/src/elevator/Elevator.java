package elevator;

import com.oocourse.TimableOutput;
import com.oocourse.elevator2.PersonRequest;
import java.util.ArrayList;

public class Elevator extends Thread {
    private int nowId;
    private int nowFloor;
    private int targetFloor;
    private boolean used;
    private ElevatorTray elevatorIn;
    private ElevatorTray elevatorTray;
    private elevator.STate st;
    private ArrayList idList;

    public Elevator() {
        nowId = 0;
        nowFloor = 1;
        targetFloor = 1;
        elevatorIn = new ElevatorTray();
        elevatorTray = new ElevatorTray();
        st = elevator.STate.WAIT;
        idList = new ArrayList();
        used = true;
    }

    public elevator.STate getSt() {
        return st;
    }

    public void setSt() {
        st = STate.SERVE;
    }

    public void setState(int id,int floor) {
        this.nowId = id;
        targetFloor = floor;
        idList.add(id);
    }

    public void addRequest(PersonRequest request) {
        elevatorIn.insertRequest(request.getPersonId(),request.getFromFloor());
        elevatorTray.insertRequest(request.getPersonId(),request.getToFloor());
        /*code here*/
    }

    public boolean judgeAlong(PersonRequest request) {
        boolean judge = false;
        if (request == null) {
            return false;
        }
        if (targetFloor > nowFloor) {
            if (request.getFromFloor() >= nowFloor
                    && request.getToFloor() > request.getFromFloor()) {
                judge = true;
            }
        } else if (targetFloor < nowFloor) {
            if (request.getFromFloor() <= nowFloor
                    && request.getToFloor() < request.getFromFloor()) {
                judge = true;
            }
        } else {
            Request temp = elevatorTray.lookTo(nowId);
            if (request.getFromFloor() == nowFloor
                    &&
                (temp.getTo() - nowFloor) * (request.getToFloor() - nowFloor)
                        > 0) {
                judge = true;
            }
        }
        return judge;
    }

    public void upOne() {
        if(nowFloor == -1) {
            nowFloor = 1;
        }
        else {
            nowFloor = nowFloor + 1;
        }
        try {
            sleep(400);
        } catch (InterruptedException e) {
            System.exit(0);
        }
        TimableOutput.println(String.format("ARRIVE-%d",nowFloor));
    }

    public void downOne() {
        if(nowFloor == 1) {
            nowFloor = -1;
        } else {
            nowFloor = nowFloor - 1;
        }
        try {
            sleep(400);
        } catch (InterruptedException e) { System.exit(0); }
        TimableOutput.println(String.format("ARRIVE-%d",nowFloor));
    }

    public void openClose(boolean exp) {
        TimableOutput.println(String.format("OPEN-%d",nowFloor));
        Request request = null;
        if (exp) {
            request = elevatorIn.get(nowFloor);
            while (request != null) {
                TimableOutput.println(
                        String.format("IN-%d-%d",request.getId(),nowFloor));
                request = elevatorIn.get(nowFloor);
            }
        } else {
            request = elevatorTray.get(nowFloor);
            while (request != null) {
                TimableOutput.println(
                        String.format("OUT-%d-%d",request.getId(),nowFloor));
                request = elevatorTray.get(nowFloor);
            }
        }
        try {
            sleep(200);
        } catch (InterruptedException e) { System.exit(0); }
        try {
            sleep(200);
        } catch (InterruptedException e) { System.exit(0); }
        TimableOutput.println(String.format("CLOSE-%d",nowFloor));
    }

    public boolean find(int id) {
        for (int i = 0;i < idList.size();i++) {
            int temp = (int)idList.get(i);
            if (temp == id) {
                return true;
            }
        }
        return false;
    }

    public void setIn() { used = false; }

    public boolean judgeEnd() {
        if (elevatorIn.isEmpty() && elevatorTray.isEmpty() && used == false) {
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            if (judgeEnd()) {
                break; }
            while (nowFloor == targetFloor && st == STate.WAIT) {
                try {
                    sleep(10);
                } catch (InterruptedException e) { System.exit(0); }
            }
            if (nowFloor == targetFloor) {
                openClose(true);
                targetFloor = elevatorTray.lookTo(nowId).getTo(); }
            while (nowFloor != targetFloor) {
                if (nowFloor > targetFloor) {
                    downOne();
                } else if (nowFloor < targetFloor) {
                    upOne(); }
                if (elevatorIn.isFind(nowFloor)) {
                    while (elevatorIn.isFind(nowFloor)) {
                        Request request = elevatorIn.get(nowFloor);
                        ArrayList<Request> list = new ArrayList<Request>();
                        while (request != null) {
                            list.add(request);
                            idList.add(request.getId());
                            request = elevatorIn.get(nowFloor);
                        }
                        for(int i = 0;i < list.size();i++) {
                            Request tt = list.get(i);
                            elevatorIn.insertRequest(tt.getId(),tt.getTo());
                        }
                        Request temp = elevatorIn.get(nowFloor);
                        elevatorIn.insertRequest(temp.getId(),temp.getTo());
                        openClose(true);
                    }
                }
                //System.out.println(elevatorTray.isFind(nowFloor));
                //elevatorTray.str();
                if (elevatorTray.isFind(nowFloor)) {
                    //System.out.println();
                    //elevatorTray.str();
                    ArrayList<Request> list = new ArrayList<Request>();
                    while (elevatorTray.isFind(nowFloor)) {
                        Request temp = elevatorTray.get(nowFloor);
                        //System.out.println(find(temp.getId()) + " " +temp.getId() + idList);
                        if (find(temp.getId())) {
                           // System.out.println();
                            //elevatorTray.str();
                            elevatorTray.insertRequest(
                                    temp.getId(),temp.getTo());
                            //elevatorTray.str();
                            openClose(false);
                        } else {
                            list.add(temp);
                        }
                    }
                    for (int j = 0;j < list.size();j++) {
                        Request temp = list.get(j);
                        elevatorTray.insertRequest(temp.getId(),temp.getTo());
                    }
                }
                if (nowFloor == targetFloor) {
                    if (elevatorTray.isEmpty() && elevatorIn.isEmpty()) {
                        break; }
                    if (!elevatorIn.isEmpty()) {
                        Request request = elevatorIn.getZero();
                        nowId = request.getId();
                        targetFloor = request.getTo();
                        continue; }
                    if (!elevatorTray.isEmpty()) {
                        Request request = elevatorTray.getZero();
                        nowId = request.getId();
                        targetFloor = request.getTo();
                        continue; }
                }
            }
            st = STate.WAIT;
        }
    }
}
