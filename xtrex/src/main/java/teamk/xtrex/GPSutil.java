package teamk.xtrex;

/**
 * Creates a PVT log to allow historic tracking
 * 
 * @author Connor Harris
 * @version Sprint 3 
 */

public class GPSutil {
    PositionVelocityTime[] log = new PositionVelocityTime[50];

    /**
     * Given the coordinates of two points on the globe, specified in terms of latitude and longitude, calculate the
     * distance between them using the Haversine formula.
     * 
     * @param double lat1 -- latitude of first point
     * @param double long1 -- longitude of first point
     * @param double lat2 -- latitude of second point
     * @param double long2 -- longitude of second point
     * 
     * @return the distance, in metres, between the two points
     * 
     * @author Daniel Gulliver
     */
    public static int latLongToDistance(double lat1, double long1, double lat2, double long2) {
        final double RADIUS_OF_EARTH = 6371E3D;
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double delta_phi = Math.toRadians(lat1 - lat2);
        double delta_lambda = Math.toRadians(long1 - long2);

        // Haversine formula for calculating the distance between two points on a sphere given their latitude and
        // longitude.
        double a = Math.sin(delta_phi / 2) * Math.sin(delta_phi / 2) +
                   Math.cos(phi1) * Math.cos(phi2) *
                   Math.sin(delta_lambda / 2) * Math.sin(delta_lambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = RADIUS_OF_EARTH * c;

        return (int) Math.round(d);
    }
    
}
