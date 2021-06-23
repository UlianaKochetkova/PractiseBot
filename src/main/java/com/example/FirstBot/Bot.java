package com.example.FirstBot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.example.FirstBot.Application.hashMap;

@Component
public class Bot extends TelegramLongPollingBot {
    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();

            try {
                execute(process(update));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return "practise_first_bot";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return "1627593419:AAGXzqkwXe0NkgBVE63mbUsOGQJK9YdGufg";
    }
    //////////////////////////////////////////////////////////////////////

    /**
     * Метод, обрабатывающий сообщения.
     * На своем уровне обрабатывает start и согласие на обработку.
     * Остальное пересылает дальше
     * @param upd - сообщение с данными от пользователя
     * @return сообщение, которое нужно отправить
     */
    public SendMessage process(Update upd) {
        //Создаем новый объект для ответа
        SendMessage sm = new SendMessage();
        //Устанавливаем чат, которому отвечаем
        sm.setChatId(upd.getMessage().getChatId().toString());
        //Извлекаем полученное сообщение
        String msg = upd.getMessage().getText();


        if (msg.equals("/start")) {
            String start_msg = "Соглашение об использовании персональных данных. Согласны?";
            sm.setText(start_msg);
            hashMap.put(sm.getChatId(), 0);

        } else if (msg.matches("[Дд][Аа]|[Yy][Ee][Ss]")) {
            if (hashMap.get(sm.getChatId()) == 0) {
                //старт был
                hashMap.put(sm.getChatId(), 1);
                //Вызов дальнейшей обработки
                sm.setText("Ваше согласие принято"+"\n"+"Выберите способ ввода данных:");
                return inlineKey(sm);
            }
        } else {
            if (hashMap.get(sm.getChatId()) == 0) {
                //старт был, но от обработки отказались
                sm.setText("Вы отказались от обработки данных. Работа невозможна");
                hashMap.remove(sm.getChatId());
            } else sm.setText("Ошибка ввода");
        }
        return sm;
    }

    /**
     * Метод, выдающий inline-клавиатуру для выбора методов ввода данных
     * @param sm - сообщение, к которому добавляется inline-клавиатура
     * @return модифицированное сообщение, которое отправит бот
     */
    public synchronized SendMessage inlineKey(SendMessage sm) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton but1=new InlineKeyboardButton();
        but1.setText("Ввод 1");
        but1.setCallbackData("input1");
        InlineKeyboardButton but2=new InlineKeyboardButton();
        but2.setText("Ввод 2");
        but2.setCallbackData("input2");
        InlineKeyboardButton but3=new InlineKeyboardButton();
        but3.setText("Ввод 3");
        but3.setCallbackData("input3");
        rowInline.add(but1);
        rowInline.add(but2);
        rowInline.add(but3);

        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);
        return sm;
    }
}
