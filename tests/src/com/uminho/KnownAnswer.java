package com.uminho;

/**
 * This class is a container for the information for each
 * of the known answers.
 *
 * @see KeccakTest
 * @see KATUtilities
 */

public class KnownAnswer {

    /** The length of the message. */
    public final int length;

    /** The size of the digest. */
    public final int size;

    /** The message. */
    public final byte[] message;

    /** The digest of the message. */
    public final byte[] digest;

    public KnownAnswer(int length, int size, byte[] message, byte[] digest){
        this.length = length;
        this.size = size;
        this.message = message;
        this.digest = digest;
    }

    public String toString() {
        return "Length: "  + length +
             ", Size: "    + size +
             ", Message: " + Utilities.bytesToHex(message) +
             ", Digest: "  + Utilities.bytesToHex(digest);
    }

}