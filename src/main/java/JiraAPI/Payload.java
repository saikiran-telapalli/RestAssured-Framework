package JiraAPI;

public class Payload {
	
	public static String getJiraSessionBody() {
		String body = "{"
				+ " \"username\": "
				+ "\"saikiran.telapalli\", "
				+ "\"password\": "
				+ "\"Welcome123$\" "
				+ "}";
		return body;
	}

	public static String getJiraCreateBody() {
		String body = "{" + 

				"      \"fields\": {" + 
				"        \"project\": {" + 
				"          \"key\": \"MOOLYA\"" + 
				"        }," + 
				"        \"summary\": \"Creating issue using Resyassured\"," + 
				"        \"description\": \"Description issue using Resyassured\"," + 
				"        \"issuetype\": {" + 
				"          \"name\": \"Bug\" " + 
				"        },";
		
		return body;
	}

}
