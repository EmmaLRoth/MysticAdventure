package objekty.postavy;

import objekty.HealthBar;
import objekty.Strela;
import objekty.postavy.nepriatelia.Nepriatel;
import objekty.predmety.Dukaty;
import spravcovia.Inventar;
import objekty.predmety.Predmet;
import prostredie.Mapa;
import fri.shapesge.Manazer;
import fri.shapesge.Obrazok;
import spravcovia.HernaPlocha;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Trieda Hrac reprezentuje hráča v hre.
 * @author Emma Lujza Rothová
 */
public class Hrac extends Charakter {
    private int rychlost;

    private Obrazok obrazok;
    private Manazer manazer;

    private ArrayList<Obrazok> zoznamVytvorenychObr;
    private ArrayList<String> pohybHoreDole;
    private ArrayList<String> pohybVpravo;
    private ArrayList<String> pohybVlavo;
    private int indexHoreDole;
    private int indexVpravo;
    private int indexVlavo;

    private Mapa mapa;
    private HernaPlocha hernaPlocha;
    private HealthBar healthBar;
    private Inventar inventar;

    private boolean mozeZobratPredmet;
    private Predmet predmetNaZobratie;

    /**
     * Konštruktor triedy hráč.
     * @param meno Meno hráča
     * @param zdravie Počiatočné zdravie hráča
     * @param sila Počiatočná sila hráča
     */
    public Hrac(String meno, int zdravie, int sila) {
        super(meno, zdravie, sila, 0, 0, "pics/wizard1.png");
        this.rychlost = 10;

        this.obrazok = new Obrazok("pics/wizard1.png");

        this.manazer = new Manazer();
        this.manazer.spravujObjekt(this);

        this.zoznamVytvorenychObr = new ArrayList<Obrazok>();
        this.zoznamVytvorenychObr.add(this.obrazok);

        this.pohybHoreDole = new ArrayList<String>();
        this.pohybHoreDole.add("pics/wizard2UpDown.png");
        this.pohybHoreDole.add("pics/wizard3UpDown.png");
        this.pohybVlavo = new ArrayList<String>();
        this.pohybVlavo.add("pics/wizard4Left.png");
        this.pohybVlavo.add("pics/wizard5Left.png");
        this.pohybVpravo = new ArrayList<String>();
        this.pohybVpravo.add("pics/wizard6Right.png");
        this.pohybVpravo.add("pics/wizard7Right.png");

        this.indexHoreDole = 0;
        this.indexVlavo = 0;
        this.indexVpravo = 0;

        this.mapa = null;
        this.hernaPlocha = null;
        this.healthBar = null;
        this.inventar = new Inventar();

        this.mozeZobratPredmet = false;
        this.predmetNaZobratie = null;
    }

    /**
     * Metóda na zobrazenie hráča na hracej ploche.
     */
    public void zobrazHraca() {
        this.obrazok.zobraz();
        this.healthBar.zobrazHealthBar();
    }

    /**
     * Metóda na zobrazenie hráča na novej mape.
     */
    public void zobrazHracaNaNovejMape() {
        this.obrazok = new Obrazok("pics/wizard1.png");
        this.obrazok.zmenPolohu(this.getPolohaX(), this.getPolohaY());
        this.obrazok.zobraz();
        this.healthBar.zobrazHealthBarNaNovejMape();
        this.zoznamVytvorenychObr.add(this.obrazok);
    }

    /**
     * Metóda na skrytie hráča na hracej ploche.
     */
    public void skryHraca() {
        this.obrazok.skry();
        this.healthBar.skryHealthBar();
    }

    /**
     * Metóda na skrytie hráča na všetkých herných mapách.
     */
    public void skryHracaNaVsetkychMapach() {
        for (Obrazok obr : this.zoznamVytvorenychObr) {
            obr.skry();
        }
        this.healthBar.skryHealthBarNaVsetkychMapach();
    }

    /**
     * Setter, ktorý nastavuje hráčovy mapa, s ktorou interaguje.
     * @param mapa
     */
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    /**
     * Getter, ktorý vracia mapu, na ktorej sa hráč práve nachádza.
     * @return mapa
     */
    public Mapa getMapa() {
        return this.mapa;
    }

    /**
     * Metóda na pohyb hráča smerom hore.
     * Metóda je volaná stlačením šípky hore na klávesnici.
     */
    public void posunHore() {
        if (super.getPolohaY() <= 0) {
            return;
        }

        if (this.mapa.jePrekazkaPreCharakter(super.getPolohaX(), super.getPolohaY() - this.rychlost, this)) {
            return;
        }

        if (this.hernaPlocha.getAktivnaInterakcia()) {
            return;
        }

        if (this.indexHoreDole >= this.pohybHoreDole.size()) {
            this.indexHoreDole = 0;
        }

        this.obrazok.posunZvisle(-this.rychlost);
        super.setPolohaY(super.getPolohaY() - this.rychlost);
        this.healthBar.akualizujPolohu();

        this.obrazok.zmenObrazok(this.pohybHoreDole.get(this.indexHoreDole));
        this.indexHoreDole++;

        this.pozdrzResetovanieObrazka(1500);

        this.mapa.setPoziciaXHraca(super.getPolohaX());
        this.mapa.setPoziciaYHraca(super.getPolohaY());
        this.mapa.interakciaSObjektmi(this);
    }

    /**
     * Metóda na pohyb hráča smerom dole.
     * Metóda je volaná stlačením šípky dole na klávesnici.
     */
    public void posunDole() {
        if (super.getPolohaY() + super.getDlzkaObrazka() + 11 >= this.mapa.getKoncovaYMapy()) {
            return;
        }

        if (this.mapa.jePrekazkaPreCharakter(super.getPolohaX(), super.getPolohaY() + this.rychlost, this)) {
            return;
        }

        if (this.hernaPlocha.getAktivnaInterakcia()) {
            return;
        }

        if (this.indexHoreDole >= this.pohybHoreDole.size()) {
            this.indexHoreDole = 0;
        }

        this.obrazok.posunZvisle(this.rychlost);
        super.setPolohaY(super.getPolohaY() + this.rychlost);
        this.healthBar.akualizujPolohu();

        this.obrazok.zmenObrazok(this.pohybHoreDole.get(this.indexHoreDole));
        this.indexHoreDole++;

        this.pozdrzResetovanieObrazka(1500);

        this.mapa.setPoziciaXHraca(super.getPolohaX());
        this.mapa.setPoziciaYHraca(super.getPolohaY());
        this.mapa.interakciaSObjektmi(this);
    }

    /**
     * Metóda na pohyb hráča smerom vlavo.
     * Metóda je volaná stlačením šípky vlavo na klávesnici.
     */
    public void posunVlavo() {
        if (super.getPolohaX() <= 0) {
            return;
        }

        if (this.mapa.jePrekazkaPreCharakter(super.getPolohaX() - this.rychlost, super.getPolohaY(), this)) {
            return;
        }

        if (this.hernaPlocha.getAktivnaInterakcia()) {
            return;
        }

        if (this.indexVlavo >= this.pohybVlavo.size()) {
            this.indexVlavo = 0;
        }

        this.obrazok.posunVodorovne(-this.rychlost);
        super.setPolohaX(super.getPolohaX() - this.rychlost);
        this.healthBar.akualizujPolohu();

        this.obrazok.zmenObrazok(this.pohybVlavo.get(this.indexVlavo));
        this.indexVlavo++;

        this.pozdrzResetovanieObrazka(2000);

        this.mapa.setPoziciaXHraca(super.getPolohaX());
        this.mapa.setPoziciaYHraca(super.getPolohaY());
        this.mapa.interakciaSObjektmi(this);
    }

    /**
     * Metóda na pohyb hráča smerom vpravo.
     * Metóda je volaná stlačením šípky vpravo na klávesnici.
     */
    public void posunVpravo() {
        if (super.getPolohaX() + this.getSirkaObrazka() + 3 >= this.mapa.getKoncovaXMapy()) {
            return;
        }

        if (this.mapa.jePrekazkaPreCharakter(super.getPolohaX() + this.rychlost, super.getPolohaY(), this)) {
            return;
        }

        if (this.hernaPlocha.getAktivnaInterakcia()) {
            return;
        }

        if (this.indexVpravo >= this.pohybVpravo.size()) {
            this.indexVpravo = 0;
        }

        this.obrazok.posunVodorovne(this.rychlost);
        super.setPolohaX(super.getPolohaX() + this.rychlost);
        this.healthBar.akualizujPolohu();

        this.obrazok.zmenObrazok(this.pohybVpravo.get(this.indexVpravo));
        this.indexVpravo++;

        this.pozdrzResetovanieObrazka(2000);

        this.mapa.setPoziciaXHraca(super.getPolohaX());
        this.mapa.setPoziciaYHraca(super.getPolohaY());
        this.mapa.interakciaSObjektmi(this);
    }

    /**
     * Metóda, ktorá obnoví základný obrázok hráča.
     */
    public void resetujObrazok() {
        this.obrazok.zmenObrazok("pics/wizard1.png");
    }

    /**
     * Metóda na oneskorené resetovanie obrázka hráča.
     * Metóda, ktorá využíva triedu Timer z Java knižnice, pomocou ktorej sa volá metóda resetujObrazok s oneskorením 2000ms.
     * Zdroj: https://www.geeksforgeeks.org/java-util-timer-class-java/
     * @param cas čas oneskorenia v milisekundách
     */
    public void pozdrzResetovanieObrazka(int cas) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Hrac.this.resetujObrazok();
            }
        }, 2000);
    }

    /**
     *Setter na nastavenie hernej plochy pre hráča.
     * @param hernaPlocha herná plocha pre hráča
     */
    public void setHernaPlocha(HernaPlocha hernaPlocha) {
        this.hernaPlocha = hernaPlocha;
        super.setPolohaX(this.hernaPlocha.getPouzivanaMapa().getPoziciaXHraca());
        super.setPolohaY(this.hernaPlocha.getPouzivanaMapa().getPoziciaYHraca());
        this.obrazok.zmenPolohu(super.getPolohaX(), super.getPolohaY());
        if (this.healthBar == null) {
            this.healthBar = new HealthBar(this);
        }
    }

    /**
     * Metóda na zníženie zdravia hráča a aktualizovanie jeho healthbaru.
     * @param silaUtoku číslo o koľko sa ma zdravie znížiť
     */
    public void obdrzPoskodenie(int silaUtoku) {
        super.obdrzPoskodenie(silaUtoku);
        this.healthBar.aktualizujUkazovatel();
    }

    /**
     * Metóda na zvýšenie zdravia hráča a aktualizovanie jeho healthbaru.
     * @param liecivaSila číslo o koľko sa má zdravie zvýšiť
     */
    public void zvysZdravie(int liecivaSila) {
        super.zvysZdravie(liecivaSila);
        this.healthBar.aktualizujUkazovatel();
    }

    /**
     * Metóda, ktorá umožňuje hráčovi vystreliť na nepriateľa, ak je aktívna bojová interakcia.
     */
    public void vystrel() {
        if (this.hernaPlocha.getUtociaciNepriatel() == null) {
            return;
        }

        if (this.hernaPlocha.getUtociaciNepriatel().getMapa() != this.mapa) {
            return;
        }

        Nepriatel nepriatel = this.hernaPlocha.getUtociaciNepriatel();
        Strela strela = new Strela(super.getPolohaX(), super.getPolohaY(), nepriatel, 2, super.getSila());
        strela.setMapa(this.mapa);
    }

    /**
     * Setter na nastavenie, či je predmet možné zobrať.
     * @param mozeZobratPredmet
     */
    public void setMozeZobratPredmet(boolean mozeZobratPredmet) {
        this.mozeZobratPredmet = mozeZobratPredmet;
    }

    /**
     * Setter na nastavenie predmetu, ktorý hráč môže zobrať.
     * @param predmetNaZobratie predmet, ktorý je možné zobrať
     */
    public void setPredmetNaZobratie(Predmet predmetNaZobratie) {
        this.predmetNaZobratie = predmetNaZobratie;
    }

    /**
     * Metóda, ktorá slúži na zobranie predmetu a jeho pridanie do inventára.
     * Metóda je volané písmenom E na klávesnici.
     */
    public void zoberPredmet() {
        if (this.mozeZobratPredmet) {
            if (this.inventar.getVelkostInventara() == 8) {
                return;
            }
            this.inventar.pridajPredmet(this.predmetNaZobratie);
            this.predmetNaZobratie.zober();
            this.mozeZobratPredmet = false;
        }
    }

    /**
     * Metóda, ktorá umožňuje požitie prvého predmetu z inventáru.
     * Metóda je volaná tlačidlom F1 na klávensnici.
     */
    public void pouziPredmet1() {
        if (this.inventar.getVelkostInventara() < 1) {
            return;
        }
        Predmet predmet = this.inventar.getPredmet(0);

        if (predmet instanceof Dukaty) {
            return;
        }

        predmet.pouziPredmet(this);
        this.inventar.odstranPredmet(0);
    }

    /**
     * Metóda, ktorá umožňuje požitie druhého predmetu z inventáru.
     * Metóda je volaná tlačidlom F2 na klávensnici.
     */
    public void pouziPredmet2() {
        if (this.inventar.getVelkostInventara() < 2) {
            return;
        }
        Predmet predmet = this.inventar.getPredmet(1);

        if (predmet instanceof Dukaty) {
            return;
        }

        predmet.pouziPredmet(this);
        this.inventar.odstranPredmet(1);
    }

    /**
     * Metóda, ktorá umožňuje požitie tretiho predmetu z inventáru.
     * Metóda je volaná tlačidlom F3 na klávensnici.
     */
    public void pouziPredmet3() {
        if (this.inventar.getVelkostInventara() < 3) {
            return;
        }
        Predmet predmet = this.inventar.getPredmet(2);

        if (predmet instanceof Dukaty) {
            return;
        }

        predmet.pouziPredmet(this);
        this.inventar.odstranPredmet(2);
    }

    /**
     * Metóda, ktorá umožňuje požitie štvrtého predmetu z inventáru.
     * Metóda je volaná tlačidlom F4 na klávensnici.
     */
    public void pouziPredmet4() {
        if (this.inventar.getVelkostInventara() < 4) {
            return;
        }
        Predmet predmet = this.inventar.getPredmet(3);

        if (predmet instanceof Dukaty) {
            return;
        }

        predmet.pouziPredmet(this);
        this.inventar.odstranPredmet(3);
    }

    /**
     * Metóda, ktorá umožňuje požitie piateho predmetu z inventáru.
     * Metóda je volaná tlačidlom F5 na klávensnici.
     */
    public void pouziPredmet5() {
        if (this.inventar.getVelkostInventara() < 5) {
            return;
        }
        Predmet predmet = this.inventar.getPredmet(4);

        if (predmet instanceof Dukaty) {
            return;
        }

        predmet.pouziPredmet(this);
        this.inventar.odstranPredmet(4);
    }

    /**
     * Metóda, ktorá umožňuje požitie šiesteho predmetu z inventáru.
     * Metóda je volaná tlačidlom F6 na klávensnici.
     */
    public void pouziPredmet6() {
        if (this.inventar.getVelkostInventara() < 6) {
            return;
        }
        Predmet predmet = this.inventar.getPredmet(5);

        if (predmet instanceof Dukaty) {
            return;
        }

        predmet.pouziPredmet(this);
        this.inventar.odstranPredmet(5);
    }

    /**
     * Metóda, ktorá umožňuje požitie siedmeho predmetu z inventáru.
     * Metóda je volaná tlačidlom F7 na klávensnici.
     */
    public void pouziPredmet7() {
        if (this.inventar.getVelkostInventara() < 7) {
            return;
        }
        Predmet predmet = this.inventar.getPredmet(6);

        if (predmet instanceof Dukaty) {
            return;
        }

        predmet.pouziPredmet(this);
        this.inventar.odstranPredmet(6);
    }

    /**
     * Metóda, ktorá umožňuje požitie ôsmeho predmetu z inventáru.
     * Metóda je volaná tlačidlom F8 na klávensnici.
     */
    public void pouziPredmet8() {
        if (this.inventar.getVelkostInventara() < 8) {
            return;
        }
        Predmet predmet = this.inventar.getPredmet(7);

        if (predmet instanceof Dukaty) {
            return;
        }

        predmet.pouziPredmet(this);
        this.inventar.odstranPredmet(7);
    }

    /**
     * Metóda, ktorá slúži na použitie dukátov z invtentára na podplatenie NPC.
     */
    public void podplatNpc() {
        this.inventar.pouziDukaty(this.hernaPlocha.getInteragujuceNpc().getVyskaUplatku());
    }

    /**
     * Getter na získanie dukátov hráča.
     * @return objekt dukátov hráča z inventára
     */
    public Dukaty getDukaty() {
        return this.inventar.getDukaty();
    }

    /**
     * Metóda, ktorá pridáva do invetára hráča odmenu, ktorú hráč získal od NPC.
     * @param predmet odmena od NPC
     */
    public void prijmyOdmenu(Predmet predmet) {
        if (this.inventar.getVelkostInventara() == 8) {
            return;
        }
        this.inventar.pridajPredmet(predmet);
    }

    /**
     * Metóda, ktorá zabezpečuje skrytie inventára hráča z hernej plochy.
     */
    public void skryInventar() {
        this.inventar.skryInventar();
    }
}

