import os.path
import sys
import time
# uses Pillow which can be downloaded here: http://pillow.readthedocs.io/en/3.3.x/installation.html#basic-installation
from PIL import Image, ImageFilter


class RhinozelfantSearcher(object):
    """
    Main Programmklasse, welche den Programmablauf steuert
    """

    def __init__(self):
        """
        Constructor - erstellt neues RhinozelfantSearcher Objekt und startet den Programmablauf
        """
        self.replacementColor = (255, 255, 255)
        print(
            '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')
        print('Rhinozelfant-Sucher\n \u00A9 Janik Rapp')
        print(
            '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')
        print(
            'WARNUNG: Sie muessen Schreibrechte auf den aktuellen Speicherort des zu bearbeitenden Bildes besitzen, um eine Ausgabe zu erhalten!')
        print(
            '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')

        self.path = self.inputPath()

        print(
            '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')

        self.filepath, self.filename = os.path.split(self.path)
        self.outputpath = self.filepath + '/' + str(self.filename.split('.')[0]) + '-edited.' + str(
            self.filename.split('.')[1])

        self.pic = Image.open(self.path)
        self.rgb_pic = self.pic.convert('RGB')
        self.piclo = self.pic.load()
        self.width, self.height = self.rgb_pic.size

        mode = self.inputMode()

        if mode == 1:
            self.easyRhinozelfantSearch()
        elif mode == 2:
            self.hardRhinozelfantSearch()
        elif mode == 3:
            self.hardRhinozelfantSearch()
            self.applyFilter()

        self.pic.save(self.outputpath)
        print('Das bearbeitete Bild ist unter folgendem Dateipfad zu finden\n\n>>' + str(self.outputpath) + '<<')
        print()
        input('Druecke beliebige Taste zum beenden....')
        sys.exit(0)

    def editPixel(self, x, y):
        """
        Aendert den RGB Wert des Pixels an der Stelle x, y zu dem als replacementColor gesetzten Wert.
        @param x: X Koordinate
        @type x: int
        @param y: Y Koordinate
        @type y: int
        """
        self.piclo[x, y] = self.replacementColor

    def searchNeighbors(self, x, y):
        """
        Sucht um den Pixel an gegebener Stelle x, y herum nach weiteren Pixeln mit der gleichen Farbe. Alle Pixel mit der
        gleichen Farbe werden editiert.
        @param x: X Koordinate
        @type x: int
        @param y: Y Koordinate
        @type y: int
        """
        thisPixel = self.rgb_pic.getpixel((x, y))
        if 0 <= x - 1 < self.width:
            if thisPixel == self.rgb_pic.getpixel((x - 1, y)):
                self.editPixel(x, y)
                self.editPixel(x - 1, y)
        if 0 <= x + 1 < self.width:
            if thisPixel == self.rgb_pic.getpixel((x + 1, y)):
                self.editPixel(x, y)
                self.editPixel(x + 1, y)
        if 0 <= y - 1 < self.height:
            if thisPixel == self.rgb_pic.getpixel((x, y - 1)):
                self.editPixel(x, y)
                self.editPixel(x, y - 1)
        if 0 <= y + 1 < self.height:
            if thisPixel == self.rgb_pic.getpixel((x, y + 1)):
                self.editPixel(x, y)
                self.editPixel(x, y + 1)

    def easyRhinozelfantSearch(self):
        """
        Fuehrt eine normale, simple Rhinozelfantensuche durch. Dabei werden alle Pixel von oben links nach unten rechts
        durchlaufen und ihr RGB Wert jeweils mit dem RGB Wert des vorherigen Pixels verglichen. Stimmen diese
        ueberein, wird der Pixel editiert.

        Dies ist nur der erste Schritt gewesen, da das Ergebnis noch recht unordentlich und unsauber ist.
        """
        last = None
        for y in range(self.height):
            for x in range(self.width):
                if last == self.rgb_pic.getpixel((x, y)):
                    self.editPixel(x, y)
                last = self.rgb_pic.getpixel((x, y))

    def hardRhinozelfantSearch(self):
        """
        Fuehrt eine erweiterte Rhinozelfantensuche durch. Dabei werden alle Pixel von oben links nach unten rechts
        durchlaufen und an die searchNeighbors Funktion weitergegeben. Diese sucht nach umliegenden, gleichfarbeigen
        Pixeln und editiert diese.

        Dies ist der zweite Schritt gewesen, da er ein saubereres Ergebnis liefert.
        """
        for y in range(self.height):
            for x in range(self.width):
                self.searchNeighbors(x, y)

    def applyFilter(self):
        """
        Diese Methode fuegt dem Bild einen MinFilter hinzu, wodurch falsche Rhinozelfantstellen
        auf dem Bild, welche zuvor weiß markiert wurden, wieder zurueckgesetzt werden. Somit werden wirklich
        nur Pixel des Rhinozelfants weiß markiert.
        """
        self.pic = self.pic.filter(ImageFilter.MinFilter)

    def inputPath(self):
        """
        Diese Funktion fragt nach einem Dateipfad. Wird ein gueltiger Pfad eingegeben, wird dieser Returnt. Andernfalls
        wird der Benutzer zu einer erneuten Eingabe aufgefordert.
        @return: Dateipfad
        @rtype: str
        """
        path = input('Bitte geben Sie einen Dateipfad zu dem zu untersuchenden Bild ein \n >>')
        if os.path.isfile(path):
            return path
        else:
            print('Ungueltiger Pfad!')
            return self.inputPath()

    def inputMode(self):
        """
        Diese Funktion fordert den Benutzer zur Eingabe einer Modi-Zahl auf. Wird eine gueltige Zahl eingegeben, wird
        diese zurueckgegeben, andernfalls wird der Benutzer erneut aufgefordert.
        @return: Modus
        @rtype: int
        """
        try:
            mode = int(input(
                'Bitte geben Sie den gewuenschten Modus ein. Nutzen Sie dazu die vorrangestellen Zahlen. \n\n1 - Einfach : schnell, vermindete Bildausgabequalitaet\n\n2 - Schwer : langsam, hoehere Qualitaet\n\n3 - Schwer mit Filter : sehr langsam, sehr hohe Qualitaet\n\n>>'))
        except(TypeError):
            print('Ungueltige Eingabe!')
            return self.inputMode()

        if 1 <= mode <= 3 and isinstance(mode, int):
            return mode
        else:
            print('Ungueltige Eingabe!')
            return self.inputMode()


finder = RhinozelfantSearcher()
