package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class Witnesses extends Puzzle {
	Witnesses() {
		// Properties.
		myName = "Witnesses";
		myTitle = "Witnesses";

		// Nouns.
		NounType slots = addNounType("Order");
		Noun slot01 = slots.addNoun("1st");
		Noun slot02 = slots.addNoun("2nd");
		Noun slot03 = slots.addNoun("3rd");
		Noun slot04 = slots.addNoun("4th");
		Noun slot05 = slots.addNoun("5th");
		Noun slot06 = slots.addNoun("6th");
		Noun slot07 = slots.addNoun("7th");
		Noun slot08 = slots.addNoun("8th");
		Noun slot09 = slots.addNoun("9th");
		Noun slot10 = slots.addNoun("10th");

		NounType firstNames = addNounType("First Name");
		Noun anne = firstNames.addNoun("Anne");
		Noun betty = firstNames.addNoun("Betty");
		Noun diane = firstNames.addNoun("Diane");
		Noun frank = firstNames.addNoun("Frank");
		Noun glenn = firstNames.addNoun("Glenn");
		Noun john = firstNames.addNoun("John");
		Noun kathy = firstNames.addNoun("Kathy");
		Noun mark = firstNames.addNoun("Mark");
		Noun mary = firstNames.addNoun("Mary");
		Noun sandra = firstNames.addNoun("Sandra");

		NounType lastNames = addNounType("Last Name");
		Noun anderson = lastNames.addNoun("Anderson");
		Noun ducklow = lastNames.addNoun("Ducklow");
		Noun fuller = lastNames.addNoun("Fuller");
		Noun mcNeil = lastNames.addNoun("McNeil");
		Noun miller = lastNames.addNoun("Miller");
		Noun olson = lastNames.addNoun("Olson");
		Noun simpson = lastNames.addNoun("Simpson");
		Noun thompson = lastNames.addNoun("Thompson");
		Noun williams = lastNames.addNoun("Williams");
		Noun zimmer = lastNames.addNoun("Zimmer");

		NounType occupations = addNounType("Occupation");
		Noun accountant = occupations.addNoun("accountant");
		Noun author = occupations.addNoun("author");
		Noun dentist = occupations.addNoun("dentist");
		Noun mechanic = occupations.addNoun("mechanic");
		Noun musician = occupations.addNoun("musician");
		Noun pilot = occupations.addNoun("pilot");
		Noun programmer = occupations.addNoun("programmer");
		Noun secretary = occupations.addNoun("secretary");
		Noun teacher = occupations.addNoun("teacher");
		Noun teller = occupations.addNoun("bank teller");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link beforeButNotJust = addLink("before, but not just before", slots);
		beforeButNotJust.f = SmartLink.getIsLessThan(1);

		Link justBefore = addLink("just before", slots);
		justBefore.f = SmartLink.getIsLessBy(1);

		// Facts.
		// Intro. At a recent trial in Justiceville, the prosecutor, Mr. Jones, called five witnesses to the stand,
		// after which the defense attorney, Ms. Smith, called five more witnesses.
		// From information and the clues, can you determine the first name (one is Diane) and last name (one is Anderson)
		// and occupation (one is an accountant) of each of the ten witnesses and the order in which they testified?

		List<Noun> slots1 = getList(slot01, slot02, slot03, slot04, slot05);
		List<Noun> slots2 = getList(slot06, slot07, slot08, slot09, slot10);

		List<Noun> guys = getList(frank, glenn, john, mark);
		List<Noun> gals = getList(anne, betty, diane, kathy, mary, sandra);

		List<Noun> witnesses1 = getList(kathy, mark, ducklow, olson, programmer);
		addFact("1", witnesses1, IsNot, With);
		addFact("1", witnesses1, IsNot, With, slots2);
		addFact("1", ducklow, IsNot, With, gals);
		addFact("1", olson, IsNot, With, guys);

		List<Noun> witnesses2 = getList(john, sandra, fuller, simpson, secretary);
		addFact("2", witnesses2, IsNot, With);
		addFact("2", witnesses2, IsNot, With, slots1);
		addFact("2", fuller, IsNot, With, guys);
		addFact("2", simpson, IsNot, With, gals);

		addFact("3", getList(mark, kathy), IsNot, With, pilot);

		addFact("4", mary, Is, justBefore, miller);
		addFact("4", miller, Is, beforeButNotJust, author);

		addFact("5", john, IsNot, With, teacher);

		addFact("6", mary, IsNot, With, thompson);
		addFact("6", thompson, Is, justBefore, dentist);

		addFact("7", mechanic, IsNot, With, slot01);
		addFact("7", mechanic, Is, beforeButNotJust, mcNeil);

		addFactsInSequence("8", getList(frank, teacher, fuller), Is, justBefore);

		addFact("9", teller, Is, beforeButNotJust, zimmer);

		addFact("10", anne, Is, With, musician);
		addFactsInSequence("10", getList(pilot, anne, williams), Is, justBefore);

		addFact("11", programmer, IsNot, justBefore, secretary);

		addFact("12", getList(glenn, betty), IsNot, With, slots2);

		addFact("13", mark, Is, beforeButNotJust, betty);

		// Solution.
		answer = new int[][]{{6, 1, 4, 9, 5, 3, 7, 8, 2, 0}, {6, 0, 8, 1, 2, 9, 5, 4, 7, 3}, {8, 6, 1, 3, 9, 2, 7, 0, 5, 4}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Order, 2=First Name, 3=Last Name, 4=Occupation
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name + " witness";
						break;
					case 2:
						msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						else
							msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name;
						else
							msg = noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name + " witness";
						break;
					case 2:
						if (link == With)
							msg = noun1.name + "'s first name " + verb.name + " " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name;
						else
							msg = noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The " + noun1.name + " " + verb.name + " the " + noun2.name + " witness";
						break;
					case 2:
						if (link != With)
							msg = "The " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3:
						if (link != With)
							msg = "The " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 4:
						msg = "The " + noun1.name + " " + verb.name + " " + link.name + " the " + noun2.name;
						break;
				}
				break;
		}

		return msg + ".";
	}
}
