package com.github.cc3002.finalreality.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.github.alanacevedo.finalreality.controller.GameController;
import com.github.alanacevedo.finalreality.controller.Settings;
import com.github.alanacevedo.finalreality.controller.phase.phase.*;
import com.github.alanacevedo.finalreality.controller.phase.phase.attack.AttackTargetSelectionPhase;
import com.github.alanacevedo.finalreality.controller.phase.phase.inventory.InventoryPhase;
import com.github.alanacevedo.finalreality.controller.phase.phase.inventory.InventorySwapPhase;
import com.github.alanacevedo.finalreality.controller.phase.phase.magic.MagicSelectionPhase;
import com.github.alanacevedo.finalreality.model.character.IPlayableCharacter;
import com.github.alanacevedo.finalreality.model.character.player.charClasses.Knight;
import com.github.alanacevedo.finalreality.model.character.player.charClasses.WhiteMage;
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
        controller.getCharacterFactory().spawnEnemyGroup(30, 3, "uno", "dos", "tres");
        controller.getCharacterFactory().addKnightToPlayerParty("a");
        controller.getCharacterFactory().addKnightToPlayerParty("b");
        controller.getCharacterFactory().addKnightToPlayerParty("c");
        controller.setCurrentChar(controller.getPlayer().getCharacterFromParty(0));
        controller.setPhase(new ActionSelectionPhase(controller));

        assertTrue(controller.getPhase() instanceof ActionSelectionPhase);

        var phase0 = (ActionSelectionPhase) controller.getPhase();
        phase0.getAttackCommand().doAction();
        assertFalse(controller.getPhase() instanceof AttackTargetSelectionPhase);

        assertTrue(controller.getPhase() instanceof ActionSelectionPhase);
        phase0 = (ActionSelectionPhase) controller.getPhase();
        phase0.getInventoryCommand().doAction();
        assertTrue(controller.getPhase() instanceof InventoryPhase);
        var phase2 = (InventoryPhase) controller.getPhase();
        phase2.getGoBackCommand().doAction();
        assertTrue(controller.getPhase() instanceof ActionSelectionPhase);

        controller.getCurrentChar().equip(new Sword("a", 1, 1));
        phase0 = (ActionSelectionPhase) controller.getPhase();
        phase0.getAttackCommand().doAction();
        assertTrue(controller.getPhase() instanceof AttackTargetSelectionPhase);
        var phase1 = (AttackTargetSelectionPhase) controller.getPhase();
        phase1.getGoBackCommand().doAction();

        phase0 = (ActionSelectionPhase) controller.getPhase();
        phase0.getMagicCommand().doAction(); // Shouldn't be able to select this option
        assertTrue(controller.getPhase() instanceof ActionSelectionPhase);

    }

    @Test
    void attackTargetSelectionPhaseTest() {
        controller.getCharacterFactory().spawnEnemyGroup(30, 3, "uno", "dos", "tres");
        controller.getWeaponFactory().addSwordToPlayerInventory("espada1", 50, 14);
        controller.getCharacterFactory().addKnightToPlayerParty("caballero2");
        controller.getCharacterFactory().addKnightToPlayerParty("caballero3");
        controller.getCharacterFactory().addKnightToPlayerParty("caballero1");
        controller.equipWeaponToCharacter(0,0);
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
        assertEquals(controller.getEnemyGroup().getEnemy(1).getHP(), controller.getEnemyGroup().getEnemy(1).getMaxHP());
        phase1.getAttackCommand1().doAction();
        assertNotEquals(controller.getEnemyGroup().getEnemy(1).getHP(), controller.getEnemyGroup().getEnemy(1).getMaxHP());

        controller.setCurrentChar(controller.getPlayer().getCharacterFromParty(0));
        controller.setPhase(new ActionSelectionPhase(controller));
        phase0 = (ActionSelectionPhase) controller.getPhase();
        phase0.getAttackCommand().doAction();
        phase1 = (AttackTargetSelectionPhase) controller.getPhase();
        assertEquals(controller.getEnemyGroup().getEnemy(2).getHP(), controller.getEnemyGroup().getEnemy(2).getMaxHP());
        phase1.getAttackCommand2().doAction();
        assertNotEquals(controller.getEnemyGroup().getEnemy(2).getHP(), controller.getEnemyGroup().getEnemy(2).getMaxHP());

    }

    @Test
    void InventoryEquipTest () {
        controller.getWeaponFactory().addSwordToPlayerInventory("espada0", 50, 14);
        controller.getWeaponFactory().addSwordToPlayerInventory("espada1", 50, 14);
        controller.getWeaponFactory().addSwordToPlayerInventory("espada2", 50, 14);
        controller.getWeaponFactory().addSwordToPlayerInventory("espada3", 50, 14);
        controller.getWeaponFactory().addSwordToPlayerInventory("espada4", 50, 14);
        controller.getCharacterFactory().spawnEnemyGroup(30, 3, "uno", "dos", "tres");
        controller.getCharacterFactory().addKnightToPlayerParty("caballero1");
        controller.getCharacterFactory().addKnightToPlayerParty("caballero1");
        controller.getCharacterFactory().addKnightToPlayerParty("caballero1");
        controller.setCurrentChar(controller.getPlayer().getCharacterFromParty(0));
        controller.setPhase(new InventoryPhase(controller));
        IPlayableCharacter knight = controller.getPlayer().getCharacterFromParty(0);
        Sword sword0 = new Sword("espada0", 50, 14);
        assertTrue(knight.getEquippedWeapon().isNull());
        assertEquals(controller.getPlayer().getWeaponFromInventory(0), sword0);

        // We equip the weapon stored in slot 0. Because knight doesn't have a weapon equipped,
        // slot 0 now has <null> stored. Knight should now have the sword equipped.
        ((InventoryPhase) controller.getPhase()).getHighlightCommand0().doAction();
        ((InventoryPhase) controller.getPhase()).getEquipCommand().doAction();
        assertEquals(knight.getEquippedWeapon(), sword0);
        assertTrue(controller.getPlayer().getWeaponFromInventory(0).isNull());

        // We scroll down
        ((InventoryPhase) controller.getPhase()).getScrollDownCommand().doAction();
        ((InventoryPhase) controller.getPhase()).getHighlightCommand2().doAction();
        //  HLCommand2 is selected, points to inventory slot 3.
        Sword sword3 = new Sword("espada3", 50, 14);
        assertEquals(controller.getPlayer().getWeaponFromInventory(3), sword3);
        assertTrue(controller.getPlayer().getWeaponFromInventory(0).isNull());
        ((InventoryPhase) controller.getPhase()).getEquipCommand().doAction();

        // Now knight should have sword3 equipped. Inventory slot 3 should now be empty, and
        // slot 0 should now have sword0 stored.

        assertEquals(knight.getEquippedWeapon(), sword3);
        assertEquals(sword0, controller.getPlayer().getWeaponFromInventory(0));
        assertTrue(controller.getPlayer().getWeaponFromInventory(3).isNull());

        // test for scrolldown limit
        for (int i=0; i < 2*Settings.inventorySize; i++) {
            ((InventoryPhase) controller.getPhase()).getScrollDownCommand().doAction();
        }
        ((InventoryPhase) controller.getPhase()).getHighlightCommand2().doAction();
        assertEquals(Settings.inventorySize-1, ((InventoryPhase) controller.getPhase()).getHighlightedSlot());

        // test for scrollup limit
        for (int i=0; i < 2*Settings.inventorySize; i++) {
            ((InventoryPhase) controller.getPhase()).getScrollUpCommand().doAction();
        }
        ((InventoryPhase) controller.getPhase()).getHighlightCommand2().doAction();
        assertEquals(2, ((InventoryPhase) controller.getPhase()).getHighlightedSlot());
    }

    @Test
    void InventorySwapPhaseTest () {
        controller.getCharacterFactory().spawnEnemyGroup(30, 3, "uno", "dos", "tres");
        controller.getWeaponFactory().addSwordToPlayerInventory("espada0", 50, 14);
        controller.getWeaponFactory().addSwordToPlayerInventory("espada1", 50, 14);
        controller.getWeaponFactory().addSwordToPlayerInventory("espada2", 50, 14);
        controller.getWeaponFactory().addSwordToPlayerInventory("espada3", 50, 14);
        controller.getWeaponFactory().addSwordToPlayerInventory("espada4", 50, 14);
        controller.getCharacterFactory().addKnightToPlayerParty("caballero1");
        controller.getCharacterFactory().addKnightToPlayerParty("caballero2");
        controller.getCharacterFactory().addKnightToPlayerParty("caballero3");

        controller.setPhase(new InventoryPhase(controller));
        controller.setCurrentChar(controller.getPlayer().getCharacterFromParty(0));
        Sword sword1 = new Sword("espada1", 50, 14);
        Sword sword3 = new Sword("espada3", 50, 14);
        IPlayableCharacter knight = controller.getPlayer().getCharacterFromParty(0);

        assertEquals(sword1, controller.getPlayer().getWeaponFromInventory(1));
        assertEquals(sword3, controller.getPlayer().getWeaponFromInventory(3));

        // We highlight slot 1 and then hit swap option
        ((InventoryPhase) controller.getPhase()).getHighlightCommand1().doAction();
        assertEquals(1, (((InventoryPhase) controller.getPhase()).getHighlightedSlot()));
        ((InventoryPhase) controller.getPhase()).getSwapCommand().doAction();
        // We check that we are in swap phase
        assertTrue(controller.getPhase() instanceof InventorySwapPhase);
        assertEquals(1, ((InventorySwapPhase) controller.getPhase()).getFirstSlot());
        // We select slot 3 and then hit confirm swap option.
        ((InventorySwapPhase) controller.getPhase()).getScrollDownCommand().doAction();
        ((InventorySwapPhase) controller.getPhase()).getHighlightCommand2().doAction();
        assertEquals(3, ((InventorySwapPhase) controller.getPhase()).getHighlightedSlot());
        ((InventorySwapPhase) controller.getPhase()).getConfirmSwapCommand().doAction();
        // We check that we are back to inventory phase and that the items are swapped
        assertTrue(controller.getPhase() instanceof InventoryPhase);
        assertEquals(sword1, controller.getPlayer().getWeaponFromInventory(3));
        assertEquals(sword3, controller.getPlayer().getWeaponFromInventory(1));


        // test for scrolldown limit
        controller.setPhase(new InventorySwapPhase(controller, 4));
        for (int i=0; i < 2*Settings.inventorySize; i++) {
            ((InventorySwapPhase) controller.getPhase()).getScrollDownCommand().doAction();
        }
        ((InventorySwapPhase) controller.getPhase()).getHighlightCommand2().doAction();
        assertEquals(Settings.inventorySize-1, ((InventorySwapPhase) controller.getPhase()).getHighlightedSlot());

        // test for scrollup limit
        for (int i=0; i < 2*Settings.inventorySize; i++) {
            ((InventorySwapPhase) controller.getPhase()).getScrollUpCommand().doAction();
        }
        ((InventorySwapPhase) controller.getPhase()).getHighlightCommand2().doAction();
        assertEquals(2, ((InventorySwapPhase) controller.getPhase()).getHighlightedSlot());
    }

    @Test
    void MagicPhaseTest() {
        controller.getCharacterFactory().spawnEnemyGroup(30, 3, "uno", "dos", "tres");
        controller.getCharacterFactory().addKnightToPlayerParty("hola");
        controller.getCharacterFactory().addWhiteMageToPlayerParty("chao");
        controller.getCharacterFactory().addWhiteMageToPlayerParty("cshao");
        controller.setCurrentChar(controller.getPlayer().getCharacterFromParty(0));
        controller.setPhase(new ActionSelectionPhase(controller));
        assertTrue(controller.getCurrentChar() instanceof Knight);
        ((ActionSelectionPhase) controller.getPhase()).getMagicCommand().doAction();
        // A knight is not a mage, so magic option shouldn't work
        assertTrue(controller.getPhase() instanceof ActionSelectionPhase);

        // Now we try with a mage
        controller.setCurrentChar(controller.getPlayer().getCharacterFromParty(1));
        assertTrue(controller.getCurrentChar() instanceof WhiteMage);
        assertTrue(controller.getCurrentChar().isMage());
        ((ActionSelectionPhase) controller.getPhase()).getMagicCommand().doAction();
        assertTrue(controller.getPhase() instanceof MagicSelectionPhase);
    }

    /*
    @Test
    void MagicTargetSelectionPhaseTest() {
        controller.getCharacterFactory().spawnEnemyGroup(30, 3, "uno", "dos", "tres");
        controller.getCharacterFactory().addBlackMageToPlayerParty("magonegro");
        controller.getCharacterFactory().addWhiteMageToPlayerParty("magoblanco");
        controller.getCharacterFactory().addEngineerToPlayerParty("aa");
        controller.getWeaponFactory().addStaffToPlayerInventory("staff0", 1, 1, 40);
        controller.getWeaponFactory().addStaffToPlayerInventory("staff1", 1, 1, 40);
        controller.equipWeaponToCharacter(0, 0);
        controller.equipWeaponToCharacter(1, 1);
        assertNotNull(controller.getPlayer().getCharacterFromParty(0).getEquippedWeapon());
        assertNotNull(controller.getPlayer().getCharacterFromParty(1).getEquippedWeapon());

        controller.setCurrentChar(controller.getPlayer().getCharacterFromParty(0)); //BlackMage
        controller.setPhase(new MagicSelectionPhase(controller));
        ((MagicSelectionPhase) controller.getPhase()).getSpellCommand0().doAction(); // Select fire
        assertTrue(controller.getPhase() instanceof MagicTargetSelectionPhase);

        ((MagicTargetSelectionPhase) controller.getPhase()).getCastCommand0().doAction(); // cast on enemy 0
        Enemy enemy0 = controller.getEnemyGroup().getEnemy(0);
        assertNotEquals(enemy0.getHP(), enemy0.getMaxHP());

        // Now we test for healing
        assertTrue(enemy0.isAlive());
        int oldHp = enemy0.getHP();
        controller.setCurrentChar(controller.getPlayer().getCharacterFromParty(1));
        controller.setPhase(new MagicSelectionPhase(controller));
        ((MagicSelectionPhase) controller.getPhase()).getSpellCommand0().doAction(); // select Cure
        assertTrue(controller.getPhase() instanceof MagicTargetSelectionPhase);

        ((MagicTargetSelectionPhase) controller.getPhase()).getCastCommand0().doAction(); // cast on enemy 0
        assertTrue(enemy0.getHP() > oldHp);
    }

    */

    @Test
    public void fillTest() {
        assertTrue(true);
    }
}
