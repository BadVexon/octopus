package theOctopus.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theOctopus.OctoMod;
import theOctopus.powers.BoostNextChoiceThisTurnPower;
import theOctopus.util.TextureLoader;

public class Inkjector extends CustomRelic {

    public static final String ID = OctoMod.makeID("Inkjector");

    private static final Texture IMG = TextureLoader.getTexture(OctoMod.makeRelicPath("Resume.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(OctoMod.makeRelicOutlinePath("LastWill.png"));

    public Inkjector() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(InkBottle.ID)) {// 52
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {// 53
                if (AbstractDungeon.player.relics.get(i).relicId.equals(InkBottle.ID)) {// 54
                    this.instantObtain(AbstractDungeon.player, i, true);// 55
                    break;// 56
                }
            }
        } else {
            super.obtain();// 60
        }
    }// 62

    @Override
    public void atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BoostNextChoiceThisTurnPower(1), 1));
    }

    @Override
    public boolean canSpawn() {
        return (AbstractDungeon.player.hasRelic(InkBottle.ID));
    }

    @Override
    public String getUpdatedDescription() {
        String name = (new InkBottle()).name;// 38
        StringBuilder sb = new StringBuilder();// 39
        String[] var3 = name.split(" ");
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {// 40
            String word = var3[var5];
            sb.append("[#").append(OctoMod.OCTO_GRAY.toString()).append("]").append(word).append("[] ");// 41
        }

        sb.setLength(sb.length() - 1);// 43
        sb.append("[#").append(OctoMod.OCTO_GRAY.toString()).append("]");// 44
        return this.DESCRIPTIONS[0] + sb.toString() + this.DESCRIPTIONS[1];// 46
    }
}
