package elevator;

import java.util.ArrayList;

public class RequestTray {
    private ArrayList<MyPersonRequest> extralTray;
    private ArrayList<int[]> dockList;
    private ArrayList<MyPersonRequest> requestTray;
    private boolean in;
    private ArrayList<int[]> inter = new ArrayList();

    public RequestTray(ArrayList<int[]> dockList) {
        requestTray = new ArrayList();
        in = true;
        this.dockList = dockList;
        inter.add(getIntersection(dockList.get(0),dockList.get(1)));
        inter.add(getIntersection(dockList.get(0),dockList.get(2)));
        inter.add(getIntersection(dockList.get(1),dockList.get(2)));
        extralTray = new ArrayList();
    }

    public void setIn() {
        in = false;
    }

    public boolean getIn() {
        return this.in;
    }

    public synchronized void put(MyPersonRequest request) {
        MyPersonRequest temp = planExchange(request);
        requestTray.add(temp);
        notifyAll();
    }
    
    public void str() {
        System.out.println("request:");
        System.out.println(requestTray.toString());
        System.out.println("extra:");
        System.out.println(extralTray.toString());
    }
    
    public synchronized void waitFor() {
        while (requestTray.isEmpty()) {
            try {
                wait();
                if (this.in == false) {
                    notifyAll();
                    break;
                }
            } catch (InterruptedException e) {
                notifyAll();
                return;
            }
        }
        notifyAll();
    }
    
    public int retSize1() {
        return extralTray.size();
    }

    public int retSize() {
        return requestTray.size();
    }

    public synchronized MyPersonRequest lookUp(int i) {
        try {
            MyPersonRequest request = requestTray.get(i);
            //requestTray.remove(0);
            notifyAll();
            return request;
        } catch (IndexOutOfBoundsException e) {
            notifyAll();
            System.exit(0);
            return null;
        }
    }

    public synchronized  MyPersonRequest get(int i) {
        while (requestTray.isEmpty()) {
            try {
                wait();
                if (this.in == false) {
                    break;
                }
            } catch (InterruptedException e) {
                System.exit(0);
                return null;
            }
        }
        try {
            MyPersonRequest request = requestTray.get(i);
            requestTray.remove(i);
            notifyAll();
            return request;
        } catch (IndexOutOfBoundsException e) {
            notifyAll();
            System.exit(0);
            return null;
        }
    }

    /*    public void str() {
            System.out.println("in Tray " + requestTray.size());
            for (int i = 0;i < requestTray.size();i++) {
                System.out.println(requestTray.get(i).toString());
            }
            System.out.println("in extra " + extralTray.size());
            for (int i = 0;i < extralTray.size();i++) {
                System.out.println(extralTray.get(i).toString());
            }
        }*/

    public boolean judgeIndock(MyPersonRequest request,int[] dock) {
        boolean judge1 = false;
        boolean judge2 = false;
        if (request == null) {
            return false;
        }
        for (int i = 0;i < dock.length;i++) {
            if (dock[i] == request.getFromFloor()) {
                judge1 = true;
                break;
            }
        }
        for (int i = 0;i < dock.length;i++) {
            if (dock[i] == request.getToFloor()) {
                judge2 = true;
                break;
            }
        }
        return judge1 && judge2;
    }

    public boolean judgeOne(MyPersonRequest request) {
        for (int i = 0;i < dockList.size();i++) {
            if (judgeIndock(request,(int[])dockList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public int getEle(int floor) {
        for (int i = 0;i < dockList.size();i++) {
            int[] temp = dockList.get(i);
            for (int j = 0;j < temp.length;j++) {
                if (temp[j] == floor) {
                    return i;
                }
            }
        }
        return -100;
    }

    public int getPosIndock(int floor,int[] dock) {
        if (floor < dock[0]) {
            return -1;
        }
        for (int i = 0;i < dock.length - 1;i++) {
            if (dock[i] < floor && floor < dock[i + 1]) {
                return i;
            }
        }
        return dock.length - 1;
    }

    public MyPersonRequest planExchange(MyPersonRequest request) {
        MyPersonRequest temp = null;
        MyPersonRequest temp1 = null;
        if (judgeOne(request) || request == null) {
            return request; }
        int ele1 = getEle(request.getFromFloor());
        int ele2 = getEle(request.getToFloor());
        int[] dockTemp = inter.get(ele1 + ele2 - 1);
        int i1 = getPosIndock(request.getFromFloor(),dockTemp);
        int i2 = getPosIndock(request.getToFloor(),dockTemp);
        if (i1 == i2) {
            if (i1 == 0) {
                temp = new MyPersonRequest(
                        request.getFromFloor()
                        ,dockTemp[1],request.getPersonId());
                temp1 = new MyPersonRequest(
                        dockTemp[1],request.getToFloor(),request.getPersonId());
            } else if (i1 == dockTemp.length - 1) {
                temp = new MyPersonRequest(request.getFromFloor()
                        ,dockTemp[i1 - 1],request.getPersonId());
                temp1 = new MyPersonRequest(dockTemp[i1 - 1]
                        ,request.getToFloor(),request.getPersonId());
            } else {
                int distance1 = Math.abs(
                        dockTemp[i1 + 1] - request.getFromFloor())
                        +  Math.abs(dockTemp[i1 + 1] - request.getToFloor());
                int distance2 = Math.abs(
                        dockTemp[i1 - 1] - request.getFromFloor())
                        +  Math.abs(dockTemp[i1 - 1] - request.getToFloor());
                if (distance1 > distance2) {
                    temp = new MyPersonRequest(
                            request.getFromFloor()
                            ,dockTemp[i1 - 1],request.getPersonId());
                    temp1 = new MyPersonRequest(
                            dockTemp[i1 - 1]
                            ,request.getToFloor(),request.getPersonId());
                } else {
                    temp = new MyPersonRequest(
                            request.getFromFloor(),
                            dockTemp[i1 + 1],request.getPersonId());
                    temp1 = new MyPersonRequest(
                            dockTemp[i1 + 1],
                            request.getToFloor(),request.getPersonId()); } }
            extralTray.add(temp1);
        } else if (i1 > i2) {
            temp = new MyPersonRequest(request.getFromFloor()
                    ,dockTemp[i1],request.getPersonId());
            temp1 = new MyPersonRequest(dockTemp[i1]
                    ,request.getToFloor(),request.getPersonId());
            extralTray.add(temp1);
        } else {
            temp = new MyPersonRequest(request.getFromFloor()
                    ,dockTemp[i2],request.getPersonId());
            temp1 = new MyPersonRequest(dockTemp[i2]
                    ,request.getToFloor(),request.getPersonId());
            extralTray.add(temp1);
        }
        return temp;
    }

    public int[] getIntersection(int []section1,int []section2) {
        ArrayList temp = new ArrayList();
        for (int i = 0;i < section1.length;i++) {
            int temp1 = section1[i];
            for (int j = 0;j < section2.length;j++) {
                int temp2 = section2[j];
                if (temp1 == temp2) {
                    temp.add(temp1);
                    break;
                }
            }
        }
        int[] tempp = new int[temp.size()];
        for (int i = 0;i < temp.size();i++) {
            tempp[i] = (int)temp.get(i);
        }
        return tempp;
    }

    public void trayAdd(MyPersonRequest temp) {
        int size = requestTray.size();
        if (size == 0) {
            requestTray.add(temp);

            return;
        }
        if (requestTray.get(size - 1) == null) {
            requestTray.remove(size - 1);
            requestTray.add(temp);
            requestTray.add(null);
        } else {
            requestTray.add(temp);
        }
    }

    public synchronized void newList(int id) {
        for (int i = 0;i < extralTray.size();) {
            MyPersonRequest temp = extralTray.get(i);
            if (temp.getPersonId() == id) {
                trayAdd(temp);
                extralTray.remove(i);
                break;
            } else {
                i++;
            }
        }
        notifyAll();
    }
}
