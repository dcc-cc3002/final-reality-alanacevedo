package com.github.alanacevedo.finalreality.controller;
import com.github.alanacevedo.finalreality.model.character.ICharacter;
import com.github.alanacevedo.finalreality.model.character.IPlayableCharacter;
import com.github.alanacevedo.finalreality.model.character.enemy.Enemy;
import com.github.alanacevedo.finalreality.model.character.enemy.EnemyGroup;
import com.github.alanacevedo.finalreality.model.character.player.charClasses.*;
import com.github.alanacevedo.finalreality.model.player.Player;
import com.github.alanacevedo.finalreality.model.weapon.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class GameController {
    private BlockingQueue<ICharacter> turnsQueue = new LinkedBlockingQueue<>();
    private Player player;
    private EnemyGroup enemyGroup;
    private boolean attending = false;
    private boolean battleActive = false;
    private final addToQueueHandler queueHandler = new addToQueueHandler(this);
    private final turnStartHandler turnStartHandler = new turnStartHandler(this);
    private final DeathHandler deathHandler = new DeathHandler(this);
    public GameController() {
        player = new Player(); // Can receive a name
        enemyGroup = new EnemyGroup();
    }

    public void addListeners(ICharacter character) {
        character.addListener(queueHandler);
        character.addListener(turnStartHandler);
        character.addListener(deathHandler);
    }

    public void addBlackMageToPlayerParty(String name) {
        BlackMage character = new BlackMage(name, turnsQueue);
        addListeners(character);
        player.addCharacterToParty(character);
    }
    public void addWhiteMageToPlayerParty(String name) {
        WhiteMage character = new WhiteMage(name, turnsQueue);
        addListeners(character);
        player.addCharacterToParty(character);
    }
    public void addKnightToPlayerParty(String name) {
        Knight character = new Knight(name, turnsQueue);
        addListeners(character);
        player.addCharacterToParty(character);
    }
    public void addThiefToPlayerParty(String name) {
        Thief character = new Thief(name, turnsQueue);
        addListeners(character);
        player.addCharacterToParty(character);
    }
    public void addEngineerToPlayerParty(String name) {
        Engineer character = new Engineer(name, turnsQueue);
        addListeners(character);
        player.addCharacterToParty(character);
    }


    public void addAxeToPlayerInventory(String name, int damage, int weight) {
        Axe weapon = new Axe(name, damage, weight);
        player.addWeaponToInventory(weapon);
    }
    public void addKnifeToPlayerInventory(String name, int damage, int weight) {
        Knife weapon = new Knife(name, damage, weight);
        player.addWeaponToInventory(weapon);
    }
    public void addSwordToPlayerInventory(String name, int damage, int weight) {
        Sword weapon = new Sword(name, damage, weight);
        player.addWeaponToInventory(weapon);
    }
    public void addBowToPlayerInventory(String name, int damage, int weight) {
        Bow weapon = new Bow(name, damage, weight);
        player.addWeaponToInventory(weapon);
    }
    public void addStaffToPlayerInventory(String name, int damage, int weight, int magicDamage) {
        Staff weapon = new Staff(name, damage, weight, magicDamage);
        player.addWeaponToInventory(weapon);
    }


    public void equipWeaponToCharacter(int inventorySlot, int partySlot) {
        if (player.getWeaponFromInventory(inventorySlot) != null) {
            player.equipWeaponToCharacter(inventorySlot, partySlot);
        }
    }

    public void swapInventorySlots(int slot1, int slot2) {
        player.swapInventorySlots(slot1, slot2);
    }


    public void spawnEnemyGroup(int lvl, int size, String... names) {
        enemyGroup.wipeGroup();
        for (int i=0; i<size; i++) {
            int randInt1 = ThreadLocalRandom.current().nextInt(10, 15); // generates random number
            int randInt2 = ThreadLocalRandom.current().nextInt(3, 8);

            int hp = randInt1 * lvl;
            int atk = randInt2 * lvl;
            int def = randInt2 * lvl / 5;

            Enemy enemy = new Enemy(names[i], 10+i, turnsQueue, hp, 20, 70); // balance later
            addListeners(enemy);
            enemyGroup.addEnemy(enemy);
        }
        deathHandler.updateEnemyGroupSize();
    }

    public void PCharAttackEnemy(int partySlot, int mobSlot) {
        Enemy enemy = enemyGroup.getEnemy(mobSlot);
        player.charAttack(partySlot, enemy);
    }

    public void EnemyAttackPChar(int mobSlot, int partySlot) {
        Enemy enemy = enemyGroup.getEnemy(mobSlot);
        enemy.attack(player.getCharacterFromParty(partySlot));
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyGroup getEnemyGroup() {
        return enemyGroup;
    }

    public BlockingQueue<ICharacter> getTurnsQueue() {
        return turnsQueue;
    }

    public void addToQueue(ICharacter character) {
        if (battleActive) {
            character.addToQueue();
            if (!attending) {
                attending = true;
                attendQueue();
            }
        }
    }

    void attendQueue() {
        ICharacter character = turnsQueue.poll();
        assert character != null;
        character.takeTurn();
    }

    public void endTurn() {
        if (turnsQueue.isEmpty()) {
            attending = false;
        } else {
            attendQueue();
        }
    }

    public void enemyTurn(Enemy enemy) {
        int partySize = Settings.partySize;
        int randSlot = ThreadLocalRandom.current().nextInt(0, partySize);

        // To avoid attacking dead characters
        while(! player.getCharacterFromParty(randSlot).isAlive()){
            randSlot = ThreadLocalRandom.current().nextInt(0, partySize);
        }

        enemy.attack(player.getCharacterFromParty(randSlot));
        enemy.waitTurn();
        endTurn();
    }

    public void playerCharacterTurn(IPlayableCharacter character) {
        // For the time being, will function similar to enemyTurn
        // User interaction will be implemented later.
        // later this method will be renamed randomPCTurn.
        int groupSize = enemyGroup.getCurrentEnemies();
        int randSlot = ThreadLocalRandom.current().nextInt(0, groupSize);

        // to avoid attacking dead players
        while(!enemyGroup.getEnemy(randSlot).isAlive()) {
            randSlot = ThreadLocalRandom.current().nextInt(0, groupSize);
        }

        character.attack(enemyGroup.getEnemy(randSlot));
        character.waitTurn();
        endTurn();
    }

    public void startBattle() {
        battleActive = true;
        for (int i=0; i<Settings.partySize; i++) {
            player.getCharacterFromParty(i).waitTurn();
        }

        for (int i=0; i<enemyGroup.getCurrentEnemies(); i++) {
            enemyGroup.getEnemy(i).waitTurn();
        }
    }

    public void endBattle() {
        battleActive = false;
        turnsQueue.clear();
    }

    public boolean isBattleActive() {
        return battleActive;
    }
}