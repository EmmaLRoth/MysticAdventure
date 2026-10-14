package objekty.predmety;

import objekty.postavy.Charakter;
import objekty.postavy.Hrac;

/**
 * Trieda AmuletOdvahy reprezentuje predmet v hre, ktorý môže hráč použiť na zvýšenie svojej sily.
 * @author Emma Lujza Rothová
 */
public class AmuletOdvahy extends Predmet {
    private int zosilnujuciUcinok;

    /**
     * Konštruktor triedy AmuletOdvahy.
     * @param nazovPredmetu
     * @param obrazok
     * @param poziciaX
     * @param poziciaY
     */
    public AmuletOdvahy(String nazovPredmetu, String obrazok, int poziciaX, int poziciaY) {
        super(nazovPredmetu, obrazok, poziciaX, poziciaY);
        this.zosilnujuciUcinok = 10;
    }

    /**
     * Metóda, ktorá umožňuje hráčovi použiť amulet a tým zvýšiť svoju silu.
     * @param charakter
     */
    @Override
    public void pouziPredmet(Charakter charakter) {
        if (charakter instanceof Hrac hrac) {
            hrac.zvysSilu(this.zosilnujuciUcinok);
        }
    }
}
