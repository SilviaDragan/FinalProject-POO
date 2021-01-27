package client;

public class NaturalPerson extends Client{
    private String birthDate;

    public NaturalPerson(int id, String name, String address, int participationNo, int wonAuctions) {
        super(id, name, address, participationNo, wonAuctions);
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


}
