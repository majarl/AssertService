package eu.izzted.media_converter.endpoints;

public record ConvertMsg(
        String jobId,
        long timestamp,
        String state,
        long jobStart,
        String msg
) {
    public static ConvertMsg create(String jobId,
                                    String state,
                                    long jobStart,
                                    String msg) {
        return new ConvertMsg(
                jobId,
                System.currentTimeMillis(),
                state,
                jobStart,
                msg
        );
    }
}
