

public class InvalidRequestException extends Exception {
    private String field;

    public InvalidRequestException(String message) {
        super(message);
        this.field = "غير محدد";
    }

    public InvalidRequestException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() { return field; }

    @Override
    public String toString() {
        return "خطأ في [" + field + "]: " + getMessage();
    }
}
