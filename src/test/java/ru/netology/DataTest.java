package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.Data.Registration.getRegUser;
import static ru.netology.Data.getRandomLogin;

public class DataTest {
    @BeforeEach
    public void setUpAll() {
        open("http://localhost:9999");
    }

    //наличие пользователя;
    @Test
    public void RegUser() {
        var regUser = getRegUser("active");

        $x("//input[@name='login']").val(regUser.getLogin());
        $x("//input[@name='password']").val(regUser.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//*[@id='root']/div/h2").shouldHave(Condition.text("Личный кабинет"));
    }

    //статус пользователя;
    @Test
    public void BlockUser() {
        var blockUser = getRegUser("blocked");

        $x("//input[@name='login']").val(blockUser.getLogin());
        $x("//input[@name='password']").val(blockUser.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }

    //невалидный логин;
    @Test
    public void FalseLogin() {
        var regUser = getRegUser("active");
        var falseLogin = getRandomLogin();

        $x("//input[@name='login']").val(falseLogin);
        $x("//input[@name='password']").val(regUser.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    //невалидный пароль.
    @Test
    public void FalsePass() {
        var regUser = getRegUser("active");
        var falsePass = getRandomLogin();

        $x("//input[@name='login']").val(regUser.getLogin());
        $x("//input[@name='password']").val(falsePass);
        $x("//span[@class='button__text']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}