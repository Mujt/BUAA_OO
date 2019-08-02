package path;

import com.oocourse.specs3.models.Path;

import java.util.ArrayList;
import java.util.Iterator;

public class MyPath implements Path {
    private ArrayList<Integer> nodes;
    private ArrayList<Integer> distinctNodes;

    public MyPath(int... nodes) {
        this.nodes = new ArrayList();
        this.distinctNodes = new ArrayList();
        for (int i = 0;i < nodes.length;i++) {
            this.nodes.add(nodes[i]);
        }
    }

    @Override
    public int size() {
        return nodes.size();
    }

    @Override
    public int getNode(int i) {
        if (i < 0 || i >= nodes.size()) {
            throw new IndexOutOfBoundsException();
        }
        return nodes.get(i);
    }

    @Override
    public boolean containsNode(int i) {
        for (Integer integer:this) {
            if (i == integer) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getDistinctNodeCount() {
        for (Integer integer:this) {
            int i = 0;
            for (i = 0;i < distinctNodes.size();i++) {
                if (distinctNodes.get(i).equals(integer)) {
                    break;
                }
            }
            if (i == distinctNodes.size()) {
                distinctNodes.add(integer);
            }
        }
        return distinctNodes.size();
    }

    @Override
    public boolean isValid() {
        return nodes.size() >= 2;
    }

    @Override
    public int getUnpleasantValue(int i) {
        if (containsNode(i)) {
            return (int)Math.pow(4, (i % 5 + 5) % 5);
        }
        return 0;
    }

    @Override
    public int compareTo(Path o) {
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
    public Iterator<Integer> iterator() {
        return nodes.iterator();
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
}

