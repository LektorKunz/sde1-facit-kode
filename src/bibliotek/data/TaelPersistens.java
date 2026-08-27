package bibliotek.data;

import bibliotek.logik.MaterialeKatalog;
import bibliotek.logik.LaanerRegister;

// GANG 5 (uge 43), øvelse 2: en anden Persistens-stand-in (ikke en rigtig fil), der beviser at
// UdlaansHaandtering ikke skal ændres for at bruge en anden implementering (DIP).
public class TaelPersistens implements Persistens {
    private int antalGemKald = 0;

    @Override
    public void gem(MaterialeKatalog katalog, LaanerRegister laanerRegister) {
        antalGemKald++;
        System.out.println("(TaelPersistens: gem() er nu kaldt " + antalGemKald + " gang(e))");
    }

    @Override
    public void hent(MaterialeKatalog katalog, LaanerRegister laanerRegister) {
        System.out.println("(TaelPersistens: henter ikke rigtige data endnu)");
    }
}
