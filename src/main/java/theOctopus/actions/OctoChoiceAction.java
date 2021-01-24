package theOctopus.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theOctopus.OctoMod;
import theOctopus.cards.AbstractChoiceCard;
import theOctopus.cards.LifeTap;
import theOctopus.cards.OctoChoiceCard;
import theOctopus.powers.IndecisivePower;
import theOctopus.powers.PostChooseSubscriber;
import theOctopus.util.CenterGridCardSelectScreen;

import static theOctopus.OctoMod.makeCardPath;

public class OctoChoiceAction extends AbstractGameAction {
    private boolean pickCard = false;
    private CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private AbstractMonster monster;
    private AbstractChoiceCard funCard;
    private int bunch;

    public OctoChoiceAction(AbstractMonster m, AbstractChoiceCard card, int amount) {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;
        monster = m;
        funCard = card;
        bunch = amount;
    }

    @Override
    public void update() {
        funCard.applyPowers();
        funCard.calculateCardDamage(monster);
        for (OctoChoiceCard q : funCard.choiceList(monster)) {
            group.addToTop(q);
        }
        if (!(funCard instanceof LifeTap)) {
            OctoMod.checkStuff(group, monster);
        }
        if (duration == Settings.ACTION_DUR_XFAST && !(AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) && !group.isEmpty()) {
            pickCard = true;
            CenterGridCardSelectScreen.centerGridSelect = true;
            if (bunch > 0) {
                AbstractDungeon.gridSelectScreen.open(group, 1, ("Choose an Action. " + bunch + " choices remaining."), false);
            } else {
                AbstractDungeon.gridSelectScreen.open(group, 1, ("Choose an Action. Last choice."), false);
            }
        } else if ((pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) || AbstractDungeon.player.hasPower(IndecisivePower.POWER_ID)) {
            OctoChoiceCard cardChoice = new OctoChoiceCard("null", "null", makeCardPath("AttackShiftingOctopus.png"), "You should never see this.", AbstractCard.CardType.ATTACK);
            if (pickCard && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                pickCard = false;
                cardChoice = (OctoChoiceCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                CenterGridCardSelectScreen.centerGridSelect = false;
            }
            for (AbstractPower q : AbstractDungeon.player.powers) {
                if (q instanceof PostChooseSubscriber) {
                    ((PostChooseSubscriber) q).onPostChoose();
                }
            }
            funCard.doChoiceStuff(monster, group, cardChoice);
            OctoMod.finalChecks(group, cardChoice, monster);

            isDone = true;
        } else if (group.isEmpty()) {
            isDone = true;
        }
        tickDuration();
    }
}