package objekty.predmety;

import objekty.postavy.Charakter;
import objekty.postavy.Hrac;

/**
 * Trieda ElixirZdravia reprezentuje predmet v hre, ktorý môže hráč použiť na zvýšenie svojho zdravia.
 * @author Emma Lujza Rothová
 */
public class ElixirZdravia extends Predmet {
    private int liecivaSila;

    /**
     * Konštruktor triedy ElixirZdravia.
     * @param nazovPredmetu
     * @param obrazok
     * @param poziciaX
     * @param poziciaY
     */
    public ElixirZdravia(String nazovPredmetu, String obrazok, int poziciaX, int poziciaY) {
        super(nazovPredmetu, obrazok, poziciaX, poziciaY);
        this.liecivaSila = 35;
    }

    /**
     * Metóda, ktorá umožňuje hráčovi použiť elixir a tým zvýšiť svoje zdravie.
     * @param charakter
     */
    @Override
    public void pouziPredmet(Charakter charakter) {
        if (charakter instanceof Hrac hrac) {
            hrac.zvysZdravie(this.liecivaSila);
        }
    }
}
