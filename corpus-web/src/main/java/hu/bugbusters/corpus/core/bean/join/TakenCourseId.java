package hu.bugbusters.corpus.core.bean.join;


import hu.bugbusters.corpus.core.bean.Course;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Embeddable
public class TakenCourseId implements java.io.Serializable {
    @Getter
    @Setter
    @ManyToOne
    private RegisteredUser registeredUser;

    @Getter
    @Setter
    @ManyToOne
    private Course course;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TakenCourseId that = (TakenCourseId) o;

        if (registeredUser != null ? !registeredUser.equals(that.registeredUser) : that.registeredUser != null) return false;
        if (course != null ? !course.equals(that.course) : that.course != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (registeredUser != null ? registeredUser.hashCode() : 0);
        result = 31 * result + (course != null ? course.hashCode() : 0);
        return result;
    }
}
