package com.casino.comm.visitors;

import com.casino.comm.messages.*;

public class Visitor {
    public void visit(AskWantToPlayMessage visitable){
        //To Implemnt
        System.out.println("implement this");
    }
    public void visit(GenericMessage visitable){
        System.out.println(visitable.getText());
    }
}
