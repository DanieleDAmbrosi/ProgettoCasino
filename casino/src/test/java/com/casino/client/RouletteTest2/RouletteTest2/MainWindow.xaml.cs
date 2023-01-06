using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Threading;

namespace RouletteTest2
{
    /// <summary>
    /// Logica di interazione per MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        DispatcherTimer kesselTimer = new DispatcherTimer();
        DispatcherTimer kugelTimer = new DispatcherTimer();
        TimeSpan restZeitKessel = new TimeSpan(0, 0, 0, 0, 50);
        TimeSpan restZeitKugel = new TimeSpan(0, 0, 0, 3, 50);

        // @Stekal: Kugelsgeschwindigkeit
        double geschwindigkeitKugel;

        // @Stekal: Diese Membervariable wird benötigt damit die Rotation funktioniert
        RotateTransform rotierungKessel = new RotateTransform(0);
        RotateTransform rotierungKugel = new RotateTransform(0);

        Random rnd = new Random();
        int zufall;

        bool rundenErreicht = false;

        public MainWindow()
        {
            InitializeComponent();

            kugelTimer.Tick += KugelTimer_Tick;
            kesselTimer.Tick += Timer_Tick;
            UeberpruefListenBefuellen();
            AllesAufAnfang();
        }

        private void KugelTimer_Tick(object sender, EventArgs e)
        {
            ball.Visibility = Visibility.Visible;

            KugelEndspurt();
            KugelVerlangsamen();




            // @Stekal:
            // Wenn die Kugel x Runden erreicht hat wird die Richtung der
            // Kugel velangsamt und in die andere Richtung gedreht
            // der Boolsche Wert wird auf true gesetzt, damit die Verzweigung
            // wo die Kugel in die andere Richtung fährt aufgerufen wird.
            if (rotierungKugel.Angle <= -360 && !rundenErreicht)
            {
                restZeitKugel -= kugelTimer.Interval;
                ball.RenderTransform = rotierungKugel;
                rotierungKugel.Angle -= geschwindigkeitKugel;

                if (geschwindigkeitKugel <= 1)
                    rundenErreicht = true;
            }
            else if (!rundenErreicht)
            {
                restZeitKugel -= kugelTimer.Interval;
                ball.RenderTransform = rotierungKugel;
                rotierungKugel.Angle -= geschwindigkeitKugel; // -3
            }
            else if (rundenErreicht)
            {

                geschwindigkeitKugel = 1;
                lblZahl.Content = zufall.ToString();



                //@Christof Label Gewinnzahl Farbe änderung
                if (zufall.ToString() == "0")
                {
                    lblZahl.Background = Brushes.Green;
                    lblGewinn.Content = gesamtGewinn;










                }
                else if (zufall.ToString() == "2" || zufall.ToString() == "4" || zufall.ToString() == "6" ||
                                                    zufall.ToString() == "8" || zufall.ToString() == "10" ||
                                                    zufall.ToString() == "11" || zufall.ToString() == "13" ||
                                                    zufall.ToString() == "15" || zufall.ToString() == "17" ||
                                                    zufall.ToString() == "20" || zufall.ToString() == "22" ||
                                                    zufall.ToString() == "24" || zufall.ToString() == "26" ||
                                                    zufall.ToString() == "28" || zufall.ToString() == "29" ||
                                                    zufall.ToString() == "31" || zufall.ToString() == "33" ||
                                                    zufall.ToString() == "35")
                {
                    lblZahl.Background = Brushes.Black;
                    lblGewinn.Content = gesamtGewinn;
                }
                else
                {
                    lblZahl.Background = Brushes.Red;
                    lblGewinn.Content = gesamtGewinn;
                }


                rotierungKugel.Angle += geschwindigkeitKugel; // +1
                restZeitKugel -= kugelTimer.Interval;
                if (rotierungKugel.Angle == 180)
                {
                    kugelTimer.Stop();
                    rundenErreicht = false;
                    ball.Visibility = Visibility.Hidden;
                }


            }

            lblGewinn.Content = gesamtGewinn;
        }

        double gradVonOben = -360;
        double gradVonUnten = -180;
        double yAchse;
        double xAchse;

        // @Stekal:
        // hier wird immer die Position "Canvas.GetTop()" oder "Canvas.GetLeft"
        // hineingespeichert
        int position;

        /// <summary>
        /// @Stekal:
        /// Mittels dieser Methode wird die Kugel von Runde zu Runde weiter in die Mitte gezogen,
        /// um eine realistischere Animation zu generieren.
        /// </summary>
        private void KugelEndspurt()
        {
            /*              O R I G I N A L - P O S I T I O N 

            von Oben:

                Canvas.Top="25"
                Canvas.Left="150"
                Stroke="Black" RenderTransformOrigin="-2.6,9.8">    
              
            */


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

        //__________________________________________________________________________________________________________________________

        //              K U G E L   V E R L A N G S A M E N

        // @Stekal:
        // wenn die Kugel einen bestimmten Umdrehungspunkt erreicht,
        // dann wird diese Variable zum vergleichen benötigt.
        double kreisUmdrehung = 0;

        private void KugelVerlangsamen()
        {
            // @Stekal:
            // Ich habe zuerst versucht alle 180° von der Kugel aus gesehn die Kugel um 0,1 zu veringern,
            // doch da ab der 1. Veringerung die Grade, in der die Kugel steht bis zu dieser Überprüfung,
            // die Bewegung in Kommazahlen ändert, muss ich Werte mit größer als und kleiner als verwenden. // 2* (bis -720)
            if (rotierungKugel.Angle > -720 && rotierungKugel.Angle <= (kreisUmdrehung + 5) && rotierungKugel.Angle >= (kreisUmdrehung - 5))
            {
                geschwindigkeitKugel -= 0.7;
                kreisUmdrehung -= 360;
            } // 6 * (bis -1440)
            else if (rotierungKugel.Angle > -1440 && rotierungKugel.Angle <= (kreisUmdrehung + 4) && rotierungKugel.Angle >= (kreisUmdrehung - 4))
            {
                geschwindigkeitKugel -= 1;
                kreisUmdrehung -= 180;
            } // 2 * (bis -2160)
            else if (rotierungKugel.Angle > -2160 && rotierungKugel.Angle <= (kreisUmdrehung + 4) && rotierungKugel.Angle >= (kreisUmdrehung - 4))
            {
                geschwindigkeitKugel -= 1;
                kreisUmdrehung -= 90;
                string stopPosition = rotierungKugel.Angle.ToString();
            }
        }

        //________________________________________________________________________________________________________________________

        //                  K E S S E L   R O T I E R U N G

        private void Timer_Tick(object sender, EventArgs e)
        {
            GenauBeiNull();

            restZeitKessel = kesselTimer.Interval;

            // hier weise ich der Ellipse (Kreis) die Rotation zu
            eliKesselInnere.RenderTransform = rotierungKessel;
            // Hier sag ich, dass das Bild immer um 1 (Grad) 
            // nach rechts rotieren soll.
            rotierungKessel.Angle += 1;
        }

        private void StartButton_png_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            if (ball.Visibility == Visibility.Hidden)
            {
                zufall = rnd.Next(0, 37);
                AllesAufAnfang();
                rotierungKessel.Angle = StartPosition()[4];
                kugelTimer.Start();


            }
            else if (rundenErreicht)
            {
                ball.Visibility = Visibility.Hidden;
                kugelTimer.Stop();
                AllesAufAnfang();


                lblGewinn.Content = gesamtGewinn;
            }
        }

    }

}
