package theOctopus.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theOctopus.OctoMod;
import theOctopus.characters.TheOctopus;
import theOctopus.powers.IndecisivePower;

import java.util.ArrayList;

import static theOctopus.OctoMod.makeCardPath;


public class SelfInterest extends AbstractChoiceCard {

    public static final String ID = OctoMod.makeID("SelfInterest");
    public static final String IMG = makeCardPath("SelfInterest.png");
    public static final CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int CHOICES = 2;

    public SelfInterest() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHOICES;
        baseBlock = BLOCK;
    }


    @Override
    public ArrayList<OctoChoiceCard> choiceList(AbstractMonster m) {
        ArrayList<OctoChoiceCard> list = new ArrayList<>();
        list.add(new OctoChoiceCard("Block", "Block", makeCardPath("SelfInterest.png"), "Gain !B! Block.", AbstractCard.CardType.SKILL, -1, this.block));
        if (AbstractDungeon.player.hand.hasUpgradableCards()) {
            if (upgraded) {
                list.add(new OctoChoiceCard("Upgrade", "Upgrade", makeCardPath("SelfInterest.png"), "Upgrade a card in your hand.", AbstractCard.CardType.SKILL));
            } else {
                list.add(new OctoChoiceCard("RandomUpgrade", "Random Upgrade", makeCardPath("SelfInterest.png"), "Upgrade a random card in your hand.", AbstractCard.CardType.SKILL));
            }
        }
        return list;
    }

    @Override
    public void doChoiceStuff(AbstractMonster monster, CardGroup group, OctoChoiceCard cardChoice) {
        if (cardChoice.cardID.equals("Block") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, group.findCardById("Block").baseBlock));
        }
        if (cardChoice.cardID.equals("Upgrade") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ArmamentsAction(false));
        }
        if (cardChoice.cardID.equals("RandomUpgrade") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new UpgradeRandomCardAction());
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
