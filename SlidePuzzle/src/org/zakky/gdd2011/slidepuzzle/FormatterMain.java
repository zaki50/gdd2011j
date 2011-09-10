
package org.zakky.gdd2011.slidepuzzle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FormatterMain {

    public static void main(String[] args) throws Exception {
        final List<String> answers;
        final int leftLimit;
        final int rightLimit;
        final int upLimit;
        final int downLimit;

        final String input = (args.length == 0) ? "./answers/a" : args[0];
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
                input), "iso8859-1"));
        try {
            final String[] limits = reader.readLine().split(" ");
            leftLimit = Integer.parseInt(limits[0]);
            rightLimit = Integer.parseInt(limits[1]);
            upLimit = Integer.parseInt(limits[2]);
            downLimit = Integer.parseInt(limits[3]);
            final int questionsCount = Integer.parseInt(reader.readLine());

            answers = new ArrayList<String>(questionsCount);
            for (int i = 0; i < questionsCount; i++) {
                answers.add(null);
            }

            for (int i = 0; i < questionsCount; i++) {
                final String line = reader.readLine();
                if (line == null) {
                    continue;
                }
                final String[] split = line.split(":");
                if (split.length < 2) {
                    continue;
                }
                final String answer = split[1];
                if (answer.trim().isEmpty()) {
                    continue;
                }

                // 問題番号(1-base)
                final int questionNumber = getQuestionNumber(split[0]);
                answers.set(questionNumber - 1, answer);
            }
        } finally {
            reader.close();
        }

        // 制限を超えていないことを確認
        int leftCount = 0;
        int rightCount = 0;
        int upCount = 0;
        int downCount = 0;
        for (String answer : answers) {
            if (answer == null) {
                continue;
            }
            for (char c : answer.toCharArray()) {
                switch (c) {
                    case 'U':
                        upCount++;
                        break;
                    case 'D':
                        downCount++;
                        break;
                    case 'L':
                        leftCount++;
                        break;
                    case 'R':
                        rightCount++;
                        break;
                }
            }
        }

        printLimit("left", leftLimit, leftCount);
        printLimit("right", rightLimit, rightCount);
        printLimit("up", upLimit, upCount);
        printLimit("down", downLimit, downCount);
        System.out.println("↓ここから解答");

        for (String answer : answers) {
            if (answer == null) {
                System.out.println();
            } else {
                System.out.println(answer);
            }
        }
    }

    private static void printLimit(String label, int limit, int count) {
        System.out.println(label + ": " + count + "/" + limit + "(rest " + (limit - count) + ")");
    }

    static int getQuestionNumber(String header) {
        final String numStr;

        final int braseIndex = header.indexOf('(');
        if (braseIndex < 0) {
            numStr = header;
        } else {
            final int slashIndex = header.indexOf('/');
            if (slashIndex < 0) {
                numStr = header.substring(0, braseIndex);
            } else {
                numStr = header.substring(0, braseIndex).substring(slashIndex + 1);
            }
        }

        final int num = Integer.parseInt(numStr);
        return num;
    }
}
