package path;

import com.oocourse.specs3.models.Path;

import java.util.ArrayList;

public class SearchFromNet {
    private NodesNet map;
    private ArrayList<Path> pathList;
    private int length;

    public SearchFromNet(ArrayList<Path> pathList) {
        map = new NodesNet();
        this.pathList = pathList;
        length = 0;
    }

    public ArrayList<Path> getPathList() {
        return pathList;
    }

    public void addNodes(int node) {
        map.addNodes(node);
    }

    public void setMap(int n1,int n2,int n) {
        map.setNet(n1,n2,n);
    }

    public boolean judgeIn(ArrayList<Integer> nodes,int node) {
        for (int i = 0;i < nodes.size();i++) {
            if (nodes.get(i) == node) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<NodesConect> getPathMap(Path path) {
        ArrayList<NodesConect> conect = new ArrayList<>();
        for (int j = 0;j < path.size() - 1;j++) {
            int i = 0;
            int n1 = path.getNode(j);
            int n2 = path.getNode(j + 1);
            for (i = 0;i < conect.size();i++) {
                NodesConect temp = conect.get(i);
                if (temp.getId() == n1) {
                    temp.addNodes(n2);
                    break;
                }
            }
            if (i == conect.size()) {
                NodesConect temp = new NodesConect(n1);
                temp.addNodes(n2);
                conect.add(temp);
            }
            for (i = 0;i < conect.size();i++) {
                NodesConect temp = conect.get(i);
                if (temp.getId() == n2) {
                    temp.addNodes(n1);
                    break; }
            }
            if (i == conect.size()) {
                NodesConect temp = new NodesConect(n2);
                temp.addNodes(n1);
                conect.add(temp);
            }
        }
        return conect;
    }

    public void SearchLeast(int from,int to) {
        length = map.search(from,to);
    }

    public int getLength() {
        return length;
    }
}
