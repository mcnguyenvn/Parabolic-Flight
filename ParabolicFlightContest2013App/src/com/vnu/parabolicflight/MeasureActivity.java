package com.vnu.parabolicflight;

import com.vnu.parabolicflight.util.Writer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MeasureActivity extends Activity {

	Button stopButton, exportButton;
	boolean stop = false;
	boolean display = false;
	String fileName;
	Writer writer;

	private SensorManager mSensorManager;
	private SensorEventListener listener;

	TextView gyroscope_xval, gyroscope_yval, gyroscope_zval;
	TextView accelerometer_xval, accelerometer_yval, accelerometer_zval;
	TextView gravity_xval, gravity_yval, gravity_zval;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_measure);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		fileName = b.getString("file");
		writer = new Writer();

		// get the sensor service
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		listener = new SensorEventListener() {
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}

			@Override
			public void onSensorChanged(SensorEvent event) {
				if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
					getAccelerometer(event);
				}
				if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
					getGyroscope(event);
				}
				if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
					getGravity(event);
				}
			}
		};

		initListeners();

		stopButton = (Button) findViewById(R.id.stop_button);
		stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				stop = true;
				if (display) {
					Intent intent1 = new Intent(MeasureActivity.this,
							DisplayActivity.class);

					Bundle b1 = new Bundle();
					b1.putString("file", fileName);
					intent1.putExtras(b1);

					startActivity(intent1);
					finish();
				} else {
					stopButton.setText("DISPLAY LOG FILE");
					display = true;
				}
			}

		});

		exportButton = (Button) findViewById(R.id.export_button);
		exportButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				stop = true;
			}
		});

		gyroscope_xval = (TextView) findViewById(R.id.gyroscope_xval);
		gyroscope_yval = (TextView) findViewById(R.id.gyroscope_yval);
		gyroscope_zval = (TextView) findViewById(R.id.gyroscope_zval);

		accelerometer_xval = (TextView) findViewById(R.id.accelerometer_xval);
		accelerometer_yval = (TextView) findViewById(R.id.accelerometer_yval);
		accelerometer_zval = (TextView) findViewById(R.id.accelerometer_zval);

		gravity_xval = (TextView) findViewById(R.id.gravity_xval);
		gravity_yval = (TextView) findViewById(R.id.gravity_yval);
		gravity_zval = (TextView) findViewById(R.id.gravity_zval);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.measure, menu);
		return true;
	}

	private void getAccelerometer(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		if (stop == false) {
			writer.appendFile(fileName, 0, x, y, z);
		}

		accelerometer_xval.setText("x:" + "\t\t" + Float.toString(x));
		accelerometer_yval.setText("y:" + "\t\t" + Float.toString(y));
		accelerometer_zval.setText("z:" + "\t\t" + Float.toString(z));
	}

	private void getGyroscope(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		if (stop == false) {
			writer.appendFile(fileName, 1, x, y, z);
		}

		gyroscope_xval.setText("x:" + "\t\t" + Float.toString(x));
		gyroscope_yval.setText("y:" + "\t\t" + Float.toString(y));
		gyroscope_zval.setText("z:" + "\t\t" + Float.toString(z));
	}

	private void getGravity(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		if (stop == false) {
			writer.appendFile(fileName, 2, x, y, z);
		}

		gravity_xval.setText("x:" + "\t\t" + Float.toString(x));
		gravity_yval.setText("y:" + "\t\t" + Float.toString(y));
		gravity_zval.setText("z:" + "\t\t" + Float.toString(z));
	}

	public void initListeners() {
		mSensorManager.registerListener(listener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
				SensorManager.SENSOR_DELAY_FASTEST);
		
		/*
		mSensorManager.registerListener(listener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);

		mSensorManager.registerListener(listener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
				SensorManager.SENSOR_DELAY_FASTEST);
		*/
	}

	@Override
	protected void onResume() {
		super.onResume();
		initListeners();
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(listener);
		super.onPause();
	}

	@Override
	public void onStop() {
		mSensorManager.unregisterListener(listener);
		super.onStop();
	}
}