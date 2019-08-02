package path;

import com.oocourse.specs3.models.Path;

import java.util.ArrayList;

public class LeastPrice extends SearchFromNet {
    public LeastPrice(ArrayList<Path> pathList) {
        super(pathList);
        init();
    }

    public void init() {
        ArrayList<Path> pathList = getPathList();
        for (int i = 0;i < pathList.size();i++) {
            Path temp = pathList.get(i);
            ArrayList<NodesConect> conect = getPathMap(temp);
            /*for(int io =0;io<conect.size();io++)
            System.out.println(conect.get(io).str());*/

            for (int j = 0;j < temp.size();j++) {
                int node1 = temp.getNode(j);
                addNodes(node1);
                //System.out.println("ds" + node1);
                for (int k = j + 1;k < temp.size();k++) {
                    int node2 = temp.getNode(k);
                    SearchNode search = new SearchNode(conect);
                    search.Search(node1,node2);
                    int len = search.getLength();
                    //System.out.println(len);
                    setMap(node1,node2,len + 2);
                }
            }
        }
    }

    public int getLeast(int from,int to) {
        super.SearchLeast(from,to);
        return getLength() - 2;
    }
}
