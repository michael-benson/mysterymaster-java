package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class FancyFootwear extends Puzzle {
	FancyFootwear() {
		// Properties.
		myName = "FancyFootwear";
		myTitle = "Fancy Footwear";

		// Nouns.
		NounType slots = addNounType("Pair");
		Noun slotA = slots.addNoun("A");
		Noun slotB = slots.addNoun("B");
		Noun slotC = slots.addNoun("C");
		Noun slotD = slots.addNoun("D");
		Noun slotE = slots.addNoun("E");
		Noun slotF = slots.addNoun("F");
		Noun slotG = slots.addNoun("G");
		Noun slotH = slots.addNoun("H");
		Noun slotI = slots.addNoun("I");
		Noun slotJ = slots.addNoun("J");

		NounType footwear = addNounType("Footwear");
		Noun boots = footwear.addNoun("boots");
		Noun moccasins = footwear.addNoun("moccasins");
		Noun sneakers = footwear.addNoun("sneakers");
		Noun slippers = footwear.addNoun("slippers");
		Noun huaraches = footwear.addNoun("huaraches");
		Noun oxfords = footwear.addNoun("oxfords");
		Noun loafers = footwear.addNoun("loafers");
		Noun galoshes = footwear.addNoun("galoshes");
		Noun sandals = footwear.addNoun("sandals");
		Noun pumps = footwear.addNoun("pumps");

		// Links.
		Link directlyBelow = addLink("directly below", slots);
		directlyBelow.f = (noun1, noun2) -> noun1.num == noun2.num + 5 ? Is : IsNot;

		Link twoLeftOf = addLink("two places to the left of", slots);
		twoLeftOf.f = (noun1, noun2) ->
		(noun2.num < 6 && noun1.num == noun2.num - 2) || (noun2.num > 7 && noun1.num == noun2.num - 2) ? Is : IsNot;

		Link sameShelf = addLink("on the same shelf as", slots);
		sameShelf.f = (noun1, noun2) -> noun1.num < 6 && noun2.num < 6 || noun1.num > 5 && noun2.num > 5 ? Is : IsNot;

		Link immediatelyLeftOf = addLink("immediately to the left of", slots);
		immediatelyLeftOf.f = (noun1, noun2) -> noun1.num != 5 && noun1.num == noun2.num - 1 ? Is : IsNot;

		// Facts.
		addFact("1", moccasins, Is, With, slotE);
		addFact("2", sneakers, Is, With, slotI);
		addFact("3", slippers, Is, directlyBelow, huaraches);
		addFact("3", huaraches, IsNot, With, getList(slotA, slotE));
		addFact("4", oxfords, IsNot, With, getList(slotF, slotG, slotH, slotI, slotJ));
		addFact("4", oxfords, Is, twoLeftOf, loafers);
		addFact("5", galoshes, Is, sameShelf, sandals);
		addFact("5", sandals, Is, immediatelyLeftOf, pumps);

		// Solution.
		answer = new int[][]{{0, 5, 4, 6, 1, 8, 9, 3, 2, 7}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Type: 1=Pair, 2=Footwear
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
				}
				break;
			case 2:
				msg = "The " + noun1.name + " " + (verb == Is ? "are" : "are not") + " ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += "pair " + noun2.name;
						break;
					case 2:
						msg += link.name + " the " + noun2.name;
						break;
				}
				break;
		}
		return msg + ".";
	}
}
