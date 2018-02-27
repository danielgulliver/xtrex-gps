/**
 * A thread to update the map and the trip computer in the background as GPS polls come through
 * 
 * @author Daniel Gulliver
 * @author Adam Griffiths
 * 
 * @version 0.1
 */

 public class UpdateThread implements Runnable {

    private boolean running = true;

    public void run() {
        
        Thread gpsThread = xtrex.getGpsThread();
        MapScreen mapScreen = MapScreen.getInstance();
        //MapScreen.setGpsInstance(GPS.getInstance());
        TripComputer tripComputer = TripComputer.getInstance();

        while (running) {
            
            gpsThread.wait();
            mapScreen.getMap();

            // TODO: Update trip odometer

        }

    }

    public void stopRunning(){
        this.running = false;
    }

 }