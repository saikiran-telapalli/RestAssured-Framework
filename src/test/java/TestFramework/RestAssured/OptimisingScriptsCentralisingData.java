package TestFramework.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import TestFramework.RestAssured.CommonResources;
import TestFramework.RestAssured.ReusableMethods;
import googleAPI.Payload;


public class OptimisingScriptsCentralisingData {
	
	//JSON Viewer  :  https://jsoneditoronline.org/
	//JSON Path Finder :    https://jsonpath.curiousconcept.com/

	Properties prop;

	@BeforeTest
	public void getData() throws IOException {

		prop = new Properties();
		FileInputStream fis = new FileInputStream("/Users/saikiran/eclipse-workspace/DemoProject/src/resources/envronment.properties");
		prop.load(fis);

		//prop.getProperty("HOST"); //To get the value of the key from properties file
	}

	@Test
	public void addAndDeletePlace() {

		
		//Task1--------------> Grab the response
		RestAssured.baseURI = prop.getProperty("Host");
		Response resp =given().

				queryParam("key", prop.getProperty("Key")).
				body(Payload.getPostBody()). 
				when().
				post(CommonResources.placePostData()). 
				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
				body("status",equalTo("OK")).
				extract().response();   								



		//Task2----------------> To get grab any thing from response we need to convert string into json
		
	/*	String responseString = resp.asString();                     //When u get response it will be in Raw format convert into string
		System.out.println(responseString);
		JsonPath js = new JsonPath(responseString);					//Convert the string response to Json format using Json path object */
		
		//Or written common reusable method for conversion
		
		String place_id = ReusableMethods.rawToJson(resp).get("place_id");
		System.out.println(place_id);



		//Task3-----------------> Place the place_id in delete request

		given().
		queryParam("key", prop.getProperty("Key")).
		body("{" + 
				"  \"place_id\": \""+place_id+"\"" + 
				"}").
		when().
		post(CommonResources.deletePostData()).
		then().assertThat().statusCode(200).contentType(ContentType.JSON).and().
		body("status", equalTo("OK"));

	}


}
