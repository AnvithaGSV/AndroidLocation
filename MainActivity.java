package com.example.latlong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    String mprovider;
    final Context context = this;
  //  private String mTextViewPercentage;
    //private TextView mTextViewPercentage;
  //  private Context mContext;
  //  private int mProgressStatus = 0;
    private String flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS},
                10);




        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            String num = extras.getString("num");
            String msg = extras.getString("msg");
            String flag = extras.getString("flag");

            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, "I AM At Receiver\nsenderNum: " + num + ", message: " + msg, duration);
            toast.show();
            //Log.i("TAG", "hihi" );

            SmsManager sms = SmsManager.getDefault();
          //  String a="Latitude: "+Location.getLatitude()+"Longitude: "+Location.getLongitude();

            //Log.i("TAG", msg1);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
             PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

            // Setting Dialog Title
            alertDialogBuilder.setTitle("Device Ringing");

            // Setting Dialog Message
            alertDialogBuilder.setMessage("Sender : " + num + "\n" + "Message : " + msg);

            // create alert dialog

            AlertDialog alertDialog = alertDialogBuilder.create();
            //show dialog
            alertDialog.show();
            if (flag.equals("event")) {
                sms.sendTextMessage(num, null,a,null, null);
                flag="eventdone";
                // pi=ni
            }
        }


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }


    }
static String a=" ";
    @Override
    public void onLocationChanged(Location location) {
        TextView latitude = (TextView) findViewById(R.id.textView1);
        latitude.setText("Latitude:"+Double.toString(location.getLatitude())+"\nLongitude:" + location.getLongitude());
       // TextView longitude = (TextView) findViewById(R.id.textView);
  a="http://www.google.com/maps/place/"+location.getLatitude()+","+location.getLongitude();

        //longitude.setText("Current Longitude:" + location.getLongitude());

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            // YES!!
            Log.i("TAG", "MY_PERMISSIONS_REQUEST--> YES");
        }
    }

}
