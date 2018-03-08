package aprogrammerisneverlate.xtrex;

/**
 * A thread to update the map and the trip computer in the background as GPS polls come through
 * 
 * @author Daniel Gulliver
 * @author Adam Griffiths
 * 
 * @version 0.1
 */

 public class UpdateThread implements Runnable {

    private static UpdateThread updateThread;
    private Thread t;
    private boolean running = true;

    private UpdateThread() {

    }

    /**
     * Return the single instance of UpdateThread allowed in the program.
     * @return the single instance of UpdateThread
     */
    public static UpdateThread getInstance() {
        if (updateThread == null)
            updateThread = new UpdateThread();
        return updateThread;
    }

    public void run() {
        Thread gpsThread = new Thread(GPSparser.getInstance(false));
        MapScreen mapScreen = MapScreen.getInstance();
        TripComputer tripComputer = TripComputer.getInstance();

        while (running) {
            
            // Wait for the GPS Thread to notify us to update.
            /*try {
                gpsThread.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            mapScreen.getMap();

            // Update odometer - calculate new values
            Odometer.update();

            // Update Trip Computer display - display new values
            tripComputer.setDistance((int) Math.round(Odometer.getDistanceTravelled()));
            tripComputer.setSpeed((int) Math.round(Odometer.getCurrentSpeed()));
            tripComputer.setTime(Odometer.getMovingTime());
            tripComputer.repaint();

            // FIXME: Temporary hack to sleep the thread. Should wait for gpsThread instead.
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void start() {
        if (t == null)
            t = new Thread(this);
        t.start();
    }

    public void stopRunning(){
        this.running = false;
    }

 }