package theOctopus.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import theOctopus.OctoMod;
import theOctopus.characters.TheOctopus;
import theOctopus.powers.IndecisivePower;

import java.util.ArrayList;

import static theOctopus.OctoMod.makeCardPath;

public class ShiftingOctopus extends AbstractChoiceCard {

    public static final String ID = OctoMod.makeID("ShiftingOctopus");
    public static final String IMG = makeCardPath("AttackShiftingOctopus.png");
    public static final AbstractCard.CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;

    public ShiftingOctopus() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 2;
        baseDamage = 6;
        baseBlock = 5;
        exhaust = true;
    }

    @Override
    public ArrayList<OctoChoiceCard> choiceList(AbstractMonster m) {
        ArrayList<OctoChoiceCard> list = new ArrayList<>();
        list.add(new OctoChoiceCard("Attack", "Attack", makeCardPath("AttackShiftingOctopus.png"), "Deal !D! damage.", AbstractCard.CardType.ATTACK, this.damage, -1));
        list.add(new OctoChoiceCard("Block", "Block", makeCardPath("ShiftingOctopus.png"), "Gain !B! Block.", AbstractCard.CardType.SKILL, -1, this.block));
        list.add(new OctoChoiceCard("BlockNextTurn", "Block Next Turn", makeCardPath("ShiftingOctopus.png"), "Gain !B! Block next turn.", AbstractCard.CardType.SKILL, -1, this.block));
        list.add(new OctoChoiceCard("Weak", "Weak", makeCardPath("ShiftingOctopus.png"), "Apply 1 Weak.", AbstractCard.CardType.SKILL, -1, -1));
        list.add(new OctoChoiceCard("Vulnerable", "Vulnerable", makeCardPath("ShiftingOctopus.png"), "Apply 1 Vulnerable.", AbstractCard.CardType.SKILL, -1, -1));
        list.add(new OctoChoiceCard("Energy", "Energy", makeCardPath("ShiftingOctopus.png"), "Gain [E] .", AbstractCard.CardType.SKILL, -1, -1));
        list.add(new OctoChoiceCard("EnergyNextTurn", "Energy Next Turn", makeCardPath("ShiftingOctopus.png"), "Next turn, gain [E] .", AbstractCard.CardType.SKILL, -1, -1));
        list.add(new OctoChoiceCard("Draw", "Draw", makeCardPath("ShiftingOctopus.png"), "Draw 1 card.", AbstractCard.CardType.SKILL, -1, -1));
        list.add(new OctoChoiceCard("DrawNextTurn", "Draw Next Turn", makeCardPath("ShiftingOctopus.png"), "Next turn, draw 1 additional card.", AbstractCard.CardType.SKILL, -1, -1));
        list.add(new OctoChoiceCard("StrengthDown", "Strength Down", makeCardPath("ShiftingOctopus.png"), "Enemy loses 2 Strength this turn.", AbstractCard.CardType.SKILL, -1, -1));
        list.add(new OctoChoiceCard("StrengthUp", "Strength Up", makeCardPath("ShiftingOctopus.png"), "Gain 2 Strength. At the end of this turn, lose 2 Strength.", AbstractCard.CardType.SKILL, -1, -1));
        list.add(new OctoChoiceCard("Upgrade", "Upgrade", makeCardPath("ShiftingOctopus.png"), "Upgrade a random card in your hand for the rest of combat.", AbstractCard.CardType.SKILL, -1, -1));
        list.add(new OctoChoiceCard("Exhaust", "Exhaust", makeCardPath("ShiftingOctopus.png"), "Exhaust a card in your hand.", AbstractCard.CardType.SKILL, -1, -1));
        list.add(new OctoChoiceCard("Barrier", "Barrier", makeCardPath("ShiftingOctopus.png"), "Whenever you are attacked this turn, deal 4 damage back.", AbstractCard.CardType.SKILL, -1, -1));
        list.add(new OctoChoiceCard("InkBlast", "Ink Blast", makeCardPath("ShiftingOctopus.png"), "Randomize the cost of your hand between 0 and 3.", AbstractCard.CardType.SKILL, -1, -1));
        return list;
    }

    @Override
    public void doChoiceStuff(AbstractMonster target, CardGroup group, OctoChoiceCard cardChoice) {
        if (cardChoice.cardID.equals("Attack") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, group.findCardById("Attack").baseDamage, damageTypeForTurn)));
        }
        if (cardChoice.cardID.equals("Block") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, group.findCardById("Block").baseBlock));
        }
        if (cardChoice.cardID.equals("Block Next Turn") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NextTurnBlockPower(AbstractDungeon.player, group.findCardById("Block").baseBlock), group.findCardById("Block").baseBlock));
        }
        if (cardChoice.cardID.equals("Vulnerable") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, 1, false), 1));
        }
        if (cardChoice.cardID.equals("Weak") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new WeakPower(target, 1, false), 1));
        }
        if (cardChoice.cardID.equals("Energy") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
        }
        if (cardChoice.cardID.equals("Energy Next Turn") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, 1), 1));
        }
        if (cardChoice.cardID.equals("Draw") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
        }
        if (cardChoice.cardID.equals("Draw Next Turn") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, 1), 1));
        }
        if (cardChoice.cardID.equals("Strength Down") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, -2), -2));
            if (target != null && !target.hasPower("Artifact")) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new GainStrengthPower(target, 2), 2));
            }
        }
        if (cardChoice.cardID.equals("Strength Up") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, 2), 2));
        }
        if (cardChoice.cardID.equals("Upgrade") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new UpgradeRandomCardAction());
        }
        if (cardChoice.cardID.equals("Exhaust") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, 1, false));
        }
        if (cardChoice.cardID.equals("Barrier") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FlameBarrierPower(AbstractDungeon.player, 4), 4));
        }
        if (cardChoice.cardID.equals("InkBlast") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                int newCost = AbstractDungeon.cardRandomRng.random(3);
                if (card.cost != newCost) {
                    card.cost = newCost;
                    card.costForTurn = card.cost;
                    card.isCostModified = true;
                }
            }
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
