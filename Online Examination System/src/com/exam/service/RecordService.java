package com.exam.service;

import com.exam.exceptions.DuplicateRecordException;
import com.exam.interfaces.CrudOperations;
import com.exam.model.Record;
import com.exam.util.FileStorage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RecordService implements CrudOperations<Record, String> {
    private final Map<String, Record> records = new LinkedHashMap<>();
    private final FileStorage storage;

    public RecordService(FileStorage storage) {
        this.storage = storage;
        loadRecords();
    }

    private void loadRecords() {
        List<Record> savedRecords = storage.loadRecords();
        for (Record record : savedRecords) {
            records.put(record.getId(), record);
        }
    }

    @Override
    public void add(Record item) {
        if (item == null) {
            throw new IllegalArgumentException("Record cannot be null.");
        }
        if (records.containsKey(item.getId())) {
            throw new IllegalArgumentException("Duplicate record ID not allowed.");
        }
        records.put(item.getId(), item);
        saveRecords();
    }

    @Override
    public void update(String id, Record item) {
        if (!records.containsKey(id)) {
            throw new IllegalArgumentException("Record not found.");
        }
        records.put(id, item);
        saveRecords();
    }

    @Override
    public void delete(String id) {
        if (records.remove(id) == null) {
            throw new IllegalArgumentException("Record ID not found.");
        }
        saveRecords();
    }

    @Override
    public Record findById(String id) {
        return records.get(id);
    }

    public List<Record> getAllRecords() {
        return new ArrayList<>(records.values());
    }

    public List<Record> search(String query) {
        String q = query.toLowerCase();
        List<Record> matches = new ArrayList<>();
        for (Record record : records.values()) {
            if (record.getId().toLowerCase().contains(q)
                    || record.getName().toLowerCase().contains(q)
                    || record.getCategory().toLowerCase().contains(q)
                    || record.getStatus().toLowerCase().contains(q)) {
                matches.add(record);
            }
        }
        return matches;
    }

    public String generateReport() {
        int total = records.size();
        int active = 0;
        int inactive = 0;

        for (Record record : records.values()) {
            if ("ACTIVE".equalsIgnoreCase(record.getStatus())) {
                active++;
            } else {
                inactive++;
            }
        }

        return "\n=== Report Summary ===\n"
                + "Total records: " + total + "\n"
                + "Active records: " + active + "\n"
                + "Inactive records: " + inactive + "\n"
                + "=====================\n";
    }

    public void saveRecords() {
        storage.saveRecords(new ArrayList<>(records.values()));
    }
}
