package com.example.capturepacket;

import java.io.IOException;

public class CapturePacket {

    Thread t;

    public CapturePacket() {
        t = null;
    }
    /*
        public void startTcpDump(){
            try {
                Runtime.getRuntime().exec(new String[]{"su", "-c", "chmod 777 /system/bin/tcpdump"});
                t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Runtime.getRuntime().exec(new String[]{"su", "-c", "tcpdump -n -tt -i any >> /sdcard/tcpdump.log"});
                            Runtime.getRuntime().exec(new String[]{"su", "-c", "tcpdump -i any -s 0 -w /sdcard/dump.pcap"});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
                long start = System.currentTimeMillis();
                long end = start + 5 * 1000; // 60 seconds * 1000 ms/sec
                while (System.currentTimeMillis() < end) {
                    t.sleep(500L);
                }
                t.interrupt();

            } catch(IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        */
    public void startTcpDump(final Boolean savePcapFormat, String hostFilter, String netFilter, String portFilter) throws IOException {
        String filters = "";
        if (!hostFilter.isEmpty()){
            filters += "host " + hostFilter;
        }
        if(!netFilter.isEmpty()){
            filters += "net " + netFilter;
        }
        if(!portFilter.isEmpty()){
            filters += "port " + portFilter;
        }
        Runtime.getRuntime().exec(new String[]{"su", "-c", "chmod 777 /system/bin/tcpdump"});
        final String finalFilters = filters;
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!savePcapFormat)
                        Runtime.getRuntime().exec(new String[]{"su", "-c", "tcpdump -n -tt -i any " + finalFilters + " >> /sdcard/tcpdump.log"});
                    else
                        Runtime.getRuntime().exec(new String[]{"su", "-c", "tcpdump -i any -s 0 -w " + finalFilters + " /sdcard/tcpdump.pcap"});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void stopTcpDump() {
        t.interrupt();
    }

}
