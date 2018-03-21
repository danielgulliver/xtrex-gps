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
        }        
        return updateThread;
    }

    public void run() {
        MapController mapController = Maps.getMapController();
        TripComputer tripComputer = TripComputer.getInstance();
        SatelliteView satView = SatelliteView.getInstance();
        GPSspoofer spoof = GPSspoofer.getInstance();
        GPSutil gpsUtil = GPSutil.getInstance();

        mapController.updateMap();
        
        while (running) {
            
        	if (xtrex.gpsEnabled) {
	        	synchronized(this) {
		            try {
                        System.out.println("Waiting");
		                wait();
		                
		            } catch (Exception e) {
                        System.out.println("Wait failed");
		                e.printStackTrace();
		            }
	        	}
        	} else {
        		try {
                    Thread.sleep(1000);
                    spoof.update();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
            
            System.out.println("Updating!!");
            
            gpsUtil.update();
            
            mapController.updateMap();
            mapController.checkLocation();

            // Update Trip Computer display - display new values
            if (XTrexDisplay.getInstance().getCurrentScreen() instanceof TripComputerView) {
                // Update the trip computer.
                tripComputer.update();
            }
            if (XTrexDisplay.getInstance().getCurrentScreen() instanceof SatelliteView) {
                satView.update();
            }
        }

    }

 }