package btuguides.models.binding;

import java.sql.Date;

public class ReservationBindingModel {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String kids;
    private String adults;
    private int singleRooms;
    private int doubleRooms;
    private int tripleRooms;
    private int apartments;
    private int bonusBeds;
    private Date fromDate;
    private Date toDate;
    private int days;
    private String more;
    private String type;
    private boolean isCheck;
    private boolean isCheck2;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getKids() {
        return kids;
    }

    public void setKids(String kids) {
        this.kids = kids;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public int getSingleRooms() {
        return singleRooms;
    }

    public void setSingleRooms(int singleRooms) {
        this.singleRooms = singleRooms;
    }

    public int getDoubleRooms() {
        return doubleRooms;
    }

    public void setDoubleRooms(int doubleRooms) {
        this.doubleRooms = doubleRooms;
    }

    public int getTripleRooms() {
        return tripleRooms;
    }

    public void setTripleRooms(int tripleRooms) {
        this.tripleRooms = tripleRooms;
    }

    public int getApartments() {
        return apartments;
    }

    public void setApartments(int apartments) {
        this.apartments = apartments;
    }

    public int getBonusBeds() {
        return bonusBeds;
    }

    public void setBonusBeds(int bonusBeds) {
        this.bonusBeds = bonusBeds;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isChecked() {
        return  isCheck() && isCheck2();
    }

    public boolean isCheck2() {
        return isCheck2;
    }

    public void setCheck2(boolean check2) {
        isCheck2 = check2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
