package uml;

import com.oocourse.uml1.models.elements.UmlElement;

public class ElementJudge {
    private UmlElement element;
    private boolean judge;

    public ElementJudge(UmlElement element) {
        this.element = element;
        this.judge = false;
    }

    public UmlElement getElement() {
        return element;
    }

    public boolean getJudge() {
        return judge;
    }

    public void set() {
        judge = true;
    }
}
