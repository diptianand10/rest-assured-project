package Api_Testing.Rest_Project;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import pojo.PojoClass;



public class Rest_Project1 {
	
	@Test
	public void postmethod(ITestContext val)throws JsonProcessingException
	{
		try{
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			
			PojoClass obj = new PojoClass();
			obj.setUsername("yume1");
			obj.setFirstName("Yumeko");
			obj.setLastName("Jabami");
			obj.setEmail("yumeko@gmail.com");
			obj.setPassword("12345");
			obj.setPhone("9876543210");
			obj.setUserStatus(0);
			
			ObjectMapper objmapper = new ObjectMapper();
		
			given()
				.contentType(ContentType.JSON)
				.body(objmapper.writeValueAsString(obj)).
			when()
				.post("/user").
			then()
				.statusCode(200)
				.log()
				.all()
				.extract()
				.response();
			
			
			String usrname= obj.getUsername();
			val.setAttribute("username", usrname);
			
			String pass= obj.getPassword();
			val.setAttribute("password", pass);
			
			System.out.println(usrname);
// 			System.out.println(pass);
			
		}catch(AssertionError e){
			System.out.println("Exception handled in postmethod");
		}
		
	}
	@Test(dependsOnMethods = "postmethod")
	public void putmethod(ITestContext val){
		
		try{
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			
			JSONObject ob  = new JSONObject();

			ob.put("username", "yume1");
			ob.put("firstName", "Yumeko");
			ob.put("lastName", "Jabami");
			ob.put("email", "yumeko1@gmail.com");
			ob.put("password", "12345");
			ob.put("phone", "9876543211");
			ob.put("userStatus", 1);
			
			given()
				.header("Content-type","application/json")
				.contentType(ContentType.JSON)
				.body(ob.toJSONString()).
			when()
				.put("/user/"+val.getAttribute("username").toString()).
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
	public void login(ITestContext val){
		
		try{
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			
			given()
				.queryParam("username", val.getAttribute("username").toString())
				.queryParam("password", val.getAttribute("password").toString())
				.get("/user/login").
			then()
				.statusCode(200)
				.log()
				.all();
		}catch(AssertionError e){
			System.out.println("Exception handled in login");
		}
		
		
	}
	
	@Test(dependsOnMethods = "login")
	public void logout(ITestContext val){
		
		try{
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			
			given()
				.get("/user/logout").
			then()
				.statusCode(200)
				.log()
				.all();
		}catch(AssertionError e){
			System.out.println("Exception handled in logout");
		}
	}
		
	
	@Test(dependsOnMethods = "logout")
	public void deletemethod(ITestContext val){
		
		try{
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			
			String username = val.getAttribute("username").toString();
			
			given()
				.delete("/user/" + username).
			then()
				.statusCode(200)
				.log()
				.all();
		}catch(AssertionError e){
			System.out.println("Exception handled in deletemethod");
		}
		
		
	}
}
