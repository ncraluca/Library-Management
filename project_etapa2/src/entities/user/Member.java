package entities.user;

import java.util.Date;

public class Member extends User{
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;

    public Member(Integer id, String firstName, String lastName, String email, String phoneNumber, Date birthDate, Date subscriptionStartDate, Date subscriptionEndDate) {
        super(id, firstName, lastName, email, phoneNumber, birthDate);
        this.subscriptionStartDate = subscriptionStartDate;
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public Date getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(Date subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public Date getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(Date subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
