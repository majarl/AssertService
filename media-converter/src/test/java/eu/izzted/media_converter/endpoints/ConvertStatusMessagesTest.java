package eu.izzted.media_converter.endpoints;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConvertStatusMessagesTest {

    @Test
    void addingAndGettingMessages() {
        ConvertStatusMessages msgStore = ConvertStatusMessages.instance();
        msgStore.addMessage(new ConvertMsg("999", 1000, "Test", 1000, ""));
        msgStore.addMessage(new ConvertMsg("999", 2000, "Test", 1000, ""));
        msgStore.addMessage(new ConvertMsg("888", 1000, "Test", 1000, ""));

        List<ConvertMsg> lstMsg = msgStore.getMessages("999");
        assertEquals(2, lstMsg.size());
        assertEquals("999", lstMsg.get(0).jobId());
        assertEquals("999", lstMsg.get(1).jobId());

        List<ConvertMsg> afterLstMsg = msgStore.getMessages("999");
        assertEquals(0, afterLstMsg.size());
    }

    @Test
    void addingAndGettingSorted() {
        ConvertStatusMessages msgStore = ConvertStatusMessages.instance();
        msgStore.addMessage(new ConvertMsg("555", 9000, "Test", 1000, ""));
        msgStore.addMessage(new ConvertMsg("555", 1, "Test", 1000, ""));
        msgStore.addMessage(new ConvertMsg("555", 8000, "Test", 1000, ""));
        msgStore.addMessage(new ConvertMsg("555", 18_000, "Test", 1000, ""));
        msgStore.addMessage(new ConvertMsg("777", 9000, "Test", 1000, ""));
        msgStore.addMessage(new ConvertMsg("555", 9000, "Test", 1000, ""));

        List<ConvertMsg> sortedMsg = msgStore.getMessagesTimeSorted("555");
        for (int i = 0; i < sortedMsg.size() - 1; i++) {
            assertTrue(
                    sortedMsg.get(i).timestamp() >= sortedMsg.get(i + 1).timestamp()
            );
        }
    }

    @Test
    void removeOld() {
        ConvertStatusMessages msgStore = ConvertStatusMessages.instance();
        long day = 1000L * 60L * 60L * 24L;
        long now = System.currentTimeMillis();
        long olderThanThisToRemove = 1 * day;

        msgStore.addMessage(new ConvertMsg("Old", now - 2*day, "Test", 1000, ""));
        msgStore.addMessage(new ConvertMsg("Really old", now - 10*day, "Test", 1000, ""));
        msgStore.addMessage(new ConvertMsg("Extremely young", now - 1L, "Test", 1000, ""));
        msgStore.addMessage(new ConvertMsg("Young", now - day/2, "Test", 1000, ""));

        List<ConvertMsg> removed = msgStore.cleanUp(olderThanThisToRemove, now);

        assertEquals(2, msgStore.peekMessages().size());
        assertEquals(2, removed.size());
        removed.forEach(m -> {
            assertTrue(m.timestamp() < now);
            assertTrue(m.timestamp() < (now - olderThanThisToRemove));
        });

        msgStore.peekMessages().forEach(m -> {
            assertTrue(m.timestamp() > (now - olderThanThisToRemove));
        });
    }
}