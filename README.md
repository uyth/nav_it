# NAV IT

## Kodeoppgave

> Skriv kode som validerer norske fødselsnummer. Du velger selv språk og hvordan du vil løse oppgaven, men husk på at vi ønsker å se dine styrker, hvor dyktig du er og ditt potensiale innen utvikling. Besvarelsen skal inneholde tester.

### Criteria
-   The National IDs are represented as a string
-   The string must only contain numbers
-   The string must be 11 characters long
-   The first 6 characters must be a valid date with format ddMMyy
-   The individual number, the 7th to the 9th character,
    must conform to some specific rules for the given year
    
    >1854-1899, brukes serien 500-749
    >
    >1900-1999, brukes serien 000-499
    >
    >1940-1999, brukes også serien 900-999
    >
    >2000-2039, brukes serien 500-999
    
    *-Skatteetaten*

    source: https://www.skatteetaten.no/person/folkeregister/fodsel-og-navnevalg/barn-fodt-i-norge/fodselsnummer/
    
    Thus for year yy in the National IDs:
    
        year = [xx00, xx39] uses the series [000, 999]
        year = [xx40, xx53] uses the series [000, 499] and [900, 999]
        year = [xx54, xx99] uses the series [000, 499], [500, 749] and [900, 999]
    
-   The checksum must correspond to the control digits
    
    The following checksum model is used:
    http://www.fnrinfo.no/Teknisk/KontrollsifferSjekk.aspx

    `sum` is the sum of the products of the n-th digit in
     the national number and the n-th number in a given list. 
     We then get a computed value by subtracting `sum modulo 11` from `11` 
    
        computed value = (11 - sum % 11)

    and then check if
    
        computed value % 11 == checksum digit
        
    The checksum model says that if the computed value is 11, 
    it should resolve to 0 instead. Thus we use `modulo 11` on the computed value.
    If the computed value resolves to 10, the checksum model says that the checksum is wrong, 
    and the National ID is not valid. This will always be the case for 10 because
    it is a two digit number. For all other single digit computed values
     it should resolve to the checksum digit. If not, we know that the National ID
     is not valid.

#### D-Number and H-Number

There is also something called a D-Number and H-Number. They act identically to
the National IDs, besides that:

  - A D-Number has its first digit shifted by 4 (up)
  - An H-Number has its third digit shifted by 4 (up)

    source: https://www.skatteetaten.no/person/utenlandsk/norsk-identitetsnummer/d-nummer/
    source: https://no.wikipedia.org/wiki/F%C3%B8dselsnummer    

Note that this only changes the date criteria and that the first 6 digits must be validated in another way
than the normal National IDs.
    
### Structure

The Java project consists of five major parts:
  1. An interface
  2. An abstract class that implements that interface
  3. Implementations of the abstract class
  4. Tests for each of the implementation
  5. A runnable Main-class that validates arguments as National IDs.

#### Unit tests - NationalIDValidatorTest, DNumberValidatorTest and HNumberValidatorTest
The unit tests are written against the criteria mentioned in the previous section.

Note that a change in the checksum digits will invalidate the National ID.

#### Interface - INationalIDValidator
A set of methods we want the Validator to have. These correspond to the criteria.
  
#### Abstract class - AbstractNationalIDValidator
An abstract class that implements `INationalIDValidator`. It implements the `validateNationalID`-method,
which uses all the other unimplemented methods from the `INationalIDValidator`-interface.

In the test we are using the *Liskov substitution principle* to constrict us 
to this class. It makes the code more maintainable and we can have several
implementation of `AbstractNationalIDValidator`.


#### Implementation - NationalIDValidator, DNumberValidator, HNumberValidator
`NationalIDValidator` extends `AbstractNationalIDValidator` and implements the `INationalIDValidator`. It uses `SimpleDateFormat` to validate
dates.

Note the `dateFormat.setLenient(false)` in the constructor of `NationalIDValidtor`.
 The tests gave some funky results when the date was set to `32` with the default setting.
 
`DNumberValidator` and `HNumberValidator` extends `NationalIDValidator`. They both
override the `ìsValidDate` with their own implementation, which also calls on
the `NationalValidator`'s `isValidDate`-method
 
### Main - A simple command line interface
Main is used for the compiled jar to run a command that accepts string
arguments.

## Compiling the project and exporting a jar
        
    git clone https://github.com/uyth/nav_it.git validator
    cd validator/src
    javac main/*.java
    jar -cvfm validator.jar META-INF/MANIFEST.MF main/*

Eureka! We have a jar!

Optionally, you could can use your IDE(A) to build the jar.

### Using the compiled jar

The jar uses strings as arguments and validates them as National IDs.
 To use the jar in the terminal, locate the directory of `validator.jar` and use
 the command

    java -jar validator.jar arg1 arg2 ...
    
where `arg1, arg2, ...` are the National IDs that you want to validate.

Optionally, you can use the command

    java -jar validator.jar
    
to enter a simple CLI.

## Intervjuspørsmål


1. **Hva er ditt favorittprogrammeringsspråk, og hvorfor?**
   
    Det står mellom Java og Python. Jeg liker Java for større prosjekter siden
    det er lettere å holde styr på strukturen, mens python er mitt *go-to* scriptespråk.
    
    Som dere ser har jeg valgt å skrive koden i Java. Det er lett å skrive unit-tester,
    grensesnitt gjør det enklere å holde styr på hva klasser gjør, og Java-kode er
    enkel å debugge. Java er objektorientert av natur, noe som gjør Java-kode 
    som en drøm å dekomponere.
    Andre grunner er at jeg er vant med å bruke IntelliJ og at Java er statisk typet.
    
    Ellers er jeg veldig spent på å lære mer om Elm, og garantien om null runtime
    errors.
   
2. **Hva er best av quicksort og bubblesort og hvorfor?**
    
    Quicksort er i de aller fleste tilfeller bedre enn Bubblesort.
    
    Grunnen er at Quicksort benytter seg av *Splitt-og-hersk-prinsippet*.
    Det vil si at problemet deles opp i mindre håndterlige delproblemer,
    og igjen, osv., til delproblemene er enkle å løse.
    Da kan delløsningene kombineres for å løse det større initielle problemet.
    
    Quicksort gjør dette ved å velge en vilkårlig pivot og opererer
    med to sub-lister som enten er større enn eller mindre enn 
    pivoten. Man gjentar denne operasjonene rekursivt
    til det kun er ett element igjen i lista. Når det kun er ett element i lista,
    returneres pivoten og det ene elementet i korrekt rekkefølge.
    Dette vil gjenta seg flere ganger fram til vi har en fin og sortert liste.
    
    Worst-case-scenarioet til Quicksort er når
    pivoten alltid er det største tallet i lista, altså en reversert sortert liste.
    Da opererer man ikke lenger med to lister,
    og Quicksort mister fordelen av splitt-og-hersk.
    Quicksort blir med andre ord tilsvarende Bubblesort.
    
    Bubblesort på den andre siden er veldig "naiv" og traverserer gjennom hele
    lista n ganger. For hver traversering gjøres også n sjekker og to elementer bytter indeks.
    
    Vi får dermed følgende gjennomsnittlige kjøretider:
    
    - Bubblesort: O(n²)
    
    - Quicksort: O(nlogn)
    
    Quicksort er garantert bedre enn Bubblesort for sortering. Bubblesort
    kan derimot være et praktisk introduksjonseksempel om man lærer bort
    sorteringsalgoritmer. Man forstår Bubblsort veldig intuitivt!
   
3. **Hva legger du i god kodekvalitet og er dette viktig?**

   **Koden skal være lett å forstå og lett å endre på.**
   Metoder bør være oppdelt i abstraksjonsnivåer slik at koden blir enklere å 
   håndtere. Topologien til metodene bør være slik at koden kan leses som en rød tråd 
   fra topp til bunn.
   
   Altså slik:
        
        Kode:
        A
            B
            C
        D
            E
                F
            G
           
   Her er A og D på det høyeste nivået, B, C, E og G er på ett nivå lavere, og 
   F på det laveste abstraksjonsnivået. Her vil A kalle B og C, D kaller E og G,
   og E kaller F. For eksempel kan A være "Les en bok", mens B er "Les kapitler"
   
   En annen ting jeg legger i kodekvalitet er at **metoder og variabler skal
   ha semantiske navn**. Av å se på navnet til en metode skal man med 
   en gang ha en idé om hva den gjør. Dersom det ikke kommer tydelig fram
   av navnet, bør man kanskje endre på navnet eller dele metoden opp i mindre deler.
   
   **God kodekvalitet fasiliterer godt samarbeid og deling.** Dersom man ikke 
   har fokus på god kodekvalitet vil...
   - det ta lenger tid for de du jobber med å forstå hva koden din gjør
   - koden bli vanskeligere å endre og å videreutvikle
   - koden bli skrevet om på nytt
   
   Dårlig kodekvalitet sløser altså mye ressurser og 
   resulterer ofte i *spaghettikode* - 
   en krunglete kode som ingen egentlig ikke forstår. 
   
4. **Hvorfor vil du jobbe i NAV?**

   For meg så er en jobb i NAV en mulighet til å bidra i samfunnet.
   Jeg er veldig glad i velferdsstaten og vil veldig gjerne være med på å
   jobbe med tjenester og produkter som hjelper mindre ressurssterke, 
   eller de som har havnet i en vanskelig knipe.
   Det er litt som å være en helt som jobber bak kulissene.
   
   Jeg har stor tro på at mange av utfordringer staten har,
   kan løses og effektiviseres med IT og innovasjon. Dette kan være alt 
   fra å effektivisere og automatisere byråkratiske prosedyrer,
   opprette tettere kommunikasjon mellom velferdstaten og befolkningen,
   lage et forståelig brukergrensesnitt eller 
   bruke ny teknologi for å takle problemer fra en ny vinkel.
   Dette er en stor motivasjon for meg som jobbsøker
   fordi jeg vil være med på å skape verdi for samfunnet.
   Jeg vil med andre ord jobbe med *det som betyr noe*.
   