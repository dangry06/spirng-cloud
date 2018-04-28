package com.dangry.microboot.demo;

import com.dangry.microboot.Server;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    private static volatile boolean running = true;

    public static void main(String[] args) {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:api.xml");
        final Server server = context.getBean(Server.class);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                server.start();
            }
        });
        thread.setDaemon(true);
        thread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                synchronized (Main.class) {
                    server.shutdown();
                    running = false;
                    Main.class.notify();
                }
            }
        }));
        synchronized (Main.class) {
            while (running) {
                try {
                    Main.class.wait();
                } catch (Throwable e) {}
            }
        }
    }
}
