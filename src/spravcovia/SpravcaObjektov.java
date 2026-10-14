package spravcovia;

import objekty.postavy.Hrac;
import objekty.postavy.nepriatelia.Nepriatel;
import objekty.postavy.Npc;
import objekty.predmety.Predmet;
import prostredie.Prechod;

import java.util.ArrayList;

/**
 * Trieda SpravcaObjektov reprezentuje správcu herných objektov, ako sú npc, nepriatelia, predmety a prechody.
 * @author Emma Lujza Rothová
 */
public class SpravcaObjektov {
    private ArrayList<Predmet> predmety;
    private ArrayList<Prechod> prechody;
    private ArrayList<Npc> npcka;
    private ArrayList<Nepriatel> nepriatelia;

    /**
     * Koštruktor triedy SpravcaObjektov.
     */
    public SpravcaObjektov() {
        this.predmety = new ArrayList<Predmet>();
        this.npcka = new ArrayList<Npc>();
        this.nepriatelia = new ArrayList<Nepriatel>();
        this.prechody = new ArrayList<Prechod>();
    }

    /**
     * Metóda, ktorá zabezpečuje pridanie npc do zoznamu spravovaných npc.
     * @param npc
     */
    public void pridajNpc(Npc npc) {
        this.npcka.add(npc);
    }

    /**
     * Metóda, ktorá zabezpečuje pridanie nepriateľa do zoznamu spravovaných nepriateľov.
     * @param nepriatel
     */
    public void pridajNepriatela(Nepriatel nepriatel) {
        this.nepriatelia.add(nepriatel);
    }

    /**
     * Metóda, ktorá zabezpečuje pridanie predmetu do zoznamu spravovaných predmetov.
     * @param predmet
     */
    public void pridajPredmet(Predmet predmet) {
        this.predmety.add(predmet);
    }

    /**
     * Metóda, ktorá zabezpečuje pridanie prechodu do zoznamu spravovaných prechodov.
     * @param prechod
     */
    public void pridajPrechod (Prechod prechod) {
        this.prechody.add(prechod);
    }

    /**
     * Metóda, ktorá zabezpečuje odstránenie predmetu zo zoznamu spravovaných predmetov.
     * @param predmet
     */
    public void odstranPredmet(Predmet predmet) {
        this.predmety.remove(predmet);
    }

    /**
     * Metóda, ktorá zabezpečuje odstránenie nepriateľa zo zoznamu spravovaných nepriateľov.
     * @param nepriatel
     */
    public void odstranNepriatela(Nepriatel nepriatel) {
        this.nepriatelia.remove(nepriatel);
    }

    /**
     * Metóda, ktorá zabezpečuje odstránenie npc zo zoznamu spravovaných npc.
     * @param npc
     */
    public void odstranNpc(Npc npc) {
        this.npcka.remove(npc);
    }

    /**
     * Metóda, ktorá zabezpečuje zobrezenie všetkých spravovaných objektov na mape.
     */
    public void zobrazObjekty() {
        for (Predmet predmet : this.predmety) {
            predmet.zobrazPredmet();
        }

        for (Npc npc : this.npcka) {
            npc.zobrazNpc();
        }

        for (Nepriatel nepriatel : this.nepriatelia) {
            nepriatel.zobrazNepriatela();
        }

        for (Prechod prechod : this.prechody) {
            prechod.zobrazPrechod();
        }
    }

    /**
     * Metóda, ktorá zabezpečuje skrytie všetkých spravovaných objektov na mape.
     */
    public void skryObjekty() {
        for (Predmet predmet : this.predmety) {
            predmet.skryPredmet();
        }

        for (Npc npc : this.npcka) {
            npc.skryNpc();
        }

        for (Nepriatel nepriatel : this.nepriatelia) {
            nepriatel.skryNepriatela();
        }

        for (Prechod prechod : this.prechody) {
            prechod.skryPrechod();
        }
    }

    /**
     * Metóda, ktorá zabezpečuje kontrolu interakcie hráča a npc a zabezpečuje jej spustenie.
     * @param hrac
     */
    public void kontrolujInterakciuNpc(Hrac hrac) {
        for (Npc npc : this.npcka) {
            if (npc.kontrolaVzdialenostiPreInterakciu(hrac)) {
                npc.interakcia(hrac);
                break;
            }
        }
    }

    /**
     * Metóda, ktorá zabezpečuje kontrolu interakcie hráča a nepriateľa a zabezpečuje jej spustenie.
     * @param hrac
     */
    public void kontrolujUtokNepriatel(Hrac hrac) {
        for (Nepriatel nepriatel : this.nepriatelia) {
            if (nepriatel.kontrolaVzdialenostiPreInterakciu(hrac)) {
                nepriatel.interakcia(hrac);
                break;
            }
        }
    }

    /**
     * Metóda, ktorá zabezpečuje kontrolu interakcie hráča a predmetu a zabezpečuje jej spustenie.
     * @param hrac
     */
    public void kontrolujBlizkostPredmetu(Hrac hrac) {
        for (Predmet predmet : this.predmety) {
            if (predmet.kontrolaVzdialenostiPreInterakciu(hrac)) {
                predmet.interakcia(hrac);
                break;
            }
        }
    }

    /**
     * Metóda, ktorá zabezpečuje kontrolu blízkosť hráča a prechodu a zabezpečuje zmenu mapy.
     * @param hrac
     */
    public void kontrolujBlizkostPrechodu(Hrac hrac) {
        for (Prechod prechod : this.prechody) {
            if (prechod.presielZa(hrac)) {
                prechod.zmenMapu();
                break;
            }
        }
    }

    /**
     * Getter, ktorý vracia počet spravovaných npc.
     * @return počet spravovaných npc
     */
    public int getPocetDostupnychNpc() {
        return this.npcka.size();
    }

    /**
     * Getter, ktorý vracia počet spravovaných nepriateľov.
     * @return počet spravovaných nepriateľov
     */
    public int getPocetDostupnychNepriatelov() {
        return this.nepriatelia.size();
    }
}
