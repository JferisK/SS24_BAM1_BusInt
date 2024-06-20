package de.jakob_kroemer.domain;

public class Customer {

    private String firstName;
    private String lastName;
    private String ssn;
    private String email;
    private Address address;

    public Customer(String firstName, String lastName, String ssn, String email, Address address) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.ssn       = ssn;
        this.email     = email;
        this.address   = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ssn='" + ssn + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }
}
