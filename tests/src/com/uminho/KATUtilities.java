package com.uminho;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility methods to read KAT (Known-Answer Tests) files.
 */

public class KATUtilities {

    /**
     * Reads the content of a KAT (Known-Answer Test) to a List.
     *
     * @param filename name of the KAT file
     * @return List<KnownAnswer> list of known answers
     * @throws IOException
     */

    private static List<KnownAnswer> readKATFile(String filename, int size)
    throws IOException {
        String regex = "Len = ([0-9]+)\nMsg = ([0-9A-F]+)\nMD = ([0-9A-F]+)\n";
        String file = Utilities.readFile(filename);

        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(file);

        List<KnownAnswer> list = new ArrayList<KnownAnswer>();

        while (matcher.find()) {
            int length = Integer.parseInt(matcher.group(1));
            byte[] message = Utilities.hexStringToByteArray(matcher.group(2));
            byte[] digest = Utilities.hexStringToByteArray(matcher.group(3));

            KnownAnswer knownAnswer = new KnownAnswer(length, size, message,digest);
            list.add(knownAnswer);
        }

        return list;
    }

    /**
     * Reads the contents of the KAT files of all sizes with that formatted
     * filename to a List.
     *
     * @param filename formatted filename
     * @return List<KnownAnswer> list of known answers
     * @throws IOException
     */

    private static List<KnownAnswer> readKATFiles(String filename)
    throws IOException {
        int[] sizes = { 224, 256, 384, 512 };

        List<KnownAnswer> knownAnswers = new ArrayList<KnownAnswer>();

        for (int size : sizes) {
            knownAnswers.addAll(readKATFile(String.format(filename,size),size));
        }

        return knownAnswers;
    }

    /**
     * Returns known answers in a List.
     *
     * @return List<KnownAnswer> known answers
     */

    public static List<KnownAnswer> KATs()
    throws IOException {
        String[] filenames = {
            "ShortMsgKAT_%d.txt",
            "LongMsgKAT_%d.txt"
        };

        int[] sizes = { 224, 256, 384, 512 };

        List<KnownAnswer> knownAnswers = new ArrayList<KnownAnswer>();

        for (String filename : filenames) {
            for (int size : sizes) {
                knownAnswers.addAll(readKATFile(String.format(System.getProperty("user.dir") + "/tests/resources/" + filename,size),size));
            }
        }

        return knownAnswers;
    }

    /**
     * Returns known answers for short messages in a List.
     *
     * @return List<KnownAnswer> known answers for short messages
     */

    public static List<KnownAnswer> readShortMessageKATs()
    throws IOException {
        String filename = System.getProperty("user.dir") + "/tests/resources/ShortMsgKAT_%d.txt";

        return readKATFiles(filename);
    }

    /**
     * Returns known answers for long messages in a List.
     *
     * @return List<KnownAnswer> known answers for long messages
     */

    public static List<KnownAnswer> readLongMessageKATs()
    throws IOException {
        String filename = System.getProperty("user.dir") + "/tests/resources/LongMsgKAT_%d.txt";

        return readKATFiles(filename);
    }

}
