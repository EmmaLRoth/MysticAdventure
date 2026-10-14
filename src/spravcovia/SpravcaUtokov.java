package spravcovia;

import objekty.postavy.Hrac;
import objekty.postavy.nepriatelia.Nepriatel;

/**
 * Trieda SpravcaUtokov reprezentuje správcu bojov medzi hráčom a nepriateľom.
 * @author Emma Lujza Rothová
 */
public class SpravcaUtokov {
    private Hrac hrac;
    private Nepriatel nepriatel;

    /**
     * Konštruktor triedy SpravcaUtokov.
     * @param hrac
     * @param nepriatel
     */
    public SpravcaUtokov(Hrac hrac, Nepriatel nepriatel) {
        this.hrac = hrac;
        this.nepriatel = nepriatel;
    }

    /**
     * Metóda, ktorá zabezpečuje ukončenie útoku.
     */
    public void ukonciUtok() {
        if (this.nepriatel.getZdravie() <= 0) {
            this.nepriatel.skryNepriatela();
            this.nepriatel.getMapa().odstranObjektovuPrekazku(this.nepriatel.getPolohaX(), this.nepriatel.getPolohaY());
            this.nepriatel.getMapa().odstranNepriatela(this.nepriatel);
            this.nepriatel = null;
        }

        if (this.hrac.getZdravie() <= 0) {
            this.hrac.skryHraca();
            this.hrac = null;
        }
    }
}
