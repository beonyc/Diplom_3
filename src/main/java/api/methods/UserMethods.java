package api.methods;

import settings.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserMethods {
    private final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/";

    @Step("Удаление пользователя")
    public Response deleteUser(User user) {
        String response = given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(user)
                .post("auth/login").then().extract().path("accessToken");
        String accessToken = response.replace("Bearer ", "");

        return given()
                .baseUri(BASE_URL)
                .auth().oauth2(accessToken)
                .delete("auth/user");
    }


}
