package lab1;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lab1 implements Runnable {
    File file;
    String word;

    public Lab1(File file, String word) {
        this.file = file;
        this.word = word;
    }

    @Override
    public void run() {
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains(word)) {
                    try {
                        FileWriter myWriter = new FileWriter("triggers.txt",true);
                        String output = String.format("%s %s", data, file.getName());
                        myWriter.write(String.format("%s\n",output));
                        System.out.println(output);
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("Input Directory:");
        String name = reader.readLine();
        System.out.println("Input Trigger Word:");
        String triggerWord = reader.readLine();
        File directory = new File(String.format("%s/src/%s", System.getProperty("user.dir"), name));
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.submit(new Call(directory, triggerWord, executor));
    }
}