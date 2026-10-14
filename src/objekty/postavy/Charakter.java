package objekty.postavy;

import fri.shapesge.DataObrazku;

/**
 * Trieda Charakter reprezentuje postavu v hre.
 * @author Emma Lujza Rothová
 */
public class Charakter {
    private String meno;
    private int zdravie;
    private final int maxZdravie;
    private int sila;
    private int polohaX;
    private int polohaY;
    private DataObrazku dataObrazku;

    /**
     * Konštruktor triedy Charakter.
     * @param meno Meno postavy
     * @param zdravie Počiatočné zdravie postavy
     * @param sila Počiatočná sila postavy
     * @param polohaX Počiatočná pozícia x postavy
     * @param polohaY Počiatočná pozícia y postavy
     * @param obrazok Cesta k obrázku postavy
     */
    public Charakter(String meno, int zdravie, int sila, int polohaX, int polohaY, String obrazok) {
        this.meno = meno;
        this.zdravie = zdravie;
        this.maxZdravie = zdravie;
        this.sila = sila;
        this.polohaX = polohaX;
        this.polohaY = polohaY;
        this.dataObrazku = new DataObrazku(obrazok);
    }

    /**
     * Getter, ktorý vracia meno postavy.
     * @return meno postavy
     */
    public String getMeno() {
        return this.meno;
    }

    /**
     * Getter, ktorý vracia zdravie postavy.
     * @return zdravie postavy
     */
    public int getZdravie() {
        return this.zdravie;
    }

    /**
     * Getter, ktorý vracia maximálne/počiatočné zdravie postavy.
     * @return maximálne/počiatočné zdravie postavy
     */
    public int getMaxZdravie() {
        return this.maxZdravie;
    }

    /**
     * Getter, ktorý vracia silu postavy.
     * @return sila postavy
     */
    public int getSila() {
        return this.sila;
    }

    /**
     * Getter, ktorý vracia pozíciu x postavy.
     * @return pozícia x postavy
     */
    public int getPolohaX() {
        return this.polohaX;
    }

    /**
     * Getter, ktorý vracia pozíciu y postavy.
     * @return pozícia y postavy
     */
    public int getPolohaY() {
        return this.polohaY;
    }

    /**
     * Setter, ktorý slúži na zmenu pozície x postavy.
     * @param polohaX nová pozícia x postavy
     */
    public void setPolohaX(int polohaX) {
        this.polohaX = polohaX;
    }

    /**
     * Setter, ktorý slúži na zmenu pozície y postavy.
     * @param polohaY nová pozícia y postavy
     */
    public void setPolohaY(int polohaY) {
        this.polohaY = polohaY;
    }

    /**
     * Getter, ktorý vracia šírku obrázka postavy.
     * @return šírka obrázka
     */
    public int getSirkaObrazka() {
        return this.dataObrazku.getSirka();
    }

    /**
     * Getter, ktorý vracia dĺžku obrázka postavy.
     * @return dĺžka obrázka
     */
    public int getDlzkaObrazka() {
        return this.dataObrazku.getVyska();
    }

    /**
     * Metóda na zníženie zdravia postavy.
     * @param silaUtoku číslo o koľko sa ma zdravie znížiť
     */
    public void obdrzPoskodenie(int silaUtoku) {
        if (this.zdravie - silaUtoku <= 0) {
            this.zdravie = 0;
        } else {
            this.zdravie -= silaUtoku;
        }
    }

    /**
     * Metóda na zvýšenie zdravia postavy.
     * @param liecivaSila číslo o koľko sa má zdravie zvýšiť
     */
    public void zvysZdravie(int liecivaSila) {
        if (this.zdravie + liecivaSila >= this.maxZdravie) {
            this.zdravie = this.maxZdravie;
        } else {
            this.zdravie += liecivaSila;
        }
    }

    /**
     * Metóda na zvýšenie sily postavy.
     * @param zosilnujuciUcinok číslo o koľko sa má sila zvýšiť
     */
    public void zvysSilu(int zosilnujuciUcinok) {
        this.sila += zosilnujuciUcinok;
    }
}
