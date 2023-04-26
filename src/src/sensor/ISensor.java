package sensor;

import source.ISource;

public interface ISensor<T> {

    T read(ISource<? extends T> source);

}