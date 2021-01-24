package theOctopus.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
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


public class Crampage extends AbstractChoiceCard {

    public static final String ID = OctoMod.makeID("Crampage");
    public static final String IMG = makeCardPath("Crampage.png");
    public static final AbstractCard.CardColor COLOR = TheOctopus.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int CHOICES = 2;
    private static final int DAMAGE = 3;

    public Crampage() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CHOICES;
        baseDamage = DAMAGE;
        baseOctoMagic = octoMagic = 3;
    }

    @Override
    public ArrayList<OctoChoiceCard> choiceList(AbstractMonster m) {
        ArrayList<OctoChoiceCard> list = new ArrayList<>();
        list.add(new OctoChoiceCard("Attack", "Attack", makeCardPath("Crampage.png"), "Deal !D! damage.", CardType.ATTACK, this.damage, -1));
        list.add(new OctoChoiceCard("DamageUp", "Damage Up", makeCardPath("SkillCrampage.png"), "Increase this card's damage by !M! this combat.", AbstractCard.CardType.SKILL, -1, -1, this.octoMagic));
        return list;
    }

    @Override
    public void doChoiceStuff(AbstractMonster monster, CardGroup group, OctoChoiceCard cardChoice) {
        if (cardChoice.cardID.equals("Attack") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, group.findCardById("Attack").baseDamage, DamageInfo.DamageType.NORMAL)));
        }
        if (cardChoice.cardID.equals("DamageUp") || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            addToTop(new ModifyDamageAction(this.uuid, group.findCardById("DamageUp").baseMagicNumber));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeOctoMagic(1);
            initializeDescription();
        }
    }
}
