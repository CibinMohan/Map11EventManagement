package com.example.c4cib.map1111.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.c4cib.map1111.R;
import com.example.c4cib.map1111.calc.AstronomicalCalendar;
import com.example.c4cib.map1111.calc.GeoLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;


public class Main extends Activity
{
	ArrayList<String> places = new ArrayList<String>();
	Map<String, String> locNew = new LinkedHashMap<String, String>();

	int year ;
	int month ;
	int day ;
	Date srise;
	Date sset;
	String text ="Melbourne";
	String spl = "-37,144";
	double lat1 = -37;
	double lat2 = 144;
	String myLocation = null;
	FragmentManager fragmentManager = getFragmentManager();
	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


		fragAct fragment = new fragAct();
		fragmentTransaction.add(R.id.rlay, fragment);
		fragmentTransaction.commit();




		initializeUI();
		Button button = (Button) findViewById(R.id.button2);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Main.this, actHome.class);
				Bundle extras = new Bundle();
				startActivity(intent);
			}
		});

		Button button_list = (Button) findViewById(R.id.bt_list);

		Button button_loc = (Button) findViewById(R.id.bt_loc);

		//Button button_loclict = (Button) findViewById(R.id.bt_listLoc);

		button_loc.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Main.this, MapsActivity.class);
				String data = text+","+spl;
				Log.i("+++_----",data);
				intent.putExtra("data",data);
				Bundle extras = new Bundle();
				startActivity(intent);
			}
		});


		button_list.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				updateTime(year,month,day);
				String ListDate = ""+ day+"/"+month+"/"+year;
				String SRTTime = sset.getHours()+":"+sset.getMinutes();
				String SSTTime = srise.getHours()+":"+srise.getMinutes();
				String Data = ListDate+","+ SSTTime+","+SRTTime+",";

				//month =month+1;
				day = day+1;
				updateTime(year,month,day);
				String ListDate2 = ""+ day+"/"+month+"/"+year;
				SRTTime = sset.getHours()+":"+sset.getMinutes();
				SSTTime = srise.getHours()+":"+srise.getMinutes();

				Data = Data +  ListDate2+","+ SSTTime+","+SRTTime+",";

				day = day+1;
				//month =month+1;
				updateTime(year,month,day+2);
				String ListDate3 = ""+ day+"/"+month+"/"+year;
				SRTTime = sset.getHours()+":"+sset.getMinutes();
				SSTTime = srise.getHours()+":"+srise.getMinutes();

				Data = Data +  ListDate3+","+ SSTTime+","+SRTTime+",";

				day = day+1;
				//month =month+1;
				updateTime(year,month,day);
				String ListDate4 = ""+ day+"/"+month+"/"+year;
				SRTTime = sset.getHours()+":"+sset.getMinutes();
				SSTTime = srise.getHours()+":"+srise.getMinutes();

				Data = Data +  ListDate4+","+ SSTTime+","+SRTTime+",";

				day = day+1;
				//month =month+1;
				updateTime(year,month,day);
				String ListDate5 = ""+ day+"/"+month+"/"+year;
				SRTTime = sset.getHours()+":"+sset.getMinutes();
				SSTTime = srise.getHours()+":"+srise.getMinutes();

				Data = Data +  ListDate5+","+ SSTTime+","+SRTTime+",0,0,0";
				Intent intent = new Intent(Main.this, locationlist.class);
				intent.putExtra("Data",Data);
				startActivity(intent);

			}
		});

	}

	private void initializeUI()
	{
		DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		dp.init(year,month,day,dateChangeHandler); // setup initial values and reg. handler
		//updateTime(year, month, day);
        addFile();
	}
    
	private void updateTime(final int year, final int monthOfYear, final int dayOfMonth)
	{
		final TimeZone tz = TimeZone.getDefault();
		final Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
		//ArrayAdapter<String> adapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, places);
// Specify the layout to use when the list of choices appears
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
		//spinner.setAdapter(adapter);
		//spinner.setOnItemSelectedListener(
				//new AdapterView.OnItemSelectedListener() {
					//public void onItemSelected(
							//AdapterView<?> parent, View view, int position, long id) {
							text = spinner.getSelectedItem().toString();
							spl = locNew.get(text);
							myLocation = text;
							String[] splVal= spl.split(",");
							lat1 = Double.parseDouble(splVal[0]);
							lat2 = Double.parseDouble(splVal[1]);

						GeoLocation geolocation = new GeoLocation(text, lat1, lat2, tz);
						AstronomicalCalendar ac = new AstronomicalCalendar(geolocation);
						ac.getCalendar().set(year, monthOfYear, dayOfMonth);
						srise = ac.getSunrise();
						sset = ac.getSunset();

						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

						TextView sunriseTV = (TextView) findViewById(R.id.sunriseTimeTV);
						TextView sunsetTV = (TextView) findViewById(R.id.sunsetTimeTV);
						Log.d("SUNRISE Unformatted", srise+"");

						sunriseTV.setText(sdf.format(sset));
						sunsetTV.setText(sdf.format(srise));


	}
	
	OnDateChangedListener dateChangeHandler = new OnDateChangedListener()
	{
		public void onDateChanged(DatePicker dp, int year, int monthOfYear, int dayOfMonth)
		{
			updateTime(year, monthOfYear, dayOfMonth);
		}
	};

	private void addFile()
	{

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(
					new InputStreamReader(getAssets().open("au_locations.txt"), "UTF-8"));

			// do reading, usually loop until end of file reading
			try {
				Intent intent = getIntent();
				String imagedesc = intent.getStringExtra("data");
				Log.i("++++++++++++++name", imagedesc);
				String[] dataLocAdded = imagedesc.split(",");
				String newStadded = dataLocAdded[1] + "," + dataLocAdded[2];
				Log.i("New Data", newStadded);
				locNew.put(dataLocAdded[0], newStadded);
				places.add(dataLocAdded[0]);
			}
			catch (Exception e)
			{}
			String mLine;
			while ((mLine = reader.readLine()) != null) {

				//Bundle extras = intent.getExtras();


				String[] dataLoc= mLine.split(",");
				String newSt = dataLoc[1]+"," +dataLoc[2];
				locNew.put(dataLoc[0], newSt);
				places.add(dataLoc[0]);



				final Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, places);
// Specify the layout to use when the list of choices appears
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
				spinner.setAdapter(adapter);
				spinner.setOnItemSelectedListener(
						new AdapterView.OnItemSelectedListener() {
							public void onItemSelected(
									AdapterView<?> parent, View view, int position, long id) {
									String text = spinner.getSelectedItem().toString();
								updateTime(year,month,day);
							}
							public void onNothingSelected(AdapterView<?> parent) {

							}
						});
			}
		} catch (IOException e) {
			//log the exception
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					//log the exception
				}
			}
		}
	}

	public String getData()
	{

		String sendLoc = "Location: "+ myLocation+"\n"+"Sun Rise Time:"+srise+"\n"+"Sun Set Time:"+sset;
		return sendLoc;
	}
	public String listUpdate(int yearn,int monthn,int dayn)	{
		updateTime(yearn,monthn,dayn);
		String ListDate2 = ""+ day+"/"+month+"/"+year;
		String SRTTime = sset.getHours()+":"+sset.getMinutes();
		String SSTTime = srise.getHours()+":"+srise.getMinutes();

		String combData =   ListDate2+","+ SSTTime+","+SRTTime;

		return combData;
	}
}