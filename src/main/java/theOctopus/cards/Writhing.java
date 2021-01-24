package theOctopus.cards;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theOctopus.OctoMod;
import theOctopus.characters.TheOctopus;
import theOctopus.powers.IndecisivePower;

import java.util.ArrayList;

import static theOctopus.OctoMod.makeCardPath;


public class Writhing extends AbstractChoiceCard {

    public static final String ID = OctoMod.makeID("Writhing");
    public static final String IMG = makeCardPath("Writhing.png");
    public static final AbstractCard.CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int CHOICES = 2;
    private static final int DAMAGE = 4;
    private static final int SECOND_DAMAGE = 5;

    int[] multi;

    public Writhing() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHOICES;
        baseDamage = DAMAGE;
        baseSecondDamage = secondDamage = SECOND_DAMAGE;
    }

    @Override
    public ArrayList<OctoChoiceCard> choiceList(AbstractMonster m) {
        ArrayList<OctoChoiceCard> list = new ArrayList<>();
        int realDamage = this.secondDamage;
        ReflectionHacks.setPrivate(this, AbstractCard.class, "isMultiDamage", true);
        this.applyPowers();
        this.calculateCardDamage(null);
        int mDamage = this.damage;
        multi = this.multiDamage;
        ReflectionHacks.setPrivate(this, AbstractCard.class, "isMultiDamage", false);
        list.add(new OctoChoiceCard("Attack", "Attack", makeCardPath("Writhing.png"), "Deal !D! damage.", AbstractCard.CardType.ATTACK, realDamage, -1));
        list.add(new OctoChoiceCard("AttackAll", "Attack All", makeCardPath("Writhing.png"), "Deal !D! damage to ALL enemies.", AbstractCard.CardType.ATTACK, mDamage, -1));
        return list;
    }

    @Override
    public void doChoiceStuff(AbstractMonster monster, CardGroup group, OctoChoiceCard cardChoice) {
        if (cardChoice.cardID.equals("Attack") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, group.findCardById("Attack").baseDamage, DamageInfo.DamageType.NORMAL)));
        }
        if (cardChoice.cardID.equals("AttackAll") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, multi, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeSecondDamage(2);
            initializeDescription();
        }
    }
}
