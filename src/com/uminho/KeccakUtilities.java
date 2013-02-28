package com.uminho;

/**
 * General utilities.
 *
 * @author Ana Costa PG23203 pg23203@alunos.uminho.pt
 * @author Diogo Vieira PG20682 pg20682@alunos.uminho.pt
 */

public class KeccakUtilities {

    /**
     * Source: <a href="http://stackoverflow.com/questions/9655181/">http://stackoverflow.com/questions/9655181/</a>
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

}
