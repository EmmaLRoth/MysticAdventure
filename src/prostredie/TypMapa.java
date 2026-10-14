package prostredie;

/**
 * Enum TypMapa reprezentuje rôzne typy herných máp.
 * @author Emma Lujza Rothová
 */
public enum TypMapa {
    LES ("txtSubory/Mapa1.txt", "#426A5A", "#3C153B"),
    JASKYNA("txtSubory/Mapa2.txt", "#4D5061", "#1C2826"),
    HRAD("txtSubory/Mapa3.txt", "#B38CB4", "#70163C");

    private final String suborSMapou;
    private final String farba0;
    private final String farba1;

    /**
     * Konštruktor enumu TypMapa.
     * @param suborSMapou
     * @param farba0
     * @param farba1
     */
    TypMapa(String suborSMapou, String farba0, String farba1) {
        this.suborSMapou = suborSMapou;
        this.farba0 = farba0;
        this.farba1 = farba1;
    }

    /**
     * Getter, ktorý vracia názov súboru, ktorý obsahuje dáta o mape.
     * @return súbor s mapou
     */
    public String getSuborSMapou() {
        return this.suborSMapou;
    }

    /**
     * Getter, ktorý vracia farbu políčka pre plochu na mape, po ktorej je možný pohyb.
     * @return farba pre voľnú plochu
     */
    public String getFarba0() {
        return this.farba0;
    }

    /**
     * Getter, ktorý vracia farbu políčka predstavujúce prekážky.
     * @return farba pre prekážky
     */
    public String getFarba1() {
        return this.farba1;
    }
}
