package Homework;

import java.util.*;

public class Main {
    static ArrayList<Student> STUDENTS = new ArrayList<>();
    public static void main(String[] args) {   //Так, простыню эту давай бить на функции/классы/методы.
                                                //Так точно не годится.
        STUDENTS.addAll(Arrays.asList(
        new Student("Иванов Иван Иванович", new ArrayList<>(Arrays.asList(5, 5, 4, 4, 4)), 20),
        new Student("Александров Александр Александрович", new ArrayList<>(Arrays.asList(3, 4, 3, 3, 4)), 22),
        new Student("Аброров Артур Дмитриевич", new ArrayList<>(Arrays.asList(3, 4, 3, 3, 4)), 20)
        ));
        // Вывод исходного списка
        System.out.println("Исходный список:");
        for (Student student : STUDENTS) {
            System.out.println(student.print());
        }

        // Имея список студентов (Student), найти и вывести имена всех студентов,
        // чей средний бал по всем предметам (Subject) выше заданного
        averageScoreAbove();


        // Имея список студентов (Student), вывести фамилии студентов, старше заданного возраста.
        aboveAge();


        // Имея список студентов (Student), вывести фамилии студентов, начинающиеся на заданную букву.
        // Результат должен быть отсортирован по возрастанию по имени, и по убыванию по возрасту студента.
        surnameStartWith();


        //Реализовать метод, принимающий на входе некоторую строку, и возвращающий список слов,
        // которые использовались в данной сроке без повторений в лексикографическом порядке
       wordsInLine();

        //Реализовать метод, принимающий на входе некоторую строку, и возвращающий статистику слов,
        // используемых в данной строке в виде Map<String, Integer>;
        wordStatistic();
    }
    //Реализовать метод, принимающий на входе некоторую строку, и возвращающий статистику слов,
    // используемых в данной строке в виде Map<String, Integer>;
    private static void wordStatistic() {
        System.out.println("Введите строку: ");
        String str = new Scanner(System.in).nextLine();

        Map<String, Integer> frequencyMap = getWordFrequency(str);
        System.out.println(frequencyMap);
    }

    public static Map<String, Integer> getWordFrequency(String input) {
        String[] words = input.split("\\s+");
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }
        return frequencyMap;
    }

    //Реализовать метод, принимающий на входе некоторую строку, и возвращающий список слов,
    // которые использовались в данной сроке без повторений в лексикографическом порядке
    private static void wordsInLine() {
        System.out.println("Введите строку: ");
        String str = new Scanner(System.in).nextLine();

        List<String> uniqueWords = getUniqueWords(str);
        System.out.println(uniqueWords);

    }

    public static List<String> getUniqueWords(String input) {
        String[] words = input.split("[^а-яА-Я]");// всё кроме букв
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(words));
        List<String> sortedWords = new ArrayList<>(uniqueWords);
        Collections.sort(sortedWords);
        return sortedWords;
    }

    // Имея список студентов (Student), вывести фамилии студентов, начинающиеся на заданную букву.
    // Результат должен быть отсортирован по возрастанию по имени, и по убыванию по возрасту студента.
    private static void surnameStartWith() {

        System.out.println("Задайте букву: ");
        String s = new Scanner(System.in).next();


        System.out.println("Список студентов (по возрастанию по имени):");
        List<Student> filteredStudents = STUDENTS.stream()
                .filter(student -> student.getFio().charAt(0) == s.charAt(0))
            .sorted(Comparator.comparing(Student::getFio, Comparator.nullsFirst(String::compareTo))
                .thenComparing(Student::getAge, Comparator.nullsFirst(String::compareTo)).reversed()).collect(Collectors.toList());
//                .sorted(Comparator.comparing(Student::getFio))
//                .sorted(Comparator.comparing(Student::getAge).reversed())
                .toList();
        for (Student student : filteredStudents) {
            System.out.println(student.print());
        }
    }

    private static void aboveAge() {
        System.out.println("Задайте возраст: ");
        int age = new Scanner(System.in).nextInt();
        System.out.println("Список студентов:");
        for (Student student : STUDENTS) {
            if (student.getAge() > age) {
                System.out.println(student.print());
            }
        }
    }

    // Имея список студентов (Student), найти и вывести имена всех студентов,
    // чей средний бал по всем предметам (Subject) выше заданного
    private static void averageScoreAbove() {
        System.out.println("Задайте балл: ");
        double avgScore =  new Scanner(System.in).nextDouble();
        System.out.println("Список студентов:");
        for (Student student : STUDENTS) {
            if (student.getAvgScore() > avgScore) {
                System.out.println(student.print());
            }
        }
    }
    class NameComparator implements Comparator<Student> {

        // override the compare() method
        public int compare(Student s1, Student s2)
        {
            return s1.getFio().compareTo(s2.getFio());
        }
    }

}
