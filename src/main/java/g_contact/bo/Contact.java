package g_contact.bo;

public class Contact {
    private int id;
    private String nom;
    private String prenom;
    private String Telephone1;
    private String Telephone2;
    private String Adresse;
    private String Email_personnel;
    private String professional;
    private String genre;


    public Contact(){}
    public Contact(int id, String nom, String prenom, String telephone1, String telephone2, String adresse, String personnel, String professional, String genre) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        Telephone1 = telephone1;
        Telephone2 = telephone2;
        Adresse = adresse;
        this.Email_personnel = personnel;
        this.professional = professional;
        this.genre = genre;
    }


    public Contact(String nom, String prenom, String telephone1, String telephone2, String adresse, String personnel, String professional, String genre) {
        this.nom = nom;
        this.prenom = prenom;
        Telephone1 = telephone1;
        Telephone2 = telephone2;
        Adresse = adresse;
        this.Email_personnel = personnel;
        this.professional = professional;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone1() {
        return Telephone1;
    }

    public void setTelephone1(String telephone1) {
        Telephone1 = telephone1;
    }

    public String getTelephone2() {
        return Telephone2;
    }

    public void setTelephone2(String telephone2) {
        Telephone2 = telephone2;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getPersonnel() {
        return Email_personnel;
    }

    public void setPersonnel(String personnel) {
        this.Email_personnel = personnel;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setAll(String nom, String prenom, String telephone1, String telephone2, String adresse, String personnel, String professional, String genre){
        this.nom = nom;
        this.prenom = prenom ;
        this.Telephone1 = telephone1;
        this.Telephone2 = telephone2;
        this.Adresse = adresse;
        this.Email_personnel = personnel;
        this.professional = professional;
        this.genre = genre;
    }
}
