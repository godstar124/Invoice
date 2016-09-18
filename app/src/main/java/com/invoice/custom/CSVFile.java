package com.invoice.custom;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<String[]> read() {

        List<String[]> resultList = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                resultList.add(row);
            }
        } catch (IOException e) {
            Log.e("CSVFile", e.getMessage());
        }

        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e("CSVFile", e.getMessage());
            }
        }

        return resultList;
    }
}
