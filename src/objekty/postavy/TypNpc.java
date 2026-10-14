package objekty.postavy;

import objekty.predmety.AmuletOdvahy;
import objekty.predmety.ElixirZdravia;
import objekty.predmety.Predmet;

/**
 * Enum TypNpc reprezentuje rôzne typy npc v hre.
 * @author Emma Lujza Rothová
 */
public enum TypNpc {
    ZABA("Frog", new String[]{"pics/frog1.png", "pics/frog2.png", "pics/frog3.png", "pics/frog4.png"}, "uhlie", 56, new AmuletOdvahy("AmuletOdvahy", "pics/amuletOdvahy1.png", 0, 0)),
    MACKA("Cat", new String[]{"pics/cat1.png", "pics/cat2.png", "pics/cat3.png", "pics/cat4.png"}, "zajtrajšok", 98, new ElixirZdravia("ElixirZdravia", "pics/elixirZdravia1.png", 0, 0));

    private final String meno;
    private final String[] zoznamObrazkov;
    private final String spravnaOdpoved;
    private final int vyskaUplatku;
    private final Predmet odmena;

    /**
     * Konštruktor enumu TypNpc.
     * @param meno
     * @param obrazok
     * @param spravnaOdpoved
     * @param vyskaUplatku
     * @param odmena
     */
    TypNpc(String meno, String[] obrazok, String spravnaOdpoved, int vyskaUplatku, Predmet odmena) {
        this.meno = meno;
        this.zoznamObrazkov = obrazok;
        this.spravnaOdpoved = spravnaOdpoved;
        this.vyskaUplatku = vyskaUplatku;
        this.odmena = odmena;
    }

    /**
     * Getter, ktorý vracia meno npc.
     * @return meno
     */
    public String getMeno() {
        return this.meno;
    }

    /**
     * Getter, ktorý vracia cestu k obrázku na základe indexu i.
     * @param i
     * @return cesta k obrázku
     */
    public String getObrazok(int i) {
        return this.zoznamObrazkov[i];
    }

    /**
     * Getter, ktorý vracia počet obrázkov dostupných v zozname.
     * @return počet obrázkov
     */
    public int getVelkostZonamuObrazkov() {
        return this.zoznamObrazkov.length;
    }

    /**
     * Getter, ktorý vracia správnu odpoveď na položenú otázku.
     * @return správna odpoveď
     */
    public String getSpravnaOdpoved() {
        return this.spravnaOdpoved;
    }

    /**
     * Getter, ktorý vracia výšku úplatku npc.
     * @return výška úplatku
     */
    public int getVyskaUplatku() {
        return this.vyskaUplatku;
    }

    /**
     * Getter, ktorý vracia predmet, ktorým bude hráč odmenený.
     * @return odmena
     */
    public Predmet getOdmena() {
        return this.odmena;
    }
}
