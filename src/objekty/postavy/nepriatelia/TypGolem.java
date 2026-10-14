package objekty.postavy.nepriatelia;

/**
 * Enum TypGolem predstavuje rôzne typy golemov v hre.
 * @author Emma Lujza Rothová
 */
public enum TypGolem {
    OBRANCA("Obranca", new String[]{"pics/pixelWarrior1.png", "pics/pixelWarrior2.png", "pics/pixelWarrior3.png", "pics/pixelWarrior4.png", "pics/pixelWarrior5.png"}),
    STRAZCA("Strážca", new String[]{"pics/pixelGuardian1.png", "pics/pixelGuardian2.png", "pics/pixelGuardian3.png", "pics/pixelGuardian4.png", "pics/pixelGuardian5.png"});

    private final String meno;
    private final String[] zoznamObrazkov;

    /**
     * Konštruktor enumu TypGolem.
     * @param meno
     * @param obrazok
     */
    TypGolem(String meno, String[] obrazok) {
        this.meno = meno;
        this.zoznamObrazkov = obrazok;
    }

    /**
     * Getter, ktorý vracia meno golema.
     * @return meno
     */
    public String getMeno() {
        return this.meno;
    }

    /**
     * Getter, ktorý vracia cestu k obrázku na základe indexu i.
     * @param i
     * @return cesta k obrázku
     */
    public String getObrazok(int i) {
        return this.zoznamObrazkov[i];
    }

    /**
     * Getter, ktorý vracia počet obrázkov dostupných v zozname.
     * @return počet obrázkov
     */
    public int getVelkostZonamuObrazkov() {
        return this.zoznamObrazkov.length;
    }
}
