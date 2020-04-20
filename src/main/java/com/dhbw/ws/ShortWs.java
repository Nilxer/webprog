package com.dhbw.ws;

import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ShortWs {

  //LinkedHashMap to store all URLs in the order of insertion
  private LinkedHashMap<String, String> urlMapping;

  //Base URL for all the short URLs provided by the service
  String baseUrl = "http://localhost:8080/api/short/";

  public ShortWs() {
    System.out.println("Registered ShortWs");
    urlMapping = new LinkedHashMap<String, String>();
  }

  //Creates a short URL for a given long URL
  @POST
  @Path("/short")
  public Response createShortUrl(String body) {
    boolean urlIsUnique = false;
    String shortId;
    String shortUrl = "";

    //Gets the long URL from the request
    JSONObject requestBody = new JSONObject(body);
    String longUrl = requestBody.getString("longUrl");

    //Checks if long URL already contains http/https; If not, adds http://
    if(!longUrl.toUpperCase().startsWith("HTTPS://") && !longUrl.toUpperCase().startsWith("HTTP://")){
      longUrl = "http://" + longUrl;
    }

    //Generates a unique short URL with four-digit ID
    while (!urlIsUnique) {
      shortId = RandomStringUtils.randomAlphanumeric(4);
      if (!urlMapping.containsKey(baseUrl + shortId)) {
        urlIsUnique = true;
        shortUrl = baseUrl + shortId;
        urlMapping.put(shortUrl, longUrl);
      }
    }

    //Return new URL
    JSONObject response = new JSONObject();
    response.put("longUrl", longUrl);
    response.put("shortUrl",shortUrl);
    return Response.ok().entity(response.toString()).type(MediaType.APPLICATION_JSON).build();
  }

  //Returns all previously shortened URLs
  @GET
  @Path("/short")
  public Response getAllUrls() {

    Gson gson = new Gson();
    String response = gson.toJson(urlMapping,LinkedHashMap.class);
    return Response.ok().entity(response).type(MediaType.APPLICATION_JSON).build();
  }

  //Redirects to long URL for a given short URL
  @GET
  @Path("/short/{shortId}")
  public Response redirectToLongUrl(@PathParam("shortId") String shortId) throws URISyntaxException {
    String longUrl = "";
    String shortUrl = baseUrl + shortId;
    if (urlMapping.containsKey(shortUrl)) {
      longUrl = urlMapping.get(shortUrl);
    }
    URI targetURIForRedirection = new URI(longUrl);
    return Response.seeOther(targetURIForRedirection).build();
  }

  //Removes mapping between short URL (with given short ID) and the long URL
  @DELETE
  @Path("/short/{shortId}")
  public Response deleteShortUrl(@PathParam("shortId") String shortId){
    String shortUrl = baseUrl + shortId;
    if (urlMapping.containsKey(shortUrl)) {
      urlMapping.remove(shortUrl);
    }
    return Response.ok().build();
  }
}
