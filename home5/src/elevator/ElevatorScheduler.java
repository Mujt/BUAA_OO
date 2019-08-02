package elevator;

import com.oocourse.TimableOutput;
import com.oocourse.elevator1.PersonRequest;

public class ElevatorScheduler extends Thread {
    private RequestTray tray;
    private Elevator elevator;
    private long date;

    public ElevatorScheduler(RequestTray t,Elevator ele,long begin) {
        tray = t;
        elevator = ele;
        date = begin;
    }

    public void openclose() {
        try {
            sleep((long)500);
        } catch (InterruptedException e) { System.exit(0); }
    }

    @Override
    public void run() {
        PersonRequest request = null;

        while (true) {
            request = tray.get();
            if (request == null && tray.getIn() == false) {
                break;
            }
            //System.out.println("sche once!"+request);
            try {
                sleep((long)Math.abs(request.getFromFloor()
                        - elevator.getNow()) * 500);
            // System.out.println(elevator.toRun(request.getToFloor()));
            } catch (InterruptedException e) { System.exit(0); }
            elevator.toRun(request.getFromFloor());
            TimableOutput.println(String.format("OPEN-%d",
                    request.getFromFloor()));
            TimableOutput.println(String.format("IN-%d-%d",
                    request.getPersonId(),request.getFromFloor()));
            openclose();
            TimableOutput.println(String.format("CLOSE-%d",
                    request.getFromFloor()));
            try {
                sleep((long)Math.abs(request.getToFloor()
                        - elevator.getNow()) * 500);
            } catch (InterruptedException e) { System.exit(0); }
            elevator.toRun(request.getToFloor());
            TimableOutput.println(String.format("OPEN-%d",
                    request.getToFloor()));
            TimableOutput.println(String.format("OUT-%d-%d",
                    request.getPersonId(),request.getToFloor()));
            openclose();
            TimableOutput.println(String.format("CLOSE-%d",
                    request.getToFloor()));
        }
    }
}
