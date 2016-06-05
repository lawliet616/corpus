package hu.bugbusters.corpus.core.util;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.bean.Message;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.factories.CourseFactory;
import hu.bugbusters.corpus.core.factories.MessageFactory;
import hu.bugbusters.corpus.core.factories.UserFactory;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.password.Password;

import java.util.HashSet;
import java.util.Set;

public class DbTest {
    public static void fillDb(){
        try{
        	UserFactory factory = UserFactory.getUserFactory();
            Dao dao = new DaoImpl();

            /*
                RegisteredUser és Course létrehozása és lementése
                1 admin, 1 teacher, 2 user, 2 course
             */
            RegisteredUser admin = new RegisteredUser();
            admin.setUsername("admin");
            admin.setFullName("admin");
            admin.setRole(Role.ADMIN);
            admin.setPassword(Password.toDatabaseHash("admin"));
            admin.setEmail("admin@admin.com");
            dao.saveEntity(admin);

            RegisteredUser teacher1 = factory.createAndSaveRegisteredUser("teacher1", "teacher1@gmail.com", Role.TEACHER);
            RegisteredUser user1 = factory.createAndSaveRegisteredUser("us1", "user1@gmail.com", Role.USER);
            RegisteredUser user2 = factory.createAndSaveRegisteredUser("us2", "user2@gmail.com", Role.USER);
            
            Course course1 = CourseFactory.createAndSaveCourse("course1","001",1,"teacher1");
            Course course2 = CourseFactory.createAndSaveCourse("course2","002",2,"teacher1");

            /*
                Kapcsolatok létrehozása Userek és Course-ok között

                ezek után a két kurzus student listájában benne van a user1,user2 és teacher1
                és a userek courses listájában bent van mindkét kurzus

             */
            Set<Course> courseSet = new HashSet<>();
            courseSet.add(course1);
            courseSet.add(course2);

            Set<RegisteredUser> studentSet = new HashSet<>();
            studentSet.add(user1);
            studentSet.add(user2);
            studentSet.add(teacher1);

            user1.setCourses(courseSet);
            user2.setCourses(courseSet);
            teacher1.setCourses(courseSet);

            course1.setStudents(studentSet);
            course2.setStudents(studentSet);
            /*
                Így is lehetne ha már nem üres
                teacher1.getCourses().add(course1);
                teacher1.getCourses().add(course2);
             */

            dao.updateEntities(user1, user2, teacher1, course1, course2);

            /*
                Message létrehozása.
             */

            Message msg1 = MessageFactory.createAndSaveMessage("subject1","message1");
            Message msg2 = MessageFactory.createAndSaveMessage("subject2","message2");

            /*
                message-k kiküldése
                a teacher1 fogja mindkét levelet kiküldeni a user1 és user2nek
             */

            // tanár kiküldi
            Set<Message> sentMessages = new HashSet<>();
            sentMessages.add(msg1);
            sentMessages.add(msg2);
            teacher1.setSentMails(sentMessages);
            dao.updateEntity(teacher1);

            //userek fogadják
            Set<Inbox> recievedMessages = new HashSet<>();

            Inbox inbox = new Inbox();
            inbox.setSeen('N');
            inbox.setMessage(msg1);
            inbox.setRegisteredUser(user1);

            Inbox inbox2 = new Inbox();
            inbox2.setSeen('N');
            inbox2.setMessage(msg2);
            inbox2.setRegisteredUser(user1);

            recievedMessages.add(inbox);
            recievedMessages.add(inbox2);

            user1.setReceivedMails(recievedMessages);

            Set<Inbox> recievedMessages2 = new HashSet<>();

            Inbox inbox3 = new Inbox();
            inbox3.setSeen('N');
            inbox3.setMessage(msg1);
            inbox3.setRegisteredUser(user2);

            Inbox inbox4 = new Inbox();
            inbox4.setSeen('N');
            inbox4.setMessage(msg2);
            inbox4.setRegisteredUser(user2);

            recievedMessages2.add(inbox3);
            recievedMessages2.add(inbox4);

            user2.setReceivedMails(recievedMessages2);

            dao.saveEntities(inbox, inbox2, inbox3, inbox4);
            dao.updateEntities(user1, user2);

            System.out.println("user1 kurzusainak száma (2): " + user1.getCourses().size());
            System.out.println("user2 kurzusainak száma (2): " + user2.getCourses().size());
            System.out.println("teacher1 kurzusainak száma (2): " + teacher1.getCourses().size());

            System.out.println("user1 beérkező leveleinek száma (2): " + user1.getReceivedMails().size());
            System.out.println("user2 beérkező leveleinek (2): " + user2.getReceivedMails().size());
            System.out.println("teacher1 elküldött száma (2): " + teacher1.getSentMails().size());

            System.out.println("course1 diákjainak száma (3): " + course1.getStudents().size());
            System.out.println("course2 diákjainak száma (3): " + course2.getStudents().size());

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
