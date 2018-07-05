package TestFramework.RestAssured;

public class CommonResources {

	public static String placePostData() {
		String res = "/maps/api/place/add/json";
		return res;
	}
	
	public static String deletePostData() {
		String res = "/maps/api/place/delete/json";
		return res;
	}
}
