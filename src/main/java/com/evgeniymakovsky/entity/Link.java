package com.evgeniymakovsky.entity;

import javax.persistence.*;

@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "link_id",
            unique = true, nullable = false)
    private Integer link_id;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Column(name = "shorted", nullable = false)
    private String shorted;

    @Column(name = "original", nullable = false)
    private String original;

    @Column(name = "invocations", nullable = false)
    private Integer invocations;

    public Integer getLink_id() {
        return link_id;
    }

    public void setLink_id(Integer link_id) {
        this.link_id = link_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShorted() {
        return shorted;
    }

    public void setShorted(String shorted) {
        this.shorted = shorted;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public Integer getInvocations() {
        return invocations;
    }

    public void setInvocations(Integer invocations) {
        this.invocations = invocations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (link_id != null ? !link_id.equals(link.link_id) : link.link_id != null) return false;
        if (shorted != null ? !shorted.equals(link.shorted) : link.shorted != null) return false;
        return original != null ? original.equals(link.original) : link.original == null;
    }

    @Override
    public int hashCode() {
        int result = link_id != null ? link_id.hashCode() : 0;
        result = 31 * result + (shorted != null ? shorted.hashCode() : 0);
        result = 31 * result + (original != null ? original.hashCode() : 0);
        return result;
    }
}
