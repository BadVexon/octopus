package theOctopus.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
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


public class OctoSlam extends AbstractChoiceCard {

    public static final String ID = OctoMod.makeID("OctoSlam");
    public static final String IMG = makeCardPath("OctoSlam.png");
    public static final AbstractCard.CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 3;
    private static final int CHOICES = 2;
    private static final int DAMAGE = 12;
    private static final int ENERGY_DAMAGE = 4;

    public OctoSlam() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHOICES;
        baseDamage = DAMAGE;
        baseSecondDamage = secondDamage = ENERGY_DAMAGE;
    }

    @Override
    public ArrayList<OctoChoiceCard> choiceList(AbstractMonster m) {
        ArrayList<OctoChoiceCard> list = new ArrayList<>();
        list.add(new OctoChoiceCard("Attack", "Attack", makeCardPath("OctoSlam.png"), "Deal !D! damage.", AbstractCard.CardType.ATTACK, this.damage, -1));
        list.add(new OctoChoiceCard("AttackEnergy", "Attack + Energy", makeCardPath("OctoSlam.png"), "Deal !D! damage. NL Gain [E] .", AbstractCard.CardType.ATTACK, this.secondDamage, -1));
        return list;
    }

    @Override
    public void doChoiceStuff(AbstractMonster monster, CardGroup group, OctoChoiceCard cardChoice) {
        if (cardChoice.cardID.equals("Attack") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, group.findCardById("Attack").baseDamage, DamageInfo.DamageType.NORMAL)));
        }
        if (cardChoice.cardID.equals("AttackEnergy") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
            AbstractDungeon.actionManager.addToTop(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, group.findCardById("AttackEnergy").baseDamage, DamageInfo.DamageType.NORMAL)));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeSecondDamage(1);
            initializeDescription();
        }
    }
}
