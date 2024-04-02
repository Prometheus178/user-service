package org.booking.core.integration.appointment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.request.TokenRequest;
import org.booking.core.domain.request.BaseRegisterRequest;
import org.booking.core.integration.AbstractIntegrationTest;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class RegistrationIntegrationTest extends AbstractIntegrationTest {

	public static String customerToken;
	public static String managerToken;

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = BASE_URI;
		managerToken = register("/api/v1/auth/business/register");
		customerToken = register("/api/v1/auth/register");

//		customerToken = login()
	}

	private static String register(String path) {
		BaseRegisterRequest registerRequest = Instancio.of(BaseRegisterRequest.class).create();

		String email = registerRequest.getEmail();
		if (path.contains("business")) {
			registerRequest.setEmail(email + "business@mail.com");
		} else {
			registerRequest.setEmail(email + "client@email.com");
		}
		String requestBody = getRequestBody(registerRequest);

		Response response = given()
				.contentType(ContentType.JSON)
				.and()
				.body(requestBody)
				.when()
				.post(path)
				.then()
				.extract()
				.response();
		TokenRequest tokenRequest = response.body().as(TokenRequest.class);
		return tokenRequest.getToken();
	}

	private static String login(String email) {
		BaseRegisterRequest registerRequest = Instancio.of(BaseRegisterRequest.class).create();

		registerRequest.setEmail(email);
		registerRequest.setPassword("");

		String requestBody = getRequestBody(registerRequest);

		Response response = given()
				.contentType(ContentType.JSON)
				.and()
				.body(requestBody)
				.when()
				.post("/api/v1/auth/login")
				.then()
				.extract()
				.response();
		TokenRequest tokenRequest = response.body().as(TokenRequest.class);
		return tokenRequest.getToken();
	}
}
