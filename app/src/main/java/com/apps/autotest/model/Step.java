package com.apps.autotest.model;

import com.google.gson.annotations.SerializedName;

public class Step {

    @SerializedName("start_location")
    private StartLocation startLocation;

    @SerializedName("end_location")
    private EndLocation endLocation;

    @SerializedName("polyline")
    private Polyline polyline;

    @SerializedName("duration")
    private Duration duration;

    @SerializedName("distance")
    private Distance distance;

    public StartLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocation startLocation) {
        this.startLocation = startLocation;
    }

    public EndLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public class StartLocation {
        private String lat;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        private String lng;
    }
    public class EndLocation {
        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        private String lat;
        private String lng;
    }
    public class Polyline {
        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        private String points;
    }
    public class Duration{
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        private String text;
    }
    public class Distance{
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        private String text;
    }
}
