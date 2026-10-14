package objekty;

import fri.shapesge.Kruh;
import fri.shapesge.Manazer;
import fri.shapesge.Obrazok;
import objekty.postavy.Charakter;
import prostredie.Mapa;

/**
 * Trieda Strela reprezentuje strelu vystrelenú hráčom alebo neprieteľom v hre.
 * @author Emma Lujza Rothová
 */
public class Strela {
    private double surX;
    private double surY;
    private double smerSurX;
    private double smerSurY;
    private double rychlost;
    private int silaStrely;
    private int velkostStrely;
    private Obrazok obrazok;
    private Kruh kruh;
    private Manazer manazer;
    private Mapa mapa;
    private Charakter ciel;

    /**
     * Konštruktor triedy Strela.
     * @param zaciatokX
     * @param zaciatokY
     * @param ciel
     * @param rychlost
     * @param sila
     */
    public Strela(int zaciatokX, int zaciatokY, Charakter ciel, int rychlost, int sila) {
        this.surX = zaciatokX;
        this.surY = zaciatokY;
        this.velkostStrely = 10;
        this.silaStrely = sila;
        this.ciel = ciel;

        double euklidVz = Math.sqrt(Math.pow(zaciatokX - (this.ciel.getPolohaX() / 2), 2) + Math.pow(zaciatokY - (this.ciel.getPolohaY() / 2), 2));

        this.smerSurX = (this.ciel.getPolohaX() - zaciatokX) / euklidVz;
        this.smerSurY = (this.ciel.getPolohaY() - zaciatokY) / euklidVz;

        this.rychlost = rychlost;
        this.kruh = new Kruh((int)this.surX, (int)this.surY);
        this.kruh.zmenFarbu("#D6EEFF");
        this.kruh.zmenPriemer(this.velkostStrely);
        this.kruh.zobraz();

        this.manazer = new Manazer();
        this.manazer.spravujObjekt(this);

        this.mapa = null;
    }

    /**
     * Metóda, ktorá zabezpečuje aktualizovanie polohy strely na základe jej smeru a rýchlosti.
     */
    public void resetujPolohu() {
        this.surX += this.smerSurX * this.rychlost;
        this.surY += this.smerSurY * this.rychlost;

        this.kruh.zmenPolohu((int)this.surX, (int)this.surY);
    }

    /**
     * Metóda, ktorá zabezpečuje pohyb strely na mape.
     * Metóda je volaná každých 15 ms.
     */
    public void let() {
        if (this.mimoMapy()) {
            this.skryStrelu();
            this.manazer.prestanSpravovatObjekt(this);
            return;
        }

        if (this.narazNaPrekazku()) {
            this.skryStrelu();
            this.manazer.prestanSpravovatObjekt(this);
            return;
        }

        if (this.narazNaCiel()) {
            this.skryStrelu();
            this.ciel.obdrzPoskodenie(this.silaStrely);
            this.manazer.prestanSpravovatObjekt(this);
            return;
        }

        this.resetujPolohu();
    }

    /**
     * Metóda, ktorá kontroluje, či strela opustila hranice mapy.
     * @return true, ak je strela mimo mapy; false inak
     */
    public boolean mimoMapy() {
        if ((this.surX >= this.mapa.getKoncovaXMapy() || this.surX <= 0) || (this.surY >= this.mapa.getKoncovaYMapy() || this.surY <= 0)) {
            return true;
        }
        return false;
    }

    /**
     * Metóda, ktorá kontroluje, či strela narazila na prekážku.
     * @return true, ak strela narazila na prekážku; false inak
     */
    public boolean narazNaPrekazku() {
        return this.mapa.jePrekazkaPreStrely(this.surX, this.surY, this.velkostStrely);
    }

    /**
     * Metóda, ktorá kontroluje, či strela zasiahla cieľ.
     * @return true, ak strela zasiahla cieľ; false inak
     */
    public boolean narazNaCiel() {
        return this.mapa.jeStretSCielom(this.surX, this.surY, this.ciel, this.velkostStrely);

    }

    /**
     * Metóda, ktorá zabezpečuje skrytie strely z hernej plochy.
     */
    public void skryStrelu() {
        this.kruh.skry();
    }

    /**
     * Setter na nastavenie mapy, po ktorej sa strela pohybuje.
     * @param mapa
     */
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }
}

