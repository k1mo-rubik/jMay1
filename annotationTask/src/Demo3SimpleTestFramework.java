import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Demo3SimpleTestFramework {

    public static void main(String[] args)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        new TestFramework().runTests(TestClassExample.class);
    }

    static class TestFramework {

        public void runTests(Class<?> testClass)
                throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            // Создали объект, считаем, что у него есть не приватный констуктор по умолчанию
            final Object testObject = testClass.getDeclaredConstructor().newInstance();

            // Из всех методов объекта нашли только тестовые
            List<Method> testMethods = new ArrayList<>();
            for (Method method : Arrays.asList(testClass.getDeclaredMethods())) {
                if (method.isAnnotationPresent(Test.class)) {
                    testMethods.add(method);
                }
            }

            // Выполнили тестовые методы и записали результаты
            List<String> results = new ArrayList<>(testMethods.size());
            for (Method testMethod : testMethods) {
                try {
                    testMethod.invoke(testObject);
                    results.add(String.format("Test: %s is success", testMethod.isAnnotationPresent(DisplayName.class)? testMethod.getAnnotation(DisplayName.class).value(): testMethod.getName()));
                } catch (InvocationTargetException e) {
                    results.add(String.format("Test: %s is failed with %s", testMethod.isAnnotationPresent(DisplayName.class)? testMethod.getAnnotation(DisplayName.class).value():testMethod.getName(), e.getCause()));
                }
            }

            // Сделали простенький репорт
            for (String result : results) {
                System.out.println(result);
            }
        }
    }

    static class TestClassExample {

        @Test
        @DisplayName("othetTest")
        public void testSomething() {
            System.out.println("Выполняем тестовый метод");
        }


        @Test
        @DisplayName("adsfasfas")
        public void testSomethingElse() {
            throw new RuntimeException("Test is failed");
        }
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface Test {

    }
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface DisplayName {
        String value();
    }

}
