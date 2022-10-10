package lab1;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class Call implements Callable<String> {
    File dir;
    String triggerWord;
    ExecutorService executor;

    public Call(File dir, String triggerWord, ExecutorService executor) {
        this.dir = dir;
        this.triggerWord = triggerWord;
        this.executor = executor;
    }

    @Override
    public String call(){
        File[] files = dir.listFiles();
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    executor.submit(new Call(file, triggerWord, executor));
                } else {
                    if (file.getName().endsWith(".txt")) {
                        Runnable task = new Lab1(file, triggerWord);
                        executor.submit(task);
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Wrong directory");
        }
        return Thread.currentThread().getName();
    }
}
