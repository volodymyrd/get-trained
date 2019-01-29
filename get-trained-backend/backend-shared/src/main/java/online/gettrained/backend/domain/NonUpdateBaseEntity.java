package online.gettrained.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class NonUpdateBaseEntity implements Serializable {

    private static final long serialVersionUID = -1782599959487855141L;

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JsonIgnore
    @Column(name = "DATE_CREATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate = new Date();

    @PrePersist
    protected void onCreate() {
        dateCreate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NonUpdateBaseEntity)) return false;
        NonUpdateBaseEntity that = (NonUpdateBaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
