package theOctopus.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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
import theOctopus.characters.TheOctopus;
import theOctopus.powers.IndecisivePower;

import java.util.ArrayList;

import static theOctopus.OctoMod.makeCardPath;

public class Conjure extends AbstractChoiceCard {

    public static final String ID = OctoMod.makeID("Conjure");
    public static final String IMG = makeCardPath("Conjure.png");
    public static final AbstractCard.CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int BLOCK = 5;

    public Conjure() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = 2;
    }



    @Override
    public ArrayList<OctoChoiceCard> choiceList(AbstractMonster m) {
        ArrayList<OctoChoiceCard> list = new ArrayList<>();
        list.add(new OctoChoiceCard("Attack", "Attack", makeCardPath("Conjure.png"), "Deal !D! damage.", AbstractCard.CardType.ATTACK, this.damage, -1));
        list.add(new OctoChoiceCard("Block", "Block", makeCardPath("SkillConjure.png"), "Gain !B! Block.", AbstractCard.CardType.SKILL, -1, this.block));
        list.add(new OctoChoiceCard("Weak", "Weak", makeCardPath("SkillConjure.png"), "Apply 1 Weak.", AbstractCard.CardType.SKILL));
        list.add(new OctoChoiceCard("Vulnerable", "Vulnerable", makeCardPath("SkillConjure.png"), "Apply 1 Vulnerable.", AbstractCard.CardType.SKILL));
        return list;
    }

    @Override
    public void doChoiceStuff(AbstractMonster monster, CardGroup group, OctoChoiceCard cardChoice) {
        if (cardChoice.cardID.contains("Attack") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, group.findCardById("Attack").baseDamage, DamageInfo.DamageType.NORMAL)));
        }
        if (cardChoice.cardID.contains("Block") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, group.findCardById("Block").baseBlock));
        }
        if (cardChoice.cardID.contains("Vulnerable") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, AbstractDungeon.player, new VulnerablePower(monster, 1, false), 1));
        }
        if (cardChoice.cardID.contains("Weak") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, AbstractDungeon.player, new WeakPower(monster, 1, false), 1));
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
