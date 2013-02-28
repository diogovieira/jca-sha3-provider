package com.uminho;

import java.security.Provider;

/**
 * Provider implementation.
 */

public final class PIProvider extends Provider {

    public PIProvider() {
        super("PIProvider", 1.0, "Provider that implements a SHA3 message digest.");
        super.put("MessageDigest.SHA3-224", "com.uminho.SHA3_224");
        super.put("MessageDigest.SHA3-256", "com.uminho.SHA3_256");
        super.put("MessageDigest.SHA3-384", "com.uminho.SHA3_384");
        super.put("MessageDigest.SHA3-512", "com.uminho.SHA3_512");
    }

}
