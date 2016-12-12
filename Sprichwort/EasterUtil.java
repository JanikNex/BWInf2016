package de.nexus;

/**
 * Enthaelt alle Funktionen, welche zum Berechnen des Osterdatums benoetigt werden.
 *
 * @author Janik Rapp
 * @version 1.0
 * @see <a href="https://de.wikipedia.org/wiki/Julianisches_Datum">Julianisches Datum</a>
 * @see <a href="https://de.wikipedia.org/wiki/Gregorianischer_Kalender">Gregorianischer Kalender</a>
 * @see <a href="https://de.wikipedia.org/wiki/Julianischer_Kalender">Julianischer Kalender</a>
 */

class EasterUtil {

    private final int tag, monat, jahr;

    /**
     * Constructor - Erstellt ein neues {@link EasterUtil} Objekt
     *
     * @param Tag   {@link Integer} Tag
     * @param Monat {@link Integer} Monat
     * @param Jahr  {@link Integer} Jahr
     */
    private EasterUtil(int Tag, int Monat, int Jahr) {
        this.tag = Tag;
        this.monat = Monat;
        this.jahr = Jahr;
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
     * Angepasste Modulo Funktion
     *
     * @param n {@link Double}
     * @param d {@link Double}
     * @return {@link Double} n%d
     */
    private static double mod(Double n, Double d) {
        Double q = n % d;
        return q < 0 ? d + q : q;
    }


    /**
     * Berechnet das Julianische Datum aus gegebenem Tag, Monat und Jahr in gegebenem Kalendersystem
     *
     * @param d {@link Integer} Tag
     * @param m {@link Integer} Monat
     * @param y {@link Integer} Jahr
     * @param s {@link Integer} Kalendersystem 1=Greg 0=Jul
     * @return {@link Double} Julianisches Datum
     * @see <a href="https://de.wikipedia.org/wiki/Julianisches_Datum">Julianisches Datum</a>
     */
    private static double getDay(@SuppressWarnings("SameParameterValue") int d, @SuppressWarnings("SameParameterValue") int m, int y, int s) {
        m -= 3;
        if (m < 0) {
            m += 12;
            y--;
        }
        double x = floor(y * 365.25) + floor(m * 30.6 + 0.5) + d + 1721117;
        if (s == 1) {
            x -= floor((double) y / 100) - floor((double) y / 400) - 2;
        }
        return x;
    }

    /**
     * Erstellt ein {@link EasterUtil} Objekt mit berechnetem Tag, Monat und Jahr aus gegebenem Julianischen Datum
     *
     * @param jd {@link Double} Julianisches Datum
     * @param s  {@link Integer} Kalendersystem 1=Greg 0=Jul
     * @return {@link EasterUtil}
     */
    private static EasterUtil makeDate(Double jd, int s) {
        double tz = jd - 1721119;
        if (s == 1) {
            tz += floor(tz / 36524.25) - floor(tz / 146097) - 2;
        }
        tz += 2;
        double y = floor((tz - 0.2) / 365.25);
        double r = tz - floor(y * 365.25);
        double m = floor((r - 0.5) / 30.6);
        double d = r - floor(m * 30.6 + 0.5);
        m += 3;
        if (m > 12) {
            m -= 12;
            y++;
        }
        return new EasterUtil((int) d, (int) m, (int) y);
    }

    /**
     * Gausssche Osterformel
     *
     * @param y    {@link Integer} Jahr, dessen Osterdatum berechnet werden soll
     * @param stil {@link Integer} Kalendersystem, nach dessen Ostern berechnet werden soll
     * @return {@link Integer}
     * @see <a href="https://de.wikipedia.org/wiki/Gau%C3%9Fsche_Osterformel">Gausssche Osterformel</a>
     */
    private static double EastCalc(int y, int stil) {
        double m, q;
        double H1 = floor((double) y / 100);
        double H2 = floor((double) y / 400);

        if (stil == 0) //julianisch
        {
            m = 15;
            q = 6;
        } else {
            m = 15 + H1 - H2 - floor((8 * H1 + 13) / 25);
            q = 4 + H1 - H2;
        }


        double a = mod((double) y, (double) 19);
        double b = mod((double) y, (double) 4);
        double c = mod((double) y, (double) 7);
        double d = mod((19 * a + m), (double) 30);
        double e = mod((2 * b + 4 * c + 6 * d + q), (double) 7);
        double g = d + e;
        if (g == 35) {
            g = 28;
        }
        if ((d == 28) && (e == 6) && (a > 10)) {
            g = 27;
        }

        return g;
    }

    /**
     * Berechnet das fertige Osterdatum als Julianisches Datum, indem zuerst das Julianische Datum des 22.03. des jeweiligen Jahres
     * berechnet wird, da dies der fruehstmoegliche Tag fuer Ostern ist. Durch die Gausssche Osterformel wird anschliessend die
     * Verschiebung berechnet und addiert, wodurch das richtige Osterdatum entsteht.
     *
     * @param y {@link Integer} Jahr
     * @param s {@link Integer} Stil
     * @return {@link Double} Osterdatum als Julianisches Datum
     * @see <a href="https://de.wikipedia.org/wiki/Gau%C3%9Fsche_Osterformel">Gausssche Osterformel</a>
     */
    private static double getEasterDay(int y, int s) {
        double x;
        double ostern = EastCalc(y, s);
        x = getDay(22, 3, y, s) + ostern;
        return x;
    }

    /**
     * Gibt das Osterdatum zu gegebenem Jahr und gegebenem Kalendersystem zurueck.
     *
     * @param jahr {@link Integer} Jahr, dessen Osterdatum berechnet werden soll
     * @param stil {@link Integer} Kalendersystem, nach dessen Ostern berechnet werden soll 1=Greg 0=Jul
     * @return {@link EasterUtil} Objekt
     */
    static EasterUtil Eingabe(int jahr, int stil) {
        return Ergebnis(jahr, stil);
    }

    /**
     * Berechnet das Osterdatum zu gegebenem Jahr und gegebenem Kaldendersystem und gibt es zurueck
     *
     * @param y {@link Integer} Jahr, dessen Osterdatum berechnet werden soll
     * @param s {@link Integer} Kalendersystem, nach dessen Ostern berechnet werden soll 1=Greg 0=Jul
     * @return {@link EasterUtil} Objekt
     */
    private static EasterUtil Ergebnis(int y, int s) {
        double JD = getEasterDay(y, s);

        return makeDate(JD, s);
    }

    /**
     * Gibt den Tag zurueck
     *
     * @return {@link Integer} Tag
     */
    int getTag() {
        return tag;
    }

    /**
     * Gibt den Monat zurueck
     *
     * @return {@link Integer} Monat
     */
    int getMonat() {
        return monat;
    }

    /**
     * Gibt das Jahr zurueck
     *
     * @return {@link Integer} Jahr
     */
    int getJahr() {
        return jahr;
    }

}
