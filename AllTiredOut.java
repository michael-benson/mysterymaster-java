package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-07
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class AllTiredOut extends Puzzle {
	AllTiredOut() {
		// Properties.
		myName = "AllTiredOut";
		myTitle = "All Tired Out";

		// Nouns.
		NounType slots = addNounType("Order");
		Noun slot1 = slots.addNoun("1st");
		Noun slot2 = slots.addNoun("2nd");
		Noun slot3 = slots.addNoun("3rd");
		Noun slot4 = slots.addNoun("4th");
		Noun slot5 = slots.addNoun("5th");

		NounType names = addNounType("Customer");
		Noun ethan = names.addNoun("Ethan");
		Noun grace = names.addNoun("Grace");
		Noun jeff = names.addNoun("Jeff");
		Noun lisa = names.addNoun("Lisa");
		Noun marge = names.addNoun("Marge");

		NounType wants = addNounType("Wanted");
		Noun alignment = wants.addNoun("alignment");
		Noun chains = wants.addNoun("chains");
		Noun jack = wants.addNoun("jack");
		Noun shocks = wants.addNoun("shock absorbers", "Shocks");
		Noun tires = wants.addNoun("tires");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link justAhead = addLink("just ahead of", slots);
		justAhead.f = SmartLink.getIsLessBy(1);

		Link threeAhead = addLink("three places ahead of", slots);
		threeAhead.f = SmartLink.getIsLessBy(3);

		Link nextTo = addLink("next to", slots);
		nextTo.f = SmartLink.getIsNextTo();

		// Facts
		addFact("1", getList(ethan, slot3, chains), IsNot, With);
		addFact("2", jack, Is, justAhead, lisa);
		addFact("3", slot2, IsNot, With, getList(ethan, jeff));
		addFact("4", tires, Is, threeAhead, alignment);
		addFact("6", jeff, Is, justAhead, shocks);

		// Rules.
		Rule rule1 = addRule("5", "Marge wasn't the second of the three women in line.");
		rule1.f = smartRule.getIsNotBetween(rule1, slots, marge, grace, lisa);

		Rule rule2 = addRule("7", "Grace stood next to at least one man in line.");
		rule2.f = smartRule.getIsRelated(rule2, grace, nextTo, getList(ethan, jeff));

		// Solution.
		answer = new int[][]{{4, 1, 2, 3, 0}, {1, 4, 2, 3, 0}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
		String lname = link == With ? " " : " " + link.name + " ";

		// Types: 1=Order, 2=Customer, 3=Wanted.
		switch (noun1.type.num) {
			case 1:
				msg = "The " + noun1.name + " person in line ";
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg += verb.name + lname + noun2.name;
						break;
					case 3:
						msg += (verb == Is ? "did" : "did not") + " buy the " + noun2.name;
						break;
				}
				break;
			case 2:
				msg = noun1.name + " ";
				switch (noun2.type.num) {
					case 1:
						msg += verb.name + lname + "the " + noun2.name + " person in line";
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg += (verb == Is ? "did" : "did not") + " buy the " + noun2.name;
						else
							msg += verb.name + " " + link.name + " the person who bought the " + noun2.name;
						break;
				}
				break;
			case 3:
				msg = "The person who bought the " + noun1.name + " " + verb.name + lname;
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg += noun2.name;
						break;
					case 3:
						msg += "the one who bought the " + noun2.name;
						break;
				}
				break;
		}
		return msg + ".";
	}
}
