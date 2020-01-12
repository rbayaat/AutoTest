package com.apps.autotest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RideResponse {
    @SerializedName("data")
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getScheduled_at() {
            return scheduled_at;
        }

        public void setScheduled_at(String scheduled_at) {
            this.scheduled_at = scheduled_at;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public Step getStep() {
            return step;
        }

        public void setStep(Step step) {
            this.step = step;
        }

        public Pickup getPickup() {
            return pickup;
        }

        public void setPickup(Pickup pickup) {
            this.pickup = pickup;
        }

        public DropOff getDropOff() {
            return dropOff;
        }

        public void setDropOff(DropOff dropOff) {
            this.dropOff = dropOff;
        }

        private int id;
        private String scheduled_at;
        private String cost;
        private int capacity;
        @SerializedName("step")
        Step step;
        @SerializedName("pickup")
        Pickup pickup;
        @SerializedName("drop_off")
        DropOff dropOff;
    }
    public class Pickup{
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

        String lat;
        String lng;
    }
    public class DropOff{
        String lat;

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

        String lng;
    }

}
