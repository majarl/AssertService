package eu.izzted.media_converter.endpoints;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConvertStatusMessages {

    private static ConvertStatusMessages convertStatusMessages;

    private List<ConvertMsg> messages = new CopyOnWriteArrayList<>();

    private ConvertStatusMessages() { }


    public static synchronized ConvertStatusMessages instance() {
        if (convertStatusMessages == null) {
            convertStatusMessages = new ConvertStatusMessages();
        }
        return convertStatusMessages;
    }


    public void addMessage(ConvertMsg msg) {
        this.messages.add(msg);
    }


    public List<ConvertMsg> getMessages(String withJobId) {
        List<ConvertMsg> msgListWithJobId = this.messages.stream()
                .filter(msg -> msg.jobId().equals(withJobId))
                .toList();
        this.messages.removeAll(msgListWithJobId);
        return msgListWithJobId;
    }


    public List<ConvertMsg> getMessagesTimeSorted(String withJobId) {
        List<ConvertMsg> msgListWithJobId = this.getMessages(withJobId);
        return msgListWithJobId.stream()
                .sorted(Comparator.comparingLong(ConvertMsg::timestamp).reversed())
                .toList();
    }


    public List<ConvertMsg> cleanUp(long limitMs, long now) {
        List<ConvertMsg> removed = new ArrayList<>();
        for (ConvertMsg cm : this.messages) {
            long diff = now - limitMs;
            if (diff > cm.timestamp()) {
                this.messages.remove(cm);
                removed.add(cm);
            }
        }
        return removed;
    }


    public List<ConvertMsg> peekMessages() {
        return messages;
    }

}
