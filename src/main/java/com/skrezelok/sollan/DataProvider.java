package com.skrezelok.sollan;
import java.util.Collection;

public interface DataProvider<ID, DATA> {
    int count();

    Collection<Pair<ID, DATA>> get(int page, int count);

    Collection<Pair<ID, DATA>> getAll();
}
