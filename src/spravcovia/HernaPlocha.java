package spravcovia;

import fri.shapesge.BlokTextu;
import fri.shapesge.Obrazok;
import fri.shapesge.StylFontu;
import objekty.postavy.Hrac;
import objekty.postavy.nepriatelia.Nepriatel;
import objekty.postavy.Npc;
import prostredie.Mapa;
import prostredie.TypMapa;

import java.io.IOException;
import java.util.HashMap;

/**
 * Trieda HernaPlocha reprezentuje herne postredie celej hry.
 * @author Emma Lujza Rothová
 */
public class HernaPlocha {
    private Mapa pouzivanaMapa;
    private Hrac hrac;
    private Obrazok informativneOkno;
    private BlokTextu nazovHry;
    private BlokTextu pravidlaNazov;
    private BlokTextu pravidlaHrania;
    private Npc interagujuceNpc;
    private Nepriatel utociaciNepriatel;
    private boolean aktivnaInterakcia;
    private HashMap<TypMapa, Mapa> zoznamDostupnychMap;

    /**
     * Konštruktor triedy HernaPlocha.
     * @param typPouzivanaMapa
     * @param hrac
     */
    public HernaPlocha(TypMapa typPouzivanaMapa, Hrac hrac) {
        this.zoznamDostupnychMap = new HashMap<TypMapa, Mapa>();
        for (TypMapa typ : TypMapa.values()) {
            Mapa mapa = new Mapa(typ);
            mapa.setHernaPlocha(this);
            try {
                mapa.nacitajMapu();
            } catch (IOException e) {
                System.out.println("Chyba pri načítavaní mapy!");
            }
            this.zoznamDostupnychMap.put(typ, mapa);
        }

        this.pouzivanaMapa = this.zoznamDostupnychMap.get(typPouzivanaMapa);

        this.hrac = hrac;
        this.hrac.setHernaPlocha(this);
        this.hrac.setMapa(this.pouzivanaMapa);

        this.informativneOkno = new Obrazok("pics/DialogWindow1.png");
        this.informativneOkno.zmenPolohu(this.pouzivanaMapa.getKoncovaXMapy(), 0);

        this.nazovHry = new BlokTextu("Mystic Adventure");
        this.nazovHry.zmenPolohu(this.pouzivanaMapa.getKoncovaXMapy() + 32, 50);
        this.nazovHry.zmenFarbu("#2B062E");
        this.nazovHry.zmenFont("Courier New", StylFontu.BOLD, 30);

        this.pravidlaNazov = new BlokTextu("Pravidla hrania:");
        this.pravidlaNazov.zmenPolohu(this.pouzivanaMapa.getKoncovaXMapy() + 32, 430);
        this.pravidlaNazov.zmenFarbu("#2B062E");
        this.pravidlaNazov.zmenFont("Courier New", StylFontu.BOLD, 20);

        StringBuilder pravidla = new StringBuilder();
        pravidla.append("Pre pohyb stlač ŠÍPKY.\n");
        pravidla.append("Pre interakciu s NPC stlač SPACE.\n");
        pravidla.append("Pre výber možnosti1 stlač A.\n");
        pravidla.append("Pre výber možnosti2 stlač B.\n");
        pravidla.append("Pre výber možnosti3 stlač C.\n");
        pravidla.append("Pre podplatenie NPC stlač D\n");
        pravidla.append("Pre vykonanie útoku stlač X.\n");
        pravidla.append("Pre zobratie predmetu stlač E.\n");
        pravidla.append("Pre použitie predmetu stlač F1-F8.\n");

        this.pravidlaHrania = new BlokTextu(pravidla.toString());
        this.pravidlaHrania.zmenPolohu(this.pouzivanaMapa.getKoncovaXMapy() + 32, 452);
        this.pravidlaHrania.zmenFarbu("black");
        this.pravidlaHrania.zmenFont("Courier New", StylFontu.BOLD, 13);

        this.aktivnaInterakcia = false;
        this.interagujuceNpc = null;
        this.utociaciNepriatel = null;
    }

    /**
     * Metóda, ktorá zabezpečuje vykreslenie celej hernej plochy, vrátane mapy, objektov a informačného panela.
     */
    public void nakresliHerneProstredie() {
        this.pouzivanaMapa.nakresliMapu();
        this.pouzivanaMapa.zobrazObjekty();
        this.hrac.zobrazHraca();
        this.informativneOkno.zobraz();
        this.nazovHry.zobraz();
        this.pravidlaNazov.zobraz();
        this.pravidlaHrania.zobraz();
    }

    /**
     * Metóda, ktorá zabezpečuje vymazanie celého herného prostredia, vrátane mapy, objektov a informačného panela.
     */
    public void vymazHerneProstredie() {
        for (Mapa mapa : this.zoznamDostupnychMap.values()) {
            mapa.skryObjekty();
            mapa.skryMapu();
        }
        this.hrac.skryHracaNaVsetkychMapach();
        this.hrac.skryInventar();
        this.informativneOkno.skry();
        this.nazovHry.skry();
        this.pravidlaNazov.skry();
        this.pravidlaHrania.skry();
    }

    /**
     * Metóda, ktorá zabezpečuje nastavenie npc ako aktívnej interakcie v hernom prostredí.
     * @param npc
     * @return true, ak sa interakcia úspešne nastaví, false ak už existuje nastavení interakcia s npc
     */
    public boolean nastavAktivnuInterakciuNpc(Npc npc) {
        if (this.interagujuceNpc != null) {
            return false;
        }

        this.interagujuceNpc = npc;
        return true;
    }

    /**
     * Metóda, ktorá zabezpečuje nastavenie nepriateľa ako aktívnej interakcie v hernom prostredí.
     * @param nepriatel
     * @return true, ak sa interakcia úspešne nastaví, false ak už existuje nastavení interakcia s nepriateľom
     */
    public boolean nastavAktivnuInterakciuNepriatel(Nepriatel nepriatel) {
        if (this.utociaciNepriatel != null) {
            return false;
        }

        this.utociaciNepriatel = nepriatel;
        return true;
    }

    /**
     * Getter, ktorý vracia hráča herného prostredia.
     * @return hráč
     */
    public Hrac getHrac() {
        return this.hrac;
    }

    /**
     * Getter, ktorý vracia interagujúce npc.
     * @return interagujúce npc
     */
    public Npc getInteragujuceNpc() {
        return this.interagujuceNpc;
    }

    /**
     * Getter, ktorý vracia interagujúceho nepriateľa.
     * @return interagujúci nepriateľ
     */
    public Nepriatel getUtociaciNepriatel() {
        return this.utociaciNepriatel;
    }

    /**
     * Setter na nastavenie aktívnej intrakcie.
     */
    public void setAktivnaInterakcia() {
        this.aktivnaInterakcia = true;
    }

    /**
     * Getter, ktorý vracia či v hernom prostredí prebieha aktívna interakcia.
     * @return true ak interakcia prebieha, inak false
     */
    public boolean getAktivnaInterakcia() {
        return this.aktivnaInterakcia;
    }

    /**
     * Metóda, ktorá zabezpečuje ukončenie aktívnej interakcie s npc.
     */
    public void zrusAktivnuInterakciuNpc() {
        if (this.interagujuceNpc != null) {
            this.interagujuceNpc = null;
            this.aktivnaInterakcia = false;
        }
    }

    /**
     * Metóda, ktorá zabezpečuje ukončenie aktívnej interakcie s nepriateľom.
     */
    public void zrusAktivnuInterakciuNepriatel() {
        if (this.utociaciNepriatel != null) {
            this.utociaciNepriatel = null;
            this.aktivnaInterakcia = false;
        }
    }

    /**
     * Getter, ktorý vracia práve používanú mapu.
     * @return používaná mapa
     */
    public Mapa getPouzivanaMapa() {
        return this.pouzivanaMapa;
    }

    /**
     * Metóda, ktorá zabezpečuje zmenu aktuálnej mapy na inú mapu.
     * @param typMapa
     */
    public void zmenMapu(TypMapa typMapa) {
        this.pouzivanaMapa.skryObjekty();
        this.pouzivanaMapa.skryMapu();

        this.pouzivanaMapa = this.zoznamDostupnychMap.get(typMapa);
        this.pouzivanaMapa.nakresliMapu();
        this.pouzivanaMapa.zobrazObjekty();

        this.hrac.setMapa(this.pouzivanaMapa);
        this.hrac.setHernaPlocha(this);
        this.hrac.setPolohaX(this.pouzivanaMapa.getZaciatokXHrac());
        this.hrac.setPolohaY(this.pouzivanaMapa.getZaciatokYHrac());

        this.hrac.zobrazHracaNaNovejMape();
    }

    /**
     * Metóda, ktorá zabezpečuje kontrolu, či je ešte možné interagovať s npc alebo nepriateľmi v hernom prostredí.
     * @return true ak je možná interakcia, false inak
     */
    public boolean getJeEsteMozneInteragovat() {
        int pocetNaInteragovanie = 0;
        for (Mapa m : this.zoznamDostupnychMap.values()) {
            pocetNaInteragovanie = pocetNaInteragovanie + m.getPocetDostupnychNpc() + m.getPocetDostupnychNepriatelov();
        }

        if (pocetNaInteragovanie > 0) {
            return true;
        }

        return false;
    }
}
