package de.nexus;

/**
 * Diese Klasse kann Daten zwischen dem gregorianischen und julianischen Kalendersystem umrechnen.
 * Dazu wird das gegebene Datum zuerst in ein julianisches Datum umgerechnet, welches anschliessend in das gewuenschte System
 * umgewandelt werden kann.
 *
 * @author Janik Rapp
 * @version 1.0
 * @see <a href="https://de.wikipedia.org/wiki/Julianisches_Datum">Julianisches Datum</a>
 * @see <a href="https://de.wikipedia.org/wiki/Gregorianischer_Kalender">Gregorianischer Kalender</a>
 * @see <a href="https://de.wikipedia.org/wiki/Julianischer_Kalender">Julianischer Kalender</a>
 */

class CalendarUtil {

    private final int Tag, Monat, Jahr;

    /**
     * Constructor - Erstellt ein neues {@link CalendarUtil} Objekt
     *
     * @param tag   {@link Integer} Tag
     * @param monat {@link Integer} Monat
     * @param jahr  {@link Integer} Jahr
     */
    private CalendarUtil(int tag, int monat, int jahr) {
        this.Tag = tag;
        this.Monat = monat;
        this.Jahr = jahr;
    }

    /**
     * Verkuerzte Form der {@link Math#floor(double)} Methode
     *
     * @param x {@link Double}
     * @return {@link Math#floor(double)}
     */
    private static double floor(Double x) {
        return Math.floor(x);
    }

    /**
     * Berechnet das Julianische Datum aus gegebenem Kalenderdatum
     *
     * @param d {@link Integer} Tag
     * @param m {@link Integer} Monat
     * @param y {@link Integer} Jahr
     * @return {@link Double} Julianisches Datum
     */
    static double getDayJul(int d, int m, int y) {
        m -= 3;
        if (m < 0) {
            m += 12;
            y--;
        }
        return floor(y * 365.25) + floor(m * 30.6 + 0.5) + d + 1721117;
    }

    /**
     * Berechnet Abweichung zwischen Gregorianischem und Julianischem Kalender
     *
     * @param jd {@link Integer} Julianisches Datum
     * @return {@link Double} Abweichung
     */
    private static double GregOut(Double jd) {
        double tz = jd - 1721119;
        return floor(tz / 36524.25) - floor(tz / 146097) - 2;
    }


    /**
     * Berechnet Tag, Monat und Jahr nach dem Julianischen Kalender aus einem Julianischen Datum
     *
     * @param jd {@link Double} Julianisches Datum
     * @return {@link CalendarUtil} Objekt
     */
    private static CalendarUtil makeDateJul(Double jd) {
        double tz = jd - 1721117;
        double y = floor((tz - 0.2) / 365.25);
        double r = tz - floor(y * 365.25);
        double m = floor((r - 0.5) / 30.6);
        double d = r - floor(m * 30.6 + 0.5);
        m += 3;
        if (m > 12) {
            m -= 12;
            y++;
        }
        return new CalendarUtil((int) d, (int) m, (int) y);
    }

    /**
     * Berechnet Tag, Monat und Jahr nach dem Gregorianischen Kalender aus einem Julianischen Datum
     *
     * @param jd {@link Double} Julianisches Datum
     * @return {@link CalendarUtil} Objekt
     */
    static CalendarUtil makeDateGreg(Double jd) {
        double a = jd + GregOut(jd);
        return makeDateJul(a);
    }

    /**
     * Gibt den Tag zurueck
     *
     * @return {@link Integer} Tag
     */
    int getTag() {
        return Tag;
    }

    /**
     * Gibt den Monat zurueck
     *
     * @return {@link Integer} Monat
     */
    int getMonat() {
        return Monat;
    }

    /**
     * Gibt das Jahr zurueck
     *
     * @return {@link Integer} Jahr
     */
    int getJahr() {
        return Jahr;
    }

}
