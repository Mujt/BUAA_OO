package path;

import java.util.ArrayList;
import java.util.Iterator;

public class NodesNet implements Iterable<Integer> {
    private ArrayList<Integer> nodes;
    private int[][] net;
    private int[] mark;
    private int[] dst;
    private ArrayList<Integer> path;
    private int begin;

    public NodesNet() {
        mark = new int[120];
        dst = new int[120];
        net = new int[120][120];
        nodes = new ArrayList<>();
    }

    public int size() {
        return nodes.size();
    }

    public int addNodes(int node) {
        for (Integer integer:this) {
            if (integer == node) {
                return 0;
            }
        }
        nodes.add(node);
        return 1;
    }

    public void setNet(int node1,int node2,int n) {
        int p1 = 0;
        int p2 = 0;
        p1 = findNode(node1);
        p2 = findNode(node2);
        if (p1 == -1) {
            nodes.add(node1);
            p1 = nodes.size() - 1;
        }
        if (p2 == -1) {
            nodes.add(node2);
            p2 = nodes.size() - 1;
        }
        if (net[p1][p2] == 0) {
            net[p1][p2] = n;
            net[p2][p1] = n;
        } else {
            if (n < net[p1][p2]) {
                net[p1][p2] = n;
                net[p2][p1] = n;
            }
        }
    }

    public int getNet(int node1,int node2) {
        int p1 = 0;
        int p2 = 0;
        for (int i = 0,j = 0;i < size() && j < size();i++,j++) {
            if (nodes.get(i) == node1) {
                p1 = i; }
            if (nodes.get(j) == node2) {
                p2 = j; }
        }
        return net[p1][p2];
    }

    @Override
    public Iterator<Integer> iterator() {
        return nodes.iterator();
    }

    public int findNode(int node) {
        for (int i = 0;i < nodes.size();i++) {
            if (nodes.get(i).equals(node)) {
                return i;
            }
        }
        return -1;
    }

    public int findMin() {
        int pos = begin;
        for (int i = 0;i < size();i++) {
            if (dst[i] != 0 && dst[i] != -1 && mark[i] != 1) {
                pos = i;
                break;
            }
        }
        int temp = dst[pos];
        for (int i = pos + 1;i < size();i++) {
            if (dst[i] <= temp && dst[i] != -1 && mark[i] != 1) {
                temp = dst[i];
                pos = i;
            }
        }
        return pos;
    }

    public int search(int from,int to) {
        for (int i = 0;i < size();i++) {
            dst[i] = -1;
        }
        //System.out.println(nodes);
        int begin = findNode(from);
        mark[begin] = 1;
        dst[begin] = 0;
        this.begin = begin;
        int num = 0;
        while (num <= size()) {
            int i = findMin();
            if (i == -1) {
                i = begin;
            }
            //System.out.println("i:"+i);
            mark[i] = 1;
            num++;
            for (int j = 0;j < size();j++) {
                if (i == j) {
                    continue; }
                if (net[i][j] != 0 && mark[j] != 1) {
                    if (dst[j] == -1) {
                        dst[j] = net[i][j] + dst[i];
                    } else {
                        dst[j] = Math.min(net[i][j] + dst[i],dst[j]);
                    }

                }
            }
        }
        int end = findNode(to);
        //System.out.println(nodes);
        /*for (int i =0;i<size();i++) {
            for (int j=0;j<size();j++) {
                System.out.print(net[i][j]+" ");
            }
            System.out.println();
        }*/
        return dst[end];
    }
}
