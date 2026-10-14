package objekty.postavy.nepriatelia;

import fri.shapesge.Obrazok;
import objekty.HealthBar;
import objekty.postavy.Charakter;
import objekty.postavy.Hrac;
import objekty.SchopnostInterakcie;
import prostredie.Mapa;
import spravcovia.HernaPlocha;
import spravcovia.SpravcaUtokov;

/**
 * Abstraktná trieda Nepriatel predstavuje základnú implementáciu nepriateľa v hre.
 * @author Emma Lujza Rothová
 */
public abstract class Nepriatel extends Charakter implements SchopnostInterakcie {
    private Obrazok obrazok;
    private HernaPlocha hernaPlocha;
    private SpravcaUtokov spravcaUtokov;
    private HealthBar healthBar;
    private Hrac hrac;
    private boolean vykonana;
    private Mapa mapa;

    /**
     * Konštruktor triedy Nepriatel.
     * @param meno
     * @param zdravie
     * @param sila
     * @param polohaX
     * @param polohaY
     * @param obrazok
     */
    public Nepriatel(String meno, int zdravie, int sila, int polohaX, int polohaY, String obrazok) {
        super(meno, zdravie, sila, polohaX, polohaY, obrazok);

        this.obrazok = new Obrazok(obrazok);
        this.obrazok.zmenPolohu(super.getPolohaX(), super.getPolohaY());

        this.hernaPlocha = null;
        this.mapa = null;
        this.hrac = null;
        this.spravcaUtokov = null;
        this.vykonana = false;

        this.healthBar = new HealthBar(this);
    }

    /**
     * Getter, ktorý vracia meno nepriateľa.
     * @return meno
     */
    public String getMeno() {
        return super.getMeno();
    }

    /**
     * Metóda, ktorá zabezpečuje vykreslenie nepriateľa a healthbaru na hernej ploche.
     */
    public void zobrazNepriatela() {
        this.obrazok.zobraz();
        this.healthBar.zobrazHealthBar();
    }

    /**
     * Metóda, ktorá zabezpečuje skrytie nepriateľa a healthbaru na hernej ploche.
     */
    public void skryNepriatela() {
        this.obrazok.skry();
        this.healthBar.skryHealthBar();
    }

    /**
     * Metóda, ktorá zebezpečuje aktualizovanie polohy nepriateľa a healthbaru v hernej ploche.
     */
    public void aktualizujPolohuObrazka() {
        this.obrazok.zmenPolohu(super.getPolohaX(), super.getPolohaY());
        this.healthBar.akualizujPolohu();
    }

    /**
     * Metóda, ktorá zabezpečuje zmenu obrázka nepriateľa.
     * @param obrazok
     */
    public void zmenObrazok(String obrazok) {
        this.obrazok.zmenObrazok(obrazok);
    }

    /**
     * Setter na nastavenie hernej plochy.
     * @param hernaPlocha
     */
    public void setHernaPlocha(HernaPlocha hernaPlocha) {
        this.hernaPlocha = hernaPlocha;
    }

    /**
     * Getter, ktorý vracia hernú plochu, na ktoeŕej je nepriateľ.
     * @return herná plocha
     */
    public HernaPlocha getHernaPlocha() {
        return this.hernaPlocha;
    }

    /**
     * Setter na nastavenie mapy, na ktorej sa nachádza nepriateľ.
     * @param mapa
     */
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    /**
     * Getter, ktorý vracia mapu, na ktorej sa nachádza nepriateľ.
     * @return mapa
     */
    public Mapa getMapa() {
        //return this.hernaPlocha.getPouzivanaMapa();
        return this.mapa;
    }

    /**
     * Metóda, ktorá ráta vzdialeniósť medzi nepriateľom a hráčom.
     * @param hrac
     * @return vzdialenosť
     */
    @Override
    public double vzdialenost(Hrac hrac) {
        double centrumXNepriatel = super.getPolohaX() + super.getSirkaObrazka() / 2;
        double centrumYNepriatel = super.getPolohaY() + super.getDlzkaObrazka() / 2;
        double centrumXHrac = hrac.getPolohaX() + hrac.getSirkaObrazka() / 2;
        double centrumYHrac = hrac.getPolohaY() + hrac.getDlzkaObrazka() / 2;

        double vz = Math.sqrt(Math.pow((centrumXNepriatel - centrumXHrac), 2) + Math.pow((centrumYNepriatel - centrumYHrac), 2));
        return vz;
    }

    /**
     * Metóda, ktorá kontroluje, či je hráč v správnej vzdialenosti potrebnej k interakcii.
     * @param hrac
     * @return true ak je v správnej vzdialenosti, inak false
     */
    @Override
    public boolean kontrolaVzdialenostiPreInterakciu(Hrac hrac) {
        if (this.vzdialenost(hrac) <= 150) {
            return true;
        }
        return false;

    }

    /**
     * Metóda, ktorá zabezpečuje vykonanie interakcie medzi hráčom a nepriateľom.
     * @param hrac
     */
    @Override
    public void interakcia(Hrac hrac) {
        if (this.hernaPlocha.getInteragujuceNpc() != null) {
            return;
        }

        if (this.vykonana) {
            return;
        }

        if (this.hernaPlocha.nastavAktivnuInterakciuNepriatel(this)) {
            this.hrac = hrac;
            this.spravcaUtokov = new SpravcaUtokov(hrac, this);
            this.vykonana = true;
            this.utoc();
        }
    }

    /**
     * Getter, ktorý vracia správcu útoku.
     * @return správca útoku
     */
    public SpravcaUtokov getSpravcaUtokov() {
        return this.spravcaUtokov;
    }

    /**
     * Getter, ktorý vracia hráča, s ktorým nepriateľ interaguje.
     * @return hráč
     */
    public Hrac getHrac() {
        return this.hrac;
    }

    /**
     * Metóda, ktorá na zníženie zdravia nepriateľa.
     * @param silaUtoku číslo o koľko sa ma zdravie znížiť
     */
    public void obdrzPoskodenie(int silaUtoku) {
        super.obdrzPoskodenie(silaUtoku);
        this.healthBar.aktualizujUkazovatel();
    }

    /**
     * Abstraktná metóda, na definovanie útoku nepriateľa.
     */
    public abstract void utoc();
}

