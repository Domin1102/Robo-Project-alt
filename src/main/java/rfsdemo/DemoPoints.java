package rfsdemo;

import api.nav.Position;

/**
 * Punkte für das Demo-Programm für den 25.03.26
 */
public enum DemoPoints {

    PUFFER(new Position(300.0, 0.0, 300.0, 180.0, 0.0, 180.0)),
    FRAESE(new Position(384.88, 299.96, 207.07, 180.0, 0.0, 180.0)),
    TABLE(new Position(141.15, 409.21, 301.384, 180.0, 0.0, -90.0)),  //100 z nach oben
    //PICKUP(new Position(392.0, 0.0, 134.0, 180.0, 0.0, 180.0)),
    SAFEPOS(new Position(420.0, 0.0, 300.0, 180, 0, 180)),
    POS1(new Position(220.0, -350.0, DemoConstants.Z_INDEX_ARBEITSPLATTE, 180.0, 0.0, 180.0)),
    POS2(new Position(-200.0, -340.0, DemoConstants.Z_INDEX_ARBEITSPLATTE, 180.0, 0.0, 180.0)),
    POS3(new Position(-150.0, -480.0, DemoConstants.Z_INDEX_ARBEITSPLATTE-1, 180.0, 0.0, 180.0)),
    POS4(new Position(200.0, -500.0, DemoConstants.Z_INDEX_ARBEITSPLATTE, 180.0, 0.0, 180.0)),
    POS5(new Position(7.5, -489.7, DemoConstants.Z_INDEX_ARBEITSPLATTE-2, 180.0, 0.0, 180.0)),
    PICKUP(new Position(7.5, -489.7, 129, 180.0, 0.0, 180.0))
    ;

    /**
     * Wird benötigt um Konstanten im Enum zu verwenden
     */
    private static class DemoConstants {
        private static final double Z_INDEX_ARBEITSPLATTE = 179; //50 z nach oben
    }

    private final Position position;

    DemoPoints(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
