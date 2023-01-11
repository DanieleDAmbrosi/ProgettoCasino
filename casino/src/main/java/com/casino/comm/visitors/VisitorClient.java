package com.casino.comm.visitors;

import java.io.ObjectOutputStream;

import com.casino.comm.messages.Message;

public class VisitorClient{
    private final ObjectOutputStream outputStream;

    public VisitorClient(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void visit(Message message) {
    }
}