package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-03-30
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class Floppies extends Puzzle {
	Floppies() {
		// Properties.
		myName = "Floppies";
		myTitle = "The Floppies";

		// Nouns.
		NounType floppies = addNounType("Floppie");
		Noun monsieur = floppies.addNoun("Monsieur");
		Noun felix = floppies.addNoun("Felix the Grump", "Felix");
		Noun tinyTurtle = floppies.addNoun("Tiny Turtle");
		Noun bertha = floppies.addNoun("Bertha");
		Noun ernestine = floppies.addNoun("Ernestine");

		NounType traits = addNounType("Trait");
		Noun bananas = traits.addNoun("banana-lovin' beast", "Bananas");
		Noun friend = traits.addNoun("Bertha's bestest friend", "Friend");
		Noun closet = traits.addNoun("lives-in-closet", "Closet");
		Noun gorilla = traits.addNoun("gorilla next door", "Gorilla");
		Noun karate = traits.addNoun("karate-kicking krazy", "Karate");

		NounType colors = addNounType("Color");
		Noun black = colors.addNoun("black");
		Noun blue = colors.addNoun("blue");
		Noun green = colors.addNoun("green");
		Noun orange = colors.addNoun("orange");
		Noun red = colors.addNoun("red");

		NounType firstNames = addNounType("First Name");
		Noun frank = firstNames.addNoun("Frank");
		Noun georgia = firstNames.addNoun("Georgia");
		Noun jim = firstNames.addNoun("Jim");
		Noun melanie = firstNames.addNoun("Melanie");
		Noun tony = firstNames.addNoun("Tony");

		NounType lastNames = addNounType("Last Name");
		Noun beard = lastNames.addNoun("Beard");
		Noun daVinci = lastNames.addNoun("da Vinci");
		Noun dix = lastNames.addNoun("Dix");
		Noun gresham = lastNames.addNoun("Gresham");
		Noun lopes = lastNames.addNoun("Lopes");

		// Facts.
		List<Noun> guys = getList(frank, jim, tony);
		List<Noun> gals = getList(georgia, melanie);

		addFact("1", getList(felix, bananas, blue, jim, dix), IsNot, With);
		addFact("1", dix, IsNot, With, guys);
		addFact("2", getList(karate, closet), IsNot, With, black);
		addFact("3", lopes, IsNot, With, getList(georgia, tinyTurtle));
		addFact("4", melanie, IsNot, With, getList(red, felix));
		addFact("5", tony, IsNot, With, bertha);
		addFact("6", bertha, IsNot, With, friend);
		addFact("6", friend, Is, With, orange);
		addFact("6", friend, IsNot, With, guys);
		addFact("7", getList(frank, tony), IsNot, With, closet);
		addFact("8", daVinci, Is, With, black);
		addFact("8", daVinci, IsNot, With, getList(georgia, melanie, ernestine));
		addFact("9", tinyTurtle, Is, With, green);
		addFact("9", tinyTurtle, IsNot, With, getList(melanie, bananas));
		addFact("10", getList(felix, tony), IsNot, With, gorilla);
		addFact("11", karate, IsNot, With, getList(red, lopes));
		addFact("12", beard, IsNot, With, closet);

		// Solution.
		answer = new int[][]{{0, 2, 4, 3, 1}, {0, 4, 2, 1, 3}, {4, 1, 2, 0, 3}, {1, 3, 0, 4, 2}};
	}
	
	@Override
	public String 	sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Floppie, 2=Trait, 3=Color, 4=First Name, 5=Last Name
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2:
						msg = noun1.name + " " + verb.name + " the " + noun2.name + " floppie";
						break;
					case 3:
						msg = noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 4:
						msg = noun1.name + " " + verb.name + " played by " + noun2.name;
						break;
					case 5:
						msg = noun1.name + " " + verb.name + " played by " + noun2.name;
						break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3:
						msg = "The " + noun1.name + " floppie " + verb.name + " " + noun2.name;
						break;
					case 4:
						msg = "The " + noun1.name + " floppie " + verb.name + " played by " + noun2.name;
						break;
					case 5:
						msg = "The " + noun1.name + " floppie " + verb.name + " played by " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
					case 4:
						msg = "The " + noun1.name + " floppie " + verb.name + " played by " + noun2.name;
						break;
					case 5:
						msg = "The " + noun1.name + " floppie " + verb.name + " played by " + noun2.name;
						break;
				}
				break;
			case 4:
				switch (noun2.type.num) {
					case 1:
						msg = noun1.name + " " + (verb == Is ? "plays" : "does not play") + " " + noun2.name;
						break;
					case 2:
						msg = noun1.name + " " + (verb == Is ? "plays" : "does not play") + " the " + noun2.name + " floppie";
						break;
					case 3:
						msg = noun1.name + "'s floppie " + verb.name + " " + noun2.name;
						break;
					case 4: break;
					case 5:
						msg = noun1.name + " " + verb.name + " " + noun2.name;
						break;
				}
				break;
			case 5:
				switch (noun2.type.num) {
					case 1:
						msg = noun1.name + " " + (verb == Is ? "plays" : "does not play") + " " + noun2.name;
						break;
					case 2:
						msg = noun1.name + " " + (verb == Is ? "plays" : "does not play") + " the " + noun2.name + " floppie";
						break;
					case 3:
						msg = noun1.name + "'s floppie " + verb.name + " " + noun2.name;
						break;
					case 4:
						msg = noun1.name + " " + verb.name + " " + noun2.name;
						break;
					case 5: break;
				}
				break;
		}

		return msg + ".";
	}
}
