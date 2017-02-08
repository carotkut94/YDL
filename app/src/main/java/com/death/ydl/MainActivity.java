package com.death.ydl;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.aspsine.multithreaddownload.CallBack;
import com.aspsine.multithreaddownload.DownloadException;
import com.aspsine.multithreaddownload.DownloadRequest;
import com.bumptech.glide.Glide;
import com.death.ydl.utils.Utility;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button downloader;
    SearchView editText;
    ProgressBar progressBar1, progressBar;
    ImageView image;
    TextView title;
    CardView cardView;
    List<String> res;
    List<String> links;
    List<String> extensions;
    Spinner spinner;
    String extension1;
    String link = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (SearchView) findViewById(R.id.link);
        res = new ArrayList<>();
        links = new ArrayList<>();
        extensions = new ArrayList<>();
        progressBar1 = (ProgressBar) findViewById(R.id.pgBar);
        image = (ImageView) findViewById(R.id.imageThumbnail);
        title = (TextView) findViewById(R.id.title);
        cardView = (CardView) findViewById(R.id.cardView);
        spinner = (Spinner) findViewById(R.id.sRes);
        downloader = (Button) findViewById(R.id.download);
        //player = (Button) findViewById(R.id.play);
        progressBar = (ProgressBar) findViewById(R.id.loader);

        progressBar.setMax(100);
        progressBar.setProgress(0);
//        FFmpeg ffmpeg = FFmpeg.getInstance(this);
//        try {
//            ffmpeg.execute(new String[]{"-i", "video.mp4"," -i"," audio.wav", "-c" ,"copy","output.mkv"}, new ExecuteBinaryResponseHandler() {
//
//                @Override
//                public void onStart() {
//                    Toast.makeText(MainActivity.this, "Started", Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onProgress(String message) {
//                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onSuccess(String message) {
//
//                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
//
//                }
//
//                @Override
//                public void onFinish() {
//                }
//            });
//        } catch (FFmpegCommandAlreadyRunningException e) {
//
//        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("Links+============+", links.get(i));
                link = links.get(i);
                extension1 = extensions.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        editText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                links.clear();
                extensions.clear();
                res.clear();
                makeJsonObjectRequest(Utility.getUrl(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

//        player.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = link;
//            }
//        });


        downloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (haveStoragePermission()) {
                    String url = link;
//                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                    request.setDescription("YDL");
//                    request.setTitle(title.getText().toString() + "." + extension1);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                        request.allowScanningByMediaScanner();
//                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                    }
//                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title.getText().toString() + "." + extension1);
//                    DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                    manager.enqueue(request);
                    final DownloadRequest request = new DownloadRequest.Builder()
                            .setName(title.getText().toString() + "." + extension1)
                            .setUri(link)
                            .setFolder(Environment.getExternalStorageDirectory())
                            .build();
                    com.aspsine.multithreaddownload.DownloadManager.getInstance().download(request, link, new CallBack() {
                        @Override
                        public void onStarted() {

                        }

                        @Override
                        public void onConnecting() {

                        }

                        @Override
                        public void onConnected(long l, boolean b) {

                        }

                        @Override
                        public void onProgress(long l, long l1, int i) {
                            Log.e("PROGRESS", i+"    "+l1+"      "+l);

                            progressBar.setProgress(i);
                        }

                        @Override
                        public void onCompleted() {
                            Log.e("PROGRESS","Completed");
                        }

                        @Override
                        public void onDownloadPaused() {

                        }

                        @Override
                        public void onDownloadCanceled() {

                        }

                        @Override
                        public void onFailed(DownloadException e) {
                            Log.e("PROGRESS",e.getErrorMessage());
                        }
                    });
                }
            }
        });
    }


    public boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error", "You have permission");
                return true;
            } else {
                Log.e("Permission error", "You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error", "You already have the permission");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void makeJsonObjectRequest(String urlJsonObj) {
        progressBar1.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response.has("error")) {
                    try {
                        Toast.makeText(MainActivity.this, response.getString("error"), Toast.LENGTH_LONG).show();
                        progressBar1.setVisibility(View.INVISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else

                {
                    cardView.setVisibility(View.VISIBLE);
                    Log.e("TAG", response.toString());
                    try {
                        JSONObject info = response.getJSONObject("info");
                        String titles = info.getString("title");
                        Log.e("Title", titles);
                        Log.e("Thumbnail", info.getString("thumbnail"));
                        Glide.with(MainActivity.this).load(info.getString("thumbnail")).into(image);
                        title.setText(titles);
                        JSONArray formats = info.getJSONArray("formats");
                        for (int i = 0; i < formats.length(); i++) {
                            JSONObject tempObject = formats.getJSONObject(i);
                            Log.e("JSON ARRAY", tempObject.toString());
                            String extension = tempObject.getString("ext");
                            if (extension == "mp4" || extension.equals("mp4") || extension == "webm" || extension.equals("webm")) {
                                res.add(tempObject.getString("format").substring(tempObject.getString("format").indexOf("-") + 1, tempObject.getString("format").length()));
                                links.add(tempObject.getString("url"));
                                extensions.add(extension);
                            }
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, res);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(dataAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    progressBar1.setVisibility(View.INVISIBLE);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar1.setVisibility(View.INVISIBLE);
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

}
