package teamk.xtrex;

/**
 * A thread to update the map and the trip computer in the background as GPS polls come through
 * 
 * @author Daniel Gulliver
 * @author Adam Griffiths
 * @author Connor Harris
 * 
 * @version 0.1
 */

 public class UpdateThread implements Runnable {

    private static UpdateThread updateThread;
    
    private Thread gpsThread;
    private boolean running = true;

    private UpdateThread() {
    	
    }

    /**
     * Return the single instance of UpdateThread allowed in the program.
     * @return the single instance of UpdateThread
     */
    public static UpdateThread getInstance() {
    	
        if (updateThread == null) {
            updateThread = new UpdateThread();
            updateThread.gpsThread = xtrex.getGpsThread();
        }
            
        
        return updateThread;
    }

    public void run() {
        MapController mapController = Maps.getMapController();
        TripComputer tripComputer = TripComputer.getInstance();
        SatelliteView satView = SatelliteView.getInstance();
        GPSspoofer spoof = GPSspoofer.getInstance();

        mapController.updateMap();
        System.out.println("Updated map");
        
        while (running) {
            
        	if (xtrex.gpsEnabled) {
	        	synchronized(this) {
		            try {
		                gpsThread.wait();
		                System.out.println("Notifed");
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
	        	}
        	} else {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
        	
            if (!xtrex.gpsEnabled) {
            	spoof.update();
            }
            
            mapController.updateMap();

            // Update odometer - calculate new values
            Odometer.update();

            // Update Trip Computer display - display new values
            if (XTrexDisplay.getInstance().getCurrentScreen() instanceof TripComputer) {
                tripComputer.setDistance((int) Math.round(Odometer.getDistanceTravelled()));
                tripComputer.setSpeed((int) Math.round(Odometer.getCurrentSpeed()));
                tripComputer.setTime(Odometer.getMovingTime());
                tripComputer.repaint();
            }
            
            satView.update();
            
        }

    }

 }