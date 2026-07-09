

public class Volunteer extends Person {
    private String specialization; // طبيب، مسعف، موزع
    private boolean isAvailable;

    public Volunteer(String name, String location, String phone, String specialization) {
        super(name, location, phone);
        this.specialization = specialization;
        this.isAvailable = true;
    }

    @Override
    public String getRole() { return "متطوع"; }

    public String getSpecialization() { return specialization; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    @Override
    public String toString() {
        return super.toString() + " | " + specialization + (isAvailable ? " ✓" : " ✗");
    }
}
