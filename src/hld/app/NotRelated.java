package hld.app;

import org.gedcom4j.comparators.IndividualByLastNameFirstNameComparator;
import org.gedcom4j.exception.GedcomParserException;
import org.gedcom4j.model.Gedcom;
import org.gedcom4j.model.Individual;
import org.gedcom4j.parser.GedcomParser;
import org.gedcom4j.query.Finder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotRelated {

    public static void main(String[] args) throws IOException,
            GedcomParserException {

        // Load the file
        GedcomParser p = new GedcomParser();
        p.load("H:\\IdeaProjects\\hld\\resources\\20180402_DudashPure.ged");
        Gedcom g = p.getGedcom();

        // Find the main person of interest
        Finder f = new Finder(g);
        List<Individual> found = f.findByName("Dudash",
                "Heather Lynne");
        Individual personOfInterest = found.iterator().next();

        // Building list of people related to person of interest.
        // Start with all the person's ancestors
        Set<Individual> bigList = personOfInterest.getAncestors();

        // Add all the spouses and descendants of everyone in the list so far
        int count = 0;
        // Keep adding the spouses and descendants until the list stops growing
        while (bigList.size() > count) {
            count = bigList.size(); // This is the size of the list before
            // possible adding to it

            // Make a copy to prevent concurrent modification exceptions
            Set<Individual> family = new HashSet<>(bigList);

            // For everyone in the list, add their spouses and their descendants
            for (Individual i : family) {
                bigList.addAll(i.getSpouses());
                bigList.addAll(i.getDescendants());
            }
        }
        // At this point, bigList contains a list of all the relatives of
        // personOfInterest

        // Get a list of everyone and sort them last name first
        List<Individual> everybody = new ArrayList<>(g.getIndividuals().values());
        everybody.sort(new IndividualByLastNameFirstNameComparator());

        // Now go through the list of everybody, and write out their names if
        // they are not in the list of relatives of the person of interest
        int k = 0;
        for (Individual i : everybody) {
            if (!bigList.contains(i)) {
                System.out.println(i);
                k++;
            }
        }
        System.out.println(k + " name(s)");
    }
}
