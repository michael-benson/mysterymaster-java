package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class FloralDeliveries extends Puzzle {
	FloralDeliveries() {
		// Properties.
		myName = "FloralDeliveries";
		myTitle = "Floral Deliveries";

		// Nouns.
		NounType slots = addNounType("Order");
		Noun slot1 = slots.addNoun("1st");
		Noun slot2 = slots.addNoun("2nd");
		Noun slot3 = slots.addNoun("3rd");
		Noun slot4 = slots.addNoun("4th");
		Noun slot5 = slots.addNoun("5th");

		NounType items = addNounType("Item");
		Noun bonsaiTree = items.addNoun("bonsai tree");
		Noun chocolates = items.addNoun("chocolates");
		Noun dishGarden = items.addNoun("dish garden");
		Noun fruitBasket = items.addNoun("fruit basket");
		Noun roses = items.addNoun("roses");

		NounType party = addNounType("Party");
		Noun adams = party.addNoun("Adams");
		Noun chen = party.addNoun("Chen");
		Noun falk = party.addNoun("Falk");
		Noun grodin = party.addNoun("Grodin");
		Noun martinez = party.addNoun("Martinez");

		NounType events = addNounType("Event");
		Noun anniversary = events.addNoun("anniversary");
		Noun babyShower = events.addNoun("baby shower");
		Noun engagement = events.addNoun("engagement");
		Noun graduation = events.addNoun("graduation");
		Noun wedding = events.addNoun("wedding");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link before = addLink("before", slots);
		before.f = SmartLink.getIsLessThan(0);

		Link justBefore = addLink("just before", slots);
		justBefore.f = SmartLink.getIsLessBy(1);

		// Facts.
		addFact("1", wedding, Is, justBefore, bonsaiTree);
		addFact("1", adams, Is, justBefore, wedding);
		addFact("2", grodin, Is, before, babyShower);
		addFact("3", slot5, Is, With, fruitBasket);
		addFact("4", roses, IsNot, With, graduation);
		addFact("4", falk, Is, before, roses);
		addFact("5", anniversary, Is, before, dishGarden);
		addFact("6", slot2, Is, With, martinez);

		// Solution.
		answer = new int[][]{{1, 2, 0, 4, 3}, {0, 4, 2, 3, 1}, {0, 4, 3, 2, 1}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Order, 2=Item, 3=Party, 4=Event
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = "The " + noun1.name + " delivery " + verb.name + " the " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = "The " + noun1.name + " delivery " + verb.name + " to the " + noun2.name + " party";
						break;
					case 4: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4:
						if (link == With)
							msg = "The delivery of the " + noun1.name + " " + verb.name + " to the " + noun2.name;
						else
							msg = "The delivery of the " + noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link != With)
							msg = "The delivery to the " + noun1.name + " party " + verb.name + " " + link.name + " the delivery of the " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link != With)
							msg = "The delivery to the " + noun1.name + " party " + verb.name + " " + link.name + " the one to the " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link != With)
							msg = "The delivery to the " + noun1.name + " " + verb.name + " " + link.name + " the delivery of the " + noun2.name;
						break;
					case 3: break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
