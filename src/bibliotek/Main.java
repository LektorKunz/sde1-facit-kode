package bibliotek;

public class Main {
    public static void main(String[] args) {
        Bibliotek bibliotek = new Bibliotek();

        bibliotek.tilfoejBog(new Bog("Effective Java", "Joshua Bloch", "978-0134685991"));
        bibliotek.tilfoejBog(new Bog("Clean Code", "Robert C. Martin", "978-0132350884"));
        bibliotek.tilfoejBog(new Bog("Design Patterns", "Erich Gamma", "978-0201633610"));

        bibliotek.registrerLaaner(new Laaner("Anna Nielsen", "L001"));
        bibliotek.registrerLaaner(new Laaner("Bo Hansen", "L002"));

        boolean success = bibliotek.udlaanBog("978-0134685991", "L001");
        System.out.println("Udlån 1 lykkedes: " + success);

        boolean gentagetForsoeg = bibliotek.udlaanBog("978-0134685991", "L002");
        System.out.println("Udlån 2 (allerede udlånt) lykkedes: " + gentagetForsoeg);

        bibliotek.afleverBog("978-0134685991");
        boolean efterAflevering = bibliotek.udlaanBog("978-0134685991", "L002");
        System.out.println("Udlån efter aflevering lykkedes: " + efterAflevering);

        System.out.println("Bøger af Joshua Bloch: " + bibliotek.findBoegerAfForfatter("Joshua Bloch"));
        System.out.println("Udlånte bøger: " + bibliotek.listUdlaanteBoeger());
    }
}
