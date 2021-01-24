package theOctopus.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.Iterator;
import java.util.UUID;

public class SuperIncreaseMiscAction extends AbstractGameAction {
    private int miscIncrease;
    private UUID uuid;

    public SuperIncreaseMiscAction(UUID targetUUID, int miscValue, int miscIncrease) {
        this.miscIncrease = miscIncrease;// 16
        this.uuid = targetUUID;// 18
    }// 19

    public void update() {
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();// 23

        AbstractCard c;
        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c.uuid.equals(this.uuid)) {// 24
                c.misc += this.miscIncrease;// 26
                c.applyPowers();// 27
                c.baseDamage = c.misc;
                c.isDamageModified = false;// 29
            }
        }

        for (var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext(); c.baseDamage = c.misc) {// 31 34
            c = (AbstractCard) var1.next();
            c.misc += this.miscIncrease;// 32
            c.applyPowers();// 33
        }

        this.isDone = true;// 36
    }// 37
}
