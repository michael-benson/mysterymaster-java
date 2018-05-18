package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-07
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class Overdue extends Puzzle {
	Overdue() {
		// Properties.
		myName = "Overdue";
		myTitle = "Overdue";

		// Nouns.
		NounType days = addNounType("Day");
		Noun wednesday1 = days.addNoun("Wednesday1");
		Noun wednesday2 = days.addNoun("Wednesday2");
		Noun wednesday3 = days.addNoun("Wednesday3");
		Noun thursday1 = days.addNoun("Thursday1");
		Noun thursday2 = days.addNoun("Thursday2");

		NounType firstNames = addNounType("First Name");
		Noun judy = firstNames.addNoun("Judy");
		Noun lisa = firstNames.addNoun("Lisa");
		Noun mary = firstNames.addNoun("Mary");
		Noun mike = firstNames.addNoun("Mike");
		Noun phil = firstNames.addNoun("Phil");

		NounType lastNames = addNounType("Last Name");
		Noun ames = lastNames.addNoun("Ames");
		Noun barr = lastNames.addNoun("Barr");
		Noun jones = lastNames.addNoun("Jones");
		Noun snell = lastNames.addNoun("Snell");
		Noun wicks = lastNames.addNoun("Wicks");

		NounType authors = addNounType("Author");
		Noun conrad = authors.addNoun("Conrad");
		Noun faulkner = authors.addNoun("Faulkner");
		Noun hemingway = authors.addNoun("Hemingway");
		Noun joyce = authors.addNoun("Joyce");
		Noun lawrence = authors.addNoun("Lawrence");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Facts.
		List<Noun> guys = getList(mike, phil);
		List<Noun> gals = getList(judy, lisa, mary);
		List<Noun> wednesdays = getList(wednesday1, wednesday2, wednesday3);
		List<Noun> thursdays = getList(thursday1, thursday2);

		addFactsOneToOne("1", getList(mary, snell, hemingway), Is, With, wednesdays);

		addFact("1", guys, IsNot, With, snell);
		addFact("2", getList(ames, barr, snell), IsNot, With, lawrence);
		addFact("3", judy, IsNot, With, getList(ames, faulkner));
		addFact("4", mike, IsNot, With, getList(faulkner, wicks));
		addFact("5", barr, Is, With, thursday1);
		addFact("5", conrad, Is, With, thursday2);
		addFact("5", guys, IsNot, With, conrad);

		// Rules.
		Rule rule1 = addRule("2", "Wicks and Jones returned their books the same day.");
		List<List<Noun>> array2D = new ArrayList<List<Noun>>() {{
			add(wednesdays);
			add(thursdays);
		}};
		rule1.f = smartRule.getMatchOneList(rule1, getList(wicks, jones), array2D);

		// Solution.
		answer = new int[][]{{2, 0, 3, 4, 1}, {4, 3, 2, 1, 0}, {4, 3, 2, 1, 0}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Day, 2=First Name, 3=Last Name, 4=Author
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
						msg = noun1.name + "'s book " + verb.name + " returned on " + noun2.name;
						break;
					case 2: break;
					case 3:
						msg = noun1.name + "'s last name " + verb.name + " " + noun2.name;
						break;
					case 4:
						msg = noun1.name + "'s novel " + verb.name + " by " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						msg = noun1.name + "'s book " + verb.name + " returned on " + noun2.name;
						break;
					case 2: break;
					case 3:
						msg = noun1.name + "'s book " + verb.name + " returned on " + noun2.name;
						break;
					case 4:
						msg = noun1.name + "'s novel " + verb.name + " by " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						msg = "The " + noun1.name + " novel " + verb.name + " returned on " + noun2.name;
						break;
					case 2: break;
					case 3: break;
					case 4: break;
				}
				break;
		}

		return msg + ".";
	}
}
