package hust.sig.api.service;

import hust.sig.api.service.model.DetailProperty;
import hust.sig.api.service.model.FullDataInstance;

public interface ICoreService {
	public FullDataInstance[] getFullDataInstaceWithPreference(String classUri,
			double geoLat, double geoLon, float radius, boolean hasPreference,
			String keyWord, String[] preferences, int limit, int offset,
			String languageCode, String key);
	
	public String[][] executeQuery(String query, boolean reason, String key);
	
	public FullDataInstance[] getFullDataInstace (String queryUri, String lang, boolean reason, String key);
	
	public DetailProperty[] getInstanceDataWithLabelObject (String uri, String lang, String key);
}
