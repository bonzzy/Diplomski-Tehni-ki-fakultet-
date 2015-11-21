package com.example.tipkovnica;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.Matrix;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class FullscreenActivity extends ActionBarActivity implements SensorEventListener{

	MojaTipkovnica mCustomKeyboard;
	private TextView tv;
	private SensorManager sManager;
	private Sensor mAccelerometer;
	private Sensor gyro;
	private Sensor lAcceleration;
	private Sensor gravity;
	private SENSOR sensor;
	private Sensor geomagnetic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
mCustomKeyboard= new MojaTipkovnica(this, R.id.keyboardview, R.xml.hexkbd );
        
        mCustomKeyboard.registerEditText(R.id.edittext0);
        
        tv = (TextView) findViewById(R.id.gyro);
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER );
        lAcceleration = sManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION );
        gravity = sManager.getDefaultSensor(Sensor.TYPE_GRAVITY ); 
        geomagnetic = sManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gyro = sManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //orientation = sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        
        sensor = new SENSOR();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	@Override
		protected void onResume()
		{
			super.onResume();
			/*register the sensor listener to listen to the gyroscope sensor, use the
			callbacks defined in this class, and gather the sensor information as quick
			as possible*/
			sManager.registerListener(this, lAcceleration,SensorManager.SENSOR_DELAY_NORMAL);
			sManager.registerListener(this, gravity,SensorManager.SENSOR_DELAY_NORMAL);
			sManager.registerListener(this, geomagnetic,SensorManager.SENSOR_DELAY_NORMAL);
			sManager.registerListener(this, mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
			         
		}
	@Override public void onBackPressed() { 
    	// NOTE Trap the back key: when the CustomKeyboard is still visible hide it, only when it is invisible, finish activity 
        if( mCustomKeyboard.isCustomKeyboardVisible() ) mCustomKeyboard.hideCustomKeyboard(); else this.finish();
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fullscreen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	@Override
	public void onSensorChanged(SensorEvent event)
	{
		Context context = getApplicationContext();
		//if sensor is unreliable, return void
		if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
		{
			tv.setText("Ne radi");
			return;
		}
		int tmp = 0;
		if ((this.mCustomKeyboard.Shift)&&(sensor.Keys[sensor.Row][sensor.Index] > 96) && (sensor.Keys[sensor.Row][sensor.Index] < 123)) tmp = -32;
		sensor.input(event,mCustomKeyboard,context);
		sensor.showCurrentCharacter();
		mCustomKeyboard.Code = sensor.Keys[sensor.Row][sensor.Index];
		//else it will output the Roll, Pitch and Yawn values
		if ((sensor.Keys[sensor.Row][sensor.Index] == -5))tv.setText("IZBRIŠI");  
		else if ((sensor.Keys[sensor.Row][sensor.Index] == -50))tv.setText("RAZMAK");  
		else if ((sensor.Keys[sensor.Row][sensor.Index] == -55))tv.setText("SHIFT");  
		else tv.setText("" + (char)(sensor.Keys[sensor.Row][sensor.Index]+tmp));  
	//lijevo je '-', a desno je '+'
	//dole je '+', a gore je '-'
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}
