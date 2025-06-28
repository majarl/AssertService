package eu.izzted.media_converter.endpoints;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConvertStatusEvents {

    private static ConvertStatusEvents convertStatusEvents;

    private List<ConvertEvent> events = new CopyOnWriteArrayList<>();
    
    private ConvertStatusEvents() { }


    public static synchronized ConvertStatusEvents instance() {
        if (convertStatusEvents == null) {
            convertStatusEvents = new ConvertStatusEvents();
        }
        return convertStatusEvents;
    }


    public void addEvent(ConvertEvent msg) {
        this.events.add(msg);
    }


    public List<ConvertEvent> getEvents(String withJobId) {
        List<ConvertEvent> msgListWithJobId = this.events.stream()
                .filter(msg -> msg.jobId().equals(withJobId))
                .toList();
        this.events.removeAll(msgListWithJobId);
        return msgListWithJobId;
    }


    public List<ConvertEvent> getEventsTimeSorted(String withJobId) {
        List<ConvertEvent> msgListWithJobId = this.getEvents(withJobId);
        return msgListWithJobId.stream()
                .sorted(Comparator.comparingLong(ConvertEvent::timestamp).reversed())
                .toList();
    }


    public List<ConvertEvent> cleanUp(long limitMs, long now) {
        List<ConvertEvent> removed = new ArrayList<>();
        for (ConvertEvent cm : this.events) {
            long diff = now - limitMs;
            if (diff > cm.timestamp()) {
                this.events.remove(cm);
                removed.add(cm);
            }
        }
        return removed;
    }


    public List<ConvertEvent> peekEvents() {
        return events;
    }

}
