package gr.dimitra.mobiletest.calls;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tokens {

    @Expose
    @SerializedName("refresh")
    String refresh;

    @Expose
    @SerializedName("access")
    String access;

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
