package com.casino.comm.visitors;

import com.casino.comm.messages.AskWantToPlayMessage;

public interface Visitor {
    public void visit(AskWantToPlayMessage visitable);
}
