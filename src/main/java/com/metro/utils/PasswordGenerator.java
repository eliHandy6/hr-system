package com.metro.utils;

import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class PasswordGenerator {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION = "!@#$%&*()_+-=[]|,./?><";
    private static boolean useLower;
    private static boolean useUpper;
    private static boolean useDigits;
    private static boolean usePunctuation;
    private static Random random = new Random();

    public PasswordGenerator() {
        this(true, true, true, true);
    }

    public PasswordGenerator(boolean useLower, boolean useUpper, boolean useDigits, boolean usePunctuation) {
        this.useLower = useLower;
        this.useUpper = useUpper;
        this.useDigits = useDigits;
        this.usePunctuation = usePunctuation;
    }

    public static String generate(int length) {
        // Validate length
        if (length <= 0) {
            return "";
        }

        // Variables holding the password
        StringBuilder password = new StringBuilder(length);
        StringBuilder characterSet = new StringBuilder();

        // If the options are selected, append the character set to the password
        if (useLower) {
            characterSet.append(LOWER);
        }
        if (useUpper) {
            characterSet.append(UPPER);
        }
        if (useDigits) {
            characterSet.append(DIGITS);
        }
        if (usePunctuation) {
            characterSet.append(PUNCTUATION);
        }

        // Get the length of the character set
        int charSetLength = characterSet.length();

        // Generate the password
        for (int i = 0; i < length; i++) {
            password.append(characterSet.charAt(random.nextInt(charSetLength)));
        }

        return password.toString();
    }
}
