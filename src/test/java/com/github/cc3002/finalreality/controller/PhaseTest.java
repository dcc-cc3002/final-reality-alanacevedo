package com.github.cc3002.finalreality.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.github.alanacevedo.finalreality.controller.GameController;
import com.github.alanacevedo.finalreality.controller.phase.ActionSelectionPhase;
import com.github.alanacevedo.finalreality.controller.phase.AttackTargetSelectionPhase;
import com.github.alanacevedo.finalreality.controller.phase.InventoryPhase;
import com.github.alanacevedo.finalreality.controller.phase.MagicSelectionPhase;
import com.github.alanacevedo.finalreality.controller.phase.mageVariants.MageActionSelectionPhase;
import com.github.alanacevedo.finalreality.controller.phase.mageVariants.MageAttackTargetSelectionPhase;
import com.github.alanacevedo.finalreality.controller.phase.mageVariants.MageInventoryPhase;
import com.github.alanacevedo.finalreality.model.character.ICharacter;
import com.github.alanacevedo.finalreality.model.character.IPlayableCharacter;
import com.github.alanacevedo.finalreality.model.weapon.Sword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhaseTest {
    GameController controller;

    @BeforeEach
    void setUp() {
        controller = new GameController();
    }

    @Test
    void ActionSelectPhaseTest() {
        controller.setPhase(new ActionSelectionPhase(controller));
        assertTrue(controller.getPhase() instanceof ActionSelectionPhase);
        var phase0 = (ActionSelectionPhase) controller.getPhase();
        phase0.getAttackCommand().doAction();
        assertTrue(controller.getPhase() instanceof AttackTargetSelectionPhase);
        var phase1 = (AttackTargetSelectionPhase) controller.getPhase();
        phase1.getGoBackCommand().doAction();
        assertTrue(controller.getPhase() instanceof ActionSelectionPhase);
        phase0 = (ActionSelectionPhase) controller.getPhase();
        phase0.getInventoryCommand().doAction();
        assertTrue(controller.getPhase() instanceof InventoryPhase);
        var phase2 = (InventoryPhase) controller.getPhase();
        phase2.getGoBackCommand().doAction();
        assertTrue(controller.getPhase() instanceof ActionSelectionPhase);
        phase0 = (ActionSelectionPhase) controller.getPhase();
        phase0.getMagicCommand().doAction(); // Shouldn't be able to select this option
        assertTrue(controller.getPhase() instanceof ActionSelectionPhase);
    }

    /*
    @Test
    void MageActionSelectPhaseTest() {
        controller.setPhase(new MageActionSelectionPhase(controller));
        assertTrue(controller.getPhase() instanceof MageActionSelectionPhase);
        var phase0 = (MageActionSelectionPhase) controller.getPhase();
        phase0.getAttackCommand().doAction();
        assertTrue(controller.getPhase() instanceof MageAttackTargetSelectionPhase);
        var phase1 = (MageAttackTargetSelectionPhase) controller.getPhase();
        phase1.getGoBackCommand().doAction();
        assertTrue(controller.getPhase() instanceof MageActionSelectionPhase);
        phase0 = (MageActionSelectionPhase) controller.getPhase();
        phase0.getInventoryCommand().doAction();
        assertTrue(controller.getPhase() instanceof MageInventoryPhase);
        var phase2 = (MageInventoryPhase) controller.getPhase();
        phase2.getGoBackCommand().doAction();
        assertTrue(controller.getPhase() instanceof MageActionSelectionPhase);
        phase0 = (MageActionSelectionPhase) controller.getPhase();
        phase0.getMagicCommand().doAction(); // Should be able to select this option
        assertTrue(controller.getPhase() instanceof MagicSelectionPhase);
        var phase3 = (MagicSelectionPhase) controller.getPhase();
        phase3.getGoBackCommand().doAction();
        assertTrue(controller.getPhase() instanceof MageActionSelectionPhase);
    }
    */
    @Test
    void attackTargetSelectionPhaseTest() {
        controller.spawnEnemyGroup(30, 3, "uno", "dos", "tres");
        controller.addSwordToPlayerInventory("espada1", 50, 14);
        controller.addKnightToPlayerParty("caballero1");
        controller.equipWeaponToCharacter(0,0);

        controller.setCurrentChar(controller.getPlayer().getCharacterFromParty(0));
        controller.setPhase(new ActionSelectionPhase(controller));

        var phase0 = (ActionSelectionPhase) controller.getPhase();
        phase0.getAttackCommand().doAction();

        var phase1 = (AttackTargetSelectionPhase) controller.getPhase();
        assertEquals(controller.getEnemyGroup().getEnemy(0).getHP(), controller.getEnemyGroup().getEnemy(0).getMaxHP());
        phase1.getAttackCommand0().doAction();
        assertNotEquals(controller.getEnemyGroup().getEnemy(0).getHP(), controller.getEnemyGroup().getEnemy(0).getMaxHP());

        controller.setCurrentChar(controller.getPlayer().getCharacterFromParty(0));
        controller.setPhase(new ActionSelectionPhase(controller));
        phase0 = (ActionSelectionPhase) controller.getPhase();
        phase0.getAttackCommand().doAction();
        phase1 = (AttackTargetSelectionPhase) controller.getPhase();
        assertEquals(controller.getEnemyGroup().getEnemy(1).getHP(), controller.getEnemyGroup().getEnemy(0).getMaxHP());
        phase1.getAttackCommand1().doAction();
        assertNotEquals(controller.getEnemyGroup().getEnemy(1).getHP(), controller.getEnemyGroup().getEnemy(0).getMaxHP());

        controller.setCurrentChar(controller.getPlayer().getCharacterFromParty(0));
        controller.setPhase(new ActionSelectionPhase(controller));
        phase0 = (ActionSelectionPhase) controller.getPhase();
        phase0.getAttackCommand().doAction();
        phase1 = (AttackTargetSelectionPhase) controller.getPhase();
        assertEquals(controller.getEnemyGroup().getEnemy(2).getHP(), controller.getEnemyGroup().getEnemy(0).getMaxHP());
        phase1.getAttackCommand2().doAction();
        assertNotEquals(controller.getEnemyGroup().getEnemy(2).getHP(), controller.getEnemyGroup().getEnemy(0).getMaxHP());

    }

    @Test
    void InventoryTest () {
        controller.addSwordToPlayerInventory("espada0", 50, 14);
        controller.addSwordToPlayerInventory("espada1", 50, 14);
        controller.addSwordToPlayerInventory("espada2", 50, 14);
        controller.addSwordToPlayerInventory("espada3", 50, 14);
        controller.addSwordToPlayerInventory("espada4", 50, 14);
        controller.addKnightToPlayerParty("caballero1");

        controller.setPhase(new InventoryPhase(controller));
        controller.setCurrentChar(controller.getPlayer().getCharacterFromParty(0));
        IPlayableCharacter knight = controller.getPlayer().getCharacterFromParty(0);
        Sword sword0 = new Sword("espada0", 50, 14);
        assertNull(knight.getEquippedWeapon());
        assertEquals(controller.getPlayer().getWeaponFromInventory(0), sword0);

        // We equip the weapon stored in slot 0. Because knight doesn't have a weapon equipped,
        // slot 0 now has <null> stored. Knight should now have the sword equipped.
        ((InventoryPhase) controller.getPhase()).getHighLightCommand0().doAction();
        ((InventoryPhase) controller.getPhase()).getEquipCommand().doAction();
        assertEquals(knight.getEquippedWeapon(), sword0);
        assertNull(controller.getPlayer().getWeaponFromInventory(0));

        // We scroll down
        ((InventoryPhase) controller.getPhase()).getScrollDownCommand().doAction();
        ((InventoryPhase) controller.getPhase()).getHighLightCommand2().doAction();
        //  HLCommand2 is selected, points to inventory slot 3.
        Sword sword3 = new Sword("espada3", 50, 14);
        assertEquals(controller.getPlayer().getWeaponFromInventory(3), sword3);
        assertNull(controller.getPlayer().getWeaponFromInventory(0));
        ((InventoryPhase) controller.getPhase()).getEquipCommand().doAction();

        // Now knight should have sword3 equipped. Inventory slot 3 should now be empty, and
        // slot 0 should now have sword0 stored.

        assertEquals(knight.getEquippedWeapon(), sword3);
        assertEquals(sword0, controller.getPlayer().getWeaponFromInventory(0));
        assertNull(controller.getPlayer().getWeaponFromInventory(3));

    }
}
