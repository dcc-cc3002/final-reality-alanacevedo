package com.github.alanacevedo.finalreality.model.player;

import com.github.alanacevedo.finalreality.model.weapon.IWeapon;
import com.github.alanacevedo.finalreality.controller.Settings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * A class that represents the player's inventory
 * @author <M. Alan Acevedo Salazar>
 */
public class Inventory {
    private final int maxSize = Settings.inventorySize;
    private IWeapon[] weaponList;
    private int currentSize;

    /**
     * Creates an inventory containing the weapons given in args;
     * @param args array of weapons.
     */
    public Inventory(IWeapon... args) {
        weaponList = new IWeapon[maxSize]; // empty
        Arrays.fill(weaponList, null);
        currentSize = 0;
        for (IWeapon weapon : args) {
            this.addWeapon(weapon);
        }
    }

    /**
     * Adds a weapon to the inventory. If the inventory is full, doesn't add it.
     * @param weapon weapon to be added
     */
    public void addWeapon(IWeapon weapon) {
        if (currentSize < maxSize) {
            for (int i=0; i<maxSize; i++) {
                if (weaponList[i] == null) {
                    weaponList[i] = weapon;
                    currentSize++;
                    break;
                }
            }
        }
    }

    /**
     * Removes from the inventory the first appearance of the given weapon.
     * @param weapon weapon to be removed
     */
    public void removeWeapon(IWeapon weapon) {
        for (int i=0; i<maxSize; i++) {
            if (weaponList[i] == weapon) {
                weaponList[i] = null;
                currentSize--;
                break;
            }
        }
    }

    /**
     * Returns the current amount of weapons in the inventory.
     */
    public int getCurrentSize() {
        return currentSize;
    }

    /**
     * Returns the weapon stored in a given slot from the inventory.
     */
    public IWeapon getWeapon(int slot) {
        return weaponList[slot];
    }

    public void swapItems(int slot1, int slot2) {
        IWeapon temp = weaponList[slot1];
        weaponList[slot1] = weaponList[slot2];
        weaponList[slot2] = temp;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventory)) {
            return false;
        }
        final Inventory inventory = (Inventory) o;

        if (inventory.getCurrentSize() != this.getCurrentSize()) {
            return false;
        }
        for (int i=0; i<this.getCurrentSize(); i++) {

            // null.equals() throws exception, so we handle it manually
            if (inventory.getWeapon(i) == null) {
                if (this.getWeapon(i) != null){
                    return false;
                }

            } else {
                if  (!(inventory.getWeapon(i).equals(this.getWeapon(i)))) {
                    return false;
                }
            }
        }
        return true;
    }

}