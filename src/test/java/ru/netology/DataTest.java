package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.Data.Registration.getRegisteredUser;
import static ru.netology.Data.Registration.getUser;
import static ru.netology.Data.getRandomLogin;
import static ru.netology.Data.getRandomPassword;

public class DataTest {
    @BeforeEach
    public void setUpAll() {
        open("http://localhost:9999");
    }

    //наличие пользователя;
    @Test
    public void shouldRegisteredUser() {
        var registeredUser = getRegisteredUser("active");

        $x("//input[@name='login']").val(registeredUser.getLogin());
        $x("//input[@name='password']").val(registeredUser.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//*[@id='root']/div/h2").shouldHave(Condition.text("Личный кабинет"));
    }

    //статус пользователя;
    @Test
    public void shouldBlockUser() {
        var blockUser = getRegisteredUser("blocked");

        $x("//input[@name='login']").val(blockUser.getLogin());
        $x("//input[@name='password']").val(blockUser.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }

    //невалидный логин;
    @Test
    public void shouldFalseLogin() {
        var regUser = getRegisteredUser("active");
        var falseLogin = getRandomLogin();

        $x("//input[@name='login']").val(falseLogin);
        $x("//input[@name='password']").val(regUser.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    //невалидный пароль.
    @Test
    public void shouldFalsePassword() {
        var regUser = getRegisteredUser("active");
        var falsePass = getRandomPassword();

        $x("//input[@name='login']").val(regUser.getLogin());
        $x("//input[@name='password']").val(falsePass);
        $x("//span[@class='button__text']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    //незарегистрированный пользователь
    @Test
    void shouldNotRegisteredUser(){
        var notRegisteredUser = getUser("active");
        $x("//input[@name='login']").val(notRegisteredUser.getLogin());
        $x("//input[@name='password']").val(notRegisteredUser.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}