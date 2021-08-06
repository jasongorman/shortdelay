package com.codemanship.shortdelay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Program {

    private static boolean running = true;
    private static final BlockingQueue<Feature> backlog = new LinkedBlockingQueue<>();
    private static final List<Feature> completed = new ArrayList<>();

    public static void main(String[] args) {

        Thread dev = new Thread(() -> {
            while (running) {
                sleep(13);
                if (!backlog.isEmpty()) {
                    Feature feature = backlog.remove();
                    feature.complete();
                    completed.add(feature);
                    System.out.println("Feature done");
                }
            }
        });

        dev.start();

        for(int i = 0; i < 100; i++){
            sleep(10);
            backlog.add(new Feature());
        }

        running = false;

        long totalLeadTime = completed.stream()
                .mapToLong(f -> f.getLeadTime())
                .reduce(0, (total, leadTime) -> total + leadTime);

        double average = totalLeadTime / completed.size();

        System.out.println("The average feature lead time was " + average + " ms");
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
