package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class AnglingForSuccess extends Puzzle {
	AnglingForSuccess() {
		// Properties.
		myName = "AnglingForSuccess";
		myTitle = "Angling For Success";

		// Nouns.
		NounType days = addNounType("Day");
		Noun monday = days.addNoun("Monday");
		Noun tuesday = days.addNoun("Tuesday");
		Noun wednesday = days.addNoun("Wednesday");
		Noun thursday = days.addNoun("Thursday");
		Noun friday = days.addNoun("Friday");

		NounType knots = addNounType("Knot");
		Noun barrel = knots.addNoun("barrel");
		Noun hitch = knots.addNoun("hitch");
		Noun bend = knots.addNoun("fisherman's bend", "Bend");
		Noun nail = knots.addNoun("nail");
		Noun turtle = knots.addNoun("turtle");

		NounType baits = addNounType("Bait");
		Noun dough = baits.addNoun("dough");
		Noun corn = baits.addNoun("corn kernel");
		Noun cut = baits.addNoun("cut");
		Noun minnow = baits.addNoun("minnow");
		Noun worm = baits.addNoun("worm");

		NounType lures = addNounType("Lure");
		Noun jig = lures.addNoun("jig");
		Noun fly = lures.addNoun("fly");
		Noun plug = lures.addNoun("plug");
		Noun spinner = lures.addNoun("spinner");
		Noun streamer = lures.addNoun("streamer");

		NounType amounts = addNounType("Caught");
		Noun four = amounts.addNoun("4");
		Noun fiveA = amounts.addNoun("5a");
		Noun fiveB = amounts.addNoun("5b");
		Noun sixA = amounts.addNoun("6a");
		Noun sixB = amounts.addNoun("6b");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link laterThan = addLink("later in the week than", days);
		laterThan.f = SmartLink.getIsMoreThan(0);

		Link moreThan = addLink("more fish than", amounts);
		moreThan.f = (noun1, noun2) ->
		((noun1.num == 2 || noun1.num == 3) && noun2.num == 1) ||
		((noun1.num == 4 || noun1.num == 5) && noun2.num < 4) ? Is : IsNot;

		// Facts.
		addFact("0", fiveB, Is, laterThan, fiveA);
		addFact("0", sixB, Is, laterThan, sixA);
		addFact("1", corn, Is, laterThan, spinner);
		addFact("1", spinner, Is, laterThan, worm);
		addFact("1", worm, IsNot, With, monday);
		addFact("1", corn, IsNot, With, jig);
		addFact("2", barrel, Is, With, fly);
		addFact("2", getList(minnow, cut), IsNot, With, barrel);
		addFact("2", nail, IsNot, With, cut);
		addFact("3", nail, IsNot, With, corn);
		addFact("3", nail, Is, moreThan, streamer);
		addFact("3", minnow, Is, moreThan, nail);
		addFact("4", jig, IsNot, With, getList(bend, turtle, monday, tuesday, nail));
		addFact("4", nail, Is, laterThan, worm);
		addFact("5", corn, IsNot, With, getList(friday, streamer, fly));
		addFact("5", streamer, IsNot, With, turtle);
		addFact("5", fly, IsNot, With, getList(sixA, sixB));

		// Solution.
		answer = new int[][]{{2, 0, 3, 4, 1}, {2, 4, 0, 1, 3}, {4, 1, 3, 2, 0}, {0, 1, 2, 3, 4}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Day, 2=Knot, 3=Bait, 4=Lure, 5=Amount
		// Links: 0=With, 1=laterThan, 2=moreThan
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5: break;
				}
				break;
			case 2:
				msg = "The " + noun1.name + " knot ";
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg += verb.name + " used on the same day as";
						else if (link.num == 1)
							msg += verb.name + " used " + link.name;
						msg += " the " + noun2.name + " bait";
						break;
					case 4:
						if (link == With)
							msg += verb.name + " used on the same day as";
						else
							msg += (verb == Is ? "caught" : "did not catch") + " " + link.name;
						msg += " the " + noun2.name + " lure";
						break;
					case 5: break;
				}
				break;
			case 3:
				msg = "The " + noun1.name + " bait ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += verb.name + " used on " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg += verb.name + " used on the same day as";
						else
							msg += (verb == Is ? "caught" : "did not catch") + " " + link.name;
						msg += " the " + noun2.name + " knot";
						break;
					case 3:
						break;
					case 4:
						if (link == With)
							msg += verb.name + " used on the same day as";
						else
							msg += verb.name + " used " + link.name;
						msg += " the " + noun2.name + " lure";
						break;
					case 5: break;
				}
				break;
			case 4:
				msg = "The " + noun1.name + " lure ";
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += verb.name + " used on " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg += verb.name + " used on the same day as the " + noun2.name + " knot";
						break;
					case 3:
						if (link == With)
							msg += "";
						else
							msg += verb.name + " used " + link.name + " the " + noun2.name + " bait";
						break;
					case 4: break;
					case 5:
						if (link == With)
							msg += (verb == Is ? "caught" : "did not catch") + " " + noun2.name + " fish";
						break;
				}
				break;
			case 5:
				switch (noun1.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5:
						if (link.num == 1)
							msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
				}
				break;
		}

		return msg + ".";
	}
}
