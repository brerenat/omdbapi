package brere.nat.omdbapi.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import brere.nat.omdbapi.model.SeriesResult;

public class OMDBAPI {
	
	private static final Logger LOG = LoggerFactory.getLogger(OMDBAPI.class);
	private static final String BASE_URL = "http://www.omdbapi.com/";
	protected HttpClientBuilder httpClientBuilder = null;
	private final String apiKey;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public OMDBAPI(final String apiKey) {
		super();
		this.apiKey = apiKey;
	}
	
	protected HttpClient getHttpClient() {
		if (httpClientBuilder == null) {
			httpClientBuilder = HttpClientBuilder.create();
		}
		return httpClientBuilder.build();
	}
	
	protected URI getURI(final List<NameValuePair> params) throws URISyntaxException {
		return new URIBuilder(BASE_URL).addParameters(params).build();
	}
	
	private List<NameValuePair> getBaseParams() {
		final List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("apikey", apiKey));
		return params;
	}
	
	public SeriesResult getSeriesByIMDBID(final String imdbID) throws URISyntaxException, ClientProtocolException, IOException {
		LOG.info("Searching for IMDB :" + imdbID);
		final HttpClient client = getHttpClient();
		
		final List<NameValuePair> params = getBaseParams();
		params.add(new BasicNameValuePair("i", imdbID));
		final HttpGet get = new HttpGet(getURI(params));
		
		final HttpResponse response = client.execute(get);
		final SeriesResult result;
		if (response.getStatusLine().getStatusCode() == 200) {
			result = MAPPER.readValue(response.getEntity().getContent(), SeriesResult.class);
			LOG.info("Response :" + result.isResponse());
		} else {
			result = null;
		}
		
		return result;
	}

}
