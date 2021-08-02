package gr.dimitra.mobiletest.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import gr.dimitra.mobiletest.R;
import gr.dimitra.mobiletest.calls.Meters;

public class CustomMarkerInfoWindowView implements GoogleMap.InfoWindowAdapter{

    private final View markerItemView;
    Context context;

    public CustomMarkerInfoWindowView(Context context) {
        this.context = context;
        markerItemView = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.marker_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        Meters meters = (Meters)marker.getTag();
        TextView tvId = markerItemView.findViewById(R.id.textView3);
        TextView tvCord = markerItemView.findViewById(R.id.textView4);
        TextView tvVolume = markerItemView.findViewById(R.id.textView5);
        TextView tvDate = markerItemView.findViewById(R.id.textView6);

        tvId.setText("Id "+meters.getMeter_id());
        tvCord.setText("Cord"+"\n"+meters.getLat()+" "+meters.getLng());
        tvVolume.setText("Volume"+"\n"+meters.getVolume());
        tvDate.setText("Date"+"\n"+meters.getLast_entry());

        return markerItemView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
