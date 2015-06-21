package edu.fudan.hangout.map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import edu.fudan.hangout.R;
import edu.fudan.hangout.location.LocationApplication;

import java.util.ArrayList;

/**
 * Created by lifengshuang on 6/21/15.
 */
public class MapActivity extends Activity {

    private MapView mapView;
    private ArrayList<Person> personArrayList = new ArrayList<Person>();
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.showZoomControls(false);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);

        LocationApplication application = (LocationApplication)getApplication();
        LatLng cenpt = new LatLng(application.getLatitude(), application.getLongitude());
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(15)
                .build();

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mapView.getMap().setMapStatus(mMapStatusUpdate);

        //TODO: true data
        personArrayList.add(new Person(31.1887220, 121.5960770, "htc"));
        personArrayList.add(new Person(31.2087220, 121.5760770, "htc"));
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();

        for (Person person : personArrayList) {
            addPerson(person);
        }
    }

    private void addPerson(Person person) {
        int size = 75;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(image, null, new Rect(0, 0, size, size), null);

        //TODO: set image to user profile photo

        OverlayOptions options = new MarkerOptions()
                .position(new LatLng(person.getLatitude(), person.getLongitude()))
                .icon(BitmapDescriptorFactory.fromBitmap(toRoundCorner(bitmap)))
                .zIndex(10)
                .draggable(false);
        mapView.getMap().addOverlay(options);
    }

    private Bitmap toRoundCorner(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);
        canvas.drawRoundRect(rectF, bitmap.getWidth() / 2, bitmap.getHeight() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
}

class Person {
    private double latitude;
    private double longitude;
    private String name;
    private String url;

    public Person(double latitude, double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public Person(double latitude, double longitude, String name, String url) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.url = url;
    }

    public double getLatitude() {

        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
