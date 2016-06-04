package hu.bugbusters.corpus.core.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "inbox")
@AssociationOverrides({
        @AssociationOverride(name = "pk.registereduser",
                joinColumns = @JoinColumn(name = "r_id")),
        @AssociationOverride(name = "pk.message",
                joinColumns = @JoinColumn(name = "m_id")) })
public class Inbox implements java.io.Serializable {
    @Getter
    @Setter
    @EmbeddedId
    private InboxId pk = new InboxId();

    @Getter
    @Setter
    @Column(name = "seen", nullable = false)
    private boolean seen;

    @Transient
    public RegisteredUser getRegisteredUser() {
        return getPk().getRegisteredUser();
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        getPk().setRegisteredUser(registeredUser);
    }

    @Transient
    public Message getMessage() {
        return getPk().getMessage();
    }

    public void setMessage(Message message) {
        getPk().setMessage(message);
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