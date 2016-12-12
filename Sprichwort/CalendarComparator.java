package de.nexus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Dieses Programm bearbeitet Aufgabe 1 des BWInf 2016 Runde 1.
 * Es wird der Zusammenfall von Ostern und Weihnachten anhand Berechnung der Daten in zwei unterschiedlichen
 * Kalendersystemen getestet.
 *
 * @author Janik Rapp
 * @version 1.0
 */
class CalendarComparator {

    private int mode;

    /**
     * Constructor - Erstellt ein neues {@link CalendarComparator} Objekt, fuehrt einen Programmdurchlauf aus und beendet das Programm.
     *
     * @throws IOException IOException bei Scanner und BufferedReader Error
     */
    private CalendarComparator() throws IOException {
        this.mode = this.setDirection();
        this.mainloop(0);
        printBorder();
        System.out.println("Drücke Taste zum beenden...");
        //noinspection ResultOfMethodCallIgnored
        System.in.read();
        System.exit(0);
    }

    /**
     * Startmethode des CalendarComparators. Sie zeigt den Starttext und erstellt ein neues {@link CalendarComparator} Objekt
     *
     * @param args unused
     * @throws IOException IOException bei Scanner und BufferedReader Error
     */
    public static void main(String[] args) throws IOException {
        printBorder();
        System.out.println("CalendarComparator\n \u00A9 Janik Rapp");
        printBorder();
        CalendarComparator tester = new CalendarComparator();
    }

    /**
     * Diese Methode fuegt einen Trennstrich in die Textausgabe ein.
     */
    private static void printBorder() {
        System.out.println(
                "\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592");
    }

    /**
     * Diese Methode fordert den Benutzer zu einer Int-Eingabe zwischen 1 und 2 auf,
     * wodurch der Richtungsmodus zur Berechnung des Zusammenfalls gesetzt wird.
     * Wird eine ungueltige Zahl oder ein ungueltiges Zeichen eingegeben, wird der Benutzer zu
     * einer erneuten Eingabe aufgefordert.
     *
     * @return {@link Integer} Zahl des Modus, welcher gewaehlt wurde.
     */
    private int setDirection() {
        System.out.println("[CalendarComparator] Setze den Vergleichsmodus, um die Datensuche zu beginnen! Gib dazu eine der vorrangestellten Ziffern ein!");
        System.out.println("[CalendarComparator] 1 - Orthodoxes Osterfest = Christliches Weihnachtsfest");
        System.out.println("[CalendarComparator] 2 - Christliches Osterfest = Orthodoxes Weihnachtsfest");
        System.out.print(">> ");
        try {
            Scanner in = new Scanner(System.in);
            int result = in.nextInt();
            if (result > 0 && result < 3) return result;
            else return setDirection();
        } catch (NoSuchElementException | IllegalStateException ex) {
            System.out.println("Ungueltige Eingabe!");
            return setDirection();
        }
    }

    /**
     * Diese Methode laesst das Weihnachtsdatum berechnen und gibt es anschliessend als {@link Date} Objekt zurueck.
     *
     * @param year {@link Integer} Jahr, dessen Weihnachtsdatum berechnet werden soll
     * @param type {@link Integer} Kalendersystem, nach dem das Weihnachtsdatum berechnet werden soll: 1=Christlich 2=Orthodox
     * @return {@link Date} Dateobjekt mit dem berechneten Weihnachtsdatum
     */
    private Date getChristmasDate(int year, int type) {
        switch (type) {
            case 1:
                //noinspection deprecation
                return new Date(year - 1900, 11, 25);
            case 2:
                CalendarUtil cutil3 = CalendarUtil.makeDateGreg(CalendarUtil.getDayJul(25, 12, year));
                //noinspection deprecation
                return new Date(cutil3.getJahr() - 1900, cutil3.getMonat() - 1, cutil3.getTag());
        }
        return null;
    }

    /**
     * Diese Methode laesst das Osterdatum berechnen und gibt es anschliessend als {@link Date} Objekt zurueck.
     *
     * @param year {@link Integer} Jahr, dessen Osterdatum berechnet werden soll
     * @param type {@link Integer} Kalendersystem, nach dem das Osterdatum berechnet werden soll: 1=Christlich 2=Orthodox
     * @return {@link Date} Dateobjekt mit dem berechneten Osterdatum
     */
    private Date getEasterDate(int year, int type) {
        switch (type) {
            case 1:
                EasterUtil eutil = EasterUtil.Eingabe(year, 1);
                //noinspection deprecation
                return new Date(eutil.getJahr() - 1900, eutil.getMonat() - 1, eutil.getTag());
            case 2:
                EasterUtil eutil2 = EasterUtil.Eingabe(year, 0);
                CalendarUtil cutil = CalendarUtil.makeDateGreg(CalendarUtil.getDayJul(eutil2.getTag(), eutil2.getMonat(), eutil2.getJahr()));
                //noinspection deprecation
                return new Date(cutil.getJahr() - 1900, cutil.getMonat() - 1, cutil.getTag());
        }
        return null;
    }

    /**
     * Diese Methode ist die Hauptschleife des Programms. Es wird nach einem Treffer der {@link CalendarComparator#compareDates compareDates}
     * Methode gesucht und bei einem Treffer gefragt, ob weitergesucht werden soll, wodurch bei einem Yes weitergesucht wird.
     * Wird in dem momentanen Jahr kein Treffer gefunden, wird im nächsten Jahr weitergesucht.
     *
     * @param start {@link Integer} Jahr, in dem die Suche nach zusammenfall begonnen werden soll.
     * @throws IOException Falls eine I/O Exception waehrend des einlesens durch br.readLine() auftritt.
     */
    private void mainloop(int start) throws IOException {
        while (!compareDates(start)) {
            start++;
        }
        if (this.mode == 1)
            System.out.println("[CalendarComparator] Treffer! Zusammenfall von Ostern und Weihnachten im Jahr " + start);
        if (this.mode == 2)
            System.out.println("[CalendarComparator] Treffer! Zusammenfall von Ostern mit Weihnachten des Vorjahres im Jahr " + start);
        printDates(this.mode, start);
        System.out.print("[CalendarComparator] Weitersuchen? (y/n) >>");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        if (br.readLine().equals("y")) this.mainloop(start + 1);
    }

    /**
     * Gemaess des zu beginn gesetzten Modus, werden beide Daten berechnet und anschliessend verglichen.
     *
     * @param year {@link Integer} Jahr, dessen Osterdatum und Weihnachtsdatum gemaess des gesetzten Modus verglichen werden soll.
     * @return {@link Boolean} True, falls beide Daten auf einen Tag fallen, False, wenn nicht.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean compareDates(int year) {
        if (this.mode == 1) //noinspection ConstantConditions,ConstantConditions
            return this.getEasterDate(year, 2).compareTo(this.getChristmasDate(year, 1)) == 0;
        //noinspection ConstantConditions,ConstantConditions
        return this.mode == 2 && this.getEasterDate(year, 1).compareTo(this.getChristmasDate(year - 1, 2)) == 0;
    }

    /**
     * Diese Methode berechnet bei einem Treffer beide Daten erneut und gibt diese formatiert aus.
     *
     * @param mode Suchmodus, um die passende Textausgabe anzeigen zu können
     * @param year Jahr, dessen Daten berechnet und angezeigt werden sollen
     */
    private void printDates(int mode, int year) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        if (mode == 1) {
            Date christmas = getChristmasDate(year, 1);
            Date easter = getEasterDate(year, 2);
            printBorder();
            System.out.println("Orthodoxe Osterfest des Jahres " + year + " findet am " + formatter.format(easter) + " statt.");
            System.out.println("Christliche Weihnachtsfest des Jahres " + year + " findet am " + formatter.format(christmas) + " statt.");
            printBorder();
        } else if (mode == 2) {
            Date christmas = getChristmasDate(year - 1, 2);
            Date easter = getEasterDate(year, 1);
            printBorder();
            System.out.println("Christliche Osterfest des Jahres " + year + " findet am " + formatter.format(easter) + " statt.");
            System.out.println("Orthodoxe Weihnachtsfest des Jahres " + (year - 1) + " findet am " + formatter.format(christmas) + " statt.");
            printBorder();
        }
    }
}
