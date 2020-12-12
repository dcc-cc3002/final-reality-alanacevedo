package com.github.cc3002.finalreality.model.character;

import com.github.alanacevedo.finalreality.model.character.player.AbsMageCharacter;

import com.github.alanacevedo.finalreality.model.character.player.charClasses.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Set of tests for Mage classes.
 *
 * @author Ignacio Slater Muñoz.
 * @author <M. Alan Acevedo Salazar>
 * @see AbsMageCharacter
 */

public class MageCharacterTest extends PlayerCharacterTest{

    @Test
    void cureTest() {
        generateCharactersAndWeapons();

        // testKnight maxHP = 100, testBlackMage initialMP = 50;
        assertEquals(testWhiteMage.getMP(), 50);
        assertEquals(testKnight.getHP(), 100);
        testKnight.receiveDamage(50); // HP = 50
        testWhiteMage.getCureSpell().cast(testKnight); // 30% of 100 is 30
        assertEquals(testKnight.getHP(), 80); // 50 + 30 = 80
        testWhiteMage.getCureSpell().cast(testKnight);
        assertEquals(testKnight.getHP(), 100); // 83 + 30 = 113 but maxHP is 100
        assertTrue(testKnight.isAlive());
        testKnight.receiveDamage(120);
        assertEquals(testKnight.getHP(), 0);
        assertFalse(testKnight.isAlive());
        testWhiteMage.getCureSpell().cast(testKnight); //If a character is dead, he shouldn't be healed.
        assertEquals(testKnight.getHP(), 0);
        assertFalse(testKnight.isAlive());

        // Until now Cure has been successfully cast 2 times, so testBlackMage has 5 MP left and
        // should be able to cast it just one more time.

        testEngineer.receiveDamage(50); // MaxHP = 100
        testWhiteMage.getCureSpell().cast(testEngineer);
        assertEquals(testEngineer.getHP(), 80);
        assertEquals(testWhiteMage.getMP(), 5);
        testWhiteMage.getCureSpell().cast(testEngineer); // He's at 80 HP now, and only 5 mp left
        assertEquals(testEngineer.getHP(), 80); // cure shouldn't heal
        assertEquals(testWhiteMage.getMP(), 5);

        // Testing if a dead Mage can cast Cure

        testWhiteMage = new WhiteMage(WHITE_MAGE_NAME, turns, testHP, testDEF, testMP); // MP = 50;
        testWhiteMage.receiveDamage(200); // Killed
        assertFalse(testWhiteMage.isAlive());
        testWhiteMage.getCureSpell().cast(testEngineer); // Shouldn't cast
        assertEquals(testEngineer.getHP(), 80);

    }

    @Test
    void fireTest() {
        generateCharactersAndWeapons();
        assertEquals(testKnight.getHP(), 100);
        assertNull(testBlackMage.getEquippedWeapon());
        testBlackMage.getFireSpell().cast(testKnight); // Si no tiene arma equipada no debería poder castear
        testBlackMage.equip(testStaff); // magicDamage = 10;
        testBlackMage.getFireSpell().cast(testKnight);
        assertEquals(testKnight.getHP(), 90); // MP = 35
        testKnight.receiveDamage(85);
        assertEquals(testKnight.getHP(), 5);
        assertTrue(testKnight.isAlive());
        testBlackMage.getFireSpell().cast(testKnight); // MP = 20
        assertEquals(testKnight.getHP(), 0);
        assertFalse(testKnight.isAlive());
        testBlackMage.getFireSpell().cast(testKnight); // Objetivo muerto, no debería castear
        assertEquals(testKnight.getHP(), 0);
        assertFalse(testKnight.isAlive());
        assertEquals(testBlackMage.getMP(), 20);

        testEngineer.receiveDamage(50); // HP = 50/100
        testBlackMage.spendMP(15);
        testBlackMage.getFireSpell().cast(testEngineer); // Shouldn't be cast because out of mana.
        assertEquals(testEngineer.getHP(), 50);

        testBlackMage = new BlackMage(WHITE_MAGE_NAME, turns, testHP, testDEF, testMP); // MP = 50;
        testBlackMage.receiveDamage(200); // Killed
        assertFalse(testBlackMage.isAlive());
        testBlackMage.getFireSpell().cast(testEngineer); // Shouldn't be cast
        assertEquals(testEngineer.getHP(), 50);
        assertEquals(testBlackMage.getMP(), 50);

    }

    @Test
    void thunderTest() {
        generateCharactersAndWeapons();
        assertEquals(testKnight.getHP(), 100);
        assertNull(testBlackMage.getEquippedWeapon());
        testBlackMage.getThunderSpell().cast(testKnight); // Si no tiene arma equipada no debería poder castear
        testBlackMage.equip(testStaff); // magicDamage = 10;
        testBlackMage.getThunderSpell().cast(testKnight);
        assertEquals(testKnight.getHP(), 90); // MP = 35
        testKnight.receiveDamage(85);
        assertEquals(testKnight.getHP(), 5);
        assertTrue(testKnight.isAlive());
        testBlackMage.getThunderSpell().cast(testKnight); // MP = 20
        assertEquals(testKnight.getHP(), 0);
        assertFalse(testKnight.isAlive());
        testBlackMage.getThunderSpell().cast(testKnight); // Objetivo muerto, no debería castear
        assertEquals(testKnight.getHP(), 0);
        assertFalse(testKnight.isAlive());
        assertEquals(testBlackMage.getMP(), 20);

        testEngineer.receiveDamage(50); // HP = 50/100
        testBlackMage.spendMP(15);
        testBlackMage.getThunderSpell().cast(testEngineer); // Shouldn't be cast because out of mana.
        assertEquals(testEngineer.getHP(), 50);

        testBlackMage = new BlackMage(WHITE_MAGE_NAME, turns, testHP, testDEF, testMP); // MP = 50;
        testBlackMage.receiveDamage(200); // Killed
        assertFalse(testBlackMage.isAlive());
        testBlackMage.getThunderSpell().cast(testEngineer); // Shouldn't be cast
        assertEquals(testEngineer.getHP(), 50);
        assertEquals(testBlackMage.getMP(), 50);

    }
}
