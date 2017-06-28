package pl.translator.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Translate {

	final static String URL_ADDRESS = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
	final static String API_KEY = "trnsl.1.1.20170509T085317Z.4759e1c37cb9cb67.405eef9c211800d05762c90c1e5f84d2ab6137d6";
	final static String CHARSET = "UTF-8";
	private String sourceLanguage;
	private String destinationLanguage;

	public Translate(String source, String destination) {
		setSourceLanguage(source);
		setDestinationLanguage(destination);
	}

	public String translate(String textToTranslate) throws UnsupportedEncodingException {
		String query = URL_ADDRESS + "key=" + URLEncoder.encode(API_KEY, CHARSET)
				+ "&lang=" + URLEncoder.encode(sourceLanguage,CHARSET) +
				URLEncoder.encode("-",CHARSET) + URLEncoder.encode(destinationLanguage,CHARSET) + "&text=" + URLEncoder.encode(textToTranslate, CHARSET);
		//System.out.println(query);
		try {
		  return manageConnection(query);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ERROR";
	}

	//  open HTTPURLConnection
	private String manageConnection(String urlAddress) throws IOException {
		URL url = new URL(urlAddress);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type","text/plain; charset=" + "UTF-8");
		connection.setRequestProperty("Accept-Charset","UTF-8");
		connection.setRequestMethod("GET");


		int responseCode = connection.getResponseCode();
		//System.out.println(responseCode);
		String response = readAnswer(connection.getInputStream());
		connection.disconnect();
		return parseResponse(response);
	}

	// Get Answer from Yandex Translator
	private String readAnswer(InputStream inputStream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8") );
		StringBuilder response = new StringBuilder();
		String inputLine;
		while ((inputLine = in.readLine())!= null) {
			response.append(inputLine);
		}
		return response.toString();
	}

	private String parseResponse(String response) {
		JSONObject jsonObj = (JSONObject)JSONValue.parse(response);
		JSONArray jsonArr = (JSONArray)jsonObj.get("text");
		String stringResponse = new String();
		for(Object obj: jsonArr) {
			stringResponse = obj.toString();
		}
		return stringResponse;
	}

	public String getSourceLanguage() {
		return sourceLanguage;
	}
	public void setSourceLanguage(String sourceLanguage) {
		this.sourceLanguage = sourceLanguage;
	}

	public String getDestinationLanguage() {
		return destinationLanguage;
	}

	public void setDestinationLanguage(String destinationLanguage) {
		this.destinationLanguage = destinationLanguage;
	}
}