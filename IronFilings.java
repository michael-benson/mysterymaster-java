package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class IronFilings extends Puzzle {
	IronFilings() {
		// Properties.
		myName = "IronFilings";
		myTitle = "Iron Filings";

		// Nouns.
		NounType drawers = addNounType("Drawer");
		Noun drawerA = drawers.addNoun("A");
		Noun drawerB = drawers.addNoun("B");
		Noun drawerC = drawers.addNoun("C");
		Noun drawerD = drawers.addNoun("D");

		NounType documents = addNounType("Document");
		Noun customer = documents.addNoun("Customer Orders", "Customer");
		Noun personnel = documents.addNoun("Personnel Records", "Personnel");
		Noun financial = documents.addNoun("Financial Statements", "Financial");
		Noun supply = documents.addNoun("Supply Requisitions", "Supply");

		NounType folders = addNounType("Folder");
		Noun green = folders.addNoun("green");
		Noun red = folders.addNoun("red");
		Noun yellow = folders.addNoun("yellow");
		Noun blue = folders.addNoun("blue");

		// Links.
		Link above = addLink("above", drawers);
		above.f = SmartLink.getIsLessThan(0);

		// Facts.
		addFact("1", customer, Is, above, green);
		addFact("1", green, Is, above, personnel);
		addFact("2", drawerD, IsNot, With, red);
		addFact("3", personnel, IsNot, With, yellow);
		addFact("4", financial, Is, above, supply);
		addFact("4", blue, Is, above, financial);

		// Solution.
		answer = new int[][]{{0, 2, 1, 3}, {3, 0, 1, 2}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
		String vname = verb == Is ? "are" : "are not";

		// Types: 1=Drawer, 2=Document, 3=Folder
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						if (link == With)
							msg = "Drawer " + noun1.name + " " + (verb == Is ? "contains" : "does not contain") + " " + noun2.name + " folders";
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + " " + vname + " " + link.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + vname + " the " + noun2.name + " folders";
						else
							msg = noun1.name + " " + vname + " " + link.name + " the " + noun2.name + " folders";
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link != With)
							msg = "The " + noun1.name + " folders " + vname + " " + link.name + " " + noun2.name;
						break;
					case 3: break;
				}
				break;
		}
		return msg + ".";
	}
}
