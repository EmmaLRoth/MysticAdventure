package prostredie;

/**
 * Trieda Prekazka reprezentuje prekážku v hre, ktorá obmedzuje pohyb charakterov.
 * @author Emma Lujza Rothová
 */
public class Prekazka {
    private final int xPravy;
    private final int yHorny;
    private final int xLavy;
    private final int yDolny;
    private final boolean charakter;

    /**
     * Konštruktor triedy Prekazka.
     * @param x
     * @param y
     * @param sirka
     * @param dlzka
     * @param velkostPolicka
     * @param charakter
     */
    public Prekazka(int x, int y, int sirka, int dlzka, int velkostPolicka, boolean charakter) {
        this.xLavy = x * velkostPolicka;
        this.xPravy = x * velkostPolicka + sirka;
        this.yHorny = y * velkostPolicka;
        this.yDolny = y * velkostPolicka + dlzka;
        this.charakter = charakter;
    }

    /**
     * Getter, ktorý vracia pravú pozíciu x prekážky.
     * @return pravá pozícia x
     */
    public int getxPravy() {
        return this.xPravy;
    }

    /**
     * Getter, ktorý vracia hornú pozíciu y prekážky.
     * @return horná pozícia y
     */
    public int getyHorny() {
        return this.yHorny;
    }

    /**
     * Getter, ktorý vracia ľavú pozíciu x prekážky.
     * @return ľavá pozícia x
     */
    public int getxLavy() {
        return this.xLavy;
    }

    /**
     * Getter, ktorý vracia dolnú pozíciu y prekážky.
     * @return dolnú pozícia x
     */
    public int getyDolny() {
        return this.yDolny;
    }

    /**
     * Getter, ktorý vracia, či prekážka je charakter.
     * @return true ak prekážka je charakter, inak false
     */
    public boolean isCharakter() {
        return this.charakter;
    }
}
