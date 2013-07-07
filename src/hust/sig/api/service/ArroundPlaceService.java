package hust.sig.api.service;

import hust.sig.api.service.model.FullDataInstance;

import java.util.ArrayList;

import ken.soapservicelib.proxy.SoapServiceProxy;

public class ArroundPlaceService {
	private static final String SERVICE_NAMESPACE = "http://service.sig.com/";
	private static final String REPOSITORY_KEY = "BJb1iW9SRxMdn67O871VQ3Tn/W0g4sleHzD2stVYx=";
	private static String serverSpecificIp = "14.160.65.178";
	private SoapServiceProxy<ICoreService> soapServiceProxy;
	public static final String VIETNAMESE = "vn";
	public static final String ENGLISH = "en";
	
	public static final int PLACE = -1;
	public static final int BANK_RESOURCE = 0;
	public static final int TOURIST_RESOURCE = 1;
	public static final int ACCOMODATION = 2;
	public static final int DINNING_SERVICE = 3;
	
	private static String getWSDLURL() {
		return "http://" + serverSpecificIp
				+ ":8080/SIG-CORE-SERVICE/CoreServicePort?WSDL";
	}

	public ArroundPlaceService() {
		soapServiceProxy = new SoapServiceProxy<ICoreService>(ICoreService.class, SERVICE_NAMESPACE, getWSDLURL());
	}


	/**
	 * 
	 * @param geoLat
	 * @param geoLon
	 * @param radius (km)
	 * @param page: begin from 1; 8 reulsts/1 page.
	 * @param category: PLACCE, BANK_RESOURCE ...
	 * @param language ENGLISH, VIETNAMESE
	 * @return
	 */
	public FullDataInstance[] getArroundPlace(double geoLat, double geoLon, float radius, int page, int category, String language){
		String classUri = "";
		switch (category) {
		case PLACE:
			classUri = "http://hust.se.vtio.owl#Place";
			break;
		case BANK_RESOURCE:
			classUri = "http://hust.se.vtio.owl#Bank-Resource";
			break;
		case TOURIST_RESOURCE:
			classUri = "http://hust.se.vtio.owl#Tourist-Resource";
			break;
		case ACCOMODATION:
			classUri = "http://hust.se.vtio.owl#Accomodation";
			break;
		case DINNING_SERVICE:
			classUri = "http://hust.se.vtio.owl#Dining-Service";
			break;

		default:
			return null;
		}
		if (page < 1) return null;
		int limit = 8;
		int offset = (page - 1) * limit;
		return soapServiceProxy.getiComCoreService().getFullDataInstaceWithPreference(classUri, geoLat, geoLon, radius, false, "", null, limit, offset, language, REPOSITORY_KEY);
	}
}
