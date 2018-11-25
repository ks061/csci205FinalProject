/* *****************************************
* CSCI205 - Software Engineering and Design
* Fall 2018
*
* Name: Logan Stiles, Kartikeya Sharma, Jason Kang, and Claudia Shrefler
* Date: Nov 3, 2018
* Time: 1:47:41 PM
*
* Project: csci205FinalProject
* Package: RPGCharacter
* File: RPGCharacter
* Description: This file contains RPGCharacter
*
* ****************************************
 */
package model.character;

import java.util.ArrayList;
import java.util.Random;
import model.item.ConsumableItem;
import model.item.Equipment;
import model.item.EquipmentType;
import model.item.Item;
import model.map.Room;

/**
 * Abstract class for all characters
 *
 * @author lts010, ks061
 */
public abstract class RPGCharacter {

    /**
     * Name of character
     */
    private String name;
    /**
     * Character statistics
     */
    private RPGCharacterStats characterStats;
    /**
     * Current size of inventory
     */
    private int inventorySize;
    /**
     * ArrayList of inventory items
     */
    private ArrayList<Item> inventory;
    /**
     * Type of equipment to be used as weapon
     */
    private Equipment weapon;
    /**
     * Type of equipment used as shield
     */
    private Equipment shield;
    /**
     * Type of equipment used as armor
     */
    private Equipment armor;
    /**
     * Room representing a location
     */
    private Room location;
    /**
     * Boolean representing if character is alive
     */
    private boolean isAlive;
    /**
     * Default chance of missing while using a weapon during attack
     */
    public static final double DEFAULT_MISS_CHANCE = 0.2;
    /**
     * Default chance of a critical hit while using a weapon during attack
     */
    public static final double DEFAULT_CRITICAL_CHANCE = 0.0625;

    /**
     * Constructor for RPGCharacter initializing all its attributes
     *
     * @param name name of character
     * @param characterStats character stats
     * @param inventorySize size of the inventory
     */
    public RPGCharacter(String name, RPGCharacterStats characterStats,
                        int inventorySize) {
        this.name = name;
        this.characterStats = characterStats;
        this.inventorySize = inventorySize;
        this.inventory = new ArrayList<>();
        this.isAlive = true;
    }

    /**
     * Uses a consumable item or equips/unequips an equipment item
     *
     * @param item - item to be used
     */
    public void use(Item item) {
        if (item instanceof ConsumableItem) {
            ConsumableItem consumable = (ConsumableItem) item;
            this.consume(consumable);
        }
        else if (item instanceof Equipment && this.isEquipped((Equipment) item)) {
            Equipment equipment = (Equipment) item;
            this.unequip(equipment);
        }
        else {
            Equipment equipment = (Equipment) item;
            this.equip(equipment);
        }
    }

    /**
     * Determines the type of equipment and equips it
     *
     * @param equipment - equipment to be equipped
     * @return boolean representing equipment equipped
     */
    public boolean isEquipped(Equipment equipment) {
        if (equipment.getType() == EquipmentType.WEAPON) {
            return equipment == this.weapon;
        }
        else if (equipment.getType() == EquipmentType.SHIELD) {
            return equipment == this.shield;
        }
        else {
            return equipment == this.armor;
        }
    }

    /**
     * Attack the given enemy lowering their health based
     *
     * @param enemy - RPGCharacter to attack
     */
    public String attack(RPGCharacter enemy) {
        Random RandomModifiers = new Random();
        double criticalHitModifier;
        double accuracyModifier;

        if (RandomModifiers.nextDouble() < RPGCharacter.DEFAULT_MISS_CHANCE) {
            accuracyModifier = 0.0;
        }
        else {
            accuracyModifier = 1.0;
        }

        if (RandomModifiers.nextDouble() < RPGCharacter.DEFAULT_CRITICAL_CHANCE) {
            criticalHitModifier = 1.5;
        }
        else {
            criticalHitModifier = 1.0;
        }

        double damageCalculation = this.characterStats.getAttack() - enemy.characterStats.getDefense();
        int trueDamage = (int) Math.round(
                damageCalculation * criticalHitModifier * accuracyModifier);
        if (trueDamage <= 0) {
            trueDamage = 0;
        }
        enemy.characterStats.setHealth(
                enemy.characterStats.getHealth() - trueDamage);
        if (enemy.characterStats.getHealth() <= 0) {
            enemy.setIsAlive(false);
        }
        if (accuracyModifier == 0) {
            return String.format("%s missed and did no damage to %s", this.name,
                                 enemy.getName());
        }
        if (criticalHitModifier == 1.5) {
            return String.format("Critical Hit! %s did %d damage to %s",
                                 this.name, trueDamage, enemy.getName());
        }
        return String.format("%s did %d damage to %s", this.name, trueDamage,
                             enemy.getName());
    }

    /**
     * Determines if inventory is full
     *
     * @return boolean representing if inventory is at max size
     */
    public boolean isInventoryFull() {
        return this.inventory.size() >= this.inventorySize;
    }

    /**
     * Gets the character statistics of this character
     *
     * @return character statistics of this character
     *
     * @author ks061
     */
    public RPGCharacterStats getCharacterStats() {
        return characterStats;
    }

    /**
     * Gets the name of the character
     *
     * @return String representing the character's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the character
     *
     * @param name - name to be set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the current size of the inventory
     *
     * @return integer for inventory size
     */
    public int getInventorySize() {
        return inventorySize;
    }

    /**
     * Sets the current inventory size
     *
     * @param inventorySize - integer representing size to be set to
     */
    public void setInventorySize(int inventorySize) {
        this.inventorySize = inventorySize;
    }

    /**
     * Gets the inventory
     *
     * @return ArrayList of items representing the inventory
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * Sets the inventory
     *
     * @param inventory - ArrayList of items to set the inventory
     */
    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    /**
     * Gets the weapon equipment variable
     *
     * @return Equipment object representing weapon
     */
    public Equipment getWeapon() {
        return weapon;
    }

    /**
     * Sets the weapon variable
     *
     * @param weapon - equipment object for weapon to be set to
     */
    public void setWeapon(Equipment weapon) {
        this.weapon = weapon;
    }

    /**
     * Gets the shield variable
     *
     * @return Equipment object representing shield
     */
    public Equipment getShield() {
        return shield;
    }

    /**
     * Sets the shield variable
     *
     * @param shield - equipment object for shield to be set to
     */
    public void setShield(Equipment shield) {
        this.shield = shield;
    }

    /**
     * Gets the armor variable
     *
     * @return Equipment object representing armor
     */
    public Equipment getArmor() {
        return armor;
    }

    /**
     * Sets the armor variable
     *
     * @param armor - equipment object for armor to be set to
     */
    public void setArmor(Equipment armor) {
        this.armor = armor;
    }

    /**
     * Gets the current location of character
     *
     * @return Room object representing location
     */
    public Room getLocation() {
        return location;
    }

    /**
     * Sets the current location of character
     *
     * @param location - Room object for location to be set to
     */
    public void setLocation(Room location) {
        this.location = location;
    }

    /**
     * Checks if character is alive
     *
     * @return boolean representing if character is alive
     */
    public boolean isIsAlive() {
        return isAlive;
    }

    /**
     * Sets the character's aliveness
     *
     * @param isAlive - boolean representing if character is alive
     */
    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * Equips the equipment based on its type
     *
     * @return String representing what was equipped
     *
     * @author ishk001
     */
    public String equip(Equipment equipment) {
        this.getCharacterStats().setMaxHealth(
                this.getCharacterStats().getMaxHealth() + equipment.getItemStatistics().getDeltaHealth());
        this.getCharacterStats().setAttack(
                this.getCharacterStats().getAttack() + equipment.getItemStatistics().getDeltaAttack());
        this.getCharacterStats().setDefense(
                this.getCharacterStats().getDefense() + equipment.getItemStatistics().getDeltaDefense());
        this.setInventorySize(
                this.getInventorySize() + equipment.getItemStatistics().getDeltaInventory());
        switch (equipment.getType()) {
            case WEAPON:
                if (this.getWeapon() != null) {
                    return this.swapEquipment(this.getWeapon());
                }
                else {
                    this.setWeapon(equipment);
                    this.getInventory().remove(equipment);
                }
                break;
            case ARMOR:
                if (this.getArmor() != null) {
                    return this.swapEquipment(this.getArmor());
                }
                else {
                    this.setArmor(equipment);
                    this.getInventory().remove(equipment);
                }
                break;
            case SHIELD:
                if (this.getShield() != null) {
                    return this.swapEquipment(this.getShield());
                }
                else {
                    this.setShield(equipment);
                    this.getInventory().remove(equipment);
                }
                break;
        }
        return String.format("Equipped the %s as a %s",
                             equipment.getName(), equipment.getType().name());
    }

    /**
     * Unequips the equipment and adds it to inventory
     *
     * @param equipment - the equipment you want to unequip
     * @return String representing equipment unequipped
     *
     * @author ishk001
     */
    public String unequip(Equipment equipment) {
        if (this.isInventoryFull()) {
            return String.format(
                    "Cannot unequip the %s because your inventory is full",
                    equipment.getName());
        }
        if (equipment.getType() == EquipmentType.WEAPON) {
            this.setWeapon(null);
        }
        else if (equipment.getType() == EquipmentType.ARMOR) {
            this.setArmor(null);
        }
        else if (equipment.getType() == EquipmentType.SHIELD) {
            this.setShield(null);
        }
        this.inventory.add(equipment);
        this.getCharacterStats().setMaxHealth(
                this.getCharacterStats().getMaxHealth() - equipment.getItemStatistics().getDeltaHealth());
        this.getCharacterStats().setAttack(
                this.getCharacterStats().getAttack() - equipment.getItemStatistics().getDeltaAttack());
        this.getCharacterStats().setDefense(
                this.getCharacterStats().getDefense() - equipment.getItemStatistics().getDeltaDefense());
        this.setInventorySize(
                this.getInventorySize() - equipment.getItemStatistics().getDeltaInventory());
        return String.format("Unequipped the %s and added it to your inventory",
                             equipment.getName());
    }

    /**
     * Swaps the current equipment with something from the inventory
     *
     * @param equipment - equipment that you want to equip
     * @return String representing what items were swapped
     *
     * @author ishk001
     */
    public String swapEquipment(Equipment equipment) {
        this.inventory.remove(equipment);
        Equipment oldEquipment = null;
        switch (equipment.getType()) {
            case WEAPON:
                oldEquipment = this.weapon;
                this.unequip(this.weapon);
                this.setWeapon(equipment);
                break;
            case ARMOR:
                oldEquipment = this.armor;
                this.unequip(this.armor);
                this.setArmor(equipment);
                break;
            case SHIELD:
                oldEquipment = this.shield;
                this.unequip(this.shield);
                this.setShield(equipment);
                break;
        }
        return String.format("Unequipped the %s and equipped the %s",
                             oldEquipment.getName(),
                             equipment.getName());
    }

    /**
     * Consumes the items and correspondingly change the health of item owner
     *
     * @return String representing what was consumed
     */
    public String consume(ConsumableItem consumableItem) {
        //For HEALTH potions
        int curHealth = this.getCharacterStats().getHealth();
        if (curHealth + consumableItem.getItemStatistics().getDeltaHealth() > this.getCharacterStats().getMaxHealth()) {
            //sets the health of the player to max health if health + potion turns
            //out to fill up the health bar of the player
            this.getCharacterStats().setHealth(
                    this.getCharacterStats().getMaxHealth());
        }
        else {
            this.getCharacterStats().setHealth(
                    curHealth + consumableItem.getItemStatistics().getDeltaHealth());
        }
        //For ATTACK potions
        this.getCharacterStats().setAttack(
                this.getCharacterStats().getAttack() + consumableItem.getItemStatistics().getDeltaAttack());
        //For DEFENSE potions
        this.getCharacterStats().setDefense(
                this.getCharacterStats().getDefense() + consumableItem.getItemStatistics().getDeltaDefense());
        //For items that permanantely increase inventory size
        this.setInventorySize(
                this.getInventorySize() + consumableItem.getItemStatistics().getDeltaInventory());
        this.getInventory().remove(consumableItem);
        return String.format("Consumed the %s", consumableItem.getName());
    }

}