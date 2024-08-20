package com.scm.smartcontactmanager.helper;

public class Message {
    private String content;
    private MessageType type = MessageType.blue;

    //Constructors
    public Message(String content, MessageType type) {
        this.content = content;
        this.type = type;
    }
    public Message() {
    }

    //Getters and Setters
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }
}
