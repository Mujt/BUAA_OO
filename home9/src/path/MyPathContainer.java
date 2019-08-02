package path;

import com.oocourse.specs1.models.Path;
import com.oocourse.specs1.models.PathContainer;
import com.oocourse.specs1.models.PathIdNotFoundException;
import com.oocourse.specs1.models.PathNotFoundException;

import java.util.ArrayList;

public class MyPathContainer implements PathContainer {
    private ArrayList<Path> pList;
    private ArrayList<Integer> pidList;
    private static int id;
    private ArrayList<Integer> nodes;

    public MyPathContainer() {
        id = 1;
        pList = new ArrayList<>();
        pidList = new ArrayList<>();
        nodes = new ArrayList<>();
    }

    @Override
    public int addPath(Path arg0) throws Exception {
        // TODO Auto-generated method stub
        if (arg0 != null && arg0.isValid()) {
            if (!this.containsPath(arg0)) {
                pidList.add(id);
                pList.add(arg0);
                /*for (Integer integer:arg0) {
                    if (!judgeIn(nodes,integer)){
                        nodes.add(integer); } }*/
                newNodes();
                return id++;
            } else {
                return getPathId(arg0);
            }
        }
        return 0;
    }

    @Override
    public boolean containsPath(Path arg0) {
        // TODO Auto-generated method stub
        if (arg0 == null) {
            return false; }
        for (int i = 0;i < pList.size();i++) {
            Path temp = pList.get(i);
            if (temp.equals(arg0)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsPathId(int arg0) {
        // TODO Auto-generated method stub
        for (int i = 0;i < pidList.size();i++) {
            if (pidList.get(i) == arg0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getDistinctNodeCount() {
        // TODO Auto-generated method stub
        newNodes();
        return nodes.size();
    }

    public boolean judgeIn(ArrayList<Integer> nodes,int node) {
        for (int i = 0;i < nodes.size();i++) {
            if (nodes.get(i)==node) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPathById(int arg0) throws Exception {
        // TODO Auto-generated method stub
        if (containsPathId(arg0)) {
            if (pList.size() == pidList.size()) {
                for (int i = 0;i < pidList.size();i++) {
                    if (pidList.get(i) == arg0) {
                        return pList.get(i);
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
        // TODO Auto-generated method stub
        if (arg0 != null && arg0.isValid() && containsPath(arg0)) {
            if (pidList.size() == pList.size()) {
                for (int i = 0;i < pList.size();i++) {
                    if (pList.get(i).equals(arg0)) {
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
    public int removePath(Path arg0) throws Exception {
        // TODO Auto-generated method stub
        if (arg0 != null && arg0.isValid() && containsPath(arg0)) {
            if (pList.size() == pidList.size()) {
                for (int i = 0;i < pList.size();i++) {
                    if (pList.get(i).equals(arg0)) {
                        pList.remove(i);
                        int id = pidList.get(i);
                        pidList.remove(i);
                        newNodes();
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
        // TODO Auto-generated method stub
        if (containsPathId(arg0)) {
            if (pList.size() == pidList.size()) {
                for (int i = 0; i < pidList.size();i++) {
                    if (pidList.get(i) == arg0) {
                        pList.remove(i);
                        pidList.remove(i);
                        newNodes();
                        return;
                    }
                }
            }
        } else {
            throw new PathIdNotFoundException(arg0);
        }
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return pList.size();
    }

    public void newNodes() {
        while (!nodes.isEmpty()) {
            nodes.remove(0);
        }
        for (int i = 0;i < pList.size();i++) {
            Path temp = pList.get(i);
            for (Integer integer:temp) {
                if (!judgeIn(nodes,integer)) {
                    nodes.add(integer);
                }
            }
        }
    }
}
