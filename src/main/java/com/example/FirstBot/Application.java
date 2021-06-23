package com.example.FirstBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Allows to register bot in spring context automatically and also use it as standard spring bean.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegram = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot=new Bot();
        telegram.registerBot(bot);
        SpringApplication.run(Application.class, args);
    }

}
