package elevator;

import com.oocourse.TimableOutput;
import java.util.ArrayList;

public class Elevator extends Thread implements Runnable {
    private String eleId;
    private int nowId;
    private int nowFloor;
    private int targetFloor;
    private boolean used;
    private final int[] dockFloor;
    private double runSpeed;
    private int carrySize;
    //private static int runQuantity = 0;
    private ElevatorTray elevatorIn;
    private ElevatorTray elevatorTray;
    private elevator.STate st;
    private ArrayList idList;
    private RequestTray tray;

    public Elevator(
            String id,int[] dockFloor,double speed,int size,RequestTray tray) {
        this.eleId = id;
        this.dockFloor = dockFloor;
        nowId = 0;
        nowFloor = 1;
        targetFloor = 1;
        elevatorIn = new ElevatorTray(this);
        elevatorTray = new ElevatorTray(this);
        st = elevator.STate.WAIT;
        idList = new ArrayList();
        used = true;
        this.runSpeed = speed;
        this.carrySize = size;
        this.tray = tray;
    }

    public boolean isFull() {
        return carrySize == idList.size();
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

    public void addRequest(ArrayList<MyPersonRequest> requestList) {
        if (requestList == null) {
            elevatorIn.insertRequest(null,0);
            elevatorTray.insertRequest(null,0);
            return;
        }
        for (int i = 0;i < requestList.size();i++) {
            MyPersonRequest request = requestList.get(i);
            elevatorIn.insertRequest(
                    request,1);
            elevatorTray.insertRequest(
                    request,2);
            //System.out.println(i);
            //elevatorIn.str();
        }

        /*code here*/
    }

    public boolean judgeAlong(MyPersonRequest request) {
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
            Request temp = null;
            temp = elevatorTray.lookFirstNot(nowFloor);
            if (idList.isEmpty()) {
                temp = elevatorTray.lookTo((int)idList.get(0)); }
            if (temp == null) {
                return true;
            }
            else {
                targetFloor = temp.getTo();
                judge = (request.getFromFloor() - request.getToFloor())
                        * (nowFloor - targetFloor) > 0;
            }
        }
        return judge;
    }

    public void upOne() {
        //runQuantity++;
        if (nowFloor == -1) {
            nowFloor = 1;
        }
        else {
            nowFloor = nowFloor + 1;
        }
        try {
            sleep((long)(runSpeed * 1000));
        } catch (InterruptedException e) {
            System.exit(0);
        }
        TimableOutput.println(String.format("ARRIVE-%d-%s",nowFloor,eleId));
    }

    public void downOne() {
        //runQuantity++;
        if (nowFloor == 1) {
            nowFloor = -1;
        } else {
            nowFloor = nowFloor - 1;
        }
        try {
            sleep((long)(runSpeed * 1000));
        } catch (InterruptedException e) {
            System.exit(0);
        }
        TimableOutput.println(String.format("ARRIVE-%d-%s",nowFloor,eleId));
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

    public void removeId(int id) {
        for (int i = 0;i < idList.size();i++) {
            int temp = (int)idList.get(i);
            if (temp == id) {
                idList.remove(i);
            }
        }
    }

    public void setIn() { used = false; }

    public boolean judgeEnd() {
        if (elevatorIn.getZero() == null &&
                elevatorTray.getZero() == null && used == false) {
            return true;
        }
        return false;
    }

    public boolean judgeDockFloor(int floor) {
        for (int i = 0;i < dockFloor.length;i++) {
            if (dockFloor[i] == floor) {
                return true;
            }
        }
        return false;
    }

    public boolean judgeOut() {
        if (elevatorTray.isEmpty()) {
            return false; }
        Request request = null;
        if (elevatorTray.isFind(nowFloor)) {
            request = elevatorTray.look(nowFloor);
            //System.out.println(request.toString());
            while (request != null) {
                if (find(request.getId())) {
                    elevatorTray.setPos();
                    return true; }
                request = elevatorTray.look(nowFloor);
            }
        }
        elevatorTray.setPos();
        return false;
    }

    public void openClose() {
        //System.out.println(judgeOut());
        //elevatorTray.str();
        //System.out.println("openclose " + eleId);
        //System.out.println(judgeOut() + " " +nowFloor);
        //for (int i = 0;i < idList.size();i++) {
        //    System.out.print(idList.get(i) + " ");
        //}System.out.println();
        elevatorIn.str();
        elevatorTray.str();
        if ((elevatorIn.isFind(nowFloor) && !isFull()) || judgeOut()) {
            TimableOutput.println(String.format("OPEN-%d-%s",nowFloor,eleId));
            Request request = null;
            //runQuantity++;
            if (elevatorIn.isFind(nowFloor)) {
                //elevatorIn.str();
                if (isFull()) {
                    request = null;
                } else {
                    request = elevatorIn.get(nowFloor);
                }
                while (request != null && !isFull()) {
                    TimableOutput.println(
                            String.format(
                                    "IN-%d-%d-%s",
                                    request.getId(),nowFloor,eleId));
                    idList.add(request.getId());
                    if (isFull()) {
                        break; }
                    request = elevatorIn.get(nowFloor);
                }
            }
            //elevatorIn.str();
            //elevatorTray.str();
            if (elevatorTray.isFind(nowFloor)) {
                request = elevatorTray.look(nowFloor);
                ArrayList pos = new ArrayList();
                while (request != null) {
                    if (find(request.getId())) {
                        TimableOutput.println(
                                String.format(
                                        "OUT-%d-%d-%s"
                                        , request.getId(), nowFloor, eleId));
                        tray.newList(request.getId());
                        pos.add(request.getId());
                        removeId(request.getId());
                    }
                    request = elevatorTray.look(nowFloor);
                }
                elevatorTray.removePos(pos);
                //System.out.println(true);
            }
            try {
                sleep(400);
            } catch (InterruptedException e) { System.exit(0); }
            TimableOutput.println(String.format("CLOSE-%d-%s",nowFloor,eleId));
        }
    }

    @Override
    public void run() {
        while (true) {
            if (judgeEnd()) {
                break; }
            //System.out.println(true);
            while (elevatorTray.isEmpty()
                    && elevatorIn.isEmpty() && st == STate.WAIT) {
                st = STate.WAIT;
                if (judgeEnd()) {
                    break;
                }
                //System.out.println("ele "+eleId +" wait");
                /*try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace(); }*/
                //System.out.println("ele "+eleId +" wait");
                elevatorIn.waitFor();
            }
            /*elevatorIn.str();
            System.out.println(eleId + " " + nowFloor);
            for(int i=0;i<idList.size();i++) {
                System.out.print(idList.get(i) + " ");
            }System.out.println();
             elevatorTray.str();*/

            if (judgeEnd()) {
                break;
            }
            st = STate.SERVE;
            openClose();
            System.out.println("ele:" + eleId + " " + idList.isEmpty());
            if (idList.isEmpty()) {
                Request temp = elevatorIn.getZero();
                if (temp != null) {
                    targetFloor = elevatorIn.getZero().getTo();
                }
                st = STate.SERVE;
            } else {
                //elevatorTray.str();
                targetFloor = elevatorTray.lookTo((int)idList.get(0)).getTo();
                st = STate.SERVE; }
            while (nowFloor != targetFloor) {
                if (nowFloor < targetFloor) {
                    upOne();
                } else {
                    downOne(); }
                openClose();
            }
            //System.out.println("A arrive at target!");
            if (elevatorTray.isEmpty()
                    && elevatorIn.isEmpty() && idList.isEmpty()) {
                st = STate.WAIT; }
        }
        //System.out.println("ele "+eleId+"end!");
    }
}


