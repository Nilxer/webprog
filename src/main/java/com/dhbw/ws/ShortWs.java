package com.dhbw.ws;

import java.net.URISyntaxException;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;

import org.json.JSONObject;

import java.net.URI;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ShortWs {

  // Hashmap to store all URLs
  private HashMap<String, String> urlMapping;

  // Base URL for all short URLs
  String baseUrl = "http://localhost:8080/api/short/";

  public ShortWs() {
    System.out.println("Registered ShortWs");
    urlMapping = new HashMap<String, String>();
  }

  // Creates short URL for a given long URL
  @POST
  @Path("/short")
  public Response createShortUrl(String body) {
    boolean urlIsUnique = false;
    String shortId = "";
    String shortUrl = "";

    // Gets long URL from request
    JSONObject requestBody = new JSONObject(body);
    String longUrl = requestBody.getString("longUrl");

    // Generates a unique short URL
    while (!urlIsUnique) {
      shortId = RandomStringUtils.randomAlphanumeric(4);
      if (!urlMapping.containsKey(baseUrl + shortId)) {
        urlIsUnique = true;
        shortUrl = baseUrl + shortId;
        urlMapping.put(shortUrl, longUrl);
      }
    }

    // Return new URL
    JSONObject response = new JSONObject();
    response.put("longUrl", longUrl);
    response.put("shortUrl",shortUrl);
    return Response.ok().entity(response.toString()).type(MediaType.APPLICATION_JSON).build();
  }

  // Returns all saved URLs
  @GET
  @Path("/short")
  public Response getAllUrls() {
    JSONObject response = new JSONObject(urlMapping);
    return Response.ok().entity(response.toString()).type(MediaType.APPLICATION_JSON).build();
  }

  // Redirects to long URL for a given short URL
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
