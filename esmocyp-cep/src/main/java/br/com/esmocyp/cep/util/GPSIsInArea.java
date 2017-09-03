package br.com.esmocyp.cep.util;

/**
 * Created by ruhandosreis on 17/06/17.
 *
 * Utility class to test if a latitude and longitude is in a pre-defined area
 */
public class GPSIsInArea {

    private static final Double DEFAULT_LATITUDE = -22.866891;
    private static final Double DEFAULT_LONGITUDE = -43.255070;
    private static final double RADIUS = 500;

    /**
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public static boolean gpsIsInArea( final Double latitude, final Double longitude ) {
        final double radius = 6371; // Earth's radius in Km

        final double distanceInKM = Math.acos(Math.sin(latitude) * Math.sin(DEFAULT_LATITUDE) +
                Math.cos(latitude) * Math.cos(DEFAULT_LATITUDE) *
                        Math.cos(longitude - DEFAULT_LONGITUDE)) * radius;

        return ( distanceInKM * 1000 ) <= RADIUS;
    }
}
