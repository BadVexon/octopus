package theOctopus.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theOctopus.OctoMod;
import theOctopus.actions.ChangeGoldAction;
import theOctopus.characters.TheOctopus;
import theOctopus.powers.FakeConstrictedPower;
import theOctopus.powers.IndecisivePower;

import java.util.ArrayList;

import static theOctopus.OctoMod.makeCardPath;


public class OctoWrath extends AbstractChoiceCard {

    public static final String ID = OctoMod.makeID("OctoWrath");
    public static final String IMG = makeCardPath("OctoWrath.png");
    public static final AbstractCard.CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;
    private static final int CHOICES = 8;
    private static final int DAMAGE = 3;
    private static final int BLOCK = 2;

    private int multiBonk;

    public OctoWrath() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHOICES;
        this.isMultiDamage = true;
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        exhaust = true;
    }


    @Override
    public ArrayList<OctoChoiceCard> choiceList(AbstractMonster m) {
        ArrayList<OctoChoiceCard> list = new ArrayList<>();
        list.add(new OctoChoiceCard("AttackAll", "Attack All", makeCardPath("OctoWrath.png"), "Deal !D! damage to ALL enemies.", AbstractCard.CardType.ATTACK, this.damage, -1));
        list.add(new OctoChoiceCard("Block", "Block", makeCardPath("SkillOctoWrath.png"), "Gain !B! Block.", AbstractCard.CardType.SKILL, -1, this.block));
        list.add(new OctoChoiceCard("Draw", "Draw", makeCardPath("SkillOctoWrath.png"), "Draw 1 card.", AbstractCard.CardType.SKILL));
        list.add(new OctoChoiceCard("Weak", "Weak", makeCardPath("SkillOctoWrath.png"), "Apply 1 Weak.", AbstractCard.CardType.SKILL));
        list.add(new OctoChoiceCard("Vulnerable", "Vulnerable", makeCardPath("SkillOctoWrath.png"), "Apply 1 Vulnerable.", AbstractCard.CardType.SKILL));
        list.add(new OctoChoiceCard("Constricted", "Constricted", makeCardPath("SkillOctoWrath.png"), "Apply 1 Constricted.", AbstractCard.CardType.SKILL));
        list.add(new OctoChoiceCard("TemporaryHP", "Temporary HP", makeCardPath("SkillOctoWrath.png"), "Gain 1 Temporary_HP.", AbstractCard.CardType.SKILL));
        list.add(new OctoChoiceCard("Gold", "Gold", makeCardPath("SkillOctoWrath.png"), "Gain 2 Gold.", AbstractCard.CardType.SKILL));
        return list;
    }

    @Override
    public void doChoiceStuff(AbstractMonster monster, CardGroup group, OctoChoiceCard cardChoice) {
        if (cardChoice.cardID.equals("AttackAll") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
        }
        if (cardChoice.cardID.equals("Block") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, group.findCardById("Block").baseBlock));
        }
        if (cardChoice.cardID.equals("Draw") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
        }
        if (cardChoice.cardID.equals("Constricted") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, AbstractDungeon.player, new FakeConstrictedPower(monster, AbstractDungeon.player, 1), 1));
        }
        if (cardChoice.cardID.contains("Weak") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, AbstractDungeon.player, new WeakPower(monster, 1, false), 1));
        }
        if (cardChoice.cardID.contains("Vulnerable") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, AbstractDungeon.player, new VulnerablePower(monster, 1, false), 1));
        }
        if (cardChoice.cardID.equals("TemporaryHP") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, 1));
        }
        if (cardChoice.cardID.equals("Gold") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ChangeGoldAction(2));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
