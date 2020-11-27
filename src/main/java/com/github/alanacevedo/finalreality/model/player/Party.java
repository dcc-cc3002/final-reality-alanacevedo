package com.github.alanacevedo.finalreality.model.player;
import com.github.alanacevedo.finalreality.controller.Settings;
import com.github.alanacevedo.finalreality.model.character.IPlayableCharacter;
import com.github.alanacevedo.finalreality.model.weapon.IWeapon;

import java.util.Arrays;

public class Party {
    private final int maxPartySize = Settings.partySize;
    private int currentPartySize = 0;
    private IPlayableCharacter[] party = new IPlayableCharacter[maxPartySize];

    public Party() {
        Arrays.fill(party, null);
    }

    public void addCharacter(IPlayableCharacter character) {
        party[currentPartySize] = character;
        currentPartySize++;
    }

    public IPlayableCharacter getCharacter(int slot) {
        return party[slot];
    }

    public int getCurrentSize() {
        return currentPartySize;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Party)) {
            return false;
        }
        final Party party = (Party) o;

        if (party.getCurrentSize() != this.getCurrentSize()) {
            return false;
        }
        for (int i=0; i<this.getCurrentSize(); i++) {
            if (!(party.getCharacter(i).equals(this.getCharacter(i)))){
                return false;
            }
        }
        return true;
    }
}