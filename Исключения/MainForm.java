import java.util.regex.Matcher;
import java.util.regex.Pattern;

       /* Реализовать программу, проверяющую качество логина и пароля по следующим критериям:
        логин должен содержать только латинские буквы, цифры, и знак подчеркивания,
        длина логина не может превышать 20 символов,
         пароль может содержать те же символы, что и логин,
        а также любые знаки препинания, минимальная длина пароля – 12 символов, максимальная длина пароля – 20 символов.
        В случае, если был введен неверный пароль, необходимо выбросить исключение BadPasswordFormatException,
        если неверный логин – BadLoginFormatException. Сообщение об исключении должно быть информативным.
        Проверку реализовать в виде статического метода, вызываемого из метода main().
        В main()-методе необходимо обработать данные исключения. */

public class MainForm {
    // регулярное для логина с буквами цифрами и подчеркиванием длинной от 1-20
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{1,20}$");
    // для уточнения ошибки регулярка на длину логина
    private static final Pattern LOGIN_PATTERN_OUT_OF_RANGE = Pattern.compile("^.{1,20}$");
    // для уточнения ошибки регулярка на символы логина
    private static final Pattern LOGIN_PATTERN_SYMBOLS = Pattern.compile("^[a-zA-Z0-9_]*$");
    // регулярное для пароля с буквами цифрами, подчеркиванием и ЗП длинной от 12-20
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9_\\p{Punct}]{12,20}$");
    // для уточнения ошибки регулярка на длину пароля
    private static final Pattern PASSWORD_PATTERN_OUT_OF_RANGE = Pattern.compile("^.{12,20}$");
    // для уточнения ошибки регулярка на символы пароля
    private static final Pattern PASSWORD_PATTERN_SYMBOLS = Pattern.compile("^[a-zA-Z0-9_\\p{Punct}]*$");

    //Всё-таки знаки препинания - это знаки препинания, а не "все символы"
    // Класс знаков пунктуации (препинания в java) - Punct содержит !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
//    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9_,.]{12,20}$"); //вот ., если нужны
//    еще какие то знаки препинания а Punct не подходит то заменить строчку закоментированной  и добавить по надобности еще знаков


    public static void main(String[] args)  {

        System.out.println(validLoginPassword(new FormUser("супер", "z3c6b7m9@#$%qsedrg3asssssssssssssssssssssssssssssssss")));
        System.out.println(validLoginPassword(new FormUser("puper222222222222222222222222222222222_2222222222222222222222222222222222222", "z3c6b7m9@#$%qsedrg3")));
        System.out.println(validLoginPassword(new FormUser("asd", "asxf45tgd##59")));
        System.out.println(validLoginPassword(new FormUser("qwerty123", "456789!")));
        System.out.println(validLoginPassword(new FormUser("qwerty", "123456µ≤≥÷789")));
        System.out.println(validLoginPassword(new FormUser("as23456sdf_d_6", "1qaghnbdty8945dft.,")));
    }

    private static void validUsername (FormUser user) throws BadLoginFormatException {

        final Matcher validateLogin = LOGIN_PATTERN.matcher(user.login);
        //если не прошло общую проверку
        if (!validateLogin.matches()) {
            // то проводим еще 2 проверки чтобы уточнить причину
            final Matcher validateLoginRange = LOGIN_PATTERN_OUT_OF_RANGE.matcher(user.login);
            final Matcher validateLoginSymbols = LOGIN_PATTERN_SYMBOLS.matcher(user.login);
            String errMessage = "";
            // если не прошла проверка на символы
            if (!validateLoginSymbols.matches()) {
                errMessage += "Ваш логин должен содержать только латинские буквы, цифры и знак подчеркивания";
            }
            // если не прошла проверка на длину
            if (!validateLoginRange.matches()) {
                // если проверка ан символы прошла, то пишем с большой буквы сообщение
                if (errMessage.equals("")){
                    errMessage += "Длина логина не может превышать 20 символов";
                }
                // если проверка ан символы НЕ прошла, то пишем запятую и дополняем строку ошибки
                else{
                    errMessage += ", длина логина не может превышать 20 символов";
                }
            }

            throw new BadLoginFormatException(errMessage);
        }
    }

    private static void validPassword (FormUser user) throws BadPasswordFormatException {
        // Аналогично логину
        final Matcher validatePassword = PASSWORD_PATTERN.matcher(user.password);
        if (!validatePassword.matches()) {
            final Matcher validatePasswordRange = PASSWORD_PATTERN_OUT_OF_RANGE.matcher(user.password);
            final Matcher validatePasswordSymbols = PASSWORD_PATTERN_SYMBOLS.matcher(user.password);
            String errMessage = "";

            if (!validatePasswordSymbols.matches()) {
                errMessage += "Ваш пароль должен содержать только латинские буквы, цифры и знак подчеркивания, а также любые знаки препинания";
            }
            if (!validatePasswordRange.matches()) {
                if (errMessage.equals("")){
                    errMessage += "Длина пароля не может быть меньше 12 символов и больше 20 символов";
                }else{
                    errMessage += ", длина пароля не может быть меньше 12 символов и больше 20 символов\"";
                }
            }
            throw new BadPasswordFormatException(errMessage);
        }

    }

    private static String validLoginPassword(FormUser user) {

        try {
            MainForm.validUsername(user);
            MainForm.validPassword(user);
            return "Логин и пароль подходят!";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    static class FormUser {
        private String login;

        private String password;

        public FormUser(String login, String password) {
            this.login = login;
            this.password = password;
        }
    }
}