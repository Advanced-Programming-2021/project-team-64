package Models.Card;

import Controllers.GameController;
import Models.Card.Ability.Ability;
import Models.Card.Ability.AfterDeathAbility;
import Models.Card.Ability.TurnUpAbility;
import Models.Game.Board;
import Models.Game.GameBoard;
import Models.Game.Cell;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Card implements Comparable<Card> {
    private String name = "";
    private String attribute = "";
    private String mainType = "";
    private String type = "";
    private String description = "";
    private Integer cost = 0;
    private Integer level = 0;
    private Integer cardNumber = 0;
    private Integer attack = 0;
    private Integer defence = 0;

    @Override
    public String toString () {
        return (new Gson()).toJson(this);
    }

    @Override
    public boolean equals (Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Card))
            return false;

        Card c = (Card) o;
        return this.hashCode() == c.hashCode();
    }

    @Override
    public int hashCode () {
        return (new Gson()).toJson(this).hashCode();
    }

    public boolean isMonster () {
        return this.checkMainType("Monster");
    }

    public boolean isSpell () {
        return this.checkMainType("Spell");
    }

    public boolean isTrap () {
        return this.checkMainType("Trap");
    }

    public boolean checkName (String cardName) {
        return this.name.equals(cardName);
    }

    public boolean checkMainType (String type) {
        return this.mainType.equals(type);
    }

    public boolean checkType (String type) {
        return this.type.equals(type);
    }

    public int compareTo (Card o) {
        return this.toString().compareTo(o.toString());
    }

    public String getName () {
        return this.name;
    }

    public Integer getCost () {
        return this.cost;
    }

    public Integer getLevel () {
        return level;
    }

    public void increaseAttack (int x) {
        attack += x;
    }

    public void decreaseAttack (int x) {
        attack = Math.max(0, attack - x);
    }

    public void increaseDefence (int x) {
        defence += x;
    }

    public void decreaseDefence (int x) {
        defence = Math.max(0, defence - x);
    }

    public int getAttack(Board myBoard) {
        if (this.checkName("The Calculator"))
            return myBoard.sumOfCardsLevels() * 300;
        return attack;
    }

    public int getAttack1() {
        return this.attack;
    }

    public Integer getDefence() {
        return defence;
    }

    public void ability (Board myBoard, Board enemyBoard) {
        if (this.checkName("Monster Reborn"))
            Ability.MonsterReborn();
        else if (this.checkName("Terraforming"))
            Ability.Terraforming();
        else if (this.checkName("Pot of Greed"))
            Ability.PotOfGreed();
        else if (this.checkName("Raigeki"))
            Ability.Raigeki(enemyBoard);
        else if (this.checkName("Change of Heart"))
            Ability.ChangeOfHeart();
        else if (this.checkName("Harpie’s Feather Duster"))
            Ability.HarpiesFeatherDuster();
        else if (this.checkName("Dark Hole"))
            Ability.DarkHole();
        else if (this.checkName("Twin Twisters"))
            Ability.TwinTwisters();
        else if (this.checkName("Mystical space typhoon"))
            Ability.MysticalSpaceTyphoon();
        else if (this.checkName("Ring of Defense"))
            Ability.RingOfDefense();
        else if (this.checkName("Yami"))
            Ability.Yami(myBoard, enemyBoard);
        else if (this.checkName("Forest"))
            Ability.Forest(myBoard, enemyBoard);
        else if (this.checkName("Closed Forest"))
            Ability.ClosedForest(myBoard);
        else if (this.checkName("UMIIRUKA"))
            Ability.UMIIRUKA(myBoard, enemyBoard);
        else if (this.checkName("Mind Crush"))
            Ability.MindCrush(myBoard, enemyBoard);
        else if (this.checkName("Call of the Haunted"))
            Ability.CallOfTheHaunted(myBoard);
    }

    public void afterPlacement (Cell me, GameBoard gameBoard, ArrayList<Cell> cells) {
    }

    public void afterDeathAbility (Cell me, GameBoard gameBoard, Cell killer) {
        if (this.checkName("Yomi Ship"))
            AfterDeathAbility.YomiShip(me, killer);
        else if (this.checkName("Exploder Dragon"))
            AfterDeathAbility.ExploderDragon(me, killer);

    }

    public void afterTurnUpAbility (Cell me, GameBoard gameboard) {
        if (this.checkName("Man-Eater Bug"))
            TurnUpAbility.Man_Eater_Bug();
    }

    public String getDescription() {
        return this.description;
    }
}
