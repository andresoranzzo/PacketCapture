package com.example.capturepacket;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class CapturePacket {

    Thread t;

    public CapturePacket() {
        t = null;
    }

    public void startTcpDump(final Boolean savePcapFormat, int timeCapture, String ipNet, final TextView textView) throws IOException, InterruptedException {
        String filters = "";
       /* if (!hostFilter.isEmpty()){
            filters += " \'host " + hostFilter + "\'";
        }
        if(!netFilter.isEmpty()){
            filters += " \'net " + netFilter + "\'";
        }
        if(!portFilter.isEmpty()){
            filters += " \'port " + portFilter + "\'";
        }*/
        if(!ipNet.isEmpty()){
            filters += " net " + ipNet;
        }
        Runtime.getRuntime().exec(new String[]{"su", "-c", "chmod 777 /system/bin/tcpdump"});
        final String finalFilters = filters;
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!savePcapFormat){
                        String cmd = "tcpdump -tt -i any -vv" + finalFilters + " -w /sdcard/tcpdump.log";
                        System.out.println(cmd);
                        Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
                    } else {
                        String cmd = "tcpdump -tt -i any -vv " + finalFilters + " -w /sdcard/tcpdump.pcap";
                        Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
        System.out.println("Started packet capturing.");
        new CountDownTimer(timeCapture*1000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                textView.setText("Capturando pacotes… Tempo restante: " + millisUntilFinished/1000 + "s.");
            }
            public void onFinish() {
                if(savePcapFormat)
                    textView.setText(R.string.stop_message_pcap);
                else
                    textView.setText(R.string.stop_message_log);
            }
        }.start();
        t.interrupt();
        System.out.println("Stopped packet capturing.");

    }
}
