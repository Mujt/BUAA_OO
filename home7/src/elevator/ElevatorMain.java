package elevator;

import com.oocourse.TimableOutput;
import com.oocourse.elevator3.ElevatorInput;

import java.util.ArrayList;

public class ElevatorMain {
    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();
        int[] dockFloorA = {-3,-2,-1,1,15,16,17,18,19,20};
        int[] dockFloorB = {-2,-1,1,2,4,5,6,7,8,9,10,11,12,13,14,15};
        int[] dockFloorC = { 1,3,5,7,9,11,13,15};
        ArrayList<int[]> dockList = new ArrayList<>();
        dockList.add(dockFloorA);
        dockList.add(dockFloorB);
        dockList.add(dockFloorC);
        RequestTray tray = new RequestTray(dockList);
        Elevator[] elevator = new Elevator[3];
        elevator[0] = new Elevator("A",dockFloorA,0.4,6,tray);
        elevator[1] = new Elevator("B",dockFloorB,0.5,8,tray);
        elevator[2] = new Elevator("C",dockFloorC,0.6,7,tray);
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        InputRequest input = new InputRequest(tray,elevatorInput);
        Observer ob = new Observer(elevator,tray);
        input.start();
        ob.start();
        elevator[0].start();
        elevator[1].start();
        elevator[2].start();
    }
}

