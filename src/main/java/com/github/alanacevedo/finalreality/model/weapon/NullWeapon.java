package com.github.alanacevedo.finalreality.model.weapon;

public class NullWeapon extends AbstractWeapon{

    public NullWeapon() {
        super("NullWeapon", 0, 1);
        isNull = true;
    }
}