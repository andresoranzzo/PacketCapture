package com.example.capturepacket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    boolean isStart;
    Button button;
    TextView textView;
    CapturePacket capture;
    Switch logPcap;
    Boolean savePcapFormat;
    EditText ipNetFilter;
    EditText ipHostFilter;
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
        //CapturePacket capture = new CapturePacket();
       // capture.startTcpDump();
    }

    private void reset() {
        isStart = true;
        textView.setText(R.string.inicial_message);
        //Filters
        ipHostFilter = findViewById(R.id.ip_host_address);
        ipHostFilter.setText("");
        ipNetFilter = findViewById(R.id.ip_net_address);
        ipNetFilter.setText("");
        portFilter = findViewById(R.id.port);
        portFilter.setText("");
        //File format (pcap ou log)
        logPcap = (Switch) findViewById(R.id.switch_pcap);
        logPcap.setChecked(false);
    }

    private void startButton(View view) throws IOException {
        capture = new CapturePacket();
        savePcapFormat = logPcap.isChecked();
        capture.startTcpDump(savePcapFormat, ipHostFilter.getText().toString(), ipNetFilter.getText().toString(), portFilter.getText().toString());

        textView.setText(R.string.start_message);
    }

    private void stopButton(View v) {
        capture.stopTcpDump();
        if(savePcapFormat)
            textView.setText(R.string.stop_message_pcap);
        else
            textView.setText(R.string.stop_message_log);
    }

    @Override
    public void onClick(View v) {
        if(isStart){
            try {
                startButton(v);
            } catch (IOException e) {
                e.printStackTrace();
            }
            isStart = false;
        } else {
            stopButton(v);
            isStart = true;
        }


            
    }


}
