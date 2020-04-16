package com.example.capturepacket;

import java.io.IOException;

public class CapturePacket {

    Thread t;

    public CapturePacket() {
        t = null;
    }

    public void startTcpDump(){

        try {
            Runtime.getRuntime().exec(new String[]{"su", "-c", "chmod 777 /system/bin/tcpdump"});
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Runtime.getRuntime().exec(new String[]{"su", "-c", "/system/bin/tcpdump -n -tt -i any >> /sdcard/tcpdump.log"});
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
            long start = System.currentTimeMillis();
            long end = start + 10*1000; // 60 seconds * 1000 ms/sec
            while (System.currentTimeMillis() < end)
            {
                t.sleep(500L);
            }
            t.interrupt();

        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
