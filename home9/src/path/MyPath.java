package path;

import java.util.ArrayList;
import java.util.Iterator;

import com.oocourse.specs1.models.Path;

public class MyPath implements Path {
    private ArrayList<Integer> nodes;

    public MyPath(int... nodeList) {
        nodes = new ArrayList<>();
        for (int i = 0;i < nodeList.length;i++) {
            this.nodes.add(nodeList[i]);
        }
    }

    @Override
    public int size() {
        return nodes.size();
    }

    @Override
    public Iterator<Integer> iterator() {
        // TODO Auto-generated method stub
        return nodes.iterator();
    }

    @Override
    public int compareTo(Path o) {
        // TODO Auto-generated method stub
        int jud = 0;
        for (int i = 0;i < o.size() && i < size();i++) {
            if (nodes.get(i) < o.getNode(i)) {
                jud = -1;
                break;
            } else if (nodes.get(i) > o.getNode(i)) {
                jud = 1;
                break;
            }
        }
        if (jud == 0) {
            return Integer.compare(size(),o.size());
        }
        return jud;
    }

    @Override
    public boolean containsNode(int arg0) {
        // TODO Auto-generated method stub
        for (int i = 0;i < size();i++) {
            if (nodes.get(i) == arg0) {
                return true; } }
        return false;
    }

    @Override
    public int getDistinctNodeCount() {
        // TODO Auto-generated method stub
        int time = 0;
        for (int i = 0;i < nodes.size();i++) {
            int j = 0;
            for (j = 0;j < i;j++) {
                if (nodes.get(j).equals(nodes.get(i))) {
                    break;
                }
            }
            if (j == i) {
                time++;
            }
        }
        return time;
    }

    @Override
    public int getNode(int arg0) {
        if (arg0 < 0 || arg0 >= nodes.size()) {
            throw new IndexOutOfBoundsException();
        }
        return nodes.get(arg0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Path) {
            if (nodes.size() == ((Path) obj).size()) {
                int i = 0;
                for (i = 0;i < nodes.size();i++) {
                    if (nodes.get(i) != ((Path) obj).getNode(i)) {
                        break;
                    }
                }
                if (i == nodes.size()) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean isValid() {
        // TODO Auto-generated method stub
        return nodes.size() >= 2;
    }
}
