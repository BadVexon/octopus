package theOctopus;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theOctopus.cards.*;
import theOctopus.characters.TheOctopus;
import theOctopus.powers.*;
import theOctopus.relics.Blindfold;
import theOctopus.relics.Choiceinator;
import theOctopus.relics.InkBottle;
import theOctopus.relics.Inkjector;
import theOctopus.util.OctoMagic;
import theOctopus.util.SecondDamage;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class OctoMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        OnCardUseSubscriber,
        OnStartBattleSubscriber {
    public static final Logger logger = LogManager.getLogger(OctoMod.class.getName());
    public static final Color OCTO_GRAY = new Color(0.278F, 1F, 0.756F, 1F);
    public static final String THE_OCTO_SHOULDER_1 = "octomodResources/images/char/octoChar/shoulder.png";
    public static final String THE_OCTO_SHOULDER_2 = "octomodResources/images/char/octoChar/shoulder2.png";
    public static final String THE_OCTO_CORPSE = "octomodResources/images/char/octoChar/corpse.png";
    private static final String ATTACK_OCTO_GRAY = "octomodResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_OCTO_GRAY = "octomodResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_OCTO_GRAY = "octomodResources/images/512/bg_power_default_gray.png";
    private static final String ENERGY_ORB_OCTO_GRAY = "octomodResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "octomodResources/images/512/card_small_orb.png";
    private static final String ATTACK_OCTO_GRAY_PORTRAIT = "octomodResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_OCTO_GRAY_PORTRAIT = "octomodResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_OCTO_GRAY_PORTRAIT = "octomodResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_OCTO_GRAY_PORTRAIT = "octomodResources/images/1024/card_default_gray_orb.png";
    private static final String THE_OCTO_BUTTON = "octomodResources/images/charSelect/OctoCharacterButton.png";
    private static final String THE_OCTO_PORTRAIT = "octomodResources/images/charSelect/OctoCharacterBG.png";
    private static String modID;
    public static AbstractChoiceCard lastChoiceCardPlayed;

    public OctoMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);


        modID = "octomod";


        logger.info("Done subscribing");

        logger.info("Creating the color " + TheOctopus.Enums.COLOR_GRAY.toString());

        BaseMod.addColor(TheOctopus.Enums.COLOR_GRAY, OCTO_GRAY, OCTO_GRAY, OCTO_GRAY,
                OCTO_GRAY, OCTO_GRAY, OCTO_GRAY, OCTO_GRAY,
                ATTACK_OCTO_GRAY, SKILL_OCTO_GRAY, POWER_OCTO_GRAY, ENERGY_ORB_OCTO_GRAY,
                ATTACK_OCTO_GRAY_PORTRAIT, SKILL_OCTO_GRAY_PORTRAIT, POWER_OCTO_GRAY_PORTRAIT,
                ENERGY_ORB_OCTO_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");
    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String getModID() {
        return modID;
    }


    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= IfsdfsdfMod. Hi. =========================");
        OctoMod octoMod = new OctoMod();
        logger.info("========================= /Defadfasdfadgdfdgkln./ =========================");
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheOctopus.Enums.THE_OCTO.toString());

        BaseMod.addCharacter(new TheOctopus("the Octopus", TheOctopus.Enums.THE_OCTO),
                THE_OCTO_BUTTON, THE_OCTO_PORTRAIT, TheOctopus.Enums.THE_OCTO);

        logger.info("Added " + TheOctopus.Enums.THE_OCTO.toString());
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        BaseMod.addRelicToCustomPool(new InkBottle(), TheOctopus.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new Inkjector(), TheOctopus.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new Blindfold(), TheOctopus.Enums.COLOR_GRAY);
        BaseMod.addRelicToCustomPool(new Choiceinator(), TheOctopus.Enums.COLOR_GRAY);

        logger.info("Done adding relics!");
    }

    @Override
    public void receiveEditCards() {

        BaseMod.addDynamicVariable(new OctoMagic());
        BaseMod.addDynamicVariable(new SecondDamage());
        BaseMod.addCard(new InkSpray()); //
        BaseMod.addCard(new StrikeDefend()); //
        BaseMod.addCard(new OctoBoost()); //
        BaseMod.addCard(new Lash()); //
        BaseMod.addCard(new Forethink()); //
        BaseMod.addCard(new BlastSmash()); //
        BaseMod.addCard(new Writhing()); //
        BaseMod.addCard(new TwinSting()); //
        BaseMod.addCard(new Strangle()); //
        BaseMod.addCard(new Punch()); //
        BaseMod.addCard(new KrakenForm()); //
        BaseMod.addCard(new Adaptoform()); //
        BaseMod.addCard(new TreasureChest()); //
        BaseMod.addCard(new Conjure()); //
        BaseMod.addCard(new DeepThought()); //
        BaseMod.addCard(new Tangle()); //
        BaseMod.addCard(new Ensnare()); //
        BaseMod.addCard(new Influx()); //
        BaseMod.addCard(new Indecisive()); //
        BaseMod.addCard(new ExtraArm()); //
        BaseMod.addCard(new SelfInterest()); //
        BaseMod.addCard(new OctoSlam()); //
        BaseMod.addCard(new StingSlap()); //
        BaseMod.addCard(new SpecialSkill()); //
        BaseMod.addCard(new BigHug()); //
        BaseMod.addCard(new OctoWrath()); //
        BaseMod.addCard(new Choke()); //
        BaseMod.addCard(new Prepare());  //
        BaseMod.addCard(new InkSpurt()); //
        BaseMod.addCard(new Coil()); //
        BaseMod.addCard(new Crampage()); //
        BaseMod.addCard(new DecisiveHit()); //
        BaseMod.addCard(new LeechingAcid()); //
        BaseMod.addCard(new SqueezeTighter(0)); //
        BaseMod.addCard(new Tighten()); //
        BaseMod.addCard(new ArmTangle()); //
        BaseMod.addCard(new VengeSplash()); //
        BaseMod.addCard(new Flexability()); //
        BaseMod.addCard(new Spines()); //
        BaseMod.addCard(new Consideration());//
        BaseMod.addCard(new SpinyArms());//
        BaseMod.addCard(new Boosted()); //
        BaseMod.addCard(new MagnumOctopus()); //
        BaseMod.addCard(new LongGame()); //
        BaseMod.addCard(new BigSlap()); //
        BaseMod.addCard(new Rethink()); //
        BaseMod.addCard(new Foresight()); //
        BaseMod.addCard(new FriendlyFeint()); //
        BaseMod.addCard(new DoubleSlap()); //
        BaseMod.addCard(new PocketStrangulation()); //
        BaseMod.addCard(new InkSplat()); //
        BaseMod.addCard(new HeadSlam()); //
        BaseMod.addCard(new Duplicate()); //
        BaseMod.addCard(new HardenBody()); //
        BaseMod.addCard(new Hindsight()); //
        BaseMod.addCard(new ShiftingOctopus()); //
        BaseMod.addCard(new DoubleDonuDeca());//
        BaseMod.addCard(new Tentaparty()); //
        BaseMod.addCard(new Adaptability()); //
        BaseMod.addCard(new PowerPulse()); //
        BaseMod.addCard(new AutoTurret()); //
        BaseMod.addCard(new LifeTap()); //
        BaseMod.addCard(new HeatedShield()); //
        BaseMod.addCard(new Camouflage()); //
        BaseMod.addCard(new MirrorBody()); //
        BaseMod.addCard(new Dish()); //
        BaseMod.addCard(new Blindside()); //
        BaseMod.addCard(new ArrowFlurry()); //
        BaseMod.addCard(new Flurry()); //
        BaseMod.addCard(new BiggestHug()); //
        BaseMod.addCard(new TobeyMaguire()); //
        BaseMod.addCard(new CannonShot()); //
        BaseMod.addCard(new Strike());//
        BaseMod.addCard(new Defend());//

    }

    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/OctoMod-Card-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + "Resources/localization/eng/OctoMod-Power-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/OctoMod-Relic-Strings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/eng/OctoMod-Character-Strings.json");

        logger.info("Done editting strings");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/OctoMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public static void checkStuff(CardGroup group, AbstractMonster monster) {
        if (AbstractDungeon.player.hasPower(NextChoiceDamageChoicePower.POWER_ID)) {
            group.addToTop(new OctoChoiceCard("SpecialAttack", "Special Attack", makeCardPath("HeadSlam.png"), "Deal !D! damage to a random enemy.", AbstractCard.CardType.ATTACK, AbstractDungeon.player.getPower(NextChoiceDamageChoicePower.POWER_ID).amount, -1));
        }
        if (AbstractDungeon.player.hasPower(NextChoiceTempHPChoicePower.POWER_ID)) {
            group.addToTop(new OctoChoiceCard("SpecialTempHP", "Special Temporary HP", makeCardPath("HardenBody.png"), "Gain !M! Temporary_HP.", AbstractCard.CardType.SKILL, -1, -1, AbstractDungeon.player.getPower(NextChoiceTempHPChoicePower.POWER_ID).amount));
        }
        if (AbstractDungeon.player.hasPower(NextChoiceLastChoiceChoicePower.POWER_ID)) {
            for (OctoChoiceCard q : lastChoiceCardPlayed.choiceList(monster)) {
                group.addToTop(q);
            }
        }
        if (AbstractDungeon.player.hasPower(BlindPower.POWER_ID) || AbstractDungeon.player.hasRelic(Blindfold.ID)) {
            for (AbstractCard q : group.group) {
                q.name = "???";
                q.rawDescription = "???";
                q.type = AbstractCard.CardType.SKILL;
                if (q instanceof CustomCard) {
                    ((CustomCard) q).loadCardImage(makeCardPath("ShiftingOctopus.png"));
                }
                q.initializeDescription();
            }
            group.shuffle();
        }
    }

    public static void finalChecks(CardGroup group, OctoChoiceCard cardChoice, AbstractMonster monster) {
        if (AbstractDungeon.player.hasPower(NextChoiceDamageChoicePower.POWER_ID)) {
            if (cardChoice.cardID.equals("SpecialAttack") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(AbstractDungeon.player, group.findCardById("SpecialAttack").baseDamage, DamageInfo.DamageType.NORMAL)));
            }
        }
        if (AbstractDungeon.player.hasPower(NextChoiceTempHPChoicePower.POWER_ID)) {
            if (cardChoice.cardID.equals("SpecialTempHP") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
                AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, group.findCardById("SpecialTempHP").baseMagicNumber));
            }
        }
        if (AbstractDungeon.player.hasPower(NextChoiceLastChoiceChoicePower.POWER_ID)) {
            lastChoiceCardPlayed.doChoiceStuff(monster, group, cardChoice);
        }
    }

    @Override
    public void receiveCardUsed(AbstractCard card) {
        if (card instanceof AbstractChoiceCard && !AbstractDungeon.player.hasPower(NextChoiceLastChoiceChoicePower.POWER_ID) && !(card instanceof Dish)) {
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    lastChoiceCardPlayed = ((AbstractChoiceCard) card);
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        lastChoiceCardPlayed = null;
    }
}
