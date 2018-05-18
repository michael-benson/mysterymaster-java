package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class ShooOutOfTheGarden extends Puzzle {
	ShooOutOfTheGarden() {
		// Properties.
		myName = "ShooOutOfTheGarden";
		myTitle = "Shoo! Out Of The Garden!";

		// Nouns.
		NounType weeks = addNounType("Week");
		Noun week1 = weeks.addNoun("1st");
		Noun week2 = weeks.addNoun("2nd");
		Noun week3 = weeks.addNoun("3rd");
		Noun week4 = weeks.addNoun("4th");
		Noun week5 = weeks.addNoun("5th");

		NounType husbands = addNounType("Husband");
		Noun pat = husbands.addNoun("Pat");
		Noun bob = husbands.addNoun("Bob");
		Noun dan = husbands.addNoun("Dan");
		Noun tom = husbands.addNoun("Tom");
		Noun hal = husbands.addNoun("Hal");

		NounType wives = addNounType("Wife");
		Noun mary = wives.addNoun("Mary");
		Noun joan = wives.addNoun("Joan");
		Noun rose = wives.addNoun("Rose");
		Noun kate = wives.addNoun("Kate");
		Noun nan = wives.addNoun("Nan");

		NounType lastNames = addNounType("Surname");
		Noun smith = lastNames.addNoun("Smith");
		Noun blake = lastNames.addNoun("Blake");
		Noun clark = lastNames.addNoun("Clark");
		Noun gray = lastNames.addNoun("Gray");
		Noun white = lastNames.addNoun("White");

		NounType animals = addNounType("Animal");
		Noun raccoon = animals.addNoun("raccoon");
		Noun deer = animals.addNoun("deer");
		Noun rabbit = animals.addNoun("rabbit");
		Noun skunk = animals.addNoun("skunk");
		Noun woodchuck = animals.addNoun("woodchuck");

		NounType gardens = addNounType("Garden");
		Noun smiths = gardens.addNoun("Smith's");
		Noun blakes = gardens.addNoun("Blake's");
		Noun clarks = gardens.addNoun("Clark's");
		Noun grays = gardens.addNoun("Gray's");
		Noun whites = gardens.addNoun("White's");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link before = addLink("before", weeks);
		before.f = SmartLink.getIsLessThan(0);

		Link justBefore = addLink("just before", weeks);
		justBefore.f = SmartLink.getIsLessBy(1);

		Link twoWeeksBefore = addLink("2 weeks before", weeks);
		twoWeeksBefore.f = SmartLink.getIsLessBy(2);

		// Facts. Surname is singular (Blake), while the garden owner is possesive (Blake's).
		addFactsInSequence("1", getList(joan, deer, smith, blakes), Is, before);
		addFactsInSequence("2", getList(rabbit, bob, rose, clark), Is, before);
		addFactsInSequence("3", getList(skunk, grays, kate), Is, before);

		addFactsNotConsecutive("4", getList(woodchuck, rabbit, deer), justBefore);

		addFact("5", white, IsNot, With, whites);
		addFact("5", white, Is, twoWeeksBefore, dan);

		addFactsOneToOne("6", getList(tom, bob), IsNot, With, getList(woodchuck, skunk));

		addFactsInSequence("7", getList(hal, nan, blake), Is, justBefore);

		// Rules.
		Rule rule1 = addRule("5", "A couple found an animal in their own garden.");
		rule1.f = (mark) -> {
			int rs = 0;

			// Violation if all couples are with a garden, but no couple has their own garden.
			boolean ok = false;
			for (Noun lastName : lastNames.nouns) {
				Noun garden = Mark.getPairNoun(lastName, gardens);
				if (garden == null || lastName.num == garden.num) {
					ok = true;
					break;
				}
			}
			if (!ok) return -1;

			// Trigger if only one couple can have their own garden.
			Noun own = null;
			for (Noun lastName : lastNames.nouns) {
				Noun garden = gardens.nouns.get(lastName.num - 1);
				Verb verb = solver.getGridVerb(lastName, garden);
				if (verb == Is) return rs;
				if (verb == Maybe) {
					if (own != null) return rs;
					own = lastName;
				}
			}
			if (own != null) {
				String msg = "Only one couple can find an animal in their own garden.";
				Noun garden = gardens.nouns.get(own.num - 1);
				rs = solver.addMarkByRule(mark, rule1, ' ', own, Is, garden, msg);
				if (rs != 0) return rs;
			}

			return rs;
		};

		// Solution.
		answer = new int[][]{{4, 3, 1, 2, 0}, {1, 4, 0, 2, 3}, {3, 4, 1, 0, 2}, {2, 3, 1, 0, 4}, {4, 2, 3, 0, 1}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Week, 2=Husband, 3=Wife, 4=Surname, 5=Animal, 6=Garden
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
					case 5: break;
					case 6: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link != With)
							msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 4: break;
					case 5:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "found" : "did not find") + " the " + noun2.name;
						break;
					case 6: break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4:
						if (link != With)
							msg = noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name + " couple";
						break;
					case 5:
						if (link != With)
							msg = noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name;
						break;
					case 6: break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link != With)
							msg = "The " + noun1.name + " couple " + (verb == Is ? "were" : "were not") + " " + link.name + " " + noun2.name;
						break;
					case 3: break;
					case 4: break;
					case 5: break;
					case 6:
						if (link == With) {
							if (noun1.num == noun2.num)
								msg = "The " + noun1.name + " couple " + (verb == Is ? "found" : "did not find") + " an animal in their own garden";
							else
								msg = "The " + noun1.name + " couple " + (verb == Is ? "found" : "did not find") + " the animal in the " + noun2.name + " garden";
						}
						else
							msg = "The " + noun1.name + " couple " + (verb == Is ? "were" : "were not") + " " + link.name + " the animal in the " + noun2.name + " garden";
						break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " found in the " + noun2.name + " week";
						break;
					case 2:
						if (link != With)
							msg = "The " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link != With)
							msg = "The " + noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name + " couple";
						break;
					case 5: break;
					case 6:
						if (link != With)
							msg = "The " + noun1.name + " " + verb.name + " " + link.name + " the animal in the " + noun2.name + " garden"; 
						break;
				}
				break;
			case 6:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link != With)
							msg = "The animal in the " + noun1.name + " garden " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 4: break;
					case 5: break;
					case 6: break;
				}
				break;
		}

		return msg + ".";
	}
}
