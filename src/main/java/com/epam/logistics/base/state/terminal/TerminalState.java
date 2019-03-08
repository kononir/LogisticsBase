package com.epam.logistics.base.state.terminal;

import com.epam.logistics.base.entitie.Terminal;

public abstract class TerminalState {
    private Terminal terminal;

    public Terminal getTerminal() {
        return terminal;
    }

    public abstract void work();
}
