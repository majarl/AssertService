package eu.izzted.media_converter.endpoints;

import java.util.List;

public record ConvertStatusResponse(
        String jobId,
        List<ConvertEvent> msgList
) {
}
