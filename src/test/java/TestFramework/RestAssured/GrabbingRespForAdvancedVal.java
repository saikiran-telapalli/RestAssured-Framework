package TestFramework.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GrabbingRespForAdvancedVal {

	@Test
	public void addAndDeletePlace() {

		String bod = "{"+
				"\"location\": {"+
				"\"lat\": -33.8669710,"+
				"\"lng\": 151.1958750"+
				" },"+
				"\"accuracy\": 50,"+
				"\"name\": \"Google Shoes!\","+
				" \"phone_number\": \"(02) 9374 4000\","+
				"\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","+
				"\"types\": [\"shoe_store\"],"+
				"\"website\": \"http://www.google.com.au/\","+
				"\"language\": \"en-AU\""+
				"}";


		//Task1--------------> Grab the response
		RestAssured.baseURI = "https://maps.googleapis.com";
		Response resp =given().

				queryParam("key", "AIzaSyD2vmZ-n9In5bBxLo7kVus5iyevtqAZeXg").
				body(bod). 
				when().
				post("/maps/api/place/add/json"). 
				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
				body("status",equalTo("OK")).
				extract().response();   								//When u get response it will be in Raw format convert into string

		String responseString = resp.asString();
		System.out.println(responseString);

		
		//Task2----------------> To get grab any thing from response we need to convert string into json

		JsonPath js = new JsonPath(responseString);					//Convert the string response to Json format using Json path object
		String place_id = js.get("place_id");
		System.out.println(place_id);
		
		
		
		//Task3-----------------> Place the place_id in delete request
		
		given().
		queryParam("key", "AIzaSyD2vmZ-n9In5bBxLo7kVus5iyevtqAZeXg").
		body("{" + 
				"  \"place_id\": \""+place_id+"\"" + 
				"}").
		when().
		post("/maps/api/place/delete/json").
		then().assertThat().statusCode(200).contentType(ContentType.JSON).and().
		body("status", equalTo("OK"));
		
	}

}
