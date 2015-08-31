package org.koushik.javabrains.messages.restassuredtest;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.koushik.javabrains.messenger.model.Link;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class MessageResourceTest {
	@Test
	public void testCommentFetchesSuccess() {
		expect().body("id", equalTo(1))
				.body("author", equalTo("hemu"))
				.body("message", equalTo("Test comment"))
				.when()
				.get("http://localhost:8080/messenger/webapi/messages/1/comments/1");
	}

	@Test
	public void testCommentSuccessProgrammatic() {
		Response res = get("http://localhost:8080/messenger/webapi/messages/1/comments/1");
		assertEquals(200, res.getStatusCode());
		String json = res.asString();
		JsonPath jp = new JsonPath(json);
		assertEquals("hemu", jp.get("author"));
		assertEquals("Test comment", jp.get("message"));
	}

	/*
	 * @Test public void testProfileResource(){ Response
	 * res=get("http://localhost:8080/messenger/webapi/messages/1"); String
	 * json=res.asString(); JsonPath jp=new JsonPath(json); jp.setRoot("links");
	 * Link resource=jp.get("find {r ->r.link=~/profiles/}");
	 * assertEquals("http://localhost:8080/messenger/webapi/profiles/Hemanth"
	 * ,resource.get("message")); }
	 */

	@Test
	public void testCommentByParameter(){
		final String author="hemu";
		final String message="Test comment";
		
		given().
		parameters(
				"author",author,
				"message",message).
				expect().
				body("author",equalTo(author)).
				body("message",equalTo(message)).
				when().
				get("http://localhost:8080/messenger/webapi/messages/1/comments/1");
	}
	
	@Test
	public void testPageNotFound(){
		expect().statusCode(404).when().get("http://localhost:8080/messenger/webapi/messages/111/");
	}
}
