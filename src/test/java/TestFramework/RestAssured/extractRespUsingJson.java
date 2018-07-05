package TestFramework.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import TestFramework.RestAssured.ReusableMethods;

public class extractRespUsingJson {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://maps.googleapis.com";

		Response resp = given().
				param("location","-33.8670522,151.1957362").
				param("radius","1500").
				param("type","restaurant").
				param("keyword","cruise").
				param("key","AIzaSyCAte6xgYrRReFOlYnAVr8cH107ifxQPeM").log().all().    
				
				//log().all() -----> logs all request specification details including parameters, headers and body refer 
				//https://github.com/rest-assured/rest-assured/wiki/usage#logging

				when().
				get("/maps/api/place/nearbysearch/json").
				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and(). 
				body("results[0].name",equalTo("Cruise Bar, Restaurant & Events")).and().
				body("results[0].place_id", equalTo("ChIJi6C1MxquEmsR9-c-3O48ykI")). 
				header("server", "scaffolding on HTTPServer2").log().body().                      // to log the response
				extract().response();

		JsonPath js = ReusableMethods.rawToJson(resp);
		int count = js.get("results.size()");
		System.out.println("\ncount of results: "+count);

		System.out.println("Printing all places extracting from results");
		for(int i=0;i<count;i++) {
			String place =js.get("results["+i+"].name");
			System.out.println(place);
		}


	}
}
