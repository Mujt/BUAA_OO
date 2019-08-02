package path;

import java.util.ArrayList;

public class SearchNode {
    private ArrayList<NodesConect> nodesConects;
    private static int length;
    private boolean conect;
    private ArrayList<Integer> nodeYes;
    private ArrayList<Integer> nodeNo;
    private ArrayList<Integer> wideNodes;

    public void addYes(int node) {
        nodeYes.add(node);
    }

    public void removeNo(int i) {
        nodeNo.remove(i);
    }

    public ArrayList<NodesConect> getNodesConects() {
        return this.nodesConects;
    }

    public ArrayList<Integer> getNodeYes() {
        return nodeYes;
    }

    public ArrayList<Integer> getNodeNo() {
        return nodeNo;
    }

    public ArrayList<Integer> getWideNodes() {
        return nodeNo;
    }

    public SearchNode(ArrayList<NodesConect> nodesConects) {
        this.nodesConects = nodesConects;
        length = 0;
        conect = false;
        nodeYes = new ArrayList<>();
        wideNodes = new ArrayList<>();
        nodeNo = new ArrayList<>();
        for (int i = 0;i < nodesConects.size();i++) {
            nodeNo.add(nodesConects.get(i).getId());
        }
    }

    public boolean getJudge() {
        return conect;
    }

    public void removeAdd(int node) {
        nodeYes.add(node);
        for (int i = 0;i < nodeNo.size();i++) {
            if (nodeNo.get(i) == node) {
                nodeNo.remove(i);
                return;
            }
        }
    }

    public int getLength() {
        return length;
    }

    public void Search(int from,int to) {
        removeAdd(from);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(from);
        getWide(list);
        wide(to);
    }

    public void getWide(ArrayList<Integer> list) {
        if (nodeNo.isEmpty()) {
            return; }
        for (int i = 0;i < list.size();i++) {
            for (int j = 0;j < nodesConects.size();j++) {
                if (nodesConects.get(j).getId() == list.get(i)) {
                    NodesConect temp = nodesConects.get(j);
                    for (Integer integer:temp) {
                        if (!judgeIn(nodeYes,integer)) {
                            removeAdd(integer);
                            wideNodes.add(integer);
                        }
                    }
                    break;
                }
            }
        }
    }

    public boolean judgeIn(ArrayList<Integer> list,int node) {
        for (int i = 0;i < list.size();i++) {
            if (list.get(i) == node) {
                return true;
            }
        }
        return false;
    }

    public void wide(int to) {
        length = length + 1;
        for (int i = 0;i < wideNodes.size();i++) {
            if (wideNodes.get(i) == to) {
                conect = true;
                return;
            }
        }
        ArrayList<Integer> newWide = new ArrayList<>();
        for (int i = 0;i < wideNodes.size();i++) {
            newWide.add(wideNodes.get(i));
        }
        cleanList();
        getWide(newWide);
        if (wideNodes.isEmpty()) {
            return; }
        wide(to);
    }

    public void cleanList() {
        while (!wideNodes.isEmpty()) {
            wideNodes.remove(0);
        }
    }

    public int blockCount() {
        if (nodeNo.isEmpty()) {
            return 0; }
        int k = 0;
        while (!nodeNo.isEmpty()) {
            ArrayList<Integer> list = new ArrayList();
            list.add(nodeNo.get(0));
            nodeYes.add(nodeNo.get(0));
            nodeNo.remove(0);
            getWide(list);
            wideFind();
            k++;
            cleanList();
        }
        return k;
    }

    public void wideFind() {
        if (wideNodes.isEmpty()) {
            return;
        }
        ArrayList<Integer> newWide = new ArrayList<>();
        for (int i = 0;i < wideNodes.size();i++) {
            newWide.add(wideNodes.get(i));
        }
        cleanList();
        getWide(newWide);
        if (wideNodes.isEmpty()) {
            return;
        }
        wideFind();
    }

}
