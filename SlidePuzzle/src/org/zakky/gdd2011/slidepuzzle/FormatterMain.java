
package org.zakky.gdd2011.slidepuzzle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FormatterMain {

    public static void main(String[] args) throws Exception {
        final List<String> answers;

        final String input = (args.length == 0) ? "./answers/answer2.txt" : args[0];
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
                input), "iso8859-1"));
        try {
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
                // 問題番号(1-base)
                if (split.length < 2) {
                    continue;
                }
                final String answer = split[1];
                if (answer.trim().isEmpty()) {
                    continue;
                }

                final int questionNumber = getQuestionNumber(split[0]);
                answers.set(questionNumber - 1, answer);
            }
        } finally {
            reader.close();
        }

        for (String answer : answers) {
            if (answer == null) {
                System.out.println();
            } else {
                System.out.println(answer);
            }
        }
    }

    private static int getQuestionNumber(String header) {
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
