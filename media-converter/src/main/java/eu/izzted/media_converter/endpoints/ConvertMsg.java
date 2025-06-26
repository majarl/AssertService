package eu.izzted.media_converter.endpoints;

public record ConvertMsg(
        String jobId,
        long timestamp
) { }
