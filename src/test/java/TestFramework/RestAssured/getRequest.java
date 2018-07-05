package TestFramework.RestAssured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

// Download the jars from-----------> https://github.com/rest-assured/rest-assured/wiki/Downloads

public class getRequest {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://maps.googleapis.com";
		
		given().
		param("location","-33.8670522,151.1957362").
		param("radius","1500").
		param("type","restaurant").
		param("keyword","cruise").
		param("key","AIzaSyCAte6xgYrRReFOlYnAVr8cH107ifxQPeM").
		
		when().
		get("/maps/api/place/nearbysearch/json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and(). 
		body("results[0].name",equalTo("Cruise Bar, Restaurant & Events")).and().
		body("results[0].place_id", equalTo("ChIJi6C1MxquEmsR9-c-3O48ykI"));
		
		System.out.println("test");
	}
}
