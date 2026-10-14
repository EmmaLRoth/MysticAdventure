package prostredie;

import fri.shapesge.DataObrazku;
import fri.shapesge.Obrazok;
import objekty.postavy.Hrac;
import spravcovia.HernaPlocha;

import java.util.Random;

/**
 * Trieda Prechod reprezentuje prechodový bod v hre, ktorý umožňuje zmenu mapy.
 * @author Emma Lujza Rothová
 */
public class Prechod {
    private Obrazok prechod;
    private final int xPravy;
    private final int yHorny;
    private final int xLavy;
    private final int yDolny;
    private HernaPlocha hernaPlocha;

    /**
     * Konštruktor triedy Prechod.
     * @param x
     * @param y
     * @param velkost
     */
    public Prechod(int x, int y, int velkost) {
        DataObrazku data = new DataObrazku("pics/portal.png");
        int sirka = data.getSirka();
        int vyska = data.getVyska();

        this.xLavy = x * velkost;
        this.xPravy = x * velkost + sirka;
        this.yHorny = y * velkost;
        this.yDolny = y * velkost + vyska;

        this.prechod = new Obrazok("pics/portal.png");
        this.prechod.zmenPolohu(this.xLavy, this.yHorny);

        this.hernaPlocha = null;
    }

    /**
     * Metóda, ktorá zabezpečuje zobrazenie prechodu na mape.
     */
    public void zobrazPrechod() {
        this.prechod.zobraz();
    }

    /**
     * Metóda, ktorá zabezpečuje skrytie prechodu na mape.
     */
    public void skryPrechod() {
        this.prechod.skry();
    }

    /**
     * Metóda, ktorá zabezpečuje kontrolu, či hráč prešiel cez prechod.
     * @param hrac
     * @return true ak hráč prešiel cez prechod, false inak.
     */
    public boolean presielZa(Hrac hrac) {
        int pozXVpravo = hrac.getPolohaX() + hrac.getSirkaObrazka();
        int pozYDole = hrac.getPolohaY() + hrac.getDlzkaObrazka();

        boolean prechodX = false;
        boolean prechodY = false;

        if (pozXVpravo > this.xLavy && hrac.getPolohaX() < this.xPravy) {
            prechodX = true;
        }
        if (pozYDole > this.yHorny && hrac.getPolohaY() + 45 < this.yDolny) {
            prechodY = true;
        }
        if (prechodX && prechodY) {
            return true;
        }
        return false;
    }

    /**
     * Metóda, ktorá zabezpečuje zmenu aktuálnej mapy na inú náhodne vybranú mapu.
     */
    public void zmenMapu() {
        Random nahoda = new Random();
        TypMapa typMapa = this.hernaPlocha.getPouzivanaMapa().getTypMapa();

        Mapa mapa = this.hernaPlocha.getPouzivanaMapa();

        while (mapa.getTypMapa() == typMapa) {
            typMapa = TypMapa.values()[nahoda.nextInt(TypMapa.values().length)];
        }

        this.hernaPlocha.zmenMapu(typMapa);
    }

    /**
     * Setter na nastavenie hernej plochy.
     * @param hernaPlocha
     */
    public void setHernaPlocha(HernaPlocha hernaPlocha) {
        this.hernaPlocha = hernaPlocha;
    }
}
