package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class ADayAtTheZoo extends Puzzle {
	ADayAtTheZoo() {
		// Properties.
		myName = "ADayAtTheZoo";
		myTitle = "A Day At The Zoo";

		// Nouns.
		NounType slots = addNounType("Order");
		Noun slot1 = slots.addNoun("1st");
		Noun slot2 = slots.addNoun("2nd");
		Noun slot3 = slots.addNoun("3rd");
		Noun slot4 = slots.addNoun("4th");
		Noun slot5 = slots.addNoun("5th");

		NounType names = addNounType("Child");
		Noun johan = names.addNoun("Johan");
		Noun kerry = names.addNoun("Kerry");
		Noun lani = names.addNoun("Lani");
		Noun mario = names.addNoun("Mario");
		Noun naomi = names.addNoun("Naomi");

		NounType animals = addNounType("Animal");
		Noun chimps = animals.addNoun("chimps");
		Noun giraffes = animals.addNoun("giraffes");
		Noun lions = animals.addNoun("lions");
		Noun tigers = animals.addNoun("tigers");
		Noun zebras = animals.addNoun("zebras");

		NounType balloons = addNounType("Balloon");
		Noun hearts = balloons.addNoun("hearts");
		Noun dots = balloons.addNoun("polka dots");
		Noun rainbow = balloons.addNoun("rainbow");
		Noun stripes = balloons.addNoun("stripes");
		Noun swirls = balloons.addNoun("swirls");

		// Links.
		Link justBehind = addLink("just behind", slots);
		justBehind.f = SmartLink.getIsMoreBy(1);

		// Facts.
		List<Noun> guys = getList(johan, mario);

		addFact("1", tigers, Is, With, slot3);
		addFact("1", tigers, IsNot, With, naomi);
		addFact("1", guys, IsNot, With, slot3);
		addFact("1", slot3, IsNot, With, getList(hearts, stripes));

		addFact("2", johan, IsNot, With, slot5);
		addFact("2", johan, Is, justBehind, mario);
		addFact("2", lani, IsNot, With, getList(tigers, zebras));

		addFact("3", getList(lani, naomi), IsNot, With, getList(hearts, stripes));
		addFact("3", mario, IsNot, With, stripes);

		addFact("4", slot4, Is, With, zebras);
		addFact("4", slot4, IsNot, With, swirls);
		addFact("4", dots, Is, With, lions);
		addFact("4", johan, IsNot, With, giraffes);

		// Solution.
		answer = new int[][]{{3, 0, 1, 4, 2}, {1, 0, 3, 4, 2}, {0, 3, 4, 2, 1}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		//print(myName + " sayFact noun1=" + noun1 + " verb=" + verb + " link=" + link + " noun2=" + noun2);
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
		String lname = link == With ? " " : " " + link.name + " ";

		// Types: 1=Order, 2=Child, 3=Animal, 4=Balloon
		switch (noun1.type.num) {
			case 1:
				msg = "The " + noun1.name + " child in line ";
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg += verb == Is ? "prefers" : "does not prefer";
						else
							msg += verb.name + lname + "the child who prefers";
						msg += " the " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg += (verb == Is ? "did" : "did not") + " receive";
						else
							msg += verb.name + lname + "the one with";
						msg += " the " + noun2.name + " balloon";
						break;
				}
				break;
			case 2:
				msg = noun1.name + " ";
				switch (noun2.type.num) {
					case 1:
						msg += verb.name + lname + "the " + noun2.name + " one in line";
						break;
					case 2:
						msg += verb.name + lname + noun2.name; break;
					case 3:
						if (link == With)
							msg += verb == Is ? "prefers" : "does not prefer";
						else
							msg += verb.name + lname + "the child who prefers";
						msg += " the " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg += verb == Is ? "received" : "did not receive";
						else
							msg += verb.name + lname + "the child with";
						msg += " the " + noun2.name + " balloon";
						break;
				}
				break;
			case 3:
				msg = "The child who prefers the " + noun1.name + " " + verb.name + lname;
				switch (noun2.type.num) {
					case 1:
						msg += noun2.name + " in line";
						break;
					case 2:
						msg += noun2.name;
						break;
					case 3: break;
					case 4: break;
				}
				break;
			case 4:
				msg = "The child with the " + noun1.name + " balloon ";
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg += verb == Is ? "prefers" : "does not prefer";
						else
							msg += verb.name + lname + "the child who prefers";
						msg += " the " + noun2.name;
						break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
