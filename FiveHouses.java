package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class FiveHouses extends Puzzle {
	FiveHouses() {
		// Properties.
		myName = "FiveHouses";
		myTitle = "Five Houses";

		// Nouns.
		NounType houses = addNounType("House");
		Noun house1 = houses.addNoun("1st");
		Noun house2 = houses.addNoun("2nd");
		Noun house3 = houses.addNoun("3rd");
		Noun house4 = houses.addNoun("4th");
		Noun house5 = houses.addNoun("5th");

		NounType colors = addNounType("Color");
		Noun red = colors.addNoun("red");
		Noun green = colors.addNoun("green");
		Noun white = colors.addNoun("white");
		Noun yellow = colors.addNoun("yellow");
		Noun blue = colors.addNoun("blue");

		NounType nationalities = addNounType("Nationality");
		Noun englishman = nationalities.addNoun("Englishman");
		Noun spaniard = nationalities.addNoun("Spaniard");
		Noun ukrainian = nationalities.addNoun("Ukrainian");
		Noun norwegian = nationalities.addNoun("Norwegian");
		Noun japanese = nationalities.addNoun("Japanese man", "Japanese");

		NounType hobbies = addNounType("Hobby");
		Noun stamps = hobbies.addNoun("stamps");
		Noun antiques = hobbies.addNoun("antiques");
		Noun sings = hobbies.addNoun("singing");
		Noun gardens = hobbies.addNoun("gardening");
		Noun cooking = hobbies.addNoun("cooking");

		NounType pets = addNounType("Pet");
		Noun dogs = pets.addNoun("dogs");
		Noun snails = pets.addNoun("snails");
		Noun fox = pets.addNoun("fox");
		Noun horse = pets.addNoun("horse");
		Noun zebra = pets.addNoun("zebra");

		NounType drinks = addNounType("Drink");
		Noun coffee = drinks.addNoun("coffee");
		Noun tea = drinks.addNoun("tea");
		Noun milk = drinks.addNoun("milk");
		Noun juice = drinks.addNoun("juice");
		Noun water = drinks.addNoun("water");

		// Links.
		Link directlyRightOf = addLink("directly to the right of", houses);
		directlyRightOf.f = SmartLink.getIsMoreBy(1);

		Link nextTo = addLink("next to", houses);
		nextTo.f = SmartLink.getIsNextTo();

		// Facts.
		addFact("1", englishman, Is, With, red, "The Englishman lives in the red house.");
		addFact("2", spaniard, Is, With, dogs, "The Spaniard owns dogs.");
		addFact("3", coffee, Is, With, green, "Coffee is drunk in the green house.");
		addFact("4", ukrainian, Is, With, tea, "The Ukrainian drinks tea.");
		addFact("5", green, Is, directlyRightOf, white, "The green house is directly to the right of the white one.");
		addFact("6", stamps, Is, With, snails, "The stamp collector owns snails.");
		addFact("7", antiques, Is, With, yellow, "The antiques collector lives in the yellow house.");
		addFact("8", house3, Is, With, milk, "The man in the middle house drinks milk.");
		addFact("9", norwegian, Is, With, house1, "The Norwegian lives in the first house.");
		addFact("10", sings, Is, nextTo, fox, "The man who sings lives next to the man with the fox.");
		addFact("11", gardens, Is, With, juice, "The man who gardens drinks juice.");
		addFact("12", antiques, Is, nextTo, horse, "The antiques collector lives next to the man with the horse.");
		addFact("13", japanese, Is, With, cooking, "The Japanese man's hobby is cooking.");
		addFact("14", norwegian, Is, nextTo, blue, "The Norwegian lives next to the blue house.");

		// Solution.
		answer = new int[][]{{3, 4, 0, 2, 1}, {3, 2, 0, 1, 4}, {1, 2, 0, 3, 4}, {2, 3, 1, 0, 4}, {4, 1, 2, 3, 0}};
	}
}
