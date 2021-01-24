package theOctopus.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;

public class BossDamageAction extends AbstractGameAction {

    private int dmg;

    public BossDamageAction(int asst) {
        dmg = asst;
        duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(AbstractDungeon.player, dmg, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            } else {
                AbstractDungeon.actionManager.addToNextCombat(new BossDamageAction(dmg));
            }
        }
        this.isDone = true;
        tickDuration();
    }
}