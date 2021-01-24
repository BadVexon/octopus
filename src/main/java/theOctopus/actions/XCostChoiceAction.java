package theOctopus.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theOctopus.cards.AbstractChoiceCard;

public class XCostChoiceAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int energyOnUse;
    private int magicNum;
    private boolean upgraded;
    private AbstractMonster monster;
    private AbstractChoiceCard buddy;

    public XCostChoiceAction(AbstractPlayer p, int energyOnUse, boolean upgraded, boolean freeToPlayOnce, int magicnum, AbstractMonster m, AbstractChoiceCard budd) {
        this.p = p;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.magicNum = magicnum;
        this.monster = m;
        this.buddy = budd;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (magicNum > 0) {
            effect += magicNum;
        }

        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                AbstractDungeon.actionManager.addToBottom(new OctoChoiceAction(monster, buddy, effect - i));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
