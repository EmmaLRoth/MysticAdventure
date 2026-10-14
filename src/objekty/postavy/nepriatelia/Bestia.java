package objekty.postavy.nepriatelia;

import fri.shapesge.Manazer;
import objekty.AnimovatelnyObjekt;
import objekty.Strela;

/**
 * Trieda Bestia reprezentuje nepriateľskú postavu v hre s útok na diaľku.
 * @author Emma Lujza Rothová
 */
public class Bestia extends Nepriatel implements AnimovatelnyObjekt {
    private TypBestia typBestia;
    private Manazer manazer;
    private boolean mozeStrielat;
    private int index;

    /**
     * Konštruktor triedy Bestia.
     * @param typBestia
     * @param polohaX
     * @param polohaY
     */
    public Bestia(TypBestia typBestia, int polohaX, int polohaY) {
        super(typBestia.getMeno(), 100, 20, polohaX, polohaY, typBestia.getObrazok(0));

        this.typBestia = typBestia;
        this.manazer = new Manazer();
        this.manazer.spravujObjekt(this);
        this.mozeStrielat = false;
        this.index = 1;
    }

    /**
     * Metóda, ktorá zabezpečuje v opakovaných intervaloch kontroluje či je útok možný,
     * ako aj samotné pravidelné útočenie.
     * Metóda sa volá každých 2000 ms.
     */
    public void tik() {
        if (!this.mozeStrielat) {
            return;
        }

        if (super.getMapa() != super.getHrac().getMapa()) {
            return;
        }

        if (super.getHrac().getZdravie() <= 0) {
            this.mozeStrielat = false;
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
     * Metóda, ktorá zebezpečuje útok, tým, že sa vytvorí strela namierená na hráča.
     */
    @Override
    public void utoc() {
        if (!this.mozeStrielat) {
            this.mozeStrielat = true;
        }

        Strela strela = new Strela(super.getPolohaX(), super.getPolohaY(), super.getHrac(), 1, super.getSila());
        strela.setMapa(super.getMapa());
    }

    /**
     * Metóda, ktorá zabezpečenie grafické prekeslovanie obrázka beštie.
     * Metóda je volaná každých 250 ms.
     */
    public void animuj() {
        if (this.index >= this.typBestia.getVelkostZonamuObrazkov()) {
            this.index = 0;
        }
        super.zmenObrazok(this.typBestia.getObrazok(this.index));
        this.index++;
    }
}
