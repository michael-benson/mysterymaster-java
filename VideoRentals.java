package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class VideoRentals extends Puzzle {
	VideoRentals() {
		// Properties.
		myName = "VideoRentals";
		myTitle = "Video Rentals";

		// Nouns.
		NounType days = addNounType("Day");
		Noun friday = days.addNoun("Friday");
		Noun saturday = days.addNoun("Saturday");
		Noun sunday = days.addNoun("Sunday");
		Noun monday = days.addNoun("Monday");

		NounType names = addNounType("Person");
		Noun max = names.addNoun("Max");
		Noun kristen = names.addNoun("Kristen");
		Noun natalie = names.addNoun("Natalie");
		Noun jeremy = names.addNoun("Jeremy");

		NounType genres = addNounType("Type");
		Noun romance = genres.addNoun("romance");
		Noun comedy = genres.addNoun("comedy");
		Noun horror = genres.addNoun("horror");
		Noun animated = genres.addNoun("animated");

		NounType snacks = addNounType("Snack");
		Noun grapes = snacks.addNoun("grapes");
		Noun yogurt = snacks.addNoun("yogurt");
		Noun apples = snacks.addNoun("apples");
		Noun popcorn = snacks.addNoun("popcorn");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link justBefore = addLink("the day before", days);
		justBefore.f = SmartLink.getIsLessBy(1);

		Link before = addLink("sometime before", days);
		before.f = SmartLink.getIsLessThan(0);

		// Facts.
		addFact("1", monday, IsNot, With, getList(comedy, kristen));
		addFact("2", getList(natalie, monday, animated, yogurt), IsNot, With);
		addFact("2", natalie, IsNot, With, horror);
		addFactsInSequence("3", getList(horror, jeremy, apples), Is, justBefore);
		addFact("4", popcorn, Is, before, yogurt);

		// Solution.
		answer = new int[][]{{2, 1, 3, 0}, {1, 2, 3, 0}, {3, 1, 0, 2}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Day, 2=Person, 3=Genre, 4=Snack
		switch (noun1.type.num) {
			case 1:
				msg = noun1.name + "'s movie " + verb.name;
				switch (noun2.type.num) {
					case 1:
						break;
					case 2:
						if (link == With)
							msg += " chosen by " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg +=" " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg += " accompanied by " + noun2.name;
						break;
				}
				break;
			case 2:
				msg = noun1.name + "'s movie " + verb.name;
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += " on " + noun2.name;
						break;
					case 2: break;
					case 3:
						if (link == With)
							msg += " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg += " accompanied by " + noun2.name;
						else
							msg += " shown " + link.name + " the family snacked on " + noun2.name;
						break;
				}
				break;
			case 3:
				msg = "The " + noun1.name + (noun1.num > 2 ? " movie " : " ") + verb.name;
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg += " on " + noun2.name;
						break;
					case 2:
						if (link != With)
							msg += " shown " + link.name + " " + noun2.name + "'s movie";
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg += " accompanied by " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4:
						msg = "The " + noun1.name + " snack " + verb.name + " " + link.name + " the " + noun2.name + " snack";
						break;
				}
				break;
		}

		return msg + ".";
	}
}
