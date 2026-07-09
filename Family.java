

public class Family extends Person {
    private int memberCount;
    private boolean hasDisabled;
    private boolean hasChronicIll;

    // Constructor overloading
    public Family(String name, String location, String phone, int memberCount) {
        super(name, location, phone);
        this.memberCount = memberCount;
        this.hasDisabled = false;
        this.hasChronicIll = false;
    }

    public Family(String name, String location, String phone,
                  int memberCount, boolean hasDisabled, boolean hasChronicIll) {
        super(name, location, phone);
        this.memberCount = memberCount;
        this.hasDisabled = hasDisabled;
        this.hasChronicIll = hasChronicIll;
    }

    @Override
    public String getRole() { return "عائلة"; }

    public int getMemberCount() { return memberCount; }
    public void setMemberCount(int memberCount) { this.memberCount = memberCount; }
    public boolean isHasDisabled() { return hasDisabled; }
    public boolean isHasChronicIll() { return hasChronicIll; }

    // Calculate priority score
    public int calculatePriority() {
        int score = memberCount;
        if (hasDisabled) score += 5;
        if (hasChronicIll) score += 3;
        return score;
    }
}

