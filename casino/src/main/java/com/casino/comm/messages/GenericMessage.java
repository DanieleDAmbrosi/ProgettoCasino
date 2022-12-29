package com.casino.comm.messages;
import com.casino.comm.visitors.Visitor;
public class GenericMessage extends Message{

    private String text = "";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
