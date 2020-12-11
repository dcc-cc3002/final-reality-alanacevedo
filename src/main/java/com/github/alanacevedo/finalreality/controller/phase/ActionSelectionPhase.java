package com.github.alanacevedo.finalreality.controller.phase;

import com.github.alanacevedo.finalreality.controller.GameController;
import com.github.alanacevedo.finalreality.controller.phase.command.ICommand;
import com.github.alanacevedo.finalreality.controller.phase.command.NullCommand;
import com.github.alanacevedo.finalreality.model.character.IPlayableCharacter;
import com.github.alanacevedo.finalreality.controller.phase.command.actionSelectPhase.*;
import org.jetbrains.annotations.NotNull;

public class ActionSelectionPhase extends AbstractPhase implements IPhase{
    //Select between Attack, Skill, Inventory
    //Skill solo tiene efecto si es un personaje mago

    ICommand attackCommand;
    ICommand magicCommand;
    ICommand inventoryCommand;


    public ActionSelectionPhase(@NotNull GameController controller) {
        super(controller);
        attackCommand = new AttackCommand(this);
        magicCommand = new NullCommand(this);
        inventoryCommand = new InventoryCommand(this);
    }

}
