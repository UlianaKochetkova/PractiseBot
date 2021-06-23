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

import static com.example.FirstBot.Application.cur_date;
import static com.example.FirstBot.Application.hashMap;
import static com.example.FirstBot.Application.inp1;
import static com.example.FirstBot.Application.inp2;
import static com.example.FirstBot.Application.inp3;

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

        //Если сообщение от пользователя содержит сообщение и оно содержит обычный текст

            if (upd.hasMessage() && upd.getMessage().hasText()){
                System.out.println("text: "+upd.getMessage().getText());
                //Устанавливаем чат, которому отвечаем
                sm.setChatId(upd.getMessage().getChatId().toString());

                //Извлекаем полученное сообщение
                String msg = upd.getMessage().getText();

                //обработка /start
                if (msg.equals("/start")) {
                    String start_msg = "Соглашение об использовании персональных данных. Согласны?";
                    sm.setText(start_msg);
                    hashMap.put(sm.getChatId(), 0);
                    return inlineKey(sm);
                }
                else if (inp1){
                    return standartInput(msg,sm);
                }
                else if (inp2){
                    return passwordInput(msg,sm);
                }
                else if (inp3){
                    return randomInput(msg,sm);
                }


            }
            else if (upd.hasCallbackQuery()){

                //Устанавливаем чат, которому отвечаем
                sm.setChatId(upd.getCallbackQuery().getMessage().getChatId().toString());
                //Забираем код с кнопки
                String data = upd.getCallbackQuery().getData();
                System.out.println("callback: "+data);
                //Обработка нажатий на кнопки
                switch(data){
                    case "/start_yes":
                    {
                        if (hashMap.get(sm.getChatId()) == 0) {
                            //старт был
                            hashMap.put(sm.getChatId(), 1);
                            //Вызов дальнейшей обработки
                            sm.setText("Ваше согласие принято"+"\n"+"Выберите способ ввода данных:");
                            return inlineMethod(sm);
                        }
                    }
                    break;
                    case "/start_no":
                    {
                        if (hashMap.get(sm.getChatId()) == 0) {
                            //старт был, но от обработки отказались
                            sm.setText("Вы отказались от обработки данных. Работа невозможна");
                            hashMap.remove(sm.getChatId());
                        } else sm.setText("Ошибка ввода");
                    }
                    break;

                    //Обработка стандартного ввода
                    case "/input1":
                    {
                        inp1=true;
                        sm.setText("Введите ФИО");
                    }
                    break;
                    case "/input2":
                    {
                        inp2=true;
                        sm.setText("Загрузите изображение паспорта");
                    }
                    break;
                    case "/input3":
                    {
                        inp3=true;
                        sm.setText("Рандомный ввод");
                    }
                    break;
                }
            }

            //Обработка загрузки фото
            else if (upd.getMessage().hasPhoto() || upd.getMessage().hasDocument()){
                sm.setText("Фото получено");
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
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        InlineKeyboardButton but1=new InlineKeyboardButton();
        but1.setText("Да");
        but1.setCallbackData("/start_yes");
        InlineKeyboardButton but2=new InlineKeyboardButton();
        but2.setText("Нет");
        but2.setCallbackData("/start_no");

        rowInline1.add(but1);
        rowInline1.add(but2);

        // Set the keyboard to the markup
        rowsInline.add(rowInline1);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);
        return sm;
    }

    /**
     * Метод, выдающий inline-клавиатуру для выбора методов ввода данных
     * @param sm - сообщение, к которому добавляется inline-клавиатура
     * @return модифицированное сообщение, которое отправит бот
     */
    public synchronized SendMessage inlineMethod(SendMessage sm) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();

        InlineKeyboardButton but1=new InlineKeyboardButton();
        but1.setText("Ввод 1. Ручной");
        but1.setCallbackData("/input1");
        InlineKeyboardButton but2=new InlineKeyboardButton();
        but2.setText("Ввод 2. Паспортный");
        but2.setCallbackData("/input2");
        InlineKeyboardButton but3=new InlineKeyboardButton();
        but3.setText("Ввод 3.?");
        but3.setCallbackData("/input3");
        rowInline1.add(but1);
        rowInline2.add(but2);
        rowInline3.add(but3);

        // Set the keyboard to the markup
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);
        return sm;
    }

    /**
     * Метод, анализирующий данные для стандартного ввода
     * @param msg - текстовые данные
     * @param sm - сформированный SM
     * @return обновленный sm
     */
    public SendMessage standartInput(String msg, SendMessage sm){
        //Обработка ввода ФИО
        if (msg.matches("[А-Яа-я]+ [А-Яа-я]+ [А-Яа-я]+")){
            String fname=msg.substring(0,msg.indexOf(' '));
            msg=msg.substring(msg.indexOf(' ')+1);
            String lname=msg.substring(0,msg.indexOf(' '));
            String mname=msg.substring(msg.indexOf(' ')+1);

            sm.setText("ФИО введено. Введите дату");
        }
        //Обработка ввода даты
        else if (msg.matches("(0[1-9]|[1-2][0-9]|3[0-1]).(0[1-9]|1[0-2]).(2[0-9]{3})")){
            sm.setText("Дата получена");
        }
        //Обработка ввода цели посещения
        else if (msg.matches("[А-Яа-я0-9//.A-Za-z]+")){

        }
        return sm;
    }

    public SendMessage passwordInput(String msg, SendMessage sm){

        return sm;
    }

    public SendMessage randomInput(String msg, SendMessage sm){

        return sm;
    }
}
