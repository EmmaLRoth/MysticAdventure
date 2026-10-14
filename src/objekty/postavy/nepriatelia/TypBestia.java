package objekty.postavy.nepriatelia;

/**
 * Enum TypBestia predstavuje rôzne typy beštií v hre.
 * @author Emma Lujza Rothová
 */
public enum TypBestia {
    STRIGA("Striga", new String[]{"pics/pixelStriga1.png", "pics/pixelStriga2.png", "pics/pixelStriga3.png", "pics/pixelStriga4.png"}),
    GLACIRON("Glaciron", new String[]{"pics/pixelGlaciron1.png", "pics/pixelGlaciron2.png", "pics/pixelGlaciron3.png", "pics/pixelGlaciron4.png"});

    private final String meno;
    private final String[] zoznamObrazkov;

    /**
     * Konštruktor enumu TypBestia.
     * @param meno
     * @param obrazok
     */
    TypBestia(String meno, String[] obrazok) {
        this.meno = meno;
        this.zoznamObrazkov = obrazok;
    }

    /**
     * Getter, ktorý vracia meno beštie.
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
