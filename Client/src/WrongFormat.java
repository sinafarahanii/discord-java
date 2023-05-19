public class WrongFormat extends Exception{
    private String message;

    public WrongFormat(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "incorrect" +
                message + '\'' +
                '}';
    }
}
