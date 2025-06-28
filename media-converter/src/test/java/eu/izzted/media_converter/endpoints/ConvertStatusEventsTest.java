package eu.izzted.media_converter.endpoints;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConvertStatusEventsTest {

    @Test
    void addingAndGettingMessages() {
        ConvertStatusEvents events = ConvertStatusEvents.instance();
        events.addEvent(new ConvertEvent("999", 1000, "Test", 1000, ""));
        events.addEvent(new ConvertEvent("999", 2000, "Test", 1000, ""));
        events.addEvent(new ConvertEvent("888", 1000, "Test", 1000, ""));

        List<ConvertEvent> lstMsg = events.getEvents("999");
        assertEquals(2, lstMsg.size());
        assertEquals("999", lstMsg.get(0).jobId());
        assertEquals("999", lstMsg.get(1).jobId());

        List<ConvertEvent> afterLstMsg = events.getEvents("999");
        assertEquals(0, afterLstMsg.size());
    }

    @Test
    void addingAndGettingSorted() {
        ConvertStatusEvents events = ConvertStatusEvents.instance();
        events.addEvent(new ConvertEvent("555", 9000, "Test", 1000, ""));
        events.addEvent(new ConvertEvent("555", 1, "Test", 1000, ""));
        events.addEvent(new ConvertEvent("555", 8000, "Test", 1000, ""));
        events.addEvent(new ConvertEvent("555", 18_000, "Test", 1000, ""));
        events.addEvent(new ConvertEvent("777", 9000, "Test", 1000, ""));
        events.addEvent(new ConvertEvent("555", 9000, "Test", 1000, ""));

        List<ConvertEvent> sortedMsg = events.getEventsTimeSorted("555");
        for (int i = 0; i < sortedMsg.size() - 1; i++) {
            assertTrue(
                    sortedMsg.get(i).timestamp() >= sortedMsg.get(i + 1).timestamp()
            );
        }
    }

    @Test
    void cleanUp() {
        ConvertStatusEvents events = ConvertStatusEvents.instance();
        long day = 1000L * 60L * 60L * 24L;
        long now = System.currentTimeMillis();
        long olderThanThisToRemove = 1 * day;

        events.addEvent(new ConvertEvent("333", now - 2*day, "Test", 1000, ""));
        events.addEvent(new ConvertEvent("333", now - 10*day, "Test", 1000, ""));
        events.addEvent(new ConvertEvent("333", now - 1L, "Test", 1000, ""));
        events.addEvent(new ConvertEvent("333", now - day/2, "Test", 1000, ""));
        events.addEvent(new ConvertEvent("333", now - olderThanThisToRemove, "Test", 1000, ""));

        List<ConvertEvent> oldies = events.cleanUp("333", olderThanThisToRemove, now);
        List<ConvertEvent> remainingEvents = events.getEvents("333");
        long timeLim = now - olderThanThisToRemove;


        oldies.forEach(anOld -> {
            assertTrue(anOld.timestamp() < timeLim);
        });

        remainingEvents.forEach(e -> {
            assertTrue(e.timestamp() >= timeLim);
        });

    }
}