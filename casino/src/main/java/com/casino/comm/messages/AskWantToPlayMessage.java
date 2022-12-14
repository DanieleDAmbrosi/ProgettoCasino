package com.casino.comm.messages;

import com.casino.comm.visitors.Visitor;

public class AskWantToPlayMessage extends Message implements Visitable{
    final int clientIndex;

    public AskWantToPlayMessage(int clientIndex){
        this.clientIndex = clientIndex;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
