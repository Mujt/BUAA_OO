package path;

import java.util.ArrayList;
import java.util.Iterator;

public class NodesConect implements Iterable<Integer> {
    private int nodeId;
    private ArrayList<Integer> nodes;

    public NodesConect(int id) {
        nodeId = id;
        nodes = new ArrayList();
    }

    public int getId() {
        return nodeId;
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

    public Iterator<Integer> iterator() {
        return nodes.iterator();
    }
}
