package fr.aquazus.slowchat.misc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

//==============================
//Version 1
//========= AquAPI =============
//AquAPI is a class I use in all of my plugins.
//I use it to do some things, like checking versions, etc...
//Please don't modify it.
//==============================

public class AquAPI {
    static String baseUrl = "http://api.aquazus.fr/bukkitplugins/";
    static String pluginName = "SlowChat";
    static String pluginAPI = baseUrl + pluginName + "/";

    public static boolean isUpToDate(String version) {
	String result = "null";
	result = readUrlContent(pluginAPI + "latestversion.php");
	if (result == "null") {
	    return true;
	} else if (result.equals(version)) {
	    return true;
	} else {
	    return false;
	}
    }

    public static String getLatestVersionNumber() {
	String latestVersion = "null";
	latestVersion = readUrlContent(pluginAPI + "latestversion.php");
	return latestVersion;
    }
    
    static String readUrlContent(String link) {
	String urlContent = "null";
	try {
	    URL url = new URL(link);
	    URLConnection urlConnection = url.openConnection();
	    InputStream is = urlConnection.getInputStream();
	    InputStreamReader isr = new InputStreamReader(is);

	    int numCharsRead;
	    char[] charArray = new char[1024];
	    StringBuffer sb = new StringBuffer();
	    while ((numCharsRead = isr.read(charArray)) > 0) {
		sb.append(charArray, 0, numCharsRead);
	    }
	    urlContent = sb.toString();
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return urlContent;
    }
}
