package com.example.lab6websocketcalc.config;

import com.example.lab6websocketcalc.calculator.CalcFunction;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;

public class SocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Connected");
    }

    @Override
    protected  void handleTextMessage(WebSocketSession session, TextMessage message) throws  Exception{
        super.handleTextMessage(session, message);

        try{
            Double result = CalcFunction.calculateResult(message.getPayload());
            if(result != null){
                session.sendMessage(new TextMessage(String.valueOf(result)));
            }
        }
        catch (NumberFormatException ex){
            session.sendMessage(new TextMessage("Invalid Input!!"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        super.afterConnectionClosed(session,status);
        System.out.println("Closed");
    }

}
