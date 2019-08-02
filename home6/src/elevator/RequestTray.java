package elevator;

import com.oocourse.elevator2.PersonRequest;

import java.util.ArrayList;

public class RequestTray {
    private ArrayList<PersonRequest> requestTray;
    //private long date;
    private boolean in;

    public RequestTray() {
        requestTray = new ArrayList<PersonRequest>();
        in = true;
    }

    public void setIn() {
        in = false;
    }

    public boolean getIn() {
        return this.in;
    }

    public synchronized void put(PersonRequest request) {
        requestTray.add(request);
        notifyAll();
    }

    public int retSize() {
        return requestTray.size();
    }

    public PersonRequest lookUp(int i) {
        try {
            PersonRequest request = requestTray.get(i);
            //requestTray.remove(0);
            // notifyAll();
            return request;
        } catch (IndexOutOfBoundsException e) {
            // notifyAll();
            return null;
        }
    }

    public synchronized  PersonRequest get(int i) {
        while (requestTray.isEmpty()) {
            try {
                wait();
                if (this.in == false) {
                    break;
                }
            } catch (InterruptedException e) { return null; }
        }
        try {
            PersonRequest request = requestTray.get(i);
            requestTray.remove(i);
            notifyAll();
            return request;
        } catch (IndexOutOfBoundsException e) {
            notifyAll();
            return null;
        }
    }
}
