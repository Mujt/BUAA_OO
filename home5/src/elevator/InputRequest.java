package elevator;

import com.oocourse.elevator1.ElevatorInput;
import com.oocourse.elevator1.PersonRequest;

import java.io.IOException;

public class InputRequest extends Thread {
    private RequestTray tray;
    private long date;
    private ElevatorInput elevatorInput;

    public InputRequest(RequestTray t,long begin,ElevatorInput elevatorInput) {
        tray = t;
        date = begin;
        this.elevatorInput = elevatorInput;
    }

    public void run() {
        //ElevatorInput elevatorInput = new ElevatorInput(System.in);
        int i = 0;
        while (true) {
            PersonRequest request = null;
            request = elevatorInput.nextPersonRequest();
            // when request == null
            // it means there are no more lines in stdin
            //System.out.println(request == null);
            if (request == null || request.equals("")) {
                tray.setIn();
                tray.put(null);
                break;
            } else {
                tray.put(request);
            }
            i++;
        }
        try {
            elevatorInput.close();
        } catch (IOException e) { System.exit(0); }
    }
}
