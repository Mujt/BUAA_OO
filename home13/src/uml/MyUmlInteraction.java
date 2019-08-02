package uml;

import com.oocourse.uml1.interact.common.AttributeClassInformation;
import com.oocourse.uml1.interact.common.AttributeQueryType;
import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml1.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml1.interact.format.UmlInteraction;
import com.oocourse.uml1.models.common.Direction;
import com.oocourse.uml1.models.common.ElementType;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlAssociation;
import com.oocourse.uml1.models.elements.UmlAssociationEnd;
import com.oocourse.uml1.models.elements.UmlElement;
import com.oocourse.uml1.models.elements.UmlInterfaceRealization;
import com.oocourse.uml1.models.elements.UmlGeneralization;
import com.oocourse.uml1.models.elements.UmlAttribute;
import com.oocourse.uml1.models.elements.UmlOperation;
import com.oocourse.uml1.models.elements.UmlParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

public class MyUmlInteraction implements UmlInteraction {
    private ArrayList<UmlElement> list;
    private HashMap<String, String> idName;
    private HashMap<String, ElementJudge> classList;
    private HashMap<String, ElementJudge> classList1;
    private HashMap<String, ContainList> operationLook;
    private HashMap<String, ContainList> attributeLook;
    private HashMap<String, ArrayList<String>> interfaceLook;
    private HashMap<String, String> parentLook;
    private HashMap<String, JudgeTwo> operationInf;
    private HashMap<String, String> interfaceList;
    private HashMap<String, ArrayList<String>> interfaceparent;
    private HashMap<String, ElementJudge> endList;
    private HashMap<String, ArrayList<String>> classAssociation;

    public MyUmlInteraction(UmlElement... elements) {
        list = new ArrayList();
        for (int i = 0;i < elements.length;i++) {
            list.add(elements[i]); }
        idName = new HashMap();
        classList = new HashMap();
        operationLook = new HashMap();
        attributeLook = new HashMap();
        interfaceLook = new HashMap();
        parentLook = new HashMap();
        operationInf = new HashMap();
        interfaceList = new HashMap();
        interfaceparent = new HashMap();
        classAssociation = new HashMap();
        endList = new HashMap();
        classList1 = new HashMap();
        for (int i = 0;i < list.size();) {
            UmlElement temp = list.get(i);
            idName.put(temp.getId(),temp.getName());
            if (temp.getElementType().equals(ElementType.UML_CLASS)) {
                classList1.put(temp.getId(),new ElementJudge(temp));
                if (!classList.containsKey(temp.getName())) {
                    classList.put(temp.getName(), new ElementJudge(temp));
                } else {
                    classList.get(temp.getName()).set(); }

                if (!operationLook.containsKey(temp.getName())) {
                    operationLook.put(temp.getName(), new ContainList());
                } else {
                    operationLook.get(temp.getName()).set(); }
                //operationLook.put(temp.getName(), new ContainList());

                if (!attributeLook.containsKey(temp.getName())) {
                    attributeLook.put(temp.getName(), new ContainList());
                } else {
                    attributeLook.get(temp.getName()).set(); }
                if (!interfaceLook.containsKey(temp.getName())) {
                    interfaceLook.put(temp.getName(), new ArrayList());
                }
                list.remove(i);
            } else {
                if (temp.getElementType().equals(
                        ElementType.UML_ASSOCIATION_END)) {
                    endList.put(temp.getId(),new ElementJudge(temp)); }
                i++; }
        }
        init();
    }

    public void addInterfaceParent(String i,String j) {
        if (interfaceparent.containsKey(i)) {
            interfaceparent.get(i).add(j);
        } else {
            interfaceparent.put(i,new ArrayList());
            interfaceparent.get(i).add(j);
        }
    }

    public void addAssociation(UmlElement umlElement) {
        String end1 = ((UmlAssociation)umlElement).getEnd1();
        String end2 = ((UmlAssociation)umlElement).getEnd2();
        String class1 = ((UmlAssociationEnd)endList
                .get(end1).getElement()).getReference();
        String class2 = ((UmlAssociationEnd)endList
                .get(end2).getElement()).getReference();
        if (classAssociation.containsKey(class1)) {
            classAssociation.get(class1).add(class2);
        } else {
            classAssociation.put(class1,new ArrayList());
            classAssociation.get(class1).add(class2);
        }
        if (classAssociation.containsKey(class2)) {
            classAssociation.get(class2).add(class1);
        } else {
            classAssociation.put(class2,new ArrayList());
            classAssociation.get(class2).add(class1);
        }
    }

    public void init() {
        for (int i = 0;i < list.size();i++) {
            UmlElement temp = list.get(i);
            ElementType tempTyp = list.get(i).getElementType();
            if (tempTyp.equals(ElementType.UML_OPERATION)) {
                String name = idName.get(temp.getParentId());
                if (operationLook.containsKey(name)) {
                    operationLook.get(name).add(temp);
                } else {
                    operationLook.put(name,new ContainList()); }
                if (!operationInf.containsKey(temp.getId())) {
                    operationInf.put(temp.getId(),new JudgeTwo()); }
            } else if (tempTyp == ElementType.UML_ATTRIBUTE) {
                String name = idName.get(temp.getParentId());
                if (attributeLook.containsKey(name)) {
                    attributeLook.get(name).add(temp);
                } else {
                    attributeLook.put(name, new ContainList()); }
            } else if (tempTyp.equals(ElementType.UML_INTERFACE_REALIZATION)) {
                addInterfaceParent(((UmlInterfaceRealization) temp).getSource()
                        ,((UmlInterfaceRealization) temp).getTarget());
                String name = idName
                        .get(((UmlInterfaceRealization)temp).getSource());
                if (interfaceLook.containsKey(name)) {
                    interfaceLook.get(name)
                            .add(((UmlInterfaceRealization)temp).getTarget());
                } else {
                    interfaceLook.put(name,new ArrayList()); }
            } else if (tempTyp.equals(ElementType.UML_GENERALIZATION)) {
                addInterfaceParent(((UmlGeneralization) temp)
                        .getSource(),((UmlGeneralization) temp).getTarget());
                if (!parentLook.containsKey(
                        ((UmlGeneralization)temp).getSource())) {
                    parentLook.put(((UmlGeneralization)temp)
                            .getSource(),((UmlGeneralization)temp).getTarget());
                }
            } /*else if (tempTyp.equals(ElementType.UML_OPERATION)) {
                if (!operationInf.containsKey(temp.getId())) {
                    operationInf.put(temp.getId(),new JudgeTwo()); }
            }*/ else if (tempTyp.equals(ElementType.UML_PARAMETER)) {
                UmlParameter temp1 = (UmlParameter)temp;
                if (!operationInf.containsKey(temp.getParentId())) {
                    operationInf.put(temp.getParentId(),new JudgeTwo());
                    if (temp1.getDirection().equals(Direction.IN)) {
                        operationInf.get(temp.getParentId()).set1();
                    } else if (temp1.getDirection().equals(Direction.RETURN)) {
                        operationInf.get(temp.getParentId()).set2();
                    }
                } else {
                    if (temp1.getDirection().equals(Direction.IN)) {
                        operationInf.get(temp.getParentId()).set1();
                    } else if (temp1.getDirection().equals(Direction.RETURN)) {
                        operationInf.get(temp.getParentId()).set2();
                    }
                }
            } else if (tempTyp.equals(ElementType.UML_INTERFACE)) {
                interfaceList.put(temp.getId(),temp.getName());
            } else if (tempTyp.equals(ElementType.UML_ASSOCIATION)) {
                addAssociation(temp);
            }
        }
    }

    @Override
    public int getClassCount() {
        return classList1.size();
    }

    @Override
    public int getClassOperationCount(String s,
                                      OperationQueryType operationQueryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        ElementJudge temp = classList.get(s);
        if (temp == null) {
            throw new ClassNotFoundException(s);
        } else if (temp.getJudge() == true) {
            throw new ClassDuplicatedException(s);
        }
        int ret = 0;
        int noRet = 0;
        int para = 0;
        int noPara = 0;
        ArrayList<UmlElement> list = operationLook.get(s).getList();
        if (operationQueryType.equals(OperationQueryType.ALL)) {
            return list.size(); }
        for (int i = 0;i < list.size();i++) {
            String id = list.get(i).getId();
            //System.out.println(id + " " +operationInf.get(id));
            if (operationInf.get(id).get1()) {
                para++;
            } else {
                noPara++; }
            if (operationInf.get(id).get2()) {
                ret++;
            } else {
                noRet++;
            }
        }
        if (operationQueryType.equals(OperationQueryType.NON_PARAM)) {
            return noPara;
        } else if (operationQueryType.equals(OperationQueryType.NON_RETURN)) {
            return noRet;
        } else if (operationQueryType.equals(OperationQueryType.PARAM)) {
            return para;
        } else if (operationQueryType.equals(OperationQueryType.RETURN)) {
            return ret;
        }
        return 0;
    }

    @Override
    public int getClassAttributeCount(String s,
                                      AttributeQueryType attributeQueryType)
            throws ClassNotFoundException, ClassDuplicatedException {
        ElementJudge temp = classList.get(s);
        if (temp == null) {
            throw new ClassNotFoundException(s);
        } else if (temp.getJudge() == true) {
            throw new ClassDuplicatedException(s);
        }
        if (attributeQueryType.equals(AttributeQueryType.SELF_ONLY)) {
            return attributeLook.get(s).getSize();
        } else if (attributeQueryType.equals(AttributeQueryType.ALL)) {
            int count = 0;
            String name = s;
            while (true) {
                ArrayList<UmlElement> at = attributeLook.get(name).getList();
                for (int i = 0; i < at.size(); i++) {
                    count++;
                }
                String parentId = parentLook.get(
                        classList.get(name).getElement().getId());
                if (!idName.containsKey(parentId)) {
                    break; }
                String parentName = idName.get(parentId);
                name = parentName;
            }
            return count;
        }
        return 0;
    }

    @Override
    public int getClassAssociationCount(String s)
            throws ClassNotFoundException, ClassDuplicatedException {
        ElementJudge temp = classList.get(s);
        if (temp == null) {
            throw new ClassNotFoundException(s);
        } else if (temp.getJudge() == true) {
            throw new ClassDuplicatedException(s);
        }
        int count = 0;
        String name = s;
        String id = classList.get(s).getElement().getId();
        while (true) {
            Set set = endList.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry) i.next();
                if (((UmlAssociationEnd)(((ElementJudge)me
                        .getValue()).getElement())).getReference().equals(id)) {
                    count++; }
            }
            String parent = parentLook.get(id);
            if (!idName.containsKey(parent)) {
                break; }
            id = parent;
        }
        return count;
    }

    @Override
    public List<String> getClassAssociatedClassList(String s)
            throws ClassNotFoundException, ClassDuplicatedException {
        ElementJudge temp = classList.get(s);
        if (temp == null) {
            throw new ClassNotFoundException(s);
        } else if (temp.getJudge() == true) {
            throw new ClassDuplicatedException(s); }
        ArrayList<String> listRe = new ArrayList();
        HashMap<String, String> map = new HashMap();
        String name = s;
        String id = classList.get(s).getElement().getId();
        while (true) {
            ArrayList list = classAssociation.get(id);
            if (list == null) {
                String parent = parentLook.get(id);
                if (!idName.containsKey(parent)) {
                    break; }
                id = parent;
                continue; }
            for (int i = 0;i < list.size();i++) {
                String nameTemp = idName.get(list.get(i));
                if (classList1.containsKey(list.get(i))) {
                    if (!map.containsKey(list.get(i))) {
                        listRe.add(nameTemp);
                        map.put((String)list.get(i), nameTemp); }
                }
            }
            String parent = parentLook.get(id);
            if (!idName.containsKey(parent)) {
                break; }
            id = parent;
        }
        return listRe;
    }

    @Override
    public Map<Visibility, Integer> getClassOperationVisibility(
                    String s, String s1)
            throws ClassNotFoundException, ClassDuplicatedException {
        ContainList temp = operationLook.get(s);
        if (temp == null) {
            throw new ClassNotFoundException(s);
        } else if (temp.getClassJudge() == true) {
            throw new ClassDuplicatedException(s); }
        Map<Visibility,Integer> map = new HashMap();
        int pub = 0;
        int pro = 0;
        int pri = 0;
        int pak = 0;
        ArrayList<UmlElement> list = temp.getList();
        for (int i = 0;i < list.size();i++) {
            if (list.get(i).getName().equals(s1)) {
                if (((UmlOperation)list.get(i))
                        .getVisibility().equals(Visibility.PUBLIC)) {
                    pub++;
                } else if (((UmlOperation)list.get(i))
                        .getVisibility().equals(Visibility.PROTECTED)) {
                    pro++;
                } else if (((UmlOperation)list.get(i))
                        .getVisibility().equals(Visibility.PRIVATE)) {
                    pri++;
                } else if (((UmlOperation)list.get(i))
                        .getVisibility().equals(Visibility.PACKAGE)) {
                    pak++; }
            }
        }
        map.put(Visibility.PUBLIC,pub);
        map.put(Visibility.PROTECTED,pro);
        map.put(Visibility.PRIVATE,pri);
        map.put(Visibility.PACKAGE,pak);
        return map;
    }

    @Override
    public Visibility getClassAttributeVisibility(String s, String s1)
            throws ClassNotFoundException, ClassDuplicatedException
            , AttributeNotFoundException, AttributeDuplicatedException {
        ContainList temp = attributeLook.get(s);
        if (temp == null) {
            throw new ClassNotFoundException(s);
        } else if (temp.getClassJudge() == true) {
            throw new ClassDuplicatedException(s);
        }
        String name = s;
        ContainList temp1 = null;
        ContainList temp2 = temp.copy();
        while (true) {
            String id = classList.get(name).getElement().getId();
            String parent = parentLook.get(id);
            if (!idName.containsKey(parent)) {
                break; }
            name = idName.get(parent);
            temp1 = attributeLook.get(name);
            ArrayList<UmlElement> list1 = temp1.getList();
            for (int i = 0;i < list1.size();i++) {
                UmlAttribute attribute = (UmlAttribute) list1.get(i);
                temp2.add(attribute); }
        }
        if (!temp2.judgeIn(s1)) {
            throw new AttributeNotFoundException(s,s1);
        } else if (temp2.judgeDup(s1)) {
            throw new AttributeDuplicatedException(s,s1); }
        Visibility visibility =
                ((UmlAttribute)temp2.getElement(s1)).getVisibility();
        return visibility;
    }

    @Override
    public String getTopParentClass(String s)
            throws ClassNotFoundException, ClassDuplicatedException {
        ElementJudge temp = classList.get(s);
        if (temp == null) {
            throw new ClassNotFoundException(s);
        } else if (temp.getJudge() == true) {
            throw new ClassDuplicatedException(s); }
        String name = null;
        name = s;
        String id = null;
        String parentId = null;
        id = classList.get(name).getElement().getId();
        while (true) {
            parentId = parentLook.get(id);
            if (!idName.containsKey(parentId)) {
                break;
            }
            id = parentId;
        }
        return idName.get(id);
    }

    @Override
    public List<String> getImplementInterfaceList(String s)
            throws ClassNotFoundException, ClassDuplicatedException {
        ElementJudge temp = classList.get(s);
        if (temp == null) {
            throw new ClassNotFoundException(s);
        } else if (temp.getJudge() == true) {
            throw new ClassDuplicatedException(s);
        }
        List idlist = new ArrayList();
        HashMap<String,String> map = new HashMap();
        String name = s;
        while (true) {
            for (int i = 0; i < interfaceLook.get(name).size(); i++) {
                idlist.add(interfaceLook.get(name).get(i));
                map.put(interfaceLook.get(name)
                        .get(i),idName.get(interfaceLook.get(name).get(i)));
            }
            String id = classList.get(name).getElement().getId();
            String parent = parentLook.get(id);
            if (!idName.containsKey(parent)) {
                break;
            }
            name = idName.get(parent);
        }
        for (int i = 0;i < idlist.size();i++) {
            String idd = (String)idlist.get(i);
            ArrayList<String> parentList = interfaceparent.get(idd);
            if (!interfaceparent.containsKey(idd) || parentList.isEmpty()) {
                continue; }
            for (int j = 0;j < parentList.size();j++) {
                if (!map.containsKey(parentList.get(j))) {
                    idlist.add(parentList.get(j));
                    map.put(parentList.get(j),idName.get(parentList.get(j)));
                }
            }
        }
        for (int k = 1;k < idlist.size();) {
            boolean jud = false;
            for (int i = 0;i < k;i++) {
                if (idlist.get(i).equals(idlist.get(k))) {
                    jud = true;
                    break; }
            }
            if (jud) {
                idlist.remove(k);
            } else {
                k++;
            }
        }
        List list = new ArrayList();
        for (int i = 0;i < idlist.size();i++) {
            list.add(map.get(idlist.get(i)));
        }
        return list;
    }

    @Override
    public List<AttributeClassInformation> getInformationNotHidden(String s)
            throws ClassNotFoundException, ClassDuplicatedException {
        ContainList temp = attributeLook.get(s);
        if (temp == null) {
            throw new ClassNotFoundException(s);
        } else if (temp.getClassJudge()) {
            throw new ClassDuplicatedException(s);
        }
        List<AttributeClassInformation> list = new ArrayList();
        String name = s;
        while (true) {
            ContainList temp1 = attributeLook.get(name);
            ArrayList<UmlElement> arrayList = temp1.getList();
            for (int i = 0;i < arrayList.size();i++) {
                if (!((UmlAttribute)arrayList.get(i))
                        .getVisibility().equals(Visibility.PRIVATE)) {
                    AttributeClassInformation attributeClassInformation
                            = new AttributeClassInformation(
                            arrayList.get(i).getName(),name);
                    list.add(attributeClassInformation);
                }
            }
            String id = classList.get(name).getElement().getId();
            String parent = parentLook.get(id);
            if (!idName.containsKey(parent)) {
                break; }
            name = idName.get(parent);
        }
        return list;
    }
}
