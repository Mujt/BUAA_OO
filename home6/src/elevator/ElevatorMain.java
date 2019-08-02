package elevator;

import com.oocourse.TimableOutput;
import com.oocourse.elevator2.ElevatorInput;

public class ElevatorMain {
    public static void main(String[] args) {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        TimableOutput.initStartTimestamp();
        RequestTray tray = new RequestTray();
        Elevator elevator = new Elevator();
        InputRequest input = new InputRequest(tray,elevatorInput);
        Observer ob = new Observer(elevator,tray);
        input.start();
        ob.start();
        elevator.start();
    }
}
