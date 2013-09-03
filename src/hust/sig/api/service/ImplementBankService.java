package hust.sig.api.service;

import hust.sig.api.service.model.DetailProperty;
import hust.sig.api.service.model.FullDataInstance;
import hust.sig.api.service.model.SimpleDataObject;
import ken.soapservicelib.proxy.SoapServiceProxy;

public class ImplementBankService {
	public static final String SERVICE_NAMESPACE = "http://service.sig.com/";
	public static String VTIO_REPOSITORY_KEY = "Db0hsr9lGsz6FaUGW7lPZcZ2VcFOMrzHZIJT2qi27p8=";
	public static String xsd = "http://www.w3.org/2001/XMLSchema#";
	// private static String serverSpecificIp = "203.113.166.55";
	private static String serverSpecificIp = "14.160.65.178";
	private SoapServiceProxy<ICoreService> soapServiceProxy;
	public static final String VIETNAMESE = "vn";
	public static final String ENGLISH = "en";

	public static String getWSDLURL() {
		return "http://" + serverSpecificIp
				+ ":8080/SIG-CORE-SERVICE/CoreServicePort?WSDL";
	}

	public ImplementBankService() {
		soapServiceProxy = new SoapServiceProxy<ICoreService>(
				ICoreService.class, SERVICE_NAMESPACE, getWSDLURL());
	}

	public ImplementBankService(int timeoutInMillis) {

		soapServiceProxy = new SoapServiceProxy<ICoreService>(
				ICoreService.class, SERVICE_NAMESPACE, getWSDLURL(),
				timeoutInMillis);
	}
	public ImplementBankService(String serverIp, String repositoryKey) {
		serverSpecificIp = serverIp;
		VTIO_REPOSITORY_KEY = repositoryKey;
		soapServiceProxy = new SoapServiceProxy<ICoreService>(
				ICoreService.class, SERVICE_NAMESPACE, getWSDLURL());
	}

	public FullDataInstance[] getAllBankByLocation(double lon, double lat,
			float radius, String categoryUri, String keyword, int limit,
			int offset, String language) {
		if (limit > 10)
			return null;
		return soapServiceProxy.getiComCoreService()
				.getFullDataInstaceWithPreference(categoryUri, lat, lon,
						radius, false, keyword, null, limit, offset, language,
						VTIO_REPOSITORY_KEY);
	}

	@Deprecated
	public SimpleDataObject[] getAllCity(String lang) {
		String[][] cityStrings = soapServiceProxy
				.getiComCoreService()
				.executeQuery(
						"select ?uri ?label { ?uri rdf:type vtio:City. { select ?label { ?uri rdfs:label ?label. FILTER(lang(?label)='"
								+ lang + "') } LIMIT 1 } }", false,
						VTIO_REPOSITORY_KEY);
		SimpleDataObject[] simpleDataObjects = new SimpleDataObject[cityStrings.length];
		for (int i = 0; i < simpleDataObjects.length; i++) {
			SimpleDataObject simpleDataObject = new SimpleDataObject();
			simpleDataObject.setUriString(cityStrings[i][0]);
			simpleDataObject.setLabelString(cityStrings[i][1]);
			simpleDataObjects[i] = simpleDataObject;
		}
		return simpleDataObjects;
	}
	
	@Deprecated
	public SimpleDataObject[] getAllDistrictOfCity(String cityUri, String lang) {
		String[][] cityStrings = soapServiceProxy
				.getiComCoreService()
				.executeQuery(
						"select ?uri ?label { ?uri rdf:type vtio:District. ?uri vtio:isPartOf <"
								+ cityUri
								+ ">. { select ?label { ?uri rdfs:label ?label. FILTER(lang(?label)='"
								+ lang + "') } LIMIT 1 } }", false,
						VTIO_REPOSITORY_KEY);
		SimpleDataObject[] simpleDataObjects = new SimpleDataObject[cityStrings.length];
		for (int i = 0; i < simpleDataObjects.length; i++) {
			SimpleDataObject simpleDataObject = new SimpleDataObject();
			simpleDataObject.setUriString(cityStrings[i][0]);
			simpleDataObject.setLabelString(cityStrings[i][1]);
			simpleDataObjects[i] = simpleDataObject;
		}
		return simpleDataObjects;
	}

	public SimpleDataObject[] getAllBankResourceCategory(String lang) {
		String[][] cityStrings = soapServiceProxy
				.getiComCoreService()
				.executeQuery(
						"select ?uri ?label { ?uri rdf:type owl:Class.  ?uri rdfs:subClassOf vtio:Bank-Resource. { select ?label { ?uri rdfs:label ?label. FILTER(lang(?label)='"
								+ lang + "') } LIMIT 1 } }", true,
						VTIO_REPOSITORY_KEY);
		SimpleDataObject[] simpleDataObjects = new SimpleDataObject[cityStrings.length];
		for (int i = 0; i < simpleDataObjects.length; i++) {
			SimpleDataObject simpleDataObject = new SimpleDataObject();
			simpleDataObject.setUriString(cityStrings[i][0]);
			simpleDataObject.setLabelString(cityStrings[i][1]);
			simpleDataObjects[i] = simpleDataObject;
		}
		return simpleDataObjects;
	}

	public SimpleDataObject[] getAllBankFirm(String lang) {
		String[][] cityStrings = soapServiceProxy
				.getiComCoreService()
				.executeQuery(
						"select ?uri ?label { ?uri rdf:type vtio:Bank. { select ?label { ?uri rdfs:label ?label. FILTER(lang(?label)='"
								+ lang + "') } LIMIT 1 } }", false,
						VTIO_REPOSITORY_KEY);
		SimpleDataObject[] simpleDataObjects = new SimpleDataObject[cityStrings.length];
		for (int i = 0; i < simpleDataObjects.length; i++) {
			SimpleDataObject simpleDataObject = new SimpleDataObject();
			simpleDataObject.setUriString(cityStrings[i][0]);
			simpleDataObject.setLabelString(cityStrings[i][1]);
			simpleDataObjects[i] = simpleDataObject;
		}
		return simpleDataObjects;
	}

	public String[] getAllImageOfPlace(String placeUri) {
		String query = "select ?url where {" + "<" + placeUri
				+ "> vtio:hasMedia ?imageUri. ?imageUri rdf:type vtio:Image."
				+ "?imageUri vtio:hasURL ?url." + "}";
		String[][] results = soapServiceProxy.getiComCoreService()
				.executeQuery(query, true, VTIO_REPOSITORY_KEY);
		String[] listUrl = new String[results.length];
		for (int i = 0; i < listUrl.length; i++) {
			String url = results[i][0].replace("^^" + xsd + "string", "");
			url = url.trim();
			url = url.replace(" ", "%20");
			listUrl[i] = url;
		}

		return listUrl;
	}

	public FullDataInstance[] getAllBankOfDistrict(String categoryUri,
			String districtUri, String lang, int limit, int offset) {
		if (limit > 10)
			return null;
		String queryString = "select ?uri { ?uri rdf:type <" + categoryUri
				+ ">. ?uri vtio:hasLocation ?l. ?l vtio:isPartOf <"
				+ districtUri + ">.  } LIMIT " + limit + " OFFSET " + offset;
		return soapServiceProxy.getiComCoreService().getFullDataInstace(
				queryString, lang, true, VTIO_REPOSITORY_KEY);
	}

	public FullDataInstance[] getAllATMOfDistrictWithFirm(String bankFirmUri,
			String districtUri, String lang, int limit, int offset) {
		String queryString = "select distinct ?place where"
				+ "{ ?place rdf:type <http://hust.se.vtio.owl#ATM>. "
				+ "?place vtio:belongToBank <"
				+ bankFirmUri
				+ ">. ?place <http://hust.se.vtio.owl#hasLocation> ?add_0. ?add_0 <http://hust.se.vtio.owl#isPartOf> <"
				+ districtUri + ">. } LIMIT " + limit + " OFFSET " + offset;;
		return soapServiceProxy.getiComCoreService().getFullDataInstace(
				queryString, lang, true, VTIO_REPOSITORY_KEY);
	}

	public FullDataInstance[] getAllATMByLocationWithFirm(String bankFirmUri,
			double lon, double lat, float radius, String lang, int limit,
			int offset) {
		String queryString = "select distinct ?place where"
				+ "{ GEO OBJECT SUBTYPE 'http://franz.com/ns/allegrograph/3.0/geospatial/spherical/degrees/-180.0/180.0/-90.0/90.0/5.0' "
				+ "HAVERSINE (POINT("
				+ lon
				+ ","
				+ lat
				+ "), "
				+ radius
				+ " KM) "
				+ "{ ?place vtio:hasGeoPoint ?loc. ?place rdf:type <http://hust.se.vtio.owl#ATM>. ?place vtio:belongToBank <"
				+ bankFirmUri + ">. } where{  } } LIMIT " + limit + " OFFSET " + offset;
		return soapServiceProxy.getiComCoreService().getFullDataInstace(
				queryString, lang, false, VTIO_REPOSITORY_KEY);
	}

	public SimpleDataObject[] getAllProvinceAndCity() {
		String queryString = "select distinct ?cityprovince ?lvn where"
				+ "{ { ?cityprovince rdf:type <http://hust.se.vtio.owl#City>. "
				+ "?cityprovince <http://hust.se.vtio.owl#isPartOf> <http://hust.se.vtio.owl#vietnam-country>. "
				+ "{ select ?lvn where {?cityprovince rdfs:label ?lvn. FILTER (lang(?lvn)='vn')} LIMIT 1 } } "
				+ "UNION { ?cityprovince rdf:type <http://hust.se.vtio.owl#Province>. "
				+ "{ select ?lvn where {?cityprovince rdfs:label ?lvn. FILTER (lang(?lvn)='vn')} LIMIT 1 } } }";
		String[][] cityStrings = soapServiceProxy.getiComCoreService()
				.executeQuery(queryString, false, VTIO_REPOSITORY_KEY);
		SimpleDataObject[] simpleDataObjects = new SimpleDataObject[cityStrings.length];
		for (int i = 0; i < simpleDataObjects.length; i++) {
			SimpleDataObject simpleDataObject = new SimpleDataObject();
			simpleDataObject.setUriString(cityStrings[i][0]);
			simpleDataObject.setLabelString(cityStrings[i][1]);
			simpleDataObjects[i] = simpleDataObject;
		}
		return simpleDataObjects;
	}
	public SimpleDataObject[] getAllDistrict(String provinceUri) {
		String queryString = "select distinct ?districtcity ?lvn where" +
				"{ { ?districtcity rdf:type <http://hust.se.vtio.owl#District>. " +
				"?districtcity <http://hust.se.vtio.owl#isPartOf> <"+provinceUri+">." +
						" { select ?lvn where {?districtcity rdfs:label ?lvn. FILTER (lang(?lvn)='vn')} LIMIT 1 } }" +
						" UNION { ?districtcity rdf:type <http://hust.se.vtio.owl#City>." +
						" ?districtcity <http://hust.se.vtio.owl#isPartOf> <"+provinceUri+">. " +
								"{ select ?lvn where {?districtcity rdfs:label ?lvn. FILTER (lang(?lvn)='vn')} LIMIT 1 } } }";
		String[][] cityStrings = soapServiceProxy.getiComCoreService()
				.executeQuery(queryString, false, VTIO_REPOSITORY_KEY);
		SimpleDataObject[] simpleDataObjects = new SimpleDataObject[cityStrings.length];
		for (int i = 0; i < simpleDataObjects.length; i++) {
			SimpleDataObject simpleDataObject = new SimpleDataObject();
			simpleDataObject.setUriString(cityStrings[i][0]);
			simpleDataObject.setLabelString(cityStrings[i][1]);
			simpleDataObjects[i] = simpleDataObject;
		}
		return simpleDataObjects;
	}

	public DetailProperty[] getDetailPropertiesOfPlace(String uri, String lang) {
		return soapServiceProxy.getiComCoreService()
				.getInstanceDataWithLabelObject(uri, lang, VTIO_REPOSITORY_KEY);
	}

}
