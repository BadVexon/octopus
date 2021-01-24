package theOctopus.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theOctopus.OctoMod;
import theOctopus.cards.AbstractChoiceCard;
import theOctopus.util.TextureLoader;

import static theOctopus.OctoMod.makePowerPath;

public class NextChoiceLastChoiceChoicePower extends AbstractPower {

    public static final String POWER_ID = OctoMod.makeID("NextChoiceLastChoiceChoicePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("NextChoiceLastChoiceChoicePower_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("NextChoiceLastChoiceChoicePower_32.png"));

    public NextChoiceLastChoiceChoicePower(final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = AbstractDungeon.player;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof AbstractChoiceCard) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    @Override
    public void onRemove() {
        AbstractDungeon.player.drawPile.applyPowers();
        AbstractDungeon.player.hand.applyPowers();
        AbstractDungeon.player.discardPile.applyPowers();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + OctoMod.lastChoiceCardPlayed.name + DESCRIPTIONS[1];
    }
}