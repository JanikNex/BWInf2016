package de.nexus;

import java.io.*;

/**
 * Dieses Programm bearbeitet Aufgabe 4 des BWInf 2016 Runde 1.
 * Durch eine Textdatei wird der Plan eines Radfahrparcours eingelesen und verarbeitet.
 * Dabei soll durch einmaliges durchlaufen des Parcours festgestellt werden koennen, ob dieser befahrbar ist.
 * Sollte er befahrbar sein, soll als Erweiterung Anweisungen fuer den Fahrer ausgegeben werden, wie sich dieser in
 * geraden Streckenabschnitten zu verhalten hat.
 * Als Zusatz kann man sich bei meiner Programmloesung den Parcourplan als Textdatei mit eingesetzten
 * Anweisungen ausgeben lassen, wodurch durch einen erneuten Programmdurchlauf auf Richtigkeit der Anweisungen getestet werden kann.
 *
 * @author Janik Rapp
 * @version 1.0
 */
class BikeAnalyser {

    private final File file; //Datei mit Parcourplan
    private final long filesize; //Groesse der Datei -> Anzahl der Zeichen bzw. Anweisungen
    private long speed = 0; //Geschwindigkeit
    private long length; //Gelesene Datenmenge bei Analyse
    private long actionUp; //Anzahl der Steigungen
    private long actionDown; //Anzahl der Gefaelle
    private long actionEqual; //Anzahl der geraden Strecken
    private long outputUp;
    private long outputDown;
    private boolean fileoutput = false, fileoutputmode = false, possible = true, abort = false;
    private PrintWriter writer;


    /**
     * Constructor - Wird ein neues Objekt dieser Klasse erstellt, wird dabei eine
     * {@link File} uebergeben, welche anschliessend durch den Analyser analysiert wird.
     * Programmablauf wird durchgefuehrt und anschliessend beendent.
     *
     * @param file {@link File} Datei mit Parcourplan
     * @throws IOException Kommt es bei Textinput zu einem Fehler wird eine IOException geworfen
     */
    private BikeAnalyser(File file) throws IOException {
        this.file = file;
        this.filesize = this.file.length();
        this.setOptions();
        printIsolation();
        System.out.println("[BikeAnalyser] Es wurden " + this.filesize + " Anweisungen gefunden!");
        printIsolation();
        this.loadData(1);
        if (!this.possible && this.abort)
            System.out.println("[BikeAnalyser] Der Analyse-Vorgang wurde aufgrund einer unterschrittenen Geschwindigkeit abgebrochen!");
        System.out.println("[BikeAnalyser] FinalSpeed: " + this.speed);
        System.out.println("[BikeAnalyser] Up " + this.actionUp + " | Down " + this.actionDown + " | Equal " + this.actionEqual);
        System.out.println("[BikeAnalyser] Diff: " + this.getDiff(this.speed, this.actionEqual));
        System.out.println("[BikeAnalyser] Drücke Taste um fortzufahren...");
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
        this.calculateSolution();
        System.out.println("[BikeAnalyser] Drücke Taste zum beenden...");
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
        System.exit(0);
    }

    /**
     * Gibt den Begrueßungstext aus, startet die Pfadeingabe und startet anschließend den Analyseprozess.
     *
     * @param args unused
     * @throws IOException Exception bei BufferedReader Error
     */
    public static void main(String[] args) throws IOException {
        printIsolation();
        System.out.println("BikeAnalyser\n \u00A9 Janik Rapp");
        printIsolation();
        File path = inputPath();
        printIsolation();
        BikeAnalyser tester = new BikeAnalyser(path);
    }

    /**
     * Gibt eine Trennungszeile aus.
     */
    private static void printIsolation() {
        System.out.println(
                "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592");
    }

    /**
     * Diese Methode fragt nach einem Dateipfad. Diese kann eingegeben werden und wird anschliessend auf Validitaet geprueft.
     * Trifft dies zu, wird das {@link File} Objekt zurueckgegeben, andernfalls wird erneut gefragt.
     *
     * @return {@link File} Input-File
     * @throws IOException Kommt es bei Textinput zu einem Fehler, wird eine IOException geworfen.
     */
    private static File inputPath() throws IOException {
        System.out.print("[BikeAnalyser] Gib bitte einen Dateipfad zu einem gültigen Streckenplan ein!\n>>");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        File path = new File(br.readLine().replace("\"", ""));
        if (!path.exists()) {
            System.out.println("[ERROR] Datei existiert nicht!");
            return inputPath();
        } else if (!(path.isFile() && path.canRead())) {
            System.out.println("[ERROR] Datei kann nicht gelesen werden!");
            return inputPath();
        } else if (!path.isFile()) {
            System.out.println("[ERROR] Ungueltige Datei!");
            return inputPath();
        }
        return path;
    }

    /**
     * Diese Methode fragt den Benutzer nach einer Eingabe, um die verschiedenen Modi des Programms
     * zu setzen.
     *
     * @throws IOException Kommt es bei Textinput zu einem Fehler, wird eine IOException geworfen.
     */
    private void setOptions() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("[BikeAnalyser] Parcourplan mit eingesetzer Lösung in Datei ausgeben? (y/n) >>");
        if (br.readLine().equals("y")){
            this.fileoutput = true;
            this.fileoutputmode = true;
        }
        System.out.print("[BikeAnalyser] Lösung in Datei ausgeben? (y/n) >>");
        if (br.readLine().equals("y")) {
            this.fileoutput = true;
            this.fileoutputmode = false;
        }
        System.out.print("[BikeAnalyser] Analysevorgang abbrechen, sobald klar ist, dass Parcour nicht überwunden werden kann? (y/n) >>");
        if (br.readLine().equals("y")) this.abort = true;
    }

    /**
     * Oeffnet {@link File} aus this.file, durchlaeuft die einzelnen Zeichen und gibt diese an eine Weiterverarbeitungsmethode, entsprechend
     * der Runde, weiter. {@link IOException} bei Fehler waehrend des lesens der Datei.
     *
     * @param round Runde des Einlesens
     * @throws IOError Fehler beim Dateilesen
     */
    private void loadData(int round) throws IOError {
        try {
            //FileInputStream stream = new FileInputStream(this.file);
            //char current;
            //while (stream.available() > 0) {
            //    current = (char) stream.read();
            //    if (round == 1) {
            //        if (!this.useAction(current)) {
            //            this.possible = false;
            //            if (this.abort) break;
            //        }
            //    } else if (round == 2) this.outputEqualInstructions(current);
            //}
            FileReader reader = new FileReader(this.file);
            int character = reader.read();
            while (character != -1) {
                char current = (char) character;
                if (round == 1) {
                    if (!this.useAction(current)) {
                        this.possible = false;
                        if (this.abort) break;
                    }
                } else if (round == 2) {
                    this.outputEqualInstructions(current);
                }
                character = reader.read();
            }
        } catch (IOException ex) {
            System.err.println("[ERROR] Datei konnte nicht gelesen werden!");
            System.exit(1);
        }
    }


    /**
     * Bekommt einen actioncode {@link Character} und erhoeht die entsprechende Variable. Sollte eine durch 1000000 teilbare
     * Zahl erreicht werden, wir zudem eine Fortschrittsanzeige ausgegeben.
     * Ist die Geschwindigkeit unter 0 und es nicht nicht genug gerade Strecken vorhanden, sodass ein Ausgleich nicht mehr
     * moeglich ist, wird false zurueckgegeben.
     *
     * @param actioncode {@link Character} Parcouractioncode (/,\\,_)
     * @return {@link Boolean} Gibt False zurueck, sollte die Geschwindigkeit unausgleichbar unter null fallen. Andernfalls True
     */
    private boolean useAction(char actioncode) {
        switch (actioncode) {
            case '/':
                this.speed--;
                this.actionUp++;
                break;
            case '\\':
                this.speed++;
                this.actionDown++;
                break;
            case '_':
                this.actionEqual++;
                break;
        }
        length++;
        if (this.length % 1000000 == 0) System.out.println("[BikeAnalyser] " + this.length + " | " + this.filesize);
        return !(this.speed < 0 && this.actionEqual < Math.abs(this.speed));
    }

    /**
     * Diese Methode bekommt einen Actioncode und gibt die passende Loesungsausgabe aus.
     * Sollte eine gerade Strecke gegeben sind, wird entsprechend der zuvor berechneten Werte gehandelt. Somit
     * wird zu beginn nur Beschleunigt, wodurch eine hohe Geschwindigkeit entsteht. Wenn vollständig beschleunigt wurde, dann
     * beginnt der Bremsvorgang, wobei soweit abgebremst wird, dass am Ende die Geschwindigkeit 0 erreicht wird.
     *
     * @param actioncode {@link Character} Parcouractioncode (/,\\,_)
     */
    private void outputEqualInstructions(char actioncode) {
        if (actioncode == '_') {
            if (this.outputDown > 0) {
                this.outputDown--;
                if (fileoutput) {
                    if (fileoutputmode) this.writer.print("\\");
                    else this.writer.print("+");
                } else System.out.print("+");
            } else if (this.outputUp > 0) {
                this.outputUp--;
                if (fileoutput) {
                    if (fileoutputmode) this.writer.print("/");
                    else this.writer.print("-");
                } else System.out.print("-");
            }
        } else if (fileoutput && fileoutputmode) this.writer.print(actioncode);
    }

    /**
     * Berechnet den Unterschied zwischen zwei {@link Long} Werten und gibt diesen zurueck.
     *
     * @param a {@link Long} Wert 1
     * @param b {@link Long} Wert 2
     * @return {@link Long} Differenz
     */
    private long getDiff(long a, long b) {
        if (a > b) return a - b;
        if (a < b) return b - a;
        return 0;
    }

    /**
     * Diese Methode gibt mit einer Textausgabe an, ob der Parcour geloest werden kann. Ausserdem werden benoetigte Werte fuer das Berechnen der Loesung berechent.
     * Zudem wird das Berechnen und Ausgaben der Loesung gestartet.
     *
     * @throws IOException Bei fehlendem Dateizugriff oder einem anderen Fehler des PrintWriters wird eine IOException geworfen.
     */
    private void calculateSolution() throws IOException {
        if ((this.speed == 0 && this.actionEqual == 0) && this.possible) {
            /*Es sind keine geraden Strecken sowie eine Endgeschwindigkeit von 0 vorhanden -> gegebener Plan ist bereits geloest*/
            System.out.println("[BikeAnalyser] Perfekt! Diese Strecke kann mit den gegebenen Anweisungen überwunden werden!");
        } else if ((this.getDiff(0, this.speed) > this.actionEqual) || (this.getDiff(this.actionDown, this.actionUp) > this.actionEqual) || ((this.actionEqual - this.getDiff(this.actionDown, this.actionUp)) % 2 != 0) || ((this.actionEqual - this.speed) % 2 != 0) || !this.possible) {
            /*Es sind nicht genug gerade Strecken da, um die Enddifferenz der Geschwindigkeit auszugleichen, oder es bleibt eine ungerade Anzahl an geraden Stuecken nach ausgleich, oder es kam zu einem "unter-Null" Fehler waehrend der Analyse -> Keine Loesung moeglich*/
            System.out.println("[BikeAnalyser] Diese Strecke kann nicht überwunden werden!");
        } else {
            /*-> Loesung kann berechnet werden*/

            printIsolation();
            System.out.println("[BikeAnalyser] WARNUNG! Da nicht auf den Verlauf des Parcours eingegangen werden kann, sind folgende Angaben unter Vorbehalt!");
            System.out.println("[BikeAnalyser] Um wirklich sicher zu sein, dass der Parcour möglich ist, muss die ausgegebene Lösung erneut eingegeben werden,");
            System.out.println("[BikeAnalyser] wonach eine genaue Aussage über die Möglichkeit getroffen werden kann!");
            printIsolation();
            if (this.speed == 0) {
                if (fileoutput)
                    System.out.println("[BikeAnalyser] Perfekt! Die Strecke kann überwunden werden. \n[BikeAnalyser] Die Anweisungen dazu sind in folgender Datei zu finden: " + file.getPath().replace(".txt", "") + "_new.txt");
                else
                    System.out.println("[BikeAnalyser] Perfekt! Die Strecke kann mit gegebenen Anweisungen überwunden werden!");
            } else if (fileoutput)
                System.out.println("[BikeAnalyser] Die Anweisungen zum Überwinden des Parcours sind in folgender Datei zu finden: " + file.getPath().replace(".txt", "_new.txt"));
            else System.out.println("[BikeAnalyser] Die Strecke kann mit folgenden Anweisungen überwunden werden!");

            /*Berechnung zweier Werte, welche fuer das richtige Verteilen der Loesungsanweisungen benoetigt werden.*/
            calculateSolutionNumbers();
            if (this.fileoutput) {
                /*Falls der Fileoutput Modus aktiviert wurde, wird ein neuer PrintWriter erstellt, welcher in eine neuerstellte Datei schreiben kann.*/
                this.writer = new PrintWriter(file.getPath().replace(".txt", "_new.txt"), "UTF-8");
            }
            this.loadData(2); /*Ruft das erneute Durchlaufen des Parcours auf, wobei die Loesung berechnet wird.*/
            if (fileoutput) this.writer.close(); /*Falls ein PrintWriter vorhanden war, wird dieser nun geschlossen.*/
            System.out.println();
        }

    }

    /**
     * Diese Methode berechnet die Werte, welche zum berechnen der Ausgabe benötigt werden.
     * Zuerst wird dazu die Differenz berechnet, welche ausgeglichen werden muss, um am Ende
     * auf 0 Geschwindigkeit zu kommen. Anschließend wird der Rest berechnet und gleichmaeßig auf
     * UP und DOWN  verteilt.
     */
    private void calculateSolutionNumbers() {
        long diff = Math.abs(this.speed);
        long rest = this.actionEqual - diff;
        if (this.speed > 0) {
            this.outputUp = diff + rest / 2;
            this.outputDown = rest / 2;
        } else if (this.speed < 0) {
            this.outputUp = rest / 2;
            this.outputDown = diff + rest / 2;
        } else if (this.speed == 0) {
            this.outputUp = rest / 2;
            this.outputDown = rest / 2;
        }
        //System.out.println(diff + " | " +rest+"\n"+this.outputUp+ " | " +this.outputDown);
    }
}
