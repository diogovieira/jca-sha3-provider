package com.uminho;

import junit.framework.TestCase;

import java.security.MessageDigest;
import java.security.Security;

import static org.junit.Assert.assertArrayEquals;

public class SHA3Test extends TestCase {

    MessageDigest messageDigest;

    @Override
    public void setUp() throws Exception {
        Security.addProvider(new PIProvider());
    }

    public void test224Bits() throws Exception {
        messageDigest = MessageDigest.getInstance("SHA3-224", "PIProvider");

        String input = "Mensagem de teste."; // 4D656E736167656D2064652074657374652E
        byte[] expected = Utilities.hexStringToByteArray("FAB2E7DD234EFCF43CFDF2CCDFBB1F947F" +
                "71C8CD70177708C6ABF595");

        messageDigest.update(input.getBytes());

        byte[] actual = messageDigest.digest();

        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);
    }

    public void test256Bits() throws Exception {
        messageDigest = MessageDigest.getInstance("SHA3-256", "PIProvider");

        String input = "Mensagem de teste."; // 4D656E736167656D2064652074657374652E
        byte[] expected = Utilities.hexStringToByteArray("D76D67AD0FBA24701F4A61F09AFB80A5E" +
                "32E2479E52352AB687A8851684884E1");

        messageDigest.update(input.getBytes());

        byte[] actual = messageDigest.digest();

        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);
    }

    public void test384Bits() throws Exception {
        messageDigest = MessageDigest.getInstance("SHA3-384", "PIProvider");

        String input = "Mensagem de teste."; // 4D656E736167656D2064652074657374652E
        byte[] expected = Utilities.hexStringToByteArray("502F64A9492D7EB15EEDDAC7C702F275" +
                "3685BDCC55835AEF8372B89FB099E1BFC2927F5F2AFCDED009C002FE1A9722EC");

        messageDigest.update(input.getBytes());

        byte[] actual = messageDigest.digest();

        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);
    }

    public void test512Bits() throws Exception {
        messageDigest = MessageDigest.getInstance("SHA3-512", "PIProvider");

        String input = "Mensagem de teste."; // 4D656E736167656D2064652074657374652E
        byte[] expected = Utilities.hexStringToByteArray("7EDCAEC456AF765A891A4495D4CDAA9A" +
                "3294CBC2F5720F4936B04DF6777D6D5D17C6665BF63F15178F5B568F4B068F21F18F88299" +
                "8CC3DCAE7DB395E5AFC3E98");

        messageDigest.update(input.getBytes());

        byte[] actual = messageDigest.digest();

        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);
    }
}
