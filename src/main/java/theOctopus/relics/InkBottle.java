package theOctopus.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theOctopus.OctoMod;
import theOctopus.powers.BoostNextChoicePower;
import theOctopus.util.TextureLoader;

public class InkBottle extends CustomRelic {

    public static final String ID = OctoMod.makeID("InkBottle");

    private static final Texture IMG = TextureLoader.getTexture(OctoMod.makeRelicPath("Ink_Potion.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(OctoMod.makeRelicOutlinePath("Ink_Potion.png"));

    public InkBottle() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BoostNextChoicePower(1), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
