package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class Data {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));

    @Value
    public static class RegistrationTo {
        String login;
        String password;
        String status;
    }

    private Data() {
    }

    @BeforeAll
    private static void sendRequest(RegistrationTo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin() {
        String login = faker.name().username();
        return login;
    }

    public static String getRandomPassword() {
        String password = faker.internet().password();
        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationTo getUser(String status) {
            RegistrationTo user = new RegistrationTo(getRandomLogin(), getRandomPassword(), status);
            return user;
        }

        public static RegistrationTo getRegisteredUser(String status) {
            RegistrationTo registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }
}
