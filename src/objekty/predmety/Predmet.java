package objekty.predmety;

import fri.shapesge.DataObrazku;
import fri.shapesge.Manazer;
import fri.shapesge.Obrazok;
import objekty.postavy.Charakter;
import objekty.SchopnostInterakcie;
import objekty.postavy.Hrac;
import spravcovia.HernaPlocha;

/**
 * Abstraktná trieda Predmet reprezentuje herný objekt, ktorý hráč môže zbierať a používať.
 * @author Emma Lujza Rothová
 */
public abstract class Predmet implements SchopnostInterakcie {
    private String nazovPredmetu;
    private String suborSObraskom;
    private Obrazok obrazok;
    private DataObrazku dataObrazku;
    private int poziciaX;
    private int poziciaY;
    private boolean zobranyPredmet;
    private HernaPlocha hernaPlocha;
    private Manazer manazer;
    private Hrac hrac;

    /**
     * Konštruktor triedy Predmet.
     * @param nazovPredmetu
     * @param obrazok
     * @param poziciaX
     * @param poziciaY
     */
    public Predmet(String nazovPredmetu, String obrazok, int poziciaX, int poziciaY) {
        this.nazovPredmetu = nazovPredmetu;
        this.poziciaX = poziciaX;
        this.poziciaY = poziciaY;
        this.suborSObraskom = obrazok;
        this.obrazok = new Obrazok(obrazok);
        this.obrazok.zmenPolohu(poziciaX, poziciaY);
        this.dataObrazku = new DataObrazku(obrazok);
        this.zobranyPredmet = false;
        this.hernaPlocha = null;
        this.manazer = null;
        this.hrac = null;
    }

    /**
     * Getter, ktorý vracia cestu k obrázku.
     * @return cesta k obrázku
     */
    public String getSuborSObraskom() {
        return this.suborSObraskom;
    }

    /**
     * Setter na nastavenie hernej plochy.
     * @param hernaPlocha
     */
    public void setHernaPlocha(HernaPlocha hernaPlocha) {
        this.hernaPlocha = hernaPlocha;
    }

    /**
     * Metóda, ktorá zabezpečuje zobrazenie predmetu na mape.
     */
    public void zobrazPredmet() {
        this.obrazok.zobraz();
    }

    /**
     * Metóda, ktorá zabezpečuje skrytie predmetu na mape.
     */
    public void skryPredmet() {
        this.obrazok.skry();
    }

    /**
     * Metóda, ktorá kontroluje či môže prebehnúť interakcia medzi predmetom a hráčom.
     * Metóda je volaná každých 10 ms.
     */
    public void kontrolujSchopnostInterakcie() {
        this.interakcia(this.hrac);
    }

    /**
     * Metóda, ktorá zabezpečuje, že hráč je schopný zobrať predmet.
     * @param hrac
     */
    @Override
    public void interakcia(Hrac hrac) {
        if (this.zobranyPredmet) {
            return;
        }

        if (this.kontrolaVzdialenostiPreInterakciu(hrac)) {
            hrac.setMozeZobratPredmet(true);
            hrac.setPredmetNaZobratie(this);

        } else {
            hrac.setMozeZobratPredmet(false);
            hrac.setPredmetNaZobratie(null);
        }
    }

    /**
     * Metóda, ktorá slúži na výpočet vzdialenosti medzi predmetom a hráčom.
     * @param hrac
     * @return vzdialenost
     */
    @Override
    public double vzdialenost(Hrac hrac) {
        double centrumXNpc = this.poziciaX + this.dataObrazku.getSirka() / 2;
        double centrumYNpc = this.poziciaY + this.dataObrazku.getVyska() / 2;
        double centrumXHrac = hrac.getPolohaX() + hrac.getSirkaObrazka() / 2;
        double centrumYHrac = hrac.getPolohaY() + hrac.getDlzkaObrazka() / 2;

        double vz = Math.sqrt(Math.pow((centrumXNpc - centrumXHrac), 2) + Math.pow((centrumYNpc - centrumYHrac), 2));
        return vz;
    }

    /**
     * Metóda, ktorá kontroluje, či je hráč v správnej vzdialenosti potrebnej k interakcii s predmetom.
     * @param hrac
     * @return true ak je v správnej vzdialenosti, inak false
     */
    @Override
    public boolean kontrolaVzdialenostiPreInterakciu(Hrac hrac) {
        if (this.vzdialenost(hrac) <= 50) {
            if (this.manazer == null) {
                this.manazer = new Manazer();
                this.manazer.spravujObjekt(this);
            }
            if (this.hrac == null) {
                this.hrac = hrac;
            }

            return true;
        }
        return false;
    }

    /**
     * Metóda, ktorá zabezpečuje zobranie predmetu a jeho odstránenie z mapy.
     */
    public void zober() {
        this.zobranyPredmet = true;
        this.skryPredmet();
        this.hernaPlocha.getPouzivanaMapa().odstranPredmet(this);
        this.manazer.prestanSpravovatObjekt(this);
    }

    /**
     * Abstraktná metóda, ktorá definuje na čo sa daný predmet použije.
     * @param charakter
     */
    public abstract void pouziPredmet(Charakter charakter);
}
