package com.casino.comm.visitors;

import com.casino.client.View;
import com.casino.comm.messages.*;

public class VisitorClient{
    /**
     * View client's side
     */
    public final View view;

    /**
     * class constructor
     *
     * @param view is the interface allocated for a client
     */
    public VisitorClient(View view) {
        this.view = view;
    }

    public void visit(AskWantToPlayMessage askWantToPlay) {//vuoi giocare
        view.askWantToPlay(askWantToPlay);
    }
}