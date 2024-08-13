import java.util.Objects;

public class Key {

    private final String key1;
    private final String key2;

    public Key(String x, String y) {
        this.key1 = x;
        this.key2 = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return key1.equals(key.key1) && key2.equals(key.key2);
    }


    @Override
    public int hashCode() {
        return Objects.hash(key1, key2);
    }

    @Override
    public String toString() {
        return  key1 + " " + key2;
    }
}
