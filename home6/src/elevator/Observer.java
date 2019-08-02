package elevator;

import com.oocourse.elevator2.PersonRequest;

public class Observer extends Thread {
    private Elevator elevator;
    private RequestTray tray;

    public Observer(Elevator elevator,RequestTray tray) {
        this.elevator = elevator;
        this.tray = tray;
    }

    public void run() {
        while (true) {
            if(tray.retSize() == 1){
                PersonRequest request = tray.get(0);
                if(request == null){
                    elevator.setIn();
                    break;
                }
                tray.put(request);
            }
            if (tray.retSize() == 0 && tray.getIn() == false) {
                elevator.setIn();
                //System.out.println("ob end!");
                break;
            }
            for (int i = 0; i < tray.retSize(); i++) {
                if (elevator.getSt() == STate.WAIT) {
                    PersonRequest temp = tray.get(0);
                    elevator.setState(temp.getPersonId(),temp.getFromFloor());
                    elevator.setSt();
                    elevator.addRequest(temp);
                } else {
                    if (elevator.judgeAlong(tray.lookUp(i))) {
                        elevator.addRequest(tray.get(i));
                    }
                }
                // System.out.println("one time!");
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }
}
