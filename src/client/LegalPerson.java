package client;

public class LegalPerson extends Client{
    private CompanyType company;
    private double socialCapital;


    public LegalPerson(int id, String name, String address, int participationNo, int wonAuctionsNo) {
        super(id, name, address, participationNo, wonAuctionsNo);
    }

    public CompanyType getCompany() {
        return company;
    }

    public void setCompanyType(String companyType) {
        if(companyType.equals("SRL")){
            this.company = CompanyType.SRL;
        }
        else {
            this.company = CompanyType.SA;
        }
    }

    public double getSocialCapital() {
        return socialCapital;
    }

    public void setSocialCapital(double socialCapital) {
        this.socialCapital = socialCapital;
    }

}
