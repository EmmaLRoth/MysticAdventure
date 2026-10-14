package objekty;

import fri.shapesge.Obdlznik;
import objekty.postavy.Charakter;

import java.util.ArrayList;

/**
 * Trieda HealthBar reprezentuje ukazovateľ zdravia charakteru v hre.
 * @author Emma Lujza Rothová
 */
public class HealthBar {
    private Charakter charakter;
    private Obdlznik podklad;
    private Obdlznik ukazovatel;
    private int dlzkaUkazovatela;
    private ArrayList<Obdlznik> zoznamVytvorenychBarov;

    /**
     * Konštruktor triedy HealthBar.
     * @param charakter
     */
    public HealthBar(Charakter charakter) {
        this.charakter = charakter;
        this.dlzkaUkazovatela = 21;

        this.podklad = new Obdlznik(charakter.getPolohaX(), charakter.getPolohaY() - 12);
        this.podklad.zmenStrany(25, 6);
        this.podklad.zmenFarbu("black");

        this.ukazovatel = new Obdlznik(charakter.getPolohaX() + 2, charakter.getPolohaY() - 10);
        this.ukazovatel.zmenStrany(this.dlzkaUkazovatela, 2);
        this.ukazovatel.zmenFarbu("red");

        this.zoznamVytvorenychBarov = new ArrayList<Obdlznik>();
        this.zoznamVytvorenychBarov.add(this.podklad);
        this.zoznamVytvorenychBarov.add(this.ukazovatel);
    }

    /**
     * Metóda, ktorá zabezpečuje zobrazenie healthbaru v hre.
     */
    public void zobrazHealthBar() {
        this.podklad.zobraz();
        this.ukazovatel.zobraz();
    }

    /**
     * Metóda, ktorá zabezpečuje skrytie healthbaru v hre.
     */
    public void skryHealthBar() {
        this.podklad.skry();
        this.ukazovatel.skry();
    }

    /**
     * Metóda, ktorá zabezpečuje aktualizovanie polohy healthbaru podľa polohy charakteru.
     */
    public void akualizujPolohu() {
        this.podklad.zmenPolohu(this.charakter.getPolohaX(), this.charakter.getPolohaY() - 12);
        this.ukazovatel.zmenPolohu(this.charakter.getPolohaX() + 2, this.charakter.getPolohaY() - 10);
    }

    /**
     * Metóda, ktorá zabezpečuej aktualizovanie grafickej reprezentácie healthbaru podľa aktuálneho stavu zdravia charakteru.
     */
    public void aktualizujUkazovatel() {
        double percentoZdravia = (double)this.charakter.getZdravie() / this.charakter.getMaxZdravie();
        this.dlzkaUkazovatela = (int)Math.round(percentoZdravia * 21);
        this.ukazovatel.zmenStrany(this.dlzkaUkazovatela, 2);
    }

    /**
     * Metóda, ktorá zabezpečuje zobrazenie healthbaru na novej mape.
     */
    public void zobrazHealthBarNaNovejMape() {
        this.podklad = new Obdlznik(this.charakter.getPolohaX(), this.charakter.getPolohaY() - 12);
        this.podklad.zmenStrany(25, 6);
        this.podklad.zmenFarbu("black");

        this.ukazovatel = new Obdlznik(this.charakter.getPolohaX() + 2, this.charakter.getPolohaY() - 10);
        this.ukazovatel.zmenStrany(this.dlzkaUkazovatela, 2);
        this.ukazovatel.zmenFarbu("red");

        this.podklad.zobraz();
        this.ukazovatel.zobraz();

        this.zoznamVytvorenychBarov.add(this.podklad);
        this.zoznamVytvorenychBarov.add(this.ukazovatel);
    }

    /**
     * Metóda, ktorá zabezpečuje skrytie healthbaru na všetkých mapách.
     */
    public void skryHealthBarNaVsetkychMapach() {
        for (Obdlznik obd : this.zoznamVytvorenychBarov) {
            obd.skry();
        }
    }
}

