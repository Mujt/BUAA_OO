
package elevator;

import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.PersonRequest;

import java.io.IOException;

public class InputRequest extends Thread {
    private RequestTray tray;
    private ElevatorInput elevatorInput;

    public InputRequest(RequestTray t,ElevatorInput elevatorInput) {
        tray = t;
        this.elevatorInput = elevatorInput;
    }

    @Override
    public void run() {
        //ElevatorInput elevatorInput = new ElevatorInput(System.in);
        int i = 0;
        while (true) {
            //System.out.println(true);
            PersonRequest request = null;
            i = i + 1;
            request = elevatorInput.nextPersonRequest();
            // when request == null
            // it means there are no more lines in stdin
            //System.out.println(request == null);
            if (request == null || request.equals("")) {
                tray.setIn();
                break;
            } else {
                MyPersonRequest temp = new MyPersonRequest(
                        request.getFromFloor(),
                        request.getToFloor(),request.getPersonId());
                tray.put(temp);
                if (i == 40) {
                    tray.put(null);
                    tray.setIn();
                    break;
                }
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
        try {
            elevatorInput.close();
        } catch (IOException e) { System.exit(0); }
    }
}
