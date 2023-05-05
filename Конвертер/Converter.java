package Homework_8_and_9.Converter;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;

public class Converter {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Неверное количество аргументов. " +
                    "Ожидалось 4: -путь_к_иcходному_файлу -путь_к_выходному_файлу -кодировка_исходного_файла -кодировка_выходного_файла");
            return;
        }

        String filename = args[0];
        String filenameOut = args[1];
        String fileCharset = args[2];
        String fileCharsetOut = args[3];

        try {
            Path file = Paths.get(filename);
            List<String> lines = Files.readAllLines(file, Charset.forName(fileCharset));

            Path fileOut = Paths.get(filenameOut);
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileOut.toUri()),
                    Charset.forName(fileCharsetOut), StandardOpenOption.CREATE_NEW)) {
                for (String line : lines) {
                    writer.append(line);
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getStackTrace());
        }
    }
}