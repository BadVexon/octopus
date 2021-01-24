package theOctopus.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FakeConstrictedPower extends AbstractPower implements HealthBarRenderPower, EndOfPlayerTurnSubscriber {
    public static final String POWER_ID = "Constricted";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AbstractCreature source;

    public FakeConstrictedPower(AbstractCreature target, AbstractCreature source, int fadeAmt) {
        this.name = NAME;// 19
        this.ID = "Constricted";// 20
        this.owner = target;// 21
        this.source = source;// 22
        this.amount = fadeAmt;// 23
        this.updateDescription();// 24
        this.loadRegion("constricted");// 25
        this.type = PowerType.DEBUFF;// 26
        this.priority = 105;// 29
    }// 30

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_CONSTRICTED", 0.05F);// 34
    }// 35

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];// 39
    }// 40

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.flashWithoutSound();// 44
            this.playApplyPowerSfx();// 45
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.source, this.amount, DamageType.THORNS)));
        }
    }

    public void atEndOfPlayerTurn() {
        this.flashWithoutSound();// 44
        this.playApplyPowerSfx();// 45
        AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.source, this.amount, DamageType.THORNS)));// 46
    }// 48

    public Color getColor() {
        return Color.PURPLE.cpy();
    }

    public int getHealthBarAmount() {
        if (this.owner.currentBlock > this.amount) {
            return 0;
        }
        return (this.amount - this.owner.currentBlock);
    }
}
