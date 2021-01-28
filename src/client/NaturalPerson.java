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

    public String toString() {
        String mystr = "";
        int id = this.getId();
        int tries = this.getParticipationNo();
        int wins = this.getWonAuctionsNo();
        mystr += "NaturalPerson Id:" + id + " Name:" + this.getName() + " Address:" + this.getAddress()
                + " NumberOfParticiptions:" + tries + " WonAuctions:" + wins + " Birthdate:" + getBirthDate();
        return mystr;
    }
}
