package eu.izzted.media_converter.endpoints;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConvertStatusEventsTest {

    @Test
    void addingAndGettingMessages() {
        ConvertStatusEvents msgStore = ConvertStatusEvents.instance();
        msgStore.addEvent(new ConvertEvent("999", 1000, "Test", 1000, ""));
        msgStore.addEvent(new ConvertEvent("999", 2000, "Test", 1000, ""));
        msgStore.addEvent(new ConvertEvent("888", 1000, "Test", 1000, ""));

        List<ConvertEvent> lstMsg = msgStore.getEvents("999");
        assertEquals(2, lstMsg.size());
        assertEquals("999", lstMsg.get(0).jobId());
        assertEquals("999", lstMsg.get(1).jobId());

        List<ConvertEvent> afterLstMsg = msgStore.getEvents("999");
        assertEquals(0, afterLstMsg.size());
    }

    @Test
    void addingAndGettingSorted() {
        ConvertStatusEvents msgStore = ConvertStatusEvents.instance();
        msgStore.addEvent(new ConvertEvent("555", 9000, "Test", 1000, ""));
        msgStore.addEvent(new ConvertEvent("555", 1, "Test", 1000, ""));
        msgStore.addEvent(new ConvertEvent("555", 8000, "Test", 1000, ""));
        msgStore.addEvent(new ConvertEvent("555", 18_000, "Test", 1000, ""));
        msgStore.addEvent(new ConvertEvent("777", 9000, "Test", 1000, ""));
        msgStore.addEvent(new ConvertEvent("555", 9000, "Test", 1000, ""));

        List<ConvertEvent> sortedMsg = msgStore.getEventsTimeSorted("555");
        for (int i = 0; i < sortedMsg.size() - 1; i++) {
            assertTrue(
                    sortedMsg.get(i).timestamp() >= sortedMsg.get(i + 1).timestamp()
            );
        }
    }

    @Test
    void removeOld() {
        ConvertStatusEvents msgStore = ConvertStatusEvents.instance();
        long day = 1000L * 60L * 60L * 24L;
        long now = System.currentTimeMillis();
        long olderThanThisToRemove = 1 * day;

        msgStore.addEvent(new ConvertEvent("Old", now - 2*day, "Test", 1000, ""));
        msgStore.addEvent(new ConvertEvent("Really old", now - 10*day, "Test", 1000, ""));
        msgStore.addEvent(new ConvertEvent("Extremely young", now - 1L, "Test", 1000, ""));
        msgStore.addEvent(new ConvertEvent("Young", now - day/2, "Test", 1000, ""));

        List<ConvertEvent> removed = msgStore.cleanUp(olderThanThisToRemove, now);

        assertEquals(2, msgStore.peekEvents().size());
        assertEquals(2, removed.size());
        removed.forEach(m -> {
            assertTrue(m.timestamp() < now);
            assertTrue(m.timestamp() < (now - olderThanThisToRemove));
        });

        msgStore.peekEvents().forEach(m -> {
            assertTrue(m.timestamp() > (now - olderThanThisToRemove));
        });
    }
}