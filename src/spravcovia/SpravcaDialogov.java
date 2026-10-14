package spravcovia;

import objekty.postavy.TypNpc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Trieda SpravcaDialogov reprezentuje správcu dialógov v hre.
 * @author Emma Lujza Rothová
 */
public class SpravcaDialogov {
    private HashMap<TypNpc, ArrayList<String>> dialogy;

    /**
     * Konštruktor triedy SpravcaDialogov.
     */
    public SpravcaDialogov() {
        this.dialogy = new HashMap<TypNpc, ArrayList<String>>();
        this.nacitajDialogy();
    }

    /**
     * Metóda, ktorá zabezpečuje načítanie dostupných dialógov zo súboru.
     */
    public void nacitajDialogy() {
        Scanner skener = null;
        try {
            skener = new Scanner(new File("txtSubory/DialogyNpc.txt"));
            String kluc = null;
            StringBuilder dialog = null;
            StringBuilder spravne = null;
            StringBuilder nespravne = null;
            String posledneSlovo = null;

            while (skener.hasNextLine()) {
                String riadok = skener.nextLine().trim();

                if (riadok.isEmpty()) {
                    this.dialogy.get(TypNpc.valueOf(kluc)).add(nespravne.toString());
                    kluc = null;
                    dialog = null;
                    spravne = null;
                    nespravne = null;

                } else {
                    if (kluc == null) {
                        kluc = riadok;
                        this.dialogy.putIfAbsent(TypNpc.valueOf(kluc), new ArrayList<String>());
                        continue;
                    }

                    if (riadok.contains("Dialog:")) {
                        dialog = new StringBuilder();
                        posledneSlovo = "Dialog:";
                        continue;

                    } else if (riadok.contains("Moznosti:")) {
                        posledneSlovo = "Moznosti:";
                        this.dialogy.get(TypNpc.valueOf(kluc)).add(dialog.toString());
                        continue;

                    } else if (riadok.contains("Spravne:")) {
                        spravne = new StringBuilder();
                        posledneSlovo = "Spravne:";
                        continue;

                    } else if (riadok.contains("Nespravne:")) {
                        nespravne = new StringBuilder();
                        posledneSlovo = "Nespravne:";
                        this.dialogy.get(TypNpc.valueOf(kluc)).add(spravne.toString());
                        continue;
                    }

                    switch (posledneSlovo) {
                        case "Dialog:":
                            dialog.append(riadok).append("\n");
                            break;

                        case "Moznosti:":
                            String [] zoznamSlov = riadok.split(" ");
                            for (int i = 0; i < zoznamSlov.length; i++) {
                                this.dialogy.get(TypNpc.valueOf(kluc)).add(zoznamSlov[i]);
                            }
                            break;

                        case "Spravne:":
                            spravne.append(riadok).append("\n");
                            break;

                        case "Nespravne:":
                            nespravne.append(riadok).append("\n");
                    }
                }
            }
            if (kluc != null && nespravne != null) {
                this.dialogy.get(TypNpc.valueOf(kluc)).add(nespravne.toString());
            }
        } catch (IOException e) {
            System.out.println("Subor nebol najdeny!");
        } finally {
            skener.close();
        }
    }

    /**
     * Getter, ktorá vracia dialóg pre daný typ npc.
     * @param npc
     * @return dialóg pre daný typ npc
     */
    public String ziskajDialog(TypNpc npc) {
        if (!this.dialogy.containsKey(npc)) {
            return null;
        }
        return this.dialogy.get(npc).get(0);
    }

    /**
     * Getter, ktorý vracia zoznam s odpoveďami na otázku.
     * @param npc
     * @return zoznam s odpoveďami na otázku
     */
    public LinkedHashMap<String, Boolean> ziskajMoznosti(TypNpc npc) {
        if (!this.dialogy.containsKey(npc)) {
            return null;
        }
        LinkedHashMap<String, Boolean> moznosti = new LinkedHashMap<String, Boolean>();
        for (int i = 1; i <= 3; i++) {
            if (this.dialogy.get(npc).get(i).equals(npc.getSpravnaOdpoved())) {
                moznosti.put(this.dialogy.get(npc).get(i), true);
            } else {
                moznosti.put(this.dialogy.get(npc).get(i), false);
            }
        }
        return moznosti;
    }

    /**
     * Getter, ktorý vracia reakciu npc na základe správnosti hráčovho výberu z možností odpovedí na otázku.
     * @param npc
     * @param spravnostOdpovede
     * @return reakciu npc na odpoveď
     */
    public String ziskajOdpoved(TypNpc npc, boolean spravnostOdpovede) {
        if (spravnostOdpovede) {
            return this.dialogy.get(npc).get(4);
        }
        return this.dialogy.get(npc).get(5);
    }
}
