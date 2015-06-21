package edu.fudan.hangout.location;

import android.app.Activity;
import android.os.Bundle;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by lifengshuang on 6/21/15.
 */
public class LocationController extends Activity {

    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initSettings(){
        mLocationClient = ((LocationApplication)getApplication()).mLocationClient;
        LocationClientOption option = new LocationClientOption();

//        <string name="hight_accuracy_desc">高精度定位模式下，会同时使用GPS、Wifi和基站定位，返回的是当前条件下精度最好的定位结果</string>
//        <string name="saving_battery_desc">低功耗定位模式下，仅使用网络定位即Wifi和基站定位，返回的是当前条件下精度最好的网络定位结果</string>
//        <string name="device_sensor_desc">仅用设备定位模式下，只使用用户的GPS进行定位。这个模式下，由于GPS芯片锁定需要时间，首次定位速度会需要一定的时间</string>
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setCoorType("gcj02");

        int span=1000;//miliseconds
        option.setScanSpan(span);

        option.setIsNeedAddress(false);

        mLocationClient.setLocOption(option);
    }

    public void start(){
        mLocationClient.start();
    }

    public void stop(){
        mLocationClient.stop();
    }
}
