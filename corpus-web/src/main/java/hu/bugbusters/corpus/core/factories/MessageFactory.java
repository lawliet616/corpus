package hu.bugbusters.corpus.core.factories;

import hu.bugbusters.corpus.core.bean.Message;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;

public class MessageFactory {
    private static MessageFactory ourInstance = new MessageFactory();
    private Dao dao;

    public static MessageFactory getInstance() {
        return ourInstance;
    }

    private MessageFactory() {
        this.dao = new DaoImpl();
    }

    public static Message createMessage(String subject, String message){
        Message msg = new Message();
        msg.setSubject(subject);
        msg.setMessage(message);
        return msg;
    }
}
