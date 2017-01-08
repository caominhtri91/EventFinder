package vn.edu.poly.eventfinder.entities;

import java.util.List;

public class Group {
    private int id;
    private String name;
    private long groupDate;
    private long createdDate;
    private String groupDescription;
    private User byUser;
    private List<User> addedUser;

    public Group() {
    }

    public Group(int id, String name, long groupDate, long createdDate, String groupDescription, User byUser, List<User> addedUser) {
        this.id = id;
        this.name = name;
        this.groupDate = groupDate;
        this.createdDate = createdDate;
        this.groupDescription = groupDescription;
        this.byUser = byUser;
        this.addedUser = addedUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(int eventDate) {
        this.groupDate = eventDate;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }

    public User getByUser() {
        return byUser;
    }

    public void setByUser(User byUser) {
        this.byUser = byUser;
    }

    public List<User> getAddedUser() {
        return addedUser;
    }

    public void setAddedUser(List<User> addedUser) {
        this.addedUser = addedUser;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
}
