package Api_Testing.Rest_Project;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.PojoClass;

public class Rest_Project2 {

	@Test
	public void postmethod(ITestContext val)throws JsonProcessingException
	{
		try{
			RestAssured.baseURI="http://localhost:3000";
			
			PojoClass obj = new PojoClass();
			obj.setUsername("yume1");
			obj.setFirstName("Yumeko");
			obj.setLastName("Jabami");
			obj.setEmail("yumeko@gmail.com");
			obj.setPassword("12345");
			obj.setPhone("9876543210");
			obj.setUserStatus(0);
			
			ObjectMapper objmapper = new ObjectMapper();
		
			Response resp = given()
				.contentType(ContentType.JSON)
				.body(objmapper.writeValueAsString(obj)).
			when()
				.post("/user").
			then()
				.statusCode(201)
				.log()
				.all()
				.extract()
				.response();
			
			
			String usrname= resp.jsonPath().getString("username");
			val.setAttribute("username", usrname);
			
			String pass= resp.jsonPath().getString("password");
			val.setAttribute("password", pass);
			
			
			String id1= resp.jsonPath().getString("id");
			val.setAttribute("id", id1);
			
			System.out.println(usrname);
			System.out.println(pass);
			
		}catch(AssertionError e){
			System.out.println("Exception handled in postmethod");
		}
		
	}
	@Test(dependsOnMethods = "postmethod")
	public void putmethod(ITestContext val){
		
		try{
			RestAssured.baseURI="http://localhost:3000";
			
			JSONObject ob  = new JSONObject();

			ob.put("username", "yume1");
			ob.put("firstName", "Yumeko");
			ob.put("lastName", "Jabami");
			ob.put("email", "yumeko2@gmail.com");
			ob.put("password", "12345");
			ob.put("phone", "9876543212");
			ob.put("userStatus", 0);
			
			
			given()
				.header("Content-type","application/json")
				.contentType(ContentType.JSON)
				.body(ob.toJSONString()).
			when()
				.put("/user/"+val.getAttribute("id").toString()).
	        then()
	        	.statusCode(200)
				.log()
				.all()
				.extract()
				.response();
		}catch(AssertionError e){
			System.out.println("Exception handled in putmethod");
		}
		
		
	}
	
	@Test(dependsOnMethods = "putmethod")
	public void getmethod(ITestContext val){
		
		try{
			RestAssured.baseURI="http://localhost:3000";
			
			
			given()
				.get("/user/"+val.getAttribute("id").toString()).
	        then()
	        	.statusCode(200)
				.log()
				.all()
				.extract()
				.response();
		}catch(AssertionError e){
			System.out.println("Exception handled in getmethod");
		}
		
		
	}
	
	@Test(dependsOnMethods = "getmethod")
	public void deletemethod(ITestContext val){
		
		try{
			RestAssured.baseURI="http://localhost:3000";
			
			
			given()
				.delete("/user/"+val.getAttribute("id").toString()).
	        then()
	        	.statusCode(200)
				.log()
				.all();
		}catch(AssertionError e){
			System.out.println("Exception handled in putmethod");
		}
		
		
	}
}
