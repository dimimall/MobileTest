package gr.dimitra.mobiletest.calls;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Meters implements Serializable {

    @Expose
    @SerializedName("lat")
    private String lat;

    @Expose
    @SerializedName("lng")
    private String lng;

    @Expose
    @SerializedName("mp_name")
    private String mp_name;

    @Expose
    @SerializedName("meter_id")
    private String meter_id;

    @Expose
    @SerializedName("meter_type")
    private String meter_type;

    @Expose
    @SerializedName("comm_mod_type")
    private String comm_mod_type;

    @Expose
    @SerializedName("comm_mod_serial")
    private String comm_mod_serial;

    @Expose
    @SerializedName("last_entry")
    private String last_entry;

    @Expose
    @SerializedName("volume")
    private String volume;

    @Expose
    @SerializedName("battery_lifetime")
    private int battery_lifetime;

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

    public String getMp_name() {
        return mp_name;
    }

    public void setMp_name(String mp_name) {
        this.mp_name = mp_name;
    }

    public String getMeter_id() {
        return meter_id;
    }

    public void setMeter_id(String meter_id) {
        this.meter_id = meter_id;
    }

    public String getMeter_type() {
        return meter_type;
    }

    public void setMeter_type(String meter_type) {
        this.meter_type = meter_type;
    }

    public String getComm_mod_type() {
        return comm_mod_type;
    }

    public void setComm_mod_type(String comm_mod_type) {
        this.comm_mod_type = comm_mod_type;
    }

    public String getComm_mod_serial() {
        return comm_mod_serial;
    }

    public void setComm_mod_serial(String comm_mod_serial) {
        this.comm_mod_serial = comm_mod_serial;
    }

    public String getLast_entry() {
        return last_entry;
    }

    public void setLast_entry(String last_entry) {
        this.last_entry = last_entry;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public int getBattery_lifetime() {
        return battery_lifetime;
    }

    public void setBattery_lifetime(int battery_lifetime) {
        this.battery_lifetime = battery_lifetime;
    }
}
