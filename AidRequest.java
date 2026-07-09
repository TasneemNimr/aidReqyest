
import java.util.Date;

public class AidRequest {
    private static int counter = 1;

    private int requestId;
    private Family family;
    private String[] aidTypes; // Arrays — غذاء، ماء، دواء، مأوى
    private int[] quantities;
    private String status;     // pending, approved, delivered
    private Date requestDate;
    private String notes;

    public static final String[] VALID_AID_TYPES = {"غذاء", "ماء", "دواء", "مأوى", "ملابس", "وقود"};

    public AidRequest(Family family, String[] aidTypes, int[] quantities) {
        this.requestId = counter++;
        this.family = family;
        this.aidTypes = aidTypes;
        this.quantities = quantities;
        this.status = "قيد الانتظار";
        this.requestDate = new Date();
    }

    // Polymorphism — overridable
    public int getPriorityScore() {
        return family.calculatePriority();
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("طلب #").append(requestId).append(" | ");
        sb.append(family.getName()).append(" | ");
        for (int i = 0; i < aidTypes.length; i++) {
            sb.append(aidTypes[i]).append(": ").append(quantities[i]).append("  ");
        }
        sb.append("| الحالة: ").append(status);
        return sb.toString();
    }

    // Getters & Setters
    public int getRequestId() { return requestId; }
    public Family getFamily() { return family; }
    public String[] getAidTypes() { return aidTypes; }
    public int[] getQuantities() { return quantities; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getRequestDate() { return requestDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() { return getSummary(); }
}
