package com.uminho;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * General utilities.
 */

public class Utilities {

    /**
     * Source: http://stackoverflow.com/questions/140131/
     *
     * Converts a string with an hexadecimal value to a byte array.
     *
     * @param s hexadecimal string to convert to byte array
     * @return byte[] converted byte array from the string
     *
     */

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * Source: http://stackoverflow.com/questions/9655181/
     *
     * Converts a byte array to an hexadecimal String.
     *
     * @param bytes byte array to convert to hexadecimal string
     * @return String converted string
     */

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Repeats a string a certain number of times.
     *
     * @param string string used for repeating
     * @param n number of times to repeat the string
     * @return String created string
     */

    public static String repeatString(String string,int n) {
        return new String(new char[n]).replace("\0", string);
    }

    /**
     * Source: http://stackoverflow.com/questions/326390/
     *
     * Reads a file to a string.
     *
     * Reads a file to a String in the path 'pathname'.
     *
     * @param pathname the path to the file to be read
     * @throws java.io.IOException
     *
     */

    public static String readFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.length() == 0 || line.charAt(0) == '#')
                    continue;
                fileContents.append(line + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

}
