package theOctopus.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import theOctopus.characters.TheOctopus;

import java.util.UUID;

public class WishAction extends AbstractGameAction {

    private UUID uuid;

    public WishAction(UUID targetUUID) {
        uuid = targetUUID;
        duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            AbstractDungeon.player.masterDeck.group.removeIf(card -> card.uuid == uuid);

            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tmpGroup.group = CardLibrary.getCardList(TheOctopus.Enums.LIBRARY_COLOR);
            tmpGroup.group.removeIf(card -> card.rarity == AbstractCard.CardRarity.RARE);

            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : tmpGroup.group) {
                AbstractCard tmp = c.makeCopy();
                tmp.upgrade();
                group.addToTop(tmp);
            }
            group.sortAlphabetically(true);
            group.sortByRarity(false);
            group.sortByStatus(true);

            AbstractDungeon.gridSelectScreen.open(group, 1, "Pick thy dish, scoundrel!", false, false, false, false);
            tickDuration();
            return;
        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            AbstractCard realCard = card.makeStatEquivalentCopy();
            AbstractDungeon.player.masterDeck.group.add(realCard);
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(realCard.makeSameInstanceOf(), false));
        }
        tickDuration();
    }
}