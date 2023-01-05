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
using System.Windows.Media.Animation;
using System.Threading;
using System.Security.Cryptography;

namespace WpfAppRoulette
{

    public partial class MainWindow : Window
    {
        Storyboard s;
        DoubleAnimation dbAnmRoulette, dbAnmEllipse;
        int numEstratto;
        public MainWindow()
        {
            InitializeComponent();
            s = (Storyboard)TryFindResource("spin");
            dbAnmRoulette = (DoubleAnimation)s.Children[0];//animazione della roulette
            dbAnmEllipse = (DoubleAnimation)s.Children[1];//animazione dell'ellipse    

        }

        private void SpinButton_Click(object sender, RoutedEventArgs e)//onclick "spin"
        {
            numEstratto = new Random().Next(0, 36);
            startSpinning(numEstratto);
        }
        private void startSpinning(int numWinner)//gira la roulette e lancia la pallina
        {
            RotateTransform a = new RotateTransform();
            dbAnmEllipse.To = 360 * 3 + getAngleFromNumber(numWinner);
            s.Begin();
        }
        private double getAngleFromNumber(int numWinner)//calcola l'angolo di giro che deve fare per arrivare al numero scelto
        {
            switch (numWinner)
            {
                case 0:
                    return 0;
                case 1:
                    return 360 - 136.2;
                case 2:
                    return 58.5;
                case 3:
                    return 360 - 19.4;
                case 4:
                    return 38.9;
                case 5:
                    return 360 - 175.2;
                case 6:
                    return 97.2;
                case 7:
                    return 360 - 58.3;
                case 8:
                    return 155.7;
                case 9:
                    return 360 - 97.2;
                case 10:
                    return 175.1;
                case 11:
                    return 136.1;
                case 12:
                    return 360 - 38.9;
                case 13:
                    return 116.8;
                case 14:
                    return 360 - 116.8;
                case 15:
                    return 19.5;
                case 16:
                    return 360 - 155.8;
                case 17:
                    return 77.7;
                case 18:
                    return 360 - 77.8;
                case 19:
                    return 29.3;
                case 20:
                    return 360 - 126.5;
                case 21:
                    return 48.5;
                case 22:
                    return 360 - 87.5;
                case 23:
                    return 165.5;
                case 24:
                    return -165.3;
                case 25:
                    return 68.2;
                case 26:
                    return -9.7;
                case 27:
                    return 107;
                case 28:
                    return -48.6;
                case 29:
                    return -68;
                case 30:
                    return 146;
                case 31:
                    return -107;
                case 32:
                    return 9.7;
                case 33:
                    return -146;
                case 34:
                    return 97.2;
                case 35:
                    return -29.1;
                case 36:
                    return 126.3;
                default:
                    return -1;

            }

        }
        private void onFinishSpin(object sender, EventArgs e)//quando finisce di girare la roulette
        {
            MessageBox.Show(numEstratto.ToString());
        }
    }
}
