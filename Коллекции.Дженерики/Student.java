package Homework;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Student {
    private String fio;

    private ArrayList<Integer> scores = new ArrayList<>();

    private int age;

    public Student(String fio, ArrayList<Integer> scores, int age) {
        this.fio = fio;
        this.scores = scores;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }

    public String getFio() {
        return fio;
    }
    public String  getName() {
        String[]FIO= fio.split(" ");
        return FIO[1];
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public double getAvgScore() {
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }

        return (double)sum/scores.size();
    }


    public String print() {
        return "ФИО: " + fio +
                ", Возраст: " + age +
                ", Средний балл: " + getAvgScore();
    }
}
