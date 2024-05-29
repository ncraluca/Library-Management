package entities.author;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Author {
    private Integer id;
    private String firstName;
    private String lastName;

    public Author(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Author otherAuthor = (Author) obj;
        return Objects.equals(firstName, otherAuthor.firstName) && Objects.equals(lastName, otherAuthor.lastName);
    }

}

