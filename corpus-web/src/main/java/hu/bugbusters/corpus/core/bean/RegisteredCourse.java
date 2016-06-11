package hu.bugbusters.corpus.core.bean;

import hu.bugbusters.corpus.core.bean.join.InboxId;
import hu.bugbusters.corpus.core.bean.join.RegisteredCourseId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@AssociationOverrides({
        @AssociationOverride(name = "pk.registeredUser2",
                joinColumns = @JoinColumn(name = "r_id")),
        @AssociationOverride(name = "pk.course",
                joinColumns = @JoinColumn(name = "c_id")) })
public class RegisteredCourse implements java.io.Serializable {
    @Getter
    @Setter
    @EmbeddedId
    private RegisteredCourseId pk = new RegisteredCourseId();

    @Getter
    @Setter
    private int mark;

    @Transient
    public RegisteredUser getRegisteredUser() {
        return getPk().getRegisteredUser();
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        getPk().setRegisteredUser(registeredUser);
    }

    @Transient
    public Course getCourse() {
        return getPk().getCourse();
    }

    public void setCourse(Course course) {
        getPk().setCourse(course);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Inbox that = (Inbox) o;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;

        return true;
    }

    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}