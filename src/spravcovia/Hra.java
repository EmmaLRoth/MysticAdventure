package spravcovia;

import fri.shapesge.BlokTextu;
import fri.shapesge.Manazer;
import fri.shapesge.Obdlznik;
import fri.shapesge.StylFontu;
import objekty.postavy.Hrac;
import prostredie.TypMapa;

/**
 * Trieda hra reprezentuje správcu priebehu hry.
 * @author Emma Lujza Rothová
 */
public class Hra {
    private StavHry stav;
    private Hrac hrac;
    private HernaPlocha hernaPlocha;
    private Manazer manazer;
    private BlokTextu nazovHry;
    private BlokTextu text;
    private Obdlznik obdlznik;
    private boolean uzZacala;

    /**
     * Koštruktor triedy Hra.
     */
    public Hra() {
        this.stav = StavHry.PREBIEHA;
        this.hrac = new Hrac("A", 90, 20);
        this.hernaPlocha = new HernaPlocha(TypMapa.LES, this.hrac);
        this.manazer = new Manazer();
        this.manazer.spravujObjekt(this);
        this.text = null;

        this.nazovHry = new BlokTextu("Mystic Adventure", 300, 200);
        this.nazovHry.zmenFont("Courier New", StylFontu.BOLD, 70);
        this.nazovHry.zmenFarbu("#43AA8B");
        this.nazovHry.zobraz();

        this.obdlznik = new Obdlznik(465, 270);
        this.obdlznik.zmenFarbu("#B2B09B");
        this.obdlznik.zmenStrany(350, 100);
        this.obdlznik.zobraz();

        StringBuilder zapisovac = new StringBuilder();
        zapisovac.append("Pre ŠTART stlač ENTER!\n");
        zapisovac.append("          :D          \n");
        this.text = new BlokTextu(zapisovac.toString(), 475, 315);
        this.text.zmenFarbu("black");
        this.text.zmenFont("Courier New", StylFontu.BOLD, 25);
        this.text.zobraz();

        this.uzZacala = false;
    }

    /**
     * Metóda, ktorá zabezpečuje spustenie hry a zobrazenie herného prostredia.
     * Metóda je volaná stlačením ENTER na klávesnici.
     */
    public void zacniHru() {
        if (this.uzZacala) {
            return;
        }
        this.hernaPlocha.nakresliHerneProstredie();
        this.nazovHry.skry();
        this.obdlznik.skry();
        this.text.skry();
        this.uzZacala = true;
    }

    /**
     * Metóda, ktorá zabezpečuje kontrolu priebehu hry a rozhoduje o jej ukončení.
     * Metóda je volaná každých 5 ms.
     */
    public void kontrolujPriebeh() {
        if (this.hrac.getZdravie() == 0) {
            this.stav = StavHry.PREHRA;
            this.ukonciHru();

        } else if (!this.hernaPlocha.getJeEsteMozneInteragovat()) {
            this.stav = StavHry.VYHRA;
            this.ukonciHru();
        }
    }

    /**
     * Metóda, ktorá zabezpečuje ukončenie hry a zobrazenie finálnej obrazovky s výsledkom hry.
     */
    public void ukonciHru() {
        this.hernaPlocha.vymazHerneProstredie();
        this.manazer.prestanSpravovatObjekt(this);
        this.nazovHry.zobraz();
        this.obdlznik.zobraz();

        StringBuilder zapisovac = new StringBuilder();
        if (this.stav == StavHry.PREHRA) {
            zapisovac.append(" Koniec hry. \n");
            zapisovac.append("Prehral si :(\n");
        } else {
            zapisovac.append(" Koniec hry.\n");
            zapisovac.append("Vyhral si :)\n");
        }
        this.text.zmenPolohu(540, 315);
        this.text.zmenText(zapisovac.toString());
        this.text.zobraz();
    }
}
