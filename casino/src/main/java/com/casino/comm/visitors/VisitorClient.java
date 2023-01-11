package com.casino.comm.visitors;

import java.io.ObjectOutputStream;

public class VisitorClient{
    private final ObjectOutputStream outputStream;

    public VisitorClient(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }
}