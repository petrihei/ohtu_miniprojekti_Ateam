
package tekstikayttis;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.ArrayList;
import static org.junit.Assert.*;


public class Stepdefs {
	IOStub io;
	Tekstikayttis kayttis;
	ArrayList<String> inputs = new ArrayList();

	@Given("^command save tip is selected$")
	public void command_save_selected(){
		inputs.add("1");
	}

	@When("^title \"([^\"]*)\" and description \"([^\"]*)\" are entered$")
	public void title_and_description_are_entered(String title, String description){
		inputs.add(title);
		inputs.add(description);

		io = new IOStub(inputs);
		kayttis = new Tekstikayttis(io);
		kayttis.kirjanLisays();
	}

	@Then("^system will respond with message \"([^\"]*)\"$")
	public void system_will_respond_with_message(String expectedOutput) throws Throwable{
		assertTrue(io.getOutputs().contains(expectedOutput));
	}

	@Then("^title \"([^\"]*)\" and description \"([^\"]*)\" are displayed")
	public void title_and_description_are_displayed(String title, String description){
		assertTrue(io.getOutputs().contains(title));
		assertTrue(io.getOutputs().contains(description));
	}
}