package com.github.cc3002.finalreality.model.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.github.alanacevedo.finalreality.model.character.ICharacter;
import com.github.alanacevedo.finalreality.model.character.player.charClasses.Knight;
import com.github.alanacevedo.finalreality.model.weapon.Weapon;
import com.github.alanacevedo.finalreality.model.weapon.WeaponType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;

class WeaponTest {

  private static final String AXE_NAME = "Test Axe";
  private static final String STAFF_NAME = "Test Staff";
  private static final String SWORD_NAME = "Test Sword";
  private static final String BOW_NAME = "Test Bow";
  private static final String KNIFE_NAME = "Test Knife";
  private static final int DAMAGE = 15;
  private static final int WEIGHT = 10;

  private Weapon testAxe;
  private Weapon testStaff;
  private Weapon testSword;
  private Weapon testBow;
  private Weapon testKnife;

  @BeforeEach
  void setUp() {
    testAxe = new Weapon(AXE_NAME, DAMAGE, WEIGHT, WeaponType.AXE);
    testStaff = new Weapon(STAFF_NAME, DAMAGE, WEIGHT, WeaponType.STAFF);
    testSword = new Weapon(SWORD_NAME, DAMAGE, WEIGHT, WeaponType.SWORD);
    testBow = new Weapon(BOW_NAME, DAMAGE, WEIGHT, WeaponType.BOW);
    testKnife = new Weapon(KNIFE_NAME, DAMAGE, WEIGHT, WeaponType.KNIFE);
  }

  @Test
  void constructorTest() {
    var expectedAxe = new Weapon(AXE_NAME, DAMAGE, WEIGHT, WeaponType.AXE);
    var diffWeight = new Weapon(AXE_NAME, DAMAGE, 999, WeaponType.AXE);
    var diffType = new Weapon(AXE_NAME, DAMAGE, WEIGHT, WeaponType.SWORD);
    var otherAxe = new Weapon(AXE_NAME, 0, 0, WeaponType.AXE);
    var expectedStaff = new Weapon(STAFF_NAME, DAMAGE, WEIGHT, WeaponType.STAFF);
    var expectedSword = new Weapon(SWORD_NAME, DAMAGE, WEIGHT, WeaponType.SWORD);
    var expectedBow = new Weapon(BOW_NAME, DAMAGE, WEIGHT, WeaponType.BOW);
    var expectedKnife = new Weapon(KNIFE_NAME, DAMAGE, WEIGHT, WeaponType.KNIFE);

    assertEquals(expectedAxe, testAxe);
    assertEquals(expectedAxe.hashCode(), testAxe.hashCode());
    assertEquals(expectedStaff, testStaff);
    assertEquals(expectedStaff.hashCode(), testStaff.hashCode());
    assertEquals(expectedSword, testSword);
    assertEquals(expectedSword.hashCode(), testSword.hashCode());
    assertEquals(expectedBow, testBow);
    assertEquals(expectedBow.hashCode(), testBow.hashCode());
    assertEquals(expectedKnife, testKnife);
    assertEquals(expectedKnife.hashCode(), testKnife.hashCode());
    assertNotEquals(testKnife, testBow);
    assertNotEquals(expectedAxe, otherAxe);
    assertNotEquals(diffWeight, expectedAxe);
    assertNotEquals(diffType, expectedAxe);
  }
}