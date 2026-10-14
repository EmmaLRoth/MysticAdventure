package objekty.postavy.nepriatelia;

import fri.shapesge.Manazer;
import objekty.AnimovatelnyObjekt;

/**
 * Trieda Golem reprezentuje nepriateľskú postavu v hre s útok na blízko.
 * @author Emma Lujza Rothová
 */
public class Golem extends Nepriatel implements AnimovatelnyObjekt {
    private TypGolem typGolem;
    private Manazer manazer;
    private boolean mozeUtocit;
    private int index;

    /**
     * Konštruktor triedy Golem.
     * @param typGolem
     * @param polohaX
     * @param polohaY
     */
    public Golem(TypGolem typGolem, int polohaX, int polohaY) {
        super(typGolem.getMeno(), 100, 20, polohaX, polohaY, typGolem.getObrazok(0));
        this.typGolem = typGolem;
        this.manazer = new Manazer();
        this.manazer.spravujObjekt(this);
        this.mozeUtocit = false;
        this.index = 1;
    }

    /**
     * Metóda, ktorá zabezpečuje v opakovaných intervaloch kontroluje či je útok možný,
     * ako aj samotné pravidelné útočenie.
     * Metóda sa volá každých 2000 ms.
     */
    public void tik() {
        if (!this.mozeUtocit) {
            return;
        }

        if (this.getMapa() != this.getHrac().getMapa()) {
            return;
        }

        if (super.getHrac().getZdravie() <= 0) {
            this.mozeUtocit = false;
            super.getSpravcaUtokov().ukonciUtok();
            super.getHernaPlocha().zrusAktivnuInterakciuNepriatel();
            return;
        }

        if (super.getZdravie() <= 0) {
            this.manazer.prestanSpravovatObjekt(this);
            super.getSpravcaUtokov().ukonciUtok();
            super.getHernaPlocha().zrusAktivnuInterakciuNepriatel();
            return;
        }

        this.utoc();
    }

    /**
     * Metóda, ktorá zabezpečuje pohyb golema smerom k hráčovi.
     * Metóda sa volá každých 50 ms.
     */
    public void hybSa() {
        if (!this.mozeUtocit) {
            return;
        }

        this.pohybSmeromKHracovi();
    }

    /**
     * Metóda, ktorá zabezpečuje útok na hráča a zmení grafické zobrazenie golema pri zásahu.
     */
    @Override
    public void utoc() {
        if (!this.mozeUtocit) {
            super.getMapa().odstranObjektovuPrekazku(getPolohaX(), getPolohaY());
            this.mozeUtocit = true;
        }

        if (super.vzdialenost(super.getHrac()) <= 45) {
            super.getHrac().obdrzPoskodenie(super.getSila());
            super.zmenObrazok(this.typGolem.getObrazok(4));
        }
    }

    /**
     * Metóda, ktorá počíta optimálny pohyb smerom k hráčovi a aktualizuje polohu golema.
     */
    public void pohybSmeromKHracovi() {
        double euklV = super.vzdialenost(super.getHrac());

        if (euklV <= 45) {
            return;
        }

        double smerX = (super.getHrac().getPolohaX() - super.getPolohaX()) / euklV;
        double smerY = (super.getHrac().getPolohaY() - super.getPolohaY()) / euklV;
        double rychlost = 5;

        int novaPolohaX = (int)(super.getPolohaX() + smerX * rychlost);
        int novaPolohaY = (int)(super.getPolohaY() + smerY * rychlost);

        if (super.getMapa().jePrekazkaPreCharakter(novaPolohaX, novaPolohaY, this)) {
            return;
        }

        super.setPolohaX((int)(super.getPolohaX() + smerX * rychlost));
        super.setPolohaY((int)(super.getPolohaY() + smerY * rychlost));
        super.aktualizujPolohuObrazka();
    }

    /**
     * Metóda, ktorá zabezpečenie grafické prekeslovanie obrázka golema.
     * Metóda je volaná každých 250 ms.
     */
    @Override
    public void animuj() {
        if (this.index >= this.typGolem.getVelkostZonamuObrazkov() - 1) {
            this.index = 0;
        }
        super.zmenObrazok(this.typGolem.getObrazok(this.index));
        this.index++;
    }
}
