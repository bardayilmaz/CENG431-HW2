package source;

public interface ISource<T> {

    T value();

    void setValue(T value);
}
