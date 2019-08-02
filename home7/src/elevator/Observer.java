package elevator;

import java.util.ArrayList;

public class Observer extends Thread implements Runnable {
    private Elevator[] elevator;
    private RequestTray tray;
    private static int pos = 0;

    public Observer(Elevator[] elevator,RequestTray tray) {
        this.elevator = elevator;
        this.tray = tray;
    }

    /*find the best elevator*/
    public ArrayList getChoice(MyPersonRequest request) {
        ArrayList eleList = new ArrayList();
        for (int i = 0;i < elevator.length;i++) {
            if (elevator[i].judgeAlong(request)
                    && elevator[i].judgeDockFloor(request.getFromFloor())
                    && elevator[i].judgeDockFloor(request.getToFloor())) {
                eleList.add(i); }
        }
        if (eleList.size() == 0) {
            for (int i = 0;i < elevator.length;i++) {
                if (elevator[i].getSt() == STate.WAIT
                        && elevator[i].judgeDockFloor(request.getFromFloor())
                        && elevator[i].judgeDockFloor(request.getToFloor())) {
                    eleList.add(i); }
            } }
        return eleList;
    }

    public boolean theSame(MyPersonRequest request1,MyPersonRequest request2) {
        if (request1.getFromFloor() == request2.getFromFloor()) {
            if ((request1.getFromFloor() - request1.getToFloor())
                    * (request2.getFromFloor() - request2.getToFloor()) > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean judge() {
        boolean judge = true;
        for (int i = 0;i < tray.retSize();i++) {
            if (tray.lookUp(i) != null) {
                judge = false;
                break;
            }
        }
        return judge && tray.retSize1() == 0
                && tray.getIn() == false;
    }

    public ArrayList<MyPersonRequest> insertSame(int pos,int ele) {
        ArrayList<MyPersonRequest> requests = new ArrayList();
        MyPersonRequest temp1 = tray.lookUp(pos);
        requests.add(temp1);
        //System.out.println(tray.retSize());
        for (int i = pos + 1;i < tray.retSize();) {
            MyPersonRequest temp2 = tray.lookUp(i);
            //System.out.println(temp2.toString());
            if (temp2 == null) {
                break;
            }
            if (theSame(temp1,temp2)
                    && elevator[ele].judgeDockFloor(temp2.getToFloor())) {
                requests.add(temp2);
                tray.get(i);
            } else {
                i++;
            }
        }
        return requests;
    }

    public void run() {
        while (true) {
            //System.out.println(tray.retSize() + " " +tray.retSize1());
            //System.out.println(elevator[0].getSt() + " "
            // +elevator[1].getSt() + " " + elevator[2].getSt());
            
            //System.out.println(false);
            if (tray.retSize() == 0 && tray.retSize1() == 0
                    && tray.getIn() == false) {
                break; }
            while (tray.retSize() == 0 && tray.retSize1() == 0) {
                //System.out.println("ob wait");
                tray.waitFor();
            }
            if (judge()) {
                break; }
            if (tray.retSize() == 1) {
                //tray.str();
                MyPersonRequest request = tray.get(0);
                if (request == null && tray.getIn() == false
                        && tray.retSize1() == 0) {
                    break; }
                tray.put(request); }
            if (pos >= tray.retSize()) {
                pos = 0; }
            //System.out.println(pos + " " + tray.retSize());
            for (;pos < tray.retSize();) {
                MyPersonRequest request = tray.lookUp(pos);
                //System.out.println(request);
                if (request == null) {
                    pos++;
                    break; }
                ArrayList temp = getChoice(request);
                if (!temp.isEmpty()) {
                    for (int j = 0;j < temp.size();j++) {
                        if (!elevator[(int) temp.get(j)].isFull()) {
                            ArrayList<MyPersonRequest> same
                                    = insertSame(pos,(int)temp.get(j));
                            //System.out.println(same.size());
                            elevator[(int) temp.get(j)].addRequest(same);
                            tray.get(pos);
                            //System.out.print(tray.retSize());
                            break; }
                    } } else {
                    pos++;
                }
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                System.exit(0);
            }
            //if(elevator[0].getSt()==STate.WAIT &&
            // elevator[1].getSt()==STate.WAIT
            // && elevator[2].getSt()==STate.WAIT ) {
            //tray.str();
            //System.exit(0);
            //}
        }
        System.out.println("ob end!");
        for (int i = 0;i < elevator.length;i++) {
            elevator[i].setIn();
            elevator[i].addRequest(null);
        }
    }
}
