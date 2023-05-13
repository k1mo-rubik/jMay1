

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <pre>
 *     Домашнее задание.
 * Необходимо запустить тесты в парралель, дождаться их выполнения и вывести результат в консоль.
 * Код ниже запускает тесты последовательно, нужно будет поменять его на многопоточный аналог. Код вывода в коносль 
 * уже написан его можно не менять.
 * Методы, которые могут понадобиться:
 *
 * {@link Thread#join()}
 * {@link Thread#start()}
 * {@link Runnable#run()}
 * </pre>
 */
public class ParallelTestFramework {

    public static void main(String[] args)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        new TestFramework().runTests(TestClassExample.class);
    }

    static class TestFramework {
        ArrayList<Runnable> task = new ArrayList<>();
        public void runTests(Class<?> testClass)
                throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException {
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
            List<String> results = Collections.synchronizedList(new ArrayList<>());
// ИЛИ ТАК            List<String> results1 = new CopyOnWriteArrayList<>();

            for (Method testMethod : testMethods) {
                task.add(() -> {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("Hello " + threadName);


                try {
                    // r                    
                    testMethod.invoke(testObject);
                    results.add(String.format("Test: %s is success", testMethod.getName()));
                } catch (InvocationTargetException | IllegalAccessException e) {
                    results.add(String.format("Test: %s is failed with %s", testMethod.getName(), e.getCause()));
                }
                });
            }
            Thread[] t;
            t = new Thread[task.size()];
            //Iterator<Runnable> iterator= task.iterator();
            int i =0;

            while (i<task.size()) {
                t[i] = new Thread(task.get(i));
                t[i].start();
                i++;
            }

            i =0;
            while (i<task.size()) {
                t[i].join();
                i++;
            }


            // Сделали простенький репорт
            for (String result : results) {
                System.out.println(result);
            }
        }
    }

    static class TestClassExample {

        @Test
        public void testSomething() {
            System.out.println("Выполняем тестовый метод1");
        }

        @Test
        public void testSomethingElse() {
            System.out.println("Выполняем тестовый метод2");
            throw new RuntimeException("Test is failed");
        }
    }




    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface Test {
    }

}