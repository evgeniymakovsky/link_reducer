package com.evgeniymakovsky.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = IDENTITY)
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
}
