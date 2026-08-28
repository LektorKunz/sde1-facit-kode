# Bibliotekssystemet — facit-kode

Dette er det samlede, kørende facit-projekt for **Software Design 1 — Bibliotekssystemet**
(1. semester, datamatiker). Repoet er en RIGTIG Git-historik, bygget gang for gang, med tags for
hver gang der faktisk ændrede koden. `HEAD` på `main` er sluttilstanden efter gang 9 (uge 47):
lagdelt, filpersistens, alle fem SOLID-principper anvendt.

Se `../design-brief.md` (afsnit 7, "Facit-strategi") for hvorfor projektet er bygget sådan, og
afsnit 3 for **facit-først-formen** (v2): holdet bygger IKKE længere selv koden op fra gang til
gang — de får hver gang vist/udleveret den relevante del af DETTE repo, og analyserer/modellerer
den. Det betyder, at et tag som `gang-04` ikke nødvendigvis kun indeholder filer, dagsplanen for
gang 4 selv nævner — se "Kendt, dokumenteret afvigelse: Liskov (L) og git-historikken" nedenfor.

## Sådan kompileres og køres projektet

Testet med **JDK 21** (`javac 21.0.10`, `openjdk 21.0.10`, Ubuntu-build) i det miljø, dette er
bygget i. Kør `javac -version` / `java -version` for at bekræfte jeres egen version — alt kode
bruger kun sprogfunktioner, der er tilgængelige siden Java 16 (pattern matching for `instanceof`,
text blocks, switch-udtryk med pilesyntaks).

Kildekoden ligger i `src/`, med rodpakken `bibliotek` og underpakkerne `bibliotek.data`,
`bibliotek.logik` og `bibliotek.praesentation` (se pakketabellen nedenfor).

```bash
# Fra facit-kode/ (dette katalogs rod):
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -Dstdout.encoding=UTF-8 -cp out bibliotek.Main
```

**Om `-Dstdout.encoding=UTF-8`:** koden bruger danske tegn (æ/ø/å) og et enkelt metodenavn med
accent (`reservér`), som er gyldig Java-syntaks (Java tillader Unicode-bogstaver i identifikatorer).
Miljøet, dette blev bygget i, har ingen UTF-8-locale sat (`LANG` er tom), så uden dette flag skriver
JVM'en `?` i stedet for æ/ø/å til konsollen — koden er upåvirket, det er kun konsol-outputtets
tegnsæt. På en maskine med en UTF-8-locale (de fleste udviklingsmaskiner, inkl. standard-opsætning
på macOS/Linux med en moderne distro) er flaget ikke nødvendigt, men det er harmløst at have med.
Kildefilerne skal altid kompileres med `-encoding UTF-8` uanset locale.

Programmet er en simpel konsol-menu (`KonsolUI`). Ved opstart forsøger det at indlæse
`boeger.json`/`laanere.json` fra den mappe, programmet køres FRA (arbejdsmappen — se gang 6's
"Typiske fejl" for hvorfor det er vigtigt). Findes filerne ikke (første kørsel), tilføjes to
eksempelbøger og en eksempellåner automatisk, og et menupunkt (`1`-`4`, `0` for at afslutte) vises:

```
--- Biblioteket ---
1) List materialer
2) Lån materiale
3) Aflever materiale
4) Reservér materiale
0) Afslut
```

Kør programmet, lav et par udlån, afslut med `0`, og kør det igen — lånestatussen er stadig der.

## Pakkestruktur (sluttilstand, efter gang 9)

```
bibliotek/                    ← sammensætningsrod
├── Main.java
├── data/                     ← "HVORDAN og HVOR data gemmes"
│   ├── Persistens.java
│   ├── IngenPersistens.java
│   ├── TaelPersistens.java
│   └── FilPersistens.java
├── logik/                    ← domænet + forretningsreglerne
│   ├── Materiale.java, Reserverbar.java
│   ├── Bog.java, Tidsskrift.java, Film.java, Braetspil.java, Podcast.java, Lydbog.java
│   ├── Laaner.java, Fjernlaaner.java, Underviser.java
│   └── MaterialeKatalog.java, LaanerRegister.java, UdlaansHaandtering.java
└── praesentation/            ← "HVORDAN taler systemet med brugeren"
    └── KonsolUI.java
```

## Hvilken gang introducerede hvad

| Klasse/interface | Introduceret | Ændret senere | Bemærkning |
|---|---|---|---|
| `Bog` | Gang 1 | Gang 3 (`implements Materiale`), Gang 5 (`implements Reserverbar`, `reserveretAf`) | |
| `Laaner` | Gang 1 | — | Uændret siden gang 1 |
| `Bibliotek` | Gang 1 | Gang 3 (refaktoreret til `Materiale`) | **Fjernet i gang 4** — erstattet af `MaterialeKatalog`/`LaanerRegister`/`UdlaansHaandtering` (SRP). Findes derfor IKKE på `HEAD`, kun i historikken før tag `gang-04` |
| `Materiale` (interface) | Gang 3 | — | |
| `Tidsskrift`, `Film` | Gang 3 | Gang 5 (`Film implements Reserverbar`) | |
| `Lydbog` | Gang 3 (øvelse 1: fjerde materialetype) | — | |
| `MaterialeKatalog`, `LaanerRegister` | Gang 4 (SRP) | — | `getAlle()` på begge tilføjet allerede i gang 4 |
| `UdlaansHaandtering` | Gang 4 (SRP, 2-parameters konstruktør) | Gang 5 (3. parameter `Persistens`, `reserverMateriale()`) | |
| `Braetspil` | Gang 4 (OCP-demo i gennemgangen) | — | |
| `Podcast` | Gang 4 (øvelse 1: elevens eget OCP-bevis) | — | |
| `Fjernlaaner`, `Underviser` | **Pædagogisk: Gang 9 (L, flyttet fra gang 4 i v2 — se design-brief.md §5b).** **Git-historisk: committet ved gang-04-tagget** (v1-arv, ikke flyttet i historikken — se afsnittet nedenfor) | — | Kun den KORREKTE `Fjernlaaner` er med — den bevidst brudte demo-udgave fra gennemgangen er ikke en del af facit |
| `Reserverbar` (interface) | Gang 5 (ISP) | — | |
| `Persistens` (interface), `IngenPersistens` | Gang 5 (DIP) | — | |
| `TaelPersistens` | Gang 5 (øvelse 2: elevens eget DIP-bevis) | — | |
| `FilPersistens` | Gang 6 | Gang 9 (flyttet til `bibliotek.data`, ny import) | Persisterer KUN `Bog`, og gemmer IKKE `reserveretAf` — bevidste, dokumenterede afgrænsninger fra gang 6, ikke fejl |
| *(ingen nye klasser)* | Gang 2, 7, 8 | — | Se "Git-tags" nedenfor |
| `KonsolUI` | Gang 9 | — | Ny — fandtes ikke før (tidligere hardcodede kald direkte i `Main`) |
| Pakkerne `bibliotek.data`/`logik`/`praesentation` | Gang 9 | — | Al eksisterende kode omorganiseret, ingen forretningslogik ændret |
| `.gitignore` | Gang 7 | — | Se "Git-tags" |

## Git-tags

```
gang-01   Gang 1  (uge 36) — Bog, Laaner, den naive Bibliotek-gudeklasse
gang-03   Gang 3  (uge 39) — Materiale, Tidsskrift, Film, Lydbog
gang-04   Gang 4  (uge 40) — SRP-opsplitning, OCP-beviser (+ Fjernlaaner/Underviser, se note nedenfor)
gang-05   Gang 5  (uge 43) — Reserverbar (ISP), Persistens/IngenPersistens/TaelPersistens (DIP)
gang-06   Gang 6  (uge 44) — FilPersistens (JSON-filpersistens)
gang-09   Gang 9  (uge 47) — Liskov-fokus (L) + lagdelt arkitektur (bibliotek.data/logik/praesentation)
```

### Kendt, dokumenteret afvigelse: Liskov (L) og git-historikken (v2)

Dagsplan-revisionen i v2 flyttede Liskov Substitution (L) fra gang 4 til gang 9 (se
`../design-brief.md` afsnit 5b — SCO, det parallelle programmeringsfag, underviser ikke arv før
sin egen uge 44, tre uger efter SD1's oprindelige uge 40). **Dette repos GIT-HISTORIK er IKKE
flyttet til at følge den nye rækkefølge** — `Fjernlaaner.java`/`Underviser.java` blev committet
tidligt (samme punkt som det oprindelige `gang-04`-tag, fra v1's konstruktion), og et forsøg på at
flytte selve tagget (uden at omskrive historikken) blev afprøvet og rullet tilbage her, fordi
`Main.java`s demo-data allerede refererer til `Fjernlaaner`/`Underviser` fra og med SRP-commit'en
(`ee1465d`) — en pre-eksisterende uoverensstemmelse i v1's konstruktion af repoet, som først blev
synlig, da nogen (denne fil) forsøgte at bruge tagget til noget, det oprindeligt ikke var bygget
til.

**Konsekvens, som den, der underviser gang 4, bør kende:** kigger man i `gang-04`-tagget, ligger
`Fjernlaaner.java`/`Underviser.java` allerede i `src/bibliotek/` — men gang 4's dagsplan nævner dem
IKKE, og det er korrekt (verificeret ved den agent, der reviderede filen). Under facit-først-formen
(v2) er det uproblematisk: holdet ser hele tiden en fuldt fungerende, voksende kodebase, og det er
helt normalt, at nogle filer endnu ikke er "gangens fokus" — pointen er blot, at dagsplanen ikke må
PÅSTÅ, at disse to klasser først opstår i gang 9 (det gør de ikke, teknisk set, i denne git-
historik). Gang 9's dagsplan er skrevet, så den introducerer dem som "lad os endelig se nærmere på
disse to klasser, I måske har lagt mærke til" snarere end "her er noget helt nyt" — hvilket er
konsistent med den faktiske kodebase.

**Hvis en fremtidig revision har tid til det:** en fuld, korrekt løsning kræver en `git rebase -i
--rebase-merges` fra `ee1465d` (SRP-commit'en), der (1) fjerner de to `Fjernlaaner`/`Underviser`-
linjer fra `Main.java` i `ee1465d` selv, og (2) tilføjer dem tilbage i en ny commit nær
`gang-09`-tagget. Dette er ikke gjort her, fordi det kræver at gen-afspille historikkens eneste
merge-commit (gang 8's konstruerede konflikt-øvelse), og risikoen ved at gøre det forkert (et
ødelagt repo, midt i en levering) blev vurderet højere end værdien af en teknisk perfekt
commit-rækkefølge, når den pædagogiske dagsplan-tekst allerede er korrekt uden den.

**Ingen tags for gang-02, gang-07 og gang-08** — som instrueret, fordi disse tre gange ikke ændrer
selve domænekoden:

- **Gang 2 (uge 37, UML):** ren tegneøvelse på gang 1's kode. Ingen commit overhovedet i dette
  repo, fordi der ikke er noget at committe.
- **Gang 7 (uge 45, Git del 1):** dagsplanens egen pointe er, at Git læres PÅ den eksisterende
  kode. Der ER én commit her (`Tilføj .gitignore til Java-projektet`), fordi det er PRÆCIS det,
  gang 7 selv lærer eleverne at gøre først — men ingen domænekode ændres, så ingen tag.
- **Gang 8 (uge 46, Git-workshop):** dagsplanens forberedte merge-konflikt-øvelse er faktisk
  gennemført i dette repos historik (se nedenfor) — men den ændrer kun en logbesked-streng i
  `UdlaansHaandtering.udlaan(...)`, ikke domænelogik, så ingen tag, jf. opgavebeskrivelsens
  instruks.

**Gang 8's merge-konflikt, gennemført i praksis:** for at gøre historikken brugbar som ægte
undervisningsmateriale til uge 46 (jf. `design-brief.md` afsnit 7's anbefaling) er selve
konflikt-øvelsen fra `gang-08-uge46-git-workshop.md` udført på rigtig kode i dette repo, ikke kun
beskrevet: to branches (`feature/udlaan-log-dato`, `feature/udlaan-log-format`) ændrede uafhængigt
PRÆCIS samme linje i `UdlaansHaandtering.udlaan(...)`, blev merget (den første fast-forward, den
anden en ægte konflikt), og konflikten blev løst manuelt ved at kombinere begge idéer. Se
merge-commit `1aecf6a` (`git log --oneline --graph --all`) for beviset — den har to forældre,
præcis som dagsplanen forudsiger. Loggen fra et vellykket udlån hedder derfor, fra og med den
commit, `"UDLÅN: <titel> -> <lånernavn> (<dato>)"` i stedet for gang 4-7's simplere besked.

## Verificeret her

Følgende er FAKTISK kørt og bekræftet i det miljø, dette blev bygget i (JDK 21.0.10, Ubuntu-build,
ingen UTF-8-locale sat):

- **Hver af de seks taggede milepæle** (`gang-01`, `gang-03`, `gang-04`, `gang-05`, `gang-06`,
  `gang-09`) er kompileret uden fejl — både undervejs i konstruktionen af repoet, OG bagefter,
  uafhængigt, ved at `git switch -d` til hver tag i en frisk `git clone` af repoet og køre
  `javac -encoding UTF-8 -d out $(find src -name "*.java")` på ny.
- **Sluttilstanden (`main`, samme commit som tag `gang-09`)** er kompileret og kørt via
  konsol-menuen (`KonsolUI`), med input sendt via en pipe (`printf ... | java ...`), FLERE gange i
  træk i samme mappe, fra en frisk `git clone`:
  - 1. kørsel: ingen `boeger.json`/`laanere.json` findes → korrekt "findes ikke endnu"-besked →
    eksempeldata tilføjes → et udlån gennemføres → `boeger.json` indeholder bagefter både den
    udlånte og den ledige bog med korrekt `tilgaengelig`-værdi.
  - 2. kørsel, samme mappe: data er der stadig (ingen "findes ikke endnu"-besked); et forsøg på at
    genudlåne den samme bog afvises korrekt (`"Kunne ikke udlåne."`), og en liste bekræfter, at
    lånestatussen er den samme som ved afslutningen af 1. kørsel. **Dette er det konkrete bevis på,
    at filpersistens overlever en fuld genstart af JVM'en, jf. opgavens krav #2.**
  - En tredje, separat test (på gang 6-tilstanden, før pakke-omrokeringen) bekræftede desuden, at en
    BEVIDST korrumperet linje i `boeger.json` (manglende afsluttende `}`) håndteres uden at
    programmet krasjer — kun den ene linje springes over, med en klar besked om linjenummer og
    årsag, resten af filen indlæses normalt.
- **`.gitignore` virker som tilsigtet:** `git status --short` efter en kørsel, der har genereret
  `boeger.json`/`laanere.json`, viser INGEN af de to filer som untracked.
- **Gang 8's merge-konflikt** blev udløst og løst som beskrevet ovenfor, med et efterfølgende
  `javac`-tjek, der bekræftede, at koden kompilerer efter konfliktløsningen.
- **`git log --oneline --graph --all`** viser den forventede, forgrenede historik med én
  merge-commit med to forældre.

**Ikke gjort her (ville kræve mere end static compilation/kørsel):** ingen automatiserede
enhedstests (bevidst — SD1 leverer ingen test-øvelser, jf. `design-brief.md`), og ingen afprøvning
på andre JDK-versioner end 21.0.10 eller andre operativsystemer end det Linux-baserede miljø, dette
blev bygget i.

## Uoverensstemmelser fundet i dagsplan-filerne, og hvordan de er løst

**Status: alle tre punkter nedenfor er RETTET i den efterfølgende revisionsrunde** (bekræftet ved
grep/manuel gennemgang af de nævnte filer efter v1→v2-revisionen). Beskrivelserne er bevaret her
som historik/dokumentation af, hvad der blev fundet og rettet — ikke som åbne TODO'er længere:

1. **`FilPersistens`s konstruktør i `gang-09-uge47-softwarearkitektur.md` var selvmodsigende** (en
   forkert TO-parameters konstruktør i ét afsnit, et korrekt tomt konstruktørkald i et andet).
   **Rettet:** filen bruger nu konsekvent gang-06's kanoniske, tomme konstruktør med interne
   konstanter (`BOEGER_FIL`, `LAANERE_FIL`).

2. **Metodenavnet `afleverMateriale` i `gang-07-uge45-git-del1.md` matchede ikke den etablerede
   metode `aflever`.** **Rettet:** alle forekomster (kodeeksempel, `git diff`-illustration,
   commit-besked-eksempel) bruger nu konsekvent `aflever`.

3. **Gang 7's eksempel-`.gitignore` brugte pladsholdernavnet `bibliotek-data.json`** i stedet for
   de faktiske filnavne. **Rettet:** eksemplet bruger nu `boeger.json`/`laanere.json`, i tråd med
   gang 6's facit.

Se også afsnittet "Kendt, dokumenteret afvigelse: Liskov (L) og git-historikken (v2)" ovenfor for
den ENE uoverensstemmelse, der IKKE er rettet — bevidst, med en begrundelse.

## Bevidste, dokumenterede afgrænsninger i facit-koden (ikke fejl)

Disse er gentaget her for overblikkets skyld — de er alle eksplicit beskrevet i de relevante
dagsplan-filer, ikke noget der er "glemt" i denne facit-kode:

- `FilPersistens` gemmer/henter KUN `Bog`-instanser (via `instanceof Bog`) — `Tidsskrift`, `Film`
  og de øvrige `Materiale`-typer persisteres ikke. Se `gang-06`, øvelse 3 (retning A) for den
  naturlige udvidelse, som IKKE er implementeret i grundfacit her.
- `reserveretAf`-feltet på `Bog`/`Film` (fra `Reserverbar`, gang 5) persisteres ikke, og har
  bevidst INGEN getter i grundfacit. Se `gang-06`, øvelse 3 (retning B).
- JSON-parsingen i `FilPersistens` er en bevidst, forenklet, håndrullet udgave (ét fladt objekt pr.
  linje, naiv split på komma — går galt på en værdi, der selv indeholder et komma). Se `gang-06`s
  "Til underviseren"-boks i lektion 2 for den fulde begrundelse (intet build-værktøj etableret i
  forløbet, ingen verificeret adgang til Maven Central i byggemiljøet).
- `Bibliotek.java` (gang 1's gudeklasse) findes IKKE på `HEAD` — den er bevidst fjernet i gang 4's
  SRP-commit, i tråd med gang 4's egen "Typiske fejl"-note ("`Bibliotek`-klassen er ERSTATTET, ikke
  suppleret"). Den findes stadig i historikken, før tag `gang-04`.
- Den brudte, LSP-krænkende `Fjernlaaner`-udgave fra gang 4's gennemgang (den, der kaster
  `UnsupportedOperationException`) er bevidst IKKE en del af facit-koden — kun den korrekte,
  endelige udgave er committet, i tråd med øvelsesteksten ("Slet den brudte variant bagefter — I
  skal kun aflevere den korrekte udgave").
- `KonsolUI.start()` har et ekstra `scanner.hasNextLine()`-tjek, som ikke er vist i
  `gang-09`-dagsplanens kodeeksempel. Det er en lille, praktisk tilføjelse (ikke en pædagogisk
  pointe), der lader programmet afslutte roligt i stedet for at kaste en `NoSuchElementException`,
  når input løber tør — relevant her, fordi den automatiserede verifikation i dette dokument kører
  programmet med input sendt via en pipe. Uden tjekket opfører programmet sig identisk ved normal,
  interaktiv brug fra en terminal.
