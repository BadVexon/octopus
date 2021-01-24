package theOctopus.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theOctopus.OctoMod;
import theOctopus.cards.AbstractChoiceCard;
import theOctopus.powers.BoostNextChoicePower;
import theOctopus.util.TextureLoader;

public class Choiceinator extends CustomRelic {

    public static final String ID = OctoMod.makeID("Choiceinator");

    private static final Texture IMG = TextureLoader.getTexture(OctoMod.makeRelicPath("Bottled_Choice.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(OctoMod.makeRelicOutlinePath("Bottled_Choice.png"));

    public Choiceinator() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof AbstractChoiceCard) {
            ++this.counter;
            if (this.counter == 10) {
                this.counter = 0;
                this.flash();
                this.pulse = false;
            } else if (this.counter == 9) {
                this.beginPulse();
                this.pulse = true;
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BoostNextChoicePower(1), 1));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
