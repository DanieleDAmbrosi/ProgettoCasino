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

namespace WpfAppRoulette
{
   
    public partial class MainWindow : Window
    {
        Storyboard s;
        public MainWindow()
        {
            InitializeComponent();
            s = (Storyboard)TryFindResource("spin");
            
        }

        private void SpinButton_Click(object sender, RoutedEventArgs e)
        {
           // s.GetAnimationBaseValue(roulette_grid);
            //s.RepeatBehavior = new RepeatBehavior(3);
            /*Thread t1 = new Thread(() => {
                while (s.GetCurrentIteration() <= 3)
                    s.SetSpeedRatio(s.GetCurrentGlobalSpeed());
            });*/
            s.Begin();
            //t1.Start();
            
        }

        private void onFinishSpin(object sender, EventArgs e)//quando finisce di girare la roulette
        {
            //MessageBox.Show("fine");
        }
    }
}
