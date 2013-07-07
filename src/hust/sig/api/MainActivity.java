package hust.sig.api;

import hust.sig.api.service.ArroundPlaceService;
import hust.sig.api.service.ICoreService;
import hust.sig.api.service.ImplementBankService;
import hust.sig.api.service.ImplementHotelService;
import hust.sig.api.service.model.DetailProperty;
import hust.sig.api.service.model.FullDataInstance;
import hust.sig.api.service.model.SimpleDataObject;
import ken.soapservicelib.proxy.SoapServiceProxy;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	ImplementHotelService implementService = new ImplementHotelService(60000);
	ImplementBankService implementBankService = new ImplementBankService();
	ArroundPlaceService arroundPlaceService = new ArroundPlaceService();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
//					FullDataInstance[] fullDataInstances = implementService.getAllHotelByLocation(105.8421478271, 20.98737144470, 3, "http://hust.se.vtio.owl#Place", "", 10, 0, ImplementService.VIETNAMESE);
//					FullDataInstance[] fullDataInstances = implementService.getAllHotelOfDistrict("http://hust.se.vtio.owl#Hotel", "http://hust.se.vtio.owl#hoan-kiem-district", ImplementService.VIETNAMESE, 10, 0);
//					SimpleDataObject [] simpleDataObjects  = implementService.getAllAccomodationCategory(ImplementService.VIETNAMESE);
//					SimpleDataObject [] simpleDataObjects  = implementService.getAllDistrictOfCity("http://hust.se.vtio.owl#hanoi-city", ImplementService.ENGLISH);
//					SimpleDataObject [] simpleDataObjects  = implementService.getAllCity(ImplementService.ENGLISH);
//					String [] simpleDataObjects  = implementService.getAllImageOfPlace("http://hust.se.vtio.owl#luc-thuy-restaurant");
//					DetailProperty[] simpleDataObjects = implementService.getDetailPropertiesOfPlace("http://hust.se.vtio.owl#luc-thuy-restaurant", ImplementService.VIETNAMESE);
//					SimpleDataObject[] bankFirmSimpleDataObjects = new ImplementBankService().getAllBankFirm(ImplementBankService.ENGLISH);
					FullDataInstance[] fullDataInstances = arroundPlaceService.getArroundPlace(20.98737144470,105.8421478271,  2.5f, 1, ArroundPlaceService.DINNING_SERVICE, ArroundPlaceService.VIETNAMESE);
					Log.v("KEN", fullDataInstances.length + " results");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		asyncTask.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
