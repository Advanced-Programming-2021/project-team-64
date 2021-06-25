package Models.Card.Ability;

import Models.Card.Card;
import Models.Game.Board;
import Models.Game.Cell;

import java.util.ArrayList;

public class Ability {
    static public void MonsterReborn () {
        //  یه کارت از گورستان هرکدوم احضار ویژه بشه
    }

    static public void Terraforming () {
        // ی􏰅 عدد کارت افسون میدان􏰀)Field Spell( را از￼ دک􏰀 که در اختیار دارید، به دست خود اضافه کنید.
    }

    static public void PotOfGreed () {
        // ۲ کارت از بالای دک بردارید
    }

    static public void Raigeki (Board enemyBoard) {
        // تمام هیولاهای حریف را نابود کنید
    }

    static public void ChangeOfHeart () {
        // ی􏰁􏰀 از هیولاهایی که حریفتان کنترل م􏰀کند را مورد هدف قرار￼ دهید و تا انتهای این نوبت از بازی آن را به کنترل خود درآورید.
    }

    static public void HarpiesFeatherDuster () {
        // تمام کارتهای افسون و تله که حریفتان کنترل م􏰀کند را نابود کنید.
    }

    static public void DarkHole () {
        //تمام کارتهای هیولای موجود در زمین بازی را نابود کنید.￼
    }

    static public void TwinTwisters () {
        // ی􏰁􏰀 از کارتهای دستتان را دور بریزید؛ سپس حداکثر ٢ کارت افسون￼ یا تله موجود در زمین بازی را مورد هدف قرار دهید و نابودشان کنید.
    }

    static public void MysticalSpaceTyphoon () {
        // ی􏰅 کارت افسون یا تله موجود در زمین بازی￼ را مورد هدف قرار داده و سپس نابودش کنید.
    }

    static public void RingOfDefense () {
        // هرگاه ی􏰅 کارت تله با اثر تخریب کننده فعال شود، آن آسیب را به ٠ برسانید.￼
    }

    static public void Yami (Board myBoard, Board enemyBoard) {
        ArrayList<Card> cards = new ArrayList<>();
        String[] types = {"Fiend", "Spellcaster", "Fairy"};
        cards.addAll(myBoard.getCardsOfTypes(types));
        cards.addAll(enemyBoard.getCardsOfTypes(types));
        for (Card card: cards)
            if (card.checkType("Fairy")) {
                card.decreaseAttack(200);
                card.decreaseDefence(200);
            }
            else {
                card.increaseAttack(200);
                card.increaseDefence(200);
            }
    }

    static public void Forest (Board myBoard, Board enemyBoard) {
        ArrayList<Card> cards = new ArrayList<>();
        String[] types = {"Insect", "Beast", "Beast-Warrior"};
        cards.addAll(myBoard.getCardsOfTypes(types));
        cards.addAll(enemyBoard.getCardsOfTypes(types));
        for (Card card: cards){
            card.increaseAttack(200);
            card.increaseDefence(200);
        }
    }

    static public void ClosedForest (Board myboard) {
        int cnt = myboard.graveYard.size();
        String[] types = {"Beast", "Beast-Warrior"};
        ArrayList<Card> cards = myboard.getCardsOfTypes(types); // Todo: what are all Beast types?
        for (Card card: cards)
            card.increaseAttack(100 * cnt);
    }

    static public void UMIIRUKA (Board myBoard, Board enemyBoard) {
        String[] types = {"Aqua"};
        ArrayList<Card> cards = myBoard.getCardsOfTypes(types);
        cards.addAll(enemyBoard.getCardsOfTypes(types));
        for (Card card: cards)
            if (card.checkMainType("Monster")) {
                card.increaseAttack(500);
                card.decreaseDefence(400);
            }
    }

    static public void MindCrush (Board myBoard, Board enemyBoard) {
        String cardName = "Mind Crush"; // Todo: get cardName from user
        if (enemyBoard.hasCardInHand(cardName))
            enemyBoard.deleteCardFromHandByName(cardName);
        else
            myBoard.deleteRandomCardFromHand();
    }

    static public void CallOfTheHaunted (Board myBoard) {
        // 􏰅 کارت را از گورستان خود در حالت حمله به زمین بازی احضار کنید.￼
    }
}
