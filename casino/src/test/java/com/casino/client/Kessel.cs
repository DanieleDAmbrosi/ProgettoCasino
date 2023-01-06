using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media;
using System.Windows.Shapes;
using System.Windows.Threading;

namespace Roulette0._0._8
{
    class Kessel
    { 
   // Kessel Timer/Restzeit/Rotierungstransformation
        private DispatcherTimer kesselTimer = new DispatcherTimer();

    private TimeSpan restZeitKessel = new TimeSpan(0, 0, 50, 0, 0);

    private RotateTransform rotierungKessel = new RotateTransform(0);

    /// <summary>
    /// Hier wird die Elipse vom MainWindow mitgegeben
    /// </summary>
    private Ellipse eliKesselInnere;

    public Ellipse EliKesselInnere
    {
        get { return eliKesselInnere; }
        set { eliKesselInnere = value; }
    }

    // Kugel Timer/Restzeit/Rotierungstransformation
    private DispatcherTimer kugelTimer = new DispatcherTimer();

    private TimeSpan restZeitKugel = new TimeSpan(0, 0, 0, 3, 50);

    private RotateTransform rotierungKugel = new RotateTransform(0);

    private Ellipse eliKugel;

    public Ellipse EliKugel
    {
        get { return eliKugel; }
        set { eliKugel = value; }
    }

    // velocita
    private double geschwindigkeitKugel;

    /// <summary>
    /// Konstruktor zum mitgeben der Ellipsen.
    /// </summary>
    /// <param name="eliKesselInnere"></param>
    /// <param name="eliKugel"></param>
    public Kessel(Ellipse eliKesselInnere, Ellipse eliKugel)
    {
        EliKesselInnere = eliKesselInnere;
        EliKugel = eliKugel;
    }

    /// <summary>
    /// Wird beim Starten des Programms benötigt um das Tickevent zu erstellen.
    /// Der Kessel wird gleich gestartet, die Kugel erst nach betätigen des Startbuttons 
    /// bzw. Klick auf den Kessel direkt.
    /// Der Interwall beträgt 20 Millisekunden (alle 20 Millisekunden wird die Tickeventschleife durchlaufen).
    /// </summary>
    public void TickEventInitialisieren()
    {
        kesselTimer.Tick += KesselTimer_Tick;
        kesselTimer.Interval = new TimeSpan(0, 0, 0, 0, 20);
        kesselTimer.Start();

        kugelTimer.Tick += KugelTimer_Tick;
        kugelTimer.Interval = new TimeSpan(0, 0, 0, 0, 20);
    }


    // Dieser BOOL wird ausschliesslich für die Demo bonötigt
    public int druckzaehler;
    int zufall;

    public void AnimationStart(int rnd, bool animationStart)
    {
        // @Stekal:
        // Damit die Animation steuerbar ist wird das Rad auf eine 
        // Standard-Startposition gesetzt (sofern gerade keine Animation
        // vorhanden ist
        if (eliKugel.Visibility == Visibility.Hidden && animationStart == true)
        {
            zufall = rnd;

            AllesAufAnfang();
            rotierungKessel.Angle = StartPosition()[4];
            kugelTimer.Start();
            druckzaehler++;
        }
        else if (rundenErreicht)
            {
                KugelFertigGedreht();
            }
        }

        public void KugelFertigGedreht()
        {
            eliKugel.Visibility = Visibility.Hidden;
            kugelTimer.Stop();
            AllesAufAnfang();
        }


        #region Kessel-Rotierung



        //________________________________________________________________________________________________________________________

        //                  K E S S E L   R O T I E R U N G



        private void KesselTimer_Tick(object sender, EventArgs e)
    {
        GenauBeiNull();

        restZeitKessel = kesselTimer.Interval;

        // hier weise ich der Ellipse (Kreis) die Rotation zu
        eliKesselInnere.RenderTransform = rotierungKessel;
        // Hier sag ich, dass das Bild immer um 1 (Grad) 
        // nach rechts rotieren soll.
        rotierungKessel.Angle += 1;
    }

    /// <summary>
    /// Hier wird auf genau 360 Grad abgestoppt 
    /// </summary>
    private void GenauBeiNull()
    {
        if (rotierungKessel.Angle == 360)
            rotierungKessel.Angle = 0;
    }

    #endregion

    #region Kugel



    //__________________________________________________________________________________________________________________________

    //                  B E W E G U N G   D E R   K U G E L

    public bool rundenErreicht = false;
    public bool labelBefueller = false;


    /// <summary>
    /// Hier wird definiert was alle 20 Millisekunden mit der Kugel passiert.+
    /// Überprüfung wie weit die Kugel gefahren ist (in Grad).
    /// </summary>
    /// <param name="sender"></param>
    /// <param name="e"></param>
    private void KugelTimer_Tick(object sender, EventArgs e)
    {
        eliKugel.Visibility = Visibility.Visible;

        KugelEndspurt();
        KugelVerlangsamen();


        // Wenn die Kugel x Runden erreicht hat wird die Richtung der
        // Kugel velangsamt und in die andere Richtung gedreht
        // der Boolsche Wert wird auf true gesetzt, damit die Verzweigung
        // wo die Kugel in die andere Richtung fährt aufgerufen wird.
        if (rotierungKugel.Angle <= -360 && !rundenErreicht)
        {
            restZeitKugel -= kugelTimer.Interval;
            eliKugel.RenderTransform = rotierungKugel;
            rotierungKugel.Angle -= geschwindigkeitKugel;

            if (geschwindigkeitKugel <= 1)
                rundenErreicht = true;
        }
        else if (!rundenErreicht)
        {
            restZeitKugel -= kugelTimer.Interval;
            eliKugel.RenderTransform = rotierungKugel;
            rotierungKugel.Angle -= geschwindigkeitKugel;
        }
        else if (rundenErreicht)
        {
            if (geschwindigkeitKugel != 1)
                labelBefueller = true;
            else
                labelBefueller = false;

            geschwindigkeitKugel = 1;

            rotierungKugel.Angle += geschwindigkeitKugel; // + 1
            restZeitKugel -= kugelTimer.Interval;
        }
    }



    //__________________________________________________________________________________________________________________________

    //              RALLENTA PALLA

    // wenn die Kugel einen bestimmten Umdrehungspunkt erreicht,
    // dann wird diese Variable zum vergleichen benötigt.
    double kreisUmdrehung = 0;

    private void KugelVerlangsamen()
    {
        // Ich habe zuerst versucht alle 180° von der Kugel aus gesehn die Kugel um 0,1 zu veringern,
        // doch da ab der 1. Veringerung die Grade, in der die Kugel steht bis zu dieser Überprüfung,
        // die Bewegung in Kommazahlen ändert, muss ich Werte mit größer als und kleiner als verwenden. // 2* (bis -720)
        if (rotierungKugel.Angle > -720 && rotierungKugel.Angle <= (kreisUmdrehung + 5) && rotierungKugel.Angle >= (kreisUmdrehung - 5))
        {
            geschwindigkeitKugel -= 0.7;
            kreisUmdrehung -= 360;
        }
        else if (rotierungKugel.Angle > -1440 && rotierungKugel.Angle <= (kreisUmdrehung + 4) && rotierungKugel.Angle >= (kreisUmdrehung - 4))
        {
            geschwindigkeitKugel -= 1;
            kreisUmdrehung -= 180;
        }
        else if (rotierungKugel.Angle > -2160 && rotierungKugel.Angle <= (kreisUmdrehung + 4) && rotierungKugel.Angle >= (kreisUmdrehung - 4))
        {
            geschwindigkeitKugel -= 1;
            kreisUmdrehung -= 90;
            string stopPosition = rotierungKugel.Angle.ToString();
        }
    }



    //__________________________________________________________________________________________________________________________


    //                  K U G E L   I N   D E N   K E S S E L   Z I E H E N

    /// <summary>
    /// Diese Variablen werden alleine nur für die Animation, dass sich die
    /// Kugel in den Kessel hinein bewegt benötigt.
    /// </summary>
    double gradVonOben = -360;
    double gradVonUnten = -180;
    double yAchse;
    double xAchse;

    // hier wird immer die Position "Canvas.GetTop()" oder "Canvas.GetLeft"
    // hineingespeichert
    int position;

    /// <summary>
    /// Mittels dieser Methode wird die Kugel von Runde zu Runde weiter in die Mitte gezogen,
    /// um eine realistischere Animation zu generieren.
    /// </summary>
    private void KugelEndspurt()
    {
        if (rotierungKugel.Angle <= (gradVonOben + 7) && rotierungKugel.Angle >= (gradVonOben - 7))
        {
            yAchse -= 0.5;
            // Wenn man von Oben um 1 runter geht, setzt man den neuen Punkt (y-Achse) um -0.1
            // eliKugel.RenderTransformOrigin = new Point(-2.6, 9.8);
            eliKugel.RenderTransformOrigin = new Point(xAchse, yAchse);
            //Canvas.SetTop(eliKugel, positionVonOben);
            position = int.Parse(Canvas.GetTop(eliKugel).ToString()) + 5;
            Canvas.SetTop(eliKugel, position);
            gradVonOben -= 360;
        }
        else if (rotierungKugel.Angle <= (gradVonUnten + 7) && rotierungKugel.Angle >= (gradVonUnten - 7))
        {
            yAchse -= 0.5;

            eliKugel.RenderTransformOrigin = new Point(xAchse, yAchse);
            position = int.Parse(Canvas.GetTop(eliKugel).ToString()) + 5;
            Canvas.SetTop(eliKugel, position);
            gradVonUnten -= 360;
        }
    }

    /// <summary>
    /// Mittels dieser Methode werden die Werte vom Kessel und von der Kugel wieder auf Anfang gesetzt.
    /// </summary>
    public void AllesAufAnfang()
    {
        EliKugel.Visibility = Visibility.Hidden;

        restZeitKessel = new TimeSpan(0, 0, 0, 3, 50);
        restZeitKugel = new TimeSpan(0, 0, 0, 3, 50);

        geschwindigkeitKugel = 10;
        rundenErreicht = false;

        rotierungKugel.Angle = 0;

        kreisUmdrehung = 0;

        // Kugel Standardwerte:120
        Canvas.SetTop(eliKugel, StartPosition()[0]);
        Canvas.SetLeft(eliKugel, StartPosition()[1]);
        //-0.6,10.1
        gradVonOben = -360;
        gradVonUnten = -540;
        xAchse = StartPosition()[2];
        yAchse = StartPosition()[3];
        eliKugel.RenderTransformOrigin = new Point(xAchse, yAchse);
    }

    private List<double[]> kesselKoordinaten;

    /// <summary>
    /// Erzeugt die Startkoordinaten des Kessels
    /// </summary>
    public void KesselKoordinatenInitialisieren()
    {
        kesselKoordinaten = new List<double[]>();

        kesselKoordinaten.Add(new double[] { 30, 81, 4.3, 9.3, 18 }); // 0
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, 193 }); // 1
        kesselKoordinaten.Add(new double[] { 20, 128, -0.4, 10.3, 10 });
        kesselKoordinaten.Add(new double[] { 30, 81, 4.3, 9.3, 38 });
        kesselKoordinaten.Add(new double[] { 20, 117, 0.7, 10.3, 20 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, -129 }); // 5
        kesselKoordinaten.Add(new double[] { 20, 137, -1.3, 10.3, 342 }); // 6
        kesselKoordinaten.Add(new double[] { 30, 81, 4.3, 9.3, 77 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, -100 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, 153 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, -119 }); // 10
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, -80 }); // 11
        kesselKoordinaten.Add(new double[] { 30, 81, 4.3, 9.3, 58 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, -60 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, 173 });
        kesselKoordinaten.Add(new double[] { 25, 150, -2.6, 9.8, 73 }); // 15
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, -148 }); // 16
        kesselKoordinaten.Add(new double[] { 25, 145, -2.1, 9.8, 10 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, 134 });
        kesselKoordinaten.Add(new double[] { 19, 117, 0.7, 10.5, 30 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, 183 }); // 20
        kesselKoordinaten.Add(new double[] { 20, 117, 0.7, 10.3, 10 }); // 21
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, 144 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, -109 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, -138 });
        kesselKoordinaten.Add(new double[] { 20, 137, -1.3, 10.3, 10 }); // 25
        kesselKoordinaten.Add(new double[] { 30, 81, 4.3, 9.3, 28 }); // 26
        kesselKoordinaten.Add(new double[] { 25, 145, -2.1, 9.8, 342 });
        kesselKoordinaten.Add(new double[] { 30, 81, 4.3, 9.3, 67 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, 123 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, -90 }); // 30
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, 163 }); // 31
        kesselKoordinaten.Add(new double[] { 30, 81, 4.3, 9.3, 9 });
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, 202 });
        kesselKoordinaten.Add(new double[] { 25, 145, -2.1, 9.8, 360 });
        kesselKoordinaten.Add(new double[] { 30, 81, 4.3, 9.3, 48 }); // 35
        kesselKoordinaten.Add(new double[] { 17, 115, 0.9, 10.6, -70 }); // 36
    }

    /// <summary>
    /// Hier wird die zufällige Zahl verglichen und die
    /// Koordinaten so geändert, dass das mit der gezogenen
    /// Zahl übereinstimmt.
    /// </summary>
    /// <param name="zufall">hier wird die Zufällig generierte Zahl mitgegeben</param>
    /// <returns>Startkoordinaten</returns>
    private List<double> StartPosition()
    {
        List<double> startPositionen = new List<double>();


        startPositionen.Add(kesselKoordinaten[zufall][0]);  // TOP-Kugel
        startPositionen.Add(kesselKoordinaten[zufall][1]);  // LEFT-Kugel
        startPositionen.Add(kesselKoordinaten[zufall][2]);  // eliKugel.RenderTransformOrigin X
        startPositionen.Add(kesselKoordinaten[zufall][3]);  // eliKugel.RenderTransformOrigin Y
        startPositionen.Add(kesselKoordinaten[zufall][4]);  // rotierungKessel.Angle


        return startPositionen;
    }

    #endregion
    }
}
