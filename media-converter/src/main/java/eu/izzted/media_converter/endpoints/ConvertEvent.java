package eu.izzted.media_converter.endpoints;

public record ConvertEvent(
        String jobId,
        long timestamp,
        String state,
        long jobStart,
        String msg
) {
    public static ConvertEvent create(String jobId,
                                      String state,
                                      long jobStart,
                                      String msg) {
        return new ConvertEvent(
                jobId,
                System.currentTimeMillis(),
                state,
                jobStart,
                msg
        );
    }
}
