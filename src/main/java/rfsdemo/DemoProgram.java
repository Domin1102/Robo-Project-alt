package rfsdemo;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import api.connector.connector;
import api.control.RobotOperations;
import api.nav.Position;
import api.programs.RunnableProgram;

public class DemoProgram implements RunnableProgram {

    private static boolean keepRunning = true;

    @Override
    public void runProgram(RobotOperations robot) {

        MainProgram demo = new MainProgram();

        while (keepRunning) {
            robot.runProgram(demo);
        }

        robot.delay(10);
        robot.destroy();
    }

    private void moveObjectFromTo(RobotOperations ops, DemoPoints from, DemoPoints to) {
        ops.movToPositionWithSafeTravel(from.getPosition());
        ops.grab();
        //ops.suck();
        ops.mvsToPosition(from.getPosition().alterZ(-50));
        ops.movToPositionWithSafeTravel(to.getPosition());
        //ops.suck();
        ops.drop();
        //ops.notsuck();
        ops.mvsToPosition(to.getPosition().alterZ(-50));
    }
    
    private void moveFromTischToFraese(RobotOperations ops, DemoPoints from, DemoPoints to, DemoPoints safe, DemoPoints pickup) {
    	ops.movToPositionWithSafeTravel(from.getPosition());
    	ops.movToPosition(from.getPosition());
    	ops.movToPosition(pickup.getPosition());
    	ops.delay(2);
    	//ops.movToPosition(from.getPosition());
    	//ops.grab();
    	ops.movToPosition(safe.getPosition());
    	ops.movToPositionWithSafeTravel(to.getPosition());
    	//ops.drop();
//    	ops.delay(1);
    	//ops.suck();
//    	ops.delay(5);
//    	ops.movToPosition(to.getPosition().alterZ(-50));
//    	ops.movToPosition(from.getPosition().alterZ(-50));
//    	ops.delay(5);
    	//ops.notsuck();
//    	ops.delay(3);
    }
    
    private void moveFromFraeseToTisch(RobotOperations ops, DemoPoints from, DemoPoints to) {
    	ops.movToPositionWithSafeTravel(to.getPosition());
    	//ops.grab();
    	ops.mvsToPosition(to.getPosition().alterZ(-50));
    	ops.movToPositionWithSafeTravel(from.getPosition());
    	//ops.drop();
    	ops.delay(5);
    	ops.mvsToPosition(from.getPosition().alterZ(-50));
    }

    private class MainProgram implements RunnableProgram {
        @Override
        public void runProgram(RobotOperations robot) {
        	
        	/*
        	connector c = new connector();
            String IP = "172.17.200.213";
            
            try {
                c.initiate(IP);
                System.out.println("Initialisiert");
				String rec = c.receive();
				if (rec.equals("initialize")) {
					System.out.println("Initialisiert");
					//robot.runProgram(new FromTischToFraese()); //zu von tisch zu fräse
					c.send("ready");
				}
				else if (rec.equals("pickup")) {
					//robot.runProgram(new FromFraeseToTisch()); //pickup nach bearbeitung
					c.send("ready");
				}
				else if (rec.equals("test")) {
					c.send("test");
				}
				c.terminate();
            } catch (IOException e) {
            	
            }
            */
            
        	robot.runProgram(new FromTischToFraese());
        	robot.runProgram(new FromFraeseToTisch());
            //robot.runProgram(new ToFraese());
            //robot.runProgram(new Rearrange());
            //robot.runProgram(new FromFraese());
        }
    }
    
    private class FromTischToFraese implements RunnableProgram {

		@Override
		public void runProgram(RobotOperations robot) {
			robot.movToPosition(DemoPoints.PUFFER.getPosition());
			moveFromTischToFraese(robot, DemoPoints.POS5, DemoPoints.TABLE, DemoPoints.SAFEPOS, DemoPoints.PICKUP);
			robot.movToPosition(DemoPoints.PUFFER.getPosition());
		}
    }
    
    private class FromFraeseToTisch implements RunnableProgram {

		@Override
		public void runProgram(RobotOperations robot) {
			robot.movToPosition(DemoPoints.PUFFER.getPosition());
			moveFromFraeseToTisch(robot, DemoPoints.TABLE, DemoPoints.POS5);
			robot.movToPosition(DemoPoints.PUFFER.getPosition());
		}
    }

    /**
     * Sortiert die Wekstücke um
     * Pos1 -> Pos2
     * Pos2 -> Pos3
     * Pos3 -> Pos4
     * Pos4 -> Pos5
     */
    private class Rearrange implements RunnableProgram {
        @Override
        public void runProgram(RobotOperations robot) {
            moveObjectFromTo(robot, DemoPoints.POS4, DemoPoints.POS5);
            moveObjectFromTo(robot, DemoPoints.POS3, DemoPoints.POS4);
            moveObjectFromTo(robot, DemoPoints.POS2, DemoPoints.POS3);
            moveObjectFromTo(robot, DemoPoints.POS1, DemoPoints.POS2);
        }
    }

    /**
     * Geht über einen Pufferpunkt von Position 5 zur Fräse
     * Puffer -> Pos5 -> grab -> P_Fräse -> drop -> Puffer
     */
    private class ToFraese implements RunnableProgram {
        @Override
        public void runProgram(RobotOperations robot) {
            robot.movToPosition(DemoPoints.PUFFER.getPosition());
            moveObjectFromTo(robot, DemoPoints.POS5, DemoPoints.TABLE);
            robot.movToPosition(DemoPoints.PUFFER.getPosition());
        }
    }
    
    /**
     * Geht über einen sicheren Pufferpunkt von der Fräse zu Position 1
     * Puffer -> P_Fräse -> grab -> Pos1 -> drop -> Puffer
     */
    private class FromFraese implements RunnableProgram {
        @Override
        public void runProgram(RobotOperations robot) {
            robot.movToPosition(DemoPoints.PUFFER.getPosition());
            moveObjectFromTo(robot, DemoPoints.FRAESE, DemoPoints.POS1);
            robot.movToPosition(DemoPoints.PUFFER.getPosition());
        }
    }

    /**
     * Wird nur ganz am Anfang benötigt um die Werkstücke zu platzieren
     */
    private class PickupHelper implements RunnableProgram {
        @Override
        public void runProgram(RobotOperations robot) {
            final double heightObject = 3.0;
            Position tmp = DemoPoints.PICKUP.getPosition().alterAbsoluteZ(200.0);
            moveObjectFromTo(robot, DemoPoints.PICKUP, DemoPoints.POS1);
            robot.movToPosition(DemoPoints.PUFFER.getPosition());
            tmp.setZ(tmp.getZ() - heightObject);

            moveObjectFromTo(robot, DemoPoints.PICKUP, DemoPoints.POS2);
            robot.movToPosition(DemoPoints.PUFFER.getPosition());
            tmp.setZ(tmp.getZ() - heightObject);

            moveObjectFromTo(robot, DemoPoints.PICKUP, DemoPoints.POS3);
            robot.movToPosition(DemoPoints.PUFFER.getPosition());
            tmp.setZ(tmp.getZ() - heightObject);

            moveObjectFromTo(robot, DemoPoints.PICKUP, DemoPoints.POS4);
            robot.movToPosition(DemoPoints.PUFFER.getPosition());
            tmp.setZ(tmp.getZ() - heightObject);

            moveObjectFromTo(robot, DemoPoints.PICKUP, DemoPoints.POS5);
            robot.movToPosition(DemoPoints.PUFFER.getPosition());
        }
    }
}
