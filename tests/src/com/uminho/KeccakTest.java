package com.uminho;

import com.google.common.primitives.UnsignedLongs;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

@RunWith(JUnit4.class)
public class KeccakTest extends TestCase {

    Keccak keccak;

    private Method privateMethod(String name,Class[] arguments)
    throws ClassNotFoundException, NoSuchMethodException {
        /* make the method and field accessible, because they're private */
        Method method = Class.forName("com.uminho.Keccak").getDeclaredMethod(name, arguments);
        method.setAccessible(true);
        return method;
    }

    private Field privateField(String name)
    throws ClassNotFoundException, NoSuchFieldException {
        Field field = Class.forName("com.uminho.Keccak").getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    @Before
    public void setUp() throws Exception {
        keccak = new Keccak();
    }

    @After
    public void tearDown() throws Exception {
        keccak = null;
    }

    @Test
    public void testPadding()
    throws Exception {
        byte[] testMessage = Utilities.hexStringToByteArray("BA594E0FB9EBBD30");
        String[] expectedResults8BitBlock = {
                "DD80",               // 7 bits
                "BA594E0FB9EBBD84",   // 58 bits
                "BA594E0FB9EBBD89",   // 59 bits
                "BA594E0FB9EBBD93",   // 60 bits
                "BA594E0FB9EBBDA6",   // 61 bits
                "BA594E0FB9EBBDCC",   // 62 bits
                "BA594E0FB9EBBD9880", // 63 bits
                "BA594E0FB9EBBD3081"  // 64 bits
        };
        String[] expectedResults1024BitBlock = {
                "BA594E0FB9EBBD04" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000080",   // 58 bits
                "BA594E0FB9EBBD09" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000080",   // 59 bits
                "BA594E0FB9EBBD13" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000080",   // 60 bits
                "BA594E0FB9EBBD26" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000080",   // 61 bits
                "BA594E0FB9EBBD4C" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000080",   // 62 bits
                "BA594E0FB9EBBD98" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000080",   // 63 bits
                "BA594E0FB9EBBD3001" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "000000000000000000000000000000000000000000000000000000000000000000000000" +
                        "0000000000000000000080",     // 64 bits
        };

        Method padMessage = this.privateMethod("padMessage", new Class[]{int.class, byte[].class, int.class});

        byte[] actual;

        actual = (byte[]) padMessage.invoke(keccak, 7, testMessage, 8);

        assertEquals(expectedResults8BitBlock[0],Utilities.bytesToHex(actual));

        for (int i = 58,j = 1; i < 65; i++,j++) {
            actual = (byte[]) padMessage.invoke(keccak, i, testMessage, 8);

            assertEquals(expectedResults8BitBlock[j],Utilities.bytesToHex(actual));
        }

        for (int i = 58,j = 0; i < 65; i++,j++) {
            actual = (byte[]) padMessage.invoke(keccak, i, testMessage, 1024);

            assertEquals(expectedResults1024BitBlock[j],Utilities.bytesToHex(actual));
        }

    }

    @Test
    public void testConvertMessageToTable()
    throws Exception {
        String testMessage = "BA594E0FB9EBBD13000000000000000000" +
                "00000000000000000000000000000000000000000000000" +
                "00000000000000000000000000000000000000000000000" +
                "00000000000000000000000000000000000000000000000" +
                "00000000000000000000000000000000000000000000000" +
                "00000000000000000000000000000000800000000000000" +
                "00000000000000000000000000000000000000000000000" +
                "00000000000000000000000000000000000000000000000" +
                "0000000000000000000000000000000000000";
        long[][] expected = {
                {UnsignedLongs.parseUnsignedLong("1422552237377214906"), 0, 0, UnsignedLongs.parseUnsignedLong("9223372036854775808"), 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        Method convertMessageToTable = this.privateMethod("convertMessageToTable", new Class[]{byte[].class});

        long[][] actual = (long[][]) convertMessageToTable.invoke(keccak, Utilities.hexStringToByteArray(testMessage));

        assertArrayEquals("Actual: " + Arrays.deepToString(actual) + "\nExpected: " + Arrays.deepToString(expected), expected, actual);
    }

    @Test
    public void testDigestAtOnce()
    throws Exception {
        byte[] actual = keccak.digest(60, Utilities.hexStringToByteArray("BA594E0FB9EBBD30"), 1024, 576, 1024);
        byte[] expected = Utilities.hexStringToByteArray("2E970FAF27F04818C34DF78BF79E9BE310C01CF63DFDDAAC4BFD2" +
                           "D873BCCF1A8117448A9285F2F0CFCA4BCAA8B36B0EA48425CC1B9" +
                           "207916462001DA0506768F5917D9E3E741222F9B34BE8AFBA7C2E" +
                           "0974C9A8EC5B97066D44CFA5CC806AE708151EEC19FCD694934AA" +
                           "E8AC44237582423F1E8F042813C7967D1F76635C4EAE");
        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) +"\nExpected: " + Utilities.bytesToHex(expected), expected, actual);

        actual = keccak.digest(144, Utilities.hexStringToByteArray("4D656E736167656D2064652074657374652E"), 1152, 448, 224);
        expected = Utilities.hexStringToByteArray("FAB2E7DD234EFCF43CFDF2CCDFBB1F947F71C8CD70177708C6ABF595");

        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) +"\nExpected: " + Utilities.bytesToHex(expected), expected, actual);
    }

    @Test
    public void testUpdateByte()
    throws Exception {
        byte b = (byte) 0xFF;
        byte[] expected = Utilities.hexStringToByteArray("FF0000000000000000000000000000" +
                "00000000000000000000000000000000000000000000000000000000000000000000000" +
                "00000000000000000000000000000000000000000000000000000000000000000000000" +
                "00000000000000000000000000000000000000000000000000000000000000000000000" +
                "0000000000000");
        keccak.update(b);

        byte[] queue = (byte[]) this.privateField("queue").get(keccak);

        assertArrayEquals("Actual: " + Utilities.bytesToHex(queue) + "\nExpected: " + Utilities.bytesToHex(expected), expected, queue);
    }

    @Test
    public void testUpdateByteArray()
    throws Exception {
        byte[] b = { (byte) 0xFF, (byte) 0xFF };
        byte[] expected = Utilities.hexStringToByteArray("FFFF00000000000000000000000000" +
                "00000000000000000000000000000000000000000000000000000000000000000000000" +
                "00000000000000000000000000000000000000000000000000000000000000000000000" +
                "00000000000000000000000000000000000000000000000000000000000000000000000" +
                "0000000000000");
        keccak.update(b, 0, b.length);

        byte[] queue = (byte[]) this.privateField("queue").get(keccak);

        assertArrayEquals("Actual: " + Utilities.bytesToHex(queue) + "\nExpected: " + Utilities.bytesToHex(expected), expected, queue);
    }

    @Test
    public void testKeccak()
    throws Exception {
        this.keccak = new Keccak(224);
        byte[] message = Utilities.hexStringToByteArray("BA594E0FB9EBBD30");
        byte[] expected = Utilities.hexStringToByteArray("AC2DA7D32BF8AAD3D80C7DB17AFDC5ED8848B529A180B116B5CD155E");

        for (int i = 0; i < message.length; i++) {
            this.keccak.update(message[i]);
        }

        byte[] actual = this.keccak.digest();

        assertArrayEquals("Update one byte at a time\nActual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);

        this.keccak.reset();

        this.keccak.update(message, 0, message.length / 2);
        this.keccak.update(message, message.length / 2, message.length - (message.length / 2));

        actual = this.keccak.digest();

        assertArrayEquals("Update in two blocks\nActual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);

        this.keccak.reset();

        this.keccak.update(message, 0, message.length);

        actual = this.keccak.digest();

        assertArrayEquals("Update at once\nActual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);

        this.keccak = new Keccak(256);
        expected = Utilities.hexStringToByteArray("4DE082A4B1E2843B138876A5A7991A" +
                "FCF3A17D33887614A465F3F8CB91AF3B80");

        this.keccak.update(message, 0, message.length);

        actual = this.keccak.digest();

        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);

        this.keccak = new Keccak(384);
        expected = Utilities.hexStringToByteArray("8C45D4FE4297EB7A154C06597BCF64" +
                "6BE968C7D649096FDF0C1A65261BA125071BFAF6B74656F7C925C5C480425652" +
                "5C");

        this.keccak.update(message, 0, message.length);

        actual = this.keccak.digest();

        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);

        this.keccak = new Keccak(512);
        expected = Utilities.hexStringToByteArray("E4A4CDAC19A977628C16A772CF4437" +
                "260F5BCB9544602F12C34AD47EB9F16BA97AC4F29419CFC659E55B20151CED24" +
                "07F2D3147B6A588103A866CD07AF1655E9");

        this.keccak.update(message, 0, message.length);

        actual = this.keccak.digest();

        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);

    }

    @Test
    public void testShortMessages()
            throws Exception {
        List<KnownAnswer> knownAnswers = KATUtilities.readShortMessageKATs();

        int bitrate = 0;
        int capacity = 0;

        for (KnownAnswer knownAnswer : knownAnswers) {
            switch (knownAnswer.size) {
                case 224: bitrate = 1152; capacity = 448; break;
                case 256: bitrate = 1088; capacity = 512; break;
                case 384: bitrate = 832; capacity = 768; break;
                case 512: bitrate = 576; capacity = 1024; break;
                default: break;
            }
            byte[] digest = keccak.digest(knownAnswer.length,knownAnswer.message, bitrate, capacity, knownAnswer.size);
            assertArrayEquals("Length/Size: " + knownAnswer.length + "/" + knownAnswer.size + "\nMessage: " + Utilities.bytesToHex(knownAnswer.message) + "\nActual: " + Utilities.bytesToHex(digest) + "\nExpected: " + Utilities.bytesToHex(knownAnswer.digest),digest, knownAnswer.digest);
        }
    }

    @Test
    public void testLongMessages()
            throws Exception {
        List<KnownAnswer> knownAnswers = KATUtilities.readLongMessageKATs();

        int bitrate = 0;
        int capacity = 0;

        for (KnownAnswer knownAnswer : knownAnswers) {
            switch (knownAnswer.size) {
                case 224: bitrate = 1152; capacity = 448; break;
                case 256: bitrate = 1088; capacity = 512; break;
                case 384: bitrate = 832; capacity = 768; break;
                case 512: bitrate = 576; capacity = 1024; break;
                default: break;
            }
            byte[] digest =  keccak.digest(knownAnswer.length, knownAnswer.message, bitrate, capacity, knownAnswer.size);
            assertArrayEquals("Length/Size: " + knownAnswer.length + "/" + knownAnswer.size + "\nMessage: " + Utilities.bytesToHex(knownAnswer.message) + "\nActual: " + Utilities.bytesToHex(digest) + "\nExpected: " + Utilities.bytesToHex(knownAnswer.digest), digest, knownAnswer.digest);
        }
    }

    /*
        This test is ignored because takes a very long time to finish.
     */

    @Ignore
    public void testExtremelyLongMessages()
            throws Exception {
        this.keccak = new Keccak(224);

        int repetitions = 16777216;
        byte[] text = "abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmno".getBytes("US-ASCII");
        byte[] expected = Utilities.hexStringToByteArray("C42E4AEE858E1A8AD2976896B9D23DD187F64436EE15969AFDBC68C5");

        for (int i = 0; i < repetitions; i++) {
            if (i % 1000 == 0)
                System.out.println("Working.. i = " + i);
            keccak.update(text, 0, text.length);
        }


        byte[] actual = keccak.digest();

        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);

        this.keccak = new Keccak(256);

        expected = Utilities.hexStringToByteArray("5F313C39963DCF792B5470D4ADE9F3A356A3E4021748690A958372E2B06F82A4");

        for (int i = 0; i < repetitions; i++) {
            if (i % 1000 == 0)
                System.out.println("Working.. i = " + i);
            keccak.update(text, 0, text.length);
        }

        actual = keccak.digest();

        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);

        this.keccak = new Keccak(384);

        expected = Utilities.hexStringToByteArray("9B7168B4494A80A86408E6B9DC4E5A1837C85DD8FF452ED410F2832959C08C8C0D040A892EB9A755776372D4A8732315");

        for (int i = 0; i < repetitions; i++) {
            if (i % 1000 == 0)
                System.out.println("Working.. i = " + i);
            keccak.update(text, 0, text.length);
        }

        actual = keccak.digest();

        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);

        this.keccak = new Keccak(512);

        expected = Utilities.hexStringToByteArray("3E122EDAF37398231CFACA4C7C216C9D66D5B899EC1D7AC617C40C7261906A45FC01617A021E5DA3BD8D4182695B5CB785A28237CBB167590E34718E56D8AAB8");

        for (int i = 0; i < repetitions; i++) {
            if (i % 1000 == 0)
                System.out.println("Working.. i = " + i);
            keccak.update(text, 0, text.length);
        }

        actual = keccak.digest();

        assertArrayEquals("Actual: " + Utilities.bytesToHex(actual) + "\nExpected: " + Utilities.bytesToHex(expected), expected, actual);
    }
}
