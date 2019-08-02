package elevator;

import com.oocourse.TimableOutput;
import com.oocourse.elevator1.ElevatorInput;
import java.util.Date;

public class ElevatorMain {
    public static void main(String[] args) {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        TimableOutput.initStartTimestamp();
        long date = new Date().getTime();
        RequestTray tray = new RequestTray(date);
        Elevator elevator = new Elevator(date);
        InputRequest in = new InputRequest(tray,date,elevatorInput);
        ElevatorScheduler scheduler = new ElevatorScheduler(tray,elevator,date);
        scheduler.start();
        in.start();
    }
}
