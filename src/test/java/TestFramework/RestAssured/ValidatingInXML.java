package TestFramework.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import TestFramework.RestAssured.ReusableMethods;

public class ValidatingInXML {

	@Test
	public void addAndDeletePlace() throws IOException {

		String postData = GenerateStringFromResorce("/Users/saikiran/eclipse-workspace/DemoProject/src/resources/postDataInXML.xml");


		//Task1--------------> Grab the response
		RestAssured.baseURI = "https://maps.googleapis.com";
		Response resp =given().

				queryParam("key", "AIzaSyD2vmZ-n9In5bBxLo7kVus5iyevtqAZeXg").
				body(postData). 
				when().
				post("/maps/api/place/add/xml"). 
				then().assertThat().statusCode(200).and().contentType(ContentType.XML).
				extract().response();   								//When u get response it will be in Raw format convert into string

		

		//Task2----------------> To get grab any thing from response we need to convert string into xml

//		String responseString = resp.asString();
//		System.out.println(responseString);
//		XmlPath X = new XmlPath(responseString);					//Convert the string response to XML format using XML path object
		
		
		String place_id = ReusableMethods.rawToXML(resp).get("PlaceAddResponse.place_id");  
		//How to find the xml path go through this : 
		//https://static.javadoc.io/com.jayway.restassured/rest-assured/1.4/com/jayway/restassured/path/xml/XmlPath.html
		System.out.println(place_id);


	}


	//<-------- Method to convert XML to String -------------->
	public static String GenerateStringFromResorce(String path) throws IOException {

		return new String(Files.readAllBytes(Paths.get(path)));
	}
}
