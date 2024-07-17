import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UserDataProcessor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.println("Введите данные (Фамилия Имя Отчество датарождения номертелефона пол), разделенные пробелом:");
        String input = scanner.nextLine();
        
        try {
            processInput(input);
        } catch (InvalidDataFormatException e) {
            System.out.println("Ошибка формата данных: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void processInput(String input) throws InvalidDataFormatException, IOException {
        String[] data = input.split(" ");
        
        if (data.length != 6) {
            throw new InvalidDataFormatException("Неверное количество данных. Ожидалось 6 элементов, получено: " + data.length);
        }

        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        String birthDate = data[3];
        String phoneNumber = data[4];
        String gender = data[5];

        if (!birthDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            throw new InvalidDataFormatException("Дата рождения должна быть в формате dd.mm.yyyy");
        }
        
        if (!phoneNumber.matches("\\d+")) {
            throw new InvalidDataFormatException("Номер телефона должен быть целым беззнаковым числом");
        }

        if (!gender.matches("[fm]")) {
            throw new InvalidDataFormatException("Пол должен быть обозначен символом 'f' или 'm'");
        }

        String userData = String.join(" ", lastName, firstName, middleName, birthDate, phoneNumber, gender);
        writeToFile(lastName, userData);
    }

    private static void writeToFile(String fileName, String data) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName + ".txt", true), StandardCharsets.UTF_8))) {
            writer.write(data + "\n");
        }
    }

    static class InvalidDataFormatException extends Exception {
        public InvalidDataFormatException(String message) {
            super(message);
        }
    }
}
