package com.github.alanacevedo.finalreality.controller.phase.command.inventorySwapPhase;

import com.github.alanacevedo.finalreality.controller.phase.IPhase;

import com.github.alanacevedo.finalreality.controller.phase.InventorySwapPhase;
import com.github.alanacevedo.finalreality.controller.phase.command.AbstractCommand;
import com.github.alanacevedo.finalreality.controller.phase.command.ICommand;

public class ScrollDownCommand extends AbstractCommand implements ICommand {

    public ScrollDownCommand(IPhase phase) {
        super(phase);
    }

    @Override
    public String getName() {
        return "scrollDown";
    }

    @Override
    public void doAction() {
        ((InventorySwapPhase) parentPhase).scrollDown();
    }
}