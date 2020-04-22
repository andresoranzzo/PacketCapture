package com.example.capturepacket;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //boolean isStart;
    Button button;
    TextView textView;
    CapturePacket capture;
    Switch logPcap;
    Boolean savePcapFormat;
    EditText timeCapture;
    EditText ipNetFilter;
    EditText portFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button start/stop capture
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        textView = findViewById(R.id.textView);
        reset();
    }

    private void reset() {
        //isStart = true;
        textView.setText(R.string.inicial_message);
        //Filters
        timeCapture = findViewById(R.id.ip_time_capture);
        timeCapture.setText("");
        ipNetFilter = findViewById(R.id.ip_net_address);
        ipNetFilter.setText("");
        portFilter = findViewById(R.id.port);
        portFilter.setText("");
        //File format (pcap ou log)
        logPcap = (Switch) findViewById(R.id.switch_pcap);
        logPcap.setChecked(false);
    }

    private void startButton(String timeCapture, String ipNet, TextView textView) throws IOException, InterruptedException {
        int time;
        if (timeCapture.matches("")) {
            time = 10;
        } else time =  Integer.parseInt(timeCapture);
        capture = new CapturePacket();
        savePcapFormat = logPcap.isChecked();
        capture.startTcpDump(savePcapFormat, time, ipNet, textView);

    }
    /*
    private void stopButton(View v) {
        capture.stopTcpDump();
        if(savePcapFormat)
            textView.setText(R.string.stop_message_pcap);
        else
            textView.setText(R.string.stop_message_log);
    }*/

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        String time = timeCapture.getText().toString();
        String ipNet = ipNetFilter.getText().toString();
        try {
            startButton(time, ipNet, textView);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
