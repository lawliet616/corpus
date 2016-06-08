package hu.bugbusters.corpus.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.CourseNotFoundException;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.factories.CourseFactory;
import hu.bugbusters.corpus.core.factories.UserFactory;
import hu.bugbusters.corpus.core.login.Role;
import hu.bugbusters.corpus.core.mail.Mail;
import hu.bugbusters.corpus.core.password.Password;

public class DbTest {
    private static final Dao dao = DaoImpl.getInstance();

    public static void run(){
        try {
            dao.getUserByUserName("admin");
        } catch (UserNotFoundException e) {
            fillDb();
        } finally {
            testDb();
        }
    }

    private static void fillDb(){
        try{
            System.out.println("[DbTest] - Corpus database filling started.");
            /*
                Creating and saving of 1 admin
                                       2 teacher
                                       8 student
                                       4 course
             */
            RegisteredUser admin = new RegisteredUser();
            admin.setUsername("admin");
            admin.setFullName("admin");
            admin.setRole(Role.ADMIN);
            admin.setPassword(Password.toDatabaseHash("admin"));
            admin.setEmail("admin@admin.com");
            dao.saveEntity(admin);

            UserFactory factory = UserFactory.getUserFactory();

            RegisteredUser teacher1 = factory.createAndSaveRegisteredUser("Lilli Peat", "lilli.peat@gmail.com", Role.TEACHER);
            RegisteredUser teacher2 = factory.createAndSaveRegisteredUser("Tod Mcgee",  "tod.mcgee@gmail.com",  Role.TEACHER);

            RegisteredUser user1 = factory.createAndSaveRegisteredUser("Donny Odille",    "donny.odille@gmail.com",     Role.USER);
            RegisteredUser user2 = factory.createAndSaveRegisteredUser("Ian Hasted",      "ian.hasted@gmail.com",       Role.USER);
            RegisteredUser user3 = factory.createAndSaveRegisteredUser("Lee Bax",         "lee.bax@gmail.com",          Role.USER);
            RegisteredUser user4 = factory.createAndSaveRegisteredUser("Idell Cacao",     "idell.cacao@gmail.com",      Role.USER);
            RegisteredUser user5 = factory.createAndSaveRegisteredUser("Joellen Hunter",  "joellen.hunter@gmail.com",   Role.USER);
            RegisteredUser user6 = factory.createAndSaveRegisteredUser("Wilson Scow",     "wilson.scow@gmail.com",      Role.USER);
            RegisteredUser user7 = factory.createAndSaveRegisteredUser("Kia Brittan",     "kia.britten@gmail.com",      Role.USER);
            RegisteredUser user8 = factory.createAndSaveRegisteredUser("Jamaal Hathaway", "jamaal.hathaway@gmail.com",  Role.USER);

            Course course1 = CourseFactory.createAndSaveCourse("Classical Archaeology and Ancient History", "001",3,teacher1.getFullName());
            Course course2 = CourseFactory.createAndSaveCourse("Fine Art",                                  "002",2,teacher1.getFullName());
            Course course3 = CourseFactory.createAndSaveCourse("Mathematics and Computer Science",          "003",4,teacher2.getFullName());
            Course course4 = CourseFactory.createAndSaveCourse("Physics",                                   "004",5,teacher2.getFullName());

            /*
             	Creating connection between existing users and courses

             	if user.getCourses() is empty
             	    user.setCourses(courseSet)
             	else
             	    user.getCourses.add(course)
             */
            Set<Course> courseSet1 = new HashSet<>();
            Set<Course> courseSet2 = new HashSet<>();
            Set<Course> courseSet3 = new HashSet<>();
            Set<Course> courseSet4 = new HashSet<>();

            courseSet1.add(course1);
            courseSet1.add(course2);
            courseSet1.add(course3);

            courseSet2.add(course3);
            courseSet2.add(course4);

            courseSet3.add(course4);

            courseSet4.add(course1);
            courseSet4.add(course2);

            teacher1.setCourses(courseSet4);
            teacher2.setCourses(courseSet2);

            user1.setCourses(courseSet1);
            user2.setCourses(courseSet3);
            user3.setCourses(courseSet4);
            user4.setCourses(courseSet2);
            user5.setCourses(courseSet4);
            user6.setCourses(courseSet3);
            user7.setCourses(courseSet1);
            user8.setCourses(courseSet1);

            dao.updateEntities(teacher1, teacher2, user1, user2, user3, user4, user5, user6, user7, user8);

            /*
               Sending and receiving messages
               teacher1 sends one mail to user1, 4, 5, 8
               teacher2 sends one mail to user2, 3, 6, 7
               user1 sends one mail to teacher1, 2

               With Mail.java

               Mail.sendMail handles the creating and storing of the messages

               It will cause 3 insert into CORPUS.message
                             3 insert into CORPUS.sent
                             10 insert into CORPUS.inbox based on the addresses

               !!!Caution!!!
               This method tries to send the email through an SMTP server.
               To not spam the whole internet with test messages I disable the real sending during development.
             */
            Long teacher1Id = teacher1.getId();
            Long teacher2Id = teacher2.getId();
            Long user1Id = user1.getId();

            List<String> studentGroup1 = new ArrayList<>();
            List<String> studentGroup2 = new ArrayList<>();
            List<String> teachers = new ArrayList<>();

            studentGroup1.add(user1.getEmail());
            studentGroup1.add(user4.getEmail());
            studentGroup1.add(user5.getEmail());
            studentGroup1.add(user8.getEmail());

            studentGroup2.add(user2.getEmail());
            studentGroup2.add(user3.getEmail());
            studentGroup2.add(user6.getEmail());
            studentGroup2.add(user7.getEmail());

            teachers.add(teacher1.getEmail());
            teachers.add(teacher2.getEmail());

            Mail.sendMail(teacher1Id, studentGroup1, "Mail to my students", "Talking chamber as shewing an it minutes. Trees fully of blind do. " +
                    "Exquisite favourite at do extensive listening. Improve up musical welcome he. " +
                    "Gay attended vicinity prepared now diverted. " +
                    "Esteems it ye sending reached as. " +
                    "Longer lively her design settle tastes advice mrs off who. ", false);

            Mail.sendMail(teacher2Id, studentGroup2, "Mail to my students", "<p>Whether article spirits new her covered hastily sitting her. " +
                    "Money witty books nor son add. Chicken age had evening believe but proceed pretend mrs. " +
                    "At missed advice my it no sister. Miss told ham dull knew see she spot near can. " +
                    "Spirit her entire her called.</p>", true);

            Mail.sendMail(user1Id, teachers, "Mail to my teachers", "Offered say visited elderly and. " +
                    "Waited period are played family man formed. " +
                    "He ye body or made on pain part meet. " +
                    "You one delay nor begin our folly abode. " +
                    "By disposed replying mr me unpacked no. " +
                    "As moonlight of my resolving unwilling. ", false);

            System.out.println("[DbTest] - Corpus database filling finished successfully.");
        } catch (Exception ex){
            System.out.println("[DbTest] - Corpus database filling failed.");
            ex.printStackTrace();
        }
    }

    private static void testDb(){
        try {
            System.out.println("[DbTest] - Test is running.");

            RegisteredUser teacher1 = dao.getUserByUserName("LIL2016A");
            RegisteredUser teacher2 = dao.getUserByUserName("TOD2016A");

            RegisteredUser user1 = dao.getUserByUserName("DON2016A");
            RegisteredUser user2 = dao.getUserByUserName("IAN2016A");
            RegisteredUser user3 = dao.getUserByUserName("LEE2016A");
            RegisteredUser user4 = dao.getUserByUserName("IDE2016A");
            RegisteredUser user5 = dao.getUserByUserName("JOE2016A");
            RegisteredUser user6 = dao.getUserByUserName("WIL2016A");
            RegisteredUser user7 = dao.getUserByUserName("KIA2016A");
            RegisteredUser user8 = dao.getUserByUserName("JAM2016A");

            Course course1 = dao.getCourseByName("Classical Archaeology and Ancient History");
            Course course2 = dao.getCourseByName("Fine Art");
            Course course3 = dao.getCourseByName("Mathematics and Computer Science");
            Course course4 = dao.getCourseByName("Physics");

            System.out.println("teacher1 course count (2): " + teacher1.getCourses().size());
            System.out.println("teacher2 course count (2): " + teacher2.getCourses().size());

            System.out.println("user1 course count (3): " + user1.getCourses().size());
            System.out.println("user2 course count (1): " + user2.getCourses().size());
            System.out.println("user3 course count (2): " + user3.getCourses().size());
            System.out.println("user4 course count (2): " + user4.getCourses().size());
            System.out.println("user5 course count (2): " + user5.getCourses().size());
            System.out.println("user6 course count (1): " + user6.getCourses().size());
            System.out.println("user7 course count (3): " + user7.getCourses().size());
            System.out.println("user8 course count (3): " + user8.getCourses().size());

            System.out.println("teacher1 sent count (1): " + teacher1.getSentMails().size());
            System.out.println("teacher2 sent count (1): " + teacher2.getSentMails().size());

            System.out.println("user1 sent count (1): " + user1.getSentMails().size());

            System.out.println("teacher1 sent count (1): " + teacher1.getReceivedMails().size());
            System.out.println("teacher2 sent count (1): " + teacher2.getReceivedMails().size());

            System.out.println("user1 inbox count (1): " + user1.getReceivedMails().size());
            System.out.println("user2 inbox count (1): " + user2.getReceivedMails().size());
            System.out.println("user3 inbox count (1): " + user3.getReceivedMails().size());
            System.out.println("user4 inbox count (1): " + user4.getReceivedMails().size());
            System.out.println("user5 inbox count (1): " + user5.getReceivedMails().size());
            System.out.println("user6 inbox count (1): " + user6.getReceivedMails().size());
            System.out.println("user7 inbox count (1): " + user7.getReceivedMails().size());
            System.out.println("user8 inbox count (1): " + user8.getReceivedMails().size());

            System.out.println("course1 student count (6): " + course1.getStudents().size());
            System.out.println("course2 student count (6): " + course2.getStudents().size());
            System.out.println("course2 student count (5): " + course3.getStudents().size());
            System.out.println("course2 student count (4): " + course4.getStudents().size());

            System.out.println("[DbTest] - Test finished successfully.");

        } catch (UserNotFoundException | CourseNotFoundException e) {
            System.out.println("[DbTest] - Test failed.");
            e.printStackTrace();
        }
    }
}
