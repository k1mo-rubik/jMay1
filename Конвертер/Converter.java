package Homework_8_and_9.Converter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class Converter {
    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            System.out.println("Неверное количество аргументов. " +
                    "Ожидалось 4: -путь_к_иcходному_файлу -путь_к_выходному_файлу -кодировка_исходного_файла -кодировка_выходного_файла");
            return;
        }

        String sourceFile = args[0];
        String targetFile = args[1];
        String sourceEncoding = args[2];
        String targetEncoding = args[3];

//        String sourceFile = "utf8.txt";
//        String targetFile = "w1251.txt";
//
//        String sourceEncoding = "Windows-1251";
//        String targetEncoding = "UTF-8";
        // Создадим FileInputStream для исходного файла
        FileInputStream fis = new FileInputStream(sourceFile);
        InputStreamReader isr = new InputStreamReader(fis, sourceEncoding);

        //Создадим FileOutputStream для целевого файла
        FileOutputStream fos = new FileOutputStream(targetFile);
        OutputStreamWriter osw = new OutputStreamWriter(fos, targetEncoding);

        //Считываем из входного потока и записываем в выходной поток
        char[] buffer = new char[1024];
        int length;
        // записываем в буфер пока не дочитаем файл до конца
        while ((length = isr.read(buffer)) != -1) {
            osw.write(buffer, 0, length);
        }
    // закрываем потоки
        isr.close();
        fis.close();
        osw.close();
        fos.close();

        System.out.println("Конвертация пройдена");


    }
}