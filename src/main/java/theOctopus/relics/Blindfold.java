package theOctopus.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theOctopus.OctoMod;
import theOctopus.powers.BoostNextChoiceThisTurnPower;
import theOctopus.util.TextureLoader;

public class Blindfold extends CustomRelic {

    public static final String ID = OctoMod.makeID("Blindfold");

    private static final Texture IMG = TextureLoader.getTexture(OctoMod.makeRelicPath("Fake_Beard.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(OctoMod.makeRelicOutlinePath("Fake_Beard.png"));

    public Blindfold() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
