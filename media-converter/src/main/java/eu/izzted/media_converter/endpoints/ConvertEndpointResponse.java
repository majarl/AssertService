package eu.izzted.media_converter.endpoints;

import java.util.Date;

public record ConvertEndpointResponse(
    String jobId,
    String msg,
    long timestamp,
    Date time,
    long duration
) {
    public static ConvertEndpointResponse create(String jobId, String msg) {
        long timestamp = System.currentTimeMillis();
        Date time = new Date(timestamp);
        return new ConvertEndpointResponse(jobId, msg, timestamp, time, 0);
    }
}
