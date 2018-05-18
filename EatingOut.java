package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class EatingOut extends Puzzle {
	EatingOut() {
		// Properties.
		myName = "EatingOut";
		myTitle = "Eating Out";

		// Nouns.
		NounType days = addNounType("Day");
		Noun day03 = days.addNoun("3rd");
		Noun day10 = days.addNoun("10th");
		Noun day17 = days.addNoun("17th");
		Noun day24 = days.addNoun("24th");
		Noun day31 = days.addNoun("31st");

		NounType members = addNounType("Member");
		Noun husband = members.addNoun("husband");
		Noun wife = members.addNoun("wife");
		Noun daughterO = members.addNoun("oldest daughter", "Oldest");
		Noun daughterY = members.addNoun("youngest daughter", "Youngest");
		Noun son = members.addNoun("son");

		NounType names = addNounType("Name");
		Noun lily = names.addNoun("Lily");
		Noun patrick = names.addNoun("Patrick");
		Noun christine = names.addNoun("Christine");
		Noun lola = names.addNoun("Lola");
		Noun frederick = names.addNoun("Frederick");

		NounType foods = addNounType("Food");
		Noun steak = foods.addNoun("steak");
		Noun seafood = foods.addNoun("seafood");
		Noun pizza = foods.addNoun("pizza");
		Noun chicken = foods.addNoun("chicken");
		Noun burger = foods.addNoun("burger");

		NounType restaurants = addNounType("Restaurant");
		Noun montys = restaurants.addNoun("Monty's Meal-o-rama", "Monty's");
		Noun eds = restaurants.addNoun("Ed's Eatery", "Ed's");
		Noun carlottas = restaurants.addNoun("Carlotta's Cafe", "Carlotta's");
		Noun helens = restaurants.addNoun("Helen's Hangout", "Helen's");
		Noun johnnys = restaurants.addNoun("Johnny's Joint", "Johnny's");

		// Verbs.
		IsNot.name = "was not";
		Is.name = "was";

		// Links.
		Link before = addLink("sometime before", days);
		before.f = SmartLink.getIsLessThan(0);

		Link oneWeekBefore = addLink("one week before", days);
		oneWeekBefore.f = SmartLink.getIsLessBy(1);

		Link twoWeeksBefore = addLink("two weeks before", days);
		twoWeeksBefore.f = SmartLink.getIsLessBy(2);

		Link threeWeeksBefore = addLink("three weeks before", days);
		threeWeeksBefore.f = SmartLink.getIsLessBy(3);

		// Facts.
		// Note: I could also add the appropriate first names into each list.
		List<Noun> guys = getList(husband, son);
		List<Noun> gals = getList(wife, daughterO, daughterY);

		addFact("1", husband, Is, With, steak);
		addFact("1", steak, Is, threeWeeksBefore, lily);

		addFact("2", getList(patrick, montys, seafood, daughterO, pizza), IsNot, With);
		addFact("2", guys, IsNot, With, seafood);
		addFact("2", gals, IsNot, With, pizza);

		addFact("3", christine, Is, With, wife);
		addFact("3", christine, Is, twoWeeksBefore, daughterY);
		addFact("3", husband, Is, oneWeekBefore, christine);

		addFact("4", guys, IsNot, With, day10);
		addFact("4", lola, IsNot, With, day10);
		addFact("4", getList(montys, eds, johnnys), IsNot, With, getList(day10, lola));

		addFact("5", chicken, Is, before, burger);
		addFact("5", chicken, IsNot, With, getList(eds, carlottas));

		addFact("6", frederick, Is, With, getList(son, johnnys));
		addFact("6", helens, Is, twoWeeksBefore, johnnys);
		addFact("6", getList(frederick, husband, wife), IsNot, With, helens);

		// Solution.
		answer = new int[][]{{0, 1, 2, 3, 4}, {1, 2, 3, 0, 4}, {0, 1, 3, 4, 2}, {1, 2, 3, 0, 4}};
	}
	
	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Day, 2=Member, 3=Name, 4=Food, 5=Restaurant.
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
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The " + noun1.name + " " + (verb == Is ? "chose" : "did not choose") + " on the " + noun2.name;
						break;
					case 2: break;
					case 3:
						if (link != With)
							msg = "The " + noun1.name + " " + (verb == Is ? "chose" : "did not choose") + " the restaurant " + link.name + " " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = "The " + noun1.name + "'s favorite food " + verb.name + " " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = "The " + noun1.name + " " + (verb == Is ? "chose" : "did not choose") + " " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + "'s choice " + verb.name + " on the " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the " + noun2.name;
						else
							msg = noun1.name + " " + (verb == Is ? "chose" : "did not choose") + " the restaurant " + link.name + " the " + noun2.name;
						break;
					case 3: break;
					case 4:
						if (link == With)
							msg = noun1.name + "'s favorite food " + verb.name + " " + noun2.name;
						break;
					case 5:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "chose" : "did not choose") + " " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the favorite food of the " + noun2.name;
						break;
					case 3:
						if (link != With)
							msg = "The restaurant serving " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name + "'s choice";
						break;
					case 4:
						msg = "The " + noun1.name + " restaurant " + verb.name + " chosen " + link.name + " the " + noun2.name + " restaurant";
						break;
					case 5:
						if (link == With)
							msg = "The " + noun1.name + " restaurant " + verb.name + " " + noun2.name;
						break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " chosen on the " + noun2.name;
						break;
					case 2:
						if (link == With)
							msg = noun1.name + " " + verb.name + " the favorite restaurant of the " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + verb.name + " chosen by " + noun2.name;
						break;
					case 4:
						if (link == With)
							msg = noun1.name + " " + (verb == Is ? "features" : "does not feature") + " " + noun2.name;
						break;
					case 5:
						msg = noun1.name + " " + verb.name + " chosen " + link.name + " " + noun2.name;
						break;
				}
				break;
		}

		return msg + ".";
	}
}
