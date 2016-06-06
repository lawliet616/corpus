package hu.bugbusters.corpus.core.util;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.bean.Message;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.CourseNotFoundException;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.factories.CourseFactory;
import hu.bugbusters.corpus.core.factories.MessageFactory;
import hu.bugbusters.corpus.core.factories.UserFactory;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.mail.Mail;
import hu.bugbusters.corpus.core.password.Password;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DbTest {
    public static void fillDb(){
        try{
        	UserFactory factory = UserFactory.getUserFactory();
            Dao dao = DaoImpl.getInstance();
            /*
             * RegisteredUser create and save
             * Course create and save
             */
            RegisteredUser admin = new RegisteredUser();
            admin.setUsername("admin");
            admin.setFullName("admin");
            admin.setRole(Role.ADMIN);
            admin.setPassword(Password.toDatabaseHash("admin"));
            admin.setEmail("admin@admin.com");
            /*
             * During dao.saveEntity(admin); hibernate will insert data 
             * into CORPUS.registered_user
             */
            dao.saveEntity(admin);

            /*
             * This block will cause 3 insert into CORPUS.registered_user
             *                       2 inesrt into CORPUS.course
             */
            RegisteredUser teacher1 = factory.createAndSaveRegisteredUser("teacher1", "teacher1@gmail.com", Role.TEACHER);
            RegisteredUser user1 = factory.createAndSaveRegisteredUser("us1", "user1@gmail.com", Role.USER);
            RegisteredUser user2 = factory.createAndSaveRegisteredUser("us2", "user2@gmail.com", Role.USER);
            Course course1 = CourseFactory.createAndSaveCourse("course1","001",1,"teacher1");
            Course course2 = CourseFactory.createAndSaveCourse("course2","002",2,"teacher1");

            /*
             *	Creating connection between existing users and courses
             */
            Set<Course> courseSet = new HashSet<>();
            courseSet.add(course1);
            courseSet.add(course2);

            user1.setCourses(courseSet);
            user2.setCourses(courseSet);
            teacher1.setCourses(courseSet);
            /*
             *  Another way to fill the users' course set
             *  teacher1.getCourses().add(course1);
             *  teacher1.getCourses().add(course2);
             */
            
            /*
             * During dao.updateEntities(user1, user2, teacher1) hibernate 
             * will insert data into CORPUS.registered_user_course
             */
            dao.updateEntities(user1, user2, teacher1);

            /*
             * Sending and receiving messages
             * Teacher1 sends 2 mail to user1 and user2
             *
             * With Mail.java
             *
             * Mail.sendMail handles the creating and storing of the Message
             *
             * It will cause one insert into CORPUS.message
             *               one insert into CORPUS.sent
             *               multiple insert into CORPUS.inbox based on the addresses
             *
             * !!!Caution!!!
             * This method tries to send the email through an SMTP server.
             * To not spam the whole internet with test messages I disable the real sending during development.
             */
            Long senderId = teacher1.getId();
            List<String> addresses = new ArrayList<>();
            addresses.add(user1.getEmail());
            addresses.add(user2.getEmail());

            Mail.sendMail(senderId, addresses, "Mail", "sendMail", false);
            Mail.sendMail(senderId, addresses, "Mail", "sendMail <b>htmlText<b>", true);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void testDb(){
        Dao dao = DaoImpl.getInstance();

        try {
            RegisteredUser user1 = dao.getUserByUserName("US12016A");
            RegisteredUser user2 = dao.getUserByUserName("US22016A");
            RegisteredUser teacher1 = dao.getUserByUserName("TEA2016A");

            Course course1 = dao.getCourseByName("course1");
            Course course2 = dao.getCourseByName("course2");

            System.out.println("user1 kurzusainak száma (2): " + user1.getCourses().size());
            System.out.println("user2 kurzusainak száma (2): " + user2.getCourses().size());
            System.out.println("teacher1 kurzusainak száma (2): " + teacher1.getCourses().size());

            System.out.println("user1 beérkező leveleinek száma (2): " + user1.getReceivedMails().size());
            System.out.println("user2 beérkező leveleinek (2): " + user2.getReceivedMails().size());
            System.out.println("teacher1 elküldött száma (2): " + teacher1.getSentMails().size());

            System.out.println("course1 diákjainak száma (3): " + course1.getStudents().size());
            System.out.println("course2 diákjainak száma (3): " + course2.getStudents().size());

        } catch (UserNotFoundException | CourseNotFoundException e) {
            e.printStackTrace();
        }

    }
}
