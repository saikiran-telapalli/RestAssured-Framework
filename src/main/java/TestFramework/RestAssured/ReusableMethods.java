package TestFramework.RestAssured;



import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import JiraAPI.Payload;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ReusableMethods {
	
	public static JsonPath rawToJson(Response r) {
		String response_string = r.asString();
		JsonPath js = new JsonPath(response_string);
		return js;
		
	}

	public static  XmlPath rawToXML(Response r) {
		String response_string = r.asString();
		XmlPath x = new XmlPath(response_string);
		return x;
		
	}
	
	
	public static String getSessionID() {
		
		RestAssured.baseURI = "https://jira.raksan.in";
		
		Response resp = given().header("Content-Type","application/json").
		body(Payload.getJiraSessionBody()).
		when().
		post("/rest/auth/1/session").then().extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(resp);
		String sessionID = js.get("session.value");
		System.out.println(sessionID);
		return sessionID;
	}
}
