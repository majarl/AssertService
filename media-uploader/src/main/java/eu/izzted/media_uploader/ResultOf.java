package eu.izzted.media_uploader;

public record ResultOf<T>(
        T value,
        boolean ok,
        String msg,
        Exception e
) {
    public static <T> ResultOf<T> success(T val) {
        return new ResultOf<>(val, true, null, null);
    }

    public static <T> ResultOf<T> fail(String msg, Exception e) {
        return new ResultOf<>(null, false, msg, e);
    }

    public static <T> ResultOf<T> fail(String msg) {
        return new ResultOf<>(null, false, msg, null);
    }

    public boolean isOk() {
        return this.ok;
    }

    public boolean failed() {
        return !this.ok;
    }

}
