package theOctopus.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theOctopus.OctoMod;
import theOctopus.characters.TheOctopus;
import theOctopus.powers.IndecisivePower;

import java.util.ArrayList;

import static theOctopus.OctoMod.makeCardPath;


public class LeechingAcid extends AbstractChoiceCard {

    public static final String ID = OctoMod.makeID("LeechingAcid");
    public static final String IMG = makeCardPath("LeechingAcid.png");
    public static final AbstractCard.CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final int COST = 1;
    private static final int CHOICES = 1;

    public LeechingAcid() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHOICES;
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 2);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 2);
    }

    @Override
    public ArrayList<OctoChoiceCard> choiceList(AbstractMonster m) {
        ArrayList<OctoChoiceCard> list = new ArrayList<>();
        list.add(new OctoChoiceCard("Strength", "Strength", makeCardPath("LeechingAcid.png"), "Gain 1 Strength.", AbstractCard.CardType.SKILL));
        list.add(new OctoChoiceCard("StrengthDown", "Strength Down", makeCardPath("LeechingAcid.png"), "Enemy loses 1 Strength.", AbstractCard.CardType.SKILL));
        return list;
    }

    @Override
    public void doChoiceStuff(AbstractMonster monster, CardGroup group, OctoChoiceCard cardChoice) {
        if (cardChoice.cardID.equals("Strength") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
        }
        if (cardChoice.cardID.equals("StrengthDown") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, AbstractDungeon.player, new StrengthPower(monster, -1), -1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
