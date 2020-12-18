package com.github.alanacevedo.finalreality.controller.phase.phase.magic;

import com.github.alanacevedo.finalreality.controller.GameController;
import com.github.alanacevedo.finalreality.controller.phase.command.magic.magicTargetSelectionPhase.CastOnEnemySlotCommand;
import com.github.alanacevedo.finalreality.controller.phase.phase.AbstractPhase;
import com.github.alanacevedo.finalreality.controller.phase.phase.IPhase;
import com.github.alanacevedo.finalreality.model.magic.IMagicSpell;
import org.jetbrains.annotations.NotNull;

public class MagicTargetSelectionPhase extends AbstractPhase implements IPhase {


    IMagicSpell spell;
    CastOnEnemySlotCommand castCommand0;
    CastOnEnemySlotCommand castCommand1;

    CastOnEnemySlotCommand castCommand2;

    public MagicTargetSelectionPhase(@NotNull GameController controller, IMagicSpell spell) {
        super(controller);
        name = "Magic Target Selection";

        this.spell = spell;
        castCommand0 = new CastOnEnemySlotCommand(this, 0);
        castCommand1 = new CastOnEnemySlotCommand(this, 1);
        castCommand2 = new CastOnEnemySlotCommand(this, 2);
    }

    public IMagicSpell getSpell() {
        return spell;
    }

    public CastOnEnemySlotCommand getCastCommand0() {
        return castCommand0;
    }

    public CastOnEnemySlotCommand getCastCommand1() {
        return castCommand1;
    }

    public CastOnEnemySlotCommand getCastCommand2() {
        return castCommand2;
    }
    public String getName() {
        return this.name;
    }

}