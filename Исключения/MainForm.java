import java.util.regex.Matcher;
import java.util.regex.Pattern;

       /* Реализовать программу, проверяющую качество логина и пароля по следующим критериям:
        логин должен содержать только латинские буквы, цифры, и знак подчеркивания,
        длина логина не может превышать 20 символов, пароль может содержать те же символы, что и логин,
        а также любые знаки препинания, минимальная длина пароля – 12 символов, максимальная длина пароля – 20 символов.
        В случае, если был введен неверный пароль, необходимо выбросить исключение BadPasswordFormatException,
        если неверный логин – BadLoginFormatException. Сообщение об исключении должно быть информативным.
        Проверку реализовать в виде статического метода, вызываемого из метода main().
        В main()-методе необходимо обработать данные исключения. */

public class MainForm {

    private static final Pattern NAME_PATTERN = Pattern.compile("^\\w{1,20}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{12,20}$"); //Всё-таки знаки препинания - это знаки препинания, а не "все символы".
                                                                                    //~˜µ≤≥÷Ωç√ - всё это не знаки препинания, но в твоём пароле использоваться могут.

    public static void main(String[] args) throws BadLogin, BadPassword {
        System.out.println(checkLoginPassword(new FormUser("супер", "z3c6b7m9@#$%qsedrg3")));  //Вот этот пароль по идее принят быть не должен.
        System.out.println(checkLoginPassword(new FormUser("puper", "z3c6b7m9@#$%qsedrg3")));  //И этот тоже. По заданию не должен. Но у тебя он примется.
        System.out.println(checkLoginPassword(new FormUser("asd", "asxf45tgd##59")));
        System.out.println(checkLoginPassword(new FormUser("qwerty123", "456789!")));
        System.out.println(checkLoginPassword(new FormUser("qwerty", "123456789")));
        System.out.println(checkLoginPassword(new FormUser("as23456sdf_d_6", "1qaghnbdty8945dft")));
    }

    private static void validUsername (FormUser user) throws BadLogin {

        final Matcher validateName = NAME_PATTERN.matcher(user.username);
        if (!validateName.matches()) {
            throw new BadLogin("Ваш логин должен содержать только латинские буквы," +
                    "цифры и знак подчеркивания, длина логина не может превышать 20 символов.");
        }
    }

    private static void validPassword (FormUser user) throws BadPassword {

        final Matcher validatePassword = PASSWORD_PATTERN.matcher(user.password);
        if (!validatePassword.matches()) {
            throw new BadPassword("Ваш пароль может содержать только латинские буквы, цифры," +
                    "знак подчеркивания, а также любые знаки препинания," +
                    "минимальная длина пароля – 12 символов, максимальная длина пароля – 20 символов.");
        }
    }

    private static String checkLoginPassword (FormUser user) {

        String error = "Неверный формат логина/пароля!";  //error у тебя где-то используется? Зачем оно тебе?
        String success = "Логин и пароль подходят!";

        try {
            MainForm.validUsername(new FormUser(user.username, user.password));   //И зачем ты тут создаёшь нового пользователя? Старый чем не угодил?
            MainForm.validPassword(new FormUser(user.username, user.password));
            return success;
        } catch (BadLogin badLoginExc) {  //Так как действие на поимку исключения у тебя одно и то же, то есть ли смысл разделять их? Кажется, что нет.
            return badLoginExc.getMessage();
        }
        catch (BadPassword badPassExc) {
            return badPassExc.getMessage();
        }
    }

    static class FormUser {
        private String username;

        private String password;

        public FormUser(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}