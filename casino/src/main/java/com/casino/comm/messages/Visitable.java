package com.casino.comm.messages;

import com.casino.comm.visitors.Visitor;

public interface Visitable {
    public void accept(Visitor visitor);
}
