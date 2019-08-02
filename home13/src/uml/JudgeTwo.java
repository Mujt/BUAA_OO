package uml;

public class JudgeTwo {
    private boolean judge1;
    private boolean judge2;

    public JudgeTwo() {
        judge1 = false;
        judge2 = false;
    }

    public void set1() {
        judge1 = true;
    }

    public void set2() {
        judge2 = true;
    }

    public boolean get1() {
        return judge1;
    }

    public boolean get2() {
        return judge2;
    }
}
