package com.casino.comm.visitors;
import java.io.ObjectOutputStream;

import com.casino.comm.messages.Message;

public class VisitorServer {
    private final ObjectOutputStream outputStream;

    public VisitorServer(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void visit(Message message){
        
    }
}
