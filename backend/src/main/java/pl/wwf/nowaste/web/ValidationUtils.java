package pl.wwf.nowaste.web;

public class ValidationUtils {

    public static void checkNotNull(final Object object, final String message) {
        check(object != null, message);
    }


    public static void check(final boolean condition, final String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

}
