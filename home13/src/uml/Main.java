package uml;

import com.oocourse.uml1.interact.AppRunner;

public class Main {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        AppRunner appRunner = AppRunner.newInstance(MyUmlInteraction.class);
        appRunner.run(args);
    }

}
