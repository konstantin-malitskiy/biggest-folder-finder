package org.example;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {

        String folderPath = "D:/java/Projects";
        File file = new File(folderPath);

        long start = System.currentTimeMillis();

        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        long size = pool.invoke(calculator);
        System.out.println(size + " - " +
                getHumanReadableSize(size) + " -- " +
                getSizeFromHumanReadable(
                        getHumanReadableSize(size)));
        //System.out.println(getFolderSize(file));

        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + " ms");
    }

    public static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            sum += getFolderSize(file);
        }
        return sum;
    }

    //TODO: 24B, 234Kb, 36Mb, 34Gb, 42Tb
    public static String getHumanReadableSize(long size) {
        String[] unit = {"B", "Kb", "Mb", "Gb", "Tb"};
        int i = 0;
        while (size > 1024) {
            size = size / 1024;
            i++;
        }
        unit[i] = size + unit[i];
        return unit[i];
    }

    //TODO: 24B, 234Kb, 36Mb, 34Gb, 42Tb
    //TODO 24B, 234K, 36M, 34G, 42T
    public static long getSizeFromHumanReadable(String size) {
        String[] unit = {"B", "K", "M", "G", "T"};
        long folderSize = Long.valueOf(size.replaceAll("[^0-9]", ""));
        for (int i = 0; i <= 4; i++) {
            if (size.contains(unit[i])){
                return (long) (folderSize * Math.pow(1024, i));
            }
        }
        return 0;
    }

}