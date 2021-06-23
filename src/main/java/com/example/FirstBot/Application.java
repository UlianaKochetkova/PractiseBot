package com.example.FirstBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;

/**
 * Allows to register bot in spring context automatically and also use it as standard spring bean.
 */

@SpringBootApplication
public class Application {
    //0 - на этапе start, 1 - на этапе да, -1 на этапе нет
    public static HashMap<String,Integer> hashMap;
    public static Date cur_date;
    public static boolean inp1,inp2,inp3;

    public static void main(String[] args) throws TelegramApiException {
        hashMap=new HashMap();
        cur_date=Calendar.getInstance().getTime();
        inp1=false;
        inp2=false;
        inp3=false;

        TelegramBotsApi telegram = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot=new Bot();
        telegram.registerBot(bot);
        SpringApplication.run(Application.class, args);
    }

}
