package theOctopus.util;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theOctopus.powers.EndOfPlayerTurnSubscriber;

@SpirePatch(
        clz = AbstractCreature.class,
        method = "applyEndOfTurnTriggers"
)
public class EndOfPlayerTurnHook {
    @SpirePrefixPatch
    public static void Postfix(AbstractCreature __instance) {
        if (__instance.isPlayer) {
            for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!q.isDying && !q.isDead) {
                    for (AbstractPower r : q.powers) {
                        if (r instanceof EndOfPlayerTurnSubscriber) {
                            ((EndOfPlayerTurnSubscriber) r).atEndOfPlayerTurn();
                        }
                    }
                }
            }
        }
    }
}