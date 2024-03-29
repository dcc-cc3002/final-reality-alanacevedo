package com.github.alanacevedo.finalreality.controller.phase.phase.magic;

import com.github.alanacevedo.finalreality.controller.GameController;
import com.github.alanacevedo.finalreality.controller.phase.phase.AbstractPhase;
import com.github.alanacevedo.finalreality.controller.phase.phase.IPhase;
import com.github.alanacevedo.finalreality.gui.phaseScene.magic.MagicSelectionPhaseScene;
import org.jetbrains.annotations.NotNull;
import com.github.alanacevedo.finalreality.controller.phase.command.magic.magicSelectionPhase.*;

// Select Skill, then select enemy to use skill on
public class MagicSelectionPhase extends AbstractPhase {

    private final GoBackCommand goBackCommand;
    private SelectSpellCommand spellCommand0;
    private SelectSpellCommand spellCommand1;
    private SelectSpellCommand spellCommand2;

    public MagicSelectionPhase(@NotNull GameController controller) {
        super(controller);

        goBackCommand = new GoBackCommand(this);
        spellCommand0 = new SelectSpellCommand(this, 0);
        spellCommand1 = new SelectSpellCommand(this, 1);
        spellCommand2 = new SelectSpellCommand(this, 2);
        phaseScene = new MagicSelectionPhaseScene(controller);
    }

    public GoBackCommand getGoBackCommand() {
        return goBackCommand;
    }
}
