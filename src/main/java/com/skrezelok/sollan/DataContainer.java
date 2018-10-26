package com.skrezelok.sollan;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataContainer<ID, DATA> {

    private List<Pair<ID, DATA>> storage;

    public DataContainer() {
        storage = new ArrayList<>();
    }

    public void load(DataProvider<ID, DATA> dataProvider) {
        storage = new ArrayList<>(dataProvider.getAll());
    }

    public void add(ID id, DATA data) {
        storage.add(new Pair(id, data));
    }

    public void update(ID id, DATA data) {
        List<Pair<ID, DATA>> pairsList = findPairsById(id);

        for (Pair<ID, DATA> pair : pairsList) {
            pair.setData(data);
        }
    }

    public DATA findById(ID id) {
        Pair<ID, DATA> pair = findPairById(id);

        if (pair != null) return findPairById(id).getData();
        else return null;
    }

    public void remove(ID id) {
        while (findPairById(id) != null) {
            storage.remove(findPairById(id));
        }
    }

    public void printAll() {
        System.out.println(storage);
    }

    public void print(int from, int to) {
        if (from < 0) from = 0;
        if (from > storage.size()) from = storage.size();
        if (to < from) to = from;
        if (to > storage.size()) to = storage.size();

        System.out.println(storage.subList(from, to));
    }

    public void clear() {
        storage.clear();
    }

    public Collection<DATA> get(int from, int to) {
        if (from < 0) from = 0;
        if (from > storage.size()) from = storage.size();
        if (to < from) to = from;
        if (to > storage.size()) to = storage.size();

        return getDataFromPairs(storage.subList(from, to));
    }

    public Collection<DATA> filter(Predicate<DATA> filter) {
        List<Pair<ID, DATA>> pairList = storage.stream()
                .filter(pair -> filter.test(pair.getData()))
                .collect(Collectors.toList());

        return getDataFromPairs(pairList);
    }

    private List<Pair<ID, DATA>> findPairsById(ID id) {
        List<Pair<ID, DATA>> pairList = storage.stream()
                .filter(pair -> pair.getId().equals(id))
                .collect(Collectors.toList());

        return pairList;
    }

    private Pair<ID, DATA> findPairById(ID id) {
        List<Pair<ID, DATA>> pairList = findPairsById(id);

        if (pairList.isEmpty()) return null;
        else return pairList.get(0);
    }

    private Collection<DATA> getDataFromPairs(List<Pair<ID, DATA>> pairs) {
        Collection<DATA> data = new ArrayList<>();

        for (Pair<ID, DATA> pair : pairs) {
            data.add(pair.getData());
        }

        return data;
    }

}
