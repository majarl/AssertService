package eu.izzted.media_converter.endpoints;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConvertStatusEvents {

    private static ConvertStatusEvents convertStatusEvents;

    private Map<String, CopyOnWriteArrayList<ConvertEvent>> events =
            new ConcurrentHashMap<>();

    private ConvertStatusEvents() { }


    public static synchronized ConvertStatusEvents instance() {
        if (convertStatusEvents == null) {
            convertStatusEvents = new ConvertStatusEvents();
        }
        return convertStatusEvents;
    }


    public void addEvent(ConvertEvent event) {
        if (!this.events.containsKey(event.jobId())) {
            this.events.put(event.jobId(), new CopyOnWriteArrayList<>());
        }
        this.events.get(event.jobId()).add(event);
    }


    public List<ConvertEvent> getEvents(String withJobId) {
        if (!this.events.containsKey(withJobId)) {
            return new ArrayList<>();
        }
        List<ConvertEvent> convertEvents = new ArrayList<>(this.events.get(withJobId));
        this.events.get(withJobId).clear();
        return convertEvents;
    }


    public List<ConvertEvent> getEventsTimeSorted(String withJobId) {
        List<ConvertEvent> msgListWithJobId = this.getEvents(withJobId);
        return msgListWithJobId.stream()
                .sorted(Comparator.comparingLong(ConvertEvent::timestamp).reversed())
                .toList();
    }


    public List<ConvertEvent> cleanUp(String jobId, long limitMs, long now) {
        if (!this.events.containsKey(jobId)) {
            return new ArrayList<>();
        }

        List<ConvertEvent> removed = new ArrayList<>();
        for (ConvertEvent cm : this.events.get(jobId)) {
            long diff = now - limitMs;
            if (diff > cm.timestamp()) {
                this.events.get(jobId).remove(cm);
                removed.add(cm);
            }
        }
        return removed;
    }


    public Map<String, CopyOnWriteArrayList<ConvertEvent>> peekEvents() {
        return events;
    }

}
