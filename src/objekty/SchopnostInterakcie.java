package objekty;

import objekty.postavy.Hrac;

/**
 * Interface SchopnostInterakcie reprezentuje schopnosť objektu interagovať s hráčom.
 * @author Emma Lujza Rothová
 */
public interface SchopnostInterakcie {
    void interakcia(Hrac hrac);
    double vzdialenost (Hrac hrac);
    boolean kontrolaVzdialenostiPreInterakciu(Hrac hrac);
}
