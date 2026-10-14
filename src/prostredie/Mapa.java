package prostredie;

import fri.shapesge.Obrazok;
import objekty.postavy.Charakter;
import objekty.postavy.Hrac;
import objekty.postavy.Npc;
import objekty.postavy.TypNpc;
import objekty.postavy.nepriatelia.Nepriatel;
import objekty.postavy.nepriatelia.Golem;
import objekty.postavy.nepriatelia.Bestia;
import objekty.postavy.nepriatelia.TypNepriatel;
import objekty.postavy.nepriatelia.TypGolem;
import objekty.postavy.nepriatelia.TypBestia;
import objekty.predmety.Predmet;
import objekty.predmety.Dukaty;
import objekty.predmety.AmuletOdvahy;
import objekty.predmety.ElixirZdravia;
import objekty.predmety.TypPredmet;
import spravcovia.HernaPlocha;
import spravcovia.SpravcaObjektov;
import fri.shapesge.Stvorec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Trieda Mapa reprezentuje hernú mapu, ktorá obsahuje objekty, prekážky a interaktívne prvky.
 * @author Emma Lujza Rothová
 */
public class Mapa {
    private TypMapa typMapa;
    private int velkostPolicka;
    private int sirka;
    private int dlzka;
    private Stvorec[][] stvorec;
    private Obrazok spawn;
    private ArrayList<Prekazka> zoznamPrekazok;
    private SpravcaObjektov spravcaObjektov;
    private HernaPlocha hernaPlocha;
    private int poziciaXHraca;
    private int poziciaYHraca;
    private int zaciatokXHrac;
    private int zaciatokYHrac;
    private boolean uzNacitana;

    /**
     * Koštruktor triedy Mapa.
     * @param typMapa
     */
    public Mapa(TypMapa typMapa) {
        this.typMapa = typMapa;
        this.sirka = 0;
        this.dlzka = 0;
        this.velkostPolicka = 60;
        this.poziciaXHraca = 0;
        this.poziciaYHraca = 0;
        this.zaciatokXHrac = 0;
        this.zaciatokYHrac = 0;
        this.spravcaObjektov = new SpravcaObjektov();
        this.hernaPlocha = null;
        this.uzNacitana = false;
        this.spawn = null;
    }

    /**
     * Metóda, ktorá zabezpečuje načítanie mapy zo súboru inicializovanie jej obsahu.
     * @throws IOException
     */
    public void nacitajMapu() throws IOException {
        if (this.uzNacitana) {
            return;
        }

        Scanner skener = null;
        try {
            skener = new Scanner(new File(this.typMapa.getSuborSMapou()));

            Random nahoda = new Random();

            String[] velkostMapy = skener.nextLine().split(" ");
            this.sirka = Integer.parseInt(velkostMapy[0]);
            this.dlzka = Integer.parseInt(velkostMapy[1]);
            this.stvorec = new Stvorec[this.dlzka][this.sirka];
            this.zoznamPrekazok = new ArrayList<Prekazka>();

            int riadok = 0;

            while (skener.hasNextLine()) {
                String[] hodnoty = skener.nextLine().split(" ");
                for (int stlpec = 0; stlpec < hodnoty.length; stlpec++) {
                    this.stvorec[stlpec][riadok] = new Stvorec(this.velkostPolicka * stlpec, this.velkostPolicka * riadok);
                    this.stvorec[stlpec][riadok].zmenStranu(this.velkostPolicka);

                    switch (hodnoty[stlpec]) {
                        case "0":
                            this.stvorec[stlpec][riadok].zmenFarbu(this.typMapa.getFarba0());
                            break;

                        case "1":
                            this.stvorec[stlpec][riadok].zmenFarbu(this.typMapa.getFarba1());
                            this.zoznamPrekazok.add(new Prekazka(stlpec, riadok, this.velkostPolicka, this.velkostPolicka, this.velkostPolicka, false));
                            break;

                        case "2":
                            this.stvorec[stlpec][riadok].zmenFarbu("#101D42");
                            this.spawn = new Obrazok("pics/spawn.png");
                            this.spawn.zmenPolohu(stlpec * this.velkostPolicka, riadok * this.velkostPolicka);
                            this.poziciaXHraca = this.velkostPolicka * stlpec;
                            this.poziciaYHraca = this.velkostPolicka * riadok;
                            this.zaciatokXHrac = this.velkostPolicka * stlpec;
                            this.zaciatokYHrac = this.velkostPolicka * riadok;
                            break;

                        case "3":
                            TypNpc typNpc = TypNpc.values()[nahoda.nextInt(TypNpc.values().length)];

                            this.stvorec[stlpec][riadok].zmenFarbu(this.typMapa.getFarba0());
                            Npc npc = new Npc(typNpc, this.velkostPolicka * stlpec, this.velkostPolicka * riadok);
                            npc.setHernaPlocha(this.hernaPlocha);
                            npc.setMapa(this);
                            this.spravcaObjektov.pridajNpc(npc);
                            this.zoznamPrekazok.add(new Prekazka(stlpec, riadok, npc.getSirkaObrazka(), npc.getDlzkaObrazka(), this.velkostPolicka, true));
                            break;

                        case "4":
                            this.stvorec[stlpec][riadok].zmenFarbu(this.typMapa.getFarba0());
                            TypNepriatel typNepriatel = TypNepriatel.values()[nahoda.nextInt(TypNepriatel.values().length)];

                            if (typNepriatel == TypNepriatel.GOLEM) {
                                TypGolem typGolem = TypGolem.values()[nahoda.nextInt(TypGolem.values().length)];
                                Golem golem = new Golem(typGolem, this.velkostPolicka * stlpec, this.velkostPolicka * riadok);
                                golem.setHernaPlocha(this.hernaPlocha);
                                golem.setMapa(this);
                                this.spravcaObjektov.pridajNepriatela(golem);
                                this.zoznamPrekazok.add(new Prekazka(stlpec, riadok, golem.getSirkaObrazka(), golem.getDlzkaObrazka(), this.velkostPolicka, true));
                            } else {
                                TypBestia typBestia = TypBestia.values()[nahoda.nextInt(TypBestia.values().length)];
                                Bestia bestia = new Bestia(typBestia, this.velkostPolicka * stlpec, this.velkostPolicka * riadok);
                                bestia.setHernaPlocha(this.hernaPlocha);
                                bestia.setMapa(this);
                                this.spravcaObjektov.pridajNepriatela(bestia);
                                this.zoznamPrekazok.add(new Prekazka(stlpec, riadok, bestia.getSirkaObrazka(), bestia.getDlzkaObrazka(), this.velkostPolicka, true));
                            }
                            break;

                        case "5":
                            this.stvorec[stlpec][riadok].zmenFarbu(this.typMapa.getFarba0());
                            TypPredmet typPredmet = TypPredmet.values()[nahoda.nextInt(TypPredmet.values().length)];
                            Predmet predmet = null;

                            if (typPredmet == TypPredmet.ELIXIR) {
                                predmet = new ElixirZdravia("ElixirZdravia", "pics/elixirZdravia1.png", this.velkostPolicka * stlpec, this.velkostPolicka * riadok);
                            } else if (typPredmet == TypPredmet.AMULET) {
                                predmet = new AmuletOdvahy("AmuletOdvahy", "pics/amuletOdvahy1.png", this.velkostPolicka * stlpec, this.velkostPolicka * riadok);
                            } else {
                                predmet = new Dukaty("Dukaty", "pics/dukaty1.png", this.velkostPolicka * stlpec, this.velkostPolicka * riadok);
                            }
                            predmet.setHernaPlocha(this.hernaPlocha);
                            this.spravcaObjektov.pridajPredmet(predmet);
                            break;

                        case "6":
                            this.stvorec[stlpec][riadok].zmenFarbu(this.typMapa.getFarba0());
                            Prechod prechod = new Prechod(stlpec, riadok, this.velkostPolicka);
                            this.spravcaObjektov.pridajPrechod(prechod);
                            prechod.setHernaPlocha(this.hernaPlocha);
                            break;

                        default:
                            System.out.println("chyba");
                    }
                }
                riadok++;
            }
            this.uzNacitana = true;
        } catch (IOException e) {
            System.out.println("Subor nebol najdeny!");
        } finally {
            skener.close();
        }
    }

    /**
     * Metóda, ktorá zabezpečuje vykreslenie mapy.
     */
    public void nakresliMapu() {
        for (int i = 0; i < this.dlzka; i++) {
            for (int j = 0; j < this.sirka; j++) {
                this.stvorec[i][j].zobraz();
            }
        }
        this.spawn.zobraz();
    }

    /**
     * Metóda, ktorá zabezpečuje skrytie mapy.
     */
    public void skryMapu() {
        for (int i = 0; i < this.dlzka; i++) {
            for (int j = 0; j < this.sirka; j++) {
                this.stvorec[i][j].skry();
            }
        }
        this.spawn.skry();
    }

    /**
     * Getter, ktorý vracia polohu x hráča na mape.
     * @return pozícia x
     */
    public int getPoziciaXHraca() {
        return this.poziciaXHraca;
    }

    /**
     * Getter, ktorý vracia polohu y hráča na mape.
     * @return pozícia y
     */
    public int getPoziciaYHraca() {
        return this.poziciaYHraca;
    }

    /**
     * Setter na nastavenie polohy x hráča.
     * @param poziciaXHraca
     */
    public void setPoziciaXHraca(int poziciaXHraca) {
        this.poziciaXHraca = poziciaXHraca;
    }

    /**
     * Setter na nastavenie polohy y hráča.
     * @param poziciaYHraca
     */
    public void setPoziciaYHraca(int poziciaYHraca) {
        this.poziciaYHraca = poziciaYHraca;
    }

    /**
     * Getter, ktorý vracia začiatočnú polohu x hráča na mape.
     * @return začiatočná poloha x
     */
    public int getZaciatokXHrac() {
        return this.zaciatokXHrac;
    }

    /**
     * Getter, ktorý vracia začiatočnú polohu y hráča na mape.
     * @return začiatočná poloha y
     */
    public int getZaciatokYHrac() {
        return this.zaciatokYHrac;
    }

    /**
     * Metóda, ktorá zabezpečuje vykreslenie objektov na mape.
     */
    public void zobrazObjekty() {
        this.spravcaObjektov.zobrazObjekty();
    }

    /**
     * Metóda, ktorá skrytie vykreslenie objektov na mape.
     */
    public void skryObjekty() {
        this.spravcaObjektov.skryObjekty();
    }

    /**
     * Getter, ktorý vracia koncovú pozíciu x mapy.
     * @return koncová pozícia x mapy
     */
    public int getKoncovaXMapy() {
        return this.dlzka * this.velkostPolicka;
    }

    /**
     * Getter, ktorý vracia koncovú pozíciu y mapy.
     * @return koncová pozícia y mapy
     */
    public int getKoncovaYMapy() {
        return this.sirka * this.velkostPolicka;
    }

    /**
     * Metóda, ktorá kontroluje, či sa na daných súradniciach nachádza prekážka pre charakter.
     * @param pozX
     * @param pozY
     * @param charakter
     * @return true, ak je na danom mieste prekážka, false inak
     */
    public boolean jePrekazkaPreCharakter(int pozX, int pozY, Charakter charakter) {
        int pozXVpravo = pozX + charakter.getSirkaObrazka();
        int pozYDole = pozY + charakter.getDlzkaObrazka();

        for (Prekazka p : this.zoznamPrekazok) {
            boolean koliziaX = false;
            boolean koliziaY = false;

            if (pozXVpravo > p.getxLavy() && pozX < p.getxPravy()) {
                koliziaX = true;
            }
            if (pozYDole > p.getyHorny() && pozY + 45 < p.getyDolny()) {
                koliziaY = true;
            }

            if (koliziaX && koliziaY) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metóda, ktorá kontroluje, či strela narazila na prekážku.
     * @param pozX
     * @param pozY
     * @param velkostStrely
     * @return true, ak strela narazila na prekážku, false inak
     */
    public boolean jePrekazkaPreStrely(double pozX, double pozY, int velkostStrely) {
        double pozXVpravo = pozX + velkostStrely;
        double pozYDole = pozY + velkostStrely;

        for (Prekazka p : this.zoznamPrekazok) {
            if (p.isCharakter()) {
                continue;
            }

            boolean koliziaX = false;
            boolean koliziaY = false;

            if (pozXVpravo > p.getxLavy() && pozX < p.getxPravy()) {
                koliziaX = true;
            }
            if (pozYDole > p.getyHorny() && pozY < p.getyDolny()) {
                koliziaY = true;
            }

            if (koliziaX && koliziaY) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metóda, ktorá kontroluje, či strela zasiahla cieľ.
     * @param pozX
     * @param pozY
     * @param ciel
     * @param velkostStrely
     * @return true, ak strela zasiahla cieľ, false inak
     */
    public boolean jeStretSCielom(double pozX, double pozY, Charakter ciel, int velkostStrely) {
        int poxXVlavoH = ciel.getPolohaX();
        int pozXVpravoH = ciel.getPolohaX() + ciel.getSirkaObrazka();
        int pozYHoreH = ciel.getPolohaY();
        int pozYDoleH = ciel.getPolohaY() + ciel.getDlzkaObrazka();

        double poxXVlavoS = pozX;
        double pozXVpravoS = pozX + velkostStrely;
        double pozYHoreS = pozY;
        double pozYDoleS = pozY + velkostStrely;

        boolean koliziaX = false;
        boolean koliziaY = false;

        if (pozXVpravoS >= poxXVlavoH && poxXVlavoS <= pozXVpravoH) {
            koliziaX = true;
        }
        if (pozYDoleS >= pozYHoreH && pozYHoreS <= pozYDoleH) {
            koliziaY = true;
        }

        if (koliziaX && koliziaY) {
            return true;
        }

        return false;
    }

    /**
     * Setter na nastavenie hernej plochy.
     * @param hernaPlocha
     */
    public void setHernaPlocha (HernaPlocha hernaPlocha) {
        this.hernaPlocha = hernaPlocha;
    }

    /**
     * Metóda, ktorá zabezpečuje odstránenie objektovej prekážky (npc, nepriateľ) z mapy.
     * @param pozX
     * @param pozY
     */
    public void odstranObjektovuPrekazku(int pozX, int pozY) {
        for (int i = 0; i < this.zoznamPrekazok.size(); i++) {
            if (this.zoznamPrekazok.get(i).isCharakter()) {
                if (this.zoznamPrekazok.get(i).getxLavy() == pozX && this.zoznamPrekazok.get(i).getyHorny() == pozY) {
                    this.zoznamPrekazok.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * Metóda, ktorá zabezpečuje kontrolu interakcie sa všektými objektami v mape.
     * @param hrac
     */
    public void interakciaSObjektmi(Hrac hrac) {
        this.spravcaObjektov.kontrolujInterakciuNpc(hrac);
        this.spravcaObjektov.kontrolujUtokNepriatel(hrac);
        this.spravcaObjektov.kontrolujBlizkostPredmetu(hrac);
        this.spravcaObjektov.kontrolujBlizkostPrechodu(hrac);
    }

    /**
     * Metóda, ktorá zabezpečuje odstránenie nepriateľa z mapy.
     * @param nepriatel
     */
    public void odstranNepriatela(Nepriatel nepriatel) {
        this.spravcaObjektov.odstranNepriatela(nepriatel);
    }

    /**
     * Metóda, ktorá zabezpečuje odstránenie predmetu z mapy.
     * @param predmet
     */
    public void odstranPredmet(Predmet predmet) {
        this.spravcaObjektov.odstranPredmet(predmet);
    }

    /**
     * Metóda, ktorá zabezpečuje odstránenie npc z mapy.
     * @param npc
     */
    public void odstranNpc(Npc npc) {
        this.spravcaObjektov.odstranNpc(npc);
    }

    /**
     * Getter, ktorý vracia počet npc na mape.
     * @return počet npc
     */
    public int getPocetDostupnychNpc() {
        return this.spravcaObjektov.getPocetDostupnychNpc();
    }

    /**
     * Getter, ktorý vracia počet nepriateľov na mape.
     * @return počet nepriateľov
     */
    public int getPocetDostupnychNepriatelov() {
        return this.spravcaObjektov.getPocetDostupnychNepriatelov();
    }

    /**
     * Getter, ktorý vracia typ mapy.
     * @return typ mapy
     */
    public TypMapa getTypMapa() {
        return this.typMapa;
    }
}
