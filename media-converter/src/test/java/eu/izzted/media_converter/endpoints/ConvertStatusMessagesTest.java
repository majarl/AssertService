package eu.izzted.media_converter.endpoints;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConvertStatusMessagesTest {

    @Test
    void addingAndGettingMessages() {
        ConvertStatusMessages msgStore = ConvertStatusMessages.instance();
        msgStore.addMessage(new ConvertMsg("999", 1000));
        msgStore.addMessage(new ConvertMsg("999", 2000));
        msgStore.addMessage(new ConvertMsg("888", 1000));

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
        msgStore.addMessage(new ConvertMsg("555", 9000));
        msgStore.addMessage(new ConvertMsg("555", 1));
        msgStore.addMessage(new ConvertMsg("555", 8000));
        msgStore.addMessage(new ConvertMsg("555", 18_000));
        msgStore.addMessage(new ConvertMsg("777", 9000));
        msgStore.addMessage(new ConvertMsg("555", 9000));

        List<ConvertMsg> sortedMsg = msgStore.getMessagesTimeSorted("555");
        for (int i = 0; i < sortedMsg.size() - 1; i++) {
            assertTrue(
                    sortedMsg.get(i).timestamp() >= sortedMsg.get(i + 1).timestamp()
            );
        }
    }
}