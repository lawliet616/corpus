package hu.bugbusters.corpus.core.factories;

import hu.bugbusters.corpus.core.bean.Message;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;

import java.sql.Timestamp;

public class MessageFactory {
	private static Dao dao = DaoImpl.getInstance();

	public static Message createMessage(RegisteredUser sender, String subject, String message) {
		Message msg = new Message();
		msg.setSentBy(sender);
		msg.setSubject(subject);
		msg.setMessage(message);
		msg.setTime(new Timestamp(System.currentTimeMillis()));
		return msg;
	}

	public static Message createAndSaveMessage(RegisteredUser sender, String subject, String message) {
		Message msg = createMessage(sender, subject, message);
		dao.saveEntity(msg);
		return msg;
	}
}
