package com.casino.comm.visitors;

import com.casino.client.View;
import com.casino.comm.messages.*;
import com.casino.comm.messages.closemessage.ResetConnectionMessage;

public class VisitorClient {
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

    public void visit(ResetConnectionMessage resetConnectionMessage) {
        view.resetConnection();
    }

    public void visit(DoABetMessage doABetMessage) {
        view.doABet(doABetMessage.playerState, doABetMessage.EndTimer);
    }
    public void visit(SendRouletteResultMessage sendRouletteResultMessage){
        view.showResults(sendRouletteResultMessage.winningNumber);
    }
}