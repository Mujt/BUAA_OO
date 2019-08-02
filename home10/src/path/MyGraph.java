package path;

import com.oocourse.specs2.models.Graph;
import com.oocourse.specs2.models.Path;
import com.oocourse.specs2.models.NodeIdNotFoundException;
import com.oocourse.specs2.models.PathIdNotFoundException;
import com.oocourse.specs2.models.PathNotFoundException;
import com.oocourse.specs2.models.NodeNotConnectedException;

import java.util.ArrayList;

public class MyGraph implements Graph {
    private ArrayList<Path> ppList;
    private ArrayList<Integer> pidList;
    private static int id;
    private ArrayList<NodesConect> nodesConects;

    public MyGraph() {
        id = 1;
        ppList = new ArrayList();
        pidList = new ArrayList();
        nodesConects = new ArrayList();
    }

    @Override
    public boolean containsNode(int arg0) {
        for (int i = 0;i < ppList.size();i++) {
            Path temp = ppList.get(i);
            if (temp.isValid() && temp.containsNode(arg0)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsEdge(int from, int to) {
        for (int i = 0;i < ppList.size();i++) {
            Path temp = ppList.get(i);
            if (temp.isValid() && temp.containsNode(from)
                    && temp.containsNode(to)) {
                for (int j = 0;j < temp.size() - 1;j++) {
                    if ((temp.getNode(j)
                            == from && temp.getNode(j + 1) == to) ||
                            (temp.getNode(j) == to
                                    && temp.getNode(j + 1) == from)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void addMapNode(int n1,int n2) {
        int i = 0;
        for (i = 0;i < nodesConects.size();i++) {
            NodesConect temp = nodesConects.get(i);
            if (temp.getId() == n1) {
                temp.addNodes(n2);
                break;
            }
        }
        if (i == nodesConects.size()) {
            NodesConect temp = new NodesConect(n1);
            temp.addNodes(n2);
            nodesConects.add(temp);
        }

        for (i = 0;i < nodesConects.size();i++) {
            NodesConect temp = nodesConects.get(i);
            if (temp.getId() == n2) {
                temp.addNodes(n1);
                break;
            }
        }
        if (i == nodesConects.size()) {
            NodesConect temp = new NodesConect(n2);
            temp.addNodes(n1);
            nodesConects.add(temp);
        }
    }

    public void getMap() {
        while (!nodesConects.isEmpty()) {
            nodesConects.remove(0);
        }
        for (int i = 0;i < ppList.size();i++) {
            Path temp = ppList.get(i);
            for (int j = 0;j < temp.size() - 1;j++) {
                addMapNode(temp.getNode(j),temp.getNode(j + 1)); } }
    }

    /*public int searchNodeWide(int node,int to) {
        for (int i = 0;i < nodesConects.size();i++) {
            nodesConect temp = nodesConects.get(i);
            if (temp.getId() == node) {
                for (Integer integer:temp) {
                    if (integer == to) {
                        return 1;
                    }
                }
            }
        }
    }*/

    @Override
    public boolean isConnected(int from, int to)
            throws NodeIdNotFoundException {
        if (!containsNode(from)) {
            throw new NodeIdNotFoundException(from);
        } else if (!containsNode(to)) {
            throw new NodeIdNotFoundException(to);
        } else {
            if (from == to) {
                return true;
            } else {
                getMap();
                SearchNode temp = new SearchNode(nodesConects);
                temp.Search(from,to);
                if (temp.getJudge()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getShortestPathLength(int from, int to)
            throws NodeIdNotFoundException, NodeNotConnectedException {
        if (!containsNode(from)) {
            throw new NodeIdNotFoundException(from);
        } else if (!containsNode(to)) {
            throw new NodeIdNotFoundException(to);
        } else if (!isConnected(from,to)) {
            throw new NodeNotConnectedException(from,to);
        } else {
            if (from == to) {
                return 0;
            } else {
                getMap();
                int length = 0;
                SearchNode temp = new SearchNode(nodesConects);
                temp.Search(from,to);
                length = temp.getLength();
                return length;
            }
        }
    }

    @Override
    public int size() {
        return ppList.size();
    }

    @Override
    public boolean containsPath(Path path) {
        if (path == null) {
            return false; }
        for (int i = 0;i < ppList.size();i++) {
            Path temp = ppList.get(i);
            if (temp.equals(path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsPathId(int arg0) {
        for (int i = 0;i < pidList.size();i++) {
            if (pidList.get(i) == arg0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPathById(int arg0) throws Exception {
        if (containsPathId(arg0)) {
            if (ppList.size() == pidList.size()) {
                for (int i = 0;i < pidList.size();i++) {
                    if (pidList.get(i) == arg0) {
                        return ppList.get(i);
                    }
                }
            }
        } else {
            throw new PathIdNotFoundException(arg0);
        }
        return null;
    }

    @Override
    public int getPathId(Path arg0) throws Exception {
        if (arg0 != null && arg0.isValid() && containsPath(arg0)) {
            if (pidList.size() == ppList.size()) {
                for (int i = 0;i < ppList.size();i++) {
                    if (ppList.get(i).equals(arg0)) {
                        return pidList.get(i);
                    }
                }
            }
        } else {
            throw new PathNotFoundException(arg0);
        }
        return 0;
    }

    @Override
    public int addPath(Path arg0) throws Exception {
        if (arg0 != null && arg0.isValid()) {
            if (!this.containsPath(arg0)) {
                pidList.add(id);
                ppList.add(arg0);
                return id++;
            } else {
                return getPathId(arg0);
            }
        }
        return 0;
    }

    @Override
    public int removePath(Path arg0) throws Exception {
        if (arg0 != null && arg0.isValid() && containsPath(arg0)) {
            if (ppList.size() == pidList.size()) {
                for (int i = 0;i < ppList.size();i++) {
                    if (ppList.get(i).equals(arg0)) {
                        ppList.remove(i);
                        int id = pidList.get(i);
                        pidList.remove(i);
                        return id;
                    }
                }
            }
        } else {
            throw new PathNotFoundException(arg0);
        }
        return 0;
    }

    @Override
    public void removePathById(int arg0) throws PathIdNotFoundException {
        if (containsPathId(arg0)) {
            if (ppList.size() == pidList.size()) {
                for (int i = 0; i < pidList.size();i++) {
                    if (pidList.get(i) == arg0) {
                        ppList.remove(i);
                        pidList.remove(i);
                        return;
                    }
                }
            }
        } else {
            throw new PathIdNotFoundException(arg0);
        }
    }

    @Override
    public int getDistinctNodeCount() {
        ArrayList<Integer> nodes = new ArrayList();
        for (int i = 0;i < ppList.size();i++) {
            Path temp = ppList.get(i);
            for (Integer integer:temp) {
                if (!judgeIn(nodes,integer)) {
                    nodes.add(integer);
                }
            }
        }
        return nodes.size();
    }

    public boolean judgeIn(ArrayList<Integer> nodes,int node) {
        for (int i = 0;i < nodes.size();i++) {
            if (nodes.get(i) == node) {
                return true;
            }
        }
        return false;
    }
}
