package com.github.alanacevedo.finalreality.controller.phase.command.actionSelectPhase;

import com.github.alanacevedo.finalreality.controller.phase.*;
import com.github.alanacevedo.finalreality.controller.phase.command.AbstractCommand;
import com.github.alanacevedo.finalreality.controller.phase.command.ICommand;

public class InventoryCommand extends AbstractCommand implements ICommand {
    public InventoryCommand(IPhase phase) {
        super(phase);
    }

    @Override
    public String getName() {
        return "Inventory";
    }

    @Override
    public void doAction() {
        parentPhase.changePhase(new InventoryPhase(parentPhase.getController()));
    }
}