# Bibliotekssystemet — facit-kode

Dette er det samlede, kørende facit-projekt for **Software Design 1 — Bibliotekssystemet**
(1. semester, datamatiker). Repoet er en RIGTIG Git-historik, bygget gang for gang, med tags for
hver gang der faktisk ændrede koden. `HEAD` på `main` er sluttilstanden efter gang 9 (uge 47):
lagdelt, filpersistens, alle fem SOLID-principper anvendt.

Se `../design-brief.md` (afsnit 7, "Facit-strategi") for hvorfor projektet er bygget sådan.

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
| `Fjernlaaner`, `Underviser` | Gang 4 (LSP) | — | Kun den KORREKTE `Fjernlaaner` er med — den bevidst brudte demo-udgave fra gennemgangen er ikke en del af facit |
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
gang-04   Gang 4  (uge 40) — SRP-opsplitning, OCP- og LSP-beviser
gang-05   Gang 5  (uge 43) — Reserverbar (ISP), Persistens/IngenPersistens (DIP)
gang-06   Gang 6  (uge 44) — FilPersistens (JSON-filpersistens)
gang-09   Gang 9  (uge 47) — lagdelt arkitektur (bibliotek.data/logik/praesentation)
```

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

Ud over de allerede rettede detaljer, opgaven selv nævnte (`LaanerRegister.getAlle()`,
`FilPersistens`s tomme konstruktør, at kun `Bog` persisteres), stødte konstruktionen af dette
facit-projekt på følgende yderligere uoverensstemmelser, som er løst pragmatisk nedenfor. Dette er
tænkt som feedback til den, der reviderer dagsplan-filerne:

1. **`FilPersistens`s konstruktør i `gang-09-uge47-softwarearkitektur.md` er selvmodsigende.**
   Lektion 2 viser en `FilPersistens`-klasse med en TO-parameters konstruktør
   (`FilPersistens(String materialeFil, String laanerFil)`), men lektion 3's `Main`-eksempel i
   SAMME fil kalder `new FilPersistens()` — UDEN parametre. Det er internt modstridende, og
   modstrider desuden `gang-06-uge44-filpersistens.md`s facit, som eksplicit har en TOM
   konstruktør og interne konstanter (`BOEGER_FIL`, `LAANERE_FIL`). **Løsning:** brugt gang-06's
   version (tom konstruktør, interne konstanter) som den kanoniske `FilPersistens`, kun flyttet til
   `bibliotek.data`-pakken i gang 9 — i tråd med opgavens eksplicitte, allerede rettede facts.
   `gang-09`-filens `FilPersistens`-kodeeksempel bør rettes til at matche gang 6's faktiske
   implementering (dette er også eksplicit nævnt som en TODO i selve `gang-09`-filens indledende
   "Til underviseren"-boks, som blev skrevet, før `gang-06` fandtes som fil).

2. **Metodenavnet `afleverMateriale` i `gang-07-uge45-git-del1.md` matcher ikke den etablerede
   metode `aflever`.** Gang 3-6 og gang 9 bruger konsekvent `UdlaansHaandtering.aflever(String
   titel)`. Gang 7's lektion 3 (`git diff`-demonstration) bruger i stedet et hypotetisk eksempel
   med metodenavnet `afleverMateriale(String titel)`, indledt med "Forestil jer, at I retter en
   fejl i...". **Løsning:** behandlet som en illustrativ, ikke-bindende demonstration af selve
   `git diff`-mekanikken (pointen med afsnittet), ikke en reel ændring af facit-koden. Den
   faktiske `UdlaansHaandtering` i dette repo beholder navnet `aflever` konsekvent. Bør rettes til
   `aflever` i `gang-07`-filen for konsistens, hvis filen redigeres igen.

3. **Gang 7's eksempel-`.gitignore` bruger et andet datafilnavn end det, gang 6 faktisk etablerer.**
   `gang-07-uge45-git-del1.md` og dens facit-`.gitignore` ekskluderer `bibliotek-data.json` — et
   generisk pladsholdernavn — mens `gang-06-uge44-filpersistens.md`s facit (og opgavens egne,
   allerede rettede facts) fastslår, at de faktiske filnavne er `boeger.json` og `laanere.json`.
   **Løsning:** dette repos `.gitignore` (tilføjet ved "gang 7"-punktet i historikken) ekskluderer
   de FAKTISKE filnavne, `boeger.json` og `laanere.json`, i stedet for pladsholderen. Bør rettes i
   `gang-07`-filen, så eksemplet matcher gang 6's facit ordret.

Ingen af de tre punkter forhindrede et rent kompilerende slutresultat — de er alle i illustrative
kodeeksempler eller i en fil, der eksplicit selv flagger sig som skrevet før sin afhængighed
(`gang-06`) fandtes. De er dokumenteret her, fordi de er tydelige, konkrete rettelser til næste
redigeringsrunde af dagsplan-filerne.

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
