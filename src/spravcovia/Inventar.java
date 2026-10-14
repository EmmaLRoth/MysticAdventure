package spravcovia;

import fri.shapesge.BlokTextu;
import fri.shapesge.DataObrazku;
import fri.shapesge.Obrazok;
import fri.shapesge.StylFontu;
import objekty.predmety.Dukaty;
import objekty.predmety.Predmet;

import java.util.ArrayList;

/**
 * Trieda Inventar reprezentuje inventár, do ktorého sa ukladajú hráčom pozbierané predmety.
 * @author Emma Lujza Rothová
 */
public class Inventar {
    private ArrayList<Predmet> zoznamPredmetov;
    private ArrayList<Obrazok> zoznamObrazkov;
    private BlokTextu hodnotaDukatov;
    private int hodnota;
    private int posun;

    /**
     * Konštruktor triedy Inventar.
     */
    public Inventar() {
        this.zoznamPredmetov = new ArrayList<Predmet>();
        this.zoznamObrazkov = new ArrayList<Obrazok>();
        this.hodnota = 0;
        this.hodnotaDukatov = null;
        this.posun = 0;
    }

    /**
     * Metóda, ktorá zabezpečuje pridanie predmetu do inventára.
     * Ak predmetom sú dukáty, tak sa iba aktualizuje ich hodnota, ak sa už v inventári nachádzajú.
     * @param predmet
     */
    public void pridajPredmet(Predmet predmet) {
        if (predmet instanceof Dukaty dukaty) {
            this.hodnota += dukaty.getHodnota();
            if (this.hodnotaDukatov == null) {
                Obrazok obr = new Obrazok(predmet.getSuborSObraskom());
                obr.zmenPolohu(932 + this.posun, 335);
                obr.zobraz();

                DataObrazku data = new DataObrazku(predmet.getSuborSObraskom());

                this.hodnotaDukatov = new BlokTextu(String.valueOf(this.hodnota));
                this.hodnotaDukatov.zmenPolohu(932 + this.posun, 335 + data.getVyska() + 15);
                this.hodnotaDukatov.zmenFont("Arial", StylFontu.BOLD, 12);
                this.hodnotaDukatov.zobraz();

                this.posun += data.getSirka() + 10;
                this.zoznamPredmetov.add(predmet);
                this.zoznamObrazkov.add(obr);

            } else {
                this.hodnotaDukatov.zmenText(String.valueOf(this.hodnota));
                for (int i = 0; i < this.zoznamPredmetov.size(); i++) {
                    if (this.zoznamPredmetov.get(i) instanceof Dukaty d) {
                        d.setHodnota(this.hodnota);
                        break;
                    }
                }
            }
        } else {
            Obrazok obr = new Obrazok(predmet.getSuborSObraskom());
            obr.zmenPolohu(932 + this.posun, 335);
            obr.zobraz();

            DataObrazku data = new DataObrazku(predmet.getSuborSObraskom());
            this.posun += data.getSirka() + 10;
            this.zoznamPredmetov.add(predmet);
            this.zoznamObrazkov.add(obr);
        }
    }

    /**
     * Metóda, ktorá zabezpečuje odstránenie predmetu z inventára na základe indexu.
     * @param index
     */
    public void odstranPredmet(int index) {
        if (this.zoznamPredmetov.isEmpty()) {
            return;
        }

        if (this.zoznamPredmetov.get(index) instanceof Dukaty d) {
            if (d.getHodnota() == 0) {
                this.hodnotaDukatov.skry();
                this.zoznamPredmetov.remove(index);
                this.zoznamObrazkov.get(index).skry();
                this.zoznamObrazkov.remove(index);
                this.akualizujInventar();
            } else {
                this.hodnota = d.getHodnota();
                this.hodnotaDukatov.zmenText(String.valueOf(d.getHodnota()));
                this.hodnotaDukatov.zobraz();
            }
            return;
        }
        this.zoznamPredmetov.remove(index);
        this.zoznamObrazkov.get(index).skry();
        this.zoznamObrazkov.remove(index);
        this.akualizujInventar();
    }

    /**
     * Getter, ktorý vracia predmet z inventára na základe indexu.
     * @param index
     * @return predmet
     */
    public Predmet getPredmet(int index) {
        return this.zoznamPredmetov.get(index);
    }

    /**
     * Getter, ktorý vracia dukáty z invetára.
     * @return Dukaty, ak sa nachádzajú v invetári, null inak
     */
    public Dukaty getDukaty() {
        for (Predmet predmet : this.zoznamPredmetov) {
            if (predmet instanceof Dukaty dukaty) {
                return dukaty;
            }
        }
        return null;
    }

    /**
     * Metóda, ktorá zabezpečuje použitie určitého množstva dukátov z inventára.
     * @param kolko
     */
    public void pouziDukaty(int kolko) {
        for (int i = 0; i < this.zoznamPredmetov.size(); i++) {
            if (this.zoznamPredmetov.get(i) instanceof Dukaty) {
                ((Dukaty)this.zoznamPredmetov.get(i)).setHodnota(this.hodnota - kolko);
                this.odstranPredmet(i);
                break;
            }
        }
    }

    /**
     * Metóda, ktorá vracia počet predmetov v inventári.
     * @return počet predmetov
     */
    public int getVelkostInventara() {
        return this.zoznamPredmetov.size();
    }

    /**
     * Metóda, ktorá zabezpečuje aktualizovanie inventára po odstránení alebo pridaní predmetov.
     */
    public void akualizujInventar() {
        this.posun = 0;

        for (Obrazok obr : this.zoznamObrazkov) {
            obr.skry();
        }
        this.zoznamObrazkov.clear();

        for (int i = 0; i < this.zoznamPredmetov.size(); i++) {
            Obrazok obr = new Obrazok(this.zoznamPredmetov.get(i).getSuborSObraskom());
            obr.zmenPolohu(932 + this.posun, 335);
            obr.zobraz();

            DataObrazku data = new DataObrazku(this.zoznamPredmetov.get(i).getSuborSObraskom());

            if (this.zoznamPredmetov.get(i) instanceof Dukaty d) {
                this.hodnotaDukatov.zmenPolohu(932 + this.posun, 335 + data.getVyska() + 15);
            }

            this.posun += data.getSirka() + 10;

            this.zoznamObrazkov.add(obr);
        }
    }

    /**
     * Metóda, ktorá zabezpečuje skrytie inventára z hernej plochy.
     */
    public void skryInventar() {
        for (Obrazok o : this.zoznamObrazkov) {
            o.skry();
        }
    }
}
