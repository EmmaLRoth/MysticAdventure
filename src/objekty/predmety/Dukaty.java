package objekty.predmety;

import fri.shapesge.BlokTextu;
import fri.shapesge.StylFontu;
import objekty.postavy.Charakter;
import objekty.postavy.Npc;

import java.util.Random;

/**
 * Trieda Dukaty reprezentuje predmet v hre, ktorý môže hráč použiť na podplácenie npc.
 * @author Emma Lujza Rothová
 */
public class Dukaty extends Predmet {
    private int hodnota;
    private BlokTextu textHotnoty;

    /**
     * Konštruktor triedy Dukaty.
     * @param nazovPredmetu
     * @param obrazok
     * @param poziciaX
     * @param poziciaY
     */
    public Dukaty(String nazovPredmetu, String obrazok, int poziciaX, int poziciaY) {
        super(nazovPredmetu, obrazok, poziciaX, poziciaY);
        Random nahoda = new Random();
        this.hodnota = nahoda.nextInt(100) + 1;

        this.textHotnoty = new BlokTextu(String.valueOf(this.hodnota));
        this.textHotnoty.zmenPolohu(poziciaX, poziciaY - 5);
        this.textHotnoty.zmenFont("Arial", StylFontu.BOLD, 12);
    }

    /**
     * Metóda, ktorá zabezpečuje zobrazenie dukátov na mape aj s ich hodnotou.
     */
    public void zobrazPredmet() {
        super.zobrazPredmet();
        this.textHotnoty.zobraz();
    }

    /**
     * Metóda, ktorá zabezpečuje skrytie dukátov na mape aj s ich hodnotou.
     */
    public void skryPredmet() {
        super.skryPredmet();
        this.textHotnoty.skry();
    }

    /**
     * Getter, ktorý vracia hodnotu dukátov.
     * @return hodnota dukátov
     */
    public int getHodnota() {
        return this.hodnota;
    }

    /**
     * Setter na nastavenie hodnoty dukátov.
     * @param hodnota
     */
    public void setHodnota(int hodnota) {
        this.hodnota = hodnota;
    }

    /**
     * Metóda, ktorá umožňuje hráčovi použiť dukáty na podplatenie npc.
     * @param charakter
     */
    @Override
    public void pouziPredmet(Charakter charakter) {
        if (charakter instanceof Npc npc) {
            if (this.hodnota - npc.getVyskaUplatku() < 0) {
                return;
            }
            this.hodnota -= npc.getVyskaUplatku();
            npc.getHrac().podplatNpc();
        }
    }
}
