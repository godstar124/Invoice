package com.invoice.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.invoice.R;
import com.invoice.custom.CSVFile;
import com.invoice.custom.FilePahAdapter;
import com.invoice.global.Global;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileExplorerActivity extends AppCompatActivity {

    private TextView myPath;
    private ListView listView;
    private ArrayList<String> item = null;
    private List<String> path = null;
    private String root="/";
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_file_explorer);

        myPath = (TextView)findViewById(R.id.path);
        listView = (ListView)findViewById(R.id.listView);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            type = extras.getString("csv type");
        }

        ImageButton btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FileExplorerActivity.this, MainActivity.class);
                intent.putExtra("tab", Global.TAB_SETTINGS);
                startActivity(intent);
                finish();
            }
        });
        getDir(root);
    }

    private void getDir(String dirPath) {

        myPath.setText("Location: " + dirPath);
        item = new ArrayList<String>();
        path = new ArrayList<String>();

        File f = new File(dirPath);
        File[] files = f.listFiles();

        if(!dirPath.equals(root)) {
            item.add(root);
            path.add(root);

            item.add("../");
            path.add(f.getParent());
        }

        for(int i=0; i < files.length; i++) {
            File file = files[i];
            path.add(file.getPath());

            if(file.isDirectory())
                item.add(file.getName() + "/");
            else {
                if(isCSVfile(file.getName())) {
                    item.add(file.getName());
                }
            }

        }

        FilePahAdapter adapter = new FilePahAdapter(this, item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final File file = new File(path.get(position));

                if (file.isDirectory()) {
                    if (file.canRead())
                        getDir(path.get(position));
                    else {
                        new AlertDialog.Builder(FileExplorerActivity.this)
                                .setTitle("[" + file.getName() + "] folder can't be read!")
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).show();
                    }
                } else {
                    if( !isCSVfile(file.getName()))
                        return;

                    new AlertDialog.Builder(FileExplorerActivity.this)
                            .setTitle("[" + file.getName() + "]")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            try {
                                                InputStream inputStream = new FileInputStream(path.get(position));
                                                if (inputStream != null) {
                                                    CSVFile csvfile = new CSVFile(inputStream);
                                                    List<String[]> list = csvfile.read();

                                                    if (type.equals("customer")) {
                                                        saveCustomerFromCSV(list);
                                                    } else {
                                                        saveProductFromCSV(list);
                                                    }

                                                }
                                            } catch (FileNotFoundException e) {
                                                showAlert(FileExplorerActivity.this, e.getMessage());
                                            }
                                        }
                                    })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });

    }

    private boolean isCSVfile(String fileName) {

        int dot = fileName.lastIndexOf(".");

        if (dot != -1 && (fileName.substring(dot)).equals(".csv")) {
            return true;
        }
        return false;
    }

    private void showAlert(final Context context, String msg) {

        AlertDialog.Builder confirmAlert = new AlertDialog.Builder(context);
        confirmAlert
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        confirmAlert.show();
    }

    private void saveProductFromCSV(List<String[]> list) {

        SharedPreferences pref = getSharedPreferences(Global.PRODUCT_DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        int position = pref.getInt("proNum", 0);
        int total = list.size();

        String[] header = list.get(0);

        if(header.length != 4 || !header[0].equals("Code") || !header[1].equals("Name")  || !header[2].equals("Price") || !header[3].equals("CBM")) {
            showAlert(this, "Incorrect CSV file style!");
            return;
        }
        for (int i = 0; i < total - 1; i++ ) {

            String[] row = list.get(i + 1);
            editor.putString(String.format("code_%d", position + i), row[0]);
            editor.putString(String.format("name_%d", position + i), row[1]);
            editor.putString(String.format("price_%d", position + i), row[2]);
            editor.putString(String.format("cbm_%d", position + i), row[3]);
        }

        editor.putInt("proNum", position + total - 1);
        editor.commit();

        showAlert(this, String.format("Successfully Imported CSV File!"));
    }

    private void saveCustomerFromCSV(List<String[]> list) {

        SharedPreferences pref = getSharedPreferences(Global.CUSTOMER_DB, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        int position = pref.getInt("customerNum", 0);
        int total = list.size();

        String[] header = list.get(0);
        if(header.length != 10 || !header[0].equals("Customer") || !header[1].equals("Contact") || !header[2].equals("Billing Address") || !header[3].equals("Billing Address1")
                || !header[4].equals("Billing Email") || !header[5].equals("Billing Phone") || !header[6].equals("Shipping Address") || !header[7].equals("Shipping Address1")
                || !header[8].equals("Shipping Email") || !header[9].equals("Shipping Phone")) {
            showAlert(this, "Incorrect CSV file style!");
            return;
        }

        for ( int i = 0; i < total - 1; i++ ) {

            String[] row = list.get(i + 1);

            editor.putString(String.format("name_%d", position + i), row[0]);
            editor.putString(String.format("contact_%d", position + i), row[1]);
            editor.putString(String.format("bAddres_%d", position + i), row[2]);
            editor.putString(String.format("bAddres1_%d", position + i), row[3]);
            editor.putString(String.format("bPhone_%d", position + i), row[4]);
            editor.putString(String.format("bEmail_%d", position + i), row[5]);
            editor.putString(String.format("sAddres_%d", position + i), row[6]);
            editor.putString(String.format("sAddres1_%d", position + i), row[7]);
            editor.putString(String.format("sPhone_%d", position + i), row[8]);
            editor.putString(String.format("sEmail_%d", position + i), row[9]);
        }

        editor.putInt("customerNum", position + total -1);
        editor.commit();

        showAlert(this, String.format("Successfully Imported CSV File!"));
    }

}
