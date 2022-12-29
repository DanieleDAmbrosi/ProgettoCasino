package com.casino.comm.visitors;
import java.io.IOException;
import java.io.ObjectOutputStream;
import com.casino.comm.messages.*;

public class Visitor {
    private final ObjectOutputStream outputStream;

    public Visitor(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void visit(AskWantToPlayMessage visitable){
        //To Implemnt
        System.out.println("implement this");
    }
    public void visit(GenericMessage visitable){
        System.out.println(visitable.getText());
        try {
            GenericMessage genericMessage = new GenericMessage();
            genericMessage.setText("Hi im Server");
            outputStream.writeObject(genericMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
