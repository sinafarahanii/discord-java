public abstract class Channel {
    private String name;
    private Type type;
    private Message pinMessage;
    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
