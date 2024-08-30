package com.AlTaraf.Booking.Config.utils;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Double[]> {
    private final double userLat;
    private final double userLong;

    public DistanceComparator(double userLat, double userLong) {
        this.userLat = userLat;
        this.userLong = userLong;
    }

    @Override
    public int compare(Double[] point1, Double[] point2) {
        double distance1 = calculateDistance(point1);
        double distance2 = calculateDistance(point2);
        return Double.compare(distance1, distance2);
    }

    private double calculateDistance(Double[] point) {
        double latDiff = userLat - point[0];
        double longDiff = userLong - point[1];
        return Math.sqrt(latDiff * latDiff + longDiff * longDiff);
    }
}