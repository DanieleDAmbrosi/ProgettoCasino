using Prism.Events;
using RouletteSimulator.Core.Models.WheelModels;
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
using Wheel.Views;
using Wheel.EventAggregator;

namespace WpfAppRouletteTest
{
    /// <summary>
    /// Logica di interazione per MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public RouletteWheel RouletteWheel { get; }
        private IEventAggregator _eventAggregator;

        public MainWindow(/*IEventAggregator eventAggregator*/)
        {
            InitializeComponent();            
            DataContext = this;                                                             // Set data context (for data binding).
            RouletteWheel = new RouletteWheel(mainGrid, wheelControl, ballControl); // Initialize the roulette wheel.

            // Listen to events.
            RouletteWheel.OnWheelSpinning += new WheelSpinning(WheelSpinningEventHandler);
            RouletteWheel.OnBallTossed += new BallTossed(BallTossedEventHandler);
            RouletteWheel.OnWinningNumber += new WinningNumber(WinningNumberEventHandler);

            // Event aggregator.
            //_eventAggregator = eventAggregator;
            /*_eventAggregator.GetEvent<SpinWheelEvent>().Subscribe(SpinWheelEventHandler, true);
            _eventAggregator.GetEvent<TossBallEvent>().Subscribe(TossBallEventHandler, true);
            _eventAggregator.GetEvent<BoardClearedEvent>().Subscribe(BoardClearedEventHandler, true);

            // Publish the initial status of the wheel/ball.
            _eventAggregator.GetEvent<WheelSpinningEvent>().Publish(false);
            _eventAggregator.GetEvent<BallTossedEvent>().Publish(false);*/
        }


        #region Methods

        /// <summary>
        /// The SpinWheelEventHandler handles an incoming SpinWheelEvent event.
        /// </summary>
        private void SpinWheelEventHandler()
        {
            RouletteWheel.SpinWheel();  // Spin the wheel.
        }

        /// <summary>
        /// The TossBallEventHandler handles an incoming TossBallEvent event.
        /// </summary>
        private void TossBallEventHandler()
        {
            RouletteWheel.TossBall();   // Toss the ball.
        }

        /// <summary>
        /// The BoardClearedEventHandler handles an incoming BoardClearedEvent event.
        /// </summary>
        private void BoardClearedEventHandler()
        {
            RouletteWheel.RetrieveBall();   // Retrieve the ball.
        }

        /// <summary>
        /// The WheelSpinningEventHandler method is called to handle an OnWheelSpinning event.
        /// </summary>
        /// <param name="wheelSpinning"></param>
        private void WheelSpinningEventHandler(bool wheelSpinning)
        {
            _eventAggregator.GetEvent<WheelSpinningEvent>().Publish(wheelSpinning); // Update the status of the wheel.
        }

        /// <summary>
        /// The BallTossedEventHandler method is called to handle an OnBallTossed event.
        /// </summary>
        /// <param name="ballTossed"></param>
        private void BallTossedEventHandler(bool ballTossed)
        {
            _eventAggregator.GetEvent<BallTossedEvent>().Publish(ballTossed);   // Update the status of the ball.
        }

        /// <summary>
        /// The WinningNumberEventHandler method is called to handle an OnWinningNumber event.
        /// </summary>
        /// <param name="winningNumber"></param>
        private void WinningNumberEventHandler(Pocket winningNumber)
        {
            _eventAggregator.GetEvent<WinningNumberEvent>().Publish(winningNumber); // Publish the winning number.
        }

        #endregion
    }

}
