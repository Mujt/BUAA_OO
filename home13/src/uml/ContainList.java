package uml;

import com.oocourse.uml1.models.elements.UmlElement;

import java.util.ArrayList;
import java.util.HashMap;

public class ContainList {
    private HashMap<String, ElementJudge> map;
    private ArrayList<UmlElement> list;
    private boolean judge;

    public ContainList() {
        this.map = new HashMap();
        this.list = new ArrayList();
        judge = false;
    }

    public ContainList(HashMap<String, ElementJudge> map,
                       ArrayList<UmlElement> list,boolean judge) {
        this.map = map;
        this.list = list;
        this.judge = judge;
    }

    public void set() {
        judge = true;
    }

    public boolean getClassJudge() {
        return judge;
    }

    public int getSize() {
        return list.size();
    }

    public ContainList copy() {
        return new ContainList(new HashMap(map)
                ,new ArrayList(list),new Boolean(judge));
    }

    public void add(UmlElement operation) {
        if (map.containsKey(operation.getName())) {
            map.get(operation.getName()).set();
        } else {
            map.put(operation.getName(),new ElementJudge(operation));
        }
        list.add(operation);
    }

    public boolean judgeIn(String id) {
        return map.containsKey(id);
    }

    public boolean judgeDup(String id) {
        if (map.containsKey(id)) {
            return map.get(id).getJudge();
        }
        return false;
    }

    public UmlElement getElement(String id) {
        if (map.containsKey(id)) {
            return map.get(id).getElement();
        }
        return null;
    }

    public ArrayList<UmlElement> getList() {
        return list;
    }

    public String toString() {
        String str = "";
        for (int i = 0;i < list.size();i++) {
            str = str + " " + list.get(i).getName();
        }
        return str;
    }
}
