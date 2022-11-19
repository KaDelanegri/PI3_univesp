package com.schoolBot.bot;

import com.schoolBot.properties.SchoolBotProperties;
import com.schoolBot.task.model.Task;
import com.schoolBot.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class SchoolBot extends TelegramLongPollingBot {

    @Autowired
    private TaskService taskService;

    @Override
    public String getBotUsername() {
        return SchoolBotProperties.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return SchoolBotProperties.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(final Update update) {
        var message = new SendMessage();
        var chatId = update.getMessage().getChatId().toString();
        if (update.getMessage().getChat().getType().equals("private")){
            var privateMessage = String.format("Saaalve %s, ta querendo saber quais lições tu tem né !?%nPor favor digite /tarefas no grupo da sua sala", update.getMessage().getChat().getFirstName());
            message = SendMessage.builder().text(privateMessage).chatId(chatId).build();
        }else if (update.hasMessage() && update.getMessage().hasText()){
            message = processMessage(update, chatId);
        }
        try{
            execute(message);
        }catch (TelegramApiException exception){
            exception.printStackTrace();
        }
    }

    private SendMessage processMessage(final Update update, String chatId) {
        String message = update.getMessage().getText().toLowerCase();
        var text = "";
        ReplyKeyboardMarkup keyboardMarkup = null;
        if (message.startsWith("/tarefas")){
            keyboardMarkup = searchTasks(update);
            text = "Suas tarefas";
            if (keyboardMarkup == null)
                return SendMessage.builder().text("Uuuuuufa nada para fazer").chatId(chatId).build();
        } else if (message.startsWith("/help")) {
            return SendMessage.builder().text("Digite /tarefas para ver a lista de tarefas de sua classe").chatId(chatId).build();
        }else if (message.startsWith("id:")){
            text = searchingDetails(message.substring(4, 6));
            return SendMessage.builder().text(text).chatId(chatId).build();
        }
        return SendMessage.builder().allowSendingWithoutReply(true).replyMarkup(keyboardMarkup).text(text).chatId(chatId).build();
    }

    private String searchingDetails(final String id) {
        Task task = taskService.getTaskDetails(id);
        String expirationDateType = "Data de entrega";
        if (task.getType().equalsIgnoreCase("prova")){
            expirationDateType = "Data da prova";
        }
        return task.getType().toUpperCase() +": " + task.getTitle() + ".\nDescrição: " + task.getDescription() + ".\n" + expirationDateType + ": "+ task.getExpirationDate().toString() + ".\nMatéria: " + task.getSubject() +".";
    }

    private ReplyKeyboardMarkup searchTasks(final Update update) {
        List<Task> taskList = taskService.getTasksByClassName(update.getMessage().getChat().getTitle());
        if(!taskList.isEmpty()){
            return createKeyboardMarkup(taskList);
        }
        return null;
    }

    private ReplyKeyboardMarkup createKeyboardMarkup(final List<Task> taskList) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        for (Task task: taskList){
            KeyboardRow row = new KeyboardRow();
            row.add("ID: " + task.getId() +" | Título: " + task.getTitle() + " | Matéria: " + task.getSubject() + " | Entrega: " + task.getExpirationDate());
            keyboardRowList.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

    @PostConstruct
    public void registerBot(){
        try{
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        }catch (TelegramApiException exception){
            exception.printStackTrace();
        }
    }
}