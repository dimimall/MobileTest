package gr.dimitra.mobiletest.calls;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefreshToken {

    @Expose
    @SerializedName("access")
    String access;

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
