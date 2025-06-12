package eu.izzted.media_uploader.store;

public class ConfigValidationResult {

    public final String msg;

    public final boolean valid;

    public ConfigValidationResult(String msg, boolean valid) {
        this.msg = msg;
        this.valid = valid;
    }


    public boolean hasFailed() {
        return !this.valid;
    }


    public static ConfigValidationResult ok() {
        return new ConfigValidationResult("", true);
    }

    public static ConfigValidationResult failed(String msg) {
        return new ConfigValidationResult(msg, false);
    }



}
