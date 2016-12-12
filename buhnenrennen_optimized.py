import os.path
import math
import sys
import time


def setup():
    """
    Diese Funktion versucht alle benoetigten Packages zu importieren. Sollte dies fehlschlagen, wird der Benutzer gefragt, ob
    eine Installation versucht werden soll. Andernfalls kann das Programm nicht ausgefuehrt werden.

    Itertools, sklearn, numpy und warning muessen in dem Fall manuell installiert werden.
    """
    try:
        import itertools
        from sklearn.utils.extmath import cartesian
        import numpy as np  # Numpy can be downloaded here: numpy.org
        import warnings
    except ImportError:
        print(
            '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')
        print('[ERROR] Es konnten nicht alle benötigten Packete gefunden werden!')
        print('[ERROR] Wollen Sie eine Installation durch den InstallationsGuide probieren? (y/n)')
        print(
            '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')
        mode = input('>>')
        if mode != "y" and mode != "n":
            print('Ungueltige Eingabe!')
            sys.exit("[ERROR] Ungueltige Eingabe")
        else:
            if mode == "y":
                try:
                    import pip
                    from subprocess import check_call
                    print('[InstallationGuide] Starting...')
                    print('[InstallationGuide] Updating PIP...')
                    check_call("pip install --upgrade pip", shell=True)
                    print('[InstallationGuide] Updating PIP -> Success')
                    print('[InstallationGuide] Installing itertools...')
                    check_call("pip install itertools", shell=True)
                    print('[InstallationGuide] Installing itertools -> Success')
                    print('[InstallationGuide] Updating itertools...')
                    check_call("pip install --upgrade itertools", shell=True)
                    print('[InstallationGuide] Updating itertools -> Success')
                    print('[InstallationGuide] Installing numpy...')
                    check_call("pip install numpy", shell=True)
                    print('[InstallationGuide] Installing numpy -> Success')
                    print('[InstallationGuide] Updating numpy...')
                    check_call("pip install --upgrade numpy", shell=True)
                    print('[InstallationGuide] Updating numpy -> Success')
                    print('[InstallationGuide] Installing scipy...')
                    check_call("pip install scipy", shell=True)
                    print('[InstallationGuide] Installing scipy -> Success')
                    print('[InstallationGuide] Updating scipy...')
                    check_call("pip install --upgrade scipy", shell=True)
                    print('[InstallationGuide] Updating scipy -> Success')
                    print('[InstallationGuide] Installing matplotlib...')
                    check_call("pip install matplotlib", shell=True)
                    print('[InstallationGuide] Installing matplotlib -> Success')
                    print('[InstallationGuide] Updating matplotlib...')
                    check_call("pip install --upgrade matplotlib", shell=True)
                    print('[InstallationGuide] Updating matplotlib -> Success')
                    print('[InstallationGuide] Installing pandas...')
                    check_call("pip install pandas", shell=True)
                    print('[InstallationGuide] Installing pandas -> Success')
                    print('[InstallationGuide] Updating pandas...')
                    check_call("pip install --upgrade pandas", shell=True)
                    print('[InstallationGuide] Updating pandas -> Success')
                    print('[InstallationGuide] Installing scikit-learn...')
                    check_call("pip install scikit-learn", shell=True)
                    print('[InstallationGuide] Installing scikit-learn -> Success')
                    print('[InstallationGuide] Updating scikit-learn...')
                    check_call("pip install --upgrade scikit-learn", shell=True)
                    print('[InstallationGuide] Updating scikit-learn -> Success')
                    print('[InstallationGuide] Installing warnings...')
                    check_call("pip install warnings", shell=True)
                    print('[InstallationGuide] Installing warnings -> Success')
                    print('[InstallationGuide] Updating warnings...')
                    check_call("pip install --upgrade warnings", shell=True)
                    print('[InstallationGuide] Updating warnings -> Success')
                    print('[InstallationGuide] Finishing...')
                except:
                    print(
                        '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')
                    print(
                        '[ERROR] Fehlenden Pakete konnten nicht installiert werden! Versuchen sie es mit Admin-Rechten erneut oder installieren sie die fehlenden Pakete manuell!')
                    print(
                        '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')
                    time.sleep(1)
                    sys.exit("[ERROR] Pakete konnten nicht installiert werden!")


sys.tracebacklimit = 0  # Hide Traceback on Error Messages!


class Buhnenanalyser(object):
    """
        Main Programmklasse, welche den Programmablauf steuert
    """

    def __init__(self):
        """
            Constructor - erstellt neues Buhnenanalyser Objekt und startet den Programmablauf
        """
        print(
            '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')
        print('Buhnenfluchtplan-Generator\n \u00A9 Janik Rapp')
        print(
            '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')

        self.path = self.inputPath()

        print(
            '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')

        self.filepath, self.filename = os.path.split(self.path)
        self.data = None
        self.loadData()
        self.distance = 70
        self.lenght = int(self.data[-1][1])
        self.speedMinnie = 20
        self.speedMax = 30
        self.trackMinnie = []
        self.trackMax = []
        self.possibilities = self.getPossibilities()
        print('Zu berechnende Moeglichkeiten: ', self.possibilities)
        self.presetMax = self.generateTestPresets('x')
        self.presetMinnie = self.generateTestPresets('m')
        self.result = self.testForSafety()
        del self.presetMinnie
        del self.presetMax
        del self.trackMinnie
        del self.trackMax
        self.bestResult = sorted(self.result, key=lambda time: time[1])[0]
        print('Es wurden ' + str(len(self.result)) + ' Fluchtwege gefunden!')
        print('Der kuerzeste Fluchtweg kann in ' + str(self.bestResult[1]) + ' Minuten ueberwunden werden!')
        if self.inputMode() == 1:
            print()
            print(self.getPossibleEscapeRoute())
            print()
            input('Druecke beliebige Taste zum beenden....')
            sys.exit(0)
        else:
            print()
            input('Druecke beliebige Taste zum beenden....')
            sys.exit(0)

    def inputPath(self):
        """
            Diese Funktion fragt nach einem Dateipfad. Wird ein gueltiger Pfad eingegeben, wird dieser Returnt. Andernfalls
            wird der Benutzer zu einer erneuten Eingabe aufgefordert.
            @return: Dateipfad
            @rtype: str
        """
        path = input('Bitte geben Sie einen Dateipfad zu einem Buhnenplan ein. \n >>')
        if os.path.isfile(path):
            return path
        else:
            print('[ERROR] Ungueltiger Pfad!')
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
                'Sollen die erfolgreichen Fluchtwege ausgegeben werden?. \n\n1 - Ja\n\n2 - Nein\n\n>>'))
        except(TypeError):
            print('Ungueltige Eingabe!')
            return self.inputMode()

        if 1 <= mode <= 2 and isinstance(mode, int):
            return mode
        else:
            print('Ungueltige Eingabe!')
            return self.inputMode()

    def loadData(self):
        """
        Diese Funktion oeffnet die Datei, dessen Pfad zuvor unter self.path gespeichert wurde.
        Der komplette Inhalt wird ausgelesen und in self.data gespeichert.
        """
        try:
            with open(self.path) as buhnenplan:
                temp = [s.replace('\n', '') for s in buhnenplan.readlines()]
                temp = [s.split(' ') for s in temp]
                self.data = temp
        except IOError:
            raise Exception('[ERROR] Datei konnte nicht gelesen werden!')

    def findPosition(self, dog=None, x=None):
        """
        Diese Funktion findet Eintraege im geladenen Buhnenplan, welche zu gegebenem Hund und gegebener X-Position passen.
        Diese werden gebuendelt als Liste zurueckgegeben.
        @param dog: Hund, welcher bearbeitet werden soll (x/m)
        @type dog: str
        @param x: X-Position, welche abgerufen werden soll
        @type x: int
        @return: Liste mit allen passenden Eintraegen
        @rtype: list
        """
        x = str(x)
        if dog is None and x is None:
            pass
        else:
            if dog is not None and x is None:
                temp = []
                for i in self.data:
                    if (i[0] == 'x' or i[0] == 'X') and dog == 'x':
                        temp += [i]
                    elif (i[0] == 'm' or i[0] == 'M') and dog == 'm':
                        temp += [i]
            elif dog is None and x is not None:
                temp = []
                for i in self.data:
                    if i[1] == x:
                        temp += [i]
            else:
                temp = []
                for i in self.data:
                    if (((i[0] == 'x' or i[0] == 'X') and dog == 'x') and i[1] == x) or (
                                    i[0] == 'x' and dog == 'm') and (i[1] == x and int(i[1]) > 0):
                        temp += [i]
                    elif ((i[0] == 'm' or i[0] == 'M') and dog == 'm') and i[1] == x:
                        temp += [i]
            return temp

    def testForSafety(self):
        """
        Diese Funktion uebernimmt die Kernberechnung des Programms. Zuerst werden alle Wege, welche von Max
        benutzt werden koennen, ausprobiert und deren Zeit berechnet. Anschließend werden alle Wege, welche von Minnie
        benutzt werden koennen, ausprobiert und deren Zeit berechnet. Zum Schluss werden die Zeiten der Wege von Max
        mit den Zeiten der Wege von Minnie verglichen, um festzustellen, ob eine Flucht moeglich ist.
        @return: Liste mit allen Wegen, welche erfolgreich zur Flucht benutzt werden koennen
        @rtype: list
        """
        # Alle Max Tracks aufzeichnen
        for i in range(len(self.presetMax) - 1):
            temp = []
            time = 0
            for e in range(len(self.presetMax[i]) - 1):
                temp += [self.findPosition('x', e * 70)[self.presetMax[i][e] - 1][2]]
            for e in range(len(temp) - 1):
                time += self.calculateSpeed('x', self.calculateDiagonal(float(temp[e]), float(temp[e + 1])))
            self.trackMax += [[self.presetMax[i], time]]
        # Alle Minnie Tracks aufzeichnen
        for i in range(len(self.presetMinnie) - 1):
            temp = []
            time = 0
            for e in range(len(self.presetMinnie[i]) - 1):
                if e == 0:
                    inew = 0
                else:
                    inew = i
                temp += [self.findPosition('m', e * 70)[self.presetMinnie[inew][e] - 1][2]]
            for e in range(len(temp) - 1):
                time += self.calculateSpeed('m', self.calculateDiagonal(float(temp[e]), float(temp[e + 1])))
            self.trackMinnie += [[self.presetMinnie[i], time]]
        # Max Tracks durchgehen und mit allen Minnie Track vergleichen, ob ein Fluchtweg vorhanden ist.
        success = []
        for i in self.trackMinnie:
            for e in self.trackMax:
                if e[1] > i[1]:
                    success += [i]
        return success

    def calculateDiagonal(self, startY, endY):
        """
        Diese Funktion berechnet die Diagonale, welche von einer Buhnenluecke zur naechsten ueberwunden werden muss.
        @param startY: Y-Koordinate der Startbuhnenluecke
        @type startY: float
        @param endY: Y-Koordinate der Zielbuhnenluecke
        @type endY: float
        @return: Laenge der Diagonale
        @rtype: float
        """
        if startY > endY:
            diff = startY - endY
        else:
            diff = endY - startY
        return math.sqrt((diff ** 2 + self.distance ** 2))

    def calculateSpeed(self, dog, distance):
        """
        Diese Funktion berechnet die Zeit, welche benoetigt wird, um die gegebene Strecke zu ueberwinden.
        @param dog: Hund, welcher die Strecke ueberwinden soll (m/x) -> da unterschiedliche Geschwindigkeit
        @type dog: str
        @param distance: Distanz, welche ueberwunden werden soll
        @type distance: float
        @return: Geschwindigkeit
        @rtype: float
        """
        if dog == 'm':
            return ((distance * 0.001) / self.speedMinnie) * 60
        elif dog == 'x':
            return ((distance * 0.001) / self.speedMax) * 60

    def getPossibilities(self):
        """
        Diese Funktion berechnet die Anzahl der Buhnenwegkombinationen, welche getestet werden sollen.
        @return: Anzahl der Kombinationsmoeglichkeiten
        @rtype: int
        """
        possMax = 0
        for i in range(int(self.lenght / 70)):
            possMax += len(self.findPosition('x', i * 70))
        possMin = 0
        for i in range(int(self.lenght / 70)):
            possMin += len(self.findPosition('m', i * 70))
        return possMin * possMax

    def generateTestPresets(self, dog):
        """
        Diese Funktion generiert alle Moeglichkeiten, welche der gegebene Hund zur Ueberwindung des Buhnenparcours hat.
        Diese Moeglichkeiten werden in Listen zurueckgegeben.
        @param dog: Hund, fuer den die Presets generiert werden sollen (m/x)
        @type dog: str
        @return: PResets
        @rtype: list
        """
        possPerStep = []

        if dog == 'x':
            possPerStep = self.getPossibilitiesPerStep('x')
            possPerStep = [1] + possPerStep
        elif dog == 'm':
            possPerStep = self.getPossibilitiesPerStep('m')
            possPerStep = [1] + possPerStep

        temp = []
        for i in range(len(possPerStep)):
            temp2 = []
            for e in range(possPerStep[i]):
                temp2 += [e + 1]
            temp += [temp2]

        preset = self.myCartesian(temp)

        return preset

    def getPossibilitiesPerStep(self, dog):
        """
        Diese Funktion berechnet fuer jeden Buhnenschritt die anzahl der Buhnenluecken, welche von gegebenem
        Hund ueberwunden werden koennen und gibt diese in einer Liste zurueck.
        @param dog: Hund (x/m)
        @type dog: str
        @return: Liste der Moeglichkeiten
        @rtype: list
        """
        poss = []
        for i in range(1, int(self.lenght / 70) + 1):
            if dog == 'x':
                poss += [len(self.findPosition('x', i * 70))]
            elif dog == 'm':
                poss += [len(self.findPosition('m', i * 70))]

        return poss

    def getPossibleEscapeRoute(self):
        """
        Diese Funktion bereitet die Ausgabe der bestmoeglichen Loesung vor, indem sie die beste Loesung zerlegt, in
        einen String packt und zurueckgibt.
        @return: String, fertig zur ausgabe
        @rtype: str
        """
        output = ""
        for num, i in enumerate(self.bestResult[0]):
            temp = self.findPosition('m', num * 70)[i - 1]
            output += "X: " + str(temp[1]) + ' Y: ' + str(temp[2] + '\n')
        return output

    def myCartesian(self, lists):
        """
        Berechnung des Kartesischen Produktes
        @param lists: Listen mit Werten
        @type lists: list
        @return: Liste mit dem Kartesischen Produkts der Parameterlisten
        @rtype: list
        """
        if lists == []: return [()]
        return [x + (y,) for x in self.myCartesian(lists[:-1]) for y in lists[-1]]


try:
    setup()
    buhnen = Buhnenanalyser()
except MemoryError:
    print(
        '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')
    print(
        'Der gegebene Buhnenplan konnte nicht zur Fluchtwegberechnung genutzt werden, da dein System für den anfallenden Rechenaufwand ungeeignet ist.')
    print("Dein System ist nur fuer ", sys.maxsize, " Listenobjekte ausgestattet. Dies wurde bei dieser Berechnung allerdings ueberschritten.")
    print(
        'Um Berechnungen in dieser oder größeren Größenordnungen durchzuführen wird empfohlen ein 64-Bit System mit guten Systemspezifikationen zu nutzen.')
    print(
        '\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592\u2592')
    time.sleep(1)
    sys.exit("[SystemFehler] Unzureichende Systemspezifikationen!")
