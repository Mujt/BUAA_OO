package elevator;

import java.util.ArrayList;

public class ElevatorTray {
    private ArrayList<Request> eleRequest;

    public ElevatorTray() {
        eleRequest = new ArrayList<Request>();
    }

    public synchronized void insertRequest(int id,int toFloor) {
        int insert = 0;
        Request requestId = new Request(id,toFloor);
        eleRequest.add(requestId);
        notifyAll();
    }

    public synchronized Request get(int floor) {
        Request temp = null;
        for (int i = 0;i < eleRequest.size();i++) {
            if (eleRequest.get(i).getTo() == floor) {
                temp = eleRequest.get(i);
                eleRequest.remove(i);
                break;
            }
        }
        notifyAll();
        return temp;
    }

    public Request getZero() {
        Request temp = null;
        temp = eleRequest.get(0);
        //eleRequest.remove(0);
        //notifyAll();
        return temp;
    }

    public Request look(int floor) {
        Request temp = null;
        for (int i = 0;i < eleRequest.size();i++) {
            if (eleRequest.get(i).getTo() == floor) {
                temp = eleRequest.get(i);
                eleRequest.remove(i);
                break;
            }
        }
        return temp;
    }

    public Request lookTo(int id) {
        Request temp = null;
        for (int i = 0;i < eleRequest.size();i++) {
            temp = eleRequest.get(i);
        }
        return temp;
    }

    public void str(){
        for(int i = 0;i < eleRequest.size();i++){
            System.out.println(eleRequest.get(i).getId() + "" + eleRequest.get(i).getTo());
        }
    }

    public boolean isEmpty() {
        return eleRequest.isEmpty();
    }

    public boolean isFind(int floor) {
        for (int i = 0;i < eleRequest.size();i++) {
            //System.out.println(eleRequest.get(i).getTo());
            if (eleRequest.get(i).getTo() == floor) {
                return true;
            }
        }
        return false;
    }
}
