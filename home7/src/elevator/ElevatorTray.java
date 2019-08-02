package elevator;

import java.util.ArrayList;

public class ElevatorTray {
    private ArrayList<Request> eleRequest;
    private static int pos = 0;
    private Elevator ele;

    public ElevatorTray(Elevator ele) {
        eleRequest = new ArrayList<Request>();
        this.ele = ele;
    }

    public synchronized void insertRequest(MyPersonRequest request,int i) {
        if (request == null) {
            eleRequest.add(null);
            notifyAll();
            return;
        }
        int id = request.getPersonId();
        int toFloor = 0;
        if (i == 1) {
            toFloor = request.getFromFloor();
        } else if (i == 2) {
            toFloor = request.getToFloor();
        }
        Request requestId = new Request(id,toFloor);
        eleRequest.add(requestId);
        notifyAll();
    }
    
    public synchronized void waitFor() {
        while (eleRequest.isEmpty()) {
            try {
                wait();
                if (ele.judgeEnd()) {
                    notifyAll();
                    return;
                }
            } catch (InterruptedException e) {
                notifyAll();
                System.exit(0);
            }
        }
        notifyAll();
    }
    
    public synchronized Request get(int floor) {
       
        Request temp = null;
        for (int i = 0;i < eleRequest.size();i++) {
            if (eleRequest.get(i) == null) {
                break; }
            if (eleRequest.get(i).getTo() == floor) {
                temp = eleRequest.get(i);
                eleRequest.remove(i);
                break; }
        }
        notifyAll();
        return temp;
    }

    public Request getZero() {
        Request temp = null;
        if (eleRequest.size() > 0) {
            temp = eleRequest.get(0);
        }
        return temp;
    }

    public Request look(int floor) {
        Request temp = null;
        for (;pos < eleRequest.size();pos++) {
            if (eleRequest.get(pos) == null) {
                pos++;
                break; }

            //System.out.println(pos + " "+eleRequest.size());
            if (eleRequest.get(pos).getTo() == floor) {
                temp = eleRequest.get(pos);
                pos++;
                break; }
        }
        return temp;
    }

    public Request lookFirstNot(int floor) {
        Request temp = null;
        for (int i = 0;i < eleRequest.size();i++) {
            temp = eleRequest.get(i);
            if (temp == null) {
                break; }
            if (temp.getTo() != floor) {
                temp = eleRequest.get(i);
                break;
            }
        }
        return temp;
    }

    public void setPos() {
        pos = 0;
    }

    public synchronized void removePos(ArrayList poslist) {
        if (poslist.isEmpty() || eleRequest.isEmpty()) {
            notifyAll();
            setPos();
            return; }

        while (!poslist.isEmpty()) {
            int id = (int)poslist.get(0);
            for (int i = 0;i < eleRequest.size();) {
                if (eleRequest.get(i) == null) {
                    break; }
                if (eleRequest.get(i).getId() == id) {
                    eleRequest.remove(i);
                } else {
                    i++;
                }
            }
            poslist.remove(0);
        }
        setPos();
        notifyAll();
    }

    public Request lookTo(int id) {
        Request temp = null;
        int i = 0;
        for (i = 0;i < eleRequest.size();i++) {
            temp = eleRequest.get(i);
            if (temp == null) {
                break; }
            if (temp.getId() == id) {
                break; }
        }
        return temp;
    }

    public Request idTo(int id) {
        Request temp = null;
        int i = 0;
        for (i = 0;i < eleRequest.size();i++) {
            temp = eleRequest.get(i);
            if (temp == null) {
                break; }
            if (temp.getId() == id) {
                eleRequest.remove(temp);
                break; }
        }
        return temp;
    }

    public void str() {
        System.out.println(eleRequest.toString());
    }

    public boolean isEmpty() {
        return eleRequest.isEmpty();
    }

    public boolean isFind(int floor) {
        for (int i = 0;i < eleRequest.size();i++) {
            //System.out.println(eleRequest.get(i).getTo());
            if (eleRequest.get(i) == null) {
                return false; }
            if (eleRequest.get(i).getTo() == floor) {
                return true; }
        }
        return false;
    }
}
