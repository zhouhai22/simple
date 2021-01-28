package tk.mybatis.simple.type;

public enum Enabled {

    enabled(1),
    disabled(0);

    private final int value;

    Enabled(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
