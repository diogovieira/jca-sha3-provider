package com.uminho;

import java.nio.ByteBuffer;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigestSpi;

public class SHA3MessageDigest extends MessageDigestSpi {

    private Keccak engine;
    private int length;

    public SHA3MessageDigest(int length) {

        assert length == 224 || length == 256 ||
               length == 384 || length == 512 :
                "The digest length should be 224, 256, 384 or 512.";

        try {
            this.engine = new Keccak(length);
            this.length = length;
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void engineUpdate(byte b) {
        engine.update(b);
    }

    @Override
    protected void engineUpdate(byte[] bytes, int offset, int length) {
        engine.update(bytes, offset, length);
    }

    @Override
    protected byte[] engineDigest() {
        return engine.digest();
    }

    @Override
    protected void engineReset() {
        engine.reset();
    }

    @Override
    protected int engineGetDigestLength() {
        return this.length;
    }

    @Override
    protected void engineUpdate(ByteBuffer byteBuffer) {
        if (!byteBuffer.hasRemaining())
            return;

        byte[] bytes;
        int offset;
        int length;

        if (byteBuffer.hasArray()) {
            bytes = byteBuffer.array();
            offset = byteBuffer.arrayOffset() + byteBuffer.position();
            length = byteBuffer.limit() - byteBuffer.position();
            byteBuffer.position(byteBuffer.limit());
        } else {
            bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);
            offset = 0;
            length = bytes.length;
        }

        engineUpdate(bytes, offset, length);
    }

    @Override
    protected int engineDigest(byte[] bytes, int i, int i2) throws DigestException {
        byte[] digest = engineDigest();
        System.arraycopy(digest, 0, bytes, i, i2);
        return digest.length;
    }

}