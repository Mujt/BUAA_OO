package elevator;

import com.oocourse.elevator1.PersonRequest;

import java.util.ArrayList;

public class RequestTray {
    private ArrayList<PersonRequest> requestTray;
    private long date;
    private boolean in;

    public RequestTray(long begin) {
        requestTray = new ArrayList<PersonRequest>();
        date = begin;
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

    public synchronized  PersonRequest get() {
        while (requestTray.isEmpty()) {
            try {
                wait();
                if (this.in == false) {
                    break;
                }
            } catch (InterruptedException e) { return null; }
        }
        try {
            PersonRequest request = requestTray.get(0);
            requestTray.remove(0);
            notifyAll();
            return request;
        } catch (IndexOutOfBoundsException e) {
            notifyAll();
            return null;
        }
    }
}
