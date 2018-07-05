package TestFramework.RestAssured;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import JiraAPI.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import TestFramework.RestAssured.ReusableMethods;

public class JiraAPI {
	
	//https://developer.atlassian.com/cloud/jira/platform/rest/#api-api-2-issue-issueIdOrKey-comment-id-put
	
	//https://developer.atlassian.com/server/jira/platform/jira-rest-api-example-cookie-based-authentication-37234858/
	//**********https://github.com/rest-assured/rest-assured/wiki/usage#logging********
	
	Properties prop;

	@BeforeTest
	public void getData() throws IOException {

		prop = new Properties();
		FileInputStream fis = new FileInputStream("/Users/saikiran/eclipse-workspace/DemoProject/src/resources/envronment.properties");
		prop.load(fis);

		//prop.getProperty("HOST"); //To get the value of the key from properties file
	}
	
	//@Test
	public void printSessionId() {
		ReusableMethods.getSessionID();
	}
	
	@Test
	public void createIssue() {
		
		RestAssured.baseURI = prop.getProperty("JiraHost");
		
		 Response resp = given().header("Content-Type","application/json").
		 header("Cookie","JSESSIONID="+ReusableMethods.getSessionID()).
		 body(Payload.getJiraCreateBody()).
		 when().
		 post("/rest/api/2/issue").then().assertThat().statusCode(400).extract().response();
		 
		 JsonPath js = ReusableMethods.rawToJson(resp);
		 String id = js.get("id");
		 System.out.println(id);
		
	}
	
	@Test
	public String addingComment() {
		
		RestAssured.baseURI = prop.getProperty("JiraHost");
		
		 Response resp = given().header("Content-Type","application/json").
		 header("Cookie","JSESSIONID="+ReusableMethods.getSessionID()).
		 body("write body for adding comment").
		 when().
		 post("/rest/api/2/issue/"+ReusableMethods.getSessionID()+"/comment").then().assertThat().statusCode(400).extract().response();
		 
		 JsonPath js = ReusableMethods.rawToJson(resp);
		 String id = js.get("id");
		 System.out.println(id);
		 return id;
		
	}
	
	@Test
	public void updatingComment() {
		
		RestAssured.baseURI = prop.getProperty("JiraHost");
		
		 Response resp = given().header("Content-Type","application/json").
		 header("Cookie","JSESSIONID="+ReusableMethods.getSessionID()).
		 body("write body for updating comment").
		 when().
		 put("/rest/api/2/issue/"+ReusableMethods.getSessionID()+"/comment/"+addingComment()).then().assertThat().statusCode(400).extract().response();
		 
		 JsonPath js = ReusableMethods.rawToJson(resp);
		 String id = js.get("id");
		 System.out.println(id);
	}
	
	

}
