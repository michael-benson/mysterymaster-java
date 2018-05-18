package com.mysterymaster.puzzles;

import com.mysterymaster.puzzle.*;
import java.util.List;

/**
 * Mystery Master Logic Puzzle.
 * @version 2018-05-10
 * @author Michael Benson <michael.benson@mysterymaster.com>
 */
public class HelloDolly extends Puzzle {
	HelloDolly() {
		// Properties.
		myName = "HelloDolly";
		myTitle = "Hello Dolly!";

		// Nouns.
		NounType slots = addNounType("Slot");
		Noun a01 = slots.addNoun("A01");
		Noun a02 = slots.addNoun("A02");
		Noun a03 = slots.addNoun("A03");
		Noun a04 = slots.addNoun("A04");
		Noun a05 = slots.addNoun("A05");
		Noun a06 = slots.addNoun("A06");
		Noun a07 = slots.addNoun("A07");
		Noun a08 = slots.addNoun("A08");
		Noun a09 = slots.addNoun("A09");
		Noun a10 = slots.addNoun("A10");
		Noun b01 = slots.addNoun("B01");
		Noun b02 = slots.addNoun("B02");
		Noun b03 = slots.addNoun("B03");
		Noun b04 = slots.addNoun("B04");
		Noun b05 = slots.addNoun("B05");
		Noun b06 = slots.addNoun("B06");
		Noun b07 = slots.addNoun("B07");
		Noun b08 = slots.addNoun("B08");
		Noun b09 = slots.addNoun("B09");
		Noun b10 = slots.addNoun("B10");

		NounType dolls = addNounType("Doll");
		Noun annabelle = dolls.addNoun("Annabelle");
		Noun ashley = dolls.addNoun("Ashley");
		Noun holly = dolls.addNoun("Holly");
		Noun jessica = dolls.addNoun("Jessica");
		Noun kirsty = dolls.addNoun("Kirsty");
		Noun michelle = dolls.addNoun("Michelle");
		Noun paige = dolls.addNoun("Paige");
		Noun staci = dolls.addNoun("Staci");
		Noun tanya = dolls.addNoun("Tanya");
		Noun tiffany = dolls.addNoun("Tiffany");
		Noun benji = dolls.addNoun("Benji");
		Noun chad = dolls.addNoun("Chad");
		Noun damon = dolls.addNoun("Damon");
		Noun darren = dolls.addNoun("Darren");
		Noun enrique = dolls.addNoun("Enrique");
		Noun jamie = dolls.addNoun("Jamie");
		Noun joshua = dolls.addNoun("Joshua");
		Noun juan = dolls.addNoun("Juan");
		Noun keenan = dolls.addNoun("Keenan");
		Noun quentin = dolls.addNoun("Quentin");

		NounType colors = addNounType("Color");
		Noun pink1 = colors.addNoun("pink1");
		Noun pink2 = colors.addNoun("pink2");
		Noun pink3 = colors.addNoun("pink3");
		Noun pink4 = colors.addNoun("pink4");
		Noun pink5 = colors.addNoun("pink5");
		Noun red1 = colors.addNoun("red1");
		Noun red2 = colors.addNoun("red2");
		Noun gold1 = colors.addNoun("gold1");
		Noun green1 = colors.addNoun("green1");
		Noun purple1 = colors.addNoun("purple1");
		Noun blue1 = colors.addNoun("blue1");
		Noun blue2 = colors.addNoun("blue2");
		Noun blue3 = colors.addNoun("blue3");
		Noun blue4 = colors.addNoun("blue4");
		Noun blue5 = colors.addNoun("blue5");
		Noun red3 = colors.addNoun("red3");
		Noun red4 = colors.addNoun("red4");
		Noun green2 = colors.addNoun("green2");
		Noun silver1 = colors.addNoun("silver1");
		Noun yellow1 = colors.addNoun("Yellow1");

		// Links
		Link nextTo = addLink("next to", slots);
		nextTo.f = (noun1, noun2) ->
		(noun1.num < 11 && noun2.num < 11 && Math.abs(noun1.num - noun2.num) == 1) ||
		(noun1.num > 10 && noun2.num > 10 && Math.abs(noun1.num - noun2.num) == 1) ? Is : IsNot;

		Link oneAwayFrom = addLink("one away from", slots);
		oneAwayFrom.f = (noun1, noun2) ->
		(noun1.num < 11 && noun2.num < 11 && Math.abs(noun1.num - noun2.num) == 2) ||
		(noun1.num > 10 && noun2.num > 10 && Math.abs(noun1.num - noun2.num) == 2) ? Is : IsNot;

		Link twoAwayFrom = addLink("two away from", slots);
		twoAwayFrom.f = (noun1, noun2) ->
		(noun1.num < 11 && noun2.num < 11 && Math.abs(noun1.num - noun2.num) == 3) ||
		(noun1.num > 10 && noun2.num > 10 && Math.abs(noun1.num - noun2.num) == 3) ? Is : IsNot;

		Link acrossFrom = addLink("across from", slots);
		acrossFrom.f = (noun1, noun2) -> Math.abs(noun1.num - noun2.num) == 10 ? Is : IsNot;

		// Facts.
		// The female dolls wear the five pinks, first two reds, gold, first green, and purple.
		List<Noun> femaleDolls = getList(annabelle, ashley, holly, jessica, kirsty, michelle, paige, staci, tanya, tiffany);
		List<Noun> femaleColors = getList(pink1, pink2, pink3, pink4, pink5, red1, red2, gold1, green1, purple1);
		List<Noun> pinks = getList(pink1, pink2, pink3, pink4, pink5);

		// The male dolls wear the five blues, last two reds, second green, silver, and yellow.
		List<Noun> maleDolls = getList(benji, chad, damon, darren, enrique, jamie, joshua, juan, keenan, quentin);
		List<Noun> maleColors = getList(blue1, blue2, blue3, blue4, blue5, red3, red4, green2, silver1, yellow1);
		List<Noun> blues = getList(blue1, blue2, blue3, blue4, blue5);

		List<Noun> reds = getList(red1, red2, red3, red4);
		List<Noun> greens = getList(green1, green2);

		// Cabinets A and B.
		List<Noun> rowA = getList(a01, a02, a03, a04, a05, a06, a07, a08, a09, a10);
		List<Noun> rowB = getList(b01, b02, b03, b04, b05, b06, b07, b08, b09, b10);

		// The slots at either end of each cabinet.
		List<Noun> endSlots = getList(a01, a10, b01, b10);

		// The slots next to the end slots.
		List<Noun> innerEndSlots = getList(a02, a09, b02, b09);


		// Introduction.
		// The female dolls do not wear the male colors.
		addFact("0", femaleDolls, IsNot, With, maleColors);

		// The male dolls do not wear the female colors.
		addFact("0", maleDolls, IsNot, With, femaleColors);


		// Clue 1. No doll wearing pink is next to another doll wearing pink and no doll wearing blue is next to another doll wearing blue.

		// No doll wearing pink is next to another doll wearing pink.
		addFact("1", pinks, IsNot, nextTo);

		// No doll wearing blue is next to another doll wearing blue.
		addFact("1", blues, IsNot, nextTo);


		// Clue 2. Female dolls are at each end of a row, only one of those females has another female next to her.

		// Male dolls are not at the end of each row. Note: The laws ensure male colors are not at the end of each row.
		addFact("2", maleDolls, IsNot, With, endSlots);

		// Clue 3. The five dolls with pink outfits are (in some order): Tiffany, the two females
		// across from each other (one between two males and the other between two females, in some
		// order), the doll next to Keenan and the doll across from the female in green.

		// Tiffany is wearing Pink1.
		addFact("3", tiffany, Is, With, pink1);

		// The doll wearing Pink2 is across from the doll wearing Pink3.
		addFact("3", pink2, Is, acrossFrom, pink3);

		// Since they are between other dolls, Pink2 and Pink3 are not in the end slots.
		addFact("3", getList(pink2, pink3), IsNot, With, endSlots);

		// Keenan is next to the doll wearing Pink4.
		addFact("3", keenan, Is, nextTo, pink4);

		// Keenan is not next to the other dolls in pink.
		addFact("3", keenan, IsNot, nextTo, getList(pink1, pink2, pink3));

		// The female in green (Green1) is across from the doll wearing Pink5.
		addFact("3", green1, Is, acrossFrom, pink5);


		// Clue 4. The dolls with blue outfits are (in some order): two males who are across from
		// each other (each next to a female in a pink outfit who are at the end of a row), Quentin,
		// the doll across from Paige and the doll between Annabelle and Enrique (who is wearing red).

		// The two male dolls across from each other are wearing Blue1 and Blue2.
		addFact("4", blue1, Is, acrossFrom, blue2);

		// Blue1 and Blue2 are each in one of the slots next to the end slots.
		List<Noun> list4 = Helper.getListExcept(slots.nouns, innerEndSlots);
		addFact("4", getList(blue1, blue2), IsNot, With, list4);

		// Quentin is wearing Blue3.
		addFact("4", quentin, Is, With, blue3);

		// Paige is across from the doll wearing Blue4.
		addFact("4", paige, Is, acrossFrom, blue4);

		// Enrique is wearing Red3.
		addFact("4", enrique, Is, With, red3);

		// Blue5 is just between Annabelle and Enrique. The laws make sure Blue5 is not in the end slots.
		addFact("4", blue5, Is, nextTo, getList(annabelle, enrique));
		addFact("4", annabelle, Is, oneAwayFrom, enrique);


		// Clue 5. The six males in row A are (in some order): the three males next to each other,
		// two males next to each other (dressed in green and red, in some order and flanked by the
		// female in gold and Holly, in some order), and the only male not to have another male next to him.

		// The female in gold is two slots away from Holly.
		addFact("5", holly, Is, twoAwayFrom, gold1);

		// The male in green (Green2), female in gold, and Holly are not in row B.
		addFact("5", getList(green2, gold1, holly), IsNot, With, rowB);

		// The male doll in green (Green2) is next to only one male doll, who's wearing red.
		List<Noun> list5 = Helper.getListExcept(maleColors, getList(green2, red3, red4));
		addFact("5", green2, IsNot, nextTo, list5);

		// Only male dolls can be in the slots next to the end slots (A02, A09) for row A.
		List<Noun> innerEndASlots = getList(a02, a09);
		addFact("5", femaleDolls, IsNot, With, innerEndASlots);

		// Based on clues 3 - 5, I will assign Pink2 and Blue1 to cabinet A, which means Pink3 and Blue2 are in cabinet B.
		// This means Pink2 is between two males, and Pink3 is between two females.
		addFact("3", pink2, IsNot, With, rowB);
		addFact("4", blue1, IsNot, With, rowB);


		// Clue 6. The four dolls in red are: Juan, the male across from Juan, the female next to that male, and Paige.

		// Paige is wearing Red2.
		addFact("6", paige, Is, With, red2);

		// Juan is wearing Red4. From clue 4, we know that Enrique is wearing Red3
		addFact("6", juan, Is, With, red4);

		// The male wearing Red3 is across from Juan.
		addFact("6", red3, Is, acrossFrom, juan);

		// From clues 4 and 6, Enrique (Red3) is between the male wearing Blue5 and the female wearing Red1.
		addFact("6", red1, Is, nextTo, red3);
		addFact("4,6", red1, Is, oneAwayFrom, blue5);

		// Red1 and Red3 are not next to the other reds.
		addFact("6", getList(red1, red3), IsNot, nextTo, getList(red2, red4));

		// The male wearing Red3 is just between the male wearing Blue5 and the female wearing Red1 (clues 4, 6)."
		addFact("4,6", red3, Is, nextTo, getList(blue5, red1));
		addFact("4,6", blue5, Is, oneAwayFrom, red1);


		// Clue 7. The two dolls in green are a male and female next to each other in cabinet A.

		// The Green female (Green1) and the green male (Green2) are next to each other.
		addFact("7", green1, Is, nextTo, green2);

		// The two green dolls are not in cabinet B.
		addFact("7", greens, IsNot, With, rowB);

		// From clue 5, two males, next to each other dressed in green (Green2) and red, are just between the female in gold (Gold1) and Holly.
		// Since the male in green (Green2) has the male in red on one side, he must have the female in green on the other side.
		// This means the male in green (Green2) is next to Holly, and Holly must be wearing green (Green1).
		addFact("5,7", holly, Is, With, green1);

		// From clue 6, the female in red (Red1) is next to the male in red (which is Enrique in Red3 from clue 4).
		// In clue 5, the male in red has the male in green (Green2) on one side, and the female in gold (Gold1) on the other side.
		// Since Enrique (Red3), must be next to the female in red (Red1), Juan is just between the male in green and the female in gold.

		// Juan is just between the male in green (Green2) and the female in gold (Gold1) (clues 4, 5, 6, 7).
		addFact("4,5,6,7", juan, Is, nextTo, getList(green2, gold1));
		addFact("4,5,6,7", green2, Is, oneAwayFrom, gold1);


		// Clue 8. Darren is dressed in yellow and he is across from the male dressed in silver.
		addFact("8", darren, Is, With, yellow1);
		addFact("8", darren, Is, acrossFrom, silver1);


		// Clue 9. Jessica (who is not next to Staci) and Ashley are at the left and right ends of row B, respectively, and wear the same color outfit.

		// Jessica is in B01 and Ashley is in B10.
		addFact("9", jessica, Is, With, b01);
		addFact("9", ashley, Is, With, b10);

		// Jessica is not next to Staci.
		addFact("9", jessica, IsNot, nextTo, staci);

		// The introduction states the female colors are: 5 pinks, 2 reds, 1 gold, 1 green, and 1 purple.
		// Because clue 6 states that Paige wears red, Jessica and Ashley must wear pink, either Pink4 or Pink5.
		List<Noun> list9 = Helper.getListExcept(femaleColors, getList(pink4, pink5));
		addFact("9", getList(jessica, ashley), IsNot, With, list9);


		// Clue 10. Jamie is flanked by Kirsty (with Tanya across from her) and Benji, in some order.

		// Jamie is just between Kirsty and Benji. Enforce Jamie is not in the end slots.
		addFact("10", jamie, Is, nextTo, getList(kirsty, benji));
		addFact("10", kirsty, Is, oneAwayFrom, benji);

		// Kirsty is across from Tanya.
		addFact("10", kirsty, Is, acrossFrom, tanya);


		// Clue 11. Michelle and Joshua are across from each other and Chad is not wearing blue.
		addFact("11", michelle, Is, acrossFrom, joshua);

		// Chad is not wearing blue.
		addFact("11", chad, IsNot, With, blues);

		// Analysis Facts.
		boolean flag = false;

		// Pink2 is in A05 or A06.
		List<Noun> listA1 = Helper.getListExcept(rowA, getList(a05, a06));
		addFact("A1", pink2, IsNot, With, listA1, null, flag);

		// Paige is not in slot A04.
		addFact("A2", paige, IsNot, With, a04, null, flag);

		// Rules.
		Rule rule1 = addRule("2", "Only one of the female dolls at the end of each row has a female doll next to her.");
		rule1.f = (mark) -> {
			int rs = 0;

			int numMales = 0;
			int numFemales = 0;

			// Violation if slots B02 and B09 both have males or both slots have females.
			for (int i = 2; i < innerEndSlots.size(); i++) {
				Noun slot = innerEndSlots.get(i);
				// Try doll first, then color.
				int j = Mark.getPairNounNum(slot, dolls);
				if (j == 0) j = Mark.getPairNounNum(slot, colors);
				if (j > 0 && j < 11)
					++numFemales;
				else if (j > 10)
					++numMales;
			}
			if (numMales > 1 || numFemales > 1) return -1;

			// Trigger. If either slot B02 or slot B09 has doll of one gender, then the other slot has the other gender.
			Noun doll02 = Mark.getPairNoun(b02, dolls); Noun color02 = Mark.getPairNoun(b02, colors);
			Noun doll09 = Mark.getPairNoun(b09, dolls); Noun color09 = Mark.getPairNoun(b09, colors);

			Noun slotX = null; int i0 = 0;
			if (doll02 != null && doll09 == null) {
				slotX = b09; if (doll02.num > 10) i0 = 10;
			}
			else if (doll02 == null && doll09 != null) {
				slotX = b02; if (doll09.num > 10) i0 = 10;
			}
			else if (color02 != null && color09 == null) {
				slotX = b09; if (color02.num > 10) i0 = 10;
			}
			else if (color02 == null && color09 != null) {
				slotX = b02; if (color09.num > 10) i0 = 10;
			}

			if (slotX != null) {
				String msg = "Slots B02 and B09 have dolls of the opposite gender.";
				//print(msg);
				for (int i = i0; i < i0 + 10; i++) {
					rs = solver.addMarkByRule(mark, rule1, 'a', slotX, IsNot, dolls.nouns.get(i), msg); if (rs != 0) return rs;
					rs = solver.addMarkByRule(mark, rule1, 'a', slotX, IsNot, colors.nouns.get(i), msg); if (rs != 0) return rs;
				}
			}

			return rs;
		};

		Rule rule2 = addRule("5", "There must be two nonconsecutive females and four males in slots A03 to A08.");
		rule2.f = (mark) -> {
			int rs = 0;

			int numMales = 0;
			int numFemales = 0;
			int[] spots = new int[]{-1, -1};

			// Violation if there are more than 2 females or more than 4 males.
			for (int i = 2; i < 8; i++) {
				Noun slot = slots.nouns.get(i);
				// Try doll first, then color.
				int x = Mark.getPairNounNum(slot, dolls);
				if (x == 0) x = Mark.getPairNounNum(slot, colors);
				if (x > 0 && x < 11) {
					if (++numFemales > 2) return -1;
					spots[numFemales - 1] = i + 1;
				}
				else if (x > 10) {
					if (++numMales > 4) return -1;
				}
			}
			// The two females cannot be next to each other.
			if (numFemales == 2 && Math.abs(spots[1] - spots[0]) == 1) return -1;

			return rs;
		};

		Rule rule3 = addRule("5", "There are exactly six females and four males in cabinet B.");
		rule3.f = (mark) -> {
			int rs = 0;

			int numMales = 0;
			int numFemales = 0;

			// Violation if number of females more than 6 or number of males more than 4.
			for (int i = 10; i < 20; i++) {
				Noun slot = slots.nouns.get(i);
				// Try doll first, then color.
				int x = Mark.getPairNounNum(slot, dolls);
				if (x == 0) x = Mark.getPairNounNum(slot, colors);
				if (x > 0 && x < 11) {
					if (++numFemales > 6) return -1;
				}
				else if (x > 10) {
					if (++numMales > 4) return -1;
				}
			}

			return rs;
		};

		// Solution.
		answer = new int[][]{{9, 12, 10, 15, 4, 19, 5, 17, 11, 2, 3, 18, 13, 6, 8, 0, 16, 14, 7, 1}, {0, 10, 18, 13, 1, 12, 7, 15, 17, 8, 3, 11, 19, 6, 2, 9, 14, 16, 5, 4}};
	}

	@Override
	public String sayFact(Noun noun1, Verb verb, Link link, Noun noun2) {
		String msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;

		// Types: 1=Slot, 2=Doll, 3=Color
		switch (noun1.type.num) {
			case 1:
				switch (noun2.type.num) {
					case 1: break;
					case 2: break;
					case 3: break;
				}
				break;
			case 2:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = noun1.name + " " + verb.name + " in slot " + noun2.name;
						break;
					case 2:
						msg = noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3:
						if (link == With)
							msg = noun1.name + " " + verb.name + " wearing " + noun2.name;
						else
							msg = noun1.name + " " + verb.name + " " + link.name + " the doll wearing " + noun2.name;
						break;
				}
				break;
			case 3:
				switch (noun2.type.num) {
					case 1:
						if (link == With)
							msg = "The doll wearing " + noun1.name + " " + verb.name + " in slot " + noun2.name;
						break;
					case 2:
						if (link != With)
							msg = "The doll wearing " + noun1.name + " " + verb.name + " " + link.name + " " + noun2.name;
						break;
					case 3:
						msg = "The doll wearing " + noun1.name + " " + verb.name + " " + link.name + " the doll wearing " + noun2.name;
						break;
				}
				break;
		}

		return msg + ".";
	}
}
