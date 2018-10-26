package com.skrezelok.sollan;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CSVDataProvider implements DataProvider {

    private List<Pair<Integer, String>> records;
    private Path csvFilePath;

    public CSVDataProvider(Path csvFilePath) throws IOException {
        this.csvFilePath = csvFilePath;
        this.records = new ArrayList<>();

        loadCsvFile();
    }

    @Override
    public int count() {
        return records.size();
    }

    @Override
    public Collection<Pair<Integer, String>> get(int page, int count) throws IndexOutOfBoundsException {
        int fromIndex = page * count;
        int toIndex = fromIndex + count;
        if (toIndex > records.size()) toIndex = records.size();

        return new ArrayList<>(records.subList(fromIndex, toIndex));
    }

    @Override
    public Collection<Pair<Integer, String>> getAll() {
        return new ArrayList<>(records);
    }

    private void loadCsvFile() throws IOException {
        try (Reader csvReader = new FileReader(this.csvFilePath.toFile())) {
            Iterable<CSVRecord> csvRecords = CSVFormat.EXCEL.withDelimiter(';').parse(csvReader);

            for (CSVRecord record : csvRecords) {
                Integer id = Integer.parseInt(record.get(0));
                String data = record.get(1) + ", " + record.get(2);
                this.records.add(new Pair<Integer, String>(id, data));
            }
        }
    }
}
