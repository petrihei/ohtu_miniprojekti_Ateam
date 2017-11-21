
package tekstikayttis;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dao.Tietokanta;
import java.util.ArrayList;
import java.util.List;
import logiikka.Logiikka;
import static org.junit.Assert.*;


public class Stepdefs {
	IOStub io;
	Tekstikayttis kayttis;
	Tietokanta db = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
	Logiikka logiikka = new Logiikka(db);
	ArrayList<String> inputs = new ArrayList();

	@Given("^command save tip is selected$")
	public void command_save_selected(){
		inputs.add("1");
	}

	@When("^title \"([^\"]*)\" and description \"([^\"]*)\" are entered$")
	public void title_and_description_are_entered(String title, String description){
		inputs.add(title);
		inputs.add(description);
		inputs.add("Kirja");
		inputs.add("0");

		io = new IOStub(inputs);
		kayttis = new Tekstikayttis(logiikka, io);
		kayttis.kayttoliittyma();
	}

	@Then("^system will respond with message \"([^\"]*)\"$")
	public void system_will_respond_with_message(String expectedOutput) throws Throwable{
		assertTrue(arrayContainsSubstring(io.getOutputs(), expectedOutput));
	}

	@Then("^title \"([^\"]*)\" and description \"([^\"]*)\" are displayed")
	public void title_and_description_are_displayed(String title, String description){
		assertTrue(arrayContainsSubstring(io.getOutputs(), title));
		assertTrue(arrayContainsSubstring(io.getOutputs(), description));
	}

	private boolean arrayContainsSubstring(List<String> list, String substr) {
    	for (String str : list) {
			if (str.contains(substr)) return true;
		}
		return false;
	}
}