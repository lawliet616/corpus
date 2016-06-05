package hu.bugbusters.corpus.core.bean.join;


import hu.bugbusters.corpus.core.bean.Message;
import hu.bugbusters.corpus.core.bean.RegisteredUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class InboxId implements java.io.Serializable {
    @Getter
    @Setter
    @ManyToOne
    private RegisteredUser registeredUser;

    @Getter
    @Setter
    @ManyToOne
    private Message message;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InboxId that = (InboxId) o;

        if (registeredUser != null ? !registeredUser.equals(that.registeredUser) : that.registeredUser != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (registeredUser != null ? registeredUser.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
