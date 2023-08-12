package settings;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import lombok.Getter;

@Getter
public class Components {
    //Класс в котором создаются тестовые данные
    private String name;
    private String email;
    private String password;
    private String invalidPasswordWith5Characters;
    private String passwordWithLength6;
    private Faker faker=new Faker();

    public Components() {
        this.name = faker.name().firstName();
        this.email = faker.lorem().characters(7)+"@yandex.ru";
        this.password = faker.lorem().characters(10);
        this.invalidPasswordWith5Characters = faker.lorem().characters(5);
        this.passwordWithLength6 = faker.lorem().characters(6);
    }
}
