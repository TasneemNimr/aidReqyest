
public class UrgentRequest extends AidRequest {
    private String emergencyType; // إصابة، مرض حاد، نزوح مفاجئ
    private int urgencyLevel;     // 1-10

    public UrgentRequest(Family family, String[] aidTypes, int[] quantities,
                         String emergencyType, int urgencyLevel) {
        super(family, aidTypes, quantities);
        this.emergencyType = emergencyType;
        this.urgencyLevel = urgencyLevel;
        this.setStatus("عاجل - قيد المراجعة");
    }

    @Override
    public int getPriorityScore() {
        // Override: urgent requests get higher score
        return super.getPriorityScore() + (urgencyLevel * 10);
    }

    @Override
    public String getSummary() {
        return "🚨 " + super.getSummary() + " | طارئ: " + emergencyType + " (مستوى " + urgencyLevel + ")";
    }

    public String getEmergencyType() { return emergencyType; }
    public int getUrgencyLevel() { return urgencyLevel; }
}
