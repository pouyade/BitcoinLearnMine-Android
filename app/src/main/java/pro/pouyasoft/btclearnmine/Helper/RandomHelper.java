package pro.pouyasoft.btclearnmine.Helper;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class RandomHelper {
    public static int getRandomNumber() {
        final int min = 1;
        final int max = 99999;
        return randInt(min, max);
    }

    public static int randInt(int min, int max) {

        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive

        return rand.nextInt((max - min) + 1) + min;
    }

    public static String decodeBase64(String base64str) {
        byte[] data = Base64.decode(base64str, Base64.DEFAULT);
        try {
            String text = new String(data, "UTF-8");
            return text;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
