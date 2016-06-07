package hu.bugbusters.corpus.core.factories;

import hu.bugbusters.corpus.core.bean.Message;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;

import java.sql.Timestamp;

public class MessageFactory {
    public static Message createMessage(Long id, String subject, String message){
        Message msg = new Message();
        msg.setCreatorId(id);
        msg.setSubject(subject);
        msg.setMessage(message);
        msg.setTime(new Timestamp(System.currentTimeMillis()));
        return msg;
    }

    public static Message createAndSaveMessage(Long id, String subject, String message){
        Message msg = createMessage(id, subject, message);
        DaoImpl.getInstance().saveEntity(msg);
        return msg;
    }
}
