package Homework;

import java.util.*;

public class Main {
    public static void main(String[] args) {   //Так, простыню эту давай бить на функции/классы/методы.
                                                //Так точно не годится.
        ArrayList<Student> students = new ArrayList<>();
        Student student1 = new Student("Иванов Иван Иванович",
                new ArrayList<>(Arrays.asList(5, 5, 4, 4, 4)), 20);
        Student student2 = new Student("Александров Александр Александрович",
                new ArrayList<>(Arrays.asList(3, 4, 3, 3, 4)), 22);
        Student student3 = new Student("Аброров Артур Дмитриевич",
                new ArrayList<>(Arrays.asList(3, 4, 3, 3, 4)), 20);

        students.add(student1); //Arrays.asList(student1, ...)
        students.add(student2);
        students.add(student3);
        Scanner scanner = new Scanner(System.in);
        // Вывод исходного списка
        System.out.println("Исходный список:");
        for (Student student : students) {
            System.out.println(student);
        }

        // Имея список студентов (Student), найти и вывести имена всех студентов,
        // чей средний бал по всем предметам (Subject) выше заданного
        System.out.println("Задайте балл: ");
        double avgScore = scanner.nextDouble();
        System.out.println("Список студентов:");
        for (Student student : students) {
            if (student.getAvgScore() > avgScore) {
                System.out.println(student);
            }
        }

        // Имея список студентов (Student), вывести фамилии студентов, старше заданного возраста.
        System.out.println("Задайте возраст: ");
        int age = scanner.nextInt();
        System.out.println("Список студентов:");
        for (Student student : students) {
            if (student.getAge() > age) {
                System.out.println(student);
            }
        }

        // Имея список студентов (Student), вывести фамилии студентов, начинающиеся на заданную букву.
        // Результат должен быть отсортирован по возрастанию по имени, и по убыванию по возрасту студента.
        System.out.println("Задайте букву: ");
        String s = scanner.next();
        ArrayList<Student> studentsName = new ArrayList<>();
        for (Student student : students) {
            if (student.getFio().charAt(0) == s.charAt(0)) { //.startsWith()
                studentsName.add(student);
            }
        }
        System.out.println("Список студентов (по возрастанию по имени):");
        Collections.sort(studentsName, (s1, s2) -> {    //Используй для сравнения библиотеку Comparator

                                                        /*Плюс, у тебя реализована сортировка только по ФИО. Нужно и по возрасту тоже
                                                        То есть не нужно делать сортировку отдельно по ФИО и отдельно по возрасту. Должна быть сортировка по имени и затем по возрасту.
                                                        Ваня 22
                                                        Коля 33
                                                        Саша 44
                                                        Ваня 55
                                                        Саша 66
                                                        ->
                                                        Ваня 55
                                                        Ваня 22
                                                        Коля 33
                                                        Саша 66
                                                        Саша 44 */

            return s1.getFio().compareTo(s2.getFio());
        });
        for (Student student : studentsName) {
            System.out.println(student);
        }

        System.out.println("Список студентов (по убыванию по возрасту):");
        Collections.sort(studentsName, (s1, s2) -> {
            return s2.getAge() - s1.getAge();
        });
        for (Student student : studentsName) {
            System.out.println(student);
        }

        //Реализовать метод, принимающий на входе некоторую строку, и возвращающий список слов,
        // которые использовались в данной сроке без повторений в лексикографическом порядке
        System.out.println("Введите строку: ");
        scanner.nextLine();
        String str = scanner.nextLine();
        String[] words = str.split(" "); //А как же знаки препинания? Переносы строк и другие символы?
        Set<String> wordsSet = new LinkedHashSet<>(Arrays.asList(words)); //Слова тут будут отсортированы так, как удобно компилятору. Нужно сохранить порядок слов. Tree...
        System.out.println("Результат:");
        for (String word : wordsSet) {
            System.out.println(word);
        }

        //Реализовать метод, принимающий на входе некоторую строку, и возвращающий статистику слов,
        // используемых в данной строке в виде Map<String, Integer>;
        System.out.println("Введите строку: ");
        String str2 = scanner.nextLine();
        String[] wordsArray = str.split(" "); //Дублирование кода
        Map<String, Integer> map = new HashMap<String, Integer>();
        System.out.println("Результат:");
        for (String word : wordsArray) {
            Integer currentCount = 0;
            if (map.containsKey(word))
                currentCount = map.get(word);

            map.put(word, currentCount + 1); //94 + 96 в одну строку сделай, пожалуйста. Нет необходимости объявлять переменную.
        }

        for (Map.Entry<String, Integer> item : map.entrySet()) {
            System.out.println(item.getKey() + " " + item.getValue());
        }
    }
}
