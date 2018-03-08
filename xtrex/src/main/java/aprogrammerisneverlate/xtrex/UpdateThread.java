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
        Maps.MapController mapController = Maps.getController();
        TripComputer tripComputer = TripComputer.getInstance();

        while (running) {
            
        	if (xtrex.gpsEnabled) {
	        	synchronized(this) {
		            try {
		                gpsThread.wait();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
	        	}
        	} else {
        		try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
            
            mapController.updateMap();

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

 }