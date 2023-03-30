package btuguides.models.binding;

public class ContactBindingModel {
    private String name;
    private String secondName;
    private String familyName;
    private String email;
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public boolean isEmpty() {
        return  getName() == null && getSecondName() == null
                && getFamilyName() == null && getEmail() == null
                && getText() == null;
    }
}
