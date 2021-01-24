package theOctopus.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import theOctopus.OctoMod;
import theOctopus.cards.*;
import theOctopus.relics.InkBottle;

import java.util.ArrayList;

import static theOctopus.OctoMod.*;
import static theOctopus.characters.TheOctopus.Enums.COLOR_GRAY;

public class TheOctopus extends CustomPlayer {
    public static final String[] orbTextures = {
            "octomodResources/images/char/octoChar/orb/layer1.png",
            "octomodResources/images/char/octoChar/orb/layer2.png",
            "octomodResources/images/char/octoChar/orb/layer3.png",
            "octomodResources/images/char/octoChar/orb/layer4.png",
            "octomodResources/images/char/octoChar/orb/layer5.png",
            "octomodResources/images/char/octoChar/orb/layer6.png",
            "octomodResources/images/char/octoChar/orb/layer1d.png",
            "octomodResources/images/char/octoChar/orb/layer2d.png",
            "octomodResources/images/char/octoChar/orb/layer3d.png",
            "octomodResources/images/char/octoChar/orb/layer4d.png",
            "octomodResources/images/char/octoChar/orb/layer5d.png",};
    private static final String ID = makeID("theOctopus");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    public TheOctopus(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "octomodResources/images/char/octoChar/orb/vfx.png", null,
                new SpriterAnimation(
                        "octomodResources/images/char/octoChar/octo.scml"));


        initializeClass(null,
                THE_OCTO_SHOULDER_1,
                THE_OCTO_SHOULDER_2,
                THE_OCTO_CORPSE,
                getLoadout(), 20.0F, -10.0F, 224.0F, 324.0F, new EnergyManager(3));


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 300.0F * Settings.scale);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                71, 71, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(StrikeDefend.ID);
        retVal.add(StrikeDefend.ID);
        retVal.add(InkSpray.ID);
        retVal.add(OctoBoost.ID);

        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(InkBottle.ID);

        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.play("GOLD_GAIN");
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 6;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_GRAY;
    }

    @Override
    public Color getCardTrailColor() {
        return OctoMod.OCTO_GRAY;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new OctoBoost();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheOctopus(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return OctoMod.OCTO_GRAY;
    }

    @Override
    public Color getSlashAttackColor() {
        return OctoMod.OCTO_GRAY;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }


    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_OCTO;
        @SpireEnum(name = "OCTO_GRAY_COLOR")
        public static AbstractCard.CardColor COLOR_GRAY;
        @SpireEnum(name = "OCTO_GRAY_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
