package objekty.postavy;

import fri.shapesge.BlokTextu;
import fri.shapesge.Manazer;
import fri.shapesge.Obdlznik;
import fri.shapesge.Obrazok;
import fri.shapesge.StylFontu;
import objekty.AnimovatelnyObjekt;
import objekty.SchopnostInterakcie;
import prostredie.Mapa;
import spravcovia.HernaPlocha;
import spravcovia.SpravcaDialogov;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Trieda Npc reprezentuje nehrateľnú postavu v hre, s ktorou môže hráč interagovať.
 * @author Emma Lujza Rothová
 */
public class Npc extends Charakter implements SchopnostInterakcie, AnimovatelnyObjekt {
    private Obrazok obrazok;
    private ArrayList<Object> dialogoveObjekty;
    private boolean spravenaInterakcia;
    private boolean mozeBytVykonana;
    private Manazer manazer;
    private SpravcaDialogov spravcaDialogov;
    private TypNpc typNpc;
    private String odpoved;
    private boolean prebehlaOdpoved;
    private boolean polozeneMoznosti;
    private HernaPlocha hernaPlocha;
    private int index;
    private Hrac hrac;
    private boolean spravnaVzdialenost;
    private Mapa mapa;

    /**
     * Konštruktor triedy Npc.
     * @param typNpc
     * @param polohaX
     * @param polohaY
     */
    public Npc(TypNpc typNpc, int polohaX, int polohaY) {
        super(typNpc.getMeno(), 0, 0, polohaX, polohaY, typNpc.getObrazok(0));
        this.typNpc = typNpc;

        this.obrazok = new Obrazok(typNpc.getObrazok(0));
        this.obrazok.zmenPolohu(super.getPolohaX(), super.getPolohaY());

        this.spravenaInterakcia = false;
        this.mozeBytVykonana = false;
        this.manazer = new Manazer();
        this.manazer.spravujObjekt(this);
        this.spravcaDialogov = new SpravcaDialogov();

        this.dialogoveObjekty = new ArrayList<Object>();
        this.odpoved = null;
        this.prebehlaOdpoved = false;
        this.polozeneMoznosti = false;

        this.hernaPlocha = null;
        this.mapa = null;

        this.index = 1;
        this.hrac = null;
        this.spravnaVzdialenost = true;
    }

    /**
     * Metóda, ktorá zabezpečenie grafické prekeslovanie obrázka npc.
     * Metóda je volaná každých 250 ms.
     */
    public void animuj() {
        if (this.index >= this.typNpc.getVelkostZonamuObrazkov()) {
            this.index = 0;
        }
        this.obrazok.zmenObrazok(this.typNpc.getObrazok(this.index));
        this.index++;
    }

    /**
     * Getter, ktorý vracia meno npc.
     * @return meno
     */
    public String getMeno() {
        return super.getMeno();
    }

    /**
     * Metóda, ktorá zabezpečuje vykreslenie npc na hre.
     */
    public void zobrazNpc() {
        this.obrazok.zobraz();
    }

    /**
     * Metóda, ktorá zabezpečuje skrytie npc z hry.
     */
    public void skryNpc() {
        this.obrazok.skry();
    }

    /**
     * Metóda, ktorá zebezpečuje položenie otázky hráčovi v rámci interakcie s npc.
     */
    public void polozOtazku() {
        if (!this.spravnaVzdialenost) {
            return;
        }

        if (!this.mozeBytVykonana) {
            return;
        }

        if (this.spravenaInterakcia) {
            return;
        }

        this.hernaPlocha.setAktivnaInterakcia();

        BlokTextu dialog = new BlokTextu(this.spravcaDialogov.ziskajDialog(this.typNpc));
        dialog.zmenPolohu(932, 80);
        dialog.zmenFarbu("black");
        dialog.zmenFont("Times New Roman", EnumSet.of(StylFontu.BOLD, StylFontu.ITALIC), 15);
        dialog.zobraz();

        this.dialogoveObjekty.add(dialog);

        this.polozMoznosti();
        this.polozeneMoznosti = true;
        this.vyhodnotOtazku();
        this.spravenaInterakcia = true;
    }

    /**
     * Metóda, ktorá zebezpečuje položenie možností odpovedí na položenú otázku.
     */
    public void polozMoznosti() {
        int stranaA = 90;
        int stranaB = 30;
        String[] moznosti = this.spravcaDialogov.ziskajMoznosti(this.typNpc).keySet().toArray(new String[0]);
        for (int i = 0; i < 3; i++) {
            Obdlznik moznostO = new Obdlznik(932 + (10 + stranaA) * i, 250);
            moznostO.zmenStrany(stranaA, stranaB);
            moznostO.zmenFarbu("#2B062E");
            moznostO.zobraz();

            BlokTextu moznostT = new BlokTextu(moznosti[i]);
            moznostT.zmenPolohu(945 + (10 + stranaA) * i, 270);
            moznostT.zmenFarbu("#C3C3C3");
            moznostT.zmenFont("Times New Roman", StylFontu.ITALIC, 15);
            moznostT.zobraz();

            this.dialogoveObjekty.add(moznostO);
            this.dialogoveObjekty.add(moznostT);
        }
    }

    /**
     * Metóda, ktorá zoznamenáva, ak hráč zvolí prvú možnosť odpovede na otázku.
     * Metóda je volaná stlačením A na klávesnici.
     */
    public void zaznamenajOdpoved1() {
        if (this.prebehlaOdpoved) {
            return;
        }

        if (!this.polozeneMoznosti) {
            return;
        }

        this.odpoved = this.spravcaDialogov.ziskajMoznosti(this.typNpc).keySet().toArray(new String[0])[0];
        this.prebehlaOdpoved = true;
        this.vyhodnotOtazku();
    }

    /**
     * Metóda, ktorá zoznamenáva, ak hráč zvolí druhú možnosť odpovede na otázku.
     * Metóda je volaná stlačením B na klávesnici.
     */
    public void zaznamenajOdpoved2() {
        if (this.prebehlaOdpoved) {
            return;
        }

        if (!this.polozeneMoznosti) {
            return;
        }

        this.odpoved = this.spravcaDialogov.ziskajMoznosti(this.typNpc).keySet().toArray(new String[0])[1];
        this.prebehlaOdpoved = true;
        this.vyhodnotOtazku();
    }

    /**
     * Metóda, ktorá zoznamenáva, ak hráč zvolí tretiu možnosť odpovede na otázku.
     * Metóda je volaná stlačením C na klávesnici.
     */
    public void zaznamenajOdpoved3() {
        if (this.prebehlaOdpoved) {
            return;
        }

        if (!this.polozeneMoznosti) {
            return;
        }

        this.odpoved = this.spravcaDialogov.ziskajMoznosti(this.typNpc).keySet().toArray(new String[0])[2];
        this.prebehlaOdpoved = true;
        this.vyhodnotOtazku();
    }

    /**
     * Metóda, ktorá kontroluje či hráč odpovedal na otázku správne.
     * @return true ak hráč odpovedal správna, false ak nie
     */
    public boolean skontrolujOdpoved() {
        if (this.spravcaDialogov.ziskajMoznosti(this.typNpc).get(this.odpoved)) {
            this.odmen();
            return true;
        }
        return false;
    }

    /**
     * Metóda, ktorá poskytuje celkové vyhodnotenie interakcie a poskytuje spätnú väzbu hráčovi na základe správnosti odpovede.
     * V metóde je využitá trieda Timer z Java knižnice.
     * Zdroj: https://www.geeksforgeeks.org/java-util-timer-class-java/
     */
    public void vyhodnotOtazku() {
        if (this.prebehlaOdpoved) {
            for (int i = 0; i < this.dialogoveObjekty.size(); i++) {
                if (this.dialogoveObjekty.get(i) instanceof BlokTextu text) {
                    text.skry();
                }
                if (this.dialogoveObjekty.get(i) instanceof Obdlznik obdlznik) {
                    obdlznik.skry();
                }
            }

            BlokTextu vyhodnotenie = new BlokTextu(this.spravcaDialogov.ziskajOdpoved(this.typNpc, this.skontrolujOdpoved()));
            vyhodnotenie.zmenPolohu(932, 80);
            vyhodnotenie.zmenFarbu("black");
            vyhodnotenie.zmenFont("Times New Roman", EnumSet.of(StylFontu.BOLD, StylFontu.ITALIC), 15);
            vyhodnotenie.zobraz();
            this.dialogoveObjekty.add(vyhodnotenie);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (Npc.this.dialogoveObjekty.get(Npc.this.dialogoveObjekty.size() - 1) instanceof BlokTextu text) {
                        text.skry();
                    }
                    Npc.this.dialogoveObjekty.removeAll(Npc.this.dialogoveObjekty);
                }
            }, 5000);

            this.hernaPlocha.zrusAktivnuInterakciuNpc();
            this.skryNpc();
            this.mapa.odstranObjektovuPrekazku(this.getPolohaX(), this.getPolohaY());
            this.hernaPlocha.getPouzivanaMapa().odstranNpc(this);
            this.hrac = null;
        }
    }

    /**
     * Metóda, ktorá zabezpečuje odmenie hráča.
     */
    public void odmen() {
        this.hrac.prijmyOdmenu(this.typNpc.getOdmena());
    }

    /**
     * Metóda, ktorá umožňuje hráčovi podplatiť npc.
     */
    public void podplat() {
        if (this.prebehlaOdpoved) {
            return;
        }

        if (!this.polozeneMoznosti) {
            return;
        }

        if (this.hrac.getDukaty() == null) {
            return;
        }

        if (this.hrac.getDukaty().getHodnota() < this.typNpc.getVyskaUplatku()) {
            return;
        }

        this.hrac.getDukaty().pouziPredmet(this);
        this.prebehlaOdpoved = true;
        this.odpoved = this.typNpc.getSpravnaOdpoved();
        this.vyhodnotOtazku();
    }

    /**
     * Getter, ktorý vracia požadovanú výšku úplatku npc
     * @return vyska uplatku
     */
    public int getVyskaUplatku() {
        return this.typNpc.getVyskaUplatku();
    }

    /**
     * Metóda, ktorá kontroluje či môže prebehnúť interakcia medzi npc a hráčom.
     * Metóda je volaná každých 10 ms.
     */
    public void kontrolujSchopnostInterakcie() {
        if (this.hrac == null) {
            return;
        }

        if (this.spravenaInterakcia) {
            return;
        }

        if (!this.mozeBytVykonana) {
            return;
        }

        if (this.kontrolaVzdialenostiPreInterakciu(this.hrac)) {
            this.spravnaVzdialenost = true;
            this.hernaPlocha.nastavAktivnuInterakciuNpc(this);
        } else {
            this.spravnaVzdialenost = false;
            this.mozeBytVykonana = false;
            this.hernaPlocha.zrusAktivnuInterakciuNpc();
        }
    }

    /**
     * Metóda, ktorá slúži na výpočet vzdialenosti medzi npc a hráčom.
     * @param hrac
     * @return vzdialenost
     */
    @Override
    public double vzdialenost(Hrac hrac) {
        double centrumXNpc = super.getPolohaX() + super.getSirkaObrazka() / 2;
        double centrumYNpc = super.getPolohaY() + super.getDlzkaObrazka() / 2;
        double centrumXHrac = hrac.getPolohaX() + hrac.getSirkaObrazka() / 2;
        double centrumYHrac = hrac.getPolohaY() + hrac.getDlzkaObrazka() / 2;

        double vz = Math.sqrt(Math.pow((centrumXNpc - centrumXHrac), 2) + Math.pow((centrumYNpc - centrumYHrac), 2));
        return vz;
    }

    /**
     * Metóda, ktorá kontroluje, či je hráč v správnej vzdialenosti potrebnej k interakcii.
     * @param hrac
     * @return true ak je v správnej vzdialenosti, inak false
     */
    @Override
    public boolean kontrolaVzdialenostiPreInterakciu(Hrac hrac) {
        if (this.vzdialenost(hrac) <= 100) {
            if (this.hrac == null) {
                this.hrac = hrac;
            }

            return true;
        }
        return false;
    }

    /**
     * Metóda, ktorá zabezpečuje vykonanie interakcie medzi hráčom a npc.
     * @param hrac
     */
    @Override
    public void interakcia(Hrac hrac) {
        if (this.spravenaInterakcia || this.mozeBytVykonana) {
            return;
        }

        if (this.hernaPlocha.getInteragujuceNpc() != null && this.hernaPlocha.getInteragujuceNpc() != this) {
            return;
        }

        if (this.hernaPlocha.getUtociaciNepriatel() != null) {
            return;
        }

        if (this.hernaPlocha.nastavAktivnuInterakciuNpc(this)) {
            this.mozeBytVykonana = true;
        }
    }

    /**
     * Setter na nastavenie hernej plochy hráča.
     * @param hernaPlocha
     */
    public void setHernaPlocha(HernaPlocha hernaPlocha) {
        this.hernaPlocha = hernaPlocha;
    }

    /**
     * Getter, ktorý vracia hráča, s ktorým sa vykonáva interakcia.
     * @return
     */
    public Hrac getHrac() {
        return this.hrac;
    }

    /**
     * Setter na nastavenie mapy, na ktorej sa npc nachádza.
     * @param mapa
     */
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }
}
