import api.commands.MelfaBasic4CommandSet;
import api.control.Robot;
import api.control.RobotBuilder;
import api.nav.Position;
import api.online.Terminal;
import rfsdemo.DemoProgram;
import yourprograms.TestProgramForF113;

public class Starter {

    private static final String HOST = "192.168.1.223";
    private static final int PORT = 10001;

    private static final Position SAFE_POSITION = new Position(420.0, 0.0, 300.0, 180, 0, 180);

    public static void main(String[] args) {
        Robot robot = new RobotBuilder(HOST, PORT)
//                .disableSecureStartup()
//                .setCommandSet(MelfaBasic4CommandSet.getCommandSet())
//                .setName("RV-3SB")
//                .build();
                .setSafePosition(SAFE_POSITION)
                .enableCommunication()
                .enableOperation()
                .enableServo()
                .setSpeed(5)
                .setName("RV-3SB")
                .setCommandSet(MelfaBasic4CommandSet.getCommandSet())
                .build();

        robot.runProgram(new DemoProgram());
        // robot.runProgram(new TestProgramForF113());

        // Terminal terminal = new Terminal(robot);
        // terminal.open();
    }

}