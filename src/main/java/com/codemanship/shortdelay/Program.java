package com.codemanship.shortdelay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Program {

    private static final BlockingQueue<Feature> backlog = new LinkedBlockingQueue<>();
    private static final List<Feature> completed = new ArrayList<>();

    public static void main(String[] args) {

        long startTime = Calendar.getInstance().getTimeInMillis();

        Thread dev = new Thread(() -> {
            int backlogLength = 0;

            // dev team loop
            while (completed.size() < 100) {
                backlogLength = Math.max(backlog.size(), backlogLength);

                sleep(9);

                if (!backlog.isEmpty()) {
                    Feature feature = backlog.remove();
                    feature.complete();
                    completed.add(feature);
                    System.out.println("Feature done");
                }
            }

            long totalLeadTime = completed.stream()
                    .mapToLong(f -> f.getLeadTime())
                    .reduce(0, (total, leadTime) -> total + leadTime);

            double average = totalLeadTime / completed.size();

            long endTime = Calendar.getInstance().getTimeInMillis();

            long totalDeliveryTime = endTime - startTime;

            System.out.println("The average feature lead time was " + average + " ms");
            System.out.println("The total delivery time was " + totalDeliveryTime + " ms");
            System.out.println("The longest the backlog got was " + backlogLength + " features");
        });

        dev.start();

        // customer loop
        for(int i = 0; i < 100; i++){
            backlog.add(new Feature());
            sleep(10);
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
