package com.example.FirstBot.entities;

import com.example.FirstBot.Person;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Класс-таблица на одобрение заявки
 * User - отправитель, chatid - куда отсылать, person - Данные заявки, статус???
 */
@Entity
public class Visitor {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    //Данные человека, который внес запись
    private User userFrom;
    //Chat-id
    private Integer charIdFrom;

    //Согласие на обработку данных
    private boolean consent;
    //Способ заполнения данных
    private Integer way;

    //Данные в заявке
    private Person person;

    //Статус заявки??
    private String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public Integer getCharIdFrom() {
        return charIdFrom;
    }

    public void setCharIdFrom(Integer charIdFrom) {
        this.charIdFrom = charIdFrom;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }



}
