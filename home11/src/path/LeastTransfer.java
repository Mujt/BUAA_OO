package path;

import com.oocourse.specs3.models.Path;

import java.util.ArrayList;

public class LeastTransfer extends SearchFromNet {
    public LeastTransfer(ArrayList<Path> pathList) {
        super(pathList);
        init();
    }

    public void init() {
        ArrayList<Path> pathList = getPathList();
        for (int i = 0;i < pathList.size();i++) {
            Path temp = pathList.get(i);
            for (int j = 0;j < temp.size() - 1;j++) {
                int node1 = temp.getNode(j);
                addNodes(node1);
                for (int k = j + 1;k < temp.size();k++) {
                    int node2 = temp.getNode(k);
                    setMap(node1,node2,1);
                }
            }
        }
    }

    public int getLeast(int from,int to) {
        super.SearchLeast(from,to);
        return getLength() - 1;
    }
}
